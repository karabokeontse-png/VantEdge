# VANTEDGE-CI-AAPT2-REPAIR-CED-01

Status: **INCOMPLETE — REMOTE COMMIT VERIFIED — UNFILTERED CI COMPLETED WITH FAILURE (2 TESTS) — ARTIFACT VERIFIED — SUCCESSFUL CI NOT ACHIEVED**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Parent directive: `VANTEDGE-CI-AAPT2-REPAIR-CED-01`
- Amendment: Formal Amendment 01
- Resumption verification timestamp: `2026-11-05T10:30:03Z`
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01`
- R2 status: **OPEN**
- Merge authorization: **NOT GRANTED**

---

## 0. Disposition

The authorized CI-only workflow repair was applied, committed, and pushed.

- Commit: `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`
- General CI run: https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081565
- Run conclusion: **failure** — because 2 unit tests failed, **not** because of AAPT2
- AAPT2: `:app:processDebugResources` succeeded
- General unfiltered suite: executed
- Test matrix: **241 tests, 2 failures, 0 errors, 0 skipped**
- Required classes:
  - `CompatibilityRecordLegacyJsonTest`: **11 tests, 0 failures**
  - `CycleScoreAggregationTest`: **15 tests, 0 failures**
- Test-result artifact: **uploaded and verified**
- `gradle.properties`: unchanged and not in the commit
- Unrelated worktree changes: not included in the commit

**CED-01 is not complete under the resumption acceptance boundary.** The remote commit is verified and the general unfiltered suite executed, but the workflow conclusion is failure because two `DateRecoveryPostProcessingTest` assertions fail. A successful unfiltered CI result has not been achieved. Fixing those tests is outside this CED's authorized scope.

A green CI result would not establish R2 acceptance. R2 remains **OPEN** until authoritative AC-U1 through AC-U11 wording and the required implementation evidence are recovered and reconciled.

No merge is authorized.

---

## 1. Contract snapshot

### 1.1 Preflight

| Item | Expected | Observed | Result |
|---|---|---|---|
| Repository | `/root/vantedge-canonical` | `/root/vantedge-canonical` | MATCH |
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | MATCH |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | MATCH |
| HEAD tree | `fdf88050c7256eee8deef404a8ec0e9e3ae0aaf7` | same | MATCH |
| PR | #5 | #5, open, draft `true`, merged `false` | MATCH |
| Base | `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159` | same | MATCH |
| Staged paths | `0` | `0` | MATCH |

Pre-existing worktree state, preserved:

- Tracked modifications: `33`
- Tracked deletions: `1`
- Untracked paths: `34`
- Staged paths: `0`
- Tracked diff SHA-256: `03b563e46fdd67ae3d57284e7130d1820d389de0bbc4a87d068694b0cdeb327b`
- Index diff SHA-256: `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855`
- Tracked-status SHA-256: `f42d00b279969ddf211f5ef69546af88d50b75adfde641c636794f429272eda8`
- Untracked-excluding-CED-01 SHA-256: `2ac8694524bf6403b2c212a63241ded42de22c48e9c684c418942d1c2003e0e8`

### 1.2 Post-change

| Item | Observed |
|---|---|
| Branch | `r2/ci-draft-01` |
| Local HEAD | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` |
| Local HEAD tree | `86468790a47381865a976a6788d32097c70821e7` |
| Remote branch | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` |
| PR head | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` |
| PR base | `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159` |
| PR state | `open`, draft `true`, merged `false` |
| Staged paths | `0` |
| Commit files | exactly `.github/workflows/ci.yml` |
| `gradle.properties` | still ` M`; not committed, not modified by this CED |

The local tracked diff, index diff, tracked-status hash, and untracked-excluding-CED-01 hash returned to their preflight values after the single-file commit. The pre-existing dirty worktree is unchanged.

---

## 2. Authorized change

Only `.github/workflows/ci.yml` was modified.

Exact diff:

```diff
diff --git a/.github/workflows/ci.yml b/.github/workflows/ci.yml
index b7789d1..ede43e2 100644
--- a/.github/workflows/ci.yml
+++ b/.github/workflows/ci.yml
@@ -21,6 +21,11 @@ jobs:
       - name: Grant execute permission for gradlew
         run: chmod +x gradlew
 
+      - name: Fix gradle.properties for CI
+        run: |
+          sed -i '/org.gradle.java.home/d' gradle.properties
+          sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
+
       - name: Run unit tests
         run: ./gradlew testDebugUnitTest --stacktrace
 
@@ -32,3 +37,4 @@ jobs:
           path: |
             app/build/reports/tests/
             app/build/test-results/
+          if-no-files-found: error
```

