package com.vantedge.pipeline.contract

/**
 * Job types supported by the contract enforcement layer.
 * Schemas are resolved internally per job type.
 */
enum class JobType {
    VACANCY_SCORING,
    PROFILE_ANALYSIS,
    COVER_LETTER_GENERATION
}
