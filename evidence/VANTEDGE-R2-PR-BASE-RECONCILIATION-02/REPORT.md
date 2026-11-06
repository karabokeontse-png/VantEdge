# VANTEDGE-R2-PR-BASE-RECONCILIATION-02

Status: **READ-ONLY BASE INVESTIGATION COMPLETE — NO SOURCE EDIT, COMMIT, PUSH, PR, OR REMOTE CHANGE**

- Repository: `/root/vantedge-canonical`
- Branch: `verification/ced-p0-authority`
- Expected local HEAD: `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`
- Actual local HEAD: `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`
- HEAD tree: `b6aa1d9cafb13154344274da187303076f338901`
- HEAD parent: `3f23859c9d38828b1da673429c11d885b0b0219c`
- Investigation time: `2026-11-04T11:57:49Z`
- Worktree: `41 M, 1 D, 34 ??`, `0` staged; porcelain SHA-256 `4aac344577a647f7a7c25655e4d2bbec2b187bd73979a564f6f5f75dafe49097`
- R2 status: **OPEN**

---

## 0. Definitive recommendation

**Use `origin/verification/phase-a-requirements-forwarding` as the PR base.**

- Exact branch SHA: `56093227307f5b4ab7d7511e14541fa3d496fc78`
- Merge base with local HEAD: `56093227307f5b4ab7d7511e14541fa3d496fc78`
- Commits reachable from local HEAD but not from the candidate: **3**
  1. `ed83a02` `fix(AC10): gate eligibility claims on confirmed gaps`
  2. `3f23859` `R2: unknown-state integrity implementation (milestone)`
  3. `06bb4ac` `fix(r2): preserve unknown requirement evidence`
- Commits reachable from the candidate but not from local HEAD: **0**
- Candidate contains `5609322...`: **yes**
- Candidate contains `ed83a02...`, `3f23859...`, `06bb4ac...`: **no**
- Local-HEAD PR surface: **41 paths**, `+2753/-190`
- Final simulated PR surface with the pending eight-path corrective commit:
  **41 paths**, `+3158/-190`

This is the narrowest coherent existing remote base. It is an ancestor of local
HEAD, it already contains the `5609322` P0-A dependency, and the PR would add
exactly the three later R2 commits plus the pending eight-path corrective commit.
It does not omit a required dependency: the only local R2 commit not in the diff
is `5609322`, because that commit is already in the base.

The base branch name was **not** used as a suitability criterion. The conclusion
is based on Git ancestry, commit membership, and path-level diffs.

No existing remote branch isolates the eight-path corrective commit. The
recommended base still exposes the three R2 commits above, which are the
dependencies and immediate corrective history for the eight-path change. If a
future directive requires an eight-path-only PR, no existing remote branch
provides that surface; that would require a different authorization.

---

## 1. Scope and preservation

This investigation was read-only. It did not:

- edit any source, test, Gradle, settings, workflow, or dependency file;
- stage, commit, branch, push, open a PR, merge, or change a remote ref;
- reset, clean, stash, revert, checkout, discard, or reformat the worktree;
- delete `.kotlin/`, build outputs, caches, or prior evidence.

Only new evidence files were written under
`evidence/VANTEDGE-R2-PR-BASE-RECONCILIATION-02/`.

The requested test strengthening in `CorrectiveUnknownStateTest.kt` was already
present in the worktree from the prior authorized reconciliation. This
investigation verified it but did not modify it.

---

## 2. Requested test-strengthening state

File:
`app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt`

Post-edit SHA-256:
`21db7689eaa692e46e3c007f02bd0b3b0c04b66b037cf503f3a9188c188ee215`

The existing test `evidence-supported qualification gaps are preserved and
complete` contains:

```kotlin
val assessment = mapped.record.requirementAssessments!!
    .first { it.text == "Bachelor of Science" }
assertEquals(RequirementAssessmentState.CONFIRMED_GAP, assessment.state)
assertNull(assessment.reason)
assertEquals(true, mapped.record.evaluationComplete)
assertTrue(mapped.record.gaps.any { it.skill == "DEGREE: Bachelor of Science" && it.importance == "MANDATORY" })
```

