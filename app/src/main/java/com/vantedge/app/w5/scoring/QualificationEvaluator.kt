package com.vantedge.app.w5.scoring

data class QualificationEvaluationResult(
    val qualificationGaps: List<QualificationGap>
) {
    // Computed properties prevent state drift
    val totalRequired: Int get() = qualificationGaps.size
    val totalMatched: Int get() = qualificationGaps.count { it.isMatched }

    // Intentional design: If no qualifications are required, the ratio is 1.0 (fully satisfied, no penalty).
    val qualificationRatio: Double
        get() = if (totalRequired == 0) 1.0 else totalMatched.toDouble() / totalRequired
}

object QualificationEvaluator {
    fun evaluate(profile: ValidatedProfile, job: ValidatedJob): QualificationEvaluationResult {
        // Use a mutable list to track and consume available profile qualifications
        // This ensures a 1-to-1 match and prevents one profile credential from satisfying multiple requirements.
        val availableProfileQuals = profile.qualifications.toMutableList()

        val gaps = job.requiredQualifications.map { required ->
            val matchIndex = availableProfileQuals.indexOfFirst { profileQual ->
                matches(required, profileQual)
            }

            val match = if (matchIndex != -1) {
                availableProfileQuals.removeAt(matchIndex) // Consume the match
            } else {
                null
            }

            QualificationGap(
                required = required,
                isMatched = match != null,
                matchedProfileEvidence = match
            )
        }

        return QualificationEvaluationResult(qualificationGaps = gaps)
    }

    // Isolated matching predicate for future refinement (e.g., text-level specificity)
    // TODO: Add text-level specificity matching (e.g., "CIPP" vs "PMP") for finer-grained scoring
    private fun matches(required: RequirementEvidence, profileQual: RequirementEvidence): Boolean {
        return required.category == profileQual.category
    }
}
