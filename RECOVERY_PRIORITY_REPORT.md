# RECOVERY PRIORITY REPORT

**Based on**: RECOVERY_VALUE_REPORT.md  
**Scope**: All HIGH recovery value files (decompiled-only — the only surviving code)

---

## FEATURE INDEX

Files mapped to VantEdge features:

| Feature | Files |
|---------|-------|
| **Onboarding** | `OnboardingViewModel.kt`, `OnboardingDraftStore.kt`, `OnboardingDraftDao.kt`, `OnboardingDraftEntity.kt`, `OnboardingExtractionState.kt`, `OnboardingViewModelFactory.kt` |
| **Extraction** | `ProfileExtractionEngine.kt`, `JobExtractionEngine.kt`, `ExtractionMode.kt`, `Gate0Result.kt`, `Gate0Reason.kt`, `JobExtractionState.kt` |
| **Scoring** | `ScoreEngine.kt`, `VantEdgeScoreResult.kt`, `GapAnalysisEngine.kt` |
| **Generation** | `GeneratorEngine.kt`, `CVTemplate.kt`, `CareerEngine.kt`, `DocxBuilder.kt`, `MediaStoreExporter.kt`, `DocumentExportUseCase.kt`, `GeneratorUiState.kt` (real), `GeneratorViewModel.kt` (real) |
| **Versioning** | `CycleEntity.version` field, `HistoryStore` (versioned cycles), `CycleState` (model) |
| **Telemetry** | `TelemetryStorage.kt`, `TelemetryExportService.kt`, `AppLogger.kt`, `LogDumper.kt`, `FailureLogBundle.kt`, `DebugViewModel.kt` |
| **Orchestration** | `CycleViewModel.kt`, `PipelineTrace.kt`, `CompatibilityEngine.kt`, `CompatibilityViewModel.kt` (real) |
| **AI Integration** | `AiGateway.kt`, `GeminiService.kt`, `RequestThrottleManager.kt`, `AiSystemInstructions_P1Kt.kt` |
| **Pipeline** | `CycleViewModel.kt`, `PipelineTrace.kt`, `CycleUiState.kt`, `ContentState.kt`, `ResultViewState.kt`, `WorkflowState.kt` (decompiled), `ActionType.kt` (decompiled), `RestorationState.kt` (decompiled) |
| **Navigation/UI** | `NavigationKt.kt`, all `ui/screens/*Kt.kt` files, `MainActivity.kt` |
| **Persistence** | `HistoryStore.kt`, `UserPreferences.kt`, `VantEdgeDatabase.kt`, `CycleDao.kt`, `CycleEntity.kt`, `CycleStateDeserializer.kt`, `CycleStateSerializer.kt` |

---

## CRITICAL PRIORITY FILES

Files whose loss would break the core VantEdge loop. Without these, the app cannot function.

### 1. ProfileExtractionEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/ProfileExtractionEngine.kt` |
| **Lines** | 861 |
| **Primary Responsibility** | Resume/CV document → structured profile pipeline (PDF, DOCX, OCR) with multi-gate validation and AI-driven extraction |
| **Importance** | **CRITICAL** |
| **Feature** | extraction |

### 2. CycleViewModel.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/viewmodel/CycleViewModel.kt` |
| **Lines** | 1,087 |
| **Primary Responsibility** | Central pipeline orchestrator — controls entire CV generation lifecycle from job extraction through ATS analysis, generation, improvement cycles, export, commit |
| **Importance** | **CRITICAL** |
| **Feature** | orchestration, pipeline, generation |

### 3. JobExtractionEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/JobExtractionEngine.kt` |
| **Lines** | 547 |
| **Primary Responsibility** | Extracts structured job info (title, company, description) from raw job posting text with multi-stage gate validation and confidence scoring |
| **Importance** | **CRITICAL** |
| **Feature** | extraction |

### 4. GeminiService.kt
| | |
|---|---|
| **Category** | AI INTEGRATION |
| **Path** | `data/network/GeminiService.kt` |
| **Lines** | 400+ |
| **Primary Responsibility** | Low-level HTTP client for OpenRouter AI API — handles model chaining, retry logic, exponential backoff, categorized failure types |
| **Importance** | **CRITICAL** |
| **Feature** | AI integration |

