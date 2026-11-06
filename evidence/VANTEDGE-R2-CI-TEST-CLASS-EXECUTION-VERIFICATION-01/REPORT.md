# VANTEDGE-R2-CI-TEST-CLASS-EXECUTION-VERIFICATION-01

Status: **READ-ONLY AUDIT COMPLETE — CLASS-LEVEL CI EXECUTION PARTIALLY VERIFIED**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01` (unchanged)
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` (unchanged)
- Draft PR: https://github.com/karabokeontse-png/VantEdge/pull/5
- Successful run 1: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174
- Successful run 2: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536
- Failed run: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839278056
- R2 status: **OPEN**

---

## 0. Executive summary

The two successful workflows do **not** establish execution of all five specified test classes.

| Test class | Run 1 `41839277174` | Run 2 `41839277536` |
|---|---|---|
| `CorrectiveUnknownStateTest` | **EXECUTED — 10/10 passed** | **EXECUTED — 10/10 passed** |
| `RequirementCoverageTest` | **EXECUTED — 21/21 passed** | **EXECUTED — 21/21 passed** |
| `ProvisionalScoreTest` | **EXECUTED — 11/11 passed** | **EXECUTED — 11/11 passed** |
| `CompatibilityRecordLegacyJsonTest` | **NOT DEMONSTRATED — absent** | **NOT DEMONSTRATED — absent** |
| `CycleScoreAggregationTest` | **NOT DEMONSTRATED — absent** | **NOT DEMONSTRATED — absent** |

The three W5 scoring classes are proven executed by JUnit XML results in both successful runs. The two non-W5 corrective test classes are absent from both result sets because both successful workflows filter execution to `com.vantedge.app.w5.scoring.*`. The only workflow that would run the full unit-test suite, `VantEdge CI`, failed before test execution on the host `aapt2` path and produced no test-result artifacts.

The specific strengthened B1-E test case,
`evidence-supported qualification gaps are preserved and complete`,
passed in both successful runs.

Conclusion: **PARTIALLY VERIFIED**.

---

## 1. Contract and method

Contract snapshot verified before the audit:

| Item | Expected | Actual | Result |
|---|---|---|---|
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | PASS |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | PASS |
| Staged paths | `0` | `0` | PASS |
| Draft PR | #5, draft | #5, draft, open, unmerged | PASS |

Method:

1. Listed the artifacts attached to both successful workflow runs through the GitHub Actions API.
2. Downloaded the JUnit XML artifacts; no repository files were changed.
3. Parsed each `TEST-*.xml` suite directly for suite counts and per-testcase status.
4. Correlated the result classes against the five required class names.
5. Checked the workflow test filters to distinguish “absent from results” from “excluded from execution.”
6. Did not use the failed `VantEdge CI` run as test-failure evidence; it produced zero artifacts and no test results.

No branch switch, staging, commit, push, retry, rerun, workflow change, Gradle change, dependency change, or merge was performed. No local test run was performed.

---

## 2. Evidence sources

### 2.1 Successful run 1 — `W5 Deterministic Scoring Tests`

- Run URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174
- Relevant job: `W5 Unit Tests (JVM)`
- Job URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174/job/126606226827
- Artifact: `w5-test-results`
- Artifact ID: `13327098780`
- Artifact size: `12,350` bytes
- Artifact API download: https://api.github.com/repos/karabokeontse-png/VantEdge/actions/artifacts/13327098780/zip
- Exact JUnit XML source:
  - `TEST-com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest.xml`
  - `TEST-com.vantedge.app.w5.scoring.RequirementCoverageTest.xml`
  - `TEST-com.vantedge.app.w5.scoring.ProvisionalScoreTest.xml`
- Corroborating artifact: `w5-test-report` (ID `13329475183`), HTML report generated from the same test task.
- Not used for class evidence: `w5-baseline-scores` (ID `13327183883`), CSV baseline output.

### 2.2 Successful run 2 — `Full CI + W5 Validation`

- Run URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536
- Relevant job: `W5 100-Run Determinism Validation`
- Job URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536/job/126606227434
- Artifact: `w5-determinism-results`
- Artifact ID: `13329430239`
- Artifact size: `37,486` bytes
- Artifact API download: https://api.github.com/repos/karabokeontse-png/VantEdge/actions/artifacts/13329430239/zip
- Exact JUnit XML source paths:
  - `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest.xml`
  - `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.RequirementCoverageTest.xml`
  - `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.ProvisionalScoreTest.xml`

