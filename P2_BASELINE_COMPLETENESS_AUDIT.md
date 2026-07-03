# BASELINE COMPLETENESS AUDIT v1.0

**Date**: 2026-07-03
**Target**: Commit `03bcf39` (branch `recovery/reconstructed`)
**Authority**: CANONICAL AUDIT DIRECTIVE — BASELINE COMPLETENESS AUDIT v1.0

---

## Section A — GeneratorEngine CorrelationId Verification

### Finding: ARCHITECTURAL DEFECT — jobTitle used as correlationId

**File**: `app/src/main/java/com/vantedge/app/data/engine/GeneratorEngine.kt`
**Line**: 84

```kotlin
val p2OutputResult = P2ValidationEngine.validateGeneratorOutput(repairResult.json, jobTitle)
```

The argument passed as correlationId to `P2ValidationEngine.validateGeneratorOutput()` is `jobTitle` — a business/domain field, not a request ID, session ID, or pipeline correlation ID.

The same `jobTitle` value is also used as the correlationId in the P2_DECISION emission on lines 85–89:

```kotlin
PipelineTrace.dataQuality("P2Validation", "P2_DECISION", mapOf(
    "correlationId" to jobTitle,
    ...
), jobTitle)
```

### Classification

| Criterion | Value |
|---|---|
| Value used | `jobTitle` (e.g., "Senior Engineer") |
| Correct value | Request-scoped UUID (e.g., `UUID.randomUUID().toString().take(8)`) |
| Classification | **ARCHITECTURAL DEFECT** |
| Impact | P2 trace correlationId is semantically meaningless. Two validations for the same job title ("Senior Engineer") would share the same correlationId, breaking traceability. |
| Baseline fix present? | **NO** — the defect exists in commit `03bcf39` and remains unmodified. |

### Contrast with other integration points

| Integration Point | correlationId source | Status |
|---|---|---|
| OptimizationOrchestrator | `UUID.randomUUID().toString().take(8)` | ✓ Correct |
| JobExtractionOrchestrator | `UUID.randomUUID().toString().take(8)` | ✓ Correct |
| ProfileExtractionEngine | `sessionId` (defaults to `""` when not provided) | ⚠ Partial — empty string when caller omits sessionId |
| GeneratorEngine | `jobTitle` (business field) | **✗ DEFECT — must use request-scoped UUID** |

---

## Section B — Logging Verification

### Methodology
Inspected all four P2 integration points for `PipelineTrace.dataQuality()` calls, `Log.d/w/i/e` calls, and any governance event emission outside `PipelineTrace.dataQuality()`.

### Integration Point 1: OptimizationOrchestrator

**File**: `app/src/main/java/com/vantedge/app/data/domain/OptimizationOrchestrator.kt`

| Line | Call | Classification |
|---|---|---|
| 44 | `PipelineTrace.entry("analysis_only", ...)` | Operational Log |
| 72 | `PipelineTrace.exit("analysis_only", ...)` | Operational Log |
| 80 | `PipelineTrace.error("analysis_only", ...)` | Operational Log |
| 96 | `PipelineTrace.entry("generation_from_cycle", ...)` | Operational Log |
| 186 | `PipelineTrace.exit("generation_from_cycle", ...)` | Operational Log |
| 195 | `PipelineTrace.error("generation_from_cycle", ...)` | Operational Log |
| 216 | `PipelineTrace.entry("full_pipeline", ...)` | Operational Log |
| 319 | `PipelineTrace.exit("full_pipeline", ...)` | Operational Log |
| 329 | `PipelineTrace.error("full_pipeline", ...)` | Operational Log |
| 433 | `P2ValidationEngine.validateContractResult(validatedNode, correlationId)` | **Governance Event** — P2_VALIDATION_START/END emitted internally |
| 434 | `PipelineTrace.dataQuality("P2Validation", "P2_DECISION", ...)` | **Governance Event** — P2_DECISION |
| 445 | `PipelineTrace.warn("OptimizationOrchestrator", "P2 degraded: ...")` | Governance-Aware Log |
| 467 | `PipelineTrace.entry("apply_design", ...)` | Operational Log |
| 512 | `PipelineTrace.exit("apply_design", ...)` | Operational Log |
| 520 | `PipelineTrace.error("apply_design", ...)` | Operational Log |

No `Log.d/w/i/e` calls in this file. All pipeline events go through `PipelineTrace`.

### Integration Point 2: JobExtractionOrchestrator

**File**: `app/src/main/java/com/vantedge/app/domain/extraction/JobExtractionOrchestrator.kt`

