package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdConfig
import com.example.ads.TinatBannerAd
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import com.example.ui.tools.ai.LearningContext
import com.example.ui.tools.ai.StudentContext
import kotlinx.coroutines.launch
import java.util.Locale

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "user", "tamhero"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun AskTamheroView(
    viewModel: StudyViewModel,
    subjectName: String,
    currentTopic: String = ""
) {
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val isDark by viewModel.isDarkTheme.collectAsState()
    val listState = rememberLazyListState()

    val learningContext by viewModel.activeLearningContext.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()

    // Dynamically available high-yield courses strictly from active enrolled subjects
    val availableCourses = remember(subjectsList) {
        subjectsList.map { it.name.trim() }.filter { it.isNotBlank() }.distinct()
    }

    // Active course: derived dynamically if set, otherwise blank (no unsolicited auto-selection)
    val activeCourse = remember(learningContext?.courseName, subjectName) {
        val ctxCourse = learningContext?.courseName?.trim()
        when {
            !ctxCourse.isNullOrBlank() -> ctxCourse
            subjectName.isNotBlank() -> subjectName
            else -> ""
        }
    }

    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var saveStatusMsg by remember { mutableStateOf<String?>(null) }
    var dismissedContextSnippet by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "tamhero",
                text = "Selam! I am **Ask Tamhero**, your intelligent offline study companion. I can explain formulas, unpack complex theories, summarize your lesson notes, solve practice problems step-by-step, and create flashcards."
            )
        )
    }

    // Auto-trigger if opened with a context prompt
    val initialPrompt by viewModel.toolsContextPrompt.collectAsState()
    LaunchedEffect(initialPrompt) {
        if (!initialPrompt.isNullOrBlank()) {
            val p = initialPrompt!!
            viewModel.toolsContextPrompt.value = null
            messages.add(ChatMessage(sender = "user", text = p))
            isLoading = true
            val effectiveContext = (learningContext?.copy(courseName = activeCourse)
                ?: LearningContext(courseName = activeCourse, topicName = currentTopic)).toStudentContext()
            val reply = viewModel.aiProvider.ask(p, effectiveContext)
            messages.add(ChatMessage(sender = "tamhero", text = reply))
            isLoading = false
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Smart contextual suggestions based on current content type and active course
    val suggestedChips = remember(learningContext, activeCourse) {
        val lCtx = learningContext
        val hasSelectedText = !lCtx?.selectedText.isNullOrBlank()
        val courseLower = activeCourse.lowercase(Locale.ROOT)

        when {
            // Scanned Document
            lCtx?.contentType == "scanned_doc" -> listOf(
                "Explain this scanned page",
                "Summarize key takeaways",
                "Generate 4 questions from page",
                "Create study flashcards"
            )
            // Selected text has highest priority
            hasSelectedText -> listOf(
                "Explain this selection",
                "Give me an example",
                "Make this easier",
                "What should I remember for exam?"
            )
            // In Practice Questions
            lCtx?.contentType == "practice" -> listOf(
                "Give me a hint",
                "Explain this question step by step",
                "Why is option ${lCtx.correctAnswer.ifBlank { "correct" }} right?",
                "Generate similar practice question"
            )
            // On Flashcards
            lCtx?.contentType == "flashcard" -> listOf(
                "Explain this flashcard",
                "Give me a memory trick",
                "Give me a real-world example",
                "Test me on this concept"
            )
            // Geography & Map Reading
            courseLower.contains("geo") -> listOf(
                "Explain this concept",
                "Give me an example",
                "Summarize this lesson",
                "Create MCQs"
            )
            // Mathematics
            courseLower.contains("math") -> listOf(
                "Explain this problem",
                "Solve step by step",
                "Give me an example",
                "Create practice questions"
            )
            // Physics
            courseLower.contains("phy") -> listOf(
                "Explain the formula",
                "Explain the concept",
                "Give me an example",
                "Create MCQs"
            )
            // History
            courseLower.contains("hist") -> listOf(
                "Explain this event",
                "Explain the causes",
                "Explain the effects",
                "Create flashcards"
            )
            // Economics
            courseLower.contains("econ") -> listOf(
                "Explain this concept",
                "Give me an example",
                "Summarize this lesson",
                "Create MCQs"
            )
            // Chemistry
            courseLower.contains("chem") -> listOf(
                "Explain atomic trend",
                "Explain the formula",
                "Give me an example",
                "Create practice questions"
            )
            // Biology
            courseLower.contains("bio") -> listOf(
                "Explain this process",
                "Explain the function",
                "Give me an example",
                "Create flashcards"
            )
            // English
            courseLower.contains("eng") -> listOf(
                "Explain grammar rule",
                "Give me an example sentence",
                "Passive vs Active rule",
                "High-yield exam tip"
            )
            else -> listOf(
                "Explain this concept",
                "Give me an example",
                "Summarize this lesson",
                "Create MCQs"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        // 1. Dynamic Horizontally Scrollable Course Selector
        Surface(
            color = if (isDark) CardBgDark else Color(0xFFF8FAFC),
            border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    val activeCourseLabel = if (activeCourse.isNotBlank()) "Active Course: $activeCourse" else "AI Academic Assistant • Select a Course or Ask Anything"
                    Text(
                        text = activeCourseLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) EmeraldLight else EmeraldDark
                    )
                    if (learningContext?.topicName?.isNotBlank() == true) {
                        Text(
                            text = " • ${learningContext!!.topicName}",
                            fontSize = 11.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (isDark) TextMuted else Color(0xFF64748B),
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }

                // Course selector pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    availableCourses.forEach { course ->
                        val isSelected = course.equals(activeCourse, ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) EmeraldPrimary else if (isDark) Color(0xFF1E293B) else Color.White,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) EmeraldPrimary else if (isDark) Color(0xFF334155) else Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier
                                .clickable {
                                    if (!isSelected) {
                                        viewModel.updateActiveCourseInContext(course)
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = course,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else if (isDark) TextLight else Color(0xFF0F172A)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Active Excerpt / Content Snippet Badge (if opened with specific note, question, or flashcard)
        val lCtx = learningContext
        val hasSnippet = lCtx != null && (
                lCtx.selectedText.isNotBlank() ||
                        lCtx.question.isNotBlank() ||
                        lCtx.flashcardFront.isNotBlank() ||
                        (lCtx.contentType == "notes" && lCtx.contentText.isNotBlank()) ||
                        (lCtx.contentType == "scanned_doc" && lCtx.contentText.isNotBlank())
                )

        if (hasSnippet && !dismissedContextSnippet) {
            val badgeTitle = when {
                lCtx?.selectedText?.isNotBlank() == true -> "Selected Excerpt"
                lCtx?.contentType == "scanned_doc" -> "Active Scanned Document: ${lCtx.topicName.ifBlank { "Page" }}"
                lCtx?.contentType == "practice" -> "Active Practice Question"
                lCtx?.contentType == "flashcard" -> "Active Flashcard"
                lCtx?.contentType == "notes" -> "Active Lesson Note: ${lCtx.topicName}"
                else -> "Active Topic Context"
            }
            val snippetText = when {
                lCtx?.selectedText?.isNotBlank() == true -> lCtx.selectedText
                lCtx?.contentType == "scanned_doc" -> lCtx.contentText.take(130) + "..."
                lCtx?.contentType == "practice" -> lCtx.question
                lCtx?.contentType == "flashcard" -> "${lCtx.flashcardFront} ➔ ${lCtx.flashcardBack}"
                lCtx?.contentType == "notes" -> lCtx.contentText.take(130) + "..."
                else -> ""
            }

            Surface(
                color = if (isDark) Color(0xFF0F172A) else Color(0xFFF0FDF4),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, if (isDark) Color(0xFF1E293B) else Color(0xFFBBF7D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = badgeTitle,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) EmeraldLight else EmeraldDark
                        )
                        if (snippetText.isNotBlank()) {
                            Text(
                                text = snippetText,
                                fontSize = 11.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = if (isDark) TextMuted else Color(0xFF334155),
                                lineHeight = 14.sp
                            )
                        }
                    }
                    IconButton(
                        onClick = { dismissedContextSnippet = true },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss snippet",
                            tint = TextMuted,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }

        // Status banner when user saves note or copies text
        AnimatedVisibility(visible = saveStatusMsg != null) {
            Surface(
                color = EmeraldPrimary,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = saveStatusMsg ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // 3. Chat Message Stream
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                val isUser = msg.sender == "user"
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                ) {
                    Surface(
                        color = if (isUser) {
                            EmeraldPrimary
                        } else {
                            if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9)
                        },
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isUser) 16.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 16.dp
                        ),
                        modifier = Modifier.widthIn(max = 320.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            TamheroMarkdownView(
                                text = msg.text,
                                isDark = isDark,
                                isUser = isUser
                            )

                            if (!isUser && msg.text.length > 50) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    FilledTonalButton(
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(msg.text))
                                            saveStatusMsg = "Copied explanation to clipboard!"
                                            coroutineScope.launch {
                                                kotlinx.coroutines.delay(2500)
                                                saveStatusMsg = null
                                            }
                                        },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Copy", fontSize = 11.sp)
                                    }

                                    FilledTonalButton(
                                        onClick = {
                                            viewModel.saveGeneratedNoteToDatabase(
                                                title = "$activeCourse: High-Yield Note",
                                                content = msg.text,
                                                subjectId = activeCourse.lowercase(Locale.ROOT)
                                            )
                                            saveStatusMsg = "Saved note to your study materials!"
                                            coroutineScope.launch {
                                                kotlinx.coroutines.delay(2500)
                                                saveStatusMsg = null
                                            }
                                        },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Icon(Icons.Default.BookmarkBorder, null, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Save Note", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = EmeraldPrimary,
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tamhero is thinking...",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                }
            }
        }

        // 4. Quick Smart Suggestion Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestedChips.forEach { chipText ->
                SuggestionChip(
                    onClick = {
                        messages.add(ChatMessage(sender = "user", text = chipText))
                        isLoading = true
                        coroutineScope.launch {
                            listState.animateScrollToItem(messages.size - 1)
                            val effectiveContext = (learningContext?.copy(courseName = activeCourse)
                                ?: LearningContext(courseName = activeCourse, topicName = currentTopic)).toStudentContext()
                            val reply = viewModel.aiProvider.ask(chipText, effectiveContext)
                            messages.add(ChatMessage(sender = "tamhero", text = reply))
                            isLoading = false
                            listState.animateScrollToItem(messages.size - 1)
                        }
                    },
                    label = { Text(chipText, fontSize = 12.sp) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9),
                        labelColor = if (isDark) TextLight else Color(0xFF0F172A)
                    ),
                    border = null
                )
            }
        }

        // 5. Input Bar
        Surface(
            color = if (isDark) CardBgDark else Color.White,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            if (activeCourse.isNotBlank()) "Ask about $activeCourse, formulas, notes..." else "Ask any academic or exam question...",
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ask_tamhero_input"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        val prompt = inputText.trim()
                        if (prompt.isNotBlank() && !isLoading) {
                            messages.add(ChatMessage(sender = "user", text = prompt))
                            inputText = ""
                            isLoading = true
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                                val effectiveContext = (learningContext?.copy(courseName = activeCourse)
                                    ?: LearningContext(courseName = activeCourse, topicName = currentTopic)).toStudentContext()
                                val reply = viewModel.aiProvider.ask(prompt, effectiveContext)
                                messages.add(ChatMessage(sender = "tamhero", text = reply))
                                isLoading = false
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(EmeraldPrimary)
                        .testTag("send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // 6. Bottom AdMob Anchored Banner Area (Dedicated reserved layout space, hidden during active keyboard typing)
        val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current)
        val isKeyboardOpen = imeBottom > 0
        if (!isKeyboardOpen && AdConfig.ADS_ENABLED && AdConfig.BANNER_ADS_ENABLED) {
            Surface(
                color = if (isDark) CardBgDark else Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TinatBannerAd(applyInsets = false)
                }
            }
        }
    }
}
