# VANTEDGE-R2-DRAFT-PR-CI-VERIFICATION-CED-01-FINAL

Status: **EXECUTED — DRAFT PR OPEN — CI RECORDED — R2 OPEN**

- Authority: ChatGPT directive `VANTEDGE-R2-DRAFT-PR-CI-VERIFICATION-CED-01-FINAL`, accepted by Claude `GO` with execution notes Z1-Z5.
- Executor: OpenCode.
- Repository: `/root/vantedge-canonical`
- Original branch: `verification/ced-p0-authority`
- Original branch SHA (unchanged): `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`
- Temporary branch: `r2/ci-draft-01`
- Corrective commit: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- PR base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- Draft PR: https://github.com/karabokeontse-png/VantEdge/pull/5
- R2 status: **OPEN**

---

## 0. Executive summary

All mandatory preflight checks passed:

- Repository identity, original branch, HEAD, tree, parent, initial porcelain hash, and staged count matched the directive.
- Remote `main` remained at the expected SHA `b3c9d06...`; it had not moved.
- The ancestry entering the PR was exactly the expected four commits, with `ed83a02` resolved to its full SHA.
- The complete `CorrectiveUnknownStateTest.kt` diff contained only the authorized B1-E characterization.
- All eight allowlisted path diffs were inspected; no unexplained or unrelated hunks were found.
- The PR path surface is 41 paths; all eight allowlisted paths are present; no workflow, build, dependency, or settings path appears.
- `P2VerificationHarnessTest.kt` was excluded from the commit and remains only in the pre-existing dirty worktree.

Execution completed:

- Created `r2/ci-draft-01` from the verified HEAD without disturbing the dirty worktree.
- Staged only the eight allowlisted paths using a prepared patch and `git apply --cached`.
- Committed exactly those eight paths: `8 files changed, 433 insertions(+), 28 deletions(-)`.
- Remained on `r2/ci-draft-01`; did not switch back to the original branch.
- Pushed the temporary branch.
- Created one Draft PR against `main`.
- Recorded all three triggered workflow runs and their actual results.

CI result:

| Workflow | Result | Note |
|---|---|---|
| `W5 Deterministic Scoring Tests` | **SUCCESS** | Both jobs succeeded |
| `Full CI + W5 Validation` | **SUCCESS** | W5 100-run determinism job succeeded |
| `VantEdge CI` | **FAILURE** | `aapt2` host-path environment failure; not an R2 defect finding |

No fix, retry, follow-up commit, or merge was performed. The red `VantEdge CI` run is recorded as an environment finding. R2 remains **OPEN**.

---

## 1. Preflight evidence

Preflight time: `2026-11-04T13:30:58Z`

