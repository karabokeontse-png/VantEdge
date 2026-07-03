# P2 Recovery Baseline v1.0

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
