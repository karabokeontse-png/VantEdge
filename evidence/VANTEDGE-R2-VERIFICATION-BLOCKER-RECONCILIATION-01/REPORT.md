# VANTEDGE-R2-VERIFICATION-BLOCKER-RECONCILIATION-01

**Status:** COMPLETE — READ-ONLY AUDIT  
**Directive authority:** ChatGPT — Lead Architect  
**Executor:** OpenCode  
**Repository:** `/root/vantedge-canonical`  
**Branch:** `verification/ced-p0-authority`  
**HEAD:** `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2`  
**Tree:** `b6aa1d9cafb13154344274da187303076f338901`  
**Parent:** `3f23859c9d38828b1da673429c11d885b0b0219c`  
**Date:** 2026-11-04  
**Implementation authorization:** NONE  
**R2 disposition:** OPEN — not accepted as complete

---

## 0. Executive summary

The two reported blockers are real, but they have different scopes.

1. **The Gradle blocker is a worktree-only configuration drift, not a defect in HEAD.**  
   `settings.gradle` at HEAD contains the correct `pluginManagement` and
   `dependencyResolutionManagement` repository declarations. The working tree has only
   `include ':app'`. With the worktree file, Gradle searches only the default
   `Gradle Central Plugin Repository`; the cached Android Gradle Plugin 9.3.1 artifacts
   are keyed to the Google Maven repository identity, so offline resolution fails.
   Missing repository declarations are the primary cause; `--offline` prevents a
   network fallback. This is not offline mode alone.

2. **The corrective source is not self-contained against HEAD.**  
   `UnassessedReason.UNRECONCILED` exists only in the uncommitted `W5Types.kt` change
   introduced by the prior corrective. The current `CompatibilityRecordMapper.kt`
   references it at lines 418 and 472. The current micro-patch is layered on the prior
   corrective Mapper and therefore inherits that dependency. It cannot compile against
   HEAD's `W5Types.kt` alone.

A safe verification route exists without editing `settings.gradle`:

- **Minimum route:** run the narrow test set on the current worktree with the existing
  external init script `/tmp/r2cuei/init.gradle`, `--offline`, the guest aapt2 override,
  and a clean build directory. This injects the repository declarations in memory and
  does not modify the repository. It does require authorization to run Gradle and write
  build/cache outputs.
- **GitHub Actions route:** a commit that excludes `settings.gradle` uses HEAD's correct
  `settings.gradle`. A single compilable corrective commit can be formed from the eight
  prior-corrective paths listed in §5.1. It is **not** a micro-patch-only commit; it
  includes the required prior-corrective dependencies. A temporary branch/PR to
  `develop` or `main` would trigger `full-ci.yml` and `w5-tests.yml`. `ci.yml` is not
  preferred because it does not strip the host-specific aapt2 override.

No Gradle test or build was executed in this audit. No test-pass claim is made for the
current micro-patch. The prior corrective's 142-test result predates the micro-patch and
is not evidence for it. R2 remains **OPEN**.

---

## 1. Repository identity and non-mutation proof

### 1.1 Identity

| Item | Value |
|---|---|
| Repository | `/root/vantedge-canonical` |
| Branch | `verification/ced-p0-authority` |
| HEAD | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` |
| HEAD tree | `b6aa1d9cafb13154344274da187303076f338901` |
| HEAD parent | `3f23859c9d38828b1da673429c11d885b0b0219c` |
| Staged paths | 0 |
| Worktree counts | 40 modified, 1 deleted, 33 untracked |
| Porcelain SHA-256 | `343e73a969ff93556909c00d906c2e8bbf693697beabc92e9fafb81daf0893e0` |

The porcelain hash is the same as the pre-audit micro-patch status hash. No tracked
source, workflow, settings, or Gradle file was modified by this audit. The only
repository writes are the required report and evidence artifacts under
`evidence/VANTEDGE-R2-VERIFICATION-BLOCKER-RECONCILIATION-01/`. A delivery copy was also
written outside the repository to the project's `evidence/put here!/` folder.
`evidence/` was already an untracked directory before this audit.

### 1.2 Non-mutation statement

This audit did **not**:

- edit any source, test, asset, manifest, database, schema, navigation, networking,
  provider, Gradle, or workflow file;
- stage, commit, amend, push, branch, checkout, reset, clean, stash, revert, or discard
  any path;
- install or update dependencies;
- run a Gradle build or test;
- run an emulator, device, APK, Compose UI, Room, or orchestrator test;
- modify `settings.gradle` to make the build work.

The prior micro-patch's failed local test attempt is cited as historical evidence only.

---

## 2. Finding A — `settings.gradle` drift and Gradle plugin resolution

### 2.1 HEAD versus worktree

`settings.gradle` at HEAD:

```groovy
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VantEdge"
include ':app'
```

Working-tree `settings.gradle`:

```groovy
include ':app'
```

Exact diff:

```diff
diff --git a/settings.gradle b/settings.gradle
index e0c4583..e7b4def 100644
--- a/settings.gradle
+++ b/settings.gradle
@@ -1,18 +1 @@
-pluginManagement {
-    repositories {
-        google()
-        mavenCentral()
-        gradlePluginPortal()
-    }
-}
-
-dependencyResolutionManagement {
-    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
-    repositories {
-        google()
-        mavenCentral()
-    }
-}
-
-rootProject.name = "VantEdge"
 include ':app'
