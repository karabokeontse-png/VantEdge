# VANTEDGE-R2-B1-E-TESTABILITY-AND-VERIFICATION-ROUTE-AUDIT-01

Read-only audit of B1-E reachability and the verification route for the blocked
42-test focused run.

- Repository: `/root/vantedge-canonical`
- Branch: `verification/ced-p0-authority`
- HEAD: `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`
- HEAD tree: `b6aa1d9cafb13154344274da187303076f338901`
- Parent: `3f23859c9d38828b1da673429c11d885b0b0219c`
- Audit date: 2026-11-04
- R2 status: **OPEN**
- Implementation authority: **none**

---

## 0. Executive summary

### B1-E reachability

**B1-E as written cannot be constructed through the current production or test
contracts.** The qualification `UnassessedReason.UNRECONCILED` branch at
`CompatibilityRecordMapper.kt:472` is unreachable.

The mapper partitions every job-required qualification into exactly one of two
complementary lists when qualification evidence is assessed:

1. `matchedQualifications` — required qualification has same-category profile
   evidence with Jaccard similarity `> 0.3`.
2. `missingQualifications` — the `filterNot` complement of the same predicate.

`assessQualification` therefore returns `MATCHED` for the first list,
`CONFIRMED_GAP` for the second, and the final `UNRECONCILED` branch has no
remaining input. If profile qualification evidence is absent, the method returns
`PROFILE_QUALIFICATION_EVIDENCE_ABSENT` before reaching the partition.

**Classification:** **CONFIRMED (source proof)** that a positive B1-E fixture
using `UNASSESSED` + `UNRECONCILED` for a job-required qualification is
**NOT CONFIRMED / unreachable** under the current contracts.

There is, however, a **reachable** state that is the closest real-world meaning of
"genuinely unreconciled": profile qualification evidence exists, but the required
qualification does not match it. That case is currently classified as
`CONFIRMED_GAP`, not `UNASSESSED`/`UNRECONCILED`, and
`CorrectiveUnknownStateTest.kt:122-128` currently asserts that
`evaluationComplete` is **true** for that case. If B1-E is intended to cover this
false-completeness risk, the contract must be changed before a B1-E test can be
written. This is a contract decision, not a test-authoring task.

### Verification route

**Detached/resumable local execution is NOT VIABLE in this sandbox.** Two short
non-Gradle probes were run:

- `setsid` background process: alive immediately, dead after 25 seconds; no
  completion or exit-code file; zero-byte log.
- `nohup` + `disown` background process: alive immediately, dead after 20
  seconds; no completion or exit-code file; zero-byte log.

`screen` and `tmux` are unavailable. Background processes are terminated when the
shell-tool command exits, so a Gradle run cannot be detached and resumed across
shell-tool calls.

The practical evidence-preserving route is the existing GitHub Actions path:
create an authorized temporary commit containing exactly the eight-path
corrective set, push it, and open a PR to `develop` or `main`. `full-ci.yml` and
`w5-tests.yml` then compile and run the W5 scoring package and upload XML/HTML
artifacts. This route tests the committed eight-path subset, not the entire dirty
worktree, and it runs the broader `com.vantedge.app.w5.scoring.*` suite, not the
exact 42-test selection. It requires commit/push/PR/network authorization and is
**BLOCKED** in this read-only audit. Remote execution is not verified.

A single foreground local command with a larger timeout is the only local
candidate for testing the exact dirty worktree, but its viability is **NOT
CONFIRMED**. The prior 900-second and 1,800-second attempts were killed at the
configured shell-tool timeout; the second reached `:app:compileDebugKotlin` but
produced no fresh JUnit XML.

### Current status

- No Gradle or test run was performed in this audit.
- No source, test, settings, Gradle, or workflow file was modified.
- No commit, push, PR, branch change, staging, reset, clean, stash, revert,
  checkout, or discard was performed.
- No dependency was installed and no network access was used.
- The 42-test focused verification remains **BLOCKED**.
- R2 remains **OPEN**.

---

## 1. Repository identity and non-mutation proof

### 1.1 Identity

Pre-audit identity captured at `2026-11-04T08:17:16Z`; the evidence file was
regenerated at `2026-11-04T08:24:30Z` without a status change.

