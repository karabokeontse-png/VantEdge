package com.vantedge.observability.p2.adapter

import com.vantedge.observability.p2.model.ExecutionMetadata
import com.vantedge.observability.p2.model.ObservabilityTraceModel
import com.vantedge.observability.p2.model.RuleEvaluation
import com.vantedge.pipeline.validation.RuleResult
import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.pipeline.validation.ValidationTrace

object P2TraceAdapter {

    fun adapt(trace: ValidationTrace): ObservabilityTraceModel {
        val evaluations = trace.rules.map { r -> mapRuleResult(r) }
        val decisionOutcome = classifyDecisionOutcome(trace)
        return ObservabilityTraceModel(
            ruleEvaluations = evaluations,
            decisionOutcome = decisionOutcome,
            inputSnapshotHash = "",
            correlationId = trace.correlationId,
            executionMetadata = buildMetadata(trace, evaluations)
        )
    }

    fun adaptWithDecision(
        trace: ValidationTrace,
        decision: ValidationDecision
    ): ObservabilityTraceModel {
        val evaluations = trace.rules.map { r -> mapRuleResult(r) }
        val decisionOutcome = when (decision) {
            is ValidationDecision.Accept -> "ACCEPT"
            is ValidationDecision.Degraded -> "DEGRADED"
            is ValidationDecision.Reject -> "REJECT"
        }
        return ObservabilityTraceModel(
            ruleEvaluations = evaluations,
            decisionOutcome = decisionOutcome,
            inputSnapshotHash = "",
            correlationId = trace.correlationId,
            executionMetadata = buildMetadata(trace, evaluations)
        )
    }

    private fun mapRuleResult(r: RuleResult): RuleEvaluation {
        return RuleEvaluation(
            ruleName = r.ruleName,
            field = r.field,
            passed = r.passed,
            severity = r.severity,
            detail = r.detail
        )
    }

    private fun classifyDecisionOutcome(trace: ValidationTrace): String {
        val blockers = trace.rules.filter { it.severity == "BLOCKER" && !it.passed }
        if (blockers.isNotEmpty()) return "REJECT"
        val warnings = trace.rules.filter { !it.passed && it.severity != "BLOCKER" }
        if (warnings.isNotEmpty()) return "DEGRADED"
        return "ACCEPT"
    }

    private fun buildMetadata(
        trace: ValidationTrace,
        evaluations: List<RuleEvaluation>
    ): ExecutionMetadata {
        val failedCount = evaluations.count { !it.passed && it.severity == "BLOCKER" }
        val warningCount = evaluations.count { !it.passed && it.severity != "BLOCKER" }
        return ExecutionMetadata(
            pipelineStage = trace.pipelineStage,
            ruleCount = evaluations.size,
            failedRuleCount = failedCount,
            warningRuleCount = warningCount
        )
    }
}
