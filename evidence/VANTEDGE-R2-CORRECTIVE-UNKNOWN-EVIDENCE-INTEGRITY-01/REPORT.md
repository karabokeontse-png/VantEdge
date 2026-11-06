# VANTEDGE-R2-CORRECTIVE-UNKNOWN-EVIDENCE-INTEGRITY-01 — EVIDENCE REPORT

**Status:** COMPLETE — BOUNDED CORRECTIVE IMPLEMENTED  
**Directive:** `VANTEDGE-R2-CORRECTIVE-UNKNOWN-EVIDENCE-INTEGRITY-01`  
**Authority:** ChatGPT — Lead Architect  
**Executor:** OpenCode  
**R2 status:** OPEN  
**Repository:** `/root/vantedge-canonical`  
**Branch:** `verification/ced-p0-authority`  
**Tests executed:** focused unit tests only  
**Staged/commit:** none

---

## 1. Repository identity and preflight

| Item | Value |
|---|---|
| Expected starting HEAD | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` |
| Actual starting HEAD | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` |
| Parent | `3f23859c9d38828b1da673429c11d885b0b0219c` |
| Tree | `b6aa1d9cafb13154344274da187303076f338901` |
| Branch | `verification/ced-p0-authority` |
| HEAD changed by this directive | No |
| Staged paths at start | none |
| Worktree at start | 32 modified, 1 deleted, 33 untracked |
| Porcelain SHA-256 at start | `13bf4cc8b67c41770673a65b0e435d4bcf661b57842c9371289ad8248573287e` |

The starting HEAD matches the expected HEAD exactly. No stop condition was triggered.

### Preflight hashes of files proposed for modification

| File | SHA-256 before |
|---|---|
| `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt` | `ed880cb97acce53ffdf41f6f11eb963a156c10c870a47bb29596e18722d28efb` |
| `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt` | `0ea43a8e89a9ad2407b6f863af444c698980086a0f49276c4fa2199560bb3baa` |
| `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt` | `34e1adedb5885a2f289d9a1c014144c1e844b885d1be73b5902626442697fa31` |
| `app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt` | `ee1cfdf57329be7d97726df3db536602ef2672a4f6deb65c658a526a13851a41` |
| `app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt` | `8acb794e7032eeb7d80d2dc4680067bdd962f2553037730cfe77a720e85eb23b` |
| `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt` | `01c0fc6b596a852fb679af9ade1cfba9b6cb58c1d79c9b7331e6436ab3384fe8` |
| `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt` | `a071d5f48e1bd8879482cab5e0e4f529388b854ac1ed81f155b3fb8424df9e8a` |
| `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt` | `078e34ed73613d7091dff02e7fab66ab9855062197f7b7a264247c51755da310` |
| `app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt` | `363f088db6ae185fb62eb2c9d65e061f728191dc30403cda89ca3aca18404964` |
| `app/src/test/java/com/vantedge/app/ui/screens/ScorePresentationTest.kt` | `4ca3bc2c8ad874002a93cc460217494ae8f143c3d4f268d7639bc5659d1b6f4d` |
| `app/src/test/java/com/vantedge/app/ui/screens/UnassessedPresentationTest.kt` | `a479529db4813a1f65d56bf6a6d8b688d10b12e9931e059bddf60c06e5dbd0ce` |

`P2VerificationHarnessTest.kt` was modified under C7's authorization to add the P2 independence regression to the existing P2 harness. The other three authorized test files (`CorrectiveUnknownStateTest.kt`, `ScorePresentationTest.kt`, `UnassessedPresentationTest.kt`) were not modified; their hashes are unchanged.

---

## 2. Worktree preservation and exact changed paths

### 2.1 Before/after fingerprints

| Item | Before | After |
|---|---|---|
| Modified | 32 | 40 |
| Deleted | 1 | 1 |
| Untracked | 33 | 33 |
| Staged | 0 | 0 |
| Porcelain SHA-256 | `13bf4cc8b67c41770673a65b0e435d4bcf661b57842c9371289ad8248573287e` | `343e73a969ff93556909c00d906c2e8bbf693697beabc92e9fafb81daf0893e0` |