### 5. AiGateway.kt
| | |
|---|---|
| **Category** | AI INTEGRATION |
| **Path** | `data/network/AiGateway.kt` |
| **Lines** | 135 |
| **Primary Responsibility** | AI request facade — wraps GeminiService with logging, progress callbacks |
| **Importance** | **CRITICAL** |
| **Feature** | AI integration |

### 6. CompatibilityEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/CompatibilityEngine.kt` |
| **Lines** | 142 |
| **Primary Responsibility** | ATS compatibility analysis — compares user profile vs job description via AI |
| **Importance** | **CRITICAL** |
| **Feature** | orchestration, scoring |

### 7. GeneratorEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/GeneratorEngine.kt` |
| **Lines** | 168 |
| **Primary Responsibility** | CV and cover letter AI generation with design/scheme application |
| **Importance** | **CRITICAL** |
| **Feature** | generation |

### 8. CVTemplate.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/CVTemplate.kt` |
| **Lines** | 700+ |
| **Primary Responsibility** | HTML CV rendering with 4 visual templates (modern, minimalist, creative, executive) and customizable color schemes |
| **Importance** | **CRITICAL** |
| **Feature** | generation |

### 9. AiSystemInstructions_P1Kt.kt
| | |
|---|---|
| **Category** | AI INTEGRATION |
| **Path** | `data/network/AiSystemInstructions_P1Kt.kt` |
| **Lines** | 80 |
| **Primary Responsibility** | System prompt strings for all 6 AI tasks — defines AI behavior for extraction, analysis, generation |
| **Importance** | **CRITICAL** |
| **Feature** | AI integration, onboarding, extraction, generation |

### 10. NavigationKt.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `navigation/NavigationKt.kt` |
| **Lines** | 2,000+ |
| **Primary Responsibility** | Complete Compose navigation graph — dependency injection wiring, NavHost setup, all screen routes from onboarding through dashboard |
| **Importance** | **CRITICAL** |
| **Feature** | navigation/UI |

---

## HIGH PRIORITY FILES

Important supporting files. Loss would significantly damage major features.

### 11. HistoryStore.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/HistoryStore.kt` |
| **Lines** | 500+ |
| **Primary Responsibility** | Cycle persistence — Room-backed CRUD with reactive StateFlow for cycle history and active-cycle tracking |
| **Importance** | **HIGH** |
| **Feature** | persistence, versioning |

### 12. UserPreferences.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/UserPreferences.kt` |
| **Lines** | 500+ |
| **Primary Responsibility** | Jetpack DataStore persistence for user profile and onboarding completion state |
| **Importance** | **HIGH** |
| **Feature** | persistence, onboarding |

### 13. OnboardingViewModel.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/OnboardingViewModel.kt` |
| **Lines** | 437 |
| **Primary Responsibility** | Manages complete onboarding flow — document extraction, skill editing, multi-step navigation, profile commit |
| **Importance** | **HIGH** |
| **Feature** | onboarding |

### 14. OnboardingDraftStore.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/OnboardingDraftStore.kt` |
| **Lines** | 350+ |
| **Primary Responsibility** | Draft persistence — Room-backed save/load/clear for onboarding drafts with Gson serialization |
| **Importance** | **HIGH** |
| **Feature** | onboarding, persistence |

### 15. VantEdgeDatabase.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/VantEdgeDatabase.kt` |
| **Lines** | 60+ |
| **Primary Responsibility** | Room database singleton — provides CycleDao and OnboardingDraftDao with thread-safe initialization |
| **Importance** | **HIGH** |
| **Feature** | persistence |

### 16. CycleDao.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/CycleDao.kt` |
| **Lines** | 120+ |
| **Primary Responsibility** | Room DAO for cycle entities — Flow queries, suspend CRUD, active-cycle management |
| **Importance** | **HIGH** |
| **Feature** | persistence |

### 17. CycleEntity.kt
| | |
|---|---|
| **Category** | DATA MODEL |
| **Path** | `data/storage/CycleEntity.kt` |
| **Lines** | 180+ |
| **Primary Responsibility** | Room entity with full cycle metadata — includes `version` field for cycle versioning |
| **Importance** | **HIGH** |
| **Feature** | persistence, versioning |

