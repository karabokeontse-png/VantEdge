# VANTEDGE-R2-MAPPER-COVERAGE-AUDIT-01

Status: **READ-ONLY AUDIT COMPLETE — MAPPER CHANGES DIRECTLY VERIFIED (PARTIAL BRANCH COVERAGE)**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Audit window: `2026-11-04T17:00:24Z` to `2026-11-04T17:21:00Z`
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01` (unchanged)
- HEAD: `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` (unchanged)
- HEAD tree: `fdf88050c7256eee8deef404a8ec0e9e3ae0aaf7`
- Draft PR: #5 — https://github.com/karabokeontse-png/VantEdge/pull/5
- PR state: `open`, draft `true`, merged `false`
- Base: `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159`
- R2 status: **OPEN**

---

## 0. Verdict

**MAPPER CHANGES DIRECTLY VERIFIED.**

Direct mapper tests exist and passed in both successful CI runs. `RequirementCoverageTest` and `CorrectiveUnknownStateTest` call `CompatibilityRecordMapper.map` directly and assert mapper-produced requirement assessments, reasons, gaps, and completeness for most of the changed branches introduced by `d68026d`.

**Complete mapper coverage is not demonstrated.** The following changed behavior is not proven by the executed CI tests:

- `assessQualification`'s changed `UNRECONCILED` else reason.
- The new `inventorySource` and `preferredReconstructable` diagnostics keys.
- The reordered `PREFERRED` + null-canonical skill path.
- Case-insensitive profile-evidence matching beyond exact-case inputs.
- Serialization round-trip of the new `UNRECONCILED` reason, because `CompatibilityRecordLegacyJsonTest` was not executed.

`ProvisionalScoreTest` exercises provisional scoring, not the mapper. `CompatibilityRecordLegacyJsonTest` was not executed. No test was found that indirectly verifies the changed mapper behavior through production code.

---

## 1. Contract snapshot and preflight

### 1.1 Repository identity and Git state

Preflight was performed before any report file was written.

| Item | Expected | Observed | Result |
|---|---|---|---|
| Repository | `/root/vantedge-canonical` | `/root/vantedge-canonical` | PASS |
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | PASS |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | PASS |
| Corrective commit | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | PASS |
| Staged paths | `0` | `0` | PASS |
| Worktree summary | preserved | `33 M, 1 D, 34 ??` | PASS |
| Porcelain SHA-256 | preserved | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` | PASS |

### 1.2 PR state

| Field | Value |
|---|---|
| Number | `#5` |
| URL | https://github.com/karabokeontse-png/VantEdge/pull/5 |
| Title | `R2 corrective: reconcile requirement evidence and provisional scoring (CI verification)` |
| State | `open` |
| Draft | `true` |
| Merged | `false` |
| Head | `r2/ci-draft-01` at `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` |
| Base | `main` at `b3c9d06c82a553a53bf9bca62cc521014ce4f159` |
| Commits | `5` |
| Changed paths | `41` |
| Diffstat | `3173` additions / `197` deletions |

No discrepancy was found. The audit proceeded read-only.

---

## 2. Exact mapper changes introduced by `d68026d`

`d68026d` changed eight paths. The mapper file changed by `+89/-21`:

- `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt`
- `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt`
- `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt`
- `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt`
- `app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt`
- `app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt`
- `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt`
- `app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt`

The mapper changes are classified below. Line numbers refer to the post-commit mapper file.

