# RECOVERY_AUDIT_REPORT

**Project:** VantEdge 3.0  
**Audit Date:** 2026-06-15  
**Scope:** 328 `.kt` files in `app/src/main/java/com/vantedge/app/`  
**Build:** AGP 8.2.2, Kotlin 1.9.22, KAPT, Room 2.6.1, Navigation Compose, Jetpack Compose BOM 2024.02.00

---

## EXECUTIVE SUMMARY

| Metric | Value |
|--------|-------|
| Total `.kt` files in source tree | 328 |
| Authentic Kotlin source (no Java syntax) | 69 |
| Decompiled KAPT stubs with Java syntax | 259 |
| Files with package/directory mismatches | 3 + 4 |
| Duplicate class groups (stub vs source) | 3+ |
| Path anomalies | 2 (space in filename, trailing-space dir) |
| Missing real source (stub-only) | ~230 files |

**CRITICAL:** 259 files (79%) are decompiled KAPT stubs containing Java-style syntax (`public final class`, `import ...;`, `import kotlin.Metadata`, `DefaultConstructorMarker`) in `.kt` files. The Kotlin compiler will reject every one. **The project cannot compile.**

**Root cause:** The workspace was reconstructed from decompiled APK bytecode and/or KAPT intermediate artifacts, not from original source. The real source files for the engine layer, network layer, most of the storage/viewmodel/model layers are absent.

---

## SECTION A — Missing Symbols

### A.1 `com.vantedge.app.data.viewmodel.ExtractionState` (BUILD BLOCKER)

**File:** `ui/screens/ExtractingScreen.kt:21`  
**Import:** `import com.vantedge.app.data.viewmodel.ExtractionState`

This class **does not exist**. `ExtractingScreen` uses subclasses `ExtractionState.Failure`, `ExtractionState.Extracting`, `ExtractionState.Retrying` which match the sealed class **`OnboardingExtractionState`** (defined inside `OnboardingViewModel.kt`).

**Fix:** Change import to `com.vantedge.app.data.viewmodel.OnboardingExtractionState` and update all `ExtractionState` references to `OnboardingExtractionState` throughout the file.

---

## SECTION B — Broken References

### B.1 All engine imports in `Navigation.kt` resolve to stubs

| Import | Resolves to | Status |
|--------|-------------|--------|
| `com.vantedge.app.data.engine.CompatibilityEngine` | `data/engine/CompatibilityEngine.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.engine.GeneratorEngine` | `data/engine/GeneratorEngine.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.engine.ProfileExtractionEngine` | `data/engine/ProfileExtractionEngine.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.network.GeminiService` | `data/network/GeminiService.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.storage.CompatibilityStore` | `data/storage/CompatibilityStore.kt` | CLEAN |
| `com.vantedge.app.data.storage.HistoryStore` | `data/storage/HistoryStore.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.storage.OnboardingDraftStore` | `data/storage/OnboardingDraftStore.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.storage.UserPreferences` | `data/storage/UserPreferences.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.storage.VantEdgeDatabase` | `data/storage/VantEdgeDatabase.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.viewmodel.CycleViewModel` | `data/viewmodel/CycleViewModel.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.viewmodel.OnboardingViewModel` | `data/viewmodel/OnboardingViewModel.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.viewmodel.OnboardingViewModelFactory` | `data/viewmodel/OnboardingViewModelFactory.kt` | STUB (Java syntax) |
| `com.vantedge.app.data.viewmodel.CompatibilityViewModel` | `data/viewmodel/CompatibilityViewModel.kt` | CLEAN |
| `com.vantedge.app.domain.OnboardingCommitService` | `domain/OnboardingCommitService.kt` | STUB |
| `com.vantedge.app.domain.OptimizationOrchestrator` | `domain/OptimizationOrchestrator.kt` | STUB |
| `com.vantedge.app.util.TelemetryCollector` | `util/TelemetryCollector.kt` | STUB |

Every stub import will cause a Kotlin compilation failure. After purging stubs, these references will become unresolved.

### B.2 `DeadlineNotificationScheduler.kt` — unresolved Material3 import

**File:** `notifications/DeadlineNotificationScheduler.kt:4`  
**Import:** `import androidx.compose.material3.CalendarModelKt`

