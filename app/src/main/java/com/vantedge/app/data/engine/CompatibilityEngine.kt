package com.vantedge.app.data.engine

import android.util.Log
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CourseRecommendation
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.QualificationRatio
import com.vantedge.app.data.model.RelevancyItem
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.network.GeminiService
import org.json.JSONArray
import org.json.JSONObject

class CompatibilityEngine {

    private val service = GeminiService()

    suspend fun analyze(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        onResult: (CompatibilityRecord?) -> Unit
    ) {
        val prompt = """
You are an elite ATS analyst and career strategist. Perform a deep compatibility analysis.
Return ONLY a valid JSON object. No markdown. No explanation. No code blocks.

{
  "score": 55,
  "vacancyScore": 48,
  "roleSummary": "3-4 sentence detailed summary of the role, what it demands technically and professionally, and the ideal candidate profile",
  "eligibilitySummary": "3-4 sentence honest and detailed assessment of this candidate's fit, referencing their actual experience and certifications",
  "dataIntegrityNote": "Only verified certifications and documented skills were evaluated. Self-reported experience was cross-referenced with work history dates.",
  "profileStats": {
    "yearsExperience": 5,
    "certificationCount": 3,
    "skillCount": 12,
    "matchedCount": 6,
    "gapCount": 4,
    "dataIntegrityNote": "Only documented certifications and listed skills were evaluated"
  },
  "qualificationRatio": {
    "matched": 6,
    "total": 13,
    "gaps": 4
  },
  "relevancyItems": [
    {
      "name": "CCNP Enterprise",
      "type": "certification",
      "matchPercent": 95,
      "aiDescription": "Directly satisfies the routing and switching requirement listed in the job description",
      "relevancyGroup": "HIGH"
    }
  ],
  "gaps": [
    {
      "skill": "Cisco ASA Firewall",
      "importance": "MANDATORY",
      "description": "The job explicitly requires hands-on firewall configuration experience which is absent from the profile",
      "experienceGap": false,
      "platformGap": true,
      "courses": [
        {
          "title": "Cisco ASA Firewall Fundamentals",
          "provider": "Udemy",
          "url": "https://www.udemy.com/course/cisco-asa/",
          "category": "paid",
          "hasCertificate": true,
          "estimatedDuration": "12 hours",
          "relevancyPercent": 94,
          "priority": 1
        }
      ]
    }
  ]
}

STRICT RULES:
- score is 0-100 integer: overall compatibility
- vacancyScore is 0-100 integer: how well candidate meets the hard vacancy requirements only
- relevancyItems must include ALL certifications and skills from the candidate profile
- relevancyGroup must be exactly one of: "HIGH", "MEDIUM", "LOW", "PROFESSIONAL_MISMATCH"
- gaps must only list skills/certs the candidate does NOT have but the job needs
- importance must be exactly "MANDATORY", "IMPORTANT", or "NICE_TO_HAVE"
- experienceGap = true if the candidate has the skill but lacks sufficient years
- platformGap = true if the gap is about a specific platform/vendor tool
- For each gap provide 2-3 real course recommendations
- ONLY use real URLs from: Coursera, Udemy, edX, Google, Microsoft, LinkedIn Learning, AWS, freeCodeCamp, Cisco, CompTIA

CANDIDATE PROFILE:
Name: ${profile.name}
Summary: ${profile.summary}
Skills: ${profile.skills.joinToString(", ")}
Certifications: ${profile.certifications.joinToString(", ")}
Experience: ${profile.workHistory.joinToString("\n") { "${it.role} at ${it.company} (${it.startDate}-${it.endDate}): ${it.description}" }}
Education: ${profile.education.joinToString(", ")}
Languages: ${profile.languages.joinToString(", ")}

JOB DESCRIPTION:
$jobDescription
        """.trimIndent()

        val result = service.generate(prompt)

        if (result == null) {
            Log.e("CompatibilityEngine", "AI returned null")
            onResult(null)
            return
        }

        try {
            val start = result.indexOf("{")
            val end = result.lastIndexOf("}") + 1
            if (start == -1 || end == 0) {
                Log.e("CompatibilityEngine", "No JSON found")
                onResult(null)
                return
            }

            val json = JSONObject(result.substring(start, end))

            val statsJson = json.optJSONObject("profileStats") ?: JSONObject()
            val stats = ProfileStats(
                yearsExperience = statsJson.optInt("yearsExperience", 0),
                certificationCount = statsJson.optInt("certificationCount", 0),
                skillCount = statsJson.optInt("skillCount", 0),
                matchedCount = statsJson.optInt("matchedCount", 0),
                gapCount = statsJson.optInt("gapCount", 0),
                dataIntegrityNote = statsJson.optString(
                    "dataIntegrityNote",
                    "Only verified profile data was evaluated"
                )
            )

            val ratioJson = json.optJSONObject("qualificationRatio") ?: JSONObject()
            val ratio = QualificationRatio(
                matched = ratioJson.optInt("matched", 0),
                total = ratioJson.optInt("total", 0),
                gaps = ratioJson.optInt("gaps", 0)
            )

            val relevancyItems = mutableListOf<RelevancyItem>()
            val relevancyArray = json.optJSONArray("relevancyItems") ?: JSONArray()
            for (i in 0 until relevancyArray.length()) {
                val item = relevancyArray.optJSONObject(i) ?: continue
                relevancyItems.add(
                    RelevancyItem(
                        name = item.optString("name", ""),
                        type = item.optString("type", "skill"),
                        matchPercent = item.optInt("matchPercent", 0),
                        aiDescription = item.optString("aiDescription", ""),
                        relevancyGroup = item.optString("relevancyGroup", "LOW")
                    )
                )
            }

            val gaps = mutableListOf<GapItem>()
            val gapsArray = json.optJSONArray("gaps") ?: JSONArray()
            for (i in 0 until gapsArray.length()) {
                val gapJson = gapsArray.optJSONObject(i) ?: continue
                val courses = mutableListOf<CourseRecommendation>()
                val coursesArray = gapJson.optJSONArray("courses") ?: JSONArray()
                for (j in 0 until coursesArray.length()) {
                    val c = coursesArray.optJSONObject(j) ?: continue
                    courses.add(
                        CourseRecommendation(
                            title = c.optString("title", ""),
                            provider = c.optString("provider", ""),
                            url = c.optString("url", ""),
                            category = c.optString("category", "paid"),
                            hasCertificate = c.optBoolean("hasCertificate", false),
                            estimatedDuration = c.optString("estimatedDuration", "Self-paced"),
                            relevancyPercent = c.optInt("relevancyPercent", 0),
                            priority = c.optInt("priority", 1)
                        )
                    )
                }
                gaps.add(
                    GapItem(
                        skill = gapJson.optString("skill", ""),
                        importance = gapJson.optString("importance", "IMPORTANT"),
                        description = gapJson.optString("description", ""),
                        experienceGap = gapJson.optBoolean("experienceGap", false),
                        platformGap = gapJson.optBoolean("platformGap", false),
                        courses = courses
                    )
                )
            }

            val record = CompatibilityRecord(
                jobTitle = jobTitle,
                company = company,
                jobDescription = jobDescription,
                score = json.optInt("score", 0),
                vacancyScore = json.optInt("vacancyScore", 0),
                roleSummary = json.optString("roleSummary", ""),
                eligibilitySummary = json.optString("eligibilitySummary", ""),
                dataIntegrityNote = json.optString("dataIntegrityNote", ""),
                profileStats = stats,
                qualificationRatio = ratio,
                relevancyItems = relevancyItems,
                gaps = gaps,
                criticalGapCount = json.optInt(
                    "criticalGapCount",
                    gaps.count { it.importance == "MANDATORY" }
                )
            )

            onResult(record)

        } catch (e: Exception) {
            Log.e("CompatibilityEngine", "Parse error: ${e.message}")
            onResult(null)
        }
    }

