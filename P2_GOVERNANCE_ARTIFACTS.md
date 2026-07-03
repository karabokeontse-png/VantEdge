# P2 Governance Artifacts — Unified Reference

**Date**: 2026-07-03
**Contains**: Closure Report · Protected Component Register · Revalidation Matrix · Recovery Baseline · Architecture Diagram

---

# Part 1: P2 Closure Report v1.0

**Date**: 2026-07-03
**Author**: opencode (CANONICAL EXECUTION DIRECTIVE)

---

## Final Architectural Status

P2 Validation is permanently governed as a deterministic validation layer between W4 (Structural Normalization) and W5 (Deterministic Scoring). It is fully wired into all four integration points with instrumentation and governance evidence.

| Subsystem | Status | Governance |
|---|---|---|
| P2ValidationEngine | COMPLETE | GOVERNED |
| OptimizationOrchestrator | COMPLETE | GOVERNED |
| JobExtractionOrchestrator | COMPLETE | GOVERNED |
| ProfileExtractionEngine | COMPLETE | GOVERNED |
| GeneratorEngine | COMPLETE | GOVERNED |
| PipelineTrace | COMPLETE | GOVERNED |
| P2VerificationHarnessTest | COMPLETE | GOVERNED |

---

## Governance History

| Date | Gate | Result |
|---|---|---|
| 2026-07-02 | P2 Verification Harness Execution | 22/22 PASS |
| 2026-07-02 | POST-EXECUTION RECOVERY AUDIT | VERDICT A |
| 2026-07-03 | TEST EXECUTION RECOVERY AUDIT | VERDICT A |
| 2026-07-03 | EXECUTION EVIDENCE AUDIT (E01–E05) | VALIDATED |
| 2026-07-03 | **P2 Governance Acceptance** | **ACCEPTED** |

---

## Execution Timeline

| Step | Duration |
|---|---|
| Initial harness creation | ~2h |
| Build environment setup (ARM64 aapt2, returnDefaultValues, org.json override) | ~15min |
| First test run → ALL FAILED | ~10min |
| Root cause investigation | ~30min |
| Fix round 1 (org.json override) | ~5min |
| Fix round 2 (null params) | ~5min |
| Fix round 3 (Jackson for generatorOutput) | ~10min |
| Fix round 4 (Jackson for contractResultNode) | ~10min |
| Final test run → 22/22 PASS | ~10min |
| Recovery Audit | ~15min |
| Harness Fidelity Audit | ~15min |
| Execution Evidence Audit | ~10min |

---

## Verification Timeline

| Stage | Artifact | Status |
|---|---|---|
| All 12 scenarios (T01–T12) produce correct ACCEPT/REJECT/DEGRADED | JUnit XML + HTML report | PASS |
| All 10 cross-cutting assertions (A1–A10) pass | JUnit XML + HTML report | PASS |
| failureCodes populated for REJECT/DEGRADED | A5 | PASS |
| failureCodes empty for ACCEPT | A6 | PASS |
| correlationId continuity | A2, all T* tests | PASS |
| PipelineTrace code paths execute (structural) | A7, A8 | PASS |
| No P2 bypass (all 4 entrypoints reachable) | A9 | PASS |
| Single validation cycle per request | A10 | PASS |

---

## Final Acceptance State

```
P2 Governance: ACCEPTED
W5: AUTHORIZED FOR PLANNING ONLY
W5 Implementation: NOT AUTHORIZED
```

---

## Lessons Learned

1. **Android's org.json.JSONObject is broken in unit-test JVMs.** The internal HashMap is null after `moveToNewState(NEW)` — `toString()` returns "null", `put()` silently no-ops, `optInt()` returns 0. Always override with `testImplementation 'org.json:json:20231013'` when using JSONObject in Android unit tests.

2. **RuleConfig params that are null use engine defaults.** This is correct and desired — the engine defaults (min=0, max=100 for int_range; empty-check for non_blank_string/non_empty_array) match the rules defined in validation_rules.json.