### 18. CycleUiState.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/CycleUiState.kt` |
| **Lines** | 310 |
| **Primary Responsibility** | Sealed state machine for pipeline lifecycle — Idle → Loading → AnalysisDone → GenerationReady → Success/Error |
| **Importance** | **HIGH** |
| **Feature** | orchestration, pipeline |

### 19. ResultViewState.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/ResultViewState.kt` |
| **Lines** | 170+ |
| **Primary Responsibility** | Immutable view state for result screen — combines workflow, content, actions, loading/error |
| **Importance** | **HIGH** |
| **Feature** | pipeline |

### 20. ContentState.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/ContentState.kt` |
| **Lines** | 170+ |
| **Primary Responsibility** | Holds generated CV content, cover letter, compatibility analysis, score tracking with delta |
| **Importance** | **HIGH** |
| **Feature** | pipeline |

### 21. ScoreEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/ScoreEngine.kt` |
| **Lines** | 48 |
| **Primary Responsibility** | Profile completeness scoring (0-100) based on field presence |
| **Importance** | **HIGH** |
| **Feature** | scoring |

### 22. GapAnalysisEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/GapAnalysisEngine.kt` |
| **Lines** | 38 |
| **Primary Responsibility** | Skill gap analysis — compares profile skills against job requirements |
| **Importance** | **HIGH** |
| **Feature** | scoring |

### 23. CareerEngine.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/CareerEngine.kt` |
| **Lines** | 118 |
| **Primary Responsibility** | Template-based plain-text CV and cover letter generation (fallback when AI is unavailable) |
| **Importance** | **HIGH** |
| **Feature** | generation |

### 24. DocxBuilder.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/engine/DocxBuilder.kt` |
| **Lines** | 110 |
| **Primary Responsibility** | Builds .docx byte arrays from plain text with inline formatting |
| **Importance** | **HIGH** |
| **Feature** | generation |

### 25. MediaStoreExporter.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/infrastructure/MediaStoreExporter.kt` |
| **Lines** | 70 |
| **Primary Responsibility** | Saves .docx files to device downloads via MediaStore API |
| **Importance** | **HIGH** |
| **Feature** | generation |

### 26. DocumentExportUseCase.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `data/domain/DocumentExportUseCase.kt` |
| **Lines** | 56 |
| **Primary Responsibility** | Export use case — strips HTML, builds docx, saves via MediaStore, returns receipt |
| **Importance** | **HIGH** |
| **Feature** | generation |

### 27. RequestThrottleManager.kt
| | |
|---|---|
| **Category** | AI INTEGRATION |
| **Path** | `data/network/RequestThrottleManager.kt` |
| **Lines** | 60 |
| **Primary Responsibility** | 12-second minimum interval rate limiter for AI API calls |
| **Importance** | **HIGH** |
| **Feature** | AI integration |

### 28. PipelineTrace.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `domain/PipelineTrace.kt` |
| **Lines** | 170+ |
| **Primary Responsibility** | Structured pipeline diagnostics — stage timing, validation helpers, data quality checks |
| **Importance** | **HIGH** |
| **Feature** | orchestration |

### 29. DebugViewModel.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/DebugViewModel.kt` |
| **Lines** | 195 |
| **Primary Responsibility** | Debug screen ViewModel — loads telemetry records, supports export |
| **Importance** | **HIGH** |
| **Feature** | telemetry |

### 30. MainActivity.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `MainActivity.kt` |
| **Lines** | 87 |
| **Primary Responsibility** | App entry point — initializes PDFBox, creates UserPreferences, launches Compose UI |
| **Importance** | **HIGH** |
| **Feature** | navigation/UI |

---

## MEDIUM PRIORITY FILES

Important but not blocking. Missing these degrades specific features.

### 31. TelemetryStorage.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/TelemetryStorage.kt` |
| **Lines** | 100+ |
| **Primary Responsibility** | Reads Gate0 telemetry records from log file for display and export |
| **Importance** | **MEDIUM** |
| **Feature** | telemetry |