### 2.3 Failed run — `VantEdge CI`

- Run URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839278056
- Job URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839278056/job/126606229667
- Artifacts: **0**
- Test-result evidence: **none**
- Failure: `Specified AAPT2 executable does not exist: /data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2`
- This is an environment failure before test execution, not a test failure.

---

## 3. Class-level results

### 3.1 `CorrectiveUnknownStateTest`

| Run | JUnit XML suite | Discovered/executed | Passed | Failed | Skipped |
|---|---|---:|---:|---:|---:|
| Run 1 `41839277174` | `TEST-com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest.xml` | 10 | 10 | 0 | 0 |
| Run 2 `41839277536` | `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest.xml` | 10 | 10 | 0 | 0 |

Exact class identifier: `com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest`.

The strengthened B1-E test case was present and passed in both runs:

```text
evidence-supported qualification gaps are preserved and complete
```

The other nine test cases in the class also passed in both runs.

Execution status: **VERIFIED** in both successful workflows.

### 3.2 `RequirementCoverageTest`

| Run | JUnit XML suite | Discovered/executed | Passed | Failed | Skipped |
|---|---|---:|---:|---:|---:|
| Run 1 `41839277174` | `TEST-com.vantedge.app.w5.scoring.RequirementCoverageTest.xml` | 21 | 21 | 0 | 0 |
| Run 2 `41839277536` | `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.RequirementCoverageTest.xml` | 21 | 21 | 0 | 0 |

Exact class identifier: `com.vantedge.app.w5.scoring.RequirementCoverageTest`.

Execution status: **VERIFIED** in both successful workflows.

### 3.3 `ProvisionalScoreTest`

| Run | JUnit XML suite | Discovered/executed | Passed | Failed | Skipped |
|---|---|---:|---:|---:|---:|
| Run 1 `41839277174` | `TEST-com.vantedge.app.w5.scoring.ProvisionalScoreTest.xml` | 11 | 11 | 0 | 0 |
| Run 2 `41839277536` | `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.ProvisionalScoreTest.xml` | 11 | 11 | 0 | 0 |

Exact class identifier: `com.vantedge.app.w5.scoring.ProvisionalScoreTest`.

Execution status: **VERIFIED** in both successful workflows.

### 3.4 `CompatibilityRecordLegacyJsonTest`

| Run | JUnit XML suite | Discovered/executed | Passed | Failed | Skipped |
|---|---|---:|---:|---:|---:|
| Run 1 `41839277174` | **ABSENT** | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED |
| Run 2 `41839277536` | **ABSENT** | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED |

Exact class identifier: `com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest`.

The class exists at
`app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt`
and declares package `com.vantedge.app.data.storage`.

The class is outside the `com.vantedge.app.w5.scoring.*` filter used by both successful workflows. The test-compilation tasks compiled the debug unit-test source set, but no class-level execution result exists. This is an **excluded-from-execution** finding; the evidence does not establish a test failure or a compilation omission.

Execution status: **NOT DEMONSTRATED** in either successful workflow.

### 3.5 `CycleScoreAggregationTest`

| Run | JUnit XML suite | Discovered/executed | Passed | Failed | Skipped |
|---|---|---:|---:|---:|---:|
| Run 1 `41839277174` | **ABSENT** | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED |
| Run 2 `41839277536` | **ABSENT** | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED | NOT DEMONSTRATED |

Exact class identifier: `com.vantedge.app.data.viewmodel.CycleScoreAggregationTest`.

The class exists at
`app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt`
and declares package `com.vantedge.app.data.viewmodel`.

The class is outside the `com.vantedge.app.w5.scoring.*` filter used by both successful workflows. The test-compilation tasks compiled the debug unit-test source set, but no class-level execution result exists. This is an **excluded-from-execution** finding; the evidence does not establish a test failure or a compilation omission.

Execution status: **NOT DEMONSTRATED** in either successful workflow.

---

## 4. Why two classes are absent

Both successful workflows execute only the W5 scoring package:

```text
w5-tests.yml:42:
./gradlew testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*" --no-daemon

full-ci.yml:38:
./gradlew testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*" --no-daemon
```