Set comparison of porcelain entries:

- Added to the modified set: **8 paths** (the authorized edits below).
- Removed from the pre-existing set: **0**.
- Pre-existing path status changes: **0**.
- All 32 pre-existing modified paths, the 1 pre-existing deletion, and all 33 pre-existing untracked entries remain present with the same status code.

### 2.2 Authorized changed paths

| File | SHA-256 after | Change |
|---|---|---|
| `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt` | `19d880dbb7bb64bb7d741e2ae214e12ed5cd38922a2b02edcfe616e6721f6964` | `UNRECONCILED` added |
| `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt` | `6d2b4622c5ec521c81ec50d22629f750eea8bfc33f34d0b7159de88c704e06c7` | C1/C2/C3/C6 corrective logic |
| `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt` | `3eee7c7c18243600cb1c6b407a39a5f71cc004d0be7646cb08867a424752dfa2` | C1/C2/C3/C5 tests |
| `app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt` | `87dca7ba65d289e2f51cdaa01835ed0e9b5a558feb5d430589aff4e423fa0e64` | C4 axis/weight tests |
| `app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt` | `d4a5e03a3ceb4cd66b588f86b904a3b598fd8dd73b8c13a4ace7a3c0f7c9cb20` | exact complete-score equality |
| `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt` | `6707548bfbd8207b39c147729cd8df8d5d8f746a8b95e7c2a51e70ccbeb2d3f5` | C6 aggregation strengthening |
| `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt` | `c8273440c10bbec6ffacc8cddc6eac51d48e7d917d24c230f4ff9e42abe81087` | C6 Gson strengthening |
| `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt` | `9a695154888899551dfa17f42ace602f305e24f3e0f861ca0eaf6d44c1a7128a` | C7 P2 independence |

Authorized diff totals: **8 files changed, 408 insertions, 28 deletions**.

No production file outside the authorized set was modified. No schema, Room migration, navigation, networking, provider configuration, Gradle file, `ResultScreen.kt`, `ScorePresentation.kt`, `CycleScoreAggregation.kt`, `JobRequirementAdapter.kt`, `GapAnalyzer.kt`, `W5ScoreEngine.kt`, `W5Constants.kt`, `CompatibilityRecord.kt`, or `RequirementEvidence.kt` was modified.

---

## 3. Implemented corrections

### C1 — Preferred-skill semantics

`CompatibilityRecordMapper.kt:333-398` now determines matching evidence before applying the preferred no-match rule:

- `matchedByGap` preserves the existing GapAnalyzer matched-set semantics for required skills.
- `matchedByProfile` is true for a `PREFERRED` item when a profile skill exactly equals the canonical or original text (case-insensitive), using `hasProfileEvidence` at `:325-331`.
- Matching preferred evidence returns `MATCHED`; no matching evidence returns `UNASSESSED(PREFERENCE_NOT_ASSESSED)`.
- The preferred branch occurs before weak/missing classification, so a preferred item can never become `CONFIRMED_GAP`.
- No new similarity threshold was introduced. Required-skill matching remains the existing GapAnalyzer path. Preferred matching is exact/case-insensitive because preferred items are not part of `job.requiredSkills` and therefore are not present in `gapAnalysis.matched`.

Tests: `preferred skill with matching profile evidence is matched`, `preferred skill without matching profile evidence stays unassessed and is not a gap`.

### C2 — Explicit reconciliation failure

`W5Types.kt:104` adds `UNRECONCILED`.

`CompatibilityRecordMapper.kt` uses it only when the mapper cannot reconcile an inventory requirement with the expected evidence outcome:

- Skill reconciliation failure (`:389-396`): `UNRECONCILED` when profile skill evidence exists but the inventory item is not matched, weak, or missing. Genuine profile absence remains `PROFILE_SKILL_EVIDENCE_ABSENT`.
- Qualification reconciliation failure (`:442-449`): `UNRECONCILED` when qualifications were assessed but the inventory qualification is neither matched nor missing. Genuine qualification evidence absence remains `PROFILE_QUALIFICATION_EVIDENCE_ABSENT` in the earlier branch.

