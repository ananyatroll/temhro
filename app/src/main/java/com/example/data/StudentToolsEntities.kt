package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "student_calendar_events")
data class StudentCalendarEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val subject: String, // e.g., "Mathematics", "Physics", "General"
    val eventType: String, // "Exam", "Test", "Assignment", "Homework", "Project", "Presentation", "Study Session", "Deadline"
    val dateEpochDay: Long, // Epoch day (days since 1970-01-01)
    val timeString: String = "09:00",
    val description: String = "",
    val priority: String = "High", // "High", "Medium", "Normal"
    val reminderMinutesBefore: Int = 60,
    val isCompleted: Boolean = false
)

@Entity(tableName = "study_tasks")
data class StudyTask(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val subject: String,
    val taskType: String = "Study", // "Homework", "Study", "Revision", "Assignment"
    val dueDateEpochDay: Long,
    val priority: String = "Medium", // "High", "Medium", "Low"
    val isCompleted: Boolean = false
)

@Entity(tableName = "study_goals")
data class StudyGoal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val targetHours: Float = 7.0f,
    val currentHours: Float = 0.0f,
    val targetQuestions: Int = 50,
    val currentQuestions: Int = 0,
    val period: String = "Weekly" // "Weekly", "Daily"
)

@Entity(tableName = "grade_courses")
data class GradeCourse(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val courseName: String,
    val creditHours: Int = 3,
    val targetGradePct: Double = 85.0
)

@Entity(tableName = "grade_assessments")
data class GradeAssessment(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val courseId: String,
    val assessmentName: String,
    val assessmentType: String = "Quiz", // "Quiz", "Midterm", "Assignment", "Project", "Final"
    val weightPercent: Double = 20.0,
    val score: Double = 17.0,
    val maxScore: Double = 20.0
)

@Entity(tableName = "study_session_logs")
data class StudySessionLog(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val subject: String,
    val durationMinutes: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "scanned_documents")
data class ScannedDocument(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val pageCount: Int = 1,
    val extractedText: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