    suspend fun extractJobFields(
        rawText: String,
        onResult: (jobTitle: String?, company: String?, jobDescription: String?) -> Unit
    ) {
        val prompt = """
Extract job posting details from this text.
Return ONLY a JSON object with this exact structure. No markdown. No explanation. No code blocks.
{
  "jobTitle": "exact job title from the posting",
  "company": "company name from the posting",
  "jobDescription": "the full job description text cleaned up"
}

STRICT RULES:
- Extract ONLY what is actually written in the text below
- Do NOT invent, assume, or hallucinate any details
- If a field cannot be found, use an empty string ""
- jobDescription must be the actual description text, not a summary

TEXT:
${rawText.take(4000)}
        """.trimIndent()

        val result = service.generate(prompt)

        if (result == null) {
            onResult(null, null, rawText.take(3000))
            return
        }

        try {
            val start = result.indexOf("{")
            val end = result.lastIndexOf("}") + 1
            if (start == -1 || end == 0) {
                onResult(null, null, rawText.take(3000))
                return
            }
            val json = JSONObject(result.substring(start, end))
            val title = json.optString("jobTitle", "").ifBlank { null }
            val company = json.optString("company", "").ifBlank { null }
            val desc = json.optString("jobDescription", "").ifBlank { rawText.take(3000) }
            onResult(title, company, desc)
        } catch (e: Exception) {
            onResult(null, null, rawText.take(3000))
        }
    }
}