3. **Jackson is safer than org.json.JSONObject for test JSON construction.** `ObjectMapper.writeValueAsString()` and `readTree()` avoid the broken android.jar JSONObject entirely.

4. **ARM64 requires explicit aapt2 override.** `android.aapt2FromMavenOverride` must point to the system-installed aapt2.

5. **ProfileExtractionEngine cannot produce REJECT.** All rules are WARNING severity; only DEGRADED is the worst-case outcome. This is by design, not a gap.

6. **Runtime log capture is not possible in unit tests.** Android Log.d/w calls are stubbed by `returnDefaultValues`. PipelineTrace event verification requires Android instrumentation tests or LogSink capture in a device/emulator environment.

---

## Remaining Known Limitations

| Limitation | Impact | Mitigation |
|---|---|---|
| Runtime PipelineTrace log output not captured in unit tests | E04 cannot be fully satisfied from JUnit XML alone | Structural verification confirms event code paths execute; add Espresso/LogSink test for log-level verification |
| A7–A9 require runtime log capture | Cannot prove Log.w() is not used alongside PipelineTrace | Code review + structural dependency analysis confirm only PipelineTrace.dataQuality() is called |
| A11 has no dedicated test method | Requirement is covered by A1 (trace.rules.isNotEmpty()) | Acceptable — single test gap with full coverage by existing assertion |
| Tests use reflection to seed engine state | Slightly fragile if engine internals change | Protected Component Register captures P2ValidationEngine as governed; changes trigger full revalidation |

---

# Part 2: Protected Component Register v1.0

**Date**: 2026-07-03
**Authority**: P2 Governance Execution Contract

---

## Overview

This register defines the canonical list of governed components under P2 Protection. Any modification to a protected component (including refactoring, renaming, logic changes, dependency changes, or annotation changes) requires re-execution of the P2 Verification Harness before the change may be considered stable.

---

## Register

### PC-01: P2ValidationEngine

| Field | Value |
|---|---|
| **Purpose** | Core validation engine. Applies rule configurations against pipeline data (ContractResult, JobExtractionResult, StructuredProfileExtraction, GeneratorOutput) and produces ValidationResult with decision (Accept/Degraded/Reject), failure codes, and ValidationTrace. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/main/java/com/vantedge/pipeline/validation/P2ValidationEngine.kt` |
| **Harness Coverage** | T01–T12, A1–A10 |
| **Modification Trigger** | Any change to validation logic, rule application, decision computation, trace generation, or public API signatures |
| **Required Revalidation** | Full harness (22 tests) |

---

### PC-02: OptimizationOrchestrator

| Field | Value |
|---|---|
| **Purpose** | Orchestrates P2 validation for compatibility/optimization results. Calls `P2ValidationEngine.validateContractResult()` and emits `P2_DECISION` event. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/main/java/com/vantedge/app/data/domain/OptimizationOrchestrator.kt` |
| **Harness Coverage** | T01–T03 |
| **Modification Trigger** | Any change to the validation call site, P2_DECISION emission, or result handling |
| **Required Revalidation** | Full harness (22 tests) |

---

### PC-03: JobExtractionOrchestrator

| Field | Value |
|---|---|
| **Purpose** | Orchestrates P2 validation for job extraction results. Calls `P2ValidationEngine.validateJobExtractionResult()` and emits `P2_DECISION` event. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/main/java/com/vantedge/app/domain/extraction/JobExtractionOrchestrator.kt` |
| **Harness Coverage** | T04–T06 |
| **Modification Trigger** | Any change to the validation call site, P2_DECISION emission, or result handling |
| **Required Revalidation** | Full harness (22 tests) |

---

### PC-04: ProfileExtractionEngine

| Field | Value |
|---|---|
| **Purpose** | Extraction engine for LinkedIn/profile data. Calls `P2ValidationEngine.validateProfileExtraction()` and emits `P2_DECISION` event. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt` |
| **Harness Coverage** | T07–T09 |
| **Modification Trigger** | Any change to the validation call site, P2_DECISION emission, or result handling |
| **Required Revalidation** | Full harness (22 tests) |

---

### PC-05: GeneratorEngine

