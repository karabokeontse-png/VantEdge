package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.model.extraction.DocumentBlock
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.data.model.extraction.ExtractionMethod
import com.vantedge.app.data.model.extraction.StructuredDocument
import com.vantedge.app.data.engine.JsonRepairUtil
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.AiRequest
import org.json.JSONObject

data class LlmExtractionResult(
    val rawJson: String,
    val fieldConfidenceMap: Map<String, Float>
)

class LlmJobExtractor(
    private val aiGateway: AiGateway
) {
    private companion object {
        val AI_SYSTEM_PROMPT = """
You are a precise job posting extraction engine. Return ONLY valid JSON — no markdown, no explanations, no code fences.

Schema (extract ONLY verbatim — never invent, infer, or expand):
{
  "jobTitle": "Position title exactly as stated",
  "company": "Institution/company name exactly as stated",
  "location": "Location exactly as stated if present",
  "employmentType": "Employment type exactly as stated (full-time, contract, etc.)",
  "description": "Complete role description — concatenate all sections",
  "responsibilities": ["Duties and responsibilities exactly as listed"],
  "requiredSkills": ["Skills explicitly listed as required — never infer"],
  "preferredSkills": ["Skills explicitly listed as preferred/nice-to-have"],
  "educationRequired": "Education/qualification requirements exactly as stated",
  "experienceRequired": "Experience requirements exactly as stated",
  "salaryRange": "Salary exactly as stated if present",
  "closingDate": "Closing date exactly as stated if present",
  "contactInfo": "Contact information exactly as stated if present"
}

STRICT RULES:
- Extract ONLY values explicitly stated in the posting text
- NEVER invent, infer, or expand abbreviations or generic terms
- If the posting says "familiar with cloud platforms" record ONLY "cloud platforms" — do NOT list AWS, GCP, Azure
- NEVER inflate experience requirements: "3+ years" stays "3+ years", not "5+ years"
- NEVER guess company info (size, funding, industry, culture) not stated
- NEVER add phrases like "I assume", "likely", "probably", "presumably"
- Use "" for missing strings, [] for missing arrays
- Never omit any key
        """.trimIndent()
    }

    data class LlmFieldResult(
        val jobTitle: String?,
        val company: String?,
        val location: String?,
        val employmentType: String?,
        val description: String?,
        val responsibilities: List<String>,
        val requiredSkills: List<String>,
        val preferredSkills: List<String>,
        val educationRequired: String?,
        val experienceRequired: String?,
        val salaryRange: String?,
        val closingDate: String?,
        val contactInfo: String?,
        val fieldConfidence: Map<String, Float>,
        val rawJson: String
    )

    suspend fun extract(document: StructuredDocument, onProgress: (String) -> Unit = {}, correlationId: String? = null): LlmFieldResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("LlmJobExtractor", mapOf(
            "blocks" to document.blocks.size,
            "correlationId" to correlationId
        ))

        onProgress("Analyzing document structure...")

        val promptBlocks = buildBlockContext(document.blocks)
        val candidateFacts = buildCandidateFacts(document.blocks)
        val userPrompt = buildUserPrompt(document, promptBlocks, candidateFacts)

        onProgress("Extracting job details...")

        val request = AiRequest(
            systemPrompt = AI_SYSTEM_PROMPT,
            userPrompt = userPrompt,
            temperature = 0.0,
            maxTokens = 4096
        )

        val response = aiGateway.generate("job_extraction", request, 120_000L)

        if (response.isNullOrBlank()) {
            throw Exception("EMPTY_AI_RESPONSE")
        }

        val extractionResult = JsonExtractionEngine.extract(response)
        if (!extractionResult.success) {
            throw Exception("EXTRACTION_FAILED: ${extractionResult.failureReason}")
        }

        PipelineTrace.dataQuality(
            stage = "LlmJobExtractor",
            issue = "EXTRACTION",
            details = mapOf(
                "strategy" to extractionResult.strategy,
                "success" to extractionResult.success,
                "correlationId" to correlationId
            )
        )

        val jsonObj = parseWithRepairFallback(extractionResult.content, correlationId)

        val schemaResult = SchemaValidator.validate(jsonObj.toString(), ExtractionMethod.LLM, correlationId)
        if (!schemaResult.passed) {
            val failureDetail = schemaResult.failures.joinToString(";") { "${it.field}: ${it.reason}" }
            PipelineTrace.dataQuality(
                stage = "LlmJobExtractor",
                issue = "SCHEMA_VIOLATION",
                details = mapOf(
                    "failures" to failureDetail,
                    "correlationId" to correlationId
                )
            )
            throw Exception("SCHEMA_VIOLATION: $failureDetail")
        }

        val result = parseResponse(jsonObj)

        val durationMs = System.currentTimeMillis() - startMs
        val fieldConf = result.fieldConfidence.entries.joinToString(", ") { "${it.key}=${"%.2f".format(it.value)}" }
        PipelineTrace.exit("LlmJobExtractor", durationMs, mapOf(
            "title" to (result.jobTitle ?: ""),
            "company" to (result.company ?: ""),
            "method" to "LLM",
            "fields" to "{$fieldConf}",
            "correlationId" to correlationId
        ))

        return result
    }

    private fun buildBlockContext(blocks: List<DocumentBlock>): String {
        return blocks.joinToString("\n\n") { block ->
            when (block.type) {
                com.vantedge.app.data.model.extraction.BlockType.HEADING -> "[HEADING] ${block.text}"
                com.vantedge.app.data.model.extraction.BlockType.LIST -> "[LIST] ${block.text}"
                com.vantedge.app.data.model.extraction.BlockType.TABLE -> "[TABLE] ${block.text}"
                com.vantedge.app.data.model.extraction.BlockType.PARAGRAPH -> "[PARAGRAPH] ${block.text}"
                com.vantedge.app.data.model.extraction.BlockType.HEADER -> "[HEADER] ${block.text}"
                com.vantedge.app.data.model.extraction.BlockType.FOOTER -> "[FOOTER] ${block.text}"
                else -> "[TEXT] ${block.text}"
            }
        }
    }

    private fun buildCandidateFacts(blocks: List<DocumentBlock>): String {
        val facts = mutableListOf<String>()

        for (block in blocks) {
            val meta = block.metadata
            if (meta.detectedEmails.isNotEmpty()) {
                facts.add("Detected email(s): ${meta.detectedEmails.joinToString(", ")}")
            }
            if (meta.detectedPhoneNumbers.isNotEmpty()) {
                facts.add("Detected phone(s): ${meta.detectedPhoneNumbers.joinToString(", ")}")
            }
            if (meta.detectedDates.isNotEmpty()) {
                facts.add("Detected date(s): ${meta.detectedDates.joinToString(", ")}")
            }
            if (meta.detectedCurrencyValues.isNotEmpty()) {
                facts.add("Detected salary/currency: ${meta.detectedCurrencyValues.joinToString(", ")}")
            }
        }

        return if (facts.isNotEmpty()) facts.joinToString("\n") else ""
    }

    private fun buildUserPrompt(
        document: StructuredDocument,
        blockContext: String,
        candidateFacts: String
    ): String {
        val sb = StringBuilder()
        sb.appendLine("Extract job posting information from the following structured document.")
        sb.appendLine()
        sb.appendLine("Document type: ${document.documentType.name}")
        sb.appendLine("Pages: ${document.pages.size}")
        sb.appendLine("Content blocks: ${document.blocks.size}")
        sb.appendLine()

        if (candidateFacts.isNotEmpty()) {
            sb.appendLine("Candidate facts detected in document (use as supporting evidence only):")
            sb.appendLine(candidateFacts)
            sb.appendLine()
        }

        sb.appendLine("Structured document content:")
        sb.appendLine(blockContext)

        return sb.toString()
    }

    private fun parseWithRepairFallback(jsonStr: String, correlationId: String? = null): JSONObject {
        return try {
            JSONObject(jsonStr).also {
                PipelineTrace.dataQuality(
                    stage = "LlmJobExtractor",
                    issue = "PARSE_DIRECT",
                    details = mapOf("outcome" to "SUCCESS", "correlationId" to correlationId)
                )
            }
        } catch (e: Exception) {
            PipelineTrace.dataQuality(
                stage = "LlmJobExtractor",
                issue = "PARSE_DIRECT",
                details = mapOf("outcome" to "FAILED", "error" to (e.message ?: ""), "correlationId" to correlationId)
            )

            val repairResult = JsonRepairUtil.repair(jsonStr)
            if (!repairResult.isSafe) {
                PipelineTrace.dataQuality(
                    stage = "LlmJobExtractor",
                    issue = "JSON_REPAIR",
                    details = mapOf("outcome" to "UNSAFE", "correlationId" to correlationId)
                )
                throw Exception("UNREPAIRABLE_JSON")
            }

            try {
                JSONObject(repairResult.json).also {
                    PipelineTrace.dataQuality(
                        stage = "LlmJobExtractor",
                        issue = "JSON_REPAIR",
                        details = mapOf("outcome" to "SUCCESS", "correlationId" to correlationId)
                    )
                }
            } catch (e2: Exception) {
                PipelineTrace.dataQuality(
                    stage = "LlmJobExtractor",
                    issue = "JSON_REPAIR",
                    details = mapOf("outcome" to "PARSE_FAILED", "correlationId" to correlationId)
                )
                throw Exception("PARSE_FAILED_AFTER_REPAIR")
            }
        }
    }

    private fun parseResponse(json: JSONObject): LlmFieldResult {
        val confidence = mutableMapOf<String, Float>()

        val jobTitle = json.optString("jobTitle", "").takeIf { it.isNotBlank() }
        confidence["jobTitle"] = if (jobTitle != null) 0.9f else 0.3f

        val company = json.optString("company", "").takeIf { it.isNotBlank() }
        confidence["company"] = if (company != null) 0.9f else 0.3f

        val location = json.optString("location", "").takeIf { it.isNotBlank() }
        val employmentType = json.optString("employmentType", "").takeIf { it.isNotBlank() }
        val description = json.optString("description", "").takeIf { it.isNotBlank() }
        confidence["description"] = if (description != null) 0.85f else 0.3f

        val responsibilities = json.optJSONArray("responsibilities")?.let { arr ->
            (0 until arr.length()).map { arr.optString(it) }.filter { it.isNotBlank() }
        } ?: emptyList()

        val requiredSkills = json.optJSONArray("requiredSkills")?.let { arr ->
            (0 until arr.length()).map { arr.optString(it) }.filter { it.isNotBlank() }
        } ?: emptyList()

        val preferredSkills = json.optJSONArray("preferredSkills")?.let { arr ->
            (0 until arr.length()).map { arr.optString(it) }.filter { it.isNotBlank() }
        } ?: emptyList()

        val educationRequired = json.optString("educationRequired", "").takeIf { it.isNotBlank() }
        val experienceRequired = json.optString("experienceRequired", "").takeIf { it.isNotBlank() }
        val salaryRange = json.optString("salaryRange", "").takeIf { it.isNotBlank() }
        val closingDate = json.optString("closingDate", "").takeIf { it.isNotBlank() }
        val contactInfo = json.optString("contactInfo", "").takeIf { it.isNotBlank() }

        return LlmFieldResult(
            jobTitle = jobTitle,
            company = company,
            location = location,
            employmentType = employmentType,
            description = description,
            responsibilities = responsibilities,
            requiredSkills = requiredSkills,
            preferredSkills = preferredSkills,
            educationRequired = educationRequired,
            experienceRequired = experienceRequired,
            salaryRange = salaryRange,
            closingDate = closingDate,
            contactInfo = contactInfo,
            fieldConfidence = confidence,
            rawJson = json.toString()
        )
    }
}
