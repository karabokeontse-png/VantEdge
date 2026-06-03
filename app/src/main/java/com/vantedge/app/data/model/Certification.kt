package com.vantedge.app.data.model

data class Certification(
    val id: String = System.currentTimeMillis().toString(),

    // Core info
    val name: String,
    val issuer: String = "",

    // Evidence (THIS FIXES YOUR "PROOF" REQUIREMENT)
    val localFileUri: String? = null,   // device storage (PDF/image)
    val cloudUrl: String? = null,       // future Firebase/S3 link
    val verificationUrl: String? = null, // optional external verification

    // Metadata
    val issueDate: Long = 0L,
    val expiryDate: Long = 0L,
    val dateAdded: Long = System.currentTimeMillis()
)