`CalendarModelKt` is not a public API in `androidx.compose.material3` as of compose-bom:2024.02.00. This file is a decompiled stub.

### B.3 `MainActivity.kt` — decompiled stub

The real `MainActivity.kt` source was lost. The current file is a 34-line decompiled stub with Java syntax. It references `PDFBoxResourceLoader.init()` which requires the `pdfbox-android` dependency (present in `build.gradle`), but the actual activity composition logic may be incomplete or incorrect.

---

## SECTION C — Package Mismatches

### C.1 `data/domain/` files declare wrong package

Three clean Kotlin source files are physically in `data/domain/` but declare `package com.vantedge.app.domain` (missing `data.`):

| File | Declared package | Correct package (by directory) |
|------|------------------|-------------------------------|
| `data/domain/OnboardingCommitService.kt` | `com.vantedge.app.domain` | `com.vantedge.app.data.domain` |
| `data/domain/OptimizationOrchestrator.kt` | `com.vantedge.app.domain` | `com.vantedge.app.data.domain` |
| `data/domain/PipelineStep.kt` | `com.vantedge.app.domain` | `com.vantedge.app.data.domain` |

The Kotlin compiler will report errors for these because the directory path `.../data/domain/` does not match the package `com.vantedge.app.domain`. Additionally, stub versions of these classes exist in the correct `domain/` directory, creating duplicates.

### C.2 Four model files use legacy `com.vantedge.model` namespace

| File | Declared package | Expected package |
|------|------------------|-----------------|
| `data/model/AtsResult.kt` | `com.vantedge.model` | `com.vantedge.app.data.model` |
| `data/model/DocumentType.kt` | `com.vantedge.model` | `com.vantedge.app.data.model` |
| `data/model/ScoreInterpretation.kt` | `com.vantedge.model` | `com.vantedge.app.data.model` |
| `data/model/VantageScoreResults.kt` | `com.vantedge.model` | `com.vantedge.app.data.model` |

These are clean Kotlin files from a previous version of the app where the namespace was `com.vantedge.model`. They must be updated to `com.vantedge.app.data.model` or every import site must use the old namespace.

Note: `AtsResult` at `com.vantedge.model` is distinct from `data/engine/AtsEngine.kt` which defines `data class AtsResult` in `com.vantedge.app.data.engine`. These are different classes with the same name in different packages — confusing but technically valid. The model version should be preferred for cross-module access.

### C.3 `data/engine/ATSEngine.kt` (upper case) vs `data/engine/AtsEngine.kt` (camelCase)

Two files exist:
- `ATSEngine.kt` — STUB (Java syntax, `package com.vantedge.app.data.engine`)
- `AtsEngine.kt` — CLEAN (Kotlin source, defines `data class AtsResult`)

`ATSEngine.kt` defines `class ATSEngine` while `AtsEngine.kt` defines `data class AtsResult`. No direct class conflict, but the naming confuses the package's purpose.

### C.4 Orphan directory: `notifications ` (trailing space)

A directory `notifications ` (with trailing U+0020 SPACE) exists at:
`app/src/main/java/com/vantedge/app/notifications /DeadlineNotificationSchedular.kt`

This file declares `package com.vantedge.app.notifications` but resides in `notifications /` (with space). The correctly-located equivalent exists at `notifications/DeadlineNotificationScheduler.kt` (correct spelling, correct path). The `Schedular` variant is an orphan.

### C.5 Space in filename: `util/Telemetry collector.kt`

File `util/Telemetry collector.kt` (with space in filename) declares `package com.vantedge.app.util`. Kotlin/Gradle may skip this file due to the space. The correctly-named `util/TelemetryCollector.kt` is a decompiled stub. After stub deletion, the spaced file should be renamed.

---

## SECTION D — Duplicate Classes

### D.1 `data/domain/` vs `domain/` — three duplicate class sets

| Class | Clean source at | Stub at |
|-------|-----------------|---------|
| `com.vantedge.app.domain.OnboardingCommitService` | `data/domain/OnboardingCommitService.kt` | `domain/OnboardingCommitService.kt` |
| `com.vantedge.app.domain.OptimizationOrchestrator` | `data/domain/OptimizationOrchestrator.kt` | `domain/OptimizationOrchestrator.kt` + 6 lambda variants |
| `com.vantedge.app.domain.PipelineStep` | `data/domain/PipelineStep.kt` | `domain/PipelineStep.kt` |

