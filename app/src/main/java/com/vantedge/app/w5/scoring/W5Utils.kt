package com.vantedge.app.w5.scoring

import java.security.MessageDigest
import java.util.Locale

fun tokenize(input: String, stopWords: Set<String>): List<String> {
    val step1 = input.lowercase(Locale.ROOT)
    val step2 = step1.replace(Regex("[^a-z0-9\\s+.#\\-]"), " ")
    val step3 = step2.split(Regex("\\s+"))
    val step4 = step3.filter { it.isNotEmpty() }
    val step5 = step4.filter { it !in stopWords }
    return step5.distinct()
}

fun jaccard(setA: Set<String>, setB: Set<String>): Double {
    val intersection = setA.intersect(setB)
    val union = setA.union(setB)
    return if (union.isEmpty()) 0.0 else intersection.size.toDouble() / union.size
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
    val allItems = mutableListOf<String>()
    allItems.addAll(profile.skills)
    allItems.addAll(profile.roles)
    allItems.add(profile.currentTitle ?: "")
    allItems.add(profile.experienceYears.toString())
    allItems.add(profile.seniorityLevel ?: "")
    allItems.add(profile.isAccepted.toString())
    allItems.add(profile.isDegraded.toString())
    allItems.add(profile.completedFields.sorted().joinToString(","))
    allItems.addAll(job.requiredSkills)
    allItems.add(job.title)
    allItems.addAll(job.keywords)
    allItems.add(job.requiredYears.toString())
    allItems.add(job.seniorityLevel ?: "")
    allItems.add(job.isAccepted.toString())
    allItems.add(job.isDegraded.toString())
    allItems.add(job.completedFields.sorted().joinToString(","))

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

    val levelCount: Int
        get() = levelMap.size

    fun derive(title: String?, experienceYears: Int?): String? {
        if (title != null) {
            val lowerTitle = title.lowercase(Locale.ROOT)
            val titleTokens = lowerTitle.split(Regex("[^a-z0-9+.#\\-]+")).filter { it.isNotEmpty() }
            val sortedKeywords = levelMap.entries.sortedByDescending { it.key.length }
            for ((keyword, _) in sortedKeywords) {
                if (keyword in titleTokens) {
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