### 32. TelemetryExportService.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `util/TelemetryExportService.kt` |
| **Lines** | 80+ |
| **Primary Responsibility** | Generates telemetry export file with share Intent via FileProvider |
| **Importance** | **MEDIUM** |
| **Feature** | telemetry |

### 33. AppLogger.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `util/AppLogger.kt` |
| **Lines** | 85+ |
| **Primary Responsibility** | In-memory ring-buffer logger (500 entries) for debug diagnostics |
| **Importance** | **MEDIUM** |
| **Feature** | telemetry |

### 34. LogDumper.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `util/LogDumper.kt` |
| **Lines** | 170+ |
| **Primary Responsibility** | Collects logs and launches share sheet for bug reports |
| **Importance** | **MEDIUM** |
| **Feature** | telemetry |

### 35. FailureLogBundle.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `util/FailureLogBundle.kt` |
| **Lines** | 40+ |
| **Primary Responsibility** | Captures crash diagnostics (device info, stack trace) to cache files |
| **Importance** | **MEDIUM** |
| **Feature** | telemetry |

### 36. Gate0Result.kt (engine)
| | |
|---|---|
| **Category** | DATA MODEL |
| **Path** | `data/engine/Gate0Result.kt` |
| **Lines** | 118 |
| **Primary Responsibility** | Gate0 validation result — score, threshold, acceptance, reason, extraction mode |
| **Importance** | **MEDIUM** |
| **Feature** | extraction |

### 37. Gate0Reason.kt (engine)
| | |
|---|---|
| **Category** | DATA MODEL |
| **Path** | `data/engine/Gate0Reason.kt` |
| **Lines** | 23 |
| **Primary Responsibility** | Enum of Gate0 rejection reasons (low structure, high narrative, OCR fragmented, etc.) |
| **Importance** | **MEDIUM** |
| **Feature** | extraction |

### 38. ExtractionMode.kt (engine)
| | |
|---|---|
| **Category** | DATA MODEL |
| **Path** | `data/engine/ExtractionMode.kt` |
| **Lines** | 20 |
| **Primary Responsibility** | Enum: PDF_TEXT, OCR, DOCX |
| **Importance** | **MEDIUM** |
| **Feature** | extraction |

### 39. VantEdgeScoreResult.kt
| | |
|---|---|
| **Category** | DATA MODEL |
| **Path** | `data/engine/VantEdgeScoreResult.kt` |
| **Lines** | 55 |
| **Primary Responsibility** | Score result — numeric score + interpretation string |
| **Importance** | **MEDIUM** |
| **Feature** | scoring |

### 40. OnboardingDraftDao.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/OnboardingDraftDao.kt` |
| **Lines** | 30+ |
| **Primary Responsibility** | Room DAO for draft entity — upsert, get, delete, clear |
| **Importance** | **MEDIUM** |
| **Feature** | onboarding, persistence |

### 41. OnboardingDraftEntity.kt
| | |
|---|---|
| **Category** | DATA MODEL |
| **Path** | `data/storage/OnboardingDraftEntity.kt` |
| **Lines** | 70+ |
| **Primary Responsibility** | Room entity — id + draftJson blob |
| **Importance** | **MEDIUM** |
| **Feature** | onboarding, persistence |

### 42. OnboardingExtractionState.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/OnboardingExtractionState.kt` |
| **Lines** | 200+ |
| **Primary Responsibility** | Sealed state — Idle, Extracting, Retrying, Success, Failure |
| **Importance** | **MEDIUM** |
| **Feature** | onboarding |

### 43. JobExtractionState.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/JobExtractionState.kt` |
| **Lines** | 180+ |
| **Primary Responsibility** | Sealed state — Idle, Extracting, Success, Failure |
| **Importance** | **MEDIUM** |
| **Feature** | extraction |

### 44. WorkflowState.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/WorkflowState.kt` |
| **Lines** | 65+ |
| **Primary Responsibility** | Workflow state definitions for result screen |
| **Importance** | **MEDIUM** |
| **Feature** | pipeline |

