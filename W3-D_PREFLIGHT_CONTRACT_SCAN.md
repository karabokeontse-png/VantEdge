# W3-D PRE-FLIGHT CONTRACT SCAN
**Generated:** 2026-06-30
**Status:** READ-ONLY AUDIT — no code modified
**Scope:** Entire repository, AI pipeline layers

---

## CONTRACT_STATE_SUMMARY

| Contract Symbol | Declared In | Type | Nullable | Values Are |
|----------------|------------|------|----------|------------|
| `ModelAttemptResult.failureType` | `ModelAttemptResult.kt:7` | `String?` | Yes | Free-form raw strings |
| `AiRawResponseArtifact.failureType` | `AiRawResponseArtifact.kt:26` | `String` | No | Free-form raw strings |
| `FailureType` (enum) | `GeminiService.kt:33-39` | `Enum<FailureType>` | N/A | 5 named values (CONFIG_ERROR, TIMEOUT, RATE_LIMIT, TRANSIENT_TRANSPORT_FAILURE, OTHER) |
| `AiFailureCategory` (enum) | `AiResult.kt:12-21` | `Enum<AiFailureCategory>` | N/A | 8 named values |
| `JobExtractionFailureReason` (enum) | `JobExtractionFailureReason.kt:3-9` | `Enum<JobExtractionFailureReason>` | N/A | 5 named values |
| `ContractFailureReason` (sealed) | `ContractFailureReason.kt:7-53` | `SealedClass` | N/A | 8 subclass objects |
| `PipelineTrace` | `PipelineTrace.kt:6` | `object` | N/A | 7 methods + 3 unused |
| `PipelineEvents` | `PipelineEvents.kt:3` | `object` | N/A | 18 string constants (5 unused) |

**Key finding:** No typed failure contract exists between GeminiService producers and consumers. `failureType` is `String?` everywhere — validated nowhere, free-form everywhere.

---

## FAILURE_TYPE_MAP

### Enum Definitions

#### `FailureType` — GeminiService.kt:33-39 (internal-only, never serialized)
```
CONFIG_ERROR → "404", "http_400", "http_401", "http_403", "http_404"
TIMEOUT      → "timeout", "network"
RATE_LIMIT   → "429"
TRANSIENT_TRANSPORT_FAILURE → "cancelled"
OTHER        → "EMPTY_BODY", "MALFORMED_SHORT", "NO_JSON_STRUCTURE", "empty", else
```
Only consumed inside `GeminiService.tryChain()` loop decision logic. Never exported.

#### `AiFailureCategory` — AiResult.kt:12-21 (public, serialized in AiResult.Failure)
```
TIMEOUT       → "timeout"
TRANSPORT     → "cancelled", "network"
EMPTY_RESPONSE → "empty"
PARSE_ERROR   → "invalid_json", "schema_error", "parse"
PROVIDER_ERROR → "provider_error"
CONFIG_ERROR  → "404"
RATE_LIMIT    → "429"
OTHER         → else
```
Consumed by `AiGateway.generate()` return-value path only.

### Write Locations — ModelAttemptResult.failureType (13 distinct string values produced)

| Value | File:Line | Trigger |
|-------|-----------|---------|
| `null` | `GeminiService.kt:263` | HTTP 200 success |
| `"timeout"` | `GeminiService.kt:471` | ReadTimeout exception |
| `"cancelled"` | `GeminiService.kt:472` | Cancellation exception |
| `"network"` | `GeminiService.kt:473` | Other IOException |
| `"TIMEOUT"` | `GeminiService.kt:508` | Transport timeout (uppercase) |
| `"CANCELLED"` | `GeminiService.kt:509` | Transport cancelled (uppercase) |
| `"TRANSPORT_FAILURE"` | `GeminiService.kt:510` | Transport fallback |
| `"EMPTY_BODY"` | `GeminiService.kt:614` | HTTP 200 body is null/empty |
| `"MALFORMED_SHORT"` | `GeminiService.kt:634` | HTTP 200 body < 50 chars |
| `"NO_JSON_STRUCTURE"` | `GeminiService.kt:654` | HTTP 200 body has no `{` |
| `"invalid_json"` | `GeminiService.kt:676` | JSON parse exception |
| `"provider_error"` | `GeminiService.kt:698` | Response body contains `"error"` object |
| `"MODEL_CONTRACT_VIOLATION"` | `GeminiService.kt:722, 808` | Missing `choices` key / extraction fail |
| `"404"` | `GeminiService.kt:832` | HTTP 404 |
| `"429"` | `GeminiService.kt:847` | HTTP 429 |
| `"http_$code"` | `GeminiService.kt:862` | Any other HTTP error code |
| `"unknown"` | `AiGateway.kt:131` | Fallback (no transport captured) |

