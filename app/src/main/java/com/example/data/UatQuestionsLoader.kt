package com.example.data

import android.content.Context
import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

object UatQuestionsLoader {
    private const val TAG = "UatQuestionsLoader"

    fun loadUatQuestions(context: Context): List<ExamQuestion> {
        val questions = mutableListOf<ExamQuestion>()

        // 1. Load AAU Quantitative Reasoning Questions
        try {
            val quantJson = context.assets.open("uat/uat_quantitative_reasoning.json").use { stream ->
                BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).readText()
            }
            val quantArray = JSONArray(quantJson)
            for (i in 0 until quantArray.length()) {
                val obj = quantArray.getJSONObject(i)
                val qText = obj.optString("question", "")
                val optA = obj.optString("A", "")
                val optB = obj.optString("B", "")
                val optC = obj.optString("C", "")
                val optD = obj.optString("D", "")
                val correct = obj.optString("correct", "A").trim().uppercase()
                val exp = obj.optString("explanation", "")
                if (qText.isNotBlank()) {
                    questions.add(
                        ExamQuestion(
                            id = "aau_uat_quant_${i + 1}",
                            subjectId = "uat_quantitative",
                            question = qText,
                            optionA = optA,
                            optionB = optB,
                            optionC = optC,
                            optionD = optD,
                            correctAnswer = correct,
                            explanation = exp
                        )
                    )
                }
            }
            Log.d(TAG, "Loaded ${quantArray.length()} AAU Quantitative questions.")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading AAU Quantitative questions", e)
        }

        // 2. Load AAU Verbal Reasoning Questions
        try {
            val verbalJson = context.assets.open("uat/uat_verbal_reasoning.json").use { stream ->
                BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).readText()
            }
            val verbalArray = JSONArray(verbalJson)
            for (i in 0 until verbalArray.length()) {
                val obj = verbalArray.getJSONObject(i)
                val qText = obj.optString("question", "")
                val optA = obj.optString("A", "")
                val optB = obj.optString("B", "")
                val optC = obj.optString("C", "")
                val optD = obj.optString("D", "")
                val correct = obj.optString("correct", "A").trim().uppercase()
                val exp = obj.optString("explanation", "")
                if (qText.isNotBlank()) {
                    questions.add(
                        ExamQuestion(
                            id = "aau_uat_verbal_${i + 1}",
                            subjectId = "uat_verbal",
                            question = qText,
                            optionA = optA,
                            optionB = optB,
                            optionC = optC,
                            optionD = optD,
                            correctAnswer = correct,
                            explanation = exp
                        )
                    )
                }
            }
            Log.d(TAG, "Loaded ${verbalArray.length()} AAU Verbal questions.")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading AAU Verbal questions", e)
        }

        return questions
    }
}
