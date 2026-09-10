package com.example.ui.screens

import android.net.Uri
import android.widget.VideoView
import android.widget.MediaController
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ads.AdsManager
import com.example.ads.AdsManager.findActivity
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun ContentPlayView(viewModel: StudyViewModel) {
    val context = LocalContext.current
    val activeSub by viewModel.activeSubject.collectAsState()
    val showStudyOptions by viewModel.showStudyOptionsModal.collectAsState()
    val showTextbookReader by viewModel.showTextbookReader.collectAsState()
    val showModeSelection by viewModel.showModeSelectionModal.collectAsState()
    val showNotes by viewModel.showNotesView.collectAsState()
    val showQuestionsPlay by viewModel.showContentPlayView.collectAsState()
    val isLoading by viewModel.contentLoading.collectAsState()

    androidx.activity.compose.BackHandler(enabled = showQuestionsPlay) {
        viewModel.resetPlayState()
    }
    val showNotesTOC by viewModel.showNotesTableOfContents.collectAsState()
    androidx.activity.compose.BackHandler(enabled = showNotesTOC) {
        viewModel.showNotesTableOfContents.value = false
    }
    androidx.activity.compose.BackHandler(enabled = showNotes) {
        viewModel.showNotesView.value = false
        viewModel.resetPlayState()
    }
    androidx.activity.compose.BackHandler(enabled = showTextbookReader) {
        viewModel.showTextbookReader.value = false
    }

    val notes by viewModel.activeNotes.collectAsState()
    val noteIndex by viewModel.activeNoteIndex.collectAsState()

    val questions by viewModel.activeQuestions.collectAsState()
    val currentQuestionIdx by viewModel.currentQuestionIndex.collectAsState()
    val userAnswers by viewModel.userSelectedAnswers.collectAsState()
    val revealedAnswers by viewModel.revealedAnswers.collectAsState()

    val isTimerActive by viewModel.isTimerActive.collectAsState()
    val timerRemaining by viewModel.timerRemainingSeconds.collectAsState()
    val activeExamMode by viewModel.activeExamMode.collectAsState()
    val showResultsModal by viewModel.showScoreResultModal.collectAsState()

    val showFreeTrialPaywall by viewModel.showFreeTrialPaywall.collectAsState()
    val paywallPackageId by viewModel.paywallPackageIdForUpgrade.collectAsState()
    val showPaymentForm by viewModel.showPaymentVerificationScreen.collectAsState()

    if (showFreeTrialPaywall) {
        val currentLang by viewModel.currentLanguage.collectAsState()
        FreeTrialPaywallDialog(
            packageId = paywallPackageId ?: "freshman_natural",
            currentLang = currentLang,
            onDismiss = { viewModel.showFreeTrialPaywall.value = false },
            onUpgradePremium = {
                viewModel.showFreeTrialPaywall.value = false
                viewModel.showPaymentVerificationScreen.value = true
            }
        )
    }

    if (showPaymentForm) {
        val progress by viewModel.userProgress.collectAsState()
        val pkgId = paywallPackageId ?: progress.activePackageId ?: "euee"
        val pkgName = when(pkgId) {
            "euee" -> "EUEE Prep Ultimate"
            "freshman_natural" -> "Natural Science Freshman"
            "freshman_social" -> "Social Science Freshman"
            "aau_uat" -> "AAU UAT Prep Pro"
            "department" -> "University Department Pro"
            "exit_exam" -> "Exit Exam Pro"
            else -> "Pro Study Plan"
        }
        SleekPaymentVerificationScreen(
            packageId = pkgId,
            packageName = pkgName,
            viewModel = viewModel,
            onClose = { viewModel.showPaymentVerificationScreen.value = false }
        )
    }

    if (activeSub == null) return

    val subject = activeSub!!

    // Root Backdrops / Modals / Active Reading overlays
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Loading State Spinner Overlay
        if (isLoading && (showNotes || showQuestionsPlay || showStudyOptions || showModeSelection)) {
            FuturisticLoadingState()
        }

        // 2. Subject Detail/Study Options Modal
        if (showStudyOptions) {
            val activeMode by viewModel.activeMode.collectAsState()
            val currentLang by viewModel.currentLanguage.collectAsState()
            SubjectDetailModal(
                subject = subject,
                mode = activeMode,
                currentLang = currentLang,
                onDismiss = { viewModel.showStudyOptionsModal.value = false },
                onNotesClick = { viewModel.startNotes() },
                onExamsClick = { viewModel.initiateModeSelection() },
                onFlashcardsClick = { viewModel.startFlashcards() },
                onTextbookClick = { viewModel.startTextbook() },
                onSavedMaterialsClick = {
                    viewModel.showStudyOptionsModal.value = false
                    viewModel.showSavedMaterialPickerModal.value = true
                }
            )
        }

        // 3. Practice / Exam Mode Selection Modal
        if (showModeSelection) {
            PracticeExamModeSelectionModal(
                subjectName = subject.name,
                onDismiss = { viewModel.showModeSelectionModal.value = false },
                onPracticeSelect = { viewModel.startSubjectMode("practice") },
                onExamSelect = { viewModel.startSubjectMode("exam") }
            )
        }

        // 3.5 Syllabus Notes Table of Contents / Unit Navigator
        if (showNotesTOC) {
            SyllabusNotesTableOfContents(
                viewModel = viewModel,
                onDismiss = { viewModel.showNotesTableOfContents.value = false }
            )
        }

        // 5. Official Textbook Reader
        if (showTextbookReader) {
            OfficialTextbookScreen(
                subjectName = subject.name,
                onClose = { viewModel.showTextbookReader.value = false },
                viewModel = viewModel
            )
        }

        // 4. Sleek Dark-Themed Notes Reader View
        if (showNotes && !isLoading) {
            val currentNote = notes.getOrNull(noteIndex)
            DarkThemedNotesReader(
                subjectName = subject.name,
                note = currentNote,
                index = noteIndex,
                totalNotes = notes.size,
                viewModel = viewModel,
                onBack = {
                    if (noteIndex > 0) {
                        viewModel.activeNoteIndex.value = noteIndex - 1
                    }
                },
                onNext = {
                    if (noteIndex < notes.size - 1) {
                        viewModel.activeNoteIndex.value = noteIndex + 1
                    }
                },
                onClose = {
                    val activity = AdsManager.findActivity(context)
                    if (activity != null) {
                        AdsManager.showInterstitialIfAllowed(activity) {
                            viewModel.resetPlayState()
                        }
                    } else {
                        viewModel.resetPlayState()
                    }
                },
                onMarkCompleted = {
                    val activity = AdsManager.findActivity(context)
                    viewModel.toggleCompleteSubject(subject.id)
                    if (activity != null) {
                        AdsManager.showInterstitialIfAllowed(activity) {
                            viewModel.resetPlayState()
                        }
                    } else {
                        viewModel.resetPlayState()
                    }
                }
            )
        }

        // 4b. Beautiful Dark-Themed Course Videos Screen
        val showVideos by viewModel.showVideosView.collectAsState()
        if (showVideos && !isLoading) {
            SleekVideosScreen(
                subjectId = subject.id,
                subjectName = subject.name,
                viewModel = viewModel,
                onClose = { viewModel.resetPlayState() }
            )
        }

        // 5. Active Quiz Interactive Arena (Practice or Exam mode)
        if (showQuestionsPlay && !isLoading) {
            val currentQuestion = questions.getOrNull(currentQuestionIdx)
            QuestionsArena(
                subjectName = subject.name,
                question = currentQuestion,
                index = currentQuestionIdx,
                totalQuestions = questions.size,
                mode = activeExamMode ?: "practice",
                timerSeconds = timerRemaining,
                isTimerActive = isTimerActive,
                selectedAnswers = userAnswers,
                revealedAnswers = revealedAnswers,
                viewModel = viewModel,
                onTimerToggle = { viewModel.togglePauseTimer() },
                onSelectOption = { qId, opt -> viewModel.selectAnswer(qId, opt) },
                onRevealAnswer = { qId -> viewModel.revealAnswer(qId) },
                onPrevious = {
                    if (currentQuestionIdx > 0) {
                        viewModel.currentQuestionIndex.value = currentQuestionIdx - 1
                        viewModel.resetQuestionTimer(120)
                    }
                },
                onNext = {
                    if (currentQuestionIdx < questions.size - 1) {
                        viewModel.currentQuestionIndex.value = currentQuestionIdx + 1
                        viewModel.resetQuestionTimer(120)
                    }
                },
                onSubmit = { viewModel.finishExam() },
                onExit = { viewModel.resetPlayState() }
            )
        }

        // 6. Exam Mode Score Calculation Dialog (Strictly NO Interstitials on Results)
        if (showResultsModal) {
            val correctCount by viewModel.correctAnswersCount.collectAsState()
            val totalCount by viewModel.questionsCount.collectAsState()
            val pctScore by viewModel.calculatedScorePercent.collectAsState()

            ScoreResultModal(
                percent = pctScore,
                correct = correctCount,
                total = totalCount,
                viewModel = viewModel,
                onDismiss = {
                    viewModel.showScoreResultModal.value = false
                    viewModel.resetPlayState()
                }
            )
        }

        // 7. Saved Items Modals
        val showSavedPicker by viewModel.showSavedMaterialPickerModal.collectAsState()
        val currentLang by viewModel.currentLanguage.collectAsState()
        val isDarkTheme by viewModel.isDarkTheme.collectAsState()
        val savedNotesSet by viewModel.savedNotesSet.collectAsState()
        val savedQuestionsSet by viewModel.savedQuestionsSet.collectAsState()
        val savedFlashcardsSet by viewModel.savedFlashcardsSet.collectAsState()
        val savedTextbookBookmarks by viewModel.savedTextbookBookmarksSet.collectAsState()
        val savedTextbookCount = savedTextbookBookmarks.size

        if (showSavedPicker) {
            SavedMaterialPickerModal(
                subjectName = subject.name,
                savedNotesCount = savedNotesSet.size,
                savedQuestionsCount = savedQuestionsSet.size,
                savedFlashcardsCount = savedFlashcardsSet.size,
                currentLang = currentLang,
                isDarkTheme = isDarkTheme,
                onDismiss = { viewModel.showSavedMaterialPickerModal.value = false },
                onSelectNotes = {
                    viewModel.showSavedMaterialPickerModal.value = false
                    viewModel.showSavedNotesModal.value = true
                },
                onSelectExams = {
                    viewModel.showSavedMaterialPickerModal.value = false
                    viewModel.showSavedQuestionsModal.value = true
                },
                onSelectFlashcards = {
                    viewModel.showSavedMaterialPickerModal.value = false
                    viewModel.showSavedFlashcardsModal.value = true
                },
                savedTextbookBookmarksCount = savedTextbookCount,
                onSelectTextbook = {
                    viewModel.showSavedMaterialPickerModal.value = false
                    viewModel.startTextbook()
                }
            )
        }

        val showSavedNotes by viewModel.showSavedNotesModal.collectAsState()
        if (showSavedNotes) {
            SavedNotesDialog(
                viewModel = viewModel,
                onDismiss = { viewModel.showSavedNotesModal.value = false },
                onSelectNote = { selectedNote ->
                    viewModel.openSavedNote(selectedNote)
                }
            )
        }

        val showSavedQuestions by viewModel.showSavedQuestionsModal.collectAsState()
        if (showSavedQuestions) {
            SavedQuestionsDialog(
                viewModel = viewModel,
                onDismiss = { viewModel.showSavedQuestionsModal.value = false }
            )
        }

        val showSavedCards by viewModel.showSavedFlashcardsModal.collectAsState()
        if (showSavedCards) {
            SavedFlashcardsDialog(
                viewModel = viewModel,
                onDismiss = { viewModel.showSavedFlashcardsModal.value = false }
            )
        }

        val showCelebration by viewModel.showCompletionParticles.collectAsState()
        if (showCelebration) {
            CompletionParticleEffect()
        }
    }
}

