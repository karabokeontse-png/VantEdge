package com.vantedge.pipeline.contract

import com.fasterxml.jackson.databind.JsonNode

/**
 * Contract Enforcement Layer.
 *
 * Three-tier validation model:
 *
 *   Tier 1 — Structural
 *     Type correctness, field presence, nullability, truncation detection,
 *     JSON structural integrity (root object, valid parse).
 *
 *   Tier 2 — Schema-boundary
 *     Value ranges, format constraints, enum membership, array bounds.
 *     Defined per job type via [SchemaBoundaryRule].
 *
 *   Tier 3 — Domain (NOT HERE)
 *     Semantic correctness, business logic, scoring algorithm validity.
 *     Owned by downstream scoring components.
 *
 * This class owns Tier 1 and Tier 2 only.
 *
 * Hard gate between extraction and scoring.
 * Deterministic — no AI-based judgment, no exceptions for control flow.
 * Binary confidence: 1.0 = success, 0.0 = failure.
 * Job-type schemas are hardcoded for P1 stability.
 * No coupling to scoring, UI, retry, or model selection logic.
 */
class ContractValidator {

    fun validate(jobType: JobType, payload: ExtractedAiPayload): ContractValidationResult {
        // 1. Truncation detection
        if (isTruncated(payload.rawJson, payload.parsedObject)) {
            return failure(
                reason = ContractFailureReason.TruncatedJson,
                details = "JSON structure is incomplete: unclosed braces or brackets detected",
                payload = payload
            )
        }

        // 2. Empty content check
        if (payload.parsedObject.isNull || payload.parsedObject.isMissingNode) {
            return failure(
                reason = ContractFailureReason.EmptyContent,
                details = "Parsed JSON is null or missing",
                payload = payload
            )
        }

        // 3. Root must be an object
        if (!payload.parsedObject.isObject) {
            return failure(
                reason = ContractFailureReason.InvalidJsonStructure,
                details = "Root JSON element must be an object",
                payload = payload
            )
        }

        // 4. Schema resolution
        val schema = resolveSchema(jobType)
        if (schema == null) {
            return failure(
                reason = ContractFailureReason.UnsupportedSchemaVersion,
                details = "No schema registered for job type: $jobType",
                payload = payload
            )
        }
        return schema(payload)
    }

    // -------------------------------------------------------------------------
    // Truncation detection
    // -------------------------------------------------------------------------

    private fun isTruncated(rawJson: String, parsedObject: JsonNode): Boolean {
        // Priority 1: parser evidence
        if (!parsedObject.isNull && !parsedObject.isMissingNode) {
            return false
        }

        // Priority 2: parser failure information
        // If parser already rejected the input, classify as truncation
        // only when the raw text shows clear structural imbalance.

        // Priority 3: structural heuristic fallback
        val trimmed = rawJson.trim()
        if (trimmed.isEmpty()) return false

        var braceDepth = 0
        var bracketDepth = 0
        var inString = false
        var escapeNext = false

        for (char in trimmed) {
            if (escapeNext) {
                escapeNext = false
                continue
            }
            if (char == '\\') {
                escapeNext = true
                continue
            }
            if (char == '"') {
                inString = !inString
                continue
            }
            if (inString) continue

            when (char) {
                '{' -> braceDepth++
                '}' -> braceDepth--
                '[' -> bracketDepth++
                ']' -> bracketDepth--
            }
        }

        return braceDepth != 0 || bracketDepth != 0
    }

    // -------------------------------------------------------------------------
    // Tier 2 — Schema-boundary rules
    // -------------------------------------------------------------------------

    /**
     * A single Tier 2 constraint. Implementations define the check logic and
     * return a human-readable description on violation or null on pass.
     */
    private sealed class SchemaBoundaryRule {
        abstract val field: String
        abstract fun validate(node: JsonNode): String?

        data class IntRange(
            override val field: String,
            val min: Int,
            val max: Int
        ) : SchemaBoundaryRule() {
            override fun validate(node: JsonNode): String? {
                val value = node.path(field).asInt(Int.MIN_VALUE)
                return if (value == Int.MIN_VALUE || value in min..max) null
                else "$field=$value outside range $min..$max"
            }
        }

        data class SubFieldNonNegative(
            override val field: String,
            val subFields: List<String>
        ) : SchemaBoundaryRule() {
            override fun validate(node: JsonNode): String? {
                val obj = node.get(field) ?: return null
                if (!obj.isObject) return null
                val violations = subFields.mapNotNull { sf ->
                    val v = obj.path(sf).asInt(0)
                    if (v < 0) "$sf=$v" else null
                }
                return if (violations.isEmpty()) null
                else "$field sub-fields must be non-negative: ${violations.joinToString()}"
            }
        }
    }

