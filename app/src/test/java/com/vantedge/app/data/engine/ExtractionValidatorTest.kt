package com.vantedge.app.data.engine

import com.vantedge.app.data.model.*
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.junit.Assert.*
import org.junit.Test

class ExtractionValidatorTest {

    // ========== HELPER: valid profile ==========

    private fun validProfile() = StructuredProfileExtraction(
        personalInfo = ExtractedPersonalInfo(
            name = ExtractedField("Jane Doe", 0.8f, ExtractionSourceType.DIRECT),
            email = ExtractedField("jane@example.com", 0.8f, ExtractionSourceType.DIRECT),
            phone = ExtractedField("+1 555-123-4567", 0.8f, ExtractionSourceType.INFERRED),
            location = null,
            linkedIn = null,
            headline = ExtractedField("Senior Engineer", 0.8f, ExtractionSourceType.INFERRED)
        ),
        summary = ExtractedField("Experienced software engineer with 8 years in backend systems.", 0.8f, ExtractionSourceType.INFERRED),
        skills = listOf(
            ExtractedField("Kotlin", 0.8f, ExtractionSourceType.DIRECT),
            ExtractedField("Java", 0.8f, ExtractionSourceType.DIRECT)
        ).toImmutableList(),
        languages = persistentListOf(),
        workHistory = listOf(
            ExtractedExperience(
                jobTitle = ExtractedField("Senior Engineer", 0.8f, ExtractionSourceType.DIRECT),
                company = ExtractedField("Acme Corp", 0.8f, ExtractionSourceType.DIRECT),
                startDate = ExtractedField("Jan 2020", 0.8f, ExtractionSourceType.INFERRED),
                endDate = null,
                description = ExtractedField("Built microservices in Kotlin.", 0.8f, ExtractionSourceType.DIRECT),
                confidence = 0.8f
            )
        ).toImmutableList(),
        education = listOf(
            ExtractedEducation(
                institution = ExtractedField("MIT", 0.8f, ExtractionSourceType.DIRECT),
                qualification = ExtractedField("BSc Computer Science", 0.8f, ExtractionSourceType.DIRECT),
                fieldOfStudy = ExtractedField("Computer Science", 0.8f, ExtractionSourceType.INFERRED),
                graduationYear = ExtractedField("2016", 0.8f, ExtractionSourceType.DIRECT),
                confidence = 0.8f
            )
        ).toImmutableList(),
        certifications = persistentListOf(),
        overallConfidence = 0.85f,
        warnings = persistentListOf(),
        metadata = ExtractionMetadata("openrouter-strict")
    )

    // ========== TEST: valid profile passes ==========

    @Test
    fun `valid profile passes validation`() {
        val result = ExtractionValidator.validateProfile(validProfile())
        assertTrue("Expected validation to pass", result.passed)
        assertTrue("Expected no errors", result.errors.isEmpty())
    }

    // ========== TEST: empty name is blocker ==========

    @Test
    fun `empty name is blocker`() {
        val profile = validProfile().copy(
            personalInfo = validProfile().personalInfo.copy(
                name = ExtractedField("", 0.8f, ExtractionSourceType.DIRECT)
            )
        )
        val result = ExtractionValidator.validateProfile(profile)
        assertFalse("Expected validation to fail", result.passed)
        val nameErrors = result.errors.filter { it.field == "personalInfo.name" }
        assertTrue("Expected blocker for empty name", nameErrors.any { it.severity == ValidationSeverity.BLOCKER })
    }

    // ========== TEST: empty email is blocker ==========

    @Test
    fun `empty email is blocker`() {
        val profile = validProfile().copy(
            personalInfo = validProfile().personalInfo.copy(
                email = ExtractedField("", 0.8f, ExtractionSourceType.DIRECT)
            )
        )
        val result = ExtractionValidator.validateProfile(profile)
        assertFalse("Expected validation to fail", result.passed)
        val emailErrors = result.errors.filter { it.field == "personalInfo.email" }
        assertTrue("Expected blocker for empty email", emailErrors.any { it.severity == ValidationSeverity.BLOCKER })
    }

    // ========== TEST: invalid email format is warning ==========

    @Test
    fun `invalid email format is warning`() {
        val profile = validProfile().copy(
            personalInfo = validProfile().personalInfo.copy(
                email = ExtractedField("not-an-email", 0.8f, ExtractionSourceType.DIRECT)
            )
        )
        val result = ExtractionValidator.validateProfile(profile)
        val emailWarnings = result.warnings.filter { it.field == "personalInfo.email" }
        assertTrue("Expected warning for invalid email format", emailWarnings.isNotEmpty())
        assertTrue("Expected validation to still pass (non-empty)", result.passed)
    }

