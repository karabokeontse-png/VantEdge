package com.vantedge.app.data.engine

import android.content.ContextWrapper
import com.vantedge.app.data.model.AiRawResponseArtifact
import com.vantedge.app.data.model.WorkExperience
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.GeminiService
import com.vantedge.app.data.storage.AiRawResponseArtifactDao
import com.vantedge.app.util.TelemetryCollector
import com.vantedge.app.w5.scoring.computeYearsExperience
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Production-path tests for ProfileExtractionEngine.normalizeRootKeys().
 *
 * Invokes the real normalization function (made `internal` for testing). The instance is
 * constructed through the production constructor; normalizeRootKeys() does not read any
 * constructor dependency, so a JVM-safe Context and a minimal work-history fake suffice.
 *
 * These tests assert authoritative date-field precedence and that the undocumented
 * "dates"/"period" compatibility fields cannot populate or overwrite date fields.
 */
class NormalizeRootKeysDateAuthorityTest {

    private fun engine(): ProfileExtractionEngine {
        val context = ContextWrapper(null)
        val dao = object : AiRawResponseArtifactDao {
            override suspend fun insert(artifact: AiRawResponseArtifact): Long = 0L
            override suspend fun deleteOldestBeyondLimit(limit: Int) {}
            override suspend fun getAll(): List<AiRawResponseArtifact> = emptyList()
        }
        val geminiService = GeminiService(dao, CoroutineScope(Dispatchers.Unconfined))
        return ProfileExtractionEngine(context, AiGateway(geminiService), TelemetryCollector(context))
    }

    private fun normalizeWork(entry: JSONObject): JSONObject {
        val root = JSONObject().apply { put("workHistory", JSONArray().apply { put(entry) }) }
        return engine().normalizeRootKeys(root).getJSONArray("workHistory").getJSONObject(0)
    }

    private fun work(
        jobTitle: String = "Engineer",
        company: String = "Acme",
        startDate: String? = null,
        endDate: String? = null,
        dates: String? = null,
        period: String? = null
    ): JSONObject = JSONObject().apply {
        put("jobTitle", jobTitle)
        put("company", company)
        startDate?.let { put("startDate", it) }
        endDate?.let { put("endDate", it) }
        dates?.let { put("dates", it) }
        period?.let { put("period", it) }
    }

    @Test
    fun `case 1 blank startDate keeps explicit endDate and ignores dates`() {
        val out = normalizeWork(work(startDate = "", endDate = "2022", dates = "2020-2024"))
        assertEquals("", out.optString("startDate"))
        assertEquals("2022", out.optString("endDate"))
    }

    @Test
    fun `case 2 blank authoritative fields stay blank when only dates present`() {
        val out = normalizeWork(work(startDate = "", endDate = "", dates = "2020-2024"))
        assertEquals("", out.optString("startDate"))
        assertEquals("", out.optString("endDate"))
    }

    @Test
    fun `case 3 blank authoritative fields stay blank when only period present`() {
        val out = normalizeWork(work(startDate = "", endDate = "", period = "Jan 2020 - Present"))
        assertEquals("", out.optString("startDate"))
        assertEquals("", out.optString("endDate"))
    }

    @Test
    fun `case 4 nonblank authoritative dates are not overwritten by conflicting compatibility fields`() {
        val out = normalizeWork(
            work(startDate = "Jan 2020", endDate = "Present", dates = "2015-2018", period = "2019-2021")
        )
        assertEquals("Jan 2020", out.optString("startDate"))
        assertEquals("Present", out.optString("endDate"))
    }

    @Test
    fun `case 5 valid explicit dates without compatibility fields are preserved`() {
        val out = normalizeWork(work(startDate = "2018", endDate = "2021"))
        assertEquals("2018", out.optString("startDate"))
        assertEquals("2021", out.optString("endDate"))
    }

    @Test
    fun `combined compatibility range cannot become an open-ended startDate inflating experience`() {
        val out = normalizeWork(work(startDate = "", endDate = "", dates = "2020-2024"))
        val start = out.optString("startDate")
        val end = out.optString("endDate")
        assertEquals("", start)
        assertEquals("", end)

        val years = computeYearsExperience(
            listOf(WorkExperience("Engineer", "Acme", start, end, "")),
            2026
        )
        assertEquals(0, years)
    }
}
