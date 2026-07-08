package com.vantedge.pipeline.evidence

sealed class EvidenceResult {
    data class Found(val value: Any) : EvidenceResult()
    object Missing : EvidenceResult()
    data class Computed(val value: Any) : EvidenceResult()
}
