package com.vantedge.app.data.domain

import com.vantedge.app.data.model.Certification
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.model.WorkExperience
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileEligibilityTest {

    @Test
    fun nullProfileIsRejected() {
        assertEquals(ProfileEligibility.Rejection.ABSENT_PROFILE, ProfileEligibility.rejectionReason(null))
        assertFalse(ProfileEligibility.isEligible(null))
    }

    @Test
    fun blankNameIsRejected() {
        val profile = UserProfile(name = "   ", email = "jane@example.com")
        assertEquals(ProfileEligibility.Rejection.BLANK_NAME, ProfileEligibility.rejectionReason(profile))
        assertFalse(ProfileEligibility.isEligible(profile))
    }

    @Test
    fun blankEmailIsRejected() {
        val profile = UserProfile(name = "Jane Doe", email = "")
        assertEquals(ProfileEligibility.Rejection.BLANK_EMAIL, ProfileEligibility.rejectionReason(profile))
        assertFalse(ProfileEligibility.isEligible(profile))
    }

    @Test
    fun validNameAndEmailWithEmptySkillsIsEligible() {
        val profile = UserProfile(name = "Jane Doe", email = "jane@example.com", skills = emptyList())
        assertNull(ProfileEligibility.rejectionReason(profile))
        assertTrue(ProfileEligibility.isEligible(profile))
    }

    @Test
    fun minimalMandatoryFieldsWithoutOptionalDataIsEligible() {
        val profile = UserProfile(name = "Jane Doe", email = "jane@example.com")
        assertTrue(ProfileEligibility.isEligible(profile))
    }

    @Test
    fun populatedOptionalFieldsIsEligible() {
        val profile = UserProfile(
            name = "Jane Doe",
            email = "jane@example.com",
            summary = "Engineer",
            skills = listOf("Kotlin"),
            education = listOf("BSc Computer Science"),
            workHistory = listOf(WorkExperience("Engineer", "Acme", "2020", "2024", "Built things")),
            certifications = listOf(Certification(name = "AWS Certified Developer"))
        )
        assertTrue(ProfileEligibility.isEligible(profile))
    }
}
