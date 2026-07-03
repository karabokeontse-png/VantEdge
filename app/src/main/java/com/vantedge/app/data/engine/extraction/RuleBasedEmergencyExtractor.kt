package com.vantedge.app.data.engine.extraction

import com.vantedge.app.data.engine.ExtractionValidator
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.data.engine.ValidationSeverity
import com.vantedge.app.data.model.ConfidenceBreakdown
import com.vantedge.app.data.model.Gate0JobReason
import com.vantedge.app.data.model.Gate0JobResult
import com.vantedge.app.data.model.JobExtractionMetrics
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.extraction.ExtractionMethod
import java.util.Locale
import java.util.UUID
import kotlin.math.max

object RuleBasedEmergencyExtractor {
    private const val GATE_0_PASS_THRESHOLD = 2

    private val HEADER_BLACKLIST = listOf(
        "vacancy", "vacancy announcement", "vacancy notice",
        "job vacancy", "position available"
    )

    private val SECTION_HEADER_BLACKLIST = listOf(
        "main purpose of the", "competencies/skills",
        "qualifications/requirements", "key performance areas",
        "duties and responsibilities", "how to apply",
        "terms of service", "remuneration", "benefits"
    )

    private val GATE_0_SIGNALS = listOf(
        "responsibilities", "requirements", "qualifications", "vacancy",
        "closing date", "job description", "about the role", "key duties",
        "experience required", "apply now", "position", "employment type", "salary"
    )

    private val LEGAL_ENTITY_PATTERN = Regex("Ltd|Inc|Pty|Limited|Corp|LLC|University|Institute|College|School", RegexOption.IGNORE_CASE)
    private val TITLE_KEYWORD_PATTERN = Regex("\\b(position|role|vacancy|job title)\\b", RegexOption.IGNORE_CASE)
    private val BRANDING_PATTERN = Regex("we are hiring|join us|about our company", RegexOption.IGNORE_CASE)
    private val LOCATION_PATTERN = Regex("remote|location|based", RegexOption.IGNORE_CASE)

    fun extractJob(rawText: String, sourceType: JobSourceType, correlationId: String? = null): Result<JobExtractionResult> {
        val startTime = System.currentTimeMillis()
        PipelineTrace.warn("RuleBasedEmergencyExtractor", "EMERGENCY FALLBACK INVOKED — no LLM available", correlationId)

        if (rawText.isBlank()) {
            return Result.failure(Exception("EMPTY_DESCRIPTION"))
        }

        val gate0Score = runGate0(rawText)
        val lines = rawText.lines().map { it.trim() }.filter { it.isNotBlank() }
        if (lines.isEmpty()) {
            return Result.failure(Exception("EMPTY_DESCRIPTION"))
        }

        var title = ""
        var company = ""
        var consumedLines = 0

        for ((idx, line) in lines.withIndex()) {
            val lower = line.lowercase(Locale.ROOT)
            if (TITLE_KEYWORD_PATTERN.containsMatchIn(lower)) {
                val headerMatch = HEADER_BLACKLIST.any { lower.trim() == it || lower.trim().startsWith(it) }
                if (!headerMatch) {
                    title = line
                    consumedLines = idx + 1
                    break
                }
            }
        }

        if (title.isBlank() && lines.isNotEmpty()) {
            for ((idx, line) in lines.withIndex()) {
                val lower = line.lowercase(Locale.ROOT)
                val isHeader = HEADER_BLACKLIST.any { lower.trim() == it || lower.trim().startsWith(it) }
                if (!isHeader && line.length < 60) {
                    val upperRatio = line.count { it.isUpperCase() }.toFloat() / line.length
                    if (upperRatio > 0.4f) {
                        title = line
                        consumedLines = idx + 1
                        break
                    }
                }
            }
        }

        if (title.isBlank() && lines.isNotEmpty()) {
            val firstHeaderCheck = HEADER_BLACKLIST.any { lines[0].lowercase(Locale.ROOT).trim() == it }
            title = if (firstHeaderCheck && lines.size > 1) lines[1] else lines[0]
            consumedLines = 1
        }

        for ((idx, line) in lines.withIndex()) {
            if (idx >= consumedLines) {
                val lower = line.lowercase(Locale.ROOT)
                val isSectionHeader = SECTION_HEADER_BLACKLIST.any { lower.startsWith(it) || lower.contains(it) }
                if (!isSectionHeader && LEGAL_ENTITY_PATTERN.containsMatchIn(line)) {
                    company = line
                    consumedLines = max(consumedLines, idx + 1)
                    break
                }
                if (BRANDING_PATTERN.containsMatchIn(lower)) {
                    company = line
                    consumedLines = max(consumedLines, idx + 1)
                    break
                }
            }
        }

        if (company.isBlank() && lines.size > consumedLines) {
            val candidate = lines[consumedLines]
            val isLocation = LOCATION_PATTERN.containsMatchIn(candidate.lowercase(Locale.ROOT))
            val isSectionHeader = SECTION_HEADER_BLACKLIST.any { candidate.lowercase(Locale.ROOT).startsWith(it) }
            if (!isLocation && !isSectionHeader) {
                company = candidate
                consumedLines++
            }
        }

        val description = if (consumedLines < lines.size) {
            lines.drop(consumedLines).joinToString("\n")
        } else {
            rawText.take(3000)
        }

        val result = JobExtractionResult(
            extractionId = UUID.randomUUID().toString(),
            extractedAt = System.currentTimeMillis(),
            jobTitle = title.takeIf { it.isNotBlank() },
            company = company.takeIf { it.isNotBlank() },
            description = description,
            confidenceScore = 0.3f,
            confidenceBreakdown = ConfidenceBreakdown(
                baseScore = 0.3f,
                titleContribution = if (title.isNotBlank()) 0.1f else 0f,
                companyContribution = if (company.isNotBlank()) 0.1f else 0f,
                qualificationContribution = 0f,
                penalties = listOf("EMERGENCY_FALLBACK"),
                finalScore = 0.3f
            ),
            gate0Result = Gate0JobResult(
                score = gate0Score,
                threshold = GATE_0_PASS_THRESHOLD,
                accepted = true,
                reason = Gate0JobReason.ACCEPTED,
                detectedSignals = emptyList(),
                appliedPenalties = emptyList(),
                rejectionCauses = emptyList(),
                narrativeDensity = 0.0f
            ),
            metrics = JobExtractionMetrics(
                durationMs = System.currentTimeMillis() - startTime,
                sourceType = sourceType,
                rawTextLength = rawText.length,
                gate0DurationMs = 0,
                gate1DurationMs = 0,
                gate2DurationMs = 0,
                gate3DurationMs = 0,
                gate4DurationMs = 0,
                qualificationPassed = true,
                narrativeDensity = 0.0f
            )
        )

        val validationResult = ExtractionValidator.validateJob(result)
        if (!validationResult.passed) {
            val blockerFields = validationResult.errors
                .filter { it.severity == ValidationSeverity.BLOCKER }
                .joinToString(", ") { it.field }
            PipelineTrace.warn("RuleBasedEmergencyExtractor", "Validation failure: $blockerFields", correlationId)
        }

        return Result.success(result)
    }

    private fun runGate0(rawText: String): Int {
        val lower = rawText.lowercase(Locale.ROOT)
        var score = 0
        for (signal in GATE_0_SIGNALS) {
            if (signal in lower) score++
        }
        return max(score, 0)
    }
}
