# CURRENT STATE — VantEdge Forensic Baseline

**Generated:** 2026-06-14
**Git commit:** b79aa26 (2026-06-03)
**Branch:** main
**Git status:** 1 file modified (gradle.properties), 1 file untracked (gradle.properties.backup)

---

## 1. COMPLETE FILE TREE (source only, excluding .git / jdk / .gradle / build artifacts)

```
VantEdge3.0/
├── .gitattributes
├── .gitignore
├── OnboardingStage.EditingProfile        (empty sentinel)
├── OnboardingStage.UploadingCv           (empty sentinel)
├── build.gradle                          (root)
├── build_log.txt                         (empty)
├── claude.md
├── gradle.properties                     (MODIFIED vs commit)
├── gradle.properties.backup              (UNTRACKED)
├── gradlew
├── gradlew.bat
├── settings.gradle
│
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties     (Gradle 8.4)
│
├── app/
│   ├── .gitignore
│   ├── build.gradle
│   ├── proguard-rules.pro
│   │
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   │
│   │   │   ├── java/com/vantedge/app/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   │
│   │   │   │   ├── data/
│   │   │   │   │   ├── domain/
│   │   │   │   │   │   ├── OnboardingCommitService.kt
│   │   │   │   │   │   ├── OptimizationOrchestrator.kt
│   │   │   │   │   │   └── PipelineStep.kt
│   │   │   │   │   │
│   │   │   │   │   ├── engine/
│   │   │   │   │   │   ├── ATSResults.kt              (empty)
│   │   │   │   │   │   ├── AtsEngine.kt
│   │   │   │   │   │   ├── CVTemplate.kt
│   │   │   │   │   │   ├── CareerEngine.kt
│   │   │   │   │   │   ├── CompatibilityEngine.kt
│   │   │   │   │   │   ├── DocxBuilder.kt
│   │   │   │   │   │   ├── GapAnalysisEngine.kt
│   │   │   │   │   │   ├── GeneratorEngine.kt
│   │   │   │   │   │   ├── ProfileExtractionEngine.kt
│   │   │   │   │   │   └── ScoreEngine.kt
│   │   │   │   │   │
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── AcquisitionMode.kt
│   │   │   │   │   │   ├── ApplicationRecord.kt
│   │   │   │   │   │   ├── ApplicationStatus.kt
│   │   │   │   │   │   ├── AtsResult.kt
│   │   │   │   │   │   ├── Certification.kt
│   │   │   │   │   │   ├── CompatibilityRecord.kt
│   │   │   │   │   │   ├── DocumentType.kt
│   │   │   │   │   │   ├── Experience.kt
│   │   │   │   │   │   ├── GapAnalysisResult.kt
│   │   │   │   │   │   ├── GapSeverity.kt
│   │   │   │   │   │   ├── GeneratedDocument.kt
│   │   │   │   │   │   ├── GenerationCycle.kt
│   │   │   │   │   │   ├── JobData.kt
│   │   │   │   │   │   ├── LearningRecommendation.kt
│   │   │   │   │   │   ├── OnboardingDraft.kt
│   │   │   │   │   │   ├── OnboardingStage.kt
│   │   │   │   │   │   ├── ScoreInterpretation.kt
│   │   │   │   │   │   ├── SkillGap.kt
│   │   │   │   │   │   ├── StructuredProfileExtraction.kt
│   │   │   │   │   │   ├── TelemetryRecord.kt
│   │   │   │   │   │   ├── UserDecisionEvent.kt
│   │   │   │   │   │   ├── UserProfile.kt
│   │   │   │   │   │   ├── VantageScoreResults.kt
│   │   │   │   │   │   └── WorkEntry.kt
│   │   │   │   │   │
│   │   │   │   │   ├── network/
│   │   │   │   │   │   ├── GeminiService.kt
│   │   │   │   │   │   └── RequestThrottleManager.kt
│   │   │   │   │   │
│   │   │   │   │   ├── storage/
│   │   │   │   │   │   ├── CompatibilityStore.kt
│   │   │   │   │   │   ├── CycleDao.kt
│   │   │   │   │   │   ├── CycleEntity.kt
│   │   │   │   │   │   ├── CycleStateSerializer.kt
│   │   │   │   │   │   ├── HistoryStore.kt
│   │   │   │   │   │   ├── OnboardingDraftDao.kt
│   │   │   │   │   │   ├── OnboardingDraftEntity.kt
│   │   │   │   │   │   ├── OnboardingDraftStore.kt
│   │   │   │   │   │   ├── UserPreferences.kt
│   │   │   │   │   │   └── VantEdgeDatabase.kt
│   │   │   │   │   │
│   │   │   │   │   └── viewmodel/
│   │   │   │   │       ├── CompatibilityViewModel.kt
│   │   │   │   │       ├── CycleViewModel.kt
│   │   │   │   │       ├── GeneratorUiState.kt
│   │   │   │   │       ├── GeneratorViewModel.kt
│   │   │   │   │       ├── HistoryViewModel.kt
│   │   │   │   │       ├── OnboardingViewModel.kt
│   │   │   │   │       └── OnboardingViewModelFactory.kt
│   │   │   │   │
│   │   │   │   ├── navigation/
│   │   │   │   │   └── Navigation.kt
│   │   │   │   │
│   │   │   │   ├── notifications /
│   │   │   │   │   └── DeadlineNotificationSchedular.kt
│   │   │   │   │
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── AdvancedAnalysisScreen.kt
│   │   │   │   │   │   ├── AnalysisResultScreen.kt
│   │   │   │   │   │   ├── AppColors.kt
│   │   │   │   │   │   ├── CVDesignScreen.kt
│   │   │   │   │   │   ├── CVGeneratorScreen.kt
│   │   │   │   │   │   ├── ChoosePathScreen.kt
│   │   │   │   │   │   ├── ColorSchemeScreen.kt
│   │   │   │   │   │   ├── CompatibilityInputScreen.kt
│   │   │   │   │   │   ├── CompatibilityResultScreen.kt
│   │   │   │   │   │   ├── CoverLetterScreen.kt
│   │   │   │   │   │   ├── CycleHistoryScreen.kt
│   │   │   │   │   │   ├── DashboardScreen.kt
│   │   │   │   │   │   ├── DocumentPreviewScreen.kt
│   │   │   │   │   │   ├── EditProfileScreen.kt
│   │   │   │   │   │   ├── EligibilityCheckerScreen.kt
│   │   │   │   │   │   ├── ErrorScreen.kt
│   │   │   │   │   │   ├── ExtractingScreen.kt
│   │   │   │   │   │   ├── FinalReviewScreen.kt
│   │   │   │   │   │   ├── HistoryScreen.kt
│   │   │   │   │   │   ├── JobInputScreen.kt
│   │   │   │   │   │   ├── LoadingScreen.kt
│   │   │   │   │   │   ├── PipelineLoadingScreen.kt
│   │   │   │   │   │   ├── PlaceHolderScreen.kt
│   │   │   │   │   │   ├── PostDownloadScreen.kt
│   │   │   │   │   │   ├── ResultScreen.kt
│   │   │   │   │   │   ├── ReviewExtractionScreen.kt
│   │   │   │   │   │   └── WelcomeScreen.kt
│   │   │   │   │   │
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── AppColors.kt
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       └── Typography.kt
│   │   │   │   │
│   │   │   │   └── util/
│   │   │   │       ├── FailureLogBundle.kt
│   │   │   │       ├── FileUtil.kt
│   │   │   │       ├── LogDumper.kt
│   │   │   │       └── Telemetry collector.kt
│   │   │   │
│   │   │   └── res/
│   │   │       ├── drawable/ic_launcher_background.xml
│   │   │       ├── drawable-v24/ic_launcher_foreground.xml
│   │   │       ├── layout/activity_main.xml
│   │   │       ├── mipmap-* (launcher icons, 5 densities)
│   │   │       ├── values/colors.xml, strings.xml, theme.xml
│   │   │       └── xml/file_paths.xml
│   │   │
│   │   └── test/
│   │       └── java/com/vantedge/app/ExampleUnitTest.kt
│   │
│   └── androidTest/
│       └── java/com/vantedge/app/ExampleInstrumentedTest.kt
```