Tests: `reconciliation failure is distinct from absent profile evidence`, `unreconciled qualification inventory is not mislabeled as absent profile evidence`.

### C3 — Lossless inventory and reconciliation

`CompatibilityRecordMapper.kt:287-295` deduplicates the **source inventory** before mapping, using the full available identity:

```
originalText, category, matchedTaxonomySkill, importance, classificationStatus
```

This:

- prevents entries differing only in `classificationStatus` from collapsing;
- preserves first-occurrence extraction order (`distinctBy` is order-preserving);
- removes the previous reduced post-map key `(text, category, canonical, importance)`.

Fallback synthesis still synthesizes required skills and qualifications only. `ValidatedJob` has no preferred-skill field, so preferred requirements cannot be reconstructed when the source inventory is empty. The mapper now emits trace details `inventorySource` (`DIAGNOSTICS` or `SYNTHESIZED`) and `preferredReconstructable` (`true` only when source inventory is present) at `:242-243`. This makes the limitation explicit and avoids a silent claim that preferred requirements were reconstructed.

Tests: `inventory entries differing in classification status remain distinct`, `inventory order is preserved`, `every gap has exactly one confirmed gap assessment`, `every matched required skill has exactly one matched assessment`, `fallback inventory keeps required requirements and does not invent preferred`.

### C4 — Provisional total score matrix

The formula and `AxisWeights.get(axisName)` contract are unchanged. `ProvisionalScoreTest.kt` now has isolated tests for each evidence axis:

- `SkillMatch`
- `ExperienceAlignment`
- `RoleRelevance`
- `KeywordCoverage`
- `SeniorityFit`

Each test asserts that only the target evidence axis is measured among evidence axes and that the provisional score equals the measured-weight normalization. Additional tests assert:

- structural/constraint axes alone yield `null`;
- all configured axes have positive weights and sum to `1.0` within `1e-9`;
- fully measured input yields a provisional total exactly equal to legacy `totalScore` (`0.0` delta) in both `ProvisionalScoreTest` and `W5ScoreEngineTest`.

The zero-total-measured-weight branch is not reachable through supported inputs because all seven configured axes have positive weights. No unsafe production hook was added to reach it.

### C5 — Provisional vacancy score

The existing `0.5/0.3/0.2` component weights and the eligibility gate (skill or experience measured) are unchanged. New tests cover:

- no eligible measured component → `null` provisional vacancy;
- experience-only partial measurement normalizes only measured components;
- existing skill+constraint partial normalization remains covered;
- fully measured input keeps provisional vacancy exactly equal to legacy `vacancyScore`.

### C6 — Persistence and aggregation protection

Existing Gson and aggregation tests are retained. New coverage:

- `confirmed gap and preferred assessment round trip` adds `CONFIRMED_GAP`, `PREFERRED`, `PREFERENCE_NOT_ASSESSED`, and `UNRECONCILED` to the round-trip matrix;
- `provisional values do not affect previous score or improvement` confirms provisional values do not affect `previousScore` or `improvedApplicationsCount`.

No production persistence or aggregation code was changed.

### C7 — P2 independence

`P2VerificationHarnessTest.kt:741-784` adds:

- `T13 requirement coverage metadata does not affect P2 validation` — two otherwise identical contract JSON inputs, one enriched with `provisionalScore`, `provisionalVacancyScore`, `evaluationComplete`, and `requirementAssessments`; decision class, failure codes, confidence penalty, rule results, and validated payload are identical.
- `T14 P2 contract entrypoint has no requirement coverage parameter` — reflection confirms `validateContractResult` accepts no `RequirementAssessment` or `CompatibilityRecord` parameter.

`P2ValidationEngine.kt` was not modified and no requirement-coverage concept was introduced into P2.

---

## 4. Acceptance matrix

