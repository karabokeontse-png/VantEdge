package com.vantedge.app.w5.scoring

object GapAnalyzer {
    fun analyze(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): GapAnalysis {
        if (profile.skills.isEmpty()) {
            val unassessed = job.requiredSkills.map {
                UnassessedRequirement(it, UnassessedReason.PROFILE_SKILL_EVIDENCE_ABSENT)
            }
            return GapAnalysis(
                missing = emptyList(),
                weak = emptyList(),
                matched = emptyList(),
                unassessed = unassessed
            )
        }

        val missing = mutableListOf<String>()
        val weak = mutableListOf<String>()
        val matched = mutableListOf<MatchEvidence>()

        for (requiredSkill in job.requiredSkills) {
            val skillTokens = tokenize(requiredSkill, emptySet()).toSet()
            val expandedSkillTokens = expand(skillTokens, assets.keywordDictionary).filter { it !in assets.stopWords }.toSet()

            val maxSimilarity = profile.skills.maxOf { profileSkill ->
                val profileTokens = tokenize(profileSkill, emptySet()).toSet()
                val expandedProfileTokens = expand(profileTokens, assets.keywordDictionary).filter { it !in assets.stopWords }.toSet()
                jaccard(expandedSkillTokens, expandedProfileTokens)
            }

            when {
                maxSimilarity < 0.3 -> missing.add(requiredSkill)
                maxSimilarity <= 0.6 -> weak.add(requiredSkill)
                else -> matched.add(MatchEvidence(name = requiredSkill, similarity = maxSimilarity))
            }
        }

        return GapAnalysis(missing, weak, matched)
    }
}
