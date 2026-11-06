package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.domain.GenerationOrchestrator
import com.vantedge.app.data.domain.PipelineStep
import com.vantedge.app.data.model.DesignConfig
import com.vantedge.app.data.model.GenerationCycle
import com.vantedge.app.data.model.GenerationMode
import com.vantedge.app.data.model.GenerationRequest
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.storage.CycleDao
import com.vantedge.app.data.storage.CycleEntity
import com.vantedge.app.data.storage.HistoryStore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Deterministic JVM verification of the Amendment C (R1.2) historical-Improve
 * handoff lifecycle. All coroutines run on a single TestCoroutineScheduler via
 * Dispatchers.setMain + an injected worker dispatcher; no sleeps or wall-clock
 * timing are used.
 */
class CycleViewModelHandoffLifecycleTest {

    private class FakeCycleDao : CycleDao {
        val inserted = mutableListOf<CycleEntity>()
        private val flow = MutableStateFlow<List<CycleEntity>>(emptyList())
        override suspend fun insertCycle(cycle: CycleEntity) {
            inserted += cycle
            flow.value = inserted.toList()
        }
        override fun getAllCycles(): Flow<List<CycleEntity>> = flow
        override suspend fun getCycleById(id: String): CycleEntity? = inserted.firstOrNull { it.id == id }
        override suspend fun deleteCycleById(id: String) { inserted.removeAll { it.id == id } }
    }

    /** Fake that gates each generation call on an externally completed deferred. */
    private class FakeOrchestrator : GenerationOrchestrator {
        val calls = mutableListOf<GenerationCycle>()
        val gates = mutableListOf<CompletableDeferred<GenerationCycle>>()
        override suspend fun runGenerationFromCycle(cycle: GenerationCycle, improvementContext: String?): GenerationCycle {
            calls += cycle
            val gate = CompletableDeferred<GenerationCycle>()
            gates += gate
            return gate.await()
        }
        override suspend fun runAnalysisOnly(
            profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
            improvementContext: String?, requiredSkills: List<String>,
            preferredSkills: List<String>, educationRequired: String?
        ): GenerationCycle = error("runAnalysisOnly not used in lifecycle tests")
        override suspend fun runFullPipeline(
            profile: UserProfile, jobTitle: String, company: String, jobDescription: String,
            mode: GenerationMode, improvementContext: String?, onProgress: (PipelineStep) -> Unit,
            requiredSkills: List<String>, preferredSkills: List<String>,
            educationRequired: String?, request: GenerationRequest
        ): GenerationCycle = error("runFullPipeline not used in lifecycle tests")
        override suspend fun applyDesign(cycleId: String, design: DesignConfig): GenerationCycle =
            error("applyDesign not used in lifecycle tests")
    }

    private class Harness(val fake: FakeOrchestrator, val dao: FakeCycleDao, val vm: CycleViewModel)

    private fun harness(scope: TestScope): Harness {
        val fake = FakeOrchestrator()
        val dao = FakeCycleDao()
        val worker: CoroutineDispatcher = StandardTestDispatcher(scope.testScheduler)
        val vm = CycleViewModel(
            orchestrator = fake,
            historyStore = HistoryStore(dao),
            workerDispatcher = worker
        )
        return Harness(fake, dao, vm)
    }

    private fun cycle(cid: String, cv: String? = null, cl: String? = null,
                      cvErr: String? = null, clErr: String? = null): GenerationCycle =
        GenerationCycle(
            id = cid, jobTitle = "Engineer", company = "Acme", jobDescription = "JD",
            profileSnapshot = UserProfile(name = "Jane", email = "jane@example.com"),
            cvContent = cv, coverLetterContent = cl,
            cvErrorMessage = cvErr, coverLetterErrorMessage = clErr
        )

    private fun failed(cid: String) = cycle(cid, cv = "", cl = "", cvErr = "provider", clErr = "provider")
    private fun partial(cid: String) = cycle(cid, cv = "{}", cl = "", clErr = "provider")
    private fun succeeded(cid: String) = cycle(cid, cv = "{}", cl = "Dear Hiring Manager")
    private fun indeterminate(cid: String) = cycle(cid)

    // 1. Generation finishes before claim: feedback is retained and delivered once.
    @Test fun generationFinishesBeforeClaim_feedbackRetainedAndConsumedOnce() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("H1")
            h.vm.startImproveFromCycle(cycle("c1"), "H1")
            advanceUntilIdle()
            assertNull("no feedback published before generation completes", h.vm.generationFeedback.value)

