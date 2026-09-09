package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
                import com.example.ads.AdManager

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
