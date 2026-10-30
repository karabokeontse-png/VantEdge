package com.vantedge.app.data.storage

import com.google.gson.Gson
import com.vantedge.app.data.domain.ScoreCompleteness
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.RequirementAssessmentState
import com.vantedge.app.ui.screens.ScorePresentation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
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
    @Test
    fun `missing coverage fields deserialize to unknown`() {
        val record = gson.fromJson(legacyJson(), CompatibilityRecord::class.java)

        assertNull(record.requirementAssessments)
        assertNull(record.provisionalScore)
        assertNull(record.provisionalVacancyScore)
    }

    @Test
    fun `recorded coverage and provisional values round trip`() {
        val record = gson.fromJson(
            legacyJson(
                "\"provisionalScore\": 72, \"provisionalVacancyScore\": 68, " +
                    "\"requirementAssessments\": [" +
                    "{\"text\": \"Kotlin\", \"state\": \"MATCHED\", \"canonical\": \"Kotlin\", \"importance\": \"MANDATORY\"}" +
                    "]"
            ),
            CompatibilityRecord::class.java
        )

        assertNotNull(record.requirementAssessments)
        assertEquals(1, record.requirementAssessments!!.size)
        assertEquals(RequirementAssessmentState.MATCHED, record.requirementAssessments!!.first().state)
        assertEquals(72, record.provisionalScore)
        assertEquals(68, record.provisionalVacancyScore)

        val roundTripped = gson.fromJson(gson.toJson(record), CompatibilityRecord::class.java)
        assertNotNull(roundTripped.requirementAssessments)
        assertEquals(RequirementAssessmentState.MATCHED, roundTripped.requirementAssessments!!.first().state)
        assertEquals(72, roundTripped.provisionalScore)
    }

    @Test
    fun `unassessed reason survives serialization`() {
        val record = gson.fromJson(
            legacyJson(
                "\"requirementAssessments\": [" +
                    "{\"text\": \"Data Protection\", \"state\": \"UNASSESSED\", \"reason\": \"TAXONOMY_MISS\", \"importance\": \"MANDATORY\"}" +
                    "]"
            ),
            CompatibilityRecord::class.java
        )

        val roundTripped = gson.fromJson(gson.toJson(record), CompatibilityRecord::class.java)
        val assessment = roundTripped.requirementAssessments!!.first()
        assertEquals(RequirementAssessmentState.UNASSESSED, assessment.state)
        assertEquals("TAXONOMY_MISS", assessment.reason)
    }

    @Test
    fun `recorded empty inventory stays distinct from unknown coverage`() {
        val record = gson.fromJson(
            legacyJson("\"requirementAssessments\": []"),
            CompatibilityRecord::class.java
        )

        assertNotNull(record.requirementAssessments)
        assertTrue(record.requirementAssessments!!.isEmpty())
    }

}
