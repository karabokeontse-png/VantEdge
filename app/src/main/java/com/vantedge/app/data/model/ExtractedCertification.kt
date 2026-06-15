package com.vantedge.app.data.model

data class ExtractedCertification(
    val name: ExtractedField,
    val issuer: ExtractedField?,
    val confidence: Float
)
