# Integration Architecture Report

## Contract Enforcement Layer — Pipeline Integration

---

## 1. Current Execution Path

The complete flow from AI provider response to scoring:

```
OpenRouter API (HTTP 200)
    ↓
GeminiService.tryModel()  [lines 420–889 of GeminiService.kt]
    ├── 1. Insert AiRawResponseArtifact (forensic artifact) — Path A
    ├── 2. Transport-layer response body validation:
    │      EMPTY_BODY → resume(null)
    │      MALFORMED_SHORT (<50 chars) → resume(null)
    │      NO_JSON_STRUCTURE (no '{') → resume(null)
    │      invalid_json (JSONObject parse fails) → resume(null)
    │      provider_error (response has "error" key) → resume(null)
    │      missing "choices" → MODEL_CONTRACT_VIOLATION → resume(null)
    ├── 3. Extract content from choices[0].message.content
    ├── 4. JsonExtractionEngine.extract(content)  [line 761]
    │      → ExtractionResult(content, strategy, success, failureReason)
    ├── 5. JSONObject(extracted.content) parse check  [line 777–801]
    │      → success → continue; failure → MODEL_CONTRACT_VIOLATION
    └── 6. Return extracted.content (String) via cont.resume()
    ↓
String? (nullable — null = all models exhausted)
    ↓
AiGateway.generate("compatibility", request)  [line 80 of AiGateway.kt]
    ├── Wraps GeminiService result as AiResult (internal only)
    └── Returns String? (raw extracted JSON or null)
    ↓
CompatibilityEngine.analyze()  [lines 31–121 of CompatibilityEngine.kt]
    ├── 1. null check → Failure("null_response")
    ├── 2. JsonExtractionEngine.extract(result)  ← REDUNDANT EXTRACTION
    │      → Failure("no_json") if unsuccessful
    ├── 3. JSONObject(extracted)  ← FIRST PARSE ATTEMPT
    │      → parseCompatibilityRecord() → CompatibilityRecord
    │      → validateCompatibilityRecord() → Failure("contract_violation") if fails
    │      → Success(record) if passes
    ├── 4. JsonRepairUtil.repair(extracted)  ← REPAIR FALLBACK
    │      → Failure("parse_error") if repair unsafe
    └── 5. JSONObject(repairResult.json)  ← SECOND PARSE ATTEMPT
           → parseCompatibilityRecord() → validateCompatibilityRecord()
           → Success(record) or Failure("parse_error")
    ↓
CompatibilityResult { Success(data) | Failure(type, message, ...) }
    ↓
OptimizationOrchestrator.runAnalysisFresh()  [lines 310–328]
    ├── Success → record
    └── Failure → throw IllegalStateException (aborts pipeline)
    ↓
GenerationCycle (with CompatibilityRecord, matchedKeywords, cvContent, ...)
    ↓
HistoryStore.saveCycle() → CycleDao → Room DB
```

### Participating Components (in order)

| # | Component | File | Role |
|---|-----------|------|------|
| 1 | `GeminiService` | network/GeminiService.kt | HTTP transport, circuit breaker, response body validation, content extraction |
| 2 | `JsonExtractionEngine` | engine/extraction/JsonExtractionEngine.kt | Stateless JSON extraction from raw AI response (4 strategy attempts) |
| 3 | `AiGateway` | network/AiGateway.kt | Request dispatching, attempt tracking, telemetry |
| 4 | `CompatibilityEngine` | engine/CompatibilityEngine.kt | AI prompt construction, redundant extraction, JSON repair, record parsing, structural validation |
| 5 | `JsonRepairUtil` | engine/JsonRepairUtil.kt | JSON repair fallback (brace balancing, trailing comma removal) |
| 6 | `OptimizationOrchestrator` | data/domain/OptimizationOrchestrator.kt | High-level pipeline sequencing, unwraps CompatibilityResult via exception |
| 7 | `HistoryStore` / `CycleDao` | data/storage/ | Persistence of GenerationCycle (includes CompatibilityRecord) |

