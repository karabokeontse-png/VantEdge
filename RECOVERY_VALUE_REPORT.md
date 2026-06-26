# RECOVERY VALUE REPORT

**Purpose**: Compare decompiled JADX artifacts against real Kotlin sources to determine if lost code can be recovered.

**Classification**: UNCHANGED | MINOR_DIFFERENCE | MAJOR_DIFFERENCE  
**Recovery Value**: HIGH (decompiled-only) | MEDIUM (extra code vs real source) | LOW (no useful addition)

---

## 1. CORE ENGINE FILES (Priority: HIGH)

### Decompiled-Only (NO real source exists — HIGH recovery value)

These files exist **only** as JADX decompiled artifacts. If deleted, all code is permanently lost.

| File | Lines | Size | Notes |
|------|-------|------|-------|
| `data/engine/ProfileExtractionEngine.kt` | 861 | 47,230 B | Largest engine, profile extraction logic |
| `data/engine/JobExtractionEngine.kt` | 547 | 30,255 B | Job extraction logic |
| `data/engine/CVTemplate.kt` | ~700+ | 38,785 B | CV template definitions |
| `data/engine/CompatibilityEngine.kt` | 142 | 6,443 B | Compatibility analysis logic |
| `data/engine/GeneratorEngine.kt` | 168 | 7,633 B | CV/cover letter generation |
| `data/engine/CareerEngine.kt` | 118 | 8,737 B | Career progression logic |
| `data/engine/DocxBuilder.kt` | 110 | 8,470 B | DOCX file builder |
| `data/engine/Gate0Result.kt` | 118 | 6,273 B | Gate 0 calibration result |
| `data/engine/Gate0Reason.kt` | 23 | 1,122 B | Gate 0 reason enum |
| `data/engine/ScoreEngine.kt` | 48 | 2,032 B | Scoring engine |
| `data/engine/ExtractionMode.kt` | 20 | 886 B | Extraction mode enum |
| `data/engine/GapAnalysisEngine.kt` | 38 | 2,185 B | Gap analysis logic |
| `data/engine/VantEdgeScoreResult.kt` | 55 | 3,482 B | Score result model |
| `data/infrastructure/MediaStoreExporter.kt` | 70 | 4,406 B | Media export to device storage |
| **TOATL** | **~3,000+** | **~163 KB** | |

**Verdict: HIGH recovery value** — These represent the core business logic of the app. All are reconstructable from decompiled code.

---

## 2. NETWORK LAYER (Priority: HIGH)

### Decompiled-Only (NO real source exists — HIGH recovery value)

| File | Lines | Size | Notes |
|------|-------|------|-------|
| `data/network/AiGateway.kt` | 135 | 7,251 B | AI gateway abstraction |
| `data/network/GeminiService.kt` | 400+ | 23,155 B | Gemini API integration |
| `data/network/RequestThrottleManager.kt` | 60 | 3,217 B | Rate limiting |
| `data/network/AiSystemInstructions_P1Kt.kt` | 80 | 4,345 B | System prompt definitions |
| Sub-lambdas (6 `$*.kt` files) | — | — | Compiler-generated; NO recovery value |

**Verdict: HIGH recovery value** (main files). Lambda files: NO recovery value.

---

## 3. DATA MODEL LAYER (Priority: MEDIUM)

### Decompiled-Only (most model classes — HIGH recovery value)

The following classes in `data/model/` are decompiled-only (no real source):

- `AcquisitionMode.kt`, `ApplicationStatus.kt`, `Certification.kt`, `CompatibilityRecord.kt`, `CompatibilityResult.kt`, `ConfidenceBreakdown.kt`, `CourseRecommendation.kt`, `CycleState.kt`, `DesignConfig.kt`, `DocumentFormat.kt`, `Experience.kt`, `ExtractedCertification.kt`, `ExtractedEducation.kt`, `ExtractedExperience.kt`, `ExtractedField.kt`, `ExtractedPersonalInfo.kt`, `ExtractionMetadata.kt`, `ExtractionSourceType.kt`, `GapAnalysisResult.kt`, `GapItem.kt`, `GapSeverity.kt`, `Gate0JobReason.kt`, `Gate0JobResult.kt`, `GeneratedDocument.kt`, `GenerationCycle.kt`, `GenerationMode.kt`, `JobData.kt`, `JobExtractionFailureReason.kt`, `JobExtractionMetrics.kt`, `JobExtractionResult.kt`, `JobSourceType.kt`, `LearningRecommendation.kt`, `OnboardingDraft.kt`, `OnboardingStage.kt`, `ProfileStats.kt`, `QualificationRatio.kt`, `RelevancyItem.kt`, `SkillGap.kt`, `StructuredProfileExtraction.kt`, `TelemetryRecord.kt`, `UserDecisionEvent.kt`, `UserProfile.kt`, `WorkEntry.kt`, `WorkExperience.kt`

