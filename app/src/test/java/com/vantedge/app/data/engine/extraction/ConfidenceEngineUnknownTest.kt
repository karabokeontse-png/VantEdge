package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ConfidenceEngineUnknownTest {

    @Test
    fun `missing llm confidence is not fabricated as a fallback`() {
        val result = ConfidenceEngine.compute(
            llmConfidenceMap = emptyMap(),
            semanticAdjustments = mapOf("title" to 0.1f),
            schemaFailures = emptyList(),
            source = ExtractionMethod.LLM
        )

        val field = result.fieldConfidenceMap.getValue("title")
        assertNull(field.llmConfidence)

        assertNull(field.overallConfidence)
        assertNull(result.overallConfidence)
    }

    @Test
    fun `missing confidence is not diluted by measured confidence`() {
        val result = ConfidenceEngine.compute(
            llmConfidenceMap = mapOf("title" to 0.8f),
            semanticAdjustments = mapOf("company" to -0.1f),
            schemaFailures = emptyList(),
            source = ExtractionMethod.LLM
        )

        assertNull(result.fieldConfidenceMap.getValue("company").overallConfidence)
        assertEquals(0.9f, result.overallConfidence!!, 1e-6f)
    }

    @Test
    fun `present llm confidence is preserved unchanged`() {
        val result = ConfidenceEngine.compute(
            llmConfidenceMap = mapOf("title" to 0.9f),
            semanticAdjustments = emptyMap(),
            schemaFailures = emptyList(),
            source = ExtractionMethod.LLM
        )

        val field = result.fieldConfidenceMap.getValue("title")
        assertEquals(0.9f, field.llmConfidence!!, 1e-6f)
    }
}
