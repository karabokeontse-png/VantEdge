package com.vantedge.app.data.engine

import com.vantedge.app.data.model.ExtractedEducation
import com.vantedge.app.data.model.ExtractedExperience
import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.data.model.StructuredProfileExtraction

data class ValidationError(
    val field: String,
    val severity: ValidationSeverity,
    val message: String,
    val value: String?
)

enum class ValidationSeverity { BLOCKER, WARNING }

data class ValidationResult(
    val passed: Boolean,
    val errors: List<ValidationError>,
    val warnings: List<ValidationError>
) {
    companion object {
        fun pass(): ValidationResult = ValidationResult(true, emptyList(), emptyList())
        fun fail(errors: List<ValidationError>): ValidationResult =
            ValidationResult(false, errors, emptyList())
    }
}

object ExtractionValidator {

    private const val MAX_NAME_LENGTH = 120
    private const val MAX_EMAIL_LENGTH = 254
    private const val MAX_PHONE_LENGTH = 30
    private const val MAX_SUMMARY_LENGTH = 1000
    private const val MAX_SKILL_NAME_LENGTH = 100
    private const val MAX_TITLE_LENGTH = 200
    private const val MAX_COMPANY_LENGTH = 200
    private const val MAX_DESCRIPTION_LENGTH = 5000
    private const val MAX_INSTITUTION_LENGTH = 200
    private const val MAX_QUALIFICATION_LENGTH = 200
    private const val MAX_CERT_NAME_LENGTH = 200
    private const val MAX_CERT_ISSUER_LENGTH = 200
    private const val MIN_JOB_DESC_LENGTH = 10
    private const val MAX_JOB_DESC_LENGTH = 50000

    private val EMAIL_REGEX = Regex("""^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$""")
    private val PHONE_REGEX = Regex("""^\+?[\d\s\-().]{7,20}$""")
    private val YEAR_REGEX = Regex("""^\d{4}$""")
    private val HALLUCINATION_MARKERS = listOf(
        "i assume", "likely", "probably", "may have", "could be",
        "presumably", "i think", "it seems", "based on my knowledge",
        "typically", "commonly known as"
    )

    fun validateProfile(extraction: StructuredProfileExtraction): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        val warnings = mutableListOf<ValidationError>()

        val pi = extraction.personalInfo

        checkField("personalInfo.name", pi.name?.value, errors, ValidationSeverity.BLOCKER) { value ->
            when {
                value.isBlank() -> "Name is required but was empty"
                value.length > MAX_NAME_LENGTH -> "Name exceeds ${MAX_NAME_LENGTH} chars (got ${value.length})"
                containsHallucinationMarker(value) -> "Name contains hallucination marker"
                else -> null
            }
        }

        checkField("personalInfo.email", pi.email?.value, errors, ValidationSeverity.BLOCKER) { value ->
            when {
                value.isBlank() -> "Email is required but was empty"
                !EMAIL_REGEX.matches(value.trim()) -> "Email does not match valid format"
                value.length > MAX_EMAIL_LENGTH -> "Email exceeds ${MAX_EMAIL_LENGTH} chars"
                containsHallucinationMarker(value) -> "Email contains hallucination marker"
                else -> null
            }
        }

        checkField("personalInfo.phone", pi.phone?.value, warnings) { value ->
            when {
                value.length > MAX_PHONE_LENGTH -> "Phone exceeds ${MAX_PHONE_LENGTH} chars (got ${value.length})"
                value.isNotBlank() && !PHONE_REGEX.matches(value.trim()) -> "Phone format unrecognized"
                containsHallucinationMarker(value) -> "Phone contains hallucination marker"
                else -> null
            }
        }

        checkField("summary", extraction.summary?.value, warnings) { value ->
            when {
                value.length > MAX_SUMMARY_LENGTH -> "Summary exceeds ${MAX_SUMMARY_LENGTH} chars (got ${value.length})"
                containsHallucinationMarker(value) -> "Summary contains hallucination marker"
                else -> null
            }
        }

