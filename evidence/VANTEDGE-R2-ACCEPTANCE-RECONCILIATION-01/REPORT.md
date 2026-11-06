# VANTEDGE-R2-ACCEPTANCE-RECONCILIATION-01

Status: **READ-ONLY AUDIT COMPLETE — AUTHORITATIVE CONTRACT NOT RECOVERED — R2 ACCEPTANCE NOT DEMONSTRATED**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Mode: READ-ONLY
- Audit timestamp: `2026-11-05T06:26:35Z`
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01`
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- HEAD tree: `fdf88050c7256eee8deef404a8ec0e9e3ae0aaf7`
- Draft PR: #5 — https://github.com/karabokeontse-png/VantEdge/pull/5
- PR state: `open`, draft `true`, merged `false`
- PR head: `r2/ci-draft-01` at `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`
- PR base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- R2 status: **OPEN**

---

## 0. Executive summary

The authoritative wording of R2 acceptance criteria **AC-U1 through AC-U11 was not recovered**.

The available project evidence contains only:

- a derived AC-U mapping table in the CED preflight closure report,
- a derived AC-U implementation-status table in the CED-01 execution report,
- blanket references such as `AC-U1 … AC-U11: NOT IMPLEMENTED / NOT VERIFIED`,
- references to `AC-U8` and `AC-U9` as unverified runtime criteria,
- the current directive’s reference to `AC-U1 through AC-U11` without definitions.

No recovered document states the exact AC-U1 through AC-U11 wording with provenance establishing that the wording is authoritative. The closest design contract, D1–D6 plus T1–T12 in the preflight closure report, does not assign AC-U identifiers or provide exact AC-U wording. The AC-U table in that report is a derived mapping, not the original contract.

Because the authoritative contract is unavailable, this report does **not** invent or reconstruct the eleven criteria. All eleven rows are therefore classified:

`BLOCKED — CONTRACT NOT RECOVERED`

Final overall decision:

`R2 ACCEPTANCE NOT DEMONSTRATED`

Existing CI and source evidence is recorded below, but it cannot be promoted into criterion-level product acceptance without the authoritative criteria. In particular, runtime persistence and UI behavior for AC-U8/AC-U9 remain unverified; `CompatibilityRecordLegacyJsonTest` and `CycleScoreAggregationTest` were not demonstrated as executed in the two successful W5-filtered runs; and complete mapper branch coverage is not demonstrated.

No source, test, workflow, Gradle, dependency, PR, branch, HEAD, index, or existing worktree content was modified. No build, test, or CI workflow was run or triggered. Only this report and its checksum sidecar were created.

---

## 1. Repository and PR contract snapshot

Preflight was performed read-only before this report was written.

| Item | Expected | Observed | Result |
|---|---|---|---|
| Repository | `/root/vantedge-canonical` | `/root/vantedge-canonical` | PASS |
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | PASS |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | PASS |
| HEAD tree | not specified | `fdf88050c7256eee8deef404a8ec0e9e3ae0aaf7` | RECORDED |
| Staged paths | `0` | `0` | PASS |
| Worktree summary | preserved | `33 modified, 1 deleted, 34 untracked, 0 staged` | RECORDED |
| Porcelain SHA-256 | preserved | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` | PASS |
| PR | #5, open, draft, unmerged | #5, open, draft, unmerged | PASS |
| PR head | `r2/ci-draft-01` at `d68026d...` | same | PASS |
| PR base | `main` at `b3c9d06...` | same | PASS |

PR state was read directly from the GitHub API at the time of this audit. No PR field was changed.

The worktree is intentionally dirty and is not a clean acceptance state. This report does not claim that the worktree is clean, that the dirty state is authorized, or that the current worktree is byte-identical to the CED-01 post-state.

---

## 2. Search scope and authoritative-contract recovery results

### 2.1 Search scope

Read-only searches were performed across:

