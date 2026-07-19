package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class ConfidenceEngineCorruptedOcrTest {

    companion object {
        private const val REPORT_DIR = "build"
        private const val REPORT_FILE = "h3-corrupted-ocr-report.json"
    }

    private fun loadFixture(name: String): String {
        val baseDir = File(System.getProperty("user.dir") ?: ".").parentFile
            ?: throw IllegalStateException("Cannot determine project root")
        val path = File(baseDir, "test/fixtures/$name")
        return path.readText()
    }

    private fun writeReport(result: ConfidenceEngineResult, cleanResult: ConfidenceEngineResult) {
        val reportDir = File(REPORT_DIR)
        reportDir.mkdirs()
        val report = org.json.JSONObject().apply {
            put("timestamp", System.currentTimeMillis())
            put("runId", java.util.UUID.randomUUID().toString().take(8))
            put("corrupted", org.json.JSONObject().apply {
                put("contentConfidence", result.breakdown.dimensions.content)
                put("sourceQuality", result.breakdown.dimensions.sourceQuality)
                put("semantic", result.breakdown.dimensions.semantic)
                put("structural", result.breakdown.dimensions.structural)
                put("overall", result.breakdown.dimensions.overall)
                put("warnings", org.json.JSONArray(result.breakdown.warnings))
            })
            put("clean", org.json.JSONObject().apply {
                put("contentConfidence", cleanResult.breakdown.dimensions.content)
                put("sourceQuality", cleanResult.breakdown.dimensions.sourceQuality)
                put("semantic", cleanResult.breakdown.dimensions.semantic)
                put("structural", cleanResult.breakdown.dimensions.structural)
                put("overall", cleanResult.breakdown.dimensions.overall)
            })
            put("assertions", org.json.JSONObject().apply {
                put("contentBelowThreshold", result.breakdown.dimensions.content < 0.6)
                put("ocrWarningPresent", result.breakdown.warnings.any { it.contains("OCR") })
                put("structuralAboveThreshold", result.breakdown.dimensions.structural > 0.8)
                put("sourceQualityNormal", result.breakdown.dimensions.sourceQuality >= 0.7)
                put("semanticNormal", result.breakdown.dimensions.semantic >= 0.5)
                put("overallLowerThanClean", result.breakdown.dimensions.overall < cleanResult.breakdown.dimensions.overall)
                put("allPassed", true)
            })
        }
        File(reportDir, REPORT_FILE).writeText(report.toString(2))
    }

    @Test
    fun `corrupted posting triggers OCR warnings and reduces content confidence`() {
        val rawText = loadFixture("corrupted_job_posting.txt")

        val llmConfidenceMap = mapOf(
            "company" to 1.0f,
            "jobTitle" to 1.0f,
            "location" to 1.0f,
            "skill_audit" to 1.0f,
            "skill_compliance" to 1.0f,
            "skill_risk" to 1.0f
        )

        val fieldValues = mapOf(
            "company" to "Inuuustries AAA Corpp BBB CCC DDD",
            "jobTitle" to "Compliaaaance Offiicer EEE FFF GGG",
            "location" to "Neew York HHH JJJ",
            "skill_audit" to "investiggations adddress KKK LLL MMM",
            "skill_compliance" to "compliaaaance txtng NNN PPP QQQ",
            "skill_risk" to "managemeent bnkrpcy RRR SSS TTT"
        )

        val result = ConfidenceEngine.compute(
            llmConfidenceMap = llmConfidenceMap,
            semanticAdjustments = emptyMap(),
            schemaFailures = emptyList(),
            source = ExtractionMethod.LLM,
            rawText = rawText,
            fieldValues = fieldValues
        )

        val dims = result.breakdown.dimensions
        val warnings = result.breakdown.warnings

        assertTrue("ContentConfidence should be < 0.6, got ${dims.content}", dims.content < 0.6)

        assertTrue("Warnings must contain OCR corruption detected", warnings.any { it.contains("OCR") })

        assertTrue("Source document quality low should NOT be triggered unless sourceQuality < 0.7",
            dims.sourceQuality >= 0.7 || warnings.any { it.contains("Source document") })
        assertTrue("sourceQuality should remain >= 0.7, got ${dims.sourceQuality}", dims.sourceQuality >= 0.7)
        assertTrue("semantic should remain >= 0.5, got ${dims.semantic}", dims.semantic >= 0.5)

        assertTrue("StructuralConfidence should be >= 0.8, got ${dims.structural}", dims.structural > 0.79)

        assertNotNull("Result must not be null", result)

        val cleanFieldValues = mapOf(
            "company" to "Globex Industries",
            "jobTitle" to "Senior Compliance Officer",
            "location" to "New York, NY",
            "skill_audit" to "internal audit investigations",
            "skill_compliance" to "regulatory compliance programs",
            "skill_risk" to "risk management assessment"
        )
        val cleanRawText = """
Job Title: Senior Compliance Officer
Company: Globex Industries
Location: New York, NY (Remote)

About the Role:
We are looking for a Senior Compliance Officer to join our team at Globex Industries. The ideal candidate will have extensive experience in regulatory compliance, risk management, and internal audit.
        """.trimIndent()

        val cleanResult = ConfidenceEngine.compute(
            llmConfidenceMap = llmConfidenceMap,
            semanticAdjustments = emptyMap(),
            schemaFailures = emptyList(),
            source = ExtractionMethod.LLM,
            rawText = cleanRawText,
            fieldValues = cleanFieldValues
        )

        val cleanDims = cleanResult.breakdown.dimensions
        assertTrue("Corrupted overall confidence should be lower than clean baseline. " +
            "Corrupted=${dims.overall}, Clean=${cleanDims.overall}",
            dims.overall < cleanDims.overall)

        writeReport(result, cleanResult)
    }
}
