package com.vantedge.app.data.network

sealed class AiResult {
    data class Success(val rawResponse: String) : AiResult()
    data class Failure(
        val category: AiFailureCategory,
        val transport: ModelAttemptResult,
        val totalElapsedMs: Long
    ) : AiResult()
}

enum class AiFailureCategory {
    TIMEOUT,
    RATE_LIMIT,
    CONFIG_ERROR,
    TRANSPORT,
    EMPTY_RESPONSE,
    PARSE_ERROR,
    PROVIDER_ERROR,
    OTHER
}
