package com.vantedge.app.data.model

data class VantEdgeScoreResult(
    val overallScore: Int,
    val atsScore: Int,
    val clarityScore: Int,
    val keywordScore: Int,
    val interpretation: ScoreInterpretation
)