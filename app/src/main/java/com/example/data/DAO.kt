package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EducationDao {
    @Query("SELECT * FROM user_progress WHERE id = 'primary_user' LIMIT 1")
    fun getUserProgress(): Flow<UserProgress?>

    @Query("SELECT * FROM user_progress WHERE id = 'primary_user' LIMIT 1")
    suspend fun getUserProgressDirect(): UserProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(userProgress: UserProgress)

    @Query("SELECT * FROM study_subjects")
    fun getSubjects(): Flow<List<StudySubject>>

    @Query("SELECT COUNT(*) FROM study_subjects")
    suspend fun getSubjectsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<StudySubject>)

    @Query("SELECT * FROM subject_notes WHERE subjectId = :subjectId")
    fun getNotesBySubject(subjectId: String): Flow<List<SubjectNote>>

    @Query("SELECT * FROM subject_notes")
    fun getAllNotes(): Flow<List<SubjectNote>>

    @Query("SELECT COUNT(*) FROM subject_notes")
    suspend fun getNotesCount(): Int

    @Query("SELECT * FROM subject_notes WHERE gradeLevel = :gradeLevel")
    fun getNotesByGrade(gradeLevel: String): Flow<List<SubjectNote>>

    @Query("SELECT * FROM subject_notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<SubjectNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<SubjectNote>)

    @Query("SELECT * FROM exam_questions WHERE subjectId = :subjectId")
    fun getQuestionsBySubject(subjectId: String): Flow<List<ExamQuestion>>

    @Query("SELECT * FROM exam_questions")
    fun getAllQuestions(): Flow<List<ExamQuestion>>

    @Query("SELECT COUNT(*) FROM exam_questions")
    suspend fun getQuestionsCount(): Int

    @Query("SELECT * FROM exam_questions WHERE id = :questionId LIMIT 1")
    suspend fun getQuestionById(questionId: String): ExamQuestion?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<ExamQuestion>)

    @Query("SELECT * FROM flashcards WHERE subjectId = :subjectId")
    fun getFlashcardsBySubject(subjectId: String): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): Flow<List<Flashcard>>

    @Query("SELECT COUNT(*) FROM flashcards")
    suspend fun getFlashcardsCount(): Int

    @Query("SELECT * FROM flashcards WHERE isStarred = 1")
    fun getStarredFlashcards(): Flow<List<Flashcard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(flashcards: List<Flashcard>)

    @Query("UPDATE flashcards SET isKnown = :isKnown, isStarred = :isStarred WHERE id = :id")
    suspend fun updateFlashcardState(id: String, isKnown: Boolean, isStarred: Boolean)

    @Query("SELECT * FROM analyzed_videos WHERE subjectId = :subjectId")
    fun getAnalyzedVideos(subjectId: String): Flow<List<AnalyzedVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalyzedVideo(video: AnalyzedVideo)
}
