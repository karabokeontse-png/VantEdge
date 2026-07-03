package com.vantedge.app.data.engine

import android.util.Log
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.engine.extraction.JsonExtractionEngine
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.AiRequest
import com.vantedge.app.domain.PipelineTrace
import com.vantedge.pipeline.validation.P2ValidationEngine
import org.json.JSONObject

sealed class EngineResult {
    data class Success(val data: String) : EngineResult()
    data class Failure(val type: String, val detail: String?) : EngineResult()
}

class GeneratorEngine(
    private val aiGateway: AiGateway
) {

    suspend fun generateCv(
        profile: UserProfile,
        jobDescription: String,
        designId: String,
        schemeId: String,
        jobTitle: String,
        company: String,
        correlationId: String,
        onResult: (EngineResult) -> Unit
    ) {
        val systemPrompt = """
Analyse this job description and return ONLY a JSON object with this exact structure, nothing else:
{
  "matchedKeywords": ["keyword1", "keyword2"],
  "relevantSummary": "rewritten summary tailored to this role in 2-3 sentences",
  "relevantExperience": ["rewritten bullet point 1", "rewritten bullet point 2"]
}

Return ONLY the JSON. No explanation. No markdown. No code blocks.
        """.trimIndent()

        val userPrompt = """
JOB DESCRIPTION:
$jobDescription

PROFILE SUMMARY:
${profile.summary}

PROFILE EXPERIENCE:
${profile.workHistory.joinToString("\n") { "${it.role} at ${it.company}: ${it.description}" }}
        """.trimIndent()

        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt)
        val result = aiGateway.generate("cv", request, 120_000L)

        if (result == null) {
            Log.e("GeneratorEngine", "CV: aiGateway returned null | EXIT Failure(provider)")
            onResult(EngineResult.Failure("provider", "AiGateway returned null"))
            return
        }

        Log.d("GeneratorEngine", "CV: aiGateway returned non-null | rawLength=${result.length}")

        Log.d("GeneratorEngine", "CV preview: ${result.take(500)}")

        try {
            val extractionResult = JsonExtractionEngine.extract(result)
            if (!extractionResult.success) {
                Log.e("GeneratorEngine", "CV: JSON extraction failed | strategy=${extractionResult.strategy} reason=${extractionResult.failureReason} | EXIT Failure(schema)")
                onResult(EngineResult.Failure("schema", extractionResult.failureReason ?: "No JSON found in AI response"))
                return
            }

            val clean = extractionResult.content

            val repairResult = JsonRepairUtil.repair(clean)
            if (!repairResult.isSafe) {
                Log.e("GeneratorEngine", "CV: JSON repair deemed unsafe | cleanLength=${clean.length} | EXIT Failure(schema)")
                onResult(EngineResult.Failure("schema", "JSON structure too damaged to repair"))
                return
            }

            val json = JSONObject(repairResult.json)
            json.getJSONArray("matchedKeywords")
            val p2OutputResult = P2ValidationEngine.validateGeneratorOutput(repairResult.json, correlationId)
            PipelineTrace.dataQuality("P2Validation", "P2_DECISION", mapOf(
                "correlationId" to correlationId,
                "orchestrator" to "GeneratorEngine",
                "decision" to p2OutputResult.decision.javaClass.simpleName
            ), correlationId)
            when (p2OutputResult.decision) {
                is com.vantedge.pipeline.validation.ValidationDecision.Degraded -> {
                    Log.w("GeneratorEngine", "[P2] Output degraded: ${p2OutputResult.decision.warnings}")
                }
                else -> {}
            }
            Log.d("GeneratorEngine", "CV: JSON parse OK | extractedLength=${repairResult.json.length} | matchedKeywords=${json.getJSONArray("matchedKeywords").length()} | EXIT Success")
            onResult(EngineResult.Success(p2OutputResult.validated))

        } catch (e: Exception) {
            Log.e("GeneratorEngine", "CV: parse failed | error=${e.message} | EXIT Failure(parse)")
            onResult(EngineResult.Failure("parse", e.message))
        }
    }

    suspend fun generateCoverLetter(
        profile: UserProfile,
        jobDescription: String,
        designId: String,
        schemeId: String,
        jobTitle: String,
        company: String,
        correlationId: String,
        onResult: (EngineResult) -> Unit
    ) {
        val systemPrompt = """
Write a professional cover letter body for this role.
Output ONLY plain HTML paragraph tags like this: <p>paragraph text here</p>
No greetings line. No sign-off. No subject line. Just the body paragraphs.
Under 350 words. Bold any keywords from the job description like this: <b>keyword</b>
Tailor content specifically to prove competency for this role using the profile below.
Do NOT invent information not in the profile.
Do NOT output any HTML document structure, head tags, or body tags.
ONLY output <p> tags with the letter content.
        """.trimIndent()

        val userPrompt = """
PROFILE:
Name: ${profile.name}
Summary: ${profile.summary}
Skills: ${profile.skills.joinToString(", ")}
Experience: ${profile.workHistory.joinToString("\n") { "${it.role} at ${it.company}: ${it.description}" }}

JOB DESCRIPTION:
$jobDescription
        """.trimIndent()

        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt)
        val result = aiGateway.generate("cover_letter", request, 120_000L)

        if (result == null) {
            Log.e("GeneratorEngine", "Cover letter: aiGateway returned null | EXIT Failure(provider)")
            onResult(EngineResult.Failure("provider", "AiGateway returned null"))
            return
        }

        Log.d("GeneratorEngine", "Cover letter: aiGateway returned non-null | rawLength=${result.length}")
        Log.d("GeneratorEngine", "Cover letter raw AI response: $result")
        Log.d("GeneratorEngine", "Cover letter: EXIT Success")
        onResult(EngineResult.Success(result))
    }

    fun applyDesignToContent(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        matchedKeywordsJson: String,
        coverLetterBody: String,
        designId: String,
        schemeId: String
    ): Pair<String, String> {
        val keywords = try {
            val json = JSONObject(matchedKeywordsJson)
            val arr = json.getJSONArray("matchedKeywords")
            (0 until arr.length()).map { arr.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }

        val cvHtml = CVTemplate.render(
            profile = profile,
            jobTitle = jobTitle,
            company = company,
            matchedKeywords = keywords,
            designId = designId,
            schemeId = schemeId
        )

        val coverLetterHtml = CVTemplate.renderCoverLetter(
            profile = profile,
            jobTitle = jobTitle,
            company = company,
            content = coverLetterBody,
            designId = designId,
            schemeId = schemeId
        )

        return Pair(cvHtml, coverLetterHtml)
    }

    suspend fun generateCvDocx(
        profile: UserProfile,
        jobDescription: String,
        correlationId: String,
        onResult: (EngineResult) -> Unit
    ) {
        val systemPrompt = """
Generate an ATS-optimized CV as plain text formatted for Microsoft Word.
Use clear section headers in ALL CAPS.
Use simple bullet points with dashes (-).
No HTML. No markdown. Plain text only.
Bold keywords from the job description by wrapping them in **double asterisks**.
Keep it under one page worth of content.
Do NOT invent information not in the profile.
        """.trimIndent()

        val userPrompt = """
PROFILE:
Name: ${profile.name}
Email: ${profile.email}
Phone: ${profile.phone}
Location: ${profile.location}
LinkedIn: ${profile.linkedIn}
Summary: ${profile.summary}
Skills: ${profile.skills.joinToString(", ")}
Work History: ${profile.workHistory.joinToString("\n") {
    "${it.role} at ${it.company} (${it.startDate} - ${it.endDate}): ${it.description}"
}}
Education: ${profile.education.joinToString(", ")}
Certifications: ${profile.certifications.joinToString(", ")}
Languages: ${profile.languages.joinToString(", ")}

JOB DESCRIPTION:
$jobDescription
        """.trimIndent()

        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt)
        val result = aiGateway.generate("cv_docx", request, 120_000L)
        if (result == null) {
            Log.e("GeneratorEngine", "CV docx: aiGateway returned null | EXIT Failure(provider)")
            onResult(EngineResult.Failure("provider", "AiGateway returned null"))
        } else {
            Log.d("GeneratorEngine", "CV docx: aiGateway returned non-null | rawLength=${result.length} | EXIT Success")
            onResult(EngineResult.Success(result))
        }
    }
}