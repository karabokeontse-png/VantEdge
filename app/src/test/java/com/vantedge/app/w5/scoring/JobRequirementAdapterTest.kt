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

    @Test
    fun `adaptFromDescription discards overly long OCR blobs but captures valid short phrases`() {
        val adapter = JobRequirementAdapter
        val description = "Requires Master's Degree in Law. " +
                "APPLICATION PROCEDURE: Master's Degree n Law/Data Science/Enterpise Rsk Management/nformation Management, " +
                "CERTIFICATION: oata governance Head ofHumar Gaborone Data Protection and/or Privscy cerufication such as CIPP " +
                "(Certified information Privacy Professionall, CERTIFICATION: CIPM (Certifed Informaion Privacy Managerl, " +
                "CERTIFICATION: CDPO (Certfed Data Protection Officer) etc, CERTIFICATION: Certificate in Enterprise Rsk " +
                "Managament ifnotholdng a Master's in ERM) P/Bag 00319 haractedeed bu ac nalogy knowiedge isa " +
                "Applications shouid be addressd to: Rotwana Schoal of Business Sciences advantage All applications accompanied by cover letter, CERTIFICATION: certified copies of ID"

        val skillTaxonomy = setOf("Kotlin", "SQL", "Python")
        val stopWords = setOf("the", "and", "or", "in", "a", "an", "to", "of", "for")

        val result = adapter.adapt(
            jobDescription = description,
            requiredSkills = emptyList(),
            preferredSkills = emptyList(),
            educationRequired = null,
            skillTaxonomy = skillTaxonomy,
            stopWords = stopWords,
            correlationId = "TEST-CED-12"
        )

        assertTrue(result.validatedJob.requiredQualifications.any {
            it.category == RequirementCategory.DEGREE && it.originalText.contains("Master's Degree in Law")
        })

        val maxPhraseLength = result.validatedJob.requiredQualifications.maxOfOrNull { it.originalText.length } ?: 0
        assertTrue(
            "Expected all extracted qualification phrases in requiredQualifications to be <= 150 characters, but found one of length $maxPhraseLength",
            maxPhraseLength <= 150
        )
    }
}
