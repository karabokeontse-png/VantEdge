package com.vantedge.app.w5.scoring

import com.vantedge.app.data.domain.CompatibilityRecordMapper
import com.vantedge.app.data.model.RequirementAssessmentState
import com.vantedge.app.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CorrectiveUnknownStateTest {

    private val assets = ValidationAssets(stopWords = emptySet(), keywordDictionary = emptyMap())

    private fun profile(skills: List<String> = listOf("Kotlin")) = ValidatedProfile(
        correlationId = "corrective",
        skills = skills,
        qualifications = emptyList(),
        roles = listOf("Developer"),
        currentTitle = "Developer",
        experienceYears = 5,
        seniorityLevel = "senior",
        completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
        isAccepted = true,
        isDegraded = false
    )

    private fun job(qualification: RequirementEvidence? = null) = ValidatedJob(
        correlationId = "corrective",
        requiredSkills = listOf("Kotlin"),
        requiredQualifications = listOfNotNull(qualification),
        title = "Developer",
        keywords = listOf("Kotlin"),
        requiredYears = 5,
        seniorityLevel = "senior",
        completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
        isAccepted = true,
        isDegraded = false
    )

    private fun degree(text: String) = RequirementEvidence(
        originalText = text,
        category = RequirementCategory.DEGREE,
        classificationStatus = ClassificationStatus.NORMALIZED
    )

    private fun map(
        validatedProfile: ValidatedProfile,
        validatedJob: ValidatedJob
    ) = CompatibilityRecordMapper().map(
        scoreResult = W5ScoreEngine(assets).evaluate(
            validatedProfile,
            validatedJob,
            W5TraceContext("corrective", "", 0L)
        ),
        profile = UserProfile(),
        normalizedProfile = NormalizedProfile(
            skills = validatedProfile.skills,
            certifications = emptyList(),
            workHistory = emptyList(),
            education = emptyList(),
            languages = emptyList(),
            summary = "",
            audit = SanitizationAudit(emptyList())
        ),
        validatedProfile = validatedProfile,
        jobTitle = validatedJob.title,
        company = "Acme",
        jobDescription = "Developer with Kotlin",
        roleSummary = "",
        eligibilitySummary = "",
        currentYear = 2026,
        validatedJob = validatedJob,
        diagnostics = RequirementDiagnostics(emptyList(), DiagnosticsSummary(0, 0, 0))
    )

    @Test
    fun `unassessed skills are not classified as confirmed gaps`() {
        val gap = GapAnalyzer.analyze(profile(skills = emptyList()), job(), assets)

        assertTrue(gap.missing.isEmpty())
        assertTrue(gap.weak.isEmpty())
        assertTrue(gap.matched.isEmpty())
    }

    @Test
    fun `evidence-supported skill gaps are preserved`() {
        val gap = GapAnalyzer.analyze(profile(), job(), assets)

        assertTrue(gap.missing.isEmpty())
        val gap2 = GapAnalyzer.analyze(
            profile(),
            job().copy(requiredSkills = listOf("Kotlin", "Kubernetes")),
            assets
        )
        assertTrue(gap2.missing.contains("Kubernetes"))
    }

    @Test
    fun `empty skill evidence keeps the evaluation incomplete`() {
        val result = W5ScoreEngine(assets).evaluate(
            profile(skills = emptyList()),
            job(),
            W5TraceContext("corrective", "", 0L)
        )

        val skillMatch = result.axisScores.first { it.axisName == "SkillMatch" }
        assertFalse(skillMatch.isMeasured)
        assertTrue(result.unknownAxes.contains("SkillMatch"))
        assertFalse(result.isComplete)
    }

    @Test
    fun `unassessed qualification evidence is not a mandatory gap and is incomplete`() {
        val mapped = map(profile(), job(degree("Bachelor of Science")))

        assertFalse(mapped.record.evaluationComplete!!)
        assertTrue(mapped.record.gaps.none { it.skill.startsWith("DEGREE:") })
    }

    @Test
    fun `evidence-supported qualification gaps are preserved and complete`() {
        val assessed = profile().copy(qualifications = listOf(degree("Master of Business")))
        val mapped = map(assessed, job(degree("Bachelor of Science")))

        val assessment = mapped.record.requirementAssessments!!
            .first { it.text == "Bachelor of Science" }
        assertEquals(RequirementAssessmentState.CONFIRMED_GAP, assessment.state)
        assertNull(assessment.reason)
        assertEquals(true, mapped.record.evaluationComplete)
        assertTrue(mapped.record.gaps.any { it.skill == "DEGREE: Bachelor of Science" && it.importance == "MANDATORY" })
    }

    @Test
    fun `matching qualification evidence produces no gap and stays complete`() {
        val assessed = profile().copy(qualifications = listOf(degree("Bachelor of Science")))
        val mapped = map(assessed, job(degree("Bachelor of Science")))

        assertEquals(true, mapped.record.evaluationComplete)
        assertTrue(mapped.record.gaps.none { it.skill.startsWith("DEGREE:") })
    }

    @Test
    fun `empty job required skills keeps skill match unmeasured`() {
        val result = W5ScoreEngine(assets).evaluate(
            profile(),
            job().copy(requiredSkills = emptyList()),
            W5TraceContext("corrective", "", 0L)
        )

        val skillMatch = result.axisScores.first { it.axisName == "SkillMatch" }
        assertFalse(skillMatch.isMeasured)
        assertTrue(result.unknownAxes.contains("SkillMatch"))
        assertFalse(result.isComplete)
    }

    @Test
    fun `empty profile roles and title keeps role relevance unmeasured`() {
        val result = W5ScoreEngine(assets).evaluate(
            profile().copy(roles = emptyList(), currentTitle = null),
            job(),
            W5TraceContext("corrective", "", 0L)
        )

        val roleRelevance = result.axisScores.first { it.axisName == "RoleRelevance" }
        assertFalse(roleRelevance.isMeasured)
        assertTrue(result.unknownAxes.contains("RoleRelevance"))
        assertFalse(result.isComplete)
    }

    @Test
    fun `blank job title keeps role relevance unmeasured`() {
        val result = W5ScoreEngine(assets).evaluate(
            profile(),
            job().copy(title = ""),
            W5TraceContext("corrective", "", 0L)
        )

        val roleRelevance = result.axisScores.first { it.axisName == "RoleRelevance" }
        assertFalse(roleRelevance.isMeasured)
        assertTrue(result.unknownAxes.contains("RoleRelevance"))
        assertFalse(result.isComplete)
    }
    @Test
    fun `empty skills preserve mapped requirements as unassessed inventory`() {
        val mapped = map(profile(skills = emptyList()), job())

        val assessment = mapped.record.requirementAssessments!!.first { it.text == "Kotlin" }
        assertEquals(RequirementAssessmentState.UNASSESSED, assessment.state)
        assertEquals("PROFILE_SKILL_EVIDENCE_ABSENT", assessment.reason)
        assertFalse(mapped.record.evaluationComplete!!)
    }

}