### Parallel flows (not on the Primary Execution Path)

- **Profile extraction path**: `ProfileExtractionEngine` → calls `AiGateway.generate("profile_extraction", ...)` → uses `JsonRepairUtil` + `ExtractionValidator` for CV-specific validation. This path has its own Gate 0–3 validators and does NOT pass through `CompatibilityEngine` or `ContractValidator`.
- **Job extraction path**: `JobExtractionOrchestrator` → `LlmJobExtractor` → calls `AiGateway.generate("job_extraction", ...)` → uses `SchemaValidator` + `SemanticValidator` + `ConfidenceEngine`. This path also does NOT pass through `CompatibilityEngine` or `ContractValidator`.

---

## 2. Orchestration Boundary

### Primary Orchestration Component

**Class:** `CompatibilityEngine`  
**File:** `app/src/main/java/com/vantedge/app/data/engine/CompatibilityEngine.kt`  
**Method:** `analyze(profile, jobTitle, company, jobDescription): CompatibilityResult`

This is the single component that owns the transition from AI response extraction to structured compatibility data (which feeds scoring). It is the only consumer of `AiGateway.generate()` output that produces a scoring input.

#### Component Detail

| Aspect | Detail |
|--------|--------|
| **Class** | `CompatibilityEngine` |
| **Constructor dependency** | `AiGateway` |
| **Entry method** | `suspend fun analyze(profile: UserProfile, jobTitle: String, company: String, jobDescription: String): CompatibilityResult` |
| **Responsibility** | Build prompts, call AI, extract JSON, parse to `CompatibilityRecord`, validate structural contract, return sealed result |
| **Input** | `UserProfile` + job strings |
| **Output** | `CompatibilityResult { Success(CompatibilityRecord) \| Failure(type, message, throwable?, rawResponse?) }` |
| **Secondary extraction call** | Calls `JsonExtractionEngine.extract(result)` at line 86 — AFTER `AiGateway` has already returned extracted content |

#### Why This Is the Integration Point

The `ContractValidator` receives `ExtractedAiPayload(rawJson, parsedObject: Jackson JsonNode, metadata)`. The `CompatibilityEngine` currently:
1. Receives the extracted JSON string from `AiGateway`
2. Calls `JsonExtractionEngine.extract(result)` redundantly (since `GeminiService` already did this)
3. Parses with `org.json.JSONObject` (not Jackson)
4. Performs its own structural validation via `validateCompatibilityRecord()` (range checks)

The natural integration point is inside `CompatibilityEngine.analyze()`, replacing the redundant `JsonExtractionEngine.extract()` call and the ad-hoc `JSONObject` / `validateCompatibilityRecord()` with the `ContractValidator`.

### Secondary Orchestration Component (Domain Layer)

**Class:** `OptimizationOrchestrator`  
**File:** `app/src/main/java/com/vantedge/app/data/domain/OptimizationOrchestrator.kt`  
**Method:** `runAnalysisFresh()` (private, line 310)

This orchestrator calls `CompatibilityEngine.analyze()` and unwraps the sealed result. It is the domain-level sequencer but does not participate in the extraction→validation→scoring contract directly — it only consumes the final `CompatibilityResult`.

---

## 3. Current Contracts

### 3.1 JsonExtractionEngine → GeminiService

```
fun extract(raw: String): ExtractionResult

data class ExtractionResult(
    content: String,        // Extracted JSON string
    strategy: String,       // "direct_parse" | "markdown_strip" | "nesting_aware_root" | "substring_scan" | "failed"
    success: Boolean,
    failureReason: String?  // null on success, "ALL_STRATEGIES_FAILED" on failure
)
```

**Used at:** `GeminiService.kt` line 761 (in `tryModel()` companion `extractContent()`)