    // ========== TEST: hallucination markers in summary generate warnings ==========

    @Test
    fun `hallucination marker in summary generates warning`() {
        val profile = validProfile().copy(
            summary = ExtractedField("The candidate likely has experience with AWS and probably knows Kubernetes.", 0.8f, ExtractionSourceType.INFERRED)
        )
        val result = ExtractionValidator.validateProfile(profile)
        val summaryWarnings = result.warnings.filter { it.field == "summary" }
        assertTrue("Expected warning for hallucination marker", summaryWarnings.isNotEmpty())
        assertTrue("Expected validation to still pass", result.passed)
    }

    // ========== TEST: hallucination markers in skills generate warnings ==========

    @Test
    fun `hallucination marker in skill name generates warning`() {
        val profile = validProfile().copy(
            skills = listOf(
                ExtractedField("Kotlin", 0.8f, ExtractionSourceType.DIRECT),
                ExtractedField("I assume they know AWS", 0.8f, ExtractionSourceType.INFERRED)
            ).toImmutableList()
        )
        val result = ExtractionValidator.validateProfile(profile)
        val skillWarnings = result.warnings.filter { it.field == "skills[1]" }
        assertTrue("Expected warning for hallucination marker in skill", skillWarnings.isNotEmpty())
    }

    // ========== TEST: name exceeding max length generates warning ==========

    @Test
    fun `name exceeding max length generates warning`() {
        val longName = "A".repeat(150)
        val profile = validProfile().copy(
            personalInfo = validProfile().personalInfo.copy(
                name = ExtractedField(longName, 0.8f, ExtractionSourceType.DIRECT)
            )
        )
        val result = ExtractionValidator.validateProfile(profile)
        val nameErrors = result.errors.filter { it.field == "personalInfo.name" }
        assertTrue("Expected error for name exceeding max length", nameErrors.isNotEmpty())
    }

    // ========== TEST: empty work history generates warning, still passes ==========

    @Test
    fun `empty work history generates warning`() {
        val profile = validProfile().copy(
            workHistory = persistentListOf()
        )
        val result = ExtractionValidator.validateProfile(profile)
        val whWarnings = result.warnings.filter { it.field == "workHistory" }
        assertTrue("Expected warning for empty work history", whWarnings.isNotEmpty())
        assertTrue("Expected validation to still pass", result.passed)
    }

    // ========== TEST: empty skills generates warning ==========

    @Test
    fun `empty skills generates warning`() {
        val profile = validProfile().copy(
            skills = persistentListOf()
        )
        val result = ExtractionValidator.validateProfile(profile)
        val skillWarnings = result.warnings.filter { it.field == "skills" }
        assertTrue("Expected warning for empty skills", skillWarnings.isNotEmpty())
    }

    // ========== TEST: empty education entry with blank institution ==========

    @Test
    fun `education entry with empty institution generates warning`() {
        val profile = validProfile().copy(
            education = listOf(
                ExtractedEducation(
                    institution = ExtractedField("", 0.8f, ExtractionSourceType.DIRECT),
                    qualification = ExtractedField("Some Degree", 0.8f, ExtractionSourceType.DIRECT),
                    fieldOfStudy = null,
                    graduationYear = null,
                    confidence = 0.8f
                )
            ).toImmutableList()
        )
        val result = ExtractionValidator.validateProfile(profile)
        val eduWarnings = result.warnings.filter { it.field == "education[0].institution" }
        assertTrue("Expected warning for empty institution", eduWarnings.isNotEmpty())
    }

    // ========== TEST: invalid graduation year format ==========

    @Test
    fun `invalid graduation year format generates warning`() {
        val profile = validProfile().copy(
            education = listOf(
                ExtractedEducation(
                    institution = ExtractedField("MIT", 0.8f, ExtractionSourceType.DIRECT),
                    qualification = ExtractedField("BSc", 0.8f, ExtractionSourceType.DIRECT),
                    fieldOfStudy = null,
                    graduationYear = ExtractedField("twenty sixteen", 0.8f, ExtractionSourceType.INFERRED),
                    confidence = 0.8f
                )
            ).toImmutableList()
        )
        val result = ExtractionValidator.validateProfile(profile)
        val yearWarnings = result.warnings.filter { it.field == "education[0].graduationYear" }
        assertTrue("Expected warning for non-numeric graduation year", yearWarnings.isNotEmpty())
    }

    // ========== TEST: phone format warning ==========

