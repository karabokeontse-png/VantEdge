package com.vantedge.app.w5.scoring

data class SanitizationAuditEntry(
    val originalValue: String,
    val normalizedValue: String,
    val ruleId: String,
    val reason: String,
    val confidence: String
)

data class SanitizationAudit(
    val entries: List<SanitizationAuditEntry>
)
