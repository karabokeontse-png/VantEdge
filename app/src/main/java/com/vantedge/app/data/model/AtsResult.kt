package com.vantedge.app.data.model

data class AtsResult(
    val score: Int,
    val missingKeywords: List<String>,
    val weakSections: List<String>
)