```

Hashes:

| File | SHA-256 |
|---|---|
| HEAD `settings.gradle` | `234d51d6433b2d926cc8668a8c4fc5f839352c39de1439b8b5bb6c1708e861e1` |
| Worktree `settings.gradle` | `4b919cfffaed71637d1ee9c89f21f4247273e14eb8433afdcd0672eba906b41f` |
| HEAD blob | `e0c45838719eb70a1aad67ff7544536157dcd1b3` |

### 2.2 Is the drift pre-existing?

Yes. The prior corrective preflight at `2026-11-04T00:04:54Z` already recorded
`M settings.gradle` and `M gradle.properties`. The prior corrective report explicitly
states that no Gradle file was modified by that corrective. The micro-patch directive
also prohibited `settings.gradle` changes, and none were made. The drift predates both
correctives.

### 2.3 Plugin and dependency repository configuration

`build.gradle`:

```groovy
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.21'
    }
}

plugins {
    id 'com.android.application' version '9.3.1' apply false
    id 'com.android.legacy-kapt' version '9.3.1' apply false
    id 'org.jetbrains.kotlin.plugin.serialization' version '2.3.21' apply false
    id 'org.jetbrains.kotlin.plugin.compose' version '2.3.21' apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```

`app/build.gradle` applies the same plugins:

```groovy
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.plugin.serialization'
    id 'org.jetbrains.kotlin.plugin.compose'
    id 'com.android.legacy-kapt'
}
```

The `plugins {}` block resolves through `settings.gradle`'s `pluginManagement`
repositories, not through `buildscript.repositories`. Removing `pluginManagement` from
the worktree therefore removes `google()` and `mavenCentral()` from plugin resolution
and leaves the Gradle default, `Gradle Central Plugin Repository`.

### 2.4 Exact failure

The micro-patch local test attempt recorded:

```text
* Where:
Build file '/root/vantedge-canonical/build.gradle' line: 15

* What went wrong:
Plugin [id: 'com.android.application', version: '9.3.1', apply: false] was not found in any of the following sources:

- Gradle Core Plugins (plugin is not in 'org.gradle' namespace)
- Included Builds (No included builds contain this plugin)
- Plugin Repositories (could not resolve plugin artifact 'com.android.application:com.android.application.gradle.plugin:9.3.1')
  Searched in the following repositories:
    Gradle Central Plugin Repository
```

Exit code: `1`. No new JUnit XMLs were produced.

### 2.5 Local cache inventory

The required plugin markers and artifacts are present in the local Gradle cache:

| Artifact | Cached file |
|---|---|
| `com.android.application.gradle.plugin:9.3.1` | `.../com.android.application.gradle.plugin-9.3.1.pom` |
| `com.android.legacy-kapt.gradle.plugin:9.3.1` | `.../com.android.legacy-kapt.gradle.plugin-9.3.1.pom` |
| `org.jetbrains.kotlin.plugin.serialization.gradle.plugin:2.3.21` | `.../org.jetbrains.kotlin.plugin.serialization.gradle.plugin-2.3.21.pom` |
| `org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.3.21` | `.../org.jetbrains.kotlin.plugin.compose.gradle.plugin-2.3.21.pom` |
| `org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.21` | POM, module, `kotlin-gradle-plugin-2.3.21-gradle813.jar` |
| `com.android.tools.build:gradle:9.3.1` | POM, module, `gradle-9.3.1.jar` |

The cache metadata shows the repository identity:

| Artifact | Descriptor repository hash | Comparable artifact |
|---|---|---|
| `com.android.application` marker 9.3.1 | `d4e342018b23d58be902a60e67105aa1` | `androidx.core:core-ktx` uses the same hash |
| `com.android.tools.build:gradle` 9.3.1 | `d4e342018b23d58be902a60e67105aa1` | Google Maven |
| Kotlin plugin markers 2.3.21 | `26c913274550a0b2221f47a0fe2d2358` | `kotlin-stdlib` uses the same hash |
| Kotlin plugin markers 2.3.21 | `26c913274550a0b2221f47a0fe2d2358` | Maven Central |

The cached 9.3.1 artifacts are therefore associated with Google Maven/Maven Central,
not the default Gradle Plugin Portal repository identity. Because the worktree removed
`pluginManagement`, Gradle does not look at those repository identities. `--offline`
then prevents any network fallback.

### 2.6 Cause classification

| Question | Finding | Classification |
|---|---|---|
| Does the worktree differ from HEAD? | Yes; 17 lines removed, only `include ':app'` remains | **CONFIRMED** |
| Is the drift pre-existing? | Yes; present before both correctives | **CONFIRMED** |
| Is offline mode the sole cause? | No; cached artifacts exist under Google/Maven Central identities | **CONFIRMED** |
| Are missing plugin repositories the primary cause? | Yes; failure searches only Gradle Central Plugin Repository | **CONFIRMED** |
| Does `--offline` contribute? | Yes; it prevents a network fallback | **CONFIRMED** |
| Does restoring `pluginManagement` fix plugin resolution? | Static/cache analysis says yes; not executed | **CONFIRMED (analysis)** / **NOT VERIFIED (run)** |
| Does restoring full HEAD `settings.gradle` restore exact behavior? | It restores all three missing sections | **CONFIRMED (content)** / **NOT VERIFIED (run)** |
| Can this audit apply the restoration? | No; read-only, and `settings.gradle` is protected | **BLOCKED** |

### 2.7 Minimum restoration options

**Minimal plugin-resolution block** (not applied):

```groovy
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
```

**Exact HEAD behavior** additionally restores:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VantEdge"
```

The plugin failure occurs before dependency resolution, so the `pluginManagement` block
is the minimal block that changes the failing repository set. `dependencyResolutionManagement`
and `rootProject.name` are still part of the exact HEAD behavior and should be restored
together if the owner chooses the settings-file route.

