package com.vantedge.app.data.model.extraction

data class BlockMetadata(
    val ocrConfidence: Float? = null,
    val detectedEmails: List<String> = emptyList(),
    val detectedPhoneNumbers: List<String> = emptyList(),
    val detectedDates: List<String> = emptyList(),
    val detectedCurrencyValues: List<String> = emptyList(),
    val language: String? = null,
    val pageRegion: String? = null,
    val layoutConfidence: Float? = null
)