| Item | Expected | Actual | Result |
|---|---|---|---|
| Branch | `verification/ced-p0-authority` | `verification/ced-p0-authority` | PASS |
| HEAD | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` | PASS |
| HEAD tree | `b6aa1d9cafb13154344274da187303076f338901` | `b6aa1d9cafb13154344274da187303076f338901` | PASS |
| HEAD parent | `3f23859c9d38828b1da673429c11d885b0b0219c` | `3f23859c9d38828b1da673429c11d885b0b0219c` | PASS |
| Staged paths | `0` | `0` | PASS |
| Worktree | `41 M, 1 D, 34 ??` | `41 M, 1 D, 34 ??` | PASS |
| Porcelain SHA-256 | `4aac344577a647f7a7c25655e4d2bbec2b187bd73979a564f6f5f75dafe49097` | same | PASS |
| Remote `main` | `b3c9d06` (must not have moved) | `b3c9d06c82a553a53bf9bca62cc521014ce4f159` | PASS |
| Temporary branch | absent | absent locally and remotely | PASS |

### 1.1 Expected ancestry

`main..HEAD` contained exactly four commits:

```text
06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2  fix(r2): preserve unknown requirement evidence
3f23859c9d38828b1da673429c11d885b0b0219c  R2: unknown-state integrity implementation (milestone)
ed83a02c5b14e3d5b116c00fd3621b66d0a4c14c  fix(AC10): gate eligibility claims on confirmed gaps
56093227307f5b4ab7d7511e14541fa3d496fc78  fix(P0-A): forward structured requirements to every analysis branch
```

`ed83a02` was resolved from Git to the full SHA
`ed83a02c5b14e3d5b116c00fd3621b66d0a4c14c`. The expected ancestry was
exactly four commits ahead of `main`, and it was.

### 1.2 Test-file preflight

The complete diff for
`app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt`
contained only:

- the required `org.junit.Assert.assertNull` import;
- selection of the existing `Bachelor of Science` assessment;
- `assertEquals(RequirementAssessmentState.CONFIRMED_GAP, assessment.state)`;
- `assertNull(assessment.reason)`;
- preservation of `evaluationComplete == true`;
- preservation of the mandatory `DEGREE: Bachelor of Science` gap assertion.

No duplicate B1-E characterization test was added to
`RequirementCoverageTest.kt`. `P2VerificationHarnessTest.kt` was not touched
by this execution and is not in the commit.

Current test-file SHA-256:
`21db7689eaa692e46e3c007f02bd0b3b0c04b66b037cf503f3a9188c188ee215`

### 1.3 Eight-path diff inspection

The complete diff for the eight allowlisted paths was generated from
`git diff HEAD -- <eight paths>` and inspected before staging.

| Path | Insertions | Deletions |
|---|---:|---:|
| `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt` | 89 | 21 |
| `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt` | 2 | 1 |
| `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt` | 224 | 0 |
| `app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt` | 5 | 0 |
| `app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt` | 79 | 5 |
| `app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt` | 1 | 1 |
| `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt` | 22 | 0 |
| `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt` | 11 | 0 |
| **Total** | **433** | **28** |

The reviewed patch is preserved verbatim at `eight-path.diff`; its SHA-256 is
`2a2909a643f4ead599cfdec873beda2c2d23bca0693c745df72d402cc4a44a6d`.
The same content is preserved as `staged-diff.diff` and `commit-diff.diff`.

No unexplained or unrelated hunk was found. No workflow, build, dependency, or
repository-settings path is part of the commit.

---

## 2. Branch, staging, and commit

Branch creation:

```bash
git switch -c r2/ci-draft-01
```

The dirty worktree was preserved. The original branch
`verification/ced-p0-authority` remained at `06bb4ac...`.

Staging:

```bash
git apply --cached --check /tmp/r2-eight.diff
git apply --cached /tmp/r2-eight.diff
```

Staged path verification:

- `git diff --cached --name-only` contained exactly **8** paths.
- The staged set matched the allowlist exactly.
- No other path was staged.
- The staged diff hash was
  `2a2909a643f4ead599cfdec873beda2c2d23bca0693c745df72d402cc4a44a6d`,
  identical to the reviewed patch.
- Porcelain after staging:
  `aef4cd95ed6b84a571942c085a5d1915e8bf67ff9aa1564e43074c75b454336b`.

Commit:

```text
d68026d7c55b50a2f50ed165dbd8aad27c51a69f
fix(r2): reconcile requirement evidence and provisional scoring
```

Commit body:

```text
Preserve unknown requirement evidence, reconcile job-required qualifications,
retain preferred-skill evidence, and add corrective regression coverage for B1-E
and provisional scoring.
```

Commit result: `8 files changed, 433 insertions(+), 28 deletions(-)`.
Commit parent: `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`.
Commit diff SHA-256:
`2a2909a643f4ead599cfdec873beda2c2d23bca0693c745df72d402cc4a44a6d`.

After commit:

- Current branch: `r2/ci-draft-01`
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- Original branch SHA: `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`
- Staged paths: `0`
- Porcelain SHA-256:
  `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237`
- Remaining worktree: `33 M, 1 D, 34 ??`, all unstaged

The remaining dirty files were not staged, committed, discarded, relocated, or
stashed. The executor stayed on `r2/ci-draft-01` after publication.

---

## 3. Push and Draft PR

Push result: success. Only the new temporary branch was pushed.

- Remote branch: `origin/r2/ci-draft-01`
- Remote branch SHA: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`

Draft PR:

- Number: `#5`
- URL: https://github.com/karabokeontse-png/VantEdge/pull/5
- State: `open`
- Draft: `true`
- Merged: `false`
- Head: `r2/ci-draft-01` at `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- Base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- PR commits: **5** (4 prior commits + 1 corrective commit)
- PR changed paths: **41**
- PR diffstat: **41 files changed, 3173 insertions(+), 197 deletions(-)**
- Merge commit ref exposed by GitHub: `9efc09a81df148fb1e2d55cfafeba7ebd6dd12b8`
- `refs/pull/5/head`: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- `refs/pull/5/merge`: `9efc09a81df148fb1e2d55cfafeba7ebd6dd12b8`

No merge was attempted. No reviewer, label, auto-merge setting, branch
protection change, workflow change, or repository-setting change was made.

### 3.1 Required disclosure text

The PR body contains the required disclosure verbatim:

> This is a Draft PR for CI verification only. No merge is authorized.The corrective implementation is limited to exactly eight paths.Due to repository ancestry, this PR exposes four prior commits and approximately 41 paths. This is the narrowest coherent review surface without history rewriting.R6 (qualification filter) remains unruled. This corrective change has not completed a successful local test run; CI is its first execution. Commit `06bb4ac` provenance is self-attested. Three UI screens previously flagged as dead appear in the diff from earlier commits. R2 remains OPEN.Draft status is not a technical merge lock. No person or automation is authorized to merge this PR.

The PR body then adds the verified actuals:

- Base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- Head: `r2/ci-draft-01` at `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- Commits entering the PR: **5** (4 prior commits + 1 corrective commit)
- Changed paths: **41**
- Diffstat: **41 files changed, 3173 insertions(+), 197 deletions(-)**
- The new corrective commit changes exactly **8** paths.
- No workflow, build, dependency, or repository-settings paths appear in the PR diff.
- This remains a Draft PR. No merge is authorized.

