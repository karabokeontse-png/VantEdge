package com.vantedge.app.data.engine

import android.util.Log
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.network.AiGateway
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
        onResult: (EngineResult) -> Unit
    ) {
        val prompt = """
            Analyse this job description and return ONLY a JSON object with this exact structure, nothing else:
            {
              "matchedKeywords": ["keyword1", "keyword2"],
              "relevantSummary": "rewritten summary tailored to this role in 2-3 sentences",
              "relevantExperience": ["rewritten bullet point 1", "rewritten bullet point 2"]
            }

            Return ONLY the JSON. No explanation. No markdown. No code blocks.

            JOB DESCRIPTION:
            $jobDescription

            PROFILE SUMMARY:
            ${profile.summary}

            PROFILE EXPERIENCE:
            ${profile.workHistory.joinToString("\n") { "${it.role} at ${it.company}: ${it.description}" }}
        """.trimIndent()

        val result = aiGateway.generate("cv", prompt)

        if (result == null) {
            Log.e("GeneratorEngine", "CV: aiGateway returned null | EXIT Failure(provider)")
            onResult(EngineResult.Failure("provider", "AiGateway returned null"))
            return
        }

        Log.d("GeneratorEngine", "CV: aiGateway returned non-null | rawLength=${result.length}")

        val respLength = result.length
        val respPreview = result.take(500)
        val hasFenceClose = result.contains("```") && result.indexOf("```") != result.lastIndexOf("```")
        val firstOpenBrace = result.indexOf("{")
        val lastCloseBrace = result.lastIndexOf("}")

        Log.d("GeneratorEngine", "CV | length=$respLength | hasFence=$hasFenceClose | braceOpen=$firstOpenBrace | braceClose=$lastCloseBrace")
        Log.d("GeneratorEngine", "CV preview: $respPreview")

        try {
            val clean: String
            if (hasFenceClose) {
                val start = result.indexOf("{", result.indexOf("```"))
                val end = result.lastIndexOf("}") + 1
                clean = if (start != -1 && end > start) result.substring(start, end) else result
                Log.d("GeneratorEngine", "CV: fence extraction | start=$start end=$end cleanLength=${clean.length}")
            } else if (firstOpenBrace != -1 && lastCloseBrace > firstOpenBrace) {
                clean = result.substring(firstOpenBrace, lastCloseBrace + 1)
                Log.d("GeneratorEngine", "CV: brace extraction | cleanLength=${clean.length}")
            } else {
                Log.e("GeneratorEngine", "CV: No JSON found in response | firstBrace=$firstOpenBrace lastBrace=$lastCloseBrace fences=$hasFenceClose | EXIT Failure(schema)")
                onResult(EngineResult.Failure("schema", "No JSON found in AI response"))
                return
            }

            val json = JSONObject(clean)
            json.getJSONArray("matchedKeywords")
            Log.d("GeneratorEngine", "CV: JSON parse OK | extractedLength=${clean.length} | matchedKeywords=${json.getJSONArray("matchedKeywords").length()} | EXIT Success")
            onResult(EngineResult.Success(clean))

        } catch (e: Exception) {
            Log.e("GeneratorEngine", "CV: parse failed | error=${e.message} | extractedLength=${try { result.substring(firstOpenBrace, lastCloseBrace + 1).length } catch (_: Exception) { -1 }} | EXIT Failure(parse)")
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
        onResult: (EngineResult) -> Unit
    ) {
        val prompt = """
            Write a professional cover letter body for this role.
            Output ONLY plain HTML paragraph tags like this: <p>paragraph text here</p>
            No greetings line. No sign-off. No subject line. Just the body paragraphs.
            Under 350 words. Bold any keywords from the job description like this: <b>keyword</b>
            Tailor content specifically to prove competency for this role using the profile below.
            Do NOT invent information not in the profile.
            Do NOT output any HTML document structure, head tags, or body tags.
            ONLY output <p> tags with the letter content.

            PROFILE:
            Name: ${profile.name}
            Summary: ${profile.summary}
            Skills: ${profile.skills.joinToString(", ")}
            Experience: ${profile.workHistory.joinToString("\n") { "${it.role} at ${it.company}: ${it.description}" }}

            JOB DESCRIPTION:
            $jobDescription
        """.trimIndent()

        val result = aiGateway.generate("cover_letter", prompt)

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
        onResult: (EngineResult) -> Unit
    ) {
        val prompt = """
            Generate an ATS-optimized CV as plain text formatted for Microsoft Word.
            Use clear section headers in ALL CAPS.
            Use simple bullet points with dashes (-).
            No HTML. No markdown. Plain text only.
            Bold keywords from the job description by wrapping them in **double asterisks**.
            Keep it under one page worth of content.
            Do NOT invent information not in the profile.

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

        val result = aiGateway.generate("cv_docx", prompt)
        if (result == null) {
            Log.e("GeneratorEngine", "CV docx: aiGateway returned null | EXIT Failure(provider)")
            onResult(EngineResult.Failure("provider", "AiGateway returned null"))
        } else {
            Log.d("GeneratorEngine", "CV docx: aiGateway returned non-null | rawLength=${result.length} | EXIT Success")
            onResult(EngineResult.Success(result))
        }
    }
}