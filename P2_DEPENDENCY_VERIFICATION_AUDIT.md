# Dependency Verification — Repository Audit

**Date**: 2026-07-03
**Scope**: Read-only audit. No modifications, no patches, no implementation proposals.

---

## 1. OptimizationOrchestrator.kt

**File**: `app/src/main/java/com/vantedge/app/data/domain/OptimizationOrchestrator.kt`

### UUID Creation Points

| Method | Line | Code | Scope (lines) |
|---|---|---|---|
| `runGenerationFromCycle()` | 94 | `val correlationId = UUID.randomUUID().toString().take(8)` | 94–203 |
| `runFullPipeline()` | 214 | `val correlationId = UUID.randomUUID().toString().take(8)` | 214–337 |
| `applyDesign()` | 465 | `val correlationId = UUID.randomUUID().toString().take(8)` | 465–528 |
| `runAnalysisFresh()` | 345 | `val correlationId = UUID.randomUUID().toString().take(8)` | 345–459 |

Every UUID is method-scoped (`val`), generated at method entry, and live through the method body.

### Invocations of `generateCv()`

#### Call 1 — `runGenerationFromCycle():113`

```kotlin
generatorEngine.generateCv(
    profile = cycle.profileSnapshot,   // UserProfile (domain object)
    jobDescription = enrichedJobDescription, // String (free text)
    designId = "modern",               // String literal
    schemeId = "navy",                 // String literal
    jobTitle = cycle.jobTitle,         // String — business field, from GenerationCycle
    company = cycle.company,           // String — business field, from GenerationCycle
    onResult = { ... }                 // callback
)
```

`correlationId` (line 94, UUID) **in scope but NOT passed**. Lines 94–132 inclusive — variable is alive.

#### Call 2 — `runFullPipeline():241`

```kotlin
generatorEngine.generateCv(
    profile = profile,                  // UserProfile (method parameter)
    jobDescription = enrichedJobDescription, // String (local variable)
    designId = "modern",                // String literal
    schemeId = "navy",                  // String literal
    jobTitle = jobTitle,                // String — business field (method parameter)
    company = company,                  // String — business field (method parameter)
    onResult = { ... }                  // callback
)
```

`correlationId` (line 214, UUID) **in scope but NOT passed**. Lines 214–260 inclusive — variable is alive.

### Invocations of `generateCoverLetter()`

#### Call 1 — `runGenerationFromCycle():136`

```kotlin
generatorEngine.generateCoverLetter(
    profile = cycle.profileSnapshot,
    jobDescription = enrichedJobDescription,
    designId = "modern",
    schemeId = "navy",
    jobTitle = cycle.jobTitle,
    company = cycle.company,
    onResult = { ... }
)
```

`correlationId` (line 94, UUID) **in scope but NOT passed**.

#### Call 2 — `runFullPipeline():265`

```kotlin
generatorEngine.generateCoverLetter(
    profile = profile,
    jobDescription = enrichedJobDescription,
    designId = "modern",
    schemeId = "navy",
    jobTitle = jobTitle,
    company = company,
    onResult = { ... }
)
```

`correlationId` (line 214, UUID) **in scope but NOT passed**.

### Method Signatures (GeneratorEngine)

```kotlin
suspend fun generateCv(
    profile: UserProfile,
    jobDescription: String,
    designId: String,
    schemeId: String,
    jobTitle: String,
    company: String,
    onResult: (EngineResult) -> Unit
)

suspend fun generateCoverLetter(
    profile: UserProfile,
    jobDescription: String,
    designId: String,
    schemeId: String,
    jobTitle: String,
    company: String,
    onResult: (EngineResult) -> Unit
)
```

**No `correlationId` parameter exists in either signature.**

### Correlation Variable Scope at Each Call Site

| Call Site | Method | Line | `correlationId` in scope? | Type | Value example |
|---|---|---|---|---|---|
| `generateCv` | `runGenerationFromCycle` | 113 | **YES** — line 94 | `String` (UUID 8-char) | `"a1b2c3d4"` |
| `generateCoverLetter` | `runGenerationFromCycle` | 136 | **YES** — line 94 | `String` (UUID 8-char) | `"a1b2c3d4"` |
| `generateCv` | `runFullPipeline` | 241 | **YES** — line 214 | `String` (UUID 8-char) | `"e5f6g7h8"` |
| `generateCoverLetter` | `runFullPipeline` | 265 | **YES** — line 214 | `String` (UUID 8-char) | `"e5f6g7h8"` |

**At every call site, the correlation variable exists and is in scope.** The defect is pure non-propagation — the variable is created but never passed as an argument.

---

## 2. GeneratorViewModel.kt

**File**: `app/src/main/java/com/vantedge/app/data/viewmodel/GeneratorViewModel.kt`

### Instantiation

```kotlin
// Navigation.kt:399
GeneratorViewModel(
    historyStore = historyStore,
    aiGateway = aiGateway
)
```

**Receives only `HistoryStore` and `AiGateway`. Does NOT receive `OptimizationOrchestrator`.**

### Engine Ownership

```kotlin
// GeneratorViewModel.kt:31
private val engine = GeneratorEngine(aiGateway)
```

Creates its own private `GeneratorEngine` instance. This is a **separate instance** from the one shared by `OptimizationOrchestrator` (created at `Navigation.kt:106`).

### Invocation Path

#### `generate()` method — lines 52–199

```kotlin
fun generate(
    profile: UserProfile,         // from UI
    jobTitle: String,             // from UI — business field
    company: String,              // from UI — business field
    jobDescription: String,       // from UI — free text
    mode: String = "html",        // from UI — literal
    designId: String = "modern",  // default literal
    schemeId: String = "navy",    // default literal
    context: Context? = nil       // from UI — optional
)
```

