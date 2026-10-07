package com.vantedge.pipeline.evidence

import com.fasterxml.jackson.databind.ObjectMapper
import com.vantedge.app.data.model.CompatibilityAnalysisSource
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.pipeline.contract.ContractValidationResult
import com.vantedge.pipeline.contract.ContractValidator
import com.vantedge.pipeline.contract.ExtractedAiPayload
import com.vantedge.pipeline.contract.ExtractionMetadata
import com.vantedge.pipeline.contract.JobType
import org.junit.Assert.*
import org.junit.Test

/**
 * Phase D regression tests.
 *
 * Scope note (important, do not misread):
 *  - TEST 1 and TEST 2 verify CONTRACT INDEPENDENCE and ANALYTICAL-FIELD INDEPENDENCE.
 *    They do NOT dynamically exercise the AI-unavailable / null-response path.
 *  - The AI-unavailable path is UNTESTED and verified by source inspection only
 *    (Pre-Check 1: no test seam exists; AiGateway is a final class).
 *  - These tests call ContractValidator and EvidenceIntegrityDetector directly.
 *    They never construct OptimizationOrchestrator or AiGateway.
 */
class PhaseDAuthorityTest {

    private val mapper = ObjectMapper()
    private val validator = ContractValidator()

    private fun payload(json: String): ExtractedAiPayload = ExtractedAiPayload(
        rawJson = json,
        parsedObject = mapper.readTree(json),
        metadata = ExtractionMetadata(
            requestId = "", correlationId = "test", modelName = "",
            extractionStrategy = "direct_parse", rawLength = json.length, normalizedLength = json.length
        )
    )

    private fun record(
        score: Int,
        vacancyScore: Int = score,
        analysisSource: CompatibilityAnalysisSource?
    ) = CompatibilityRecord(
        jobTitle = "DATA PROTECTION MANAGER",
        company = "Botswana School of Business Sciences",
        jobDescription = "Data protection compliance role.",
        score = score,
        vacancyScore = vacancyScore,
        roleSummary = "Role summary narrative.",
        eligibilitySummary = "Eligibility narrative.",
        dataIntegrityNote = "",
        profileStats = com.vantedge.app.data.model.ProfileStats(8, 4, 7, 3, 5, ""),
        qualificationRatio = com.vantedge.app.data.model.QualificationRatio(3, 8, 5),
        relevancyItems = emptyList(),
        gaps = emptyList(),
        criticalGapCount = 0,
        analysisSource = analysisSource
    )

    // ---------------------------------------------------------------- TEST 1
    @Test
    fun `TEST 1 - corrupted AI analytical values do not create contract violation`() {
        // score is a STRING (was type-mismatched), vacancyScore is negative,
        // profileStats is a string not an object, relevancyItems is a string not an array.
        val corrupted = """
            {"score":"not-a-number","vacancyScore":-9999,"profileStats":"broken",
             "relevancyItems":"broken","gaps":"broken","criticalGapCount":"broken",
             "roleSummary":"A role summary.","eligibilitySummary":"An eligibility summary."}
        """.trimIndent()

        val result = validator.validate(JobType.VACANCY_SCORING, payload(corrupted))

        assertTrue(
            "Corrupted DISCARDED AI analytical fields must not produce contract_violation; got $result",
            result is ContractValidationResult.Success
        )
    }

    // ---------------------------------------------------------------- TEST 2
    @Test
    fun `TEST 2 - absent AI analytical fields do not produce contract violation`() {
        // Narrative-only response. Every analytical field is absent.
        val narrativeOnly = """
            {"roleSummary":"A role summary.","eligibilitySummary":"An eligibility summary."}
        """.trimIndent()

        val result = validator.validate(JobType.VACANCY_SCORING, payload(narrativeOnly))

        assertTrue(
            "Absent DISCARDED AI analytical fields must not produce contract_violation; got $result",
            result is ContractValidationResult.Success
        )
    }

