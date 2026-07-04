package com.vantedge.app.w5.scoring

import java.security.MessageDigest

fun tokenize(input: String, stopWords: Set<String>): List<String> {
    val step1 = input.lowercase()
    val step2 = step1.replace(Regex("[^a-z0-9\\s]"), " ")
    val step3 = step2.split(Regex("\\s+"))
    val step4 = step3.filter { it.isNotEmpty() }
    val step5 = step4.filter { it !in stopWords }
    return step5.distinct()
}

fun jaccard(setA: Set<String>, setB: Set<String>): Double {
    val intersection = setA.intersect(setB)
    val union = setA.union(setB)
    return if (union.isEmpty()) 1.0 else intersection.size.toDouble() / union.size
}

fun expand(tokens: Set<String>, dictionary: Map<String, List<String>>): Set<String> {
    val result = mutableSetOf<String>()
    result.addAll(tokens)

    val sortedTokens = tokens.sorted()
    for (token in sortedTokens) {
        val synonyms = dictionary[token] ?: emptyList()
        result.addAll(synonyms.sorted())
    }

    for (token in sortedTokens) {
        for ((key, synonyms) in dictionary.entries.sortedBy { it.key }) {
            if (token in synonyms) {
                result.add(key)
            }
        }
    }

    return result
}

fun computeInputHash(profile: ValidatedProfile, job: ValidatedJob): String {
    val allItems = profile.skills + profile.roles + listOf(profile.currentTitle ?: "") +
                   job.requiredSkills + job.keywords
    val sorted = allItems.sorted()
    val canonicalString = sorted.joinToString("|")
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(canonicalString.toByteArray(Charsets.UTF_8))
    return hashBytes.joinToString("") { "%02x".format(it) }
}

object SeniorityDeriver {
    private val levelMap = mapOf(
        "junior" to 0,
        "mid" to 1,
        "senior" to 2,
        "lead" to 3
    )

    fun derive(title: String?, experienceYears: Int?): String? {
        if (title != null) {
            val lowerTitle = title.lowercase()
            val sortedKeywords = levelMap.entries.sortedByDescending { it.key.length }
            for ((keyword, _) in sortedKeywords) {
                if (lowerTitle.contains(keyword)) {
                    return keyword
                }
            }
        }

        if (experienceYears != null) {
            return when {
                experienceYears < 2 -> "junior"
                experienceYears in 2..5 -> "mid"
                experienceYears in 6..10 -> "senior"
                experienceYears > 10 -> "lead"
                else -> null
            }
        }

        return null
    }

    fun toIndex(level: String?): Int? {
        return levelMap[level]
    }
}
