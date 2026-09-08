package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdsManager
import com.example.ads.AdsManager.findActivity
import com.example.data.Flashcard
import com.example.data.StudySubject
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun FlashcardScreen(viewModel: StudyViewModel) {
    val progress by viewModel.userProgress.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()
    val expandedSubjectId by viewModel.showFlashcardSubjectDetailId.collectAsState()
    val activeSubject by viewModel.activeSubject.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()

    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    val targetSubjectId = expandedSubjectId ?: activeSubject?.id
    val displaySubjects = if (targetSubjectId != null) {
        subjectsList.filter { it.id == targetSubjectId }
    } else {
        subjectsList
    }

    val context = LocalContext.current
    val bgModifier = Modifier.background(Color.Transparent)
    val showSavedCards by viewModel.showSavedFlashcardsModal.collectAsState()
    val showCelebration by viewModel.showCompletionParticles.collectAsState()
    val savedFlashcardsSet by viewModel.savedFlashcardsSet.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(bgModifier)
    ) {
        if (showCelebration) {
            CompletionParticleEffect()
        }

        if (showSavedCards) {
            SavedFlashcardsDialog(
                viewModel = viewModel,
                onDismiss = { viewModel.showSavedFlashcardsModal.value = false }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 80.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val activity = AdsManager.findActivity(context)
                            if (activity != null) {
                                AdsManager.showInterstitialIfAllowed(activity) {
                                    viewModel.currentTab.value = "home"
                                }
                            } else {
                                viewModel.currentTab.value = "home"
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkTheme) Color.White else IndigoSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = TranslationManager.get("btn_back_to_dashboard", currentLang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isDarkTheme) Color.White else IndigoSecondary
                        )
                    }
                }

                Text(
                    text = TranslationManager.get("flashcards_title", currentLang),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 28.sp,
                        fontSize = 22.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary
                )
                Text(
                    text = TranslationManager.get("flashcards_subtitle", currentLang),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkTheme) TextMuted else Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                if (targetSubjectId != null && subjectsList.isNotEmpty()) {
                    Text(
                        text = "${TranslationManager.get("flashcards_course_prefix", currentLang)}${displaySubjects.firstOrNull()?.name ?: ""}",
                        style = MaterialTheme.typography.titleSmall,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            if (subjectsList.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No flashcards found for current package.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkTheme) TextMuted else Color.Gray
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.enrollInPackage("euee_natural") },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                            ) {
                                Text("Load EUEE Flashcards", color = Color.White)
                            }
                        }
                    }
                }
            } else {
                items(displaySubjects) { subject ->
                    val isExpanded = targetSubjectId == null || targetSubjectId == subject.id
                    SubjectFlashcardRow(
                        subject = subject,
                        isExpanded = isExpanded,
                        onRowClick = {
                            if (targetSubjectId == null) {
                                if (viewModel.checkFreeTrialAccess(subject)) {
                                    viewModel.activeSubject.value = subject
                                    viewModel.showFlashcardSubjectDetailId.value = subject.id
                                    viewModel.currentFlashcardIndex.value = 0
                                    viewModel.isFlashcardFlipped.value = false
                                }
                            }
                        },
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SubjectFlashcardRow(
    subject: StudySubject,
    isExpanded: Boolean,
    onRowClick: () -> Unit,
    viewModel: StudyViewModel
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val flashcards by viewModel.activeFlashcards.collectAsState()
    val cardIndex by viewModel.currentFlashcardIndex.collectAsState()
    val isFlipped by viewModel.isFlashcardFlipped.collectAsState()
    val isLocked = viewModel.isSubjectLocked(subject)
    val savedFlashcardsSet by viewModel.savedFlashcardsSet.collectAsState()

    LaunchedEffect(subject.id, isExpanded) {
        if (isExpanded && viewModel.activeSubject.value?.id != subject.id) {
            viewModel.activeSubject.value = subject
        }
    }

    val containerBg = if (isLocked) {
        if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFFAF9F9)
    } else if (isExpanded) {
        IndigoSecondary
    } else {
        if (isDarkTheme) CardBgDark else Color.White
    }

    val borderCol = if (isLocked) {
        Color(0xFFEF4444).copy(alpha = 0.3f)
    } else if (isExpanded) {
        EmeraldPrimary.copy(alpha = 0.5f)
    } else if (isDarkTheme) {
        Color.White.copy(alpha = 0.12f)
    } else {
        Color(0xFFF1F5F9)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                borderCol,
                ChamferedCardShape
            ),
        shape = ChamferedCardShape,
        colors = CardDefaults.cardColors(
            containerColor = containerBg
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Main clickable Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onRowClick)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isLocked) (if (isDarkTheme) Color(0xFF7F1D1D).copy(alpha = 0.3f) else Color(0xFFFEF2F2))
                                else if (isExpanded) EmeraldPrimary.copy(alpha = 0.20f)
                                else (if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFEEF2FF))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        DuotoneIcon(
                            name = if (isLocked) "lock" else subject.icon,
                            isActive = !isLocked && isExpanded,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = subject.name,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = if (isLocked) Color.Gray
                                    else if (isExpanded) Color.White
                                    else if (isDarkTheme) Color.White
                                    else IndigoSecondary
                        )
                        if (isLocked) {
                            Text(
                                text = TranslationManager.get("badge_premium_locked", currentLang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFEF4444),
                                    fontSize = 10.sp
                                ),
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                if (isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Premium Locked",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    // Chevron icon
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Expand cards",
                        tint = if (isExpanded) EmeraldPrimary else if (isDarkTheme) Color.White else IndigoSecondary,
                        modifier = Modifier
                            .size(28.dp)
                            .graphicsLayer {
                                rotationZ = if (isExpanded) 90f else 0f
                            }
                    )
                }
            }

            // Expanded Interactive Flashcard Arena
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                val selectedGrade by viewModel.selectedGradeFilter.collectAsState()
                val selectedUnit by viewModel.selectedFlashcardUnit.collectAsState()
                val cardMasteredSet by viewModel.cardMasteredSet.collectAsState()
                val cardDifficultSet by viewModel.cardDifficultSet.collectAsState()
                val isSatCourse = subject.name.contains("SAT", ignoreCase = true) || subject.name.contains("Aptitude", ignoreCase = true)

                var smartFilter by remember { mutableStateOf("all") } // "all", "review", "difficult", "mastered"
                var shuffleSeed by remember { mutableStateOf(0) }

                Column(modifier = Modifier.fillMaxWidth()) {
                    val isFreshmanCourse = subject.packageId == "freshman" ||
                                           subject.id.startsWith("freshman") ||
                                           subject.name.contains("Freshman", ignoreCase = true) ||
                                           subject.name.contains("1011", ignoreCase = true) ||
                                           subject.name.contains("1012", ignoreCase = true) ||
                                           subject.name.contains("1111", ignoreCase = true) ||
                                           subject.name.contains("1122", ignoreCase = true) ||
                                           subject.name.contains("1112", ignoreCase = true)

                    val filterOptions = when {
                        isSatCourse -> emptyList()
                        isFreshmanCourse -> listOf("All", "Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4")
                        else -> listOf("All", "Grade 9", "Grade 10", "Grade 11", "Grade 12")
                    }

                    // 1. Grade / Chapter Filter Chips
                    if (filterOptions.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            filterOptions.forEach { grade ->
                                val isSelected = selectedGrade.equals(grade, ignoreCase = true)
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) EmeraldPrimary else CardBgDark,
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) EmeraldPrimary else Color.White.copy(alpha = 0.15f)
                                    ),
                                    modifier = Modifier
                                        .clickable {
                                            viewModel.selectedGradeFilter.value = grade
                                            viewModel.selectedFlashcardUnit.value = "All"
                                            viewModel.currentFlashcardIndex.value = 0
                                            viewModel.isFlashcardFlipped.value = false
                                        }
                                ) {
                                    Text(
                                        text = grade,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 12.sp
                                        ),
                                        maxLines = 1,
                                        color = if (isSelected) IndigoSecondary else Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }

                    // 2. Unit Selector Row (Grade → Unit → Flashcards)
                    val availableUnits = remember(flashcards) {
                        val units = flashcards.mapNotNull { card ->
                            val regex = Regex("""\[(Unit\s+\d+[^,\]]*)[,\]]""", RegexOption.IGNORE_CASE)
                            val match = regex.find(card.front)
                            match?.groupValues?.getOrNull(1)?.trim() ?: run {
                                val uMatch = Regex("""(Unit\s+\d+)""", RegexOption.IGNORE_CASE).find(card.front)
                                uMatch?.groupValues?.getOrNull(1)?.trim()
                            }
                        }.distinct()
                        if (units.isNotEmpty()) listOf("All Units") + units else emptyList()
                    }

                    if (!selectedGrade.equals("All", ignoreCase = true) && availableUnits.size > 1) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            availableUnits.forEach { unitItem ->
                                val isUnitSelected = selectedUnit.equals(unitItem, ignoreCase = true) ||
                                        (selectedUnit == "All" && unitItem == "All Units")
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isUnitSelected) IndigoSecondary else (CardBgDark.copy(alpha = 0.8f)),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isUnitSelected) EmeraldPrimary else Color.White.copy(alpha = 0.12f)
                                    ),
                                    modifier = Modifier.clickable {
                                        viewModel.selectedFlashcardUnit.value = if (unitItem == "All Units") "All" else unitItem
                                        viewModel.currentFlashcardIndex.value = 0
                                        viewModel.isFlashcardFlipped.value = false
                                    }
                                ) {
                                    Text(
                                        text = unitItem,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isUnitSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 11.sp
                                        ),
                                        maxLines = 1,
                                        color = if (isUnitSelected) EmeraldLight else Color.LightGray,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }

                    // 3. Filter Flashcards by Unit and Learning State
                    val filteredByUnit = remember(flashcards, selectedUnit) {
                        if (selectedUnit == "All" || selectedUnit == "All Units") {
                            flashcards
                        } else {
                            val targetUnitClean = selectedUnit.trim().lowercase()
                            flashcards.filter { card ->
                                card.unit.lowercase() == targetUnitClean || card.unit.lowercase() == "all" || card.unit.lowercase() == "all units"
                            }
                        }
                    }

                    val activeDeckCards = remember(filteredByUnit, smartFilter, cardMasteredSet, cardDifficultSet, shuffleSeed) {
                        val filtered = when (smartFilter) {
                            "difficult" -> filteredByUnit.filter { cardDifficultSet.contains(it.id) }
                            "mastered" -> filteredByUnit.filter { cardMasteredSet.contains(it.id) || it.isKnown }
                            "review" -> filteredByUnit.filter { !cardMasteredSet.contains(it.id) && !it.isKnown }
                            else -> filteredByUnit
                        }
                        if (shuffleSeed > 0) filtered.shuffled(java.util.Random(shuffleSeed.toLong())) else filtered
                    }

                    // 4. Smart Mastery Progress & Spaced Repetition Bar
                    val masteredCount = filteredByUnit.count { cardMasteredSet.contains(it.id) || it.isKnown }
                    val difficultCount = filteredByUnit.count { cardDifficultSet.contains(it.id) }
                    val totalDeckSize = filteredByUnit.size

                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "MASTERY: $masteredCount / $totalDeckSize CARDS",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    color = EmeraldPrimary
                                )
                                Text(
                                    text = "$difficultCount marked difficult • ${totalDeckSize - masteredCount} to review",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = Color.Gray
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                // Smart filter mode: All vs Difficult vs Review
                                IconButton(
                                    onClick = {
                                        smartFilter = when (smartFilter) {
                                            "all" -> "difficult"
                                            "difficult" -> "review"
                                            "review" -> "mastered"
                                            else -> "all"
                                        }
                                        viewModel.currentFlashcardIndex.value = 0
                                    },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (smartFilter != "all") EmeraldPrimary.copy(alpha = 0.2f) else Color(0xFF1E293B))
                                ) {
                                    Icon(
                                        imageVector = when (smartFilter) {
                                            "difficult" -> Icons.Default.Flag
                                            "mastered" -> Icons.Default.CheckCircle
                                            "review" -> Icons.Default.Schedule
                                            else -> Icons.Default.FilterList
                                        },
                                        contentDescription = "Filter status",
                                        tint = if (smartFilter != "all") EmeraldLight else Color.LightGray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                // Shuffle Deck Button
                                IconButton(
                                    onClick = {
                                        shuffleSeed += 1
                                        viewModel.currentFlashcardIndex.value = 0
                                        viewModel.isFlashcardFlipped.value = false
                                    },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF1E293B))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Shuffle,
                                        contentDescription = "Shuffle cards",
                                        tint = Color.LightGray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (activeDeckCards.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = if (smartFilter != "all") "No $smartFilter cards found in this unit."
                                    else if (selectedGrade != "All") "No flashcards found for $selectedGrade."
                                    else "No flashcards found.",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (smartFilter != "all") {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextButton(onClick = { smartFilter = "all" }) {
                                        Text("Show All Cards", color = EmeraldPrimary)
                                    }
                                }
                                
                                if (selectedUnit != "All" && selectedUnit != "All Units" && smartFilter == "all") {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { /* viewModel.generateSmartFlashcards(selectedUnit) */ },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Generate Smart Flashcards for ")
                                    }
                                }
                            }
                        }
                    } else {
                        val currentCard = activeDeckCards.getOrNull(cardIndex.coerceIn(0, activeDeckCards.size - 1))
                        if (currentCard != null) {
                            val isCardMastered = cardMasteredSet.contains(currentCard.id) || currentCard.isKnown
                            val isCardDifficult = cardDifficultSet.contains(currentCard.id)
                            var popupMessage by remember { mutableStateOf("") }
                            LaunchedEffect(popupMessage) {
                                if (popupMessage.isNotEmpty()) {
                                    kotlinx.coroutines.delay(1500)
                                    popupMessage = ""
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Deck progress tracker and status badges
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "CARD ${cardIndex.coerceIn(0, activeDeckCards.size - 1) + 1} OF ${activeDeckCards.size}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = EmeraldPrimary
                                    )

                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        if (isCardMastered) {
                                            Surface(
                                                color = EmeraldPrimary.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = "MASTERED",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                                    color = EmeraldPrimary,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        if (isCardDifficult) {
                                            Surface(
                                                color = Color(0xFFEF4444).copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = "DIFFICULT",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                                    color = Color(0xFFF87171),
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Interactive Flippable Card Frame
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    FlippableCard(
                                        card = currentCard,
                                        isFlipped = isFlipped,
                                        onFlipClick = { viewModel.isFlashcardFlipped.value = !isFlipped }
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Impeccable Quick Toggles Row (Difficult / Mastered / Star)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Difficult Pill Chip
                                    Surface(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(40.dp)
                                            .pressBounce(pressedScale = 0.94f)
                                            .clickable {
                                                viewModel.markFlashcardDifficult(currentCard.id)
                                                popupMessage = if (isCardDifficult) "Flag removed" else "Marked as difficult"
                                            },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isCardDifficult) Color(0xFFEF4444).copy(alpha = 0.18f) else Color(0xFF1E293B),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isCardDifficult) Color(0xFFEF4444) else Color.White.copy(alpha = 0.08f)
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isCardDifficult) Icons.Default.Flag else Icons.Default.OutlinedFlag,
                                                contentDescription = "Difficult",
                                                tint = if (isCardDifficult) Color(0xFFF87171) else Color.LightGray,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                 text = "Difficult",
                                                 style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                 color = if (isCardDifficult) Color(0xFFF87171) else Color.LightGray
                                            )
                                        }
                                    }

                                    // Mastered Pill Chip
                                    Surface(
                                        modifier = Modifier
                                            .weight(1.1f)
                                            .height(40.dp)
                                            .pressBounce(pressedScale = 0.94f)
                                            .clickable {
                                                viewModel.markFlashcardMastered(currentCard.id)
                                                if (!isCardMastered) {
                                                    if (cardIndex == activeDeckCards.size - 1) {
                                                        viewModel.triggerCompletionCelebration()
                                                    }
                                                    popupMessage = "Mastered!"
                                                } else {
                                                    popupMessage = "Mastered removed"
                                                }
                                            },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isCardMastered) EmeraldPrimary.copy(alpha = 0.2f) else Color(0xFF1E293B),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isCardMastered) EmeraldPrimary else Color.White.copy(alpha = 0.08f)
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isCardMastered) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                                contentDescription = "Mastered",
                                                tint = if (isCardMastered) EmeraldPrimary else Color.LightGray,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "Mastered",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (isCardMastered) EmeraldPrimary else Color.LightGray
                                            )
                                        }
                                    }

                                    // Star / Save Icon Button
                                    val isCardSaved = savedFlashcardsSet.contains(currentCard.id) || currentCard.isStarred
                                    Surface(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clickable {
                                                viewModel.toggleFlashcardStarred(
                                                    currentCard.id,
                                                    currentCard.isKnown,
                                                    currentCard.isStarred
                                                )
                                                viewModel.toggleSaveFlashcard(currentCard.id)
                                                popupMessage = if (!isCardSaved) "Saved to bookmarks" else "Removed from bookmarks"
                                            },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isCardSaved) GoldAccent.copy(alpha = 0.18f) else Color(0xFF1E293B),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isCardSaved) GoldAccent else Color.White.copy(alpha = 0.08f)
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isCardSaved) Icons.Default.Star else Icons.Default.StarBorder,
                                                contentDescription = "Save card",
                                                tint = if (isCardSaved) GoldAccent else Color.LightGray,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Navigation & Source Control Bar
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF0F172A), RoundedCornerShape(12.dp))
                                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Previous Button
                                    TextButton(
                                        onClick = {
                                            if (cardIndex > 0) {
                                                viewModel.currentFlashcardIndex.value = cardIndex - 1
                                                viewModel.isFlashcardFlipped.value = false
                                            }
                                        },
                                        enabled = cardIndex > 0,
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = if (cardIndex > 0) Color.White else Color(0xFF475569),
                                            disabledContentColor = Color(0xFF475569)
                                        )
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Previous",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Previous", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                    }

                                    // View Source Button (Bridge to Syllabus Notes)
                                    OutlinedButton(
                                        onClick = {
                                            val regex = Regex("""\[(Unit\s+\d+[^,\]]*)[,\]]""", RegexOption.IGNORE_CASE)
                                            val match = regex.find(currentCard.front)
                                            val unitSnippet = match?.groupValues?.getOrNull(1)?.trim() ?: run {
                                                val uMatch = Regex("""(Unit\s+\d+)""", RegexOption.IGNORE_CASE).find(currentCard.front)
                                                uMatch?.groupValues?.getOrNull(1)?.trim() ?: ""
                                            }
                                            viewModel.navigateToSourceNote(
                                                subjectId = subject.id,
                                                grade = currentCard.gradeLevel,
                                                unitSnippet = unitSnippet
                                            )
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f))
                                    ) {
                                        Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp), tint = EmeraldPrimary)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Source", fontSize = 11.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                                    }

                                    // Next Button
                                    TextButton(
                                        onClick = {
                                            if (cardIndex < activeDeckCards.size - 1) {
                                                viewModel.currentFlashcardIndex.value = cardIndex + 1
                                                viewModel.isFlashcardFlipped.value = false
                                            }
                                        },
                                        enabled = cardIndex < activeDeckCards.size - 1,
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = if (cardIndex < activeDeckCards.size - 1) Color.White else Color(0xFF475569),
                                            disabledContentColor = Color(0xFF475569)
                                        )
                                    ) {
                                        Text("Next", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                             Icons.AutoMirrored.Filled.ArrowForward,
                                             contentDescription = "Next",
                                             modifier = Modifier.size(16.dp)
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

@Composable
fun FlippableCard(
    card: Flashcard,
    isFlipped: Boolean,
    onFlipClick: () -> Unit
) {
    // Apple Fluid Interfaces: Interruptible critically damped spring rotation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = 0.82f,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "rotate"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .pressBounce()
            .clickable(onClick = onFlipClick)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 14f * density
            }
            .border(
                width = 1.2.dp,
                color = if (isFlipped) EmeraldPrimary.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131B2E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = if (isFlipped) listOf(Color(0xFF131B2E), Color(0xFF0F172A))
                        else listOf(Color(0xFF1A233A), Color(0xFF111827))
                    )
                )
                .padding(22.dp),
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                // Front Side (Question / Term)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (card.gradeLevel != "General") {
                            Surface(
                                color = EmeraldPrimary.copy(alpha = 0.18f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.35f))
                            ) {
                                Text(
                                    text = card.gradeLevel,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                                    color = EmeraldPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        Surface(
                            color = HolographicAqua.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "QUESTION / TERM",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    fontSize = 9.5.sp
                                ),
                                color = HolographicAqua,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = card.front,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = if (card.front.length > 80) 16.sp else 19.sp,
                                lineHeight = if (card.front.length > 80) 24.sp else 28.sp,
                                letterSpacing = 0.2.sp
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.06f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "Tap to reveal answer",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp, fontWeight = FontWeight.Medium),
                                color = Color.LightGray
                            )
                        }
                    }
                }
            } else {
                // Back Side (Answer & Explanation)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = EmeraldPrimary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "ANSWER & EXPLANATION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    fontSize = 9.5.sp
                                ),
                                color = EmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        if (card.gradeLevel != "General") {
                            Surface(
                                color = EmeraldPrimary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = card.gradeLevel,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                                    color = EmeraldPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = card.back,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = if (card.back.length > 150) 13.5.sp else 15.sp,
                                lineHeight = if (card.back.length > 150) 20.sp else 23.sp,
                                letterSpacing = 0.2.sp
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.06f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Replay,
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "Tap to flip back",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp, fontWeight = FontWeight.Medium),
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}