### 3.2 GeminiService → AiGateway

```
fun generate(requestId, request, onProgress, onModelResult, budgetDeadlineMs): String?
```

**Return:** `String?` — the extracted JSON content string, or `null` if all models exhausted.

### 3.3 AiGateway → CompatibilityEngine

```
fun generate(tag: String, request: AiRequest, onProgress): String?
```

**Return:** `String?` — same extracted JSON content string as above, passed through transparently.

### 3.4 CompatibilityEngine → OptimizationOrchestrator

```
sealed class CompatibilityResult {
    data class Success(val data: CompatibilityRecord) : CompatibilityResult()
    data class Failure(
        val type: String,           // "null_response" | "no_json" | "contract_violation" | "parse_error"
        val message: String?,
        val throwable: Throwable? = null,
        val rawResponse: String? = null
    ) : CompatibilityResult()
}
```

### 3.5 ContractValidator (currently unused)

```
fun validate(jobType: JobType, payload: ExtractedAiPayload): ContractValidationResult

data class ExtractedAiPayload(
    val rawJson: String,
    val parsedObject: JsonNode,         // Jackson JsonNode (not org.json.JSONObject)
    val metadata: ExtractionMetadata
)

data class ExtractionMetadata(
    val requestId: String,
    val correlationId: String,
    val modelName: String,
    val extractionStrategy: String,
    val rawLength: Int,
    val normalizedLength: Int
)

sealed class ContractValidationResult {
    data class Success(
        val validatedObject: ValidatedAiPayload,  // Contains deep-copied Jackson JsonNode
        override val confidence: Float = 1.0f
    ) : ContractValidationResult()
    data class Failure(
        val reason: ContractFailureReason,        // Sealed taxonomy
        val details: String,
        val correlationId: String,
        val rawSnapshot: String,
        override val confidence: Float = 0.0f
    ) : ContractValidationResult()
}
```

### 3.6 Contract Impact Summary

| Contract | Changes Required? | Reason |
|----------|------------------|--------|
| `JsonExtractionEngine.extract()` → `ExtractionResult` | YES | Must also produce Jackson `JsonNode` alongside `ExtractionResult.content` |
| `GeminiService` → `AiGateway` (`String?`) | YES | Must pass Jackson `JsonNode` alongside extracted content string |
| `AiGateway` → `CompatibilityEngine` (`String?`) | YES | Must pass Jackson `JsonNode` alongside extracted content string |
| `CompatibilityEngine.analyze()` → `CompatibilityResult` | NO | Outer sealed class unchanged; internal extraction replaced by `ContractValidator` |
| `OptimizationOrchestrator` → callers (`GenerationCycle`) | NO | No contract change |
| `ContractValidator` | Internal only | Already defined; no change needed |

---

## 4. Integration Strategy

### Minimum Architectural Change

The integration point is `CompatibilityEngine.analyze()` lines 80–99. The current flow:

```
AiGateway.generate() → String?
    ↓ (line 86)
JsonExtractionEngine.extract(result) → ExtractionResult
    ↓ (line 93)
JSONObject(extracted) → parseCompatibilityRecord → validateCompatibilityRecord
```

This must become:

```
AiGateway.generate() → String? + JsonNode (via ExtractionResult enhancement)
    ↓
ContractValidator.validate(JobType.VACANCY_SCORING, ExtractedAiPayload)
    ↓ Success → ValidatedAiPayload (contains Jackson tree)
    ↓ Failure → map to CompatibilityResult.Failure
    ↓
parseCompatibilityRecord(ValidatedAiPayload.node) → CompatibilityRecord
```

### What Changes

1. **`ExtractionResult`** — Add a `parsedNode: JsonNode?` field (nullable, non-null on success). The `JsonExtractionEngine` already has all JSON parsing logic; populate this field when `success == true`.

