package com.vantedge.app.data.model

import java.util.UUID

data class GenerationCycle(
    val id: String = UUID.randomUUID().toString(),
    val jobTitle: String,
    val company: String,
    val jobDescription: String,
    val profileSnapshot: UserProfile,
    val profileSchemaVersion: Int = 1,
    val version: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isCommitted: Boolean = false,
    val isVisibleInHistory: Boolean = false,
    val title: String? = null,
    val compatibility: CompatibilityRecord? = null,
    val matchedKeywords: List<String> = emptyList(),
    val design: DesignConfig? = null,
    val cvContent: String? = null,
    val coverLetterContent: String? = null,
    val cvErrorMessage: String? = null,
    val coverLetterErrorMessage: String? = null
)
