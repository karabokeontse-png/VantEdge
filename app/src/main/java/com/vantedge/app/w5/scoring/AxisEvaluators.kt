package com.vantedge.app.w5.scoring

import kotlin.math.min

object AxisEvaluators {
    fun skillMatch(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): Double {
        val profileTokens = profile.skills.flatMap { tokenize(it, emptySet()) }.toSet()
        val jobTokens = job.requiredSkills.flatMap { tokenize(it, emptySet()) }.toSet()
        val expandedProfile = expand(profileTokens, assets.keywordDictionary)
        val expandedJob = expand(jobTokens, assets.keywordDictionary)
        val filteredProfile = expandedProfile.filter { it !in assets.stopWords }.toSet()
        val filteredJob = expandedJob.filter { it !in assets.stopWords }.toSet()
        return jaccard(filteredProfile, filteredJob)
    }

    fun experienceAlignment(profile: ValidatedProfile, job: ValidatedJob): Double {
        val required = job.requiredYears ?: return 0.0
        if (required == 0) return 1.0
        return min(profile.experienceYears.toDouble() / required, 1.0)
    }

    fun roleRelevance(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): Double {
        val profileTokens = (profile.roles + listOf(profile.currentTitle ?: ""))
            .flatMap { tokenize(it, emptySet()) }
            .toSet()
        val expandedProfile = expand(profileTokens, assets.keywordDictionary)
        val filteredProfile = expandedProfile.filter { it !in assets.stopWords }.toSet()
        val jobTokens = tokenize(job.title, emptySet()).toSet()
        val expandedJob = expand(jobTokens, assets.keywordDictionary)
        val filteredJob = expandedJob.filter { it !in assets.stopWords }.toSet()
        return jaccard(filteredProfile, filteredJob)
    }

    fun keywordCoverage(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): Double {
        if (job.keywords.isEmpty()) return 1.0
        val profileTokens = profile.skills.flatMap { tokenize(it, emptySet()) }.toSet()
        val expandedProfile = expand(profileTokens, assets.keywordDictionary)
        val filteredProfile = expandedProfile.filter { it !in assets.stopWords }.toSet()
        val matched = job.keywords.count { keyword ->
            val keywordTokens = tokenize(keyword, emptySet()).toSet()
            val expandedKeyword = expand(keywordTokens, assets.keywordDictionary)
            val filteredKeyword = expandedKeyword.filter { it !in assets.stopWords }.toSet()
            jaccard(filteredKeyword, filteredProfile) > 0.0
        }
        return matched.toDouble() / job.keywords.size
    }

    fun seniorityFit(profile: ValidatedProfile, job: ValidatedJob): Double {
        val profileIndex = SeniorityDeriver.toIndex(profile.seniorityLevel)
        val jobIndex = SeniorityDeriver.toIndex(job.seniorityLevel)
        if (profileIndex == null || jobIndex == null) return 0.0
        val maxDistance = (SeniorityDeriver.levelCount - 1).toDouble()
        return 1.0 - kotlin.math.abs(profileIndex - jobIndex) / maxDistance
    }

    fun structuralCompleteness(profile: ValidatedProfile, job: ValidatedJob): Double {
        val profileRatio = profile.completedFields.intersect(RequiredProfileFields.fields).size.toDouble() / RequiredProfileFields.fields.size
        val jobRatio = job.completedFields.intersect(RequiredJobFields.fields).size.toDouble() / RequiredJobFields.fields.size
        return min((profileRatio + jobRatio) / 2.0, 1.0)
    }

    fun constraintCompliance(profile: ValidatedProfile, job: ValidatedJob): Double {
        return if (profile.isAccepted && job.isAccepted) 1.0 else 0.0
    }
}
