package com.example.ui.api

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

object GeminiHttpClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"

    suspend fun analyzeVideo(videoUrlOrTopic: String, subjectName: String, mode: String): JSONObject? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        val modeLabel = if (mode == "exit_exam") "EXIT EXAM Mode" else "University Department Mode"
        val prompt = """
            You are an expert Academic Assistant and Exam Prep Specialist.
            Analyze the following video link or academic topic: "$videoUrlOrTopic".
            
            This video corresponds to the subject: "$subjectName".
            The study mode to emphasize is: "$modeLabel".
            
            Based on the subject and mode, generate a comprehensive study analysis:
            1) Map the core concepts directly to its relevant academic domain (${if (mode == "exit_exam") "Exit Exam national benchmarks and high-yield blueprints" else "University Department major requirements and career trajectories"}).
            2) Provide a detailed, concise, actionable study summary of the topic.
            3) Extract high-yield key terms.
            4) Generate exactly 3-5 multiple-choice questions (A, B, C, D) to test active retention, along with clear conceptual explanations.
            
            You MUST return a JSON object EXACTLY in the following format (do NOT wrap in markdown or add notes, output raw JSON only):
            {
              "title": "A highly descriptive, engaging title for this video/lecture",
              "duration": "Estimated study time (e.g., 18 mins)",
              "description": "A brief overview description of the lecture topic.",
              "conceptMapping": "A detailed explanation mapping the concepts to $modeLabel: requirements, courses, and jobs (if Department mode) OR blueprint weights and review tips (if Exit Exam mode).",
              "summary": "Detailed study summary formatted with rich bullet points (using \n for breaks)",
              "keyTerms": "A paragraph showcasing key terms with concise definitions.",
              "questions": [
                 {
                   "questionText": "Question text...",
                   "optionA": "Choice A...",
                   "optionB": "Choice B...",
                   "optionC": "Choice C...",
                   "optionD": "Choice D...",
                   "correctOption": "A",
                   "explanation": "Why this option is correct..."
                 }
              ]
            }
        """.trimIndent()

        try {
            val partsObj = JSONObject().put("text", prompt)
            val contentObj = JSONObject().put("parts", org.json.JSONArray().put(partsObj))
            val contentsArr = org.json.JSONArray().put(contentObj)
            
            val genConfig = JSONObject().put("responseMimeType", "application/json")
            
            val payload = JSONObject()
                .put("contents", contentsArr)
                .put("generationConfig", genConfig)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = payload.toString().toRequestBody(mediaType)

            val url = "$BASE_URL?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext null
            
            val responseBody = response.body?.string() ?: return@withContext null
            val responseJson = JSONObject(responseBody)
            
            val candidates = responseJson.getJSONArray("candidates")
            val firstCandidate = candidates.getJSONObject(0)
            val text = firstCandidate.getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text")
            
            JSONObject(text)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun generateText(prompt: String, systemInstruction: String? = null): String? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        try {
            val partsObj = JSONObject().put("text", prompt)
            val contentObj = JSONObject().put("parts", org.json.JSONArray().put(partsObj))
            val contentsArr = org.json.JSONArray().put(contentObj)

            val payload = JSONObject().put("contents", contentsArr)

            if (!systemInstruction.isNullOrBlank()) {
                val sysPart = JSONObject().put("text", systemInstruction)
                val sysContent = JSONObject().put("parts", org.json.JSONArray().put(sysPart))
                payload.put("systemInstruction", sysContent)
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = payload.toString().toRequestBody(mediaType)

            val url = "$BASE_URL?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext null

            val responseBody = response.body?.string() ?: return@withContext null
            val responseJson = JSONObject(responseBody)

            val candidates = responseJson.getJSONArray("candidates")
            val firstCandidate = candidates.getJSONObject(0)
            firstCandidate.getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
