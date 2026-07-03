package com.vantedge.pipeline.validation

sealed class ValidationDecision {
    data class Accept(val trace: ValidationTrace) : ValidationDecision()
    data class Degraded(
        val trace: ValidationTrace,
        val warnings: List<String>
    ) : ValidationDecision()
    data class Reject(
        val reason: String,
        val trace: ValidationTrace
    ) : ValidationDecision()
}
