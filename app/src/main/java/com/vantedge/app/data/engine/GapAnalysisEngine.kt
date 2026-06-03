package com.vantedge.app.data.engine

import com.vantedge.app.data.model.*

object GapAnalysisEngine {

    fun analyze(profile: UserProfile, jobSkills: List<String>): List<SkillGap> {

        val userSkills: Set<String> = profile.skills.toSet()

        return jobSkills.map { skill ->

            val hasSkill = userSkills.contains(skill)

            SkillGap(
                skill = skill,
                severity = if (hasSkill) GapSeverity.LOW else GapSeverity.HIGH,
                reason = if (hasSkill) "Already present" else "Missing skill"
            )
        }
    }
}