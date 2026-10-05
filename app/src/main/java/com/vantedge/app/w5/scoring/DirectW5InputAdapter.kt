package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.UserProfile

object DirectW5InputAdapter {

    fun adaptProfile(
        profile: UserProfile,
        normalizedProfile: NormalizedProfile,
        correlationId: String,
        currentYear: Int
    ): ValidatedProfile {
        val currentTitle: String =
            profile.workHistory.maxByOrNull { extractYear(it.endDate) ?: 0 }?.role
                ?: profile.summary.takeIf { it.isNotBlank() }
                ?: ""

        val experienceYears = computeYearsExperience(profile.workHistory, currentYear)
        val seniorityLevel = SeniorityDeriver.derive(currentTitle, experienceYears)

        val completedFields = mutableSetOf<String>()
        if (normalizedProfile.skills.isNotEmpty()) completedFields.add("skills")
        if (profile.workHistory.isNotEmpty()) completedFields.add("roles")
        if (currentTitle.isNotEmpty()) completedFields.add("currentTitle")
        if (experienceYears > 0) completedFields.add("experienceYears")
        if (seniorityLevel != null) completedFields.add("seniorityLevel")

        val profileQualifications = mutableListOf<RequirementEvidence>()
        normalizedProfile.education.forEach {
            profileQualifications.add(RequirementEvidence(
                originalText = it,
                category = RequirementCategory.DEGREE,
                classificationStatus = ClassificationStatus.NORMALIZED
            ))
        }
        normalizedProfile.certifications.forEach {
            profileQualifications.add(RequirementEvidence(
                originalText = it,
                category = RequirementCategory.CERTIFICATION,
                classificationStatus = ClassificationStatus.NORMALIZED
            ))
        }

        return ValidatedProfile(
            correlationId = correlationId,
            skills = normalizedProfile.skills,
            qualifications = profileQualifications,
            roles = profile.workHistory.map { it.role },
            currentTitle = currentTitle,
            experienceYears = experienceYears,
            seniorityLevel = seniorityLevel,
            completedFields = completedFields.intersect(RequiredProfileFields.fields),
            isAccepted = true,
            isDegraded = false
        )
    }
}