This audit does **not** assume that restoring the entire HEAD file is authorized. Any
`settings.gradle` edit requires a new explicit authorization because the micro-patch
directive prohibits it.

---

## 3. Finding B — `UnassessedReason.UNRECONCILED` dependency

### 3.1 Declaration and introduction point

HEAD `W5Types.kt` excerpt:

```kotlin
enum class UnassessedReason {
    ...
    PROFILE_QUALIFICATION_EVIDENCE_ABSENT,
    TAXONOMY_MISS,
    UNSUPPORTED_CATEGORY,
    PREFERENCE_NOT_ASSESSED
}
```

Worktree `W5Types.kt` excerpt:

```kotlin
enum class UnassessedReason {
    ...
    PROFILE_QUALIFICATION_EVIDENCE_ABSENT,
    TAXONOMY_MISS,
    UNSUPPORTED_CATEGORY,
    PREFERENCE_NOT_ASSESSED,
    UNRECONCILED
}
```

Exact diff:

```diff
@@ -100,7 +100,8 @@ enum class UnassessedReason {
     PROFILE_QUALIFICATION_EVIDENCE_ABSENT,
     TAXONOMY_MISS,
     UNSUPPORTED_CATEGORY,
-    PREFERENCE_NOT_ASSESSED
+    PREFERENCE_NOT_ASSESSED,
+    UNRECONCILED
 }
```

| Item | Value |
|---|---|
| Declaration | `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt:104` |
| HEAD SHA-256 | `ed880cb97acce53ffdf41f6f11eb963a156c10c870a47bb29596e18722d28efb` |
| Worktree SHA-256 | `19d880dbb7bb64bb7d741e2ae214e12ed5cd38922a2b02edcfe616e6721f6964` |
| Commit containing it | None (`git log --all -S'UNRECONCILED'` returned empty) |
| Introduction point | Prior corrective `VANTEDGE-R2-CORRECTIVE-UNKNOWN-EVIDENCE-INTEGRITY-01`, C3/AC3, uncommitted |

### 3.2 Every production and test reference

| Path:line | Kind | Use |
|---|---|---|
| `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt:104` | Declaration | Enum value |
| `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt:418` | Production, enum | `assessSkill` fallback when profile skills exist but the evidence cannot be reconciled |
| `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt:472` | Production, enum | `assessQualification` fallback for an unreconciled qualification |
| `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt:292` | Test, string | Asserts `"UNRECONCILED"` for a reconciliation failure |
| `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt:445` | Test, string | Asserts no assessment has reason `"UNRECONCILED"` for a non-required profile qualification |
| `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt:172` | Test, string | Legacy JSON fixture uses reason `"UNRECONCILED"` |
| `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt:184` | Test, string | Asserts round-trip reason `"UNRECONCILED"` |
| `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt:753` | Test, string | P2 independence fixture uses reason `"UNRECONCILED"` |

There are no other references. Only the two Mapper references use the enum type; all test
references are string literals.

### 3.2.1 `RequirementCoverageTest.kt` HEAD versus worktree

The scope explicitly includes this file because the micro-patch's B1 regression tests
live there.

| Item | HEAD | Worktree |
|---|---:|---:|
| `@Test` methods | 8 | 21 |
| SHA-256 | `34e1adedb5885a2f289d9a1c014144c1e844b885d1be73b5902626442697fa31` | `9db3e123c05ef5ab3cf9e9f0f57b5c54d5327862c829fbaf52fdfc6237cb4f92` |
| Diff versus HEAD | — | 224 insertions, 0 deletions |

The worktree adds three micro-patch test functions:

- `job required qualification with matching profile evidence is matched`
- `profile qualification not required by the job is not unreconciled and does not block completeness`
- `missing job required qualification is reconciled and blocks false completeness`

It also replaces the prior-corrective test
`unreconciled qualification inventory is not mislabeled as absent profile evidence` with
the second function above. The prior corrective added the other tests. The current file
has 21 `@Test` methods; the stale JUnit XML records 19 tests from
`2026-11-04T00:45:04.513Z`, including the now-replaced test name. That XML predates the
micro-patch and cannot be used as evidence for the current source.

### 3.3 Mapper dependency

HEAD Mapper anchors:

```text
270: private fun buildRequirementAssessments(
287: return source.map { evidence ->
308: private fun assessSkill(
358: private fun assessQualification(
```

HEAD Mapper has no `UNRECONCILED` reference. Its qualification fallback uses
`PROFILE_QUALIFICATION_EVIDENCE_ABSENT` at lines 372 and 404.

Worktree Mapper anchors:

```text
276: private fun buildRequirementAssessments(
348: return assessments + reconciledQualifications
359: private fun assessSkill(
418: UnassessedReason.UNRECONCILED
426: private fun assessQualification(
472: reason = UnassessedReason.UNRECONCILED.name,
```

Current Mapper excerpts:

```kotlin
// assessSkill fallback, lines 415-422
else -> unassessed(
    evidence,
    if (profileSkills.isNotEmpty()) {
        UnassessedReason.UNRECONCILED
    } else {
        UnassessedReason.PROFILE_SKILL_EVIDENCE_ABSENT
    }
)
```

```kotlin
// assessQualification fallback, lines 468-475
else -> RequirementAssessment(
    text = evidence.originalText,
    state = RequirementAssessmentState.UNASSESSED,
    canonical = canonical,
    reason = UnassessedReason.UNRECONCILED.name,
    category = evidence.category.name,
    importance = importance
)
```

The current Mapper cannot compile against HEAD `W5Types.kt` because
`UnassessedReason.UNRECONCILED` is unresolved.

### 3.4 Does the micro-patch add the dependency?

No. The micro-patch diff adds only one `UNRECONCILED` string assertion:

```diff
+        assertTrue(assessments.none { it.reason == "UNRECONCILED" })
```

It adds no enum references and no imports. It depends on the current Mapper, which
already contains the prior-corrective enum references. The B1 reconciliation calls
`assessQualification`, whose fallback contains the enum reference at line 472.
Therefore the micro-patch inherits the dependency; it does not introduce it.

### 3.5 Smallest coherent source dependency set

**Narrow B1/AC6/AC7/AC8 set** (changed paths):

1. `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt`
2. `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt`
3. `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt`
4. `app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt`

`app/src/test/java/com/vantedge/app/w5/scoring/CorrectiveUnknownStateTest.kt` is already
tracked at HEAD and unchanged; it is retained as a regression test and does not need to
be staged.

**Full-suite corrective set** adds the remaining prior-corrective test paths:

5. `app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt`
6. `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt`
7. `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt`
8. `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt`

The micro-patch adds no new imports or external dependencies. The narrow set is the
minimum needed to compile and run the B1/AC6 correction plus the AC7/AC8 test evidence.

### 3.6 Classification

| Question | Finding | Classification |
|---|---|---|
| Is `UNRECONCILED` declared at `W5Types.kt:104`? | Yes | **CONFIRMED** |
| Is it absent from HEAD? | Yes; `git grep` at HEAD returned no matches | **CONFIRMED** |
| Is it in any commit? | No; `git log --all -S` returned empty | **CONFIRMED** |
| Does current Mapper use it? | Yes, lines 418 and 472 | **CONFIRMED** |
| Can current Mapper compile against HEAD `W5Types.kt`? | No; unresolved enum reference | **CONFIRMED (static)** |
| Does the micro-patch itself add enum references? | No; only a string assertion | **CONFIRMED** |
| Does the micro-patch depend on the prior-corrective Mapper? | Yes | **CONFIRMED** |
| Is the narrow set sufficient to compile the correction? | Static analysis says yes; not executed | **CONFIRMED (analysis)** / **NOT VERIFIED (run)** |
| Can this audit execute a compile to prove it? | No; read-only and no cache mutation | **BLOCKED** |

---

## 4. Finding C — Safe verification routes

### 4.1 Route 1 — current worktree with external init script (recommended minimum)

The prior corrective already proved this route's mechanics:

- Without an init script, Gradle failed with the same
  `com.android.application:9.3.1` plugin-resolution error.
- With `/tmp/r2cuei/init.gradle`, the prior corrective compiled and ran 142 tests
  successfully.

Init script:

```groovy
beforeSettings { settings ->
    settings.pluginManagement {
        repositories {
            google()
            mavenCentral()
            gradlePluginPortal()
        }
    }
    settings.dependencyResolutionManagement {
        repositories {
            google()
            mavenCentral()
        }
    }
}
```

Recommended narrow command (not run in this audit):

```bash
rm -rf app/build .kotlin .gradle/configuration-cache

/root/tools/gradle-9.6.1/bin/gradle :app:testDebugUnitTest \
  --offline \
  --no-daemon \
  --no-configuration-cache \
  -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2 \
  -I /tmp/r2cuei/init.gradle \
  --tests "com.vantedge.app.w5.scoring.RequirementCoverageTest" \
  --tests "com.vantedge.app.w5.scoring.ProvisionalScoreTest" \
  --tests "com.vantedge.app.w5.scoring.CorrectiveUnknownStateTest"
```

Why this is safe:

- It does not edit `settings.gradle`; repositories are injected in memory.
- It tests the exact current worktree source, including the micro-patch and the
  prior-corrective `W5Types.kt` dependency.
- It uses cached dependencies with `--offline`; no dependency installation is required.
- It uses the guest aapt2 override instead of the invalid AndroidIDE host path in
  `gradle.properties`.

Prerequisites and limitations:

- Requires authorization to run Gradle and write `app/build`, `.gradle`, and cache
  outputs. The audit prohibition on cache mutation prevents running it now.
- A clean build directory and `--no-configuration-cache` avoid stale incremental or
  configuration-cache artifacts. The worktree has `kotlin.incremental=true` and a prior
  failed configuration-cache entry.
- This route produces local JUnit XMLs, not GitHub Actions evidence.
- The prior 142-test result is for the prior corrective, not the current micro-patch.
  A fresh run is required.

**Classification:** CONFIRMED viable by prior evidence; **BLOCKED** in this audit by the
read-only/no-cache-mutation constraint.

### 4.2 Route 2 — isolated copy or worktree

Existing temporary copies are stale:

| Path | State | Suitable? |
|---|---|---|
| `/tmp/r2-verify` | Git worktree at HEAD `ed83a02c5b14e3d5b116c00fd3621b66d0a4c14c`; 24 M, 10 ?? | No; different commit and dirty |
| `/tmp/ckpt` | Non-git copy dated 2026-10-16 | No; stale |

A fresh copy from the current worktree, or from HEAD plus the eight-path diff, could
isolate the corrective source from unrelated worktree changes. However:

- creating and populating a copy is extra implementation work;
- `git worktree add` mutates `.git/worktrees` metadata and is not appropriate for a
  read-only audit;
- running Gradle in the copy still writes build/cache outputs.

This route is technically possible but not necessary; Route 1 is simpler and tests the
exact worktree source.

**Classification:** Existing copies **NOT VIABLE**; fresh copy **CONFIRMED possible but
not executed**.

### 4.3 Route 3 — GitHub Actions via temporary commit/branch/PR

Workflow triggers and tasks:

