package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.model.CompatibilityRecord

data class ContentState(
    val cvContent: String,
    val coverLetterContent: String,
    val compatibility: CompatibilityRecord,
    val score: Int,
    val previousScore: Int? = null,
    val delta: Int? = null,
    val hasDocs: Boolean = false,
    val isCvCorruptedJson: Boolean = false,
    val isCoverLetterCorruptedJson: Boolean = false
)
