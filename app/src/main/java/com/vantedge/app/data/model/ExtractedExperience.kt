package com.vantedge.app.data.model

data class ExtractedExperience(
    val jobTitle: ExtractedField,
    val company: ExtractedField,
    val startDate: ExtractedField?,
    val endDate: ExtractedField?,
    val description: ExtractedField?,
    val confidence: Float
)