| ID | Location | Change | Behavioral effect |
|---|---|---|---|
| C1 | `CompatibilityRecordMapper.kt:194-202`, `:276-284` | `profileSkills = validatedProfile.skills` is passed into `buildRequirementAssessments`. | Gives assessment logic access to profile skill evidence. |
| C2 | `:290-298` | `distinctSource` now deduplicates on `originalText`, `category`, `matchedTaxonomySkill`, `importance`, and `classificationStatus`. | Inventory entries differing only by classification status remain distinct. |
| C3 | `:299-305` | `assessmentSource` filters qualification-category inventory to items present in `validatedJob.requiredQualifications`. | Profile qualifications not required by the job are excluded from assessments. |
| C4 | `:333-348` | `reconciledQualifications` appends required qualifications absent from the inventory, assessed via `assessQualification`. | Missing job-required qualifications can now produce assessments and block false completeness. |
| C5 | `:351-357`, `:369-379` | New `hasProfileEvidence` and `matchedByProfile` path for `PREFERRED` skills. | A preferred skill with matching profile evidence is `MATCHED`. |
| C6 | `:382-384` | Unmatched `PREFERRED` skills return `PREFERENCE_NOT_ASSESSED` before the canonical-null branch. | Preferred requirements remain non-blocking and are not mislabeled as taxonomy misses. |
| C7 | `:386-395` | Canonical-null skill branch remains, now after the preferred branch. | Non-preferred unmapped skills still return `TAXONOMY_MISS` or `UNSUPPORTED_CATEGORY`. |
| C8 | `:397-422` | Weak/missing branches remain; final else now returns `UNRECONCILED` when `profileSkills` is non-empty, otherwise `PROFILE_SKILL_EVIDENCE_ABSENT`. | Distinguishes reconciliation failure from absent profile evidence. |
| C9 | `:468-475` | `assessQualification` else reason changed from `PROFILE_QUALIFICATION_EVIDENCE_ABSENT` to `UNRECONCILED`. | New reason for qualification evidence that is neither matched nor missing while qualifications are assessed. |
| C10 | `:245-246` | New `PipelineTrace` diagnostics keys `inventorySource` and `preferredReconstructable`. | Informational diagnostics only; not part of the returned record. |
| C11 | `:222-224` | Comment about `preferredReconstructable` and blocking semantics. | No runtime behavior. |

`W5Types.kt` adds `UNRECONCILED` to `UnassessedReason`.

The mapper file SHA-256 is `bf34c26a16dcad1281553a20966171bc46a1f11f58491dbfa2cf6d9ab4b240c2`.

---

## 3. Verified call-graph evidence

### 3.1 Direct mapper tests

`RequirementCoverageTest` imports and constructs the mapper:

```kotlin
import com.vantedge.app.data.domain.CompatibilityRecordMapper
...
) = CompatibilityRecordMapper().map(
```

Its `map` helper is at `RequirementCoverageTest.kt:66-99`. Every test that calls `map(...)` enters `CompatibilityRecordMapper.map` and then `buildRequirementAssessments`.

`CorrectiveUnknownStateTest` also imports and constructs the mapper:

```kotlin
import com.vantedge.app.data.domain.CompatibilityRecordMapper
...
) = CompatibilityRecordMapper().map(
```

Its `map` helper is at `CorrectiveUnknownStateTest.kt:48-76`.

### 3.2 Non-mapper scoring tests

`ProvisionalScoreTest` has no mapper import. Its helper calls:

```kotlin
private fun evaluate(profile: ValidatedProfile, job: ValidatedJob) =
    engine.evaluate(profile, job, W5TraceContext("provisional", "", 0L))
```

`W5ScoreEngine.evaluate` calls `GapAnalyzer.analyze` and axis evaluators; it does not reference `CompatibilityRecordMapper`.

### 3.3 Additional relevant test class

`CompatibilityRecordLegacyJsonTest` imports `Gson`, `CompatibilityRecord`, `RequirementAssessmentState`, `ScoreCompleteness`, and `ScorePresentation`. It does not import or call `CompatibilityRecordMapper`. It tests serialization and presentation of the mapper's output data type, not the mapper itself.

The corrective commit added a new test to this class, `confirmed gap and preferred assessment round trip`, which serializes `PREFERENCE_NOT_ASSESSED` and `UNRECONCILED`. That test was **not executed** in either successful W5-filtered workflow.

### 3.4 Supplementary executed class that reaches the mapper

`JobTitleRoleRelevanceProductionTest` contains one test that constructs `DeterministicCompatibilityEngine` with `CompatibilityRecordMapper()` and calls `engine.analyze(...)`. `DeterministicCompatibilityEngine.analyze` calls `compatibilityRecordMapper.map(...)` at `DeterministicCompatibilityEngine.kt:96-109`.

That test asserts only `withTitle.record.score > withoutTitle.record.score`. It does not assert the changed requirement-assessment behavior. It is therefore not evidence of changed mapper behavior coverage.

### 3.5 Workflow filter

Both successful workflows run:

```text
./gradlew testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"
```

This includes `RequirementCoverageTest`, `CorrectiveUnknownStateTest`, and `ProvisionalScoreTest`, but excludes `com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest`.

---

## 4. JUnit execution evidence

### 4.1 Successful workflows

- Run 1: `41839277174` — W5 Deterministic Scoring Tests, head `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`.
- Run 2: `41839277536` — Full CI + W5 Validation, head `d68026d7c55b50a2f50ed165dbd8aad27c51a69f`.
- Failed general run `41839278056` was also on head `d68026d...` but produced no artifacts and is not used as test evidence.

### 4.2 Suite-level results

| Test class | Run 1 `41839277174` | Run 2 `41839277536` | Evidence |
|---|---|---|---|
| `RequirementCoverageTest` | 21 tests, 0 failures, 0 errors, 0 skipped | 21 tests, 0 failures, 0 errors, 0 skipped | XML `TEST-com.vantedge.app.w5.scoring.RequirementCoverageTest.xml` |
| `CorrectiveUnknownStateTest` | 10 tests, 0 failures, 0 errors, 0 skipped | 10 tests, 0 failures, 0 errors, 0 skipped | XML `TEST-com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest.xml` |
| `ProvisionalScoreTest` | 11 tests, 0 failures, 0 errors, 0 skipped | 11 tests, 0 failures, 0 errors, 0 skipped | XML `TEST-com.vantedge.app.w5.scoring.ProvisionalScoreTest.xml` |
| `CompatibilityRecordLegacyJsonTest` | No XML | No XML | Not executed in either run |
| `JobTitleRoleRelevanceProductionTest` (supplementary) | 8 tests, 0 failures, 0 errors, 0 skipped | 8 tests, 0 failures, 0 errors, 0 skipped | XML present |

JUnit XML proves which test methods executed and passed. It does not, by itself, prove branch coverage. Branch reach below is established by static source trace of each test's inputs, helper calls, and assertions.

---

## 5. Test-by-test execution-path analysis

### 5.1 `RequirementCoverageTest` — 21 executed tests

All tests in this class were executed in both runs. The helper `map` at lines 66-99 directly invokes `CompatibilityRecordMapper().map`.

