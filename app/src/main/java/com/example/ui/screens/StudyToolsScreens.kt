package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdsManager
import com.example.ads.InterstitialAdManager
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.theme.*
import com.example.ui.tools.ui.*

@Composable
fun StudyToolHeader(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    isDark: Boolean,
    onBack: () -> Unit
) {
    Surface(
        color = if (isDark) CardBgDark else Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Back Button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onBack)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Courses",
                            tint = if (isDark) Color.White else IndigoSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Tool Icon & Title
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = iconBg,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        ),
                        color = if (isDark) Color.White else IndigoSecondary,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = TextMuted,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Dedicated Full Screen: Study Timer & Focus Tasks
 */
@Composable
fun StudyTimerScreen(
    viewModel: StudyViewModel,
    onBack: () -> Unit
) {
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val activeSub by viewModel.activeSubject.collectAsState()
    val activeLearningContext by viewModel.activeLearningContext.collectAsState()

    val defaultSubject = activeLearningContext?.courseName?.ifBlank { activeSub?.name ?: "" }
        ?: activeSub?.name ?: "General Study"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight)
    ) {
        StudyToolHeader(
            title = TranslationManager.get("tab_timer_tasks", currentLang),
            subtitle = "Deep focus Pomodoro & daily milestone checklist",
            icon = Icons.Default.Timer,
            iconBg = Color(0xFF10B981),
            isDark = isDark,
            onBack = onBack
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            StudyTimerAndTasksView(
                viewModel = viewModel,
                defaultSubject = defaultSubject
            )
        }
    }
}

/**
 * Dedicated Full Screen: Academic Calendar & Plan My Week
 */
@Composable
fun AcademicCalendarScreen(
    viewModel: StudyViewModel,
    onBack: () -> Unit
) {
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    var showPlanWeekDetail by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight)
    ) {
        StudyToolHeader(
            title = if (showPlanWeekDetail) "Plan My Week" else TranslationManager.get("tab_calendar_plan", currentLang),
            subtitle = if (showPlanWeekDetail) "7-Day pacing & weekly study schedule" else "Assessment dates & syllabus roadmap",
            icon = Icons.Default.CalendarMonth,
            iconBg = Color(0xFF3B82F6),
            isDark = isDark,
            onBack = {
                if (showPlanWeekDetail) {
                    showPlanWeekDetail = false
                } else {
                    onBack()
                }
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (showPlanWeekDetail) {
                PlanMyWeekView(
                    viewModel = viewModel,
                    onBackToCalendar = { showPlanWeekDetail = false }
                )
            } else {
                AcademicCalendarView(
                    viewModel = viewModel,
                    onSwitchToPlanWeek = { showPlanWeekDetail = true }
                )
            }
        }
    }
}

/**
 * Dedicated Full Screen: Target Grades & GPA Planner
 */
@Composable
fun GradePlannerScreen(
    viewModel: StudyViewModel,
    onBack: () -> Unit
) {
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight)
    ) {
        StudyToolHeader(
            title = TranslationManager.get("tab_target_grades", currentLang),
            subtitle = "Course assessment simulator & target GPA calculator",
            icon = Icons.Default.Calculate,
            iconBg = Color(0xFF8B5CF6),
            isDark = isDark,
            onBack = onBack
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            GradePlannerView(
                viewModel = viewModel
            )
        }
    }
}

/**
 * Dedicated Full Screen: AI Document & Exam Scanner
 */
@Composable
fun DocumentScannerScreen(
    viewModel: StudyViewModel,
    onBack: () -> Unit
) {
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val activeSub by viewModel.activeSubject.collectAsState()
    val activeLearningContext by viewModel.activeLearningContext.collectAsState()

    val defaultSubject = activeLearningContext?.courseName?.ifBlank { activeSub?.name ?: "" }
        ?: activeSub?.name ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight)
    ) {
        StudyToolHeader(
            title = TranslationManager.get("tab_doc_scanner", currentLang),
            subtitle = "OCR scanner for exam papers & offline notes summaries",
            icon = Icons.Default.DocumentScanner,
            iconBg = Color(0xFFEC4899),
            isDark = isDark,
            onBack = onBack
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            DocumentScannerView(
                viewModel = viewModel,
                subjectName = defaultSubject
            )
        }
    }
}
