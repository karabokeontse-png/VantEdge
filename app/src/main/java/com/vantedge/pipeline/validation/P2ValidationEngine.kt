package com.vantedge.pipeline.validation

import android.content.Context
import com.fasterxml.jackson.databind.JsonNode
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.StructuredProfileExtraction
import com.vantedge.app.domain.PipelineTrace
import org.json.JSONArray
import org.json.JSONObject

data class RuleConfig(
    val name: String,
    val field: String,
    val type: String,
    val params: JSONObject?,
    val severity: String,
    val failureCode: String = ""
)

data class ValidationResult<T>(
    val decision: ValidationDecision,
    val validated: T
)

object P2ValidationEngine {
    private var initialized = false
    private var skillTaxonomy: Set<String> = emptySet()
    private var certificationTaxonomy: Set<String> = emptySet()
    private var providerWhitelistDomains: Set<String> = emptySet()
    private var providerWhitelistProviders: Set<String> = emptySet()
    private var hallucinationMarkers: List<String> = emptyList()
    private var invalidHeaders: List<String> = emptyList()
    private var compatibilityRules: List<RuleConfig> = emptyList()
    private var jobExtractionRules: List<RuleConfig> = emptyList()
    private var generatorRules: List<RuleConfig> = emptyList()

    fun initialize(context: Context) {
        if (initialized) return
        val am = context.assets

        val skillJson = JSONObject(am.open("validation/skill_taxonomy.json").bufferedReader().readText())
        val certJson = JSONObject(am.open("validation/certification_taxonomy.json").bufferedReader().readText())
        val providerJson = JSONObject(am.open("validation/provider_whitelist.json").bufferedReader().readText())
        val hallLines = am.open("validation/hallucination_markers.txt").bufferedReader().readLines()
        val headerLines = am.open("validation/invalid_headers.txt").bufferedReader().readLines()
        val rulesJson = JSONObject(am.open("validation/validation_rules.json").bufferedReader().readText())

        val skills = mutableSetOf<String>()
        val cats = skillJson.getJSONArray("categories")
        for (i in 0 until cats.length()) {
            val arr = cats.getJSONObject(i).getJSONArray("skills")
            for (j in 0 until arr.length()) skills.add(arr.getString(j).lowercase())
        }
        skillTaxonomy = skills

        val certs = mutableSetOf<String>()
        val certArr = certJson.getJSONArray("certifications")
        for (i in 0 until certArr.length()) certs.add(certArr.getString(i).lowercase())
        certificationTaxonomy = certs

        val domains = mutableSetOf<String>()
        val dArr = providerJson.getJSONArray("domains")
        for (i in 0 until dArr.length()) domains.add(dArr.getString(i).lowercase())
        providerWhitelistDomains = domains

        val provs = mutableSetOf<String>()
        val pArr = providerJson.getJSONArray("providers")
        for (i in 0 until pArr.length()) provs.add(pArr.getString(i).lowercase())
        providerWhitelistProviders = provs

        hallucinationMarkers = hallLines.map { it.trim().lowercase() }.filter { it.isNotBlank() }
        invalidHeaders = headerLines.map { it.trim().lowercase() }.filter { it.isNotBlank() }

        compatibilityRules = parseRules(rulesJson.getJSONObject("compatibility").getJSONArray("rules"))
        jobExtractionRules = parseRules(rulesJson.getJSONObject("job_extraction").getJSONArray("rules"))
        generatorRules = parseRules(rulesJson.getJSONObject("generator").getJSONArray("rules"))

        initialized = true
    }

    private fun parseRules(arr: JSONArray): List<RuleConfig> {
        val rules = mutableListOf<RuleConfig>()
        for (i in 0 until arr.length()) {
            val r = arr.getJSONObject(i)
            rules.add(RuleConfig(
                name = r.getString("name"),
                field = r.getString("field"),
                type = r.getString("type"),
                params = r.optJSONObject("params"),
                severity = r.getString("severity"),
                failureCode = r.optString("failure_code")
            ))
        }
        return rules
    }

    private fun buildTrace(stage: String, correlationId: String): ValidationTrace {
        return ValidationTrace(stage, correlationId, System.currentTimeMillis())
    }

    private fun computeDecision(trace: ValidationTrace): ValidationDecision {
        val blockerFails = trace.rules.filter { it.severity == "BLOCKER" && !it.passed }
        val warnings = trace.rules.filter { !it.passed && it.severity != "BLOCKER" }.map { it.ruleName }
        return when {
            blockerFails.isNotEmpty() -> ValidationDecision.Reject(
                blockerFails.first().detail ?: "BLOCKER: ${blockerFails.first().ruleName}",
                trace
            )
            warnings.isNotEmpty() -> ValidationDecision.Degraded(trace, warnings)
            else -> ValidationDecision.Accept(trace)
        }
    }