Both sets declare the same package. If both exist at compile time, the Kotlin compiler will report `Duplicate class` errors. This also applies to generated lambda classes (e.g., `OptimizationOrchestrator$applyDesign$1.kt`).

### D.2 KAPT stub contamination

Standard KAPT generates `.java` stubs in `build/tmp/kapt3/stubs/debug/`. The 259 decompiled `.kt` files in `src/main/java/` will conflict with the KAPT-generated `.java` stubs during kapt processing. Room entities, DAOs, and ViewModels are all affected.

### D.3 Generated `BuildConfig.kt` and `R.kt` in source tree

`app/src/main/java/com/vantedge/app/BuildConfig.kt` and `app/src/main/java/com/vantedge/app/R.kt` are build-generated files that should NOT be in the source tree. Gradle's `buildConfig = true` generates `BuildConfig` in `build/generated/source/buildConfig/`. These decompiled copies will cause `Duplicate class` errors.

---

## SECTION E — Build Blockers

### E.1 [CRITICAL] 259 decompiled stubs prevent compilation

Every file containing `public final class`, `import ...;` (semicolons), `import kotlin.Metadata`, or `DefaultConstructorMarker` will cause the Kotlin compiler to report syntax errors. These are Java syntax constructs in `.kt` files.

**Counts by directory:**

| Directory | Total | Stubs | Clean | Notes |
|-----------|-------|-------|-------|-------|
| Root (`app/`) | 3 | 2 | 0 | `MainActivity.kt`, `BuildConfig.kt`, `R.kt` all stubs |
| `data/domain/` | 5 | 2 | 3 | 3 clean files have package mismatch |
| `data/engine/` | 25 | 23 | 1 + 1 | `AtsEngine.kt` clean; `ATSResults.kt` empty; rest stubs |
| `data/infrastructure/` | 1 | 1 | 0 | `MediaStoreExporter.kt` stub |
| `data/model/` | 48 | 43 | 5 | 4 of 5 clean use wrong namespace |
| `data/network/` | 15 | 15 | 0 | All stubs |
| `data/storage/` | 30 | 29 | 1 | `CompatibilityStore.kt` clean |
| `data/viewmodel/` | 58 | 54 | 4 | `CompatibilityViewModel.kt`, `GeneratorUiState.kt`, `GeneratorViewModel.kt`, `HistoryViewModel.kt` clean |
| `domain/` | 8 | 8 | 0 | All stubs (clean versions in `data/domain/`) |
| `navigation/` | 6 | 5 | 1 | `Navigation.kt` clean; `NavigationKt.kt` is a 1791-line stub |
| `notifications/` | 2 | 2 | 0 | Both stubs (orphan in space-dir ALSO a stub) |
| `ui/screens/` | 124 | 68 | 56 | All real screens are clean; `*Kt.kt` and `*Kt$*.kt` files are stubs |
| `ui/theme/` | 7 | 4 | 3 | `Color.kt`, `Theme.kt`, `Typography.kt` clean |
| `util/` | 9 | 7 | 2 | `FileUtil.kt` and `Telemetry collector.kt` (spaced) clean |
| **Total** | **328** | **259** | **69** | |

### E.2 Gradle properties contain conflicting duplicates

`gradle.properties` has duplicate definitions:
- `org.gradle.jvmargs` on lines 7 AND 9
- `org.gradle.caching=true` on line 10 vs `false` on line 6

The later values win, making lines 6-7 dead configuration.

### E.3 Missing `local.properties`

`local.properties` (containing `OPENROUTER_API_KEY`) doesn't exist. The build will not fail (defaults to empty string), but the API key field in `BuildConfig` will be blank, making all AI calls fail at runtime.

### E.4 Room kapt processing on stubs

With `kapt` enabled for Room (2.6.1) and only stub versions of `CycleDao`, `OnboardingDraftDao`, `VantEdgeDatabase` in the source tree, the annotation processor will read these stubs and likely produce incorrect or non-compilable `*_Impl.java` files.

---

## SECTION F — High-Risk Runtime Failures

### F.1 Decompiled engine methods throw `UnsupportedOperationException`