        if (extraction.skills.isEmpty()) {
            warnings.add(
                ValidationError("skills", ValidationSeverity.WARNING, "No skills extracted; may be incomplete", null)
            )
        }
        extraction.skills.forEachIndexed { idx, skill ->
            checkField("skills[$idx]", skill.value, warnings) { value ->
                when {
                    value.length > MAX_SKILL_NAME_LENGTH -> "Skill name exceeds ${MAX_SKILL_NAME_LENGTH} chars"
                    containsHallucinationMarker(value) -> "Skill contains hallucination marker"
                    else -> null
                }
            }
        }

        if (extraction.workHistory.isEmpty()) {
            warnings.add(
                ValidationError("workHistory", ValidationSeverity.WARNING, "No work history extracted; may be incomplete", null)
            )
        }
        extraction.workHistory.forEachIndexed { idx, exp ->
            validateExperience(exp, idx, errors, warnings)
        }

        extraction.education.forEachIndexed { idx, edu ->
            validateEducation(edu, idx, errors, warnings)
        }

        extraction.certifications.forEachIndexed { idx, cert ->
            checkField("certifications[$idx].name", cert.name.value, warnings) { value ->
                when {
                    value.isBlank() -> "Certification name is empty"
                    value.length > MAX_CERT_NAME_LENGTH -> "Certification name exceeds ${MAX_CERT_NAME_LENGTH} chars"
                    containsHallucinationMarker(value) -> "Certification name contains hallucination marker"
                    else -> null
                }
            }
            cert.issuer?.let { issuer ->
                checkField("certifications[$idx].issuer", issuer.value, warnings) { value ->
                    when {
                        value.length > MAX_CERT_ISSUER_LENGTH -> "Issuer exceeds ${MAX_CERT_ISSUER_LENGTH} chars"
                        containsHallucinationMarker(value) -> "Issuer contains hallucination marker"
                        else -> null
                    }
                }
            }
        }

