package com.vantedge.app.w5.scoring

import com.vantedge.app.domain.PipelineTrace

data class AdaptedJobInput(
    val requiredSkills: List<String>,
    val preferredSkills: List<String>,
    val requiredQualifications: List<RequirementEvidence> = emptyList(),
    val requiredYears: Int?,
    val keywords: List<String>,
    val unmappedRequirements: List<RequirementEvidence>
)

enum class RequirementDiagnosticType {
    UNMAPPED_REQUIREMENT
}

data class RequirementDiagnostic(
    val type: RequirementDiagnosticType,
    val category: String,
    val text: String
)

data class DiagnosticsSummary(
    val totalUnmapped: Int,
    val totalClassified: Int,
    val totalEvidence: Int
)

data class RequirementDiagnostics(
    val diagnostics: List<RequirementDiagnostic>,
    val summary: DiagnosticsSummary
)

data class AdaptationResult(
    val validatedJob: ValidatedJob,
    val diagnostics: RequirementDiagnostics
)

object JobRequirementAdapter {

    private val preferredMarkers = setOf("preferred", "advantage", "nice to have", "bonus", "desirable", "plus")
    private val experiencePattern = Regex("""(\d+)\+?\s*(year|years|yr|yrs)""")

    fun adapt(
        jobDescription: String,
        requiredSkills: List<String> = emptyList(),
        preferredSkills: List<String> = emptyList(),
        educationRequired: String? = null,
        skillTaxonomy: Set<String>,
        stopWords: Set<String>,
        correlationId: String
    ): AdaptationResult {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("JobRequirementAdapter.adapt", mapOf(
            "correlationId" to correlationId,
            "descLength" to jobDescription.length,
            "taxonomySize" to skillTaxonomy.size,
            "structuredRequiredCount" to requiredSkills.size,
            "structuredPreferredCount" to preferredSkills.size
        ))

        val hasStructuredSkills = requiredSkills.isNotEmpty() || preferredSkills.isNotEmpty()

        val adaptedInput = if (hasStructuredSkills) {
            adaptFromStructuredSkills(
                jobDescription = jobDescription,
                requiredSkills = requiredSkills,
                preferredSkills = preferredSkills,
                educationRequired = educationRequired,
                skillTaxonomy = skillTaxonomy
            )
        } else {
            adaptFromDescription(
                jobDescription = jobDescription,
                skillTaxonomy = skillTaxonomy,
                stopWords = stopWords,
                correlationId = correlationId
            )
        }

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.dataQuality(
            stage = "JobRequirementAdapter",
            issue = "REQUIREMENT_CLASSIFICATION",
            details = mapOf(
                "correlationId" to correlationId,
                "structuredRequiredCount" to requiredSkills.size,
                "structuredPreferredCount" to preferredSkills.size,
                "matchedSkillCount" to adaptedInput.requiredSkills.size + adaptedInput.preferredSkills.size,
                "unmappedCount" to adaptedInput.unmappedRequirements.size,
                "unmappedCategories" to adaptedInput.unmappedRequirements.map { it.category }.distinct()
            ),
            correlationId = correlationId
        )

        val diagnosticsList = adaptedInput.unmappedRequirements.map { evidence ->
            RequirementDiagnostic(
                type = RequirementDiagnosticType.UNMAPPED_REQUIREMENT,
                category = evidence.category.name,
                text = evidence.originalText
            )
        }

        val classifiedCount = adaptedInput.requiredSkills.size +
                              adaptedInput.preferredSkills.size +
                              adaptedInput.requiredQualifications.size
        val totalEvidence = classifiedCount + adaptedInput.unmappedRequirements.size
        val summary = DiagnosticsSummary(
            totalUnmapped = adaptedInput.unmappedRequirements.size,
            totalClassified = classifiedCount,
            totalEvidence = totalEvidence
        )

        val diagnostics = RequirementDiagnostics(
            diagnostics = diagnosticsList,
            summary = summary
        )

        val validatedJob = toValidatedJob(adaptedInput, "", correlationId)

        PipelineTrace.dataQuality(
            stage = "JobRequirementAdapter.adapt",
            issue = "ADAPTATION_COMPLETE",
            details = mapOf(
                "correlationId" to correlationId,
                "requiredSkillCount" to adaptedInput.requiredSkills.size,
                "preferredSkillCount" to adaptedInput.preferredSkills.size,
                "requiredYears" to (adaptedInput.requiredYears ?: "null"),
                "unmappedCount" to adaptedInput.unmappedRequirements.size,
                "method" to if (hasStructuredSkills) "STRUCTURED" else "DESCRIPTION_FALLBACK"
            ),
            correlationId = correlationId
        )
        PipelineTrace.exit("JobRequirementAdapter.adapt", durationMs, mapOf(
            "correlationId" to correlationId,
            "requiredSkills" to adaptedInput.requiredSkills.size,
            "preferredSkills" to adaptedInput.preferredSkills.size,
            "unmappedRequirements" to adaptedInput.unmappedRequirements.size
        ))

        return AdaptationResult(
            validatedJob = validatedJob,
            diagnostics = diagnostics
        )
    }