Therefore:

- `CorrectiveUnknownStateTest`, `RequirementCoverageTest`, and `ProvisionalScoreTest` are inside the filter and have JUnit XML results.
- `CompatibilityRecordLegacyJsonTest` (`com.vantedge.app.data.storage`) and `CycleScoreAggregationTest` (`com.vantedge.app.data.viewmodel`) are outside the filter and have no JUnit XML results.

The failed `VantEdge CI` workflow runs the unfiltered command:

```text
ci.yml:25:
./gradlew testDebugUnitTest --stacktrace
```

That workflow is the only one configured to run all unit tests, but it failed before test execution on the host `aapt2` path and produced no test-result artifacts. It therefore supplies no class-level evidence for either missing class.

The successful artifacts contain 11 W5 XML suites and 117 tests per run, all passing. The three audited W5 classes account for 42 of those tests per run. The two non-W5 classes are not part of that result set.

---

## 5. Execution vs. compilation

- **Execution proven:** The three W5 classes have JUnit XML suites with `tests`, `failures`, `errors`, and `skipped` attributes and explicit per-testcase results.
- **Compilation indicated:** The successful test tasks compiled the debug unit-test source set. This is not the same as class execution.
- **Not demonstrated:** The two non-W5 classes have no JUnit XML, no Gradle test output, and no artifact in either successful run. Their execution is not established.
- **Not a failure:** Absence from the filtered result set is not a failed test. It is an absence of execution evidence.

A green workflow job alone was not accepted as proof of class execution. The class-level conclusions above are based on JUnit XML content.

---

## 6. Conclusion

**PARTIALLY VERIFIED**

- **3 of 5** specified classes are proven executed and passed in both successful workflows:
  `CorrectiveUnknownStateTest`, `RequirementCoverageTest`, `ProvisionalScoreTest`.
- **2 of 5** specified classes are not demonstrated in either successful workflow:
  `CompatibilityRecordLegacyJsonTest`, `CycleScoreAggregationTest`.
- The failed `VantEdge CI` run produced no test results and is not evidence of a test failure.
- This audit does not close R2, R6, B1-E replacement coverage, or S1.

No implementation, source, workflow, or remote change was made by this audit; only this report was written.

---

## 7. Read-only state confirmation

- Branch remained `r2/ci-draft-01`.
- HEAD remained `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`.
- Staged paths remained `0`.
- No branch switch, staging, commit, push, retry, rerun, workflow change, Gradle change, dependency change, or merge was performed.
- The only file written is this report.

---

## Appendix A — Exact testcase identifiers found

### Run 1 `41839277174` / `w5-test-results`

**com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest** — `tests=10` `failures=0` `errors=0` `skipped=0`

```text
PASSED: unassessed qualification evidence is not a mandatory gap and is incomplete
PASSED: empty job required skills keeps skill match unmeasured
PASSED: evidence-supported skill gaps are preserved
PASSED: evidence-supported qualification gaps are preserved and complete
PASSED: empty profile roles and title keeps role relevance unmeasured
PASSED: blank job title keeps role relevance unmeasured
PASSED: empty skill evidence keeps the evaluation incomplete
PASSED: unassessed skills are not classified as confirmed gaps
PASSED: empty skills preserve mapped requirements as unassessed inventory
PASSED: matching qualification evidence produces no gap and stays complete
```

**com.vantedge.app.w5.scoring.RequirementCoverageTest** — `tests=21` `failures=0` `errors=0` `skipped=0`

```text
PASSED: reconciliation failure is distinct from absent profile evidence
PASSED: empty skill evidence keeps every requirement unassessed and inventory intact
PASSED: partial measurement persists a provisional score without changing legacy score
PASSED: preferred skill without matching profile evidence stays unassessed and is not a gap
PASSED: missing job required qualification is reconciled and blocks false completeness
PASSED: qualifications are assessed independently of skill evidence
PASSED: preferred skill with matching profile evidence is matched
PASSED: job required qualification with matching profile evidence is matched
PASSED: profile qualification not required by the job is not unreconciled and does not block completeness
PASSED: every gap has exactly one confirmed gap assessment
PASSED: inventory order is preserved
PASSED: confirmed gaps and unassessed requirements remain distinct
PASSED: fallback inventory keeps required requirements and does not invent preferred
PASSED: no eligible measured component yields a null provisional vacancy score
PASSED: fully measured record keeps provisional equal to legacy values
PASSED: inventory entries differing in classification status remain distinct
PASSED: legacy records keep unknown coverage null
PASSED: unmapped requirement is unassessed and is not a confirmed gap
PASSED: new records always persist a coverage inventory
PASSED: experience-only partial measurement normalizes measured vacancy components
PASSED: every matched required skill has exactly one matched assessment
```