The approximate 41-path disclosure resolves to the verified actual count of
41 paths. The PR is not claimed to be limited to eight total changed paths.

---

## 4. CI results

All three expected workflows triggered on the `pull_request` event. GitHub
Actions checked out `refs/pull/5/merge`, so CI tested the synthetic PR merge
state of head `d68026d...` with base `b3c9d06...`, not the raw branch tip in
isolation. The run `head_sha` reported by the API is
`d68026d7c55b50a2f50ed165dbd8aad27c51a69f`; the checkout ref was
`refs/remotes/pull/5/merge`.

| Workflow | Run | Status | Conclusion | Jobs |
|---|---|---|---|---|
| `W5 Deterministic Scoring Tests` | https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277174 | completed | **success** | `W5 Unit Tests (JVM)` success; `W5 Baseline Benchmark` success |
| `Full CI + W5 Validation` | https://github.com/karabokeontse-png/VantEdge/actions/runs/41839277536 | completed | **success** | `W5 100-Run Determinism Validation` success |
| `VantEdge CI` | https://github.com/karabokeontse-png/VantEdge/actions/runs/41839278056 | completed | **failure** | `test` failure in `Run unit tests` |

### 4.1 Successful workflows

`W5 Deterministic Scoring Tests`:

- Run ID: `41839277174`
- Created: `2026-11-04T13:34:06Z`
- Updated: `2026-11-04T13:37:16Z`
- `W5 Unit Tests (JVM)`: success; `BUILD SUCCESSFUL in 2m 31s`
- `W5 Baseline Benchmark`: success; `BUILD SUCCESSFUL in 2m 51s`
- The workflow removes the host `android.aapt2FromMavenOverride` before building.

`Full CI + W5 Validation`:

- Run ID: `41839277536`
- Created: `2026-11-04T13:34:07Z`
- Updated: `2026-11-04T13:37:11Z`
- `W5 100-Run Determinism Validation`: success
- Compile: `BUILD SUCCESSFUL in 2m 27s`
- Full W5 test suite: `BUILD SUCCESSFUL in 19s`
- The workflow removes the host `android.aapt2FromMavenOverride` before building.

### 4.2 Red workflow finding

`VantEdge CI`:

- Run ID: `41839278056`
- Created: `2026-11-04T13:34:07Z`
- Updated: `2026-11-04T13:35:54Z`
- Job `test`: failure at step `Run unit tests`
- Exact failure:

```text
org.gradle.api.InvalidUserDataException: Specified AAPT2 executable does not exist:
/data/data/com.itsaky.androidide/files/home/android-sdk/build-tools/36.0.0/aapt2.
Must supply one of aapt2 from maven or custom location.
```

- Result: `BUILD FAILED in 1m 34s`
- No test results were produced; the upload step found no test report or test-result files.

This is an environment finding caused by the host `android.aapt2FromMavenOverride`
path in `gradle.properties`. `ci.yml` does not run the “Fix gradle.properties for
CI” step that `full-ci.yml` and `w5-tests.yml` use, so it resolves the invalid
host path on the GitHub runner. It is not evidence of an R2 code defect, and it
does not prove the tests fail.

No fix, retry, workflow edit, or follow-up commit was made. The red workflow is
recorded as a finding and left for a new directive.

### 4.3 Missing or skipped workflows

No expected workflow was missing. All three triggered. No workflow was skipped
by a path filter.

### 4.4 CI scope

CI verifies only the committed code under the GitHub Actions environment. It is
not device verification, installation verification, or runtime verification.
A green run does not close R2, B1-E replacement coverage, R6, or S1.

---

## 5. Preservation and final state

Porcelain hashes:

| Stage | Porcelain SHA-256 |
|---|---|
| Initial | `4aac344577a647f7a7c25655e4d2bbec2b187bd73979a564f6f5f75dafe49097` |
| After staging | `aef4cd95ed6b84a571942c085a5d1915e8bf67ff9aa1564e43074c75b454336b` |
| After commit | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` |
| After evidence files | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` |

