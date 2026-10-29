package com.vantedge.app.data.storage

import com.google.gson.Gson
import com.vantedge.app.data.domain.ScoreCompleteness
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.ui.screens.ScorePresentation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CompatibilityRecordLegacyJsonTest {

    private val gson = Gson()

    private fun legacyJson(extra: String = "") = """
        {
          "id": 1,
          "jobTitle": "Developer",
          "company": "Acme",
          "jobDescription": "Developer",
          "score": 85,
          "vacancyScore": 80,
          "roleSummary": "",
          "eligibilitySummary": "You meet all requirements.",
          "dataIntegrityNote": "",
          "profileStats": {
            "yearsExperience": 5,
            "certificationCount": 0,
            "skillCount": 1,
            "matchedCount": 1,
            "gapCount": 0,
            "dataIntegrityNote": ""
          },
          "qualificationRatio": { "matched": 1, "total": 1, "gaps": 0 },
          "relevancyItems": [],
          "gaps": [],
          "criticalGapCount": 0,
          "analysisSource": "DETERMINISTIC_W5",
          "createdAt": 123
          ${if (extra.isBlank()) "" else ", $extra"}
        }
    """.trimIndent()

    @Test
    fun `missing evaluationComplete deserializes to unknown`() {
        val record = gson.fromJson(legacyJson(), CompatibilityRecord::class.java)

        assertNull(record.evaluationComplete)
        assertFalse(ScoreCompleteness.isAuthoritative(record))
        assertFalse(ScorePresentation.eligibilityClaimAllowed(record))
    }

    @Test
    fun `explicit null evaluationComplete remains unknown`() {
        val record = gson.fromJson(
            legacyJson("\"evaluationComplete\": null"),
            CompatibilityRecord::class.java
        )

        assertNull(record.evaluationComplete)
        assertFalse(ScoreCompleteness.isAuthoritative(record))
    }

    @Test
    fun `existing record fields remain preserved`() {
        val record = gson.fromJson(legacyJson(), CompatibilityRecord::class.java)

        assertEquals("Developer", record.jobTitle)
        assertEquals("Acme", record.company)
        assertEquals(85, record.score)
        assertEquals(5, record.profileStats.yearsExperience)
    }

    @Test
    fun `explicit true remains authoritative when the record permits it`() {
        val record = gson.fromJson(
            legacyJson("\"evaluationComplete\": true"),
            CompatibilityRecord::class.java
        )

        assertEquals(true, record.evaluationComplete)
        assertTrue(ScoreCompleteness.isAuthoritative(record))
        assertTrue(ScorePresentation.eligibilityClaimAllowed(record))
    }

    @Test
    fun `gaps prevent a positive eligibility claim`() {
        val record = gson.fromJson(
            legacyJson(
                "\"evaluationComplete\": true, \"gaps\": [" +
                    "{\"skill\": \"Kubernetes\", \"importance\": \"MANDATORY\", \"description\": \"\", \"courses\": []}" +
                    "]"
            ),
            CompatibilityRecord::class.java
        )

        assertTrue(ScoreCompleteness.isAuthoritative(record))
        assertFalse(ScorePresentation.eligibilityClaimAllowed(record))
        assertFalse(ScorePresentation.eligibilityBody(record).contains("You meet all requirements."))
        assertTrue(ScorePresentation.eligibilityBody(record).contains("Confirmed gaps"))
    }

    @Test
    fun `serialization round trip preserves unknown state`() {
        val record = gson.fromJson(legacyJson(), CompatibilityRecord::class.java)
        val roundTripped = gson.fromJson(gson.toJson(record), CompatibilityRecord::class.java)

        assertNull(roundTripped.evaluationComplete)
        assertFalse(ScoreCompleteness.isAuthoritative(roundTripped))
    }
}
