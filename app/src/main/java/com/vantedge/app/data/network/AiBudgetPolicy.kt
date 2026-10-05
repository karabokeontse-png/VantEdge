package com.vantedge.app.data.network

/**
 * Centralized timeout policy for AI gateway operations.
 * Callers select intent; AiGateway owns execution timing.
 */
enum class AiBudgetPolicy(
    internal val budgetMs: Long
) {
    NARRATIVE(60_000L),      // Compatibility narrative generation
    EXTRACTION(120_000L),    // Profile and Job extraction
    GENERATION(120_000L)     // CV and Cover Letter generation
}
