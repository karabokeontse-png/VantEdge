package com.vantedge.app.data.engine

import android.util.Log
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.network.GeminiService
import org.json.JSONObject

class GeneratorEngine {

    private val service = GeminiService()

    suspend fun generateCv(
        profile: UserProfile,
        jobDescription: String,
        designId: String,
        schemeId: String,
        jobTitle: String,
        company: String,
        onResult: (String?) -> Unit
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

        val result = service.generate(prompt)

        if (result == null) {
            Log.e("GeneratorEngine", "CV: AI returned null")
            onResult(null)
            return
        }

        Log.d("GeneratorEngine", "CV raw AI response: $result")

        try {
            val startIndex = result.indexOf("{")
            val endIndex = result.lastIndexOf("}") + 1

            if (startIndex == -1 || endIndex == 0) {
                Log.e("GeneratorEngine", "CV: No JSON found in response")
                onResult("{\"matchedKeywords\":[]}")
                return
            }

            val clean = result.substring(startIndex, endIndex)
            val json = JSONObject(clean)
            json.getJSONArray("matchedKeywords")
            onResult(clean)

        } catch (e: Exception) {
            Log.e("GeneratorEngine", "CV parse error: ${e.message}")
            onResult("{\"matchedKeywords\":[]}")
        }
    }

    suspend fun generateCoverLetter(
        profile: UserProfile,
        jobDescription: String,
        designId: String,
        schemeId: String,
        jobTitle: String,
        company: String,
        onResult: (String?) -> Unit
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

        val result = service.generate(prompt)

        if (result == null) {
            Log.e("GeneratorEngine", "Cover letter: AI returned null")
            onResult(null)
            return
        }

        Log.d("GeneratorEngine", "Cover letter raw AI response: $result")
        onResult(result)
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
        onResult: (String?) -> Unit
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

        val result = service.generate(prompt)
        onResult(result)
    }

    suspend fun extractJobFields(
        rawText: String,
        onResult: (jobTitle: String?, company: String?, jobDescription: String?) -> Unit
    ) {
        val prompt = """
            Extract the following from this job posting text and return ONLY a JSON object:
            {
              "jobTitle": "extracted job title or empty string",
              "company": "extracted company name or empty string",
              "jobDescription": "cleaned job description text, max 2000 characters"
            }

            Return ONLY the JSON. No explanation. No markdown. No code blocks.

            TEXT:
            ${rawText.take(4000)}
        """.trimIndent()

        val result = service.generate(prompt)

        if (result == null) {
            onResult(null, null, rawText)
            return
        }

        try {
            val startIndex = result.indexOf("{")
            val endIndex = result.lastIndexOf("}") + 1

            if (startIndex == -1 || endIndex == 0) {
                onResult(null, null, rawText)
                return
            }

            val clean = result.substring(startIndex, endIndex)
            val json = JSONObject(clean)

            val jobTitle = json.optString("jobTitle").takeIf { it.isNotBlank() }
            val company = json.optString("company").takeIf { it.isNotBlank() }
            val jobDescription = json.optString("jobDescription").takeIf { it.isNotBlank() } ?: rawText

            onResult(jobTitle, company, jobDescription)

        } catch (e: Exception) {
            onResult(null, null, rawText)
        }
    }
}