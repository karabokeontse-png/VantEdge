package com.vantedge.app.data.network

import android.util.Log
import java.util.concurrent.atomic.AtomicInteger

class AiGateway(
    private val geminiService: GeminiService
) {
    companion object {
        private val requestCounter = AtomicInteger(0)
    }

    suspend fun generate(
        tag: String,
        userPrompt: String,
        onProgress: (String) -> Unit = {}
    ): String? {
        val requestId = String.format("REQ-%06d", requestCounter.incrementAndGet())
        Log.i("AiGateway", "[$tag] [$requestId] ENTRY promptLength=${userPrompt.length}")
        val startTime = System.currentTimeMillis()

        val onModelResult: (String, String?, String?) -> Unit = { model, errorType, detail ->
            if (errorType == null) {
                Log.i("AiGateway", "[$tag] [$requestId] MODEL_ATTEMPT model=$model status=success")
            } else {
                Log.w("AiGateway", "[$tag] [$requestId] MODEL_ATTEMPT model=$model status=failed error=$errorType" +
                    (if (detail != null) " detail=$detail" else ""))
                Log.w("AiGateway", "[$tag] [$requestId] ERROR_CLASSIFICATION model=$model type=$errorType" +
                    (if (detail != null) " detail=$detail" else ""))
            }
        }

        val result = geminiService.generate(requestId, userPrompt, onProgress, onModelResult)

        val elapsed = System.currentTimeMillis() - startTime
        Log.i("AiGateway", "[$tag] [$requestId] EXIT duration=${elapsed}ms success=${result != null}")
        return result
    }

    suspend fun generate(
        prompt: String,
        onProgress: (String) -> Unit = {}
    ): String? {
        return generate("default", prompt, onProgress)
    }
}
