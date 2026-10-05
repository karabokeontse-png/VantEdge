package com.vantedge.app.domain.policy

class ThresholdPolicy(
    val profileDocumentGate0: Int = 3,
    val emergencyExtractionGate0: Int = 2,
    val extractionAccept: Float = 0.90f,
    val extractionWarning: Float = 0.70f,
    val extractionReview: Float = 0.50f,
    val minConfidence: Float = 0.3f
)