| AC | Criterion | Status | Evidence |
|---|---|---|---|
| AC1 | Preferred skills with matching evidence are `MATCHED` | **PASS** | `CompatibilityRecordMapper.kt:343-354`; test `preferred skill with matching profile evidence is matched` |
| AC2 | Preferred skills without matching evidence are `UNASSESSED(PREFERENCE_NOT_ASSESSED)` and never gaps | **PASS** | `CompatibilityRecordMapper.kt:356-358`; test `preferred skill without matching profile evidence stays unassessed and is not a gap` |
| AC3 | `UNRECONCILED` distinct from profile-evidence absence and used for actual reconciliation failure | **PASS** | `W5Types.kt:104`; mapper `:389-396` and `:442-449`; tests `reconciliation failure is distinct from absent profile evidence`, `unreconciled qualification inventory is not mislabeled as absent profile evidence` |
| AC4 | Deduplication preserves classification-status distinctions and inventory order | **PASS** | Source-key dedup at `:287-295`; tests `inventory entries differing in classification status remain distinct`, `inventory order is preserved` |
| AC5 | Reconciliation invariants tested for confirmed gaps and matched required skills | **PASS** | Tests `every gap has exactly one confirmed gap assessment`, `every matched required skill has exactly one matched assessment` |
| AC6 | Fallback reconstruction does not silently claim completeness when preferred cannot be recovered | **PASS (documented limitation)** | `ValidatedJob` has no preferred field; fallback synthesizes required requirements only; trace `inventorySource`/`preferredReconstructable` at `:242-243`; test `fallback inventory keeps required requirements and does not invent preferred`; limitation documented in §6 |
| AC7 | All five evidence axes have isolated provisional-score tests | **PASS** | `ProvisionalScoreTest.kt:115-149` (five tests) |
| AC8 | Axis-weight completeness and sum are tested | **PASS** | `ProvisionalScoreTest.kt:164-177`; sum `1.0` within `1e-9`, all positive |
| AC9 | Complete provisional total equals legacy total exactly | **PASS** | `ProvisionalScoreTest.kt:98` and `W5ScoreEngineTest.kt:977` use `0.0` delta; tests pass |
| AC10 | Provisional vacancy normalization and complete-value equality tested | **PASS** | `RequirementCoverageTest.kt:353-385`; existing partial/complete tests pass |
| AC11 | Gson compatibility and authoritative aggregation isolation remain covered | **PASS** | `CompatibilityRecordLegacyJsonTest` 11 tests; `CycleScoreAggregationTest` 15 tests; new round-trip and previous/improvement tests pass |
| AC12 | P2 coverage independence tested without production P2 changes | **PASS** | `P2VerificationHarnessTest.kt:741-784`; `P2ValidationEngine.kt` unchanged |
| AC13 | Existing authoritative-score and eligibility gates remain unchanged | **PASS** | `ScorePresentation.kt` and `ResultScreen.kt` untouched; `ScorePresentationTest` 13 tests and `UnassessedPresentationTest` 5 tests pass; `CorrectiveUnknownStateTest` 10 tests pass |
| AC14 | No unauthorized production files, schema changes, or unrelated dirty-tree changes included | **PASS** | Only the 8 authorized paths changed; pre-existing porcelain set preserved; no Room/schema/Gradle changes; no staging |

---

## 5. Test execution evidence

### 5.1 Commands and results

**Attempt 1 — direct test run without temporary repository configuration (FAILED, environmental):**

```
/root/tools/gradle-9.6.1/bin/gradle :app:testDebugUnitTest \
  --offline -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2 \
  --no-daemon --console=plain \
  --tests "com.vantedge.app.w5.scoring.RequirementCoverageTest" \
  --tests "com.vantedge.app.w5.scoring.ProvisionalScoreTest" \
  --tests "com.vantedge.app.w5.scoring.W5ScoreEngineTest" \
  --tests "com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest" \
  --tests "com.vantedge.app.data.viewmodel.CycleScoreAggregationTest" \
  --tests "com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest" \
  --tests "com.vantedge.app.ui.screens.ScorePresentationTest" \
  --tests "com.vantedge.app.ui.screens.UnassessedPresentationTest" \
  --tests "com.vantedge.pipeline.validation.P2VerificationHarnessTest"
```