        val blockerCount = errors.count { it.severity == ValidationSeverity.BLOCKER }
        return ValidationResult(
            passed = blockerCount == 0,
            errors = errors,
            warnings = warnings
        )
    }

    fun validateJob(extraction: JobExtractionResult): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        val warnings = mutableListOf<ValidationError>()

        extraction.jobTitle?.let { title ->
            checkField("jobTitle", title, warnings) { value ->
                when {
                    value.length > MAX_TITLE_LENGTH -> "Job title exceeds ${MAX_TITLE_LENGTH} chars (got ${value.length})"
                    containsHallucinationMarker(value) -> "Job title contains hallucination marker"
                    else -> null
                }
            }
        }

        extraction.company?.let { comp ->
            checkField("company", comp, warnings) { value ->
                when {
                    value.length > MAX_COMPANY_LENGTH -> "Company name exceeds ${MAX_COMPANY_LENGTH} chars (got ${value.length})"
                    containsHallucinationMarker(value) -> "Company name contains hallucination marker"
                    else -> null
                }
            }
        }

        checkField("description", extraction.description, errors, ValidationSeverity.BLOCKER) { value ->
            when {
                value.isBlank() -> "Job description is required but was empty"
                value.length < MIN_JOB_DESC_LENGTH -> "Job description too short (${value.length} chars, min $MIN_JOB_DESC_LENGTH)"
                value.length > MAX_JOB_DESC_LENGTH -> "Job description exceeds ${MAX_JOB_DESC_LENGTH} chars"
                containsHallucinationMarker(value) -> "Description contains hallucination marker"
                else -> null
            }
        }

        if (extraction.confidenceScore < 0f || extraction.confidenceScore > 1f) {
            errors.add(
                ValidationError(
                    "confidenceScore",
                    ValidationSeverity.BLOCKER,
                    "Confidence score out of range [0,1]: ${extraction.confidenceScore}",
                    extraction.confidenceScore.toString()
                )
            )
        }

        val blockerCount = errors.count { it.severity == ValidationSeverity.BLOCKER }
        return ValidationResult(
            passed = blockerCount == 0,
            errors = errors,
            warnings = warnings
        )
    }

    private fun validateExperience(
        exp: ExtractedExperience,
        idx: Int,
        errors: MutableList<ValidationError>,
        warnings: MutableList<ValidationError>
    ) {
        checkField("workHistory[$idx].jobTitle", exp.jobTitle.value, warnings) { value ->
            when {
                value.isBlank() -> "Job title in work history entry $idx is empty"
                value.length > MAX_TITLE_LENGTH -> "Job title exceeds ${MAX_TITLE_LENGTH} chars"
                containsHallucinationMarker(value) -> "Job title contains hallucination marker"
                else -> null
            }
        }

        checkField("workHistory[$idx].company", exp.company.value, warnings) { value ->
            when {
                value.isBlank() -> "Company in work history entry $idx is empty"
                value.length > MAX_COMPANY_LENGTH -> "Company name exceeds ${MAX_COMPANY_LENGTH} chars"
                containsHallucinationMarker(value) -> "Company name contains hallucination marker"
                else -> null
            }
        }

        exp.description?.let { desc ->
            checkField("workHistory[$idx].description", desc.value, warnings) { value ->
                when {
                    value.length > MAX_DESCRIPTION_LENGTH -> "Description exceeds ${MAX_DESCRIPTION_LENGTH} chars (got ${value.length})"
                    containsHallucinationMarker(value) -> "Description contains hallucination marker"
                    else -> null
                }
            }
        }

        exp.startDate?.let { sd ->
            checkField("workHistory[$idx].startDate", sd.value, warnings) { value ->
                if (containsHallucinationMarker(value)) "Start date contains hallucination marker" else null
            }
        }

        exp.endDate?.let { ed ->
            checkField("workHistory[$idx].endDate", ed.value, warnings) { value ->
                if (containsHallucinationMarker(value)) "End date contains hallucination marker" else null
            }
        }
    }

    private fun validateEducation(
        edu: ExtractedEducation,
        idx: Int,
        errors: MutableList<ValidationError>,
        warnings: MutableList<ValidationError>
    ) {
        checkField("education[$idx].institution", edu.institution.value, warnings) { value ->
            when {
                value.isBlank() -> "Institution in education entry $idx is empty"
                value.length > MAX_INSTITUTION_LENGTH -> "Institution exceeds ${MAX_INSTITUTION_LENGTH} chars"
                containsHallucinationMarker(value) -> "Institution contains hallucination marker"
                else -> null
            }
        }

        checkField("education[$idx].qualification", edu.qualification.value, warnings) { value ->
            when {
                value.isBlank() -> "Qualification in education entry $idx is empty"
                value.length > MAX_QUALIFICATION_LENGTH -> "Qualification exceeds ${MAX_QUALIFICATION_LENGTH} chars"
                containsHallucinationMarker(value) -> "Qualification contains hallucination marker"
                else -> null
            }
        }

        edu.graduationYear?.let { gy ->
            checkField("education[$idx].graduationYear", gy.value, warnings) { value ->
                when {
                    !YEAR_REGEX.matches(value.trim()) -> "Graduation year is not a valid year (expected 4 digits)"
                    containsHallucinationMarker(value) -> "Graduation year contains hallucination marker"
                    else -> null
                }
            }
        }

        edu.fieldOfStudy?.let { fos ->
            checkField("education[$idx].fieldOfStudy", fos.value, warnings) { value ->
                if (containsHallucinationMarker(value)) "Field of study contains hallucination marker" else null
            }
        }
    }

    private fun containsHallucinationMarker(value: String): Boolean {
        val lower = value.lowercase()
        return HALLUCINATION_MARKERS.any { marker -> lower.contains(marker) }
    }

    private fun checkField(
        fieldPath: String,
        value: String?,
        collector: MutableList<ValidationError>,
        severity: ValidationSeverity = ValidationSeverity.WARNING,
        rule: (String) -> String?
    ) {
        val actual = value ?: ""
        val errorMessage = rule(actual) ?: return
        collector.add(ValidationError(fieldPath, severity, errorMessage, actual))
    }
}