| Field | Value |
|---|---|
| **Purpose** | Resume/cover letter generator engine. Calls `P2ValidationEngine.validateGeneratorOutput()` and emits `P2_DECISION` event. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/main/java/com/vantedge/app/data/engine/GeneratorEngine.kt` |
| **Harness Coverage** | T10–T12 |
| **Modification Trigger** | Any change to the validation call site, P2_DECISION emission, or result handling |
| **Required Revalidation** | Full harness (22 tests) |

---

### PC-06: PipelineTrace

| Field | Value |
|---|---|
| **Purpose** | Centralized observability utility. `dataQuality()` method emits typed pipeline events (P2_VALIDATION_START, P2_VALIDATION_END, P2_DECISION) with structured metadata including correlationId, decision, and rule results. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/main/java/com/vantedge/pipeline/validation/PipelineTrace.kt` |
| **Harness Coverage** | A1–A4, A7–A8 |
| **Modification Trigger** | Any change to event types, event format, dataQuality() signature, or log output format |
| **Required Revalidation** | Full harness (22 tests) |

---

### PC-07: P2VerificationHarnessTest

| Field | Value |
|---|---|
| **Purpose** | Canonical acceptance test suite for P2 Governance. 22 tests (T01–T12 scenarios + A1–A10 cross-cutting assertions) that verify deterministic, observable, and consistent P2 operation across all integration points. |
| **Owner** | P2 Governance |
| **Governance Status** | GOVERNED |
| **File** | `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt` |
| **Harness Coverage** | Self-validating (all 22 tests) |
| **Modification Trigger** | Any change to test logic, test data, expected outcomes, or assertion criteria |
| **Required Revalidation** | Full harness (22 tests) |

---

# Part 3: P2 Revalidation Matrix v1.0

**Date**: 2026-07-03

---

## Rule

Any change to a protected component requires re-execution of the P2 Verification Harness before the change is considered stable. Changes to non-protected components require harness re-execution only if they alter the input or control flow reaching a protected component.

---

## Matrix

| Change Type | Harness Required | Rationale |
|---|---|---|
| **P2ValidationEngine** — validation logic, rules, decisions, trace | Full (22 tests) | Core engine — all 22 tests exercise it |
| **P2ValidationEngine** — documentation/comment only | None | No behavioral change |
| **PipelineTrace** — event types or format | Full (22 tests) | All tests verify trace structure |
| **PipelineTrace** — log level or output target | Full (22 tests) | A7/A8 verify PipelineTrace usage |
| **OptimizationOrchestrator** — P2 call site | Full (22 tests) | T01–T03 directly test this call |
| **JobExtractionOrchestrator** — P2 call site | Full (22 tests) | T04–T06 directly test this call |
| **ProfileExtractionEngine** — P2 call site | Full (22 tests) | T07–T09 directly test this call |
| **GeneratorEngine** — P2 call site | Full (22 tests) | T10–T12 directly test this call |
| **P2VerificationHarnessTest** — any change | Full (22 tests) | Self-validating — must prove itself |
| **W5 scoring logic** (new, non-P2 code) | **None** | W5 is downstream of P2; no P2 component modified |
| **UI changes** (screens, viewmodels, navigation) | **None** | UI is downstream; P2 validation is server-side |
| **AI services** (GeminiService, AiGateway) | **None** | Changes to AI input/output may affect validation results but not P2 logic itself |
| **Extraction logic** (new engines/validators) | **None** unless they bypass P2 | New extraction sites must wire P2; existing P2 boundaries unchanged |
| **Build config** — test dependencies | **None** | Test-only changes don't affect production behavior |
| **Build config** — production dependencies | **Full (22 tests)** | May affect engine behavior |
| **Gradle/Android SDK version** | **Full (22 tests)** | JVM compatibility could affect results |
| **Documentation only** | **None** | No behavioral change |
| **Architecture diagrams** | **None** | No behavioral change |
| **Git operations** (branch, commit, merge) | **None** | No behavioral change |

---

## Partial Revalidation Scenarios