1. `/root/checkpoints` — all checkpoint directories and raw evidence.
2. `/root/vantedge-canonical` — tracked and untracked repository files, including `evidence/`.
3. `/data/user/0/ai.opencode.app/files/projects/Vantedge` — project audit reports and mirrored checkpoint evidence.
4. Repository Git history — `git log --all -S'AC-U' -- .` and `git grep` across all commits returned by `git rev-list --all`.
5. The opencode SQLite database at `/data/user/0/ai.opencode.app/files/home/.local/share/opencode/opencode.db` — user text parts and session transcripts containing `AC-U`.

Searches used exact and pattern matches for `AC-U[0-9]`, `AC-U1`, and `AC-U11`, plus surrounding context inspection.

### 2.2 Recovery results

| Location | What was found | Classification |
|---|---|---|
| `/root/checkpoints/VANTEDGE-R2-CED-PREFLIGHT-CLOSURE-01-20261030-150001/REPORT.md:586-600` | Section 8, “AC-U mapping”: an 11-row table mapping AC-U1–AC-U11 to D1–D6 and T1–T12 with one-line paraphrases. The report also states that implementation authorization was `NONE` at that time. | Derived mapping only. Not exact criterion wording. |
| `/root/checkpoints/VANTEDGE-R2-UNKNOWN-STATE-INTEGRITY-CED-01-20261030-153602/REPORT.md:126-140` | Section 5, “AC-U mapping (implementation evidence)”: an 11-row status table with claims such as `Implemented / unit-verified` and `runtime NOT VERIFIED`. | Derived status table only. Not exact criterion wording. |
| `/root/checkpoints/VANTEDGE-R2-SOURCE-DELTA-RECONCILIATION-01-20261030-135143/REPORT.md:12` | Blanket statement: `AC-U1 … AC-U11: NOT IMPLEMENTED / NOT VERIFIED`. | No criterion wording. |
| `/data/user/0/ai.opencode.app/files/projects/Vantedge/VANTEDGE-R2-CURRENT-HEAD-AUTHORIZATION-RECONCILIATION-01-REPORT.md:75,88,274-276` | References `AC-U1–AC-U11` in an authorization summary; states that the primary directive is absent and that AC-U8/AC-U9 runtime remains `NOT VERIFIED`. | Secondary reference. No criterion wording. |
| `/data/user/0/ai.opencode.app/files/projects/Vantedge/evidence/put here!/VANTEDGE-R2-CORRECTIVE-UNKNOWN-EVIDENCE-INTEGRITY-01-REPORT.md:439` | States that AC-U8 and AC-U9 remain unverified. | Secondary limitation. No criterion wording. |
| `/data/user/0/ai.opencode.app/files/projects/Vantedge/evidence/put here!/VANTEDGE-R2-FINAL-CORRECTIVE-MICRO-PATCH-01-REPORT.md:595` | States that AC-U8 and AC-U9 remain unverified. | Secondary limitation. No criterion wording. |
| `/root/vantedge-canonical/evidence/VANTEDGE-R2-CORRECTIVE-UNKNOWN-EVIDENCE-INTEGRITY-01/REPORT.md:307` | States that AC-U8 and AC-U9 remain unverified. | Secondary limitation. No criterion wording. |
| `/root/vantedge-canonical/evidence/VANTEDGE-R2-FINAL-CORRECTIVE-MICRO-PATCH-01/REPORT.md:595` | States that AC-U8 and AC-U9 remain unverified. | Secondary limitation. No criterion wording. |
| Project evidence mirrors under `/data/user/0/ai.opencode.app/files/projects/Vantedge/evidence/` | Duplicate the three checkpoint reports listed above. | Duplicate derived material. |
| Repository Git history | No commit or tracked file containing `AC-U1` through `AC-U11` was found by `git log -S` or `git grep` across all refs. | No authoritative contract. |
| opencode SQLite database | Contains the current directive and session transcript/summary references to `AC-U`, but no original CED-01 directive containing exact AC-U definitions. | No authoritative contract. |

