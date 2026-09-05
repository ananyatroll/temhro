package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.theme.*

@Composable
fun SyllabusNotesTableOfContents(
    viewModel: StudyViewModel,
    onDismiss: () -> Unit
) {
    val activeSubject by viewModel.activeSubject.collectAsState()
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val selectedGrade by viewModel.selectedGradeFilter.collectAsState()
    val notesList by viewModel.activeNotes.collectAsState()
    val readNotes by viewModel.readNotesSet.collectAsState()

    val lastStudiedUnit by viewModel.lastStudiedUnit.collectAsState()
    val lastStudiedTopic by viewModel.lastStudiedTopic.collectAsState()
    val lastStudiedSubjectId by viewModel.lastStudiedSubjectId.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    val subjectName = activeSubject?.name ?: "Syllabus Notes"

    val isSatCourse = subjectName.contains("SAT", ignoreCase = true) ||
            subjectName.contains("Aptitude", ignoreCase = true)

    val isFreshmanCourse = activeSubject?.packageId == "freshman" ||
            activeSubject?.id?.startsWith("freshman") == true ||
            subjectName.contains("Freshman", ignoreCase = true)

    val gradeOptions = when {
        isSatCourse -> emptyList()
        isFreshmanCourse -> listOf("All", "Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4")
        else -> listOf("All", "Grade 9", "Grade 10", "Grade 11", "Grade 12")
    }

    val filteredNotes = remember(notesList, searchQuery) {
        if (searchQuery.isBlank()) {
            notesList
        } else {
            val q = searchQuery.trim().lowercase()
            notesList.filter { note ->
                note.unit.lowercase().contains(q) ||
                note.title.lowercase().contains(q) ||
                note.content.lowercase().contains(q)
            }
        }
    }

    Surface(
        color = if (isDark) ReaderBgDark else Color(0xFFF8FAFC),
        modifier = Modifier
            .fillMaxSize()
            .testTag("syllabus_notes_toc_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Header Top Bar
            Surface(
                color = if (isDark) CardBgDark else Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF1E293B) else Color(0xFFEEF2F6))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDark) Color.White else IndigoSecondary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = subjectName,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = if (isDark) Color.White else IndigoSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Table of Contents • ${notesList.size} Units & Lessons",
                            style = MaterialTheme.typography.bodySmall,
                            color = EmeraldPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Open Ask Tamhero context shortcut
                    IconButton(
                        onClick = {
                            viewModel.openStudentTools(
                                tab = "ask",
                                prompt = "I'm studying syllabus notes for $subjectName. Please give me an overview of the key units and exam focus areas."
                            )
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(EmeraldPrimary.copy(alpha = 0.15f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = "Ask Tamhero",
                            tint = EmeraldPrimary
                        )
                    }
                }
            }

            // Grade Level Selector Row (Grade 9, 10, 11, 12)
            if (gradeOptions.isNotEmpty()) {
                Surface(
                    color = if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        gradeOptions.forEach { grade ->
                            val isSelected = selectedGrade.equals(grade, ignoreCase = true)
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) EmeraldPrimary else (if (isDark) CardBgDark else Color.White),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) EmeraldPrimary else (if (isDark) Color.White.copy(alpha = 0.15f) else Color(0xFFCBD5E1))
                                ),
                                modifier = Modifier
                                    .clickable {
                                        viewModel.selectedGradeFilter.value = grade
                                    }
                            ) {
                                Text(
                                    text = grade,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 13.sp
                                    ),
                                    color = if (isSelected) Color.White else (if (isDark) Color.White else IndigoSecondary),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Search Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Search units, lessons, topics...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear search", tint = Color.Gray)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = if (isDark) CardBgDark else Color.White,
                        unfocusedContainerColor = if (isDark) CardBgDark else Color.White,
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color(0xFFE2E8F0),
                        focusedTextColor = if (isDark) Color.White else Color.Black,
                        unfocusedTextColor = if (isDark) Color.White else Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 4.dp, bottom = 40.dp)
            ) {
                // "Continue where you left off" Card
                val hasRecentStudy = lastStudiedSubjectId == activeSubject?.id && !lastStudiedUnit.isNullOrBlank()
                if (hasRecentStudy && searchQuery.isBlank()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDark) Color(0xFF1E293B) else Color(0xFFECFDF5)
                            ),
                            border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    val resumeIndex = notesList.indexOfFirst {
                                        it.unit == lastStudiedUnit || it.title == lastStudiedTopic
                                    }.coerceAtLeast(0)
                                    viewModel.openNoteUnit(resumeIndex)
                                }
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
                                        .clip(CircleShape)
                                        .background(EmeraldPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "CONTINUE WHERE YOU LEFT OFF",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 1.sp,
                                            fontSize = 10.sp
                                        ),
                                        color = EmeraldPrimary
                                    )
                                    Text(
                                        text = lastStudiedUnit ?: "Recent Lesson",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = if (isDark) Color.White else IndigoSecondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    if (!lastStudiedTopic.isNullOrBlank()) {
                                        Text(
                                            text = lastStudiedTopic!!,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Button(
                                    onClick = {
                                        val resumeIndex = notesList.indexOfFirst {
                                            it.unit == lastStudiedUnit || it.title == lastStudiedTopic
                                        }.coerceAtLeast(0)
                                        viewModel.openNoteUnit(resumeIndex)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("Resume", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                if (filteredNotes.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDark) CardBgDark else Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = if (searchQuery.isNotBlank()) "No units match '$searchQuery'"
                                    else "No notes available for $selectedGrade",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isDark) Color.White else IndigoSecondary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Try selecting 'All' grades or clearing the search query.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                                if (selectedGrade != "All") {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    OutlinedButton(
                                        onClick = { viewModel.selectedGradeFilter.value = "All" }
                                    ) {
                                        Text("Show All Grades")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    itemsIndexed(filteredNotes) { idx, note ->
                        val isRead = readNotes.contains(note.id)
                        val rawIndexInMaster = notesList.indexOf(note).coerceAtLeast(0)

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDark) CardBgDark else Color.White
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (isDark) Color.White.copy(alpha = 0.08f) else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    viewModel.recordNoteStudied(note)
                                    viewModel.openNoteUnit(rawIndexInMaster)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Unit index badge
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isRead) EmeraldPrimary.copy(alpha = 0.2f)
                                            else (if (isDark) Color(0xFF1E293B) else Color(0xFFEEF2F6))
                                        )
                                        .border(
                                            1.dp,
                                            if (isRead) EmeraldPrimary else Color.Transparent,
                                            RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isRead) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = EmeraldPrimary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    } else {
                                        Text(
                                            text = "${idx + 1}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = if (isDark) Color.White else IndigoSecondary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            color = EmeraldPrimary.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = note.gradeLevel,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp
                                                ),
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }

                                        if (isRead) {
                                            Text(
                                                text = "Completed",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp
                                                ),
                                                color = EmeraldPrimary
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = note.unit,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        ),
                                        color = if (isDark) Color.White else IndigoSecondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    if (note.title.isNotBlank() && note.title != note.unit) {
                                        Text(
                                            text = note.title,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Read Unit",
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
