    # VANTEDGE RECOVERY MATRIX v1.0

**Governing Document:** All reconstruction work follows this matrix.
**Last Updated:** 2026-06-15
**Baseline Commit:** 5e1ce7e (recovery/main)

---

## COLUMNS

| Column | Values | Meaning |
|--------|--------|---------|
| **Source** | REAL / DECOMPILED / BOTH | Where the surviving code exists |
| **Priority** | P0 / P1 / P2 | Recovery importance per architectural directive |
| **Status** | PRESERVE / RECONSTRUCT / CROSS-CHECK / DELETE | What to do with this file |

---

## TIER 1 — CORE BUSINESS LOGIC (P0 — Recover First)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/engine/ProfileExtractionEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 861 lines, core extraction pipeline. Highest-risk recovery. |
| `data/engine/JobExtractionEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 547 lines, job posting extraction with gate validation. |
| `data/engine/CompatibilityEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 142 lines, ATS compatibility analysis. |
| `data/engine/GeneratorEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 168 lines, CV/cover letter generation. |
| `data/engine/ScoreEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 48 lines, profile completeness scoring. |
| `data/engine/GapAnalysisEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 38 lines, skill gap analysis. |
| `data/engine/CareerEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | 118 lines, template-based CV fallback. |
| `data/engine/CVTemplate.kt` | DECOMPILED | P0 | RECONSTRUCT | 700+ lines, HTML CV rendering templates. |
| `data/engine/DocxBuilder.kt` | DECOMPILED | P0 | RECONSTRUCT | 110 lines, DOCX byte array builder. |
| `data/engine/Gate0Result.kt` | DECOMPILED | P0 | RECONSTRUCT | 118 lines, Gate0 validation result data class. |
| `data/engine/Gate0Reason.kt` | DECOMPILED | P0 | RECONSTRUCT | 23 lines, Gate0 rejection reason enum. |
| `data/engine/ExtractionMode.kt` | DECOMPILED | P0 | RECONSTRUCT | 20 lines, extraction mode enum (PDF_TEXT/OCR/DOCX). |
| `data/engine/VantEdgeScoreResult.kt` | DECOMPILED | P0 | RECONSTRUCT | 55 lines, score result data class. |

## TIER 2 — DATA CONTRACTS (P0 — Models)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/model/UserProfile.kt` | DECOMPILED | P0 | RECONSTRUCT | Core user profile model |
| `data/model/JobData.kt` | DECOMPILED | P0 | RECONSTRUCT | Job posting data model |
| `data/model/StructuredProfileExtraction.kt` | DECOMPILED | P0 | RECONSTRUCT | Profile extraction result |
| `data/model/CompatibilityResult.kt` | DECOMPILED | P0 | RECONSTRUCT | Compatibility analysis result |
| `data/model/JobExtractionResult.kt` | DECOMPILED | P0 | RECONSTRUCT | Job extraction result |
| `data/model/GapAnalysisResult.kt` | DECOMPILED | P0 | RECONSTRUCT | Gap analysis result |
| `data/model/GenerationCycle.kt` | DECOMPILED | P0 | RECONSTRUCT | Core cycle model |
| `data/model/CycleState.kt` | DECOMPILED | P0 | RECONSTRUCT | Cycle state model |
| `data/model/OnboardingDraft.kt` | DECOMPILED | P0 | RECONSTRUCT | Onboarding draft model |
| `data/model/OnboardingStage.kt` | DECOMPILED | P0 | RECONSTRUCT | Onboarding stage enum |
| `data/model/StructuredProfileExtraction.kt` | DECOMPILED | P0 | RECONSTRUCT | Structured extraction model |
| `data/model/ConfidenceBreakdown.kt` | DECOMPILED | P0 | RECONSTRUCT | Confidence scoring model |
| `data/model/ExtractionMetadata.kt` | DECOMPILED | P0 | RECONSTRUCT | Extraction metadata |
| `data/model/CompatibilityRecord.kt` | DECOMPILED | P0 | RECONSTRUCT | Compatibility record |
| `data/model/Gate0JobResult.kt` | DECOMPILED | P0 | RECONSTRUCT | Gate0 job result |
| `data/model/Gate0JobReason.kt` | DECOMPILED | P0 | RECONSTRUCT | Gate0 job reason enum |
| `data/model/JobExtractionMetrics.kt` | DECOMPILED | P0 | RECONSTRUCT | Job extraction metrics |
| All other `data/model/*.kt` (28 more) | DECOMPILED | P0 | RECONSTRUCT | Remaining data contracts |