| Item | Value |
|---|---|
| Branch | `verification/ced-p0-authority` |
| HEAD | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` |
| HEAD tree | `b6aa1d9cafb13154344274da187303076f338901` |
| HEAD parent | `3f23859c9d38828b1da673429c11d885b0b0219c` |
| Staged paths | 0 |
| Modified paths | 40 |
| Deleted paths | 1 |
| Untracked paths | 34 |
| Porcelain SHA-256 | `71570223b0346af77388bb5c71120be98845f737f06513441b541e8344211830` |

The porcelain hash differs from the earlier verification audit's
`343e73a9...` because `.kotlin/` now exists as an untracked build-cache directory
from the prior blocked Gradle run. It is preserved. The extra untracked entry is
not a source change.

### 1.2 Non-mutation statement

This audit added only files under:

```text
evidence/VANTEDGE-R2-B1-E-TESTABILITY-AND-VERIFICATION-ROUTE-AUDIT-01/
```

The repository worktree source, tests, `settings.gradle`, `gradle.properties`,
workflows, `.kotlin/`, and build outputs were not modified or deleted. The
delivery copy is outside the repository. No repository file was staged or
committed.

Post-audit identity at `2026-11-04T08:33:18Z` matched the pre-audit counts and
porcelain hash: 40 modified, 1 deleted, 34 untracked, 0 staged, SHA-256
`71570223b0346af77388bb5c71120be98845f737f06513441b541e8344211830`.
`.kotlin/` remains present, `app/build` remains 17 MB, and there are still zero
fresh test XML files.

The two detached-process probes wrote only to:

```text
/data/user/0/ai.opencode.app/cache/opencode/r2-detach-probe/
/data/user/0/ai.opencode.app/cache/opencode/r2-nohup-probe/
```

No Gradle daemon or test process was started.

---

## 2. Finding A — B1-E reachability

### 2.1 The B1-E contract

B1-E asks for a job-required qualification that is **genuinely unreconciled**,
not merely absent from profile evidence, with all of the following:

1. The required qualification is present in the job requirements and in the
   requirement inventory.
2. Profile qualification evidence is present, but the required qualification is
   not reconciled to it.
3. The assessment state is `UNASSESSED` and the reason is `UNRECONCILED`.
4. `evaluationComplete` is `false`.
5. The state is distinct from `PROFILE_QUALIFICATION_EVIDENCE_ABSENT`.
6. The legacy score fields remain unchanged.

The audit tests whether that state can be reached through the existing production
and test contracts without inventing a bypass fixture.

### 2.2 Source trace

#### 2.2.1 Qualification partition

`CompatibilityRecordMapper.kt:128-158`:

```kotlin
val qualificationRequirementsPresent = validatedJob.requiredQualifications.isNotEmpty()
val profileQualificationsAssessed = validatedProfile.qualifications.isNotEmpty()
val qualificationsAssessed = !qualificationRequirementsPresent || profileQualificationsAssessed

val matchedQualifications = if (qualificationsAssessed) {
    validatedJob.requiredQualifications.filter { jobReq ->
        validatedProfile.qualifications.any { profileQual ->
            profileQual.category == jobReq.category &&
            jaccard(
                tokenize(profileQual.originalText, emptySet()).toSet(),
                tokenize(jobReq.originalText, emptySet()).toSet()
            ) > 0.3
        }
    }
} else {
    emptyList()
}

val missingQualifications = if (qualificationsAssessed) {
    validatedJob.requiredQualifications.filterNot { jobReq ->
        validatedProfile.qualifications.any { profileQual ->
            profileQual.category == jobReq.category &&
            jaccard(
                tokenize(profileQual.originalText, emptySet()).toSet(),
                tokenize(jobReq.originalText, emptySet()).toSet()
            ) > 0.3
        }
    }
} else {
    emptyList()
}
```

The predicate is identical and `filter` / `filterNot` are complementary. Every
required qualification is therefore in exactly one list when
`qualificationsAssessed` is true. When it is false, both lists are empty.

#### 2.2.2 Assessment source

`CompatibilityRecordMapper.kt:299-305`:

```kotlin
val requiredQualificationKeys = validatedJob.requiredQualifications
    .map { it.category to it.originalText }
    .toSet()