No artifact paths, test commands, test filters, dependencies, or other workflow behavior were changed.

---

## 3. Commit and push evidence

- Commit SHA: `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`
- Commit tree: `86468790a47381865a976a6788d32097c70821e7`
- Commit message: `ci: fix AAPT2 override and require test artifacts`
- Files in commit: exactly `.github/workflows/ci.yml`
- Diffstat: `1 file changed, 6 insertions(+)`
- Staged paths after commit: `0`

The token scope was updated by the user to include `workflow`:

```text
X-OAuth-Scopes: repo, workflow
```

The push command emitted a transient TLS/RPC error and then reported `Everything up-to-date`. Remote verification after the command confirms the push succeeded:

```text
d03d38b3f44c3ba9a104241e0a93cee07fc285ee  refs/heads/r2/ci-draft-01
```

The GitHub API also reports the PR head as `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`.

---

## 4. CI run evidence

### 4.1 Runs for the commit

| Run | Workflow | Result | URL |
|---|---|---|---|
| `42005081522` | W5 Deterministic Scoring Tests | success | https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081522 |
| `42005081565` | VantEdge CI | **failure — 2 tests** | https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081565 |
| `42005081853` | Full CI + W5 Validation | success | https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081853 |

The general unfiltered run is `42005081565`.

### 4.2 Job steps

| Step | Result |
|---|---|
| Set up job | success |
| Run `actions/checkout@v4` | success |
| Set up JDK 17 | success |
| Grant execute permission for `gradlew` | success |
| **Fix `gradle.properties` for CI** | **success** |
| **Run unit tests** | **failure — 2 test assertions** |
| **Upload test results** | **success** |
| Post steps / Complete job | success |

### 4.3 AAPT2 result

The cleanup step executed successfully:

```text
2026-11-05T09:46:07.8014381Z ##[group]Run sed -i '/org.gradle.java.home/d' gradle.properties
2026-11-05T09:46:07.8018274Z sed -i '/android.aapt2FromMavenOverride/d' gradle.properties
```

AAPT2 resource processing then succeeded:

```text
2026-11-05T09:47:12.6923067Z > Task :app:processDebugResources
```

There is no `Specified AAPT2 executable does not exist` occurrence and no `processDebugResources FAILED` occurrence in the run log.

### 4.4 Unfiltered suite execution

The general workflow reached and executed the test task:

```text
2026-11-05T09:48:33.3949185Z > Task :app:testDebugUnitTest
```

The run then failed only because two tests failed:

```text
2026-11-05T09:48:33.3998677Z DateRecoveryPostProcessingTest > dates fallback recovers year when startDate is blank FAILED
2026-11-05T09:48:33.3999974Z     org.junit.ComparisonFailure at DateRecoveryPostProcessingTest.kt:51
2026-11-05T09:48:33.4001358Z DateRecoveryPostProcessingTest > primary fallback uses dates field when startDate is blank FAILED
2026-11-05T09:48:33.4002856Z     org.junit.ComparisonFailure at DateRecoveryPostProcessingTest.kt:73
2026-11-05T09:48:34.6923393Z > Task :app:testDebugUnitTest FAILED
2026-11-05T09:48:34.7229083Z BUILD FAILED in 2m 25s
```

This is a test-assertion failure, not an environment/AAPT2 failure.

---

## 5. Complete test matrix

Source: downloaded `test-results` artifact, 26 JUnit XML files.

