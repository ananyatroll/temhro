package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class StudentToolsRepository(private val dao: StudentToolsDao) {

    // Calendar
    val allCalendarEvents: Flow<List<StudentCalendarEvent>> = dao.getAllCalendarEvents()

    fun getUpcomingCalendarEvents(fromEpochDay: Long): Flow<List<StudentCalendarEvent>> =
        dao.getUpcomingCalendarEvents(fromEpochDay)

    suspend fun insertCalendarEvent(event: StudentCalendarEvent) = withContext(Dispatchers.IO) {
        dao.insertCalendarEvent(event)
    }

    suspend fun updateCalendarEventCompletion(id: String, completed: Boolean) = withContext(Dispatchers.IO) {
        dao.updateCalendarEventCompletion(id, completed)
    }

    suspend fun deleteCalendarEvent(id: String) = withContext(Dispatchers.IO) {
        dao.deleteCalendarEvent(id)
    }

    // Tasks
    val allTasks: Flow<List<StudyTask>> = dao.getAllTasks()

    suspend fun insertTask(task: StudyTask) = withContext(Dispatchers.IO) {
        dao.insertTask(task)
    }

    suspend fun updateTaskCompletion(id: String, completed: Boolean) = withContext(Dispatchers.IO) {
        dao.updateTaskCompletion(id, completed)
    }

    suspend fun deleteTask(id: String) = withContext(Dispatchers.IO) {
        dao.deleteTask(id)
    }

    // Goals
    val allGoals: Flow<List<StudyGoal>> = dao.getAllGoals()

    suspend fun insertGoal(goal: StudyGoal) = withContext(Dispatchers.IO) {
        dao.insertGoal(goal)
    }

    suspend fun updateGoalProgress(id: String, hours: Float, questions: Int) = withContext(Dispatchers.IO) {
        dao.updateGoalProgress(id, hours, questions)
    }

    suspend fun deleteGoal(id: String) = withContext(Dispatchers.IO) {
        dao.deleteGoal(id)
    }

    // Grade Planner
    val allGradeCourses: Flow<List<GradeCourse>> = dao.getAllGradeCourses()
    val allAssessments: Flow<List<GradeAssessment>> = dao.getAllAssessments()

    fun getAssessmentsForCourse(courseId: String): Flow<List<GradeAssessment>> =
        dao.getAssessmentsForCourse(courseId)

    suspend fun insertGradeCourse(course: GradeCourse) = withContext(Dispatchers.IO) {
        dao.insertGradeCourse(course)
    }

    suspend fun deleteGradeCourse(courseId: String) = withContext(Dispatchers.IO) {
        dao.deleteAssessmentsForCourse(courseId)
        dao.deleteGradeCourse(courseId)
    }

    suspend fun insertAssessment(assessment: GradeAssessment) = withContext(Dispatchers.IO) {
        dao.insertAssessment(assessment)
    }

    suspend fun deleteAssessment(assessmentId: String) = withContext(Dispatchers.IO) {
        dao.deleteAssessment(assessmentId)
    }

    // Study Sessions
    val allStudySessions: Flow<List<StudySessionLog>> = dao.getAllStudySessions()

    suspend fun logStudySession(subject: String, minutes: Int) = withContext(Dispatchers.IO) {
        dao.insertStudySession(StudySessionLog(subject = subject, durationMinutes = minutes))
    }

    // Scanned Documents
    val allScannedDocuments: Flow<List<ScannedDocument>> = dao.getAllScannedDocuments()

    suspend fun saveScannedDocument(title: String, pageCount: Int, text: String) = withContext(Dispatchers.IO) {
        dao.insertScannedDocument(ScannedDocument(title = title, pageCount = pageCount, extractedText = text))
    }

    suspend fun deleteScannedDocument(id: String) = withContext(Dispatchers.IO) {
        dao.deleteScannedDocument(id)
    }

    // Initial Seed for immediate usefulness
    suspend fun seedInitialDataIfEmpty(currentEpochDay: Long) = withContext(Dispatchers.IO) {
        // Pre-seed sample courses, upcoming exam dates, tasks, and goals so student can immediately interact
        val seedCourses = listOf(
            GradeCourse(id = "course_math", courseName = "Mathematics", creditHours = 4, targetGradePct = 85.0),
            GradeCourse(id = "course_physics", courseName = "Physics", creditHours = 4, targetGradePct = 80.0),
            GradeCourse(id = "course_chemistry", courseName = "Chemistry", creditHours = 3, targetGradePct = 82.0),
            GradeCourse(id = "course_biology", courseName = "Biology", creditHours = 3, targetGradePct = 88.0)
        )
        dao.insertGradeCourses(seedCourses)

        val seedAssessments = listOf(
            GradeAssessment(id = "math_a1", courseId = "course_math", assessmentName = "Quiz 1 (Calculus)", assessmentType = "Quiz", weightPercent = 15.0, score = 13.5, maxScore = 15.0),
            GradeAssessment(id = "math_a2", courseId = "course_math", assessmentName = "Midterm Exam", assessmentType = "Midterm", weightPercent = 30.0, score = 25.0, maxScore = 30.0),
            GradeAssessment(id = "math_a3", courseId = "course_math", assessmentName = "Assignment 1", assessmentType = "Assignment", weightPercent = 15.0, score = 14.0, maxScore = 15.0),

            GradeAssessment(id = "phys_a1", courseId = "course_physics", assessmentName = "Quiz 1 (Vectors)", assessmentType = "Quiz", weightPercent = 15.0, score = 11.0, maxScore = 15.0),
            GradeAssessment(id = "phys_a2", courseId = "course_physics", assessmentName = "Midterm Exam", assessmentType = "Midterm", weightPercent = 30.0, score = 22.5, maxScore = 30.0),

            GradeAssessment(id = "chem_a1", courseId = "course_chemistry", assessmentName = "Lab Report 1", assessmentType = "Assignment", weightPercent = 20.0, score = 17.5, maxScore = 20.0),
            GradeAssessment(id = "chem_a2", courseId = "course_chemistry", assessmentName = "Midterm Exam", assessmentType = "Midterm", weightPercent = 30.0, score = 26.0, maxScore = 30.0)
        )
        dao.insertAssessments(seedAssessments)

        val seedEvents = listOf(
            StudentCalendarEvent(
                id = "evt_math_exam",
                title = "Mathematics Unit Assessment",
                subject = "Mathematics",
                eventType = "Exam",
                dateEpochDay = currentEpochDay + 3,
                timeString = "09:30",
                description = "Differential Calculus & Coordinate Geometry",
                priority = "High"
            ),
            StudentCalendarEvent(
                id = "evt_phys_test",
                title = "Physics Mid-Semester Test",
                subject = "Physics",
                eventType = "Test",
                dateEpochDay = currentEpochDay + 6,
                timeString = "14:00",
                description = "Mechanics, Rotational Dynamics & Energy",
                priority = "High"
            ),
            StudentCalendarEvent(
                id = "evt_chem_assign",
                title = "Chemistry Lab Assignment",
                subject = "Chemistry",
                eventType = "Assignment",
                dateEpochDay = currentEpochDay + 9,
                timeString = "11:00",
                description = "Chemical Bonding & Stoichiometry problem set",
                priority = "Medium"
            )
        )
        dao.insertCalendarEvents(seedEvents)

        val seedTasks = listOf(
            StudyTask(
                id = "task_math_hw",
                title = "Practice 25 Calculus Derivative Questions",
                subject = "Mathematics",
                taskType = "Homework",
                dueDateEpochDay = currentEpochDay + 2,
                priority = "High",
                isCompleted = false
            ),
            StudyTask(
                id = "task_phys_rev",
                title = "Review Newton's Laws & Friction Formulas",
                subject = "Physics",
                taskType = "Revision",
                dueDateEpochDay = currentEpochDay + 4,
                priority = "Medium",
                isCompleted = false
            ),
            StudyTask(
                id = "task_bio_fc",
                title = "Master Unit 2 Biology Flashcards",
                subject = "Biology",
                taskType = "Study",
                dueDateEpochDay = currentEpochDay + 5,
                priority = "Low",
                isCompleted = true
            )
        )
        dao.insertTasks(seedTasks)

        val seedGoals = listOf(
            StudyGoal(
                id = "goal_week_hours",
                title = "Weekly Study Target: 7 Hours",
                targetHours = 7.0f,
                currentHours = 3.5f,
                targetQuestions = 100,
                currentQuestions = 45,
                period = "Weekly"
            ),
            StudyGoal(
                id = "goal_practice_mcq",
                title = "Complete 100 Practice Questions",
                targetHours = 5.0f,
                currentHours = 2.0f,
                targetQuestions = 100,
                currentQuestions = 50,
                period = "Weekly"
            )
        )
        dao.insertGoals(seedGoals)
    }
}