val assessmentSource = distinctSource.filter { evidence ->
    evidence.category !in qualificationCategories ||
        (evidence.category to evidence.originalText) in requiredQualificationKeys
}
```

For qualification categories, the source is restricted to exact required
qualification `(category, originalText)` keys. A qualification inventory item
that is not a required qualification is not sent to `assessQualification`.

#### 2.2.3 Assessment dispatch and reconciled fallback

`CompatibilityRecordMapper.kt:310-348`:

```kotlin
val assessments = assessmentSource.map { evidence ->
    when {
        evidence.category == RequirementCategory.SKILL ->
            assessSkill(...)
        evidence.category in qualificationCategories ->
            assessQualification(
                evidence,
                matchedQualifications,
                missingQualifications,
                qualificationsAssessed
            )
        evidence.classificationStatus == ClassificationStatus.UNMAPPED ->
            unassessed(evidence, UnassessedReason.TAXONOMY_MISS)
        else ->
            unassessed(evidence, UnassessedReason.UNSUPPORTED_CATEGORY)
    }
}
...
val reconciledQualifications = validatedJob.requiredQualifications
    .filter { (it.category.name to it.originalText) !in assessedQualificationKeys }
    .map { qualification ->
        assessQualification(
            qualification,
            matchedQualifications,
            missingQualifications,
            qualificationsAssessed
        )
    }
