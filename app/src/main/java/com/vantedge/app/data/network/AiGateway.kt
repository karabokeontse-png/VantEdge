package com.vantedge.app.data.network

import android.os.SystemClock
import android.util.Log
import com.vantedge.app.domain.PipelineTrace
import kotlin.math.min
import java.util.concurrent.atomic.AtomicInteger

class AiGateway(
    private val geminiService: GeminiService
) : AiTextGateway {
    companion object {
        private val requestCounter = AtomicInteger(0)
    }

    override suspend fun generate(
        pipeline: String,
        request: AiRequest,
        budgetMs: Long
    ): String? {
        require(budgetMs > 0) {
            "budgetMs must be explicitly provided. Infinite budgets are forbidden."
        }
        val effectiveBudget = min(budgetMs, 120_000L)
        val requestId = String.format("REQ-%06d", requestCounter.incrementAndGet())
        val correlationId = requestId
        Log.i("AiGateway", "[$pipeline] [$requestId] ENTRY promptLength=${request.userPrompt.length}")

        PipelineTrace.entry(pipeline, mapOf(
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
                stage = pipeline,
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

        val budgetDeadlineMs = SystemClock.elapsedRealtime() + effectiveBudget
        val result = geminiService.generate(
            requestId = requestId,
            pipeline = pipeline,
            request = request,
            onModelResult = onModelResult,
            budgetDeadlineMs = budgetDeadlineMs
        )

        val totalElapsed = System.currentTimeMillis() - startTime

        if (result != null) {
            Log.i("AiGateway", "[$pipeline] [$requestId] EXIT duration=${totalElapsed}ms success=true resultLength=${result.length}")

            PipelineTrace.exit(
                stage = pipeline,
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
        } else {
            Log.i("AiGateway", "[$pipeline] [$requestId] EXIT duration=${totalElapsed}ms success=false")

            PipelineTrace.error(
                stage = pipeline,
                reason = "All model attempts exhausted without success",
                correlationId = correlationId
            )

            PipelineTrace.exit(
                stage = pipeline,
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
        }

        return result
    }

}