2. **`GeminiService.tryModel()`** — After `JSONObject(extracted.content)` parse check succeeds (line 799), also produce a Jackson `JsonNode` and pass it along. This means the return type changes from `String?` to a richer type, OR the `JsonNode` is threaded through separately.

   The cleanest approach: Return `String?` from `tryModel` as today, but emit the `JsonNode` via the existing `onModelResult` callback or a new callback. Or, since `GeminiService` already returns `extracted.content` (the JSON string), and `CompatibilityEngine` re-extracts it redundantly, the `JsonNode` can be reconstructed inside `CompatibilityEngine` from the content string using Jackson's `ObjectMapper.readTree()`.

   **Recommended (lowest risk):** Add Jackson `ObjectMapper` to `CompatibilityEngine`, deserialize the returned `result` string to `JsonNode` there, and pass it to `ContractValidator`. This avoids changing the `GeminiService` → `AiGateway` → `CompatibilityEngine` contract.

3. **`CompatibilityEngine.analyze()`** — Replace lines 86–99 with:
   - Jackson `ObjectMapper.readTree(result)` → `JsonNode`
   - Construct `ExtractionMetadata` from available context
   - Construct `ExtractedAiPayload(rawJson=result, parsedObject=jsonNode, metadata)`
   - Call `ContractValidator.validate(JobType.VACANCY_SCORING, payload)`
   - On `Success` → extract Jackson tree → `parseCompatibilityRecord()` (adapted for Jackson)
   - On `Failure` → `CompatibilityResult.Failure("contract_violation", ...)`

4. **`parseCompatibilityRecord`** — Currently uses `org.json.JSONObject`. Must be adapted to accept `Jackson JsonNode` instead, using Jackson's tree traversal API (`get()`, `path()`, etc). The field mapping logic remains identical.

5. **`validateCompatibilityRecord`** — Replaced by `ContractValidator`. The range checks (score 0..100, non-negative stats) become part of the vacancy scoring schema in `ContractValidator.validateVacancyScoring()`.

### What Does NOT Change

- `GeminiService` — No changes to HTTP transport, circuit breaker, or forensic artifact logic
- `AiGateway` — No changes to request dispatching or attempt tracking
- `OptimizationOrchestrator` — No changes; continues to consume `CompatibilityResult`
- `CycleViewModel` / `CompatibilityViewModel` — No changes
- `HistoryStore` / `CycleDao` — No changes
- `GenerationCycle` — No changes
- `ProfileExtractionEngine` / `JobExtractionOrchestrator` — No changes
- `JsonRepairUtil` — No changes (can remain as fallback if adaptation needed)
- `ContractValidator` / `ValidatedAiPayload` / `ExtractedAiPayload` — Already defined; internal hardening patches already applied
- `JsonExtractionEngine` — No changes needed if `JsonNode` is produced inside `CompatibilityEngine`

---

## 5. Failure Propagation

### Current Failure Handling

In `OptimizationOrchestrator.runAnalysisFresh()` (line 322–327):

```kotlin
return when (result) {
    is CompatibilityResult.Success -> result.data
    is CompatibilityResult.Failure -> throw IllegalStateException(
        "Compatibility analysis failed: ${result.type} - ${result.message}"
    )
}
```

`CompatibilityResult.Failure` is converted to an exception at the domain boundary. The exception propagates:
- `runAnalysisOnly()` → `catch (e: Exception)` → `PipelineTrace.error()` → `throw e`
- `runFullPipeline()` → `catch (e: Exception)` → `PipelineTrace.error()` → `throw e`
- `CycleViewModel.runPipeline()` → catches exception → sets `PipelineUiState.Error`

### Proposed Failure Propagation

`ContractValidationResult.Failure` is mapped to `CompatibilityResult.Failure` inside `CompatibilityEngine.analyze()`:

| `ContractFailureReason` | `CompatibilityResult.type` | Notes |
|-------------------------|---------------------------|-------|
| `TruncatedJson` | `"contract_violation"` | Details appended: "JSON truncated" |
| `EmptyContent` | `"contract_violation"` | Details appended |
| `InvalidJsonStructure` | `"contract_violation"` | Details appended |
| `MissingRequiredFields` | `"contract_violation"` | Details list missing fields |
| `TypeMismatch` | `"contract_violation"` | Details list mismatched fields |
| `UnsupportedSchemaVersion` | `"contract_violation"` | Should not occur for VACANCY_SCORING |
| `SchemaMismatch` | `"contract_violation"` | Details appended |
| `UnknownModelOutputFormat` | `"contract_violation"` | Details appended |

The existing `OptimizationOrchestrator` exception conversion is preserved unchanged.

### PipelineTrace Integration

The `ContractValidator` currently does not emit `PipelineTrace` events. It should emit a `VALIDATION_RESULT` trace event at the `contract_validation` stage with:
- `reason.code` on failure
- `confidence` value
- `correlationId` and `requestId` (from `ExtractionMetadata`)
- `elapsedMs` for observability

This is added to `ContractValidator.validate()` as a single `PipelineTrace.dataQuality()` call before returning.

### Is an Adapter Required?

No. The `ContractValidationResult` maps 1:1 to `CompatibilityResult.Failure` fields. The adapter is a simple `when` expression inside `CompatibilityEngine.analyze()`.

---

## 6. Dependency Analysis

### Classes Requiring Modification

| Class | Why It Changes | Contract Change | Downstream Impact |
|-------|---------------|-----------------|-------------------|
| **`CompatibilityEngine`** | Integration point — replace `JsonExtractionEngine.extract()` + `JSONObject` + `validateCompatibilityRecord()` with `ContractValidator` | Internal: removes redundant extraction, replaces `validateCompatibilityRecord` private method with `ContractValidator` | None — public API `analyze()` signature unchanged; `CompatibilityResult` sealed class unchanged |
| **`CompatibilityEngine.parseCompatibilityRecord()`** | Must accept Jackson `JsonNode` instead of `org.json.JSONObject` | Private method signature changes | None — only called internally |
| **`ContractValidator`** | Must emit `PipelineTrace` events for observability | Minor addition — no contract change | None — internally emits events |
| **`ContractValidator`** | Must accept `jsonExtractionEngine: JsonExtractionEngine?` or equivalent for the binding between transport extraction and contract validation | N/A — constructor injection | None — already detached from pipeline |
| **`ContractValidator.validateVacancyScoring()`** | Must add range constraints (score 0..100, non-negative stats) currently in `validateCompatibilityRecord()` | Internal schema expansion | None — replaces existing enforcement |

### Classes Confirmed to Require No Changes

