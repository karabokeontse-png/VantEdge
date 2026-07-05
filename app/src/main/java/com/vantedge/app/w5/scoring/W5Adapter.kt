package com.vantedge.app.w5.scoring


import com.vantedge.app.data.model.StructuredProfileExtraction
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.pipeline.validation.ValidationDecision


object W5Adapter {
    fun adapt(
        profile: StructuredProfileExtraction,
        profileDecision: ValidationDecision,
        job: JobExtractionResult,
        jobRequiredSkills: List<String>,
        jobPreferredSkills: List<String>,
        jobRequiredYears: Int?,
        jobDecision: ValidationDecision,
        correlationId: String,
        asOfYear: Int
    ): AdaptedW5Input {
        val skills = profile.skills.mapNotNull { it.value }
        val roles = profile.workHistory.mapNotNull { it.jobTitle?.value }
        val currentTitle = deriveCurrentTitle(profile)
        val experienceYears = deriveExperienceYears(profile, asOfYear)
        val seniorityLevel = SeniorityDeriver.derive(currentTitle, experienceYears)
        val profileCompletedFields = buildProfileCompletedFields(skills, roles, currentTitle, experienceYears, seniorityLevel)
        val profileIsAccepted = profileDecision is ValidationDecision.Accept || profileDecision is ValidationDecision.Degraded
        val profileIsDegraded = profileDecision is ValidationDecision.Degraded


        val validatedProfile = ValidatedProfile(
            correlationId = correlationId,
            skills = skills,
            roles = roles,
            currentTitle = currentTitle,
            experienceYears = experienceYears,
            seniorityLevel = seniorityLevel,
            completedFields = profileCompletedFields,
            isAccepted = profileIsAccepted,
            isDegraded = profileIsDegraded
        )


        val title = job.jobTitle ?: ""
        val keywords = buildKeywords(jobRequiredSkills, jobPreferredSkills)
        val jobSeniorityLevel = SeniorityDeriver.derive(title, jobRequiredYears)
        val jobCompletedFields = buildJobCompletedFields(jobRequiredSkills, title, keywords, jobRequiredYears, jobSeniorityLevel)
        val jobIsAccepted = jobDecision is ValidationDecision.Accept || jobDecision is ValidationDecision.Degraded
        val jobIsDegraded = jobDecision is ValidationDecision.Degraded


        val validatedJob = ValidatedJob(
            correlationId = correlationId,
            requiredSkills = jobRequiredSkills,
            title = title,
            keywords = keywords,
            requiredYears = jobRequiredYears,
            seniorityLevel = jobSeniorityLevel,
            completedFields = jobCompletedFields,
            isAccepted = jobIsAccepted,
            isDegraded = jobIsDegraded
        )


        return AdaptedW5Input(validatedProfile, validatedJob)
    }


    private fun deriveCurrentTitle(profile: StructuredProfileExtraction): String? {
        val latest = profile.workHistory.maxByOrNull { experience ->
            extractYear(experience.endDate?.value) ?: 0
        }
        val latestTitle = latest?.jobTitle?.value
        if (latestTitle != null && latestTitle.isNotEmpty()) return latestTitle


        return profile.personalInfo.headline?.value
    }


    private fun deriveExperienceYears(
        profile: StructuredProfileExtraction,
        asOfYear: Int
    ): Int {
        val intervals = profile.workHistory.mapNotNull { experience ->
            val start = extractYear(experience.startDate?.value)
            val end = extractYear(experience.endDate?.value) ?: asOfYear
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


    // TECHNICAL DEBT: Date normalization should migrate upstream (W4/P3).
    // W5 consumes extracted date strings because upstream does not yet provide normalized year Ints.
    // When upstream adds normalized year fields, this function should be removed and W5 should
    // consume Int? directly from StructuredProfileExtraction.workHistory[].startYear / .endYear.
    private fun extractYear(dateString: String?): Int? {
        if (dateString == null) return null
        val match = Regex("""\b(19|20)\d{2}\b""").find(dateString)
        return match?.value?.toInt()
    }


    private fun buildKeywords(
        jobRequiredSkills: List<String>,
        jobPreferredSkills: List<String>
    ): List<String> {
        return (jobRequiredSkills + jobPreferredSkills).distinct()
    }


    private fun buildProfileCompletedFields(
        skills: List<String>,
        roles: List<String>,
        currentTitle: String?,
        experienceYears: Int,
        seniorityLevel: String?
    ): Set<String> {
        val fields = mutableSetOf<String>()
        if (skills.isNotEmpty()) fields.add("skills")
        if (roles.isNotEmpty()) fields.add("roles")
        if (currentTitle != null && currentTitle.isNotEmpty()) fields.add("currentTitle")
        if (experienceYears > 0) fields.add("experienceYears")
        if (seniorityLevel != null) fields.add("seniorityLevel")
        return fields
    }


    private fun buildJobCompletedFields(
        requiredSkills: List<String>,
        title: String,
        keywords: List<String>,
        requiredYears: Int?,
        seniorityLevel: String?
    ): Set<String> {
        val fields = mutableSetOf<String>()
        if (requiredSkills.isNotEmpty()) fields.add("requiredSkills")
        if (title.isNotEmpty()) fields.add("title")
        if (keywords.isNotEmpty()) fields.add("keywords")
        if (requiredYears != null) fields.add("requiredYears")
        if (seniorityLevel != null) fields.add("seniorityLevel")
        return fields
    }
}