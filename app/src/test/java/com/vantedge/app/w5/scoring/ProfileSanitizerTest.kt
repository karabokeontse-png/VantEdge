package com.vantedge.app.w5.scoring

import com.vantedge.app.data.model.Certification
import com.vantedge.app.data.model.UserProfile
import org.junit.Assert.*
import org.junit.Test

class ProfileSanitizerTest {

    @Test
    fun `R5-skill detects mation C-Java as OCR artifact`() {
        val profile = UserProfile(
            skills = listOf("mation C/Java", "MS SQL Server", "GABS Administration")
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(2, result.skills.size)
        assertTrue(result.skills.contains("MS SQL Server"))
        assertTrue(result.skills.contains("GABS Administration"))
        assertFalse(result.skills.contains("mation C/Java"))

        assertEquals(1, result.excluded.size)
        assertEquals("mation C/Java", result.excluded[0].token)
        assertEquals("R5", result.excluded[0].ruleId)
    }

    @Test
    fun `R5-skill detects Contrdl as OCR artifact`() {
        val profile = UserProfile(
            skills = listOf("Rok-based Access Contrdl", "Python")
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(1, result.skills.size)
        assertEquals("Python", result.skills[0])

        assertEquals(1, result.excluded.size)
        assertEquals("Rok-based Access Contrdl", result.excluded[0].token)
        assertEquals("R5", result.excluded[0].ruleId)
    }

    @Test
    fun `R5-skill does not false-positive on legitimate skills`() {
        val profile = UserProfile(
            skills = listOf(
                "Automation",
                "Control",
                "MS SQL Server",
                "Python",
                "Information Security"
            )
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(5, result.skills.size)
        assertTrue(result.excluded.isEmpty())
    }

    @Test
    fun `certification path unchanged by R5-skill enhancement`() {
        val profile = UserProfile(
            skills = emptyList(),
            certifications = listOf(
                Certification(name = "AWS Certified Solutions Architect"),
                Certification(name = "CIPP/E")
            )
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(2, result.certifications.size)
        assertTrue(result.certifications.contains("AWS Certified Solutions Architect"))
        assertTrue(result.certifications.contains("CIPP/E"))
        assertTrue(result.excluded.isEmpty())
    }

    @Test
    fun `technical identifier C-sharp preserved as accepted when in taxonomy`() {
        val profile = UserProfile(
            skills = listOf("C#")
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(1, result.skills.size)
        assertEquals("C#", result.skills[0])
        assertTrue(result.excluded.isEmpty())

        val auditEntry = result.audit.entries.first { it.originalValue == "C#" }
        assertEquals("R5_PASS", auditEntry.ruleId)
        assertEquals("HIGH", auditEntry.confidence)
    }

    @Test
    fun `technical identifier Node JS preserved as accepted when in taxonomy`() {
        val profile = UserProfile(
            skills = listOf("Node.js")
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(1, result.skills.size)
        assertEquals("Node.js", result.skills[0])
        assertTrue(result.excluded.isEmpty())

        val auditEntry = result.audit.entries.first { it.originalValue == "Node.js" }
        assertEquals("R5_PASS", auditEntry.ruleId)
        assertEquals("HIGH", auditEntry.confidence)
    }

    @Test
    fun `technical identifier ASP dot NET preserved as accepted when in taxonomy`() {
        val profile = UserProfile(
            skills = listOf("ASP.NET")
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(1, result.skills.size)
        assertEquals("ASP.NET", result.skills[0])
        assertTrue(result.excluded.isEmpty())

        val auditEntry = result.audit.entries.first { it.originalValue == "ASP.NET" }
        assertEquals("R5_PASS", auditEntry.ruleId)
        assertEquals("HIGH", auditEntry.confidence)
    }

    @Test
    fun `certifed Java repairs to certified Java`() {
        val profile = UserProfile(
            skills = listOf("certifed Java")
        )

        val result = ProfileSanitizer.sanitize(profile)

        assertEquals(1, result.skills.size)
        assertEquals("certified Java", result.skills[0])
        assertTrue(result.excluded.isEmpty())

        val auditEntry = result.audit.entries.first { it.ruleId == "R5_REPAIRED" }
        assertEquals("certifed Java", auditEntry.originalValue)
        assertEquals("certified Java", auditEntry.normalizedValue)
        assertEquals("MEDIUM", auditEntry.confidence)
    }
}
