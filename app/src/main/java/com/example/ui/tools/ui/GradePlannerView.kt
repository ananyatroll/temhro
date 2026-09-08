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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GradeAssessment
import com.example.data.GradeCourse
import com.example.ui.StudyViewModel
import com.example.ui.theme.*
import com.example.ui.tools.grades.GradeCalculator
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

@Composable
fun GradePlannerView(
    viewModel: StudyViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val isDark by viewModel.isDarkTheme.collectAsState()
    val courses by viewModel.gradeCourses.collectAsState()
    val assessments by viewModel.gradeAssessments.collectAsState()

    val activeLearningContext by viewModel.activeLearningContext.collectAsState()
    var selectedCourseId by remember(courses, activeLearningContext) {
        val ctxCourse = activeLearningContext?.courseName
        val matched = courses.firstOrNull { it.courseName.equals(ctxCourse, ignoreCase = true) }
        mutableStateOf(matched?.id ?: courses.firstOrNull()?.id ?: "")
    }

    var showAddCourseDialog by remember { mutableStateOf(false) }
    var showAddAssessmentDialog by remember { mutableStateOf(false) }

    // What-If slider value (hypothetical final exam score % from 0 to 100)
    var whatIfFinalScore by remember { mutableStateOf(85.0) }

    // Calculate Semester GPA
    val semesterGpaResult = remember(courses, assessments) {
        GradeCalculator.calculateSemesterGpa(courses, assessments)
    }

    val selectedCourse = courses.firstOrNull { it.id == selectedCourseId }
    val selectedCourseAssessments = assessments.filter { it.courseId == selectedCourseId }

    val courseAnalysis = remember(selectedCourse, selectedCourseAssessments) {
        if (selectedCourse != null) {
            GradeCalculator.analyzeCourse(selectedCourse, selectedCourseAssessments)
        } else {
            null
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Top Summary: Semester GPA & CGPA Banner
        Surface(
            color = if (isDark) CardBgDark else Color(0xFFF8FAFC),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Projected Semester GPA",
                        style = MaterialTheme.typography.labelSmall.copy(color = if (isDark) TextMuted else Color.Gray)
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = String.format(Locale.US, "%.2f", semesterGpaResult.semesterGpa),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimary
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "(${semesterGpaResult.overallLetter})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (isDark) TextLight else Color.DarkGray
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Total Credits",
                        style = MaterialTheme.typography.labelSmall.copy(color = if (isDark) TextMuted else Color.Gray)
                    )
                    Text(
                        text = "${semesterGpaResult.totalCredits} Cr. Hrs",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) TextLight else Color(0xFF0F172A)
                        )
                    )
                }
            }
        }

        // Course Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            courses.forEach { course ->
                val isSelected = course.id == selectedCourseId
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedCourseId = course.id
                        viewModel.updateActiveCourseInContext(course.courseName)
                    },
                    label = { Text("${course.courseName} (${course.creditHours}cr)", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }

            IconButton(
                onClick = { showAddCourseDialog = true },
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Course", modifier = Modifier.size(16.dp))
            }
        }

        // Course Details
        if (selectedCourse == null || courseAnalysis == null) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Please add a course to start planning your grades.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // At-Risk Subject Alert Banner
                if (courseAnalysis.requiredScoreOnRemaining > 85.0 || courseAnalysis.currentAveragePct < 70.0) {
                    item {
                        Surface(
                            color = if (isDark) Color(0xFF451A03) else Color(0xFFFFFBEB),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Priority Alert: ${selectedCourse.courseName}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (isDark) Color(0xFFFDE68A) else Color(0xFF92400E)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "You need ${String.format(Locale.US, "%.1f", courseAnalysis.requiredScoreOnRemaining)}% on remaining exams to secure your target ${GradeCalculator.getLetterGrade(selectedCourse.targetGradePct)}. Recommended: Schedule extra revision in Plan My Week.",
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp,
                                        color = if (isDark) Color(0xFFFEF3C7) else Color(0xFF78350F)
                                    )
                                }
                            }
                        }
                    }
                }

                // Course Grade Analytics Card
                item {
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedCourse.courseName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDark) TextLight else Color(0xFF0F172A)
                                    )
                                )

                                IconButton(
                                    onClick = { viewModel.deleteGradeCourse(selectedCourse.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.DeleteOutline, contentDescription = "Delete Course", tint = TextMuted)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Stats Grid: Current Avg, Target Grade, Required on Final
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Current Avg", fontSize = 11.sp, color = TextMuted)
                                    Text(
                                        text = "${String.format(Locale.US, "%.1f", courseAnalysis.currentAveragePct)}%",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDark) TextLight else Color(0xFF0F172A)
                                    )
                                }

                                Column {
                                    Text("Target Grade", fontSize = 11.sp, color = TextMuted)
                                    Text(
                                        text = "${String.format(Locale.US, "%.1f", selectedCourse.targetGradePct)}%",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary
                                    )
                                }

                                Column {
                                    Text("Req. on Final", fontSize = 11.sp, color = TextMuted)
                                    val req = courseAnalysis.requiredScoreOnRemaining
                                    val reqColor = when {
                                        req <= 0 -> EmeraldPrimary
                                        req <= 85 -> EmeraldPrimary
                                        req <= 100 -> GoldAccent
                                        else -> Color(0xFFEF4444)
                                    }
                                    Text(
                                        text = if (req <= 0) "Achieved!" else "${String.format(Locale.US, "%.1f", req)}%",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = reqColor
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Letter / GPA", fontSize = 11.sp, color = TextMuted)
                                    Text(
                                        text = "${courseAnalysis.projectedLetter} (${courseAnalysis.projectedGpaPoints})",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Weight Progress Bar
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Completed Weight: ${courseAnalysis.completedWeight.toInt()}%",
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                    Text(
                                        "Remaining: ${courseAnalysis.remainingWeight.toInt()}%",
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                LinearProgressIndicator(
                                    progress = { (courseAnalysis.completedWeight / 100.0).toFloat().coerceIn(0f, 1f) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = EmeraldPrimary,
                                    trackColor = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)
                                )
                            }
                        }
                    }
                }

                // "What-If" Scenario Simulator Card
                item {
                    val whatIfResult = remember(courseAnalysis, whatIfFinalScore) {
                        GradeCalculator.calculateWhatIf(
                            courseAnalysis.currentWeightedPoints,
                            courseAnalysis.remainingWeight,
                            whatIfFinalScore
                        )
                    }
                    val whatIfLetter = GradeCalculator.getLetterGrade(whatIfResult)
                    val whatIfGpa = GradeCalculator.getGpaPoints(whatIfLetter)

                    Surface(
                        color = if (isDark) CardBgDark else Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🔮 What-If Final Exam Simulator",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary
                                    )
                                )
                                Text(
                                    text = "Score: ${whatIfFinalScore.toInt()}%",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Adjust expected score on the remaining ${courseAnalysis.remainingWeight.toInt()}% weight:",
                                fontSize = 11.sp,
                                color = TextMuted
                            )

                            Slider(
                                value = whatIfFinalScore.toFloat(),
                                onValueChange = { whatIfFinalScore = it.toDouble() },
                                valueRange = 0f..100f,
                                steps = 99,
                                colors = SliderDefaults.colors(
                                    thumbColor = EmeraldPrimary,
                                    activeTrackColor = EmeraldPrimary
                                )
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Projected Course Grade:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${String.format(Locale.US, "%.1f", whatIfResult)}% ($whatIfLetter • $whatIfGpa GPA)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                            }
                        }
                    }
                }



                // Assessments Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Course Assessments (${selectedCourseAssessments.size})",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        TextButton(onClick = { showAddAssessmentDialog = true }) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Assessment", fontSize = 12.sp)
                        }
                    }
                }

                // Assessment Items
                items(selectedCourseAssessments, key = { it.id }) { assessment ->
                    Surface(
                        color = if (isDark) CardBgDark else Color.White,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = assessment.assessmentName,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDark) TextLight else Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = "${assessment.assessmentType} • Weight: ${assessment.weightPercent.toInt()}%",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${String.format(Locale.US, "%.1f", assessment.score)} / ${String.format(Locale.US, "%.1f", assessment.maxScore)}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = { viewModel.deleteGradeAssessment(assessment.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        Icons.Default.DeleteOutline,
                                        contentDescription = "Delete",
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

    // Add Course Dialog
    if (showAddCourseDialog) {
        var courseName by remember { mutableStateOf("") }
        var creditHours by remember { mutableStateOf("3") }
        var targetPct by remember { mutableStateOf("85") }

        AlertDialog(
            onDismissRequest = { showAddCourseDialog = false },
            title = { Text("Add Course", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = courseName,
                        onValueChange = { courseName = it },
                        label = { Text("Course Name (e.g. Mathematics)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = creditHours,
                        onValueChange = { creditHours = it },
                        label = { Text("Credit Hours (e.g. 3 or 4)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = targetPct,
                        onValueChange = { targetPct = it },
                        label = { Text("Target Grade % (e.g. 85)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (courseName.isNotBlank()) {
                            val newCourse = GradeCourse(
                                id = "course_" + UUID.randomUUID().toString().take(8),
                                courseName = courseName.trim(),
                                creditHours = creditHours.toIntOrNull() ?: 3,
                                targetGradePct = targetPct.toDoubleOrNull() ?: 85.0
                            )
                            viewModel.addGradeCourse(newCourse)
                            selectedCourseId = newCourse.id
                            showAddCourseDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Save Course")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCourseDialog = false }) { Text("Cancel") }
            }
        )
    }

    // Add Assessment Dialog
    if (showAddAssessmentDialog && selectedCourse != null) {
        var assessName by remember { mutableStateOf("") }
        var assessType by remember { mutableStateOf("Quiz") }
        var weight by remember { mutableStateOf("20") }
        var score by remember { mutableStateOf("18") }
        var maxScore by remember { mutableStateOf("20") }

        AlertDialog(
            onDismissRequest = { showAddAssessmentDialog = false },
            title = { Text("Add Assessment to ${selectedCourse.courseName}", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = assessName,
                        onValueChange = { assessName = it },
                        label = { Text("Assessment Name (e.g. Quiz 2, Midterm)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight Percentage % (e.g. 20)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = score,
                            onValueChange = { score = it },
                            label = { Text("Score") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = maxScore,
                            onValueChange = { maxScore = it },
                            label = { Text("Max Score") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (assessName.isNotBlank()) {
                            val assessment = GradeAssessment(
                                id = "assess_" + UUID.randomUUID().toString().take(8),
                                courseId = selectedCourse.id,
                                assessmentName = assessName.trim(),
                                assessmentType = assessType,
                                weightPercent = weight.toDoubleOrNull() ?: 20.0,
                                score = score.toDoubleOrNull() ?: 18.0,
                                maxScore = maxScore.toDoubleOrNull() ?: 20.0
                            )
                            viewModel.addGradeAssessment(assessment)
                            showAddAssessmentDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Add Assessment")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddAssessmentDialog = false }) { Text("Cancel") }
            }
        )
    }
}
