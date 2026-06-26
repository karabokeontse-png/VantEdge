package com.vantedge.app.data.network

data class AiRequest(
    val systemPrompt: String,
    val userPrompt: String,
    val temperature: Double = 0.2,
    val maxTokens: Int = 8192
)
