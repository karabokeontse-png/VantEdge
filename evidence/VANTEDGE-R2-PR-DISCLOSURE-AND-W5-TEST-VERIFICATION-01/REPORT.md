# VANTEDGE-R2-PR-DISCLOSURE-AND-W5-TEST-VERIFICATION-01

Status: **READ-ONLY AUDIT COMPLETE — PR DISCLOSURE NON-COMPLIANT — W5ScoreEngineTest VERIFIED**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01` (unchanged)
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` (unchanged)
- Draft PR: #5 — https://github.com/karabokeontse-png/VantEdge/pull/5
- Base branch: `main`
- Base SHA: `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- Successful run 1: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174
- Successful run 2: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536
- Failed general run: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839278056
- R2 status: **OPEN**

---

## 0. Conclusions

| Area | Conclusion |
|---|---|
| PR disclosure | **NON-COMPLIANT** |
| `W5ScoreEngineTest` | **VERIFIED** |
| Overall audit | **COMPLETE** |

Summary:

- Every required disclosure sentence is present in the actual PR body.
- No substantive wording was omitted or silently changed. The only textual-format difference is Markdown backticks around `06bb4ac`.
- The required paragraph boundaries and spacing are **not** preserved. Four required paragraph breaks are concatenated with no space or newline, producing `authorized.The`, `paths.Due`, `rewriting.R6`, and `OPEN.Draft`.
- The appended `### Verified scope` section is clearly distinguishable from the mandated disclosure and its counts do not contradict it.
- PR #5 remains open, draft, and unmerged.
- `W5ScoreEngineTest` executed and passed in both successful workflows: **34/34 tests passed in each run**, with `failures=0`, `errors=0`, and `skipped=0`.
- The prior five-class matrix is unchanged.
- No prohibited action was taken.

---

## 1. Repository identity and pre/post state

| Item | Expected | Pre-audit actual | Post-audit actual | Result |
|---|---|---|---|---|
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | `r2/ci-draft-01` | PASS |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | same | PASS |
| Staged paths | `0` | `0` | `0` | PASS |
| Worktree | not specified | `33 M, 1 D, 34 ??` | `33 M, 1 D, 34 ??` | PRESERVED |
| Porcelain SHA-256 | not specified | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` | same | PRESERVED |

The report file is under the existing untracked `evidence/` directory. No tracked file was changed.

---

## 2. Objective A — actual Draft PR disclosure

### 2.1 Actual PR metadata

Retrieved directly from the authenticated GitHub API, not from a local transcription.

| Field | Actual value |
|---|---|
| Number | `#5` |
| URL | https://github.com/karabokeontse-png/VantEdge/pull/5 |
| Title | `R2 corrective: reconcile requirement evidence and provisional scoring (CI verification)` |
| State | `open` |
| Draft | `true` |
| Merged | `false` |
| Head branch | `r2/ci-draft-01` |
| Head SHA | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` |
| Base branch | `main` |
| Base SHA | `b3c9d06c82a553a53bf9bca62cc521014ce4f159` |
| Merge commit ref | `9efc09a81df148fb1e2d55cfafeba7ebd6dd12b8` |
| Commits | `5` |
| Changed paths | `41` |
| Diffstat | `3173` additions, `197` deletions |
| Created | `2026-11-04T13:34:02Z` |
| Updated | `2026-11-04T13:34:02Z` |

The PR remains open, draft, and unmerged. The base branch is `main`; the current base SHA is `b3c9d06...`.

### 2.2 Actual PR body

The body is `1,197` characters and `13` lines. The first five required paragraphs are concatenated into one physical line; the appended `### Verified scope` section follows after a blank line.

Exact actual body:

```text
This is a Draft PR for CI verification only. No merge is authorized.The corrective implementation is limited to exactly eight paths.Due to repository ancestry, this PR exposes four prior commits and approximately 41 paths. This is the narrowest coherent review surface without history rewriting.R6 (qualification filter) remains unruled. This corrective change has not completed a successful local test run; CI is its first execution. Commit `06bb4ac` provenance is self-attested. Three UI screens previously flagged as dead appear in the diff from earlier commits. R2 remains OPEN.Draft status is not a technical merge lock. No person or automation is authorized to merge this PR.

### Verified scope

- Base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- Head: `r2/ci-draft-01` at `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- Commits entering the PR: **5** (4 prior commits + 1 corrective commit)
- Changed paths: **41**
- Diffstat: **41 files changed, 3173 insertions(+), 197 deletions(-)**
- The new corrective commit changes exactly **8** paths.
- No workflow, build, dependency, or repository-settings paths appear in the PR diff.
- This remains a Draft PR. No merge is authorized.
```

### 2.3 Required disclosure

```text
This is a Draft PR for CI verification only. No merge is authorized.

The corrective implementation is limited to exactly eight paths.

Due to repository ancestry, this PR exposes four prior commits and approximately 41 paths. This is the narrowest coherent review surface without history rewriting.

R6 (qualification filter) remains unruled. This corrective change has not completed a successful local test run; CI is its first execution. Commit 06bb4ac provenance is self-attested. Three UI screens previously flagged as dead appear in the diff from earlier commits. R2 remains OPEN.