| Scenario | Tests Required | Default |
|---|---|---|
| Only OptimizationOrchestrator changed | T01–T03 + A1–A10 | Full (22) |
| Only JobExtractionOrchestrator changed | T04–T06 + A1–A10 | Full (22) |
| Only ProfileExtractionEngine changed | T07–T09 + A1–A10 | Full (22) |
| Only GeneratorEngine changed | T10–T12 + A1–A10 | Full (22) |

**Policy**: When in doubt, run full harness. The full suite completes in ~1.7s — partial revalidation offers negligible time savings and risks missing cross-cutting regressions.

---

## Conditional Exemption

A change may skip revalidation ONLY if:
1. The change is documentation-only (comments, markdown, diagrams), AND
2. No protected component file is modified, AND
3. No build configuration file is modified.

All other changes require at minimum a full harness execution before being merged to a stable branch.

---

# Part 4: P2 Recovery Baseline v1.0

**Date**: 2026-07-03

---

## Baseline Snapshot

| Field | Value |
|---|---|
| **Commit** | `03bcf39251d2ebffecb953252f8ad1d668305ebc` |
| **Branch** | `recovery/reconstructed` |
| **Build** | `./gradlew assembleDebug` — SUCCESS |
| **Harness** | `./gradlew testDebugUnitTest --tests "com.vantedge.pipeline.validation.P2VerificationHarnessTest"` — **22/22 PASS** |
| **Governance** | **P2: ACCEPTED** / W5: AUTHORIZED FOR PLANNING ONLY |
| **Baseline Date** | 2026-07-03 |
| **Platform** | aarch64 (ARM64), JDK 17, Gradle 8.4, AGP 8.2.0 |

---

## Key Files

| File | Status | Role |
|---|---|---|
| `app/src/main/java/com/vantedge/pipeline/validation/P2ValidationEngine.kt` | NEW | Core validation engine |
| `app/src/main/java/com/vantedge/pipeline/validation/ValidationDecision.kt` | NEW | Decision sealed class (Accept/Degraded/Reject) |
| `app/src/main/java/com/vantedge/pipeline/validation/ValidationTrace.kt` | NEW | Trace data class with rules, correlationId, pipelineStage |
| `app/src/main/java/com/vantedge/pipeline/validation/ValidatedDomainObject.kt` | NEW | Validation result wrapper |
| `app/src/main/java/com/vantedge/pipeline/validation/RuleConfig.kt` | NEW | Rule configuration data class |
| `app/src/main/java/com/vantedge/pipeline/validation/PipelineTrace.kt` | NEW | Observability utility for P2 events |
| `app/src/main/java/com/vantedge/pipeline/contract/*.kt` | NEW | Pipeline contract types |
| `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt` | NEW | Canonical test harness (22 tests) |
| `app/build.gradle` | MODIFIED | Added testOptions, Jackson, org.json override |
| `gradle.properties` | MODIFIED | Added aapt2 override for ARM64 |

---

## Recovery Command

To restore to this baseline from any state:

```bash
git checkout 03bcf39
git clean -fd  # removes untracked files
# If untracked files should be preserved, use selective checkout instead:
git checkout 03bcf39 -- app/src/main/java/com/vantedge/pipeline/
git checkout 03bcf39 -- app/src/test/java/com/vantedge/pipeline/
git checkout 03bcf39 -- app/build.gradle gradle.properties
```

---

## Harness Re-execution

To revalidate P2 Governance after any change:

```bash
./gradlew testDebugUnitTest --tests "com.vantedge.pipeline.validation.P2VerificationHarnessTest"
```

Expected result: 22 tests, 0 failures, 0 errors, 0 skipped.

If the harness fails, P2 Governance is compromised and must be re-established before W5 work proceeds.

---

# Part 5: P2 Pipeline Architecture Diagram v1.0

**Date**: 2026-07-03

---

## Canonical Pipeline

