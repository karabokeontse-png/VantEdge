package com.vantedge.app.data.engine

import com.fasterxml.jackson.databind.JsonNode
import com.vantedge.app.data.model.CompatibilityRecord
import com.vantedge.app.data.model.CourseRecommendation
import com.vantedge.app.data.model.GapItem
import com.vantedge.app.data.model.ProfileStats
import com.vantedge.app.data.model.QualificationRatio
import com.vantedge.app.data.model.RelevancyItem
import com.vantedge.app.data.model.VerificationStatus
import com.vantedge.app.domain.PipelineTrace

class CompatibilitySchemaViolationException(missingFields: List<String>) :
    Exception("Compatibility schema violation — missing fields: ${missingFields.joinToString(", ")}")

sealed class CompatibilityResult {
    data class Success(val data: CompatibilityRecord) : CompatibilityResult()
    data class Failure(
        val type: String,
        val message: String?,
        val throwable: Throwable? = null,
        val rawResponse: String? = null
    ) : CompatibilityResult()
}

class CompatibilityEngine {

    fun analyze(
        node: JsonNode,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityRecord {
        validateCompatibilitySchema(node)
        return parseCompatibilityRecord(node, jobTitle, company, jobDescription)
    }

    private fun validateCompatibilitySchema(node: JsonNode) {
        val required = listOf("score", "vacancyScore", "roleSummary", "eligibilitySummary", "profileStats", "qualificationRatio", "relevancyItems", "gaps")
        val missing = required.filter { field -> !node.has(field) || node.get(field).isMissingNode }
        if (missing.isNotEmpty()) {
            PipelineTrace.error("CompatibilityEngine", "Schema validation failed: missing $missing")
            throw CompatibilitySchemaViolationException(missing)
        }
    }

    private fun computeUrlVerification(url: String): VerificationStatus {
        if (url.isBlank()) return VerificationStatus.HALLUCINATED
        return if (url.startsWith("http://") || url.startsWith("https://")) {
            VerificationStatus.UNVERIFIED
        } else {
            VerificationStatus.HALLUCINATED
        }
    }

    private fun parseCompatibilityRecord(
        node: JsonNode,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityRecord {
        val statsNode = node.get("profileStats")
        val stats = ProfileStats(
            yearsExperience = statsNode.path("yearsExperience").asInt(0),
            certificationCount = statsNode.path("certificationCount").asInt(0),
            skillCount = statsNode.path("skillCount").asInt(0),
            matchedCount = statsNode.path("matchedCount").asInt(0),
            gapCount = statsNode.path("gapCount").asInt(0),
            dataIntegrityNote = statsNode.path("dataIntegrityNote").asText(
                "Only verified profile data was evaluated"
            )
        )

        val ratioNode = node.get("qualificationRatio")
        val ratio = if (ratioNode != null && !ratioNode.isMissingNode) {
            QualificationRatio(
                matched = ratioNode.path("matched").asInt(0),
                total = ratioNode.path("total").asInt(0),
                gaps = ratioNode.path("gaps").asInt(0)
            )
        } else {
            QualificationRatio(0, 0, 0)
        }

        val relevancyItems = mutableListOf<RelevancyItem>()
        val relevancyArray = node.get("relevancyItems")
        if (relevancyArray != null && relevancyArray.isArray) {
            for (itemNode in relevancyArray) {
                relevancyItems.add(
                    RelevancyItem(
                        name = itemNode.path("name").asText(""),
                        type = itemNode.path("type").asText("skill"),
                        matchPercent = itemNode.path("matchPercent").asInt(0),
                        aiDescription = itemNode.path("aiDescription").asText(""),
                        relevancyGroup = itemNode.path("relevancyGroup").asText("LOW")
                    )
                )
            }
        }

        val gaps = mutableListOf<GapItem>()
        val gapsArray = node.get("gaps")
        if (gapsArray != null && gapsArray.isArray) {
            for (gapNode in gapsArray) {
                val courses = mutableListOf<CourseRecommendation>()
                val coursesArray = gapNode.get("courses")
                if (coursesArray != null && coursesArray.isArray) {
                    for (c in coursesArray) {
                        val url = c.path("url").asText("")
                        courses.add(
                            CourseRecommendation(
                                title = c.path("title").asText(""),
                                provider = c.path("provider").asText(""),
                                url = url,
                                category = c.path("category").asText("paid"),
                                hasCertificate = c.path("hasCertificate").asBoolean(false),
                                estimatedDuration = c.path("estimatedDuration").asText("Self-paced"),
                                relevancyPercent = c.path("relevancyPercent").asInt(0),
                                priority = c.path("priority").asInt(1),
                                verificationStatus = computeUrlVerification(url)
                            )
                        )
                    }
                }
                gaps.add(
                    GapItem(
                        skill = gapNode.path("skill").asText(""),
                        importance = gapNode.path("importance").asText("IMPORTANT"),
                        description = gapNode.path("description").asText(""),
                        experienceGap = gapNode.path("experienceGap").asBoolean(false),
                        platformGap = gapNode.path("platformGap").asBoolean(false),
                        courses = courses
                    )
                )
            }
        }

        return CompatibilityRecord(
            jobTitle = jobTitle,
            company = company,
            jobDescription = jobDescription,
            score = node.get("score").asInt(),
            vacancyScore = node.path("vacancyScore").asInt(0),
            roleSummary = node.get("roleSummary").asText(),
            eligibilitySummary = node.get("eligibilitySummary").asText(),
            dataIntegrityNote = node.path("dataIntegrityNote").asText(""),
            profileStats = stats,
            qualificationRatio = ratio,
            relevancyItems = relevancyItems,
            gaps = gaps,
            criticalGapCount = node.path("criticalGapCount").asInt(
                gaps.count { it.importance == "MANDATORY" }
            )
        )
    }
}