### 2.3 Recovery conclusion

**AUTHORITATIVE CONTRACT NOT RECOVERED.**

The AC-U identifiers exist only in derived mapping/status material and secondary limitation statements. No recovered source establishes:

- the exact wording of AC-U1 through AC-U11,
- the document or message that originally issued those criteria,
- a version, commit, or checkpoint identity for the authoritative wording,
- evidence that the derived table is a faithful transcription rather than a summary.

The preflight closure report’s D1–D6 design decisions and T1–T12 test definitions are substantive contract material, but they are not an AC-U contract. They do not define the eleven criteria and cannot be substituted for them without inventing wording.

---

## 3. Exact wording and provenance for AC-U1 through AC-U11

For every criterion, the exact authoritative wording was not recovered.

| Criterion | Exact authoritative wording | Original document or file path | Version/commit/checkpoint identity | Provenance finding |
|---|---|---|---|---|
| AC-U1 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U2 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U3 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U4 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U5 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U6 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U7 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U8 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U9 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U10 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |
| AC-U11 | `AUTHORITATIVE CONTRACT NOT RECOVERED` | None recovered | None recovered | No authoritative source found. |

No exact criterion text is reproduced in this report because no authoritative text was recovered.

---

## 4. Eleven-row acceptance matrix

The “derived paraphrase” column is reproduced only to identify the existing derived material. It is **not** authoritative criterion wording and was **not** used to assign a non-blocked disposition.

| ID | Derived paraphrase (non-authoritative) | Disposition | Evidence references and limitations |
|---|---|---|---|
| AC-U1 | `provisionalScore` uses only measured weights. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 590. Existing orientation: `ProvisionalScoreTest` 11/11 in both successful W5-filtered runs, including measured-weight normalization and isolated per-axis tests. Limitation: no authoritative wording, expected tolerance, or acceptance threshold. |
| AC-U2 | Fully measured provisional equals `score`; legacy scoring untouched. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 591. Existing orientation: `ProvisionalScoreTest` “fully measured input keeps provisional equal to legacy score” passed in both runs; `RequirementCoverageTest` “fully measured record keeps provisional equal to legacy values” passed. Limitation: no authoritative criterion text. |
| AC-U3 | `unknownAxes` plus `evaluationComplete=false`; no authoritative label. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 592; source references `W5ScoreEngine.kt:44` and `ScorePresentation.kt:15-22`. Existing orientation: `CorrectiveUnknownStateTest` incomplete-state tests passed; `ProvisionalScoreTest` null-score tests passed. Limitation: no authoritative definition of “authoritative label” or runtime UI evidence. |
| AC-U4 | Every required requirement appears in `requirementAssessments`. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 593. Existing orientation: `RequirementCoverageTest` 21/21 in both runs, including inventory-order and fallback-inventory tests that directly invoke `CompatibilityRecordMapper.map`. Limitation: complete mapper branch coverage is not demonstrated; no authoritative inventory-completeness definition. |
| AC-U5 | `MATCHED`, `CONFIRMED_GAP`, and `UNASSESSED` remain distinct; `gaps` and `relevancyItems` projections. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 594. Existing orientation: `RequirementCoverageTest` distinct-state, one-gap, and one-matched-skill tests passed; `CorrectiveUnknownStateTest` 10/10 passed. Limitation: no authoritative projection contract. |
| AC-U6 | Empty skills do not erase mapped inventory. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 595. Existing orientation: `RequirementCoverageTest` “empty skill evidence keeps every requirement unassessed and inventory intact” passed; `CorrectiveUnknownStateTest` “empty skills preserve mapped requirements as unassessed inventory” passed. Limitation: unit-level only; no runtime persistence/UI evidence. |
| AC-U7 | P2 unchanged; no coverage signal. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 596. Existing orientation: CED-01 D5/T9 and mapper audit state no P2 production change; corrective report says P2 tests exercise the contract entrypoint and parameter surface, not an orchestrator, database, or UI path. Limitation: no executed P2 independence test in the two successful W5-filtered runs. |
| AC-U8 | Gson additive persistence. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 597. Existing orientation: `CompatibilityRecordLegacyJsonTest` contains persistence/round-trip tests, but no JUnit XML for that class exists in either successful W5-filtered run. No device persistence run was performed. Limitation: not demonstrated as executed; no authoritative persistence criterion. |
| AC-U9 | `ResultScreen` unassessed section; reason plus original text. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 598. Existing orientation: CED-01 and later audits state runtime UI rendering is `NOT VERIFIED`; no UI/device/emulator run exists. Limitation: no executed UI evidence and no authoritative UI criterion. |
| AC-U10 | `eligibilityClaimAllowed` cannot be true for new records with unassessed requirements. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 599; CED-01 D4 and `ScorePresentation.kt:18-19` source inspection. Existing orientation: `RequirementCoverageTest` false-completeness tests passed; no direct `eligibilityClaimAllowed` runtime test was identified. Limitation: no authoritative eligibility criterion. |
| AC-U11 | Regression guard; no unrelated mutation. | `BLOCKED — CONTRACT NOT RECOVERED` | Derived source: preflight report line 600. Existing orientation: CED-01 report claims preservation audit and partial staging; mapper audit confirms the eight-path corrective commit. Limitation: current worktree is dirty (`33 M, 1 D, 34 ??`) with post-CED-01 drift, so “no unrelated mutation” is not demonstrated for the current state. |

