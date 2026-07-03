package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.data.model.extraction.FieldConfidence
import com.vantedge.app.domain.PipelineTrace

data class SemanticValidationResult(
    val passed: Boolean,
    val fieldConfidenceAdjustments: Map<String, Float>,
    val warnings: List<String>
)

object SemanticValidator {
    private val HEADER_BLACKLIST = listOf(
        "vacancy", "vacancy announcement", "vacancy notice",
        "job vacancy", "position available"
    )

    private val SECTION_HEADER_BLACKLIST = listOf(
        "main purpose of the job", "competencies/skills",
        "qualifications/requirements", "key performance areas",
        "duties and responsibilities", "how to apply",
        "terms of service", "remuneration", "benefits"
    )

    private val KNOWN_INVALID_TOKENS = listOf(
        "i assume", "likely", "probably", "presumably",
        "may have", "could be", "i think", "it seems",
        "based on my knowledge"
    )

    fun validate(
        jobTitle: String?,
        company: String?,
        description: String?,
        source: ExtractionMethod = ExtractionMethod.LLM,
        correlationId: String? = null
    ): SemanticValidationResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("SemanticValidator", mapOf(
            "jobTitle" to (jobTitle ?: ""),
            "company" to (company ?: ""),
            "descriptionLen" to (description?.length ?: 0),
            "method" to source,
            "correlationId" to correlationId
        ))

        val adjustments = mutableMapOf<String, Float>()
        val warnings = mutableListOf<String>()

        if (jobTitle != null) {
            val matchedHeader = HEADER_BLACKLIST.any { jobTitle.lowercase().trim() == it }
            val startsWithHeader = HEADER_BLACKLIST.any { jobTitle.lowercase().trim().startsWith(it) }
            if (matchedHeader || startsWithHeader) {
                adjustments["jobTitle"] = -0.4f
                warnings.add("jobTitle matches header blacklist: '$jobTitle'")
            }
        }

        if (company != null) {
            val isSectionHeader = SECTION_HEADER_BLACKLIST.any { company.lowercase().trim().startsWith(it) }
            val containsSectionHeader = SECTION_HEADER_BLACKLIST.any { company.lowercase().contains(it) }
            if (isSectionHeader || containsSectionHeader) {
                adjustments["company"] = -0.5f
                warnings.add("company matches section header: '$company'")
            }
        }

        if (description != null) {
            if (description.length < 50) {
                adjustments["description"] = -0.2f
                warnings.add("description too short: ${description.length} chars")
            }

            if (company != null && description.lowercase().contains(company.lowercase())) {
                adjustments["company"] = (adjustments["company"] ?: 0f) + 0.1f
            }
        }

        listOfNotNull(
            "jobTitle" to jobTitle,
            "company" to company,
            "description" to description
        ).forEach { (field, value) ->
            val matched = KNOWN_INVALID_TOKENS.any { value?.lowercase()?.contains(it) == true }
            if (matched) {
                adjustments[field] = (adjustments[field] ?: 0f) - 0.3f
                warnings.add("$field contains hallucination marker")
            }
        }

        if (company != null && jobTitle != null) {
            if (company.lowercase() == jobTitle.lowercase()) {
                adjustments["company"] = (adjustments["company"] ?: 0f) - 0.3f
                adjustments["jobTitle"] = (adjustments["jobTitle"] ?: 0f) - 0.2f
                warnings.add("company equals jobTitle — possible misattribution")
            }
        }

        val passed = warnings.isEmpty()
        val durationMs = System.currentTimeMillis() - startMs
        val adjDetails = if (adjustments.isNotEmpty()) adjustments.entries.joinToString(", ") { "${it.key}=${"%.2f".format(it.value)}" } else "none"
        val warnDetails = if (warnings.isNotEmpty()) warnings.joinToString("; ") else "none"
        PipelineTrace.exit("SemanticValidator", durationMs, mapOf(
            "passed" to passed,
            "warnings" to warnings.size,
            "method" to source,
            "adjustments" to "{$adjDetails}",
            "warningDetails" to "[$warnDetails]",
            "correlationId" to correlationId
        ))

        return SemanticValidationResult(passed, adjustments, warnings)
    }
}
