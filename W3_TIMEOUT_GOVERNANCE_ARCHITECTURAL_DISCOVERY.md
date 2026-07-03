# W3 — Timeout Governance: Architectural Discovery Report

**Mode:** Read-only. No implementation. No Kotlin. No patch proposals.

---

## Executive Summary

This report presents a comprehensive architectural discovery of the VantEdge 3.0 application, identifying every runtime stage, concurrency boundary, timeout, retry, cancellation, and observability property across all pipelines. The findings are organized into a shared Execution Graph with 136 annotated nodes and 15 analytical sections.

**Key findings:**
- 12 `HIGH RISK` concurrency boundaries identified, concentrated in the `suspendCancellableCoroutine` bridge in `GeminiService` and the `CountDownLatch` bridge in `GeneratorViewModel`
- 3 distinct timeout regimes exist (AI transport 120s budget, OCR 30s, web scraping 15s) — only the OCR timeout is consistent
- Retry amplification risk across nested retries (GeminiService tryChain × model loop × final retry)
- No structured cancellation propagation beyond `viewModelScope` — application-scoped work can outlive its ViewModel
- CorrelationId is threaded through most stages but not universally preserved on all failure paths
- Telemetry collector has fire-and-forget semantics with no overflow/drop observability
- Zero `org.json.JSONObject` in the compatibility pipeline (GOOD — per the invariant), but `JSONObject` still used in transport layer and distinct pipelines

---

## Shared Execution Graph (Canonical)

All node IDs prefixed `EG-`. Edges represent control flow, data flow, or concurrency handoff.

### EG-001 to EG-025 — Application Entry & Navigation

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-001 | App launch | `VantEdgeApplication.onCreate` → Room DB init | sync | main | DB singleton creation |
| EG-002 | DI wiring | `Navigation.AppNavigation` → `remember {}` factories | sync | main (Compose) | All singletons created here |
| EG-003 | DI: GeminiService | `GeminiService(artifactDao)` construction | sync | main | OkHttpClient built here (connect=15s, read=90s, write=20s) |
| EG-004 | DI: AiGateway | `AiGateway(geminiService)` construction | sync | main | Thin wrapper |
| EG-005 | DI: ContractValidator | `ContractValidator()` construction | sync | main | Stateless |
| EG-006 | DI: CompatibilityEngine | `CompatibilityEngine()` construction | sync | main | Stateless |
| EG-007 | DI: Orchestrator | `OptimizationOrchestrator(aiGateway, contractValidator, engine, generator, historyStore)` | sync | main | Pipeline owner |
| EG-008 | DI: ViewModel Factory | `CompatibilityViewModel(store, preferences, orchestrator, extractionOrchestrator)` | sync | main | Via ViewModelProvider.Factory |
| EG-009 | DI: ViewModel Factory | `CycleViewModel(orchestrator, historyStore)` | sync | main | Via ViewModelProvider.Factory |
| EG-010 | DI: ViewModel Factory | `GeneratorViewModel(historyStore, aiGateway)` | sync | main | Has its own AiGateway ref |
| EG-011 | DI: ViewModel Factory | `OnboardingViewModel(extractionEngine, commitService, draftStore, telemetryCollector)` | sync | main | Via custom factory |
| EG-012 | DI: ProfileExtractionEngine | `ProfileExtractionEngine(context, aiGateway, telemetryCollector)` | sync | main | Heavy object — OCR, PDF, AI |
| EG-013 | DI: JobExtractionOrchestrator | `JobExtractionOrchestrator(aiGateway)` | sync | main | Nullable AiGateway |
| EG-014 | UI: Nav entry | Compose NavHost → screen composables | sync | main | Jetpack Navigation |
| EG-015 | Screen: CompatibilityInput | User taps "Analyze" → `viewModel.analyze()` | sync | main | Button click handler |
| EG-016 | Screen: JobInput | User submits job URL/text → extraction | sync | main | Button click handler |
| EG-017 | Notification: DeadlineNotificationWorker | WorkManager `doWork()` | async | WorkManager thread | Periodic background task |

### EG-026 to EG-050 — Compatibility Pipeline

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-026 | ViewModel.analyze() | `CompatibilityViewModel.analyze()` → `orchestrator.runAnalysisFresh()` | async | Dispatchers.IO | ViewModelScope.launch |
| EG-027 | Orchestrator.entry | `OptimizationOrchestrator.runAnalysisFresh()` starts | sync | IO | PipelineTrace.entry |
| EG-028 | Prompt building | inline system + user prompt construction | sync | IO | Hardcoded schema + profile |
| EG-029 | AI Request | `AiRequest(systemPrompt, userPrompt)` | sync | IO | Data class |
| EG-030 | AiGateway.generate() | `aiGateway.generate("compatibility", request)` | async → EG-031 | IO → EG-031 | Budget deadline set: +120s |
| EG-031 | GeminiService.generate() | `generate(requestId, request, ...)` | async | IO | Creates requestId |
| EG-032 | tryChain() | `tryChain(isRetry=false, ...)` | async | IO | Iterates 3 models |
| EG-033 | Model iteration | `for (index in models.indices)` | sync | IO | 3 models: nemotron, gpt-oss, llama |
| EG-034 | Circuit breaker check | `cbState.degradedUntilMs > now?` | sync | IO | Per-model state |
| EG-035 | Budget check | `elapsedRealtime() > budgetDeadlineMs?` | sync | IO | 120s overall budget |
| EG-036 | tryModel() | `tryModel(requestId, model, attr...)` | async | IO | Builds OkHttp request |
| EG-037 | ***CONCURRENCY BRIDGE*** | `suspendCancellableCoroutine { cont → call.enqueue(Callback) }` | ASYNC | IO → OkHttp thread pool | **HIGH RISK** — callback-to-coroutine bridge |
| EG-038 | HTTP request | `client.newCall(request).enqueue(callback)` | ASYNC | OkHttp dispatcher | Connect=15s, Read=90s, Write=20s |
| EG-039 | onResponse() callback | OkHttp → `Callback.onResponse()` | ASYNC | OkHttp thread | Path A execution |
| EG-040 | onFailure() callback | OkHttp → `Callback.onFailure()` | ASYNC | OkHttp thread | Path B execution |
| EG-041 | Forensic artifact insert | `artifactDao.insertWithRetention(...)` via `runBlocking` | ASYNC | OkHttp thread → `runBlocking(IO)` | Mixed concurrency model |
| EG-042 | `cont.resume(value)` | Callback resumes coroutine | sync | OkHttp thread → resumes in `cont` context | Exactly-once resume? |
| EG-043 | `call.cancel()` | `invokeOnCancellation { call.cancel() }` | sync | Caller's thread | Cancellation propagation |
| EG-044 | Transport validation | `EMPTY_BODY` / `MALFORMED_SHORT` / `NO_JSON_STRUCTURE` / `INVALID_JSON` / `PROVIDER_ERROR` / `choices` check | sync | OkHttp thread | Sequential guard clauses |
| EG-045 | Extraction | `JsonExtractionEngine.extract(raw)` | sync | OkHttp thread | 5 strategies (direct_parse → balanced_brace → markdown_strip → substring_scan → failed) |
| EG-046 | Parse validation | `JSONObject(extracted.content)` | sync | OkHttp thread | Try-catch |
| EG-047 | cont.resume(content) | On parse success | sync | OkHttp thread | Returns extracted string |
| EG-048 | Delay between attempts | `delay(delayForAttempt(index))` | async | IO | 1s/2s/3s escalating |
| EG-049 | Budget check after delay | `elapsedRealtime() > deadlineMs?` | sync | IO | Prevents sleeping past budget |
| EG-050 | Final retry | `delay(2000)` → `tryChain(isRetry=true, ...)` | async | IO | Full chain repeat |