**17 distinct string values**, no validation, no compile-time safety.

### Read Locations — ModelAttemptResult.failureType

| Consumer | File:Line | How Read |
|----------|-----------|----------|
| `capturedErrorType` assignment | `GeminiService.kt:227` | `result.failureType` — used for loop decisions |
| `classifyError()` input | `GeminiService.kt:289` | Feeds `FailureType` enum mapping |
| `classifyFailure()` input | `AiGateway.kt:126` | `lastFailure?.failureType` — maps to `AiFailureCategory` |
| `"success"` vs `"failed"` status | `AiGateway.kt:60` | `if (result.failureType == null)` |
| Transport filter | `AiGateway.kt:124` | `transports.lastOrNull { it.failureType != null }` |

---

## PIPELINE_TRACE_MAP

### Definition: PipelineTrace (PipelineTrace.kt:6)

| Method | Line | Signature |
|--------|------|-----------|
| `entry` | 9 | `fun entry(stage: String, context: Map<String, Any?>)` |
| `exit` | 16 | `fun exit(stage: String, durationMs: Long, summary: Map<String, Any?>)` |
| `error` | 23 | `fun error(stage: String, reason: String, throwable: Throwable? = null, correlationId: String? = null)` |
| `warn` | 38 | `fun warn(stage: String, message: String, correlationId: String? = null)` |
| `dataQuality` | 47 | `fun dataQuality(stage: String, issue: String, details: Map<String, Any?>, correlationId: String? = null)` |
| `validateScore` | 58 | `fun validateScore(stage: String, scoreName: String, value: Int, min: Int = 0, max: Int = 100): Boolean` |
| `validateNonBlank` | 70 | `fun validateNonBlank(stage: String, fieldName: String, value: String?): Boolean` |
| `validateEnum` | 76 | `fun validateEnum(stage: String, fieldName: String, value: String?, allowed: Set<String>, fallback: String): String` |

### Definition: PipelineEvents (PipelineEvents.kt:3)

| Constant | Line | Used? |
|----------|------|-------|
| `TRYCHAIN_START` | 4 | ✅ GeminiService.kt:147 |
| `ATTEMPT_START` | 5 | ✅ GeminiService.kt:214 |
| `HTTP_REQUEST_BEGIN` | 6 | ✅ GeminiService.kt:447 |
| `HTTP_RESPONSE_RECEIVED` | 7 | ✅ GeminiService.kt:543 |
| `HTTP_REQUEST_FAILED` | 8 | ✅ GeminiService.kt:478 |
| `WITH_TIMEOUT_EXIT` | 9 | ❌ Dead |
| `ATTEMPT_END` | 10 | ✅ GeminiService.kt:343 |
| `TRYCHAIN_END` | 11 | ✅ GeminiService.kt:167, 365, 381 |
| `FINAL_RETRY_BEGIN` | 12 | ❌ Dead |
| `FINAL_RETRY_END` | 13 | ❌ Dead |
| `RAW_RESPONSE_CAPTURED` | 14 | ❌ Dead |
| `RAW_RESPONSE_ARTIFACT` | 15 | ✅ GeminiService.kt:591 |
| `CIRCUIT_BREAKER_SKIP` | 16 | ✅ GeminiService.kt:190 |
| `CIRCUIT_BREAKER_RESET` | 17 | ✅ GeminiService.kt:276 |
| `CIRCUIT_BREAKER_DEGRADE` | 18 | ✅ GeminiService.kt:329 |
| `EXTRACTION_RESULT` | 19 | ✅ GeminiService.kt:748 |
| `PARSE_RESULT` | 20 | ✅ GeminiService.kt:771 |
| `VALIDATION_RESULT` | 21 | ❌ Dead |

### Emitter Files (13 files, ~88 call sites)