| Workflow | Triggers | Test task | aapt2 handling |
|---|---|---|---|
| `ci.yml` | push/PR to `main`, `develop` | `./gradlew testDebugUnitTest --stacktrace` (all tests) | **No** `sed` removal; host-specific aapt2 override remains |
| `full-ci.yml` | push to `main`, `develop`, `recovery/reconstructed`; PR to `main`, `develop` | `compileDebugUnitTestKotlin`; `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"` | Removes `org.gradle.java.home` and `android.aapt2FromMavenOverride` |
| `w5-tests.yml` | push to `main`, `develop`, `feature/w5-scoring` with W5 paths; PR to `main`, `develop` with W5 paths | `testDebugUnitTest --tests "com.vantedge.app.w5.scoring.*"`; baseline job | Removes both host-specific properties |
| `h3-corrupted-ocr-test.yml` | `workflow_dispatch` only | OCR-only test | Not relevant |

There is no W5 `workflow_dispatch`. The current branch
`verification/ced-p0-authority` is not in any push trigger. A push to that branch alone
does not trigger the W5 workflows. A PR to `develop` or `main` is required.

Key fact: a commit that does **not** stage `settings.gradle` uses HEAD's blob
`e0c45838719eb70a1aad67ff7544536157dcd1b3`, which contains the correct
`pluginManagement` and `dependencyResolutionManagement` blocks. The Gradle blocker is
therefore worktree-only for this route.

Candidate commit:

- **Full-suite corrective set:** the eight paths in §3.5. This includes the current
  micro-patch and all prior-corrective test updates needed for the existing test suite.
- **Narrow set:** the four changed paths in §3.5. This may be sufficient for
  `full-ci.yml`/`w5-tests.yml`, but `ci.yml` runs all tests and may need the other four
  prior-corrective test updates.

Exclusions: `settings.gradle`, `gradle.properties`, all unrelated worktree paths, and
`evidence/`.

Risks:

- Requires commit, push, PR, and remote credentials; this audit is read-only and did not
  attempt any of these.
- `ci.yml` does not strip the invalid `android.aapt2FromMavenOverride` path from HEAD
  `gradle.properties`; `full-ci.yml` and `w5-tests.yml` do. Prefer the latter two.
- A commit that excludes `app/build.gradle` uses HEAD's version, which lacks the
  `kotlinx-coroutines-test` dependency added in the worktree. The narrow corrective
  tests do not import it; only untracked lifecycle tests do. This is acceptable for the
  narrow set but should be understood for any all-tests run.

**Classification:** CONFIRMED viable with commit/push/PR authorization; **BLOCKED** in
this audit.

### 4.4 Route 4 — restore `settings.gradle` in the worktree

This would modify a protected file and contaminate the preserved dirty worktree. It is
not necessary because Route 1 injects the repositories externally and Route 3 uses
HEAD's correct `settings.gradle`. It would require explicit authorization and a reliable
restore step.

**Classification:** **NOT RECOMMENDED**.

### 4.5 Route summary

| Route | Edits repo? | Needs commit/push? | Tests exact worktree? | Available now? |
|---|---|---|---|---|
| Init script + current worktree | No | No | Yes | Requires verification authorization for Gradle writes |
| Fresh isolated copy | No (outside repo) | No | Yes, if built correctly | Requires copy + Gradle writes |
| Temporary commit/PR | Yes (commit only) | Yes | Yes, committed subset | Requires commit/push/PR authorization |
| Restore `settings.gradle` | Yes | No | Yes | Requires settings authorization; not recommended |
| Existing `/tmp` copies | No | No | No; stale | No |

---

## 5. Finding D — Path-level commit boundary

The complete 74-entry worktree classification is in Appendix A and in
`path-classification.txt`. The counts below are category occurrences, not mutually
exclusive; the eight prior-corrective paths include the two micro-patch paths and the
four narrow-required paths. Total status entries: 74.

| Classification | Count |
|---|---:|
| `PRIOR_CORRECTIVE_DEPENDENCY` (8 paths; 2 also current micro-patch) | 8 |
| `CURRENT_MICRO_PATCH_DELTA` | 2 |
| `REQUIRED_FOR_NARROW_B1_AC6_AC7_AC8_COMPILE` | 4 |
| `PREEXISTING_UNRELATED` | 63 |
| `PREEXISTING_UNRELATED_BUILD_SPEED_CONFIG` (`gradle.properties`) | 1 |
| `GRADLE_DRIFT_RESTORATION_CANDIDATE` (`settings.gradle`) | 1 |
| `REQUIRED_AUDIT_OUTPUT_LOCATION` (`evidence/`) | 1 |

### 5.1 Single compilable corrective commit

**Yes, structurally, for the eight-path full-suite corrective set**, provided the owner
authorizes inclusion of the prior-corrective dependencies:

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

This commit:

- includes the current micro-patch (in Mapper and RequirementCoverageTest);
- includes the required `W5Types.kt` enum dependency;
- includes the prior-corrective test updates needed for full-suite consistency;
- excludes `settings.gradle`, so the commit tree uses HEAD's correct settings;
- excludes `gradle.properties`, so it uses HEAD's CI-compatible-enough version (subject
  to the `ci.yml` aapt2 caveat);
- excludes all 63 unrelated worktree paths and `evidence/`.

It is **not a micro-patch-only commit**. The micro-patch is layered on the prior
corrective Mapper and cannot be isolated from that Mapper without a new standalone
implementation against HEAD. A micro-patch-only commit is therefore **NOT CONFIRMED** as
possible from the current worktree state.

Compilability is not executed in this audit. The eight-path set is structurally coherent;
a fresh compile/test run is still required. The prior corrective's 142-test result does
not cover the micro-patch changes made after that run.

### 5.2 Narrow four-path commit