return assessments + reconciledQualifications
```

`assessQualification` is called only for:

- qualification-category evidence already restricted to required keys, or
- a required qualification from `reconciledQualifications`.

In both cases the evidence is a member of
`validatedJob.requiredQualifications`.

#### 2.2.4 `assessQualification`

`CompatibilityRecordMapper.kt:426-477`:

```kotlin
private fun assessQualification(
    evidence: RequirementEvidence,
    matchedQualifications: List<RequirementEvidence>,
    missingQualifications: List<RequirementEvidence>,
    qualificationsAssessed: Boolean
): RequirementAssessment {
    val canonical = "${evidence.category}: ${evidence.originalText}"
    val importance = evidence.importance ?: "MANDATORY"

    if (!qualificationsAssessed) {
        return RequirementAssessment(
            text = evidence.originalText,
            state = RequirementAssessmentState.UNASSESSED,
            canonical = canonical,
            reason = UnassessedReason.PROFILE_QUALIFICATION_EVIDENCE_ABSENT.name,
            category = evidence.category.name,
            importance = importance
        )
    }

    val matched = matchedQualifications.any {
        it.category == evidence.category && it.originalText == evidence.originalText
    }
    val missing = missingQualifications.any {
        it.category == evidence.category && it.originalText == evidence.originalText
    }

    return when {
        matched -> RequirementAssessment(
            text = evidence.originalText,
            state = RequirementAssessmentState.MATCHED,
            canonical = canonical,
            category = evidence.category.name,
            importance = importance
        )
        missing -> RequirementAssessment(
            text = evidence.originalText,
            state = RequirementAssessmentState.CONFIRMED_GAP,
            canonical = canonical,
            category = evidence.category.name,
            importance = "MANDATORY"
        )
        else -> RequirementAssessment(
            text = evidence.originalText,
            state = RequirementAssessmentState.UNASSESSED,
            canonical = canonical,
            reason = UnassessedReason.UNRECONCILED.name,
            category = evidence.category.name,
            importance = importance
        )
    }
}
```

Given the partition, the `else` branch requires an evidence item that is a
required qualification but is neither in `matchedQualifications` nor in
`missingQualifications`. That cannot occur when `qualificationsAssessed` is true,
and the method returns early when it is false.

### 2.3 Proof of unreachability

1. If `qualificationsAssessed` is false, `assessQualification` returns
   `PROFILE_QUALIFICATION_EVIDENCE_ABSENT` immediately. The `UNRECONCILED` branch
   is not reached.
2. If `qualificationsAssessed` is true, `matchedQualifications` and
   `missingQualifications` are a partition of
   `validatedJob.requiredQualifications` under the same predicate.
3. Every `assessQualification` call is for an element of
   `validatedJob.requiredQualifications`:
   - direct qualification-category assessments are filtered by exact required
     keys;
   - `reconciledQualifications` is built from `requiredQualifications`.
4. Therefore every call has exactly one of `matched == true` or `missing == true`.
5. The final `else` branch, and therefore the qualification
   `UNRECONCILED` reason at `CompatibilityRecordMapper.kt:472`, is unreachable.

This is a source-level proof for all `ValidatedJob` / `ValidatedProfile` inputs,
not merely for the production adapter. It does not depend on the adapter's
supported-category filter, although that filter independently prevents arbitrary
qualification categories from being produced in normal operation.

### 2.4 What `UNRECONCILED` is actually used for

The reachable `UNRECONCILED` production use is in `assessSkill`, not
`assessQualification`:

`CompatibilityRecordMapper.kt:415-422`:

```kotlin
else -> unassessed(
    evidence,
    if (profileSkills.isNotEmpty()) {
        UnassessedReason.UNRECONCILED
    } else {
        UnassessedReason.PROFILE_SKILL_EVIDENCE_ABSENT
    }
)
```

The existing test at `RequirementCoverageTest.kt:282-304` uses a skill named
`Unmapped Inventory Item`, not a qualification. The P2 harness fixture at
`P2VerificationHarnessTest.kt:749-755` uses the skill text `Kotlin`. The legacy
JSON fixture at `CompatibilityRecordLegacyJsonTest.kt:172` uses `Legacy Skill`.
None of these is a job-required qualification.

`QualificationEvaluator` is not called from production code; its only callers are
in `QualificationEvaluatorTest.kt`. `W5ScoreEngine` has no qualification
references. The mapper's qualification assessment is therefore the only
production path relevant to B1-E, and its `UNRECONCILED` branch is dead.

### 2.5 The reachable "present but non-matching" case

The closest reachable state to "genuinely unreconciled" is:

- Job requires `DEGREE: Bachelor of Science`.
- Profile has `DEGREE: Master of Business`.
- Profile qualification evidence is present, so
  `qualificationsAssessed == true`.
- The required degree is not matched (`jaccard` below `0.3`), so it is in
  `missingQualifications`.
- `assessQualification` returns `CONFIRMED_GAP`, not
  `UNASSESSED`/`UNRECONCILED`.
- Because there is no blocking `UNASSESSED` item and the score axes are complete,
  `evaluationComplete` is `true`.

This behavior is already asserted by
`CorrectiveUnknownStateTest.kt:122-128`:

```kotlin
@Test
fun `evidence-supported qualification gaps are preserved and complete`() {
    val assessed = profile().copy(qualifications = listOf(degree("Master of Business")))
    val mapped = map(assessed, job(degree("Bachelor of Science")))

    assertEquals(true, mapped.record.evaluationComplete)
    assertTrue(mapped.record.gaps.any {
        it.skill == "DEGREE: Bachelor of Science" && it.importance == "MANDATORY"
    })
}
```

This is distinct from the absent-evidence case at
`CorrectiveUnknownStateTest.kt:114-119` and
`RequirementCoverageTest.kt:451-465`, which returns
`PROFILE_QUALIFICATION_EVIDENCE_ABSENT` and `evaluationComplete == false`.

The distinction is therefore:

| Profile qualification evidence | Required qualification match | State | Reason | `evaluationComplete` |
|---|---|---|---|---|
| Absent | N/A | `UNASSESSED` | `PROFILE_QUALIFICATION_EVIDENCE_ABSENT` | `false` |
| Present | Matching | `MATCHED` | none | `true` |
| Present | Non-matching | `CONFIRMED_GAP` | none | `true` (current test) |
| Present | Unreconciled | `UNASSESSED` | `UNRECONCILED` | unreachable |

If B1-E is intended to cover the third row as a false-completeness defect, the
current test is asserting the opposite of the desired behavior. If B1-E is
intended to cover the fourth row literally, the production contract must change.

### 2.6 Smallest test addition

#### 2.6.1 Characterization test that passes now

This is the smallest test that uses only existing helpers and production
contracts. It documents the reachable present-but-non-matching state:

```kotlin
@Test
fun `job required qualification with non-matching profile evidence is a confirmed gap and complete`() {
    val required = degree("Bachelor of Science")
    val mapped = map(
        profile(qualifications = listOf(degree("Master of Business"))),
        job(
            requiredSkills = listOf("Kotlin"),
            qualifications = listOf(required)
        ),
        listOf(skill("Kotlin"), required)
    )

    val assessment = mapped.record.requirementAssessments!!
        .first { it.text == "Bachelor of Science" }
    assertEquals(RequirementAssessmentState.CONFIRMED_GAP, assessment.state)
    assertNull(assessment.reason)
    assertTrue(mapped.record.gaps.any {
        it.skill == "DEGREE: Bachelor of Science" && it.importance == "MANDATORY"
    })
    assertTrue(mapped.record.evaluationComplete!!)
}
```

This test does **not** prove B1-E. It proves the current classification and the
current completeness behavior. It is expected to pass under the current source.

#### 2.6.2 B1-E expectation test that would fail now

The smallest test that would prove B1-E as written is the same fixture with these
assertions:

```kotlin
assertEquals(RequirementAssessmentState.UNASSESSED, assessment.state)
assertEquals("UNRECONCILED", assessment.reason)
assertFalse(mapped.record.evaluationComplete!!)
```

That test cannot pass under the current contracts. It would require a production
change that makes the non-matching-present case `UNASSESSED`/`UNRECONCILED` and
makes it block completeness, or a different production trigger for
`UNRECONCILED`.

#### 2.6.3 Legacy score assertion

To prove the legacy score formulas remain unchanged while qualification
assessment changes, use a control mapping with a matching qualification and
assert:

```kotlin
assertEquals(control.record.score, mapped.record.score)
assertEquals(control.record.vacancyScore, mapped.record.vacancyScore)
```

This is an additional assertion, not a substitute for the contract decision.
Qualification assessment is not part of the W5 axis score inputs, so the legacy
score fields are expected to remain equal; the audit did not execute this.

### 2.7 Contract decision required

B1-E cannot be closed by adding a test alone. The Lead Architect must choose one
of the following:

**Option A — Make the present-but-non-matching case unreconciled and blocking.**

- Change the mapper so a required qualification with profile qualification
  evidence present but no match is `UNASSESSED` with `UNRECONCILED` (or another
  defined reason).
- Ensure `blockingUnassessed` makes `evaluationComplete == false`.
- Update `CorrectiveUnknownStateTest.evidence-supported qualification gaps are
  preserved and complete`, which currently expects `true`.
- This is the option that matches B1-E literally.

**Option B — Keep `CONFIRMED_GAP` but make mandatory qualification gaps block
completeness.**

- Change the completeness gate to treat mandatory qualification gaps as
  blocking.
- This satisfies the false-completeness concern but does not "preserve the
  unreconciled reason"; the reason remains absent.
- This may conflict with the existing test name and assertions.

**Option C — Remove or rewrite B1-E.**

- Keep `UNRECONCILED` only for skills, where it is reachable.
- State that qualification unreconciled is not part of the current contract.
- This is the only option that requires no production behavior change.

**Recommendation:** do not add the positive B1-E test yet. First decide whether
the intended defect is the reachable `CONFIRMED_GAP` + `evaluationComplete=true`
case (Option A or B) or whether B1-E should be removed/rewritten (Option C). The
audit has no implementation authority and did not change production or tests.

### 2.8 Classification

| Question | Finding | Classification |
|---|---|---|
| Can a job-required qualification reach `UNASSESSED` + `UNRECONCILED` under current contracts? | No; the branch is unreachable | **NOT CONFIRMED** (positive claim) / **CONFIRMED** (unreachability proof) |
| Is the qualification `UNRECONCILED` branch at `CompatibilityRecordMapper.kt:472` reachable? | No | **CONFIRMED (source proof)** |
| Is `PROFILE_QUALIFICATION_EVIDENCE_ABSENT` distinct from the non-matching-present case? | Yes | **CONFIRMED** |
| Is a present-but-non-matching qualification reachable? | Yes; `CONFIRMED_GAP` + `evaluationComplete=true` | **CONFIRMED (existing test)** |
| Does B1-E's required behavior hold for that reachable case? | No; it is a confirmed gap and completeness is true | **NOT CONFIRMED** |
| Can B1-E be proven by a test addition alone? | No; contract change required | **NOT CONFIRMED** |
| Did this audit implement a fix? | No | **BLOCKED** by read-only scope |

---

## 3. Finding B — Verification route

### 3.1 Detached/resumable local execution is not viable

Two short probes were run outside the repository. They were not Gradle or test
runs.

#### 3.1.1 `setsid`

- Start: `2026-11-04T08:20:06Z`
- PID: `14139`
- Immediate check: alive
- After 25 seconds in a separate shell-tool command: dead
- Completion file: missing
- Exit-code file: missing
- Log: 0 bytes

#### 3.1.2 `nohup` + `disown`

- Start: `2026-11-04T08:20:40Z`
- PID: `14443`
- Immediate check: alive
- After 20 seconds in a separate shell-tool command: dead
- Completion file: missing
- Exit-code file: missing
- Log: 0 bytes

`screen` and `tmux` are not installed. `setsid`, `nohup`, `timeout`, and `flock`
are available, but the process tree is terminated when the shell-tool command
exits. A background Gradle run cannot be left running and polled from a later
shell-tool command.

**Classification:** detached/resumable local route **NOT CONFIRMED** and
**NOT VIABLE** in this sandbox.

### 3.2 Local single-command route

The prior verification attempts used the exact focused command:

```bash
/root/tools/gradle-9.6.1/bin/gradle :app:testDebugUnitTest \
  --offline \
  --no-daemon \
  --no-configuration-cache \
  --rerun-tasks \
  -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2 \
  -I /tmp/r2cuei/init.gradle \
  --console=plain \
  --tests "com.vantedge.app.w5.scoring.RequirementCoverageTest" \
  --tests "com.vantedge.app.w5.scoring.ProvisionalScoreTest" \
  --tests "com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest"