    @Test
    fun `unusual phone format generates warning`() {
        val profile = validProfile().copy(
            personalInfo = validProfile().personalInfo.copy(
                phone = ExtractedField("XYZ-12345-NOT-A-PHONE", 0.8f, ExtractionSourceType.INFERRED)
            )
        )
        val result = ExtractionValidator.validateProfile(profile)
        val phoneWarnings = result.warnings.filter { it.field == "personalInfo.phone" }
        assertTrue("Expected warning for unusual phone format", phoneWarnings.isNotEmpty())
    }

    // ========== TEST: certification with hallucination marker ==========

    @Test
    fun `certification with hallucination marker generates warning`() {
        val profile = validProfile().copy(
            certifications = listOf(
                ExtractedCertification(
                    name = ExtractedField("I assume they have a security cert", 0.8f, ExtractionSourceType.INFERRED),
                    issuer = ExtractedField("ISC2", 0.8f, ExtractionSourceType.DIRECT),
                    confidence = 0.8f
                )
            ).toImmutableList()
        )
        val result = ExtractionValidator.validateProfile(profile)
        val certWarnings = result.warnings.filter { it.field == "certifications[0].name" }
        assertTrue("Expected warning for hallucination marker in cert", certWarnings.isNotEmpty())
    }

    // ========== JOB EXTRACTION TESTS ==========

    private fun validJobExtractionResult() = JobExtractionResult(
        extractionId = "test-123",
        extractedAt = System.currentTimeMillis(),
        jobTitle = "Senior Engineer",
        company = "Acme Corp",
        description = "We are looking for a Senior Engineer with 5+ years experience in Kotlin and cloud platforms. The role involves building microservices and leading a team of 4 engineers.",
        confidenceScore = 0.85f,
        confidenceBreakdown = ConfidenceBreakdown(
            baseScore = 0.5f,
            titleContribution = 0.2f,
            companyContribution = 0.2f,
            qualificationContribution = 0.1f,
            penalties = emptyList(),
            finalScore = 0.85f
        ),
        gate0Result = Gate0JobResult(
            score = 5,
            threshold = 2,
            accepted = true,
            reason = Gate0JobReason.ACCEPTED,
            detectedSignals = listOf("responsibilities", "requirements"),
            appliedPenalties = emptyList(),
            rejectionCauses = emptyList(),
            narrativeDensity = 0.3f
        ),
        metrics = JobExtractionMetrics(
            durationMs = 150L,
            sourceType = JobSourceType.TXT,
            rawTextLength = 500,
            gate0DurationMs = 5L,
            gate1DurationMs = 10L,
            gate2DurationMs = 2L,
            gate3DurationMs = 1L,
            gate4DurationMs = 1L,
            qualificationPassed = true,
            narrativeDensity = 0.3f
        )
    )

    @Test
    fun `valid job extraction passes validation`() {
        val job = validJobExtractionResult()
        val result = ExtractionValidator.validateJob(job)
        assertTrue("Expected job validation to pass", result.passed)
        assertTrue("Expected no errors", result.errors.isEmpty())
    }

    @Test
    fun `job extraction with blank description fails`() {
        val job = validJobExtractionResult().copy(description = "")
        val result = ExtractionValidator.validateJob(job)
        assertFalse("Expected validation to fail for blank description", result.passed)
        val descErrors = result.errors.filter { it.field == "description" }
        assertTrue("Expected blocker for empty description", descErrors.any { it.severity == ValidationSeverity.BLOCKER })
    }

    @Test
    fun `job extraction with very short description fails`() {
        val job = validJobExtractionResult().copy(description = "Hi")
        val result = ExtractionValidator.validateJob(job)
        assertFalse("Expected validation to fail for short description", result.passed)
    }

    @Test
    fun `job extraction with confidence out of range fails`() {
        val job = validJobExtractionResult().copy(confidenceScore = 1.5f)
        val result = ExtractionValidator.validateJob(job)
        assertFalse("Expected validation to fail for out-of-range confidence", result.passed)
        val confErrors = result.errors.filter { it.field == "confidenceScore" }
        assertTrue("Expected blocker for invalid confidence", confErrors.any { it.severity == ValidationSeverity.BLOCKER })
    }

    @Test
    fun `job extraction with hallucination marker generates warning`() {
        val job = validJobExtractionResult().copy(
            description = "The candidate likely needs experience with AWS. This is probably a senior role."
        )
        val result = ExtractionValidator.validateJob(job)
        val descWarnings = result.warnings.filter { it.field == "description" }
        assertTrue("Expected warning for hallucination marker in description", descWarnings.isNotEmpty())
        assertTrue("Expected validation to still pass", result.passed)
    }
}
