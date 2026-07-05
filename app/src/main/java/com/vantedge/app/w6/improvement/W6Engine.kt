package com.vantedge.app.w6.improvement

object W6Engine {
    fun evaluate(input: W6Input): W6ImprovementResult {
        val gapClosurePlan = W6Rules.generateGapClosurePlan(input.gaps, input.score)
        val profileImprovements = W6Rules.generateProfileImprovements(input.profile, input.score)
        val jobImprovements = W6Rules.generateJobImprovements(input.job, input.score)
        val impactProjections = W6Rules.generateImpactProjections(input.score)

        return W6ImprovementResult(
            profileImprovements = profileImprovements,
            jobImprovements = jobImprovements,
            gapClosurePlan = gapClosurePlan,
            impactProjections = impactProjections,
            correlationId = input.correlationId
        )
    }
}
