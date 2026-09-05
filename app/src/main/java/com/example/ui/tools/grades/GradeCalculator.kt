package com.example.ui.tools.grades

import com.example.data.GradeAssessment
import com.example.data.GradeCourse

data class CourseGradeAnalysis(
    val course: GradeCourse,
    val completedWeight: Double,
    val remainingWeight: Double,
    val currentWeightedPoints: Double,
    val currentAveragePct: Double,
    val requiredScoreOnRemaining: Double, // % needed on remaining weight to hit target
    val projectedLetter: String,
    val projectedGpaPoints: Double
)

data class SemesterGpaResult(
    val totalCredits: Int,
    val semesterGpa: Double,
    val overallLetter: String,
    val courseAnalyses: List<CourseGradeAnalysis>
)

object GradeCalculator {

    fun analyzeCourse(course: GradeCourse, assessments: List<GradeAssessment>): CourseGradeAnalysis {
        var completedWeight = 0.0
        var weightedPoints = 0.0

        assessments.forEach { a ->
            if (a.maxScore > 0) {
                val ratio = (a.score / a.maxScore).coerceIn(0.0, 1.0)
                completedWeight += a.weightPercent
                weightedPoints += ratio * a.weightPercent
            }
        }

        val currentAveragePct = if (completedWeight > 0) {
            (weightedPoints / completedWeight) * 100.0
        } else {
            0.0
        }

        val remainingWeight = (100.0 - completedWeight).coerceAtLeast(0.0)

        // Required score on remaining assessments to achieve targetGradePct:
        // (weightedPoints + remainingWeight * (req / 100)) = targetGradePct
        // remainingWeight * (req / 100) = targetGradePct - weightedPoints
        // req = (targetGradePct - weightedPoints) / remainingWeight * 100
        val requiredScoreOnRemaining = if (remainingWeight > 0) {
            val req = ((course.targetGradePct - weightedPoints) / remainingWeight) * 100.0
            req
        } else {
            0.0
        }

        val projectedOverall = if (remainingWeight > 0) {
            // Assume student performs at their current average on the remaining portion
            weightedPoints + (remainingWeight * (currentAveragePct / 100.0))
        } else {
            weightedPoints
        }

        val letter = getLetterGrade(projectedOverall)
        val gpaPoints = getGpaPoints(letter)

        return CourseGradeAnalysis(
            course = course,
            completedWeight = completedWeight,
            remainingWeight = remainingWeight,
            currentWeightedPoints = weightedPoints,
            currentAveragePct = currentAveragePct,
            requiredScoreOnRemaining = requiredScoreOnRemaining,
            projectedLetter = letter,
            projectedGpaPoints = gpaPoints
        )
    }

    fun calculateWhatIf(
        currentWeightedPoints: Double,
        remainingWeight: Double,
        hypotheticalFinalScorePct: Double
    ): Double {
        val additionalPoints = remainingWeight * (hypotheticalFinalScorePct / 100.0)
        return (currentWeightedPoints + additionalPoints).coerceIn(0.0, 100.0)
    }

    fun calculateSemesterGpa(
        courses: List<GradeCourse>,
        allAssessments: List<GradeAssessment>
    ): SemesterGpaResult {
        val analyses = courses.map { course ->
            val courseAssessments = allAssessments.filter { it.courseId == course.id }
            analyzeCourse(course, courseAssessments)
        }

        var totalCredits = 0
        var totalQualityPoints = 0.0

        analyses.forEach { analysis ->
            val credits = analysis.course.creditHours.coerceAtLeast(1)
            totalCredits += credits
            totalQualityPoints += analysis.projectedGpaPoints * credits
        }

        val gpa = if (totalCredits > 0) totalQualityPoints / totalCredits else 0.0
        val overallLetter = getLetterGradeFromGpa(gpa)

        return SemesterGpaResult(
            totalCredits = totalCredits,
            semesterGpa = gpa,
            overallLetter = overallLetter,
            courseAnalyses = analyses
        )
    }

    fun getLetterGrade(pct: Double): String = when {
        pct >= 90.0 -> "A+"
        pct >= 85.0 -> "A"
        pct >= 80.0 -> "A-"
        pct >= 75.0 -> "B+"
        pct >= 70.0 -> "B"
        pct >= 65.0 -> "B-"
        pct >= 60.0 -> "C+"
        pct >= 50.0 -> "C"
        pct >= 40.0 -> "D"
        else -> "F"
    }

    fun getGpaPoints(letter: String): Double = when (letter) {
        "A+", "A" -> 4.0
        "A-" -> 3.75
        "B+" -> 3.5
        "B" -> 3.0
        "B-" -> 2.75
        "C+" -> 2.5
        "C" -> 2.0
        "D" -> 1.0
        else -> 0.0
    }

    fun getLetterGradeFromGpa(gpa: Double): String = when {
        gpa >= 3.85 -> "A"
        gpa >= 3.5 -> "B+"
        gpa >= 3.0 -> "B"
        gpa >= 2.5 -> "C+"
        gpa >= 2.0 -> "C"
        gpa >= 1.0 -> "D"
        else -> "F"
    }
}