    private fun adaptFromStructuredSkills(
        jobDescription: String,
        requiredSkills: List<String>,
        preferredSkills: List<String>,
        educationRequired: String? = null,
        skillTaxonomy: Set<String>
    ): AdaptedJobInput {
        val matchedRequired = mutableListOf<String>()
        val matchedPreferred = mutableListOf<String>()
        val requiredQualifications = mutableListOf<RequirementEvidence>()
        val unmapped = mutableListOf<RequirementEvidence>()

        for (skill in requiredSkills) {
            val matched = matchTaxonomy(skill, skillTaxonomy)
            if (matched != null) {
                matchedRequired.add(matched)
            } else {
                val category = classifyRequirement(skill)
                when (category) {
                    RequirementCategory.DEGREE,
                    RequirementCategory.CERTIFICATION,
                    RequirementCategory.LICENCE,
                    RequirementCategory.SECURITY_CLEARANCE -> {
                        requiredQualifications.add(RequirementEvidence(
                            originalText = skill,
                            category = category,
                            classificationStatus = ClassificationStatus.NORMALIZED
                        ))
                    }
                    else -> {
                        val status = if (category == RequirementCategory.UNCLASSIFIED) {
                            ClassificationStatus.UNMAPPED
                        } else {
                            ClassificationStatus.UNSUPPORTED
                        }
                        unmapped.add(RequirementEvidence(
                            originalText = skill,
                            category = category,
                            classificationStatus = status,
                            matchedTaxonomySkill = null
                        ))
                    }
                }
            }
        }

        for (skill in preferredSkills) {
            val matched = matchTaxonomy(skill, skillTaxonomy)
            if (matched != null) {
                matchedPreferred.add(matched)
            } else {
                val category = classifyRequirement(skill)
                when (category) {
                    RequirementCategory.DEGREE,
                    RequirementCategory.CERTIFICATION,
                    RequirementCategory.LICENCE,
                    RequirementCategory.SECURITY_CLEARANCE -> {
                        requiredQualifications.add(RequirementEvidence(
                            originalText = skill,
                            category = category,
                            classificationStatus = ClassificationStatus.NORMALIZED
                        ))
                    }
                    else -> {
                        val status = if (category == RequirementCategory.UNCLASSIFIED) {
                            ClassificationStatus.UNMAPPED
                        } else {
                            ClassificationStatus.UNSUPPORTED
                        }
                        unmapped.add(RequirementEvidence(
                            originalText = skill,
                            category = category,
                            classificationStatus = status,
                            matchedTaxonomySkill = null
                        ))
                    }
                }
            }
        }

        val requiredYears = experiencePattern.find(jobDescription)?.groupValues?.get(1)?.toIntOrNull()

        // PROVISIONAL: Converts legacy educationRequired text into canonical qualification evidence.
        // Replace when structured qualification extraction exists in the normalization layer.
        if (!educationRequired.isNullOrBlank()) {
            val parsedFragments = parseEducationString(educationRequired)
            parsedFragments.forEach { fragment ->
                val category = classifyRequirement(fragment.lowercase())
                val isSupported = category in setOf(
                    RequirementCategory.DEGREE,
                    RequirementCategory.CERTIFICATION,
                    RequirementCategory.LICENCE,
                    RequirementCategory.SECURITY_CLEARANCE
                )

                val evidence = RequirementEvidence(
                    originalText = fragment,
                    category = category,
                    classificationStatus = if (isSupported) ClassificationStatus.NORMALIZED else ClassificationStatus.UNMAPPED,
                    matchedTaxonomySkill = null
                )

                if (isSupported) {
                    requiredQualifications.add(evidence)
                } else {
                    unmapped.add(evidence)
                }
            }
        }

        return AdaptedJobInput(
            requiredSkills = matchedRequired.distinct(),
            preferredSkills = matchedPreferred.distinct(),
            requiredQualifications = requiredQualifications,
            requiredYears = requiredYears,
            keywords = (matchedRequired + matchedPreferred).distinct(),
            unmappedRequirements = unmapped
        )
    }