**Verdict: HIGH recovery value** — 44 files, ~250 KB. All reconstructable from decompiled code.

### Real Sources (5 files — already present, no recovery needed)

| File | Package | Lines |
|------|---------|-------|
| `data/model/ApplicationRecord.kt` | `com.vantedge.app.data.model` | ~8 |
| `data/model/AtsResult.kt` | `com.vantedge.model` **⚠️ (mismatch)** | 7 |
| `data/model/DocumentType.kt` | `com.vantedge.model` **⚠️ (mismatch)** | 3 |
| `data/model/ScoreInterpretation.kt` | `com.vantedge.model` **⚠️ (mismatch)** | 3 |
| `data/model/VantageScoreResults.kt` | `com.vantedge.model` **⚠️ (mismatch)** | 4 |

**⚠️ Cross-package issue**: These 4 files declare `package com.vantedge.model` but reside in `com.vantedge.app.data.model` directory. They are real sources that exist in a wrong directory.

---

## 4. VIEWMODEL LAYER (Priority: MEDIUM)

### Decompiled-Only (HIGH recovery value)

| File | Lines | Size | Notes |
|------|-------|------|-------|
| `data/viewmodel/CycleViewModel.kt` | 1,087 | 64,343 B | Core ViewModel — main UI controller |
| `data/viewmodel/OnboardingViewModel.kt` | 437 | 27,420 B | Onboarding flow logic |
| `data/viewmodel/CycleUiState.kt` | 310 | 16,102 B | UI state definitions |
| `data/viewmodel/DebugViewModel.kt` | 195 | 11,560 B | Debug/telemetry ViewModel |
| `data/viewmodel/OnboardingExtractionState.kt` | 200+ | 10,785 B | Extraction state machine |
| `data/viewmodel/JobExtractionState.kt` | 180+ | 10,059 B | Job extraction state |
| `data/viewmodel/ContentState.kt` | 170+ | 9,526 B | Content state |
| `data/viewmodel/ResultViewState.kt` | 170+ | 9,442 B | Result view state |
| `data/viewmodel/ExportEvent.kt`, `ExportState.kt`, `RestorationState.kt`, `WorkflowState.kt`, `CycleNavEvent.kt`, `CycleStage.kt`, `ActionType.kt`, `ResultError.kt` | — | — | Supporting state/event classes |
| Sub-lambdas (~27 `$*.kt` files) | — | — | Compiler-generated; NO recovery value |

**Verdict: HIGH recovery value** — Core ViewModel logic is decompiled-only. Sub-lambdas: NO recovery value.

### Real Sources (3 files — already present, no recovery needed)

| File | Lines | Notes |
|------|-------|-------|
| `data/viewmodel/CompatibilityViewModel.kt` | ~100 | REAL source |
| `data/viewmodel/GeneratorViewModel.kt` | ~115 | REAL source |
| `data/viewmodel/GeneratorUiState.kt` | ~6 | REAL source |
| `data/viewmodel/HistoryViewModel.kt` | ~30 | REAL source |

---

## 5. STORAGE LAYER (Priority: MEDIUM)

### Decompiled-Only (HIGH recovery value)

