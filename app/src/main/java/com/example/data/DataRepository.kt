package com.example.data

import kotlinx.coroutines.flow.Flow

/**
 * DataRepository layer providing a clean abstraction over Room database
 * for caching study subjects, unit notes, exam questions, flashcards,
 * and user progress locally on-device.
 */
interface DataRepository {
    val userProgress: Flow<UserProgress?>
    val subjects: Flow<List<StudySubject>>

    // Notes caching operations
    fun getNotesBySubject(subjectId: String): Flow<List<SubjectNote>>
    fun getAllNotes(): Flow<List<SubjectNote>>
    fun getNotesByGrade(gradeLevel: String): Flow<List<SubjectNote>>
    fun searchNotes(query: String): Flow<List<SubjectNote>>
    suspend fun insertNotes(notes: List<SubjectNote>)

    // Questions caching operations
    fun getQuestionsBySubject(subjectId: String): Flow<List<ExamQuestion>>
    fun getAllQuestions(): Flow<List<ExamQuestion>>
    suspend fun getQuestionById(questionId: String): ExamQuestion?
    suspend fun insertQuestions(questions: List<ExamQuestion>)

    // Flashcards caching operations
    fun getFlashcardsBySubject(subjectId: String): Flow<List<Flashcard>>
    fun getAllFlashcards(): Flow<List<Flashcard>>
    fun getStarredFlashcards(): Flow<List<Flashcard>>
    suspend fun insertFlashcards(flashcards: List<Flashcard>)
    suspend fun updateFlashcard(id: String, isKnown: Boolean, isStarred: Boolean)

    // Analyzed Videos operations
    fun getAnalyzedVideos(subjectId: String): Flow<List<AnalyzedVideo>>
    suspend fun insertAnalyzedVideo(video: AnalyzedVideo)

    // User Progress & Entitlement operations
    suspend fun updateProgress(progress: UserProgress)
    suspend fun enrollPackage(packageId: String)
    suspend fun resetEnrollment()
    suspend fun submitPaymentIntent(txnId: String, senderPhone: String, screenshotPath: String)
    suspend fun approvePayment(packageId: String? = null, username: String? = null)
    suspend fun rejectPayment()
    suspend fun updateUsername(newName: String)
    suspend fun completeSubject(subjectId: String)
    suspend fun updateTelegramStatus(connected: Boolean)
    suspend fun incrementExamScore(score: Int)
    suspend fun seedDatabaseIfEmpty()

    // Fine-grained Entitlements & Purchase Requests
    suspend fun grantEntitlement(productId: String, reference: String = "")
    fun getEntitlements(): Flow<List<Entitlement>>
    suspend fun savePurchaseRequest(request: PurchaseRequest)
    suspend fun updatePurchaseStatus(reference: String, status: String)
    suspend fun isEntitledTo(productId: String): Boolean
}
