package com.vantedge.app.data.network

import android.util.Log

class AiGateway(
    private val geminiService: GeminiService
) {
    suspend fun generate(
        tag: String,
        systemInstruction: String,
        userPrompt: String,
        onProgress: (String) -> Unit = {}
    ): String? {
        Log.d("AiGateway", "[$tag] ENTRY promptLength=${userPrompt.length}")
        val startTime = System.currentTimeMillis()

        val result = geminiService.generate(userPrompt, onProgress)

        val elapsed = System.currentTimeMillis() - startTime
        Log.d("AiGateway", "[$tag] EXIT duration=${elapsed}ms success=${result != null}")
        return result
    }
}