| Class | File | Evidence |
|-------|------|----------|
| `GeminiService` | network/GeminiService.kt | Integration happens in `CompatibilityEngine`; transport layer unchanged |
| `AiGateway` | network/AiGateway.kt | Pass-through layer; no contract change needed |
| `JsonExtractionEngine` | engine/extraction/JsonExtractionEngine.kt | New Jackson node produced inside `CompatibilityEngine`, not here |
| `JsonRepairUtil` | engine/JsonRepairUtil.kt | Fallback path may be removed entirely; no changes required |
| `OptimizationOrchestrator` | data/domain/OptimizationOrchestrator.kt | Consumes `CompatibilityResult` unchanged |
| `CycleViewModel` | data/viewmodel/CycleViewModel.kt | No contract change |
| `CompatibilityViewModel` | data/viewmodel/CompatibilityViewModel.kt | Separate path; no contract change |
| `HistoryStore` / `CycleDao` | data/storage/ | Data model `GenerationCycle` unchanged |
| `GenerationCycle` | data/model/GenerationCycle.kt | No change |
| `CompatibilityRecord` | data/model/CompatibilityRecord.kt | No change — still produced by `parseCompatibilityRecord()` |
| `ProfileExtractionEngine` | engine/ProfileExtractionEngine.kt | Separate extraction pipeline |
| `JobExtractionOrchestrator` | domain/extraction/JobExtractionOrchestrator.kt | Separate extraction pipeline |
| `ExtractionValidator` | engine/ExtractionValidator.kt | Profile-specific; no change |
| `PipelineTrace` / `PipelineEvents` | domain/ | Constants unchanged; `ContractValidator` emits events using existing API |
| `AiRawResponseArtifact` / DAO | data/model/ data/storage/ | Transport forensic store; unchanged |
| `ValidatedAiPayload` | pipeline/contract/ValidatedAiPayload.kt | Already defined and hardened |
| `ExtractedAiPayload` | pipeline/contract/ExtractedAiPayload.kt | Already defined |
| `ExtractionMetadata` | pipeline/contract/ExtractionMetadata.kt | Already defined |
| `ContractFailureReason` | pipeline/contract/ContractFailureReason.kt | Already defined |
| `ContractValidationResult` | pipeline/contract/ContractValidationResult.kt | Already defined |
| `JobType` | pipeline/contract/JobType.kt | Already defined |
| `Navigation.kt` | navigation/Navigation.kt | Composition root; new `ContractValidator` instance created here if needed |

---

## 7. Risk Assessment

### R1: Hidden Coupling — `CompatibilityEngine` and `JsonExtractionEngine`

**Evidence:** `CompatibilityEngine` calls `JsonExtractionEngine.extract(result)` at line 86, but `GeminiService` already performed the exact same extraction at line 761 and returned the successfully-extracted content string. The second extraction in `CompatibilityEngine` is always against an already-extracted string, making the `ExtractionResult.content` identical to its input. Only the `strategy` field differs (it will be `"direct_parse"` since the input is already valid JSON).

**Impact:** Low. The redundant extraction is harmless but masks the architectural boundary. When `ContractValidator` is introduced, this redundancy must be eliminated to avoid confusion about where extraction ends and validation begins.

### R2: Duplicate Validation — Structural Checks in Two Locations

**Evidence:** `CompatibilityEngine.validateCompatibilityRecord()` (line 218–226) performs structural range checks:
- `score in 0..100`
- `yearsExperience >= 0`
- `certificationCount >= 0`
- `skillCount >= 0`
- `matchedCount >= 0`
- `gapCount >= 0`

The `ContractValidator.validateVacancyScoring()` (ContractValidator.kt lines 99–137) performs a different set of checks:
- Required fields exist and are non-null: `score`, `vacancyScore`, `roleSummary`, `eligibilitySummary`, `profileStats`, `relevancyItems`
- Type checks: `isInt`, `isTextual && isNotBlank`, `isObject`, `isArray`

**Impact:** Medium. These are complementary (range vs. presence/type), but the `ContractValidator` does not enforce the range constraints that `validateCompatibilityRecord()` currently enforces. After integration, the range constraints must be relocated to the vacancy scoring schema in `ContractValidator`, and the private `validateCompatibilityRecord()` method must be removed.

### R3: Contract Conflict — Jackson `JsonNode` vs `org.json.JSONObject`

**Evidence:** The `ContractValidator` is built on Jackson (`com.fasterxml.jackson.databind.JsonNode`). The `CompatibilityEngine` currently uses `org.json.JSONObject` (Android SDK). The contract layer (`ExtractedAiPayload.parsedObject`) is `JsonNode`. The current pipeline has zero Jackson infrastructure. The `parseCompatibilityRecord()` method uses `JSONObject.getInt()`, `JSONObject.getString()`, `JSONObject.getJSONArray()`, `JSONObject.getJSONObject()`, `optInt()`, `optString()`, `optBoolean()`, `optJSONObject()`, `optJSONArray()`.

