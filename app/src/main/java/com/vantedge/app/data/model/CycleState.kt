package com.vantedge.app.data.model

sealed class CycleState {
    data class AnalysisOnly(
        val compatibility: CompatibilityRecord
    ) : CycleState()

    data class GenerationReady(
        val compatibility: CompatibilityRecord,
        val matchedKeywords: List<String>,
        val coverLetterBody: String
    ) : CycleState()

    data class FullCycle(
        val compatibility: CompatibilityRecord,
        val cvContent: String,
        val coverLetterContent: String,
        val design: DesignConfig
    ) : CycleState()
}
