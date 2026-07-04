package com.vantedge.app.w5.scoring

object HintBuilder {
    fun build(gapAnalysis: GapAnalysis, axisScores: List<AxisScore>): RecommendationHints {
        val hints = mutableListOf<String>()

        for (skill in gapAnalysis.missing) {
            hints.add("Acquire based on SkillMatch: $skill")
        }

        for (skill in gapAnalysis.weak) {
            hints.add("Strengthen based on SkillMatch: $skill")
        }

        val experienceAxis = axisScores.find { it.axisName == "ExperienceAlignment" }
        if (experienceAxis != null && experienceAxis.score < 0.5) {
            hints.add("Increase exposure to target domain based on ExperienceAlignment")
        }

        val seniorityAxis = axisScores.find { it.axisName == "SeniorityFit" }
        if (seniorityAxis != null && seniorityAxis.score < 0.5) {
            hints.add("Re-evaluate trajectory based on SeniorityFit")
        }

        return RecommendationHints(hints)
    }
}
