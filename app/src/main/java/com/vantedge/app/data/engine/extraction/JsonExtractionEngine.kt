package com.vantedge.app.data.engine.extraction

import com.vantedge.app.domain.PipelineTrace
import org.json.JSONObject
import java.util.regex.Pattern

data class ExtractionResult(
    val content: String,
    val strategy: String,
    val success: Boolean,
    val failureReason: String?,
    val repairTrail: List<String> = emptyList()
)

object JsonExtractionEngine {

    private const val TAG = "JsonExtractionEngine"

    fun extract(raw: String): ExtractionResult {
        val cleaned = raw
            .replace(Regex("<think>[\\s\\S]*?</think>"), "")
            .replace(Regex("<reasoning>[\\s\\S]*?</reasoning>"), "")
            .replace(Regex("<thinking>[\\s\\S]*?</thinking>"), "")
        val normalized = cleaned.trim().replace("\r\n", "\n")

        PipelineTrace.dataQuality(
            stage = "json_extraction",
            issue = "EXTRACTION_START",
            details = mapOf(
                "rawLength" to raw.length,
                "normalizedLength" to normalized.length
            )
        )

        val result = extractInternal(normalized)

        PipelineTrace.dataQuality(
            stage = "json_extraction",
            issue = "EXTRACTION_RESULT",
            details = mapOf(
                "strategy" to result.strategy,
                "success" to result.success,
                "failureReason" to (result.failureReason ?: ""),
                "contentLength" to result.content.length,
                "repairTrail" to result.repairTrail.joinToString(";")
            )
        )

        return result
    }

    private fun extractInternal(normalized: String): ExtractionResult {
        val repairTrail = mutableListOf<String>()

        val directObj = tryDirectParse(normalized)
        if (directObj != null) {
            if (hasTrailingCorruption(normalized)) {
                repairTrail.add("direct_parse:STRUCTURAL_CORRUPTION")
                return ExtractionResult(
                    normalized,
                    "direct_parse_quarantined",
                    false,
                    "STRUCTURAL_CORRUPTION_AFTER_OBJECT_CLOSURE",
                    repairTrail
                )
            }
            return ExtractionResult(normalized, "direct_parse", true, null, repairTrail)
        }
        repairTrail.add("direct_parse:INVALID_JSON")

        val noFence = normalized
            .replace("```json", "")
            .replace("```", "")
            .replace(Pattern.compile("""\*\*(.+?)\*\*""").toRegex(), "$1")
            .replace(Pattern.compile("""\*(.+?)\*""").toRegex(), "$1")
            .trim()
        if (noFence != normalized) {
            val stripObj = tryDirectParse(noFence)
            if (stripObj != null) {
                if (hasTrailingCorruption(noFence)) {
                    repairTrail.add("markdown_strip:STRUCTURAL_CORRUPTION")
                    return ExtractionResult(
                        noFence,
                        "markdown_strip_quarantined",
                        false,
                        "STRUCTURAL_CORRUPTION_AFTER_OBJECT_CLOSURE",
                        repairTrail
                    )
                }
                repairTrail.add("markdown_strip:REPAIRED")
                return ExtractionResult(noFence, "markdown_strip", true, null, repairTrail)
            }
            repairTrail.add("markdown_strip:INVALID_JSON")
        }

        val rootObj = extractNestingAwareRoot(normalized)
        if (rootObj != null) {
            val rootParsed = tryDirectParse(rootObj)
            if (rootParsed != null) {
                val trailing = normalized.substringAfter(rootObj).trim()
                if (trailing.isNotEmpty()) {
                    repairTrail.add("nesting_aware_root:STRUCTURAL_CORRUPTION")
                    return ExtractionResult(
                        rootObj,
                        "nesting_aware_root_quarantined",
                        false,
                        "STRUCTURAL_CORRUPTION_AFTER_OBJECT_CLOSURE: trailing=${trailing.take(100)}",
                        repairTrail
                    )
                }
                repairTrail.add("nesting_aware_root:REPAIRED")
                return ExtractionResult(rootObj, "nesting_aware_root", true, null, repairTrail)
            }
            repairTrail.add("nesting_aware_root:EXTRACTED_BUT_INVALID_JSON")
        }

        val candidates = findAllNestingAwareCandidates(normalized)
            .filter { it.length > 50 }
            .sortedByDescending { it.length }
        for ((index, candidate) in candidates.withIndex()) {
            val candidateObj = tryDirectParse(candidate)
            if (candidateObj != null) {
                repairTrail.add("substring_scan:REPAIRED candidate=$index")
                return ExtractionResult(candidate, "substring_scan", true, null, repairTrail)
            }
            repairTrail.add("substring_scan:INVALID_JSON candidate=$index")
        }

        return ExtractionResult(normalized, "failed", false, "ALL_STRATEGIES_FAILED", repairTrail)
    }

    private fun tryDirectParse(input: String): JSONObject? {
        return try {
            JSONObject(input)
        } catch (_: Exception) {
            null
        }
    }

    private fun hasTrailingCorruption(input: String): Boolean {
        val root = extractNestingAwareRoot(input)
        if (root == null) return false
        val remaining = input.substringAfter(root).trim()
        return remaining.isNotEmpty()
    }

    private fun extractNestingAwareRoot(raw: String): String? {
        var inString = false
        var escaped = false
        var depth = 0
        var start = -1

        for (i in raw.indices) {
            val c = raw[i]

            if (inString) {
                if (escaped) {
                    escaped = false
                } else if (c == '\\') {
                    escaped = true
                } else if (c == '"') {
                    inString = false
                }
                continue
            }

            when (c) {
                '"' -> inString = true
                '{' -> {
                    if (depth == 0) start = i
                    depth++
                }
                '}' -> {
                    depth--
                    if (depth == 0 && start != -1) {
                        return raw.substring(start, i + 1)
                    }
                }
            }
        }

        return null
    }

    private fun findAllNestingAwareCandidates(raw: String): List<String> {
        val candidates = mutableListOf<String>()
        var inString = false
        var escaped = false
        var depth = 0
        var start = -1

        for (i in raw.indices) {
            val c = raw[i]

            if (inString) {
                if (escaped) {
                    escaped = false
                } else if (c == '\\') {
                    escaped = true
                } else if (c == '"') {
                    inString = false
                }
                continue
            }

            when (c) {
                '"' -> inString = true
                '{' -> {
                    if (depth == 0) start = i
                    depth++
                }
                '}' -> {
                    depth--
                    if (depth == 0 && start != -1) {
                        candidates.add(raw.substring(start, i + 1))
                        start = -1
                    }
                }
            }
        }

        return candidates
    }
}
