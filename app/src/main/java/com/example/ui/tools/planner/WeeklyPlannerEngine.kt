package com.example.ui.tools.planner

import com.example.data.StudentCalendarEvent
import java.util.Locale

data class StudyBlock(
    val subject: String,
    val durationMinutes: Int,
    val activityType: String, // "Concept Review", "Problem Solving", "Practice MCQs", "Flashcards Mastery"
    val note: String = ""
)

data class DaySchedule(
    val dayName: String, // "Monday", "Tuesday", etc.
    val totalMinutes: Int,
    val blocks: List<StudyBlock>
)

data class WeeklyScheduleResult(
    val title: String,
    val summary: String,
    val totalWeeklyHours: Float,
    val days: List<DaySchedule>
)

object WeeklyPlannerEngine {

    val DAYS_OF_WEEK = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    fun generatePlan(
        subjects: List<String>,
        weakSubjects: Set<String>,
        dailyHours: Float,
        preferredTime: String,
        upcomingEvents: List<StudentCalendarEvent>
    ): WeeklyScheduleResult {
        val safeSubjects = if (subjects.isNotEmpty()) subjects else listOf("Mathematics", "Physics", "Chemistry", "English")
        val dailyMinutes = (dailyHours * 60).toInt().coerceIn(60, 360)

        // Identify upcoming high-priority subjects from exams in next 7 days
        val examSubjects = upcomingEvents
            .filter { it.eventType.equals("Exam", ignoreCase = true) || it.eventType.equals("Test", ignoreCase = true) }
            .map { it.subject }
            .toSet()

        // Prioritize: (1) Exam subjects, (2) Weak subjects, (3) Other subjects
        val prioritizedList = safeSubjects.sortedWith(
            compareByDescending<String> { examSubjects.contains(it) }
                .thenByDescending { weakSubjects.contains(it) }
        )

        val daysSchedule = mutableListOf<DaySchedule>()

        DAYS_OF_WEEK.forEachIndexed { dayIdx, dayName ->
            val isWeekend = dayIdx >= 5
            val effectiveDailyMinutes = if (isWeekend) (dailyMinutes * 0.8f).toInt() else dailyMinutes

            val blocks = mutableListOf<StudyBlock>()
            var remainingMin = effectiveDailyMinutes

            // Select 2 or 3 distinct subjects for the day to ensure variety
            val primarySub = prioritizedList[dayIdx % prioritizedList.size]
            val secondarySub = prioritizedList[(dayIdx + 1) % prioritizedList.size]

            val isWeakOrExam = examSubjects.contains(primarySub) || weakSubjects.contains(primarySub)
            val primaryMin = if (isWeakOrExam) (remainingMin * 0.55f).toInt() else (remainingMin * 0.45f).toInt()
            remainingMin -= primaryMin

            blocks.add(
                StudyBlock(
                    subject = primarySub,
                    durationMinutes = primaryMin,
                    activityType = if (examSubjects.contains(primarySub)) "High-Yield Exam Prep" else "Concept Mastery & Problem Solving",
                    note = if (weakSubjects.contains(primarySub)) "Priority Focus (Identified Weak Subject)" else "Active Recall"
                )
            )

            if (remainingMin >= 30) {
                val secMin = (remainingMin * 0.65f).toInt()
                remainingMin -= secMin
                blocks.add(
                    StudyBlock(
                        subject = secondarySub,
                        durationMinutes = secMin,
                        activityType = "Lesson Review & Flashcards",
                        note = "Spaced Repetition"
                    )
                )
            }

            // Always add a dedicated 20-30 min Practice Question & Exam Arena block
            val practiceMin = remainingMin.coerceAtLeast(20)
            blocks.add(
                StudyBlock(
                    subject = primarySub,
                    durationMinutes = practiceMin,
                    activityType = "Tamhero Practice Questions",
                    note = "Simulate Exam Speed"
                )
            )

            daysSchedule.add(
                DaySchedule(
                    dayName = dayName,
                    totalMinutes = blocks.sumOf { it.durationMinutes },
                    blocks = blocks
                )
            )
        }

        val totalHours = daysSchedule.sumOf { it.totalMinutes } / 60.0f

        val summary = buildString {
            append("Tailored for **$preferredTime** study sessions. ")
            if (examSubjects.isNotEmpty()) {
                append("Prioritizes upcoming exams in **${examSubjects.joinToString(", ")}**. ")
            }
            if (weakSubjects.isNotEmpty()) {
                append("Includes intensified practice blocks for weak areas: **${weakSubjects.joinToString(", ")}**.")
            }
        }

        return WeeklyScheduleResult(
            title = "Personalized Weekly Academic Blueprint",
            summary = summary,
            totalWeeklyHours = totalHours,
            days = daysSchedule
        )
    }
}