All eleven rows are `BLOCKED — CONTRACT NOT RECOVERED`. No criterion is assigned `DEMONSTRATED`, `PARTIALLY DEMONSTRATED`, `NOT DEMONSTRATED`, or `CONTRADICTED` because the authoritative expected behavior is unavailable.

---

## 5. Disposition summary

| Disposition | Count | Criteria |
|---|---:|---|
| `DEMONSTRATED` | 0 | None |
| `PARTIALLY DEMONSTRATED` | 0 | None |
| `NOT DEMONSTRATED` | 0 | None |
| `CONTRADICTED` | 0 | None |
| `BLOCKED — CONTRACT NOT RECOVERED` | 11 | AC-U1 through AC-U11 |

The absence of `NOT DEMONSTRATED` rows is not a positive finding. It means the criteria cannot be evaluated at all because their authoritative wording is missing.

---

## 6. Test execution evidence versus product acceptance evidence

### 6.1 Existing executed test evidence

The two successful W5-filtered workflow runs are:

- Run 1: `41839277174` — W5 Deterministic Scoring Tests, head `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`.
- Run 2: `41839277536` — Full CI + W5 Validation, head `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`.

Both runs executed the filter `com.vantedge.app.w5.scoring.*`.

| Test class | Run 1 `41839277174` | Run 2 `41839277536` | Evidence status |
|---|---|---|---|
| `W5ScoreEngineTest` | 34/34 passed | 34/34 passed | VERIFIED executed |
| `RequirementCoverageTest` | 21/21 passed | 21/21 passed | VERIFIED executed |
| `CorrectiveUnknownStateTest` | 10/10 passed | 10/10 passed | VERIFIED executed |
| `ProvisionalScoreTest` | 11/11 passed | 11/11 passed | VERIFIED executed |
| `CompatibilityRecordLegacyJsonTest` | No XML | No XML | NOT DEMONSTRATED as executed |
| `CycleScoreAggregationTest` | No XML | No XML | NOT DEMONSTRATED as executed |