@Composable
fun FuturisticLoadingState() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Skeleton Header
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Skeleton List Items
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = alpha))
            )
        }
    }
}

fun getSubjectDepartmentDetail(subjectId: String): String {
    val cleanId = subjectId.lowercase()
    return when {
        cleanId.contains("biology") -> """
            🎓 DEPARTMENT SPECS: DEPT OF BIOLOGICAL SCIENCES
            • Major Fields: Microbiology, Genetics, Biotechnology, Plant Physiology, and Ecology.
            • Curriculum Requirements: Requires Freshman Biology (4 credits) + General Chemistry (3 credits).
            • College Course Mapping: Maps directly to BIO-101 (Cell Biology) and BIO-202 (Genetics).
            • Career Trajectories: Medical Assistant, Healthcare Researcher, Soil Analyst, and Biotechnology Specialist (Growth: +12% annually).
        """.trimIndent()
        cleanId.contains("chemistry") -> """
            🎓 DEPARTMENT SPECS: DEPT OF CHEMICAL SCIENCES
            • Major Fields: Organic Synthesis, Analytical Assay, Quantum Chemistry, Thermodynamics.
            • Curriculum Requirements: General Chemistry (4 credits) + Calculus I & II.
            • College Course Mapping: Maps to CHEM-101 (Inorganic Foundations) and CHEM-301 (Analytical Lab).
            • Career Trajectories: Industrial Chemist, Pharmacologist, Food Safety Auditor, Environmental Technologist.
        """.trimIndent()
        cleanId.contains("civics") -> """
            🎓 DEPARTMENT SPECS: DEPT OF POLITICAL SCIENCE & CIVICS
            • Major Fields: Constitutional Jurisprudence, Civic Governance, International Treaty Systems.
            • Curriculum Requirements: Moral Philosophy (3 credits) + Constitutional Law (4 credits).
            • College Course Mapping: Maps to POL-101 (Principles of Government) and MCiE-1012 (Moral & Civics).
            • Career Trajectories: Policy Advisor, Legal Counsel, Public Arbitrator, NGO Coordinator.
        """.trimIndent()
        cleanId.contains("anthropology") -> """
            🎓 DEPARTMENT SPECS: DEPT OF ANTHROPOLOGY & SOCIOLOGY
            • Major Fields: Cultural Relativism, Paleoanthropology, Linguistic Evolution, Ethnoarchaeology.
            • Curriculum Requirements: Introduction to Anthropology (3 credits) + Archaeological Foundations.
            • College Course Mapping: Maps to ANTH-1012 (Social Anthropology) and ANTH-203 (East African Peoples).
            • Career Trajectories: Heritage Conservationist, Museum Curator, Intercultural Advisor, Policy Planner.
        """.trimIndent()
        else -> """
            🎓 DEPARTMENT SPECS: COMPREHENSIVE MAJOR MATRIX
            • Academic Fields: Advanced Foundational Theory, Methodology, Integrated Lab Research.
            • Curriculum Requirements: Standard Freshman courses (3 credits) + Senior Seminar project.
            • College Course Mapping: Maps directly to introductory standard college course syllabus modules.
            • Career Trajectories: Graduate Assistant, Professional Specialist, Field Consultant, Domain Educator.
        """.trimIndent()
    }
}