    private fun parseEducationString(raw: String): List<String> {
        return raw.split(Regex(",|\\band\\b|/"))
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }

    private fun adaptFromDescription(
        jobDescription: String,
        skillTaxonomy: Set<String>,
        stopWords: Set<String>,
        correlationId: String
    ): AdaptedJobInput {
        val tokens = tokenize(jobDescription, stopWords)

        val matchedSkills = skillTaxonomy.filter { taxonomySkill ->
            val skillTokens = taxonomySkill.split(" ")
            tokens.windowed(skillTokens.size, 1, partialWindows = false)
                .any { window -> window == skillTokens }
        }

        val required = mutableListOf<String>()
        val preferred = mutableListOf<String>()

        for (skill in matchedSkills) {
            val skillTokens = skill.split(" ")
            val skillIndex = tokens.windowed(skillTokens.size, 1, partialWindows = false)
                .indexOfFirst { it == skillTokens }
            if (skillIndex == -1) {
                required.add(skill)
                continue
            }
            val precedingWindow = if (skillIndex > 5) tokens.subList(skillIndex - 5, skillIndex) else tokens.subList(0, skillIndex)
            val windowText = precedingWindow.joinToString(" ")
            when {
                preferredMarkers.any { windowText.contains(it) } -> preferred.add(skill)
                else -> required.add(skill)
            }
        }

        val requiredYears = experiencePattern.find(jobDescription)?.groupValues?.get(1)?.toIntOrNull()

        val extractedQualifications = mutableListOf<RequirementEvidence>()
        val extractedUnmapped = mutableListOf<RequirementEvidence>()

        val phrases = jobDescription.split(Regex("[.!?;,/\\n\\r]+|\\s+and\\s+|\\s+or\\s+"))
            .map { it.trim() }
            .filter { it.length >= 3 }

        for (phrase in phrases) {
            if (phrase.length > 150) {
                extractedUnmapped.add(RequirementEvidence(
                    originalText = phrase,
                    category = RequirementCategory.UNCLASSIFIED,
                    classificationStatus = ClassificationStatus.UNMAPPED,
                    matchedTaxonomySkill = null
                ))
                continue
            }

            val category = classifyRequirement(phrase)
            if (category == RequirementCategory.UNCLASSIFIED) continue

            val isSupported = category in setOf(
                RequirementCategory.DEGREE,
                RequirementCategory.CERTIFICATION,
                RequirementCategory.LICENCE,
                RequirementCategory.SECURITY_CLEARANCE
            )
            val status = if (isSupported) ClassificationStatus.NORMALIZED else ClassificationStatus.UNMAPPED

            val evidence = RequirementEvidence(
                originalText = phrase,
                category = category,
                classificationStatus = status,
                matchedTaxonomySkill = null
            )

            if (isSupported) extractedQualifications.add(evidence)
            else extractedUnmapped.add(evidence)
        }

        return AdaptedJobInput(
            requiredSkills = required.distinct(),
            preferredSkills = preferred.distinct(),
            requiredQualifications = extractedQualifications,
            requiredYears = requiredYears,
            keywords = (required + preferred).distinct(),
            unmappedRequirements = extractedUnmapped
        )
    }

    private fun matchTaxonomy(extractedSkill: String, taxonomy: Set<String>): String? {
        val normalized = extractedSkill.lowercase().trim()
        if (normalized.isEmpty()) return null

        taxonomy.find { it.lowercase() == normalized }?.let { return it }

        val extractedTokens = normalized.split(" ").filter { it.isNotBlank() }.toSet()

        for (taxSkill in taxonomy) {
            val taxTokens = taxSkill.lowercase().split(" ").filter { it.isNotBlank() }
            if (taxTokens.isNotEmpty() && taxTokens.all { it in extractedTokens }) {
                return taxSkill
            }
        }

        for (taxSkill in taxonomy) {
            val taxTokens = taxSkill.lowercase().split(" ").filter { it.isNotBlank() }.toSet()
            if (extractedTokens.isNotEmpty() && extractedTokens.all { it in taxTokens }) {
                return taxSkill
            }
        }

        return null
    }