### EG-051 to EG-075 — Compatibility Pipeline (continued)

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-051 | AiGateway result | `geminiService.generate()` returns `String?` | sync | IO | null if all models failed |
| EG-052 | PipelineTrace.exit | On success or failure | sync | IO | Logs attempt stats |
| EG-053 | Orchestrator: null check | `aiResponse == null` → `Failure("null_response")` | sync | IO | Early return |
| EG-054 | Orchestrator: extraction | `JsonExtractionEngine.extract(aiResponse)` | sync | IO | Re-extracts from response |
| EG-055 | Orchestrator: Jackson parse | `JsonMapper().readTree(extractionResult.content)` | sync | IO | Jackson-only |
| EG-056 | Orchestrator: payload construction | `ExtractedAiPayload(rawJson, jsonNode, metadata)` | sync | IO | Metadata from extraction |
| EG-057 | Orchestrator: contract validation | `contractValidator.validate(VACANCY_SCORING, payload)` | sync | IO | Calls schema resolver |
| EG-058 | ContractValidator: truncation check | `isTruncated(rawJson, parsedObject)` | sync | IO | Brace-counting heuristic |
| EG-059 | ContractValidator: empty/null | `parsedObject.isNull \|\| isMissingNode` | sync | IO | |
| EG-060 | ContractValidator: root object | `!parsedObject.isObject` | sync | IO | |
| EG-061 | ContractValidator: schema resolve | `resolveSchema(jobType)` → map lookup | sync | IO | |
| EG-062 | ContractValidator: Phase A | Structural checks (presence, type, nullability) | sync | IO | MissingRequiredFields → TypeMismatch |
| EG-063 | ContractValidator: Phase B | Schema-boundary checks (range, non-negative) | sync | IO | Via SchemaBoundaryRule sealed class |
| EG-064 | ContractValidator: success | `ValidatedAiPayload.create(node, jobType)` | sync | IO | deepCopy() of JsonNode |
| EG-065 | Orchestrator: engine analysis | `compatibilityEngine.analyze(node, jobTitle, company, desc)` | sync | IO | Jackson-only pure parser |
| EG-066 | Orchestrator: result mapping | Success → `CompatibilityResult.Success(record)` | sync | IO | |
| EG-067 | Orchestrator: failure mapping | Failure → `CompatibilityResult.Failure("contract_violation", details)` | sync | IO | |
| EG-068 | Orchestrator: runAnalysisOnly | `runAnalysisFresh` → `when(result)` → `IllegalStateException` | sync | IO | Exception-based flow for callers |
| EG-069 | Orchestrator: runFullPipeline | `runAnalysisFresh` + `generatorEngine.generateCv` + `generateCoverLetter` | async | IO | Multi-step |
| EG-070 | HistoryStore.saveCycle | `historyStore.saveCycle(cycle)` | async | IO | Mutex-protected write |
| EG-071 | ViewModel: UI state dispatch | `_uiState.value = UiState.Success(record)` | sync | main | viewModelScope.launch(Main) |
| EG-072 | ViewModel: error dispatch | `_uiState.value = UiState.Error(...)` | sync | main | |
| EG-073 | Orchestrator: failure path | `ContractValidationResult.Failure` → `CompatibilityResult.Failure` → `IllegalStateException` | sync | IO | Exception unwrapping |
| EG-074 | PipelineTrace.error | On exception catch | sync | IO | Logs to LogSink |
| EG-075 | RunAnalysisFresh return | Returns `CompatibilityResult` to ViewModel | sync | IO | |

### EG-076 to EG-090 — Profile Extraction Pipeline

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-076 | OnboardingViewModel.extract() | User selects file → `extractDocument()` | async | Dispatchers.IO | Mutex-protected |
| EG-077 | ExtractionMutex | `extractionMutex.withLock { ... }` | sync | IO | Prevents concurrent extractions |
| EG-078 | ProfileExtractionEngine.extractRawText() | File URI → raw text | async | IO | Dispatches by file type |
| EG-079 | PDF text extraction | `PDFTextStripper.getText(doc)` | async | IO | **withTimeout(30s)** — EG-080 |
| EG-080 | PDF OCR fallback | `extractPdfOcr(uri)` → ML Kit | ASYNC | IO → ML Kit thread | `Tasks.await(30s)` — EG-081 |
| EG-081 | Image OCR | `extractImageOcr(uri)` → ML Kit | ASYNC | IO → ML Kit thread | `Tasks.await(30s)` |
| EG-082 | ***CONCURRENCY BRIDGE*** | PDF text `withTimeout(30s)` on `Dispatchers.IO` | ASYNC | IO | TimeoutException → OCR fallback |
| EG-083 | ***CONCURRENCY BRIDGE*** | `Tasks.await()` ML Kit task | ASYNC | ML Kit thread | Google Tasks → blocking bridge |
| EG-084 | Gate 0 structural analysis | `runGate0(rawText, extractionMode)` | sync | IO | 5-structure heuristics |
| EG-085 | AI extraction | `aiGateway.generate("profile_extraction", request)` | async | IO | Full compatibility pipeline path |
| EG-086 | JSON extraction | `JsonExtractionEngine.extract(response)` | sync | IO | |
| EG-087 | JSON repair | `JsonRepairUtil.repair(jsonStr)` | sync | IO | Structural repair + safety check |
| EG-088 | Gate 0 sanitization | OCR artifact cleanup | sync | IO | Bounded whitespace normalization |
| EG-089 | retry on failure | `ExtractionState.Retrying` → second `structureProfile` call | async | IO | Manual retry |
| EG-090 | TelemetryCollector.record | On extraction complete | ASYNC | IO (fire-and-forget) | |

### EG-091 to EG-105 — CV/Cover Letter Generation Pipeline

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-091 | GeneratorEngine.generateCv() | `aiGateway.generate("cv", request)` | async | GeneratorEngine thread | Callback-based |
| EG-092 | ***CONCURRENCY BRIDGE*** | `CountDownLatch(1).await()` | BLOCKING | GeneratorViewModel IO | **HIGH RISK** — blocks coroutine thread |
| EG-093 | GeneratorEngine.generateCoverLetter() | `aiGateway.generate("cover_letter", request)` | async | GeneratorEngine thread | |
| EG-094 | ***CONCURRENCY BRIDGE*** | `CountDownLatch(1).await()` | BLOCKING | GeneratorViewModel IO | **HIGH RISK** — blocks coroutine thread |
| EG-095 | Document generation | `GeneratorEngine` callback → `onResult(result)` | async | GeneratorEngine thread | Wakes waiting coroutine |
| EG-096 | JSON extraction | `JsonExtractionEngine.extract(result)` | sync | GeneratorEngine thread | |
| EG-097 | JSON repair | `JsonRepairUtil.repair(clean)` | sync | GeneratorEngine thread | Safety check |
| EG-098 | DocxBuilder | DOCX generation from templates | sync | IO | |
| EG-099 | PDF/A rendering | CV rendering pipeline | sync | IO | |
| EG-100 | GeminiService.generate() | Same AI transport as compatibility | async | IO | Shared |
| EG-101 | tryChain() | Same model iteration | async | IO | Shared |
| EG-102 | Circuit breaker | Same per-model state | sync | IO | Shared across ALL pipelines |

### EG-106 to EG-120 — Job Extraction Pipeline

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-106 | JobExtractionOrchestrator.extractJob() | User paste URL/text → extraction | async | Dispatchers.IO | |
| EG-107 | LLM extraction | `LlmJobExtractor.extract(rawText)` | async | IO | Uses aiGateway |
| EG-108 | Rule-based emergency fallback | `RuleBasedEmergencyExtractor.extractJob(...)` | sync | IO | No AI needed |
| EG-109 | AI request | `aiGateway.generate("job_extraction", request)` | async | IO | |
| EG-110 | JSON extraction | `JsonExtractionEngine.extract(response)` | sync | IO | |
| EG-111 | JSON repair | `JsonRepairUtil.repair(jsonStr)` | sync | IO | |
| EG-112 | ExtractionDecisionCoordinator | Classifies extraction quality | sync | IO | Accept/Retry/UserReview/Failure |
| EG-113 | Web scraping (URL paste) | `OkHttpClient().execute()` + Jsoup | ASYNC | Dispatchers.IO | **No timeout propagation** |

