package com.vantedge.app.w5.scoring

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtractionValidatorTest {

    @Test
    fun `Test Case A - Dictionary prevents unnecessary fuzzy repair`() {
        val result = ExtractionValidator.validate("responsible", taxonomy = emptySet())
        assertEquals(AssessmentVerdict.ACCEPTED, result.verdict)
        assertEquals("DICTIONARY_MATCH", result.reason)
        assertEquals(1.0f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case B - Legitimate typo repair`() {
        val taxonomy = setOf("certified")
        val result = ExtractionValidator.validate("certifed", taxonomy = taxonomy)
        assertEquals(AssessmentVerdict.REPAIRED, result.verdict)
        assertEquals("FUZZY_REPAIR_SUGGESTED", result.reason)
        assertEquals("certified", result.optionalSuggestion)
        assertEquals(0.75f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case C - Unambiguous short typo repair`() {
        val taxonomy = setOf("java")
        val result = ExtractionValidator.validate("jav", taxonomy = taxonomy)
        assertEquals(AssessmentVerdict.REPAIRED, result.verdict)
        assertEquals("FUZZY_REPAIR_SUGGESTED", result.reason)
        assertEquals("java", result.optionalSuggestion)
        assertEquals(0.75f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case D - Truncation fragment rejected`() {
        val result = ExtractionValidator.validate("mation", taxonomy = emptySet())
        assertEquals(AssessmentVerdict.REJECTED, result.verdict)
        assertEquals("KNOWN_TRUNCATED_FRAGMENT", result.reason)
        assertEquals(0.0f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case E - Impossible consonant cluster rejected`() {
        val result = ExtractionValidator.validate("xqzpl", taxonomy = emptySet())
        assertEquals(AssessmentVerdict.REJECTED, result.verdict)
        assertEquals("IMPOSSIBLE_CONSONANT_CLUSTER", result.reason)
        assertEquals(0.0f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case F - Multi-word entity preservation`() {
        val taxonomy = setOf("sql server")
        val result = ExtractionValidator.validate("sql server", taxonomy = taxonomy)
        assertEquals(AssessmentVerdict.ACCEPTED, result.verdict)
        assertEquals("ENTITY_MATCH", result.reason)
        assertEquals(1.0f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case G - Tie-breaking ambiguity`() {
        val taxonomy = setOf("Java", "Jave")
        val result = ExtractionValidator.validate("Jav", taxonomy = taxonomy)
        assertEquals(AssessmentVerdict.LOW_CONFIDENCE, result.verdict)
        assertEquals("AMBIGUOUS_MATCH", result.reason)
        assertEquals(0.5f, result.confidence, 0.0f)
    }

    @Test
    fun `Test Case H - Governance cap on known truncations`() {
        assertTrue(ExtractionValidator.KNOWN_TRUNCATIONS.size <= 15)
    }
}
