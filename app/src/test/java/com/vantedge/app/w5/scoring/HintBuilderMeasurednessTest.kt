package com.vantedge.app.w5.scoring

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HintBuilderMeasurednessTest {

    private fun gap(missing: List<String> = emptyList()) =
        GapAnalysis(missing = missing, weak = emptyList(), matched = emptyList())

    private fun hints(vararg axes: AxisScore): List<String> =
        HintBuilder.build(gap(), axes.toList()).hints

    @Test
    fun `unmeasured experience score does not generate an experience hint`() {
        val result = hints(AxisScore("ExperienceAlignment", 0.0, false))

        assertFalse(result.any { it.contains("ExperienceAlignment") })
    }

    @Test
    fun `measured low experience score still generates the existing hint`() {
        val result = hints(AxisScore("ExperienceAlignment", 0.3, true))

        assertTrue(result.any { it == "Increase exposure to target domain based on ExperienceAlignment" })
    }

    @Test
    fun `measured experience score above the threshold does not generate a hint`() {
        val result = hints(AxisScore("ExperienceAlignment", 0.8, true))

        assertFalse(result.any { it.contains("ExperienceAlignment") })
    }

    @Test
    fun `unmeasured seniority score does not generate a seniority hint`() {
        val result = hints(AxisScore("SeniorityFit", 0.0, false))

        assertFalse(result.any { it.contains("SeniorityFit") })
    }

    @Test
    fun `measured low seniority score still generates the existing hint`() {
        val result = hints(AxisScore("SeniorityFit", 0.2, true))

        assertTrue(result.any { it == "Re-evaluate trajectory based on SeniorityFit" })
    }

    @Test
    fun `unmeasured axes do not suppress a measured skill hint`() {
        val result = HintBuilder.build(
            gap(missing = listOf("Kubernetes")),
            listOf(
                AxisScore("ExperienceAlignment", 0.0, false),
                AxisScore("SeniorityFit", 0.0, false)
            )
        ).hints

        assertEquals(listOf("Acquire based on SkillMatch: Kubernetes"), result)
    }

    @Test
    fun `unmeasured experience does not suppress a measured seniority hint`() {
        val result = hints(
            AxisScore("ExperienceAlignment", 0.0, false),
            AxisScore("SeniorityFit", 0.2, true)
        )

        assertFalse(result.any { it.contains("ExperienceAlignment") })
        assertTrue(result.any { it == "Re-evaluate trajectory based on SeniorityFit" })
    }
}
