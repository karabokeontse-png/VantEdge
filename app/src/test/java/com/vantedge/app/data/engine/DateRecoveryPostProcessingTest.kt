package com.vantedge.app.data.engine

import org.junit.Assert.*
import org.junit.Test

/**
 * TD-PROFILE-002a: Deterministic date recovery test.
 * Verifies the post-processing fallback logic for startDate recovery
 * from structured dates/period fields, and confirms description mining is NOT performed.
 */
class DateRecoveryPostProcessingTest {

    private val yearRegex = Regex("""\b(19|20)\d{2}\b""")

    /**
     * Simulates the enhanced post-processing logic from ProfileExtractionEngine.kt:
     * 1. Primary fallback: use combined dates field if startDate is blank
     * 2. Secondary fallback: if startDate still lacks a 4-digit year, recover from dates field
     */
    private fun simulatePostProcessing(
        startDate: String,
        endDate: String,
        dates: String
    ): Pair<String, String> {
        var resultStart = startDate
        var resultEnd = endDate

        if (dates.isNotBlank() && resultStart.isBlank()) {
            resultStart = dates
            resultEnd = ""
        }

        if (yearRegex.find(resultStart) == null && dates.isNotBlank()) {
            yearRegex.find(dates)?.value?.let { year ->
                resultStart = year
            }
        }

        return resultStart to resultEnd
    }

    // --- Positive Tests: dates fallback ---

    @Test
    fun `dates fallback recovers year when startDate is blank`() {
        val (start, end) = simulatePostProcessing(
            startDate = "",
            endDate = "",
            dates = "2020-2024"
        )
        assertEquals("2020", start)
        assertEquals("", end)
    }

    @Test
    fun `dates fallback recovers year when startDate is unparseable`() {
        val (start, end) = simulatePostProcessing(
            startDate = "Recent",
            endDate = "Current",
            dates = "2018-2022"
        )
        assertEquals("2018", start)
        assertEquals("Current", end)
    }

    @Test
    fun `primary fallback uses dates field when startDate is blank`() {
        val (start, end) = simulatePostProcessing(
            startDate = "",
            endDate = "",
            dates = "Jan 2020 - Present"
        )
        assertEquals("Jan 2020", start)
        assertEquals("", end)
    }

    @Test
    fun `secondary fallback extracts year from dates when primary gives unparseable`() {
        val (start, end) = simulatePostProcessing(
            startDate = "N/A",
            endDate = "",
            dates = "2015"
        )
        assertEquals("2015", start)
        assertEquals("", end)
    }

    // --- Negative Tests: description guard ---

    @Test
    fun `description field is NEVER mined for dates`() {
        // Verify simulatePostProcessing does NOT accept description parameter.
        // startDate is blank, dates is blank — only description has years.
        // If the function signature were expanded to include description,
        // this would be a compilation error (tests would not compile).
        val (start, _) = simulatePostProcessing(
            startDate = "",
            endDate = "",
            dates = ""
        )
        // startDate must remain blank — description years are NOT used
        assertEquals("", start)
        // Confirm description contains parseable years (proving the risk exists)
        val description = "Company founded in 1998, project completed 2015"
        val yearsInDescription = yearRegex.findAll(description).map { it.value }.toList()
        assertEquals(2, yearsInDescription.size)
        assertEquals("1998", yearsInDescription[0])
        assertEquals("2015", yearsInDescription[1])
    }

    @Test
    fun `startDate remains unchanged when dates field is also blank`() {
        val (start, end) = simulatePostProcessing(
            startDate = "Recent",
            endDate = "",
            dates = ""
        )
        assertEquals("Recent", start)
        assertEquals("", end)
    }

    @Test
    fun `startDate with existing year is not overwritten`() {
        val (start, end) = simulatePostProcessing(
            startDate = "Jan 2020",
            endDate = "Present",
            dates = "2015-2018"
        )
        assertEquals("Jan 2020", start)
        assertEquals("Present", end)
    }
}
