package com.example

import com.example.data.GradeAssessment
import com.example.data.GradeCourse
import com.example.data.StudentCalendarEvent
import com.example.ui.tools.ai.TamheroSmartEngine
import com.example.ui.tools.grades.GradeCalculator
import com.example.ui.tools.planner.WeeklyPlannerEngine
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testTamheroSmartEngineExplainsPhysicsAndMathOffline() = runBlocking {
    val engine = TamheroSmartEngine()

    // Test calculus
    val mathResponse = engine.explainConcept("derivative in calculus", "Mathematics")
    assertTrue(mathResponse.isNotBlank())
    assertTrue(mathResponse.contains("Calculus") || mathResponse.contains("derivative"))

    // Test physics
    val physicsResponse = engine.explainConcept("Newton laws of motion", "Physics")
    assertTrue(physicsResponse.isNotBlank())
    assertTrue(physicsResponse.contains("Newton") || physicsResponse.contains("Inertia"))

    // Test question generation
    val generatedQuestions = engine.generateQuestions("Newton second law states force equals mass times acceleration.", 3)
    assertEquals(3, generatedQuestions.size)
    assertTrue(generatedQuestions[0].optionA.isNotBlank())
    assertTrue(generatedQuestions[0].correctOption.isNotBlank())

    // Test flashcard generation
    val generatedCards = engine.generateFlashcards(
      "Mitochondria is the powerhouse of the cell producing ATP.\nRibosome is the site of cellular protein synthesis.",
      2
    )
    assertTrue(generatedCards.isNotEmpty())
    assertTrue(generatedCards[0].front.isNotBlank())
    assertTrue(generatedCards[0].back.isNotBlank())
  }

  @Test
  fun testGradeCalculatorCurrentAndRequiredScores() {
    val course = GradeCourse(id = "c1", courseName = "Physics I", creditHours = 3, targetGradePct = 90.0)
    val assessments = listOf(
      GradeAssessment(courseId = "c1", assessmentName = "Quiz 1", weightPercent = 10.0, score = 8.5, maxScore = 10.0), // 85%
      GradeAssessment(courseId = "c1", assessmentName = "Midterm", weightPercent = 30.0, score = 27.0, maxScore = 30.0) // 90%
    )

    val analysis = GradeCalculator.analyzeCourse(course, assessments)
    assertEquals(88.75, analysis.currentAveragePct, 0.1) // (8.5 + 27) / 40 * 100 = 88.75%
    assertEquals(60.0, analysis.remainingWeight, 0.01)
    // Target is 90 total out of 100. Currently has 35.5 out of 40. Needs 54.5 out of remaining 60 => 90.83%
    assertEquals(90.83, analysis.requiredScoreOnRemaining, 0.5)

    // Test GPA Calculation
    val courses = listOf(
      GradeCourse(id = "c1", courseName = "Physics", creditHours = 3, targetGradePct = 90.0),
      GradeCourse(id = "c2", courseName = "Calculus", creditHours = 4, targetGradePct = 85.0),
      GradeCourse(id = "c3", courseName = "English", creditHours = 2, targetGradePct = 95.0)
    )
    val allAssessments = listOf(
      GradeAssessment(courseId = "c1", assessmentName = "Exam", weightPercent = 100.0, score = 92.0, maxScore = 100.0),
      GradeAssessment(courseId = "c2", assessmentName = "Exam", weightPercent = 100.0, score = 85.0, maxScore = 100.0),
      GradeAssessment(courseId = "c3", assessmentName = "Exam", weightPercent = 100.0, score = 95.0, maxScore = 100.0)
    )
    val gpaResult = GradeCalculator.calculateSemesterGpa(courses, allAssessments)
    assertTrue(gpaResult.semesterGpa in 3.5..4.0)
  }

  @Test
  fun testWeeklyPlannerEngineScheduleGeneration() {
    val subjects = listOf("Mathematics", "Physics", "Chemistry")
    val plan = WeeklyPlannerEngine.generatePlan(
      subjects = subjects,
      weakSubjects = setOf("Physics"),
      dailyHours = 2.0f,
      preferredTime = "Evening (6 PM - 9 PM)",
      upcomingEvents = emptyList()
    )

    assertEquals(7, plan.days.size)
    plan.days.forEach { day ->
      assertTrue(day.blocks.isNotEmpty())
      day.blocks.forEach { block ->
        assertTrue(subjects.contains(block.subject))
      }
    }
  }
}
