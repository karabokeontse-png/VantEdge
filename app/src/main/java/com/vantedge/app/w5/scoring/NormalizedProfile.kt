package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.UserProfile

data class NormalizedProfile(
    val skills: List<String>,
    val certifications: List<String>,
    val workHistory: List<String>,
    val education: List<String>,
    val languages: List<String>,
    val summary: String,
    val audit: SanitizationAudit
) {
    companion object {
        fun from(userProfile: UserProfile, sanitizationResult: SanitizationResult): NormalizedProfile {
            return NormalizedProfile(
                skills = sanitizationResult.skills,
                certifications = sanitizationResult.certifications,
                workHistory = userProfile.workHistory.map {
                    "${it.role} at ${it.company} (${it.startDate}-${it.endDate}): ${it.description}"
                },
                education = userProfile.education,
                languages = userProfile.languages,
                summary = userProfile.summary,
                audit = sanitizationResult.audit
            )
        }
    }
}
