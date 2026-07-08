package com.vantedge.pipeline.evidence

data class ValidationReportEntry(
    val fieldPath: String,
    val violationType: ViolationType,
    val expectedEvidence: String,
    val actualEvidence: String,
    val detail: String? = null
)

data class ValidationReport(
    val entries: List<ValidationReportEntry>,
    val correlationId: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class ClassifiedEntry(
    val entry: ValidationReportEntry,
    val severity: FabricationSeverity
)

data class EnforcementDecision(
    val cycleSeverity: FabricationSeverity,
    val action: EnforcementAction,
    val classifiedEntries: List<ClassifiedEntry>,
    val correlationId: String
)