| File | Lines | Size | Notes |
|------|-------|------|-------|
| `data/storage/HistoryStore.kt` | 500+ | 26,941 B | Cycle history persistence |
| `data/storage/UserPreferences.kt` | 500+ | 28,334 B | User preferences DataStore |
| `data/storage/OnboardingDraftStore.kt` | 350+ | 18,652 B | Draft persistence |
| `data/storage/TelemetryStorage.kt` | 100+ | 5,711 B | Telemetry storage |
| `data/storage/CycleDao.kt` | 120+ | 6,450 B | Room DAO for cycles |
| `data/storage/CycleEntity.kt` | 180+ | 9,874 B | Room entity |
| `data/storage/CycleStateDeserializer.kt` | 120+ | 6,499 B | Type converter |
| `data/storage/CycleStateSerializer.kt` | 65+ | 3,424 B | Type converter |
| `data/storage/VantEdgeDatabase.kt` | 60+ | 3,149 B | Room database |
| `data/storage/OnboardingDraftDao.kt` | 30+ | 1,654 B | Room DAO for drafts |
| `data/storage/OnboardingDraftEntity.kt` | 70+ | 3,800 B | Room entity |
| `data/storage/CycleDao_Impl.kt` | 508 | 28,211 B | Room generated impl |
| `data/storage/VantEdgeDatabase_Impl.kt` | 191 | 10,364 B | Room generated impl |
| `data/storage/OnboardingDraftDao_Impl.kt` | 176 | 8,564 B | Room generated impl |
| Sub-lambdas (~10 `$*.kt` files) | — | — | Compiler-generated |

**Verdict: HIGH recovery value** (main files). Room `_Impl` files have NO recovery value (auto-generated). Lambdas: NO recovery value.

### Real Sources (1 file — already present, no recovery needed)

| File | Lines | Notes |
|------|-------|-------|
| `data/storage/CompatibilityStore.kt` | ~18 | REAL source |

---

## 6. DOMAIN LAYER (Priority: MEDIUM-LOW)

### Paired Files: Real Source vs Decompiled

#### OptimizationOrchestrator
| Version | Path | Lines | Key Difference |
|---------|------|-------|----------------|
| **REAL** | `data/domain/OptimizationOrchestrator.kt` | 230 | Working implementation, 5 methods |
| **DECOMPILED** | `domain/OptimizationOrchestrator.kt` | 299 | All methods throw `UnsupportedOperationException` |

**Classification**: MAJOR_DIFFERENCE  
**Recovery Value**: ZERO — Decompiled version has NO working code. All methods are stubs. Real source must be used.

#### OnboardingCommitService
| Version | Path | Lines | Key Difference |
|---------|------|-------|----------------|
| **REAL** | `data/domain/OnboardingCommitService.kt` | 57 | Working implementation, 2 methods |
| **DECOMPILED** | `domain/OnboardingCommitService.kt` | 220 | `commit()` throws UnsuppOpEx; `extractionToProfile()` works |

**Classification**: MAJOR_DIFFERENCE  
**Recovery Value**: LOW — Decompiled `commit()` is broken. `extractionToProfile()` matches real source. Cross-check only.

#### PipelineStep
| Version | Path | Lines | Key Difference |
|---------|------|-------|----------------|
| **REAL** | `data/domain/PipelineStep.kt` | 8 | Same 4 enum values |
| **DECOMPILED** | `domain/PipelineStep.kt` | 31 | Same 4 enum values + auto-generated Enum boilerplate |

**Classification**: UNCHANGED  
**Recovery Value**: NONE — Decompiled adds nothing.

#### DocumentExportUseCase / ExportReceipt
| Version | Path | Lines | Key Difference |
|---------|------|-------|----------------|
| **REAL** | — | — | No real source exists |
| **DECOMPILED** | `data/domain/DocumentExportUseCase.kt` | 56 | Decompiled-only |
| **DECOMPILED** | `data/domain/ExportReceipt.kt` | 89 | Decompiled-only (inner class extracted) |

**Verdict**: HIGH recovery value for DocumentExportUseCase. ExportReceipt is auto-generated data class copy, LOW value.

### Decompiled-Only (HIGH recovery value)

| File | Lines | Notes |
|------|-------|-------|
| `domain/PipelineTrace.kt` | 170+ | 9,488 B — Pipeline tracing logic |

---

## 7. NAVIGATION (Priority: LOW)

| File | Lines | Size | Status |
|------|-------|------|--------|
| `navigation/Navigation.kt` | 350+ | 19,220 B | **REAL SOURCE** |
| `navigation/NavigationKt.kt` | 2,000+ | 117,989 B | **DECOMPILED** — Compose navigation UI |
| `navigation/NavigationKt$AppNavigation$2-5.kt` | — | — | DECOMPILED — Lambda fragments |

