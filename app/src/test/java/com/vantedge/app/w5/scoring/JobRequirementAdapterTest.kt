package com.vantedge.app.w5.scoring

import org.junit.Assert.assertTrue
import org.junit.Test

class JobRequirementAdapterTest {

    @Test
    fun `adaptFromDescription extracts qualifications from non-technical job description`() {
        val adapter = JobRequirementAdapter
        val result = adapter.adapt(
            jobDescription = "Data Protection Manager. Requires Master's Degree in Law and CIPP certification. Must have security clearance.",
            requiredSkills = emptyList(),
            preferredSkills = emptyList(),
            educationRequired = null,
            skillTaxonomy = setOf("Kotlin", "SQL", "Python", "AWS", "Azure"),
            stopWords = setOf("the", "and", "or", "in", "a", "an", "to", "of", "for"),
            correlationId = "TEST-CED-10"
        )

        assertTrue(result.validatedJob.requiredQualifications.isNotEmpty())
        assertTrue(result.validatedJob.requiredQualifications.any { it.category == RequirementCategory.DEGREE })
        assertTrue(result.validatedJob.requiredQualifications.any { it.category == RequirementCategory.CERTIFICATION })
        assertTrue(!result.validatedJob.isDegraded)
    }
}
