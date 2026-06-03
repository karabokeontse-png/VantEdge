package com.vantedge.app.data.model

data class ApplicationRecord(
    val id: Long = System.currentTimeMillis(),
    val jobTitle: String,
    val company: String,
    val cv: String,
    val coverLetter: String,
    val createdAt: Long = System.currentTimeMillis(),
    val deadline: Long? = null,
    val reminderSet: Boolean = false,
    val status: ApplicationStatus = ApplicationStatus.SAVED
)
