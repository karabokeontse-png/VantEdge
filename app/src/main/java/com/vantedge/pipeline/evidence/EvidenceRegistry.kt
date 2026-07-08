package com.vantedge.pipeline.evidence

import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.w5.scoring.NormalizedProfile
import com.vantedge.app.w5.scoring.tokenize

interface EvidenceRegistry {
    fun query(category: EvidenceCategory, key: String): EvidenceResult
    fun listKeys(category: EvidenceCategory): List<String>
    fun hasEvidence(category: EvidenceCategory, key: String): Boolean
}

class VantEdgeEvidenceRegistry(
    private val normalizedProfile: NormalizedProfile,
    private val userProfile: UserProfile,
    private val jobDescription: String,
    private val correlationId: String
) : EvidenceRegistry {

    private val profileSkillsLower: Set<String> = normalizedProfile.skills.map { it.lowercase().trim() }.toSet()
    private val profileCertsLower: Set<String> = normalizedProfile.certifications.map { it.lowercase().trim() }.toSet()
    private val jobDescriptionTokens: Set<String> = tokenize(jobDescription, emptySet()).toSet()
    private val computedCache: MutableMap<String, EvidenceResult> = mutableMapOf()

    override fun query(category: EvidenceCategory, key: String): EvidenceResult {
        return when (category) {
            is EvidenceCategory.ProfileEvidence -> queryProfile(key)
            is EvidenceCategory.JobEvidence -> queryJob(key)
            is EvidenceCategory.ComputedEvidence -> queryComputed(key)
            is EvidenceCategory.SystemEvidence -> querySystem(key)
        }
    }

    override fun listKeys(category: EvidenceCategory): List<String> {
        return when (category) {
            is EvidenceCategory.ProfileEvidence -> listOf("skills", "certifications", "workHistory", "education", "languages", "summary")
            is EvidenceCategory.JobEvidence -> listOf("description")
            is EvidenceCategory.ComputedEvidence -> listOf("skillCount", "certificationCount", "yearsExperience")
            is EvidenceCategory.SystemEvidence -> listOf("correlationId", "timestamp")
        }
    }

    override fun hasEvidence(category: EvidenceCategory, key: String): Boolean {
        return query(category, key) !is EvidenceResult.Missing
    }

    private fun queryProfile(key: String): EvidenceResult {
        return when (key) {
            "skills" -> EvidenceResult.Found(profileSkillsLower.toList())
            "certifications" -> EvidenceResult.Found(profileCertsLower.toList())
            "workHistory" -> EvidenceResult.Found(normalizedProfile.workHistory)
            "education" -> EvidenceResult.Found(normalizedProfile.education)
            "languages" -> EvidenceResult.Found(normalizedProfile.languages)
            "summary" -> EvidenceResult.Found(normalizedProfile.summary)
            else -> {
                if (key.startsWith("skill.")) {
                    val skillName = key.removePrefix("skill.").lowercase().trim()
                    val match = profileSkillsLower.find { it == skillName }
                    if (match != null) EvidenceResult.Found(match) else EvidenceResult.Missing
                } else if (key.startsWith("cert.")) {
                    val certName = key.removePrefix("cert.").lowercase().trim()
                    val match = profileCertsLower.find { it == certName }
                    if (match != null) EvidenceResult.Found(match) else EvidenceResult.Missing
                } else {
                    EvidenceResult.Missing
                }
            }
        }
    }

    private fun queryJob(key: String): EvidenceResult {
        return when {
            key == "description" -> EvidenceResult.Found(jobDescription)
            key.startsWith("contains.") -> {
                val term = key.removePrefix("contains.").lowercase().trim()
                EvidenceResult.Computed(term in jobDescriptionTokens)
            }
            else -> EvidenceResult.Missing
        }
    }

    private fun queryComputed(key: String): EvidenceResult {
        return computedCache.getOrPut(key) {
            when (key) {
                "skillCount" -> EvidenceResult.Computed(profileSkillsLower.size)
                "certificationCount" -> EvidenceResult.Computed(profileCertsLower.size)
                "yearsExperience" -> EvidenceResult.Computed(computeYearsExperience())
                else -> EvidenceResult.Missing
            }
        }
    }

    private fun querySystem(key: String): EvidenceResult {
        return when (key) {
            "correlationId" -> EvidenceResult.Found(correlationId)
            "timestamp" -> EvidenceResult.Computed(System.currentTimeMillis())
            else -> EvidenceResult.Missing
        }
    }

    private fun computeYearsExperience(): Int {
        val asOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val intervals = userProfile.workHistory.mapNotNull { exp ->
            val start = extractYear(exp.startDate)
            val end = extractYear(exp.endDate) ?: asOfYear
            if (start != null && start <= end) start to end else null
        }.sortedBy { it.first }
        if (intervals.isEmpty()) return 0
        val merged = mutableListOf<Pair<Int, Int>>()
        var currentStart = intervals[0].first
        var currentEnd = intervals[0].second
        for ((start, end) in intervals.drop(1)) {
            if (start <= currentEnd) {
                currentEnd = maxOf(currentEnd, end)
            } else {
                merged.add(currentStart to currentEnd)
                currentStart = start
                currentEnd = end
            }
        }
        merged.add(currentStart to currentEnd)
        return merged.sumOf { (start, end) -> end - start }
    }

    private fun extractYear(dateString: String?): Int? {
        if (dateString == null) return null
        val match = Regex("""\b(19|20)\d{2}\b""").find(dateString)
        return match?.value?.toInt()
    }
}
