package com.vantedge.app.data.model

data class CompatibilityRecord(
    val id: Long = System.currentTimeMillis(),
    val jobTitle: String,
    val company: String,
    val jobDescription: String,
    val score: Int,
    val vacancyScore: Int = 0,      // score specifically against vacancy requirements
    val roleSummary: String,
    val eligibilitySummary: String,
    val dataIntegrityNote: String = "",
    val profileStats: ProfileStats,
    val qualificationRatio: QualificationRatio = QualificationRatio(0, 0, 0),
    val relevancyItems: List<RelevancyItem>,
    val gaps: List<GapItem>,
    val criticalGapCount: Int,
    val createdAt: Long = System.currentTimeMillis()
)