| File | entry | exit | error | warn | dataQuality |
|------|-------|------|-------|------|-------------|
| `GeminiService.kt` | 0 | 0 | 0 | 0 | 15 |
| `AiGateway.kt` | 1 | 2 | 1 | 0 | 1 |
| `OptimizationOrchestrator.kt` | 4 | 8 | 4 | 0 | 0 |
| `ProfileExtractionEngine.kt` | 2 | 4 | 4 | 0 | 1 |
| `JobExtractionOrchestrator.kt` | 1 | 3 | 3 | 5 | 0 |
| `ExtractionDecisionCoordinator.kt` | 1 | 5 | 0 | 0 | 0 |
| `LlmJobExtractor.kt` | 1 | 1 | 0 | 0 | 7 |
| `JsonExtractionEngine.kt` | 0 | 0 | 0 | 0 | 2 |
| `RuleBasedEmergencyExtractor.kt` | 0 | 0 | 0 | 2 | 0 |
| `ConfidenceEngine.kt` | 1 | 1 | 0 | 0 | 0 |
| `SchemaValidator.kt` | 1 | 1 | 0 | 0 | 0 |
| `SemanticValidator.kt` | 1 | 1 | 0 | 0 | 0 |
| `DocumentPreprocessor.kt` | 1 | 1 | 0 | 0 | 0 |
| `DocumentStructureAnalyzer.kt` | 1 | 1 | 0 | 0 | 0 |

---

## RAW_FAILURE_STRING_OCCURRENCES

### Category 1: Failure type assignment strings (produced, GeminiService.kt)

| Raw String | Lines | Used In classifyError? | classifyError Result |
|------------|-------|----------------------|---------------------|
| `"timeout"` | 471 | ✅ `"timeout"` → TIMEOUT | TIMEOUT |
| `"TIMEOUT"` | 508 | ❌ No match (case mismatch) | OTHER (fallthrough!) |
| `"cancelled"` | 472 | ✅ `"cancelled"` → TRANSIENT_TRANSPORT_FAILURE | TRANSIENT_TRANSPORT_FAILURE |
| `"CANCELLED"` | 509 | ❌ No match | OTHER (fallthrough!) |
| `"network"` | 473 | ✅ `"network"` → TIMEOUT | TIMEOUT |
| `"TRANSPORT_FAILURE"` | 510 | ❌ No match | OTHER (fallthrough!) |
| `"EMPTY_BODY"` | 614 | ✅ `"EMPTY_BODY"` → OTHER | OTHER |
| `"MALFORMED_SHORT"` | 634 | ✅ `"MALFORMED_SHORT"` → OTHER | OTHER |
| `"NO_JSON_STRUCTURE"` | 654 | ✅ `"NO_JSON_STRUCTURE"` → OTHER | OTHER |
| `"invalid_json"` | 676 | ❌ No match (not in classifyError) | OTHER (fallthrough!) |
| `"provider_error"` | 698 | ❌ No match (not in classifyError) | OTHER (fallthrough!) |
| `"MODEL_CONTRACT_VIOLATION"` | 722, 808 | ❌ No match | OTHER (fallthrough!) |
| `"404"` | 832 | ✅ `"404"` → CONFIG_ERROR | CONFIG_ERROR |
| `"429"` | 847 | ✅ `"429"` → RATE_LIMIT | RATE_LIMIT |
| `"http_$code"` | 862 | ✅ partial (`"http_400"`, `"http_401"`, etc.) | depends |
| `"UNKNOWN"` | 574 | ❌ No match | OTHER (fallthrough!) |

**RISK: 6 of 16 values are unrecognized by classifyError → fall through to `FailureType.OTHER` silently.**

### Category 2: Exception-message-as-failure-type (produced in throw sites)

| Exception Message String | File:Line | Pattern |
|-------------------------|-----------|---------|
| `"EMPTY_AI_RESPONSE"` | `LlmJobExtractor.kt:96` | `throw Exception("EMPTY_AI_RESPONSE")` |
| `"EXTRACTION_FAILED: ${...}"` | `LlmJobExtractor.kt:101` | `throw Exception("EXTRACTION_FAILED: ${...}")` |
| `"SCHEMA_VIOLATION: ${...}"` | `LlmJobExtractor.kt:127` | `throw Exception("SCHEMA_VIOLATION: ${...}")` |
| `"UNREPAIRABLE_JSON"` | `LlmJobExtractor.kt:229` | `throw Exception("UNREPAIRABLE_JSON")` |
| `"PARSE_FAILED_AFTER_REPAIR"` | `LlmJobExtractor.kt:246` | `throw Exception("PARSE_FAILED_AFTER_REPAIR")` |

