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
        requestId: String,
        prompt: String,
        onProgress: (String) -> Unit = {},
        onModelResult: (String, String?, String?) -> Unit = { _, _, _ -> }
    ): String? {
        val result = tryChain(requestId, prompt, onProgress, onModelResult, isRetry = false)
        if (result != null) return result

        onProgress("Final retry in progress...")
        delay(2_000L)

        return tryChain(requestId, prompt, onProgress, onModelResult, isRetry = true)
    }

    private suspend fun tryChain(
        requestId: String,
        prompt: String,
        onProgress: (String) -> Unit,
        onModelResult: (String, String?, String?) -> Unit,
        isRetry: Boolean
    ): String? {

        models.forEachIndexed { index, model ->

            onProgress(statusForAttempt(index))

            val modelAttempted = java.util.concurrent.atomic.AtomicBoolean(false)

            val result = withTimeoutOrNull(25_000L) {
                tryModel(requestId, model, prompt) { modelName, errorType, detail ->
                    modelAttempted.set(true)
                    onModelResult(modelName, errorType, detail)
                }
            }

            if (result != null) {
                onModelResult(model, null, null)
                return result
            }

            if (!modelAttempted.get()) {
                onModelResult(model, "timeout", null)
            }

            delay(delayForAttempt(index))
        }

        return null
    }

    private suspend fun tryModel(
        requestId: String,
        model: String,
        prompt: String,
        onModelResult: (String, String?, String?) -> Unit = { _, _, _ -> }
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
                    onModelResult(model, "network", e.message)
                    if (cont.isActive) cont.resume(null)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val code = response.code

                    if (!cont.isActive) return

                    if (code == 200) {
                        val bodyPreview = body?.take(500) ?: ""

                        if (body.isNullOrBlank()) {
                            Log.e("GeminiService", "[$requestId] [$model] EMPTY_RESPONSE code=$code body=$bodyPreview")
                            onModelResult(model, "empty", null)
                            cont.resume(null)
                            return
                        }

                        val payload: JSONObject = try {
                            JSONObject(body)
                        } catch (e: Exception) {
                            Log.e("GeminiService", "[$requestId] [$model] INVALID_JSON code=$code error=${e.message} body=$bodyPreview")
                            onModelResult(model, "invalid_json", e.message)
                            cont.resume(null)
                            return
                        }

                        if (payload.has("error")) {
                            val errorObj = payload.getJSONObject("error")
                            val errorMsg = errorObj.optString("message", errorObj.toString())
                            Log.e("GeminiService", "[$requestId] [$model] PROVIDER_ERROR code=$code message=$errorMsg body=$bodyPreview")
                            onModelResult(model, "provider_error", errorMsg)
                            cont.resume(null)
                            return
                        }

                        if (!payload.has("choices")) {
                            val keys = mutableListOf<String>().also { list ->
                                val iter = payload.keys()
                                while (iter.hasNext()) list += iter.next()
                            }
                            Log.e("GeminiService", "[$requestId] [$model] SCHEMA_ERROR code=$code keys=$keys body=$bodyPreview")
                            onModelResult(model, "schema_error", "No value for choices")
                            cont.resume(null)
                            return
                        }

                        try {
                            val raw = payload
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

                            Log.d("GeminiService", "[$requestId] [$model] SUCCESS contentLength=${cleaned.length}")
                            cont.resume(cleaned)

                        } catch (e: Exception) {
                            val keys = mutableListOf<String>().also { list ->
                                val iter = payload.keys()
                                while (iter.hasNext()) list += iter.next()
                            }
                            Log.e("GeminiService", "[$requestId] [$model] PARSE_ERROR error=${e.message} keys=$keys body=$bodyPreview")
                            onModelResult(model, "parse", e.message)
                            cont.resume(null)
                        }
                        return
                    }

                    val bodyPreview = body?.take(200) ?: ""
                    when (code) {
                        404 -> {
                            Log.w("GeminiService", "[$requestId] [$model] HTTP_404 body=$bodyPreview")
                            onModelResult(model, "404", null)
                        }
                        429 -> {
                            Log.w("GeminiService", "[$requestId] [$model] HTTP_429 body=$bodyPreview")
                            onModelResult(model, "429", null)
                        }
                        else -> {
                            Log.w("GeminiService", "[$requestId] [$model] HTTP_${code} body=$bodyPreview")
                            onModelResult(model, "http_$code", null)
                        }
                    }
                    cont.resume(null)
                }
            })
        }
    }
}
