package com.vantedge.observability.p2.diff

import com.vantedge.observability.p2.model.ObservabilityTraceModel

data class TraceDiff(
    val addedRules: List<String>,
    val removedRules: List<String>,
    val changedDecisions: List<String>,
    val divergencePoint: String?
)

object TraceDiffEngine {

    fun diff(
        a: ObservabilityTraceModel,
        b: ObservabilityTraceModel
    ): TraceDiff {
        val aRulesByName = a.ruleEvaluations.associateBy { it.ruleName }
        val bRulesByName = b.ruleEvaluations.associateBy { it.ruleName }

        val aNames = aRulesByName.keys
        val bNames = bRulesByName.keys

        val added = (bNames - aNames).toList().sorted()
        val removed = (aNames - bNames).toList().sorted()

        val changed = mutableListOf<String>()
        var divergence: String? = null

        for (name in aNames.intersect(bNames).sorted()) {
            val ra = aRulesByName[name]!!
            val rb = bRulesByName[name]!!
            if (ra.passed != rb.passed || ra.severity != rb.severity || ra.detail != rb.detail) {
                changed.add(name)
                if (divergence == null) {
                    divergence = name
                }
            }
        }

        if (divergence == null && added.isNotEmpty()) {
            divergence = added.first()
        }
        if (divergence == null && removed.isNotEmpty()) {
            divergence = removed.first()
        }

        return TraceDiff(
            addedRules = added,
            removedRules = removed,
            changedDecisions = changed,
            divergencePoint = divergence
        )
    }
}
