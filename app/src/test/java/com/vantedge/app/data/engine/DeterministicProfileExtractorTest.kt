package com.vantedge.app.data.engine

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DeterministicProfileExtractorTest {

    private val sampleCv = """
        John Doe
        john.doe@example.com
        +27 82 123 4567

        Summary
        Experienced software engineer focused on mobile platforms.

        Skills
        Kotlin, Android, SQL

        Experience
        Software Engineer at Acme Corp
        Jan 2020 - Present
        Built and shipped mobile features.

        Education
        University of Cape Town
        BSc Computer Science 2018

        Certifications
        AWS Certified Developer
    """.trimIndent()

    @Test
    fun extractsRequiredFieldsFromSampleCv() {
        val json = DeterministicProfileExtractor.extractJson(sampleCv)
        assertNotNull("expected deterministic extraction to succeed", json)
        val o = JSONObject(json!!)
        assertEquals("john.doe@example.com", o.optString("email"))
        assertTrue("name should be captured", o.optString("name").contains("John"))
        assertTrue("skills should not be empty", o.optJSONArray("skills")!!.length() > 0)
    }

    @Test
    fun returnsNullWhenEmailMissing() {
        val noEmail = """
            John Doe
            Skills
            Kotlin
        """.trimIndent()
        assertNull(DeterministicProfileExtractor.extractJson(noEmail))
    }

    @Test
    fun returnsNullWhenNameMissing() {
        val noName = """
            john.doe@example.com
            Skills
            Kotlin
        """.trimIndent()
        assertNull(DeterministicProfileExtractor.extractJson(noName))
    }
}
