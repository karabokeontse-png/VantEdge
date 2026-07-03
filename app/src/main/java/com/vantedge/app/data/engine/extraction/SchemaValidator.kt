package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.data.model.extraction.FieldConfidence
import org.json.JSONArray
import org.json.JSONObject

data class SchemaValidationResult(
    val passed: Boolean,
    val failures: List<SchemaFailure>
)

data class SchemaFailure(
    val field: String,
    val reason: String
)

object SchemaValidator {
    fun validate(rawJson: String, source: ExtractionMethod, correlationId: String? = null): SchemaValidationResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("SchemaValidator", mapOf(
            "jsonLength" to rawJson.length,
            "method" to source,
            "correlationId" to correlationId
        ))

        val failures = mutableListOf<SchemaFailure>()

        val json = try {
            JSONObject(rawJson)
        } catch (e: Exception) {
            failures.add(SchemaFailure("_root", "Invalid JSON: ${e.message}"))
            return SchemaValidationResult(false, failures)
        }

        val requiredStringFields = listOf(
            "jobTitle", "company", "location", "employmentType",
            "description", "educationRequired", "experienceRequired",
            "salaryRange", "closingDate", "contactInfo"
        )
        for (field in requiredStringFields) {
            if (!json.has(field)) {
                failures.add(SchemaFailure(field, "Missing required field"))
                continue
            }
            val value = json.opt(field)
            if (value != null && value !is String) {
                failures.add(SchemaFailure(field, "Expected string, got ${value.javaClass.simpleName}"))
            }
        }

        val requiredArrayFields = listOf("responsibilities", "requiredSkills", "preferredSkills")
        for (field in requiredArrayFields) {
            if (!json.has(field)) {
                failures.add(SchemaFailure(field, "Missing required field"))
                continue
            }
            val value = json.opt(field)
            if (value != null && value !is JSONArray) {
                failures.add(SchemaFailure(field, "Expected array, got ${value.javaClass.simpleName}"))
            }
        }

        val passed = failures.isEmpty()
        val durationMs = System.currentTimeMillis() - startMs
        val failureDetails = if (failures.isNotEmpty()) failures.joinToString("; ") { "${it.field}: ${it.reason}" } else "none"
        PipelineTrace.exit("SchemaValidator", durationMs, mapOf(
            "passed" to passed,
            "failures" to failures.size,
            "method" to source,
            "failureDetails" to failureDetails,
            "correlationId" to correlationId
        ))
        return SchemaValidationResult(passed, failures)
    }
}