| # | Test method | Mapper path | Changed behavior reached | Assertion relevant to mapper behavior | Runs 1/2 | Classification |
|---:|---|---|---|---|---|---|
| 1 | `unmapped requirement is unassessed and is not a confirmed gap` | `map` → `CompatibilityRecordMapper.map` → `buildRequirementAssessments` → `assessSkill` | C1, C2; C7 for `Data Protection` (`canonical=null`, `UNMAPPED`) | Asserts `TAXONOMY_MISS`, no gap, incomplete | PASS/PASS | **DIRECTLY COVERED** |
| 2 | `empty skill evidence keeps every requirement unassessed and inventory intact` | same | C1, C2; C8 with empty `profileSkills`; C7 for unmapped item | Asserts all `UNASSESSED`, `PROFILE_SKILL_EVIDENCE_ABSENT`, no gap, incomplete | PASS/PASS | **DIRECTLY COVERED** |
| 3 | `confirmed gaps and unassessed requirements remain distinct` | same | C1, C2; `assessSkill` matched and missing branches; C7 | Asserts `MATCHED`, `CONFIRMED_GAP`, `UNASSESSED`, one gap | PASS/PASS | **DIRECTLY COVERED** |
| 4 | `qualifications are assessed independently of skill evidence` | same | C1, C3; `assessQualification` matched branch | Asserts degree `MATCHED`, skill `UNASSESSED`, incomplete | PASS/PASS | **DIRECTLY COVERED** |
| 5 | `new records always persist a coverage inventory` | same | C1 only; empty inventory/source path | Asserts non-null empty `requirementAssessments` | PASS/PASS | **DIRECTLY COVERED** (empty path; no non-empty changed branch) |
| 6 | `partial measurement persists a provisional score without changing legacy score` | same | Mapper invoked; changed assessment output not asserted | Asserts `score`, `provisionalScore`, `provisionalVacancyScore` only | PASS/PASS | **NOT DEMONSTRATED** (changed assessment behavior not asserted) |
| 7 | `fully measured record keeps provisional equal to legacy values` | same | Mapper invoked; changed assessment output not asserted | Asserts score/provisional equality only | PASS/PASS | **NOT DEMONSTRATED** (changed assessment behavior not asserted) |
| 8 | `legacy records keep unknown coverage null` | No mapper call; constructs `CompatibilityRecord` directly | None | Asserts null coverage fields on a manually built record | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 9 | `preferred skill with matching profile evidence is matched` | `map` → mapper → `assessSkill` | C1, C2, C5 (`matchedByProfile` true) | Asserts preferred `MATCHED`, importance `PREFERRED`, no gaps | PASS/PASS | **DIRECTLY COVERED** |
| 10 | `preferred skill without matching profile evidence stays unassessed and is not a gap` | same | C1, C2, C6 | Asserts `UNASSESSED`, `PREFERENCE_NOT_ASSESSED`, no gaps | PASS/PASS | **DIRECTLY COVERED** |
| 11 | `reconciliation failure is distinct from absent profile evidence` | same | C1, C2, C8 both branches | Asserts `UNRECONCILED` and `PROFILE_SKILL_EVIDENCE_ABSENT` | PASS/PASS | **DIRECTLY COVERED** |
| 12 | `inventory entries differing in classification status remain distinct` | same | C1, C2 (`classificationStatus` in distinct key) | Asserts two `Kotlin` assessments | PASS/PASS | **DIRECTLY COVERED** |
| 13 | `every gap has exactly one confirmed gap assessment` | same | C1, C2; `assessSkill` matched and missing branches | Asserts one `CONFIRMED_GAP` and canonical `Kubernetes` | PASS/PASS | **DIRECTLY COVERED** |
| 14 | `every matched required skill has exactly one matched assessment` | same | C1, C2; `assessSkill` matched branch | Asserts one `MATCHED` and canonical `Kotlin` | PASS/PASS | **DIRECTLY COVERED** |
| 15 | `no eligible measured component yields a null provisional vacancy score` | same | Mapper invoked; changed assessment output not asserted | Asserts `provisionalVacancyScore == null` | PASS/PASS | **NOT DEMONSTRATED** (changed assessment behavior not asserted) |
| 16 | `experience-only partial measurement normalizes measured vacancy components` | same | Mapper invoked; changed assessment output not asserted | Asserts `provisionalVacancyScore` only | PASS/PASS | **NOT DEMONSTRATED** (changed assessment behavior not asserted) |
| 17 | `inventory order is preserved` | same | C1, C2, C3; C8 for `Kubernetes`; qualification unassessed branch | Asserts assessment order `Kubernetes`, `Kotlin`, `Bachelor of Science` | PASS/PASS | **DIRECTLY COVERED** |
| 18 | `fallback inventory keeps required requirements and does not invent preferred` | same | C1, C2; synthesized inventory; matched branch | Asserts only `Kotlin`, no preferred, complete | PASS/PASS | **DIRECTLY COVERED** |
| 19 | `job required qualification with matching profile evidence is matched` | same | C1, C2, C3, C4; `assessQualification` matched | Asserts degree `MATCHED`, complete | PASS/PASS | **DIRECTLY COVERED** |
| 20 | `profile qualification not required by the job is not unreconciled and does not block completeness` | same | C1, C2, C3 filter | Asserts degree absent, no `UNRECONCILED`, complete | PASS/PASS | **DIRECTLY COVERED** |
| 21 | `missing job required qualification is reconciled and blocks false completeness` | same | C1, C2, C3, C4; `assessQualification` unassessed | Asserts `UNASSESSED`, `PROFILE_QUALIFICATION_EVIDENCE_ABSENT`, incomplete | PASS/PASS | **DIRECTLY COVERED** |

### 5.2 `CorrectiveUnknownStateTest` — 10 executed tests

All tests in this class were executed in both runs. Only tests that call the local `map` helper at lines 48-76 reach the mapper.

