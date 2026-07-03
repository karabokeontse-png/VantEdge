# P2 Correlation Ownership Audit v1.1

**Date**: 2026-07-03
**Authority**: ChatGPT Canonical Audit Directive (P2 Correlation Ownership Audit v1.1)
**Scope**: Inspection only — no source modification

---

## C1 — Correlation Origin

### File/Method/Line/Format

| Field | Value |
|---|---|
| **File** | `app/src/main/java/com/vantedge/app/data/engine/GeneratorEngine.kt` |
| **Method** | `GeneratorEngine.generateCv()` |
| **Line** | 84 |
| **Code** | `P2ValidationEngine.validateGeneratorOutput(repairResult.json, jobTitle)` |
| **Value used as correlationId** | `jobTitle` (method parameter) |
| **Format** | Free-form string (e.g., `"Senior Engineer"`, `"Software Developer"`) |
| **Type** | `String` |

### Derivation

The `jobTitle` parameter reaches `generateCv()` via three call paths:

| Path | Caller | Line | Value Source |
|---|---|---|---|
| 1 | `OptimizationOrchestrator.runGenerationFromCycle()` | 118 | `cycle.jobTitle` — field on `GenerationCycle` domain object |
| 2 | `OptimizationOrchestrator.runFullPipeline()` | 246 | `jobTitle` — parameter of `runFullPipeline()`, received from UI |
| 3 | `GeneratorViewModel.generate()` | 83 | `jobTitle` — parameter of `generate()`, received from UI |

In all three paths, the value is a **business domain field** (the job title being applied to), not a request-scoped identifier.

---

## C2 — Single Owner Identification

The single owner of the correlation ID for the P2 validation in `generateCv()` **should be the caller**.

At present:

- **OptimizationOrchestrator** already generates a proper UUID-based correlationId at each method entry (e.g., `runFullPipeline:214`: `val correlationId = UUID.randomUUID().toString().take(8)`) and uses it for its own P2 operations (`runAnalysisFresh:433-438`).
- **OptimizationOrchestrator does NOT pass this correlationId to `generatorEngine.generateCv()`** — the handoff is lost.
- **GeneratorViewModel** does not generate any correlation ID — it has no UUID concept at all.

The correct owner of the correlation ID for the GeneratorEngine P2 call site is:

| Call Path | Owner | Current State |
|---|---|---|
| `runFullPipeline` → `generateCv` | `OptimizationOrchestrator` | Has UUID, does not propagate |
| `runGenerationFromCycle` → `generateCv` | `OptimizationOrchestrator` | Has UUID, does not propagate |
| `GeneratorViewModel` → `generateCv` | `GeneratorViewModel` | No UUID, no propagation |

---

## C3 — Propagation Diagram

### Full handoff chain: UI → OptimizationOrchestrator → GeneratorEngine → P2ValidationEngine → PipelineTrace

