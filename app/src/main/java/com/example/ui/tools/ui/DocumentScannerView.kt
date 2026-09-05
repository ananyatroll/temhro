package com.example.ui.tools.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.PointF
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.data.ScannedDocument
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import com.example.ui.tools.ai.LearningContext
import com.example.ui.tools.scanner.DocumentCorners
import com.example.ui.tools.scanner.DocumentScannerEngine
import com.example.ui.tools.scanner.ScannedPageItem
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale

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

    val pages = remember { mutableStateListOf<ScannedPageItem>() }
    var activePageIndex by remember { mutableStateOf(0) }
    var isInCropMode by remember { mutableStateOf(false) }

    var documentTitle by remember { mutableStateOf("Curriculum Lesson Scan") }
    var extractedText by remember { mutableStateOf("") }
    var ocrConfidence by remember { mutableFloatStateOf(0.96f) }
    var uncertainWords by remember { mutableStateOf(listOf<String>()) }
    var detectedSubject by remember { mutableStateOf(subjectName.ifBlank { "History" }) }
    var isTextExpanded by remember { mutableStateOf(false) }
    var showSavedDocsDialog by remember { mutableStateOf(false) }

    var actionFeedback by remember { mutableStateOf<String?>(null) }
    var isProcessing by remember { mutableStateOf(false) }

    fun processOcrForActivePage(page: ScannedPageItem) {
        val ocrResult = DocumentScannerEngine.extractTextFromDocument(
            bitmap = page.processedBitmap,
            filterMode = page.enhancementMode,
            fallbackSubject = detectedSubject
        )
        extractedText = ocrResult.text
        ocrConfidence = ocrResult.confidence
        uncertainWords = ocrResult.uncertainWords
        detectedSubject = ocrResult.detectedSubject

        // Connect automatically into system-wide LearningContext
        viewModel.setLearningContext(
            LearningContext(
                courseName = detectedSubject,
                topicName = ocrResult.title,
                contentText = ocrResult.text,
                contentType = "scanned_doc"
            )
        )
    }

    // Camera Capture Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val detectedCorners = DocumentScannerEngine.detectDocumentCorners(bitmap)
            val corrected = DocumentScannerEngine.correctPerspective(bitmap, detectedCorners)
            val enhanced = DocumentScannerEngine.enhanceDocumentBitmap(corrected, "clean")
            val newPage = ScannedPageItem(
                rawBitmap = bitmap,
                processedBitmap = enhanced,
                corners = detectedCorners,
                enhancementMode = "clean"
            )
            pages.add(newPage)
            activePageIndex = pages.size - 1
            processOcrForActivePage(newPage)
            actionFeedback = "Captured Page ${pages.size}! Tap 'Crop/Adjust' to fine-tune perspective."
        }
    }

    // Photo Gallery Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    }
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }
                val detectedCorners = DocumentScannerEngine.detectDocumentCorners(bitmap)
                val corrected = DocumentScannerEngine.correctPerspective(bitmap, detectedCorners)
                val enhanced = DocumentScannerEngine.enhanceDocumentBitmap(corrected, "clean")
                val newPage = ScannedPageItem(
                    rawBitmap = bitmap,
                    processedBitmap = enhanced,
                    corners = detectedCorners,
                    enhancementMode = "clean"
                )
                pages.add(newPage)
                activePageIndex = pages.size - 1
                processOcrForActivePage(newPage)
                actionFeedback = "Imported Page ${pages.size} from Gallery!"
            } catch (e: Exception) {
                actionFeedback = "Error importing image: ${e.message}"
            }
        }
    }

    // Load initial curriculum document on startup if empty
    LaunchedEffect(Unit) {
        if (pages.isEmpty()) {
            val sample = DocumentScannerEngine.createSampleCurriculumNoteBitmap()
            val corners = DocumentScannerEngine.detectDocumentCorners(sample)
            val corrected = DocumentScannerEngine.correctPerspective(sample, corners)
            val enhanced = DocumentScannerEngine.enhanceDocumentBitmap(corrected, "clean")
            val page = ScannedPageItem(
                rawBitmap = sample,
                processedBitmap = enhanced,
                corners = corners,
                enhancementMode = "clean"
            )
            pages.add(page)
            activePageIndex = 0
            processOcrForActivePage(page)
        }
    }

    val activePage = pages.getOrNull(activePageIndex)

    Column(modifier = Modifier.fillMaxSize()) {

        // Feedback Banner
        AnimatedVisibility(visible = actionFeedback != null) {
            Surface(
                color = EmeraldPrimary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = actionFeedback ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        // Top Action Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { cameraLauncher.launch(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Scan Page", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                FilledTonalButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Import", fontSize = 12.sp)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                    onClick = {
                        if (activePage != null) {
                            isInCropMode = !isInCropMode
                        }
                    },
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(if (isInCropMode) EmeraldPrimary else (if (isDark) CardBgDark else Color(0xFFE2E8F0)))
                ) {
                    Icon(
                        imageVector = Icons.Default.Crop,
                        contentDescription = "Crop & Perspective",
                        tint = if (isInCropMode) Color.White else (if (isDark) TextLight else Color(0xFF0F172A)),
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = {
                        if (activePage != null) {
                            activePage.rotationDegrees = (activePage.rotationDegrees + 90f) % 360f
                            activePage.processedBitmap = DocumentScannerEngine.rotateBitmap(activePage.processedBitmap, 90f)
                            actionFeedback = "Rotated 90°"
                        }
                    },
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(if (isDark) CardBgDark else Color(0xFFE2E8F0))
                ) {
                    Icon(
                        imageVector = Icons.Default.RotateRight,
                        contentDescription = "Rotate",
                        tint = if (isDark) TextLight else Color(0xFF0F172A),
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = {
                        // Save document to Database
                        coroutineScope.launch {
                            viewModel.saveScannedDoc(
                                title = documentTitle,
                                pageCount = pages.size,
                                text = extractedText
                            )
                            actionFeedback = "Saved document '$documentTitle' (${pages.size} pages)!"
                            kotlinx.coroutines.delay(2000)
                            actionFeedback = null
                        }
                    },
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(EmeraldPrimary.copy(alpha = 0.15f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save Document",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Multi-Page Thumbnails Strip
        if (pages.size > 1) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(pages) { index, page ->
                    val isSelected = index == activePageIndex
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, if (isSelected) EmeraldPrimary else Color.Transparent),
                        modifier = Modifier
                            .size(width = 54.dp, height = 72.dp)
                            .clickable {
                                activePageIndex = index
                                processOcrForActivePage(page)
                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                bitmap = page.processedBitmap.asImageBitmap(),
                                contentDescription = "Page ${index + 1}",
                                modifier = Modifier.fillMaxSize()
                            )
                            Surface(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(bottomStart = 4.dp),
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Body Scrollable Area
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Interactive Crop & Perspective Mode
            if (isInCropMode && activePage != null) {
                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isDark) CardBgDark else Color(0xFF0F172A),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Drag the 4 corner handles to correct perspective:",
                                color = EmeraldLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Interactive 4-Corner Viewport
                            var cornersState by remember(activePage.id) { mutableStateOf(activePage.corners) }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    bitmap = activePage.rawBitmap.asImageBitmap(),
                                    contentDescription = "Document to crop",
                                    modifier = Modifier.fillMaxSize()
                                )

                                // Draggable corner pins canvas overlay
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    val w = size.width
                                    val h = size.height
                                    val rawW = activePage.rawBitmap.width.toFloat()
                                    val rawH = activePage.rawBitmap.height.toFloat()

                                    val scaleX = w / rawW
                                    val scaleY = h / rawH

                                    val pTL = Offset(cornersState.topLeft.x * scaleX, cornersState.topLeft.y * scaleY)
                                    val pTR = Offset(cornersState.topRight.x * scaleX, cornersState.topRight.y * scaleY)
                                    val pBR = Offset(cornersState.bottomRight.x * scaleX, cornersState.bottomRight.y * scaleY)
                                    val pBL = Offset(cornersState.bottomLeft.x * scaleX, cornersState.bottomLeft.y * scaleY)

                                    // Draw polygon bounding quad
                                    drawLine(color = Color(0xFF10B981), start = pTL, end = pTR, strokeWidth = 3f)
                                    drawLine(color = Color(0xFF10B981), start = pTR, end = pBR, strokeWidth = 3f)
                                    drawLine(color = Color(0xFF10B981), start = pBR, end = pBL, strokeWidth = 3f)
                                    drawLine(color = Color(0xFF10B981), start = pBL, end = pTL, strokeWidth = 3f)

                                    // Draw corner pins
                                    drawCircle(color = Color.White, radius = 12f, center = pTL)
                                    drawCircle(color = Color(0xFF10B981), radius = 8f, center = pTL)

                                    drawCircle(color = Color.White, radius = 12f, center = pTR)
                                    drawCircle(color = Color(0xFF10B981), radius = 8f, center = pTR)

                                    drawCircle(color = Color.White, radius = 12f, center = pBR)
                                    drawCircle(color = Color(0xFF10B981), radius = 8f, center = pBR)

                                    drawCircle(color = Color.White, radius = 12f, center = pBL)
                                    drawCircle(color = Color(0xFF10B981), radius = 8f, center = pBL)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(onClick = {
                                    cornersState = DocumentScannerEngine.detectDocumentCorners(activePage.rawBitmap)
                                    activePage.corners = cornersState
                                }) {
                                    Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(16.dp), tint = EmeraldPrimary)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Auto-Detect", color = EmeraldPrimary, fontSize = 12.sp)
                                }

                                Button(
                                    onClick = {
                                        val corrected = DocumentScannerEngine.correctPerspective(activePage.rawBitmap, cornersState)
                                        val enhanced = DocumentScannerEngine.enhanceDocumentBitmap(corrected, activePage.enhancementMode)
                                        activePage.corners = cornersState
                                        activePage.processedBitmap = enhanced
                                        isInCropMode = false
                                        processOcrForActivePage(activePage)
                                        actionFeedback = "Perspective corrected successfully!"
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Apply Crop", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // Document Preview Card
            if (activePage != null && !isInCropMode) {
                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isDark) CardBgDark else Color.White,
                        border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            // Title row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Page ${activePageIndex + 1} of ${pages.size} • $detectedSubject",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary
                                    )
                                    Text(
                                        text = documentTitle,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Black,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = if (isDark) Color.White else Color(0xFF0F172A)
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = EmeraldPrimary.copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = "${(ocrConfidence * 100).toInt()}% Confidence",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // High-Res Image Preview Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isDark) Color(0xFF0B1329) else Color(0xFFF8FAFC)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    bitmap = activePage.processedBitmap.asImageBitmap(),
                                    contentDescription = "Enhanced Document Page",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Enhancement Filters
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val filters = listOf(
                                    "clean" to "Clean",
                                    "auto" to "Auto",
                                    "threshold_bw" to "B&W",
                                    "grayscale" to "Grayscale",
                                    "color" to "Color",
                                    "original" to "Original"
                                )
                                filters.forEach { (mode, label) ->
                                    val isSelected = activePage.enhancementMode == mode
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            activePage.enhancementMode = mode
                                            val corrected = DocumentScannerEngine.correctPerspective(activePage.rawBitmap, activePage.corners)
                                            activePage.processedBitmap = DocumentScannerEngine.enhanceDocumentBitmap(corrected, mode)
                                            processOcrForActivePage(activePage)
                                        },
                                        label = { Text(label, fontSize = 11.sp) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = EmeraldPrimary,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Real Extracted OCR Text Card
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isDark) CardBgDark else Color.White,
                    border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.TextFields, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Extracted Text (Editable)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) Color.White else Color(0xFF0F172A)
                                )
                            }

                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(extractedText))
                                    actionFeedback = "Copied text to clipboard!"
                                    coroutineScope.launch {
                                        kotlinx.coroutines.delay(2000)
                                        actionFeedback = null
                                    }
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp), tint = EmeraldPrimary)
                            }
                        }

                        OutlinedTextField(
                            value = extractedText,
                            onValueChange = { extractedText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            minLines = 4,
                            maxLines = 10,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = EmeraldPrimary,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 17.sp)
                        )

                        // Uncertain words check
                        if (uncertainWords.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Verify terms: ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) TextMuted else Color(0xFF64748B)
                                )
                                uncertainWords.take(4).forEach { word ->
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = GoldAccent.copy(alpha = 0.2f),
                                        modifier = Modifier.padding(end = 4.dp)
                                    ) {
                                        Text(
                                            text = word,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = if (isDark) GoldAccent else Color(0xFF854D0E),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 1-Tap Cross-System AI Actions
            item {
                Text(
                    text = "Transform Scanned Page into Study Materials:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) TextLight else Color(0xFF0F172A),
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            onOpenAskTamheroWithText("Explain this scanned page from $detectedSubject in detail:\n$extractedText")
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.SmartToy, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ask Tamhero", fontSize = 12.sp)
                    }

                    FilledTonalButton(
                        onClick = {
                            onOpenAskTamheroWithText("Summarize the key exam takeaways from this scanned $detectedSubject note:\n$extractedText")
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Summarize, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Summarize", fontSize = 12.sp)
                    }

                    FilledTonalButton(
                        onClick = {
                            viewModel.saveGeneratedNoteToDatabase(
                                title = "$detectedSubject: Scanned Note",
                                content = extractedText,
                                subjectId = detectedSubject.lowercase(Locale.ROOT)
                            )
                            actionFeedback = "Saved scanned page to your Study Notes!"
                            coroutineScope.launch {
                                kotlinx.coroutines.delay(2000)
                                actionFeedback = null
                            }
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.BookmarkBorder, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Save Note", fontSize = 12.sp)
                    }

                    FilledTonalButton(
                        onClick = {
                            onOpenAskTamheroWithText("Generate 4 multiple-choice practice questions with detailed explanations from this scanned text:\n$extractedText")
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Quiz, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Create MCQs", fontSize = 12.sp)
                    }
                }
            }

            // Export & Share Card
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isDark) CardBgDark else Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Export Multi-Page Document",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color.White else Color(0xFF0F172A)
                            )
                            Text(
                                text = "${pages.size} page(s) • PDF / Text format",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            OutlinedButton(
                                onClick = {
                                    try {
                                        val pdfFile = DocumentScannerEngine.exportToPdf(
                                            context = context,
                                            pages = pages.map { it.processedBitmap },
                                            documentTitle = documentTitle,
                                            extractedText = extractedText
                                        )
                                        val uri = FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.fileprovider",
                                            pdfFile
                                        )
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "application/pdf"
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Share Scanned PDF"))
                                    } catch (e: Exception) {
                                        actionFeedback = "Export error: ${e.message}"
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.PictureAsPdf, null, modifier = Modifier.size(14.dp), tint = EmeraldPrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("PDF", fontSize = 11.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, extractedText)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Share Scanned Text"))
                                },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.Share, null, modifier = Modifier.size(14.dp), tint = EmeraldPrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Share", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