| # | Test method | Mapper path | Changed behavior reached | Assertion relevant to mapper behavior | Runs 1/2 | Classification |
|---:|---|---|---|---|---|---|
| 1 | `unassessed skills are not classified as confirmed gaps` | `GapAnalyzer.analyze` only | None | Asserts gap lists empty | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 2 | `evidence-supported skill gaps are preserved` | `GapAnalyzer.analyze` only | None | Asserts missing contains `Kubernetes` | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 3 | `empty skill evidence keeps the evaluation incomplete` | `W5ScoreEngine.evaluate` only | None | Asserts `SkillMatch` unmeasured and incomplete | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 4 | `unassessed qualification evidence is not a mandatory gap and is incomplete` | `map` → mapper → `buildRequirementAssessments` | C1, C2, C3; qualification unassessed branch | Asserts incomplete and no `DEGREE:` gap | PASS/PASS | **DIRECTLY COVERED** |
| 5 | `evidence-supported qualification gaps are preserved and complete` | same | C1, C2; `assessQualification` missing branch | Asserts `CONFIRMED_GAP`, null reason, complete, degree gap | PASS/PASS | **DIRECTLY COVERED** |
| 6 | `matching qualification evidence produces no gap and stays complete` | same | C1, C2; `assessQualification` matched branch | Asserts complete and no `DEGREE:` gap | PASS/PASS | **DIRECTLY COVERED** |
| 7 | `empty job required skills keeps skill match unmeasured` | `W5ScoreEngine.evaluate` only | None | Asserts `SkillMatch` unmeasured and incomplete | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 8 | `empty profile roles and title keeps role relevance unmeasured` | `W5ScoreEngine.evaluate` only | None | Asserts `RoleRelevance` unmeasured and incomplete | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 9 | `blank job title keeps role relevance unmeasured` | `W5ScoreEngine.evaluate` only | None | Asserts `RoleRelevance` unmeasured and incomplete | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 10 | `empty skills preserve mapped requirements as unassessed inventory` | `map` → mapper → `buildRequirementAssessments` | C1, C2, C8 with empty `profileSkills` | Asserts `UNASSESSED`, `PROFILE_SKILL_EVIDENCE_ABSENT`, incomplete | PASS/PASS | **DIRECTLY COVERED** |

### 5.3 `ProvisionalScoreTest` — 11 executed tests

All tests in this class were executed in both runs. None call the mapper. They call `W5ScoreEngine.evaluate` and assert scoring/provisional behavior.

| # | Test method | Path | Mapper reached? | Runs 1/2 | Classification |
|---:|---|---|---|---|---|
| 1 | `provisional score normalizes over measured weights only` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 2 | `legacy total score keeps unmeasured axes as zero` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 3 | `fully measured input keeps provisional equal to legacy score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 4 | `SkillMatch alone supports a provisional score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 5 | `ExperienceAlignment alone supports a provisional score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 6 | `RoleRelevance alone supports a provisional score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 7 | `KeywordCoverage alone supports a provisional score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 8 | `SeniorityFit alone supports a provisional score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 9 | `structural and constraint axes alone yield a null provisional score` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 10 | `all configured axes have positive weights summing to one` | `AxisWeights` only | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |
| 11 | `provisional score stays within the unit interval` | `W5ScoreEngine.evaluate` | No | PASS/PASS | **RELATED — MAPPER NOT REACHED** |

### 5.4 `CompatibilityRecordLegacyJsonTest` — 11 tests, not executed

No JUnit XML for this class exists in either successful W5-filtered run. It also does not import or call the mapper.

| # | Test method | Mapper relationship | Runs 1/2 | Classification |
|---:|---|---|---|---|
| 1 | `missing evaluationComplete deserializes to unknown` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 2 | `explicit null evaluationComplete remains unknown` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 3 | `existing record fields remain preserved` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 4 | `explicit true remains authoritative when the record permits it` | Gson/`ScoreCompleteness`; no mapper | No XML | **NOT EXECUTED** |
| 5 | `gaps prevent a positive eligibility claim` | Gson/`ScorePresentation`; no mapper | No XML | **NOT EXECUTED** |
| 6 | `serialization round trip preserves unknown state` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 7 | `missing coverage fields deserialize to unknown` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 8 | `recorded coverage and provisional values round trip` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 9 | `unassessed reason survives serialization` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |
| 10 | `confirmed gap and preferred assessment round trip` | Gson/`CompatibilityRecord`; added by `d68026d`; no mapper | No XML | **NOT EXECUTED** |
| 11 | `recorded empty inventory stays distinct from unknown coverage` | Gson/`CompatibilityRecord`; no mapper | No XML | **NOT EXECUTED** |