```
UI Layer (CVGeneratorScreen)
  |
  | calls orchestrator.runFullPipeline(profile, jobTitle, company, jobDescription, mode)
  |                                                                                
  v                                                                                
OptimizationOrchestrator.runFullPipeline()                                         
  |                                                                                
  | line 214: val correlationId = UUID.randomUUID().toString().take(8)             
  |   └── type: String, format: "a1b2c3d4" (8-char hex slug)                      
  |   └── lifetime: method-scoped (runFullPipeline)                                
  |   └── used: PipelineTrace.entry/exit, runAnalysisFresh P2 call                
  |                                                                                
  | line 241: generatorEngine.generateCv(                                          
  |     profile = profile,                    // UserProfile domain object         
  |     jobDescription = enrichedJobDescription,// String                          
  |     designId = "modern",                  // static literal                    
  |     schemeId = "navy",                    // static literal                    
  |     jobTitle = jobTitle,                  // ← BUSINESS FIELD, NOT correlationId
  |     company = company,                    // ← BUSINESS FIELD                 
  |     onResult = { ... }                                                        
  |   )                                                                            
  |   └── correlationId NOT PASSED — chain broken here                            
  v                                                                                
GeneratorEngine.generateCv()                                                       
  |                                                                                
  | line 26:  fun generateCv(                                                     
  |             profile: UserProfile,                                              
  |             jobDescription: String,                                            
  |             designId: String,                                                  
  |             schemeId: String,                                                  
  |             jobTitle: String,          // ← receives business field            
  |             company: String,           // ← receives business field            
  |             onResult: (EngineResult) -> Unit                                   
  |           )                                                                    
  |   └── NO correlationId PARAMETER in signature                                  
  |                                                                                
  | line 84: val p2OutputResult = P2ValidationEngine.validateGeneratorOutput(      
  |             repairResult.json,                                                 
  |             jobTitle                   // ← jobTitle MISUSED as correlationId  
  |           )                                                                    
  v                                                                                
P2ValidationEngine.validateGeneratorOutput()                                       
  |                                                                                
  | line 246: PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_START",     
  |             mapOf("correlationId" to jobTitle, ...),                           
  |             jobTitle                   // ← jobTitle passed as correlationId   
  |           )                                                                    
  | line 251: buildTrace("GeneratorOutput", jobTitle)                              
  | line 278: PipelineTrace.dataQuality("P2Validation", "P2_VALIDATION_END",       
  |             mapOf("correlationId" to jobTitle, ...),                           
  |             jobTitle                   // ← jobTitle passed as correlationId   
  |           )                                                                    
  v                                                                                
PipelineTrace.dataQuality()                                                        
  |                                                                                
  | line 54: Log.w(TAG, "[PIPELINE] DATA_QUALITY ...", ...)                        
  | line 55: LogSink.write("WARN", stage, jobTitle, ...)                          
  v                                                                                
LogSink / Logcat                                                                   
  └── All entries tagged with jobTitle ("Senior Engineer") instead of unique UUID
```

### Handoff chain for direct `GeneratorViewModel` path (no Orchestrator)

```
GeneratorViewModel.generate()
  |
  | line 78: engine.generateCv(
  |     jobTitle = jobTitle,        // ← business field from UI
  |     ...
  |   )
  |   └── NO correlationId AT ALL — no UUID generated anywhere
  v
GeneratorEngine.generateCv()
  | line 84: validateGeneratorOutput(json, jobTitle)  // ← jobTitle misused as cid
  v
P2ValidationEngine → PipelineTrace (same as above)
```

### Variable names at each handoff step

| Step | File | Variable Name | Type | Value Example |
|---|---|---|---|---|
| UI input | `Navigation.kt` → `CVGeneratorScreen` | `jobTitle` | `String` | `"Senior Engineer"` |
| Orchestrator param | `OptimizationOrchestrator.kt:207` | `jobTitle` | `String` | `"Senior Engineer"` |
| Orchestrator cid | `OptimizationOrchestrator.kt:214` | `correlationId` | `String` | `"a1b2c3d4"` |
| Generator param | `GeneratorEngine.kt:26` | `jobTitle` | `String` | `"Senior Engineer"` |
| P2 call arg | `GeneratorEngine.kt:84` | `jobTitle` (position 2) | `String` | `"Senior Engineer"` |
| P2 param | `P2ValidationEngine.kt:244` | `correlationId` | `String` | `"Senior Engineer"` |
| PipelineTrace arg | `P2ValidationEngine.kt:246` | `correlationId` | `String`? | `"Senior Engineer"` |
| LogSink arg | `PipelineTrace.kt:55` | `correlationId` | `String`? | `"Senior Engineer"` |

---

## C4 — GeneratorEngine Evidence: receives/accesses/creates/substitutes/loses

| Action | Exists? | Lines | Detail |
|---|---|---|---|
| **Receives** correlation ID | **NO** | 21-28 | Method signature has no `correlationId` parameter. Parameters: `profile`, `jobDescription`, `designId`, `schemeId`, `jobTitle`, `company`, `onResult`. |
| **Accesses** correlation ID | **NO** | — | No existing correlation ID is accessed because none is received. The GeneratorEngine class has no stored correlation state. |
| **Creates** correlation ID | **NO** | — | No `UUID.randomUUID()` call exists anywhere in GeneratorEngine. |
| **Substitutes** correlation ID | **YES** | 84 | Passes `jobTitle` in the position where `correlationId` is expected by `validateGeneratorOutput()`. This is a semantic substitution — a business field used as a tracking identifier. |
| **Loses** correlation ID | **YES** | 241 (orchestrator) | The OptimizationOrchestrator generates a proper UUID at its own method entry but does not pass it to `generateCv()`. The correlation ID is lost at the orchestrator-to-engine boundary. |