For `full-ci.yml`/`w5-tests.yml` only, the narrow set is:

```text
app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt
app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt
app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt
app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt
```

This excludes the four prior-corrective test files not needed for the narrow set. It may
not satisfy `ci.yml`'s all-tests job, which would run HEAD versions of those tests
against the new Mapper behavior.

### 5.3 Classification

| Question | Finding | Classification |
|---|---|---|
| Is the 8-path boundary exact and free of unrelated files? | Yes, per Appendix A | **CONFIRMED** |
| Can a single compilable corrective commit be formed? | Yes structurally; not executed | **CONFIRMED (analysis)** / **NOT VERIFIED (run)** |
| Can a micro-patch-only commit be formed? | No; it depends on prior-corrective Mapper/W5Types | **NOT CONFIRMED** |
| Can this audit create the commit? | No; prohibited | **BLOCKED** |

---

## 6. Minimum safe authorization recommendation

### 6.1 Recommended minimum authorization

Authorize exactly the following bounded verification action:

1. Delete the ignored build outputs `app/build`, `.kotlin`, and
   `.gradle/configuration-cache` to eliminate stale incremental/configuration-cache
   state. This is build-output cleanup, not a Git clean or worktree reset, and it does
   not touch tracked or untracked source.
2. Run the narrow Gradle test command on the current worktree with:
   - `/root/tools/gradle-9.6.1/bin/gradle :app:testDebugUnitTest`
   - `--offline`
   - `--no-daemon`
   - `--no-configuration-cache`
   - `-Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2`
   - `-I /tmp/r2cuei/init.gradle`
   - the three `--tests` filters in §4.1.
3. Permit Gradle build/cache writes and JUnit XML/report generation.
4. Do **not** permit source, test, settings, workflow, staging, commit, branch, or
   dependency changes.

This is the minimum authorization needed to verify B1/AC6 and AC7/AC8 against the exact
current worktree source without editing `settings.gradle` and without committing.

### 6.2 If GitHub Actions evidence is mandatory

Additionally authorize a temporary commit on a temporary branch containing exactly the
eight paths in §5.1, excluding `settings.gradle`, `gradle.properties`, all unrelated
paths, and `evidence/`; push the branch and open a PR to `develop` or `main` to trigger
`full-ci.yml` and `w5-tests.yml`. Prefer those workflows over `ci.yml` because `ci.yml`
does not strip the invalid aapt2 override.

This requires commit, push, PR, and remote credentials. It is not authorized by this
audit and was not attempted.

### 6.3 Do not authorize

- Editing `settings.gradle` unless the owner specifically chooses that route.
- Editing workflow YAML.
- Staging or committing unrelated worktree paths.
- Git reset, `git clean`, stash, revert, checkout, or discard of the preserved worktree.
- Dependency installation or network fetching.
- Emulator, device, APK, Compose UI, Room, or orchestrator testing.

### 6.4 Disposition

R2 remains **OPEN**. The current micro-patch is implemented but unverified. The next
action should be a verification-only authorization as in §6.1, followed by a fresh
narrow test run and evidence capture. If CI evidence is required, follow with §6.2.

---

## 7. Limitations and no-test-pass statement

- No Gradle test or build was executed in this audit.
- No test-pass claim is made for the current micro-patch.
- The prior corrective's 142 passing tests were run before the micro-patch and do not
  cover the current Mapper/RequirementCoverageTest changes.
- The existing `app/build/test-results/.../RequirementCoverageTest.xml` is stale:
  it records 19 tests at `2026-11-04T00:45:04.513Z`, while the current source has 21
  `@Test` methods and was modified at `2026-11-04 02:20:52Z`.
- The current source has not been compiled or run.
- The commit boundary is a static/structural conclusion, not a compile result.
- No GitHub Actions run was triggered.
- No commit, push, PR, or branch change was made.
- The audit's only repository writes are the report and evidence artifacts; the
  delivery copy is outside the repository.

---

## Appendix A — Complete path-level classification

The following table is generated from the current `git status --porcelain=v1` and the
classifications used in this report. It contains all 74 worktree entries.

