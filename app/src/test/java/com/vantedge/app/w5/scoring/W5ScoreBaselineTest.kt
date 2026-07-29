package com.vantedge.app.w5.scoring

import org.junit.Test
import org.junit.BeforeClass
import java.io.File

class W5ScoreBaselineTest {

    companion object {
        private lateinit var engine: W5ScoreEngine
        private const val FIXTURE_COUNT = 50

        @BeforeClass
        @JvmStatic
        fun setup() {
            val assets = ValidationAssets(
                stopWords = setOf("the", "a", "an", "and", "or", "of", "to", "in", "for", "with"),
                keywordDictionary = mapOf(
                    "kotlin" to listOf("kotlin", "kotlinlang"),
                    "java" to listOf("java", "java8", "java11"),
                    "python" to listOf("python", "py"),
                    "javascript" to listOf("javascript", "js", "ecmascript"),
                    "aws" to listOf("aws", "amazon-web-services", "amazonwebservices")
                )
            )
            engine = W5ScoreEngine(assets)
        }
    }

    @Test
    fun `generate 50-fixture deterministic baseline`() {
        val csvFile = File("build/reports/baseline_scores.csv")
        csvFile.parentFile.mkdirs()

        val skillPool = listOf(
            "Kotlin", "Java", "Python", "JavaScript", "TypeScript",
            "AWS", "Azure", "GCP", "Docker", "Kubernetes",
            "SQL", "NoSQL", "Redis", "Kafka", "RabbitMQ",
            "Spring", "React", "Angular", "Vue", "Node.js"
        )

        val qualificationPool = listOf(
            RequirementEvidence("PhD in Computer Science", RequirementCategory.DEGREE, ClassificationStatus.NORMALIZED),
            RequirementEvidence("Master's Degree in Computer Science", RequirementCategory.DEGREE, ClassificationStatus.NORMALIZED),
            RequirementEvidence("Bachelor's Degree in Computer Science", RequirementCategory.DEGREE, ClassificationStatus.NORMALIZED),
            RequirementEvidence("CISSP Certification", RequirementCategory.CERTIFICATION, ClassificationStatus.NORMALIZED),
            RequirementEvidence("AWS Solutions Architect", RequirementCategory.CERTIFICATION, ClassificationStatus.NORMALIZED),
            RequirementEvidence("PMP Certification", RequirementCategory.CERTIFICATION, ClassificationStatus.NORMALIZED),
            RequirementEvidence("Security Clearance TS/SCI", RequirementCategory.SECURITY_CLEARANCE, ClassificationStatus.NORMALIZED),
            RequirementEvidence("Chartered Financial Analyst", RequirementCategory.CERTIFICATION, ClassificationStatus.NORMALIZED),
            RequirementEvidence("Master's Degree in Data Science", RequirementCategory.DEGREE, ClassificationStatus.NORMALIZED),
            RequirementEvidence("Licensed Professional Engineer", RequirementCategory.LICENCE, ClassificationStatus.NORMALIZED)
        )

        val csvLines = mutableListOf<String>()
        csvLines.add("fixture,correlationId,skillMatch,experienceAlignment,roleRelevance,keywordCoverage,seniorityFit,structuralCompleteness,constraintCompliance,totalScore,confidence,missingGaps,weakGaps,matchedGaps,profileSkills,jobSkills")

        for (i in 0 until FIXTURE_COUNT) {
            val correlationId = "baseline-fixture-%03d".format(i)

            val profileSkills = skillPool.take(i % 7 + 3)
            val jobSkills = skillPool.drop(2).take(i % 5 + 2)

            val profileQuals = qualificationPool.take(i % 4)
            val jobQuals = qualificationPool.drop(1).take(i % 4)

            val profile = ValidatedProfile(
                correlationId = correlationId,
                skills = profileSkills,
                qualifications = profileQuals,
                roles = listOf("Engineer"),
                currentTitle = "Software Engineer",
                experienceYears = i % 15 + 1,
                seniorityLevel = when (i % 3) { 0 -> "junior"; 1 -> "mid"; else -> "senior" },
                completedFields = setOf("skills", "roles", "currentTitle", "experienceYears", "seniorityLevel"),
                isAccepted = true,
                isDegraded = false
            )

            val job = ValidatedJob(
                correlationId = correlationId,
                requiredSkills = jobSkills,
                requiredQualifications = jobQuals,
                title = "Software Engineer",
                keywords = jobSkills,
                requiredYears = if (i % 2 == 0) 3 else null,
                seniorityLevel = when (i % 3) { 0 -> "junior"; 1 -> "mid"; else -> "senior" },
                completedFields = setOf("requiredSkills", "title", "keywords"),
                isAccepted = true,
                isDegraded = false
            )

            val trace = W5TraceContext(correlationId, "", 0L)

            val result = engine.evaluate(profile, job, trace)

            val skillMatchScore = result.breakdown.axisBreakdowns.find { it.axisName == "SkillMatch" }?.score ?: 0.0
            val expAlignScore = result.breakdown.axisBreakdowns.find { it.axisName == "ExperienceAlignment" }?.score ?: 0.0
            val roleRelScore = result.breakdown.axisBreakdowns.find { it.axisName == "RoleRelevance" }?.score ?: 0.0
            val kwCoverageScore = result.breakdown.axisBreakdowns.find { it.axisName == "KeywordCoverage" }?.score ?: 0.0
            val seniorFitScore = result.breakdown.axisBreakdowns.find { it.axisName == "SeniorityFit" }?.score ?: 0.0
            val structCompScore = result.breakdown.axisBreakdowns.find { it.axisName == "StructuralCompleteness" }?.score ?: 0.0
            val constraintCompScore = result.breakdown.axisBreakdowns.find { it.axisName == "ConstraintCompliance" }?.score ?: 0.0

            csvLines.add(
                "%d,%s,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%d,%d,%d,%d".format(
                    i, correlationId,
                    skillMatchScore, expAlignScore, roleRelScore, kwCoverageScore,
                    seniorFitScore, structCompScore, constraintCompScore,
                    result.totalScore, result.confidence,
                    result.gapAnalysis.missing.size, result.gapAnalysis.weak.size, result.gapAnalysis.matched.size,
                    profileSkills.size, jobSkills.size
                )
            )
        }

        csvFile.writeText(csvLines.joinToString("\n"))

        val lines = csvFile.readLines()
        assert(lines.size == FIXTURE_COUNT + 1) { "Expected ${FIXTURE_COUNT + 1} lines, got ${lines.size}" }
        println("Baseline CSV written to ${csvFile.absolutePath} with $FIXTURE_COUNT fixtures")
    }
}