Exit code: `1`. Cause: post-CED-01 worktree drift left `settings.gradle` without `pluginManagement`/`dependencyResolutionManagement`, so Gradle could not resolve `com.android.application:9.3.1` offline. No source or test result was produced by this attempt.

**Attempt 2 — compile-only with temporary init script (PASS):**

```
/root/tools/gradle-9.6.1/bin/gradle -I /tmp/r2cuei/init.gradle \
  :app:compileDebugUnitTestKotlin \
  --offline -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2 \
  --no-daemon --console=plain --max-workers=2
```

Exit code: `0`; `BUILD SUCCESSFUL in 5m 44s`.

**Attempt 3 — final focused unit-test run with temporary init script (PASS):**

```
/root/tools/gradle-9.6.1/bin/gradle -I /tmp/r2cuei/init.gradle \
  :app:testDebugUnitTest \
  --offline -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2 \
  --no-daemon --console=plain --max-workers=2 \
  --tests "com.vantedge.app.w5.scoring.RequirementCoverageTest" \
  --tests "com.vantedge.app.w5.scoring.ProvisionalScoreTest" \
  --tests "com.vantedge.app.w5.scoring.W5ScoreEngineTest" \
  --tests "com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest" \
  --tests "com.vantedge.app.data.viewmodel.CycleScoreAggregationTest" \
  --tests "com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest" \
  --tests "com.vantedge.app.ui.screens.ScorePresentationTest" \
  --tests "com.vantedge.app.ui.screens.UnassessedPresentationTest" \
  --tests "com.vantedge.pipeline.validation.P2VerificationHarnessTest"
```

Exit code: `0`; `BUILD SUCCESSFUL in 9m 46s`.

The temporary init script is at `/tmp/r2cuei/init.gradle`, outside the repository. It only restores repository declarations for the build invocation:

```groovy
beforeSettings { settings ->
    settings.pluginManagement {
        repositories { google(); mavenCentral(); gradlePluginPortal() }
    }
    settings.dependencyResolutionManagement {
        repositories { google(); mavenCentral() }
    }
}
```

No repository Gradle file was modified. The pre-existing `settings.gradle` drift remains exactly as found.

### 5.2 Test counts

| Test class | Tests | Skipped | Failures | Errors |
|---|---:|---:|---:|---:|
| `com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest` | 11 | 0 | 0 | 0 |
| `com.vantedge.app.data.viewmodel.CycleScoreAggregationTest` | 15 | 0 | 0 | 0 |
| `com.vantedge.app.ui.screens.ScorePresentationTest` | 13 | 0 | 0 | 0 |
| `com.vantedge.app.ui.screens.UnassessedPresentationTest` | 5 | 0 | 0 | 0 |
| `com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest` | 10 | 0 | 0 | 0 |
| `com.vantedge.app.w5.scoring.ProvisionalScoreTest` | 11 | 0 | 0 | 0 |
| `com.vantedge.app.w5.scoring.RequirementCoverageTest` | 19 | 0 | 0 | 0 |
| `com.vantedge.app.w5.scoring.W5ScoreEngineTest` | 34 | 0 | 0 | 0 |
| `com.vantedge.pipeline.validation.P2VerificationHarnessTest` | 24 | 0 | 0 | 0 |
| **Total** | **142** | **0** | **0** | **0** |

### 5.3 JUnit XML paths

```
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.data.storage.CompatibilityRecordLegacyJsonTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.data.viewmodel.CycleScoreAggregationTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.ui.screens.ScorePresentationTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.ui.screens.UnassessedPresentationTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.ProvisionalScoreTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.RequirementCoverageTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.app.w5.scoring.W5ScoreEngineTest.xml
app/build/test-results/testDebugUnitTest/TEST-com.vantedge.pipeline.validation.P2VerificationHarnessTest.xml
```

All nine XML files were regenerated at `2026-11-04 00:45:12 UTC`.

---

## 6. Unresolved limitations