**Verdict**: Navigation.kt is real source (route definitions). NavigationKt.kt is decompiled-only with the Compose navigation UI — **HIGH recovery value** (117 KB of UI navigation code).

---

## 8. NOTIFICATIONS (Priority: LOW)

| File | Path | Status |
|------|------|--------|
| `DeadlineNotificationSchedular.kt` | `notifications /` (trailing space) | **REAL SOURCE** — Typo in name ("Schedular") |
| `DeadlineNotificationScheduler.kt` | `notifications/` | **DECOMPILED** — Corrected spelling |
| `DeadlineNotificationWorker.kt` | `notifications/` | **DECOMPILED** |

**Verdict**: Decompiled versions have LOW recovery value (real source exists in trailing-space dir). The real source is in a potentially unreachable directory due to trailing space.

---

## 9. UI SCREENS (Priority: LOW)

### Real Sources (19 "thin" screen files)
These are REAL but very thin — just class stubs that compose into the decompiled `*Kt.kt` files:
`AdvancedAnalysisScreen.kt`, `AnalysisResultScreen.kt`, `AppColors.kt`, `CVDesignScreen.kt`, `CVGeneratorScreen.kt`, `ChoosePathScreen.kt`, `ColorSchemeScreen.kt`, `CompatibilityInputScreen.kt`, `CompatibilityResultScreen.kt`, `CoverLetterScreen.kt`, `CycleHistoryScreen.kt`, `DashboardScreen.kt`, `DocumentPreviewScreen.kt`, `EditProfileScreen.kt`, `EligibilityCheckerScreen.kt`, `ErrorScreen.kt`, `ExtractingScreen.kt`, `FinalReviewScreen.kt`, `HistoryScreen.kt`, `JobInputScreen.kt`, `LoadingScreen.kt`, `PipelineLoadingScreen.kt`, `PlaceHolderScreen.kt`, `PostDownloadScreen.kt`, `ResultScreen.kt`, `ReviewExtractionScreen.kt`, `WelcomeScreen.kt`

**Verdict**: The real screen stubs are thin wrappers. The actual Compose UI rendering code is in the decompiled `*Kt.kt` files (e.g., `ResultScreenKt.kt` at 110 KB) — **HIGH recovery value** for all `*Kt.kt` files.

### Theme Files
| Real Source | Decompiled | Notes |
|-------------|-----------|-------|
| `ui/theme/Color.kt` (25 lines) | `ui/theme/ColorKt.kt` (100+ lines) | ⚠️ MAJOR_DIFFERENCE — decompiled has 18 val definitions vs real's 16 |
| `ui/theme/Theme.kt` (67 lines) | `ui/theme/ThemeKt.kt` (180+ lines) | ⚠️ MAJOR_DIFFERENCE — decompiled has full color scheme + JADX restructured blocks |
| `ui/theme/Typography.kt` (94 lines) | `ui/theme/TypographyKt.kt` (170+ lines) | UNCHANGED — same `VantEdgeTypography` val |
| `ui/screens/AppColors.kt` (7 lines, 4 vals) | `ui/theme/AppColors.kt` (69 lines, 9 vals) | ⚠️ MAJOR_DIFFERENCE — decompiled has 9 color vals vs real's 4 |

---

## 10. UTILITY FILES (Priority: LOW)

| Real Source | Decompiled Counterpart | Notes |
|-------------|----------------------|-------|
| `util/Telemetry collector.kt` (203 lines) | `util/TelemetryCollector.kt` (111 lines) — **different class name** | ⚠️ MAJOR_DIFFERENCE — Real source has space in filename, full implementation |
| `util/FileUtil.kt` (real, ~15 lines) | — | No decompiled counterpart needed |
| — | `util/AppLogger.kt` (decompiled, 85+ lines) | HIGH recovery — decompiled-only |
| — | `util/HashUtils.kt` (decompiled, 40+ lines) | HIGH recovery — decompiled-only |
| — | `util/LogDumper.kt` (decompiled, 170+ lines) | HIGH recovery — decompiled-only |
| — | `util/FailureLogBundle.kt` (decompiled, 40+ lines) | HIGH recovery — decompiled-only |
| — | `util/TelemetryExportService.kt` (decompiled, 80+ lines) | HIGH recovery — decompiled-only |

