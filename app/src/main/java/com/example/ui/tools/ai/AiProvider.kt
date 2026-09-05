package com.example.ui.tools.ai

data class StudentContext(
    val subject: String = "General",
    val currentTopic: String = "",
    val gradeLevel: String = "Grade 9-12",
    val activeNotesSnippet: String = "",
    val currentAverageGrade: Double = 0.0,
    val learningContext: LearningContext? = null
)

data class GeneratedFlashcard(
    val front: String,
    val back: String
)

data class GeneratedQuestion(
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String, // "A", "B", "C", "D"
    val explanation: String
)

interface AiProvider {
    val providerName: String
    val isLocalAi: Boolean

    suspend fun ask(prompt: String, context: StudentContext): String
    suspend fun explainConcept(concept: String, subject: String): String
    suspend fun summarize(text: String): String
    suspend fun generateFlashcards(text: String, count: Int = 4): List<GeneratedFlashcard>
    suspend fun generateQuestions(text: String, count: Int = 4): List<GeneratedQuestion>
    suspend fun explainGrades(courseName: String, currentAvg: Double, target: Double, reqFinal: Double): String
}
