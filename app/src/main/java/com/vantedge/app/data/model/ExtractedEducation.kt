package com.vantedge.app.data.model

data class ExtractedEducation(
    val institution: ExtractedField,
    val qualification: ExtractedField,
    val fieldOfStudy: ExtractedField?,
    val graduationYear: ExtractedField?,
    val confidence: Float
)