fun getSubjectExitExamDetail(subjectId: String): String {
    val cleanId = subjectId.lowercase()
    return when {
        cleanId.contains("biology") -> """
            🎯 EXIT EXAM BLUEPRINT: NATIONAL GRADUATION BENCHMARK
            • Weighting Index: 15% of national exams (equivalent to 15 distinct multiple-choice questions).
            • Core Competencies: High mastery required in Cellular Structures, Aerobic/Anaerobic ATP pathways, and Mendelian Genetics.
            • Target Syllabus Blueprint: Modules 1-4 of national graduation blueprint specifications.
            • High Rating Guidelines: Solve active flash recall modules daily; prioritize Glycolysis and Krebs structures.
        """.trimIndent()
        cleanId.contains("chemistry") -> """
            🎯 EXIT EXAM BLUEPRINT: NATIONAL GRADUATION BENCHMARK
            • Weighting Index: 12% of national exams (covers stoichiometric equations, pH balancing, and molecular bonds).
            • Core Competencies: Redox reactions calculations, balancing acidic titration curves, and hybridizations.
            • Target Syllabus Blueprint: Inorganic standards set by national evaluation boards.
            • High Rating Guidelines: Rewrite Lewis structures; memorize pH concentration equations.
        """.trimIndent()
        cleanId.contains("civics") -> """
            🎯 EXIT EXAM BLUEPRINT: NATIONAL GRADUATION BENCHMARK
            • Weighting Index: 10% of national exams (focuses on federal structures, rule of law, and civic integrity).
            • Core Competencies: Federalism divisions of power, human rights covenants, and democratic participation duties.
            • Target Syllabus Blueprint: Ethiopian Constitutional studies weighting criteria.
            • High Rating Guidelines: Dissect the division of federal-regional powers; practice past treaties questions.
        """.trimIndent()
        cleanId.contains("anthropology") -> """
            🎯 EXIT EXAM BLUEPRINT: NATIONAL GRADUATION BENCHMARK
            • Weighting Index: 8% of national exams (focuses on human origin biology and classic subfields).
            • Core Competencies: Australopithecus afarensis structural bipedalism traits, ethnocentrism fallacies, and subfields.
            • Target Syllabus Blueprint: Social anthropology graduation appraisal framework.
            • High Rating Guidelines: Memorize Lucy's fossil location (Afar) and bipedal features.
        """.trimIndent()
        else -> """
            🎯 EXIT EXAM BLUEPRINT: NATIONAL GRADUATION BENCHMARK
            • Weighting Index: Standard weight of 10% overall graduation benchmark score.
            • Core Competencies: Basic concepts recall, speed problem-solving, structural analytical comprehension.
            • Target Syllabus Blueprint: High-yield exit appraisal blueprints.
            • High Rating Guidelines: Practice 20 historical questions; maintain active track dashboard grids.
        """.trimIndent()
    }
}

@Composable
fun SubjectDetailModal(
    subject: StudySubject,
    mode: String,
    currentLang: String = "en",
    onDismiss: () -> Unit,
    onNotesClick: () -> Unit,
    onExamsClick: () -> Unit,
    onFlashcardsClick: () -> Unit,
    onTextbookClick: () -> Unit,
    onSavedMaterialsClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.5f), if (mode == "exit_exam") Color(0xFFDC2626).copy(alpha = 0.5f) else EmeraldPrimary.copy(alpha = 0.5f))
                    ),
                    shape = ChamferedCardShape
                ),
            shape = ChamferedCardShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A).copy(alpha = 0.96f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circular duotone icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(HexagonChamferShape)
                        .background(if (mode == "exit_exam") Color(0xFFDC2626).copy(alpha = 0.15f) else EmeraldPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    DuotoneIcon(name = subject.icon, isActive = true, modifier = Modifier.size(28.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                val isUat = subject.packageId == "aau_uat" || mode == "aau_uat"

                // 1. Notes Mode Button (Hidden for AAU UAT)
                if (!isUat) {
                    Button(
                        onClick = onNotesClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .pressBounce()
                            .testTag("study_option_notes"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (mode == "exit_exam") Color(0xFF991B1B) else EmeraldPrimary)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(TranslationManager.get("opt_syllabus_notes", currentLang), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 2. Exams Mode Button (Always available, tailored for UAT practice & mock exams)
                Button(
                    onClick = onExamsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .pressBounce()
                        .testTag("study_option_exams"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            if (isUat) "Timed Mock & Past Exams" else TranslationManager.get("opt_past_exams", currentLang),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 3. Flashcards Mode Button (Hidden for AAU UAT)
                if (!isUat) {
                    Button(
                        onClick = onFlashcardsClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .pressBounce()
                            .testTag("study_option_flashcards"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Layers, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(TranslationManager.get("opt_flashcards", currentLang), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 4. Official Textbook Mode Button (Hidden for AAU UAT)
                if (!isUat) {
                    Button(
                        onClick = onTextbookClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .pressBounce()
                            .testTag("study_option_textbook"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(TranslationManager.get("opt_official_textbook", currentLang), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 5. Saved Materials Mode Button
                Button(
                    onClick = onSavedMaterialsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .pressBounce()
                        .testTag("study_option_saved_materials"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Bookmark, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            TranslationManager.get("opt_saved_materials", currentLang),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                // Dismiss Button
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text(TranslationManager.get("btn_close_panel", currentLang))
                }
            }
        }
    }
}

@Composable
fun PracticeExamModeSelectionModal(
    subjectName: String,
    onDismiss: () -> Unit,
    onPracticeSelect: () -> Unit,
    onExamSelect: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.5f), HolographicAqua.copy(alpha = 0.5f))),
                    shape = ChamferedCardShape
                ),
            shape = ChamferedCardShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A).copy(alpha = 0.96f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choose Your Mode",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
                Text(
                    text = "Assessment style for $subjectName",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                // Practice Mode Button: calming blue, no timer
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.4f), Color(0xFF3B82F6).copy(alpha = 0.4f))),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .pressBounce()
                        .clickable(onClick = onPracticeSelect)
                        .testTag("practice_mode_select_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A).copy(alpha = 0.7f)) // Deep blue glass
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF3B82F6).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color(0xFF60A5FA))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Practice Mode", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Quiet, No timer. Great for untimed, focused conceptual learning and review.", style = MaterialTheme.typography.labelSmall, color = Color(0xFF93C5FD))
                        }
                    }
                }

                // Exam Mode Button: intense red, with timer
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.4f), Color(0xFFEF4444).copy(alpha = 0.6f))),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(onClick = onExamSelect)
                        .testTag("exam_mode_select_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF7F1D1D).copy(alpha = 0.7f)) // Deep red glass
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.Red.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Timer, contentDescription = null, tint = Color(0xFFFCA5A5))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Exam Mode", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Online - With timer. 120s countdown. Simulates real EUEE pressure with scoring.", style = MaterialTheme.typography.labelSmall, color = Color(0xFFFCA5A5))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text("Go Back")
                }
            }
        }
    }
}

