package com.vantedge.app.data.model

enum class VerificationStatus {
    VERIFIED,
    UNVERIFIED,
    HALLUCINATED
}

data class CourseRecommendation(
    val title: String,
    val provider: String,
    val url: String,
    val category: String,
    val hasCertificate: Boolean,
    val estimatedDuration: String,
    val relevancyPercent: Int = 0,
    val priority: Int = 1,
    val verificationStatus: VerificationStatus = VerificationStatus.UNVERIFIED
)
