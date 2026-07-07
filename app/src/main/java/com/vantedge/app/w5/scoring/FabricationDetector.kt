package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.CompatibilityRecord

object FabricationDetector {

    private val HALLUCINATION_MARKERS = listOf(
        "i assume", "likely", "probably", "presumably", "may have", "could be",
        "i think", "it seems", "based on my knowledge", "typically",
        "commonly known as", "i believe", "in my experience", "it appears",
        "might be", "possibly", "perhaps", "as far as i know",
        "to the best of my knowledge"
    )

    fun containsHallucinationMarkers(text: String): Boolean {
        val lower = text.lowercase()
        return HALLUCINATION_MARKERS.any { lower.contains(it) }
    }

    fun detect(record: CompatibilityRecord): FabricationReport {
        val issues = mutableListOf<FabricationIssue>()

        if (containsHallucinationMarkers(record.roleSummary)) {
            issues.add(FabricationIssue("roleSummary", "hallucination_marker"))
        }
        if (containsHallucinationMarkers(record.eligibilitySummary)) {
            issues.add(FabricationIssue("eligibilitySummary", "hallucination_marker"))
        }
        record.gaps.forEach { gap ->
            if (containsHallucinationMarkers(gap.description)) {
                issues.add(FabricationIssue("gap.${gap.skill}", "hallucination_marker"))
            }
            gap.courses.forEach { course ->
                if (course.verificationStatus == com.vantedge.app.data.model.VerificationStatus.HALLUCINATED) {
                    issues.add(FabricationIssue("gap.${gap.skill}.course.${course.title}", "hallucinated_url"))
                }
            }
        }

        return FabricationReport(
            hasIssues = issues.isNotEmpty(),
            issues = issues,
            hallucinationMarkerCount = issues.count { it.type == "hallucination_marker" },
            hallucinatedUrlCount = issues.count { it.type == "hallucinated_url" }
        )
    }
}

data class FabricationIssue(
    val field: String,
    val type: String
)

data class FabricationReport(
    val hasIssues: Boolean,
    val issues: List<FabricationIssue>,
    val hallucinationMarkerCount: Int,
    val hallucinatedUrlCount: Int
)
