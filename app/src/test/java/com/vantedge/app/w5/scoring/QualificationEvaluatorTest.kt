package com.vantedge.app.w5.scoring

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QualificationEvaluatorTest {

    private val degreeRequired = RequirementEvidence(
        originalText = "Master's Degree in Law",
        category = RequirementCategory.DEGREE,
        classificationStatus = ClassificationStatus.NORMALIZED
    )

    private val cippCert = RequirementEvidence(
        originalText = "CIPP certification",
        category = RequirementCategory.CERTIFICATION,
        classificationStatus = ClassificationStatus.NORMALIZED
    )

    private val pmpCert = RequirementEvidence(
        originalText = "PMP certification",
        category = RequirementCategory.CERTIFICATION,
        classificationStatus = ClassificationStatus.NORMALIZED
    )

    private val emptyProfile = ValidatedProfile(
        correlationId = "test-no-quals",
        skills = emptyList(),
        qualifications = emptyList(),
        roles = emptyList(),
        currentTitle = null,
        experienceYears = 0,
        seniorityLevel = null,
        completedFields = emptySet(),
        isAccepted = true,
        isDegraded = false
    )

    @Test
    fun `no required qualifications returns ratio 1`() {
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = emptyList(),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(emptyProfile, job)
        assertEquals(1.0, result.qualificationRatio, 0.0)
        assertEquals(0, result.totalRequired)
        assertEquals(0, result.totalMatched)
        assertTrue(result.qualificationGaps.isEmpty())
    }

    @Test
    fun `no profile qualifications all gaps unmatched`() {
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = listOf(degreeRequired),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(emptyProfile, job)
        assertEquals(0.0, result.qualificationRatio, 0.0)
        assertEquals(1, result.totalRequired)
        assertEquals(0, result.totalMatched)
        assertFalse(result.qualificationGaps[0].isMatched)
    }

    @Test
    fun `exact one-to-one match`() {
        val profile = emptyProfile.copy(
            correlationId = "test-exact",
            qualifications = listOf(degreeRequired)
        )
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = listOf(degreeRequired),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(profile, job)
        assertEquals(1.0, result.qualificationRatio, 0.0)
        assertEquals(1, result.totalRequired)
        assertEquals(1, result.totalMatched)
        assertTrue(result.qualificationGaps[0].isMatched)
    }

    @Test
    fun `more required than profile qualifications`() {
        val profile = emptyProfile.copy(
            correlationId = "test-more-req",
            qualifications = listOf(cippCert)
        )
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = listOf(cippCert, pmpCert),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(profile, job)
        assertEquals(0.5, result.qualificationRatio, 0.0)
        assertEquals(2, result.totalRequired)
        assertEquals(1, result.totalMatched)
        val matchedGaps = result.qualificationGaps.filter { it.isMatched }
        val unmatchedGaps = result.qualificationGaps.filter { !it.isMatched }
        assertEquals(1, matchedGaps.size)
        assertEquals(1, unmatchedGaps.size)
    }

    @Test
    fun `more profile than required qualifications extra ignored`() {
        val profile = emptyProfile.copy(
            correlationId = "test-extra",
            qualifications = listOf(degreeRequired, degreeRequired)
        )
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = listOf(degreeRequired),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(profile, job)
        assertEquals(1.0, result.qualificationRatio, 0.0)
        assertEquals(1, result.totalRequired)
        assertEquals(1, result.totalMatched)
    }

    @Test
    fun `duplicate categories no double counting`() {
        val profile = emptyProfile.copy(
            correlationId = "test-no-double",
            qualifications = listOf(cippCert)
        )
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = listOf(cippCert, pmpCert),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(profile, job)
        assertEquals(0.5, result.qualificationRatio, 0.0)
        assertEquals(2, result.totalRequired)
        assertEquals(1, result.totalMatched)
    }

    @Test
    fun `mixed matched and missing qualifications`() {
        val profile = emptyProfile.copy(
            correlationId = "test-mixed",
            qualifications = listOf(degreeRequired)
        )
        val job = ValidatedJob(
            correlationId = "test",
            requiredSkills = emptyList(),
            requiredQualifications = listOf(degreeRequired, cippCert),
            title = "Engineer",
            keywords = emptyList(),
            requiredYears = null,
            seniorityLevel = null,
            completedFields = emptySet(),
            isAccepted = true,
            isDegraded = false
        )
        val result = QualificationEvaluator.evaluate(profile, job)
        assertEquals(0.5, result.qualificationRatio, 0.0)
        assertEquals(2, result.totalRequired)
        assertEquals(1, result.totalMatched)
        assertEquals(RequirementCategory.DEGREE, result.qualificationGaps[0].required.category)
        assertTrue(result.qualificationGaps[0].isMatched)
        assertEquals(RequirementCategory.CERTIFICATION, result.qualificationGaps[1].required.category)
        assertFalse(result.qualificationGaps[1].isMatched)
    }
}