| STATUS | PATH | CLASSIFICATION |
|---|---|---|
| M | `app/build.gradle` | PREEXISTING_UNRELATED |
| M | `app/src/main/AndroidManifest.xml` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/domain/CompatibilityRecordMapper.kt` | PRIOR_CORRECTIVE_DEPENDENCY, CURRENT_MICRO_PATCH_DELTA, REQUIRED_FOR_NARROW_B1_AC6_AC7_AC8_COMPILE |
| M | `app/src/main/java/com/vantedge/app/data/domain/OptimizationOrchestrator.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/engine/GeneratorEngine.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/network/AiGateway.kt` | PREEXISTING_UNRELATED |
| D | `app/src/main/java/com/vantedge/app/data/network/AiResult.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/network/GeminiService.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/storage/VantEdgeDatabase.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/CompatibilityViewModel.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/CycleNavEvent.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/CycleUiState.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/CycleViewModel.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/GeneratorViewModel.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/JobExtractionState.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/data/viewmodel/OnboardingViewModel.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/domain/PipelineTrace.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/domain/extraction/ExtractionOutcome.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/domain/extraction/JobExtractionOrchestrator.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/navigation/Navigation.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/ui/screens/CVDesignScreen.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/ui/screens/CVGeneratorScreen.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/ui/screens/CompatibilityInputScreen.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/ui/screens/EditProfileScreen.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/ui/screens/JobInputScreen.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/ui/screens/ResultScreen.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/util/FailureLogBundle.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/util/LogDumper.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/util/LogSink.kt` | PREEXISTING_UNRELATED |
| M | `app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt` | PRIOR_CORRECTIVE_DEPENDENCY, REQUIRED_FOR_NARROW_B1_AC6_AC7_AC8_COMPILE |
| M | `app/src/test/java/com/vantedge/app/data/engine/DateRecoveryPostProcessingTest.kt` | PREEXISTING_UNRELATED |
| M | `app/src/test/java/com/vantedge/app/data/storage/CompatibilityRecordLegacyJsonTest.kt` | PRIOR_CORRECTIVE_DEPENDENCY, PRIOR_CORRECTIVE_NOT_REQUIRED_FOR_NARROW_SET |
| M | `app/src/test/java/com/vantedge/app/data/viewmodel/CycleScoreAggregationTest.kt` | PRIOR_CORRECTIVE_DEPENDENCY, PRIOR_CORRECTIVE_NOT_REQUIRED_FOR_NARROW_SET |
| M | `app/src/test/java/com/vantedge/app/data/viewmodel/ExtractionOutcomeMapperTest.kt` | PREEXISTING_UNRELATED |
| M | `app/src/test/java/com/vantedge/app/w5/scoring/ProvisionalScoreTest.kt` | PRIOR_CORRECTIVE_DEPENDENCY, REQUIRED_FOR_NARROW_B1_AC6_AC7_AC8_COMPILE |
| M | `app/src/test/java/com/vantedge/app/w5/scoring/RequirementCoverageTest.kt` | PRIOR_CORRECTIVE_DEPENDENCY, CURRENT_MICRO_PATCH_DELTA, REQUIRED_FOR_NARROW_B1_AC6_AC7_AC8_COMPILE |
| M | `app/src/test/java/com/vantedge/app/w5/scoring/W5ScoreEngineTest.kt` | PRIOR_CORRECTIVE_DEPENDENCY, PRIOR_CORRECTIVE_NOT_REQUIRED_FOR_NARROW_SET |
| M | `app/src/test/java/com/vantedge/pipeline/validation/P2VerificationHarnessTest.kt` | PRIOR_CORRECTIVE_DEPENDENCY, PRIOR_CORRECTIVE_NOT_REQUIRED_FOR_NARROW_SET |
| M | `gradle.properties` | PREEXISTING_UNRELATED_BUILD_SPEED_CONFIG |
| M | `settings.gradle` | GRADLE_DRIFT_RESTORATION_CANDIDATE |
| ?? | `AUDIT_2026-10-15.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_AUDIT_BRIEF.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_AUDIT_INDEX.json` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_BUILD_SPEED_CONFIG_CACHE.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_CED-PRIV-01_REVISION_E_AUDIT.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_DETERMINISTIC_FIRST_PHASE1.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_DETERMINISTIC_FIRST_PHASE3.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_P0_PROFILE_EXTRACTION_INVESTIGATION.md` | PREEXISTING_UNRELATED |
| ?? | `VANTEDGE_SIGNING_CONTINUITY_REPORT.md` | PREEXISTING_UNRELATED |
| ?? | `app/src/androidTest/java/com/vantedge/app/R2AutoVerifyTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/domain/GenerationOrchestrator.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/domain/GenerationOutcome.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/domain/ProfileEligibility.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/engine/DeterministicProfileExtractor.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/model/GenerationRequest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/network/AiTextGateway.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/data/viewmodel/ExtractionReviewController.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/domain/extraction/ExtractionReview.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/java/com/vantedge/app/ui/components/ExtractionReviewSurface.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/res/xml/backup_rules.xml` | PREEXISTING_UNRELATED |
| ?? | `app/src/main/res/xml/data_extraction_rules.xml` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/A13BlankApiKeyTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/data/domain/` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/data/engine/DeterministicProfileExtractorTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/data/engine/NormalizeRootKeysDateAuthorityTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/data/engine/ReviewedJobRequirementsPromptTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/data/viewmodel/CycleViewModelHandoffLifecycleTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/data/viewmodel/GeneratorViewModelReviewedRequirementsChainTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/domain/extraction/ExtractionReviewTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/domain/extraction/JobExtractionDecisionEnforcementTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/domain/extraction/JobExtractionOrchestratorFieldPreservationTest.kt` | PREEXISTING_UNRELATED |
| ?? | `app/src/test/java/com/vantedge/app/navigation/` | PREEXISTING_UNRELATED |
| ?? | `evidence/` | REQUIRED_AUDIT_OUTPUT_LOCATION |

---

## Appendix B — Evidence artifacts and hashes

