package com.vantedge.app.data.network

import android.util.Log
import com.vantedge.app.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class GeminiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json".toMediaType()

    private val apiKey = BuildConfig.OPENROUTER_API_KEY
    private val baseUrl = "https://openrouter.ai/api/v1/chat/completions"

    private val models = listOf(
        "openrouter/free",
        "nvidia/nemotron-3-nano-30b-a3b:free",
        "openai/gpt-oss-120b:free",
        "meta-llama/llama-3.3-70b-instruct:free",
        "openai/gpt-oss-20b:free",
        "z-ai/glm-4.5-air:free",
        "minimax/minimax-m2.5:free",
        "nvidia/nemotron-nano-9b-v2:free",
        "google/gemma-4-26b-a4b:free",
        "qwen/qwen3-next-80b-a3b-instruct:free",
        "meta-llama/llama-3.2-3b-instruct:free",
        "liquid/lfm2.5-1.2b-instruct:free"
    )

    private val systemInstruction = """
        You are a precise CV extraction engine.

        CRITICAL RULES:
        - Return ONLY raw JSON
        - No markdown formatting (no ```json fences)
        - No explanations, intro, or closing text
        - JSON MUST ALWAYS BE COMPLETE AND VALID.
        - Never stop mid-response.
        - Ensure all nested objects and arrays are properly closed.
        
        CANONICAL SCHEMA (must match exactly):
        {
          "name": "",
          "email": "",
          "phone": "",
          "summary": "",
          "skills": [],
          "workHistory": [
            {
              "jobTitle": "",
              "company": "",
              "startDate": "",
              "endDate": "",
              "description": ""
            }
          ],
          "education": [],
          "certifications": []
        }
        
        If a field is missing or unknown, return an empty string "" or empty array [].
        Never omit a key entirely.
    """.trimIndent()

    private fun delayForAttempt(index: Int): Long = when {
        index < 3 -> 1_000L
        index < 6 -> 2_000L
        else -> 3_000L
    }

    private fun statusForAttempt(index: Int): String = when {
        index == 0 -> "Analyzing your document..."
        index < 3 -> "Analyzing your document..."
        index < 6 -> "Still working — trying alternative methods..."
        index < 9 -> "Experiencing temporary congestion..."
        else -> "Almost done..."
    }

    suspend fun generate(
        prompt: String,
        onProgress: (String) -> Unit = {}
    ): String? {
        val result = tryChain(prompt, onProgress, isRetry = false)
        if (result != null) return result

        onProgress("Final retry in progress...")
        delay(2_000L)

        return tryChain(prompt, onProgress, isRetry = true)
    }

    private suspend fun tryChain(
        prompt: String,
        onProgress: (String) -> Unit,
        isRetry: Boolean
    ): String? {

        models.forEachIndexed { index, model ->

            onProgress(statusForAttempt(index))

            Log.d(
                "GeminiService",
                "DEBUG: [tryChain] ${if (isRetry) "[RETRY] " else ""}Trying model: $model"
            )

            val result = withTimeoutOrNull(25_000L) {
                tryModel(model, prompt)
            }

            if (result != null) {
                Log.d("GeminiService", "DEBUG: [tryChain] Success with model: $model")
                return result
            }

            Log.w(
                "GeminiService",
                "DEBUG: [tryChain] Model $model failed or timed out. Backing off for ${delayForAttempt(index)}ms"
            )

            delay(delayForAttempt(index))
        }

        return null
    }

    private suspend fun tryModel(
        model: String,
        prompt: String
    ): String? {

        val json = JSONObject().apply {
            put("model", model)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", systemInstruction)
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
            // HARDENING: Increased from 2048 to 8192 to prevent token truncation mid-array
            put("max_tokens", 8192)
            put("temperature", 0.2)
            put("top_p", 0.8)
        }

        val request = Request.Builder()
            .url(baseUrl)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("HTTP-Referer", "com.vantedge.app")
            .addHeader("X-Title", "VantEdge")
            .post(json.toString().toRequestBody(mediaType))
            .build()

        return suspendCancellableCoroutine { cont ->

            val call = client.newCall(request)

            cont.invokeOnCancellation {
                call.cancel()
            }

            call.enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("GeminiService", "DEBUG: [tryModel] Network failure on $model: ${e.message}")
                    if (cont.isActive) cont.resume(null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()

                    if (!cont.isActive) return

                    when (response.code) {
                        200 -> {
                            try {
                                val raw = JSONObject(body ?: "")
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content")

                                val cleaned = raw
                                    .replace("```json", "")
                                    .replace("```", "")
                                    .replace(Regex("""\*\*(.+?)\*\*"""), "$1")
                                    .replace(Regex("""\*(.+?)\*"""), "$1")
                                    .trim()

                                cont.resume(cleaned)

                            } catch (e: Exception) {
                                Log.e("GeminiService", "DEBUG: [tryModel] Parse error on $model: ${e.message}")
                                cont.resume(null)
                            }
                        }
                        404, 429 -> {
                            Log.w("GeminiService", "DEBUG: [tryModel] $model returned ${response.code}, skipping to next model.")
                            cont.resume(null)
                        }
                        else -> {
                            Log.e("GeminiService", "DEBUG: [tryModel] Error ${response.code} on $model")
                            cont.resume(null)
                        }
                    }
                }
            })
        }
    }
}