#### Direct calls to GeneratorEngine (no orchestrator)

| Call | Line | Target |
|---|---|---|
| `engine.generateCvDocx(...)` | 73 | GeneratorEngine (direct) |
| `engine.generateCv(...)` | 78 | GeneratorEngine (direct) |
| `engine.generateCoverLetter(...)` | 143 | GeneratorEngine (direct) |

#### UUID creation in GeneratorViewModel

**NONE.** The class has no `UUID.randomUUID()` call anywhere. No correlation ID is generated at any point in the invocation chain.

#### Correlation ID scope at call sites

| Call | Line | correlationId available? |
|---|---|---|
| `engine.generateCv(...)` | 78 | **NO** — no UUID in scope, no parameter, no field |
| `engine.generateCoverLetter(...)` | 143 | **NO** — same state |

### Transport vs Origin Analysis

| Criterion | Evidence | Verdict |
|---|---|---|
| Calls orchestrator? | **NO** — no `OptimizationOrchestrator` import or field. Calls `engine.generateCv()` directly. | **Bypasses orchestrator entirely** |
| Receives correlationId from caller? | **NO** — `generate()` method has no correlationId parameter | **No propagation path exists** |
| Generates its own correlationId? | **NO** — no `UUID.randomUUID()` in file | **Does not originate identity** |
| Stores workflow identity? | **NO** — no correlation field, no request-scoped state | **No identity state** |
| Persists results without correlation? | **YES** — `historyStore.addRecord(record)` at lines 132, 192. Record has `jobTitle`, `company`, `cv`, `coverLetter` — **no correlationId field** | **Records not traceable** |

### Verdict

GeneratorViewModel is **NOT pure transport**. It **bypasses the orchestrator** and calls GeneratorEngine directly. It neither receives nor generates a correlation ID. However, it also does NOT originate workflow identity — it simply omits the concept entirely.

The class performs **transport + persistence** for the direct generation path, while `CycleViewModel` handles the **orchestrated path** (analysis → generation → design). These are two parallel and independent generation entry points.

---

## 3. Complete Call Graph

```
                            UI Layer
                               |
              +----------------+----------------+
              |                                 |
   CycleViewModel                           GeneratorViewModel
   (orchestrator injected)                  (engine created inline)
              |                                 |
              v                                 v
   OptimizationOrchestrator               GeneratorEngine (private)
   (shared GeneratorEngine)                     |
              |                                 |
              +------------+--------------------+
                           |
                           v
                    GeneratorEngine (shared)
                           |
                      (line 84)
                           v
                    P2ValidationEngine
                    .validateGeneratorOutput()
```

### Dependency Summary

| Dependency | Affects Propagation? | Detail |
|---|---|---|
| `GeneratorEngine` method signature | **YES** — lacks `correlationId` param | Blocking — cannot propagate without it |
| `OptimizationOrchestrator` correlation in scope | **NO** — variable exists at every call site | Ready for propagation — zero changes needed to scope |
| `CycleViewModel` → `OptimizationOrchestrator` | **NO** — fully wired | Receives orchestrator via constructor injection |
| `GeneratorViewModel` → `GeneratorEngine` | **YES** — bypasses orchestrator | Has own `GeneratorEngine` instance, lacks UUID |
| `ApplicationRecord` model | **YES** — no correlationId field | Cannot store correlation ID in persisted records |
| `GenerationCycle` model | No | Has `jobTitle`/`company` but no correlationId field |
| `HistoryStore` | No | Receives records as-is; no correlation logic |

---

## 4. Key Repository Facts

### Parameter flow for orchestrator path

```
CycleViewModel.runFullPipeline(profile, jobTitle, company, jobDescription, mode)
    ↓
OptimizationOrchestrator.runFullPipeline(profile, jobTitle, company, jobDescription, mode)
    ↓ line 214: val correlationId = UUID...
    ↓ line 241: generatorEngine.generateCv(..., jobTitle, company, onResult)
    └── correlationId NOT PASSED — in scope at line 214, alive at 241
    ↓
GeneratorEngine.generateCv(..., jobTitle, company, ...)
    ↓ line 84: validateGeneratorOutput(json, jobTitle)
    └── jobTitle MISUSED as correlationId
```

### Parameter flow for direct path

```
GeneratorViewModel.generate(profile, jobTitle, company, jobDescription, ...)
    ↓ line 78: engine.generateCv(..., jobTitle, company, ...)
    └── NO correlationId AT ALL
    ↓
GeneratorEngine.generateCv(..., jobTitle, company, ...)
    ↓ line 84: validateGeneratorOutput(json, jobTitle)
    └── jobTitle MISUSED as correlationId
```

### Propagation blockers

| Blocker | File | Lines | Fix required |
|---|---|---|---|
| `GeneratorEngine.generateCv()` missing `correlationId` param | `GeneratorEngine.kt` | 21–28 | Add parameter to signature |
| `GeneratorEngine.generateCoverLetter()` missing `correlationId` param | `GeneratorEngine.kt` | 105–112 | Add parameter to signature (consistency) |
| `runFullPipeline()` does not pass `correlationId` | `OptimizationOrchestrator.kt` | 241, 265 | Add argument at call sites |
| `runGenerationFromCycle()` does not pass `correlationId` | `OptimizationOrchestrator.kt` | 113, 136 | Add argument at call sites |
| `GeneratorViewModel` has no UUID at all | `GeneratorViewModel.kt` | 78, 143 | Generate UUID + pass to engine |
| `ApplicationRecord` lacks correlationId field | model | 126–131, 186–192 | No fix needed for P2 scope (persistence, not validation) |

---

Report contains only observed repository evidence: method signatures, call graph, line references, parameter flow, and dependencies. No code changes, patches, or implementation proposals are included.
