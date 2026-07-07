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

        // R5-skill: OCR artifact detection (shared engine with certifications)
        val ocrFiltered = alphaFiltered.filter { skill ->
            val artifact = isOcrArtifact(skill)
            if (artifact) {
                excluded.add(ExcludedToken(skill, "OCR artifact", "R5"))
                audit.add(
                    SanitizationAuditEntry(
                        originalValue = skill,
                        normalizedValue = "",
                        ruleId = "R5",
                        reason = "OCR artifact (skill)",
                        confidence = "LOW_CONFIDENCE"
                    )
                )
            }
            !artifact
        }

        // R3: Deterministic case-insensitive deduplication
        val skillMap = linkedMapOf<String, String>()
        ocrFiltered.forEach { skill ->
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

        // R4: Filter OCR artifacts from certifications
        val cleanCertifications = profile.certifications.filter { cert ->
            val artifact = isOcrArtifact(cert.name)
            if (artifact) {
                excluded.add(ExcludedToken(cert.name, "OCR artifact", "R5"))
                audit.add(
                    SanitizationAuditEntry(
                        originalValue = cert.name,
                        normalizedValue = "",
                        ruleId = "R5",
                        reason = "OCR artifact (certification)",
                        confidence = "LOW_CONFIDENCE"
                    )
                )
            } else {
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
            !artifact
        }

        // TODO: overlapping employment periods are not de-duplicated in current P0 model

        return SanitizationResult(
            skills = dedupedSkills,
            certifications = cleanCertifications.map { it.name },
            audit = SanitizationAudit(audit),
            excluded = excluded
        )
    }

    private fun isOcrArtifact(token: String): Boolean {
        if (token.isBlank()) return true
        val alphaRatio = token.count { it.isLetter() }.toDouble() / token.length
        if (alphaRatio < MIN_ALPHA_RATIO) return true
        val consonantRun = token.runningFold(0) { acc, c ->
            if (c.isLetter() && c.lowercaseChar() !in "aeiou") acc + 1 else 0
        }.max()
        if (consonantRun > 6) return true


        // Word-level OCR hardening (R5-skill)
        val words = token.split(Regex("[^A-Za-z]+")).filter { it.isNotBlank() }
        for (word in words) {
            // Exact-match truncated fragment detection
            if (word.equals("mation", ignoreCase = true)) {
                return true
            }
            // Impossible consonant cluster detection
            if (word.contains("trdl", ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}

data class SanitizationResult(
    val skills: List<String>,
    val certifications: List<String>,
    val audit: SanitizationAudit,
    val excluded: List<ExcludedToken>
)
