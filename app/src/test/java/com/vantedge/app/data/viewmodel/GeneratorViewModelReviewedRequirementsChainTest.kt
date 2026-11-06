package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.domain.OptimizationOrchestrator
import com.vantedge.app.data.engine.CompatibilityEngine
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.engine.ReviewedJobRequirements
import com.vantedge.app.data.model.ConfidenceBreakdown
import com.vantedge.app.data.model.Gate0JobReason
import com.vantedge.app.data.model.Gate0JobResult
import com.vantedge.app.data.model.JobExtractionMetrics
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.JobSourceType
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.model.WorkExperience
import com.vantedge.app.data.network.AiRequest
import com.vantedge.app.data.network.AiTextGateway
import com.vantedge.app.data.storage.CycleDao
import com.vantedge.app.data.storage.CycleEntity
import com.vantedge.app.data.storage.HistoryStore
import com.vantedge.app.domain.extraction.ExtractionOutcome
import com.vantedge.pipeline.contract.ContractValidator
import com.vantedge.pipeline.validation.ValidationDecision
import com.vantedge.pipeline.validation.ValidationTrace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GeneratorViewModelReviewedRequirementsChainTest {

    private data class Call(val pipeline: String, val request: AiRequest)

    private class RecordingGateway : AiTextGateway {
        val calls = mutableListOf<Call>()

        override suspend fun generate(pipeline: String, request: AiRequest, budgetMs: Long): String? {
            calls += Call(pipeline, request)
            return when (pipeline) {
                "cv" -> CV_FIXTURE
                "cover_letter" -> COVER_FIXTURE
                "cv_docx" -> DOCX_FIXTURE
                else -> error("unexpected pipeline: $pipeline")
            }
        }

        fun prompt(pipeline: String): String =
            calls.single { it.pipeline == pipeline }.request.userPrompt
    }

    private class FakeCycleDao : CycleDao {
        private val flow = MutableStateFlow<List<CycleEntity>>(emptyList())

        override suspend fun insertCycle(cycle: CycleEntity) {
            flow.value = listOf(cycle) + flow.value
        }

        override fun getAllCycles(): Flow<List<CycleEntity>> = flow

        override suspend fun getCycleById(id: String): CycleEntity? =
            flow.value.firstOrNull { it.id == id }

        override suspend fun deleteCycleById(id: String) {
            flow.value = flow.value.filterNot { it.id == id }
        }
    }

    private class Harness(val gateway: RecordingGateway, val vm: GeneratorViewModel)

    private fun TestScope.harness(): Harness {
        val gateway = RecordingGateway()
        val historyStore = HistoryStore(FakeCycleDao())
        val orchestrator = OptimizationOrchestrator(
            aiGateway = gateway,
            contractValidator = ContractValidator(),
            compatibilityEngine = CompatibilityEngine(),
            generatorEngine = GeneratorEngine(gateway),
            historyStore = historyStore
        )
        val vm = GeneratorViewModel(
            historyStore = historyStore,
            orchestrator = orchestrator,
            workerDispatcher = StandardTestDispatcher(testScheduler)
        )
        return Harness(gateway, vm)
    }

    private fun profile() = UserProfile(
        name = "Candidate Name",
        email = "candidate@example.com",
        phone = "123",
        location = "Remote",
        linkedIn = "linkedin.com/in/candidate",
        summary = "Candidate summary",
        skills = listOf("CandidateSkill", "Kotlin"),
        workHistory = listOf(
            WorkExperience(
                role = "Engineer",
                company = "CandidateCo",
                startDate = "2020",
                endDate = "2022",
                description = "Built candidate systems"
            )
        ),
        education = listOf("CandidateDegree")
    )

    private fun corrected(
        requiredSkills: List<String> = listOf("Kotlin", "Android"),
        preferredSkills: List<String> = listOf("Compose"),
        educationRequired: String? = "Bachelor's degree"
    ) = JobExtractionResult(
        extractionId = "ext-reviewed",
        extractedAt = 1L,
        jobTitle = "Senior Engineer",
        company = "Acme",
        description = "Build Android software.",
        requiredSkills = requiredSkills,
        preferredSkills = preferredSkills,
        confidenceScore = 0.62f,
        confidenceBreakdown = ConfidenceBreakdown(
            baseScore = 0.5f,
            titleContribution = 0.05f,
            companyContribution = 0.05f,
            qualificationContribution = 0.02f,
            penalties = emptyList(),
            finalScore = 0.62f
        ),
        gate0Result = Gate0JobResult(
            score = 3,
            threshold = 2,
            accepted = true,
            reason = Gate0JobReason.ACCEPTED,
            detectedSignals = emptyList(),
            appliedPenalties = emptyList(),
            rejectionCauses = emptyList(),
            narrativeDensity = 0.2f
        ),
        metrics = JobExtractionMetrics(
            durationMs = 1L,
            sourceType = JobSourceType.USER_INPUT,
            rawTextLength = 40,
            gate0DurationMs = 0,
            gate1DurationMs = 0,
            gate2DurationMs = 0,
            gate3DurationMs = 0,
            gate4DurationMs = 0,
            qualificationPassed = true,
            narrativeDensity = 0.2f
        ),
        educationRequired = educationRequired
    )

    private fun review(result: JobExtractionResult): ExtractionOutcome.ReviewRequired =
        ExtractionOutcome.ReviewRequired(
            result = result,
            decision = ValidationDecision.Degraded(
                ValidationTrace(pipelineStage = "P2", correlationId = "verify-03"),
                emptyList()
            )
        )

    private fun acknowledged(vm: GeneratorViewModel, result: JobExtractionResult = corrected()) {
        vm.presentExtractionOutcome(review(result))
        vm.acknowledgeExtractionReview(result)
    }

    private fun expectedRequirements() = ReviewedJobRequirements(
        requiredSkills = listOf("Kotlin", "Android"),
        preferredSkills = listOf("Compose"),
        educationRequired = "Bachelor's degree"
    )

    private fun assertReviewedBlock(prompt: String) {
        assertTrue(prompt, prompt.contains("JOB REQUIREMENTS (user-reviewed; role requirements, not candidate qualifications):"))
        assertTrue(prompt, prompt.contains("Required Skills: Kotlin, Android"))
        assertTrue(prompt, prompt.contains("Preferred Skills: Compose"))
        assertTrue(prompt, prompt.contains("Education Requirement: Bachelor's degree"))
    }

    private fun assertNoReviewedBlock(prompt: String) {
        assertFalse(prompt, prompt.contains("JOB REQUIREMENTS (user-reviewed"))
        assertFalse(prompt, prompt.contains("Required Skills: Kotlin, Android"))
    }

    @Test
    fun `T1 CV prompt contains exact acknowledged reviewed requirements`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "html"
            )
            advanceUntilIdle()

            assertReviewedBlock(h.gateway.prompt("cv"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T2 cover letter prompt contains exact acknowledged reviewed requirements`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "html"
            )
            advanceUntilIdle()

            assertReviewedBlock(h.gateway.prompt("cover_letter"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T3 DOCX prompt contains exact acknowledged reviewed requirements`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "docx"
            )
            advanceUntilIdle()

            assertReviewedBlock(h.gateway.prompt("cv_docx"))
            assertEquals(listOf("cv_docx"), h.gateway.calls.map { it.pipeline })
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T4 reviewed job requirements stay distinct from candidate qualifications`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "docx"
            )
            advanceUntilIdle()

            val prompt = h.gateway.prompt("cv_docx")
            assertReviewedBlock(prompt)
            val profileSection = prompt.substringBefore("JOB DESCRIPTION:")
            assertTrue(profileSection, profileSection.contains("Skills: CandidateSkill, Kotlin"))
            assertTrue(profileSection, profileSection.contains("Education: CandidateDegree"))
            assertFalse(
                prompt.substringAfter("JOB DESCRIPTION:"),
                prompt.substringAfter("JOB DESCRIPTION:").contains("CandidateDegree")
            )
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T5 null and empty requirements preserve previous prompt behavior`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val nullHarness = harness()
            nullHarness.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "html"
            )
            advanceUntilIdle()
            val nullPrompt = nullHarness.gateway.prompt("cv")

            val emptyHarness = harness()
            val empty = corrected(
                requiredSkills = emptyList(),
                preferredSkills = emptyList(),
                educationRequired = null
            )
            acknowledged(emptyHarness.vm, empty)
            emptyHarness.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "html"
            )
            advanceUntilIdle()
            val emptyPrompt = emptyHarness.gateway.prompt("cv")

            assertNoReviewedBlock(nullPrompt)
            assertNoReviewedBlock(emptyPrompt)
            assertEquals(nullPrompt, emptyPrompt)
            assertTrue(emptyHarness.vm.reviewedRequirements!!.isEmpty)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T6 acknowledgment owns the exact corrected values consumed by generation`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            val result = corrected()
            acknowledged(h.vm, result)

            assertEquals(expectedRequirements(), h.vm.reviewedRequirements)

            h.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "html"
            )
            advanceUntilIdle()

            assertReviewedBlock(h.gateway.prompt("cv"))
            assertReviewedBlock(h.gateway.prompt("cover_letter"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T7 acknowledged requirements remain when UI is recreated with ViewModel retained`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)

            val retainedViewModel = h.vm
            assertEquals(expectedRequirements(), retainedViewModel.reviewedRequirements)

            retainedViewModel.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "docx"
            )
            advanceUntilIdle()

            assertReviewedBlock(h.gateway.prompt("cv_docx"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T8 new job reset cannot reuse prior reviewed requirements`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.resetState()
            assertNull(h.vm.reviewedRequirements)

            h.vm.generate(
                profile = profile(),
                jobTitle = "Different Role",
                company = "DifferentCo",
                jobDescription = "A different job.",
                mode = "html"
            )
            advanceUntilIdle()

            assertNoReviewedBlock(h.gateway.prompt("cv"))
            assertNoReviewedBlock(h.gateway.prompt("cover_letter"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T8b new unreviewed extraction clears prior acknowledged requirements`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.presentExtractionOutcome(
                review(
                    corrected(
                        requiredSkills = listOf("Swift"),
                        preferredSkills = listOf("iOS"),
                        educationRequired = "Master's degree"
                    )
                )
            )

            assertNull(h.vm.reviewedRequirements)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T8c rejected extraction clears prior acknowledged requirements`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.presentExtractionOutcome(
                ExtractionOutcome.Rejected(
                    reason = "rejected",
                    traceId = "verify-03-rejected"
                )
            )

            assertNull(h.vm.reviewedRequirements)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `T9 only the fake transport receives generation requests`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness()
            acknowledged(h.vm)
            h.vm.generate(
                profile = profile(),
                jobTitle = "Senior Engineer",
                company = "Acme",
                jobDescription = "Build Android software.",
                mode = "html"
            )
            advanceUntilIdle()

            assertEquals(listOf("cv", "cover_letter"), h.gateway.calls.map { it.pipeline })
            assertTrue(h.gateway.calls.all { it.request.userPrompt.isNotBlank() })
        } finally {
            Dispatchers.resetMain()
        }
    }

    companion object {
        private const val CV_FIXTURE =
            """{"matchedKeywords":["Kotlin"],"relevantSummary":"Tailored summary","relevantExperience":["Built systems"]}"""
        private const val COVER_FIXTURE =
            """{"subject":"Application","greeting":"Dear Hiring Manager","body":["Body"],"closing":"Sincerely","signature":"Candidate Name"}"""
        private const val DOCX_FIXTURE = "DOCX CONTENT"
    }
}
