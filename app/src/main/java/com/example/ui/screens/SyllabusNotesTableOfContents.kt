package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.SubjectNote
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    val coroutineScope = rememberCoroutineScope()

    val subjectName = activeSubject?.name ?: "Syllabus Notes"

    val isSatCourse = subjectName.contains("SAT", ignoreCase = true) ||
            subjectName.contains("Aptitude", ignoreCase = true)

    val isFreshmanCourse = activeSubject?.packageId == "freshman_natural" ||
            activeSubject?.packageId == "freshman_social" ||
            activeSubject?.id?.startsWith("freshman_") == true ||
            subjectName.contains("Freshman", ignoreCase = true)

    LaunchedEffect(Unit) {
        viewModel.selectedGradeFilter.value = "All"
    }

    val filteredNotes = notesList

    // Generalized Unit & Chronologically Ordered Sections Data Structure
    val groupedUnits = remember(filteredNotes, notesList, readNotes) {
        val unitMap = mutableMapOf<Int, MutableList<SubjectNote>>()
        val unitTitleMap = mutableMapOf<Int, String>()

        for (note in filteredNotes) {
            val uMatch = Regex("""(?:Unit|Chapter)\s*(\d+)""", RegexOption.IGNORE_CASE).find(note.unit)
            val uNumFromUnit = uMatch?.groupValues?.getOrNull(1)?.toIntOrNull()

            val secInTitle = Regex("""(?:Section|Sec\.?|Sections)\s*(\d+)\.(\d+)""", RegexOption.IGNORE_CASE).find(note.title)
                ?: Regex("""\b(\d+)\.(\d+)\b""").find(note.title)
            val secInUnit = Regex("""(?:Section|Sec\.?|Sections)\s*(\d+)\.(\d+)""", RegexOption.IGNORE_CASE).find(note.unit)
                ?: Regex("""\b(\d+)\.(\d+)\b""").find(note.unit)

            val uNumFromSec = secInTitle?.groupValues?.getOrNull(1)?.toIntOrNull()
                ?: secInUnit?.groupValues?.getOrNull(1)?.toIntOrNull()

            val unitNumber = uNumFromUnit ?: uNumFromSec ?: run {
                Regex("""(?:Unit|Chapter)\s*(\d+)""", RegexOption.IGNORE_CASE).find(note.title)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 1
            }

            unitMap.getOrPut(unitNumber) { mutableListOf() }.add(note)

            val currentBest = unitTitleMap[unitNumber]
            val cleanUnit = note.unit.trim()
            if (cleanUnit.isNotBlank() && (currentBest == null || (cleanUnit.contains("-") || cleanUnit.contains(":")))) {
                unitTitleMap[unitNumber] = cleanUnit
            }
        }

        unitMap.entries.sortedBy { it.key }.map { (uNum, notesInUnit) ->
            val rawTitle = unitTitleMap[uNum] ?: "Unit $uNum"
            val displayUnitTitle = if (rawTitle.contains(Regex("""Unit\s*$uNum\s*[-:]?\s*""", RegexOption.IGNORE_CASE))) {
                rawTitle.replace(Regex("""Unit\s*$uNum\s*[-:]?\s*""", RegexOption.IGNORE_CASE), "").trim()
            } else {
                rawTitle
            }.ifBlank { "Unit $uNum" }

            val sections = notesInUnit.map { note ->
                val rawIndexInMaster = notesList.indexOf(note).coerceAtLeast(0)
                val isRead = readNotes.contains(note.id)

                val secMatch = Regex("""(?:Section|Sec\.?|Sections)\s*(\d+(?:\.\d+)?)""", RegexOption.IGNORE_CASE).find(note.title)
                    ?: Regex("""\b(\d+\.\d+)\b""").find(note.title)
                    ?: Regex("""(?:Section|Sec\.?|Sections)\s*(\d+(?:\.\d+)?)""", RegexOption.IGNORE_CASE).find(note.unit)
                    ?: Regex("""\b(\d+\.\d+)\b""").find(note.unit)

                val secNumVal = secMatch?.groupValues?.getOrNull(1)?.toDoubleOrNull()
                    ?: (uNum.toDouble() + (rawIndexInMaster % 100) * 0.01)

                val secTag = secMatch?.groupValues?.getOrNull(1) ?: "${uNum}.${rawIndexInMaster + 1}"

                var cleanTitle = note.title
                if (cleanTitle.isBlank() || cleanTitle.equals(note.unit, ignoreCase = true)) {
                    cleanTitle = "Lesson $secTag"
                }

                TocSectionItem(
                    sectionNumber = secNumVal,
                    sectionTag = secTag,
                    displayTitle = cleanTitle,
                    note = note,
                    rawIndexInMaster = rawIndexInMaster,
                    isRead = isRead
                )
            }.sortedBy { it.sectionNumber }

            TocUnitItem(
                unitNumber = uNum,
                unitHeaderTitle = if (displayUnitTitle.startsWith("Unit", ignoreCase = true) || displayUnitTitle.startsWith("Chapter", ignoreCase = true)) displayUnitTitle else (if (isFreshmanCourse) "Chapter $uNum: $displayUnitTitle" else "Unit $uNum: $displayUnitTitle"),
                sections = sections
            )
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
                            text = "${groupedUnits.size} Chapters • ${notesList.size} Sections",
                            style = MaterialTheme.typography.bodySmall,
                            color = EmeraldPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp)
            ) {
                // "Continue where you left off" Card
                val hasRecentStudy = lastStudiedSubjectId == activeSubject?.id && !lastStudiedUnit.isNullOrBlank()
                if (hasRecentStudy) {
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

                if (groupedUnits.isEmpty()) {
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
                                    text = "Try selecting another grade or clearing the search query.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else {
                    items(groupedUnits, key = { it.unitNumber }) { unit ->
                        val completedSectionsCount = unit.sections.count { it.isRead }
                        val isAllRead = unit.sections.isNotEmpty() && completedSectionsCount == unit.sections.size
                        val firstNoteInUnit = unit.sections.firstOrNull()
                        val firstNoteIndex = firstNoteInUnit?.rawIndexInMaster ?: 0

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
                                    if (firstNoteInUnit != null) {
                                        viewModel.recordNoteStudied(firstNoteInUnit.note)
                                    }
                                    viewModel.openNoteUnit(firstNoteIndex)
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
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isAllRead) EmeraldPrimary.copy(alpha = 0.2f)
                                            else (if (isDark) Color(0xFF1E293B) else Color(0xFFEEF2F6))
                                        )
                                        .border(
                                            1.dp,
                                            if (isAllRead) EmeraldPrimary else Color.Transparent,
                                            RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isAllRead) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = EmeraldPrimary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    } else {
                                        Text(
                                            text = "${unit.unitNumber}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
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
                                        Text(
                                            text = (if (isFreshmanCourse) "CHAPTER ${unit.unitNumber}" else "UNIT ${unit.unitNumber}").uppercase(),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Black,
                                                fontSize = 10.sp,
                                                letterSpacing = 1.sp
                                            ),
                                            color = EmeraldPrimary
                                        )
                                        Text(
                                            text = "• ${unit.sections.size} ${if (unit.sections.size == 1) "Section" else "Sections"}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.Gray
                                        )
                                        if (completedSectionsCount > 0) {
                                            Text(
                                                text = "($completedSectionsCount/${unit.sections.size})",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = EmeraldPrimary
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = unit.unitHeaderTitle,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        ),
                                        color = if (isDark) Color.White else IndigoSecondary,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Open Chapter",
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

data class TocSectionItem(
    val sectionNumber: Double,
    val sectionTag: String,
    val displayTitle: String,
    val note: com.example.data.SubjectNote,
    val rawIndexInMaster: Int,
    val isRead: Boolean
)

data class TocUnitItem(
    val unitNumber: Int,
    val unitHeaderTitle: String,
    val sections: List<TocSectionItem>
)
