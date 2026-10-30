package com.vantedge.app.w5.scoring

import com.vantedge.app.data.domain.CompatibilityRecordMapper
import com.vantedge.app.data.model.RequirementAssessmentState
import com.vantedge.app.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RequirementCoverageTest {

    private val assets = ValidationAssets(stopWords = emptySet(), keywordDictionary = emptyMap())

    private fun profile(
        skills: List<String> = listOf("Kotlin"),
        qualifications: List<RequirementEvidence> = emptyList()
    ) = ValidatedProfile(
        correlationId = "coverage",
        skills = skills,
        qualifications = qualifications,
        roles = listOf("Developer"),
        currentTitle = "Developer",
        experienceYears = 5,
        seniorityLevel = "senior",
        completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
        isAccepted = true,
        isDegraded = false
    )

    private fun job(
        requiredSkills: List<String> = listOf("Kotlin"),
        qualifications: List<RequirementEvidence> = emptyList(),
        requiredYears: Int? = 5
    ) = ValidatedJob(
        correlationId = "coverage",
        requiredSkills = requiredSkills,
        requiredQualifications = qualifications,
        title = "Developer",
        keywords = listOf("Kotlin"),
        requiredYears = requiredYears,
        seniorityLevel = "senior",
        completedFields = setOf("requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"),
        isAccepted = true,
        isDegraded = false
    )

    private fun skill(text: String, canonical: String? = text, status: ClassificationStatus = ClassificationStatus.NORMALIZED) =
        RequirementEvidence(
            originalText = text,
            category = RequirementCategory.SKILL,
            classificationStatus = status,
            matchedTaxonomySkill = canonical,
            importance = "MANDATORY"
        )

    private fun degree(text: String) = RequirementEvidence(
        originalText = text,
        category = RequirementCategory.DEGREE,
        classificationStatus = ClassificationStatus.NORMALIZED,
        importance = "MANDATORY"
    )

    private fun map(
        validatedProfile: ValidatedProfile,
        validatedJob: ValidatedJob,
        inventory: List<RequirementEvidence>
    ) = CompatibilityRecordMapper().map(
        scoreResult = W5ScoreEngine(assets).evaluate(
            validatedProfile,
            validatedJob,
            W5TraceContext("coverage", "", 0L)
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
        diagnostics = RequirementDiagnostics(
            diagnostics = emptyList(),
            summary = DiagnosticsSummary(0, 0, 0),
            inventory = inventory
        )
    )

    @Test
    fun `unmapped requirement is unassessed and is not a confirmed gap`() {
        val mapped = map(
            profile(),
            job(),
            listOf(skill("Kotlin"), skill("Data Protection", canonical = null, status = ClassificationStatus.UNMAPPED))
        )

        val assessments = mapped.record.requirementAssessments!!
        assertEquals(2, assessments.size)
        assertEquals(RequirementAssessmentState.MATCHED, assessments.first { it.text == "Kotlin" }.state)

        val unmapped = assessments.first { it.text == "Data Protection" }
        assertEquals(RequirementAssessmentState.UNASSESSED, unmapped.state)
        assertEquals("TAXONOMY_MISS", unmapped.reason)

        assertTrue(mapped.record.gaps.isEmpty())
        assertFalse(mapped.record.evaluationComplete!!)
    }

    @Test
    fun `empty skill evidence keeps every requirement unassessed and inventory intact`() {
        val mapped = map(
            profile(skills = emptyList()),
            job(),
            listOf(skill("Kotlin"), skill("Data Protection", canonical = null, status = ClassificationStatus.UNMAPPED))
        )

        val assessments = mapped.record.requirementAssessments!!
        assertEquals(2, assessments.size)
        assertTrue(assessments.all { it.state == RequirementAssessmentState.UNASSESSED })
        assertEquals(
            "PROFILE_SKILL_EVIDENCE_ABSENT",
            assessments.first { it.text == "Kotlin" }.reason
        )
        assertTrue(mapped.record.gaps.isEmpty())
        assertFalse(mapped.record.evaluationComplete!!)
    }

    @Test
    fun `confirmed gaps and unassessed requirements remain distinct`() {
        val mapped = map(
            profile(),
            job(requiredSkills = listOf("Kotlin", "Kubernetes")),
            listOf(
                skill("Kotlin"),
                skill("Kubernetes"),
                skill("Data Protection", canonical = null, status = ClassificationStatus.UNMAPPED)
            )
        )

        val assessments = mapped.record.requirementAssessments!!
        assertEquals(RequirementAssessmentState.MATCHED, assessments.first { it.text == "Kotlin" }.state)
        assertEquals(RequirementAssessmentState.CONFIRMED_GAP, assessments.first { it.text == "Kubernetes" }.state)
        assertEquals(RequirementAssessmentState.UNASSESSED, assessments.first { it.text == "Data Protection" }.state)
        assertEquals(1, mapped.record.gaps.size)
        assertFalse(mapped.record.evaluationComplete!!)
    }

    @Test
    fun `qualifications are assessed independently of skill evidence`() {
        val mapped = map(
            profile(skills = emptyList(), qualifications = listOf(degree("Bachelor of Science"))),
            job(requiredSkills = listOf("Kotlin"), qualifications = listOf(degree("Bachelor of Science"))),
            listOf(skill("Kotlin"), degree("Bachelor of Science"))
        )

        val assessments = mapped.record.requirementAssessments!!
        assertEquals(
            RequirementAssessmentState.MATCHED,
            assessments.first { it.text == "Bachelor of Science" }.state
        )
        assertEquals(
            RequirementAssessmentState.UNASSESSED,
            assessments.first { it.text == "Kotlin" }.state
        )
        assertFalse(mapped.record.evaluationComplete!!)
    }

    @Test
    fun `new records always persist a coverage inventory`() {
        val mapped = map(profile(), job(requiredSkills = emptyList()), emptyList())

        assertNotNull(mapped.record.requirementAssessments)
        assertTrue(mapped.record.requirementAssessments!!.isEmpty())
    }

    @Test
    fun `partial measurement persists a provisional score without changing legacy score`() {
        val partialJob = job(requiredSkills = listOf("Kotlin"), requiredYears = null).copy(
            title = "",
            keywords = emptyList(),
            seniorityLevel = null,
            completedFields = setOf("requiredSkills")
        )
        val mapped = map(profile(), partialJob, listOf(skill("Kotlin")))

        val scoreResult = W5ScoreEngine(assets).evaluate(
            profile(),
            partialJob,
            W5TraceContext("coverage", "", 0L)
        )
        val legacyScore = (scoreResult.totalScore.coerceIn(0.0, 1.0) * 100).toInt()
        assertEquals(legacyScore, mapped.record.score)
        assertEquals(
            (scoreResult.provisionalTotalScore!! * 100).toInt(),
            mapped.record.provisionalScore
        )

        val skillMatch = scoreResult.axisScores.first { it.axisName == "SkillMatch" }.score
        val constraint = scoreResult.axisScores.first { it.axisName == "ConstraintCompliance" }.score
        val expectedVacancy = (((skillMatch * 0.5) + (constraint * 0.2)) / 0.7 * 100).toInt()
        assertEquals(expectedVacancy, mapped.record.provisionalVacancyScore)
    }

    @Test
    fun `fully measured record keeps provisional equal to legacy values`() {
        val mapped = map(profile(), job(), listOf(skill("Kotlin")))

        assertEquals(mapped.record.score, mapped.record.provisionalScore)
        assertEquals(mapped.record.vacancyScore, mapped.record.provisionalVacancyScore)
    }

    @Test
    fun `legacy records keep unknown coverage null`() {
        val legacy = com.vantedge.app.data.model.CompatibilityRecord(
            jobTitle = "Developer",
            company = "Acme",
            jobDescription = "Developer",
            score = 80,
            roleSummary = "",
            eligibilitySummary = "",
            profileStats = com.vantedge.app.data.model.ProfileStats(0, 0, 0, 0, 0, ""),
            relevancyItems = emptyList(),
            gaps = emptyList(),
            criticalGapCount = 0
        )

        assertNull(legacy.requirementAssessments)
        assertNull(legacy.provisionalScore)
        assertNull(legacy.provisionalVacancyScore)
    }
}