```

Observed history:

| Attempt | Shell timeout | Furthest task | Test XMLs | Outcome |
|---|---:|---|---:|---|
| 1 | 900 s | `:app:processDebugResources` | 0 | killed at shell timeout |
| 2 | 1,800 s | `:app:compileDebugKotlin` | 0 | killed at shell timeout |

The second attempt got further, but no Gradle exit code and no test execution were
captured. The init script bypassed the worktree `settings.gradle` repository
drift. The prior report classified the verification as **BLOCKED**, not failed.

A single foreground command with a larger configured timeout is the only local
route that tests the exact dirty worktree. It is **NOT CONFIRMED** as viable:
the shell tool's maximum timeout and the sandbox's hard limits were not
established, and this audit was prohibited from starting another long test
attempt. A future authorization would need a timeout comfortably above 1,800
seconds, a clean build directory or a deliberate decision to reuse the partial
build, and capture of the full log plus exit code. The partial `app/build` is
17 MB and `.kotlin/` is 7 KB; no fresh test XML exists.

**Classification:** local single-command route **NOT CONFIRMED**; requires a new
bounded authorization and a larger timeout.

### 3.3 GitHub Actions route

#### 3.3.1 Workflow facts

| Workflow | Triggers | Test task | Timeout | Artifact upload |
|---|---|---|---:|---|
| `ci.yml` | push/PR to `main`, `develop` | `./gradlew testDebugUnitTest --stacktrace` (all tests) | not set | test results/reports |
| `full-ci.yml` | push to `main`, `develop`, `recovery/reconstructed`; PR to `main`, `develop` | `compileDebugUnitTestKotlin`; `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"` | 15 min | `w5-determinism-results` |
| `w5-tests.yml` | push to `main`, `develop`, `feature/w5-scoring` with W5 paths; PR to `main`, `develop` with W5 paths | `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"`; baseline job | 10 min | `w5-test-results`, `w5-test-report`, baseline CSV |
| `h3-corrupted-ocr-test.yml` | `workflow_dispatch` only | OCR-only test | 15 min | OCR artifacts |

