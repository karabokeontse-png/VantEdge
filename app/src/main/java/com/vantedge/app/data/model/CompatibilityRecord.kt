package com.vantedge.app.data.model

/**
 * Phase D: identifies which subsystem produced the analytical fields on a
 * CompatibilityRecord.
 *
 * Only [DETERMINISTIC_W5] is ever assigned by current code. The field is declared
 * NULLABLE on CompatibilityRecord so that records persisted before Phase D
 * deserialize safely: HistoryStore uses plain Gson (GsonBuilder().create()), which
 * bypasses Kotlin default values and would otherwise leave a non-null enum as null.
 *
 * `null` therefore means LEGACY OR UNKNOWN and must never be treated as deterministic.
 */
enum class CompatibilityAnalysisSource {
    DETERMINISTIC_W5
}

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
    /**
     * Phase D: provenance of the analytical fields above.
     * Nullable by design - `null` means legacy/unknown (pre-Phase D records),
     * never deterministic. See [CompatibilityAnalysisSource].
     */
    val analysisSource: CompatibilityAnalysisSource? = null,
    /**
     * Nullable by design: plain Gson does not apply Kotlin defaults, so records
     * persisted before this field existed deserialize to null. null means
     * UNKNOWN/LEGACY and must never be treated as complete.
     */
    val evaluationComplete: Boolean? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val provisionalScore: Int? = null,
    val provisionalVacancyScore: Int? = null,
    val requirementAssessments: List<RequirementAssessment>? = null
)