## TIER 3 — EXTRACTION PIPELINE (P0)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/engine/ProfileExtractionEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | Already listed in Tier 1 — extraction entry point |
| `data/engine/JobExtractionEngine.kt` | DECOMPILED | P0 | RECONSTRUCT | Already listed in Tier 1 |
| `data/infrastructure/MediaStoreExporter.kt` | DECOMPILED | P0 | RECONSTRUCT | File export infrastructure |
| `data/domain/DocumentExportUseCase.kt` | DECOMPILED | P0 | RECONSTRUCT | Export use case |
| `data/domain/ExportReceipt.kt` | DECOMPILED | P0 | CROSS-CHECK | Auto-generated data class copy |

## TIER 4 — ONBOARDING SYSTEM (P0)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/viewmodel/OnboardingViewModel.kt` | DECOMPILED | P0 | RECONSTRUCT | 437 lines, full onboarding flow |
| `data/viewmodel/OnboardingExtractionState.kt` | DECOMPILED | P0 | RECONSTRUCT | 200+ lines, extraction state machine |
| `data/viewmodel/OnboardingViewModelFactory.kt` | DECOMPILED | P0 | RECONSTRUCT | VM factory |
| `data/storage/OnboardingDraftStore.kt` | DECOMPILED | P0 | RECONSTRUCT | 350+ lines, draft persistence |
| `data/storage/OnboardingDraftDao.kt` | DECOMPILED | P0 | RECONSTRUCT | 30+ lines, Room DAO |
| `data/storage/OnboardingDraftEntity.kt` | DECOMPILED | P0 | RECONSTRUCT | 70+ lines, Room entity |
| `data/domain/OnboardingCommitService.kt` | REAL (data/domain/) | P0 | PRESERVE | Real source exists, fix package mismatch |

## TIER 5 — STORAGE LAYER (P1)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/storage/UserPreferences.kt` | DECOMPILED | P1 | RECONSTRUCT | 500+ lines, DataStore persistence |
| `data/storage/HistoryStore.kt` | DECOMPILED | P1 | RECONSTRUCT | 500+ lines, cycle history persistence |
| `data/storage/VantEdgeDatabase.kt` | DECOMPILED | P1 | RECONSTRUCT | 60+ lines, Room database singleton |
| `data/storage/CycleDao.kt` | DECOMPILED | P1 | RECONSTRUCT | 120+ lines, Room DAO |
| `data/storage/CycleEntity.kt` | DECOMPILED | P1 | RECONSTRUCT | 180+ lines, Room entity |
| `data/storage/CycleStateSerializer.kt` | DECOMPILED | P1 | RECONSTRUCT | 65+ lines, Gson serializer |
| `data/storage/CycleStateDeserializer.kt` | DECOMPILED | P1 | RECONSTRUCT | 120+ lines, Gson deserializer |
| `data/storage/CompatibilityStore.kt` | REAL | P1 | PRESERVE | Clean real source |

