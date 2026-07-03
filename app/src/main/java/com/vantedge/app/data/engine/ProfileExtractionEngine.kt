package com.vantedge.app.data.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.vantedge.app.data.model.*
import com.vantedge.app.data.engine.extraction.JsonExtractionEngine
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.AiRequest
import com.vantedge.app.util.HashUtils
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.app.util.TelemetryCollector
import com.vantedge.pipeline.validation.P2ValidationEngine
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.zip.ZipInputStream

// ==========================================
// GATE 0 ENUMS & METADATA
// ==========================================
// CONSTRUCTOR CHANGE (Phase 3 — Telemetry)
// Added: telemetryCollector: TelemetryCollector
//
// Callers must update instantiation to pass a TelemetryCollector.
// No other constructor behaviour changed.
// ==========================================

class ProfileExtractionEngine(
    private val context: Context,
    private val aiGateway: AiGateway,
    private val telemetryCollector: TelemetryCollector
) {

    companion object {
        private const val TAG = "ProfileExtractionEngine"

        // GATE 0 THRESHOLD: PROVISIONAL
        // Current value: 3
        // Calibration rule: Do not adjust until ≥50 real-world samples logged.
        // Evidence required: Signal distribution plots showing clear separation.
        private const val GATE_0_PASS_THRESHOLD = 3

        // ==========================================
        // SINGLE AUTHORITATIVE TRUNCATION BOUNDARY
        // All pipeline stages (Gate 0, callAi, Gate 2, Gate 3)
        // operate on the same truncated input. Retries also
        // re-enter structureProfile() with identical input.
        // ==========================================
        private const val MAX_INPUT_LENGTH = 12000

        // ==========================================
        // TIMEOUT CONSTANTS (PROVISIONAL)
        // Calibration rule: Do not adjust until ≥50 real-world
        // samples logged showing timeout rate distribution.
        // ==========================================
        private const val PDF_TEXT_TIMEOUT_MS = 15_000L
        private const val OCR_TIMEOUT_MS = 30_000L

        // ==========================================
        // DETERMINISTIC ERROR CODES
        // All pipeline failures at extractRawText boundary
        // must map to one of these.
        // ==========================================
        private const val ERROR_PDF_TEXT_TIMEOUT = "PDF_TEXT_TIMEOUT"
        private const val ERROR_OCR_TIMEOUT = "OCR_TIMEOUT"
        private const val ERROR_FILE_READ = "FILE_READ_ERROR"
        private const val ERROR_EMPTY_DOCUMENT = "EMPTY_DOCUMENT"

        // Gate 1 — CV keyword vocabulary
        private val CV_KEYWORDS = listOf(
            "experience", "work", "education", "skills",
            "employment", "projects", "certification", "resume", "cv",
            "profile", "summary"
        )

        // Gate 0 — Structural section anchors (exact string matching, no regex)
        private val CV_SECTION_HEADERS = listOf(
            "experience", "education", "skills", "work history",
            "projects", "certifications", "qualifications"
        )

        // Gate 0 — Chronology patterns (bounded to date tokens only)
        private val YEAR_PATTERN = Regex("""\b(19|20)\d{2}\b""")
        private val MONTH_YEAR_PATTERN = Regex(
            """\b(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\.?\s*(19|20)\d{2}\b""",
            RegexOption.IGNORE_CASE
        )

        // Gate 0 — Identity patterns (applied to first 15 lines only)
        private val EMAIL_PATTERN = Regex("""[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}""")
        private val PHONE_PATTERN = Regex("""(\+?\d[\d\s\-().]{7,}\d)""")
        private val URL_PATTERN = Regex("""https?://\S+|www\.\S+|linkedin\.com\S*""", RegexOption.IGNORE_CASE)
    }

    // ==========================================
    // RAW TEXT EXTRACTION
    // ==========================================

    suspend fun extractRawText(uri: Uri): Result<String> =
        withContext(Dispatchers.IO) {
            val rawStartMs = System.currentTimeMillis()
            PipelineTrace.entry("extractRawText", mapOf("uri" to uri.toString()))
            var extractionRoute = "UNKNOWN"

            try {
                val mime = context.contentResolver.getType(uri) ?: ""
                val fileName = getFileName(uri)

                val text = when {
                    mime.contains("pdf", true) || fileName.endsWith(".pdf", true) -> {
                        extractionRoute = "PDF_TEXT"
                        try {
                            val parsed = extractPdf(uri)
                            if (parsed.isBlank()) {
                                extractionRoute = "PDF_OCR"
                                Log.w(TAG, "DEBUG: [extractRawText] PDF extraction blank → ML Kit OCR fallback")
                                extractPdfOcr(uri)
                            } else parsed
                        } catch (e: TimeoutCancellationException) {
                            extractionRoute = "PDF_TEXT_TIMEOUT"
                            PipelineTrace.error("extractRawText", ERROR_PDF_TEXT_TIMEOUT, e)
                            return@withContext Result.failure(Exception(ERROR_PDF_TEXT_TIMEOUT))
                        }
                    }
                    mime.contains("word", true) ||
                            mime.contains("officedocument", true) ||
                            fileName.endsWith(".docx", true) ||
                            fileName.endsWith(".doc", true) -> {
                        extractionRoute = "DOCX"
                        extractDocx(uri)
                    }
                    mime.startsWith("image/", true) ||
                            fileName.endsWith(".png", true) ||
                            fileName.endsWith(".jpg", true) ||
                            fileName.endsWith(".jpeg", true) -> {
                        extractionRoute = "IMAGE_OCR"
                        extractImageOcr(uri)
                    }
                    else -> {
                        extractionRoute = "PLAIN"
                        extractPlain(uri)
                    }
                }

                if (text.isBlank()) {
                    PipelineTrace.exit("extractRawText", System.currentTimeMillis() - rawStartMs, mapOf(
                        "status" to "FAILURE",
                        "reason" to ERROR_EMPTY_DOCUMENT,
                        "method" to extractionRoute
                    ))
                    return@withContext Result.failure(Exception(ERROR_EMPTY_DOCUMENT))
                }

                PipelineTrace.exit("extractRawText", System.currentTimeMillis() - rawStartMs, mapOf(
                    "status" to "SUCCESS",
                    "charCount" to text.length,
                    "method" to extractionRoute
                ))
                Result.success(text)

            } catch (e: TimeoutCancellationException) {
                PipelineTrace.error("extractRawText", ERROR_PDF_TEXT_TIMEOUT, e)
                Result.failure(Exception(ERROR_PDF_TEXT_TIMEOUT))
            } catch (e: Exception) {
                val errorCode = when (e.message) {
                    ERROR_OCR_TIMEOUT -> ERROR_OCR_TIMEOUT
                    else -> ERROR_FILE_READ
                }
                Log.e(TAG, "extractRawText failed: $errorCode", e)
                PipelineTrace.error("extractRawText", errorCode, e)
                Result.failure(Exception(errorCode))
            }
        }

    // ==========================================
    // STRUCTURED EXTRACTION PIPELINE
    // ==========================================

    // sessionId: String = "" — ViewModel-scoped UUID passed for telemetry correlation.
    // Defaults to "" so existing callers without sessionId continue to compile.
    suspend fun structureProfile(
        rawText: String,
        extractionMode: ExtractionMode = ExtractionMode.PDF_TEXT,
        sessionId: String = "",
        onProgress: (String) -> Unit = {}
    ): Result<StructuredProfileExtraction> {

        if (rawText.isBlank()) {
            return Result.failure(Exception("EMPTY_TEXT"))
        }

        // ==========================================
        // SINGLE AUTHORITATIVE TRUNCATION BOUNDARY
        // All downstream stages operate on inputText.
        // ==========================================
        val inputText = rawText.take(MAX_INPUT_LENGTH)
        val pipelineStartMs = System.currentTimeMillis()
        PipelineTrace.entry("structureProfile", mapOf(
            "textLength" to rawText.length,
            "inputLength" to inputText.length,
            "mode" to extractionMode.name,
            "sessionId" to sessionId
        ))

        // ==========================================
        // GATE 0: POSITIVE CV STRUCTURE CLASSIFICATION
        // Transparent, per-signal instrumentation.
        // Threshold: score >= 3 to proceed.
        // ==========================================
        val gate0Result = runGate0(inputText, extractionMode)

        // ==========================================
        // TELEMETRY: Emit at Gate 0 completion.
        //
        // Emitted for BOTH accepted and rejected documents.
        // record() is synchronous (returns Unit); async dispatch
        // is handled internally by TelemetryCollector.
        // Deduplication by documentHash is handled by the collector.
        // ==========================================
        val documentHash = HashUtils.sha256(rawText)
        telemetryCollector.record(
            TelemetryRecord(
                documentHash = documentHash,
                sessionId = sessionId,
                timestampMs = System.currentTimeMillis(),
                gate0Score = gate0Result.score,
                gate0Threshold = gate0Result.threshold,
                gate0Accepted = gate0Result.accepted,
                gate0Reason = gate0Result.reason.name,
                extractionMode = gate0Result.extractionMode.name
            )
        )

        if (!gate0Result.accepted) {
            Log.i(TAG, "[Gate0] REJECTED — reason=${gate0Result.reason}, score=${gate0Result.score}, threshold=${gate0Result.threshold}")
            PipelineTrace.exit("gate0", System.currentTimeMillis() - pipelineStartMs, mapOf(
                "accepted" to false,
                "reason" to gate0Result.reason.name,
                "score" to gate0Result.score
            ))
            return Result.failure(Exception("NOT_A_CV"))
        }
        PipelineTrace.exit("gate0", System.currentTimeMillis() - pipelineStartMs, mapOf(
            "accepted" to true,
            "reason" to gate0Result.reason.name,
            "score" to gate0Result.score
        ))

        // ==========================================
        // GATE 1: DOCUMENT CLASSIFICATION (keyword check)
        // ==========================================
        val keywordScore = CV_KEYWORDS.count { inputText.lowercase().contains(it) }
        if (keywordScore < 2) {
            Log.e(TAG, "DEBUG: [Gate 1] Document rejected. Minimal CV keywords detected.")
            return Result.failure(Exception("NOT_A_CV"))
        }

        return try {
            onProgress("Analyzing document...")

            val result = callAi(inputText, onProgress)

            // ==========================================
            // GATE 3: STRICT PARSE VALIDATION
            // ==========================================
            val validationResult = ExtractionValidator.validateProfile(result)
            if (!validationResult.passed) {
                val blockerFields = validationResult.errors
                    .filter { it.severity == ValidationSeverity.BLOCKER }
                    .joinToString(", ") { it.field }
                Log.e(TAG, "DEBUG: [Gate 3] Validation failed. Blockers: $blockerFields")
                return Result.failure(Exception("VALIDATION_FAILED: $blockerFields"))
            }

            val p2ProfileResult = P2ValidationEngine.validateProfileExtraction(result, sessionId)
            PipelineTrace.dataQuality("P2Validation", "P2_DECISION", mapOf(
                "correlationId" to sessionId,
                "orchestrator" to "ProfileExtractionEngine",
                "decision" to p2ProfileResult.decision.javaClass.simpleName
            ), sessionId)
            when (p2ProfileResult.decision) {
                is com.vantedge.pipeline.validation.ValidationDecision.Degraded -> {
                    Log.w(TAG, "[P2] Profile degraded: ${p2ProfileResult.decision.warnings}")
                }
                else -> {}
            }

            val enrichedResult = if (validationResult.warnings.isNotEmpty()) {
                val warningMessages = validationResult.warnings.map { it.message }
                result.copy(warnings = (result.warnings + warningMessages).toImmutableList())
            } else result

            val durationMs = System.currentTimeMillis() - pipelineStartMs
            PipelineTrace.exit("structureProfile", durationMs, mapOf(
                "status" to "SUCCESS",
                "skills" to result.skills.size,
                "workHistory" to result.workHistory.size,
                "education" to result.education.size,
                "overallConfidence" to result.overallConfidence
            ))
            Result.success(enrichedResult)

        } catch (e: Exception) {
            val durationMs = System.currentTimeMillis() - pipelineStartMs
            PipelineTrace.error("structureProfile", e.message ?: "AI_EXTRACTION_FAILED", e)
            Log.e(TAG, "structureProfile failed: ${e.message}", e)
            Result.failure(Exception(e.message ?: "AI_EXTRACTION_FAILED"))
        }
    }

    // ==========================================
    // GATE 0: TRANSPARENT STRUCTURAL ANALYSIS
    //
    // Five independent signals. Each computed and
    // logged independently. No cross-signal dependencies.
    //
    // REASON RESOLUTION PRIORITY (first-match wins):
    // 1. HIGH_NARRATIVE_DENSITY
    // 2. NO_SECTIONAL_STRUCTURE
    // 3. NO_CHRONOLOGY
    // 4. OCR_TOO_FRAGMENTED
    // 5. LOW_STRUCTURAL_EVIDENCE
    // ==========================================

    private fun runGate0(rawText: String, extractionMode: ExtractionMode): Gate0Result {

        // SANITIZATION BOUNDARY: OCR artifact cleanup ONLY.
        // Allowed:
        // - whitespace normalization
        // - soft-hyphen removal
        // - empty punctuation-line stripping
        //
        // Forbidden:
        // - sentence reconstruction
        // - paragraph merging
        // - semantic repair
        //
        // Gate 0 classifies raw document structure,
        // not an idealized reconstruction.
        val text = sanitizeForGate0(rawText)
        val lines = text.lines()
        val nonEmptyLines = lines.filter { it.isNotBlank() }
        val totalLines = lines.size
        val lower = text.lowercase()

        Log.i(TAG, "[Gate0] ===== STRUCTURAL ANALYSIS =====")

        // ------------------------------------------
        // Signal 1: StructuralSections
        // Canonical resume headings — exact/case-insensitive string match.
        // Pure string matching. No regex.
        // Contribution: +2 if matches >= 2
        // ------------------------------------------
        val sectionMatches = CV_SECTION_HEADERS.count { header -> lower.contains(header) }
        val sectionContribution = if (sectionMatches >= 2) 2 else 0
        Log.i(TAG, "[Gate0] StructuralSections: matches=$sectionMatches, contribution=${if (sectionContribution > 0) "+$sectionContribution" else "0"}")

        // ------------------------------------------
        // Signal 2: ChronologyDensity
        // Count of lines containing year or month-year pattern.
        // Regex isolated to date tokens only. No contextual parsing.
        // Contribution: +2 if matches >= 2
        // ------------------------------------------
        val yearMatches = YEAR_PATTERN.findAll(lower).count()
        val monthYearMatches = MONTH_YEAR_PATTERN.findAll(lower).count()
        val chronologyMatches = yearMatches + monthYearMatches
        val chronologyContribution = if (chronologyMatches >= 2) 2 else 0
        Log.i(TAG, "[Gate0] ChronologyDensity: matches=$chronologyMatches, contribution=${if (chronologyContribution > 0) "+$chronologyContribution" else "0"}")

        // ------------------------------------------
        // Signal 3: IdentitySignals
        // Presence of email OR phone OR URL in first 15 lines only.
        // Stop after first successful detection.
        // Contribution: +1 if >= 1
        // ------------------------------------------
        val openingLines = lines.take(15).joinToString("\n")
        val identityMatches: Int
        val identityContribution: Int
        if (EMAIL_PATTERN.containsMatchIn(openingLines) ||
            PHONE_PATTERN.containsMatchIn(openingLines) ||
            URL_PATTERN.containsMatchIn(openingLines)
        ) {
            identityMatches = 1
            identityContribution = 1
        } else {
            identityMatches = 0
            identityContribution = 0
        }
        Log.i(TAG, "[Gate0] IdentitySignals: matches=$identityMatches, contribution=${if (identityContribution > 0) "+$identityContribution" else "0"}")

        // ------------------------------------------
        // Signal 4: LayoutFragmentation
        // Ratio of short lines (length <= 35) to total lines.
        // Length-based only. No regex.
        // Contribution: +1 if ratio >= 0.5
        // ------------------------------------------
        val shortLineCount = lines.count { it.trim().length in 1..35 }
        val layoutRatio = if (totalLines > 0) shortLineCount.toFloat() / totalLines else 0f
        val layoutContribution = if (layoutRatio >= 0.5f) 1 else 0
        val layoutRatioFormatted = "%.2f".format(layoutRatio)
        Log.i(TAG, "[Gate0] LayoutFragmentation: ratio=$layoutRatioFormatted, contribution=${if (layoutContribution > 0) "+$layoutContribution" else "0"}")

        // ------------------------------------------
        // Signal 5: NarrativeDensity (penalty)
        // Ratio of lines with length > 60 to total non-empty lines.
        // Lightweight penalty only. Must never dominate acceptance logic.
        // Penalty: -1 if ratio > 0.6
        // ------------------------------------------
        val longLineCount = nonEmptyLines.count { it.trim().length > 60 }
        val narrativeRatio = if (nonEmptyLines.isNotEmpty()) longLineCount.toFloat() / nonEmptyLines.size else 0f
        val narrativePenalty = if (narrativeRatio > 0.6f) -1 else 0
        val avgLineLength = if (nonEmptyLines.isNotEmpty())
            nonEmptyLines.sumOf { it.trim().length } / nonEmptyLines.size
        else 0
        Log.i(TAG, "[Gate0] NarrativeDensity: avgLineLength=$avgLineLength, penalty=${if (narrativePenalty < 0) narrativePenalty else "0"}")

        // ------------------------------------------
        // Score aggregation
        // ------------------------------------------
        val rawScore = sectionContribution + chronologyContribution +
                identityContribution + layoutContribution + narrativePenalty
        val score = maxOf(rawScore, 0)
        val accepted = score >= GATE_0_PASS_THRESHOLD

        // ------------------------------------------
        // Reason resolution (first-match wins)
        // ------------------------------------------
        val reason = when {
            accepted -> Gate0Reason.ACCEPTED
            narrativePenalty < 0 && narrativeRatio > 0.6f && sectionContribution == 0 ->
                Gate0Reason.HIGH_NARRATIVE_DENSITY
            sectionContribution == 0 -> Gate0Reason.NO_SECTIONAL_STRUCTURE
            chronologyContribution == 0 -> Gate0Reason.NO_CHRONOLOGY
            extractionMode == ExtractionMode.OCR && layoutRatio < 0.2f ->
                Gate0Reason.OCR_TOO_FRAGMENTED
            else -> Gate0Reason.LOW_STRUCTURAL_EVIDENCE
        }

        val decision = if (accepted) "ACCEPT" else "REJECT"
        Log.i(TAG, "[Gate0] FINAL: total=$score, threshold=$GATE_0_PASS_THRESHOLD, decision=$decision, reason=$reason, mode=$extractionMode")

        PipelineTrace.dataQuality("gate0", "score", mapOf(
            "score" to score,
            "rawScore" to rawScore,
            "threshold" to GATE_0_PASS_THRESHOLD,
            "accepted" to accepted,
            "reason" to reason.name,
            "extractionMode" to extractionMode.name
        ))

        return Gate0Result(
            score = score,
            threshold = GATE_0_PASS_THRESHOLD,
            accepted = accepted,
            reason = reason,
            extractionMode = extractionMode
        )
    }

    // ==========================================
    // GATE 0 SANITIZATION
    // Bounded OCR artifact cleanup only.
    // No semantic repair. No paragraph merging.
    // ==========================================

    private fun sanitizeForGate0(raw: String): String {
        return raw
            .replace("\u00AD", "")          // Remove soft hyphens
            .lines()
            .map { line ->
                line
                    .replace(Regex("\\s+"), " ")   // Normalize whitespace within line
                    .trim()
            }
            .filter { line ->
                // Strip lines that are purely punctuation/symbols with no alphanumeric content
                line.isEmpty() || line.any { it.isLetterOrDigit() }
            }
            .joinToString("\n")
    }

// ==========================================
// AI CALL + STRUCTURAL INTEGRITY GATE
// ==========================================

    private suspend fun callAi(
        rawText: String,
        onProgress: (String) -> Unit
    ): StructuredProfileExtraction {

        onProgress("Extracting structured data...")

        val systemPrompt = """
You are a precise CV extraction engine. Return ONLY valid JSON — no markdown, no explanations, no code fences.

Schema (extract ONLY verbatim — never invent, infer, or expand):
{
  "name": "Full name exactly as written",
  "email": "Email address if present",
  "phone": "Phone number if present",
  "summary": "Professional summary (max 500 chars)",
  "skills": ["Only skills explicitly listed — never invent or infer"],
  "workHistory": [
    {
      "jobTitle": "Title exactly as written",
      "company": "Company exactly as written",
      "startDate": "Start exactly as written (month/year)",
      "endDate": "End exactly as written, or empty if current",
      "description": "Description exactly as written"
    }
  ],
  "education": [
    {
      "institution": "School exactly as written",
      "qualification": "Degree exactly as written",
      "fieldOfStudy": "Field exactly as written",
      "graduationYear": "Year exactly as written"
    }
  ],
  "certifications": [
    {
      "name": "Name exactly as written",
      "issuer": "Issuer exactly as written"
    }
  ]
}

STRICT RULES:
- Extract ONLY values explicitly present in the input text
- NEVER invent, infer, or expand abbreviations or generic terms
- If a skill says "cloud platforms" record ONLY "cloud platforms" — do NOT list AWS, GCP, Azure
- NEVER guess company size, seniority level, or industry
- NEVER add phrases like "I assume", "likely", "probably", "presumably"
- Use "" for missing strings, [] for missing arrays
- Never omit any key
        """.trimIndent()

        val userPrompt = "Extract this CV following the schema and rules above. Never invent content.\n\nCV:\n$rawText"
        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt)
        val response = aiGateway.generate("profile_extraction", request, 120_000L)

        if (response.isNullOrBlank()) {
            throw Exception("EMPTY_AI_RESPONSE")
        }

        val extractionResult = JsonExtractionEngine.extract(response)
        if (!extractionResult.success) {
            throw Exception(extractionResult.failureReason ?: "NO_JSON_FOUND")
        }

        val jsonStr = extractionResult.content

        // ==========================================
        // GATE 2: STRUCTURAL INTEGRITY & SAFE REPAIR
        // ==========================================
        val repairResult = JsonRepairUtil.repair(jsonStr)
        if (!repairResult.isSafe) {
            Log.e(TAG, "DEBUG: [Gate 2] Integrity failed. JSON heavily truncated (over-repair risk).")
            throw Exception("JSON_STRUCTURAL_CORRUPTION")
        }

        return try {
            val rawObj = JSONObject(repairResult.json)
            val normalizedObj = normalizeRootKeys(rawObj)
            parse(normalizedObj)
        } catch (e: Exception) {
            Log.e(TAG, "JSON parse/mapping failed", e)
            throw Exception("PARSE_ERROR")
        }
    }

    // ==========================================
    // NORMALIZATION LAYER
    // ==========================================

    private fun normalizeRootKeys(json: JSONObject): JSONObject {
        val n = JSONObject()

        n.put("name", json.optString("name", json.optString("fullName", "")))
        n.put("email", json.optString("email", json.optString("emailAddress", "")))
        n.put("phone", json.optString("phone", json.optString("phoneNumber", "")))
        n.put("summary", json.optString("summary", json.optString("profile", json.optString("about", ""))))

        val skillsArray = json.optJSONArray("skills")
            ?: json.optJSONArray("core_skills")
            ?: JSONArray()
        n.put("skills", skillsArray)

        val workArray = json.optJSONArray("workHistory")
            ?: json.optJSONArray("experience")
            ?: JSONArray()
        val normalizedWork = JSONArray()
        for (i in 0 until workArray.length()) {
            val w = workArray.optJSONObject(i) ?: continue
            val nw = JSONObject()
            nw.put("jobTitle", w.optString("jobTitle", w.optString("position", w.optString("role", ""))))
            nw.put("company", w.optString("company", w.optString("employer", w.optString("organization", ""))))

            val dates = w.optString("dates", w.optString("period", ""))
            if (dates.isNotBlank() && w.optString("startDate").isBlank()) {
                nw.put("startDate", dates)
                nw.put("endDate", "")
            } else {
                nw.put("startDate", w.optString("startDate", ""))
                nw.put("endDate", w.optString("endDate", ""))
            }

            nw.put("description", w.optString("description",
                w.optString("responsibilities", w.optString("duties", ""))))
            normalizedWork.put(nw)
        }
        n.put("workHistory", normalizedWork)

        val eduArray = json.optJSONArray("education")
            ?: json.optJSONArray("academic")
            ?: JSONArray()
        val normalizedEdu = JSONArray()
        for (i in 0 until eduArray.length()) {
            val e = eduArray.optJSONObject(i) ?: continue
            val ne = JSONObject()
            ne.put("institution", e.optString("institution",
                e.optString("school", e.optString("university", ""))))
            ne.put("qualification", e.optString("qualification", e.optString("degree", "")))
            ne.put("fieldOfStudy", e.optString("fieldOfStudy", e.optString("major", "")))
            ne.put("graduationYear", e.optString("graduationYear", e.optString("year", "")))
            normalizedEdu.put(ne)
        }
        n.put("education", normalizedEdu)

        n.put("certifications", json.optJSONArray("certifications") ?: JSONArray())

        return n
    }

    // ==========================================
    // PARSER
    // ==========================================

    private fun computeFieldConfidence(value: String, fieldName: String): Float {
        if (value.isBlank()) return 0.0f
        var conf = 0.5f
        val normalizedLen = (value.length.coerceAtMost(100)).toFloat() / 100f
        conf += normalizedLen * 0.2f
        when (fieldName) {
            "email" -> if (value.contains("@") && value.contains(".")) conf += 0.3f
            "phone" -> if (value.any { it.isDigit() }) conf += 0.2f
            "startDate", "endDate", "graduationYear" -> if (value.any { it.isDigit() }) conf += 0.1f
        }
        return conf.coerceIn(0.0f, 1.0f)
    }

    private fun parse(json: JSONObject): StructuredProfileExtraction {
        val skillsArray = json.optJSONArray("skills")
        val workArray = json.optJSONArray("workHistory")
        val eduArray = json.optJSONArray("education")
        val certArray = json.optJSONArray("certifications")

        val skills = mutableListOf<ExtractedField>()
        for (i in 0 until (skillsArray?.length() ?: 0)) {
            val skill = skillsArray!!.optString(i)
            if (skill.isNotBlank()) {
                skills.add(ExtractedField(skill, computeFieldConfidence(skill, "skill"), ExtractionSourceType.INFERRED))
            }
        }

        val work = mutableListOf<ExtractedExperience>()
        for (i in 0 until (workArray?.length() ?: 0)) {
            val o = workArray!!.optJSONObject(i) ?: continue
            val title = o.optString("jobTitle")
            val company = o.optString("company")
            if (title.isNotBlank() || company.isNotBlank()) {
                val titleField = ExtractedField(title, computeFieldConfidence(title, "jobTitle"), ExtractionSourceType.INFERRED)
                val companyField = ExtractedField(company, computeFieldConfidence(company, "company"), ExtractionSourceType.INFERRED)
                val startDateStr = o.optString("startDate")
                val endDateStr = o.optString("endDate")
                val descStr = o.optString("description")
                val fieldConfidences = listOf(titleField.confidence, companyField.confidence,
                    if (startDateStr.isNotBlank()) computeFieldConfidence(startDateStr, "startDate") else 0f,
                    if (endDateStr.isNotBlank()) computeFieldConfidence(endDateStr, "endDate") else 0f,
                    if (descStr.isNotBlank()) computeFieldConfidence(descStr, "description") else 0f)
                work.add(
                    ExtractedExperience(
                        jobTitle = titleField,
                        company = companyField,
                        startDate = ExtractedField(startDateStr, computeFieldConfidence(startDateStr, "startDate"), ExtractionSourceType.INFERRED)
                            .takeIf { it.value.isNotBlank() },
                        endDate = ExtractedField(endDateStr, computeFieldConfidence(endDateStr, "endDate"), ExtractionSourceType.INFERRED)
                            .takeIf { it.value.isNotBlank() },
                        description = ExtractedField(descStr, computeFieldConfidence(descStr, "description"), ExtractionSourceType.INFERRED)
                            .takeIf { it.value.isNotBlank() },
                        confidence = if (fieldConfidences.any { it > 0f }) fieldConfidences.average().toFloat() else 0.0f
                    )
                )
            }
        }

        val edu = mutableListOf<ExtractedEducation>()
        for (i in 0 until (eduArray?.length() ?: 0)) {
            val o = eduArray!!.optJSONObject(i) ?: continue
            val inst = o.optString("institution")
            val qual = o.optString("qualification")
            if (inst.isNotBlank() || qual.isNotBlank()) {
                val instField = ExtractedField(inst, computeFieldConfidence(inst, "institution"), ExtractionSourceType.INFERRED)
                val qualField = ExtractedField(qual, computeFieldConfidence(qual, "qualification"), ExtractionSourceType.INFERRED)
                val fieldOfStudyStr = o.optString("fieldOfStudy")
                val gradYearStr = o.optString("graduationYear")
                val fieldConfidences = listOf(instField.confidence, qualField.confidence,
                    if (fieldOfStudyStr.isNotBlank()) computeFieldConfidence(fieldOfStudyStr, "fieldOfStudy") else 0f,
                    if (gradYearStr.isNotBlank()) computeFieldConfidence(gradYearStr, "graduationYear") else 0f)
                edu.add(
                    ExtractedEducation(
                        institution = instField,
                        qualification = qualField,
                        fieldOfStudy = ExtractedField(fieldOfStudyStr, computeFieldConfidence(fieldOfStudyStr, "fieldOfStudy"), ExtractionSourceType.INFERRED)
                            .takeIf { it.value.isNotBlank() },
                        graduationYear = ExtractedField(gradYearStr, computeFieldConfidence(gradYearStr, "graduationYear"), ExtractionSourceType.INFERRED)
                            .takeIf { it.value.isNotBlank() },
                        confidence = if (fieldConfidences.any { it > 0f }) fieldConfidences.average().toFloat() else 0.0f
                    )
                )
            }
        }

        val certs = mutableListOf<ExtractedCertification>()
        for (i in 0 until (certArray?.length() ?: 0)) {
            val o = certArray!!.optJSONObject(i) ?: continue
            val name = o.optString("name")
            if (name.isNotBlank()) {
                val nameField = ExtractedField(name, computeFieldConfidence(name, "name"), ExtractionSourceType.INFERRED)
                val issuerStr = o.optString("issuer")
                val fieldConfidences = listOf(nameField.confidence,
                    if (issuerStr.isNotBlank()) computeFieldConfidence(issuerStr, "issuer") else 0f)
                certs.add(
                    ExtractedCertification(
                        name = nameField,
                        issuer = ExtractedField(issuerStr, computeFieldConfidence(issuerStr, "issuer"), ExtractionSourceType.INFERRED)
                            .takeIf { it.value.isNotBlank() },
                        confidence = if (fieldConfidences.any { it > 0f }) fieldConfidences.average().toFloat() else 0.0f
                    )
                )
            }
        }

        val confidence = when {
            skills.isNotEmpty() && work.isNotEmpty() -> 0.85f
            skills.isNotEmpty() || work.isNotEmpty() || edu.isNotEmpty() -> 0.55f
            else -> 0.2f
        }

        val nameVal = json.optString("name")
        val emailVal = json.optString("email")
        val phoneVal = json.optString("phone")
        val summaryVal = json.optString("summary")

        return StructuredProfileExtraction(
            personalInfo = ExtractedPersonalInfo(
                name = ExtractedField(nameVal, computeFieldConfidence(nameVal, "name"), ExtractionSourceType.INFERRED)
                    .takeIf { it.value.isNotBlank() },
                email = ExtractedField(emailVal, computeFieldConfidence(emailVal, "email"), ExtractionSourceType.INFERRED)
                    .takeIf { it.value.isNotBlank() },
                phone = ExtractedField(phoneVal, computeFieldConfidence(phoneVal, "phone"), ExtractionSourceType.INFERRED)
                    .takeIf { it.value.isNotBlank() },
                location = null,
                linkedIn = null,
                headline = ExtractedField(summaryVal, computeFieldConfidence(summaryVal, "summary"), ExtractionSourceType.INFERRED)
                    .takeIf { it.value.isNotBlank() }
            ),
            summary = ExtractedField(summaryVal, computeFieldConfidence(summaryVal, "summary"), ExtractionSourceType.INFERRED)
                .takeIf { it.value.isNotBlank() },
            skills = skills.toImmutableList(),
            languages = persistentListOf(),
            workHistory = work.toImmutableList(),
            education = edu.toImmutableList(),
            certifications = certs.toImmutableList(),
            overallConfidence = confidence,
            warnings = persistentListOf(),
            metadata = ExtractionMetadata("openrouter-strict")
        )
    }

    // ==========================================
    // GATE 3: VALIDATION (delegated to ExtractionValidator)
    // ==========================================

    // ==========================================
    // FILE EXTRACTION UTILITIES
    // ==========================================

    private suspend fun extractPdf(uri: Uri): String {
        val input = context.contentResolver.openInputStream(uri) ?: return ""

        return try {
            val doc = com.tom_roush.pdfbox.pdmodel.PDDocument.load(input)

            try {
                withTimeout(PDF_TEXT_TIMEOUT_MS) {
                    val stripper = com.tom_roush.pdfbox.text.PDFTextStripper()
                    stripper.getText(doc)
                }
            } finally {
                doc.close()
            }

        } finally {
            input.close()
        }
    }

    private fun extractPdfOcr(uri: Uri): String {
        val descriptor = context.contentResolver.openFileDescriptor(uri, "r")!!
        val renderer = PdfRenderer(descriptor)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val sb = StringBuilder()

        try {
            val pages = minOf(renderer.pageCount, 5)
            for (i in 0 until pages) {
                var page: PdfRenderer.Page? = null
                var bmp: Bitmap? = null
                try {
                    page = renderer.openPage(i)
                    bmp = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                    val img = InputImage.fromBitmap(bmp, 0)
                    val task = recognizer.process(img)
                    val result = try {
                        Tasks.await(task, OCR_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    } catch (e: TimeoutException) {
                        throw Exception(ERROR_OCR_TIMEOUT)
                    }
                    sb.append(result.text).append("\n")
                } finally {
                    bmp?.recycle()
                    page?.close()
                }
            }
        } finally {
            renderer.close()
            descriptor.close()
            recognizer.close()
        }
        return sb.toString()
    }

    private fun extractImageOcr(uri: Uri): String {
        val input = context.contentResolver.openInputStream(uri)!!
        val bmp = BitmapFactory.decodeStream(input)!!
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        return try {
            val img = InputImage.fromBitmap(bmp, 0)
            val task = recognizer.process(img)
            try {
                Tasks.await(task, OCR_TIMEOUT_MS, TimeUnit.MILLISECONDS).text
            } catch (e: TimeoutException) {
                throw Exception(ERROR_OCR_TIMEOUT)
            }
        } finally {
            bmp.recycle()
            input.close()
            recognizer.close()
        }
    }

    private fun extractDocx(uri: Uri): String {
        val input = context.contentResolver.openInputStream(uri) ?: return ""

        try {
            val zip = ZipInputStream(input)

            try {
                var entry = zip.nextEntry
                var docText = ""

                while (entry != null) {
                    if (entry.name == "word/document.xml") {
                        val xml = zip.readBytes().toString(Charsets.UTF_8)

                        docText = xml.replace(Regex("<[^>]+>"), " ")
                            .replace(Regex("\\s+"), " ")
                            .trim()

                        break
                    }
                    entry = zip.nextEntry
                }

                return docText

            } finally {
                zip.close()
            }

        } finally {
            input.close()
        }
    }

    private fun extractPlain(uri: Uri): String {
        val input = context.contentResolver.openInputStream(uri)!!
        return input.bufferedReader().use { it.readText() }
    }

    private fun getFileName(uri: Uri): String {
        var name = ""
        context.contentResolver.query(uri, null, null, null, null)?.use { c ->
            val i = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (c.moveToFirst() && i >= 0) name = c.getString(i) ?: ""
        }
        return name
    }
}
