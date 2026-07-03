package com.vantedge.app.data.network

import android.os.SystemClock
import android.util.Base64
import android.util.Log
import com.vantedge.app.BuildConfig
import com.vantedge.app.data.engine.extraction.ExtractionResult
import com.vantedge.app.data.engine.extraction.JsonExtractionEngine
import com.vantedge.app.data.model.AiRawResponseArtifact
import com.vantedge.app.data.storage.AiRawResponseArtifactDao
import com.vantedge.app.domain.PipelineEvents
import com.vantedge.app.domain.PipelineTrace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
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

enum class RequestFailureType {
    TIMEOUT,
    NETWORK,
    CANCELLED,
    TRANSPORT_FAILURE,
    EMPTY_BODY,
    MALFORMED_SHORT,
    NO_JSON_STRUCTURE,
    INVALID_JSON,
    PROVIDER_ERROR,
    MODEL_CONTRACT_VIOLATION,
    HTTP_404,
    HTTP_429,
    HTTP_ERROR,
    UNKNOWN
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

    private val bridgePersistenceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json".toMediaType()

    private val apiKey = BuildConfig.OPENROUTER_API_KEY
    private val baseUrl = "https://openrouter.ai/api/v1/chat/completions"

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val RAW_ARTIFACT_MAX_BYTES = 100 * 1024
        private const val RAW_BODY_LOG_LIMIT = 8192
        private const val DEGRADE_THRESHOLD = 3
        private const val DEGRADE_DURATION_MS = 15 * 60 * 1000L

        private fun computePromptHash(systemPrompt: String, userPrompt: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update((systemPrompt + userPrompt).toByteArray(Charsets.UTF_8))
            return Base64.encodeToString(digest.digest(), Base64.NO_WRAP).take(16)
        }

        private fun extractContent(raw: String): ExtractionResult {
            return JsonExtractionEngine.extract(raw)
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

    private fun classifyError(errorType: String?, stage: String = "gemini_service", correlationId: String? = null): FailureType = when (errorType) {
        RequestFailureType.TIMEOUT.name, "timeout", "network" -> FailureType.TIMEOUT
        RequestFailureType.NETWORK.name -> FailureType.TIMEOUT
        RequestFailureType.CANCELLED.name, "cancelled" -> FailureType.TRANSIENT_TRANSPORT_FAILURE
        RequestFailureType.TRANSPORT_FAILURE.name -> FailureType.OTHER
        RequestFailureType.EMPTY_BODY.name -> FailureType.OTHER
        RequestFailureType.MALFORMED_SHORT.name -> FailureType.OTHER
        RequestFailureType.NO_JSON_STRUCTURE.name -> FailureType.OTHER
        RequestFailureType.INVALID_JSON.name -> FailureType.OTHER
        RequestFailureType.PROVIDER_ERROR.name -> FailureType.OTHER
        RequestFailureType.MODEL_CONTRACT_VIOLATION.name -> FailureType.OTHER
        RequestFailureType.HTTP_404.name, "404" -> FailureType.CONFIG_ERROR
        RequestFailureType.HTTP_429.name, "429" -> FailureType.RATE_LIMIT
        RequestFailureType.HTTP_ERROR.name -> FailureType.OTHER
        RequestFailureType.UNKNOWN.name, "unknown" -> FailureType.OTHER
        "empty", "http_400", "http_401", "http_403", "http_404" -> FailureType.CONFIG_ERROR
        else -> {
            PipelineTrace.warn(stage, "Unrecognized failure type: ${errorType ?: "null"}", correlationId)
            FailureType.OTHER
        }
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
        return tryChain(requestId, request, onProgress, onModelResult, blacklistedModels, requestStartElapsed, budgetDeadlineMs)
    }

    private suspend fun tryChain(
        requestId: String,
        request: AiRequest,
        onProgress: (String) -> Unit,
        onModelResult: (ModelAttemptResult) -> Unit,
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
                "blacklistedCount" to blacklistedModels.size,
                "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed)
            ),
            correlationId = requestId
        )

        var capturedErrorType: String? = null