It also imports `org.junit.Assert.assertNull`.

This satisfies the corrected placement decision:

- explicit `RequirementAssessmentState.CONFIRMED_GAP`;
- explicit `reason == null`;
- explicit `evaluationComplete == true`;
- existing mandatory gap assertion preserved;
- no duplicate non-matching qualification test added to
  `RequirementCoverageTest.kt`;
- `P2VerificationHarnessTest.kt` not modified by this investigation;
- production qualification semantics not altered.

The exact diff is in `test-strengthening.diff`.

---

## 3. Candidate remote branches: exact ancestry

All remote branches were inspected. Every candidate is an ancestor of local HEAD,
so every candidate has `0` commits reachable from the candidate but not from
local HEAD. No candidate is diverged.

| Candidate | Exact SHA | Merge base with HEAD | Ahead (HEAD not candidate) | Behind (candidate not HEAD) | Contains 5609322 | Contains ed83a02 | Contains 3f23859 | Contains 06bb4ac | Paths | Local-HEAD diffstat |
|---|---|---|---:|---:|---|---|---|---|---:|---|
| `origin/main` | `b3c9d06c82a553a53bf9bca62cc521014ce4f159` | `b3c9d06...` | 4 | 0 | no | no | no | no | 41 | `+2768/-197` |
| `origin/verification/ced-p0-authority` | `b3c9d06c82a553a53bf9bca62cc521014ce4f159` | `b3c9d06...` | 4 | 0 | no | no | no | no | 41 | `+2768/-197` |
| `origin/verification/phase-a-requirements-forwarding` | `56093227307f5b4ab7d7511e14541fa3d496fc78` | `5609322...` | 3 | 0 | **yes** | no | no | no | 41 | `+2753/-190` |
| `origin/recovery/reconstructed` | `71d130de810ec79b60d80800db134eea3d3928b4` | `71d130d...` | 67 | 0 | no | no | no | no | 128 | `+9150/-1251` |
| `origin/recovery/main` | `c67d003853e6a1f457896e9fd1cf0189e546293d` | `c67d003...` | 79 | 0 | no | no | no | no | 488 | `+26578/-40601` |
| `origin/fix/p0-sanitizer-r5-skill-ocr` | `e620278dd75ce5507d3b0bb06fcd3345cbc74e23` | `e620278...` | 43 | 0 | no | no | no | no | 113 | `+6693/-942` |
| `origin/feature/ced-10-fallback-restoration` | `3e800b3762487f7f384fdb228bf481577cd9693f` | `3e800b3...` | 30 | 0 | no | no | no | no | 94 | `+4794/-891` |
| `origin/feature/td-compatibility-001` | `965c884a2d2a5d9fc8c32b7341d7631f89151776` | `965c884...` | 25 | 0 | no | no | no | no | 90 | `+4764/-925` |
| `origin/feature/td-telemetry-001` | `b56e6c7b8c0d005a245abcaafa4f863989c8c9fc` | `b56e6c7...` | 29 | 0 | no | no | no | no | 90 | `+4764/-862` |

`origin/main` and `origin/verification/ced-p0-authority` are the same commit.

The four local R2 commits are:

```text
56093227307f5b4ab7d7511e14541fa3d496fc78
ed83a02c5b14e3d5b116c00fd3621b66d0a4c14c
3f23859c9d38828b1da673429c11d885b0b0219c
06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2
```

`origin/verification/phase-a-requirements-forwarding` is the only candidate that
contains any of them; it contains exactly `5609322...`.

---

## 4. Candidate-by-candidate review

### 4.1 `origin/main` / `origin/verification/ced-p0-authority`