**Telemetry collector vs TelemetryCollector**: The real source `Telemetry collector.kt` (with space) has a working implementation with KDoc comments. The decompiled `TelemetryCollector.kt` has the correct class name. The decompiled version should be renamed to match the real source's filename pattern.

---

## 11. ROOT-LEVEL FILES

| File | Status | Recovery Value |
|------|--------|---------------|
| `MainActivity.kt` | **DECOMPILED** (Java-style semicolons) | HIGH — 87 lines of core app entry |
| `BuildConfig.kt` | **DECOMPILED** | LOW — auto-generated build config |
| `R.kt` | **DECOMPILED** | LOW — auto-generated resource IDs |

---

## SUMMARY TABLE

| Category | HIGH Value | MEDIUM Value | LOW/NO Value | Total |
|----------|-----------|-------------|-------------|-------|
| data/engine | 13 files | 0 | 2 (lambdas) | 15 |
| data/network | 4 files | 0 | 6 (lambdas) | 10 |
| data/model | 44 files | 0 | 0 | 44 |
| data/viewmodel | 16 files | 0 | 27 (lambdas) | 43 |
| data/storage | 11 files | 0 | 15 (Impl+lambdas) | 26 |
| data/domain | 2 files | 0 | 1 (ExportReceipt) | 3 |
| domain/ | 1 file | 0 | 3 (paired) | 4 |
| navigation | 1 file | 0 | 4 (lambdas) | 5 |
| notifications | 2 files | 0 | 0 | 2 |
| ui/screens | 50+ `*Kt.kt` | 0 | 19 (real thin stubs) | 70+ |
| ui/theme | 3 files | 0 | 1 (Color.kt real) | 4 |
| util | 4 files | 1 (TelemetryCollector) | 1 (FileUtil real) | 6 |
| root | 1 file | 0 | 2 (auto-gen) | 3 |
| **TOTAL** | **~152 files** | **1 file** | **~80 files** | **~233 files** |

### Key Decisions

