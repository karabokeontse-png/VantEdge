package com.vantedge.app.w5.scoring

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProvisionalScoreTest {

    private val assets = ValidationAssets(stopWords = emptySet(), keywordDictionary = emptyMap())
    private val engine = W5ScoreEngine(assets)

    private fun profile(skills: List<String> = listOf("Kotlin")) = ValidatedProfile(
        correlationId = "provisional",
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

    private fun job(
        requiredSkills: List<String> = listOf("Kotlin"),
        title: String = "",
        keywords: List<String> = emptyList(),
        requiredYears: Int? = null,
        seniorityLevel: String? = null,
        completedFields: Set<String> = setOf("requiredSkills")
    ) = ValidatedJob(
        correlationId = "provisional",
        requiredSkills = requiredSkills,
        title = title,
        keywords = keywords,
        requiredYears = requiredYears,
        seniorityLevel = seniorityLevel,
        completedFields = completedFields,
        isAccepted = true,
        isDegraded = false
    )

    private fun evaluate(profile: ValidatedProfile, job: ValidatedJob) =
        engine.evaluate(profile, job, W5TraceContext("provisional", "", 0L))

    @Test
    fun `provisional score normalizes over measured weights only`() {
        val result = evaluate(profile(), job())

        assertFalse(result.isComplete)
        val measured = result.axisScores.filter { it.isMeasured }
        assertTrue(measured.isNotEmpty())
        val expectedWeight = measured.sumOf { AxisWeights.get(it.axisName) }
        val expected = measured.sumOf { it.score * AxisWeights.get(it.axisName) } / expectedWeight

        assertEquals(expected, result.provisionalTotalScore!!, 1e-12)
        assertNotEquals(result.totalScore, result.provisionalTotalScore!!, 1e-12)
    }

    @Test
    fun `legacy total score keeps unmeasured axes as zero`() {
        val result = evaluate(profile(), job())

        val legacy = result.axisScores.sumOf { it.score * AxisWeights.get(it.axisName) }
        assertEquals(legacy, result.totalScore, 1e-12)
        result.axisScores.filterNot { it.isMeasured }.forEach {
            assertEquals(0.0, it.score, 0.0)
        }
    }

    @Test
    fun `fully measured input keeps provisional equal to legacy score`() {
        val result = evaluate(
            profile(),
            job(
                title = "Developer",
                keywords = listOf("Kotlin"),
                requiredYears = 5,
                seniorityLevel = "senior",
                completedFields = setOf(
                    "requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"
                )
            )
        )

        assertTrue(result.isComplete)
        assertEquals(result.totalScore, result.provisionalTotalScore!!, 1e-12)
    }

    @Test
    fun `no measured evidence axis yields a null provisional score`() {
        val result = evaluate(
            profile(skills = emptyList()),
            job(requiredSkills = emptyList())
        )

        assertNull(result.provisionalTotalScore)
        assertFalse(result.isComplete)
    }

    @Test
    fun `provisional score stays within the unit interval`() {
        val result = evaluate(
            profile(),
            job(
                title = "Developer",
                keywords = listOf("Kotlin"),
                requiredYears = 5,
                seniorityLevel = "senior",
                completedFields = setOf(
                    "requiredSkills", "title", "keywords", "requiredYears", "seniorityLevel"
                )
            )
        )

        val provisional = result.provisionalTotalScore!!
        assertTrue(provisional in 0.0..1.0)
    }
}
