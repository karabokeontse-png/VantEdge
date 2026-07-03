package com.vantedge.pipeline.contract

/**
 * Deterministic failure taxonomy for contract enforcement.
 * No generic FAILURE type allowed.
 */
sealed class ContractFailureReason {
    abstract val code: String
    abstract val category: String

    // Schema failures
    object SchemaMismatch : ContractFailureReason() {
        override val code = "SCHEMA_MISMATCH"
        override val category = "SCHEMA"
    }

    object MissingRequiredFields : ContractFailureReason() {
        override val code = "MISSING_REQUIRED_FIELDS"
        override val category = "SCHEMA"
    }

    object TypeMismatch : ContractFailureReason() {
        override val code = "TYPE_MISMATCH"
        override val category = "SCHEMA"
    }

    // Content failures
    object EmptyContent : ContractFailureReason() {
        override val code = "EMPTY_CONTENT"
        override val category = "CONTENT"
    }

    object TruncatedJson : ContractFailureReason() {
        override val code = "TRUNCATED_JSON"
        override val category = "CONTENT"
    }

    object InvalidJsonStructure : ContractFailureReason() {
        override val code = "INVALID_JSON_STRUCTURE"
        override val category = "CONTENT"
    }

    // System-level failures
    object UnknownModelOutputFormat : ContractFailureReason() {
        override val code = "UNKNOWN_MODEL_OUTPUT_FORMAT"
        override val category = "SYSTEM"
    }

    object UnsupportedSchemaVersion : ContractFailureReason() {
        override val code = "UNSUPPORTED_SCHEMA_VERSION"
        override val category = "SYSTEM"
    }
}
