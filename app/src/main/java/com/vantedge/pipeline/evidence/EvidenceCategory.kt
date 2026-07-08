package com.vantedge.pipeline.evidence

sealed class EvidenceCategory {
    object ProfileEvidence : EvidenceCategory()
    object JobEvidence : EvidenceCategory()
    object ComputedEvidence : EvidenceCategory()
    object SystemEvidence : EvidenceCategory()
}
