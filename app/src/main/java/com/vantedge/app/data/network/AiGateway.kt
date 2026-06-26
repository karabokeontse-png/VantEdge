package com.vantedge.app.data.network

import android.os.SystemClock
import android.util.Log
import com.vantedge.app.domain.PipelineTrace
import java.util.concurrent.atomic.AtomicInteger

class AiGateway(
    private val geminiService: GeminiService
) {
    companion object {
        private val requestCounter = AtomicInteger(0)
    }

    suspend fun generate(
        tag: String,
        request: AiRequest,
        onProgress: (String) -> Unit = {}
    ): String? {
        val requestId = String.format("REQ-%06d", requestCounter.incrementAndGet())
        val correlationId = requestId
        Log.i("AiGateway", "[$tag] [$requestId] ENTRY promptLength=${request.userPrompt.length}")

        PipelineTrace.entry(tag, mapOf(
            "correlationId" to correlationId,
            "requestId" to requestId,
            "promptLength" to request.userPrompt.length,
            "systemPromptLength" to request.systemPrompt.length,
            "maxTokens" to request.maxTokens,
            "temperature" to request.temperature
        ))

        val startTime = System.currentTimeMillis()

        // Store ALL attempts, not just last failure
        val transports = mutableListOf<ModelAttemptResult>()
        var attemptCount = 0
        var successfulAttempts = 0
        var failedAttempts = 0

        val onModelResult: (ModelAttemptResult) -> Unit = { result ->
            attemptCount++
            if (result.failureType == null) {
                successfulAttempts++
            } else {
                failedAttempts++
            }
            transports += result
            PipelineTrace.dataQuality(
                stage = tag,
                issue = "model_attempt",
                details = mapOf(
                    "requestId" to requestId,
                    "model" to result.model,
                    "status" to if (result.failureType == null) "success" else "failed",
                    "failureType" to (result.failureType ?: ""),
                    "httpCode" to (result.httpCode ?: ""),
                    "durationMs" to result.durationMs,
                    "detail" to (result.detail ?: "")
                ),
                correlationId = correlationId
            )
        }

        val budgetDeadlineMs = SystemClock.elapsedRealtime() + 120_000L
        val result = geminiService.generate(
            requestId = requestId,
            request = request,
            onProgress = onProgress,
            onModelResult = onModelResult,
            budgetDeadlineMs = budgetDeadlineMs
        )

        val totalElapsed = System.currentTimeMillis() - startTime

        // Build AiResult internally for Phase 3A validation
        val aiResult = if (result != null) {
            Log.i("AiGateway", "[$tag] [$requestId] EXIT duration=${totalElapsed}ms success=true resultLength=${result.length}")

            PipelineTrace.exit(
                stage = tag,
                durationMs = totalElapsed,
                summary = mapOf(
                    "correlationId" to correlationId,
                    "requestId" to requestId,
                    "success" to true,
                    "resultLength" to result.length,
                    "terminationReason" to "SUCCESS",
                    "attemptCount" to attemptCount,
                    "successfulAttempts" to successfulAttempts,
                    "failedAttempts" to failedAttempts
                )
            )

            AiResult.Success(result)
        } else {
            Log.i("AiGateway", "[$tag] [$requestId] EXIT duration=${totalElapsed}ms success=false")

            PipelineTrace.error(
                stage = tag,
                reason = "All model attempts exhausted without success",
                correlationId = correlationId
            )

            PipelineTrace.exit(
                stage = tag,
                durationMs = totalElapsed,
                summary = mapOf(
                    "correlationId" to correlationId,
                    "requestId" to requestId,
                    "success" to false,
                    "result" to "null",
                    "terminationReason" to "ALL_MODELS_FAILED",
                    "attemptCount" to attemptCount,
                    "successfulAttempts" to successfulAttempts,
                    "failedAttempts" to failedAttempts
                )
            )

            val lastFailure = transports.lastOrNull { it.failureType != null }
            AiResult.Failure(
                category = classifyFailure(lastFailure?.failureType),
                transport = lastFailure ?: ModelAttemptResult(
                    requestId = requestId,
                    model = "unknown",
                    attempt = 0,
                    failureType = "unknown",
                    httpCode = null,
                    durationMs = 0L,
                    latencyMs = null,
                    bodyPreview = null,
                    detail = "No transport result captured",
                    cancelReason = null
                ),
                totalElapsedMs = totalElapsed
            )
        }

        Log.d("AiGateway", "[$tag] [$requestId] AiResult=$aiResult")

        return result
    }

    suspend fun generate(
        request: AiRequest,
        onProgress: (String) -> Unit = {}
    ): String? {
        return generate("default", request, onProgress)
    }

    // PRIVATE MAPPER
    private fun classifyFailure(errorType: String?): AiFailureCategory = when (errorType) {
        "timeout" -> AiFailureCategory.TIMEOUT
        "cancelled" -> AiFailureCategory.TRANSPORT
        "network" -> AiFailureCategory.TRANSPORT
        "empty" -> AiFailureCategory.EMPTY_RESPONSE
        "invalid_json" -> AiFailureCategory.PARSE_ERROR
        "provider_error" -> AiFailureCategory.PROVIDER_ERROR
        "schema_error" -> AiFailureCategory.PARSE_ERROR
        "parse" -> AiFailureCategory.PARSE_ERROR
        "404" -> AiFailureCategory.CONFIG_ERROR
        "429" -> AiFailureCategory.RATE_LIMIT
        else -> AiFailureCategory.OTHER
    }
}
