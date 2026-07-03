package com.vantedge.app.data.domain

import com.vantedge.app.data.engine.CompatibilityResult
import com.vantedge.app.data.model.UserProfile

interface CompatibilityOrchestrator {
    suspend fun runAnalysisFresh(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String
    ): CompatibilityResult
}