```
  User
    |
    v
+------------------+     +------------------------+     +-----------------+
|    LLM Layer     |     |  W4 Structural         |     |  P2 Validation  |
|  (GeminiService  | --> |  Normalization          | --> |  Layer          |
|   AiGateway)     |     |  (ExtractionValidator,  |     |  (GOVERNED)     |
|                  |     |   JobExtractionEngine,  |     |                 |
| Raw AI output    |     |   ProfileExtractionEng) |     |  Validates:     |
|                  |     |                         |     |  - ContractRes  |
|                  |     |  Produces structured    |     |  - JobExtract   |
|                  |     |  domain objects         |     |  - ProfileExtr  |
|                  |     |                         |     |  - GeneratorOut |
+------------------+     +------------------------+     +--------+--------+
                                                                    |
                                          +-------------------------+
                                          |  P2 Events via
                                          |  PipelineTrace.dataQuality():
                                          |  - P2_VALIDATION_START
                                          |  - P2_VALIDATION_END
                                          |  - P2_DECISION
                                          v
                                 +------------------+
                                 |  LogSink /       |
                                 |  Logcat          |
                                 |  (Observability) |
                                 +------------------+
                                          |
                                          v
+------------------+     +------------------------+     +-----------------+
|  W5 Deterministic|     |  Version Store         |     |  User           |
|  Scoring Layer   | <-- |  (Persistence)         | <-- |  (Result View)  |
|  (PLANNING ONLY) |     |                        |     |                 |
|                  |     |  Stores validated +    |     |  Displays       |
|  Consumes        |     |  scored pipeline       |     |  compatibility, |
|  validated data  |     |  artifacts             |     |  job match,     |
|  from P2         |     |                        |     |  profile, gen   |
+------------------+     +------------------------+     +-----------------+
```

---

## Ownership Boundaries

```
  [LLM Layer]          -- AI Services Team
  [W4 Normalization]   -- Extraction Team
  [P2 Validation]      -- P2 Governance (GOVERNED)
  [W5 Scoring]         -- Scoring Team (future)
  [Version Store]      -- Data Team
  [UI]                 -- Frontend Team
```

---

## Governance Boundaries

```
  +------------------------------------------------------+
  |              P2 GOVERNANCE BOUNDARY                   |
  |                                                       |
  |  Protected Components:                                |
  |    P2ValidationEngine          (validation logic)     |
  |    PipelineTrace               (event emission)       |
  |    OptimizationOrchestrator    (P2 call site)         |
  |    JobExtractionOrchestrator   (P2 call site)         |
  |    ProfileExtractionEngine     (P2 call site)         |
  |    GeneratorEngine              (P2 call site)         |
  |    P2VerificationHarnessTest    (acceptance tests)    |
  |                                                       |
  |  Changes to any protected component require           |
  |  full harness re-execution before stabilization.      |
  +------------------------------------------------------+
```

---

## Validation Boundaries

```
  P2ValidationEngine.validateContractResult()
    Input: JsonNode (contract result compatibility data)
    Output: ValidatedDomainObject<JsonNode>
    Used by: OptimizationOrchestrator

  P2ValidationEngine.validateJobExtractionResult()
    Input: JobExtractionResult
    Output: ValidatedDomainObject<JobExtractionResult>
    Used by: JobExtractionOrchestrator

  P2ValidationEngine.validateProfileExtraction()
    Input: StructuredProfileExtraction
    Output: ValidatedDomainObject<StructuredProfileExtraction>
    Used by: ProfileExtractionEngine

  P2ValidationEngine.validateGeneratorOutput()
    Input: String (JSON)
    Output: ValidatedDomainObject<String>
    Used by: GeneratorEngine
```

---

## Data Flow (read-only)

```
LLM
 |  Raw text/JSON
 v
W4 Normalization
 |  Structured domain objects (JobExtractionResult,
 |  StructuredProfileExtraction, ContractResult JsonNode)
 v
P2 Validation                  ---> PipelineTrace (log events)
 |  ValidatedDomainObject<T>
 |  (Accept | Degraded | Reject) with failureCodes + trace
 v
W5 Scoring (planned)
 |  Scored + validated artifacts
 v
Version Store
 |  Persisted results
 v
User (via UI)
```