| Artifact | SHA-256 |
|---|---|
| `commands.txt` | `b7b623b3c6edd25a5a6388c42c9fc71256de4449cfdba6ad652d3bf7cf809855` |
| `commit-boundary.txt` | `d495cb089014917ea2b63e59a05dbdc2a2d40cfcebba3362395d946528d60cb4` |
| `existing-temp-copies.txt` | `39cb91c24f26ca43ebee189c5bdcf0b8fe9a7037ff171b0d49cec1aec6a09f88` |
| `git-diff-check.txt` | `418a5c17f33c70e99b0cc0a07fce69191489cfedc94164bfa903785777c5bd4b` |
| `gradle-cache-evidence.txt` | `9642aaefdff9c1b350efb533348a57ffb94f8e7073f939fffad830de492fca9e` |
| `gradle-version.txt` | `222ee28142575b12400d3a5d6b2c40bcb25f404b6bc0900df74b324c7ede5cc5` |
| `head-vs-worktree-hashes.txt` | `e556ede142c2dd2c167c4a673b99e953ac09b505755ba5dbaa151d4ad08da485` |
| `identity.txt` | `8d79a1d00fc53ae9e9cbd63477a9ad0baa7a52e73164f3703e65c61da4e9e2fe` |
| `mapper-anchors.txt` | `6687fde102071ecbe457e3fe2943321c6bff0f370c0124074f86d8d04b34b7df` |
| `micro-patch-local-test-failure.log` | `1c9816559b701847cbe155030553d85ac0d98dd6e8e2717e8d9ed71d8903afa9` |
| `micro-patch.diff` | `7b110fddf84f3b138517d7f54132cbada2d1e24e1e9e1201d5771679608034ad` |
| `path-classification.txt` | `690c4f2d50b0df808d27513ce07f33aa7d6fc66ea0313ea394d577b91a159ff3` |
| `prior-corrective-8path.diff` | `83f707c6d44750476f0a6a31bd0a71f30655c885af2729c7e1997916ec17bacc` |
| `prior-corrective-8path.stat.txt` | `258d13e3bd00a135926229bda3e4b434bfae1cc2d030dfcf878f6a7e306d2cef` |
| `prior-corrective-no-init-failure.log` | `6eae0871e763ce8812b67511eabb214783a4e45aaeeea1ca2f4d26bb8c8ea7d3` |
| `prior-corrective-with-init-success.log` | `808f6e6fa46369dd065716615a9a1be60c3841fe9bbe1223b8d4261eb52c3fda` |
| `prior-test-evidence-staleness.txt` | `f0ad209483a54e7196939494c094255b7e61421ccafd3d379cc2bbf35bf899d0` |
| `settings-head.gradle` | `234d51d6433b2d926cc8668a8c4fc5f839352c39de1439b8b5bb6c1708e861e1` |
| `settings-restoration-options.txt` | `29a19f7e82c5189d94db5a306a9998fb46567ab5546bf68e199ca845efca8d2f` |
| `settings-worktree.gradle` | `4b919cfffaed71637d1ee9c89f21f4247273e14eb8433afdcd0672eba906b41f` |
| `settings.diff` | `b830b45c0e36f80cae501fc09048befe425fe345fb37a046701a91c459b4ba33` |
| `status.txt` | `343e73a969ff93556909c00d906c2e8bbf693697beabc92e9fafb81daf0893e0` |
| `unreconciled-refs-head.txt` | `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` |
| `unreconciled-refs-worktree.txt` | `d32c1fd14575c73dffa823ac19a6746489759907e03b70419fb8e46bb4951e2e` |
| `verification-command-candidates.txt` | `6553d38051de7634e36f90c19d27dc625124e64f5ffad689ac66aec1279415a8` |
| `w5types-head-excerpt.txt` | `6bffe0da30d90b126033cd18d066b7ff25e5f1b4e0464f4960e867bfef5b51e5` |
| `w5types-worktree-excerpt.txt` | `1332f8f53d0868e9580d3b51d28e3267e2868d42795c54fc3447fac0c5838549` |
| `w5types.diff` | `5e5576caf9619d8e67b0d074c238f973592154cc188f32a4b312bf3cd7e96fe0` |
| `workflows.txt` | `593f6b3eebef2c533b04c8b70d97ed57d6bc09fbd88af77990a1a45b54d51703` |

`REPORT.md` is checksummed separately in `REPORT.sha256` to avoid a self-referential
hash inside the report.

---

## Appendix C — Key commands and exit codes

The complete command log is in `commands.txt`. Key results:

| Command | Exit | Result |
|---|---:|---|
| `git rev-parse --abbrev-ref HEAD` | 0 | `verification/ced-p0-authority` |
| `git rev-parse HEAD` | 0 | `06bb4ac287973dd3ca6bdab1eeb85714b4cb97c2` |
| `git status --porcelain=v1` | 0 | 40 M, 1 D, 33 ??; porcelain SHA-256 `343e73a9...` |
| `git diff --cached --name-only` | 0 | empty; 0 staged |
| `git show HEAD:settings.gradle` | 0 | correct `pluginManagement`/`dependencyResolutionManagement` |
| `git diff -- settings.gradle` | 0 | removes 17 lines; only `include ':app'` remains |
| `git grep -n 'UNRECONCILED' HEAD -- ':!evidence'` | 1 | no matches |
| `git grep -n 'UNRECONCILED' -- ':!evidence'` | 0 | 8 references in Mapper/W5Types/tests |
| `git log --all -S'UNRECONCILED' -- app/src/main/java/com/vantedge/app/w5/scoring/W5Types.kt` | 0 | empty; no commit contains it |
| `git diff --check -- <4 corrective paths>` | 0 | clean |
| `/root/tools/gradle-9.6.1/bin/gradle --version` | 0 | Gradle 9.6.1, Kotlin 2.3.21, JVM 17.0.20.1 |
| prior micro-patch local test attempt | 1 | plugin `com.android.application:9.3.1` not found; searched only Gradle Central Plugin Repository |

No Gradle test/build command was executed during this audit.

---

## Appendix D — Prior-corrective evidence used

The prior corrective report and artifacts establish the 8-path boundary and the
init-script mechanism:

- `/root/vantedge-canonical/evidence/VANTEDGE-R2-CORRECTIVE-UNKNOWN-EVIDENCE-INTEGRITY-01/REPORT.md`
- `/tmp/r2cuei/init.gradle`
- `/tmp/r2cuei/final-authorized.diff` (8 files, 408 insertions, 28 deletions; SHA-256
  `83f707c6d44750476f0a6a31bd0a71f30655c885af2729c7e1997916ec17bacc`)
- `/tmp/r2cuei/test3.log` and `/tmp/r2cuei/test4.log` (prior corrective successful runs)
- `/tmp/r2cuei/test.log` (prior corrective failure without init script)

These are historical evidence. They do not verify the current micro-patch.