**Impact:** High. `parseCompatibilityRecord()` must be rewritten to use Jackson tree API (`JsonNode.get()`, `.asInt()`, `.asText()`, `.isArray()`, `.get(index)`, `.elements()`, etc.) instead of `org.json.JSONObject`. This is a mechanical translation — the field mapping logic remains identical — but every accessor call changes. Jackson 2.15.2 is already declared as a project dependency (from previous work).

### R4: Circular Dependencies — Detection: None

No package-level or class-level circular dependencies were found. `com.vantedge.pipeline.contract` is entirely self-contained with zero external imports beyond Jackson and the Kotlin stdlib.

### R5: Observability Gap — `ContractValidator` Currently Silent

**Evidence:** The `ContractValidator.validate()` method has no `PipelineTrace` calls. Decision outcomes (truncated, empty, invalid structure, missing fields, type mismatch, unsupported schema) are returned as `ContractValidationResult` but not emitted as trace events. Pipeline operators cannot observe validation decisions without adding instrumentation.

**Impact:** Low. Can be addressed by adding a single `PipelineTrace.dataQuality()` call at the end of `validate()`. The `PipelineEvents` constant `VALIDATION_RESULT` already exists.

### R6: Hidden Coupling — `ContractValidator` Owns No State, But…

**Evidence:** `ContractValidator` is currently stateless (no constructor parameters, no fields beyond the `validators` map which is a compile-time constant). It consumes `ExtractedAiPayload` and produces `ContractValidationResult`. It does not inject `PipelineTrace` — it accesses it by direct object reference. This is consistent with the existing pattern in `JsonExtractionEngine`, `AiGateway`, and `GeminiService`.

**Impact:** Low. If `PipelineTrace` access pattern changes in the future, `ContractValidator` must be updated alongside every other component.

### R7: Integration Point Scope — `CompatibilityEngine.analyze()` Is the Right Place

**Evidence:** The execution path confirms that `CompatibilityEngine.analyze()` is the single orchestration component that receives extracted AI content and transforms it into a scoring input. No other component in the primary path performs this transformation. Inserting `ContractValidator` between the AI response string and `CompatibilityRecord` construction is architecturally correct — it does not move responsibilities, introduce layer contamination, or bypass orchestration.

---

## 8. Implementation Sequence

The following sequence eliminates architectural ambiguity. No implementation details are specified below.

### Phase 1: Enable `parseCompatibilityRecord` to accept Jackson `JsonNode`

- Adapt the private method in `CompatibilityEngine` to accept `JsonNode` alongside or instead of `JSONObject`
- The field mapping logic (extracting score, stats, relevancy items, gaps, courses) is preserved identically — only the accessor API changes (`get()`, `asInt()`, `asText()`, `elements()`, etc.)
- `validateCompatibilityRecord()` is temporarily kept alongside the new method

### Phase 2: Integrate `ContractValidator` into `CompatibilityEngine.analyze()`

- After `AiGateway.generate()` returns, deserialize the result string to Jackson `JsonNode` via `ObjectMapper.readTree()`
- Construct `ExtractedAiPayload(rawJson=result, parsedObject=node, metadata=...)` — metadata is populated from available context (requestId from AiGateway, model name from last attempt, extraction strategy, lengths)
- Call `ContractValidator.validate(JobType.VACANCY_SCORING, payload)`
- On `ContractValidationResult.Success`, pass the validated Jackson tree to the adapted `parseCompatibilityRecord()`
- On `ContractValidationResult.Failure`, map to `CompatibilityResult.Failure("contract_violation", ...)`
- Remove the now-redundant `JsonExtractionEngine.extract()` call
- Remove `validateCompatibilityRecord()` — its constraints are relocated to `ContractValidator.validateVacancyScoring()`

### Phase 3: Add range constraints to `ContractValidator.validateVacancyScoring()`

- The `score` field must pass `in 0..100` in addition to `isInt`
- The `profileStats` sub-fields (`yearsExperience`, `certificationCount`, `skillCount`, `matchedCount`, `gapCount`) must pass non-negative checks
- These replace the removed `validateCompatibilityRecord()`