| Decision | Count | Action |
|----------|-------|--------|
| **SAFE TO DELETE** (no recovery value) | ~80 files | Lambda `$*.kt`, Room `_Impl.kt`, `BuildConfig.kt`, `R.kt`, `*Kt.kt` with real source counterpart, decompiled duplicates with only UnsuppOpEx |
| **KEEP decompiled** (HIGH recovery value) | ~152 files | All decompiled-only files — these are the ONLY copy of the code |
| **KEEP real source** | ~49 files | Real sources must be preserved (they're the canonical version) |
| **CROSS-CHECK** (MAJOR_DIFFERENCE) | 5 pairs | Domain classes, TelemetryCollector, Theme files — real and decompiled differ significantly |

---

## APPENDIX: Paired File Comparisons

### Pair 1: OptimizationOrchestrator
- **Real**: `data/domain/OptimizationOrchestrator.kt` (230 lines, 5 working methods)
- **Decompiled**: `domain/OptimizationOrchestrator.kt` (299 lines, all methods throw UnsupportedOperationException)
- **Classification**: MAJOR_DIFFERENCE
- **Recovery**: ZERO — Decompiled is useless. Real source is authoritative.
- **Extra in decompiled**: `applyDesign()`, extra overloads — but all are stubs

### Pair 2: OnboardingCommitService
- **Real**: `data/domain/OnboardingCommitService.kt` (57 lines, 2 methods)
- **Decompiled**: `domain/OnboardingCommitService.kt` (220 lines)
- **Classification**: MAJOR_DIFFERENCE
- **Recovery**: LOW — `extractionToProfile()` works and can cross-check real source. `commit()` is broken.
- **Extra in decompiled**: Decompiled has `extractionToProfile()` method signature + supplementary lambdas

### Pair 3: PipelineStep
- **Real**: `data/domain/PipelineStep.kt` (8 lines)
- **Decompiled**: `domain/PipelineStep.kt` (31 lines)
- **Classification**: UNCHANGED (same 4 enum values)
- **Recovery**: NONE

### Pair 4: AtsEngine / AtsResult (engine)
- **Real**: `data/engine/AtsEngine.kt` (23 lines) — contains BOTH `AtsResult` data class and `ATSEngine` object
- **Decompiled**: `data/engine/ATSEngine.kt` (39 lines) + `data/engine/AtsResult.kt` (87 lines)
- **Classification**: UNCHANGED — same fields, same logic
- **Recovery**: NONE — real source is authoritative. Decompiled splits into 2 files.

### Pair 5: AtsResult (model) vs AtsResult (engine)
- **Real**: `data/model/AtsResult.kt` (7 lines, package `com.vantedge.model`)
  - Fields: `score: Int`, `missingKeywords: List<String>`, `weakSections: List<String>`
- **Decompiled**: `data/engine/AtsResult.kt` (87 lines, package `com.vantedge.app.data.engine`)
  - Fields: `score: Int`, `keywords: List<String>`, `missingKeywords: List<String>`
- **Classification**: MAJOR_DIFFERENCE — Different classes entirely (different packages, different fields: `keywords` vs `weakSections`)
- **Recovery**: MEDIUM — The engine version extends the model with `keywords`. Cross-check for field usage.

### Pair 6: Telemetry collector / TelemetryCollector
- **Real**: `util/Telemetry collector.kt` (203 lines, space in filename)
  - Class: `TelemetryCollector` — working implementation with KDoc, coroutine scope, `record()` + `recordDecision()`
- **Decompiled**: `util/TelemetryCollector.kt` (111 lines)
  - Same class name but smaller — missing comments, compressed expression bodies
- **Classification**: UNCHANGED (functionally identical)
- **Recovery**: LOW — real source is complete and documented

### Pair 7: DeadlineNotificationSchedular / Scheduler
- **Real**: `notifications /DeadlineNotificationSchedular.kt` (119 lines, trailing space dir, "Schedular" typo)
  - Real working implementation
- **Decompiled**: `notifications/DeadlineNotificationScheduler.kt` (77 lines, corrected name)
- **Classification**: MINOR_DIFFERENCE — functionally similar, corrected class name
- **Recovery**: LOW — real source is working but in hard-to-reach directory

### Pair 8: Color.kt / ColorKt.kt
- **Real**: `ui/theme/Color.kt` (25 lines) — 16 `val` color definitions
- **Decompiled**: `ui/theme/ColorKt.kt` — 18+ val definitions + JADX getter wrappers
- **Classification**: MINOR_DIFFERENCE — extra vals not in real source
- **Recovery**: LOW — color vals are trivial

### Pair 9: Theme.kt / ThemeKt.kt
- **Real**: `ui/theme/Theme.kt` (67 lines) — `VantEdgeTheme()` composable
- **Decompiled**: `ui/theme/ThemeKt.kt` — full color scheme, JADX-restructured blocks with code loss
- **Classification**: MAJOR_DIFFERENCE — JADX lost block structure
- **Recovery**: LOW — real source is authoritative

### Pair 10: AppColors (screen) vs AppColors (theme)
- **Real**: `ui/screens/AppColors.kt` (7 lines) — 4 color vals
- **Decompiled**: `ui/theme/AppColors.kt` (69 lines) — 9 color vals in a class
- **Classification**: MAJOR_DIFFERENCE — Different package, different structure (val vs object class)
- **Recovery**: LOW — color vals are trivial, but decompiled has 5 extra colors

---

## FINAL RECOMMENDATIONS

1. **PRESERVE all decompiled-only files** (~152) — they are the only surviving copy of the code
2. **DELETE safe files** (~80) — lambdas, Room Impl, BuildConfig, R.kt, broken domain duplicates
3. **CROSS-CHECK 5 paired comparisons** — real and decompiled differ; verify against each other
4. **RENAME `Telemetry collector.kt`** → `TelemetryCollector.kt` (remove space in filename)
5. **REMOVE trailing-space `notifications /` directory** — content is duplicated in `notifications/`
6. **MOVE 4 cross-package model files** — `AtsResult.kt`, `DocumentType.kt`, `ScoreInterpretation.kt`, `VantageScoreResults.kt` declare `com.vantedge.model` but reside in `com.vantedge.app.data.model`
