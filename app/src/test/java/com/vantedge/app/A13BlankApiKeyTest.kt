package com.vantedge.app

import com.vantedge.app.data.model.AiRawResponseArtifact
import com.vantedge.app.data.network.AiRequest
import com.vantedge.app.data.network.GeminiService
import com.vantedge.app.data.network.ModelAttemptResult
import com.vantedge.app.data.storage.AiRawResponseArtifactDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class A13BlankApiKeyTest {

    private val dao = object : AiRawResponseArtifactDao {
        override suspend fun insert(artifact: AiRawResponseArtifact): Long = 0L
        override suspend fun deleteOldestBeyondLimit(limit: Int) {}
        override suspend fun getAll(): List<AiRawResponseArtifact> = emptyList()
    }

    @Test
    fun blankKeyReturnsConfigErrorAndNull() = runBlocking {
        val service = GeminiService(dao, CoroutineScope(Dispatchers.Unconfined))
        var callbackResult: ModelAttemptResult? = null
        val ret = service.generate(
            requestId = "REQ-000001",
            pipeline = "test",
            request = AiRequest("sys", "user"),
            onModelResult = { callbackResult = it },
            budgetDeadlineMs = 60_000L
        )
        assertNull(ret)
        val cr = callbackResult
        checkNotNull(cr) { "no ModelAttemptResult callback received" }
        assertEquals("CONFIG_ERROR", cr.failureType)
        assertEquals("none", cr.model)
        assertEquals(0, cr.attempt)
        assertEquals("configuration", cr.failureStage)
        assertEquals(null, cr.httpCode)
        assertEquals(0L, cr.durationMs)
        assertEquals(null, cr.latencyMs)
        assertEquals(null, cr.bodyPreview)
        assertEquals("API key is not configured", cr.detail)
        assertEquals(null, cr.cancelReason)
    }
}