---

## 2. PACKAGE TREE

| Package | Directory | Files |
|---|---|---|
| `com.vantedge.app` | `app/src/main/java/.../app/` | MainActivity.kt |
| `com.vantedge.app.domain` | `.../data/domain/` | OnboardingCommitService, OptimizationOrchestrator, PipelineStep |
| `com.vantedge.app.data.engine` | `.../data/engine/` | AtsEngine, CVTemplate, CareerEngine, CompatibilityEngine, DocxBuilder, GapAnalysisEngine, GeneratorEngine, ProfileExtractionEngine, ScoreEngine |
| `com.vantedge.app.data.model` | `.../data/model/` | 18 model files |
| `com.vantedge.model` | `.../data/model/` | AtsResult, DocumentType, ScoreInterpretation, VantageScoreResults |
| `com.vantedge.app.data.network` | `.../data/network/` | GeminiService, RequestThrottleManager |
| `com.vantedge.app.data.storage` | `.../data/storage/` | 10 storage files |
| `com.vantedge.app.data.viewmodel` | `.../data/viewmodel/` | 7 viewmodel files |
| `com.vantedge.app.navigation` | `.../navigation/` | Navigation.kt |
| `com.vantedge.app.notifications` | `.../notifications /` | DeadlineNotificationSchedular |
| `com.vantedge.app.ui.screens` | `.../ui/screens/` | 26 screen files |
| `com.vantedge.app.ui.theme` | `.../ui/theme/` | 4 theme files |
| `com.vantedge.app.util` | `.../util/` | 4 utility files |

