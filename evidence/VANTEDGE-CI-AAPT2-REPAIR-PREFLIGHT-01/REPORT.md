# VANTEDGE-CI-AAPT2-REPAIR-PREFLIGHT-01

Status: **READ-ONLY AUDIT COMPLETE — ROOT CAUSE CONFIRMED — BOUNDED CI CORRECTIVE CED CAN BE WRITTEN (NOT APPLIED)**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Mode: **READ-ONLY AUDIT**
- Audit completed: `2026-11-05T07:29:04Z`
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01` (unchanged)
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` (unchanged)
- HEAD tree: `fdf88050c7256eee8deef404a8ec0e9e3ae0aaf7` (unchanged)
- Draft PR: #5 — https://github.com/karabokeontse-png/VantEdge/pull/5
- PR state: `open`, draft `true`, merged `false`
- PR base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- R2 status: **OPEN**
- Failed general CI run: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839278056
- Successful W5 run: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174
- Successful full-CI/W5 run: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536

---

## 0. Verdict

The AAPT2 failure is fully explained by one committed, host-specific property:

```properties
android.aapt2FromMavenOverride=/data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2
```

That path is an AndroidIDE app-private path. It does not exist on the GitHub-hosted Ubuntu runner. `.github/workflows/ci.yml` runs Gradle without removing the property, so AGP attempts to use the non-existent custom AAPT2 binary during `:app:processDebugResources` and fails before any unit test executes.

The two successful workflows do not hit the failure because both run:

```bash
sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
```

before invoking Gradle. The successful runs prove that AGP can resolve AAPT2 without the hard-coded AndroidIDE path.

The smallest safe correction is a **CI-only** sanitization step in `.github/workflows/ci.yml`, matching the already-proven pattern in `full-ci.yml` and `w5-tests.yml`. The committed `gradle.properties` should not be changed globally, because the override exists to support local AndroidIDE/ARM64 builds. No source, test, dependency, Gradle-version, or SDK change is required.

The general workflow intends to run all JVM unit tests. It executed none in the failed run. The two non-W5 classes `CompatibilityRecordLegacyJsonTest` and `CycleScoreAggregationTest` are part of the intended general suite but were not executed by the successful W5-filtered workflows. A bounded CI corrective CED can now be written; it should not claim that the general suite will pass, only that it will be unblocked to execute.

---

## 1. Repository and PR contract snapshot

### 1.1 Identity

| Item | Expected | Observed | Result |
|---|---|---|---|
| Repository | `/root/vantedge-canonical` | `/root/vantedge-canonical` | MATCH |
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | MATCH |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | MATCH |
| HEAD tree | `fdf88050c7256eee8deef404a8ec0e9e3ae0aaf7` | same | MATCH |
| Staged paths | `0` | `0` | MATCH |
| PR | #5, open, draft, unmerged | open, draft `true`, merged `false` | MATCH |
| PR base | `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159` | same | MATCH |
| Remote | `https://github.com/karabokeontse-png/VantEdge.git` | same | MATCH |

No identity discrepancy was found. The audit did not attempt to restore or reconcile any state.

### 1.2 Worktree preservation

The worktree was already dirty before this audit and was preserved exactly:

- Tracked modifications: `33`
- Tracked deletions: `1`
- Untracked paths: `34` before this report
- Staged paths: `0`

Pre-write tracked state:

- `git diff --binary HEAD` SHA-256: `03b563e46fdd67ae3d57284e7130d1820d389de0bbc4a87d068694b0cdeb327b`
- `git diff --cached --binary` SHA-256: `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` (empty)
- Tracked-status SHA-256 (`git status --porcelain=v1 -uno`): `f42d00b279969ddf211f5ef69546af88d50b75adfde641c636794f429272eda8`

The only paths created by this audit are:

- `evidence/VANTEDGE-CI-AAPT2-REPAIR-PREFLIGHT-01/REPORT.md`
- `evidence/VANTEDGE-CI-AAPT2-REPAIR-PREFLIGHT-01/REPORT.sha256`

The worktree `gradle.properties` differs from HEAD in caching/incremental settings, but the invalid AAPT2 line is present in both:

- HEAD `gradle.properties:23`
- Worktree `gradle.properties:27`

CI builds the HEAD/PR-merge version, not the worktree version.

### 1.3 CI checkout identity

All three inspected runs checked out the PR merge ref, not HEAD directly:

- Checkout ref: `refs/remotes/pull/5/merge`
- Merge commit: `9efc09a81df148fb1e2d55cfafeba7ebd6dd12b8`
- Merge message: `Merge d68026d7c55b50a2f50ed165dbd8aad27c51a69f into b3c9d06c82a553a53bf9bca62cc521014ce4f159`

The run metadata `head_sha` is `d68026d...`, while the tested checkout is the merge commit `9efc09a...`. This is normal GitHub PR behavior and does not change the AAPT2 analysis.

---

## 2. Exact failure analysis — run `41839278056`

### 2.1 Run metadata

| Field | Value |
|---|---|
| Workflow | `VantEdge CI` |
| Workflow path | `.github/workflows/ci.yml` |
| Workflow ID | `307410931` |
| Event | `pull_request` |
| Head branch | `r2/ci-draft-01` |
| Head SHA | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` |
| Job | `test` |
| Job ID | `126606229667` |
| Runner | `ubuntu-latest` |
| Created | `2026-11-04T13:34:07Z` |
| Updated | `2026-11-04T13:35:54Z` |
| Conclusion | `failure` |
| Test command | `./gradlew testDebugUnitTest --stacktrace` |
| Artifacts | `0` |

### 2.2 Runner and Android environment

Observed from the failed job log:

- OS: Ubuntu `24.04.5` LTS
- Runner image: `ubuntu-24.04`
- Image version: `20261025.353.1`
- Runner version: `2.338.2`
- JDK: Temurin `17.0.21+9`
- Gradle wrapper: `9.6.1`
- Android Gradle Plugin: `9.3.1` (`build.gradle:15`)
- `compileSdk`: `34` (`app/build.gradle:16`)
- Explicit `buildToolsVersion`: none
- Android SDK setup action: none; the workflow relies on the GitHub-hosted runner image
- `local.properties`: gitignored and not part of the CI checkout; the runner image supplies SDK discovery

The exact runner SDK/build-tools version is not printed in the run log and is not pinned by `ci.yml`. This is not the failure cause: the custom override bypasses normal SDK discovery entirely.

### 2.3 Source of the invalid path

The path is not supplied by a workflow step. It is committed in `gradle.properties`:

HEAD `gradle.properties:23`:

```properties
android.aapt2FromMavenOverride=/data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2
```

Worktree `gradle.properties:27` contains the same line.

`.github/workflows/ci.yml` reads this file as-is. It has no `sed` removal step.

The path is an AndroidIDE private path under `/data/data/...`; the GitHub runner operates under `/home/runner` and does not have that path. AGP therefore rejects the configured custom executable.

### 2.4 Exact log evidence

From the downloaded failed-job log (`/tmp/vantedge-ci-aapt2-audit/41839278056-logs/0_test.txt`):

Command and warning:

```text
2026-11-04T13:34:14.4227166Z ##[group]Run ./gradlew testDebugUnitTest --stacktrace
2026-11-04T13:34:14.4229256Z [36;1m./gradlew testDebugUnitTest --stacktrace[0m
...
2026-11-04T13:34:46.3960380Z WARNING: The option setting 'android.aapt2FromMavenOverride=/data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2' is experimental.
```

Failure point:

```text
2026-11-04T13:35:21.5963633Z > Task :app:processDebugResources FAILED
...
2026-11-04T13:35:48.1393729Z Execution failed for task ':app:processDebugResources' (registered by plugin 'com.android.internal.application').
2026-11-04T13:35:48.1468299Z > Could not resolve all files for configuration ':app:debugRuntimeClasspath'.
2026-11-04T13:35:48.1555169Z    > Failed to transform text-recognition-16.0.0.aar (com.google.mlkit:text-recognition:16.0.0) ...
2026-11-04T13:35:48.1604235Z       > Execution failed for AarResourcesCompilerTransform: ...
2026-11-04T13:35:48.1654242Z          > Specified AAPT2 executable does not exist: /data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2. Must supply one of aapt2 from maven or custom location.
```

The same cause repeats across many AAR transforms. The log records:

```text
2026-11-04T13:35:48.4463945Z > There are 48 more failures with identical causes.
```

The root cause stack frame is:

```text
Caused by: org.gradle.api.InvalidUserDataException: Specified AAPT2 executable does not exist: /data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2. Must supply one of aapt2 from maven or custom location.
    at com.android.build.gradle.internal.services.Aapt2DaemonBuildService.getAapt2ExecutablePath(Aapt2DaemonBuildService.kt:127)
```

Build result:

```text
2026-11-04T13:35:50.7975771Z BUILD FAILED in 1m 34s
2026-11-04T13:35:50.7983189Z ##[error]Process completed with exit code 1.
2026-11-04T13:35:50.7978560Z 19 actionable tasks: 19 executed
```

### 2.5 Test execution and artifacts

The failure occurred at `:app:processDebugResources`; `:app:testDebugUnitTest` was never reached. Therefore:

- Tests executed: **0**
- Test result XML files produced: **0**
- Artifacts produced: **0**

The `Upload test results` step still completed successfully because `actions/upload-artifact@v4` defaults to `if-no-files-found: warn`. The log states:

```text
2026-11-04T13:35:50.9846770Z ##[warning]No files were found with the provided path: app/build/reports/tests/
app/build/test-results/. No artifacts will be uploaded.
```

The artifact API confirms `total_count: 0` for run `41839278056`.

### 2.6 Mechanism

AGP 9.3.1 normally resolves AAPT2 from Maven unless `android.aapt2FromMavenOverride` is set. The property is an explicit override to a custom executable. `Aapt2DaemonBuildService.getAapt2ExecutablePath()` verifies that the custom executable exists and throws `InvalidUserDataException` when it does not.

Because the override is present and invalid:

1. Gradle configures the project and prints the experimental-option warning.
2. `:app:processDebugResources` needs AAPT2 for AAR resource compilation.
3. AGP checks the custom path, does not find it, and fails.
4. The build stops before unit-test compilation/execution.

This is a single, reproducible configuration failure, not a flaky test or a missing dependency.

---

## 3. Comparison with successful runs

### 3.1 Run comparison

| Run | Workflow | Test command | AAPT2 cleanup | Result | Test artifacts |
|---|---|---|---|---|---|
| `41839278056` | `VantEdge CI` / `.github/workflows/ci.yml` | `./gradlew testDebugUnitTest --stacktrace` | **No** | **FAILED** at `processDebugResources` | **0** |
| `41839277174` | `W5 Deterministic Scoring Tests` / `.github/workflows/w5-tests.yml` | `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"` and baseline class | **Yes** | **SUCCESS** | 3 artifacts |
| `41839277536` | `Full CI + W5 Validation` / `.github/workflows/full-ci.yml` | `compileDebugUnitTestKotlin`; then W5-filtered test | **Yes** | **SUCCESS** | 1 artifact |

All three runs checked out the same PR merge commit `9efc09a...`.

### 3.2 Why the successful runs did not hit the failure

Both successful workflows remove the invalid property before Gradle starts.

`.github/workflows/full-ci.yml:26-29`:

```yaml
      - name: Fix gradle.properties for CI
        run: |
          sed -i '/org.gradle.java.home/d' gradle.properties
          sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
```

`.github/workflows/w5-tests.yml:36-39` and `:77-80`:

```yaml
      - name: Fix gradle.properties for CI
        run: |
          sed -i '/org.gradle.java.home/d' gradle.properties
          sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
```

The test filter is **not** the reason these runs avoided the AAPT2 failure. `processDebugResources` runs regardless of which tests are selected. The `sed` removal is the decisive difference.

### 3.3 Proof that default AAPT2 resolution works

In run `41839277536`, the compile step completed successfully:

```text
> Task :app:processDebugResources
...
BUILD SUCCESSFUL in 2m 27s
```

The subsequent W5 test step reused it:

```text
> Task :app:processDebugResources UP-TO-DATE
...
BUILD SUCCESSFUL in 19s
```

In run `41839277174`, `processDebugResources` also completed and the build succeeded. These runs prove that AGP can resolve AAPT2 without the hard-coded AndroidIDE path. The exact AAPT2 artifact is not printed in the logs; the property name and AGP behavior indicate normal Maven resolution is restored when the override is removed. A runner-specific SDK path is not required.

### 3.4 Successful test evidence is W5-only

Run `41839277174` executed 11 W5 test classes, 117 tests, all passing:

| Class | Tests | Failures | Errors |
|---|---:|---:|---:|
| `CorrectiveUnknownStateTest` | 10 | 0 | 0 |
| `ExtractionValidatorTest` | 8 | 0 | 0 |
| `HintBuilderMeasurednessTest` | 7 | 0 | 0 |
| `JobRequirementAdapterTest` | 2 | 0 | 0 |
| `JobTitleRoleRelevanceProductionTest` | 8 | 0 | 0 |
| `ProfileSanitizerTest` | 8 | 0 | 0 |
| `ProvisionalScoreTest` | 11 | 0 | 0 |
| `QualificationEvaluatorTest` | 7 | 0 | 0 |
| `RequirementCoverageTest` | 21 | 0 | 0 |
| `W5ScoreBaselineTest` | 1 | 0 | 0 |
| `W5ScoreEngineTest` | 34 | 0 | 0 |

Run `41839277536` produced the same 11-class W5 set with the same counts and zero failures/errors. Neither run executed the general suite.

---

## 4. Workflow test-scope analysis

### 4.1 Effective scopes

| Workflow | Trigger | Gradle test command | Effective scope | AAPT2 cleanup |
|---|---|---|---|---|
| `ci.yml` | push/PR to `main`, `develop` | `testDebugUnitTest --stacktrace` | **All JVM unit tests intended** | **No** |
| `full-ci.yml` | push to `main`, `develop`, `recovery/reconstructed`; PR to `main`, `develop` | `compileDebugUnitTestKotlin`; `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"` | All test sources compiled; **only W5 tests executed** | Yes |
| `w5-tests.yml` | push/PR to `main`, `develop` on W5 paths; baseline job | `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"`; baseline class | **Only W5 tests** | Yes |
| `h3-corrupted-ocr-test.yml` | `workflow_dispatch` only | `testDebugUnitTest --tests "...ConfidenceEngineCorruptedOcrTest"` | **One class** | **No** |

### 4.2 General workflow

`.github/workflows/ci.yml:24-25`:

```yaml
      - name: Run unit tests
        run: ./gradlew testDebugUnitTest --stacktrace
```

There is no `--tests` filter and no Gradle test include/exclude configuration. The workflow therefore intends to run the entire `testDebugUnitTest` suite. It executed zero tests in run `41839278056` because the build failed first.

### 4.3 Misleading "Full CI" name

`.github/workflows/full-ci.yml` is named `Full CI + W5 Validation`, but its test step is filtered to `com.vantedge.app.w5.scoring.*` at line 38. It compiles all unit-test sources at line 35, but it does not execute the full suite. Its passing status is not evidence that the general suite passes.

### 4.4 Artifact rules

- `ci.yml:27-34`: uploads `test-results` with `if: always()`; paths are `app/build/reports/tests/` and `app/build/test-results/`; `if-no-files-found` is not set, so the default is `warn`.
- `full-ci.yml:47-54`: uploads `w5-determinism-results` with `if: always()`; paths are `app/build/test-results/` and `app/build/reports/`.
- `w5-tests.yml:44-56`: uploads `w5-test-results` and `w5-test-report` with `if: always()`.
- `w5-tests.yml:85-89`: uploads `w5-baseline-scores`.
- `h3-corrupted-ocr-test.yml:32-40`: uploads `h3-corrupted-ocr-report` with `if: always()`.

The failed general run's upload step succeeded but produced no artifact because no test-result paths existed. This is expected with the default `warn` behavior.

### 4.5 Out-of-scope latent issue: H-3 workflow

`h3-corrupted-ocr-test.yml` also lacks the `gradle.properties` sanitization step. Additionally, its target class exists only as:

`app/src/test/java/com/vantedge/app/data/engine/extraction/ConfidenceEngineCorruptedOcrTest.kt.bak`

There is no `.kt` source for that class. The H-3 workflow is therefore independently broken and is not part of the general CI failure. It should not be bundled into the smallest general-CI correction.

---

## 5. Missing test-class execution analysis

### 5.1 The two classes

| Class | Source path | Package | `@Test` count | In intended general suite? | Executed in successful W5 runs? |
|---|---|---|---:|---|---|
| `CompatibilityRecordLegacyJsonTest` | `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt` | `com.vantedge.app.data.storage` | 11 | **Yes** | **No** |
| `CycleScoreAggregationTest` | `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt` | `com.vantedge.app.data.viewmodel` | 15 | **Yes** | **No** |

Both classes are in the `app` unit-test source set. `ci.yml` runs `testDebugUnitTest` with no filter, so both would be included when the general suite executes.

### 5.2 Why they were not executed

The successful workflows filter to `com.vantedge.app.w5.scoring.*`. Both classes are outside that package. The downloaded test-result artifacts from runs `41839277174` and `41839277536` contain only the 11 W5 classes listed in §3.4. There are no XML files for `CompatibilityRecordLegacyJsonTest` or `CycleScoreAggregationTest`.

### 5.3 What the successful runs do prove

Run `41839277536` executed `compileDebugUnitTestKotlin` before the filtered test step. That task compiles the unit-test source set, so the two classes were compiled. They were not executed. Compilation is not runtime evidence; no claim is made that their assertions pass.

### 5.4 Consequence for the corrective CED

The AAPT2 fix will unblock the general suite, which will then execute these two classes for the first time in CI. Their pass/fail status is unknown from existing evidence. The corrective CED must describe the change as unblocking execution, not as proving the general suite green. If these classes fail, that is a separate test/implementation issue and must not be hidden inside the AAPT2 fix.

---

## 6. Smallest safe corrective change (proposed, not applied)

### 6.1 Recommended change

Insert a CI-only sanitization step in `.github/workflows/ci.yml` after `Set up JDK 17` (line 19) and before `Grant execute permission for gradlew` (line 21), matching the proven pattern:

```yaml
      - name: Fix gradle.properties for CI
        run: |
          sed -i '/org.gradle.java.home/d' gradle.properties
          sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
```

The absolute minimum is the single AAPT2 line:

```yaml
      - name: Remove host-specific AAPT2 override
        run: sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
```

`org.gradle.java.home` is not present in HEAD `gradle.properties`, so its removal is optional. The two-line form is recommended only because it matches the two workflows that already succeed.

### 6.2 Why this is safe

- It changes only the ephemeral CI checkout, not the repository's committed `gradle.properties`.
- It preserves the AndroidIDE/ARM64 override for local development.
- It restores AGP's default AAPT2 resolution, which is already proven by runs `41839277174` and `41839277536`.
- It does not change AGP, Gradle, `compileSdk`, dependencies, test scope, or test code.
- It keeps the general workflow's intended all-tests command unchanged.

### 6.3 Alternatives considered and rejected

| Alternative | Assessment |
|---|---|
| Delete the line from committed `gradle.properties` | Textually smaller, but affects local AndroidIDE/ARM64 builds. `P2_CLOSURE_REPORT.md:88` records that ARM64 requires an explicit AAPT2 override. Not safe as a CI-only correction. |
| Point the override at a runner SDK build-tools path | Requires knowing/pinning the runner's build-tools version; `ubuntu-latest` can drift. Less reproducible and unnecessary. |
| Install/copy an AAPT2 binary to the AndroidIDE path in CI | Artificial; creates a fake host-specific path and does not address the configuration error. |
| Keep the override and add `-Pandroid.aapt2FromMavenOverride=` | Empty/invalid override behavior is not the proven path. The successful workflows delete the property. |

### 6.4 Reproducibility impact

Removing the override in CI is low-risk and generally more reproducible than using a runner-specific SDK path:

- AGP `9.3.1` and Gradle `9.6.1` are pinned.
- The default AAPT2 artifact is versioned with AGP and resolved through Maven.
- The two successful runs already demonstrate this path on the same runner image.
- The CI step does not alter the committed build configuration, so local/AndroidIDE reproducibility is preserved.

The main residual risk is that the general suite has never executed in CI, so test-level failures may appear after the environment blocker is removed.

---

## 7. Regression risks and required verification

### 7.1 Risks

1. **General-suite failures may surface.** The AAPT2 fix only unblocks execution. The two non-W5 classes and other general tests may fail on assertions or environment assumptions. These must be treated separately from the AAPT2 root cause.
2. **AAPT2 resolution changes.** CI moves from a host-specific override to AGP's default resolution. This is proven by two successful runs, but the general workflow should still be verified end-to-end.
3. **Runner image drift.** `ubuntu-latest` is not pinned. Default AAPT2 resolution reduces dependence on the runner's SDK, but the runner image still supplies the Android SDK/JDK environment.
4. **Artifact absence can be silent.** `ci.yml` does not set `if-no-files-found: error`. Verification must inspect the artifact and XML contents, not just the upload step conclusion.
5. **H-3 remains broken.** The H-3 workflow lacks sanitization and targets a `.kt.bak` class. It is outside the smallest general-CI fix.
6. **No claim of full-suite green.** A successful `processDebugResources` and test start do not prove all tests pass.

### 7.2 Required verification after a bounded CED

1. Confirm the CED diff touches only `.github/workflows/ci.yml` and adds the CI-only sanitization step.
2. Trigger the general CI workflow on PR #5 and confirm `:app:processDebugResources` succeeds.
3. Confirm `:app:testDebugUnitTest` executes, rather than failing before test execution.
4. Confirm the `test-results` artifact exists and contains XML files for the general suite, including:
   - `TEST-com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest.xml`
   - `TEST-com.vantedge.app.data.viewmodel.CycleScoreAggregationTest.xml`
5. Record the full test counts (tests/failures/errors/skipped). Distinguish AAPT2/environment failures from assertion failures.
6. Confirm the successful W5 workflows remain green and unaffected.
7. Confirm the committed `gradle.properties` and local AndroidIDE override were not changed.
8. Do not merge or close R2 on the basis of the AAPT2 fix alone.

---

## 8. Non-mutation statement

This audit was read-only.

- No source, test, workflow, Gradle, dependency, settings, or configuration file was modified.
- No Gradle build, test run, CI run, or workflow dispatch was performed.
- No branch, HEAD, index, or staged state was changed.
- No commit, push, merge, retarget, review, label, or PR edit was performed.
- The only repository paths created are `REPORT.md` and `REPORT.sha256` in this evidence directory.
- The pre-existing dirty worktree was preserved. The tracked-diff and index-diff hashes recorded in §1.2 were rechecked after report creation; no tracked or staged changes were introduced by this audit.
- No credentials or secret values are reproduced in this report.

R2 remains **OPEN**. This audit does not declare R2 acceptance, does not close R6/B1-E/S1, and does not authorize a merge.

---

## 9. Final recommendation

**A bounded CI corrective CED can now be written.**

Recommended scope:

- File: `.github/workflows/ci.yml`
- Change: add the CI-only `gradle.properties` sanitization step before the Gradle invocation
- Pattern: remove `android.aapt2FromMavenOverride` (and optionally `org.gradle.java.home`) from the ephemeral checkout
- Do not change: committed `gradle.properties`, source, tests, dependencies, Gradle/AGP versions, test filters, or the general test command
- Do not bundle: H-3 workflow repair, test fixes, artifact-hardening changes, or R2 acceptance claims

Expected outcome: the general CI workflow reaches and executes `testDebugUnitTest` instead of failing at `processDebugResources`. Whether the full suite passes is a separate question that the CED must verify and report honestly.

---

## 10. Evidence sources

### 10.1 Workflow and build files

- `.github/workflows/ci.yml:11-34`
- `.github/workflows/full-ci.yml:26-54`
- `.github/workflows/w5-tests.yml:36-89`
- `.github/workflows/h3-corrupted-ocr-test.yml:26-40`
- `gradle.properties` (HEAD): line 23
- `gradle.properties` (worktree): line 27
- `build.gradle:15` (AGP 9.3.1)
- `app/build.gradle:16` (compileSdk 34), `:18-20` (unit-test options)
- `gradle/wrapper/gradle-wrapper.properties:3` (Gradle 9.6.1)

### 10.2 CI runs and local evidence

- Failed run: `41839278056` — `.github/workflows/ci.yml`
- Successful run: `41839277174` — `.github/workflows/w5-tests.yml`
- Successful run: `41839277536` — `.github/workflows/full-ci.yml`
- Failed log: `/tmp/vantedge-ci-aapt2-audit/41839278056-logs/0_test.txt`
- Successful logs: `/tmp/vantedge-ci-aapt2-audit/41839277174-logs/`, `/tmp/vantedge-ci-aapt2-audit/41839277536-logs/`
- Test artifacts: `/tmp/vantedge-ci-aapt2-audit/artifacts/`
- Existing failure excerpt: `evidence/VANTEDGE-R2-DRAFT-PR-CI-VERIFICATION-CED-01-FINAL/ci-vantedge-failure-excerpt.txt`

### 10.3 Existing project context

- `AUDIT_2026-10-15.md:154` — finding A45: `ci.yml` fails on clean checkout due committed absolute AAPT2 path with no `sed` repair.
- `AUDIT_2026-10-15.md:2738` — prior proposal to delete the override and drop the CI `sed`; this audit does not adopt the global-deletion approach because of local AndroidIDE/ARM64 impact.
- `P2_CLOSURE_REPORT.md:88` — ARM64 requires an explicit AAPT2 override.
- `evidence/VANTEDGE-R2-VERIFICATION-BLOCKER-RECONCILIATION-01/REPORT.md` — prior workflow table confirming `ci.yml` has no AAPT2 cleanup.

### 10.4 Test classes

- `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt`
- `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt`
- W5 test sources under `app/src/test/java/com/vantedge/app/w5/scoring/`
