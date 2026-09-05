package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentToolsDao {

    // Calendar Events
    @Query("SELECT * FROM student_calendar_events ORDER BY dateEpochDay ASC, timeString ASC")
    fun getAllCalendarEvents(): Flow<List<StudentCalendarEvent>>

    @Query("SELECT * FROM student_calendar_events WHERE dateEpochDay >= :fromEpochDay ORDER BY dateEpochDay ASC")
    fun getUpcomingCalendarEvents(fromEpochDay: Long): Flow<List<StudentCalendarEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarEvent(event: StudentCalendarEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarEvents(events: List<StudentCalendarEvent>)

    @Query("UPDATE student_calendar_events SET isCompleted = :completed WHERE id = :id")
    suspend fun updateCalendarEventCompletion(id: String, completed: Boolean)

    @Query("DELETE FROM student_calendar_events WHERE id = :id")
    suspend fun deleteCalendarEvent(id: String)

    // Study Tasks
    @Query("SELECT * FROM study_tasks ORDER BY isCompleted ASC, dueDateEpochDay ASC")
    fun getAllTasks(): Flow<List<StudyTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: StudyTask)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<StudyTask>)

    @Query("UPDATE study_tasks SET isCompleted = :completed WHERE id = :id")
    suspend fun updateTaskCompletion(id: String, completed: Boolean)

    @Query("DELETE FROM study_tasks WHERE id = :id")
    suspend fun deleteTask(id: String)

    // Goals
    @Query("SELECT * FROM study_goals")
    fun getAllGoals(): Flow<List<StudyGoal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: StudyGoal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoals(goals: List<StudyGoal>)

    @Query("UPDATE study_goals SET currentHours = :hours, currentQuestions = :questions WHERE id = :id")
    suspend fun updateGoalProgress(id: String, hours: Float, questions: Int)

    @Query("DELETE FROM study_goals WHERE id = :id")
    suspend fun deleteGoal(id: String)

    // Grade Planner
    @Query("SELECT * FROM grade_courses")
    fun getAllGradeCourses(): Flow<List<GradeCourse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeCourse(course: GradeCourse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeCourses(courses: List<GradeCourse>)

    @Query("DELETE FROM grade_courses WHERE id = :courseId")
    suspend fun deleteGradeCourse(courseId: String)

    @Query("SELECT * FROM grade_assessments WHERE courseId = :courseId")
    fun getAssessmentsForCourse(courseId: String): Flow<List<GradeAssessment>>

    @Query("SELECT * FROM grade_assessments")
    fun getAllAssessments(): Flow<List<GradeAssessment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: GradeAssessment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessments(assessments: List<GradeAssessment>)

    @Query("DELETE FROM grade_assessments WHERE id = :assessmentId")
    suspend fun deleteAssessment(assessmentId: String)

    @Query("DELETE FROM grade_assessments WHERE courseId = :courseId")
    suspend fun deleteAssessmentsForCourse(courseId: String)

    // Study Sessions
    @Query("SELECT * FROM study_session_logs ORDER BY timestamp DESC")
    fun getAllStudySessions(): Flow<List<StudySessionLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudySession(session: StudySessionLog)

    // Scanned Documents
    @Query("SELECT * FROM scanned_documents ORDER BY createdAt DESC")
    fun getAllScannedDocuments(): Flow<List<ScannedDocument>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScannedDocument(doc: ScannedDocument)

    @Query("DELETE FROM scanned_documents WHERE id = :id")
    suspend fun deleteScannedDocument(id: String)
}