The 34/34 `W5ScoreEngineTest` result is recorded in `VANTEDGE-R2-PR-DISCLOSURE-AND-W5-TEST-VERIFICATION-01`. The other class-level counts and the absent-class findings are recorded in `VANTEDGE-R2-CI-TEST-CLASS-EXECUTION-VERIFICATION-01`.

The failed general CI run `41839278056` produced zero test-result artifacts. It failed before test execution because the configured AAPT2 executable was unavailable. It is not evidence that tests passed or failed.

### 6.2 Mapper coverage limitation

`VANTEDGE-R2-MAPPER-COVERAGE-AUDIT-01` found that the executed tests directly invoke `CompatibilityRecordMapper.map` for many changed behaviors. The audit also found that complete mapper coverage is not demonstrated, including:

- `assessQualification`’s changed `UNRECONCILED` else reason,
- the new `inventorySource` and `preferredReconstructable` diagnostics keys,
- the reordered `PREFERRED` plus null-canonical skill path,
- case-insensitive profile-evidence matching beyond exact-case inputs,
- serialization round-trip of the new `UNRECONCILED` reason, because `CompatibilityRecordLegacyJsonTest` was not executed.

### 6.3 PR disclosure evidence

`VANTEDGE-R2-PR-DISCLOSURE-CORRECTION-01` reports the PR #5 disclosure correction as `COMPLIANT`. This is administrative evidence about the PR body, not evidence of product acceptance.

### 6.4 Separation statement

The following are **test execution evidence**, not product acceptance evidence:

- class-level JUnit XML pass counts,
- workflow conclusion,
- source-level mapper call-graph analysis,
- PR disclosure compliance.

A test class pass is not proof of an acceptance criterion unless the criterion’s exact expected behavior is exercised and asserted. Because the authoritative AC-U wording is unavailable, no class-level pass is promoted to a criterion-level `DEMONSTRATED` disposition.

The following are **product acceptance evidence** but are absent or insufficient:

- device/emulator runtime execution,
- Room persistence round-trip on device,
- Compose UI rendering of unassessed requirements,
- screenshot or log evidence for AC-U8/AC-U9,
- an authoritative criterion-by-criterion evidence map.

---

## 7. Remaining evidence gaps

1. **Authoritative AC-U contract absent.** No exact AC-U1–AC-U11 wording or issuing source was recovered.
2. **No authoritative provenance.** The derived tables do not establish that their one-line paraphrases are faithful to the original criteria.
3. **No runtime evidence for AC-U8/AC-U9.** Persistence and UI rendering remain `NOT VERIFIED`.
4. **Two relevant test classes were not demonstrated as executed in CI.** `CompatibilityRecordLegacyJsonTest` and `CycleScoreAggregationTest` are outside the successful W5 filter and have no JUnit XML.
5. **Complete mapper branch coverage is not demonstrated.** The mapper audit identifies changed branches without executed assertions.
6. **General CI failed before tests.** Run `41839278056` provides no test-result evidence.
7. **No independent test execution in this audit.** No Gradle, build, unit test, instrumentation test, CI workflow, or coverage instrumentation was run.
8. **Current worktree is dirty and drifted.** The observed worktree is not a clean acceptance baseline and includes post-CED-01 changes.
9. **No criterion-level acceptance mapping is possible.** The derived table cannot substitute for the missing authoritative contract.

These gaps are recorded only. This directive does not authorize implementation, additional tests, CI repair, or closure work to fill them.

---

## 8. Final overall decision

`R2 ACCEPTANCE NOT DEMONSTRATED`

R2 remains **OPEN**.

The evidence is sufficient to show that some unit-level behaviors were tested and passed in two W5-filtered CI runs. It is not sufficient to show that the authoritative R2 acceptance criteria were met, because the authoritative AC-U1 through AC-U11 contract was not recovered. Under the directive, missing criterion wording must not be reconstructed, and the overall decision must be `R2 ACCEPTANCE NOT DEMONSTRATED`.