### 5.5 Supplementary executed mapper-reaching test

`JobTitleRoleRelevanceProductionTest` was executed 8/8 in both runs. Only one test reaches the mapper:

| Test method | Path | Changed behavior reached | Assertion | Runs 1/2 | Classification |
|---|---|---|---|---|---|
| `production engine forwards job title so role relevance raises the score` | `DeterministicCompatibilityEngine.analyze` → `CompatibilityRecordMapper.map` | Mapper executes, but the test asserts `record.score` only | `withTitle.record.score > withoutTitle.record.score` | PASS/PASS | **NOT DEMONSTRATED** (changed assessment behavior not asserted) |

The other seven `JobTitleRoleRelevanceProductionTest` tests exercise the adapter or scoring engine and do not reach the mapper.

---

## 6. Coverage matrix

### 6.1 Required test classes

| Classification | Count | Tests |
|---|---:|---|
| **DIRECTLY COVERED** | 20 | 16 in `RequirementCoverageTest`; 4 in `CorrectiveUnknownStateTest` |
| **INDIRECTLY COVERED** | 0 | None |
| **RELATED — MAPPER NOT REACHED** | 18 | 1 in `RequirementCoverageTest`; 6 in `CorrectiveUnknownStateTest`; 11 in `ProvisionalScoreTest` |
| **NOT EXECUTED** | 11 | 11 in `CompatibilityRecordLegacyJsonTest` |
| **NOT DEMONSTRATED** | 4 | 4 score/provisional-only tests in `RequirementCoverageTest` |
| **Total required** | **53** | 21 + 10 + 11 + 11 |

### 6.2 Changed-branch coverage

| Change | Covered by executed tests? | Evidence / limitation |
|---|---|---|
| C1 `profileSkills` passed into assessments | **Yes** | All direct mapper tests execute the call; preferred and UNRECONCILED tests depend on it. |
| C2 classification-status distinctness | **Yes** | `inventory entries differing in classification status remain distinct` asserts two assessments. |
| C3 non-required qualification filter | **Yes** | `profile qualification not required by the job...` asserts degree absent and no `UNRECONCILED`. |
| C4 required-qualification reconciliation | **Yes** | `job required qualification with matching profile evidence...` and `missing job required qualification...` cover appended required qualifications. |
| C5 preferred skill matched by profile evidence | **Yes** | `preferred skill with matching profile evidence is matched` asserts `MATCHED`. |
| C6 unmatched preferred skill stays non-blocking | **Yes (canonical non-null only)** | `preferred skill without matching profile evidence...` asserts `PREFERENCE_NOT_ASSESSED`. No test uses preferred + canonical null. |
| C7 canonical-null skill branch | **Yes (non-preferred only)** | `unmapped requirement...` asserts `TAXONOMY_MISS`; `empty skill evidence...` also exercises it. |
| C8 `UNRECONCILED` vs absent evidence | **Yes, both branches** | `reconciliation failure is distinct from absent profile evidence` asserts both reasons. |
| C9 qualification else reason `UNRECONCILED` | **No** | No executed test constructs a required qualification that is neither matched nor missing while `qualificationsAssessed=true`. |
| C10 diagnostics keys | **No** | No executed test captures or asserts `PipelineTrace` `inventorySource` or `preferredReconstructable`. |
| C11 comment only | N/A | No runtime behavior. |

### 6.3 What the direct tests actually establish

The direct tests establish that:

- The mapper is invoked through its public `map` entry point.
- Requirement assessments are produced and asserted.
- Preferred profile evidence can produce `MATCHED` (C5).
- Unmatched preferred skills are `UNASSESSED` with `PREFERENCE_NOT_ASSESSED` (C6).
- Inventory entries differing by classification status remain distinct (C2).
- Non-required profile qualifications are filtered out (C3).
- Missing job-required qualifications are reconciled and can block completeness (C4).
- `UNRECONCILED` is distinguished from absent profile skill evidence (C8).

They do not establish complete mapper coverage.

---

## 7. Mapper coverage versus downstream scoring coverage