Draft status is not a technical merge lock. No person or automation is authorized to merge this PR.
```

### 2.4 Sentence and wording comparison

| Required sentence | Present in actual body | Wording status |
|---|---|---|
| `This is a Draft PR for CI verification only.` | Yes | Exact |
| `No merge is authorized.` | Yes | Exact |
| `The corrective implementation is limited to exactly eight paths.` | Yes | Exact |
| `Due to repository ancestry, this PR exposes four prior commits and approximately 41 paths.` | Yes | Exact |
| `This is the narrowest coherent review surface without history rewriting.` | Yes | Exact |
| `R6 (qualification filter) remains unruled.` | Yes | Exact |
| `This corrective change has not completed a successful local test run; CI is its first execution.` | Yes | Exact |
| `Commit 06bb4ac provenance is self-attested.` | Yes | Wording exact; actual body adds Markdown backticks around `06bb4ac` |
| `Three UI screens previously flagged as dead appear in the diff from earlier commits.` | Yes | Exact |
| `R2 remains OPEN.` | Yes | Exact |
| `Draft status is not a technical merge lock.` | Yes | Exact |
| `No person or automation is authorized to merge this PR.` | Yes | Exact |

No required sentence is omitted. No substantive wording change was detected.

### 2.5 Paragraph boundary and spacing compliance

| Required boundary | Required form | Actual form | Result |
|---|---|---|---|
| After `No merge is authorized.` | Paragraph break before `The corrective implementation...` | `authorized.The corrective implementation...` | **FAIL** |
| After `The corrective implementation is limited to exactly eight paths.` | Paragraph break before `Due to repository ancestry...` | `paths.Due to repository ancestry...` | **FAIL** |
| After `...without history rewriting.` | Paragraph break before `R6 (qualification filter)...` | `rewriting.R6 (qualification filter)...` | **FAIL** |
| After `R2 remains OPEN.` | Paragraph break before `Draft status is not a technical merge lock.` | `OPEN.Draft status is not a technical merge lock.` | **FAIL** |
| After `No person or automation is authorized to merge this PR.` | End of mandated disclosure | Followed by blank line and `### Verified scope` | PASS |

The actual body concatenates the mandated disclosure into one physical line. This fails the directive's readability and paragraph-boundary requirement even though the sentence text is present.

### 2.6 Appended path/commit information

The appended `### Verified scope` section is separated from the mandated text by a blank line and a heading. It is clearly distinguishable. Its actual counts are:

- Commits: `5` (4 prior + 1 corrective)
- Changed paths: `41`
- Diffstat: `41 files changed, 3173 insertions(+), 197 deletions(-)`
- New corrective commit: exactly `8` paths

These do not contradict the mandated text. The mandated “approximately 41 paths” is consistent with the appended actual count of 41.

### 2.7 Disclosure conclusion

**PR disclosure: NON-COMPLIANT.**

Reason: all required sentences are present and substantively unchanged, but four required paragraph boundaries are concatenated without spaces or newlines. The disclosure is therefore not presented with the required readable paragraph structure.

No PR title or body edit was made by this audit.

---

## 3. Objective B — `W5ScoreEngineTest` execution

### 3.1 Run 1 — `W5 Deterministic Scoring Tests`

- Run: `41839277174`
- Run URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174
- Job: `W5 Unit Tests (JVM)` — https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174/job/126606226827
- Artifact: `w5-test-results`
- Artifact ID: `13327098780`
- Artifact API URL: https://api.github.com/repos/karabokeontse-png/VantEdge/actions/artifacts/13327098780/zip
- Exact XML path within artifact: `TEST-com.vantedge.app.w5.scoring.W5ScoreEngineTest.xml`
- Suite/class identifier: `com.vantedge.app.w5.scoring.W5ScoreEngineTest`

| Metric | Value |
|---|---:|
| `tests` | 34 |
| `failures` | 0 |
| `errors` | 0 |
| `skipped` | 0 |
| Testcases passed | 34 |
| Testcases failed | 0 |
| Testcases skipped | 0 |
| Suite time | 0.098 s |

Every testcase passed. No `<failure>`, `<error>`, or `<skipped>` element was present in the suite.

### 3.2 Run 2 — `Full CI + W5 Validation`

- Run: `41839277536`
- Run URL: https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536
- Job: `W5 100-Run Determinism Validation` — https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536/job/126606227434
- Artifact: `w5-determinism-results`
- Artifact ID: `13329430239`
- Artifact API URL: https://api.github.com/repos/karabokeontse-png/VantEdge/actions/artifacts/13329430239/zip
- Exact XML path within artifact: `test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.W5ScoreEngineTest.xml`
- Suite/class identifier: `com.vantedge.app.w5.scoring.W5ScoreEngineTest`

| Metric | Value |
|---|---:|
| `tests` | 34 |
| `failures` | 0 |
| `errors` | 0 |
| `skipped` | 0 |
| Testcases passed | 34 |
| Testcases failed | 0 |
| Testcases skipped | 0 |
| Suite time | 0.092 s |