| Line | Call | Classification |
|---|---|---|
| 43 | `PipelineTrace.entry("JobExtractionOrchestrator", ...)` | Operational Log |
| 99 | `PipelineTrace.warn("JobExtractionOrchestrator", "LLM extraction accepted", ...)` | Operational Log |
| 102 | `PipelineTrace.warn("JobExtractionOrchestrator", "Retry requested: ...", ...)` | Operational Log |
| 106 | `PipelineTrace.warn("JobExtractionOrchestrator", "User review needed: ...", ...)` | Operational Log |
| 109 | `PipelineTrace.error("JobExtractionOrchestrator", "Extraction failed: ...", ...)` | Operational Log |
| 129 | `P2ValidationEngine.validateJobExtractionResult(result, correlationId)` | **Governance Event** — P2_VALIDATION_START/END emitted internally |
| 130 | `PipelineTrace.dataQuality("P2Validation", "P2_DECISION", ...)` | **Governance Event** — P2_DECISION |
| 137 | `PipelineTrace.warn("JobExtractionOrchestrator", "P2 rejection: ...", ...)` | Governance-Aware Log |
| 140 | `PipelineTrace.warn("JobExtractionOrchestrator", "P2 degraded: ...", ...)` | Governance-Aware Log |
| 145 | `PipelineTrace.exit("JobExtractionOrchestrator", ...)` | Operational Log |
| 155 | `PipelineTrace.warn("JobExtractionOrchestrator", "AiGateway unavailable ...", ...)` | Operational Log |
| 159 | `PipelineTrace.exit("JobExtractionOrchestrator", ...)` | Operational Log |
| 169 | `PipelineTrace.error("JobExtractionOrchestrator", ...)` | Operational Log |
| 172 | `PipelineTrace.warn("JobExtractionOrchestrator", "Primary extraction failed ...", ...)` | Operational Log |
| 175 | `PipelineTrace.error("JobExtractionOrchestrator", "Emergency fallback also failed", ...)` | Operational Log |

No `Log.d/w/i/e` calls in this file. All pipeline events go through `PipelineTrace`.

### Integration Point 3: ProfileExtractionEngine

**File**: `app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt`

| Line | Call | Classification |
|---|---|---|
| 119 | `PipelineTrace.entry("extractRawText", ...)` | Operational Log |
| 138 | `PipelineTrace.error("extractRawText", ...)` | Operational Log |
| 163 | `PipelineTrace.exit("extractRawText", ...)` | Operational Log |
| 171 | `PipelineTrace.exit("extractRawText", ...)` | Operational Log |
| 179 | `PipelineTrace.error("extractRawText", ...)` | Operational Log |
| 186 | `Log.e(TAG, "extractRawText failed: ...", e)` | Operational Log (Android Log) |
| 187 | `PipelineTrace.error("extractRawText", ...)` | Operational Log |
| 215 | `PipelineTrace.entry("structureProfile", ...)` | Operational Log |
| 252 | `Log.i(TAG, "[Gate0] REJECTED — ...")` | Operational Log (Android Log) |
| 253 | `PipelineTrace.exit("gate0", ...)` | Operational Log |
| 260 | `PipelineTrace.exit("gate0", ...)` | Operational Log |
| 271 | `Log.e(TAG, "DEBUG: [Gate 1] Document rejected. ...")` | Operational Log (Android Log) |
| 288 | `Log.e(TAG, "DEBUG: [Gate 3] Validation failed. ...")` | Operational Log (Android Log) |
| 292 | `P2ValidationEngine.validateProfileExtraction(result, sessionId)` | **Governance Event** — P2_VALIDATION_START/END emitted internally |
| 293 | `PipelineTrace.dataQuality("P2Validation", "P2_DECISION", ...)` | **Governance Event** — P2_DECISION |
| 300 | `Log.w(TAG, "[P2] Profile degraded: ...")` | Governance-Aware Log (Android Log.w) |
| 311 | `PipelineTrace.exit("structureProfile", ...)` | Operational Log |
| 322 | `PipelineTrace.error("structureProfile", ...)` | Operational Log |
| 323 | `Log.e(TAG, "structureProfile failed: ...", e)` | Operational Log (Android Log) |
| 363 | `Log.i(TAG, "[Gate0] ===== STRUCTURAL ANALYSIS =====")` | Debug Log (Android Log) |
| 373 | `Log.i(TAG, "[Gate0] StructuralSections: ...")` | Debug Log (Android Log) |
| 385 | `Log.i(TAG, "[Gate0] ChronologyDensity: ...")` | Debug Log (Android Log) |
| 406 | `Log.i(TAG, "[Gate0] IdentitySignals: ...")` | Debug Log (Android Log) |
| 418 | `Log.i(TAG, "[Gate0] LayoutFragmentation: ...")` | Debug Log (Android Log) |
| 432 | `Log.i(TAG, "[Gate0] NarrativeDensity: ...")` | Debug Log (Android Log) |
| 457 | `Log.i(TAG, "[Gate0] FINAL: ...")` | Debug Log (Android Log) |
| 459 | `PipelineTrace.dataQuality("gate0", "score", ...)` | Operational Log (not P2 governance) |
| 575 | `Log.e(TAG, "DEBUG: [Gate 2] Integrity failed. ...")` | Operational Log (Android Log) |
| 584 | `Log.e(TAG, "JSON parse/mapping failed", e)` | Operational Log (Android Log) |

