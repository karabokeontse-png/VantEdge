# Protected Component Register v1.0

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