**Namespace anomaly:** 4 model files use `com.vantedge.model` (without `.app`) while physically located in the data/model directory. This is a package declaration inconsistency.

---

## 3. VIEWMODEL INVENTORY

| ViewModel | File | Lines | State sealed classes | Key responsibilities |
|---|---|---|---|---|
| `OnboardingViewModel` | data/viewmodel/OnboardingViewModel.kt | 371 | `ExtractionState` (Idle/Extracting/Success/Failure/Retrying) | Profile extraction, gate 0 validation, draft management, skill editing |
| `CycleViewModel` | data/viewmodel/CycleViewModel.kt | 358 | `CycleUiState` (Idle/Loading/AnalysisDone/GenerationReady/Success/Error), `CycleNavEvent`, `CycleStage` | Pipeline orchestration, design application, improvement cycles |
| `CompatibilityViewModel` | data/viewmodel/CompatibilityViewModel.kt | 161 | `CompatibilityUiState` (Idle/Loading/Success/Error) | Compatibility analysis, field extraction, profile editing |
| `GeneratorViewModel` | data/viewmodel/GeneratorViewModel.kt | 181 | `GeneratorUiState` (Idle/Loading/Success/Error) | CV/CL generation, docx building |
| `HistoryViewModel` | data/viewmodel/HistoryViewModel.kt | 51 | — | Cycle history listing |
| `OnboardingViewModelFactory` | data/viewmodel/OnboardingViewModelFactory.kt | 28 | — | Factory for OnboardingViewModel |
| `GeneratorViewModel` constructor | — | — | — | Takes GeminiService, HistoryStore, UserPreferences |

**Total: 6 ViewModels + 1 Factory**

---

## 4. SERVICE INVENTORY

| Service | File | Lines | Type |
|---|---|---|---|
| `GeminiService` | data/network/GeminiService.kt | 233 | AI API client (OpenRouter/Gemini) |
| `OnboardingCommitService` | data/domain/OnboardingCommitService.kt | 57 | Domain commit service |
| `RequestThrottleManager` | data/network/RequestThrottleManager.kt | 27 | Rate limiter (object) |
| `DeadlineNotificationSchedular` | notifications /DeadlineNotificationSchedular.kt | 119 | Notification scheduling |

**Note:** No `android.app.Service` subclasses declared in manifest — only one Activity (MainActivity). Notifications uses WorkManager (manifest shows custom WorkManagerInitializer removed).

**Missing:** No `service/` directory exists. No dedicated repository layer.

---

## 5. ENGINE INVENTORY

| Engine | File | Lines | Type | Role |
|---|---|---|---|---|
| `ProfileExtractionEngine` | data/engine/ProfileExtractionEngine.kt | 757 | Class | Core extraction with gate validation (Gate0Reason, Gate0Result, ExtractionMode) |
| `CVTemplate` | data/engine/CVTemplate.kt | 343 | Object | CV template definitions (modern, creative, executive, minimalist) |
| `CompatibilityEngine` | data/engine/CompatibilityEngine.kt | 265 | Class | Job compatibility analysis |
| `GeneratorEngine` | data/engine/GeneratorEngine.kt | 234 | Class | CV/CL/cover letter generation via AI |
| `DocxBuilder` | data/engine/DocxBuilder.kt | 111 | Object | DOCX document building |
| `CareerEngine` | data/engine/CareerEngine.kt | 61 | Object | Career path recommendations |
| `ScoreEngine` | data/engine/ScoreEngine.kt | 31 | Object | Scoring logic |
| `AtsEngine` | data/engine/AtsEngine.kt | 22 | Object | ATS keyword scanning |
| `GapAnalysisEngine` | data/engine/GapAnalysisEngine.kt | 21 | Object | Gap analysis |
| `ATSResults.kt` | data/engine/ATSResults.kt | **0** | — | Empty stub |

**Total: 9 engines + 1 empty stub**

---

## 6. REPOSITORY / STORAGE INVENTORY