    private fun classifyRequirement(skill: String): RequirementCategory {
        val tokens = skill.lowercase().split(" ").filter { it.isNotBlank() }.toSet()
        return when {
            tokens.any { it in setOf("degree", "bachelor", "master", "phd", "diploma", "ba", "bs", "ma", "mba") } -> RequirementCategory.DEGREE
            tokens.any { it in setOf("certification", "certified", "certificate", "cert", "cipp", "cipm", "cdpo", "cisa", "cism", "pmp") } -> RequirementCategory.CERTIFICATION
            tokens.any { it in setOf("year", "years", "experience", "experienced") } -> RequirementCategory.EXPERIENCE
            tokens.any { it in setOf("fluent", "language", "english", "french", "spanish", "german", "mandarin") } -> RequirementCategory.LANGUAGE
            tokens.contains("clearance") || tokens.contains("security") -> RequirementCategory.SECURITY_CLEARANCE
            tokens.any { it in setOf("licence", "license", "licenced", "licensed") } -> RequirementCategory.LICENCE
            tokens.any { it in setOf("travel", "relocation", "relocate") } -> RequirementCategory.TRAVEL
            tokens.any { it in setOf("regulation", "compliance", "legal", "regulatory") } -> RequirementCategory.REGULATION
            tokens.any { it in setOf("physical", "fitness", "lift", "standing", "mobility") } -> RequirementCategory.PHYSICAL_REQUIREMENT
            tokens.any { it in setOf("communication", "leadership", "teamwork", "interpersonal", "collaboration") } || skill.lowercase().contains("problem solving") -> RequirementCategory.SOFT_SKILL
            tokens.any { it in setOf("domain", "industry", "sector") } -> RequirementCategory.DOMAIN_KNOWLEDGE
            else -> RequirementCategory.UNCLASSIFIED
        }
    }

    fun toValidatedJob(
        jobInput: AdaptedJobInput,
        jobTitle: String,
        correlationId: String
    ): ValidatedJob {
        val startMs = System.currentTimeMillis()
        PipelineTrace.entry("JobRequirementAdapter.toValidatedJob", mapOf(
            "correlationId" to correlationId,
            "jobTitle" to jobTitle,
            "requiredSkillCount" to jobInput.requiredSkills.size,
            "unmappedCount" to jobInput.unmappedRequirements.size
        ))

        val seniorityLevel = SeniorityDeriver.derive(jobTitle, jobInput.requiredYears)
        val completedFields = mutableSetOf<String>()
        if (jobInput.requiredSkills.isNotEmpty()) completedFields.add("requiredSkills")
        if (jobTitle.isNotEmpty()) completedFields.add("title")
        if (jobInput.keywords.isNotEmpty()) completedFields.add("keywords")
        if (jobInput.requiredYears != null) completedFields.add("requiredYears")
        if (seniorityLevel != null) completedFields.add("seniorityLevel")

        val isDegraded = determineDegradation(jobInput)

        val result = ValidatedJob(
            correlationId = correlationId,
            requiredSkills = jobInput.requiredSkills,
            requiredQualifications = jobInput.requiredQualifications,
            title = jobTitle,
            keywords = jobInput.keywords,
            requiredYears = jobInput.requiredYears,
            seniorityLevel = seniorityLevel,
            completedFields = completedFields.intersect(RequiredJobFields.fields),
            isAccepted = true,
            isDegraded = isDegraded
        )

        val durationMs = System.currentTimeMillis() - startMs
        PipelineTrace.dataQuality(
            stage = "JobRequirementAdapter.toValidatedJob",
            issue = "VALIDATED_JOB_CREATED",
            details = mapOf(
                "correlationId" to correlationId,
                "fieldCount" to result.completedFields.size,
                "seniorityLevel" to (result.seniorityLevel ?: "null"),
                "totalKeywords" to result.keywords.size,
                "isDegraded" to isDegraded,
                "unmappedRequirements" to jobInput.unmappedRequirements.size
            ),
            correlationId = correlationId
        )
        PipelineTrace.exit("JobRequirementAdapter.toValidatedJob", durationMs, mapOf(
            "correlationId" to correlationId,
            "fieldCount" to result.completedFields.size,
            "isDegraded" to isDegraded,
            "unmappedRequirements" to jobInput.unmappedRequirements.size
        ))

        return result
    }

    private fun determineDegradation(jobInput: AdaptedJobInput): Boolean {
        val hasAnyStructuredRequirement = jobInput.requiredSkills.isNotEmpty() ||
                                          jobInput.preferredSkills.isNotEmpty() ||
                                          jobInput.requiredQualifications.isNotEmpty() ||
                                          jobInput.unmappedRequirements.isNotEmpty()
        return !hasAnyStructuredRequirement
    }
}