- Base SHA: `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- Merge base: `b3c9d06...`
- Commits entering PR: 4
  - `5609322` `fix(P0-A): forward structured requirements to every analysis branch`
  - `ed83a02` `fix(AC10): gate eligibility claims on confirmed gaps`
  - `3f23859` `R2: unknown-state integrity implementation (milestone)`
  - `06bb4ac` `fix(r2): preserve unknown requirement evidence`
- Paths: 41
- Local-HEAD diffstat: `+2768/-197`
- Final simulated diffstat with pending corrective commit: `+3173/-197`
- Contains the four local R2 commits: **none**
- Unrelated changes: no non-R2 build/workflow/historical changes are in the
  41-path surface, but the PR exposes all four R2 commits, including the P0-A
  commit, rather than only the three later R2 commits.
- Required dependency omission: none; all four commits are in the PR.

This is coherent but broader than the recommended base by one commit
(`5609322`) and `+15/-7` of diff.

### 4.2 `origin/verification/phase-a-requirements-forwarding`

- Base SHA: `56093227307f5b4ab7d7511e14541fa3d496fc78`
- Merge base: `5609322...`
- Commits entering PR: 3
  - `ed83a02` `fix(AC10): gate eligibility claims on confirmed gaps`
  - `3f23859` `R2: unknown-state integrity implementation (milestone)`
  - `06bb4ac` `fix(r2): preserve unknown requirement evidence`
- Paths: 41
- Local-HEAD diffstat: `+2753/-190`
- Final simulated diffstat with pending corrective commit: `+3158/-190`
- Contains `5609322`: **yes**
- Contains `ed83a02`, `3f23859`, `06bb4ac`: **no**
- Unrelated changes: the 41 paths are the R2 corrective/test paths; no build,
  workflow, dependency, or historical recovery paths are included. The PR still
  exposes the three R2 commits, which are the dependencies/history for the
  eight-path corrective.
- Required dependency omission: **none**. The only local R2 commit absent from
  the diff is `5609322`, and it is already in the base. The final PR retains all
  eight corrective paths.

The path set is identical to the `origin/main` 41-path set. The content differs
only in the `CycleViewModel.kt` hunk introduced by `5609322`; because the
recommended base already contains that commit, the PR does not need to reintroduce
it. This is the narrowest existing surface.

### 4.3 `origin/recovery/reconstructed`

- Base SHA: `71d130de810ec79b60d80800db134eea3d3928b4`
- Merge base: `71d130d...`
- Commits entering PR: 67
- Paths: 128
- Local-HEAD diffstat: `+9150/-1251`
- Contains the four local R2 commits: **none**
- Assessment: **not coherent**. The PR would expose 67 historical commits and
  128 paths, including recovery-era source, workflow, build, asset, and test
  changes unrelated to the eight-path corrective. It would not omit the
  corrective dependencies, but the review surface is far too broad.

### 4.4 `origin/recovery/main`

- Base SHA: `c67d003853e6a1f457896e9fd1cf0189e546293d`
- Merge base: `c67d003...`
- Commits entering PR: 79
- Paths: 488
- Local-HEAD diffstat: `+26578/-40601`
- Contains the four local R2 commits: **none**
- Assessment: **not coherent**. This is the broadest candidate: 79 commits and
  488 paths, including large historical recovery changes. It is unsuitable as a
  narrow review surface.

### 4.5 `origin/fix/p0-sanitizer-r5-skill-ocr`

- Base SHA: `e620278dd75ce5507d3b0bb06fcd3345cbc74e23`
- Merge base: `e620278...`
- Commits entering PR: 43
- Paths: 113
- Local-HEAD diffstat: `+6693/-942`
- Contains the four local R2 commits: **none**
- Assessment: **not coherent**. The surface includes 43 commits and 113 paths,
  with `.github/workflows/w5-tests.yml`, `app/build.gradle`, validation assets,
  and P0 history beyond the R2 corrective.

### 4.6 Feature branches

| Candidate | Commits | Paths | Diffstat | Contains R2 commits | Assessment |
|---|---:|---:|---|---|---|
| `origin/feature/ced-10-fallback-restoration` | 30 | 94 | `+4794/-891` | none | not coherent; feature/history surface |
| `origin/feature/td-compatibility-001` | 25 | 90 | `+4764/-925` | none | not coherent; feature/history surface |
| `origin/feature/td-telemetry-001` | 29 | 90 | `+4764/-862` | none | not coherent; feature/history surface |

These branches include build, workflow, asset, and feature-specific changes and
do not provide a narrow R2 corrective review surface.

---

## 5. Path-level PR diff for the recommended base

`origin/verification/phase-a-requirements-forwarding...HEAD` contains 41 paths:

```text
M  app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt
A  app/src/main/java/com/vantedge/app/data/domain/ScoreCompleteness.kt
M  app/src/main/java/com/vantedge/app/data/engine/extraction/ConfidenceEngine.kt
M  app/src/main/java/com/vantedge/app/data/model/CompatibilityRecord.kt
A  app/src/main/java/com/vantedge/app/data/model/RequirementAssessment.kt
M  app/src/main/java/com/vantedge/app/data/model/extraction/FieldConfidence.kt
A  app/src/main/java/com/vantedge/app/data/viewmodel/CycleScoreAggregation.kt
M  app/src/main/java/com/vantedge/app/data/viewmodel/CycleViewModel.kt
M  app/src/main/java/com/vantedge/app/domain/extraction/ExtractionDecisionCoordinator.kt
M  app/src/main/java/com/vantedge/app/domain/extraction/JobExtractionOrchestrator.kt
M  app/src/main/java/com/vantedge/app/narrative/W5Evidence.kt
M  app/src/main/java/com/vantedge/app/ui/screens/AdvancedAnalysisScreen.kt
M  app/src/main/java/com/vantedge/app/ui/screens/AnalysisResultScreen.kt
M  app/src/main/java/com/vantedge/app/ui/screens/CompatibilityResultScreen.kt
M  app/src/main/java/com/vantedge/app/ui/screens/CycleHistoryScreen.kt
M  app/src/main/java/com/vantedge/app/ui/screens/DashboardScreen.kt
M  app/src/main/java/com/vantedge/app/ui/screens/ResultScreen.kt
A  app/src/main/java/com/vantedge/app/ui/screens/ScorePresentation.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/AxisEvaluators.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/DeterministicCompatibilityEngine.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/GapAnalyzer.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/HintBuilder.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/JobRequirementAdapter.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/RequirementEvidence.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/W5ScoreEngine.kt
M  app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt
M  app/src/main/java/com/vantedge/app/w6/improvement/W6Rules.kt
A  app/src/test/java/com/vantedge/app/data/engine/extraction/ConfidenceEngineUnknownTest.kt
A  app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt
A  app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt
A  app/src/test/java/com/vantedge/app/domain/extraction/ExtractionDecisionCoordinatorUnknownTest.kt
A  app/src/test/java/com/vantedge/app/ui/screens/ScorePresentationTest.kt
A  app/src/test/java/com/vantedge/app/ui/screens/UnassessedPresentationTest.kt
A  app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt
A  app/src/test/java/com/vantedge/app/w5/scoring/HintBuilderMeasurednessTest.kt
M  app/src/test/java/com/vantedge/app/w5/scoring/JobRequirementAdapterTest.kt
A  app/src/test/java/com/vantedge/app/w5/scoring/JobTitleRoleRelevanceProductionTest.kt
A  app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt
A  app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt
M  app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt
A  app/src/test/java/com/vantedge/app/w6/improvement/W6UnknownAxisTest.kt
```

The same 41 paths appear for `origin/main...HEAD`; the path sets are identical.
The only content difference between the two local-HEAD PR surfaces is the
`CycleViewModel.kt` hunk from `5609322`, which is already in the recommended
base.

The pending eight-path corrective commit adds changes within this same 41-path
set; it does not add new paths. The simulated final PR surface from the
recommended base is 41 paths, `+3158/-190`.

---

## 6. Reproducible Git evidence

The following commands were used. They are read-only and reproducible:

```bash
cd /root/vantedge-canonical
HEAD_SHA=$(git rev-parse HEAD)
for ref in \
  origin/main \
  origin/verification/ced-p0-authority \
  origin/verification/phase-a-requirements-forwarding \
  origin/recovery/reconstructed \
  origin/recovery/main \
  origin/fix/p0-sanitizer-r5-skill-ocr \
  origin/feature/ced-10-fallback-restoration \
  origin/feature/td-compatibility-001 \
  origin/feature/td-telemetry-001