### 45. ActionType.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/ActionType.kt` |
| **Lines** | 20 |
| **Primary Responsibility** | Enum of available pipeline actions (re-analyze, re-generate, improve, etc.) |
| **Importance** | **MEDIUM** |
| **Feature** | pipeline |

### 46. RestorationState.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/RestorationState.kt` |
| **Lines** | 85+ |
| **Primary Responsibility** | Saved state restoration tracking for cycle recovery |
| **Importance** | **MEDIUM** |
| **Feature** | pipeline |

### 47. CycleStateDeserializer.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/CycleStateDeserializer.kt` |
| **Lines** | 120+ |
| **Primary Responsibility** | Gson TypeAdapter for CycleState deserialization |
| **Importance** | **MEDIUM** |
| **Feature** | persistence, versioning |

### 48. CycleStateSerializer.kt
| | |
|---|---|
| **Category** | STORAGE |
| **Path** | `data/storage/CycleStateSerializer.kt` |
| **Lines** | 65+ |
| **Primary Responsibility** | Gson TypeAdapter for CycleState serialization |
| **Importance** | **MEDIUM** |
| **Feature** | persistence, versioning |

### 49. HashUtils.kt
| | |
|---|---|
| **Category** | CORE BUSINESS LOGIC |
| **Path** | `util/HashUtils.kt` |
| **Lines** | 40+ |
| **Primary Responsibility** | SHA-256 hashing utility |
| **Importance** | **MEDIUM** |
| **Feature** | telemetry |

### 50. OnboardingViewModelFactory.kt
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/OnboardingViewModelFactory.kt` |
| **Lines** | 65+ |
| **Primary Responsibility** | ViewModelProvider.Factory for OnboardingViewModel |
| **Importance** | **MEDIUM** |
| **Feature** | onboarding |

---

## LOW PRIORITY FILES (within HIGH recovery set)

Useful but non-essential. Easy to re-implement.

### 51-62. UI Screen `*Kt.kt` files (decompiled Compose renderers)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Paths** | `ui/screens/ResultScreenKt.kt` (110KB), `AdvancedAnalysisScreenKt.kt` (95KB), `AnalysisResultScreenKt.kt` (50KB), `JobInputScreenKt.kt` (47KB), `EditProfileScreenKt.kt` (65KB), `ReviewExtractionScreenKt.kt` (65KB), `ExtractingScreenKt.kt` (32KB), `FinalReviewScreenKt.kt` (30KB), `CycleHistoryScreenKt.kt` (37KB), `DashboardScreenKt.kt` (14KB), etc. |
| **Primary Responsibility** | Compose UI rendering for each screen |
| **Importance** | **LOW** (thin wrappers exist as real sources; Compose UI can be regenerated from the real screen stubs) |
| **Feature** | navigation/UI |

### 63-66. Theme *Kt.kt files (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Paths** | `ui/theme/ColorKt.kt`, `ThemeKt.kt`, `TypographyKt.kt`, `ui/theme/AppColors.kt` (decompiled) |
| **Primary Responsibility** | Color/theme/Typography top-level function wrappers |
| **Importance** | **LOW** (real source counterparts exist in same directory — Color.kt, Theme.kt, Typography.kt) |
| **Feature** | navigation/UI |

### 67. CycleNavEvent.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/CycleNavEvent.kt` |
| **Lines** | 130+ |
| **Primary Responsibility** | Navigation event sealed class |
| **Importance** | **LOW** |
| **Feature** | navigation/UI |

### 68. CycleStage.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/CycleStage.kt` |
| **Lines** | 18 |
| **Primary Responsibility** | Pipeline stage enum |
| **Importance** | **LOW** |
| **Feature** | pipeline |

### 69. ExportEvent.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/ExportEvent.kt` |
| **Lines** | 85+ |
| **Primary Responsibility** | Export event sealed class |
| **Importance** | **LOW** |
| **Feature** | pipeline |

### 70. ExportState.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/ExportState.kt` |
| **Lines** | 50+ |
| **Primary Responsibility** | Export state sealed class |
| **Importance** | **LOW** |
| **Feature** | pipeline |

### 71. ResultError.kt (decompiled)
| | |
|---|---|
| **Category** | UI WORKFLOW |
| **Path** | `data/viewmodel/ResultError.kt` |
| **Lines** | 45+ |
| **Primary Responsibility** | Error state sealed class |
| **Importance** | **LOW** |
| **Feature** | pipeline |