    private val vacancyScoringBoundaryRules = listOf<SchemaBoundaryRule>(
        SchemaBoundaryRule.IntRange("score", 0, 100),
        SchemaBoundaryRule.IntRange("vacancyScore", 0, 100),
        SchemaBoundaryRule.SubFieldNonNegative(
            "profileStats",
            listOf("yearsExperience", "certificationCount", "skillCount", "matchedCount", "gapCount")
        )
    )

    // -------------------------------------------------------------------------
    // Schema resolver
    // -------------------------------------------------------------------------

    private val validators: Map<JobType, (ExtractedAiPayload) -> ContractValidationResult> = mapOf(
        JobType.VACANCY_SCORING to ::validateVacancyScoring,
        JobType.PROFILE_ANALYSIS to ::validateProfileAnalysis,
        JobType.COVER_LETTER_GENERATION to ::validateCoverLetterGeneration
    )

    private fun resolveSchema(jobType: JobType): ((ExtractedAiPayload) -> ContractValidationResult)? {
        return validators[jobType]
    }

    // -------------------------------------------------------------------------
    // Job-type validators
    // -------------------------------------------------------------------------

    private fun validateVacancyScoring(payload: ExtractedAiPayload): ContractValidationResult {
        val node = payload.parsedObject

        // ---------------------------------------------------------------------
        // Phase A — Tier 1: Structural checks
        //   Field presence, nullability, type correctness
        // ---------------------------------------------------------------------

        val required = linkedMapOf<String, (JsonNode) -> Boolean>(
            "score" to { it.isInt },
            "vacancyScore" to { it.isInt },
            "roleSummary" to { it.isTextual && it.asText().isNotBlank() },
            "eligibilitySummary" to { it.isTextual && it.asText().isNotBlank() },
            "profileStats" to { it.isObject },
            "relevancyItems" to { it.isArray }
        )

        val missing = required.keys.filter { name ->
            !node.has(name) || node.get(name).isNull
        }

        if (missing.isNotEmpty()) {
            return failure(
                reason = ContractFailureReason.MissingRequiredFields,
                details = "Missing required fields: ${missing.joinToString()}",
                payload = payload
            )
        }

        val typeErrors = required.mapNotNull { (name, check) ->
            if (!check(node.get(name))) name else null
        }

        if (typeErrors.isNotEmpty()) {
            return failure(
                reason = ContractFailureReason.TypeMismatch,
                details = "Type mismatch in fields: ${typeErrors.joinToString()}",
                payload = payload
            )
        }

        // ---------------------------------------------------------------------
        // Phase B — Tier 2: Schema-boundary checks
        //   Value ranges, format constraints via SchemaBoundaryRule
        // ---------------------------------------------------------------------

        val boundaryViolations = vacancyScoringBoundaryRules.mapNotNull { rule ->
            rule.validate(node)?.let { "${rule.field}: $it" }
        }

        if (boundaryViolations.isNotEmpty()) {
            return failure(
                reason = ContractFailureReason.TypeMismatch,
                details = "Schema-boundary violations: ${boundaryViolations.joinToString("; ")}",
                payload = payload
            )
        }

        return ContractValidationResult.Success(
            validatedObject = ValidatedAiPayload.create(node, JobType.VACANCY_SCORING)
        )
    }

    private fun validateProfileAnalysis(payload: ExtractedAiPayload): ContractValidationResult {
        // P1: Schema not yet defined. Pass through structural checks only.
        return ContractValidationResult.Success(
            validatedObject = ValidatedAiPayload.create(payload.parsedObject, JobType.PROFILE_ANALYSIS)
        )
    }

    private fun validateCoverLetterGeneration(payload: ExtractedAiPayload): ContractValidationResult {
        // P1: Schema not yet defined. Pass through structural checks only.
        return ContractValidationResult.Success(
            validatedObject = ValidatedAiPayload.create(payload.parsedObject, JobType.COVER_LETTER_GENERATION)
        )
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private fun failure(
        reason: ContractFailureReason,
        details: String,
        payload: ExtractedAiPayload
    ): ContractValidationResult.Failure {
        return ContractValidationResult.Failure(
            reason = reason,
            details = details,
            correlationId = payload.metadata.correlationId,
            rawSnapshot = payload.rawJson
        )
    }
}