These propagate up through `catch` blocks and their `.message` is used as error type downstream — never validated.

### Category 3: Inconsistent casing (write vs read mismatch)

| Producer writes | classifyError expects | Consumer reads | RISK |
|----------------|----------------------|----------------|------|
| `"TIMEOUT"` (upper) | `"timeout"` (lower) | `classifyError()` → OTHER | Loss of timeout classification |
| `"CANCELLED"` (upper) | `"cancelled"` (lower) | `classifyError()` → OTHER | Loss of cancellation classification |
| `"TRANSPORT_FAILURE"` | No match | `classifyError()` → OTHER | Loss of transport failure classification |
| `"invalid_json"` | No match | `classifyError()` → OTHER | Loss of parse-error classification |
| `"provider_error"` | No match | `classifyError()` → OTHER | Loss of provider-error classification |
| `"MODEL_CONTRACT_VIOLATION"` | No match | `classifyError()` → OTHER | Loss of contract-violation classification |
| `"UNKNOWN"` | No match | `classifyError()` → OTHER | Loss of default classification |

### Category 4: Ad-hoc string identifiers in upstream layers

| File:Line | String | Context |
|-----------|--------|---------|
| `OptimizationOrchestrator.kt:385` | `"null_response"` | `CompatibilityResult.Failure("null_response", ...)` |
| `OptimizationOrchestrator.kt:391` | `"no_json"` | `CompatibilityResult.Failure("no_json", ...)` |
| `OptimizationOrchestrator.kt:400` | `"parse_error"` | `CompatibilityResult.Failure("parse_error", ...)` |
| `ProfileExtractionEngine.kt:80` | `"PDF_TEXT_TIMEOUT"` | Constant (named, deterministic) |
| `ProfileExtractionEngine.kt:81` | `"OCR_TIMEOUT"` | Constant (named, deterministic) |
| `ProfileExtractionEngine.kt:82` | `"FILE_READ_ERROR"` | Constant (named, deterministic) |
| `ProfileExtractionEngine.kt:83` | `"EMPTY_DOCUMENT"` | Constant (named, deterministic) |

---

## DEPENDENCY_GRAPH

### GeminiService → Downstream Callers

```
GeminiService.generate()
  └─ AiGateway.generate()                           [Data layer / Orchestration-bound]
       ├─ ProfileExtractionEngine.callAi():543       [Data layer / Orchestration-bound]
       │    └─ OnboardingViewModel:204               [UI-bound]
       ├─ GeneratorEngine.generateCv():51            [Data layer / Orchestration-bound]
       │    └─ GeneratorViewModel:66/GeneratorEngine  [UI-bound]
       ├─ GeneratorEngine.generateCoverLetter():123  [Data layer / Orchestration-bound]
       │    └─ GeneratorViewModel:66/GeneratorEngine  [UI-bound]
       ├─ GeneratorEngine.generateCvDocx():211       [Data layer / Orchestration-bound]
       │    └─ GeneratorViewModel:66/GeneratorEngine  [UI-bound]
       ├─ OptimizationOrchestrator.runAnalysisFresh():382 [Data layer / Orchestration-bound]
       │    └─ CycleViewModel:195                     [UI-bound]
       └─ LlmJobExtractor.extract():93               [Data layer / Orchestration-bound]
            └─ JobExtractionOrchestrator:65          [Orchestration-bound]
```

### ModelAttemptResult.failureType → Consumers

```
ModelAttemptResult.failureType (String?, free-form)
  ├─ GeminiService.tryChain():227     → capturedErrorType (loop state)    [Internal-only]
  ├─ GeminiService.tryChain():289     → classifyError() → FailureType    [Internal-only]
  ├─ AiGateway:48                     → "success"/"failed" decision      [Internal-only]
  ├─ AiGateway:61                     → dataQuality detail               [Telemetry-bound]
  ├─ AiGateway:124-126                → classifyFailure() → AiFailureCategory [Orchestration-bound]
  │    └─ AiResult.Failure.category                                      [Orchestration-bound]
  └─ (Not consumed by UI or Telemetry directly — only via AiResult/AiFailureCategory)
```

### PipelineTrace → All Emitters (classified)

