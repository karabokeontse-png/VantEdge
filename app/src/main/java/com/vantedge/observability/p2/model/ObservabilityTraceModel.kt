package com.vantedge.observability.p2.model

data class RuleEvaluation(
    val ruleName: String,
    val field: String,
    val passed: Boolean,
    val severity: String,
    val detail: String? = null
)

data class ObservabilityTraceModel(
    val ruleEvaluations: List<RuleEvaluation>,
    val decisionOutcome: String,
    val inputSnapshotHash: String,
    val correlationId: String,
    val executionMetadata: ExecutionMetadata
)

data class ExecutionMetadata(
    val pipelineStage: String,
    val ruleCount: Int,
    val failedRuleCount: Int,
    val warningRuleCount: Int
)
