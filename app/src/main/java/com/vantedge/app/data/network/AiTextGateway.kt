package com.vantedge.app.data.network

interface AiTextGateway {
    suspend fun generate(
        pipeline: String,
        request: AiRequest,
        budgetMs: Long
    ): String?
}
