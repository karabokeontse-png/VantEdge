package com.vantedge.app.data.engine

import android.util.Log
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CourseRecommendation
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.QualificationRatio
import com.vantedge.app.data.model.RelevancyItem
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.AiRequest
import org.json.JSONArray
import org.json.JSONObject

sealed class CompatibilityResult {
    data class Success(val data: CompatibilityRecord) : CompatibilityResult()
    data class Failure(
        val type: String,
        val message: String?,
        val throwable: Throwable? = null,
        val rawResponse: String? = null
    ) : CompatibilityResult()
}

class CompatibilityEngine(
    private val aiGateway: AiGateway
) {

    suspend fun analyze(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityResult {
        val systemPrompt = """
You are an elite ATS analyst and career strategist. Perform a deep compatibility analysis.
Return ONLY a valid JSON object. No markdown. No explanation. No code blocks.

Schema (every field required unless noted):
- score: int 0-100 overall compatibility
- vacancyScore: int 0-100 hard vacancy match
- roleSummary: string 3-4 sentence role analysis
- eligibilitySummary: string 3-4 sentence candidate fit assessment
- dataIntegrityNote: string (optional)
- profileStats: { yearsExperience:int, certificationCount:int, skillCount:int, matchedCount:int, gapCount:int, dataIntegrityNote:string }
- qualificationRatio: { matched:int, total:int, gaps:int }
- relevancyItems[]: { name:string, type:string("skill"|"certification"), matchPercent:int 0-100, aiDescription:string, relevancyGroup:string("HIGH"|"MEDIUM"|"LOW"|"PROFESSIONAL_MISMATCH") }
- gaps[]: { skill:string, importance:string("MANDATORY"|"IMPORTANT"|"NICE_TO_HAVE"), description:string, experienceGap:bool, platformGap:bool, courses[]:{ title:string, provider:string, url:string, category:string, hasCertificate:bool, estimatedDuration:string, relevancyPercent:int 0-100, priority:int } }

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
        """.trimIndent()

        val userPrompt = """
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

        val request = AiRequest(systemPrompt = systemPrompt, userPrompt = userPrompt)
        val result = aiGateway.generate("compatibility", request)

        if (result == null) {
            return CompatibilityResult.Failure("null_response", "AI returned null")
        }

        val start = result.indexOf("{")
        val end = result.lastIndexOf("}") + 1
        if (start == -1 || end == 0) {
            return CompatibilityResult.Failure("no_json", "No JSON found in AI response", rawResponse = result)
        }

        val extracted = result.substring(start, end)

        try {
            val json = JSONObject(extracted)
            val record = parseCompatibilityRecord(json, jobTitle, company, jobDescription)
            if (!validateCompatibilityRecord(record)) {
                return CompatibilityResult.Failure("contract_violation", "CompatibilityRecord failed structural contract validation", rawResponse = result)
            }
            return CompatibilityResult.Success(record)
        } catch (e: Exception) {
            Log.i("CompatibilityEngine", "Initial parse failed, attempting JSON repair | type=${e.javaClass.simpleName}")
        }

        val repairResult = JsonRepairUtil.repair(extracted)
        if (!repairResult.isSafe) {
            return CompatibilityResult.Failure("parse_error", "JSON repair unsafe: structure too damaged", rawResponse = result)
        }

        return try {
            val json = JSONObject(repairResult.json)
            val record = parseCompatibilityRecord(json, jobTitle, company, jobDescription)
            if (!validateCompatibilityRecord(record)) {
                return CompatibilityResult.Failure("contract_violation", "CompatibilityRecord failed structural contract validation after repair", rawResponse = result)
            }
            Log.i("CompatibilityEngine", "JSON repair succeeded | recovery=JSON_REPAIR")
            CompatibilityResult.Success(record)
        } catch (e: Exception) {
            Log.i("CompatibilityEngine", "JSON repair failed | recovery=FAILED | type=${e.javaClass.simpleName}")
            CompatibilityResult.Failure("parse_error", e.message, e, rawResponse = result)
        }
    }

    private fun parseCompatibilityRecord(
        json: JSONObject,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityRecord {
        val statsJson = json.getJSONObject("profileStats")
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
        val relevancyArray = json.getJSONArray("relevancyItems")
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
        val gapsArray = json.getJSONArray("gaps")
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

        return CompatibilityRecord(
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            score = json.getInt("score"),
            vacancyScore = json.optInt("vacancyScore", 0),
            roleSummary = json.getString("roleSummary"),
            eligibilitySummary = json.getString("eligibilitySummary"),
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
    }

    // Structural contract validator only. No semantic evaluation permitted. Missing field = failure. Empty field = valid. Derived fields never fail validation.
    private fun validateCompatibilityRecord(record: CompatibilityRecord): Boolean {
        if (record.score !in 0..100) return false
        if (record.profileStats.yearsExperience < 0) return false
        if (record.profileStats.certificationCount < 0) return false
        if (record.profileStats.skillCount < 0) return false
        if (record.profileStats.matchedCount < 0) return false
        if (record.profileStats.gapCount < 0) return false
        return true
    }
}