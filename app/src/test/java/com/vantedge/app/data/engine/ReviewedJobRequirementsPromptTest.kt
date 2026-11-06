package com.vantedge.app.data.engine

import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.model.WorkExperience
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReviewedJobRequirementsPromptTest {

    private val jobDescription = "Build reliable Android software."
    private val requirements = ReviewedJobRequirements(
        requiredSkills = listOf("Kotlin", "Android"),
        preferredSkills = listOf("Compose"),
        educationRequired = "Bachelor's degree"
    )
    private val jobHeader =
        "JOB REQUIREMENTS (user-reviewed; role requirements, not candidate qualifications):"

    private fun profile() = UserProfile(
        name = "Candidate Name",
        email = "candidate@example.com",
        phone = "123",
        location = "Remote",
        linkedIn = "linkedin.com/in/candidate",
        summary = "Candidate summary",
        skills = listOf("CandidateSkill", "Kotlin"),
        workHistory = listOf(
            WorkExperience(
                role = "Engineer",
                company = "CandidateCo",
                startDate = "2020",
                endDate = "2022",
                description = "Built candidate systems"
            )
        ),
        education = listOf("CandidateDegree")
    )

    @Test
    fun `typed requirements preserve required preferred and education fields`() {
        assertEquals(listOf("Kotlin", "Android"), requirements.requiredSkills)
        assertEquals(listOf("Compose"), requirements.preferredSkills)
        assertEquals("Bachelor's degree", requirements.educationRequired)
        assertFalse(requirements.isEmpty)
    }

    @Test
    fun `blank requirements are empty`() {
        val blank = ReviewedJobRequirements(
            requiredSkills = listOf(" "),
            preferredSkills = emptyList(),
            educationRequired = ""
        )
        assertTrue(blank.isEmpty)
        assertTrue(buildReviewedJobRequirementsPrompt(blank).isEmpty())
    }

    @Test
    fun `requirements are labeled as job requirements in every artifact prompt`() {
        val profile = profile()
        val cv = buildCvUserPrompt(profile, jobDescription, requirements)
        val coverLetter = buildCoverLetterUserPrompt(profile, jobDescription, requirements)
        val docx = buildCvDocxUserPrompt(profile, jobDescription, requirements)

        listOf(cv, coverLetter, docx).forEach { prompt ->
            assertTrue(prompt, prompt.contains(jobHeader))
            assertTrue(prompt, prompt.contains("Required Skills: Kotlin, Android"))
            assertTrue(prompt, prompt.contains("Preferred Skills: Compose"))
            assertTrue(prompt, prompt.contains("Education Requirement: Bachelor's degree"))
        }
    }

    @Test
    fun `candidate profile skills and education stay distinct from job requirements`() {
        val profile = profile()
        val coverLetter = buildCoverLetterUserPrompt(profile, jobDescription, requirements)
        val docx = buildCvDocxUserPrompt(profile, jobDescription, requirements)

        val coverCandidateSection = coverLetter.substringBefore(jobHeader)
        assertTrue(coverCandidateSection, coverCandidateSection.contains("Skills: CandidateSkill, Kotlin"))
        assertFalse(coverLetter.substringAfter(jobHeader), coverLetter.substringAfter(jobHeader).contains("CandidateSkill"))

        val docxCandidateSection = docx.substringBefore(jobHeader)
        assertTrue(docxCandidateSection, docxCandidateSection.contains("Skills: CandidateSkill, Kotlin"))
        assertTrue(docxCandidateSection, docxCandidateSection.contains("Education: CandidateDegree"))
        assertFalse(docx.substringAfter(jobHeader), docx.substringAfter(jobHeader).contains("CandidateDegree"))
    }

    @Test
    fun `absent requirements preserve previous CV prompt`() {
        val expected = """
            JOB DESCRIPTION:
            $jobDescription

            PROFILE SUMMARY:
            Candidate summary

            PROFILE EXPERIENCE:
            Engineer at CandidateCo: Built candidate systems
        """.trimIndent()

        assertEquals(expected, buildCvUserPrompt(profile(), jobDescription, null))
    }

    @Test
    fun `absent requirements preserve previous cover letter prompt`() {
        val expected = """
            PROFILE:
            Name: Candidate Name
            Summary: Candidate summary
            Skills: CandidateSkill, Kotlin
            Experience: Engineer at CandidateCo: Built candidate systems

            JOB DESCRIPTION:
            $jobDescription
        """.trimIndent()

        assertEquals(expected, buildCoverLetterUserPrompt(profile(), jobDescription, null))
    }

    @Test
    fun `absent requirements preserve previous DOCX prompt`() {
        val expected = """
            PROFILE:
            Name: Candidate Name
            Email: candidate@example.com
            Phone: 123
            Location: Remote
            LinkedIn: linkedin.com/in/candidate
            Summary: Candidate summary
            Skills: CandidateSkill, Kotlin
            Work History: Engineer at CandidateCo (2020 - 2022): Built candidate systems
            Education: CandidateDegree
            Certifications: 
            Languages: 

            JOB DESCRIPTION:
            $jobDescription
        """.trimIndent()

        assertEquals(expected, buildCvDocxUserPrompt(profile(), jobDescription, null))
    }

    @Test
    fun `empty typed requirements preserve previous prompts`() {
        val profile = profile()
        val empty = ReviewedJobRequirements()
        assertEquals(
            buildCvUserPrompt(profile, jobDescription, null),
            buildCvUserPrompt(profile, jobDescription, empty)
        )
        assertEquals(
            buildCoverLetterUserPrompt(profile, jobDescription, null),
            buildCoverLetterUserPrompt(profile, jobDescription, empty)
        )
        assertEquals(
            buildCvDocxUserPrompt(profile, jobDescription, null),
            buildCvDocxUserPrompt(profile, jobDescription, empty)
        )
    }
}
