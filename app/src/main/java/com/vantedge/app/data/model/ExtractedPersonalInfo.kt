package com.vantedge.app.data.model

data class ExtractedPersonalInfo(
    val name: ExtractedField?,
    val email: ExtractedField?,
    val phone: ExtractedField?,
    val location: ExtractedField?,
    val linkedIn: ExtractedField?,
    val headline: ExtractedField?
)
