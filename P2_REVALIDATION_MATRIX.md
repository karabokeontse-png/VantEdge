# P2 Revalidation Matrix v1.0

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