do
  echo "== $ref =="
  git rev-parse "$ref"
  git merge-base HEAD "$ref"
  git rev-list --count "$ref..HEAD"
  git rev-list --count "HEAD..$ref"
  git log --oneline --no-decorate "$ref..HEAD"
  git log --oneline --no-decorate "HEAD..$ref"
  git diff --name-status "$ref...HEAD"
  git diff --stat "$ref...HEAD"
  for c in \
    56093227307f5b4ab7d7511e14541fa3d496fc78 \
    ed83a02c5b14e3d5b116c00fd3621b66d0a4c14c \
    3f23859c9d38828b1da673429c11d885b0b0219c \
    06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2
  do
    if git merge-base --is-ancestor "$c" "$ref"; then echo "contains $c"; else echo "lacks $c"; fi
  done
done
```

Additional artifacts contain the full per-candidate commit and path lists.

---

## 7. Evidence artifacts

Directory:
`/root/vantedge-canonical/evidence/VANTEDGE-R2-PR-BASE-RECONCILIATION-02/`

| File | Purpose |
|---|---|
| `REPORT.md` | This report |
| `identity.txt` | HEAD/worktree identity and status hash |
| `branch-refs.txt` | Local and remote refs plus `git ls-remote --heads` |
| `four-local-commits.txt` | Full metadata and changed paths for the four local commits |
| `test-strengthening.diff` | Exact current B1-E test diff |
| `candidate-summary.tsv` | Exact candidate ancestry/diff summary |
| `candidate-*.txt` | Per-candidate commits, paths, diffstat, membership checks |
| `pr-local-head-*.patch` | Full local-HEAD PR patch for `main` and recommended base |
| `pr-local-head-*.name-status.txt` | Exact path-level diff for `main` and recommended base |
| `pr-local-head-*.stat.txt` | Exact diffstat for `main` and recommended base |
| `final-pr-surface-*.patch` | Simulated final PR patch including pending corrective commit |
| `final-pr-surface-*.txt` | Simulated final PR commit/path/diffstat summary |
| `main-vs-phasea.txt` | Path-set comparison and content diff between the two surfaces |
| `final-identity.txt` | Post-investigation identity and worktree preservation check |

---

## 8. Conclusion

- The corrected test placement is satisfied: the existing
  `CorrectiveUnknownStateTest` production-path test is strengthened; no duplicate
  test was added to `RequirementCoverageTest.kt`.
- No existing remote branch isolates only the eight-path corrective commit.
- The narrowest coherent existing remote base is
  **`origin/verification/phase-a-requirements-forwarding`** at
  `56093227307f5b4ab7d7511e14541fa3d496fc78`.
- That base already contains `5609322`, so the PR would not omit that dependency.
  It would add the three later R2 commits plus the pending eight-path corrective
  commit: 41 paths, `+3158/-190`.
- `origin/main` is coherent but broader by one commit: 41 paths, `+3173/-197`.
- `origin/recovery/reconstructed` (67 commits, 128 paths) and
  `origin/recovery/main` (79 commits, 488 paths) are not coherent review
  surfaces. The fix and feature branches are also too broad.
- No source edit, staging, commit, branch creation, push, PR, merge, or remote ref
  change was made.

**R2 remains OPEN.** This report is read-only evidence and a base recommendation;
it does not authorize the final commit or PR.