There is no W5 `workflow_dispatch`. The current branch
`verification/ced-p0-authority` is not in any push trigger. A PR to `develop` or
`main` is required. `ci.yml` does not strip the invalid
`android.aapt2FromMavenOverride` property; `full-ci.yml` and `w5-tests.yml` do.
`ci.yml` is therefore not preferred.

#### 3.3.2 Candidate commit

The prior blocker audit established the eight-path corrective set:

```text
app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt
app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt
app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt
app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt
app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt
app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt
app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt
app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt
```

All eight paths are currently modified in the worktree. The commit must exclude:

- `settings.gradle` (worktree drift; HEAD's version is correct and contains the
  repository blocks);
- `gradle.properties` (HEAD's version is used; CI strips the host-specific
  properties);
- unrelated worktree paths;
- `evidence/`;
- `.kotlin/` and build outputs.

A commit that excludes `settings.gradle` uses HEAD's blob, which contains the
correct `pluginManagement` and `dependencyResolutionManagement` blocks. A commit
that excludes `gradle.properties` uses HEAD's version; CI removes the invalid
aapt2 override and `org.gradle.java.home`.

The micro-patch-only two-path commit is **not** viable because the Mapper
depends on the uncommitted `W5Types.kt` enum and the prior-corrective test
updates. The eight-path set is the smallest coherent commit identified by the
prior audit.

#### 3.3.3 What CI would and would not test

**Would test:**

- The committed eight-path source state.
- Compilation of all unit-test Kotlin sources (`full-ci.yml`).
- The entire `com.vantedge.app.w5.scoring.*` package, including
  `RequirementCoverageTest`, `ProvisionalScoreTest`, and
  `CorrectiveUnknownStateTest` (`full-ci.yml` and `w5-tests.yml`).
- XML/HTML test artifacts, uploaded even on failure.

**Would not test:**

