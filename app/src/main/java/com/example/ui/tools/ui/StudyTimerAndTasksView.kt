package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudyTask
import com.example.ui.NotificationHelper
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.UUID

@Composable
fun StudyTimerAndTasksView(
    viewModel: StudyViewModel,
    defaultSubject: String
) {
    val context = LocalContext.current
    val isDark by viewModel.isDarkTheme.collectAsState()
    val tasks by viewModel.studyTasks.collectAsState()
    val goals by viewModel.studyGoals.collectAsState()

    var activeSubTab by remember { mutableStateOf("timer") } // "timer", "tasks"

    // Timer State
    var selectedSubject by remember { mutableStateOf(defaultSubject) }
    var totalSeconds by remember { mutableStateOf(25 * 60) }
    var remainingSeconds by remember { mutableStateOf(25 * 60) }
    var isTimerRunning by remember { mutableStateOf(false) }

    // Add Task Dialog
    var showAddTaskDialog by remember { mutableStateOf(false) }

    val currentEpochDay = remember { System.currentTimeMillis() / (1000 * 60 * 60 * 24) }

    // Ticking Effect
    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds -= 1
            if (remainingSeconds <= 0) {
                isTimerRunning = false
                val minutesCompleted = totalSeconds / 60
                viewModel.logCompletedStudySession(selectedSubject, minutesCompleted)
                NotificationHelper.sendTimerCompletedNotification(context, selectedSubject, minutesCompleted)
            }
        }
    }

    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val formattedTime = String.format(Locale.US, "%02d:%02d", minutes, seconds)
    val progress = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds.toFloat() else 0f

    Column(modifier = Modifier.fillMaxSize()) {

        // Sub-navigation: Focus Timer | Tasks & Goals
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilledTonalButton(
                onClick = { activeSubTab = "timer" },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (activeSubTab == "timer") EmeraldPrimary else if (isDark) CardBgDark else Color(0xFFF1F5F9),
                    contentColor = if (activeSubTab == "timer") Color.White else if (isDark) TextLight else Color.Black
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Timer, null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Focus Timer", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }

            FilledTonalButton(
                onClick = { activeSubTab = "tasks" },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (activeSubTab == "tasks") EmeraldPrimary else if (isDark) CardBgDark else Color(0xFFF1F5F9),
                    contentColor = if (activeSubTab == "tasks") Color.White else if (isDark) TextLight else Color.Black
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Checklist, null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Tasks & Goals (${tasks.count { !it.isCompleted }})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        if (activeSubTab == "timer") {
            // Focus Timer Layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Quick Duration Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.Center
                ) {
                    listOf(
                        "25m Pomodoro" to 25 * 60,
                        "50m Deep Work" to 50 * 60,
                        "15m Rapid Review" to 15 * 60
                    ).forEach { (label, secs) ->
                        val isSelected = totalSeconds == secs
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                isTimerRunning = false
                                totalSeconds = secs
                                remainingSeconds = secs
                            },
                            label = { Text(label, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldPrimary,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }

                // Subject tag selector
                val subjectsList by viewModel.subjects.collectAsState()
                val subjectChips = remember(subjectsList) {
                    subjectsList.map { it.name }.distinct().filter { it.isNotBlank() }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Subject:", fontSize = 12.sp, color = TextMuted)
                    subjectChips.forEach { sub ->
                        val isSelected = selectedSubject.equals(sub, true)
                        SuggestionChip(
                            onClick = { selectedSubject = sub },
                            label = { Text(sub, fontSize = 11.sp) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else Color.Transparent,
                                labelColor = if (isSelected) EmeraldPrimary else if (isDark) TextMuted else Color.DarkGray
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) EmeraldPrimary else if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)
                            )
                        )
                    }
                }

                // Circular Progress Dial
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        color = EmeraldPrimary,
                        strokeWidth = 10.dp,
                        trackColor = if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0),
                        strokeCap = StrokeCap.Round
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = formattedTime,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) TextLight else Color(0xFF0F172A)
                            )
                        )
                        Text(
                            text = selectedSubject,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = EmeraldPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                // Timer Controls: Play/Pause, Reset
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            isTimerRunning = false
                            remainingSeconds = totalSeconds
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF1E293B) else Color(0xFFF1F5F9))
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset Timer", tint = TextMuted)
                    }

                    Button(
                        onClick = { isTimerRunning = !isTimerRunning },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isTimerRunning) Color(0xFFEF4444) else EmeraldPrimary
                        ),
                        modifier = Modifier
                            .height(52.dp)
                            .widthIn(min = 160.dp),
                        shape = RoundedCornerShape(26.dp)
                    ) {
                        Icon(
                            if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isTimerRunning) "Pause Session" else "Start Deep Work",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                // Motivation Insight
                Surface(
                    color = if (isDark) CardBgDark else Color(0xFFF0FDF4),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Bolt, null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Studying for 25 minutes with full focus builds long-term neural retention. Completed minutes are logged automatically.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (isDark) TextLight else Color(0xFF0F172A),
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        } else {
            // Tasks & Goals Layout
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // Goals Summary Card
                item {
                    val primaryGoal = goals.firstOrNull()
                    if (primaryGoal != null) {
                        Surface(
                            color = if (isDark) CardBgDark else Color.White,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "🎯 ${primaryGoal.title}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isDark) TextLight else Color(0xFF0F172A)
                                    )
                                    Text(
                                        text = "${String.format(Locale.US, "%.1f", primaryGoal.currentHours)} / ${primaryGoal.targetHours} hrs",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = EmeraldPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                val goalProgress = (primaryGoal.currentHours / primaryGoal.targetHours).coerceIn(0f, 1f)
                                LinearProgressIndicator(
                                    progress = { goalProgress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = EmeraldPrimary,
                                    trackColor = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Practice Questions Goal: ${primaryGoal.currentQuestions} / ${primaryGoal.targetQuestions}",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }

                // Tasks Header & Add Button
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Study Checklist",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Button(
                            onClick = { showAddTaskDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("New Task", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Tasks List
                items(tasks, key = { it.id }) { task ->
                    val diff = task.dueDateEpochDay - currentEpochDay
                    val dueText = when {
                        diff < 0 -> "Overdue"
                        diff == 0L -> "Due Today"
                        else -> "Due in $diff days"
                    }

                    val priorityColor = when (task.priority.lowercase()) {
                        "high" -> Color(0xFFEF4444)
                        "medium" -> GoldAccent
                        else -> EmeraldPrimary
                    }

                    Surface(
                        color = if (isDark) CardBgDark else Color.White,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { checked ->
                                    viewModel.toggleTaskCompletion(task.id, checked)
                                },
                                colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = task.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (task.isCompleted) TextMuted else if (isDark) TextLight else Color(0xFF0F172A)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "${task.subject} • ${task.taskType}",
                                        fontSize = 11.sp,
                                        color = EmeraldPrimary
                                    )
                                    Text(
                                        text = "• $dueText",
                                        fontSize = 10.sp,
                                        color = if (diff <= 1 && !task.isCompleted) Color(0xFFEF4444) else TextMuted
                                    )
                                }
                            }

                            IconButton(
                                onClick = { viewModel.deleteTask(task.id) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = TextMuted, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Task Dialog
    if (showAddTaskDialog) {
        var taskTitle by remember { mutableStateOf("") }
        var taskSubject by remember { mutableStateOf(defaultSubject) }
        var taskType by remember { mutableStateOf("Homework") }
        var daysInFuture by remember { mutableStateOf(2) }
        var priority by remember { mutableStateOf("Medium") }

        AlertDialog(
            onDismissRequest = { showAddTaskDialog = false },
            title = { Text("Add Study Task", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text("Task Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = taskSubject,
                        onValueChange = { taskSubject = it },
                        label = { Text("Subject") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Task Type:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Homework", "Study", "Revision", "Practice Questions").forEach { type ->
                            FilterChip(
                                selected = taskType == type,
                                onClick = { taskType = type },
                                label = { Text(type, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text("Due Date: in $daysInFuture days", fontSize = 12.sp)
                    Slider(
                        value = daysInFuture.toFloat(),
                        onValueChange = { daysInFuture = it.toInt() },
                        valueRange = 1f..14f,
                        steps = 13,
                        colors = SliderDefaults.colors(thumbColor = EmeraldPrimary, activeTrackColor = EmeraldPrimary)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (taskTitle.isNotBlank()) {
                            val task = StudyTask(
                                id = UUID.randomUUID().toString(),
                                title = taskTitle.trim(),
                                subject = taskSubject.trim(),
                                taskType = taskType,
                                dueDateEpochDay = currentEpochDay + daysInFuture,
                                priority = priority
                            )
                            viewModel.addTask(task)
                            showAddTaskDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Save Task")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTaskDialog = false }) { Text("Cancel") }
            }
        )
    }
}
