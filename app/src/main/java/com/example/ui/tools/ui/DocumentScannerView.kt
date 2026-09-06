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
import com.example.ui.tools.ai.LearningContext
import com.example.ui.tools.scanner.DocumentScannerEngine
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun DocumentScannerView(
    viewModel: StudyViewModel,
    subjectName: String,
    onOpenAskTamheroWithText: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val isDark by viewModel.isDarkTheme.collectAsState()

    var extractedText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var scannedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var documentTitle by remember { mutableStateOf("\ Scan") }
    var showSavedDocsDialog by remember { mutableStateOf(false) }
    var showExportMenu by remember { mutableStateOf(false) }
    
    // Popup state
    var popupMessage by remember { mutableStateOf("") }
    LaunchedEffect(popupMessage) {
        if (popupMessage.isNotEmpty()) {
            kotlinx.coroutines.delay(1500)
            popupMessage = ""
        }
    }

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scanResult = com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            scanResult?.pages?.firstOrNull()?.imageUri?.let { uri ->
                isProcessing = true
                coroutineScope.launch {
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream?.close()
                        
                        if (bitmap != null) {
                            scannedImageBitmap = bitmap
                            val ocrResult = DocumentScannerEngine.extractTextFromDocument(bitmap, "clean", subjectName)
                            extractedText = ocrResult.text
                            popupMessage = "Document scanned successfully!"
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        popupMessage = "Failed to process scan."
                    }
                    isProcessing = false
                }
            }
        }
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
                val file = File(context.cacheDir, "scan_\.jpg")
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
                
                val file = File(context.cacheDir, "scan_\.pdf")
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
                val file = File(context.cacheDir, "scan_\.txt")
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
        val options = GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(true)
            .setPageLimit(1)
            .setResultFormats(RESULT_FORMAT_JPEG)
            .setScannerMode(SCANNER_MODE_FULL)
            .build()
            
        val scanner = GmsDocumentScanning.getClient(options)
        scanner.getStartScanIntent(context as Activity)
            .addOnSuccessListener { intentSender ->
                scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
            }
            .addOnFailureListener {
                popupMessage = "Failed to open scanner."
            }
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
                        IconButton(onClick = { launchScanner() }) {
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
                            text = "Tap the camera to scan a document.",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { launchScanner() },
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
                                                val doc = ScannedDocument(
                                                    title = documentTitle,
                                                    extractedText = extractedText,
                                                    dateScanned = System.currentTimeMillis(),
                                                    subject = subjectName
                                                )
                                                viewModel.saveScannedDocument(doc)
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
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { onOpenAskTamheroWithText(extractedText) },
                                        colors = ButtonDefaults.buttonColors(containerColor = HolographicAqua),
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(Icons.Default.SmartToy, contentDescription = null, tint = Color.White)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Ask Tamhero about this text", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    com.example.ui.tools.ui.ContextualAiActions(
                                        viewModel = viewModel,
                                        learningContext = com.example.ui.tools.ai.LearningContext(
                                            courseId = "scanner",
                                            courseName = subjectName,
                                            topicId = "scanned_doc",
                                            topicName = documentTitle,
                                            contentId = "scan_\",
                                            contentType = "scanned_doc",
                                            contentText = extractedText
                                        ),
                                        actions = com.example.ui.tools.ui.ContextualAiActionSets.scannedPage(),
                                        modifier = Modifier.fillMaxWidth()
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
}