        for (attempt in 1..MAX_RETRY_ATTEMPTS) {
            if (attempt > MAX_RETRY_ATTEMPTS) break
            val index = attempt - 1
            val model = models[index]

            // Budget check at iteration start (includes retry delays)
            if (SystemClock.elapsedRealtime() > budgetDeadlineMs) {
                val remainingMs = budgetDeadlineMs - SystemClock.elapsedRealtime()
                Log.w("GeminiService", "[$requestId] BUDGET_EXCEEDED at iteration start remainingMs=$remainingMs")
                PipelineTrace.dataQuality(
                    stage = "gemini_service",
                    issue = PipelineEvents.TRYCHAIN_END,
                    details = mapOf(
                        "requestId" to requestId,
                        "reason" to "BUDGET_EXCEEDED",
                        "canonicalFailureType" to "BUDGET_EXCEEDED",
                        "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed),
                        "remainingBudgetMs" to remainingMs,
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

            val metrics = AttemptMetrics()
            val attemptStart = SystemClock.elapsedRealtime()
            httpAttemptCounter++
            val transportAttempt = "HTTP-$httpAttemptCounter"

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
                val errorType = if (!modelAttempted.get()) RequestFailureType.TIMEOUT.name else capturedErrorType
                when (classifyError(errorType, "gemini_service", requestId)) {
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

            val errorType = if (!modelAttempted.get()) RequestFailureType.TIMEOUT.name else capturedErrorType
            val failureType = classifyError(errorType, "gemini_service", requestId)

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

            val canonicalFailure = capturedErrorType ?: RequestFailureType.TIMEOUT.name

            PipelineTrace.dataQuality(
                stage = "gemini_service",
                issue = PipelineEvents.ATTEMPT_END,
                details = mapOf(
                    "requestId" to requestId,
                    "model" to model,
                    "outcome" to outcome,
                    "canonicalFailureType" to canonicalFailure,
                    "attemptElapsedMs" to (attemptEnd - attemptStart),
                    "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed),
                    "timeoutSource" to "NONE"
                ),
                correlationId = requestId
            )

            if (shouldStop) break

            delay(delayForAttempt(index))

            // Budget check after delay to prevent sleeping past budget expiry
            if (SystemClock.elapsedRealtime() > budgetDeadlineMs) {
                val remainingMs = budgetDeadlineMs - SystemClock.elapsedRealtime()
                Log.w("GeminiService", "[$requestId] BUDGET_EXCEEDED after delay remainingMs=$remainingMs")
                PipelineTrace.dataQuality(
                    stage = "gemini_service",
                    issue = PipelineEvents.TRYCHAIN_END,
                    details = mapOf(
                        "requestId" to requestId,
                        "reason" to "BUDGET_EXCEEDED",
                        "canonicalFailureType" to "BUDGET_EXCEEDED",
                        "elapsedMs" to (SystemClock.elapsedRealtime() - requestStartElapsed),
                        "remainingBudgetMs" to remainingMs,
                        "timeoutSource" to "BUDGET"
                    ),
                    correlationId = requestId
                )
                shouldStop = true
            }
        }

        val lastCanonicalFailure = capturedErrorType ?: RequestFailureType.TIMEOUT.name
        PipelineTrace.dataQuality(
            stage = "gemini_service",
            issue = PipelineEvents.TRYCHAIN_END,
            details = mapOf(
                "requestId" to requestId,
                "shouldStop" to shouldStop,
                "blacklistedCount" to blacklistedModels.size,
                "canonicalFailureType" to lastCanonicalFailure,
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
        val completionGuard = java.util.concurrent.atomic.AtomicBoolean(false)
        var artifactPayload: AiRawResponseArtifact? = null

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

        val result: String? = suspendCancellableCoroutine { cont ->

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
                        isReadTimeout -> RequestFailureType.TIMEOUT.name
                        isTransportCancellation -> RequestFailureType.CANCELLED.name
                        else -> RequestFailureType.NETWORK.name
                    }

                    PipelineTrace.dataQuality(
                        stage = "gemini_service",
                        issue = PipelineEvents.HTTP_REQUEST_FAILED,
                        details = mapOf(
                            "requestId" to requestId,
                            "transportAttempt" to transportAttempt,
                            "model" to model,
                            "errorType" to errorType,
                            "canonicalFailureType" to errorType,
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

                    // Path B: transport failure — construct immutable forensic payload
                    val transportFailureType = when {
                        isReadTimeout -> RequestFailureType.TIMEOUT.name
                        isTransportCancellation -> RequestFailureType.CANCELLED.name
                        else -> RequestFailureType.TRANSPORT_FAILURE.name
                    }
                    val artifactTimestamp = System.currentTimeMillis()
                    artifactPayload = AiRawResponseArtifact(
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

                    if (completionGuard.compareAndSet(false, true)) {
                        cont.resume(null)
                    }
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

                    // Path A: HTTP response received — construct immutable forensic payload
                    val artifactTimestamp = System.currentTimeMillis()
                    val bodyStr = body ?: ""
                    artifactPayload = AiRawResponseArtifact(
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
                        failureType = RequestFailureType.UNKNOWN.name,
                        failureStage = "NONE"
                    )

                    if (!cont.isActive) return

                    // RAW_RESPONSE_ARTIFACT: single-line log-safe emission
                    val safeBody = body ?: ""
                    val leadingWhitespace = safeBody.length - safeBody.trimStart().length
                    val preview = safeBody.trimStart()
                        .replace("\r", "\\r")
                        .replace("\n", "\\n")
                        .replace("\t", "\\t")
                        .take(RAW_BODY_LOG_LIMIT)
                    val rawPreview = if (preview.isBlank()) "<EMPTY_OR_WHITESPACE_ONLY>" else preview
                    PipelineTrace.dataQuality(
                        stage = "gemini_service",
                        issue = PipelineEvents.RAW_RESPONSE_ARTIFACT,
                        details = mapOf(
                            "requestId" to requestId,
                            "model" to model,
                            "transportAttempt" to transportAttempt,
                            "httpCode" to code,
                            "rawLength" to safeBody.length,
                            "leadingWhitespaceChars" to leadingWhitespace,
                            "rawPreview" to rawPreview
                        ),
                        correlationId = requestId
                    )

                    if (code == 200) {
                        val bodyPreview = bodyStr.take(500)

                        // Transport-layer response body validation
                        if (body.isNullOrBlank()) {
                            Log.e("GeminiService", "[$requestId] [$model] EMPTY_BODY code=$code")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = RequestFailureType.EMPTY_BODY.name,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "HTTP 200 with empty body",
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
                            return
                        }

                        if (bodyStr.trim().length < 50) {
                            Log.e("GeminiService", "[$requestId] [$model] MALFORMED_SHORT length=${bodyStr.length}")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = RequestFailureType.MALFORMED_SHORT.name,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "HTTP 200 body too short: ${bodyStr.length} chars",
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
                            return
                        }

                        if (!bodyStr.contains("{")) {
                            Log.e("GeminiService", "[$requestId] [$model] NO_JSON_STRUCTURE")
                            onModelResult(ModelAttemptResult(
                                requestId = requestId,
                                model = model,
                                attempt = attemptIndex + 1,
                                failureType = RequestFailureType.NO_JSON_STRUCTURE.name,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "HTTP 200 body contains no JSON object start",
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
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
                                failureType = RequestFailureType.INVALID_JSON.name,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = e.message,
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
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
                                failureType = RequestFailureType.PROVIDER_ERROR.name,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = errorMsg,
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
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
                                failureType = RequestFailureType.MODEL_CONTRACT_VIOLATION.name,
                                failureStage = "VALIDATION",
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = "No value for choices",
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
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
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(extracted.content)
                            }

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
                                failureType = RequestFailureType.MODEL_CONTRACT_VIOLATION.name,
                                failureStage = contractStage,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = e.message,
                                cancelReason = null
                            ))
                            if (completionGuard.compareAndSet(false, true)) {
                                cont.resume(null)
                            }
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
                                failureType = RequestFailureType.HTTP_404.name,
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
                                failureType = RequestFailureType.HTTP_429.name,
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
                                failureType = RequestFailureType.HTTP_ERROR.name,
                                httpCode = code,
                                durationMs = durationMs,
                                latencyMs = latencyMs,
                                bodyPreview = bodyPreview,
                                detail = null,
                                cancelReason = null
                            ))
                        }
                    }
                    if (completionGuard.compareAndSet(false, true)) {
                        cont.resume(null)
                    }
                }
            })
        }

        val payload = artifactPayload
        if (payload != null) {
            bridgePersistenceScope.launch {
                artifactDao.insertWithRetention(payload)
            }
        }

        return result
    }
}