| Emitter File | Dependency Classification |
|-------------|--------------------------|
| `GeminiService.kt` | Internal-only |
| `AiGateway.kt` | Internal-only |
| `OptimizationOrchestrator.kt` | Orchestration-bound |
| `ProfileExtractionEngine.kt` | Orchestration-bound |
| `JobExtractionOrchestrator.kt` | Orchestration-bound |
| `ExtractionDecisionCoordinator.kt` | Orchestration-bound |
| `LlmJobExtractor.kt` | Internal-only |
| `JsonExtractionEngine.kt` | Internal-only |
| `RuleBasedEmergencyExtractor.kt` | Internal-only |
| `ConfidenceEngine.kt` | Internal-only |
| `SchemaValidator.kt` | Internal-only |
| `SemanticValidator.kt` | Internal-only |
| `DocumentPreprocessor.kt` | Internal-only |
| `DocumentStructureAnalyzer.kt` | Internal-only |

**No UI-bound PipelineTrace consumers exist.** Telemetry is purely internal/orchestration.

---

## COROUTINE_ORDERING_ANALYSIS

### GeminiService tryModel() — Continuation Boundary (line 437)

```
suspendCancellableCoroutine { cont ->
    cont.invokeOnCancellation { call.cancel() }
    val request = buildJsonRequest(...)
    val httpCall = createHttpCall(request)

    httpCall.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            [1] PipelineTrace.dataQuality(HTTP_REQUEST_FAILED)   ← BEFORE resume (transport fail path)
            [2] Build ModelAttemptResult with failureType        ← BEFORE resume
            [3] onModelResult(attemptResult)                     ← BEFORE resume
            [4] Build AiRawResponseArtifact                      ← BEFORE resume
            [5] bridgePersistenceScope.launch { persist }        ← BEFORE resume (fire-and-forget)
            [6] cont.resume(null)                                ← RESUME
        }

        override fun onResponse(call: Call, response: Response) {
            [1] PipelineTrace.dataQuality(HTTP_RESPONSE_RECEIVED)  ← BEFORE resume
            [2] Parse response body                                ← BEFORE resume
            [3] if empty/malformed/no-json/invalid/provider-error/contract-violation:
                  PipelineTrace.dataQuality(EXTRACTION/PARSE)      ← BEFORE resume
                  Build ModelAttemptResult                         ← BEFORE resume
                  Build AiRawResponseArtifact                      ← BEFORE resume
                  bridgePersistenceScope.launch { persist }        ← BEFORE resume (fire-and-forget)
                  cont.resume(null)                                ← RESUME (failure path)

            [4] if success (extracted content):
                  PipelineTrace.dataQuality(EXTRACTION_RESULT)     ← BEFORE resume
                  Update artifact failureType = "UNKNOWN"          ← BEFORE resume
                  bridgePersistenceScope.launch { persist }        ← BEFORE resume (fire-and-forget)
                  cont.resume(extractedContent)                    ← RESUME (success path)
        }
    })
}
```

### Ordering Rules

| Operation | Position Relative to `cont.resume()` |
|-----------|--------------------------------------|
| `PipelineTrace.dataQuality` (all variants) | **BEFORE resume** |
| `onModelResult(attemptResult)` | **BEFORE resume** |
| `AiRawResponseArtifact` construction | **BEFORE resume** |
| `bridgePersistenceScope.launch { persist }` | **BEFORE resume** (fire-and-forget, scheduled but not awaited) |
| `cont.resume(null/String)` | **FINAL operation** in both `onFailure` and `onResponse` |
| `cont.resume()` → consumer code runs | **AFTER resume** (resuming coroutine) |
| Persistence completion | **Indeterminate** (fire-and-forget on different scope) |

**All PipelineTrace emissions occur BEFORE cont.resume().** The ordering guarantee holds for all 10 resume paths.

### Post-resume execution (tryChain loop, lines 225-377)

```
cont.resume() returns → code after line 437
  ├─ onModelResult was already called inside callback (BEFORE resume)
  ├─ result is non-null → handleSuccess pipeline
  │    └─ PipelineTrace.dataQuality(CIRCUIT_BREAKER_RESET) line 276
  │    └─ return result (exit tryChain)
  ├─ result is null → failure handling
  │    ├─ classifyError(capturedErrorType) line 289
  │    ├─ when (failureType) { ... }          ← loop decision logic (lines 291-324)
  │    │    ├─ CONFIG_ERROR → blacklist model, continue loop
  │    │    ├─ TIMEOUT → streak check, maybe shouldStop
  │    │    ├─ RATE_LIMIT → cluster check, maybe shouldStop
  │    │    ├─ TRANSIENT_TRANSPORT_FAILURE → continue
  │    │    └─ OTHER → continue
  │    ├─ PipelineTrace.dataQuality(CIRCUIT_BREAKER_DEGRADE) if applicable (line 329)
  │    ├─ PipelineTrace.dataQuality(ATTEMPT_END) (line 343)
  │    ├─ delay(delayForAttempt(index)) (line 357) ← NEXT suspend point
  │    └─ loop continues or exits
```