    fun validateContractResult(node: JsonNode, correlationId: String): ValidationResult<JsonNode> {
        val inputHash = node.toString().hashCode()
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_START", mapOf(
            "correlationId" to correlationId,
            "validationType" to "ContractResult",
            "inputHash" to inputHash.toString()
        ), correlationId)
        val trace = buildTrace("ContractResult", correlationId)
        for (rule in compatibilityRules) {
            trace.addResult(runRule(node, rule))
        }
        val decision = computeDecision(trace)
        val failureCodes = when (decision) {
            is ValidationDecision.Reject -> listOf(decision.reason)
            is ValidationDecision.Degraded -> decision.warnings
            else -> emptyList()
        }
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_END", mapOf(
            "correlationId" to correlationId,
            "validationType" to "ContractResult",
            "decision" to decision.javaClass.simpleName,
            "traceHash" to trace.hashCode().toString(),
            "failureCodes" to failureCodes.toString()
        ), correlationId)
        return ValidationResult(decision, node)
    }

    fun validateJobExtractionResult(result: JobExtractionResult, correlationId: String): ValidationResult<JobExtractionResult> {
        val inputHash = result.hashCode()
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_START", mapOf(
            "correlationId" to correlationId,
            "validationType" to "JobExtractionResult",
            "inputHash" to inputHash.toString()
        ), correlationId)
        val trace = buildTrace("JobExtractionResult", correlationId)

        val desc = result.description ?: ""
        val charCount = desc.length
        val wordCount = desc.split("\\s+".toRegex()).count { it.isNotBlank() }
        val hasSep = desc.contains(".") || desc.contains("!") || desc.contains("?")
        val hasAlpha = desc.any { it.isLetter() }
        val descPassed = charCount >= 100 && wordCount >= 20 && hasSep && hasAlpha
        trace.addResult(RuleResult("description_min_length", "description", descPassed, "BLOCKER",
            detail = if (!descPassed) "chars=$charCount words=$wordCount sep=$hasSep alpha=$hasAlpha" else null))

        val titlePassed = !result.jobTitle.isNullOrBlank()
        trace.addResult(RuleResult("title_non_empty", "jobTitle", titlePassed, "WARNING"))

        val companyPassed = !result.company.isNullOrBlank()
        trace.addResult(RuleResult("company_non_empty", "company", companyPassed, "WARNING"))

        val lowerDesc = desc.lowercase()
        val hallFound = hallucinationMarkers.filter { lowerDesc.contains(it) }
        if (hallFound.isNotEmpty()) {
            trace.addResult(RuleResult("hallucination_check", "description", false, "WARNING",
                detail = hallFound.joinToString(", ")))
        }

        val decision = computeDecision(trace)
        val failureCodes = when (decision) {
            is ValidationDecision.Reject -> listOf(decision.reason)
            is ValidationDecision.Degraded -> decision.warnings
            else -> emptyList()
        }
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_END", mapOf(
            "correlationId" to correlationId,
            "validationType" to "JobExtractionResult",
            "decision" to decision.javaClass.simpleName,
            "traceHash" to trace.hashCode().toString(),
            "failureCodes" to failureCodes.toString()
        ), correlationId)
        return ValidationResult(decision, result)
    }

    fun validateProfileExtraction(
        extraction: StructuredProfileExtraction,
        correlationId: String
    ): ValidationResult<StructuredProfileExtraction> {
        val inputHash = extraction.hashCode()
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_START", mapOf(
            "correlationId" to correlationId,
            "validationType" to "ProfileExtraction",
            "inputHash" to inputHash.toString()
        ), correlationId)
        val trace = buildTrace("ProfileExtraction", correlationId)

        val skillWords = extraction.skills.map { it.value.lowercase() }
        for (s in skillWords) {
            if (s.isBlank()) continue
            val known = skillTaxonomy.any { s.contains(it) || it.contains(s) }
            if (!known) {
                trace.addResult(RuleResult("skill_taxonomy_check", "skills", false, "WARNING",
                    detail = "UNKNOWN_TAXONOMY_ENTRY: $s"))
            }
        }

        val certWords = extraction.certifications.map { it.name.value.lowercase() }
        for (c in certWords) {
            if (c.isBlank()) continue
            val known = certificationTaxonomy.contains(c) ||
                certificationTaxonomy.any { c.contains(it) || it.contains(c) }
            if (!known) {
                trace.addResult(RuleResult("certification_taxonomy_check", "certifications", false, "WARNING",
                    detail = "UNKNOWN_TAXONOMY_ENTRY: $c"))
            }
        }

        val summaryText = extraction.summary?.value?.lowercase() ?: ""
        val hallFound = hallucinationMarkers.filter { summaryText.contains(it) }
        if (hallFound.isNotEmpty()) {
            trace.addResult(RuleResult("hallucination_check", "summary", false, "WARNING",
                detail = hallFound.joinToString(", ")))
        }

        val decision = computeDecision(trace)
        val failureCodes = when (decision) {
            is ValidationDecision.Reject -> listOf(decision.reason)
            is ValidationDecision.Degraded -> decision.warnings
            else -> emptyList()
        }
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_END", mapOf(
            "correlationId" to correlationId,
            "validationType" to "ProfileExtraction",
            "decision" to decision.javaClass.simpleName,
            "traceHash" to trace.hashCode().toString(),
            "failureCodes" to failureCodes.toString()
        ), correlationId)
        return ValidationResult(decision, extraction)
    }

    fun validateGeneratorOutput(jsonString: String, correlationId: String): ValidationResult<String> {
        val inputHash = jsonString.hashCode()
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_START", mapOf(
            "correlationId" to correlationId,
            "validationType" to "GeneratorOutput",
            "inputHash" to inputHash.toString()
        ), correlationId)
        val trace = buildTrace("GeneratorOutput", correlationId)

        try {
            val json = JSONObject(jsonString)
            val kwArr = json.optJSONArray("matchedKeywords")
            val kwPassed = kwArr != null && kwArr.length() > 0
            trace.addResult(RuleResult("matched_keywords_present", "matchedKeywords", kwPassed, "WARNING"))

            val summary = json.optString("relevantSummary", "")
            trace.addResult(RuleResult("relevant_summary_required", "relevantSummary", summary.isNotBlank(), "WARNING"))

            val lower = jsonString.lowercase()
            val hallFound = hallucinationMarkers.filter { lower.contains(it) }
            if (hallFound.isNotEmpty()) {
                trace.addResult(RuleResult("hallucination_check", "content", false, "WARNING",
                    detail = hallFound.joinToString(", ")))
            }
        } catch (e: Exception) {
            trace.addResult(RuleResult("json_parse", "root", false, "BLOCKER", detail = e.message))
        }

        val decision = computeDecision(trace)
        val failureCodes = when (decision) {
            is ValidationDecision.Reject -> listOf(decision.reason)
            is ValidationDecision.Degraded -> decision.warnings
            else -> emptyList()
        }
        PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_END", mapOf(
            "correlationId" to correlationId,
            "validationType" to "GeneratorOutput",
            "decision" to decision.javaClass.simpleName,
            "traceHash" to trace.hashCode().toString(),
            "failureCodes" to failureCodes.toString()
        ), correlationId)
        return ValidationResult(decision, jsonString)
    }

    private fun runRule(node: JsonNode, rule: RuleConfig): RuleResult {
        return when (rule.type) {
            "int_range" -> checkIntRange(node, rule)
            "non_blank_string" -> checkNonBlankString(node, rule)
            "non_empty_array" -> checkNonEmptyArray(node, rule)
            "min_length" -> checkMinLength(node, rule)
            else -> RuleResult(rule.name, rule.field, true, rule.severity)
        }
    }

    private fun checkIntRange(node: JsonNode, rule: RuleConfig): RuleResult {
        val min = rule.params?.optInt("min", 0) ?: 0
        val max = rule.params?.optInt("max", 100) ?: 100
        val value = resolveIntField(node, rule.field)
        val passed = value in min..max
        return RuleResult(rule.name, rule.field, passed, rule.severity,
            detail = if (!passed) "value=$value range=[$min,$max]" else null)
    }

    private fun checkNonBlankString(node: JsonNode, rule: RuleConfig): RuleResult {
        val value = node.path(rule.field).asText("")
        return RuleResult(rule.name, rule.field, value.isNotBlank(), rule.severity)
    }

    private fun checkNonEmptyArray(node: JsonNode, rule: RuleConfig): RuleResult {
        val arr = node.get(rule.field)
        val passed = arr != null && arr.isArray && arr.size() > 0
        return RuleResult(rule.name, rule.field, passed, rule.severity)
    }

    private fun checkMinLength(node: JsonNode, rule: RuleConfig): RuleResult {
        val text = node.path(rule.field).asText("")
        val minChars = rule.params?.optInt("min_chars", 100) ?: 100
        val minWords = rule.params?.optInt("min_words", 20) ?: 20
        val charOk = text.length >= minChars
        val wordOk = text.split("\\s+".toRegex()).count { it.isNotBlank() } >= minWords
        return RuleResult(rule.name, rule.field, charOk && wordOk, rule.severity,
            detail = if (!charOk || !wordOk) "chars=${text.length} words=${text.split("\\s+".toRegex()).count { it.isNotBlank() }}" else null)
    }

    private fun resolveIntField(node: JsonNode, field: String): Int {
        if (field.contains("[].")) {
            val parts = field.split("[].")
            val arr = node.get(parts[0])
            if (arr != null && arr.isArray) {
                var min = Int.MAX_VALUE
                for (item in arr) {
                    val v = item.path(parts[1]).asInt(Int.MAX_VALUE)
                    if (v < min) min = v
                }
                return min
            }
            return Int.MAX_VALUE
        }
        return node.path(field).asInt(Int.MAX_VALUE)
    }
}
