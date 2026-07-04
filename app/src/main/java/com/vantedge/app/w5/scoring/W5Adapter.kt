package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.StructuredProfileExtraction
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.ExtractedExperience
import com.vantedge.pipeline.validation.ValidationDecision

object W5Adapter {
    fun adapt(
        profile: StructuredProfileExtraction,
        profileDecision: ValidationDecision,
        job: JobExtractionResult,
        jobRequiredSkills: List<String>,
        jobPreferredSkills: List<String>,
        jobDecision: ValidationDecision,
        traceId: String,
        assets: ValidationAssets
    ): W5Input {
        val skills = profile.skills.mapNotNull { it.value }
        val roles = profile.workHistory.mapNotNull { it.jobTitle.value }
        val currentTitle = deriveCurrentTitle(profile)
        val experienceYears = deriveExperienceYears(profile)
        val seniorityLevel = SeniorityDeriver.derive(currentTitle, experienceYears)
        val profileCompletedFields = buildProfileCompletedFields(skills, roles, currentTitle, experienceYears, seniorityLevel)
        val profileIsAccepted = profileDecision is ValidationDecision.Accept || profileDecision is ValidationDecision.Degraded
        val profileIsDegraded = profileDecision is ValidationDecision.Degraded

        val validatedProfile = ValidatedProfile(
            correlationId = traceId,
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
        val keywords = buildKeywords(job, jobRequiredSkills, assets)
        val requiredYears = deriveRequiredYears(job)
        val jobSeniorityLevel = SeniorityDeriver.derive(title, requiredYears)
        val jobCompletedFields = buildJobCompletedFields(jobRequiredSkills, title, keywords, requiredYears, jobSeniorityLevel)
        val jobIsAccepted = jobDecision is ValidationDecision.Accept || jobDecision is ValidationDecision.Degraded
        val jobIsDegraded = jobDecision is ValidationDecision.Degraded

        val validatedJob = ValidatedJob(
            correlationId = traceId,
            requiredSkills = jobRequiredSkills,
            title = title,
            keywords = keywords,
            requiredYears = requiredYears,
            seniorityLevel = jobSeniorityLevel,
            completedFields = jobCompletedFields,
            isAccepted = jobIsAccepted,
            isDegraded = jobIsDegraded
        )

        val trace = W5TraceContext(traceId, "", System.currentTimeMillis())

        return W5Input(validatedProfile, validatedJob, trace)
    }

    private fun deriveCurrentTitle(profile: StructuredProfileExtraction): String? {
        val latest = profile.workHistory.maxByOrNull { experience ->
            extractYear(experience.endDate.value) ?: 0
        }
        val latestTitle = latest?.jobTitle?.value
        if (latestTitle != null && latestTitle.isNotEmpty()) return latestTitle

        return profile.personalInfo.headline?.value
    }

    private fun deriveExperienceYears(profile: StructuredProfileExtraction): Int {
        var totalYears = 0
        for (experience in profile.workHistory) {
            val startYear = extractYear(experience.startDate.value)
            val endYear = extractYear(experience.endDate.value) ?: java.time.Year.now().value
            if (startYear != null) {
                totalYears += (endYear - startYear).coerceAtLeast(0)
            }
        }
        return totalYears
    }

    private fun extractYear(dateString: String?): Int? {
        if (dateString == null) return null
        val match = Regex("""\b(19|20)\d{2}\b""").find(dateString)
        return match?.value?.toInt()
    }

    private fun buildKeywords(
        job: JobExtractionResult,
        jobRequiredSkills: List<String>,
        assets: ValidationAssets
    ): List<String> {
        val descriptionTokens = tokenize(job.description, assets.stopWords)
        val frequencyMap = descriptionTokens.groupingBy { it }.eachCount()
        val topTokens = frequencyMap.entries
            .sortedByDescending { it.value }
            .take(10)
            .map { it.key }
        return (jobRequiredSkills + topTokens).distinct()
    }

    private fun deriveRequiredYears(job: JobExtractionResult): Int? {
        val pattern = Regex("""(\d+)\+?\s*(years|yrs|year)""")
        val matches = pattern.findAll(job.description)
        val years = matches.map { it.groupValues[1].toInt() }.toList()
        return if (years.isEmpty()) null else years.max()
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
