package com.vantedge.app.w5.scoring

import kotlin.math.abs

/**
 * TAXONOMY CURATION GOVERNANCE: The taxonomy curation process MUST include an automated
 * validation step that checks every new taxonomy entry against the extraction dictionary.
 * If an entry is a known truncated fragment (e.g., ends with 'mation', 'ization') and is
 * not a standalone English word, it MUST be flagged for human review before inclusion to
 * prevent dictionary poisoning.
 */
object ExtractionValidator {

    internal val KNOWN_TRUNCATIONS = setOf("mation", "ization", "isation", "fication")

    private val CONSONANTS = setOf(
        'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
        'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'
    )

    private var _dictionary: Set<String>? = null

    fun validate(
        token: String,
        taxonomy: Set<String> = SkillTaxonomyProvider.getTaxonomy()
    ): ExtractionAssessment {
        require(token.isNotBlank()) { "token must not be blank" }

        val lowerToken = token.lowercase()

        val entitySet = taxonomy.filter {
            it.contains(" ") || it.contains(Regex("[^a-zA-Z0-9]"))
        }.toSet()

        if (lowerToken in entitySet) {
            return ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.ACCEPTED,
                confidence = 1.0f,
                reason = "ENTITY_MATCH"
            )
        }

        if (lowerToken in taxonomy) {
            return ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.ACCEPTED,
                confidence = 1.0f,
                reason = "EXACT_DOMAIN_MATCH"
            )
        }

        val dictionary = getDictionary()
        if (lowerToken in dictionary) {
            return ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.ACCEPTED,
                confidence = 1.0f,
                reason = "DICTIONARY_MATCH"
            )
        }

        if (lowerToken.length <= 7) {
            var maxRun = 0
            var currentRun = 0
            for (c in lowerToken) {
                if (c in CONSONANTS) {
                    currentRun++
                    if (currentRun > maxRun) maxRun = currentRun
                } else {
                    currentRun = 0
                }
            }
            if (maxRun >= 5) {
                return ExtractionAssessment(
                    originalText = token,
                    verdict = AssessmentVerdict.REJECTED,
                    confidence = 0.0f,
                    reason = "IMPOSSIBLE_CONSONANT_CLUSTER"
                )
            }
        }

        if (lowerToken.length <= 8 && lowerToken in KNOWN_TRUNCATIONS) {
            return ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.REJECTED,
                confidence = 0.0f,
                reason = "KNOWN_TRUNCATED_FRAGMENT"
            )
        }

        val threshold = if (lowerToken.length <= 4) 1 else 2
        var bestDistance = Int.MAX_VALUE
        val candidates = mutableListOf<String>()

        for (term in taxonomy) {
            val dist = levenshtein(lowerToken, term.lowercase(), threshold)
            if (dist != -1 && dist <= threshold) {
                when {
                    dist < bestDistance -> {
                        bestDistance = dist
                        candidates.clear()
                        candidates.add(term)
                    }
                    dist == bestDistance -> {
                        candidates.add(term)
                    }
                }
            }
        }

        if (candidates.isEmpty()) {
            return ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.LOW_CONFIDENCE,
                confidence = 0.5f,
                reason = "NO_MATCH_FOUND"
            )
        }

        val minLength = candidates.minOf { it.length }
        val shortest = candidates.filter { it.length == minLength }

        return if (shortest.size == 1) {
            ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.REPAIRED,
                confidence = 0.75f,
                reason = "FUZZY_REPAIR_SUGGESTED",
                optionalSuggestion = shortest[0]
            )
        } else {
            ExtractionAssessment(
                originalText = token,
                verdict = AssessmentVerdict.LOW_CONFIDENCE,
                confidence = 0.5f,
                reason = "AMBIGUOUS_MATCH"
            )
        }
    }

    private fun getDictionary(): Set<String> {
        if (_dictionary == null) {
            _dictionary = loadDictionary()
        }
        return _dictionary!!
    }

    private fun loadDictionary(): Set<String> {
        val stream = javaClass.classLoader?.getResourceAsStream("extraction_dictionary.txt")
            ?: return emptySet()
        return stream.bufferedReader().readLines()
            .filter { !it.startsWith("//") && it.isNotBlank() }
            .map { it.trim().lowercase() }
            .toSet()
    }

    private fun levenshtein(s1: String, s2: String, maxDist: Int): Int {
        val m = s1.length
        val n = s2.length

        if (abs(m - n) > maxDist) return -1

        val dp = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..m) dp[i][0] = i
        for (j in 0..n) dp[0][j] = j

        for (i in 1..m) {
            var minInRow = Int.MAX_VALUE
            for (j in 1..n) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                )
                if (dp[i][j] < minInRow) minInRow = dp[i][j]
            }
            if (minInRow > maxDist) return -1
        }

        return dp[m][n]
    }
}