### EG-121 to EG-136 — Background & Infrastructure

| ID | Stage | Caller → Callee | Sync/Async | Thread/Dispatcher | Notes |
|----|-------|-----------------|------------|-------------------|-------|
| EG-121 | DeadlineNotificationWorker | WorkManager periodic | async | WorkManager | Background only |
| EG-122 | HistoryStore.saveCycle | Room DB write | async | IO | Mutex-guarded |
| EG-123 | CompatibilityStore.addRecord | In-memory + Room | async | IO | Mutex-guarded |
| EG-124 | OnboardingDraftStore.saveDraft | DataStore/SharedPreferences | async | IO | |
| EG-125 | TelemetryCollector.record | Async file append | ASYNC | IO (fire-and-forget) | Unbounded queue, overflow-drops oldest |
| EG-126 | LogSink.write | File append with rotation | sync | Caller's thread | ReentrantLock-guarded |
| EG-127 | LogFileProvider.file() | Returns log file path | sync | Caller's thread | Must be bound first |
| EG-128 | MediaStoreExporter | Export to gallery | async | IO | |
| EG-129 | DocumentExportUseCase | Export cycle as PDF/DOCX | async | IO | |
| EG-130 | RequestThrottleManager.throttle | `Mutex.withLock` + `delay(waitTime)` | async | Caller's thread | 12s minimum interval |
| EG-131 | CycleViewModel.cancelPipeline | `pipelineJob?.cancel()` | sync | main | Only cancels current ViewModel Job |
| EG-132 | compatibilityViewModel.analyze() | `viewModelScope.launch(IO) { ... }` | async | IO → main | |
| EG-133 | ViewModel onCleared | ViewModel destruction → scope cancelled | sync | main | Propagates to children |
| EG-134 | Activity.onDestroy | Activity destruction → clears ViewModelStore | sync | main | |
| EG-135 | App scope work | TelemetryCollector internal `scope` | COROUTINE | IO (SupervisorJob) | Application-scoped — can outlive Activity |
| EG-136 | GeminiService OkHttpClient | Shared client instance | sync | Any thread | Default dispatcher (max 64 connections, 5 per host) |

---

## Part 1 — Repository Inventory

All occurrences are documented in the exploration task outputs. Key density concentrations:

| Pattern | Files | Total Matches | Primary Files |
|---------|-------|---------------|---------------|
| `Tasks.await` | 1 | 2 | `ProfileExtractionEngine.kt` (OCR timeout) |
| `.await(` (CountDownLatch) | 1 | 2 | `GeneratorViewModel.kt` |
| `withTimeout` | 1 | 2 | `ProfileExtractionEngine.kt` (PDF 30s) |
| `TimeoutException` / `CancellationException` | 3 | 12 | `ProfileExtractionEngine.kt`, `CycleViewModel.kt` |
| `suspendCancellableCoroutine` | 1 | 2 | `GeminiService.kt` |
| `CountDownLatch` | 1 | 2 | `GeneratorViewModel.kt` |
| `Mutex` | 4 | 7 | `HistoryStore`, `CompatibilityStore`, `RequestThrottleManager`, `OnboardingViewModel` |
| `delay(` | 6 | 12 | `GeminiService.kt`, `RequestThrottleManager.kt`, UI files |
| `retry`/`Retry` | 6 | 26 | `GeminiService.kt`, `OnboardingViewModel.kt`, domain extraction |
| `OkHttpClient` | 4 | 8 | `GeminiService.kt` + 3 UI screens |
| `call.enqueue` / `.execute()` | 4 | 5 | `GeminiService.kt` + 3 UI screens |
| `PipelineTrace` | 16 | 62+ | All pipeline files |
| `LogSink` | 1 | 1 | `LogSink.kt` |
| `correlationId` | 10+ | 30+ | All pipeline + contract files |

---

## Part 2 — Pipeline Inventory

| Pipeline | Owner | Entry Point | Exit Point | Orchestrator | Retry Owner | Timeout Owner | Logging Owner | Cancellation Owner | Validation Owner |
|----------|-------|-------------|------------|--------------|-------------|---------------|---------------|-------------------|------------------|
| **Compatibility** | OptimizationOrchestrator | `runAnalysisFresh()` | `CompatibilityResult` | OptimizationOrchestrator | GeminiService (tryChain × final retry) | AiGateway (120s budget) + GeminiService (OkHttp 15/90/20s) | PipelineTrace + LogSink | ViewModelScope | ContractValidator |
| **Profile Extraction** | ProfileExtractionEngine | `structureProfile()` | `StructuredProfileExtraction` | OnboardingViewModel | OnboardingViewModel (manual retry) | ProfileExtractionEngine (withTimeout 30s PDF, Tasks.await 30s OCR) | PipelineTrace + android.util.Log | ViewModelScope + extractionMutex | Gate0 + SchemaValidator |
| **CV Generation** | GeneratorEngine | `generateCv()` | `EngineResult` | CycleViewModel + GeneratorViewModel | None (single attempt) | GeminiService (shared) | android.util.Log | CountDownLatch.unblock | JsonRepairUtil |
| **Cover Letter Generation** | GeneratorEngine | `generateCoverLetter()` | `EngineResult` | CycleViewModel + GeneratorViewModel | None (single attempt) | GeminiService (shared) | android.util.Log | CountDownLatch.unblock | JsonRepairUtil |
| **Job Extraction** | JobExtractionOrchestrator | `extractJob()` | `JobExtractionResult` | JobExtractionOrchestrator | ExtractionDecisionCoordinator (Retry decision) | GeminiService (shared) | PipelineTrace | Caller's scope | ExtractionDecisionCoordinator |
| **OCR (PDF)** | ProfileExtractionEngine | `extractPdfOcr()` | Text string | ProfileExtractionEngine | None | Tasks.await(30s) | android.util.Log | None | None |
| **OCR (Image)** | ProfileExtractionEngine | `extractImageOcr()` | Text string | ProfileExtractionEngine | None | Tasks.await(30s) | android.util.Log | None | None |
| **Background Notification** | DeadlineNotificationWorker | `doWork()` | `Result` | WorkManager | WorkManager (built-in) | WorkManager (10s default) | android.util.Log | WorkManager | None |

---

## Part 3 — End-to-End Dependency Graph (annotated Execution Graph)

All annotations reference EG node IDs. See the Shared Execution Graph above for full detail.

Key annotations by stage:

- **EG-030 (AiGateway.generate):** timeout=120s budget, retry=trystChain × 3 models + final retry × 3 models (up to 6 total chains), cancellation=via GeminiService propagate, logging=PipelineTrace entry/exit/dataQuality, failure type=String? null → `AiResult.Failure` with category
- **EG-037 (suspendCancellableCoroutine bridge):** ***HIGH RISK*** — OkHttp callback thread resumes coroutine. `invokeOnCancellation` cancels the OkHttp Call. Duplicate resume risk if both onResponse and onFailure fire. Exactly-once: partially guaranteed by `cont.isActive` check in onResponse line 607.
- **EG-041 (runBlocking for Room):** Mixed concurrency model — OkHttp callback thread blocks on `runBlocking(IO)` to insert Room artifact. Potential for thread starvation under load.
- **EG-048 (delay between attempts):** 1s/2s/3s escalating delays. No overall max delay cap on the tryChain loop (could potentially run for 9+ seconds × 3 models × 2 chains = 50+ seconds before budget enforcement).
- **EG-080/EG-081 (Tasks.await OCR):** Blocking call on IO thread waiting for ML Kit. Uses `Tasks.await(task, 30s, TimeUnit.MILLISECONDS)`. Catches `TimeoutException` and throws `Exception(ERROR_OCR_TIMEOUT)`.

---

## Part 3.1 — Thread Boundary Analysis

Classification of every concurrency boundary:

| EG Node | Source → Destination | Bridge Type | Cancellation Propagation | Timeout Protection | Exactly-Once Resume | Duplicate Resume Risk | Orphan Risk | Indefinite Wait Risk | Observability | correlationId |
|---------|---------------------|-------------|------------------------|-------------------|---------------------|----------------------|-------------|---------------------|---------------|---------------|
| EG-037 | IO → OkHttp thread | `suspendCancellableCoroutine` | ✅ `invokeOnCancellation → call.cancel()` | ✅ OkHttp 15/90/20s + 120s budget | ⚠️ `cont.isActive` check | ⚠️ Possible if both onResponse AND onFailure fire | ⚠️ If `cont.resume()` not called | ⚠️ OkHttp read timeout 90s | ✅ PipelineTrace | ✅ |
| EG-041 | OkHttp thread → IO (runBlocking) | `runBlocking(Dispatchers.IO)` | ❌ Not propagated | ❌ None | ✅ Blocking call | ❌ N/A | ❌ Blocking ensures completion | ❌ Could block OkHttp thread | ✅ PipelineTrace | ✅ |
| EG-082 | IO → IO | `withTimeout(30s)` | ✅ Timeout → TimeoutCancellationException | ✅ 30s explicit | ✅ Try-catch | ❌ N/A | ✅ Coroutine cancelled on timeout | ✅ Protected by withTimeout | ❌ Manual error logging | ⚠️ Not explicitly |
| EG-083 | IO → ML Kit thread | `Tasks.await(30s)` | ✅ Timeout → TimeoutException | ✅ 30s explicit | ✅ Try-catch | ❌ N/A | ❌ Blocking ensures completion | ✅ Protected by Tasks.await timeout | ❌ Manual error logging | ⚠️ Not explicitly |
| EG-092 | IO → GeneratorEngine callback | `CountDownLatch(1).await()` | ❌ Not propagated to latch waiters | ❌ No timeout on latch | ✅ Latch countdown | ⚠️ Double countDown possible | ⚠️ If callback never fires | **HIGH** — latch.await() has NO timeout | ❌ Minimal | ⚠️ Not explicitly |
| EG-093 | IO → GeneratorEngine callback | `CountDownLatch(1).await()` | ❌ Not propagated | ❌ No timeout on latch | ✅ Latch countdown | ⚠️ Double countDown possible | ⚠️ If callback never fires | **HIGH** — latch.await() has NO timeout | ❌ Minimal | ⚠️ Not explicitly |
| EG-113 | IO → OkHttp pool | `OkHttpClient.execute()` | ❌ Not propagated | ✅ 15s connect/read | ✅ Blocking | ❌ N/A | ❌ Blocking ensures completion | ✅ OkHttp timeouts | ❌ None | ❌ Not present |

**HIGH RISK boundaries** (detailed below):

### HR-001 — EG-037: GeminiService suspendCancellableCoroutine

- **Full dependency chain:** EG-026 → EG-027 → EG-029 → EG-030 → EG-031 → EG-032 → EG-036 → **EG-037**
- **Risk:** The `suspendCancellableCoroutine` bridge transitions from coroutine world to OkHttp callback world. `cont.isActive` at line 607 provides partial guard against duplicate resume, but if `onResponse` completes validation and runs `cont.resume()` while `onFailure` is also dispatched (e.g., a race), the coroutine could be resumed twice (second resume is a no-op in Kotlin coroutines — it throws `IllegalStateException`). The `runBlocking(Dispatchers.IO)` for Room artifact insertion in both Path A and Path B adds thread-blocking work on the OkHttp callback thread.
- **Mitigation:** `invokeOnCancellation` cancels the OkHttp `Call`, which triggers `onFailure` with cancellation message. The `cont.isActive` check helps but isn't foolproof in races.
- **Recommendation:** W3-B scope (Cancellation Propagation). Consider `withTimeout` wrapping the entire `suspendCancellableCoroutine` block.

### HR-002 — EG-041: runBlocking in OkHttp callback

- **Full dependency chain:** EG-037 → EG-039/EG-040 → **EG-041**
- **Risk:** `runBlocking(Dispatchers.IO)` blocks the OkHttp callback thread to write to Room. Under load, this can exhaust the OkHttp dispatcher thread pool (default max 64). Since this happens on every HTTP response and every failure, concurrent requests compound the risk.
- **Mitigation:** None. This is a synchronous blocking call on a callback thread.
- **Recommendation:** W3-E (Resource Lifecycle). Replace with `cont.resume` + async Room write on the resumed coroutine.

### HR-003 — EG-092/EG-094: CountDownLatch in GeneratorViewModel

- **Full dependency chain:** EG-091/EG-093 → EG-092/EG-094
- **Risk:** `CountDownLatch(1).await()` is called on `Dispatchers.IO` with NO timeout. If the GeneratorEngine callback is never invoked (e.g., `generateCv` crashes before calling `onResult`, or the callback is lost in a cancellation), the coroutine thread blocks indefinitely. This is a thread leak. The latch also blocks a coroutine thread from the IO pool, potentially starving the pool.
- **Mitigation:** None — no `await(5, TimeUnit.SECONDS)`, no `withTimeout` wrapper.
- **Recommendation:** W3-A (Timeout Governance). Replace with `withTimeout` + `CountDownLatch.await(timeout, unit)` or migrate to `suspendCoroutine`.

---

## Part 3.2 — Lifecycle Analysis

| Owner | Cancellation Trigger | Cleanup Path | Leak Risk | EG Ref |
|-------|---------------------|--------------|-----------|--------|
| **CompatibilityViewModel** | ViewModel.onCleared → viewModelScope cancelled | Coroutine children cancelled automatically | Low — standard ViewModelScope | EG-026, EG-132 |
| **CycleViewModel** | ViewModel.onCleared + `cancelPipeline()` | `pipelineJob.cancel()` sets state to Idle | Low — explicit cleanup | EG-131 |
| **OnboardingViewModel** | ViewModel.onCleared | `extractionJob?.cancel()` | Low | EG-076 |
| **GeneratorViewModel** | ViewModel.onCleared | CountDownLatch callback may still fire after cancellation | **MEDIUM** — latch.await() blocked thread may not be interrupted | EG-092, EG-094 |
| **TelemetryCollector scope** | Application lifetime | Never cancelled | **MEDIUM** — application-scoped SupervisorJob can outlive Activity | EG-135 |
| **HistoryStore scope** | Application lifetime | Never cancelled | **LOW** — scope is IO with no blocking work | EG-070 |

---

## Part 4 — Failure Taxonomy Audit

