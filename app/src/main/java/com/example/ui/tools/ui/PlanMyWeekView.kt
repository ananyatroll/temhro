package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudentCalendarEvent
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import com.example.ui.tools.planner.WeeklyPlannerEngine
import com.example.ui.tools.planner.WeeklyScheduleResult
import java.util.UUID

@Composable
fun PlanMyWeekView(
    viewModel: StudyViewModel,
    onBackToCalendar: () -> Unit
) {
    val isDark by viewModel.isDarkTheme.collectAsState()
    val upcomingEvents by viewModel.calendarEvents.collectAsState()

    val subjectsList by viewModel.subjects.collectAsState()
    val availableSubjects = remember(subjectsList) {
        subjectsList.map { it.name }.distinct().filter { it.isNotBlank() }
    }
    var selectedSubjects by remember(availableSubjects) {
        mutableStateOf(availableSubjects.take(4).toSet())
    }
    var weakSubjects by remember(availableSubjects) {
        mutableStateOf(setOfNotNull(availableSubjects.firstOrNull()))
    }
    var dailyHours by remember { mutableStateOf(2.5f) }
    var preferredTime by remember { mutableStateOf("Evening") }

    var generatedPlan by remember { mutableStateOf<WeeklyScheduleResult?>(null) }
    var planSavedStatus by remember { mutableStateOf<String?>(null) }

    val currentEpochDay = remember { System.currentTimeMillis() / (1000 * 60 * 60 * 24) }

    Column(modifier = Modifier.fillMaxSize()) {

        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackToCalendar) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Calendar")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Plan My Week Engine",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) TextLight else Color(0xFF0F172A)
                )
            )
        }

        if (generatedPlan == null) {
            // Configuration Form
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Info Card
                Surface(
                    color = if (isDark) Color(0xFF1E293B) else Color(0xFFEFF6FF),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF3B82F6),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tamhero builds a balanced weekly schedule based on upcoming exams, your weak areas, and realistic study hours without burnout.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (isDark) TextLight else Color(0xFF1E3A8A),
                                lineHeight = 18.sp
                            )
                        )
                    }
                }

                // Daily Hours Slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Available Daily Study Time:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Text("${dailyHours}h / day", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                    }
                    Slider(
                        value = dailyHours,
                        onValueChange = { dailyHours = (Math.round(it * 2) / 2.0f) },
                        valueRange = 1.0f..6.0f,
                        steps = 9,
                        colors = SliderDefaults.colors(thumbColor = EmeraldPrimary, activeTrackColor = EmeraldPrimary)
                    )
                }

                // Preferred Study Time
                Column {
                    Text("Preferred Study Time:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Morning (06:00 - 08:30)", "Afternoon (14:00 - 17:00)", "Evening (19:00 - 22:00)").forEach { timeSlot ->
                            val label = timeSlot.substringBefore(" ")
                            val isSelected = preferredTime == label
                            FilterChip(
                                selected = isSelected,
                                onClick = { preferredTime = label },
                                label = { Text(timeSlot, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Included Subjects
                Column {
                    Text("Included Curriculum Subjects:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        availableSubjects.forEach { sub ->
                            val isSelected = selectedSubjects.contains(sub)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedSubjects = if (isSelected) {
                                        if (selectedSubjects.size > 1) selectedSubjects - sub else selectedSubjects
                                    } else {
                                        selectedSubjects + sub
                                    }
                                },
                                label = { Text(sub, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Weak Subjects (Prioritized)
                Column {
                    Text("Flag Weak Subjects (Prioritized):", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        availableSubjects.forEach { sub ->
                            val isWeak = weakSubjects.contains(sub)
                            FilterChip(
                                selected = isWeak,
                                onClick = {
                                    weakSubjects = if (isWeak) weakSubjects - sub else weakSubjects + sub
                                },
                                label = { Text(sub, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFEF4444),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        generatedPlan = WeeklyPlannerEngine.generatePlan(
                            subjects = selectedSubjects.toList(),
                            weakSubjects = weakSubjects,
                            dailyHours = dailyHours,
                            preferredTime = preferredTime,
                            upcomingEvents = upcomingEvents
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Generate My Study Blueprint", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            // Plan Results
            val plan = generatedPlan!!

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                // Summary Card
                item {
                    Surface(
                        color = if (isDark) CardBgDark else Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = plan.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary
                                    )
                                )
                                Text(
                                    text = "Total: ${String.format("%.1f", plan.totalWeeklyHours)} hrs",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = plan.summary,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (isDark) TextLight else Color(0xFF1E293B)
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        // Save generated plan blocks to calendar
                                        plan.days.forEachIndexed { dayIdx, daySched ->
                                            daySched.blocks.forEach { block ->
                                                val event = StudentCalendarEvent(
                                                    id = UUID.randomUUID().toString(),
                                                    title = "Study: ${block.subject} (${block.activityType})",
                                                    subject = block.subject,
                                                    eventType = "Study Session",
                                                    dateEpochDay = currentEpochDay + dayIdx,
                                                    timeString = if (preferredTime == "Morning") "07:00" else if (preferredTime == "Afternoon") "15:00" else "19:30",
                                                    description = block.note,
                                                    priority = "Medium"
                                                )
                                                viewModel.addCalendarEvent(event)
                                            }
                                        }
                                        planSavedStatus = "Added weekly study sessions to your Calendar!"
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                                ) {
                                    Icon(Icons.Default.CalendarMonth, null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Add to Calendar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(onClick = { generatedPlan = null }) {
                                    Text("Reconfigure", fontSize = 12.sp)
                                }
                            }

                            if (planSavedStatus != null) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = planSavedStatus!!,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = EmeraldPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                // Days
                items(plan.days) { day ->
                    Surface(
                        color = if (isDark) CardBgDark else Color.White,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = day.dayName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isDark) TextLight else Color(0xFF0F172A)
                                )
                                Text(
                                    text = "${day.totalMinutes} mins",
                                    fontSize = 12.sp,
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            day.blocks.forEach { block ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = EmeraldPrimary.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "${block.durationMinutes}m",
                                            color = EmeraldPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column {
                                        Text(
                                            text = "${block.subject} • ${block.activityType}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (isDark) TextLight else Color(0xFF0F172A)
                                        )
                                        if (block.note.isNotBlank()) {
                                            Text(
                                                text = block.note,
                                                fontSize = 11.sp,
                                                color = if (isDark) TextMuted else Color.Gray
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