### Phase 4: Add `PipelineTrace` emission to `ContractValidator.validate()`

- Before returning, emit `PipelineTrace.dataQuality(stage="contract_validation", issue="VALIDATION_RESULT", details=...)`
- Include `reason.code` on failure, `confidence` value, `elapsedMs`, `correlationId`

### Phase 5: Verify no residual coupling

- Confirm `ContractValidator` is the only validation gate between extraction and scoring for the compatibility path
- Confirm `JsonExtractionEngine` is no longer called redundantly
- Confirm `CompatibilityEngine` no longer calls `validateCompatibilityRecord()` directly

---

## Summary Diagram (Post-Integration)

```
AiGateway.generate() → String?
    ↓
CompatibilityEngine.analyze()
    ↓
ObjectMapper.readTree(result) → JsonNode
    ↓
ContractValidator.validate(VACANCY_SCORING, ExtractedAiPayload)
    ↓
┌──────────────────────────────────────────────┐
│  Success(ValidatedAiPayload)                  │
│       ↓                                       │
│  parseCompatibilityRecord(validatedNode)      │
│       ↓                                       │
│  CompatibilityResult.Success(record)          │
└──────────────────────────────────────────────┘
    OR
┌──────────────────────────────────────────────┐
│  Failure(reason, details, ...)                │
│       ↓                                       │
│  CompatibilityResult.Failure("contract_...")  │
└──────────────────────────────────────────────┘
    ↓
OptimizationOrchestrator.runAnalysisFresh()
    ↓
GenerationCycle → HistoryStore
```

---

## Appendix A: Files Referenced

| File | Lines | Role |
|------|-------|------|
| `app/src/main/java/com/vantedge/app/data/network/GeminiService.kt` | 1–890 | HTTP transport, circuit breaker, response body validation |
| `app/src/main/java/com/vantedge/app/data/network/AiGateway.kt` | 1–165 | Request dispatching, attempt tracking |
| `app/src/main/java/com/vantedge/app/data/engine/CompatibilityEngine.kt` | 1–227 | Integration point — prompt building, extraction, parsing, validation |
| `app/src/main/java/com/vantedge/app/data/engine/extraction/JsonExtractionEngine.kt` | 1–164 | Stateless JSON extraction from raw AI output |
| `app/src/main/java/com/vantedge/app/data/engine/JsonRepairUtil.kt` | — | JSON repair fallback |
| `app/src/main/java/com/vantedge/app/data/domain/OptimizationOrchestrator.kt` | 1–398 | Domain-level pipeline sequencing |
| `app/src/main/java/com/vantedge/pipeline/contract/ContractValidator.kt` | 1–198 | Contract enforcement — schema validation, truncation detection |
| `app/src/main/java/com/vantedge/pipeline/contract/ExtractedAiPayload.kt` | 1–13 | Contract layer input type |
| `app/src/main/java/com/vantedge/pipeline/contract/ValidatedAiPayload.kt` | 1–22 | Contract layer output type |
| `app/src/main/java/com/vantedge/pipeline/contract/ExtractionMetadata.kt` | 1–13 | Extraction context metadata |
| `app/src/main/java/com/vantedge/pipeline/contract/ContractValidationResult.kt` | 1–23 | Contract layer sealed result |
| `app/src/main/java/com/vantedge/pipeline/contract/ContractFailureReason.kt` | 1–53 | Deterministic failure taxonomy |
| `app/src/main/java/com/vantedge/pipeline/contract/JobType.kt` | — | Enum of supported job types |
| `app/src/main/java/com/vantedge/app/domain/PipelineTrace.kt` | — | Observability — pipeline stage entry/exit/error/warn |
| `app/src/main/java/com/vantedge/app/domain/PipelineEvents.kt` | — | Observability event constants |