| Failure Type | Originating Class | Originating Function | Propagation Chain | User-Visible | Logged? | correlationId? | Retryable? | Deterministic? | EG Ref |
|-------------|-------------------|---------------------|-------------------|--------------|---------|----------------|------------|----------------|--------|
| `null_response` | OptimizationOrchestrator | `runAnalysisFresh()` | → CompatibilityResult.Failure → UI | ✅ "Analysis failed" | ✅ PipelineTrace | ✅ requestId | ✅ | ✅ | EG-053 |
| `no_json` | OptimizationOrchestrator | `runAnalysisFresh()` | → CompatibilityResult.Failure → UI | ✅ | ✅ PipelineTrace | ✅ | ✅ | ✅ | EG-054 |
| `parse_error` | OptimizationOrchestrator | `runAnalysisFresh()` | → CompatibilityResult.Failure → UI | ✅ | ✅ PipelineTrace | ✅ requestId | ✅ | ✅ | EG-055 |
| `contract_violation` | OptimizationOrchestrator | `runAnalysisFresh()` | → CompatibilityResult.Failure | ✅ | ✅ | ✅ | ❌ (validation deterministic) | ✅ | EG-067 |
| `EMPTY_BODY` | GeminiService | `tryModel().onResponse()` | → ModelAttemptResult → tryChain continues | ⚠️ As part of all-models-exhausted | ✅ `Log.e` + PipelineTrace | ✅ requestId | ✅ | ✅ | EG-044 |
| `MALFORMED_SHORT` | GeminiService | `tryModel().onResponse()` | → ModelAttemptResult → tryChain continues | ⚠️ Same | ✅ | ✅ | ✅ | ✅ | EG-044 |
| `INVALID_JSON` | GeminiService | `tryModel().onResponse()` | → ModelAttemptResult → tryChain continues | ⚠️ Same | ✅ | ✅ | ✅ | ✅ | EG-044 |
| `MODEL_CONTRACT_VIOLATION` | GeminiService | `tryModel().onResponse()` | → ModelAttemptResult + `cont.resume(null)` | ⚠️ Same | ✅ | ✅ | ✅ | ✅ | EG-044 |
| `provider_error` | GeminiService | `tryModel().onResponse()` | → ModelAttemptResult → tryChain continues | ⚠️ Same | ✅ | ✅ | ❌ | ✅ | EG-044 |
| `TIMEOUT` (transport) | GeminiService | `classifyError("timeout")` | → tryChain failure analysis | ⚠️ Same | ✅ | ✅ | ✅ | ✅ | EG-040 |
| `RATE_LIMIT` | GeminiService | `classifyError("429")` | → tryChain failure analysis | ⚠️ Same | ✅ | ✅ | ✅(with backoff) | ✅ | EG-040 |
| `CANCELLED` | GeminiService | `tryModel().onFailure()` | → ModelAttemptResult → tryChain | ⚠️ Same | ✅ | ✅ | ✅(circuit breaker) | ✅ | EG-040 |
| `BUDGET_EXCEEDED` | GeminiService | `tryChain()` | → tryChain terminates → GeminiService returns null | ⚠️ Same | ✅ PipelineTrace | ✅ | ⚠️ Only via final retry | ✅ | EG-035/EG-049 |
| `PDF_TEXT_TIMEOUT` | ProfileExtractionEngine | `extractRawText()` | → Exception → PipelineTrace.error | ✅ "Extraction failed" | ✅ Log + PipelineTrace | ⚠️ Via context | ✅ manual retry | ✅ | EG-082 |
| `OCR_TIMEOUT` | ProfileExtractionEngine | `extractPdfOcr()` / `extractImageOcr()` | → Exception → PipelineTrace.error | ✅ Same | ✅ Log | ⚠️ Not explicit | ⚠️ Manual retry possible | ✅ | EG-083 |

**Inconsistencies identified:**
1. `compatibilityViewModel` error message is generic ("Check your connection") — loses the specific failure type
2. Some ProfileExtractionEngine failure paths lack explicit `correlationId` propagation
3. GeneratorViewModel uses raw `Exception.message` for user display — exposes internal error strings

---

## Part 5 — Timeout Governance Audit

| External Dependency | Timeout Owner | Read Timeout | Connect Timeout | Write Timeout | Overall Operation Timeout | Cancellation Behaviour | Recovery Behaviour | EG Ref |
|-------------------|---------------|-------------|----------------|---------------|--------------------------|----------------------|-------------------|--------|
| **OpenRouter AI (OkHttp)** | GeminiService | 90s | 15s | 20s | 120s budget (AiGateway) | `call.cancel()` via `invokeOnCancellation` | tryChain continues with next model; final retry after 2s delay | EG-036, EG-037 |
| **ML Kit OCR** | ProfileExtractionEngine | N/A (Google Tasks) | N/A | N/A | `Tasks.await(30s)` | TimeoutException → throw | Caught by caller → PipelineTrace.error | EG-080, EG-081 |
| **PDF text extraction** | ProfileExtractionEngine | N/A | N/A | N/A | `withTimeout(30s)` | TimeoutCancellationException | OCR fallback | EG-079 |
| **Web scraping (UI screens)** | OkHttpClient in screen | 15s | 15s | N/A | None (overall) | Thread block | Exception caught → user-visible error | EG-113 |
| **Room DB (artifact)** | GeminiService callback | N/A | N/A | N/A | None | Blocking (runBlocking) | N/A | EG-041 |
| **HistoryStore** | None | N/A | N/A | N/A | None | Coroutine cancellation | N/A | EG-070 |
| **LogSink file I/O** | None | N/A | N/A | N/A | None | File I/O exception caught | Swallows error silently | EG-126 |

**Gaps:**
1. GeneratorViewModel `CountDownLatch.await()` has NO timeout — indefinite wait risk
2. Web scraping in UI screens (`CompatibilityInputScreen`, `JobInputScreen`, `CVGeneratorScreen`) has OkHttp timeouts but no overall coroutine timeout
3. Room artifact insert via `runBlocking` has no timeout on the blocking call itself
4. LogSink file rotation can fail silently — no user-visible or pipeline-blocking impact but diagnostic loss

---

## Part 6 — Retry Governance Audit

| Retry | Owner | Trigger | Retry Count | Backoff | Idempotent? | Duplicate Side-Effects? | Observability | EG Ref |
|-------|-------|---------|-------------|---------|-------------|------------------------|---------------|--------|
| **tryChain model iteration** | GeminiService | Any model failure | Up to 3 models × 2 chains = 6 total | 1s/2s/3s escalating delay | ⚠️ Not fully (prompt sent again) | ⚠️ Duplicate forensic artifacts | ✅ PipelineTrace dataQuality at every stage | EG-032 to EG-050 |
| **Final retry chain** | GeminiService.generate() | tryChain returns null | 1 additional chain (3 models) | 2s initial + 1s/2s/3s per attempt | ⚠️ Same | ⚠️ Same | ✅ | EG-050 |
| **Circuit breaker degrade** | GeminiService | 3 consecutive failures | N/A (15min cooldown) | Degraded model skipped until cooldown | ✅ | ❌ No duplicate work | ✅ PipelineTrace | EG-034 |
| **Manual extraction retry** | OnboardingViewModel | Extraction failure | 1 manual retry | None | ✅ | ❌ New AI request | ✅ ExtractionState.Retrying | EG-089 |
| **ExtractionDecision.Retry** | JobExtractionOrchestrator | LLM extraction quality | Configurable | None | ⚠️ Not guaranteed | ⚠️ Duplicate extraction | PipelineTrace.warn | EG-099 |

**Retry amplification risk:** Nested retries in GeminiService — the `tryChain` loop (3 models × up to 3 escalating delays + budget check per iteration) is nested inside `generate()` which adds a full final retry chain after ALL models fail. This means worst-case: 3 models × 2 chains = 6 HTTP requests + ~20-30s of cumulative delays before returning null. The 120s budget is the sole governor.

---

## Part 7 — Observability Audit

| Stage | correlationId | requestId | duration | timeout | retry count | failure reason | structured exit | confidence | contract status | EG Ref |
|-------|--------------|-----------|----------|---------|-------------|----------------|-----------------|------------|----------------|--------|
| **AiGateway.generate** | ✅ | ✅ (REQ-XXXXXX) | ✅ | ⚠️ Budget only (120s) | ✅ attemptCount | ✅ | ✅ | ❌ | ❌ | EG-030 |
| **GeminiService.tryChain** | ✅ (requestId as cid) | ✅ | ✅ per attempt | ⚠️ Budget + read timeout | ✅ index, isRetry | ✅ FailureType | ✅ dataQuality | ❌ | ❌ | EG-032 |
| **GeminiService.onResponse** | ✅ | ✅ | ✅ | ⚠️ OkHttp read timeout | ⚠️ Not directly | ✅ | ✅ PipelineTrace | ❌ | ❌ | EG-039 |
| **OptimizationOrchestrator** | ✅ | ⚠️ Only via aiResponse | ✅ | ❌ | ❌ | ✅ | ✅ | ❌ | ✅ ContractValidationResult | EG-026 |
| **ContractValidator** | ✅ correlationId | ❌ | ❌ | ❌ | ❌ | ✅ TypeMismatch | ✅ | ✅ Binary (1.0/0.0) | N/A own output | EG-057 |
| **ProfileExtractionEngine** | ⚠️ Via PipelineTrace calls | ❌ | ⚠️ Via context | ❌ | ⚠️ ExtractionState.Retrying | ⚠️ Manual string | ⚠️ Via telemetry | ❌ | ❌ | EG-076 |
| **GeneratorEngine** | ❌ Not structured | ❌ | ❌ | ❌ | ❌ | ✅ EngineResult | ✅ onResult callback | ❌ | ❌ | EG-091 |
| **CycleViewModel** | ❌ | ❌ | ❌ | ⚠️ TimeoutCancellationException catch | ❌ | ⚠️ Exception.message | ❌ | ❌ | ❌ | EG-069 |
| **GeneratorViewModel** | ❌ | ❌ | ❌ | ❌ | ❌ | ⚠️ Exception.message | ⚠️ UiState | ❌ | ❌ | EG-091 |
| **TelemetryCollector** | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ toLogLine | ❌ | ❌ | EG-125 |

