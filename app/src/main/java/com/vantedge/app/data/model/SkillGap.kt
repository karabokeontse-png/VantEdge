package com.vantedge.app.data.model

data class SkillGap(
    val skill: String,
    val severity: GapSeverity,
    val reason: String
)