### Integration Point 4: GeneratorEngine

**File**: `app/src/main/java/com/vantedge/app/data/engine/GeneratorEngine.kt`

| Line | Call | Classification |
|---|---|---|
| 56 | `Log.e("GeneratorEngine", "CV: aiGateway returned null ...")` | Operational Log (Android Log) |
| 61 | `Log.d("GeneratorEngine", "CV: aiGateway returned non-null ...")` | Debug Log (Android Log) |
| 63 | `Log.d("GeneratorEngine", "CV preview: ...")` | Debug Log (Android Log) |
| 68 | `Log.e("GeneratorEngine", "CV: JSON extraction failed ...")` | Operational Log (Android Log) |
| 77 | `Log.e("GeneratorEngine", "CV: JSON repair deemed unsafe ...")` | Operational Log (Android Log) |
| 84 | `P2ValidationEngine.validateGeneratorOutput(repairResult.json, jobTitle)` | **Governance Event** — P2_VALIDATION_START/END emitted internally |
| 85 | `PipelineTrace.dataQuality("P2Validation", "P2_DECISION", ...)` | **Governance Event** — P2_DECISION |
| 92 | `Log.w("GeneratorEngine", "[P2] Output degraded: ...")` | Governance-Aware Log (Android Log.w) |
| 96 | `Log.d("GeneratorEngine", "CV: JSON parse OK ...")` | Debug Log (Android Log) |
| 100 | `Log.e("GeneratorEngine", "CV: parse failed ...")` | Operational Log (Android Log) |
| 140 | `Log.e("GeneratorEngine", "Cover letter: aiGateway returned null ...")` | Operational Log (Android Log) |
| 145 | `Log.d("GeneratorEngine", "Cover letter: aiGateway returned non-null ...")` | Debug Log (Android Log) |
| 146 | `Log.d("GeneratorEngine", "Cover letter raw AI response: ...")` | Debug Log (Android Log) |
| 147 | `Log.d("GeneratorEngine", "Cover letter: EXIT Success")` | Debug Log (Android Log) |
| 227 | `Log.e("GeneratorEngine", "CV docx: aiGateway returned null ...")` | Operational Log (Android Log) |
| 230 | `Log.d("GeneratorEngine", "CV docx: aiGateway returned non-null ...")` | Debug Log (Android Log) |

### Section B Conclusion

**All P2 governance events (P2_VALIDATION_START, P2_VALIDATION_END, P2_DECISION) are emitted exclusively through `PipelineTrace.dataQuality()`.**

However, the earlier claim that "only PipelineTrace.dataQuality() is called" was **too absolute**. Specifically:

1. **ProfileExtractionEngine** emits `Log.w(TAG, "[P2] Profile degraded: ...")` on line 300 — a duplicate P2-aware log via `Log.w()` in addition to the canonical `PipelineTrace.dataQuality()` P2_DECISION event. This is **not a governance event** (it's an operational log for operator visibility), but it does prove that raw `Log.w()` is used alongside `PipelineTrace.dataQuality()`.

2. **GeneratorEngine** emits `Log.w("GeneratorEngine", "[P2] Output degraded: ...")` on line 92 — same pattern, duplicate P2-aware operational log via `Log.w()`.

3. **All other `Log.d/w/i/e` calls** are debug or operational logs unrelated to governance events (Gate 0 scoring details, AI response debugging, error logging).

**Verdict**: The statement "only PipelineTrace.dataQuality() is called" is **correct for canonical governance events but incorrect as an absolute statement about all logging**. The two `Log.w()` calls at lines 300 (ProfileExtractionEngine) and 92 (GeneratorEngine) replicate P2 decision information for operator visibility. This is **acceptable** per the directive: "If orchestrators additionally emit Log.w() for operator visibility, that is acceptable provided PipelineTrace remains the canonical governance event stream." This condition is satisfied.

### Logging Classification Summary

| Category | Count | Examples |
|---|---|---|
| Governance Events (via PipelineTrace.dataQuality) | 8 | P2_VALIDATION_START/END (engine internal) + P2_DECISION x4 (orchestrator sites) |
| Governance-Aware Logs (raw Log.w) | 2 | ProfileExtractionEngine line 300, GeneratorEngine line 92 |
| Operational Logs (via PipelineTrace) | ~25 | entry/exit/error/warn for pipeline stages |
| Debug Logs (raw Log.d) | 6 | GeneratorEngine debug lines |
| Operational Logs (raw Log.e/w/i) | ~12 | Error reporting, Gate 0 scoring |

---

## Section C — Existing Scoring Architecture Inventory

### ScoreEngine

| Field | Value |
|---|---|
| **File** | `app/src/main/java/com/vantedge/app/data/engine/ScoreEngine.kt` |
| **State** | **DEAD** — zero callers in current codebase |
| **Type** | `object ScoreEngine` |
| **Input** | `UserProfile` |
| **Output** | `VantEdgeScoreResult(score: Int, interpretation: String)` |
| **Logic** | Simple point-based scoring: name=10, summary=15, skills=20, workHistory=25, education=10, certifications=10, languages=10. Max score: 100. |
| **Callers** | NONE |
| **Dependencies** | `UserProfile` model only |
| **W5 Relevance** | Placeholder — too simple for production. Point-based scoring with no factor weighting, no normalization, no validation input. |

### ATSEngine

| Field | Value |
|---|---|
| **File** | `app/src/main/java/com/vantedge/app/data/engine/AtsEngine.kt` |
| **State** | **DEAD** — zero callers in current codebase |
| **Type** | `object ATSEngine` |
| **Input** | `UserProfile`, `jobDescription: String` |
| **Output** | `AtsResult(score: Int, keywords: List<String>, missingKeywords: List<String>)` |
| **Logic** | Tokenizes job description, takes first 10 distinct words as keywords, counts missing from profile skills. Score = `(100 - missing * 5).coerceAtLeast(0)`. Note: `AtsResult` data class file has been **deleted** from the working tree (`git status` shows `D app/src/main/java/com/vantedge/app/data/model/AtsResult.kt`). The object references a deleted type, making it a compile-time orphan. |
| **Callers** | NONE |
| **Dependencies** | `UserProfile`, `AtsResult` (DELETED) |
| **W5 Relevance** | **Zero.** Model class deleted, no callers, trivial token-splitting logic. Should be removed or replaced. |

### GapAnalysisEngine

| Field | Value |
|---|---|
| **File** | `app/src/main/java/com/vantedge/app/data/engine/GapAnalysisEngine.kt` |
| **State** | **DEAD** — zero callers in current codebase |
| **Type** | `object GapAnalysisEngine` |
| **Input** | `UserProfile`, `jobSkills: List<String>` |
| **Output** | `List<SkillGap>` (reference — `SkillGap` model class exist status unclear) |
| **Logic** | For each job skill: if profile contains it → `GapSeverity.LOW`, else `GapSeverity.HIGH`. |
| **Callers** | NONE |
| **Dependencies** | `UserProfile`, `SkillGap` model — `SkillGap` unreferenced elsewhere |
| **W5 Relevance** | **Zero.** Trivial set-difference logic, no callers, dead code. |

### CareerEngine

| Field | Value |
|---|---|
| **File** | `app/src/main/java/com/vantedge/app/data/engine/CareerEngine.kt` |
| **State** | **DEAD** — zero callers in current codebase |
| **Type** | `object CareerEngine` |
| **Input** | `UserProfile`, `jobDescription: String` (for cover letter) |
| **Output** | `String` (plain-text CV or cover letter template) |
| **Logic** | Builds plain-text CV from profile fields. Cover letter is a hardcoded template. |
| **Callers** | NONE |
| **Dependencies** | `UserProfile` only |
| **W5 Relevance** | **Zero.** Plain-text CV generation, not scoring. No callers. Superseded by GeneratorEngine. |

### Section C Conclusion

| Engine | File | Active? | Callers | Has P2 wiring? | W5-relevant? |
|---|---|---|---|---|---|
| ScoreEngine | `data/engine/ScoreEngine.kt` | NO (dead) | 0 | No | Yes — concept, but needs complete rewrite |
| ATSEngine | `data/engine/AtsEngine.kt` | NO (dead) | 0 | No | No — model deleted, logic trivial |
| GapAnalysisEngine | `data/engine/GapAnalysisEngine.kt` | NO (dead) | 0 | No | No — trivial set diff |
| CareerEngine | `data/engine/CareerEngine.kt` | NO (dead) | 0 | No | No — plain-text CV, superseded |

**All four existing scoring engines are dead code.** None have P2 wiring, none have callers, and only ScoreEngine's concept (profile scoring) is relevant to W5.

---

## Section D — SkillGraphModel Status

### Finding: NEVER EXISTED

| Search Method | Result |
|---|---|
| Glob `**/*[Ss]kill*[Gg]raph*[Mm]odel*` | No files found |
| `rg 'SkillGraph|skillGraph' app/src/` | No matches |
| `git log --all -- '*SkillGraph*'` | No commits found |
| `git log --all -- '*skill*graph*'` | No commits found |
| `rg -l` for pattern across entire src tree | No files found |

**Verdict**: `SkillGraphModel` has **never existed** in this codebase. It was either planned-only or was part of a prior architecture that was never committed to this repository's history.

---

## Section E — Baseline Freeze Assessment

### Defects Found

| # | Severity | Component | Issue | Blocks Freeze? |
|---|---|---|---|---|
| 1 | **HIGH** | GeneratorEngine | `jobTitle` used as correlationId instead of UUID. All GeneratorEngine P2 traces share the same correlationId for the same job title. | **YES** |
| 2 | LOW | ProfileExtractionEngine | `sessionId` defaults to `""`. Callers omitting sessionId produce P2 traces with empty correlationId. | No (lower impact — the callers in OnboardingViewModel do not pass a sessionId, but traces are logically grouped per extraction anyway) |
| 3 | LOW | ProfileExtractionEngine + GeneratorEngine | `Log.w()` emits duplicate P2 decision info for operator visibility. Acceptable per directive — PipelineTrace remains the canonical governance event stream. | No (by design) |
| 4 | INFO | ScoreEngine, ATSEngine, GapAnalysisEngine, CareerEngine | Dead code — zero callers, no P2 wiring. Relevant to W5 architectural audit but not to P2 governance. | No (dead code does not affect baseline integrity) |
| 5 | INFO | SkillGraphModel | Never existed. No action required. | No |

### Verdict

| Criterion | Status |
|---|---|
| GeneratorEngine correlation ID verified? | Yes — **defect found** (jobTitle used instead of UUID) |
| Logging behavior fully classified? | Yes — see Section B above |
| Existing scoring architecture inventoried? | Yes — all 4 engines dead code |
| SkillGraphModel status resolved? | Yes — never existed |
| **Baseline Freeze Verdict** | **BASELINE REQUIRES PATCH BEFORE FREEZE** |

### Rationale

Defect #1 (GeneratorEngine correlationId) is a **data integrity issue**, not a validation logic issue. The P2 harness correctly validates that `validateGeneratorOutput()` produces correct decisions — it does not verify that the correlationId is a meaningful identifier. This is the exact gap Claude identified: the harness tests the engine, not the callers.

The fix is scoped and surgical:
- `GeneratorEngine.kt:84` — change `jobTitle` to a UUID-generated correlationId
- `GeneratorEngine.kt:85-89` — change `jobTitle` to the same correlationId in the P2_DECISION emission

Once patched, the recovery baseline qualifies for freeze.

### Recommended Action

1. Patch GeneratorEngine.kt to use `UUID.randomUUID().toString().take(8)` as the correlationId (mirroring OptimizationOrchestrator and JobExtractionOrchestrator patterns).
2. Re-run the P2 Verification Harness (22 tests) to confirm no regression.
3. Freeze baseline at the patched commit.

---

## Audit Summary

| Section | Result |
|---|---|
| A — GeneratorEngine correlationId | **ARCHITECTURAL DEFECT** — jobTitle used instead of UUID |
| B — Logging verification | All governance events via PipelineTrace.dataQuality(). Two `Log.w()` calls replicate P2 decision info for operator visibility — acceptable. |
| C — Scoring architecture | 4 engines inventoried. All dead code (zero callers). Only ScoreEngine concept is W5-relevant. |
| D — SkillGraphModel | Never existed. Resolved. |
| E — Baseline freeze | **BASELINE REQUIRES PATCH BEFORE FREEZE** |