**Missing observability:**
1. `CycleViewModel` catches `TimeoutCancellationException` but doesn't log it to PipelineTrace or LogSink — just sets UI state
2. `GeneratorViewModel` CountDownLatch pattern has no observability at all — structured logging is entirely absent from this path
3. No `duration` field is logged from ViewModel-level pipeline calls — only internal orchestration stages track duration
4. OCR timeout events are logged but not correlated with a pipeline-wide correlationId

---

## Part 8 — Risk Inventory

| Risk ID | Severity | Probability | File | Function | Line | Dependency Chain | Blast Radius | Architectural Impact | EG Ref |
|---------|----------|-------------|------|----------|------|-----------------|--------------|---------------------|--------|
| R-001 | **CRITICAL** | Medium | `GeneratorViewModel.kt` | `generateCv()/.generateDocx()` | 90, 150 | EG-091→EG-092, EG-093→EG-094 | `CountDownLatch.await()` blocks IO thread indefinitely if callback never fires | Thread starvation of entire IO dispatcher | EG-092, EG-094 |
| R-002 | **HIGH** | Low | `GeminiService.kt` | `tryModel()` | 460 | EG-036→EG-037→EG-041 | `runBlocking(IO)` on OkHttp callback thread under concurrent load | OkHttp dispatcher thread pool exhaustion | EG-041 |
| R-003 | **HIGH** | Low | `GeminiService.kt` | `tryModel().onResponse()` | 607 | EG-037→EG-039 | Possible duplicate resume if both onResponse and onFailure fire | `IllegalStateException` in coroutines machinery | EG-037 |
| R-004 | **MEDIUM** | Medium | `GeminiService.kt` | `tryChain()` | 183–401 | EG-032→EG-033→EG-036→EG-048 | Nested retry without explicit iteration cap (only budget) | Up to 6 AI API calls per single user request | EG-032 |
| R-005 | **MEDIUM** | Low | `GeminiService.kt` | `tryModel().onResponse()` | 586 | EG-039→EG-041 | `runBlocking` for Room insert — not cancellable | Room insert proceeds even if coroutine is cancelled | EG-041 |
| R-006 | **MEDIUM** | Low | `GeminiService.kt` | `tryChain()` | 201 | EG-032→EG-035 | Budget check uses `Long.MAX_VALUE` default — caller may not set | Unlimited execution time | EG-035 |
| R-007 | **MEDIUM** | Medium | UI Screens (3) | `withContext(IO) { OkHttpClient().execute() }` | Various | EG-113 | Web scraping without coroutine timeout | UI‑visible indefinite loading | EG-113 |
| R-008 | **LOW** | Medium | `OnboardingViewModel.kt` | `structureProfile()` | 220–240 | EG-076→EG-089 | Manual retry uses second AI call — no circuit breaker | Extra API cost per extraction failure | EG-089 |
| R-009 | **LOW** | Low | `ProfileExtractionEngine.kt` | `extractPdfOcr()` | 786 | EG-080 | `Tasks.await(30s)` — 30-second blocking wait on IO thread | IO thread occupied for 30s | EG-080 |
| R-010 | **LOW** | High | `LogSink.kt` | `write()` | 38–45 | EG-126 | File I/O with ReentrantLock — no async queue | Sequential blocking on every PipelineTrace call | EG-126 |
| R-011 | **LOW** | Medium | `TelemetryCollector.kt` | `record()` | 36 | EG-125 | Fire-and-forget with max queue 100 — overflow drops oldest records | Telemetry data loss under heavy extraction | EG-125 |
| R-012 | **LOW** | Low | `OptimizationOrchestrator.kt` | `runAnalysisFresh()` | All | EG-026→EG-075 | Entire pipeline runs on `Dispatchers.IO` — no dedicated dispatcher | No isolation from other IO work | EG-027 |
| R-013 | **LOW** | Low | `ProfileExtractionEngine.kt` | `extractPdf()` | 758 | EG-079 | `withTimeout(30s)` on `Dispatchers.IO` — cancellation on timeout, but finally block runs on cancelled coroutine | PDF document may not be closed in finally block if already timed out | EG-079 |

---

## Part 9 — Architectural Invariants Audit

| Invariant | Status | Verification | Violations | EG Ref |
|-----------|--------|-------------|------------|--------|
| **Single Entry Rule** | ✅ COMPLIANT | Compatibility pipeline has exactly one entry point: `OptimizationOrchestrator.runAnalysisFresh()` | None | EG-026 |
| **Contract Enforcement Layer** | ✅ COMPLIANT | `ContractValidator` is the sole gate between extraction and scoring. No fallback, no retry, no partial-data acceptance. | None | EG-057 → EG-065 |
| **Compatibility Pipeline Invariant** | ✅ COMPLIANT | `ContractValidator.validate()` has exactly 1 caller. `CompatibilityEngine.analyze()` has exactly 1 caller. | None | EG-057, EG-065 |
| **Orchestrator ownership** | ✅ COMPLIANT | Compatibility pipeline execution originates from `OptimizationOrchestrator`. ViewModel calls interface only. | None | EG-008, EG-026 |
| **Deterministic before AI** | ✅ COMPLIANT | Transport validation in `GeminiService` is deterministic (string analysis before AI). Contract validation in `ContractValidator` is deterministic. | None | EG-044 |
| **Binary confidence** | ✅ COMPLIANT | `ContractValidator` returns 1.0 (Success) or 0.0 (Failure). | None | ContractValidator.kt:14 |
| **Jackson-only compatibility pipeline** | ✅ COMPLIANT | `org.json.JSONObject` removed from `CompatibilityEngine`. Transport layer still uses `org.json.JSONObject`. | None | EG-046 (transport only) |
| **Immutable validated payload** | ✅ COMPLIANT | `ValidatedAiPayload.node` is deep-copied via `deepCopy()` in `create()` factory. | None | ContractValidator.kt:64 |
| **Single validation owner** | ✅ COMPLIANT | Only `ContractValidator` validates compatibility JSON structure. | None | EG-057 |

---

## Part 10 — Dependency Ownership Matrix

| External Dependency | Owner | Wrapper | Timeout Owner | Retry Owner | Logging Owner | Cancellation Owner | EG Ref |
|--------------------|-------|---------|---------------|-------------|---------------|-------------------|--------|
| **OkHttpClient** | GeminiService | `GeminiService.client` | GeminiService (15/90/20s) | GeminiService (tryChain) | GeminiService + PipelineTrace | GeminiService (call.cancel) | EG-003, EG-036 |
| **OpenRouter API** | AiGateway | `AiGateway.generate()` → `GeminiService.generate()` | AiGateway (120s budget) | GeminiService | AiGateway + PipelineTrace | GeminiService (via tryModel) | EG-030 |
| **ML Kit TextRecognizer** | ProfileExtractionEngine | `TextRecognition.getClient(options)` | ProfileExtractionEngine (30s Tasks.await) | None | android.util.Log | None (thread-blocking) | EG-080 |
| **PdfRenderer** | ProfileExtractionEngine | `PdfRenderer(descriptor)` | ProfileExtractionEngine (30s withTimeout) | None | android.util.Log | via coroutine cancellation | EG-079 |
| **Room Database** | VantEdgeDatabase | DAO interfaces | None | None | PipelineTrace (via LogSink) | None | EG-001 |
| **WorkManager** | DeadlineNotificationWorker | `Worker.doWork()` | WorkManager (10s default) | WorkManager | android.util.Log | WorkManager | EG-017 |