### Classification

**COMPLETE CORRELATION FAILURE for P2 validation calls within GeneratorEngine.** The engine neither receives nor creates a meaningful correlation ID. It substitutes a business field (`jobTitle`) for the expected correlation identifier. The upstream orchestrator that owns the proper UUID does not propagate it.

---

## C5 — Every In-Scope Variable at the P2 Validation Call Site

Call site: `GeneratorEngine.kt:84`

```kotlin
val p2OutputResult = P2ValidationEngine.validateGeneratorOutput(repairResult.json, jobTitle)
```

### Variables accessible at this line

| Variable | Origin | Line Created | Type | Lifetime | Suitable as correlationId? |
|---|---|---|---|---|---|
| `repairResult` | `JsonRepairUtil.repair(clean)` | 75 | `RepairResult` | method scope | NO — validation output, not an identifier |
| `clean` | `extractionResult.content` | 73 | `String` | method scope | NO — AI JSON content |
| `extractionResult` | `JsonExtractionEngine.extract(result)` | 66 | `ExtractionResult` | method scope | NO — extraction wrapper |
| `result` | `aiGateway.generate(...)` | 53 | `String?` | method scope | NO — raw AI response |
| `request` | `AiRequest(systemPrompt, userPrompt)` | 52 | `AiRequest` | method scope | NO — AI request object |
| `userPrompt` | string template | 41-50 | `String` | method scope | NO — AI prompt content |
| `systemPrompt` | string template | 30-39 | `String` | method scope | NO — AI prompt content |
| `jobTitle` | method parameter | 26 | `String` | method scope | **NO** — business field, not unique |
| `company` | method parameter | 27 | `String` | method scope | NO — business field |
| `designId` | method parameter | 24 | `String` | method scope | NO — static value `"modern"` |
| `schemeId` | method parameter | 25 | `String` | method scope | NO — static value `"navy"` |
| `jobDescription` | method parameter | 23 | `String` | method scope | NO — free-form text |
| `profile` | method parameter | 22 | `UserProfile` | method scope | NO — domain object |
| `profile.jobTitle` | profile field | (external) | `String` | via profile ref | NO — different business field |
| `this.aiGateway` | constructor injection | 18 | `AiGateway` | instance lifetime | NO — AI gateway reference |

**None of the in-scope variables is a suitable correlation ID.** The method signature itself has no correlation ID parameter. Every available variable is either a business domain field, static string, prompt content, or infrastructure reference.

### Consequence

The P2 correlation chain cannot be fixed within `GeneratorEngine` alone — the method signature must be extended with a `correlationId` parameter, and all callers must be updated to pass a meaningful value.

---

## C6 — Upstream REQ-xxxxxxxx Identity

### Finding: NO upstream request identity exists

| Question | Answer |
|---|---|
| Is there a `REQ-xxxxxxxx` or similar request-scoped identity created at the UI/API boundary? | **NO** |
| Is there any request-scoped UUID created before the Orchestrator layer? | **NO** |
| Is there a propagation path from a request identity to GeneratorEngine? | **NO** — no identity exists to propagate |

### Current identity creation points

| Location | Type | Scope | Note |
|---|---|---|---|
| `OptimizationOrchestrator.runAnalysisFresh:345` | UUID (8-char) | Method-scoped | Created per analysis call, not shared across pipeline stages |
| `OptimizationOrchestrator.runFullPipeline:214` | UUID (8-char) | Method-scoped | Created per pipeline run, NOT passed to GeneratorEngine |
| `OptimizationOrchestrator.runGenerationFromCycle:94` | UUID (8-char) | Method-scoped | Created per generation, NOT passed to GeneratorEngine |
| `OptimizationOrchestrator.applyDesign:465` | UUID (8-char) | Method-scoped | Design-only, separate from generation |
| `JobExtractionOrchestrator.extractJob:42` | UUID (8-char) | Method-scoped | Extraction only |
| `ProfileExtractionEngine.structureProfile` | `sessionId` (from caller) | Per-extraction | Defaults to `""` when omitted |

