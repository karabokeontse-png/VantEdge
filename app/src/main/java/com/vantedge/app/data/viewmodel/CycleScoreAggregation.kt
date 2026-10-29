package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.domain.ScoreCompleteness
import com.vantedge.app.data.model.GenerationCycle

object CycleScoreAggregation {

    fun completeScore(cycle: GenerationCycle): Int? =
        cycle.compatibility
            ?.takeIf { ScoreCompleteness.isAuthoritative(it) }
            ?.score

    fun previousScore(cycles: List<GenerationCycle>, cycle: GenerationCycle): Int? {
        if (completeScore(cycle) == null) return null
        val currentVersion = cycle.version ?: return null
        if (currentVersion <= 1) return null
        return cycles
            .filter { other ->
                other.id != cycle.id &&
                    other.jobTitle.equals(cycle.jobTitle, ignoreCase = true) &&
                    other.company.equals(cycle.company, ignoreCase = true) &&
                    other.version == currentVersion - 1
            }
            .mapNotNull { completeScore(it) }
            .firstOrNull()
    }

    fun improvedApplicationsCount(cycles: List<GenerationCycle>): Int =
        cycles
            .filter { it.isVisibleInHistory }
            .count { cycle ->
                val current = completeScore(cycle)
                val previous = previousScore(cycles, cycle)
                current != null && previous != null && current > previous
            }

    fun averageScore(cycles: List<GenerationCycle>): Int? {
        val scores = cycles
            .filter { it.isVisibleInHistory }
            .mapNotNull { completeScore(it) }
        return if (scores.isEmpty()) null else scores.average().toInt()
    }

    fun bestScoringRole(cycles: List<GenerationCycle>): String? =
        cycles
            .filter { it.isVisibleInHistory }
            .mapNotNull { cycle ->
                completeScore(cycle)?.let { score -> cycle.jobTitle to score }
            }
            .maxByOrNull { it.second }
            ?.first

    fun incompleteScoreCount(cycles: List<GenerationCycle>): Int =
        cycles.count { it.isVisibleInHistory && completeScore(it) == null }
}
