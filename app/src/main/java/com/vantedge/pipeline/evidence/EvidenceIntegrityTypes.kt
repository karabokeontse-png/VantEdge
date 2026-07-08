package com.vantedge.pipeline.evidence

enum class FabricationSeverity {
    E0, E1, E2, E3, E4
}

enum class FieldCriticality {
    CRITICAL, MAJOR, MINOR
}

enum class ViolationType {
    VALID, THIN, MISMATCH, MISSING, UNKNOWN_FIELD
}

enum class EnforcementAction {
    CONTINUE, ANNOTATE, CORRECT, REGENERATE, HALT
}