| Class | File | Type | Notes |
|---|---|---|---|
| `VantEdgeDatabase` | data/storage/VantEdgeDatabase.kt | Room DB abstract class | Entities: CycleEntity, OnboardingDraftEntity |
| `CycleDao` | data/storage/CycleDao.kt | Room DAO interface | CRUD for cycles |
| `OnboardingDraftDao` | data/storage/OnboardingDraftDao.kt | Room DAO interface | CRUD for drafts |
| `CycleEntity` | data/storage/CycleEntity.kt | Room entity | Cycle persistence |
| `OnboardingDraftEntity` | data/storage/OnboardingDraftEntity.kt | Room entity | Draft persistence |
| `HistoryStore` | data/storage/HistoryStore.kt | Class | Business logic for cycle history |
| `OnboardingDraftStore` | data/storage/OnboardingDraftStore.kt | Class | Draft management |
| `CompatibilityStore` | data/storage/CompatibilityStore.kt | Class | Compatibility record storage |
| `UserPreferences` | data/storage/UserPreferences.kt | Class | DataStore preferences |
| `CycleStateSerializer` | data/storage/CycleStateSerializer.kt | Class | Gson serializer for CycleState |

**Note:** No dedicated `Repository` layer exists. Storage classes act as repositories. No use of Android Architecture Repository pattern.

---

## 7. AI PROMPT INVENTORY

| Engine | File | Prompt usage |
|---|---|---|
| `GeminiService` | data/network/GeminiService.kt | Generic `generate()` and `tryModel()` with prompt/maxTokens/temperature params. No hardcoded system prompts. |
| `CompatibilityEngine` | data/engine/CompatibilityEngine.kt | `analyze()` constructs an analysis prompt; `extractJobFields()` constructs extraction prompt |
| `GeneratorEngine` | data/engine/GeneratorEngine.kt | `generateCv()`, `generateCoverLetter()`, `generateCvDocx()` — each constructs a prompt string. `extractJobFields()` extracts from job text. |
| `ProfileExtractionEngine` | data/engine/ProfileExtractionEngine.kt | `structureProfile()` constructs extraction prompt with structured output schema |

**All prompts are inline string templates within engine files** — no dedicated prompts/ directory, no prompt configuration files, no prompt versioning.

---

## 8. TELEMETRY INVENTORY

| Class | File | Lines | Role |
|---|---|---|---|
| `TelemetryRecord` | data/model/TelemetryRecord.kt | 27 | Data model for telemetry events |
| `Telemetry collector.kt` | util/Telemetry collector.kt | 203 | **UNUSED** — This file exists but is NOT referenced by any other source file (verified by grep). Contains TelemetryCollector class. |
| `LogDumper` | util/LogDumper.kt | 101 | Debug log dump utility |
| `FailureLogBundle` | util/FailureLogBundle.kt | 53 | Error reporting bundle |
| `UserDecisionEvent` | data/model/UserDecisionEvent.kt | 20 | User decision tracking model |

**Finding:** `Telemetry collector.kt` (203 lines) contains telemetry collection logic but is **orphaned** — zero references from any other file. It appears to be implemented but never wired into the application.

---

## 9. BUILD CONFIG STATE

| Property | Value |
|---|---|
| Gradle version | 8.4 (wrapper) |
| AGP version | 8.2.2 |
| Kotlin version | 1.9.22 |
| compileSdk / targetSdk | 34 |
| minSdk | 24 |
| Compose BOM | 2024.02.00 |
| Kotlin compiler extension | 1.5.8 |
| Java target | 17 |
| JDK on disk | jdk-17.0.19+10 (embedded, untracked) |
| JDK in git baseline | Not set (was `/opt/java/jdk-17.0.18+8` in original gradle.properties) |
| local.properties | **MISSING** (needed for OPENROUTER_API_KEY) |

---

## 10. SUMMARY STATISTICS

| Metric | Value |
|---|---|
| Total source .kt files | 95 |
| Total Kotlin LOC | 13,492 |
| Total declarations (class/interface/object/enum) | 62 |
| Total top-level functions | 33 |
| Total distinct packages | 12 |
| Content mismatch vs git | 1 file (gradle.properties) |
| Files on disk not in git | 1 file (gradle.properties.backup) |
| Committed files missing from disk | 0 |
| Empty committed files | 4 (ATSResults.kt, build_log.txt, 2 OnboardingStage sentinels) |
| Orphaned files (no incoming references) | Telemetry collector.kt |
| Path anomalies | 2 (trailing-space dir `notifications /`, space in `Telemetry collector.kt`) |
| Package declaration anomalies | 4 files in data/model/ use wrong package `com.vantedge.model` |
| Missing expected config | local.properties (API key) |
| /services directory | DOES NOT EXIST |
| Repository pattern | NOT USED (no separate repository layer) |
| Prompt management | NONE (prompts are inline strings) |
| Telemetry wiring | TelemetryRecord model exists, Telemetry collector.kt exists but is UNREFERENCED |
| AI governance | Gate 0 validation exists in ProfileExtractionEngine | Gate0Reason enum |
| Onboarding stages | 9 sealed subtypes defined + 2 empty sentinel files |
| Navigation | Single file (528 lines), composable-based routing |