**com.vantedge.app.w5.scoring.ProvisionalScoreTest** — `tests=11` `failures=0` `errors=0` `skipped=0`

```text
PASSED: SeniorityFit alone supports a provisional score
PASSED: legacy total score keeps unmeasured axes as zero
PASSED: provisional score normalizes over measured weights only
PASSED: SkillMatch alone supports a provisional score
PASSED: provisional score stays within the unit interval
PASSED: structural and constraint axes alone yield a null provisional score
PASSED: KeywordCoverage alone supports a provisional score
PASSED: fully measured input keeps provisional equal to legacy score
PASSED: ExperienceAlignment alone supports a provisional score
PASSED: all configured axes have positive weights summing to one
PASSED: RoleRelevance alone supports a provisional score
```

### Run 2 `41839277536` / `w5-determinism-results`

**com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest** — `tests=10` `failures=0` `errors=0` `skipped=0`

```text
PASSED: unassessed qualification evidence is not a mandatory gap and is incomplete
PASSED: empty job required skills keeps skill match unmeasured
PASSED: evidence-supported skill gaps are preserved
PASSED: evidence-supported qualification gaps are preserved and complete
PASSED: empty profile roles and title keeps role relevance unmeasured
PASSED: blank job title keeps role relevance unmeasured
PASSED: empty skill evidence keeps the evaluation incomplete
PASSED: unassessed skills are not classified as confirmed gaps
PASSED: empty skills preserve mapped requirements as unassessed inventory
PASSED: matching qualification evidence produces no gap and stays complete
```

**com.vantedge.app.w5.scoring.RequirementCoverageTest** — `tests=21` `failures=0` `errors=0` `skipped=0`

```text
PASSED: reconciliation failure is distinct from absent profile evidence
PASSED: empty skill evidence keeps every requirement unassessed and inventory intact
PASSED: partial measurement persists a provisional score without changing legacy score
PASSED: preferred skill without matching profile evidence stays unassessed and is not a gap
PASSED: missing job required qualification is reconciled and blocks false completeness
PASSED: qualifications are assessed independently of skill evidence
PASSED: preferred skill with matching profile evidence is matched
PASSED: job required qualification with matching profile evidence is matched
PASSED: profile qualification not required by the job is not unreconciled and does not block completeness
PASSED: every gap has exactly one confirmed gap assessment
PASSED: inventory order is preserved
PASSED: confirmed gaps and unassessed requirements remain distinct
PASSED: fallback inventory keeps required requirements and does not invent preferred
PASSED: no eligible measured component yields a null provisional vacancy score
PASSED: fully measured record keeps provisional equal to legacy values
PASSED: inventory entries differing in classification status remain distinct
PASSED: legacy records keep unknown coverage null
PASSED: unmapped requirement is unassessed and is not a confirmed gap
PASSED: new records always persist a coverage inventory
PASSED: experience-only partial measurement normalizes measured vacancy components
PASSED: every matched required skill has exactly one matched assessment
```

**com.vantedge.app.w5.scoring.ProvisionalScoreTest** — `tests=11` `failures=0` `errors=0` `skipped=0`

```text
PASSED: SeniorityFit alone supports a provisional score
PASSED: legacy total score keeps unmeasured axes as zero
PASSED: provisional score normalizes over measured weights only
PASSED: SkillMatch alone supports a provisional score
PASSED: provisional score stays within the unit interval
PASSED: structural and constraint axes alone yield a null provisional score
PASSED: KeywordCoverage alone supports a provisional score
PASSED: fully measured input keeps provisional equal to legacy score
PASSED: ExperienceAlignment alone supports a provisional score
PASSED: all configured axes have positive weights summing to one
PASSED: RoleRelevance alone supports a provisional score
```

The two non-W5 target classes have no testcase identifiers in either artifact because they were excluded by the `com.vantedge.app.w5.scoring.*` test filter.