1. **Preferred matching is exact/case-insensitive.** Preferred items are not part of `job.requiredSkills`, so they are absent from `gapAnalysis.matched`. The corrective checks canonical or original text against profile skill strings without adding a new similarity threshold. A preferred requirement expressed only as a semantic synonym not present in the profile skill list remains `UNASSESSED(PREFERENCE_NOT_ASSESSED)`. This is conservative: it cannot produce a false `MATCHED` or a gap, but it may under-match synonyms.
2. **Fallback preferred reconstruction is impossible with current data.** `ValidatedJob` has no preferred-skill field. When `diagnostics.inventory` is empty, fallback synthesis retains required skills and qualifications only. The mapper emits `inventorySource=SYNTHESIZED` and `preferredReconstructable=false`; this report does not claim preferred completeness. AC6 is marked PASS with this documented limitation.
3. **Zero-total-measured-weight branch is unreachable.** All seven configured axes have positive weights, so the `measuredWeight <= 1e-9` branch cannot be exercised through supported inputs without an unsafe production hook. No such hook was added. Weight positivity and the sum are tested instead.
4. **No runtime verification.** No APK, install, device, emulator, Compose UI, Room persistence, or orchestrator-level P2 test was executed. AC-U8 and AC-U9 remain unverified, as the directive states.
5. **P2 test scope.** T13/T14 exercise the P2 contract entrypoint and its parameter surface. They do not exercise an orchestrator, database, or UI path.
6. **Build environment drift.** The post-CED-01 `settings.gradle` modification removes repository declarations. Tests required a temporary init script outside the repository. No repo build configuration was changed.

---

## 7. No unauthorized changes / non-mutation statement

- No source, test, asset, manifest, database, Room schema, navigation, networking, provider, or Gradle file was modified outside the authorized paths.
- No Room migration was added.
- No file was staged.
- No commit was created.
- No branch switch, reset, clean, stash, revert, or checkout was performed.
- The 32 pre-existing modified paths, the 1 pre-existing deletion, and all 33 pre-existing untracked entries remain present with unchanged porcelain status codes.
- The only new porcelain entries are the 8 authorized modified paths listed in §2.2.
- `git diff --check` on the 8 authorized paths returned clean (exit 0).
- The working tree is intentionally dirty; no clean-worktree claim is made.

**R2 remains OPEN.** This directive does not close R2, does not retroactively authorize prospective CED-03, and does not establish AC-U8/AC-U9 runtime verification.

---

## Appendix — New and strengthened tests

### RequirementCoverageTest (19 total)
- `preferred skill with matching profile evidence is matched`
- `preferred skill without matching profile evidence stays unassessed and is not a gap`
- `reconciliation failure is distinct from absent profile evidence`
- `inventory entries differing in classification status remain distinct`
- `every gap has exactly one confirmed gap assessment`
- `every matched required skill has exactly one matched assessment`
- `no eligible measured component yields a null provisional vacancy score`
- `experience-only partial measurement normalizes measured vacancy components`
- `inventory order is preserved`
- `fallback inventory keeps required requirements and does not invent preferred`
- `unreconciled qualification inventory is not mislabeled as absent profile evidence`

### ProvisionalScoreTest (11 total)
- `SkillMatch alone supports a provisional score`
- `ExperienceAlignment alone supports a provisional score`
- `RoleRelevance alone supports a provisional score`
- `KeywordCoverage alone supports a provisional score`
- `SeniorityFit alone supports a provisional score`
- `structural and constraint axes alone yield a null provisional score`
- `all configured axes have positive weights summing to one`

### Other strengthened classes
- `CompatibilityRecordLegacyJsonTest`: `confirmed gap and preferred assessment round trip`
- `CycleScoreAggregationTest`: `provisional values do not affect previous score or improvement`
- `P2VerificationHarnessTest`: `T13 requirement coverage metadata does not affect P2 validation`, `T14 P2 contract entrypoint has no requirement coverage parameter`
- `W5ScoreEngineTest`: complete provisional total equality tightened to `0.0` delta