### CountDownLatch.await() in GeneratorViewModel (lines 91, 155)

```
viewModelScope.launch(Dispatchers.IO) {
    cvLatch.await(30, TimeUnit.SECONDS)       ← BLOCKS IO thread (not a coroutine suspend)
    withContext(Dispatchers.Main) { ... }      ← Thread switch to Main
}
```

**RISK:** `CountDownLatch.await()` blocks the underlying IO thread for up to 30 seconds. This is a thread-blocking call inside a coroutine — not a coroutine-aware suspension.

---

## RISK FLAGS

### BREAKING: Loss of failure classification (6 string values unrecognized by classifyError)
- `"TIMEOUT"` (uppercase), `"CANCELLED"` (uppercase), `"TRANSPORT_FAILURE"`, `"invalid_json"`, `"provider_error"`, `"MODEL_CONTRACT_VIOLATION"`, `"UNKNOWN"` all fall through to `FailureType.OTHER`
- Downstream `tryChain` loop decisions lose visibility into the actual failure mode
- Circuit breaker, timeout-streak detection, and rate-limit cluster detection all operate on `FailureType`, which is silently wrong for these values

### BREAKING: Inconsistent casing between producer and consumer
- Line 471 writes `"timeout"` (lowercase), line 508 writes `"TIMEOUT"` (uppercase)
- Both flow through the same `classifyError()` — only lowercase is matched
- This is a latent bug: the transport-failure path (line 508) produces a value that the decision loop cannot classify correctly

### HIDDEN COUPLING: Exception-message-as-failure-type
- `LlmJobExtractor.kt` uses `throw Exception("RAW_STRING_ID")` as error signaling
- Upstream `catch { e.message }` propagates these as error details
- No structural contract binds these strings — they are free-form and only matched by string equality in downstream checks
- Any refactor that renames these strings breaks silently

### HIDDEN COUPLING: PipelineEvents constants vs manual strings
- `PipelineEvents` defines 18 string constants
- `AiGateway.kt:54` uses raw `"model_attempt"` instead of a PipelineEvents constant
- 5 PipelineEvents constants are dead (never referenced) — dead code that creates misleading documentation

### UNSAFE: CountDownLatch.await() blocks IO dispatcher thread
- `GeneratorViewModel.kt:91,155` blocks the IO thread pool inside a coroutine
- Under load (multiple simultaneous generations), this can exhaust the IO dispatcher thread pool
- Coroutine-aware alternative: `withTimeout(30_000L)` + `suspendCoroutine`

### UNSAFE: `bridgePersistenceScope.launch` in callback (before resume)
- Fire-and-forget persistence is scheduled inside the OkHttp callback thread, not a coroutine
- `bridgePersistenceScope.launch` creates a coroutine from the callback thread — safe by Kotlin coroutine design, but creates non-trivial ordering: persistence may complete AFTER the resuming coroutine has already processed the result and returned to the user

### OBSERVATION: FailureType enum is dead for upstream consumption
- `FailureType` (GeminiService.kt:33-39) is defined but never serialized or exported
- Consumers see `ModelAttemptResult.failureType: String?` — raw, unvalidated strings
- The enum only matters for one decision loop inside `tryChain()`
- If the tryChain loop were refactored, the enum would have zero consumers

### OBSERVATION: No single source of truth for failure type strings
- `classifyError()` in GeminiService.kt defines one mapping
- `classifyFailure()` in AiGateway.kt defines a second, overlapping mapping
- 17 distinct raw strings can appear in `ModelAttemptResult.failureType`
- No file lists all valid values
- No validation at write time (can write any string)
- No validation at read time (consumers use `when` with `else` catch-all)

### Count: 9 risk flags (3 BREAKING, 3 HIDDEN COUPLING, 2 UNSAFE, 2 OBSERVATION)
