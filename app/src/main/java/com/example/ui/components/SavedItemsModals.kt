package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.ExamQuestion
import com.example.data.Flashcard
import com.example.data.SubjectNote
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.theme.*

@Composable
fun SavedNotesDialog(
    viewModel: StudyViewModel,
    onDismiss: () -> Unit,
    onSelectNote: (SubjectNote) -> Unit
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val savedIds by viewModel.savedNotesSet.collectAsState()
    val allNotes by viewModel.allDatabaseNotes.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()

    val savedNotes = remember(savedIds, allNotes) {
        allNotes.filter { savedIds.contains(it.id) }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .testTag("saved_notes_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = if (isDarkTheme) Color(0xFF0F172A) else Color.White,
            tonalElevation = 6.dp,
            border = BorderStroke(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.3f) else Color(0xFFE2E8F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(GoldAccent.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Bookmark, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = TranslationManager.get("saved_notes_title", currentLang),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                            Text(
                                text = "${savedNotes.size} ${TranslationManager.get("saved_items_count", currentLang)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isDarkTheme) TextMuted else Color.Gray
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = if (isDarkTheme) Color.White else IndigoSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (savedNotes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = TranslationManager.get("no_saved_notes", currentLang),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkTheme) TextMuted else Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(savedNotes) { note ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelectNote(note)
                                        onDismiss()
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFF8FAFC)
                                ),
                                border = BorderStroke(1.dp, if (isDarkTheme) Color.White.copy(alpha = 0.08f) else Color(0xFFE2E8F0))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Surface(
                                            color = EmeraldPrimary.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = note.gradeLevel,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = note.unit,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (isDarkTheme) Color.White else IndigoSecondary
                                        )
                                        Text(
                                            text = note.content.take(75) + "...",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (isDarkTheme) TextMuted else Color.Gray,
                                            maxLines = 2
                                        )
                                    }

                                    IconButton(onClick = { viewModel.toggleSaveNote(note.id) }) {
                                        Icon(Icons.Default.Bookmark, contentDescription = "Unsave", tint = GoldAccent)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedFlashcardsDialog(
    viewModel: StudyViewModel,
    onDismiss: () -> Unit
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val savedIds by viewModel.savedFlashcardsSet.collectAsState()
    val allCards by viewModel.allDatabaseFlashcards.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()

    val savedCards = remember(savedIds, allCards) {
        allCards.filter { savedIds.contains(it.id) }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .testTag("saved_flashcards_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = if (isDarkTheme) Color(0xFF0F172A) else Color.White,
            tonalElevation = 6.dp,
            border = BorderStroke(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.3f) else Color(0xFFE2E8F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(GoldAccent.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = TranslationManager.get("saved_flashcards_title", currentLang),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                            Text(
                                text = "${savedCards.size} ${TranslationManager.get("saved_items_count", currentLang)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isDarkTheme) TextMuted else Color.Gray
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = if (isDarkTheme) Color.White else IndigoSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (savedCards.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.StarBorder, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = TranslationManager.get("no_saved_flashcards", currentLang),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkTheme) TextMuted else Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(savedCards) { card ->
                            var isRevealed by remember { mutableStateOf(false) }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isRevealed = !isRevealed },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFF8FAFC)
                                ),
                                border = BorderStroke(1.dp, if (isRevealed) EmeraldPrimary.copy(alpha = 0.5f) else if (isDarkTheme) Color.White.copy(alpha = 0.08f) else Color(0xFFE2E8F0))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            color = EmeraldPrimary.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = if (isRevealed) "ANSWER" else "QUESTION",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }

                                        IconButton(
                                            onClick = { viewModel.toggleSaveFlashcard(card.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.Star, contentDescription = "Unfavorite", tint = GoldAccent, modifier = Modifier.size(18.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = if (isRevealed) card.back else card.front,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = if (isRevealed) FontWeight.Normal else FontWeight.SemiBold),
                                        color = if (isDarkTheme) Color.White else IndigoSecondary
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = if (isRevealed) "Tap to see question" else "Tap to flip and see answer",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = if (isDarkTheme) HolographicAqua else EmeraldPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedQuestionsDialog(
    viewModel: StudyViewModel,
    onDismiss: () -> Unit
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val savedIds by viewModel.savedQuestionsSet.collectAsState()
    val allQuestions by viewModel.allDatabaseQuestions.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()

    val savedQuestions = remember(savedIds, allQuestions) {
        allQuestions.filter { savedIds.contains(it.id) }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .testTag("saved_questions_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = if (isDarkTheme) Color(0xFF0F172A) else Color.White,
            tonalElevation = 6.dp,
            border = BorderStroke(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.3f) else Color(0xFFE2E8F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(GoldAccent.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Bookmark, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = TranslationManager.get("saved_questions_title", currentLang),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                            Text(
                                text = "${savedQuestions.size} ${TranslationManager.get("saved_items_count", currentLang)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isDarkTheme) TextMuted else Color.Gray
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = if (isDarkTheme) Color.White else IndigoSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (savedQuestions.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = TranslationManager.get("no_saved_questions", currentLang),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkTheme) TextMuted else Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(savedQuestions) { q ->
                            var showAnswer by remember { mutableStateOf(false) }

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFF8FAFC)
                                ),
                                border = BorderStroke(1.dp, if (isDarkTheme) Color.White.copy(alpha = 0.08f) else Color(0xFFE2E8F0))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            color = EmeraldPrimary.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = q.subjectId.uppercase(),
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }

                                        IconButton(
                                            onClick = { viewModel.toggleSaveQuestion(q.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Default.Bookmark, contentDescription = "Unsave", tint = GoldAccent, modifier = Modifier.size(18.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = q.questionText,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = if (isDarkTheme) Color.White else IndigoSecondary
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    val options = listOf("A" to q.optionA, "B" to q.optionB, "C" to q.optionC, "D" to q.optionD)
                                    options.forEach { (label, optText) ->
                                        val isCorrect = showAnswer && q.correctOption.equals(label, ignoreCase = true)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 2.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(if (isCorrect) EmeraldPrimary.copy(alpha = 0.15f) else Color.Transparent)
                                                .padding(horizontal = 6.dp, vertical = 3.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "$label. $optText",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (isCorrect) EmeraldPrimary else if (isDarkTheme) TextMuted else IndigoSecondary
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Button(
                                        onClick = { showAnswer = !showAnswer },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (showAnswer) EmeraldPrimary else (if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0))
                                        )
                                    ) {
                                        Text(
                                            text = if (showAnswer) "Hide Explanation" else "Show Correct Answer & Explanation",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (showAnswer) Color.White else (if (isDarkTheme) Color.White else IndigoSecondary)
                                        )
                                    }

                                    if (showAnswer && q.explanation.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Surface(
                                            color = if (isDarkTheme) Color(0xFF0F172A) else Color(0xFFEEF2FF),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "💡 ${q.explanation}",
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                                color = if (isDarkTheme) HolographicAqua else IndigoSecondary,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedMaterialPickerModal(
    subjectName: String?,
    savedNotesCount: Int,
    savedQuestionsCount: Int,
    savedFlashcardsCount: Int,
    currentLang: String,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit,
    onSelectNotes: () -> Unit,
    onSelectExams: () -> Unit,
    onSelectFlashcards: () -> Unit,
    savedTextbookBookmarksCount: Int = 0,
    onSelectTextbook: (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .testTag("saved_materials_picker_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = if (isDarkTheme) Color(0xFF0F172A) else Color.White,
            tonalElevation = 6.dp,
            border = BorderStroke(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else Color(0xFFE2E8F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(GoldAccent.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = TranslationManager.get("prompt_saved_materials_title", currentLang),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                            Text(
                                text = if (subjectName != null) {
                                    subjectName.uppercase()
                                } else {
                                    TranslationManager.get("prompt_saved_materials_desc", currentLang)
                                },
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isDarkTheme) HolographicAqua else EmeraldDark,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = if (isDarkTheme) Color.White else IndigoSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = TranslationManager.get("prompt_saved_materials_desc", currentLang),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDarkTheme) TextMuted else Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 1. Syllabus Notes Option
                SavedCategoryOptionCard(
                    icon = Icons.Default.MenuBook,
                    iconTint = EmeraldPrimary,
                    iconBg = EmeraldPrimary.copy(alpha = 0.15f),
                    title = TranslationManager.get("saved_notes_label", currentLang),
                    description = "Bookmarked units, summaries and formulas",
                    count = savedNotesCount,
                    isDarkTheme = isDarkTheme,
                    onClick = onSelectNotes,
                    testTag = "picker_saved_notes"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 2. Exams & Practice Option
                SavedCategoryOptionCard(
                    icon = Icons.Default.Timer,
                    iconTint = Color(0xFF3B82F6),
                    iconBg = Color(0xFF3B82F6).copy(alpha = 0.15f),
                    title = TranslationManager.get("saved_exams_label", currentLang),
                    description = "Bookmarked exam questions & explanations",
                    count = savedQuestionsCount,
                    isDarkTheme = isDarkTheme,
                    onClick = onSelectExams,
                    testTag = "picker_saved_exams"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 3. Flashcards Option
                SavedCategoryOptionCard(
                    icon = Icons.Default.Layers,
                    iconTint = GoldAccent,
                    iconBg = GoldAccent.copy(alpha = 0.15f),
                    title = TranslationManager.get("saved_flashcards_label", currentLang),
                    description = "Starred flashcards for quick active recall",
                    count = savedFlashcardsCount,
                    isDarkTheme = isDarkTheme,
                    onClick = onSelectFlashcards,
                    testTag = "picker_saved_flashcards"
                )

                // 4. Official Textbook Bookmarks Option (If available)
                if (onSelectTextbook != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    SavedCategoryOptionCard(
                        icon = Icons.Default.Bookmark,
                        iconTint = EmeraldPrimary,
                        iconBg = EmeraldPrimary.copy(alpha = 0.15f),
                        title = "Official Textbook Pages",
                        description = "Saved pages & chapters from official curriculum",
                        count = savedTextbookBookmarksCount,
                        isDarkTheme = isDarkTheme,
                        onClick = onSelectTextbook,
                        testTag = "picker_saved_textbooks"
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isDarkTheme) Color.White else IndigoSecondary
                    ),
                    border = BorderStroke(1.dp, if (isDarkTheme) Color.Gray.copy(alpha = 0.5f) else Color.LightGray)
                ) {
                    Text(
                        text = TranslationManager.get("btn_close_panel", currentLang),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun SavedCategoryOptionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    iconBg: Color,
    title: String,
    description: String,
    count: Int,
    isDarkTheme: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFF8FAFC)
        ),
        border = BorderStroke(
            1.dp,
            if (isDarkTheme) Color.White.copy(alpha = 0.08f) else Color(0xFFE2E8F0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isDarkTheme) Color.White else IndigoSecondary
                    )
                    Surface(
                        color = if (count > 0) EmeraldPrimary.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$count",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                            color = if (count > 0) EmeraldPrimary else Color.Gray,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDarkTheme) TextMuted else Color.Gray,
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = if (isDarkTheme) Color.LightGray else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
