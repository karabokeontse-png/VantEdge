package com.vantedge.app.w5.scoring

enum class AssessmentVerdict {
    ACCEPTED, REPAIRED, LOW_CONFIDENCE, REJECTED
}

data class ExtractionAssessment(
    val originalText: String,
    val verdict: AssessmentVerdict,
    val confidence: Float,
    val reason: String,
    val optionalSuggestion: String? = null
) {
    init {
        require(originalText.isNotBlank()) { "originalText cannot be blank" }
        require(confidence in 0.0f..1.0f) { "confidence must be between 0.0 and 1.0" }
        require(reason.isNotBlank()) { "reason must be provided for all verdicts" }
        require(verdict != AssessmentVerdict.REPAIRED || optionalSuggestion != null) {
            "optionalSuggestion must be non-null when verdict is REPAIRED"
        }
    }
}
