package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ads.AdConfig
import com.example.ads.AdManager
import com.example.ads.TinatBannerAd
import com.example.ui.StudyViewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.tools.ui.StudentToolsLauncher
import com.example.ui.tools.ui.StudentToolsModalSheet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
        }

        // Initialize Google AdMob SDK & UMP Privacy Consent flow via AdManager
        AdManager.initialize(this)

        setContent {
            val viewModel: StudyViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()

            MyApplicationTheme(darkTheme = isDarkTheme) {
                // Initialize Central ViewModel states
                val progress by viewModel.userProgress.collectAsState()
                val currentTab by viewModel.currentTab.collectAsState()
                val showMarketing by viewModel.showMarketingPage.collectAsState()
                val onboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()
                val isSplashChecking by viewModel.isSplashChecking.collectAsState()

                val academicDepartment by viewModel.academicDepartment.collectAsState()

                // Student Tools & Assessment States
                val isStudentToolsOpen by viewModel.isStudentToolsOpen.collectAsState()
                val activeSub by viewModel.activeSubject.collectAsState()
                val showContentPlay by viewModel.showContentPlayView.collectAsState()
                val showNotes by viewModel.showNotesView.collectAsState()
                val showNotesTOC by viewModel.showNotesTableOfContents.collectAsState()
                val showVideos by viewModel.showVideosView.collectAsState()
                val showTextbookReader by viewModel.showTextbookReader.collectAsState()
                val showStudyOptions by viewModel.showStudyOptionsModal.collectAsState()
                val showModeSelection by viewModel.showModeSelectionModal.collectAsState()
                val showSavedMaterialPicker by viewModel.showSavedMaterialPickerModal.collectAsState()
                val showScoreResultModal by viewModel.showScoreResultModal.collectAsState()

                val showPaymentForm by viewModel.showPaymentVerificationScreen.collectAsState()
                val showFreeTrialPaywall by viewModel.showFreeTrialPaywall.collectAsState()

                val isPaymentOrVerification = showPaymentForm || showFreeTrialPaywall || progress.paymentStatus == "pending"

                // Check if user is in any active study screen, notes reader, exam arena, or modal
                val isStudyingOrTakingExam = showNotes || showContentPlay || showNotesTOC ||
                        showVideos || showTextbookReader || showStudyOptions || showModeSelection ||
                        showSavedMaterialPicker || showScoreResultModal || (currentTab == "flashcards")

                // Student tools button is strictly locked to the courses selector view only
                val isUserEnrolledInCourses = onboardingCompleted &&
                        progress.activePackageId != null &&
                        progress.paymentStatus != "pending" &&
                        !(progress.activePackageId == "department" && academicDepartment.isBlank())

                val isCourseSelectorScreen = isUserEnrolledInCourses &&
                        currentTab == "home" &&
                        !isStudyingOrTakingExam

                val showStudentToolsLauncher = isCourseSelectorScreen && !showMarketing && !isStudentToolsOpen

                val shouldShowBottomBanner = AdManager.ADS_ENABLED &&
                        AdManager.BANNER_ADS_ENABLED &&
                        !isSplashChecking &&
                        !isPaymentOrVerification &&
                        !showScoreResultModal &&
                        !isStudentToolsOpen

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent
                ) { innerPadding ->
                    GlassBackground(
                        isDark = isDarkTheme,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (isSplashChecking) {
                            // Splash Screen while validating stored entitlement
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = androidx.compose.ui.Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    androidx.compose.foundation.Image(
                                        painter = androidx.compose.ui.res.painterResource(id = R.drawable.img_app_icon),
                                        contentDescription = "ተምህሮ / Temhiro Logo",
                                        modifier = Modifier
                                            .size(96.dp)
                                            .padding(bottom = 4.dp)
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "ተምህሮ / Temhiro",
                                        style = MaterialTheme.typography.headlineLarge.copy(
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                            color = EmeraldPrimary
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Educational Ecosystem",
                                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
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
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        repeat(3) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(60.dp)
                                                    .clip(RoundedCornerShape(12.dp))
                                                    .background(Color.Gray.copy(alpha = alpha))
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            // Root layout: Content area + Persistent bottom banner advertisement
                            Column(modifier = Modifier.fillMaxSize()) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                ) {
                                    // Step 1: Onboarding questions (Name, Language, Theme, Class Level, Goals)
                                    if (!onboardingCompleted) {
                                        StudentOnboardingScreen(viewModel = viewModel)
                                    } else if (progress.activePackageId == null) {
                                        // Step 2: Choose Your Tool Page
                                        ChooseToolScreen(viewModel = viewModel)
                                    } else {
                                        if (progress.paymentStatus == "pending") {
                                            // Manual proof review screen restricting study content access
                                            VerificationPendingOverlay(
                                                viewModel = viewModel,
                                                txnId = progress.paymentTxnId,
                                                senderPhone = progress.paymentSenderPhone
                                            )
                                        } else {
                                             if ((progress.activePackageId == "department" || progress.activePackageId == "exit_exam") && academicDepartment.isBlank()) {
                                                 // Dedicated Department Selection Screen
                                                 DepartmentSelectionScreen(viewModel = viewModel)
                                             } else {
                                                 // Enrolled active views (approved status or free trial model)
                                                 when (currentTab) {
                                                     "home" -> DashboardScreen(viewModel = viewModel)
                                                     "flashcards" -> FlashcardScreen(viewModel = viewModel)
                                                     "profile" -> ProfileScreen(viewModel = viewModel)
                                                     else -> DashboardScreen(viewModel = viewModel)
                                                 }
                                             }
                                         }
                                    }

                                    // Core assessment overlay (Notes reader, study choice panel, Exams mode)
                                    ContentPlayView(viewModel = viewModel)

                                    // Marketing Landing Section Overlay
                                    androidx.compose.animation.AnimatedVisibility(
                                        visible = showMarketing,
                                        enter = fadeIn(),
                                        exit = fadeOut()
                                    ) {
                                        MarketingLandingView(viewModel = viewModel)
                                    }

                                    // Student Tools Floating Launcher (Circular 56dp button with Tamhero logo)
                                    // Locked strictly to the course selector view; hidden in notes reader, exam arena, etc.
                                    StudentToolsLauncher(
                                        isVisible = showStudentToolsLauncher,
                                        onClick = { viewModel.openStudentTools("timer_tasks") },
                                        modifier = Modifier.align(androidx.compose.ui.Alignment.BottomEnd)
                                    )

                                    // Student Tools Modal Bottom Sheet
                                    if (isStudentToolsOpen) {
                                        val activeLearningContext by viewModel.activeLearningContext.collectAsState()
                                        val defaultSubject = activeLearningContext?.courseName?.ifBlank { activeSub?.name ?: "" }
                                            ?: activeSub?.name ?: ""
                                        val defaultTopic = activeLearningContext?.topicName?.ifBlank { activeSub?.name ?: "" } ?: ""
                                        StudentToolsModalSheet(
                                            viewModel = viewModel,
                                            subjectName = defaultSubject,
                                            currentTopic = defaultTopic,
                                            onDismiss = { viewModel.closeStudentTools() }
                                        )
                                    }

                                    // Free Trial 72h Confirmation Modal Dialog
                                    val showFreeTrialConfirmation by viewModel.showFreeTrialConfirmationDialog.collectAsState()
                                    val pendingTrialPackageId by viewModel.pendingTrialPackageId.collectAsState()
                                    if (showFreeTrialConfirmation && pendingTrialPackageId != null) {
                                        val context = androidx.compose.ui.platform.LocalContext.current
                                        com.example.ui.screens.FreeTrialConfirmationModal(
                                            onDismiss = { viewModel.showFreeTrialConfirmationDialog.value = false },
                                            onConfirm = {
                                                viewModel.activateFreeTrialConfirmed(pendingTrialPackageId!!, context)
                                            }
                                        )
                                    }
                                }

                                // Persistent Anchored Adaptive Bottom Banner
                                if (shouldShowBottomBanner) {
                                    TinatBannerAd()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
