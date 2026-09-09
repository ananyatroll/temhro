package com.example

import com.example.data.Flashcard
import com.example.data.GradeAssessment
import com.example.data.GradeCourse
import com.example.ui.TranslationManager
import com.example.ui.screens.OfficialTextbookRegistry
import com.example.ui.tools.grades.GradeCalculator
import com.example.ui.tools.planner.WeeklyPlannerEngine
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testOfficialTextbookRegistryLoadsCurriculumEditions() {
    val biologyTextbooks = OfficialTextbookRegistry.getTextbooksForSubject("Biology")
    assertTrue(biologyTextbooks.isNotEmpty())
    val g12Bio = biologyTextbooks.find { it.grade == "Grade 12" }
    assertNotNull(g12Bio)
    assertEquals("Grade_12_Biology.pdf", g12Bio?.fileName)
    assertEquals(358, g12Bio?.pageCount)
    assertTrue((g12Bio?.units?.size ?: 0) >= 5)

    val chemistryTextbooks = OfficialTextbookRegistry.getTextbooksForSubject("Chemistry")
    assertTrue(chemistryTextbooks.isNotEmpty())
  }

  @Test
  fun testFlashcardUnitExtraction() {
    val unitPattern = Regex("""(?:Unit|Chapter)\s+\d+(?:\s*:\s*[^,\)\]]+)?""", RegexOption.IGNORE_CASE)

    val cardFront = "What is a function? (Grade 12 Mathematics, Unit 1: Sequences and Series)"
    val matchFront = unitPattern.find(cardFront)
    assertNotNull(matchFront)
    assertEquals("Unit 1: Sequences and Series", matchFront?.value?.trim())

    val cardBack = "A function assigns one output to each input. [Chapter 2: Matrices]"
    val matchBack = unitPattern.find(cardBack)
    assertNotNull(matchBack)
    assertEquals("Chapter 2: Matrices", matchBack?.value?.trim())
  }

  @Test
  fun testTranslationManagerHasSupportedLanguages() {
    val englishTitle = TranslationManager.get("student_tools_title", "en")
    val amharicTitle = TranslationManager.get("student_tools_title", "am")
    assertEquals("Student Tools", englishTitle)
    assertTrue(amharicTitle.isNotBlank())
    assertNotEquals("Student Tools", amharicTitle)
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