### 72. ComposableSingletons files
| | |
|---|---|
| **Category** | GENERATED / LOW VALUE |
| **Paths** | All `ui/screens/ComposableSingletons$*.kt` files (~20 files) |
| **Primary Responsibility** | Compose compiler-generated singleton holders |
| **Importance** | **LOW** (regenerated by Compose compiler) |
| **Feature** | — |

---

## FILES LIKELY MODIFIED DURING THE LOST 2-WEEK PERIOD

Based on feature velocity patterns, these files were most likely being worked on:

| File | Reasoning |
|------|-----------|
| `data/engine/ProfileExtractionEngine.kt` | High churn area — extraction pipeline tuning, Gate0 calibration |
| `data/engine/JobExtractionEngine.kt` | Job extraction gates, confidence scoring refinements |
| `data/engine/Gate0Result.kt` | Gate0 threshold tuning — critical for extraction quality |
| `data/engine/Gate0Reason.kt` | New rejection reasons may have been added |
| `data/viewmodel/CycleViewModel.kt` | Pipeline orchestrator — likely improvement cycle feature work |
| `data/viewmodel/OnboardingViewModel.kt` | Onboarding flow changes |
| `data/network/GeminiService.kt` | Model chaining changes, API updates |
| `data/network/AiSystemInstructions_P1Kt.kt` | Prompt engineering — highest churn area |
| `data/storage/HistoryStore.kt` | Cycle versioning support |
| `data/storage/CycleEntity.kt` | `version` field suggests versioning feature was in progress |
| `data/engine/CVTemplate.kt` | Template design work |
| `navigation/NavigationKt.kt` | New screen additions/routing changes |
| `util/TelemetryCollector.kt` (decompiled) | Telemetry additions for Gate0 |

---

## RANKED RECOVERY QUEUE

### TIER 1 — CRITICAL (recover first — app cannot function without these)

```
#  Priority  File                                     Lines   Reason
  1          ProfileExtractionEngine.kt                861    Core extraction pipeline
  2          CycleViewModel.kt                       1,087    Central orchestrator
  3          GeminiService.kt                         400+    AI API client
  4          AiGateway.kt                             135     AI facade
  5          AiSystemInstructions_P1Kt.kt              80     AI prompts
  6          JobExtractionEngine.kt                    547    Job extraction
  7          CompatibilityEngine.kt                    142    ATS analysis
  8          GeneratorEngine.kt                        168    CV/CL generation
  9          CVTemplate.kt                            700+    HTML template rendering
  10         NavigationKt.kt                        2,000+    Complete navigation graph
```

### TIER 2 — HIGH (recover next — major features break without these)

```
#  Priority  File                                     Lines   Reason
  11         HistoryStore.kt                          500+    Cycle persistence
  12         UserPreferences.kt                       500+    Profile persistence
  13         OnboardingViewModel.kt                    437    Onboarding flow
  14         OnboardingDraftStore.kt                   350+    Draft persistence
  15         VantEdgeDatabase.kt                        60    DB singleton
  16         CycleDao.kt                              120+    Room DAO
  17         CycleEntity.kt                           180+    Room entity + version field
  18         CycleUiState.kt                          310     Pipeline state machine
  19         ResultViewState.kt                       170+    Result screen state
  20         ContentState.kt                          170+    Generated content state
  21         ScoreEngine.kt                             48    Profile scoring
  22         GapAnalysisEngine.kt                       38    Skill gap analysis
  23         CareerEngine.kt                           118    Text CV fallback
  24         DocxBuilder.kt                           110     DOCX builder
  25         MediaStoreExporter.kt                      70    Device export
  26         DocumentExportUseCase.kt                   56    Export orchestration
  27         RequestThrottleManager.kt                  60    API rate limiter
  28         PipelineTrace.kt                         170+    Pipeline diagnostics
  29         DebugViewModel.kt                        195     Telemetry debug screen
  30         MainActivity.kt                           87     App entry point
```

### TIER 3 — MEDIUM (recover when possible — specific feature degrades)

