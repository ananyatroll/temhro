package com.example.ui.tools.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StudentCalendarEvent
import com.example.ui.NotificationHelper
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AcademicCalendarView(
    viewModel: StudyViewModel,
    onSwitchToPlanWeek: () -> Unit
) {
    val context = LocalContext.current
    val isDark by viewModel.isDarkTheme.collectAsState()
    val events by viewModel.calendarEvents.collectAsState()

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showAddDialog by remember { mutableStateOf(false) }

    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7 // 0=Sunday, 6=Saturday
    
    val selectedEpochDay = selectedDate.toEpochDay()
    val selectedDayEvents = events.filter { it.dateEpochDay == selectedEpochDay }

    Column(modifier = Modifier.fillMaxSize().background(if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9))) {
        // Header Controls
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

        // Calendar Grid Card
        Surface(
            color = if (isDark) CardBgDark else Color.White,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Month Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                        Icon(Icons.Rounded.ChevronLeft, contentDescription = "Previous Month")
                    }
                    Text(
                        text = currentMonth.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color.White else Color.Black
                    )
                    IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                        Icon(Icons.Rounded.ChevronRight, contentDescription = "Next Month")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Days of week header
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                        Text(
                            text = day,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Calendar Grid
                val totalCells = daysInMonth + firstDayOfWeek
                val rows = Math.ceil(totalCells / 7.0).toInt()
                
                for (row in 0 until rows) {
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceAround) {
                        for (col in 0 until 7) {
                            val cellIndex = row * 7 + col
                            val dayOfMonth = cellIndex - firstDayOfWeek + 1
                            
                            if (dayOfMonth in 1..daysInMonth) {
                                val date = currentMonth.atDay(dayOfMonth)
                                val isSelected = date == selectedDate
                                val isToday = date == LocalDate.now()
                                val dayEvents = events.filter { it.dateEpochDay == date.toEpochDay() }
                                
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isSelected -> EmeraldPrimary
                                                isToday -> EmeraldPrimary.copy(alpha = 0.2f)
                                                else -> Color.Transparent
                                            }
                                        )
                                        .clickable { selectedDate = date }
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = dayOfMonth.toString(),
                                            color = if (isSelected) Color.White else if (isDark) Color.White else Color.Black,
                                            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 14.sp
                                        )
                                        if (dayEvents.isNotEmpty()) {
                                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                dayEvents.take(3).forEach { ev ->
                                                    val dotColor = when (ev.eventType.lowercase()) {
                                                        "exam", "test" -> Color(0xFFEF4444)
                                                        "assignment", "project" -> GoldAccent
                                                        else -> HolographicAqua
                                                    }
                                                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(if(isSelected) Color.White else dotColor))
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                            }
                        }
                    }
                }
            }
        }

        // Agenda View
        Text(
            text = "Schedule for $selectedDate",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color.LightGray else Color.DarkGray,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        if (selectedDayEvents.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                Text("No events for this day.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(selectedDayEvents, key = { it.id }) { event ->
                    val colorAccent = when (event.eventType.lowercase()) {
                        "exam", "test" -> Color(0xFFEF4444)
                        "assignment", "project" -> GoldAccent
                        else -> HolographicAqua
                    }
                    
                    Surface(
                        color = if (isDark) CardBgDark else Color.White,
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(colorAccent)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = event.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) Color.White else Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Class, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(event.subject, fontSize = 12.sp, color = Color.Gray)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(modifier = Modifier.background(colorAccent.copy(alpha=0.1f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text(event.eventType, fontSize = 10.sp, color = colorAccent, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            IconButton(onClick = { viewModel.deleteCalendarEvent(event.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        // Just reuse the existing add logic or show a stub since we are replacing the file. 
        // We will put a minimal add dialog here.
        var newTitle by remember { mutableStateOf("") }
        var newSubject by remember { mutableStateOf("General") }
        var newType by remember { mutableStateOf("Homework") }
        
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Event") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Event Title") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newTitle.isNotBlank()) {
                        val ev = StudentCalendarEvent(
                            title = newTitle,
                            subject = newSubject,
                            eventType = newType,
                            dateEpochDay = selectedEpochDay
                        )
                        viewModel.addCalendarEvent(ev)
                        showAddDialog = false
                    }
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
            }
        )
    }
}
