package com.vantedge.app.domain

import com.vantedge.app.data.model.*
import com.vantedge.app.data.storage.OnboardingDraftStore
import com.vantedge.app.data.storage.UserPreferences

class OnboardingCommitService(
    private val userPreferences: UserPreferences,
    private val draftStore: OnboardingDraftStore
) {

    suspend fun commit(draft: OnboardingDraft) {
        val profile = draft.editedProfile
            ?: throw IllegalStateException("Cannot commit: no profile in draft")

        userPreferences.saveProfile(profile)
        draftStore.clearDraft()
    }

    // Maps extraction output → domain UserProfile
    // Called during REVIEWING_EXTRACTION → EDITING_PROFILE transition
    fun extractionToProfile(extraction: StructuredProfileExtraction): UserProfile {
        return UserProfile(
            name = extraction.personalInfo.name?.value ?: "",
            email = extraction.personalInfo.email?.value ?: "",
            phone = extraction.personalInfo.phone?.value ?: "",
            location = extraction.personalInfo.location?.value ?: "",
            linkedIn = extraction.personalInfo.linkedIn?.value ?: "",
            summary = extraction.summary?.value ?: "",
            skills = extraction.skills.map { it.value },
            languages = extraction.languages.map { it.value },
            education = extraction.education.map { edu ->
                buildString {
                    append(edu.qualification.value)
                    if (edu.fieldOfStudy != null) append(" in ${edu.fieldOfStudy.value}")
                    append(" — ${edu.institution.value}")
                    if (edu.graduationYear != null) append(" (${edu.graduationYear.value})")
                }
            },
            workHistory = extraction.workHistory.map { exp ->
                WorkExperience(
                    role = exp.jobTitle.value,
                    company = exp.company.value,
                    startDate = exp.startDate?.value ?: "",
                    endDate = exp.endDate?.value ?: "",
                    description = exp.description?.value ?: ""
                )
            },
            certifications = extraction.certifications.map { cert ->
                Certification(
                    name = cert.name.value,
                    issuer = cert.issuer?.value ?: ""
                )
            }
        )
    }
}