The evidence directory is under the already-untracked `evidence/` directory, so
adding evidence files did not change the tracked-worktree porcelain hash.

Final state:

- Current branch: `r2/ci-draft-01`
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- Original branch: `verification/ced-p0-authority` at `06bb4ac...`
- Staged paths: `0`
- Remaining dirty worktree: `33 M, 1 D, 34 ??`
- The remaining dirty files are unstaged and preserved.
- `P2VerificationHarnessTest.kt` remains excluded from the commit.

No tracked file outside the authorized commit was staged or committed. The
commit contains exactly the eight allowlisted paths. No source, test, workflow,
build, dependency, or repository-settings file was changed by this execution
outside that commit.

---

## 6. Execution notes Z1-Z5 disposition

- **Z1 — Commit scope against CI:** Only the eight paths were committed.
  `P2VerificationHarnessTest.kt` was excluded. No scope widening occurred.
- **Z2 — Ancestry:** `ed83a02` was resolved to
  `ed83a02c5b14e3d5b116c00fd3621b66d0a4c14c`. `main` remained at
  `b3c9d06...`; ancestry was exactly four commits before the corrective commit.
- **Z3 — Branch creation:** `git switch -c` preserved the dirty worktree.
  After commit, the executor remained on `r2/ci-draft-01`. The original branch
  remains unchanged at `06bb4ac...`.
- **Z4 — Prepared patch:** The patch was generated from `git diff HEAD` for
  only the eight allowlisted paths. No outside hunk was present. The staged
  patch hash matched the reviewed patch hash exactly.
- **Z5 — CI expectations:** All three workflows triggered. `full-ci.yml` and
  `w5-tests.yml` succeeded after stripping the host aapt2 override. `ci.yml`
  failed on the host aapt2 path because it does not strip that override. This
  is an environment finding, not proof of an R2 defect. No fix or retry was
  performed.

R6 remains unruled. R2 remains **OPEN**. B1-E replacement coverage, R6, and S1
are not closed by this execution. The PR is a Draft and is not a technical merge
lock.

---

## 7. Evidence artifacts

| Artifact | Purpose |
|---|---|
| `REPORT.md` | This report |
| `eight-path.diff` | Complete reviewed/staged eight-path diff |
| `eight-path.sha256` | SHA-256 of `eight-path.diff` |
| `staged-diff.diff` | Staged diff after `git apply --cached` |
| `staged-diff.sha256` | SHA-256 of `staged-diff.diff` |
| `commit-diff.diff` | Diff of the corrective commit |
| `commit-diff.sha256` | SHA-256 of `commit-diff.diff` |
| `pr-body.md` | Exact PR body |
| `pr-response.json` | GitHub API response for PR #5 |
| `ci-runs.json` | Workflow run summary |
| `ci-detail.json` | Run and job details |
| `ci-vantedge-failure-excerpt.txt` | Exact `VantEdge CI` failure excerpt |
| `ci-success-excerpts.txt` | Successful build/test excerpts |
| `workflow-definitions.txt` | Relevant workflow filters and commands |
| `ARTIFACTS.sha256` | SHA-256 manifest for this evidence directory |
| `REPORT.sha256` | SHA-256 of this report |

The full eight-path staged diff is preserved verbatim in `eight-path.diff`.
Its SHA-256 is
`2a2909a643f4ead599cfdec873beda2c2d23bca0693c745df72d402cc4a44a6d`.
It is identical to `staged-diff.diff` and `commit-diff.diff`.

---

## 8. Reproduction commands

```bash
cd /root/vantedge-canonical

git rev-parse --abbrev-ref HEAD
git rev-parse HEAD
git rev-parse 'HEAD^{tree}'
git rev-parse 'HEAD^'
git diff --cached --name-only | wc -l
git status --porcelain=v1
git status --porcelain=v1 | sha256sum

git ls-remote origin refs/heads/main
git rev-list --count b3c9d06c82a553a53bf9bca62cc521014ce4f159..HEAD
git log --oneline b3c9d06c82a553a53bf9bca62cc521014ce4f159..HEAD

git diff HEAD -- app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt
sha256sum app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt

git diff --name-only b3c9d06c82a553a53bf9bca62cc521014ce4f159...HEAD
git diff --numstat b3c9d06c82a553a53bf9bca62cc521014ce4f159...HEAD

git ls-remote origin 'refs/pull/5/*'
```

---

## Appendix A — Full eight-path staged diff

The complete reviewed/staged diff is preserved verbatim at
`eight-path.diff` (644 lines, 29,143 bytes; SHA-256
`2a2909a643f4ead599cfdec873beda2c2d23bca0693c745df72d402cc4a44a6d`).
It is identical to `staged-diff.diff` and `commit-diff.diff`.
