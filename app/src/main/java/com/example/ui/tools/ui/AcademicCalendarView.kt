package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudentCalendarEvent
import com.example.ui.NotificationHelper
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import java.util.UUID

@Composable
fun AcademicCalendarView(
    viewModel: StudyViewModel,
    onSwitchToPlanWeek: () -> Unit
) {
    val context = LocalContext.current
    val isDark by viewModel.isDarkTheme.collectAsState()
    val events by viewModel.calendarEvents.collectAsState()

    val currentEpochDay = remember { System.currentTimeMillis() / (1000 * 60 * 60 * 24) }

    var selectedFilter by remember { mutableStateOf("All") }
    var showAddDialog by remember { mutableStateOf(false) }

    val filterOptions = listOf("All", "Exam", "Test", "Assignment", "Homework", "Project")

    val filteredEvents = remember(events, selectedFilter) {
        if (selectedFilter == "All") {
            events
        } else {
            events.filter { it.eventType.equals(selectedFilter, ignoreCase = true) }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Top Controls: Switch to Plan My Week + Add Event
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = onSwitchToPlanWeek,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isDark) IndigoMedium else IndigoLight
                )
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Plan My Week", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Add Event", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Filter chips row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterOptions.forEach { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Events List
        if (filteredEvents.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.EventAvailable,
                        contentDescription = null,
                        tint = if (isDark) TextMuted else Color.LightGray,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No academic events found",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextMuted)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(filteredEvents, key = { it.id }) { event ->
                    val diffDays = event.dateEpochDay - currentEpochDay
                    val countdownText = when {
                        diffDays < 0 -> "Passed"
                        diffDays == 0L -> "Today!"
                        diffDays == 1L -> "Tomorrow"
                        else -> "$diffDays days remaining"
                    }

                    val badgeColor = when {
                        event.eventType.equals("Exam", true) -> Color(0xFFEF4444) // Red
                        event.eventType.equals("Test", true) -> GoldAccent // Amber
                        event.eventType.equals("Assignment", true) -> Color(0xFF3B82F6) // Blue
                        else -> EmeraldPrimary
                    }

                    Surface(
                        color = if (isDark) CardBgDark else Color.White,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)
                        ),
                        tonalElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Checkbox to toggle completion
                            Checkbox(
                                checked = event.isCompleted,
                                onCheckedChange = { checked ->
                                    viewModel.toggleCalendarEventCompletion(event.id, checked)
                                },
                                colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(
                                        color = badgeColor.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = event.eventType.uppercase(),
                                            color = badgeColor,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Surface(
                                        color = if (diffDays <= 3 && !event.isCompleted) Color(0xFFFEE2E2) else Color(0xFFF3F4F6),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = countdownText,
                                            color = if (diffDays <= 3 && !event.isCompleted) Color(0xFFB91C1C) else Color.DarkGray,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 10.sp
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDark) TextLight else Color(0xFF0F172A)
                                    )
                                )

                                if (event.description.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = event.description,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = if (isDark) TextMuted else Color.Gray
                                        ),
                                        maxLines = 2
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${event.subject} • ${event.timeString}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = EmeraldPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }

                            // Notification reminder button & Delete
                            Column(horizontalAlignment = Alignment.End) {
                                IconButton(
                                    onClick = {
                                        NotificationHelper.sendEventReminderNotification(
                                            context = context,
                                            eventTitle = event.title,
                                            subject = event.subject,
                                            timeRemaining = countdownText
                                        )
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        Icons.Default.NotificationsActive,
                                        contentDescription = "Trigger Reminder",
                                        tint = GoldAccent,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.deleteCalendarEvent(event.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        Icons.Default.DeleteOutline,
                                        contentDescription = "Delete Event",
                                        tint = TextMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Event Dialog
    if (showAddDialog) {
        val currentCourse = viewModel.activeSubject.collectAsState().value?.name
            ?: viewModel.subjects.collectAsState().value.firstOrNull()?.name
            ?: "General"
        var newTitle by remember { mutableStateOf("") }
        var newSubject by remember(currentCourse) { mutableStateOf(currentCourse) }
        var newType by remember { mutableStateOf("Exam") }
        var daysInFuture by remember { mutableStateOf(3) }
        var newTime by remember { mutableStateOf("09:00") }
        var newDescription by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Academic Event", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Event Title (e.g. Unit 3 Exam)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newSubject,
                        onValueChange = { newSubject = it },
                        label = { Text("Subject") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Event Type Selector
                    Text("Event Type:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Exam", "Test", "Assignment", "Homework", "Project", "Study Session").forEach { t ->
                            FilterChip(
                                selected = newType == t,
                                onClick = { newType = t },
                                label = { Text(t, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    // Days from today
                    Text("Due Date: $daysInFuture days from today", fontSize = 12.sp)
                    Slider(
                        value = daysInFuture.toFloat(),
                        onValueChange = { daysInFuture = it.toInt() },
                        valueRange = 0f..30f,
                        steps = 29,
                        colors = SliderDefaults.colors(thumbColor = EmeraldPrimary, activeTrackColor = EmeraldPrimary)
                    )

                    OutlinedTextField(
                        value = newTime,
                        onValueChange = { newTime = it },
                        label = { Text("Time (e.g. 09:30 AM)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newDescription,
                        onValueChange = { newDescription = it },
                        label = { Text("Description (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            val event = StudentCalendarEvent(
                                id = UUID.randomUUID().toString(),
                                title = newTitle.trim(),
                                subject = newSubject.trim(),
                                eventType = newType,
                                dateEpochDay = currentEpochDay + daysInFuture,
                                timeString = newTime.trim(),
                                description = newDescription.trim(),
                                priority = "High"
                            )
                            viewModel.addCalendarEvent(event)
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Save Event", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
