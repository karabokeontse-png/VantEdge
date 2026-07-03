package com.vantedge.pipeline.validation

data class RuleResult(
    val ruleName: String,
    val field: String,
    val passed: Boolean,
    val severity: String,
    val detail: String? = null
)

data class ValidationTrace(
    val pipelineStage: String,
    val correlationId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val rules: MutableList<RuleResult> = mutableListOf()
) {
    fun addResult(result: RuleResult) {
        rules.add(result)
    }
}