All engine stubs (`CompatibilityEngine`, `GeneratorEngine`, `CVTemplate`, `CareerEngine`, `GapAnalysisEngine`, `ScoreEngine`, `ATSEngine`) contain methods ending with:

```kotlin
throw new UnsupportedOperationException("Method not decompiled: ...")
```

These are decompilation artifacts from `jadx` or similar tools. Calling any method on these engine classes will crash at runtime.

### F.2 `GeminiService` and `AiGateway` — entire network layer broken

The two network entry points both have decompiled method bodies with `UnsupportedOperationException`. Every API call — profile extraction, job analysis, CV generation, cover letter generation — will throw.

### F.3 `ExtractionState` missing (see A.1)

If the stubs are purged but the import in `ExtractingScreen.kt` is not fixed, the UI screen for extraction will fail with an unresolved reference at compile time.

### F.4 `DocumentExportUseCase` — stub only

The document export use case exists only as a decompiled stub. Saving generated documents will fail.

### F.5 `CycleViewModel` (1087 lines) — stub only

The core pipeline ViewModel is 100% decompiled code. Its state management, coroutine flows, and pipeline orchestration logic are unreliable.

### F.6 Wrong-package model classes (see C.2)

Four model classes in `com.vantedge.model` will be incompatible with code that imports them from `com.vantedge.app.data.model`. Cross-package access will work (Kotlin allows it), but code completion and IDE navigation will be degraded. If these are ever moved to the correct package, all import sites must be updated.

---

## SECTION G — Recommended Sync Actions

### G.1 [IMMEDIATE] Delete all 259 decompiled stub files

```bash
# Remove Java-syntax stub files from source tree
grep -rl "public final class\|public abstract class\|import kotlin.Metadata\|DefaultConstructorMarker\|import.*;\$" \
  app/src/main/java/com/vantedge/app/ --include="*.kt" | grep -v "ComposableSingletons" | xargs rm
```

This leaves only the 69 authentic Kotlin source files. The project will still not compile because many imports point at deleted files, but the syntax-level errors will be gone.

### G.2 [HIGH] Fix three `data/domain/` package declarations

Edit these files to change `package com.vantedge.app.domain` to `package com.vantedge.app.data.domain`:
- `data/domain/OnboardingCommitService.kt`
- `data/domain/OptimizationOrchestrator.kt`
- `data/domain/PipelineStep.kt`

Alternatively move them to `domain/` and keep `package com.vantedge.app.domain`.

### G.3 [HIGH] Fix `ExtractingScreen.kt` import

Change `import com.vantedge.app.data.viewmodel.ExtractionState` to `import com.vantedge.app.data.viewmodel.OnboardingExtractionState` and update all usages.

### G.4 [HIGH] Rename or fix anomalous paths

```bash
# Remove spaced directory
rm -rf "app/src/main/java/com/vantedge/app/notifications /"

# Rename space-in-filename
mv "app/src/main/java/com/vantedge/app/util/Telemetry collector.kt" \
   app/src/main/java/com/vantedge/app/util/TelemetryCollector.kt
```

### G.5 [HIGH] Delete build-generated files from source tree

```bash
rm app/src/main/java/com/vantedge/app/BuildConfig.kt
rm app/src/main/java/com/vantedge/app/R.kt
rm app/src/main/java/com/vantedge/app/data/storage/CycleDao_Impl.kt
rm app/src/main/java/com/vantedge/app/data/storage/OnboardingDraftDao_Impl.kt
rm app/src/main/java/com/vantedge/app/data/storage/VantEdgeDatabase_Impl.kt
```

### G.6 [HIGH] Recover or rewrite lost source files (~230 files)

**Layers requiring full recovery (no clean source remains):**
- `data/engine/` — 23 of 25 files lost
- `data/network/` — all 15 files lost
- `data/storage/` — 29 of 30 files lost
- `data/viewmodel/` — 54 of 58 files lost
- `data/model/` — 43 of 48 files lost
- `domain/` — 5 of 8 files lost (3 available via `data/domain/`)
- `navigation/` — 5 of 6 files lost
- `notifications/` — 2 of 2 files lost
- `util/` — 7 of 9 files lost
- `data/infrastructure/` — 1 of 1 file lost

