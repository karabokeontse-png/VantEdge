package com.vantedge.model

data class AtsResult(
    val score: Int,
    val missingKeywords: List<String>,
    val weakSections: List<String>
)