**No identity spans the full pipeline from request to persistence.** Each orchestrator method creates its own isolated correlation ID. The concept of a "request" that spans UI → Orchestrator → Engine → P2 → PipelineTrace does not exist in the current architecture.

---

## C7 — Object Lifetime Audit

### GeneratorEngine class definition

```kotlin
class GeneratorEngine(
    private val aiGateway: AiGateway
)
```

| Property | Value |
|---|---|
| **Type** | `class` (not `object`, not singleton) |
| **Constructors** | 1 (primary, `AiGateway` injection) |
| **Mutable object-level state** | **NONE** |
| **Mutable properties** | **NONE** |
| **Thread safety concern** | **NONE** — no mutable state to protect |

### Instantiation points

| Location | Scope | Pattern |
|---|---|---|
| `Navigation.kt:106` | Composable lifecycle | `remember { GeneratorEngine(aiGateway) }` — single instance per composition |
| `GeneratorViewModel.kt:31` | ViewModel lifecycle | `private val engine = GeneratorEngine(aiGateway)` — instance per ViewModel |
| `OptimizationOrchestrator.kt:31` | Orchestrator lifecycle | Constructor injection (`private val generatorEngine: GeneratorEngine`) |

### Verdict

GeneratorEngine is a **stateless class with no mutable object-level state**. No mutable state participates in correlation tracking. **No concurrency defect exists.** The class is safe to use across coroutine boundaries.

### Correlation implication

Because GeneratorEngine has no mutable correlation state, the correlationId for its P2 calls **must come from the caller** via the method signature. Currently it does not. Fixing C1–C5 requires adding a `correlationId` parameter to `generateCv()` — a pure plumbing change with no concurrency risk.

---

## C8 — ATSEngine Build Report

### Files status

| File | HEAD Status | Working Tree Status |
|---|---|---|
| `app/src/main/java/com/vantedge/app/data/engine/AtsEngine.kt` | EXISTS | EXISTS (unchanged) |
| `app/src/main/java/com/vantedge/app/data/model/AtsResult.kt` | EXISTS | **DELETED** |

### AtsResult definitions

`engine/AtsEngine.kt` (lines 5-8, self-contained):
```kotlin
data class AtsResult(
    val score: Int,
    val keywords: List<String>,
    val missingKeywords: List<String>
)
```

`model/AtsResult.kt` (HEAD only, deleted in working tree):
```kotlin
data class AtsResult(
    val score: Int,
    val missingKeywords: List<String>,
    val weakSections: List<String>
)
```

### Dependency analysis

| Reference | Found? | Source |
|---|---|---|
| `import com.vantedge.app.data.model.AtsResult` | **NO** — zero imports found anywhere | All `app/src/` |
| `AtsResult` usage (as return type) | Self-contained within `AtsEngine.kt` | `AtsEngine.kt:3` (local data class) |
| `ATSEngine.analyze()` callers | **ZERO** — dead code | `app/src/` |
| `model/AtsResult` usage outside its file | **ZERO** — completely unreferenced | `app/src/` |

### Verdict

**DORMANT-BUT-BUILD-SAFE.** Classification per directive:

`ATSEngine.kt` defines its own local `AtsResult` data class. The separately deleted `model/AtsResult.kt` was dead code — zero references anywhere. Deleting it has no compilation impact on `AtsEngine.kt` or any other file.

`ATSEngine` itself has zero callers — it is dead code that compiles independently. No compilation error exists from the file deletion.

| File | Build Status |
|---|---|
| `ATSEngine.kt` | **BUILD-SAFE** — self-contained, local types only |
| `model/AtsResult.kt` (deleted) | **HARMLESS DELETE** — dead code, zero references |
| Overall project | No AtsEngine/AtsResult-related build breakage |