`ProvisionalScoreTest` is not mapper coverage. It verifies `W5ScoreEngine` provisional-score aggregation over measured axes. The mapper consumes `ScoreResult.provisionalTotalScore` in unchanged mapping lines, but these tests never pass their result to `CompatibilityRecordMapper.map` and never assert mapper output.

The four `RequirementCoverageTest` tests that assert only score or provisional-vacancy values also invoke the mapper, but their assertions target unchanged score/provisional fields rather than the changed requirement-assessment logic. They are therefore classified **NOT DEMONSTRATED** for changed mapper behavior.

`CompatibilityRecordLegacyJsonTest` is downstream data-model/serialization coverage. Even if it had run, it would not invoke the mapper; it would only verify that serialized mapper output survives round-trip. It did not run in either successful CI workflow.

---

## 8. Unresolved paths and unavailable evidence

1. **No runtime coverage instrumentation.** The classifications use static source tracing plus JUnit execution evidence. No JaCoCo/coverage artifact was available.
2. **No `CompatibilityRecordLegacyJsonTest` XML.** The W5 test filter excludes its package. The new `UNRECONCILED` round-trip test was not executed.
3. **No `PipelineTrace` assertions.** C10 diagnostics keys are not verified by any executed test.
4. **No test for C9.** The qualification else reason `UNRECONCILED` is not demonstrated.
5. **No test for preferred + canonical null.** C6 is verified only when the preferred skill has a non-null canonical value.
6. **No case-variation test for C5.** `hasProfileEvidence` is case-insensitive, but the test uses exact-case `"Docker"` on both sides.
7. **Other production call paths exist but were not executed by W5 CI.** `OptimizationOrchestrator` and `DeterministicCompatibilityEngine` can reach the mapper, but the W5-filtered runs only executed `JobTitleRoleRelevanceProductionTest`'s engine path; its assertion does not cover changed assessment behavior. `P2VerificationHarnessTest` and `GeneratorViewModelReviewedRequirementsChainTest` were not in the W5 XML results.
8. **Failed general CI run has no artifacts.** Run `41839278056` cannot supply test-class evidence.

---

## 9. Read-only compliance

| Prohibition | Evidence | Result |
|---|---|---|
| Modify source or test files | No source/test file written; hashes unchanged | PASS |
| Create/modify/delete repository files | Only this report and `REPORT.sha256` written | PASS |
| Run Gradle, builds, unit tests, instrumentation | No Gradle/test command run | PASS |
| Run CI or trigger workflows | No workflow dispatch/rerun; run list unchanged | PASS |
| Commit or push | No Git mutation command run | PASS |
| Modify PR #5 | No PR mutation request; PR remains open/draft/unmerged | PASS |
| Change branches, HEAD, index, worktree | Branch/HEAD/staged/worktree/porcelain unchanged | PASS |
| Reset, clean, stash, revert, discard | No such command run | PASS |

### 9.1 Post-audit Git state

Post-audit timestamp: `2026-11-04T17:21:00Z`

| Item | Preflight | Post-audit | Result |
|---|---|---|---|
| Branch | `r2/ci-draft-01` | `r2/ci-draft-01` | PASS |
| HEAD | `d68026d7c55b50a2f50ed165dbd8aad27c51a69f` | same | PASS |
| Staged paths | `0` | `0` | PASS |
| Worktree summary | `33 M, 1 D, 34 ??` | `33 M, 1 D, 34 ??` | PASS |
| Porcelain SHA-256 | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` | same | PASS |

The only new untracked content is this audit directory under `evidence/`; the top-level porcelain state and tracked worktree are unchanged.

---

## 10. Report integrity

- The authoritative SHA-256 of this report is recorded in the adjacent `REPORT.sha256` file.
- The same SHA-256 is returned in the execution response.
- A literal self-referential hash cannot be embedded inside the same file without changing that hash.

---

## 11. Completion boundary

- Verdict: **MAPPER CHANGES DIRECTLY VERIFIED** with partial branch coverage.
- Complete mapper coverage is **NOT DEMONSTRATED**.
- This audit does not close R2, R6, B1-E, or S1.
- This audit does not authorize source/test changes, additional CI, workflow changes, PR merge, or PR closure.
- PR #5 remains open, draft, and unmerged.