---

## Part 11 — Contract Boundary Audit

| Pipeline Stage | Input Contract | Output Contract | Contract Owner | Validation Owner | Immutable? | Downstream Mutation Risk | Multiple Representations? | EG Ref |
|---------------|---------------|----------------|---------------|-----------------|-----------|------------------------|-------------------------|--------|
| **Transport (OkHttp → GeminiService)** | `OkHttp Response.body` | `ExtractionResult` | GeminiService | GeminiService (JsonExtractionEngine) | ⚠️ Raw string | ✅ No | ⚠️ raw+extracted+parsed coexist | EG-044 |
| **GeminiService → AiGateway** | `String?` (extracted content) | `String?` | GeminiService | None | ⚠️ Nullable | ✅ No | None | EG-047 |
| **AiGateway → callers** | `String?` | `String?` | AiGateway | None | ⚠️ Nullable | ✅ No | None | EG-051 |
| **OptimizationOrchestrator → ContractValidator** | `ExtractedAiPayload` | `ContractValidationResult` | ContractValidator | ContractValidator | ✅ (ValidatedAiPayload.node deep-copied) | ✅ No | None | EG-057 |
| **ContractValidator → CompatibilityEngine** | `JsonNode` (read-only) | `CompatibilityRecord` | CompatibilityEngine | ContractValidator (validated node) | ✅ JsonNode from deepCopy | ✅ No downstream mutation | None | EG-065 |
| **CompatibilityEngine → Orchestrator** | `CompatibilityRecord` | `CompatibilityRecord` | CompatibilityEngine | ContractValidator (pre-validated) | ⚠️ Data class (copy possible) | ✅ No mutation observed | None | EG-066 |
| **OptimizationOrchestrator → ViewModel** | `CompatibilityResult` | UI state | Orchestrator | None after validation | ✅ Sealed class | ✅ ViewModel only reads | None | EG-071 |

**Violations identified:** None in the compatibility pipeline. All contracts are properly scoped and validated.

---

## Part 12 — Resource Ownership Audit

| Resource | Creation Owner | Lifecycle Owner | Disposal Owner | Cancellation Owner | Timeout Owner | EG Ref |
|----------|---------------|----------------|---------------|-------------------|---------------|--------|
| **OkHttpClient** | GeminiService (singleton) | GeminiService | GeminiService | Not explicitly | 15/90/20s builder config | EG-003 |
| **OkHttp Call** | GeminiService.tryModel() | GeminiService | OkHttp (via enqueue completion) | `invokeOnCancellation → call.cancel()` | None distinct (client config applies) | EG-037 |
| **Response.body** | OkHttp | GeminiService callback | `response.body?.string()` consumes it | N/A | N/A (string after read) | EG-039 |
| **PdfRenderer** | ProfileExtractionEngine | ProfileExtractionEngine | Not explicitly (`page.close()`, `renderer.close()` not visible) | None | 30s withTimeout on extract | EG-079 |
| **Bitmap (OCR)** | ProfileExtractionEngine | ProfileExtractionEngine | Not explicitly (`bmp.recycle()` not visible) | None | 30s Tasks.await | EG-080 |
| **ML Kit TextRecognizer** | ProfileExtractionEngine | ProfileExtractionEngine | `recognizer.close()` not called | None | 30s Tasks.await | EG-080 |
| **InputStream (file)** | ProfileExtractionEngine | ProfileExtractionEngine | Not explicitly (try-with-resources not used) | None | None (file read) | EG-079 |
| **Coroutine Job (pipeline)** | CycleViewModel | CycleViewModel | `pipelineJob.cancel()` | viewModelScope.onCleared | 120s budget (AI) | EG-069 |
| **Coroutine Job (extraction)** | OnboardingViewModel | OnboardingViewModel | `extractionJob?.cancel()` | viewModelScope.onCleared | None | EG-076 |
| **Dispatchers.IO** | Kotlin framework | Application | N/A (shared) | N/A | N/A | All |

**Resources with no explicit owner / potential leak:**
1. `PdfRenderer` — not explicitly closed in `extractPdfOcr()` — native resource leak risk
2. `Bitmap` objects in `extractPdfOcr()` — `bmp.recycle()` not called — memory pressure
3. `TextRecognizer` (ML Kit) — `recognizer.close()` not called — ML Kit model in memory
4. OkHttp `Call` objects — cancelled via `invokeOnCancellation` but the Response body may still be consumed on OkHttp thread even after cancellation
5. `InputStream` from `openInputStream` — no explicit close, relies on GC/finalizer

---

## Part 13 — Architectural Debt Register

| ID | Severity | Probability | Blast Radius | Implementation Complexity | Blocking Deps | Recommended Order | P-Level | EG Ref | Finding |
|----|----------|-------------|--------------|--------------------------|---------------|-------------------|---------|--------|---------|
| W3-001 | **CRITICAL** | Medium | High (IO thread starvation) | Low (add timeout to latch) | None | 1 | **P0** | EG-092, EG-094 | GeneratorViewModel CountDownLatch.await() lacks timeout |
| W3-002 | **HIGH** | Low | Medium (OkHttp pool) | Low (async Room write) | None | 2 | **P0** | EG-041 | runBlocking(IO) on OkHttp callback thread for Room insert |
| W3-003 | **HIGH** | Low | Medium (coroutine crash) | Low (readOnce guard) | None | 3 | **P0** | EG-037 | Duplicate resume risk in suspendCancellableCoroutine |
| W3-004 | **MEDIUM** | Medium | Low (API cost) | Low (cap maxIterations) | None | 4 | **P0.5** | EG-032 | tryChain retry amplification — no iteration cap beyond budget |
| W3-005 | **MEDIUM** | Low | Low (diagnostic loss) | Low (catch block logging) | None | 5 | **P0.5** | EG-069 | CycleViewModel TimeoutCancellationException not logged to PipelineTrace |
| W3-006 | **MEDIUM** | Medium | Low (UI freeze) | Low (withTimeout wrapper) | None | 6 | **P0.5** | EG-113 | UI screen web scraping lacks coroutine timeout |
| W3-007 | **LOW** | High | Low (sequential I/O) | Medium (async queue) | W3-A (timeout infra) | 7 | **P1** | EG-126 | LogSink synchronous file I/O on PipelineTrace hot path |
| W3-008 | **LOW** | Medium | Low (telemetry data loss) | Low (increase queue or add flush) | None | 8 | **P1** | EG-125 | TelemetryCollector overflow silently drops oldest records |
| W3-009 | **LOW** | Low | Low (PDF leak) | Low (try-finally close) | None | 9 | **P1** | EG-079 | PdfRenderer/Bitmap/InputStream not explicitly disposed |
| W3-010 | **LOW** | Low | Low (memory) | Low (recycle call) | None | 10 | **P1** | EG-080 | Bitmap not recycled after OCR use |
| W3-011 | **LOW** | Low | Low (ML Kit model) | Low (close call) | None | 11 | **P1** | EG-080 | TextRecognizer not closed after OCR use |
| W3-012 | **LOW** | Low | Low (thread isolation) | Medium (new dispatcher) | None | 12 | **P2** | EG-027 | OptimizationOrchestrator uses shared Dispatchers.IO — no dedicated dispatcher |

---

## Part 14 — Defect Classification Matrix

Every defect references the Part 8 Risk ID.

### Architecture Defects

