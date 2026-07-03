package com.vantedge.app.data.engine.extraction

import android.util.Log
import com.vantedge.app.domain.PipelineTrace
import org.json.JSONObject
import java.util.regex.Pattern

data class ExtractionResult(
    val content: String,
    val strategy: String,
    val success: Boolean,
    val failureReason: String?
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
                "contentLength" to result.content.length
            )
        )

        return result
    }

    private fun extractInternal(normalized: String): ExtractionResult {
        try {
            JSONObject(normalized)
            return ExtractionResult(normalized, "direct_parse", true, null)
        } catch (_: Exception) {}

        val noFence = normalized
            .replace("```json", "")
            .replace("```", "")
            .replace(Pattern.compile("""\*\*(.+?)\*\*""").toRegex(), "$1")
            .replace(Pattern.compile("""\*(.+?)\*""").toRegex(), "$1")
            .trim()
        if (noFence != normalized) {
            try {
                JSONObject(noFence)
                return ExtractionResult(noFence, "markdown_strip", true, null)
            } catch (_: Exception) {}
        }

        val rootObj = extractNestingAwareRoot(normalized)
        if (rootObj != null) {
            try {
                JSONObject(rootObj)
                return ExtractionResult(rootObj, "nesting_aware_root", true, null)
            } catch (_: Exception) {}
        }

        val candidates = findAllNestingAwareCandidates(normalized)
            .filter { it.length > 50 }
            .sortedByDescending { it.length }
        for (candidate in candidates) {
            try {
                JSONObject(candidate)
                return ExtractionResult(candidate, "substring_scan", true, null)
            } catch (_: Exception) {}
        }

        return ExtractionResult(normalized, "failed", false, "ALL_STRATEGIES_FAILED")
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
