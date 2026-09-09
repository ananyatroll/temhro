package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entitlements")
data class Entitlement(
    @PrimaryKey val productId: String,
    val grantedAtMillis: Long = System.currentTimeMillis(),
    /** Server purchase reference that granted this entitlement (for audit). */
    val purchaseReference: String = ""
)

@Entity(tableName = "purchase_requests")
data class PurchaseRequest(
    @PrimaryKey val reference: String, // e.g. "TH-7F92K"
    val productId: String,
    val category: String,
    val stream: String? = null,
    val academicYear: Int? = null,
    val department: String? = null,
    val plan: String = "sem1",
    val amount: Int = 0,
    val currency: String = "ETB",
    val language: String = "en",
    val status: String = "pending", // "pending", "approved", "rejected", "redeemed"
    val payerName: String = "",
    val transactionId: String = "",
    val createdAtMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: String = "primary_user",
    val username: String = "Ananya",
    val activePackageId: String? = null,
    val purchasedPackageId: String = "", // Comma-separated purchased package IDs (e.g. "euee_natural")
    val completedSubjects: String = "", // Comma-separated subject IDs (e.g. "biology,chemistry")
    val telegramConnected: Boolean = false,
    val scoreCount: Int = 0,
    val examsCompleted: Int = 0,
    val paymentStatus: String = "none", // "none", "pending", "approved"
    val paymentTxnId: String = "",
    val paymentSenderPhone: String = "",
    val paymentScreenshotPath: String = ""
)

@Entity(tableName = "study_subjects")
data class StudySubject(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String, // "biology", "civics", "chemistry", "anthropology"
    val packageId: String = "euee"
)

@Entity(tableName = "subject_notes")
data class SubjectNote(
    @PrimaryKey val id: String,
    val subjectId: String,
    val unit: String, // "UNIT 1", "UNIT 2", etc.
    val title: String,
    val content: String,
    val gradeLevel: String = "Grade 9", // "Grade 9", "Grade 10", "Grade 11", "Grade 12", "General"
    val releaseDate: String = "" // ISO date string "yyyy-MM-dd" – empty means always available
)

@Entity(tableName = "exam_questions")
data class ExamQuestion(
    @PrimaryKey val id: String,
    val subjectId: String,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String, // "A", "B", "C", or "D"
    val explanation: String,
    val unit: String = "All"
)

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey val id: String,
    val subjectId: String,
    val front: String,
    val back: String,
    val isStarred: Boolean = false,
    val isKnown: Boolean = false,
    val gradeLevel: String = "Grade 9",
    val unit: String = "All"
)

@Entity(tableName = "analyzed_videos")
data class AnalyzedVideo(
    @PrimaryKey val id: String,
    val subjectId: String,
    val videoUrl: String,
    val title: String,
    val duration: String,
    val description: String,
    val domainOrDepartment: String, // "University Department" or "EXIT EXAM"
    val conceptMapping: String,
    val summary: String,
    val keyTerms: String,
    val questionsJson: String // Serialized JSON representation of custom practice questions
)