**Key files that must be recovered first (build deps):**
1. `data/network/GeminiService.kt` — core AI client
2. `data/network/AiGateway.kt` — AI abstraction
3. `data/engine/CompatibilityEngine.kt` — analysis engine
4. `data/engine/GeneratorEngine.kt` — document generation
5. `data/storage/VantEdgeDatabase.kt` — Room database
6. `data/storage/CycleDao.kt` — Room DAO
7. `data/storage/HistoryStore.kt` — cycle persistence
8. `data/viewmodel/CycleViewModel.kt` — pipeline ViewModel
9. `data/viewmodel/OnboardingViewModel.kt` — onboarding VM
10. `data/model/GenerationCycle.kt` — core model class
11. `data/model/CycleState.kt` — core model class
12. `domain/PipelineTrace.kt` — pipeline step tracking

The decompiled stubs can serve as *signature reference* for method contracts and type signatures, but must NOT be compiled as-is.

### G.7 [MEDIUM] Fix model package namespace

Update the four model files from `com.vantedge.model` to `com.vantedge.app.data.model`:
- `data/model/AtsResult.kt`
- `data/model/DocumentType.kt`
- `data/model/ScoreInterpretation.kt`
- `data/model/VantageScoreResults.kt`

### G.8 [MEDIUM] Fix `gradle.properties`

Remove or comment out conflicting lines 6-7. Keep:
```
org.gradle.jvmargs=-Xmx1536m -XX:MaxMetaspaceSize=384m -Dfile.encoding=UTF-8
org.gradle.caching=true
```

### G.9 [MEDIUM] Delete zero-byte / empty files

- `data/engine/ATSResults.kt` (0 bytes)

### G.10 [LOW] Delete Compose compiler lambda files

After the main stub purge, the following generated-lambda files will likely still remain and should also be removed:
- All `*Kt\$*.kt` files in `ui/screens/` (e.g., `JobInputScreenKt$JobInputScreen$1.kt`)
- These are Compose compiler outputs for `@Composable` lambdas with `$default` inline expansions

### G.11 [LOW] Create `local.properties`

```properties
sdk.dir=/path/to/android/sdk
OPENROUTER_API_KEY=your_key_here
```

### G.12 Rebuild sequence

```bash
./gradlew clean                 # Remove stale build outputs
# After G.1-G.11 fixes:
./gradlew assembleDebug          # First-pass: expect ~230 unresolved reference errors
# Fix errors by recovering/rewriting source files iteratively
```

---

## APPENDIX: Authentic Kotlin Source Files (69 files)

Files confirmed as hand-written Kotlin (no Java syntax, no `@Metadata`/`Intrinsics`/`DefaultConstructorMarker`):

| Directory | Files |
|-----------|-------|
| `data/domain/` | `OnboardingCommitService.kt`, `OptimizationOrchestrator.kt`, `PipelineStep.kt` (package mismatch) |
| `data/engine/` | `AtsEngine.kt`, `ATSResults.kt` (0 bytes) |
| `data/model/` | `ApplicationRecord.kt`, `AtsResult.kt`, `DocumentType.kt`, `ScoreInterpretation.kt`, `VantageScoreResults.kt` (4 wrong package) |
| `data/storage/` | `CompatibilityStore.kt` |
| `data/viewmodel/` | `CompatibilityViewModel.kt`, `GeneratorUiState.kt`, `GeneratorViewModel.kt`, `HistoryViewModel.kt` |
| `navigation/` | `Navigation.kt` |
| `ui/screens/` | 56 clean files (all main `*Screen.kt` composables + `*ScreenKt.kt` main files + `ComposableSingletons` + `CVDesign.kt`, `AnalysisScreenMode.kt`, `AppColors.kt`, `AppColorsKt.kt`, `ColorScheme.kt`, `JobInputStage.kt`, `LoadingMode.kt`, `ResultScreenMode.kt`, `TabInfo.kt`) |
| `ui/theme/` | `Color.kt`, `Theme.kt`, `Typography.kt` |
| `util/` | `FileUtil.kt`, `Telemetry collector.kt` (space in name) |
| Root | — (none: `MainActivity.kt`, `BuildConfig.kt`, `R.kt` are all stubs) |

---

## END OF RECOVERY_AUDIT_REPORT