    @Test
    fun `TEST 2b - completely empty AI response body still validates`() {
        val empty = "{}"
        val result = validator.validate(JobType.VACANCY_SCORING, payload(empty))
        assertTrue(
            "A narrative-free AI response must not block deterministic analysis; got $result",
            result is ContractValidationResult.Success
        )
    }

    // ---------------------------------------------------------------- TEST 4
    @Test
    fun `TEST 4 - valid W5 provenance is not classified THIN`() {
        val stubRegistry = object : EvidenceRegistry {
            override fun query(category: EvidenceCategory, key: String) =
                EvidenceResult.Missing
            override fun listKeys(category: EvidenceCategory) = emptyList<String>()
            override fun hasEvidence(category: EvidenceCategory, key: String) = false
        }

        val deterministic = record(score = 55, analysisSource = CompatibilityAnalysisSource.DETERMINISTIC_W5)
        val report = EvidenceIntegrityDetector.validate(deterministic, stubRegistry)

        val scoreEntry = report.entries.first { it.fieldPath == "score" }
        assertEquals(
            "A deterministic in-range score must be VALID, not THIN",
            ViolationType.VALID, scoreEntry.violationType
        )

        val vacancyEntry = report.entries.first { it.fieldPath == "vacancyScore" }
        assertEquals(ViolationType.VALID, vacancyEntry.violationType)
    }

    @Test
    fun `TEST 4b - null legacy provenance is NOT promoted to deterministic`() {
        val stubRegistry = object : EvidenceRegistry {
            override fun query(category: EvidenceCategory, key: String) =
                EvidenceResult.Missing
            override fun listKeys(category: EvidenceCategory) = emptyList<String>()
            override fun hasEvidence(category: EvidenceCategory, key: String) = false
        }

        val legacy = record(score = 55, analysisSource = null)
        val report = EvidenceIntegrityDetector.validate(legacy, stubRegistry)

        val scoreEntry = report.entries.first { it.fieldPath == "score" }
        assertEquals(
            "Legacy/null provenance must keep the original THIN classification",
            ViolationType.THIN, scoreEntry.violationType
        )
    }

    // ---------------------------------------------------------------- TEST 5
    @Test
    fun `TEST 5 - deterministic provenance does not bypass range validation`() {
        val stubRegistry = object : EvidenceRegistry {
            override fun query(category: EvidenceCategory, key: String) =
                EvidenceResult.Missing
            override fun listKeys(category: EvidenceCategory) = emptyList<String>()
            override fun hasEvidence(category: EvidenceCategory, key: String) = false
        }

        val outOfRange = record(score = 250, analysisSource = CompatibilityAnalysisSource.DETERMINISTIC_W5)
        val report = EvidenceIntegrityDetector.validate(outOfRange, stubRegistry)

        val scoreEntry = report.entries.first { it.fieldPath == "score" }
        assertEquals(
            "An out-of-range DETERMINISTIC score must still be detected as MISMATCH",
            ViolationType.MISMATCH, scoreEntry.violationType
        )
        assertNotNull("Out-of-range deterministic score must carry a detail message", scoreEntry.detail)

        val vacancyEntry = report.entries.first { it.fieldPath == "vacancyScore" }
        assertEquals(ViolationType.MISMATCH, vacancyEntry.violationType)
    }

    @Test
    fun `TEST 5b - enforcement still escalates a deterministic out-of-range score to HALT`() {
        val stubRegistry = object : EvidenceRegistry {
            override fun query(category: EvidenceCategory, key: String) =
                EvidenceResult.Missing
            override fun listKeys(category: EvidenceCategory) = emptyList<String>()
            override fun hasEvidence(category: EvidenceCategory, key: String) = false
        }

        val outOfRange = record(score = 250, analysisSource = CompatibilityAnalysisSource.DETERMINISTIC_W5)
        val decision = EvidencePolicyEnforcer.enforce(EvidenceIntegrityDetector.validate(outOfRange, stubRegistry))

        assertEquals(
            "score is a CRITICAL field; a deterministic MISMATCH must escalate, not annotate",
            EnforcementAction.HALT, decision.action
        )
    }
}
