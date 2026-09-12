package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ads.AdsManager
import com.example.ads.InterstitialAdManager
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.theme.*

/**
 * Slide-out Student Tools Sidebar Drawer.
 * Replaces the legacy floating action button with a modern, structured sidebar
 * displaying all study tools, active learning status, and quick curriculum controls.
 */
@Composable
fun StudentToolsSidebar(
    viewModel: StudyViewModel,
    isOpen: Boolean,
    onClose: () -> Unit
) {
    val isDark by viewModel.isDarkTheme.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val progress by viewModel.userProgress.collectAsState()
    val studentName by viewModel.studentName.collectAsState()
    val answeredCount = viewModel.answeredQuestionsSet.collectAsState().value.size
    val readNotesCount = viewModel.readNotesSet.collectAsState().value.size
    val totalXp = (answeredCount * 15) + (readNotesCount * 25)
    val streakDays by viewModel.dailyStreakCount.collectAsState()

    val trialActivatedAt by viewModel.freeTrialActivatedAtMillis.collectAsState()
    val isTrialExpired = viewModel.isFreeTrialExpired()
    var trialRemainingMs by remember { mutableStateOf(viewModel.getFreeTrialRemainingMillis()) }

    LaunchedEffect(trialActivatedAt) {
        while (true) {
            trialRemainingMs = viewModel.getFreeTrialRemainingMillis()
            kotlinx.coroutines.delay(1000L)
        }
    }

    val context = LocalContext.current
    val handleToolClick: (String) -> Unit = { toolTab ->
        onClose()
        val activity = AdsManager.findActivity(context)
        if (activity != null) {
            InterstitialAdManager.showIfAllowed(activity) {
                viewModel.openToolScreen(toolTab)
            }
        } else {
            viewModel.openToolScreen(toolTab)
        }
    }

    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(animationSpec = tween(250)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
                .clickable(
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                    indication = null,
                    onClick = onClose
                )
        ) {
            AnimatedVisibility(
                visible = isOpen,
                enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
                exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(250)),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 360.dp)
                        .fillMaxWidth(0.88f)
                        .clickable(
                            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                            indication = null,
                            onClick = { /* consume clicks */ }
                        ),
                    color = if (isDark) CardBgDark else Color.White,
                    shadowElevation = 16.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Sidebar Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White,
                                    shadowElevation = 3.dp,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .border(1.5.dp, EmeraldPrimary, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_app_icon),
                                        contentDescription = "Temhiro Logo",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "ተምህሮ / Temhiro",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Black,
                                            fontSize = 16.sp
                                        ),
                                        color = if (isDark) Color.White else IndigoSecondary
                                    )
                                    Text(
                                        text = "STUDENT STUDY SUITE",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp,
                                            fontSize = 10.sp
                                        ),
                                        color = EmeraldPrimary
                                    )
                                }
                            }

                            IconButton(
                                onClick = onClose,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (isDark) Color(0xFF334155) else Color(0xFFF1F5F9))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Sidebar",
                                    tint = if (isDark) Color.White else Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        HorizontalDivider(
                            color = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0),
                            thickness = 1.dp
                        )

                        // Student Profile & Progress Card
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isDark) Color(0xFF0F172A) else Color(0xFFF8FAFC),
                            border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = studentName.ifBlank { progress.username.ifBlank { "Scholar" } },
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp
                                            ),
                                            color = if (isDark) Color.White else IndigoSecondary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        val packageLabel = when (progress.activePackageId) {
                                            "freshman_natural" -> "Freshman Natural Sciences"
                                            "freshman_social" -> "Freshman Social Sciences"
                                            "euee_natural" -> "EUEE Natural Science"
                                            "euee_social" -> "EUEE Social Science"
                                            "aau_uat" -> "AAU UAT Entrance"
                                            "department" -> "Department Curriculum"
                                            "exit_exam" -> "Exit Exam Blueprints"
                                            else -> "Enrolled Student"
                                        }
                                        Text(
                                            text = packageLabel,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                            color = TextMuted,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = EmeraldPrimary.copy(alpha = 0.15f),
                                        modifier = Modifier.padding(start = 6.dp)
                                    ) {
                                        Text(
                                            text = "$totalXp XP",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Black,
                                                fontSize = 11.sp
                                            ),
                                            color = EmeraldPrimary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Badges Row: Streak + Trial Status
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isDark) Color(0xFF1E293B) else Color(0xFFFFFBEB),
                                        border = BorderStroke(1.dp, Color(0xFFF97316).copy(alpha = 0.3f)),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                Icons.Default.LocalFireDepartment,
                                                contentDescription = "Streak",
                                                tint = Color(0xFFF97316),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "$streakDays Day Streak",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (isDark) GoldLight else Color(0xFFC2410C)
                                            )
                                        }
                                    }

                                    if (trialActivatedAt > 0L && !isTrialExpired) {
                                        val totalSec = (trialRemainingMs / 1000).toInt()
                                        val h = totalSec / 3600
                                        val m = (totalSec % 3600) / 60
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isDark) Color(0xFF1E293B) else Color(0xFFF0FDF4),
                                            border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Icon(
                                                    Icons.Default.Timer,
                                                    contentDescription = "Trial",
                                                    tint = EmeraldPrimary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "${h}h ${m}m Trial",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = EmeraldPrimary
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Section: Academic Tools
                        Text(
                            text = "ACADEMIC STUDY TOOLS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.2.sp,
                                fontSize = 11.sp
                            ),
                            color = TextMuted,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )

                        val tools = listOf(
                            SidebarToolItem(
                                id = "timer_tasks",
                                title = TranslationManager.get("tab_timer_tasks", currentLang),
                                subtitle = "Pomodoro focus timer & daily task tracker",
                                icon = Icons.Default.Timer,
                                iconBg = Color(0xFF10B981)
                            ),
                            SidebarToolItem(
                                id = "calendar",
                                title = TranslationManager.get("tab_calendar_plan", currentLang),
                                subtitle = "Exam deadlines, syllabus schedule & week plan",
                                icon = Icons.Default.CalendarMonth,
                                iconBg = Color(0xFF3B82F6)
                            ),
                            SidebarToolItem(
                                id = "grades",
                                title = TranslationManager.get("tab_target_grades", currentLang),
                                subtitle = "Course grading simulator & target GPA calculator",
                                icon = Icons.Default.Calculate,
                                iconBg = Color(0xFF8B5CF6)
                            ),
                            SidebarToolItem(
                                id = "scanner",
                                title = TranslationManager.get("tab_doc_scanner", currentLang),
                                subtitle = "Camera OCR scanner for exam papers & notes",
                                icon = Icons.Default.DocumentScanner,
                                iconBg = Color(0xFFEC4899)
                            )
                        )

                        tools.forEach { tool ->
                            SidebarToolCard(
                                item = tool,
                                isDark = isDark,
                                onClick = {
                                    handleToolClick(tool.id)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Section: Quick Navigation & Preferences
                        Text(
                            text = "PREFERENCES & SHORTCUTS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.2.sp,
                                fontSize = 11.sp
                            ),
                            color = TextMuted,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )

                        // Switch Curriculum Package Action
                        SidebarActionRow(
                            icon = Icons.Default.SwapHoriz,
                            iconTint = EmeraldPrimary,
                            title = "Reset / Re-enroll Package",
                            subtitle = "Re-enroll to select a new curriculum or package",
                            isDark = isDark,
                            onClick = {
                                onClose()
                                viewModel.resetEnrollment()
                            }
                        )

                        // Student Profile Action
                        SidebarActionRow(
                            icon = Icons.Default.Person,
                            iconTint = Color(0xFF3B82F6),
                            title = "Student Profile & Analytics",
                            subtitle = "View detailed mastery stats and records",
                            isDark = isDark,
                            onClick = {
                                onClose()
                                viewModel.currentTab.value = "profile"
                            }
                        )

                        // Theme Toggle Action
                        SidebarActionRow(
                            icon = if (isDark) Icons.Default.WbSunny else Icons.Default.DarkMode,
                            iconTint = if (isDark) GoldLight else Color(0xFF6366F1),
                            title = if (isDark) "Switch to Light Mode" else "Switch to Dark Mode",
                            subtitle = if (isDark) "Comfortable reading in bright environments" else "Dark OLED reader canvas",
                            isDark = isDark,
                            onClick = {
                                viewModel.toggleDarkTheme()
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

data class SidebarToolItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBg: Color
)

@Composable
private fun SidebarToolCard(
    item: SidebarToolItem,
    isDark: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isDark) Color(0xFF1E293B) else Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = item.iconBg,
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = if (isDark) Color.White else IndigoSecondary
                )
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = if (isDark) TextMuted else Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun SidebarActionRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    isDark: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = iconTint.copy(alpha = 0.15f),
            modifier = Modifier.size(34.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                ),
                color = if (isDark) Color.White else IndigoSecondary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