## TIER 6 — OBSERVABILITY (P1)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `util/TelemetryCollector.kt` | DECOMPILED | P1 | RECONSTRUCT | Telemetry collection |
| `util/TelemetryExportService.kt` | DECOMPILED | P1 | RECONSTRUCT | 80+ lines, telemetry export |
| `util/AppLogger.kt` | DECOMPILED | P1 | RECONSTRUCT | 85+ lines, ring-buffer logger |
| `util/LogDumper.kt` | DECOMPILED | P1 | RECONSTRUCT | 170+ lines, diagnostic dump |
| `util/FailureLogBundle.kt` | DECOMPILED | P1 | RECONSTRUCT | 40+ lines, crash diagnostics |
| `util/HashUtils.kt` | DECOMPILED | P1 | RECONSTRUCT | 40+ lines, SHA-256 utility |
| `data/storage/TelemetryStorage.kt` | DECOMPILED | P1 | RECONSTRUCT | 100+ lines, telemetry log reader |
| `data/viewmodel/DebugViewModel.kt` | DECOMPILED | P1 | RECONSTRUCT | 195 lines, debug screen VM |
| `data/model/TelemetryRecord.kt` | DECOMPILED | P1 | RECONSTRUCT | Telemetry data model |
| `data/model/UserDecisionEvent.kt` | DECOMPILED | P1 | RECONSTRUCT | User decision tracking |

## TIER 7 — AI ABSTRACTION LAYER (P1)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/network/AiGateway.kt` | DECOMPILED | P1 | RECONSTRUCT | 135 lines, AI request facade |
| `data/network/GeminiService.kt` | DECOMPILED | P1 | RECONSTRUCT | 400+ lines, OpenRouter API client |
| `data/network/RequestThrottleManager.kt` | DECOMPILED | P1 | RECONSTRUCT | 60 lines, rate limiter |
| `data/network/AiSystemInstructions_P1Kt.kt` | DECOMPILED | P1 | RECONSTRUCT | 80 lines, system prompt definitions |

## TIER 8 — ORCHESTRATION / VIEWMODEL (P1)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/viewmodel/CycleViewModel.kt` | DECOMPILED | P1 | RECONSTRUCT | 1087 lines, central pipeline orchestrator |
| `data/viewmodel/CycleUiState.kt` | DECOMPILED | P1 | RECONSTRUCT | 310 lines, pipeline state machine |
| `data/viewmodel/ContentState.kt` | DECOMPILED | P1 | RECONSTRUCT | 170+ lines, generated content state |
| `data/viewmodel/ResultViewState.kt` | DECOMPILED | P1 | RECONSTRUCT | 170+ lines, result screen state |
| `data/viewmodel/WorkflowState.kt` | DECOMPILED | P1 | RECONSTRUCT | 65+ lines, workflow definitions |
| `data/viewmodel/ActionType.kt` | DECOMPILED | P1 | RECONSTRUCT | 20 lines, pipeline action enum |
| `data/viewmodel/RestorationState.kt` | DECOMPILED | P1 | RECONSTRUCT | 85+ lines, state restoration |
| `data/viewmodel/CycleNavEvent.kt` | DECOMPILED | P1 | RECONSTRUCT | 130+ lines, navigation events |
| `data/viewmodel/CycleStage.kt` | DECOMPILED | P1 | RECONSTRUCT | 18 lines, pipeline stage enum |
| `data/viewmodel/ExportEvent.kt` | DECOMPILED | P1 | RECONSTRUCT | 85+ lines, export events |
| `data/viewmodel/ExportState.kt` | DECOMPILED | P1 | RECONSTRUCT | 50+ lines, export state |
| `data/viewmodel/ResultError.kt` | DECOMPILED | P1 | RECONSTRUCT | 45+ lines, error state |
| `data/viewmodel/JobExtractionState.kt` | DECOMPILED | P1 | RECONSTRUCT | 180+ lines, extraction state |
| `domain/PipelineTrace.kt` | DECOMPILED | P1 | RECONSTRUCT | 170+ lines, pipeline diagnostics |

## REAL SOURCES — PRESERVE (No Reconstruction Needed)