R6, B1-E, and S1 remain outside this directive and are not closed by this work. PR #5 remains open, draft, and unmerged.

---

## 9. Non-mutation and completion boundary

- Branch remained `r2/ci-draft-01`.
- HEAD remained `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`.
- Staged paths remained `0`.
- No source, test, workflow, Gradle file, dependency, settings file, PR field, branch, HEAD, index, or existing worktree content was modified.
- No build, unit test, instrumentation test, CI workflow, or coverage instrumentation was run.
- No CI run was triggered or retried.
- No commit, push, merge, or PR close was performed.
- The only files created are:
  - `/root/vantedge-canonical/evidence/VANTEDGE-R2-ACCEPTANCE-RECONCILIATION-01/REPORT.md`
  - `/root/vantedge-canonical/evidence/VANTEDGE-R2-ACCEPTANCE-RECONCILIATION-01/REPORT.sha256`

---

## Appendix A — Exact derived AC-U references found

The following lines are reproduced to demonstrate that the available AC-U material is a derived mapping or status table, not authoritative criterion wording.

From `/root/checkpoints/VANTEDGE-R2-CED-PREFLIGHT-CLOSURE-01-20261030-150001/REPORT.md:590-600`:

```text
| AC-U1 | `provisionalScore` uses only measured weights | D1 formula; T1–T2 |
| AC-U2 | fully measured provisional == `score`; legacy scoring untouched | D1; T1 |
| AC-U3 | `unknownAxes` + `evaluationComplete=false`; no authoritative label | `W5ScoreEngine.kt:44`; `ScorePresentation.kt:15-22`; T2–T3 |
| AC-U4 | every required requirement in `requirementAssessments` | D2; `JobRequirementAdapter` inventory; T5–T6 |
| AC-U5 | `MATCHED` / `CONFIRMED_GAP` / `UNASSESSED` distinct; `gaps`/`relevancyItems` projections | D2; T5–T7 |
| AC-U6 | empty skills do not erase mapped inventory | D3; `GapAnalyzer` change; T5 |
| AC-U7 | P2 unchanged; no coverage signal | D5; T9 |
| AC-U8 | Gson additive persistence | D2; T7–T8 |
| AC-U9 | `ResultScreen` unassessed section; reason + original text | D2/D4; T3, T6 |
| AC-U10 | `eligibilityClaimAllowed` cannot be true for new records with unassessed | D4; T5, T9 |
| AC-U11 | regression guard; no unrelated mutation | D6; T12 |
```

From `/root/checkpoints/VANTEDGE-R2-UNKNOWN-STATE-INTEGRITY-CED-01-20261030-153602/REPORT.md:130-140`:

```text
| AC-U1 | Implemented / unit-verified | `ProvisionalScoreTest`; measured-weight normalization |
| AC-U2 | Implemented / unit-verified | full measurement equals legacy score/vacancy |
| AC-U3 | Implemented / unit-verified | `unknownAxes` + `evaluationComplete=false`; incomplete UI hides score |
| AC-U4 | Implemented / unit-verified | adapter inventory + mapper assessment round trip |
| AC-U5 | Implemented / unit-verified | matched / confirmed gap / unassessed remain distinct |
| AC-U6 | Implemented / unit-verified | empty skills keep inventory unassessed |
| AC-U7 | Preserved / unit-verified | P2 untouched; coverage cannot authorize |
| AC-U8 | Implemented, **runtime NOT VERIFIED** | JSON legacy null + round trip tests; no device persistence run |
| AC-U9 | Implemented, **runtime NOT VERIFIED** | `ResultScreen` unassessed section tested at helper level; no UI run |
| AC-U10 | Implemented / unit-verified | unassessed forces `evaluationComplete=false`; authority gate unchanged |
| AC-U11 | Verified | preservation audit + partial staging; porcelain restored |
```

Neither table identifies an original issuing document, exact criterion wording, or version/commit provenance for the criteria themselves.
