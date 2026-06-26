package com.vantedge.app.data.model

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val linkedIn: String = "",
    val summary: String = "",

    val skills: List<String> = emptyList(),
    val workHistory: List<WorkExperience> = emptyList(),
    val education: List<String> = emptyList(),

    // STRUCTURED CERTIFICATION MODEL (FIXED)
    val certifications: List<Certification> = emptyList(),

    val languages: List<String> = emptyList()
)