| File | Source | Priority | Status | Notes |
|------|--------|----------|--------|-------|
| `data/domain/OnboardingCommitService.kt` | REAL | P0 | CROSS-CHECK | Fix package declaration to `data.domain` |
| `data/domain/OptimizationOrchestrator.kt` | REAL | P0 | CROSS-CHECK | Fix package declaration to `data.domain` |
| `data/domain/PipelineStep.kt` | REAL | P0 | CROSS-CHECK | Fix package declaration to `data.domain` |
| `data/engine/AtsEngine.kt` | REAL | P1 | PRESERVE | 23 lines, ATS keyword scanning |
| `data/storage/CompatibilityStore.kt` | REAL | P1 | PRESERVE | Clean source |
| `data/viewmodel/CompatibilityViewModel.kt` | REAL | P1 | PRESERVE | Clean source |
| `data/viewmodel/GeneratorUiState.kt` | REAL | P1 | PRESERVE | Clean source |
| `data/viewmodel/GeneratorViewModel.kt` | REAL | P1 | PRESERVE | Clean source |
| `data/viewmodel/HistoryViewModel.kt` | REAL | P1 | PRESERVE | Clean source |
| `navigation/Navigation.kt` | REAL | P1 | PRESERVE | 350+ lines, route definitions |
| `ui/screens/*Screen.kt` (26 files) | REAL | P2 | PRESERVE | Thin screen composable stubs |
| `ui/theme/Color.kt` | REAL | P2 | PRESERVE | 16 color definitions |
| `ui/theme/Theme.kt` | REAL | P2 | PRESERVE | 67 lines, VantEdgeTheme composable |
| `ui/theme/Typography.kt` | REAL | P2 | PRESERVE | 94 lines, typography definitions |
| `util/FileUtil.kt` | REAL | P2 | PRESERVE | 15 lines, file utility |

## TO DELETE — No Recovery Value

| File | Reason |
|------|--------|
| `BuildConfig.kt` | Build-generated duplicate of `build/generated/` version |
| `R.kt` | Build-generated resource IDs |
| `data/storage/CycleDao_Impl.kt` | Room-generated implementation |
| `data/storage/OnboardingDraftDao_Impl.kt` | Room-generated implementation |
| `data/storage/VantEdgeDatabase_Impl.kt` | Room-generated implementation |
| `data/engine/ATSResults.kt` | Zero bytes / empty file |
| All `*$*.kt` files (lambdas) ~100+ files | Compiler-generated lambda artifacts |
| All `ComposableSingletons$*.kt` files ~20 files | Compose compiler-generated |
| `domain/OnboardingCommitService.kt` | Broken stub, real source exists in `data/domain/` |
| `domain/OptimizationOrchestrator.kt` | Broken stub, real source exists in `data/domain/` |
| `domain/PipelineStep.kt` | Broken stub, real source exists in `data/domain/` |
| `domain/OptimizationOrchestrator$*.kt` (5 files) | Lambda artifacts for broken stub |
| `domain/OnboardingCommitService$commit$1.kt` | Lambda artifact for broken stub |
| `domain/PipelineTrace.kt` | DECOMPILED — Keep (has real business logic) |
| `notifications /DeadlineNotificationSchedular.kt` | Orphan with trailing-space dir; functionally duplicated |
| `util/Telemetry collector.kt` | Space in filename; functionally duplicated by TelemetryCollector.kt |

## CROSS-CHECK — Requires Verification

| File | Source | Issue |
|------|--------|-------|
| `data/model/AtsResult.kt` | REAL | Package `com.vantedge.model` — fix to `com.vantedge.app.data.model` |
| `data/model/DocumentType.kt` | REAL | Package `com.vantedge.model` — fix |
| `data/model/ScoreInterpretation.kt` | REAL | Package `com.vantedge.model` — fix |
| `data/model/VantageScoreResults.kt` | REAL | Package `com.vantedge.model` — fix |

---

## EXECUTION ORDER

```
Phase 2: Recover engines (data/engine/*.kt)         ← NOW
Phase 3: Recover models (data/model/*.kt)           
Phase 4: Recover extraction pipeline                 
Phase 5: Recover onboarding system                   
Phase 6: Recover storage layer                       
Phase 7: Recover observability                       
Phase 8: Recover AI layer                            
Phase 9: Recover UI                                  
Phase 10: Cleanup (delete Phase 10 listed files)     
```
