package com.vantedge.pipeline.evidence

import com.vantedge.app.domain.PipelineTrace

object EvidencePolicyEnforcer {

    fun enforce(report: ValidationReport): EnforcementDecision {
        val classifiedEntries = report.entries.map { entry ->
            val criticality = determineCriticality(entry.fieldPath)
            val severity = classify(entry.violationType, criticality)
            ClassifiedEntry(entry, severity)
        }

        val cycleSeverity = aggregateSeverity(classifiedEntries)
        val action = determineAction(cycleSeverity)

        for (entry in classifiedEntries.filter { it.severity != FabricationSeverity.E0 }) {
            emitTraceEvent(entry, report.correlationId)
        }

        return EnforcementDecision(
            cycleSeverity = cycleSeverity,
            action = action,
            classifiedEntries = classifiedEntries,
            correlationId = report.correlationId
        )
    }

    private fun determineCriticality(fieldPath: String): FieldCriticality {
        return when {
            fieldPath == "score" -> FieldCriticality.CRITICAL
            fieldPath == "relevancyItems" || fieldPath.startsWith("relevancyItems[") -> FieldCriticality.CRITICAL
            fieldPath == "gaps" || fieldPath.startsWith("gaps[") -> FieldCriticality.CRITICAL
            fieldPath.startsWith("gap.") -> FieldCriticality.CRITICAL
            fieldPath == "profileStats" || fieldPath.startsWith("profileStats.") -> FieldCriticality.MAJOR
            fieldPath == "qualificationRatio" || fieldPath.startsWith("qualificationRatio.") -> FieldCriticality.MAJOR
            fieldPath == "vacancyScore" -> FieldCriticality.MAJOR
            fieldPath == "criticalGapCount" -> FieldCriticality.MAJOR
            fieldPath == "roleSummary" -> FieldCriticality.MAJOR
            fieldPath == "eligibilitySummary" -> FieldCriticality.MAJOR
            else -> FieldCriticality.MINOR
        }
    }

    private fun classify(violationType: ViolationType, criticality: FieldCriticality): FabricationSeverity {
        return when (violationType) {
            ViolationType.VALID -> FabricationSeverity.E0
            ViolationType.THIN -> when (criticality) {
                FieldCriticality.CRITICAL -> FabricationSeverity.E2
                FieldCriticality.MAJOR -> FabricationSeverity.E1
                FieldCriticality.MINOR -> FabricationSeverity.E1
            }
            ViolationType.MISMATCH -> when (criticality) {
                FieldCriticality.CRITICAL -> FabricationSeverity.E3
                FieldCriticality.MAJOR -> FabricationSeverity.E2
                FieldCriticality.MINOR -> FabricationSeverity.E1
            }
            ViolationType.MISSING -> when (criticality) {
                FieldCriticality.CRITICAL -> FabricationSeverity.E3
                FieldCriticality.MAJOR -> FabricationSeverity.E3
                FieldCriticality.MINOR -> FabricationSeverity.E2
            }
            ViolationType.UNKNOWN_FIELD -> FabricationSeverity.E4
        }
    }

    private fun aggregateSeverity(classifiedEntries: List<ClassifiedEntry>): FabricationSeverity {
        if (classifiedEntries.any { it.severity == FabricationSeverity.E4 }) return FabricationSeverity.E4
        val criticalE3 = classifiedEntries.filter { it.severity == FabricationSeverity.E3 && isCriticalField(it.entry.fieldPath) }
        if (criticalE3.isNotEmpty()) return FabricationSeverity.E3
        val majorE3 = classifiedEntries.filter { it.severity == FabricationSeverity.E3 && isMajorField(it.entry.fieldPath) }
        if (majorE3.size >= 2) return FabricationSeverity.E3
        val anyE3 = classifiedEntries.any { it.severity == FabricationSeverity.E3 }
        if (anyE3) return FabricationSeverity.E3
        val anyE2 = classifiedEntries.any { it.severity == FabricationSeverity.E2 }
        if (anyE2) return FabricationSeverity.E2
        val anyE1 = classifiedEntries.any { it.severity == FabricationSeverity.E1 }
        if (anyE1) return FabricationSeverity.E1
        return FabricationSeverity.E0
    }

    private fun isCriticalField(fieldPath: String): Boolean {
        return determineCriticality(fieldPath) == FieldCriticality.CRITICAL
    }

    private fun isMajorField(fieldPath: String): Boolean {
        return determineCriticality(fieldPath) == FieldCriticality.MAJOR
    }

    private fun determineAction(severity: FabricationSeverity): EnforcementAction {
        return when (severity) {
            FabricationSeverity.E0 -> EnforcementAction.CONTINUE
            FabricationSeverity.E1 -> EnforcementAction.ANNOTATE
            FabricationSeverity.E2 -> EnforcementAction.CORRECT
            FabricationSeverity.E3 -> EnforcementAction.HALT
            FabricationSeverity.E4 -> EnforcementAction.HALT
        }
    }

    private fun emitTraceEvent(entry: ClassifiedEntry, correlationId: String) {
        val remediation = when (entry.severity) {
            FabricationSeverity.E1 -> "ANNOTATE"
            FabricationSeverity.E2 -> "CORRECT"
            FabricationSeverity.E3 -> "REGENERATE"
            FabricationSeverity.E4 -> "HALT"
            else -> "CONTINUE"
        }

        PipelineTrace.dataQuality(
            stage = "EVIDENCE_INTEGRITY",
            issue = entry.entry.violationType.name,
            details = mapOf(
                "correlationId" to correlationId,
                "severity" to entry.severity.name,
                "fieldPath" to entry.entry.fieldPath,
                "violationType" to entry.entry.violationType.name,
                "expectedEvidence" to entry.entry.expectedEvidence,
                "actualEvidence" to entry.entry.actualEvidence,
                "remediationAction" to remediation,
                "detail" to (entry.entry.detail ?: "")
            ),
            correlationId = correlationId
        )
    }
}