- The entire dirty worktree; unrelated modifications are excluded.
- The exact 42-test focused selection. The workflows run the broader W5 scoring
  package plus a baseline job. There is no existing workflow input or
  `workflow_dispatch` for the exact three-class selection.
- The worktree `settings.gradle` or `gradle.properties`; CI uses HEAD's files and
  strips host-specific properties.
- Remote execution in this audit; no network or credentials were used.

**Classification:** CI route is **CONFIRMED (analysis)** as a viable
evidence-preserving route for the committed eight-path subset, and **BLOCKED** in
this audit by the prohibition on commit/push/PR/network. It is **NOT CONFIRMED**
for the entire dirty worktree or the exact 42-test selection.

### 3.4 Route summary

| Route | Repo edits | Commit/push/PR | Tests exact worktree | Exact 42 tests | Available now |
|---|---|---|---|---|---|
| Detached local (`setsid`/`nohup`) | No | No | Yes, if it survived | Yes | **No; probes failed** |
| Single foreground local | No | No | Yes | Yes | Requires larger timeout and Gradle authorization; unverified |
| CI eight-path commit/PR | Commit only | Yes | Committed eight-path subset | No; broader W5 package | Requires authorization; **BLOCKED** now |
| CI on current branch without commit | No | No | No | No | **No; branch not triggered** |
| Restore `settings.gradle` | Yes | No | Yes | Yes | Not recommended; edits protected file |
| Existing stale `/tmp` copies | No | No | No | No | **No; stale** |

### 3.5 Recommendation

1. **For B1-E:** do not add a positive `UNRECONCILED` qualification test. Decide
   the contract first (Option A, B, or C in §2.7). If the intended defect is
   false completeness for a present-but-non-matching qualification, authorize a
   production change and add the expectation test that currently fails. If B1-E
   is meant literally, the production contract must make the state reachable.
2. **For verification:** prefer the CI eight-path commit/PR route when
   commit/push/PR/network authorization is available. It preserves XML/HTML
   artifacts and tests the committed corrective source. If exact dirty-worktree
   evidence is mandatory, authorize a single foreground local run with a timeout
   comfortably above 1,800 seconds; do not rely on detached processes.
3. **Do not** modify `settings.gradle`, `gradle.properties`, workflows, tests, or
   production code under this audit. R2 remains **OPEN**.

---

## 4. Limitations and no-test-pass statement

- No Gradle command was run in this audit.
- No JUnit test was executed.
- No fresh JUnit XML was produced.
- No test pass or fail is claimed.
- The B1-E unreachability conclusion is a static source proof, not a runtime
  result.
- The CI route was not executed; no network, commit, push, PR, or remote
  credentials were used.
- CI job timeouts are 10/15 minutes; remote runtime was not verified. The local
  sandbox's 30+ minute runtime is not evidence of CI runtime.
- The prior 142-test result belongs to the prior corrective and does not cover
  the current micro-patch.
- The worktree remains dirty and preserved, including `.kotlin/` and `app/build`.

---

## Appendix A — Evidence artifacts

Directory:
`/root/vantedge-canonical/evidence/VANTEDGE-R2-B1-E-TESTABILITY-AND-VERIFICATION-ROUTE-AUDIT-01/`

| File | SHA-256 |
|---|---|
| `pre-run-identity.txt` | `c4a20d96bf0d107f0fc8603de74471bb8e84fa0a2c2efb63fb256a6a67b50403` |
| `b1e-source-trace.txt` | `5d264087ea6eae19ccec8f1c906d457bee47e1d85cb0c0074b70a33d7d5933df` |
| `detach-probe.txt` | `61d9a287639cadfe53df28e56aedf8fe4b8d777ecb5008020282eea9a5decc7b` |
| `verification-route.txt` | `fa16e7eecf1f379dcc576a0812f3e705baab315ed293a75675d544a5f81c5ffe` |
| `post-run-identity.txt` | `384afb2928bb7c44159868c6f6a45ba8bcbb87a25e4f58be627d22b91a1dbe0a` |
| `REPORT.md` | recorded in `REPORT.sha256` after write |

Delivery copy:
`/data/user/0/ai.opencode.app/files/projects/Vantedge/evidence/put here!/VANTEDGE-R2-B1-E-TESTABILITY-AND-VERIFICATION-ROUTE-AUDIT-01-artifacts/`

Prior evidence:

| Artifact | SHA-256 |
|---|---|
| `VANTEDGE-R2-CURRENT-WORKTREE-VERIFICATION-01/REPORT.md` | `6e8be22833281f69d545e09480f9c8fc36604c4d159e97da3f9909b5d11b65c6` |
| `VANTEDGE-R2-VERIFICATION-BLOCKER-RECONCILIATION-01/REPORT.md` | `c5db5883e4d5f61fa7329492972109073d8c8ddd06be6d69fdce4ef0dbad3e17` |
| `gradle-run.log` | `b0d5356064217f851275504ba77a13c8a90fb2e51272612f47aa964dc0a31c6f` |
| `gradle-run-2.log` | `3d40534b8b9c553ee03575e8d6fa21f1728154624dd208e0564ef9621e6fd4cd` |
| `/tmp/r2cuei/init.gradle` | `6aa2bd5f4b2c79595b0224226173e9cd91aecef2fcf9e598780e161bdd1cd1ab` |

Relevant source hashes:

| File | SHA-256 |
|---|---|
| `CompatibilityRecordMapper.kt` | `bf34c26a16dcad1281553a20966171bc46a1f11f58491dbfa2cf6d9ab4b240c2` |
| `W5Types.kt` | `19d880dbb7bb64bb7d741e2ae214e12ed5cd38922a2b02edcfe616e6721f6964` |
| `JobRequirementAdapter.kt` | `0503ef3206f1200d5bac5e8523ef7efc81d1426632ef2fd7f1596827f4e9baaa` |
| `QualificationEvaluator.kt` | `ee18608a8aeb6d7599dfcbd8163bae1191446bc13e10e27570534e61a983e6c9` |
| `W5ScoreEngine.kt` | `ec28579a217d552222619940fbe5cd164b429edb90374efec61dfb7e17d25825` |
| `RequirementCoverageTest.kt` | `9db3e123c05ef5ab3cf9e9f0f57b5c54d5327862c829fbaf52fdfc6237cb4f92` |
| `CorrectiveUnknownStateTest.kt` | `363f088db6ae185fb62eb2c9d65e061f728191dc30403cda89ca3aca18404964` |
| `ProvisionalScoreTest.kt` | `87dca7ba65d289e2f51cdaa01835ed0e9b5a558feb5d430589aff4e423fa0e64` |

---

## Appendix B — Key commands and results

| Command / check | Result |
|---|---|
| `git rev-parse HEAD` | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` |
| `git status --porcelain=v1` | 40 M, 1 D, 34 ??; 0 staged |
| `sha256sum` of porcelain | `71570223b0346af77388bb5c71120be98845f737f06513441b541e8344211830` |
| `setsid` probe after 25 s | dead; no completion/exit file |
| `nohup` + `disown` probe after 20 s | dead; no completion/exit file |
| `find app/build/test-results -type f` | 0 files |
| `du -sh app/build .kotlin` | 17 MB, 7 KB |
| `git status --porcelain=v1 -- settings.gradle gradle.properties` | both modified; unchanged by audit |
| Gradle/test run | not performed |
| Commit/push/PR/network | not performed |

---

## Appendix C — Source references

| Reference | Role |
|---|---|
| `CompatibilityRecordMapper.kt:128-158` | qualification partition |
| `CompatibilityRecordMapper.kt:299-305` | required qualification key filter |
| `CompatibilityRecordMapper.kt:310-348` | assessment dispatch and reconciled fallback |
| `CompatibilityRecordMapper.kt:415-422` | skill `UNRECONCILED` branch |
| `CompatibilityRecordMapper.kt:426-477` | qualification assessment; unreachable `UNRECONCILED` at 472 |
| `CompatibilityRecordMapper.kt:203-225` | blocking gate and `evaluationComplete` |
| `W5Types.kt:98-105` | `UnassessedReason.UNRECONCILED` |
| `JobRequirementAdapter.kt:176-207` | structured qualification categories |
| `JobRequirementAdapter.kt:390-411` | description qualification categories |
| `RequirementCoverageTest.kt:282-304` | skill-only `UNRECONCILED` test |
| `RequirementCoverageTest.kt:419-466` | B1-A/B1-B tests |
| `CorrectiveUnknownStateTest.kt:114-128` | absent vs present-but-non-matching qualification |
| `QualificationEvaluator.kt` | no production callers |
| `W5ScoreEngine.kt` | no qualification references |

---

## Appendix D — Report integrity

The report hash is recorded in `REPORT.sha256` after the report is written. The
delivery copy must be verified against that hash.