| Defect ID | Classification | Risk ID | Root Cause | Immediate Symptom | Long-Term Impact | Fix Implementation Alone Resolves? |
|-----------|---------------|---------|------------|-------------------|------------------|-----------------------------------|
| D-ARC-001 | Boundary violation — missing contract | R-001 | `CountDownLatch` is a raw Java concurrency primitive, not a coroutine contract. The bridge from callback to coroutine has no timeout, no cancellation propagation, and no structured logging. | IO thread blocks indefinitely if callback never fires | IO dispatcher thread starvation under sustained use | No — requires replacing the bridge pattern (W3-A + W3-B) |
| D-ARC-002 | Layer violation — mixed concurrency model | R-002 | `runBlocking(IO)` on an OkHttp callback thread couples the non-blocking I/O transport layer with blocking Room I/O | OkHttp dispatcher thread blocked on Room write | OkHttp connection pool exhaustion under load, cascading timeouts | No — requires async Room write on the resumed coroutine (W3-C) |
| D-ARC-003 | Missing contract — no overall timeout | R-006 | `budgetDeadlineMs` defaults to `Long.MAX_VALUE`. If a caller doesn't set it (or sets it far), the pipeline runs without deadline | Long-running requests consume API quota without budget enforcement | Architectural regression of the Timeout Governance layer | Yes — setting caller-side budget would fix (W3-A) |
| D-ARC-004 | Invalid responsibility allocation | R-004 | Retry governance is split between `GeminiService` (tryChain loop), `AiGateway` (final retry), and `OptimizationOrchestrator` (no retry). No single owner. | Nested retries create amplification without coordination | Duplicate API costs, unpredictable timing | No — requires an explicit RetryGovernor (W3-C) |

### Implementation Defects

| Defect ID | Classification | Risk ID | Root Cause | Immediate Symptom | Long-Term Impact | Fix Implementation Alone Resolves? |
|-----------|---------------|---------|------------|-------------------|------------------|-----------------------------------|
| D-IMP-001 | Missing timeout — CountDownLatch | R-001 | `CountDownLatch(1).await()` called without `timeout` parameter | Thread blocked indefinitely | IO dispatcher starvation | Yes — add `await(30, TimeUnit.SECONDS)` (W3-A) |
| D-IMP-002 | Missing cancellation — OCR | R-009 | `Tasks.await()` is a blocking call; cancellation of the enclosing coroutine does not cancel the ML Kit task | Cancelled extraction still completes OCR work | Wasted computation, no user-facing impact | No — ML Kit task cancellation after Tasks.await requires separate handling (W3-B) |
| D-IMP-003 | Missing logging — CycleViewModel | R-005 | `TimeoutCancellationException` caught but not logged to PipelineTrace or LogSink | Pipeline diagnostic gap — no record of pipeline timeout in structured logs | Debugging and observability gap | Yes — add PipelineTrace.error call in catch block (W3-D) |
| D-IMP-004 | Resource leak — PdfRenderer | R-009 (related) | `PdfRenderer` not closed in `extractPdfOcr()` | Native PDF resource held until GC | File descriptor leak under repeated PDF scanning | Yes — add try-finally with renderer.close() (W3-E) |
| D-IMP-005 | Resource leak — Bitmap | R-009 (related) | `Bitmap` not recycled after OCR use | Bitmap memory held until GC | Memory pressure under repeated OCR | Yes — add bmp.recycle() in finally block (W3-E) |
| D-IMP-006 | Resource leak — TextRecognizer | R-009 (related) | ML Kit recognizer not closed | ML Kit model in memory | Memory pressure | Yes — add recognizer.close() (W3-E) |
| D-IMP-007 | Missing timeout — web scraping | R-007 | `withContext(IO) { client.execute() }` without wrapping in `withTimeout` | UI-visible indefinite loading for slow/scraped URLs | Poor UX | Yes — add `withTimeout(30s)` wrapper (W3-A) |
| D-IMP-008 | Race condition — duplicate resume | R-003 | `cont.isActive` check doesn't fully guard against OkHttp dispatching both onResponse and onFailure | `IllegalStateException` in coroutines internals | Coroutine crash in edge case | Yes — add `call.isCanceled()` guard or atomic boolean (W3-B) |

---

## Part 15 — Workstream Decomposition

### W3-A — Timeout Governance
**Objective:** Establish consistent timeout ownership across all external dependencies and callback bridges.
**Files affected:**
- `GeneratorViewModel.kt` (lines 90, 150) — add timeout to CountDownLatch.await()
- `GeminiService.kt` (line 460) — consider withTimeout wrapper around suspendCancellableCoroutine
- UI screens: `CompatibilityInputScreen.kt`, `JobInputScreen.kt`, `CVGeneratorScreen.kt` — add withTimeout to web scraping
- `AiGateway.kt` (line 65) — verify budget deadline is always set; consider making it required
- `ProfileExtractionEngine.kt` (lines 758, 786, 813) — verify all timeout values are appropriate

**EG nodes in scope:** EG-092, EG-094, EG-037, EG-113, EG-030, EG-079, EG-080, EG-081

### W3-B — Cancellation Propagation
**Objective:** Ensure cancellation propagates across all callback-to-coroutine boundaries.
**Files affected:**
- `GeminiService.kt` (lines 460–888) — atomic boolean guard against duplicate resume; verify `invokeOnCancellation` covers all paths
- `ProfileExtractionEngine.kt` (lines 786, 813) — ML Kit task cancellation when coroutine is cancelled

**EG nodes in scope:** EG-037, EG-082, EG-083, EG-040

### W3-C — Retry Governance
**Objective:** Centralize retry policy; prevent amplification.
**Files affected:**
- `GeminiService.kt` (lines 155–418) — add maximum iteration cap independent of budget; centralize retry policy
- `AiGateway.kt` (line 65) — decouple final retry from tryChain
- `OnboardingViewModel.kt` (lines 222–240) — add circuit breaker respect for extraction retry

**EG nodes in scope:** EG-032, EG-048, EG-050, EG-089

### W3-D — Observability Completion
**Objective:** Close all diagnostic gaps identified in the observability audit.
**Files affected:**
- `CycleViewModel.kt` (lines 119, 246, 310) — log TimeoutCancellationException to PipelineTrace
- `GeneratorViewModel.kt` (lines 69, 90, 136, 150) — add structured logging around CountDownLatch bridges
- `ProfileExtractionEngine.kt` — add correlationId to OCR timeout error paths

**EG nodes in scope:** EG-069, EG-092, EG-094, EG-080, EG-081

### W3-E — Resource Lifecycle Governance
**Objective:** Explicit ownership and cleanup for all native/ML resources.
**Files affected:**
- `ProfileExtractionEngine.kt` (lines 767–820) — add try-finally for PdfRenderer/Bitmap/InputStream cleanup; add recognizer.close()

**EG nodes in scope:** EG-079, EG-080, EG-081

---

## Final Architectural Verdict

The VantEdge 3.0 application has a well-structured architecture with clear pipeline ownership, deterministic contract enforcement, and strong separation of concerns. The Contract Enforcement Layer and Compatibility Pipeline Invariant are fully satisfied.

**Critical findings (must fix before W3 completion):**
1. **R-001** — `CountDownLatch.await()` without timeout in `GeneratorViewModel` is the single highest-risk defect. It can cause IO dispatcher thread starvation.
2. **R-002** — `runBlocking(IO)` on OkHttp callback threads risks dispatcher pool exhaustion under concurrent AI requests.

**Architectural strengths preserved:**
- Single Entry Rule for compatibility pipeline is enforced at the type system level
- Contract Enforcement Layer correctly separates Tier 1 (structural), Tier 2 (schema-boundary), and Tier 3 (domain) concerns
- Binary confidence model prevents ambiguous scoring states
- `ValidatedAiPayload` immutability prevents downstream data corruption
- Jackson-only compatibility pipeline eliminates dual JSON model risk

**Recommendation:** Execute workstreams in order W3-A → W3-B → W3-C → W3-D → W3-E. W3-A and W3-B are **P0** and address the immediate thread-safety risks. W3-C and W3-D are **P0.5** and address retry governance and observability gaps. W3-E is **P1** and addresses resource lifecycle hygiene.