Every testcase passed. No `<failure>`, `<error>`, or `<skipped>` element was present in the suite.

### 3.3 Execution vs. compilation

The evidence is JUnit XML produced by Gradle test execution. It is not a compile-only artifact and not an inference from a green workflow. The exact suite identifier and per-testcase statuses establish execution of `W5ScoreEngineTest` in both successful workflows.

### 3.4 Prior-evidence consistency

No contradiction was found between the prior report and the newly inspected evidence:

- The prior report recorded 11 W5 XML suites and 117 tests per successful run.
- `W5ScoreEngineTest` accounts for 34 of those tests in each run.
- The prior five-class findings remain unchanged.

### 3.5 `W5ScoreEngineTest` conclusion

**W5ScoreEngineTest: VERIFIED** in both successful workflows.

---

## 4. Updated five-class execution matrix

| Test class | Run 1 `41839277174` | Run 2 `41839277536` |
|---|---|---|
| `CorrectiveUnknownStateTest` | **10/10 passed** | **10/10 passed** |
| `RequirementCoverageTest` | **21/21 passed** | **21/21 passed** |
| `ProvisionalScoreTest` | **11/11 passed** | **11/11 passed** |
| `CompatibilityRecordLegacyJsonTest` | **NOT DEMONSTRATED** | **NOT DEMONSTRATED** |
| `CycleScoreAggregationTest` | **NOT DEMONSTRATED** | **NOT DEMONSTRATED** |

Additional verified class:

| Test class | Run 1 `41839277174` | Run 2 `41839277536` |
|---|---|---|
| `W5ScoreEngineTest` | **34/34 passed** | **34/34 passed** |

The two `NOT DEMONSTRATED` classes remain outside the `com.vantedge.app.w5.scoring.*` filter used by both successful workflows. The failed general CI run `41839278056` produced no test-result artifacts and is not evidence of test execution or test failure.

---

## 5. Prohibited actions confirmation

No prohibited action occurred:

- No application source or test file was modified.
- No workflow, Gradle, dependency, or repository configuration was modified.
- No branch switch or HEAD change occurred.
- No staging, commit, push, or cherry-pick occurred.
- No PR title or body edit occurred.
- No PR comment, label, reviewer, merge, close, auto-merge, or repository-setting change occurred.
- No workflow was rerun or retried.
- No local Gradle test or build was run.
- No AAPT2 environment fix was attempted.
- No implementation allowlist was expanded.
- No claim of R2, R6, B1-E replacement coverage, or S1 closure is made.

The only files written are this report and its `REPORT.sha256` checksum sidecar.

---

## 6. Final governance

- PR disclosure: **NON-COMPLIANT**.
- `W5ScoreEngineTest`: **VERIFIED**.
- Overall audit: **COMPLETE**.
- R2 remains **OPEN**.
- Correcting the PR body would require a new authorization; this audit did not edit it.
- This audit closes only the administrative disclosure and test-evidence verification task.

---

## 7. Report integrity

- The authoritative SHA-256 of this report is recorded in the adjacent `REPORT.sha256` file.
- The same SHA-256 is returned in the audit response.
- A literal self-referential hash cannot be embedded inside the same file without changing that hash.

---

## Appendix A — `W5ScoreEngineTest` testcase identifiers

The same 34 testcase identifiers were present in both successful runs; each passed in both runs.

```text
PASSED: correlationId continuity verification
PASSED: roleRelevance evaluator independent test
PASSED: rejected profile results in zero confidence
PASSED: hintBuilder independent test
PASSED: adapter handles degraded decisions
PASSED: adapter produces correct ValidatedJob from JobExtractionResult
PASSED: degraded profile reduces confidence
PASSED: seniorityFit evaluator independent test
PASSED: seniorityFit returns 0 when either level is null
PASSED: SeniorityDeriver derive from experience
PASSED: SeniorityDeriver toIndex
PASSED: SeniorityDeriver derive from title
PASSED: division by zero safety - empty requiredSkills skillMatch
PASSED: provisional score excludes unmeasured axes and matches legacy when complete
PASSED: division by zero safety - empty requiredYears
PASSED: adapter produces correct ValidatedProfile from StructuredProfileExtraction
PASSED: skillMatch evaluator independent test
PASSED: unknown required axes are flagged and not treated as measured zero
PASSED: expand utility adds synonyms
PASSED: tokenize utility produces correct tokens
PASSED: structuralCompleteness evaluator independent test
PASSED: fixed dataset produces expected scores
PASSED: gapAnalyzer independent test
PASSED: jaccard similarity produces correct values
PASSED: experienceAlignment evaluator independent test
PASSED: adapter handles reject decisions
PASSED: 100-run identical output validation
PASSED: fully measured input yields a complete evaluation
PASSED: division by zero safety - empty keywords
PASSED: constraintCompliance evaluator independent test
PASSED: division by zero safety - RequiredProfileFields empty
PASSED: null handling - currentTitle and seniorityLevel are null
PASSED: keywordCoverage evaluator independent test
PASSED: computeInputHash is deterministic
```