```
#  Priority  File                                     Lines   Reason
  31         TelemetryStorage.kt                      100+    Telemetry log reader
  32         TelemetryExportService.kt                  80    Telemetry export
  33         AppLogger.kt                              85+    In-memory logger
  34         LogDumper.kt                             170+    Diagnostic dump
  35         FailureLogBundle.kt                        40    Crash diagnostics
  36         Gate0Result.kt (engine)                   118    Gate0 data class
  37         Gate0Reason.kt (engine)                    23    Gate0 reason enum
  38         ExtractionMode.kt (engine)                  20    Extraction mode enum
  39         VantEdgeScoreResult.kt                     55    Score data class
  40         OnboardingDraftDao.kt                      30+   Draft DAO
  41         OnboardingDraftEntity.kt                   70+   Draft entity
  42         OnboardingExtractionState.kt              200+   Extraction state
  43         JobExtractionState.kt                     180+   Extraction state
  44         WorkflowState.kt (decompiled)               65+   Workflow definitions
  45         ActionType.kt (decompiled)                  20    Action definitions
  46         RestorationState.kt (decompiled)            85+   State restoration
  47         CycleStateDeserializer.kt                 120+   Gson deserializer
  48         CycleStateSerializer.kt                    65+   Gson serializer
  49         HashUtils.kt                               40+   Hashing utility
  50         OnboardingViewModelFactory.kt              65+   VM factory
```

### TIER 4 — LOW (recover last — trivial to re-implement)

```
#  Priority  File                                     Lines   Reason
  51-62      ui/screens/*Kt.kt files*             Various   Compose UI (real stubs exist)
  63-66      ui/theme/*Kt.kt files + AppColors.kt  Various   Theme wrappers (real sources exist)
  67         CycleNavEvent.kt                       130+    Navigation events
  68         CycleStage.kt                           18     Stage enum
  69         ExportEvent.kt                          85+    Export events
  70         ExportState.kt                          50+    Export state
  71         ResultError.kt                          45+    Error state
  72         ComposableSingletons*.kt files        Various   Compiler-generated
```

---

## APPENDIX: Data Model Files (44 files, decompiled-only)

These are all data classes in `data/model/`. They are not individually ranked because they are straightforward data structures. Recover in bulk after Tier 2.

| Importance | Count | Examples |
|-----------|-------|---------|
| HIGH | 10 | `GenerationCycle.kt`, `UserProfile.kt`, `CycleState.kt`, `OnboardingDraft.kt`, `JobExtractionResult.kt`, `CompatibilityRecord.kt`, `CompatibilityResult.kt`, `StructuredProfileExtraction.kt`, `GapAnalysisResult.kt`, `ConfidenceBreakdown.kt` |
| MEDIUM | 24 | `Experience.kt`, `WorkExperience.kt`, `WorkEntry.kt`, `Education.kt`, `Certification.kt`, `SkillGap.kt`, `JobData.kt`, `JobExtractionMetrics.kt`, etc. |
| LOW | 10 | `AcquisitionMode.kt`, `GenerationMode.kt`, `DocumentFormat.kt`, `GapSeverity.kt`, `JobSourceType.kt`, `ExtractionSourceType.kt`, `JobExtractionFailureReason.kt`, etc. |

---

## SUMMARY: RECOVERY EFFORT BY TIER

| Tier | Files | Est. Lines | Cumulative Lines | Effort |
|------|-------|-----------|-----------------|--------|
| **Tier 1 (CRITICAL)** | 10 | ~6,100 | 6,100 | Highest priority — core app logic |
| **Tier 2 (HIGH)** | 20 | ~3,550 | 9,650 | Major features |
| **Tier 3 (MEDIUM)** | 20 | ~1,800 | 11,450 | Specific features |
| **Tier 4 (LOW)** | ~22 | ~150,000+ | ~161,450 | UI rendering (Compose) |
| **Data Models (bulk)** | 44 | ~12,500 | ~173,950 | Data classes |

**Total HIGH recovery value**: ~152 files, ~174,000 lines  
**Effective recovery priority**: Tiers 1-3 (50 files, ~11,450 lines) represent the functional core
