package com.vantedge.app.data.network

import android.os.SystemClock
import android.util.Base64
import android.util.Log
import com.vantedge.app.BuildConfig
import com.vantedge.app.data.model.AiRawResponseArtifact
import com.vantedge.app.data.storage.AiRawResponseArtifactDao
import com.vantedge.app.domain.PipelineEvents
import com.vantedge.app.domain.PipelineTrace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
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
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

enum class FailureType {
    CONFIG_ERROR,
    TIMEOUT,
    RATE_LIMIT,
    TRANSIENT_TRANSPORT_FAILURE,
    OTHER
}

class AttemptMetrics {
    var firstCallbackMs: Long = 0L
    var httpCode: Int = 0
    var cancelReason: String? = null
}

private data class ModelCircuitState(
    var consecutiveFailures: Int = 0,
    var degradedUntilMs: Long = 0L
)

class GeminiService(
    private val artifactDao: AiRawResponseArtifactDao
) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json".toMediaType()

    private val apiKey = BuildConfig.OPENROUTER_API_KEY
    private val baseUrl = "https://openrouter.ai/api/v1/chat/completions"

    companion object {
        private const val RAW_ARTIFACT_MAX_BYTES = 100 * 1024
        private const val DEGRADE_THRESHOLD = 3
        private const val DEGRADE_DURATION_MS = 15 * 60 * 1000L

        private data class ExtractionResult(
            val content: String,
            val strategy: String
        )

        private fun computePromptHash(systemPrompt: String, userPrompt: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update((systemPrompt + userPrompt).toByteArray(Charsets.UTF_8))
            return Base64.encodeToString(digest.digest(), Base64.NO_WRAP).take(16)
        }

        private fun extractContent(raw: String): ExtractionResult {
            try {
                JSONObject(raw)
                return ExtractionResult(raw, "direct_parse")
            } catch (_: Exception) {}

            val firstBrace = raw.indexOf('{')
            val lastBrace = raw.lastIndexOf('}')
            if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
                val candidate = raw.substring(firstBrace, lastBrace + 1)
                try {
                    JSONObject(candidate)
                    return ExtractionResult(candidate, "balanced_brace")
                } catch (_: Exception) {}
            }

            val noFence = raw
                .replace("```json", "")
                .replace("```", "")
                .replace(Regex("""\*\*(.+?)\*\*"""), "$1")
                .replace(Regex("""\*(.+?)\*"""), "$1")
                .trim()
            if (noFence != raw) {
                try {
                    JSONObject(noFence)
                    return ExtractionResult(noFence, "markdown_strip")
                } catch (_: Exception) {}
            }

            val candidates = mutableListOf<String>()
            var idx = 0
            while (true) {
                val start = raw.indexOf('{', idx)
                if (start == -1) break
                val end = raw.indexOf('}', start)
                if (end == -1) break
                val segment = raw.substring(start, end + 1)
                if (segment.length > 50) candidates.add(segment)
                idx = end + 1
            }
            for (candidate in candidates.sortedByDescending { it.length }) {
                try {
                    JSONObject(candidate)
                    return ExtractionResult(candidate, "substring_scan")
                } catch (_: Exception) {}
            }

            return ExtractionResult(raw, "failed")
        }
    }

    private val modelStates = mutableMapOf<String, ModelCircuitState>()

    private val models = listOf(
        "nvidia/nemotron-nano-9b-v2:free",
        "openai/gpt-oss-20b:free",
        "meta-llama/llama-3.2-3b-instruct:free"
    )

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

    private fun classifyError(errorType: String?): FailureType = when (errorType) {
        "404", "http_400", "http_401", "http_403", "http_404" -> FailureType.CONFIG_ERROR
        "timeout", "network" -> FailureType.TIMEOUT
        "429" -> FailureType.RATE_LIMIT
        "cancelled" -> FailureType.TRANSIENT_TRANSPORT_FAILURE
        "EMPTY_BODY", "MALFORMED_SHORT", "NO_JSON_STRUCTURE", "empty" -> FailureType.OTHER
        else -> FailureType.OTHER
    }

    suspend fun generate(
        requestId: String,
        request: AiRequest,
        onProgress: (String) -> Unit = {},
        onModelResult: (ModelAttemptResult) -> Unit = {},
        budgetDeadlineMs: Long = Long.MAX_VALUE
    ): String? {
        val requestStartElapsed = SystemClock.elapsedRealtime()
        val blacklistedModels = mutableSetOf<String>()
        val result = tryChain(requestId, request, onProgress, onModelResult, isRetry = false, blacklistedModels, requestStartElapsed, budgetDeadlineMs)
        if (result != null) return result

        onProgress("Final retry in progress...")

        PipelineTrace.dataQuality(
            stage = "gemini_service",
            issue = PipelineEvents.FINAL_RETRY_BEGIN,
            details = mapOf(
                "requestId" to requestId,
                "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
            ),
            correlationId = requestId
        )

        delay(2_000L)

        val finalResult = tryChain(requestId, request, onProgress, onModelResult, isRetry = true, blacklistedModels, requestStartElapsed, budgetDeadlineMs)

        PipelineTrace.dataQuality(
            stage = "gemini_service",
            issue = PipelineEvents.FINAL_RETRY_END,
            details = mapOf(
                "requestId" to requestId,
                "resultIsNull" to (finalResult == null),
                "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
            ),
            correlationId = requestId
        )

        return finalResult
    }

    private suspend fun tryChain(
        requestId: String,
        request: AiRequest,
        onProgress: (String) -> Unit,
        onModelResult: (ModelAttemptResult) -> Unit,
        isRetry: Boolean,
        blacklistedModels: MutableSet<String>,
        requestStartElapsed: Long,
        budgetDeadlineMs: Long
    ): String? {

        var httpAttemptCounter = 0
        var failureStreak = 0
        val recentRateLimits = mutableListOf<Boolean>()
        var shouldStop = false

        PipelineTrace.dataQuality(
            stage = "gemini_service",
            issue = PipelineEvents.TRYCHAIN_START,
            details = mapOf(
                "requestId" to requestId,
                "isRetry" to isRetry,
                "blacklistedCount" to blacklistedModels.size,
                "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
            ),
            correlationId = requestId
        )

        for (index in models.indices) {
            val model = models[index]

            // Budget check at iteration start (includes retry delays)
            if (SystemClock.elapsedRealtime() > budgetDeadlineMs) {
                Log.w("GeminiService", "[$requestId] BUDGET_EXCEEDED at iteration start")
                PipelineTrace.dataQuality(
                    stage = "gemini_service",
                    issue = PipelineEvents.TRYCHAIN_END,
                    details = mapOf(
                        "requestId" to requestId,
                        "reason" to "BUDGET_EXCEEDED",
                        "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed),
                        "timeoutSource" to "BUDGET"
                    ),
                    correlationId = requestId
                )
                shouldStop = true
            }

            if (shouldStop) break
            if (model in blacklistedModels) continue

            // Circuit breaker: skip degraded models
            val cbState = modelStates.getOrPut(model) { ModelCircuitState() }
            val now = SystemClock.elapsedRealtime()
            if (cbState.degradedUntilMs > now) {
                Log.w("GeminiService", "[$requestId] CIRCUIT_BREAKER_SKIP model=$model degradedForMs=${cbState.degradedUntilMs - now}")
                PipelineTrace.dataQuality(
                    stage = "gemini_service",
                    issue = PipelineEvents.CIRCUIT_BREAKER_SKIP,
                    details = mapOf(
                        "requestId" to requestId,
                        "model" to model,
                        "consecutiveFailures" to cbState.consecutiveFailures,
                        "remainingCooldownMs" to (cbState.degradedUntilMs - now)
                    ),
                    correlationId = requestId
                )
                continue
            }

            onProgress(statusForAttempt(index))

            val modelAttempted = java.util.concurrent.atomic.AtomicBoolean(false)
            var capturedErrorType: String? = null

            val metrics = AttemptMetrics()
            val attemptStart = SystemClock.elapsedRealtime()
            httpAttemptCounter++
            val transportAttempt = "HTTP-$httpAttemptCounter"

            PipelineTrace.dataQuality(
                stage = "gemini_service",
                issue = PipelineEvents.ATTEMPT_START,
                details = mapOf(
                    "requestId" to requestId,
                    "model" to model,
                    "index" to index,
                    "chainAttempt" to (index + 1),
                    "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
                ),
                correlationId = requestId
            )

            val result = tryModel(requestId, model, index, request, metrics, transportAttempt, requestStartElapsed) { result ->
                modelAttempted.set(true)
                capturedErrorType = result.failureType
                onModelResult(result)
            }

            val attemptEnd = SystemClock.elapsedRealtime()
            val durationMs = attemptEnd - attemptStart
            val latencyMs = if (metrics.firstCallbackMs != 0L) metrics.firstCallbackMs - attemptStart else null

            val outcome = if (result != null) {
                "SUCCESS"
            } else {
                val errorType = if (!modelAttempted.get()) "timeout" else capturedErrorType
                when (classifyError(errorType)) {
                    FailureType.CONFIG_ERROR -> "CONFIG_ERROR"
                    FailureType.TIMEOUT -> "TIMEOUT"
                    FailureType.RATE_LIMIT -> "RATE_LIMIT"
                    FailureType.TRANSIENT_TRANSPORT_FAILURE -> "TRANSIENT_TRANSPORT_FAILURE"
                    FailureType.OTHER -> "OTHER"
                }
            }

            val logLine = buildString {
                append("[$requestId] MODEL_ATTEMPT model=$model attempt=${index + 1}")
                append(" durationMs=$durationMs")
                if (latencyMs != null) append(" latencyMs=$latencyMs")
                append(" outcome=$outcome")
                if (metrics.httpCode != 0) append(" httpCode=${metrics.httpCode}")
                if (metrics.cancelReason != null) append(" cancelReason=${metrics.cancelReason}")
            }
            Log.i("GeminiService", logLine)

            if (result != null) {
                onModelResult(ModelAttemptResult(
                    requestId = requestId,
                    model = model,
                    attempt = index + 1,
                    failureType = null,
                    httpCode = if (metrics.httpCode != 0) metrics.httpCode else null,
                    durationMs = durationMs,
                    latencyMs = latencyMs,
                    bodyPreview = null,
                    detail = null,
                    cancelReason = metrics.cancelReason
                ))
                // Circuit breaker: full contract success resets counter
                if (cbState.consecutiveFailures > 0) {
                    cbState.consecutiveFailures = 0
                    PipelineTrace.dataQuality(
                        stage = "gemini_service",
                        issue = PipelineEvents.CIRCUIT_BREAKER_RESET,
                        details = mapOf(
                            "requestId" to requestId,
                            "model" to model
                        ),
                        correlationId = requestId
                    )
                }
                failureStreak = 0
                return result
            }

            val errorType = if (!modelAttempted.get()) "timeout" else capturedErrorType
            val failureType = classifyError(errorType)

            when (failureType) {
                FailureType.CONFIG_ERROR -> {
                    Log.w("GeminiService", "[$requestId] [$model] CONFIG_ERROR — blacklisting")
                    blacklistedModels.add(model)
                }
                FailureType.TIMEOUT -> {
                    failureStreak++
                    Log.w("GeminiService", "[$requestId] [$model] TIMEOUT streak=$failureStreak")
                    if (failureStreak >= 2) {
                        Log.w("GeminiService", "[$requestId] FAILURE_STREAK=$failureStreak — terminating chain")
                        shouldStop = true
                    }
                }
                FailureType.RATE_LIMIT -> {
                    recentRateLimits.add(true)
                    if (recentRateLimits.size > 3) recentRateLimits.removeAt(0)
                    Log.w("GeminiService", "[$requestId] [$model] RATE_LIMIT pressure=${recentRateLimits.count { it }}")
                    if (recentRateLimits.count { it } >= 2) {
                        Log.w("GeminiService", "[$requestId] RATE_LIMIT_CLUSTER — terminating chain")
                        shouldStop = true
                    }
                }
                FailureType.TRANSIENT_TRANSPORT_FAILURE -> {
                    Log.w("GeminiService", "[$requestId] [$model] TRANSPORT_CANCELLATION — no streak increment")
                }
                FailureType.OTHER -> {
                    Log.w("GeminiService", "[$requestId] [$model] OTHER error=$errorType — skipping")
                }
            }

            // Circuit breaker: increment on every failure except transport cancellation
            if (failureType != FailureType.TRANSIENT_TRANSPORT_FAILURE) {
                cbState.consecutiveFailures++
                if (cbState.consecutiveFailures >= DEGRADE_THRESHOLD) {
                    cbState.degradedUntilMs = SystemClock.elapsedRealtime() + DEGRADE_DURATION_MS
                    Log.w("GeminiService", "[$requestId] CIRCUIT_BREAKER_DEGRADE model=$model consecutiveFailures=${cbState.consecutiveFailures} degradedUntilMs=${cbState.degradedUntilMs}")
                    PipelineTrace.dataQuality(
                        stage = "gemini_service",
                        issue = PipelineEvents.CIRCUIT_BREAKER_DEGRADE,
                        details = mapOf(
                            "requestId" to requestId,
                            "model" to model,
                            "consecutiveFailures" to cbState.consecutiveFailures,
                            "degradeDurationMs" to DEGRADE_DURATION_MS
                        ),
                        correlationId = requestId
                    )
                }
            }

            PipelineTrace.dataQuality(
                stage = "gemini_service",
                issue = PipelineEvents.ATTEMPT_END,
                details = mapOf(
                    "requestId" to requestId,
                    "model" to model,
                    "outcome" to outcome,
                    "attemptElapsedMs" to (attemptEnd - attemptStart),
                    "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed),
                    "timeoutSource" to "NONE"
                ),
                correlationId = requestId
            )

            if (shouldStop) break

            delay(delayForAttempt(index))
        }

        PipelineTrace.dataQuality(
            stage = "gemini_service",
            issue = PipelineEvents.TRYCHAIN_END,
            details = mapOf(
                "requestId" to requestId,
                "shouldStop" to shouldStop,
                "blacklistedCount" to blacklistedModels.size,
                "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed),
                "timeoutSource" to if (SystemClock.elapsedRealtime() > budgetDeadlineMs) "BUDGET" else "NONE"
            ),
            correlationId = requestId
        )

        return null
    }

    private suspend fun tryModel(
        requestId: String,
        model: String,
        attemptIndex: Int,
        aiRequest: AiRequest,
        metrics: AttemptMetrics,
        transportAttempt: String,
        requestStartElapsed: Long,
        onModelResult: (ModelAttemptResult) -> Unit = {}
    ): String? {

        val attemptStart = SystemClock.elapsedRealtime()
        val requestStartMs = System.currentTimeMillis()
        val promptHash = computePromptHash(aiRequest.systemPrompt, aiRequest.userPrompt)

        val json = JSONObject().apply {
            put("model", model)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", aiRequest.systemPrompt)
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", aiRequest.userPrompt)
                })
            })
            put("max_tokens", aiRequest.maxTokens)
            put("temperature", aiRequest.temperature)
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

            PipelineTrace.dataQuality(
                stage = "gemini_service",
                issue = PipelineEvents.HTTP_REQUEST_BEGIN,
                details = mapOf(
                    "requestId" to requestId,
                    "transportAttempt" to transportAttempt,
                    "model" to model,
                    "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
                ),
                correlationId = requestId
            )

            call.enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    if (metrics.firstCallbackMs == 0L) metrics.firstCallbackMs = SystemClock.elapsedRealtime()
                    val durationMs = SystemClock.elapsedRealtime() - attemptStart
                    val latencyMs = if (metrics.firstCallbackMs != 0L) metrics.firstCallbackMs - attemptStart else null
                    val msg = e.message ?: ""
                    val isReadTimeout = msg.contains("timeout", ignoreCase = true) ||
                            msg.contains("Read timed out", ignoreCase = true)
                    val isTransportCancellation = msg.contains("CANCEL", ignoreCase = true) ||
                            msg.contains("reset", ignoreCase = true) ||
                            msg.contains("Socket closed", ignoreCase = true) ||
                            msg.contains("Canceled", ignoreCase = true)
                    val errorType = when {
                        isReadTimeout -> "timeout"
                        isTransportCancellation -> "cancelled"
                        else -> "network"
                    }

                    PipelineTrace.dataQuality(
                        stage = "gemini_service",
                        issue = PipelineEvents.HTTP_REQUEST_FAILED,
                        details = mapOf(
                            "requestId" to requestId,
                            "transportAttempt" to transportAttempt,
                            "model" to model,
                            "errorType" to errorType,
                            "httpElapsedMs" to (if (metrics.firstCallbackMs != 0L) metrics.firstCallbackMs - attemptStart else 0),
                            "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
                        ),
                        correlationId = requestId
                    )

                    if (isTransportCancellation) {
                        metrics.cancelReason = msg
                    }
                    onModelResult(ModelAttemptResult(
                        requestId = requestId,
                        model = model,
                        attempt = attemptIndex + 1,
                        failureType = errorType,
                        httpCode = if (metrics.httpCode != 0) metrics.httpCode else null,
                        durationMs = durationMs,
                        latencyMs = latencyMs,
                        bodyPreview = null,
                        detail = msg,
                        cancelReason = if (isTransportCancellation) metrics.cancelReason else null
                    ))

                    // Path B: transport failure — insert forensic artifact
                    val transportFailureType = when {
                        isReadTimeout -> "TIMEOUT"
                        isTransportCancellation -> "CANCELLED"
                        else -> "TRANSPORT_FAILURE"
                    }
                    val artifactTimestamp = System.currentTimeMillis()
                    runBlocking(Dispatchers.IO) {
                        artifactDao.insertWithRetention(
                            AiRawResponseArtifact(
                                correlationId = requestId,
                                attemptNumber = attemptIndex + 1,
                                model = model,
                                provider = "openrouter",
                                timestamp = artifactTimestamp,
                                requestStartMs = requestStartMs,
                                requestDurationMs = artifactTimestamp - requestStartMs,
                                httpCode = metrics.httpCode,
                                finishReason = "TRANSPORT_FAILURE",
                                promptHash = promptHash,
                                bodyLength = 0,
                                rawResponse = "",
                                failureType = transportFailureType,
                                failureStage = "TRANSPORT"
                            )
                        )
                    }

                    if (cont.isActive) cont.resume(null)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (metrics.firstCallbackMs == 0L) metrics.firstCallbackMs = SystemClock.elapsedRealtime()
                    val durationMs = SystemClock.elapsedRealtime() - attemptStart
                    val latencyMs = if (metrics.firstCallbackMs != 0L) metrics.firstCallbackMs - attemptStart else null
                    metrics.httpCode = response.code

                    PipelineTrace.dataQuality(
                        stage = "gemini_service",
                        issue = PipelineEvents.HTTP_RESPONSE_RECEIVED,
                        details = mapOf(
                            "requestId" to requestId,
                            "transportAttempt" to transportAttempt,
                            "model" to model,
                            "httpCode" to metrics.httpCode,
                            "httpElapsedMs" to (if (metrics.firstCallbackMs != 0L) metrics.firstCallbackMs - attemptStart else 0),
                            "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
                        ),
                        correlationId = requestId
                    )

                    val body = response.body?.string()
                    val code = response.code

                    // Path A: HTTP response received — insert forensic artifact before any processing
                    val artifactTimestamp = System.currentTimeMillis()
                    val bodyStr = body ?: ""
                    runBlocking(Dispatchers.IO) {
                        artifactDao.insertWithRetention(
                            AiRawResponseArtifact(
                                correlationId = requestId,
                                attemptNumber = attemptIndex + 1,
                                model = model,
                                provider = "openrouter",
                                timestamp = artifactTimestamp,
                                requestStartMs = requestStartMs,
                                requestDurationMs = artifactTimestamp - requestStartMs,
                                httpCode = code,
                                finishReason = if (code == 200) "STOP" else "TRANSPORT_FAILURE",
                                promptHash = promptHash,
                                bodyLength = body?.length ?: 0,
                                rawResponse = bodyStr,
                                failureType = "UNKNOWN",
                                failureStage = "NONE"
                            )
                        )
                    }

                    if (!cont.isActive) return

                    // RAW_RESPONSE_ARTIFACT: capture full body before any downstream processing
                    if (body != null) {
                        val truncated = body.length > RAW_ARTIFACT_MAX_BYTES
                        PipelineTrace.dataQuality(
                            stage = "gemini_service",
                            issue = PipelineEvents.RAW_RESPONSE_ARTIFACT,
                            details = mapOf(
                                "requestId" to requestId,
                                "model" to model,
                                "transportAttempt" to transportAttempt,
                                "httpCode" to code,
                                "rawBody" to (if (truncated) body.take(RAW_ARTIFACT_MAX_BYTES) else body),
                                "originalLength" to body.length,
                                "truncated" to truncated
                            ),
                            correlationId = requestId
                        )
                    }

                    if (code == 200) {
                        val bodyPreview = bodyStr.take(500)

                        // Transport-layer response body validation
                        if (body.isNullOrBlank()) {
                            Log.e("GeminiService", "[$requestId] [$model] EMPTY_BODY code=$code")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "EMPTY_BODY",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "HTTP 200 with empty body",
                                cancelReason = null
                            ))
                            cont.resume(null)
                            return
                        }

                        if (bodyStr.trim().length < 50) {
                            Log.e("GeminiService", "[$requestId] [$model] MALFORMED_SHORT length=${bodyStr.length}")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "MALFORMED_SHORT",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "HTTP 200 body too short: ${bodyStr.length} chars",
                                cancelReason = null
                            ))
                            cont.resume(null)
                            return
                        }

                        if (!bodyStr.contains("{")) {
                            Log.e("GeminiService", "[$requestId] [$model] NO_JSON_STRUCTURE")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "NO_JSON_STRUCTURE",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "HTTP 200 body contains no JSON object start",
                                cancelReason = null
                            ))
                            cont.resume(null)
                            return
                        }

                        val payload: JSONObject = try {
                            JSONObject(bodyStr)
                        } catch (e: Exception) {
                            Log.e("GeminiService", "[$requestId] [$model] INVALID_JSON code=$code error=${e.message} body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "invalid_json",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = e.message,
                                cancelReason = null
                            ))
                            cont.resume(null)
                            return
                        }

                        if (payload.has("error")) {
                            val errorObj = payload.getJSONObject("error")
                            val errorMsg = errorObj.optString("message", errorObj.toString())
                            Log.e("GeminiService", "[$requestId] [$model] PROVIDER_ERROR code=$code message=$errorMsg body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "provider_error",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = errorMsg,
                                cancelReason = null
                            ))
                            cont.resume(null)
                            return
                        }

                        if (!payload.has("choices")) {
                            val keys = mutableListOf<String>().also { list ->
                                val iter = payload.keys()
                                while (iter.hasNext()) list += iter.next()
                            }
                            Log.e("GeminiService", "[$requestId] [$model] MODEL_CONTRACT_VIOLATION stage=VALIDATION code=$code keys=$keys body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "MODEL_CONTRACT_VIOLATION",
                                failureStage = "VALIDATION",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "No value for choices",
                                cancelReason = null
                            ))
                            cont.resume(null)
                            return
                        }

                        try {
                            val raw = payload
                                .getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content")

                            val extracted = extractContent(raw)

                            PipelineTrace.dataQuality(
                                stage = "gemini_service",
                                issue = PipelineEvents.EXTRACTION_RESULT,
                                details = mapOf(
                                    "requestId" to requestId,
                                    "model" to model,
                                    "strategy" to extracted.strategy,
                                    "rawLength" to raw.length,
                                    "extractedLength" to extracted.content.length,
                                    "transportAttempt" to transportAttempt
                                ),
                                correlationId = requestId
                            )

                            val parseResult = try {
                                JSONObject(extracted.content)
                                true to null
                            } catch (e: Exception) {
                                false to e::class.java.simpleName
                            }
                            val parseSuccess = parseResult.first
                            val parseErrorClass = parseResult.second

                            PipelineTrace.dataQuality(
                                stage = "gemini_service",
                                issue = PipelineEvents.PARSE_RESULT,
                                details = mapOf(
                                    "requestId" to requestId,
                                    "model" to model,
                                    "parseSuccess" to parseSuccess,
                                    "errorClass" to (parseErrorClass ?: ""),
                                    "extractedLength" to extracted.content.length
                                ),
                                correlationId = requestId
                            )

                            if (!parseSuccess) {
                                val stage = if (extracted.strategy == "failed") "EXTRACTION" else "PARSE"
                                throw Exception("MODEL_CONTRACT_VIOLATION stage=$stage")
                            }

                            Log.d("GeminiService", "[$requestId] [$model] SUCCESS strategy=${extracted.strategy} contentLength=${extracted.content.length}")
                            cont.resume(extracted.content)

                        } catch (e: Exception) {
                            val msg = e.message ?: ""
                            val contractStage = if (msg.startsWith("MODEL_CONTRACT_VIOLATION")) {
                                msg.substringAfter("stage=").substringBefore(" ")
                            } else {
                                "EXTRACTION"
                            }
                            val keys = mutableListOf<String>().also { list ->
                                val iter = payload.keys()
                                while (iter.hasNext()) list += iter.next()
                            }
                            Log.e("GeminiService", "[$requestId] [$model] MODEL_CONTRACT_VIOLATION stage=$contractStage error=${e.message} keys=$keys body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "MODEL_CONTRACT_VIOLATION",
                                failureStage = contractStage,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = e.message,
                                cancelReason = null
                            ))
                            cont.resume(null)
                        }
                        return
                    }

                    val bodyPreview = body?.take(200) ?: ""
                    when (code) {
                        404 -> {
                            Log.w("GeminiService", "[$requestId] [$model] HTTP_404 body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "404",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = null,
                                cancelReason = null
                            ))
                        }
                        429 -> {
                            Log.w("GeminiService", "[$requestId] [$model] HTTP_429 body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "429",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = null,
                                cancelReason = null
                            ))
                        }
                        else -> {
                            Log.w("GeminiService", "[$requestId] [$model] HTTP_${code} body=$bodyPreview")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = "http_$code",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = null,
                                cancelReason = null
                            ))
                        }
                    }
                    cont.resume(null)
                }
            })
        }
    }
}
