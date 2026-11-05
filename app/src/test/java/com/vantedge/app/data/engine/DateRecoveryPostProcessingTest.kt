package com.vantedge.app.data.engine

import android.content.ContextWrapper
import com.vantedge.app.data.model.AiRawResponseArtifact
import com.vantedge.app.data.model.WorkExperience
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.network.GeminiService
import com.vantedge.app.data.storage.AiRawResponseArtifactDao
import com.vantedge.app.util.TelemetryCollector
import com.vantedge.app.w5.scoring.computeYearsExperience
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Production-path date-authority tests.
 *
 * These tests call the real ProfileExtractionEngine.normalizeRootKeys() used by
 * production ingestion. They intentionally do not simulate or duplicate the
 * production normalization logic.
 */
class DateRecoveryPostProcessingTest {

    private val engine: ProfileExtractionEngine by lazy {
        val context = object : ContextWrapper(null) {
            override fun getCacheDir(): File = File(System.getProperty("java.io.tmpdir") ?: "/tmp")
        }
        val dao = object : AiRawResponseArtifactDao {
            override suspend fun insert(artifact: AiRawResponseArtifact): Long = 0L
            override suspend fun deleteOldestBeyondLimit(limit: Int) {}
            override suspend fun getAll(): List<AiRawResponseArtifact> = emptyList()
        }
        val geminiService = GeminiService(dao, CoroutineScope(Dispatchers.Unconfined))
        ProfileExtractionEngine(context, AiGateway(geminiService), TelemetryCollector(context))
    }

    private fun normalizeWork(entry: JSONObject): JSONObject {
        val root = JSONObject().apply { put("workHistory", JSONArray().apply { put(entry) }) }
        return engine.normalizeRootKeys(root).getJSONArray("workHistory").getJSONObject(0)
    }

    private fun work(
        startDate: String? = null,
        endDate: String? = null,
        dates: String? = null,
        period: String? = null,
        description: String? = null
    ): JSONObject = JSONObject().apply {
        put("jobTitle", "Engineer")
        put("company", "Acme")
        startDate?.let { put("startDate", it) }
        endDate?.let { put("endDate", it) }
        dates?.let { put("dates", it) }
        period?.let { put("period", it) }
        description?.let { put("description", it) }
    }

    @Test
    fun `blank canonical dates remain blank when only dates is supplied`() {
        val out = normalizeWork(work(startDate = "", endDate = "", dates = "2020-2024"))

        assertEquals("", out.optString("startDate"))
        assertEquals("", out.optString("endDate"))
        assertFalse(out.has("dates"))
        assertFalse(out.has("period"))
    }

    @Test
    fun `blank canonical dates remain blank when only period is supplied`() {
        val out = normalizeWork(work(startDate = "", endDate = "", period = "Jan 2020 - Present"))

        assertEquals("", out.optString("startDate"))
        assertEquals("", out.optString("endDate"))
        assertFalse(out.has("dates"))
        assertFalse(out.has("period"))
    }

    @Test
    fun `blank startDate keeps explicit endDate when dates is supplied`() {
        val out = normalizeWork(work(startDate = "", endDate = "2022", dates = "2020-2024"))

        assertEquals("", out.optString("startDate"))
        assertEquals("2022", out.optString("endDate"))
    }

    @Test
    fun `blank startDate keeps explicit endDate when period is supplied`() {
        val out = normalizeWork(work(startDate = "", endDate = "2022", period = "Jan 2020 - Present"))

        assertEquals("", out.optString("startDate"))
        assertEquals("2022", out.optString("endDate"))
    }

    @Test
    fun `nonblank canonical dates remain unchanged when conflicting compatibility fields are present`() {
        val out = normalizeWork(
            work(
                startDate = "Jan 2020",
                endDate = "Present",
                dates = "2015-2018",
                period = "2019-2021"
            )
        )

        assertEquals("Jan 2020", out.optString("startDate"))
        assertEquals("Present", out.optString("endDate"))
        assertFalse(out.has("dates"))
        assertFalse(out.has("period"))
    }

    @Test
    fun `valid canonical dates are copied independently and unchanged`() {
        val out = normalizeWork(work(startDate = "2018", endDate = "2021"))

        assertEquals("2018", out.optString("startDate"))
        assertEquals("2021", out.optString("endDate"))
    }

    @Test
    fun `completed combined range cannot become an open-ended canonical employment period`() {
        val out = normalizeWork(work(startDate = "", endDate = "", dates = "2020-2024"))
        val start = out.optString("startDate")
        val end = out.optString("endDate")

        assertEquals("", start)
        assertEquals("", end)

        // Focused W5 regression: the actual normalized canonical values above are
        // passed into the existing experience calculation path. They must not be
        // interpreted as a 2020..2026 open-ended employment interval.
        val years = computeYearsExperience(
            listOf(WorkExperience("Engineer", "Acme", start, end, "")),
            2026
        )
        assertEquals(0, years)
    }

    @Test
    fun `description years are not mined into canonical dates`() {
        val out = normalizeWork(
            work(
                startDate = "",
                endDate = "",
                description = "Company founded in 1998, project completed 2015"
            )
        )

        assertEquals("", out.optString("startDate"))
        assertEquals("", out.optString("endDate"))
    }
}
