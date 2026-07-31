package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.Certification
import com.vantedge.app.data.model.UserProfile

data class ExcludedToken(
    val token: String,
    val reason: String,
    val ruleId: String
)

object ProfileSanitizer {

    private val MIN_SKILL_LENGTH = 1
    private val MAX_SKILL_LENGTH = 80
    private val MIN_ALPHA_RATIO = 0.4

    private val tokenRegex = Regex("""[\s,/\n]+""")

    fun sanitize(profile: UserProfile): SanitizationResult {
        val audit = mutableListOf<SanitizationAuditEntry>()
        val excluded = mutableListOf<ExcludedToken>()

        // R1: Filter blank/empty skills
        val nonBlankSkills = profile.skills.filter { it.isNotBlank() }

        // R2: Filter skills outside length bounds
        val lengthFiltered = nonBlankSkills.filter { skill ->
            val valid = skill.length in MIN_SKILL_LENGTH..MAX_SKILL_LENGTH
            if (!valid) {
                excluded.add(ExcludedToken(skill, "Length out of bounds", "R2"))
                audit.add(
                    SanitizationAuditEntry(
                        originalValue = skill,
                        normalizedValue = "",
                        ruleId = "R2",
                        reason = "skill length ${skill.length} outside [$MIN_SKILL_LENGTH, $MAX_SKILL_LENGTH]",
                        confidence = "LOW_CONFIDENCE"
                    )
                )
            }
            valid
        }

        // R2b: Filter low alphabetic ratio (OCR garbled tokens)
        val alphaFiltered = lengthFiltered.filter { skill ->
            val alphaRatio = skill.count { it.isLetter() }.toDouble() / skill.length.coerceAtLeast(1)
            val valid = alphaRatio >= MIN_ALPHA_RATIO
            if (!valid) {
                excluded.add(ExcludedToken(skill, "OCR artifact (low alphabetic ratio)", "R2b"))
                audit.add(
                    SanitizationAuditEntry(
                        originalValue = skill,
                        normalizedValue = "",
                        ruleId = "R2b",
                        reason = "OCR artifact — alphabetic ratio $alphaRatio below $MIN_ALPHA_RATIO",
                        confidence = "LOW_CONFIDENCE"
                    )
                )
            }
            valid
        }

        // R5-skill: OCR artifact detection and fuzzy repair via ExtractionValidator
        val validatedSkills = mutableListOf<String>()
        alphaFiltered.forEach { skill ->
            val phaseA = ExtractionValidator.validate(skill)
            if (phaseA.verdict == AssessmentVerdict.ACCEPTED) {
                validatedSkills.add(skill)
                audit.add(
                    SanitizationAuditEntry(
                        originalValue = skill,
                        normalizedValue = skill,
                        ruleId = "R5_PASS",
                        reason = "skill retained",
                        confidence = "HIGH"
                    )
                )
            } else {
                val words = skill.split(tokenRegex).filter { it.isNotBlank() }
                val assessments = words.map { word -> word to ExtractionValidator.validate(word) }

                val rejected = assessments.firstOrNull { it.second.verdict == AssessmentVerdict.REJECTED }
                if (rejected != null) {
                    excluded.add(ExcludedToken(skill, rejected.second.reason, "R5"))
                    audit.add(
                        SanitizationAuditEntry(
                            originalValue = skill,
                            normalizedValue = "",
                            ruleId = "R5",
                            reason = rejected.second.reason,
                            confidence = "REJECTED"
                        )
                    )
                } else {
                    val repaired = assessments.firstOrNull { it.second.verdict == AssessmentVerdict.REPAIRED }
                    if (repaired != null) {
                        val reconstructed = words.mapIndexed { index, word ->
                            val assessment = assessments[index].second
                            if (assessment.verdict == AssessmentVerdict.REPAIRED && assessment.optionalSuggestion != null) {
                                applyCasing(word, assessment.optionalSuggestion)
                            } else {
                                word
                            }
                        }.joinToString(" ")
                        validatedSkills.add(reconstructed)
                        audit.add(
                            SanitizationAuditEntry(
                                originalValue = skill,
                                normalizedValue = reconstructed,
                                ruleId = "R5_REPAIRED",
                                reason = "Suggested repair applied",
                                confidence = "MEDIUM"
                            )
                        )
                    } else {
                        val lowConfidence = assessments.firstOrNull { it.second.verdict == AssessmentVerdict.LOW_CONFIDENCE }
                        if (lowConfidence != null) {
                            validatedSkills.add(skill)
                            audit.add(
                                SanitizationAuditEntry(
                                    originalValue = skill,
                                    normalizedValue = skill,
                                    ruleId = "R5_LOW_CONFIDENCE",
                                    reason = lowConfidence.second.reason,
                                    confidence = "LOW_CONFIDENCE"
                                )
                            )
                        } else {
                            validatedSkills.add(skill)
                            audit.add(
                                SanitizationAuditEntry(
                                    originalValue = skill,
                                    normalizedValue = skill,
                                    ruleId = "R5_PASS",
                                    reason = "skill retained",
                                    confidence = "HIGH"
                                )
                            )
                        }
                    }
                }
            }
        }

        // R3: Deterministic case-insensitive deduplication
        val skillMap = linkedMapOf<String, String>()
        validatedSkills.forEach { skill ->
            val key = skill.lowercase()
            if (!skillMap.containsKey(key)) {
                skillMap[key] = skill
            } else {
                audit.add(
                    SanitizationAuditEntry(
                        originalValue = skill,
                        normalizedValue = skillMap[key] ?: skill,
                        ruleId = "R3",
                        reason = "duplicate collapsed",
                        confidence = "HIGH"
                    )
                )
            }
        }
        val dedupedSkills = skillMap.values.toList()

        // R4: Filter OCR artifacts and apply repairs to certifications
        val cleanCertifications = mutableListOf<String>()
        profile.certifications.forEach { cert ->
            val assessment = ExtractionValidator.validate(cert.name)
            when (assessment.verdict) {
                AssessmentVerdict.REJECTED -> {
                    excluded.add(ExcludedToken(cert.name, assessment.reason, "R5"))
                    audit.add(
                        SanitizationAuditEntry(
                            originalValue = cert.name,
                            normalizedValue = "",
                            ruleId = "R5",
                            reason = assessment.reason,
                            confidence = "REJECTED"
                        )
                    )
                }
                AssessmentVerdict.REPAIRED -> {
                    val reconstructed = assessment.optionalSuggestion?.let { applyCasing(cert.name, it) } ?: cert.name
                    cleanCertifications.add(reconstructed)
                    audit.add(
                        SanitizationAuditEntry(
                            originalValue = cert.name,
                            normalizedValue = reconstructed,
                            ruleId = "R5_REPAIRED",
                            reason = "Suggested repair applied",
                            confidence = "MEDIUM"
                        )
                    )
                }
                AssessmentVerdict.LOW_CONFIDENCE -> {
                    cleanCertifications.add(cert.name)
                    audit.add(
                        SanitizationAuditEntry(
                            originalValue = cert.name,
                            normalizedValue = cert.name,
                            ruleId = "R5_LOW_CONFIDENCE",
                            reason = assessment.reason,
                            confidence = "LOW_CONFIDENCE"
                        )
                    )
                }
                AssessmentVerdict.ACCEPTED -> {
                    cleanCertifications.add(cert.name)
                    audit.add(
                        SanitizationAuditEntry(
                            originalValue = cert.name,
                            normalizedValue = cert.name,
                            ruleId = "R5_PASS",
                            reason = "certification retained",
                            confidence = "HIGH"
                        )
                    )
                }
            }
        }

        // TODO: overlapping employment periods are not de-duplicated in current P0 model

        return SanitizationResult(
            skills = dedupedSkills,
            certifications = cleanCertifications,
            audit = SanitizationAudit(audit),
            excluded = excluded
        )
    }

    fun applyCasing(original: String, suggestion: String): String {
        if (original.all { it.isUpperCase() }) return suggestion.uppercase()
        if (original.firstOrNull()?.isUpperCase() == true) return suggestion.replaceFirstChar { it.uppercase() }
        return suggestion
    }
}

data class SanitizationResult(
    val skills: List<String>,
    val certifications: List<String>,
    val audit: SanitizationAudit,
    val excluded: List<ExcludedToken>
)
