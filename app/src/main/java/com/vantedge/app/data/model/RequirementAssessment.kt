package com.vantedge.app.data.model

enum class RequirementAssessmentState {
    MATCHED,
    CONFIRMED_GAP,
    UNASSESSED
}

data class RequirementAssessment(
    val text: String,
    val state: RequirementAssessmentState,
    val canonical: String? = null,
    val reason: String? = null,
    val category: String? = null,
    val importance: String? = null
)
