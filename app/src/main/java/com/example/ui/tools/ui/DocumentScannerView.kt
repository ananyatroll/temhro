package com.example.ui.tools.ui

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.IntentSenderRequest
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ScannedDocument
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import com.example.ui.tools.scanner.DocumentScannerEngine
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun DocumentScannerView(
    viewModel: StudyViewModel,
    subjectName: String
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val isDark by viewModel.isDarkTheme.collectAsState()

    var extractedText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var scannedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var documentTitle by remember { mutableStateOf("Document Scan") }
    var showSourceSelector by remember { mutableStateOf(false) }
    var showSavedDocsDialog by remember { mutableStateOf(false) }
    var showExportMenu by remember { mutableStateOf(false) }
    var popupMessage by remember { mutableStateOf("") }
    LaunchedEffect(popupMessage) {
        if (popupMessage.isNotEmpty()) {
            kotlinx.coroutines.delay(1500)
            popupMessage = ""
        }
    }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    fun processBitmap(bitmap: Bitmap) {
        isProcessing = true
        coroutineScope.launch {
            try {
                // Apply smart document boundary detection & perspective crop
                val corners = DocumentScannerEngine.detectDocumentCorners(bitmap)
                val cropped = DocumentScannerEngine.warpPerspective(bitmap, corners)
                val enhanced = DocumentScannerEngine.enhanceDocumentBitmap(cropped, "clean")
                scannedImageBitmap = enhanced
                
                // Extract text offline using bundled on-device ML Kit
                val ocrResult = DocumentScannerEngine.extractTextFromDocument(enhanced, "clean", subjectName)
                extractedText = ocrResult.text
                popupMessage = "Document scanned successfully!"
            } catch (e: Exception) {
                e.printStackTrace()
                // Fallback to direct bitmap if perspective warp fails
                try {
                    scannedImageBitmap = bitmap
                    val ocrResult = DocumentScannerEngine.extractTextFromDocument(bitmap, "clean", subjectName)
                    extractedText = ocrResult.text
                    popupMessage = "Document scanned successfully!"
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    popupMessage = "Failed to process scan."
                }
            } finally {
                isProcessing = false
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            coroutineScope.launch {
                try {
                    val inputStream = context.contentResolver.openInputStream(tempCameraUri!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()
                    if (bitmap != null) {
                        processBitmap(bitmap)
                    } else {
                        popupMessage = "Could not load captured photo."
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    popupMessage = "Failed to open camera photo."
                }
            }
        }
    }

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            coroutineScope.launch {
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()
                    if (bitmap != null) {
                        processBitmap(bitmap)
                    } else {
                        popupMessage = "Could not load selected image."
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    popupMessage = "Failed to process image."
                }
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            popupMessage = "Camera permission is required to scan documents."
        }
    }

    fun launchCamera() {
        val permissionCheck = androidx.core.content.ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        )
        if (permissionCheck != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            return
        }

        try {
            val photoFile = File(context.cacheDir, "camera_scan_${System.currentTimeMillis()}.jpg")
            val uri = androidx.core.content.FileProvider.getUriForFile(
                context,
                "com.aistudio.tinat.studyapp.provider",
                photoFile
            )
            tempCameraUri = uri
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            e.printStackTrace()
            popupMessage = "Unable to launch camera: ${e.message}"
        }
    }

    fun launchGallery() {
        scannerLauncher.launch("image/*")
    }

    fun openScanOptions() {
        showSourceSelector = true
    }


    // Function to share file
    fun shareFile(file: File, mimeType: String) {
        try {
            val uri = androidx.core.content.FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                file
            )
            val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(android.content.Intent.EXTRA_STREAM, uri)
                addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(android.content.Intent.createChooser(intent, "Export Document"))
        } catch (e: Exception) {
            e.printStackTrace()
            popupMessage = "Export failed"
        }
    }

    fun exportAsImage() {
        val bitmap = scannedImageBitmap ?: return
        coroutineScope.launch {
            try {
                val file = File(context.cacheDir, "scan_image.jpg")
                val out = java.io.FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
                out.close()
                shareFile(file, "image/jpeg")
            } catch (e: Exception) {
                popupMessage = "Failed to export image"
            }
        }
    }

    fun exportAsPdf() {
        val bitmap = scannedImageBitmap ?: return
        coroutineScope.launch {
            try {
                val pdfDocument = android.graphics.pdf.PdfDocument()
                val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                page.canvas.drawBitmap(bitmap, 0f, 0f, null)
                pdfDocument.finishPage(page)
                
                val file = File(context.cacheDir, "scan_document.pdf")
                val out = java.io.FileOutputStream(file)
                pdfDocument.writeTo(out)
                pdfDocument.close()
                out.close()
                
                shareFile(file, "application/pdf")
            } catch (e: Exception) {
                popupMessage = "Failed to export PDF"
            }
        }
    }
    
    fun exportAsText() {
        if (extractedText.isEmpty()) return
        coroutineScope.launch {
            try {
                val file = File(context.cacheDir, "scan_document.txt")
                val out = java.io.FileOutputStream(file)
                out.write(extractedText.toByteArray())
                out.close()
                shareFile(file, "text/plain")
            } catch (e: Exception) {
                popupMessage = "Failed to export text"
            }
        }
    }

    fun launchScanner() {
        scannerLauncher.launch("image/*")
    }

    Box(modifier = Modifier.fillMaxSize().background(if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Surface(
                color = if (isDark) CardBgDark else Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Document Scanner",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color.White else Color.Black
                    )
                    Row {
                        IconButton(onClick = { showSavedDocsDialog = true }) {
                            Icon(Icons.Default.Folder, contentDescription = "Saved Scans", tint = EmeraldPrimary)
                        }
                        IconButton(onClick = { openScanOptions() }) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Scan Document", tint = EmeraldPrimary)
                        }
                    }
                }
            }

            if (scannedImageBitmap == null) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.DocumentScanner,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = EmeraldPrimary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Take a photo or upload a document to scan.",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { openScanOptions() },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Start Scanning", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp)) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.fillMaxWidth().height(300.dp)
                        ) {
                            Image(
                                bitmap = scannedImageBitmap!!.asImageBitmap(),
                                contentDescription = "Scanned Document",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    
                    if (isProcessing) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = EmeraldPrimary)
                            }
                        }
                    } else if (extractedText.isNotEmpty()) {
                        item {
                            Surface(
                                color = if (isDark) CardBgDark else Color.White,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Extracted Text",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = EmeraldPrimary
                                        )
                                        Row {
                                            IconButton(onClick = {
                                                clipboardManager.setText(AnnotatedString(extractedText))
                                                popupMessage = "Text copied to clipboard!"
                                            }) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.Gray)
                                            }
                                            IconButton(onClick = {
                                                 viewModel.saveScannedDoc(documentTitle, 1, extractedText)
                                                 popupMessage = "Document saved!"
                                             }) {
                                                Icon(Icons.Default.Save, contentDescription = "Save", tint = EmeraldPrimary)
                                            }
                                            
                                            Box {
                                                IconButton(onClick = { showExportMenu = true }) {
                                                    Icon(Icons.Default.Share, contentDescription = "Export", tint = EmeraldPrimary)
                                                }
                                                DropdownMenu(
                                                    expanded = showExportMenu,
                                                    onDismissRequest = { showExportMenu = false },
                                                    modifier = Modifier.background(if (isDark) CardBgDark else Color.White)
                                                ) {
                                                    DropdownMenuItem(
                                                        text = { Text("Export as PDF", color = if (isDark) Color.White else Color.Black) },
                                                        onClick = { showExportMenu = false; exportAsPdf() },
                                                        leadingIcon = { Icon(Icons.Default.PictureAsPdf, tint = EmeraldPrimary, contentDescription = null) }
                                                    )
                                                    DropdownMenuItem(
                                                        text = { Text("Export as Image (JPEG)", color = if (isDark) Color.White else Color.Black) },
                                                        onClick = { showExportMenu = false; exportAsImage() },
                                                        leadingIcon = { Icon(Icons.Default.Image, tint = EmeraldPrimary, contentDescription = null) }
                                                    )
                                                    DropdownMenuItem(
                                                        text = { Text("Export as Text File", color = if (isDark) Color.White else Color.Black) },
                                                        onClick = { showExportMenu = false; exportAsText() },
                                                        leadingIcon = { Icon(Icons.Default.Description, tint = EmeraldPrimary, contentDescription = null) }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                     Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = extractedText,
                                        color = if (isDark) Color.LightGray else Color.DarkGray,
                                        fontSize = 15.sp,
                                        lineHeight = 22.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
        
        // Popup Overlay
        AnimatedVisibility(
            visible = popupMessage.isNotEmpty(),
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Surface(
                color = Color(0xFF0F172A).copy(alpha = 0.95f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, EmeraldPrimary.copy(alpha = 0.5f)),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = popupMessage,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }

    // Modal Sheet: Choose Camera or Gallery
    if (showSourceSelector) {
        AlertDialog(
            onDismissRequest = { showSourceSelector = false },
            containerColor = if (isDark) CardBgDark else Color.White,
            title = {
                Text(
                    text = "Scan Document",
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color.White else Color.Black
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Text(
                        text = "Choose how you would like to scan your document. Both work completely offline.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        onClick = {
                            showSourceSelector = false
                            launchCamera()
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = EmeraldPrimary.copy(alpha = 0.1f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text("Take Photo with Camera", fontWeight = FontWeight.Bold, color = if (isDark) Color.White else Color.Black)
                                Text("Real-time camera capture & scan", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        onClick = {
                            showSourceSelector = false
                            launchGallery()
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = EmeraldPrimary.copy(alpha = 0.1f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text("Upload from Gallery / Files", fontWeight = FontWeight.Bold, color = if (isDark) Color.White else Color.Black)
                                Text("Select an existing document photo", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showSourceSelector = false }) {
                    Text("Cancel", color = EmeraldPrimary)
                }
            }
        )
    }

    // Saved Documents Dialog
    if (showSavedDocsDialog) {
        val savedDocs by viewModel.scannedDocs.collectAsState()
        AlertDialog(
            onDismissRequest = { showSavedDocsDialog = false },
            containerColor = if (isDark) CardBgDark else Color.White,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saved Scans (${savedDocs.size})",
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color.White else Color.Black
                    )
                    IconButton(onClick = { showSavedDocsDialog = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }
            },
            text = {
                if (savedDocs.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(140.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No saved documents yet.", color = Color.Gray)
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)) {
                        itemsIndexed(savedDocs) { _, doc ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E293B) else Color(0xFFF8FAFC)),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(doc.title, fontWeight = FontWeight.Bold, color = if (isDark) Color.White else Color.Black, maxLines = 1)
                                        Text(
                                            doc.extractedText.take(60) + if (doc.extractedText.length > 60) "..." else "",
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            maxLines = 2
                                        )
                                    }
                                    Row {
                                        IconButton(onClick = {
                                            extractedText = doc.extractedText
                                            documentTitle = doc.title
                                            showSavedDocsDialog = false
                                            popupMessage = "Loaded scan: ${doc.title}"
                                        }) {
                                            Icon(Icons.Default.Visibility, contentDescription = "View", tint = EmeraldPrimary)
                                        }
                                        IconButton(onClick = {
                                            viewModel.deleteScannedDoc(doc.id)
                                            popupMessage = "Deleted scan"
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}