---

## Final Outcome

### Decision Matrix

| Outcome | Condition | Match? |
|---|---|---|
| **Outcome A** — Correlation ID is correct | GeneratorEngine receives or creates a proper UUID-based correlationId and passes it to P2ValidationEngine | **NO** — uses `jobTitle` |
| **Outcome B** — Partial correlation defect | GeneratorEngine uses a non-UUID value BUT that value is still a unique, request-scoped identifier (e.g., session token, request hash) | **NO** — `jobTitle` is neither unique nor request-scoped. Multiple requests for the same role produce identical IDs. |
| **Outcome C** — Complete correlation failure | GeneratorEngine has no meaningful correlation ID — uses a business field, duplicates across requests, has no UUID anywhere in scope, and the upstream owner does not propagate | **YES** |

### Verdict: **OUTCOME C — Complete Correlation Failure**

### Required Implementation (per Outcome C)

The following changes are required to resolve Outcome C:

1. **`GeneratorEngine.generateCv()`** — add `correlationId: String` parameter to method signature (line 21)
2. **`GeneratorEngine.generateCv()` line 84** — replace `jobTitle` with `correlationId` in `validateGeneratorOutput()` call
3. **`GeneratorEngine.generateCv()` lines 86-89** — replace `jobTitle` with `correlationId` in P2_DECISION `PipelineTrace.dataQuality()` call
4. **`GeneratorEngine.generateCoverLetter()`** — add `correlationId: String` parameter for consistency (method does not call P2, but signature parity avoids future defects)
5. **`OptimizationOrchestrator.runFullPipeline()` line 241** — pass `correlationId` (from line 214) to `generatorEngine.generateCv()`
6. **`OptimizationOrchestrator.runFullPipeline()` line 265** — pass `correlationId` to `generatorEngine.generateCoverLetter()`
7. **`OptimizationOrchestrator.runGenerationFromCycle()` line 113** — pass `correlationId` (from line 94) to `generatorEngine.generateCv()`
8. **`OptimizationOrchestrator.runGenerationFromCycle()` line 136** — pass `correlationId` to `generatorEngine.generateCoverLetter()`
9. **`GeneratorViewModel.generate()`** — generate `val correlationId = UUID.randomUUID().toString().take(8)` and pass to `engine.generateCv()` / `engine.generateCoverLetter()`
10. **Re-run P2 Verification Harness** (22 tests) to confirm no regression

### Scope of change

| File | Lines Changed | Type |
|---|---|---|
| `GeneratorEngine.kt` | ~4 (signature + calls) | Parameter plumbing |
| `OptimizationOrchestrator.kt` | ~4 (call-site args) | Parameter plumbing |
| `GeneratorViewModel.kt` | ~3 (UUID creation + args) | New UUID + parameter plumbing |

**Estimated effort**: < 30 minutes. **Risk**: Low — pure parameter plumbing, no logic change.

---

## Audit Summary

| Question | Finding |
|---|---|
| C1 — Correlation origin | `jobTitle` at `GeneratorEngine.kt:84` — business field, not an identifier |
| C2 — Single owner | `OptimizationOrchestrator` (primary path) — has UUID, does not propagate |
| C3 — Propagation chain | Broken at `OptimizationOrchestrator:241` — UUID never reaches `generateCv()` |
| C4 — Receives/accesses/creates/substitutes/loses | **Substitutes** `jobTitle` for correlationId at line 84. **Loses** upstream UUID at orchestrator boundary. |
| C5 — Variable audit | Zero in-scope variables are suitable as correlationId |
| C6 — REQ-xxxxxxxx identity | **Does not exist** in the architecture. No request-scoped identity spans the full pipeline. |
| C7 — Object lifetime | `class` — stateless, no mutable state, no concurrency defect |
| C8 — ATSEngine build | Dormant-but-build-safe. Self-contained `AtsResult` definition. Deleted model file was dead code. |
| **Outcome** | **C — Complete Correlation Failure** |
