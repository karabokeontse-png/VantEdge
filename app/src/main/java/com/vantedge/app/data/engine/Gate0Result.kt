package com.vantedge.app.data.engine

data class Gate0Result(
    val score: Int,
    val threshold: Int,
    val accepted: Boolean,
    val reason: Gate0Reason,
    val extractionMode: ExtractionMode
)