| Test class | Tests | Failures | Errors | Skipped | Time (s) |
|---|---:|---:|---:|---:|---:|
| `com.vantedge.app.ExampleUnitTest` | 1 | 0 | 0 | 0 | 0.004 |
| `com.vantedge.app.data.engine.DateRecoveryPostProcessingTest` | 7 | **2** | 0 | 0 | 0.024 |
| `com.vantedge.app.data.engine.ExtractionValidatorTest` | 18 | 0 | 0 | 0 | 0.067 |
| `com.vantedge.app.data.engine.JsonRepairUtilTest` | 4 | 0 | 0 | 0 | 0.015 |
| `com.vantedge.app.data.engine.extraction.ConfidenceEngineUnknownTest` | 3 | 0 | 0 | 0 | 0.074 |
| `com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest` | **11** | 0 | 0 | 0 | 0.111 |
| `com.vantedge.app.data.viewmodel.CycleScoreAggregationTest` | **15** | 0 | 0 | 0 | 0.008 |
| `com.vantedge.app.data.viewmodel.ExtractionOutcomeMapperTest` | 4 | 0 | 0 | 0 | 0.008 |
| `com.vantedge.app.domain.extraction.ExtractionDecisionCoordinatorUnknownTest` | 4 | 0 | 0 | 0 | 0.006 |
| `com.vantedge.app.ui.screens.ScorePresentationTest` | 13 | 0 | 0 | 0 | 0.006 |
| `com.vantedge.app.ui.screens.UnassessedPresentationTest` | 5 | 0 | 0 | 0 | 0.003 |
| `com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest` | 10 | 0 | 0 | 0 | 0.058 |
| `com.vantedge.app.w5.scoring.ExtractionValidatorTest` | 8 | 0 | 0 | 0 | 0.012 |
| `com.vantedge.app.w5.scoring.HintBuilderMeasurednessTest` | 7 | 0 | 0 | 0 | 0.002 |
| `com.vantedge.app.w5.scoring.JobRequirementAdapterTest` | 2 | 0 | 0 | 0 | 0.010 |
| `com.vantedge.app.w5.scoring.JobTitleRoleRelevanceProductionTest` | 8 | 0 | 0 | 0 | 0.058 |
| `com.vantedge.app.w5.scoring.ProfileSanitizerTest` | 8 | 0 | 0 | 0 | 0.028 |
| `com.vantedge.app.w5.scoring.ProvisionalScoreTest` | 11 | 0 | 0 | 0 | 0.011 |
| `com.vantedge.app.w5.scoring.QualificationEvaluatorTest` | 7 | 0 | 0 | 0 | 0.002 |
| `com.vantedge.app.w5.scoring.RequirementCoverageTest` | 21 | 0 | 0 | 0 | 0.034 |
| `com.vantedge.app.w5.scoring.W5ScoreBaselineTest` | 1 | 0 | 0 | 0 | 0.082 |
| `com.vantedge.app.w5.scoring.W5ScoreEngineTest` | 34 | 0 | 0 | 0 | 0.074 |
| `com.vantedge.app.w6.improvement.W6EngineTest` | 8 | 0 | 0 | 0 | 0.036 |
| `com.vantedge.app.w6.improvement.W6UnknownAxisTest` | 2 | 0 | 0 | 0 | 0.000 |
| `com.vantedge.pipeline.evidence.PhaseDAuthorityTest` | 7 | 0 | 0 | 0 | 0.131 |
| `com.vantedge.pipeline.validation.P2VerificationHarnessTest` | 22 | 0 | 0 | 0 | 0.041 |
| **TOTAL** | **241** | **2** | **0** | **0** | |

### 5.1 Required class evidence

| Class | XML | Tests | Failures | Errors | Skipped |
|---|---|---:|---:|---:|---:|
| `com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest` | `TEST-com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest.xml` | 11 | 0 | 0 | 0 |
| `com.vantedge.app.data.viewmodel.CycleScoreAggregationTest` | `TEST-com.vantedge.app.data.viewmodel.CycleScoreAggregationTest.xml` | 15 | 0 | 0 | 0 |

Both previously unexecuted classes are now confirmed executed in general CI and passed.

### 5.2 Failure details

`com.vantedge.app.data.engine.DateRecoveryPostProcessingTest`:

1. `dates fallback recovers year when startDate is blank`
   - `org.junit.ComparisonFailure: expected:<2020[]> but was:<2020[-2024]>`
   - `DateRecoveryPostProcessingTest.kt:51`
2. `primary fallback uses dates field when startDate is blank`
   - `org.junit.ComparisonFailure: expected:<Jan 2020[]> but was:<Jan 2020[ - Present]>`
   - `DateRecoveryPostProcessingTest.kt:73`

These failures are not AAPT2-related. They were not investigated or fixed under this CED because source/test changes are prohibited.

---

## 6. Artifact evidence

The `Upload test results` step succeeded with `if-no-files-found: error`:

```text
2026-11-05T09:48:35.0584429Z   if-no-files-found: error
2026-11-05T09:48:35.2619653Z With the provided path, there will be 61 files uploaded
2026-11-05T09:48:35.9990320Z Uploaded bytes 78899
2026-11-05T09:48:36.0574172Z SHA256 digest of uploaded artifact zip is fb3d17a77c9ab18137362a0b9f95504bd5ff7a4d76b1b1d2e1b0ec3d31eee3d3
2026-11-05T09:48:36.5620731Z Artifact test-results has been successfully uploaded! Final size is 78899 bytes. Artifact ID is 13403723118
```

Artifact:

- Name: `test-results`
- ID: `13403723118`
- Size: `78,899` bytes
- Download URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081565/artifacts/13403723118
- Expired: `false`
- Contents: 61 files, including 26 JUnit XML files
- Downloaded ZIP SHA-256: `fb3d17a77c9ab18137362a0b9f95504bd5ff7a4d76b1b1d2e1b0ec3d31eee3d3` (matches the upload log)

The artifact contains XML for every executed class, including both required classes.

---

## 7. Preservation checks

- `gradle.properties` remains dirty (` M`) exactly as before; it was not modified by this CED and is not in the commit.
- The commit contains exactly `.github/workflows/ci.yml`.
- No source or test file was modified.
- No other workflow was modified.
- No PR metadata was edited.
- No merge, branch deletion, unrelated commit, or unrelated push occurred.
- The pre-existing worktree was preserved; the only new repository paths are this report and its checksum sidecar.
- The local commit was pushed; remote and PR head both show `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`.

---

## 8. Resumption verification

Resumption directive: `VANTEDGE-CI-AAPT2-REPAIR-CED-01 — EXECUTION RESUMPTION`.

| Check | Expected | Observed | Result |
|---|---|---|---|
| Credential can update workflows | `workflow` scope | `X-OAuth-Scopes: repo, workflow` | PASS |
| Local HEAD | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` | same | PASS |
| Commit files | exactly `.github/workflows/ci.yml` | same | PASS |
| Authorized changes present | cleanup step + `if-no-files-found: error` | both present | PASS |
| Unexpected staged changes | none | `0` | PASS |
| Remote branch before resumption | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` (directive snapshot) | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` | ALREADY ADVANCED |
| Push result | push existing commit | `Everything up-to-date` | PASS — already pushed |
| Remote branch after push | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` | same | PASS |
| PR #5 head | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` | same | PASS |
| PR #5 state | open, draft, unmerged | open, draft `true`, merged `false` | PASS |
| PR #5 base | `b3c9d06c82a553a53bf9bca62cc521014ce4f159` | same | PASS |

The remote branch was already at the expected local commit when resumption began, because the prior execution had successfully pushed it after the token scope was updated. No new commit was created or amended. The idempotent push confirmed the remote was current.

No file was modified by the resumption verification except this report and its checksum sidecar. No additional file was staged or committed. The pre-existing worktree remains preserved:

- Staged paths: `0`
- Tracked diff SHA-256: `03b563e46fdd67ae3d57284e7130d1820d389de0bbc4a87d068694b0cdeb327b`
- Index diff SHA-256: `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855`
- Tracked-status SHA-256: `f42d00b279969ddf211f5ef69546af88d50b75adfde641c636794f429272eda8`
- `gradle.properties`: still ` M`; not committed, not modified

CI runs for the remote commit:

| Run | Workflow | Result | URL |
|---|---|---|---|
| `42005081522` | W5 Deterministic Scoring Tests | success | https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081522 |
| `42005081565` | VantEdge CI | **failure — 2 tests** | https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081565 |
| `42005081853` | Full CI + W5 Validation | success | https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081853 |

No successful unfiltered `testDebugUnitTest` run exists for the remote commit. The general run completed and produced verifiable test/artifact evidence, but its conclusion is failure.

---

## 9. Final disposition

**CED-01 INCOMPLETE — REMOTE COMMIT VERIFIED — SUCCESSFUL UNFILTERED CI NOT ACHIEVED.**

- The authorized workflow repair is pushed and verified at `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`.
- The general unfiltered CI run completed.
- AAPT2 resource processing succeeded.
- The unfiltered JVM suite executed.
- Both required previously unexecuted classes executed and passed.
- Complete test counts and artifact evidence are recorded above.
- The run conclusion is failure solely because of two `DateRecoveryPostProcessingTest` assertions.
- The resumption acceptance boundary requires successful unfiltered CI evidence; that condition is not met.

A separate authorized CED is required to address the `DateRecoveryPostProcessingTest` failures. R2 remains **OPEN**. This CED does not establish R2 acceptance and does not authorize a merge.
