package com.vantedge.observability.p2.hash

import com.vantedge.observability.p2.model.ObservabilityTraceModel
import java.security.MessageDigest

object TraceHashCalculator {

    fun compute(trace: ObservabilityTraceModel): String {
        val canonical = buildCanonicalString(trace)
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(canonical.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }

    private fun buildCanonicalString(trace: ObservabilityTraceModel): String {
        val sb = StringBuilder()
        sb.appendLine("decision=${trace.decisionOutcome}")
        for (e in trace.ruleEvaluations) {
            sb.appendLine("rule=${e.ruleName}|field=${e.field}|passed=${e.passed}|severity=${e.severity}|detail=${e.detail ?: ""}")
        }
        sb.appendLine("inputHash=${trace.inputSnapshotHash}")
        return sb.toString()
    }
}
