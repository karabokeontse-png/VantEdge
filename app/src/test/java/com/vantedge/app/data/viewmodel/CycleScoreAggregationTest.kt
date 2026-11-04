package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CycleScoreAggregationTest {

    private fun record(score: Int, complete: Boolean?, provisional: Int? = null) = CompatibilityRecord(
        jobTitle = "Developer",
        company = "Acme",
        jobDescription = "Developer",
        score = score,
        roleSummary = "",
        eligibilitySummary = "",
        profileStats = ProfileStats(0, 0, 0, 0, 0, ""),
        relevancyItems = emptyList(),
        gaps = emptyList(),
        criticalGapCount = 0,
        evaluationComplete = complete,
        provisionalScore = provisional
    )

    private fun cycle(
        id: String,
        title: String,
        score: Int,
        complete: Boolean?,
        provisional: Int? = null,
        version: Int? = null,
        visible: Boolean = true
    ) = GenerationCycle(
        id = id,
        jobTitle = title,
        company = "Acme",
        jobDescription = "Developer",
        profileSnapshot = UserProfile(),
        version = version,
        isVisibleInHistory = visible,
        compatibility = record(score, complete, provisional)
    )

    @Test
    fun `average excludes incomplete and legacy records`() {
        val cycles = listOf(
            cycle("a", "Engineer A", 80, true),
            cycle("b", "Engineer B", 40, false),
            cycle("c", "Engineer C", 100, null),
            cycle("d", "Engineer D", 60, true)
        )

        assertEquals(70, CycleScoreAggregation.averageScore(cycles))
        assertEquals(2, CycleScoreAggregation.incompleteScoreCount(cycles))
    }

    @Test
    fun `best role ignores incomplete higher score`() {
        val cycles = listOf(
            cycle("a", "Engineer A", 80, true),
            cycle("b", "Engineer B", 100, false),
            cycle("c", "Engineer C", 95, null)
        )

        assertEquals("Engineer A", CycleScoreAggregation.bestScoringRole(cycles))
    }

    @Test
    fun `previous score is hidden when either side is incomplete`() {
        val previousIncomplete = cycle("p", "Engineer", 50, false, version = 1)
        val current = cycle("c", "Engineer", 90, true, version = 2)
        assertNull(CycleScoreAggregation.previousScore(listOf(previousIncomplete, current), current))

        val previousComplete = cycle("p2", "Engineer", 70, true, version = 1)
        assertEquals(
            70,
            CycleScoreAggregation.previousScore(listOf(previousComplete, current), current)
        )

        val currentIncomplete = cycle("c2", "Engineer", 90, null, version = 2)
        assertNull(
            CycleScoreAggregation.previousScore(listOf(previousComplete, currentIncomplete), currentIncomplete)
        )
    }

    @Test
    fun `no complete evaluations returns unavailable aggregates`() {
        val cycles = listOf(
            cycle("a", "Engineer A", 80, false),
            cycle("b", "Engineer B", 90, null)
        )

        assertNull(CycleScoreAggregation.averageScore(cycles))
        assertNull(CycleScoreAggregation.bestScoringRole(cycles))
        assertEquals(2, CycleScoreAggregation.incompleteScoreCount(cycles))
    }

    @Test
    fun `invisible incomplete records are excluded from aggregates and count`() {
        val cycles = listOf(
            cycle("a", "Engineer A", 80, true),
            cycle("b", "Engineer B", 100, false, visible = false)
        )

        assertEquals(80, CycleScoreAggregation.averageScore(cycles))
        assertEquals("Engineer A", CycleScoreAggregation.bestScoringRole(cycles))
        assertEquals(0, CycleScoreAggregation.incompleteScoreCount(cycles))
    }

    @Test
    fun `improved count excludes incomplete later version`() {
        val previous = cycle("p", "Engineer", 60, true, version = 1)
        val current = cycle("c", "Engineer", 90, false, version = 2)

        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, current)))
    }

    @Test
    fun `improved count excludes complete lower score`() {
        val previous = cycle("p", "Engineer", 80, true, version = 1)
        val current = cycle("c", "Engineer", 70, true, version = 2)

        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, current)))
    }

    @Test
    fun `improved count excludes complete equal score`() {
        val previous = cycle("p", "Engineer", 80, true, version = 1)
        val current = cycle("c", "Engineer", 80, true, version = 2)

        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, current)))
    }

    @Test
    fun `improved count includes complete strictly higher comparable score`() {
        val previous = cycle("p", "Engineer", 60, true, version = 1)
        val current = cycle("c", "Engineer", 90, true, version = 2)

        assertEquals(1, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, current)))
    }

    @Test
    fun `improved count excludes unavailable or invalid comparison`() {
        val noPrior = cycle("c", "Engineer", 90, true, version = 2)
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(noPrior)))

        val incompletePrior = cycle("p", "Engineer", 60, false, version = 1)
        val current = cycle("c2", "Engineer", 90, true, version = 2)
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(incompletePrior, current)))

        val noVersion = cycle("n", "Engineer", 90, true, version = null)
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(noVersion)))
    }

    @Test
    fun `improved count does not compare unrelated contexts`() {
        val previous = cycle("p", "Engineer", 60, true, version = 1)
        val differentTitle = cycle("c", "Manager", 90, true, version = 2)
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, differentTitle)))

        val differentCompany = cycle("c2", "Engineer", 90, true, version = 2).copy(company = "Other")
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, differentCompany)))
    }

    @Test
    fun `version number alone cannot establish improvement`() {
        val completeV2WithoutPrior = cycle("v2", "Engineer", 90, true, version = 2)
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(completeV2WithoutPrior)))

        val incompleteV2 = cycle("v2i", "Engineer", 70, false, version = 2)
        val completeV3 = cycle("v3", "Engineer", 95, true, version = 3)
        assertEquals(0, CycleScoreAggregation.improvedApplicationsCount(listOf(incompleteV2, completeV3)))
    }
    @Test
    fun `authoritative aggregates ignore provisional scores`() {
        val cycles = listOf(
            cycle("a", "Engineer A", 80, true, provisional = 100),
            cycle("b", "Engineer B", 60, true, provisional = 10)
        )

        assertEquals(70, CycleScoreAggregation.averageScore(cycles))
        assertEquals("Engineer A", CycleScoreAggregation.bestScoringRole(cycles))
    }

    @Test
    fun `incomplete record with provisional score is still excluded`() {
        val cycles = listOf(
            cycle("a", "Engineer A", 80, true, provisional = 80),
            cycle("b", "Engineer B", 100, false, provisional = 100)
        )

        assertEquals(80, CycleScoreAggregation.averageScore(cycles))
        assertEquals(1, CycleScoreAggregation.incompleteScoreCount(cycles))
    }

    @Test
    fun `provisional values do not affect previous score or improvement`() {
        val previous = cycle("p", "Engineer", 60, true, provisional = 100, version = 1)
        val current = cycle("c", "Engineer", 90, true, provisional = 10, version = 2)

        assertEquals(
            60,
            CycleScoreAggregation.previousScore(listOf(previous, current), current)
        )
        assertEquals(1, CycleScoreAggregation.improvedApplicationsCount(listOf(previous, current)))
    }
}
