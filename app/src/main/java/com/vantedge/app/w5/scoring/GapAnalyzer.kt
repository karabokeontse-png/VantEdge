package com.vantedge.app.w5.scoring

object GapAnalyzer {
    fun analyze(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): GapAnalysis {
        val profileUnion = profile.skills.flatMap { tokenize(it, assets.stopWords) }.toSet()

        val missing = mutableListOf<String>()
        val weak = mutableListOf<String>()
        val matched = mutableListOf<String>()

        for (requiredSkill in job.requiredSkills) {
            val skillTokens = tokenize(requiredSkill, assets.stopWords).toSet()
            val similarity = jaccard(skillTokens, profileUnion)

            when {
                similarity < 0.3 -> missing.add(requiredSkill)
                similarity < 0.6 -> weak.add(requiredSkill)
                else -> matched.add(requiredSkill)
            }
        }

        return GapAnalysis(missing, weak, matched)
    }
}
