package com.vantedge.app.domain.extraction

import com.vantedge.app.data.engine.extraction.LlmJobExtractor
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.viewmodel.JobExtractionState
import com.vantedge.app.data.viewmodel.mapOutcomeToState
import com.vantedge.pipeline.validation.ValidationDecision
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class JobExtractionDecisionEnforcementTest {

    private companion object {
        const val LONG_DESCRIPTION =
            "We are looking for a Senior Kotlin Engineer to build reliable distributed systems. " +
                "You will design APIs, mentor engineers, and improve platform reliability across teams."

        const val COMPLETE_JSON =
            """{"jobTitle":"Senior Kotlin Engineer","company":"Acme Corp","location":"Remote",""" +
                """"employmentType":"Full-time","description":"We are looking for a Senior Kotlin Engineer """ +
                """to build reliable distributed systems. You will design APIs, mentor engineers, and """ +
                """improve platform reliability across teams.","responsibilities":["Build APIs"],""" +
                """"requiredSkills":["Kotlin"],"preferredSkills":["Kubernetes"],""" +
                """"educationRequired":"Bachelor's degree","experienceRequired":"5 years",""" +
                """"salaryRange":"","closingDate":"","contactInfo":""}"""

        const val JSON_WITHOUT_TITLE =
            """{"company":"Acme Corp","location":"Remote","employmentType":"Full-time",""" +
                """"description":"We are looking for a Senior Kotlin Engineer to build reliable """ +
                """distributed systems. You will design APIs, mentor engineers, and improve platform """ +
                """reliability across teams.","responsibilities":["Build APIs"],""" +
                """"requiredSkills":["Kotlin"],"preferredSkills":["Kubernetes"],""" +
                """"educationRequired":"Bachelor's degree","experienceRequired":"5 years",""" +
                """"salaryRange":"","closingDate":"","contactInfo":""}"""

        const val JSON_WITHOUT_COMPANY =
            """{"jobTitle":"Senior Kotlin Engineer","location":"Remote","employmentType":"Full-time",""" +
                """"description":"We are looking for a Senior Kotlin Engineer to build reliable """ +
                """distributed systems. You will design APIs, mentor engineers, and improve platform """ +
                """reliability across teams.","responsibilities":["Build APIs"],""" +
                """"requiredSkills":["Kotlin"],"preferredSkills":["Kubernetes"],""" +
                """"educationRequired":"Bachelor's degree","experienceRequired":"5 years",""" +
                """"salaryRange":"","closingDate":"","contactInfo":""}"""

        const val JOB_TEXT =
            "Senior Kotlin Engineer\nAcme Corp\nRemote\n" +
                "We are looking for a Senior Kotlin Engineer to build reliable distributed systems. " +
                "You will design APIs, mentor engineers, and improve platform reliability across teams."

        val HIGH_CONFIDENCE = mapOf(
            "jobTitle" to 0.9f,
            "company" to 0.9f,
            "description" to 0.85f
        )
    }

    private fun fieldResult(
        jobTitle: String? = "Senior Kotlin Engineer",
        company: String? = "Acme Corp",
        description: String? = LONG_DESCRIPTION,
        requiredSkills: List<String> = listOf("Kotlin", "Distributed Systems"),
        preferredSkills: List<String> = listOf("Kubernetes"),
        educationRequired: String? = "Bachelor's degree in Computer Science",
        fieldConfidence: Map<String, Float> = HIGH_CONFIDENCE,
        rawJson: String = COMPLETE_JSON
    ) = LlmJobExtractor.LlmFieldResult(
        jobTitle = jobTitle,
        company = company,
        location = "Remote",
        employmentType = "Full-time",
        description = description,
        responsibilities = listOf("Build APIs"),
        requiredSkills = requiredSkills,
        preferredSkills = preferredSkills,
        educationRequired = educationRequired,
        experienceRequired = "5 years",
        salaryRange = "",
        closingDate = "",
        contactInfo = "",
        fieldConfidence = fieldConfidence,
        rawJson = rawJson
    )

    private fun orchestrator(
        result: LlmJobExtractor.LlmFieldResult,
        decision: ExtractionDecision? = null
    ): JobExtractionOrchestrator =
        if (decision == null) {
            JobExtractionOrchestrator(
                aiGateway = null,
                extractionFnOverride = { _, _, _ -> result }
            )
        } else {
            JobExtractionOrchestrator(
                aiGateway = null,
                extractionFnOverride = { _, _, _ -> result },
                decisionEvaluator = { _, _, _ -> decision }
            )
        }

    private fun extract(result: LlmJobExtractor.LlmFieldResult): ExtractionOutcome = runBlocking {
        val outcome = orchestrator(result).extractJob(JOB_TEXT, JobSourceType.USER_INPUT).getOrThrow()
        outcome
    }

    @Test
    fun `failure decision is rejected and preserves diagnostic reason`() {
        val outcome = extract(fieldResult(fieldConfidence = emptyMap()))

        assertTrue("expected Rejected, got $outcome", outcome is ExtractionOutcome.Rejected)
        val rejected = outcome as ExtractionOutcome.Rejected
        assertTrue(rejected.reason.contains("EXTRACTION_DECISION_FAILURE"))
        assertTrue(rejected.reason.contains("UNRECOVERABLE_PARSE_ERROR"))
    }

    @Test
    fun `user review with p2 accept yields review required with true p2 accept decision`() {
        val outcome = extract(
            fieldResult(
                fieldConfidence = mapOf("jobTitle" to 0.3f, "company" to 0.9f, "description" to 0.85f),
                rawJson = JSON_WITHOUT_TITLE
            )
        )

        assertTrue("expected ReviewRequired, got $outcome", outcome is ExtractionOutcome.ReviewRequired)
        val review = outcome as ExtractionOutcome.ReviewRequired
        assertTrue(review.decision is ValidationDecision.Accept)
        assertEquals(1, review.extractionReview.size)
        assertEquals("jobTitle", review.extractionReview[0].fieldName)
        val overall = review.extractionReview[0].confidence.overallConfidence
        assertTrue(overall != null && overall < 0.5f)
        assertNotNull(review.extractionReview[0].reviewReason)
    }

    @Test
    fun `user review with p2 degraded yields review required preserving degraded decision and evidence`() {
        val outcome = extract(
            fieldResult(
                company = null,
                fieldConfidence = mapOf("jobTitle" to 0.9f, "company" to 0.3f, "description" to 0.85f),
                rawJson = JSON_WITHOUT_COMPANY
            )
        )

        assertTrue("expected ReviewRequired, got $outcome", outcome is ExtractionOutcome.ReviewRequired)
        val review = outcome as ExtractionOutcome.ReviewRequired
        assertTrue(review.decision is ValidationDecision.Degraded)
        assertEquals(listOf("company"), review.extractionReview.map { it.fieldName })
    }

    @Test
    fun `user review with p2 reject keeps rejection`() {
        val outcome = extract(
            fieldResult(
                description = "Too short.",
                fieldConfidence = mapOf("jobTitle" to 0.3f, "company" to 0.9f, "description" to 0.85f),
                rawJson = JSON_WITHOUT_TITLE
            )
        )

        assertTrue("expected Rejected, got $outcome", outcome is ExtractionOutcome.Rejected)
        assertTrue((outcome as ExtractionOutcome.Rejected).reason.isNotBlank())
    }

    @Test
    fun `accept decision continues through p2 and yields accepted`() {
        val outcome = extract(fieldResult())

        assertTrue("expected Accepted, got $outcome", outcome is ExtractionOutcome.Accepted)
        assertTrue((outcome as ExtractionOutcome.Accepted).decision is ValidationDecision.Accept)
    }

    @Test
    fun `structured requirements survive the enforced path`() {
        val outcome = extract(
            fieldResult(
                requiredSkills = listOf("Kotlin", "Distributed Systems", "PostgreSQL"),
                preferredSkills = listOf("Kubernetes", "Terraform"),
                educationRequired = "Master's degree"
            )
        )

        assertTrue(outcome is ExtractionOutcome.Accepted)
        val result = (outcome as ExtractionOutcome.Accepted).result
        assertEquals(listOf("Kotlin", "Distributed Systems", "PostgreSQL"), result.requiredSkills)
        assertEquals(listOf("Kubernetes", "Terraform"), result.preferredSkills)
        assertEquals("Master's degree", result.educationRequired)
    }

    @Test
    fun `null gateway still uses emergency path and never reports accepted`() {
        val outcome = runBlocking {
            JobExtractionOrchestrator(aiGateway = null).extractJob("", JobSourceType.USER_INPUT)
        }

        assertTrue(outcome.isFailure || outcome.getOrNull() !is ExtractionOutcome.Accepted)
    }

    // Injected-decision boundary test: ExtractionDecisionCoordinator cannot currently produce Retry,
    // so this proves orchestrator handling of an injected Retry decision, not production reachability.
    @Test
    fun `injected retry decision is rejected with unsupported diagnostic`() {
        val outcome = runBlocking {
            orchestrator(fieldResult(), ExtractionDecision.Retry(RetryReason.TIMEOUT))
                .extractJob(JOB_TEXT, JobSourceType.USER_INPUT)
                .getOrThrow()
        }

        assertTrue("expected Rejected, got $outcome", outcome is ExtractionOutcome.Rejected)
        val reason = (outcome as ExtractionOutcome.Rejected).reason
        assertTrue(reason.contains("RETRY_UNSUPPORTED"))
        assertTrue(reason.contains("TIMEOUT"))
        assertTrue(reason.contains("not supported by this pipeline"))
    }

    @Test
    fun `extraction review evidence survives outcome to state mapping without faking degradation`() {
        val review = extract(
            fieldResult(
                fieldConfidence = mapOf("jobTitle" to 0.3f, "company" to 0.9f, "description" to 0.85f),
                rawJson = JSON_WITHOUT_TITLE
            )
        ) as ExtractionOutcome.ReviewRequired

        val state = mapOutcomeToState(review) as JobExtractionState.SuccessWithWarning
        assertEquals(review.extractionReview, state.extractionReview)
        assertEquals(1, state.extractionReview.size)
        assertTrue("P2 Accept must not be represented as warnings", state.warnings.isEmpty())
    }

    @Test
    fun `p2 warnings and extraction review are preserved independently in state`() {
        val review = extract(
            fieldResult(
                company = null,
                fieldConfidence = mapOf("jobTitle" to 0.9f, "company" to 0.3f, "description" to 0.85f),
                rawJson = JSON_WITHOUT_COMPANY
            )
        ) as ExtractionOutcome.ReviewRequired

        val state = mapOutcomeToState(review) as JobExtractionState.SuccessWithWarning
        val degraded = review.decision as ValidationDecision.Degraded
        assertEquals(degraded.warnings, state.warnings)
        assertEquals(review.extractionReview, state.extractionReview)
        assertEquals(listOf("company"), state.extractionReview.map { it.fieldName })
    }
}