            h.fake.gates[0].complete(failed("c1"))
            advanceUntilIdle()

            val fb = h.vm.generationFeedback.value
            assertNotNull("feedback retained when generation completes before claim", fb)
            assertEquals("H1", fb!!.handoffId)
            assertEquals(1L, fb.operationId)

            val claimed = h.vm.claimImproveHandoff()
            assertNotNull("destination claims the retained reservation", claimed)
            assertEquals("H1", claimed!!.handoffId)
            assertEquals(1L, claimed.operationId)

            h.vm.acknowledgeGenerationFeedback(claimed, fb.feedbackId)
            assertNull("acknowledgement consumes the single feedback record", h.vm.generationFeedback.value)
            advanceUntilIdle()
            assertNull("consumed feedback is not re-published", h.vm.generationFeedback.value)
        } finally { Dispatchers.resetMain() }
    }

    // 2. Destination claims before generation finishes: feedback delivered once.
    @Test fun claimBeforeGenerationFinishes_feedbackDeliveredOnce() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("H2")
            h.vm.startImproveFromCycle(cycle("c2"), "H2")
            advanceUntilIdle()

            val claimed = h.vm.claimImproveHandoff()
            assertNotNull(claimed)
            assertEquals("H2", claimed!!.handoffId)

            h.fake.gates[0].complete(failed("c2"))
            advanceUntilIdle()

            val fb = h.vm.generationFeedback.value
            assertNotNull(fb)
            assertEquals("H2", fb!!.handoffId)
            assertEquals(1L, fb.operationId)
        } finally { Dispatchers.resetMain() }
    }

    // 3. Released ownership cannot receive later feedback.
    @Test fun releasedOwnership_cannotReceiveLaterFeedback() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("H3")
            h.vm.startImproveFromCycle(cycle("c3"), "H3")
            advanceUntilIdle()

            val claimed = h.vm.claimImproveHandoff()
            assertNotNull(claimed)
            h.vm.releaseImproveSession(claimed)   // destination disposed during generation

            h.fake.gates[0].complete(failed("c3"))
            advanceUntilIdle()

            assertNull("released owner receives no later feedback", h.vm.generationFeedback.value)
        } finally { Dispatchers.resetMain() }
    }

    // 4. Superseded operation cannot publish stale UI or clear newer feedback.
    @Test fun supersededOperation_cannotPublishOrClearNewerFeedback() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("H1")
            h.vm.startImproveFromCycle(cycle("c1"), "H1")
            advanceUntilIdle()

            h.vm.registerImproveHandoff("H2")
            h.vm.startImproveFromCycle(cycle("c2"), "H2")
            advanceUntilIdle()

            // Newer operation completes first and publishes.
            h.fake.gates[1].complete(failed("c2"))
            advanceUntilIdle()
            assertEquals("H2", h.vm.generationFeedback.value?.handoffId)
            assertEquals("c2", h.vm.currentCycle?.id)

            // Older operation completes later: must neither publish nor clear newer feedback.
            h.fake.gates[0].complete(failed("c1"))
            advanceUntilIdle()
            assertEquals("newer feedback survives", "H2", h.vm.generationFeedback.value?.handoffId)
            assertEquals("older op did not overwrite newer cycle", "c2", h.vm.currentCycle?.id)
        } finally { Dispatchers.resetMain() }
    }

    // 5. Stale destination release/acknowledgement cannot affect a newer handoff.
    @Test fun staleDestination_cannotReleaseOrAcknowledgeNewerHandoff() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("H1")
            h.vm.startImproveFromCycle(cycle("c1"), "H1")
            advanceUntilIdle()
            val rec1 = h.vm.claimImproveHandoff()
            assertNotNull(rec1)

            h.vm.registerImproveHandoff("H2")
            h.vm.startImproveFromCycle(cycle("c2"), "H2")
            advanceUntilIdle()
            val rec2 = h.vm.claimImproveHandoff()
            assertNotNull(rec2)

            h.fake.gates[1].complete(failed("c2"))
            advanceUntilIdle()
            val fb2 = h.vm.generationFeedback.value
            assertNotNull(fb2)

            h.vm.releaseImproveSession(rec1)                       // stale release
            assertNotNull("stale release must not clear newer feedback", h.vm.generationFeedback.value)

            h.vm.acknowledgeGenerationFeedback(rec1!!, fb2!!.feedbackId)  // stale ack
            assertNotNull("stale ack must not clear newer feedback", h.vm.generationFeedback.value)

            h.vm.acknowledgeGenerationFeedback(rec2!!, fb2.feedbackId)    // correct owner
            assertNull("matching owner acknowledgement clears the record", h.vm.generationFeedback.value)
        } finally { Dispatchers.resetMain() }
    }

    // 6a. Successful outcome publishes no feedback (feedback-only for partial/failure).
    @Test fun successfulOutcome_publishesNoFeedback() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("HS")
            h.vm.startImproveFromCycle(cycle("cs"), "HS")
            advanceUntilIdle()
            h.fake.gates[0].complete(succeeded("cs"))
            advanceUntilIdle()
            assertNull(h.vm.generationFeedback.value)
            assertEquals(GenerationStatus.SUCCESS, (h.vm.uiState.value as UiState.Content).data.status)
        } finally { Dispatchers.resetMain() }
    }

    // 6b. INDETERMINATE is neither success nor a fabricated failure: no feedback, no failure.
    @Test fun indeterminateOutcome_isNotSuccessNorFailure() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("HI")
            h.vm.startImproveFromCycle(cycle("ci"), "HI")
            advanceUntilIdle()
            h.fake.gates[0].complete(indeterminate("ci"))
            advanceUntilIdle()
            assertNull("INDETERMINATE produces no feedback", h.vm.generationFeedback.value)
            val state = h.vm.uiState.value
            assertEquals(GenerationStatus.INDETERMINATE, (state as UiState.Content).data.status)
        } finally { Dispatchers.resetMain() }
    }

    // 6c. PARTIAL and FAILURE produce feedback only (PARTIAL/FAILED kinds).
    @Test fun partialAndFailureOutcomes_produceFeedbackOnly() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("HP")
            h.vm.startImproveFromCycle(cycle("cp"), "HP")
            advanceUntilIdle()
            h.fake.gates[0].complete(partial("cp"))
            advanceUntilIdle()
            assertEquals(GenerationFeedbackKind.PARTIAL, h.vm.generationFeedback.value?.kind)

            h.vm.registerImproveHandoff("HF")
            h.vm.startImproveFromCycle(cycle("cf"), "HF")
            advanceUntilIdle()
            h.fake.gates[1].complete(failed("cf"))
            advanceUntilIdle()
            assertEquals(GenerationFeedbackKind.FAILED, h.vm.generationFeedback.value?.kind)
        } finally { Dispatchers.resetMain() }
    }

    // 7. Commit invalidates obsolete UI ownership while persistence stays independently scheduled.
    @Test fun commitDuringGeneration_invalidatesObsoleteOwnership_persistenceIndependent() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("HC")
            h.vm.startImproveFromCycle(cycle("cc"), "HC")
            advanceUntilIdle()

            h.vm.commitCurrentCycle()
            assertEquals("persistence is not performed synchronously on the commit call",
                0, h.dao.inserted.size)
            advanceUntilIdle()
            assertEquals("persistence executed on its own scheduled path", 1, h.dao.inserted.size)

            // The committed cycle has no error fields; the suppressed worker result did.
            assertNull((h.vm.currentCycle ?: error("cycle")).cvErrorMessage)

            h.fake.gates[0].complete(failed("cc"))
            advanceUntilIdle()
            assertNull("obsolete op cannot publish feedback after commit", h.vm.generationFeedback.value)
            assertNull("obsolete op cannot overwrite the committed cycle", h.vm.currentCycle?.cvErrorMessage)
        } finally { Dispatchers.resetMain() }
    }

    // 7b. Dismiss invalidates obsolete UI ownership while persistence stays independently scheduled.
    @Test fun dismissDuringGeneration_invalidatesObsoleteOwnership_persistenceIndependent() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val h = harness(this)
            h.vm.registerImproveHandoff("HD")
            h.vm.startImproveFromCycle(cycle("cd"), "HD")
            advanceUntilIdle()

            h.vm.dismissCurrentCycle()
            assertEquals(0, h.dao.inserted.size)
            advanceUntilIdle()
            assertEquals(1, h.dao.inserted.size)

            h.fake.gates[0].complete(failed("cd"))
            advanceUntilIdle()
            assertNull(h.vm.generationFeedback.value)
            assertEquals(false, h.vm.currentCycle?.isVisibleInHistory)
        } finally { Dispatchers.resetMain() }
    }
}
