package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ads.AdsManager
import com.example.ads.InterstitialAdManager
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentToolsModalSheet(
    viewModel: StudyViewModel,
    subjectName: String = "",
    currentTopic: String = "",
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val activeTab by viewModel.activeToolsTab.collectAsState()
    val learningContext by viewModel.activeLearningContext.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()

    val effectiveSubjectName = remember(learningContext?.courseName, subjectName) {
        val fromCtx = learningContext?.courseName?.trim()
        when {
            !fromCtx.isNullOrBlank() -> fromCtx
            subjectName.isNotBlank() -> subjectName
            else -> ""
        }
    }

    val effectiveTopic = remember(learningContext?.topicName, currentTopic) {
        learningContext?.topicName?.trim()?.ifBlank { currentTopic } ?: currentTopic
    }

    var showPlanWeekDetail by remember { mutableStateOf(false) }

    // Interstitial ad trigger on dismiss (respects 3-minute cap and test unit fallback)
    val handleDismiss: () -> Unit = {
        val activity = AdsManager.findActivity(context)
        if (activity != null) {
            InterstitialAdManager.showIfAllowed(activity) {
                onDismiss()
            }
        } else {
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = handleDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = if (isDark) CardBgDark else Color.White,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = if (isDark) Color(0xFF475569) else Color(0xFFCBD5E1)
            )
        },
        modifier = Modifier.fillMaxHeight(0.95f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Header: Logo + Title + Close Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon),
                            contentDescription = "Tamhero Logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = TranslationManager.get("student_tools_title", currentLang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) TextLight else Color(0xFF0F172A)
                            )
                        )
                        Text(
                            text = TranslationManager.get("student_tools_subtitle", currentLang),
                            style = MaterialTheme.typography.labelSmall.copy(color = EmeraldPrimary)
                        )
                    }
                }

                IconButton(
                    onClick = handleDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                }
            }

            // Tab Navigation Row
            val tabs = listOf(
                "timer_tasks" to (TranslationManager.get("tab_timer_tasks", currentLang) to Icons.Default.Timer),
                "calendar" to (TranslationManager.get("tab_calendar_plan", currentLang) to Icons.Default.CalendarMonth),
                "grades" to (TranslationManager.get("tab_target_grades", currentLang) to Icons.Default.Calculate),
                "scanner" to (TranslationManager.get("tab_doc_scanner", currentLang) to Icons.Default.DocumentScanner)
            )

            val selectedIndex = tabs.indexOfFirst { it.first == activeTab }.coerceAtLeast(0)

            ScrollableTabRow(
                selectedTabIndex = selectedIndex,
                edgePadding = 12.dp,
                containerColor = if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9),
                contentColor = EmeraldPrimary,
                indicator = { tabPositions ->
                    if (selectedIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                            color = EmeraldPrimary,
                            height = 3.dp
                        )
                    }
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, (tabId, tabData) ->
                    val isSelected = activeTab == tabId
                    Tab(
                        selected = isSelected,
                        onClick = {
                            viewModel.activeToolsTab.value = tabId
                            if (tabId != "calendar") {
                                showPlanWeekDetail = false
                            }
                        },
                        text = {
                            Text(
                                text = tabData.first,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) EmeraldPrimary else (if (isDark) TextMuted else Color.Gray)
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tabData.second,
                                contentDescription = tabData.first,
                                modifier = Modifier.size(18.dp),
                                tint = if (isSelected) EmeraldPrimary else (if (isDark) TextMuted else Color.Gray)
                            )
                        }
                    )
                }
            }

            // Tab Body Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (activeTab) {
                    "calendar" -> {
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
                    "grades" -> {
                        GradePlannerView(
                            viewModel = viewModel
                        )
                    }
                    "scanner" -> {
                        DocumentScannerView(
                            viewModel = viewModel,
                            subjectName = effectiveSubjectName
                        )
                    }
                    "timer_tasks" -> {
                        StudyTimerAndTasksView(
                            viewModel = viewModel,
                            defaultSubject = effectiveSubjectName
                        )
                    }
                    else -> {
                        StudyTimerAndTasksView(
                            viewModel = viewModel,
                            defaultSubject = effectiveSubjectName
                        )
                    }
                }
            }
        }
    }
}
