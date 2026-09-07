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

    private const val BASE_URL_25 = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
    private const val BASE_URL_15 = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent"

    private fun getApiUrls(): List<String> = listOf(BASE_URL_25, BASE_URL_15)

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

        for (endpoint in getApiUrls()) {
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

                val url = "$endpoint?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()
                if (!response.isSuccessful) continue
                
                val responseBody = response.body?.string() ?: continue
                val responseJson = JSONObject(responseBody)
                
                val candidates = responseJson.getJSONArray("candidates")
                val firstCandidate = candidates.getJSONObject(0)
                val text = firstCandidate.getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text")
                
                return@withContext JSONObject(text)
            } catch (e: Exception) {
                // Try next endpoint
            }
        }
        null
    }

    suspend fun generateText(prompt: String, systemInstruction: String? = null): String? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        for (endpoint in getApiUrls()) {
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

                val url = "$endpoint?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()
                if (!response.isSuccessful) continue

                val responseBody = response.body?.string() ?: continue
                val responseJson = JSONObject(responseBody)

                val candidates = responseJson.optJSONArray("candidates") ?: continue
                if (candidates.length() == 0) continue
                val firstCandidate = candidates.getJSONObject(0)
                val parts = firstCandidate.optJSONObject("content")?.optJSONArray("parts") ?: continue
                if (parts.length() == 0) continue
                val result = parts.getJSONObject(0).optString("text", "")
                if (result.isNotBlank()) {
                    return@withContext result
                }
            } catch (e: Exception) {
                // Try next fallback endpoint
            }
        }
        null
    }

    /**
     * Extracts text and study notes from a document bitmap using Gemini Vision.
     */
    suspend fun extractTextFromImage(bitmap: android.graphics.Bitmap): String? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        val base64Image = try {
            val stream = java.io.ByteArrayOutputStream()
            // Scale bitmap to reasonable resolution for high OCR fidelity without payload bloat
            val maxDimension = 1280
            val scale = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
                val factor = maxDimension.toFloat() / kotlin.math.max(bitmap.width, bitmap.height)
                android.graphics.Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * factor).toInt(),
                    (bitmap.height * factor).toInt(),
                    true
                )
            } else {
                bitmap
            }
            scale.compress(android.graphics.Bitmap.CompressFormat.JPEG, 85, stream)
            val byteArray = stream.toByteArray()
            android.util.Base64.encodeToString(byteArray, android.util.Base64.NO_WRAP)
        } catch (e: Exception) {
            return@withContext null
        }

        val ocrPrompt = "Transcribe all printed or handwritten text from this document image accurately and clearly. Preserve paragraph structure, headings, bullet points, numbers, and formulas. Do not add intro/outro commentary, return only the extracted text."

        for (endpoint in getApiUrls()) {
            try {
                val textPart = JSONObject().put("text", ocrPrompt)
                val inlineData = JSONObject()
                    .put("mimeType", "image/jpeg")
                    .put("data", base64Image)
                val imagePart = JSONObject().put("inlineData", inlineData)

                val partsArr = org.json.JSONArray().put(textPart).put(imagePart)
                val contentObj = JSONObject().put("parts", partsArr)
                val contentsArr = org.json.JSONArray().put(contentObj)

                val payload = JSONObject().put("contents", contentsArr)

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val body = payload.toString().toRequestBody(mediaType)

                val url = "$endpoint?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()
                if (!response.isSuccessful) continue

                val responseBody = response.body?.string() ?: continue
                val responseJson = JSONObject(responseBody)

                val candidates = responseJson.optJSONArray("candidates") ?: continue
                if (candidates.length() == 0) continue
                val firstCandidate = candidates.getJSONObject(0)
                val parts = firstCandidate.optJSONObject("content")?.optJSONArray("parts") ?: continue
                if (parts.length() == 0) continue
                val result = parts.getJSONObject(0).optString("text", "")
                if (result.isNotBlank()) {
                    return@withContext result.trim()
                }
            } catch (e: Exception) {
                // Try next endpoint
            }
        }
        null
    }
}