@Composable
fun DarkThemedNotesReader(
    subjectName: String,
    note: SubjectNote?,
    index: Int,
    totalNotes: Int,
    viewModel: StudyViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onClose: () -> Unit,
    onMarkCompleted: () -> Unit
) {
    val selectedGrade by viewModel.selectedGradeFilter.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isSatCourse = subjectName.contains("SAT", ignoreCase = true) || subjectName.contains("Aptitude", ignoreCase = true)
    val context = LocalContext.current

    // Date-based locking check
    val isLockedByDate = remember(note?.id) {
        val releaseDate = note?.releaseDate
        if (releaseDate.isNullOrBlank()) {
            false // No release date set, always available
        } else {
            try {
                val today = java.time.LocalDate.now()
                val release = java.time.LocalDate.parse(releaseDate)
                today.isBefore(release)
            } catch (e: Exception) {
                false // Invalid date format, treat as available
            }
        }
    }

    // Access logging
    LaunchedEffect(note?.id) {
        note?.let {
            viewModel.markNoteAsRead(it.id)
            // Log access timestamp
            val prefs = context.getSharedPreferences("note_access_log", android.content.Context.MODE_PRIVATE)
            val logKey = "accessed_${it.id}"
            val currentTime = java.time.LocalDateTime.now().toString()
            val existingLog = prefs.getString(logKey, "")
            val newLog = if (existingLog.isNullOrBlank()) currentTime else "$existingLog|$currentTime"
            prefs.edit().putString(logKey, newLog).apply()
            // Increment access count
            val countKey = "count_${it.id}"
            val currentCount = prefs.getInt(countKey, 0)
            prefs.edit().putInt(countKey, currentCount + 1).apply()
        }
    }

    Surface(
        color = ReaderBgDark,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            val savedNotesSet by viewModel.savedNotesSet.collectAsState()
            val isSaved = note != null && savedNotesSet.contains(note.id)
            val coroutineScope = rememberCoroutineScope()
            var isDownloaded by remember(note?.id) { mutableStateOf(false) }
            var downloadProgress by remember(note?.id) { mutableStateOf<Int?>(null) }

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = subjectName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = HolographicAqua,
                            fontWeight = FontWeight.Black
                        )
                        if (note != null && !isSatCourse && note.gradeLevel.isNotBlank()) {
                            Surface(
                                color = EmeraldPrimary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f))
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
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = note?.unit ?: "Unit 1",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Download note for offline study
                    IconButton(
                        onClick = {
                            if (!isDownloaded && downloadProgress == null) {
                                coroutineScope.launch {
                                    for (p in 20..100 step 20) {
                                        downloadProgress = p
                                        kotlinx.coroutines.delay(80)
                                    }
                                    downloadProgress = null
                                    isDownloaded = true
                                }
                            }
                        }
                    ) {
                        if (downloadProgress != null) {
                            CircularProgressIndicator(
                                progress = { (downloadProgress ?: 0) / 100f },
                                modifier = Modifier.size(20.dp),
                                color = EmeraldPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download,
                                contentDescription = "Download note",
                                tint = if (isDownloaded) EmeraldPrimary else Color.White
                            )
                        }
                    }

                    // Save note button
                    IconButton(
                        onClick = {
                            if (note != null) {
                                viewModel.toggleSaveNote(note.id)
                            }
                        },
                        modifier = Modifier.testTag("note_bookmark_btn_${note?.id ?: "temp"}")
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save note",
                            tint = if (isSaved) GoldAccent else Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close Note", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = if (totalNotes > 0) (index + 1).toFloat() / totalNotes else 0f,
                color = HolographicAqua,
                trackColor = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content space
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                if (note != null) {
                    if (isLockedByDate) {
                        // Date-locked content
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = GoldAccent,
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    text = "Content Locked",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "This note will be available on ${note.releaseDate}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                                val daysRemaining = try {
                                    val today = java.time.LocalDate.now()
                                    val release = java.time.LocalDate.parse(note.releaseDate)
                                    java.time.temporal.ChronoUnit.DAYS.between(today, release)
                                } catch (e: Exception) { 0 }
                                if (daysRemaining > 0) {
                                    Surface(
                                        color = GoldAccent.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "$daysRemaining day${if (daysRemaining > 1) "s" else ""} remaining",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = GoldAccent,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        // Normal Markdown content display
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        com.example.ui.tools.ui.TamheroMarkdownView(
                            text = note.content,
                            isDark = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (selectedGrade != "All") "No notes found for $selectedGrade." else "No notes loaded.",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation bottom chevrons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    enabled = index > 0,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (index > 0) CardBgDark else Color.Transparent)
                ) {
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = "Previous note",
                        tint = if (index > 0) EmeraldPrimary else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // If on final note unit, show complete subject button!
                if (index == totalNotes - 1) {
                    Button(
                        onClick = onMarkCompleted,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("mark_completed_button")
                    ) {
                        Text("Complete & Exit", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text(
                        text = "${index + 1} OF $totalNotes UNITS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = onNext,
                    enabled = index < totalNotes - 1,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (index < totalNotes - 1) CardBgDark else Color.Transparent)
                ) {
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = "Next note",
                        tint = if (index < totalNotes - 1) EmeraldPrimary else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionsArena(
    subjectName: String,
    question: ExamQuestion?,
    index: Int,
    totalQuestions: Int,
    mode: String,
    timerSeconds: Int,
    isTimerActive: Boolean,
    selectedAnswers: Map<String, String>,
    revealedAnswers: Set<String>,
    viewModel: StudyViewModel,
    onTimerToggle: () -> Unit,
    onSelectOption: (String, String) -> Unit,
    onRevealAnswer: (String) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onSubmit: () -> Unit,
    onExit: () -> Unit
) {
    val isExam = mode == "exam"
    val chosenOption = selectedAnswers[question?.id ?: ""]
    val isAnswerRevealed = revealedAnswers.contains(question?.id ?: "")
    val savedQuestionsSet by viewModel.savedQuestionsSet.collectAsState()
    val isCurrentQuestionSaved = question != null && savedQuestionsSet.contains(question.id)

    Surface(
        color = ReaderBgDark,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Header / Mode Indicator Panel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = subjectName.uppercase() + " ASSESS",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isExam) Color.Red else HolographicAqua,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Question ${index + 1} of $totalQuestions",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {


                    // Save Question Button
                    IconButton(
                        onClick = {
                            if (question != null) {
                                viewModel.toggleSaveQuestion(question.id)
                            }
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isCurrentQuestionSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save question",
                            tint = if (isCurrentQuestionSaved) GoldAccent else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // If exam, count timer active click and blink
                    if (isExam) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF7F1D1D)) // deep red
                                .clickable(onClick = onTimerToggle)
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isTimerActive) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                contentDescription = "Pause assessment", 
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${timerSeconds}s",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF1E3A8A))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("PRACTICE", color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Close X out
                    IconButton(onClick = onExit, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Exit Quiz", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Body
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                if (question != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBgDark)
                    ) {
                        Text(
                            text = question.questionText,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            lineHeight = 26.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    // Options Grid Cards
                    val opts = listOf(
                        "A" to question.optionA,
                        "B" to question.optionB,
                        "C" to question.optionC,
                        "D" to question.optionD
                    )

                    opts.forEach { (letter, valText) ->
                        val isSelected = chosenOption == letter
                        
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onSelectOption(question.id, letter) }
                                .border(
                                    1.dp,
                                    if (isSelected) EmeraldPrimary else Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) EmeraldDark.copy(alpha = 0.25f) else Color(0xFF1E293B)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Circular alphabet
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) EmeraldPrimary else Color.DarkGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = letter,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = valText,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Reveal Answer button (For Practice Mode)
                    if (!isExam) {
                        Spacer(modifier = Modifier.height(20.dp))
                        if (!isAnswerRevealed) {
                            Button(
                                onClick = { onRevealAnswer(question.id) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("reveal_answer_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = IndigoActive)
                            ) {
                                Text("Reveal Correct Answer", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            // Revealed layout with holographic shimmering
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, HolographicAqua, RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldPrimary)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Correct Option: ${question.correctOption}",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = EmeraldPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = question.explanation,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextLight,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }

                } else {
                    Text("No question packets.", color = Color.Gray)
                }
            }

            // Bottom Navigation Chevrons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onPrevious,
                    enabled = index > 0,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (index > 0) Color.White else Color.DarkGray)
                ) {
                    Text("Previous", color = if (index > 0) Color.White else Color.DarkGray)
                }

                if (index == totalQuestions - 1) {
                    Button(
                        onClick = onSubmit,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        modifier = Modifier.testTag("submit_exam_button")
                    ) {
                        Text("Finish Exam", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onNext,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Text("Next Question")
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreResultModal(
    percent: Int,
    correct: Int,
    total: Int,
    viewModel: StudyViewModel,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (percent >= 60 || correct > 0) {
            viewModel.triggerCompletionCelebration()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.5f), GoldAccent.copy(alpha = 0.5f))
                    ),
                    shape = ChamferedCardShape
                ),
            shape = ChamferedCardShape,
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A).copy(alpha = 0.96f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Trophy Emblem
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(HexagonalCutShape)
                        .background(GoldLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = GoldDark, modifier = Modifier.size(36.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (percent >= 75) "Excellent Work!" else "Keep Practicing!",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "You scored $percent% on this assessment.",
                    style = MaterialTheme.typography.titleLarge,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${correct}/${total}", style = MaterialTheme.typography.displaySmall, color = Color.White)
                        Text("Answers Correct", style = MaterialTheme.typography.labelSmall, color = Color.LightGray)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("+${correct * 10}", style = MaterialTheme.typography.displaySmall, color = EmeraldPrimary)
                        Text("Points Accumulated", style = MaterialTheme.typography.labelSmall, color = Color.LightGray)
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("result_continue_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Continue Studying", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

fun getYouTubeIdFromUrl(url: String): String? {
    return when {
        url.contains("list=") -> {
            val listId = url.substringAfter("list=").substringBefore("&").substringBefore("/")
            "videoseries?list=$listId"
        }
        url.contains("youtu.be/") -> {
            val pre = url.substringAfter("youtu.be/")
            val qIdx = pre.indexOf('?')
            val sIdx = pre.indexOf('/')
            val safeLen = if (qIdx != -1 && sIdx != -1) minOf(qIdx, sIdx) else if (qIdx != -1) qIdx else if (sIdx != -1) sIdx else pre.length
            pre.substring(0, safeLen)
        }
        url.contains("v=") -> {
            url.substringAfter("v=").substringBefore("&").substringBefore("/")
        }
        url.contains("embed/") -> {
            url.substringAfter("embed/").substringBefore("?").substringBefore("/")
        }
        else -> {
            if (url.length == 11 && !url.contains(" ") && !url.contains("/")) url else null
        }
    }
}

fun isYouTubeVideo(idOrUrl: String): Boolean {
    val clean = idOrUrl.trim()
    return clean.contains("youtube.com") || 
           clean.contains("youtu.be") || 
           clean.startsWith("videoseries") || 
           clean.contains("list=") ||
           (clean.length == 11 && !clean.contains(" ") && !clean.contains("/") && !clean.contains(":"))
}

fun extractYoutubeEmbedUrl(youtubeIdOrUrl: String): String {
    val clean = youtubeIdOrUrl.trim()
    
    if (clean.startsWith("videoseries") || clean.contains("list=")) {
        val base = if (clean.startsWith("http")) clean else "https://www.youtube.com/embed/$clean"
        return if (base.contains("?")) "$base&autoplay=1&mute=0" else "$base?autoplay=1&mute=0"
    }
    
    var extractedId = ""
    if (clean.contains("v=")) {
        val parts = clean.split("v=")
        if (parts.size > 1) {
            val afterV = parts[1]
            extractedId = afterV.split("&")[0].split("?")[0]
        }
    } else if (clean.contains("youtu.be/")) {
        val parts = clean.split("youtu.be/")
        if (parts.size > 1) {
            extractedId = parts[1].split("&")[0].split("?")[0]
        }
    } else if (clean.contains("embed/")) {
        val parts = clean.split("embed/")
        if (parts.size > 1) {
            extractedId = parts[1].split("&")[0].split("?")[0]
        }
    } else if (clean.startsWith("http")) {
        val parts = clean.split("/")
        if (parts.isNotEmpty()) {
            val lastPart = parts.last()
            extractedId = lastPart.split("&")[0].split("?")[0]
        }
    } else {
        extractedId = clean
    }
    
    return "https://www.youtube.com/embed/$extractedId?autoplay=1&mute=0&rel=0&showinfo=0&controls=1"
}

@Composable
fun SleekVideosScreen(
    subjectId: String,
    subjectName: String,
    viewModel: StudyViewModel,
    onClose: () -> Unit
) {
    val activeMode by viewModel.activeMode.collectAsState()
    val customVideos by viewModel.activeAnalyzedVideos.collectAsState()
    val isAnalyzing by viewModel.isAnalyzingVideo.collectAsState()
    val analysisError by viewModel.analysisError.collectAsState()

    val standardVideos = remember(subjectId) {
        when (subjectId) {
            "euee_nat_maths", "euee_soc_maths", "freshman_soc_maths", "uat_quantitative", "uat_analytical" -> {
                listOf(
                    VideoItem("math_playlist", "📺 PLAYLIST: Full EUEE Maths Prep Series", "Complete", "Exquisite, comprehensive, high-yield Mathematics preparation series covering both Natural & Social streams with past entrance exam breakthroughs.", "videoseries?list=PLfXpdCfxjXmZooKjB88nHk8FCjnNxSsJ6"),
                    VideoItem("math_1", "Lecture 1: Mathematical Induction & Sequences", "18 mins", "Mastering arithmetic progression (AP), geometric progression (GP), convergence, divergence, and infinite geometric series calculations.", "videoseries?list=PLfXpdCfxjXmZooKjB88nHk8FCjnNxSsJ6&index=0"),
                    VideoItem("math_2", "Lecture 2: Limits and Continuity Mastery", "22 mins", "Solving one-sided limits, indeterminate forms, squeeze theorem, and verifying continuity of piecewise functions under exam pressure.", "videoseries?list=PLfXpdCfxjXmZooKjB88nHk8FCjnNxSsJ6&index=1"),
                    VideoItem("math_3", "Lecture 3: Derivatives & Optimization Problems", "19 mins", "High-yield drills on the chain rule, implicit differentiation, absolute extrema, tangent lines, and real-world rates of change.", "videoseries?list=PLfXpdCfxjXmZooKjB88nHk8FCjnNxSsJ6&index=2"),
                    VideoItem("math_4", "Lecture 4: Integral Calculus & Area Integration", "24 mins", "Integration techniques: u-substitution, integration by parts, partial fractions, and calculating volumes of revolutionized solids.", "videoseries?list=PLfXpdCfxjXmZooKjB88nHk8FCjnNxSsJ6&index=3"),
                    VideoItem("math_5", "Lecture 5: Matrices and Systems of Linear Equations", "15 mins", "Calculating determinants, row reduction, inverse matrices, and applying Cramer's Rule to solve multi-variable systems of equations.", "videoseries?list=PLfXpdCfxjXmZooKjB88nHk8FCjnNxSsJ6&index=4")
                )
            }
            "freshman_nat_emerging_tech", "freshman_soc_emerging_tech" -> {
                listOf(
                    VideoItem("emtech_playlist", "📺 PLAYLIST: Full Emerging Tech Lectures (EmTe 1012)", "Complete", "Official freshman semester course covering Industry 4.0, Artificial Intelligence, Blockchain, IoT, and Cloud Computing ecosystems.", "videoseries?list=PLFNOu8LMZhXZ-a_ze9rH9mfBVyepzxBvf"),
                    VideoItem("emtech_1", "Lecture 1: Industry 4.0 & Digital Revolution", "15 mins", "An essential overview of the evolution of industrial eras, key drivers of the digital age, and hyper-connected systems.", "videoseries?list=PLFNOu8LMZhXZ-a_ze9rH9mfBVyepzxBvf&index=0"),
                    VideoItem("emtech_2", "Lecture 2: Artificial Intelligence & Machine Learning", "20 mins", "Understanding neural networks, supervised vs unsupervised learning, NLP, computer vision, and expert system structures.", "videoseries?list=PLFNOu8LMZhXZ-a_ze9rH9mfBVyepzxBvf&index=1"),
                    VideoItem("emtech_3", "Lecture 3: Blockchain Ecosystems & Cybersecurity", "18 mins", "Mastering key-cryptography, secure distributed consensus, smart contracts, immutable ledgers, and secure decentralization paradigms.", "videoseries?list=PLFNOu8LMZhXZ-a_ze9rH9mfBVyepzxBvf&index=2"),
                    VideoItem("emtech_4", "Lecture 4: Internet of Things (IoT) & Smart Sensors", "14 mins", "Explaining physical sensors, actuators, edge gateway layers, fog computing, and telemetry networks in smart cities.", "videoseries?list=PLFNOu8LMZhXZ-a_ze9rH9mfBVyepzxBvf&index=3")
                )
            }
            "freshman_nat_english_1", "freshman_soc_english_1" -> {
                listOf(
                    VideoItem(
                        id = "part_1",
                        title = "Part 1: Chapter 1 & 2 - Uses of Tenses",
                        duration = "15 mins",
                        description = "In-depth Communicative English I video course covering Present, Past, and Perfect tenses with practical context drills.",
                        youtubeId = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                    ),
                    VideoItem(
                        id = "part_2",
                        title = "Part 2: Chapter 2 - Modals and Infinitives for Advice",
                        duration = "18 mins",
                        description = "Mastering degrees of certainty, obligation, permission, and grammatical forms of giving advice with exercises.",
                        youtubeId = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                    ),
                    VideoItem(
                        id = "part_3",
                        title = "Part 3: Chapter 2 - Conditional Sentences (If-Clauses)",
                        duration = "19 mins",
                        description = "Deep dive into Conditional Types 0, 1, 2, and 3: structures, inversion rules, and hypothetical meanings.",
                        youtubeId = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                    ),
                    VideoItem(
                        id = "part_4",
                        title = "Part 4: Chapter 3 - Exercises on Tenses and Modals",
                        duration = "16 mins",
                        description = "Walkthrough of chapter-end review exercises and practice questions from the module step-by-step.",
                        youtubeId = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
                    ),
                    VideoItem(
                        id = "part_5",
                        title = "Part 5: Chapter 4 - Cohesive Devices & unified Paragraphs",
                        duration = "15 mins",
                        description = "Developing paragraph unity, transitions, coordinators, write-up coherence, and advanced essay layouts.",
                        youtubeId = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
                    )
                )
            }
            "freshman_nat_english_2", "freshman_soc_english_2" -> {
                listOf(
                    VideoItem("eng2_1", "Chapter 1: Advanced Critical Reading and Summary Skills", "18 mins", "Critically dissecting complex scholarly publications, journal structures, and formulating high-value academic summaries.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
                    VideoItem("eng2_2", "Chapter 2: Paraphrasing Techniques & Plagiarism Prevention", "15 mins", "Active rewriting of core scientific source content without sacrificing semantic meaning or committing standard plagiarism.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"),
                    VideoItem("eng2_3", "Chapter 3: Academic Citations: APA Style Format (7th Edition)", "16 mins", "Configuring perfect in-text parenthetical citations, narrative citations, references list structures, and citation generators.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"),
                    VideoItem("eng2_4", "Chapter 3: Scholarly Citations: MLA Style Formatting", "14 mins", "Author-page indexation models, works cited assemblies, and formatting modern research papers cleanly.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"),
                    VideoItem("eng2_5", "Chapter 4: Academic Report Writing & Structure", "19 mins", "Structuring formal files (introductions, literature reviews, results, discussions) and standard research templates.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")
                )
            }
            "euee_nat_english", "euee_soc_english" -> {
                listOf(
                    VideoItem("euee_eng1", "EUEE English Drill 1: Grammatical Concord & Tenses", "15 mins", "High-yield preparation focused entirely on recurring active tense alignment questions in regional entrance events.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
                    VideoItem("euee_eng2", "EUEE English Drill 2: Voice Transformation Breakthroughs", "17 mins", "Solving active-to-passive question patterns on national exam sheets with speed tips.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"),
                    VideoItem("euee_eng3", "EUEE English Drill 3: Conditional If-Clauses & Modals", "15 mins", "Drilling Conditional Types 0-3 on past exam questions. Eliminating wrong choices in under 10 seconds.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"),
                    VideoItem("euee_eng4", "EUEE English Drill 4: Context Clues & Rapid Synonyms", "14 mins", "How to decrypt hard english words on comprehension passages using nearby syntax markers and context.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")
                )
            }
            "uat_english" -> {
                listOf(
                    VideoItem("uat_eng1", "Lecture 1: Verbal Aptitude Section & Strategy", "14 mins", "Navigating the English and critical reasoning components of the modern University Admission Test (UAT).", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
                    VideoItem("uat_eng2", "Lecture 2: Synonyms, Antonyms, and Analogy Hacks", "15 mins", "Developing vocabulary reasoning power and logical associations under adaptive exam timing constraints.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"),
                    VideoItem("uat_eng3", "Lecture 3: Sentence Corrections & Error Identifications", "16 mins", "Spotting misplaced modifiers, dangling reference structures, and subtle grammar flaws in exam sentences.", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
                )
            }
            else -> {
                listOf(
                    VideoItem("vid_1", "Lecture 1: Introduction to $subjectName", "12 mins", "An overview of core units, syllabus expectations, and national exam weighting indices for high yield topics.", "8bH0-P8640k"),
                    VideoItem("vid_2", "Lecture 2: Core Concepts & Deep Dive", "18 mins", "Simplifying complex theorems, cellular maps, or chronological structures with fast flash recall patterns.", "b8-M9OubR80"),
                    VideoItem("vid_3", "Lecture 3: Past Exams Breakthrough Strategy", "22 mins", "Working through historical questions step-by-step. Discover shortcut tricks to rule out incorrect options in seconds.", "tsh0N5S4lU0"),
                    VideoItem("vid_4", "Lecture 4: Rapid Revision & Final Checklist", "15 mins", "A quick summary of every chapter and formulas to memorize before turning over your exam papers.", "dQw4w9WgXcQ")
                )
            }
        }
    }

    val allVideos = remember(standardVideos, customVideos, activeMode) {
        val customItems = customVideos.filter {
            val isExamVideo = it.domainOrDepartment == "EXIT EXAM"
            if (activeMode == "exit_exam") isExamVideo else !isExamVideo
        }.map {
            VideoItem(
                id = it.id,
                title = it.title,
                duration = it.duration,
                description = it.description,
                youtubeId = getYouTubeIdFromUrl(it.videoUrl) ?: it.videoUrl,
                isCustom = true,
                conceptMapping = it.conceptMapping,
                summary = it.summary,
                keyTerms = it.keyTerms,
                questionsJson = it.questionsJson
            )
        }
        standardVideos + customItems
    }

    var selectedIndex by remember(allVideos.size) { mutableStateOf(0) }
    val activeVideo = allVideos.getOrNull(selectedIndex) ?: allVideos.getOrNull(0) ?: standardVideos[0]

    var isPlaying by remember { mutableStateOf(true) }
    var customInfoTab by remember { mutableStateOf("summary") } // "summary", "mapping", "quiz"
    var videoInputText by remember { mutableStateOf("") }
    
    // Tracks interactive quiz selections: questionId -> selectedOption
    var quizSelectedState by remember(activeVideo.id) { mutableStateOf<Map<String, String>>(emptyMap()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)) // Twilight canvas background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E293B))
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClose) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (subjectId == "freshman_nat_english_1" || subjectId == "freshman_soc_english_1") "Communicative English I (FLEN 1011)" else "$subjectName Video Lessons",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (activeMode == "exit_exam") Color(0xFFDC2626).copy(alpha = 0.2f) else EmeraldPrimary.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (activeMode == "exit_exam") "EXIT EXAM STREAM" else "DEPARTMENT PATH",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Black),
                        color = if (activeMode == "exit_exam") Color(0xFFFCA5A5) else EmeraldPrimary
                    )
                }
            }

            // Interactive High-Fi Video Player Container (YouTube Embed iFrame / Embedded Local Player)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        WebView(context).apply {
                            settings.apply {
                                javaScriptEnabled = true
                                useWideViewPort = true
                                loadWithOverviewMode = true
                                domStorageEnabled = true
                                databaseEnabled = true
                                mediaPlaybackRequiresUserGesture = false
                                allowContentAccess = true
                                allowFileAccess = true
                                javaScriptCanOpenWindowsAutomatically = true
                                mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                userAgentString = "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36"
                            }
                            webViewClient = WebViewClient()
                            webChromeClient = WebChromeClient()
                        }
                    },
                    update = { webView ->
                        val targetUrl = if (isYouTubeVideo(activeVideo.youtubeId)) {
                            extractYoutubeEmbedUrl(activeVideo.youtubeId)
                        } else {
                            val sdCardPath = "/sdcard/Download/"
                            val files = listOf(
                                "${sdCardPath}${activeVideo.id}.mp4",
                                "${sdCardPath}Part_${activeVideo.id.substringAfterLast("_")}.mp4",
                                "${sdCardPath}part${activeVideo.id.substringAfterLast("_")}.mp4",
                                "${sdCardPath}Part${activeVideo.id.substringAfterLast("_")}.mp4"
                            )
                            var foundPath: String? = null
                            for (f in files) {
                                if (java.io.File(f).exists()) {
                                    foundPath = f
                                    break
                                }
                            }
                            if (foundPath != null) "file://$foundPath" else activeVideo.youtubeId
                        }

                        val currentUrl = webView.tag as? String
                        if (currentUrl != targetUrl) {
                            webView.tag = targetUrl
                            webView.loadUrl(targetUrl)
                        }
                    },
                    onRelease = { webView ->
                        webView.stopLoading()
                        webView.loadUrl("about:blank")
                        webView.destroy()
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Video Meta Segment
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = activeVideo.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${activeVideo.duration} • Fully Offline Enabled • AI Resource Stream",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = if (activeMode == "exit_exam") Color(0xFFF87171) else EmeraldPrimary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // AI Integrated Analysis Segment (Shown ONLY for custom analyzed videos!)
                if (activeVideo.isCustom) {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            // Professional Tabs Indicator
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF1E293B)),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                val tabs = listOf("summary" to "AI Summary", "mapping" to "Mapping", "quiz" to "Interactive Quiz")
                                tabs.forEach { (tabId, label) ->
                                    val isSelected = customInfoTab == tabId
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { customInfoTab = tabId }
                                            .background(if (isSelected) (if (activeMode == "exit_exam") Color(0xFF991B1B) else EmeraldPrimary) else Color.Transparent)
                                            .padding(vertical = 12.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = label.uppercase(),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                            color = if (isSelected) Color.White else Color.Gray
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Tab details
                            when (customInfoTab) {
                                "summary" -> {
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text(
                                                text = "ACTIONABLE STUDY SUMMARY",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(bottom = 8.dp)
                                            )
                                            Text(
                                                text = activeVideo.summary,
                                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                                color = Color.White
                                            )
                                            HorizontalDivider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 12.dp))
                                            Text(
                                                text = "HIGH YIELD TERMS",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(bottom = 8.dp)
                                            )
                                            Text(
                                                text = activeVideo.keyTerms,
                                                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                                                color = TextMuted
                                            )
                                        }
                                    }
                                }
                                "mapping" -> {
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(20.dp))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "ACADEMIC DOMAIN RELEVANCE MAP",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                                    color = Color(0xFFF59E0B)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Text(
                                                text = activeVideo.conceptMapping,
                                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                                "quiz" -> {
                                    val questions = remember(activeVideo.id, activeVideo.questionsJson) {
                                        try {
                                            val arr = org.json.JSONArray(activeVideo.questionsJson)
                                            val list = mutableListOf<com.example.data.ExamQuestion>()
                                            for (i in 0 until arr.length()) {
                                                val obj = arr.getJSONObject(i)
                                                list.add(
                                                    com.example.data.ExamQuestion(
                                                        id = "${activeVideo.id}_q_$i",
                                                        subjectId = subjectName,
                                                        questionText = obj.optString("questionText", ""),
                                                        optionA = obj.optString("optionA", ""),
                                                        optionB = obj.optString("optionB", ""),
                                                        optionC = obj.optString("optionC", ""),
                                                        optionD = obj.optString("optionD", ""),
                                                        correctOption = obj.optString("correctOption", "A"),
                                                        explanation = obj.optString("explanation", "")
                                                    )
                                                )
                                            }
                                            list
                                        } catch (e: Exception) {
                                            emptyList()
                                        }
                                    }

                                    if (questions.isEmpty()) {
                                        Box(
                                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("No practice quiz questions generated for this lecture.", color = Color.Gray)
                                        }
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                            questions.forEachIndexed { qIdx, question ->
                                                val selectedOption = quizSelectedState[question.id]
                                                val isAnswered = selectedOption != null

                                                Card(
                                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                                    shape = RoundedCornerShape(12.dp),
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Column(modifier = Modifier.padding(16.dp)) {
                                                        Text(
                                                            text = "QUESTION ${qIdx + 1} OF ${questions.size}",
                                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                                            color = if (activeMode == "exit_exam") Color(0xFFFCA5A5) else EmeraldPrimary,
                                                            modifier = Modifier.padding(bottom = 6.dp)
                                                        )
                                                        Text(
                                                            text = question.questionText,
                                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                                            color = Color.White,
                                                            modifier = Modifier.padding(bottom = 12.dp)
                                                        )

                                                        val options = listOf(
                                                            "A" to question.optionA,
                                                            "B" to question.optionB,
                                                            "C" to question.optionC,
                                                            "D" to question.optionD
                                                        )

                                                        options.forEach { (letter, optText) ->
                                                            val isCurrent = selectedOption == letter
                                                            val isCorrectOpt = question.correctOption == letter
                                                            val buttonColor = when {
                                                                !isAnswered -> Color(0xFF0F172A)
                                                                isCurrent && isCorrectOpt -> Color(0xFF10B981) // Correct (Green)
                                                                isCurrent && !isCorrectOpt -> Color(0xFFEF4444) // Wrong (Red)
                                                                isCorrectOpt -> Color(0xFF10B981) // Highlight correct
                                                                else -> Color(0xFF0F172A)
                                                            }

                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .run {
                                                                        if (!isAnswered) clickable {
                                                                            val updated = quizSelectedState.toMutableMap()
                                                                            updated[question.id] = letter
                                                                            quizSelectedState = updated
                                                                        } else this
                                                                    }
                                                                    .clip(RoundedCornerShape(8.dp))
                                                                    .background(buttonColor)
                                                                    .border(1.dp, if (isCurrent) Color.White else Color(0xFF334155), RoundedCornerShape(8.dp))
                                                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Box(
                                                                    modifier = Modifier
                                                                        .size(24.dp)
                                                                        .clip(CircleShape)
                                                                        .background(if (isCurrent) Color.White else Color(0xFF334155)),
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Text(
                                                                        text = letter,
                                                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                                                        color = if (isCurrent) Color.Black else Color.White
                                                                    )
                                                                }
                                                                Spacer(modifier = Modifier.width(10.dp))
                                                                Text(
                                                                    text = optText,
                                                                    style = MaterialTheme.typography.bodyMedium,
                                                                    color = Color.White
                                                                )
                                                            }
                                                            Spacer(modifier = Modifier.height(8.dp))
                                                        }

                                                        if (isAnswered) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .clip(RoundedCornerShape(8.dp))
                                                                    .background(Color(0xFFF59E0B).copy(alpha = 0.15f))
                                                                    .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                                                    .padding(12.dp)
                                                            ) {
                                                                Text(
                                                                    text = "🔑 Explanation: ${question.explanation}",
                                                                    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
                                                                    color = Color(0xFFFBBF24)
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
                } else {
                    item {
                        Text(
                            text = activeVideo.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFF1E293B))
                }

                item {
                    HorizontalDivider(color = Color(0xFF1E293B))
                    Text(
                        text = "COURSE PLAYLIST CHAPTERS",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.sp),
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                    )
                }

                // Chapters List
                itemsIndexed(allVideos) { index, item ->
                    val isActive = item.id == activeVideo.id
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedIndex = index
                                isPlaying = true
                            }
                            .background(if (isActive) Color(0xFF1E293B) else Color.Transparent)
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Play/Pause icon indicator
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isActive) EmeraldPrimary.copy(alpha = 0.15f) else Color(0xFF1E293B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (item.isCustom) Icons.Default.Info else (if (isActive && isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow),
                                contentDescription = null,
                                tint = if (isActive) EmeraldPrimary else Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isActive) EmeraldPrimary else Color.White
                            )
                            Text(
                                text = "${item.duration} • ${if (item.isCustom) "AI GENERATED" else "PRELOADED"}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (item.isCustom) Color(0xFFF59E0B) else Color.Gray
                            )
                        }
                    }
                    HorizontalDivider(color = Color(0xFF1E293B).copy(alpha = 0.5f))
                }
            }
        }
    }
}

data class VideoItem(
    val id: String,
    val title: String,
    val duration: String,
    val description: String,
    val youtubeId: String,
    val isCustom: Boolean = false,
    val conceptMapping: String = "",
    val summary: String = "",
    val keyTerms: String = "",
    val questionsJson: String = ""
)

@Composable
fun FreeTrialPaywallDialog(
    packageId: String,
    currentLang: String = "en",
    onDismiss: () -> Unit,
    onUpgradePremium: () -> Unit
) {
    fun t(key: String): String = com.example.ui.TranslationManager.get(key, currentLang)

    val displayedPackageName = when (packageId) {
        "freshman_natural" -> t("pkg_freshman_natural_title")
        "freshman_social" -> t("pkg_freshman_social_title")
        "euee" -> t("pkg_euee_title")
        "aau_uat" -> t("pkg_uat_title")
        "department" -> t("pkg_dept_title")
        "exit_exam" -> t("pkg_exit_title")
        else -> "ተምህሮ / Temhiro Premium"
    }

    val displayDetails = when (packageId) {
        "freshman_natural", "freshman_social" -> t("paywall_freshman_desc")
        "euee", "euee_natural", "euee_social" -> t("paywall_euee_desc")
        "aau_uat" -> t("paywall_uat_desc")
        "department" -> t("paywall_dept_desc")
        "exit_exam" -> t("paywall_exit_desc")
        else -> t("paywall_default_desc")
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.5f), HolographicAqua.copy(alpha = 0.5f))
                    ),
                    shape = ChamferedCardShape
                ),
            shape = ChamferedCardShape,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0F172A).copy(alpha = 0.96f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .holographicShimmer()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(ChamferedCardShape)
                        .background(EmeraldPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = t("paywall_title"),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = displayedPackageName,
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Text(
                    text = displayDetails,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onUpgradePremium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .pressBounce()
                            .testTag("paywall_ultimate_premium_upgrade"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = Color(0xFF0F172A)
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = t("btn_go_premium"),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                    }

                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = t("btn_cancel_back"),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
