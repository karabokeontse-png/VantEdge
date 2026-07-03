package com.vantedge.app.data.engine.extraction

import com.vantedge.app.domain.PipelineTrace

object DocumentPreprocessor {
    private const val MIN_CONFIDENCE_THRESHOLD = 0.3f

    data class PreprocessingResult(
        val text: String,
        val qualityScore: Float,
        val warnings: List<String>
    )

    fun preprocess(rawText: String, correlationId: String? = null): PreprocessingResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("DocumentPreprocessor", mapOf(
            "textLength" to rawText.length,
            "correlationId" to correlationId
        ))
        val warnings = mutableListOf<String>()

        val unicodeNormalized = rawText
            .replace("\u00A0", " ")
            .replace("\u2010", "-")
            .replace("\u2011", "-")
            .replace("\u2012", "-")
            .replace("\u2013", "-")
            .replace("\u2014", "--")
            .replace("\u2018", "'")
            .replace("\u2019", "'")
            .replace("\u201C", "\"")
            .replace("\u201D", "\"")
            .replace("\u2022", "-")
            .replace("\u2026", "...")
            .replace("\u00AD", "")

        val hyphenRepaired = repairHyphenation(unicodeNormalized)

        val confusionRepaired = repairOcrConfusionPairs(hyphenRepaired)

        val whitespaceNormalized = confusionRepaired
            .lines()
            .joinToString("\n") { line ->
                line.replace(Regex("\\s+"), " ").trim()
            }

        val qualityScore = estimateQuality(whitespaceNormalized, warnings)

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.exit("DocumentPreprocessor", durationMs, mapOf(
            "quality" to "%.2f".format(qualityScore),
            "warnings" to warnings.size,
            "correlationId" to correlationId
        ))

        return PreprocessingResult(
            text = whitespaceNormalized,
            qualityScore = qualityScore,
            warnings = warnings
        )
    }

    private fun repairHyphenation(text: String): String {
        return text.replace(Regex("""(\w)-\s*\n\s*(\w)""")) { match ->
            match.groupValues[1] + match.groupValues[2]
        }
    }

    private fun repairOcrConfusionPairs(text: String): String {
        var result = text
        result = result.replace(Regex("(?<=\\S)rn(?=\\s|\\b|[.,!?;:])")) { if (it.value.contains("\\b")) it.value else "m" }
        result = result.replace(Regex("\\bcl(?=[aeiou])"), "d")
        result = result.replace(Regex("(?<=\\d)0(?=[A-Za-z])"), "O")
        result = result.replace(Regex("(?<=\\D)0(?=\\d)"), "O")
        result = result.replace(Regex("(?<=\\d)l(?=\\d)"), "1")
        result = result.replace(Regex("\\bI(?=[a-z])"), "l")
        return result
    }

    private fun estimateQuality(text: String, warnings: MutableList<String>): Float {
        if (text.isBlank()) {
            warnings.add("Document is empty")
            return 0.0f
        }

        val words = text.split(Regex("\\s+"))
        val totalChars = text.length
        val alphaChars = text.count { it.isLetter() }
        val digitChars = text.count { it.isDigit() }
        val alphaRatio = if (totalChars > 0) alphaChars.toFloat() / totalChars else 0f
        val digitRatio = if (totalChars > 0) digitChars.toFloat() / totalChars else 0f
        val avgWordLength = if (words.isNotEmpty()) words.sumOf { it.length }.toFloat() / words.size else 0f
        val shortWordCount = words.count { it.length <= 2 }
        val shortWordRatio = if (words.isNotEmpty()) shortWordCount.toFloat() / words.size else 0f

        val nonAlphaTokens = words.count { word -> word.isNotEmpty() && word.none { it.isLetter() } }
        val nonAlphaRatio = if (words.isNotEmpty()) nonAlphaTokens.toFloat() / words.size else 0f

        val gibberishPatterns = listOf(
            Regex("""(.)\1{3,}"""),
            Regex("""[bcdfghjklmnpqrstvwxz]{5,}"""),
            Regex("""[aeiouy]{4,}""")
        )
        val gibberishLines = text.lines().count { line ->
            gibberishPatterns.any { it.containsMatchIn(line.lowercase()) }
        }
        val gibberishRatio = if (text.lines().isNotEmpty()) gibberishLines.toFloat() / text.lines().size else 0f

        var score = 1.0f

        if (alphaRatio < 0.5f) {
            score -= 0.2f
            warnings.add("Low alphabetic content ratio: ${"%.2f".format(alphaRatio)}")
        }
        if (shortWordRatio > 0.4f) {
            score -= 0.15f
            warnings.add("High short-word ratio: ${"%.2f".format(shortWordRatio)}")
        }
        if (nonAlphaRatio > 0.2f) {
            score -= 0.1f
            warnings.add("High non-alphabetic token ratio: ${"%.2f".format(nonAlphaRatio)}")
        }
        if (avgWordLength > 15f) {
            score -= 0.1f
            warnings.add("Unusually long average word length: ${"%.1f".format(avgWordLength)}")
        }
        if (gibberishRatio > 0.1f) {
            score -= 0.2f
            warnings.add("Lines with gibberish patterns: ${"%.2f".format(gibberishRatio)}")
        }
        if (digitRatio > 0.3f) {
            score -= 0.1f
            warnings.add("High digit ratio: ${"%.2f".format(digitRatio)}")
        }

        val shortLines = text.lines().count { it.isNotBlank() && it.length < 15 }
        val shortLineRatio = if (text.lines().isNotEmpty()) shortLines.toFloat() / text.lines().count { it.isNotBlank() } else 0f
        if (shortLineRatio > 0.5f) {
            score -= 0.1f
            warnings.add("Fragmented layout: many short lines")
        }

        if (score < MIN_CONFIDENCE_THRESHOLD) {
            warnings.add("Low quality score: ${"%.2f".format(score)} — extraction results may be unreliable")
        }

        return score.coerceIn(0.0f, 1.0f)
    }
}
