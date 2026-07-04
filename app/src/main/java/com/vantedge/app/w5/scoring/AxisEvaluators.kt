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
        if (required == 0) {
            return if (profile.experienceYears > 0) 1.0 else 0.0
        }
        return min(profile.experienceYears.toDouble() / required, 1.0)
    }

    fun roleRelevance(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): Double {
        val profileTokens = (profile.roles + listOf(profile.currentTitle ?: ""))
            .flatMap { tokenize(it, assets.stopWords) }
            .toSet()
        val jobTokens = tokenize(job.title, assets.stopWords).toSet()
        return jaccard(profileTokens, jobTokens)
    }

    fun keywordCoverage(profile: ValidatedProfile, job: ValidatedJob, assets: ValidationAssets): Double {
        if (job.keywords.isEmpty()) return 0.0
        val profileTokens = profile.skills.flatMap { tokenize(it, assets.stopWords) }.toSet()
        val matched = job.keywords.count { keyword ->
            val keywordTokens = tokenize(keyword, assets.stopWords).toSet()
            jaccard(keywordTokens, profileTokens) > 0.0
        }
        return matched.toDouble() / job.keywords.size
    }

    fun seniorityFit(profile: ValidatedProfile, job: ValidatedJob): Double {
        val profileIndex = SeniorityDeriver.toIndex(profile.seniorityLevel)
        val jobIndex = SeniorityDeriver.toIndex(job.seniorityLevel)
        if (profileIndex == null || jobIndex == null) return 0.0
        return 1.0 - kotlin.math.abs(profileIndex - jobIndex) / 3.0
    }

    fun structuralCompleteness(profile: ValidatedProfile, job: ValidatedJob): Double {
        val profileRatio = if (RequiredProfileFields.fields.isEmpty()) {
            1.0
        } else {
            profile.completedFields.size.toDouble() / RequiredProfileFields.fields.size
        }
        val jobRatio = if (RequiredJobFields.fields.isEmpty()) {
            1.0
        } else {
            job.completedFields.size.toDouble() / RequiredJobFields.fields.size
        }
        return (profileRatio + jobRatio) / 2.0
    }

    fun constraintCompliance(profile: ValidatedProfile, job: ValidatedJob): Double {
        return if (profile.isAccepted && job.isAccepted) 1.0 else 0.0
    }
}
