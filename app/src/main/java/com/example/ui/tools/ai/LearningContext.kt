package com.example.ui.tools.ai

/**
 * Lightweight, immutable context representing the student's active learning state.
 * Captures the current course, topic, content type, and any excerpt or question being viewed.
 */
data class LearningContext(
    val courseId: String = "",
    val courseName: String = "",
    val topicId: String = "",
    val topicName: String = "",
    val contentId: String = "",
    val contentText: String = "",
    val selectedText: String = "",
    val contentType: String = "general", // "notes", "practice", "flashcard", "reading", "scanned_doc", "general"
    val gradeLevel: String = "Grade 9-12",
    val question: String = "",
    val questionOptions: List<String> = emptyList(),
    val correctAnswer: String = "",
    val explanation: String = "",
    val flashcardFront: String = "",
    val flashcardBack: String = "",
    val metadata: Map<String, String> = emptyMap()
) {
    fun withCourse(newCourseName: String, newCourseId: String = ""): LearningContext {
        return copy(
            courseName = newCourseName,
            courseId = if (newCourseId.isNotBlank()) newCourseId else courseId,
            topicName = if (topicName.isBlank() || topicName == courseName || contentType == "general") newCourseName else topicName
        )
    }
}
