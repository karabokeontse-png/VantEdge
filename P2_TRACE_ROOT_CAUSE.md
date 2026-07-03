# Root-Cause Report: P2 Trace Evidence Missing from Execution Logs

## 1. Finding: P2 Is Wired and Executes

| Path | Calls P2? | Via |
|---|---|---|
| `analysis_only` | Yes | `runAnalysisFresh()` → `P2ValidationEngine.validateContractResult()` |
| `full_pipeline` | Yes | `runAnalysisFresh()` → `P2ValidationEngine.validateContractResult()` |
| `JobExtractionOrchestrator.run()` | Yes | `P2ValidationEngine.validateJobExtractionResult()` |
| `ProfileExtractionEngine.structureProfile()` | Yes | `P2ValidationEngine.validateProfileExtraction()` |
| `GeneratorEngine.process()` | Yes | `P2ValidationEngine.validateGeneratorOutput()` |

No flag (`analysis_only`, `code_search_explicit`, etc.) bypasses P2. Every orchestrator path includes a P2 call.

## 2. Root Cause: P2 Is a "Dark Engine"

`P2ValidationEngine` (267 lines) contains **zero** calls to any logging system:
- `PipelineTrace.*` — 0 calls
- `PipelineTrace.dataQuality()` — 0 calls
- `android.util.Log.*` — 0 calls
- `LogSink.*` — 0 calls

Validation traces are computed and returned inside `ValidationResult.decision.trace` as a pure in-memory object — they are **never emitted** to any observable channel.

## 3. Orchestrator Handling: Accept Is Silent

| Decision | OptimizationOrch | JobExtractionOrch | ProfileExtractionEngine | GeneratorEngine |
|---|---|---|---|---|
| **Accept** (common) | **silent** — returns `CompatibilityResult.Success(record)` | **silent** — falls to `else -> {}`, then `PipelineTrace.exit` without P2 data | **silent** — falls to `else -> {}` | **silent** — falls to `else -> {}` |
| **Degraded** | `PipelineTrace.warn("OptimizationOrchestrator", "P2 degraded: ...")` | `PipelineTrace.warn("JobExtractionOrchestrator", "P2 degraded: ...")` | `Log.w(TAG, "[P2] Profile degraded: ...")` | `Log.w("GeneratorEngine", "[P2] Output degraded: ...")` |
| **Reject** | Returns `CompatibilityResult.Failure("p2_rejection", ...)` | `PipelineTrace.warn("JobExtractionOrchestrator", "P2 rejection: ...")` | falls to `else -> {}` | falls to `else -> {}` |

For the **Accept** case (the common case when all validation rules pass), **zero log output is produced by any of the 4 orchestrators** apart from the pre-existing `PipelineTrace.exit` which already existed before P2 was added and does not mention P2.

## 4. Degraded/Reject Logging Is Fragile

Even on the Degraded path:
- **OptimizationOrchestrator** calls `PipelineTrace.warn()` (line 440) but **omits the `correlationId` parameter**, breaking trace correlation
- **ProfileExtractionEngine** and **GeneratorEngine** use raw `Log.w()` (not `PipelineTrace`) — these bypass `LogSink` entirely
- No orchestrator uses `PipelineTrace.dataQuality()` — the dedicated channel for quality/validation events

## 5. `PipelineTrace.dataQuality()` Exists but Is Unused by P2

`PipelineTrace.dataQuality()` at `PipelineTrace.kt:47` is the designated channel for data quality issues. It is actively used by 6 other components:
- `JsonExtractionEngine` (2 calls), `LlmJobExtractor` (8 calls), `AiGateway` (1 call), `GeminiService` (14 calls), `ProfileExtractionEngine` (1 call), `ConfidenceEngine` (0 but present)

Zero P2-related code calls it.

## 6. Secondary Finding: Two Correlation ID Systems

- Orchestrator-level: `UUID.randomUUID().toString().take(8)` (e.g. `0766c5c5`)
- Request-level: `REQ-000001` format from `GeminiService`

Both flow through `PipelineTrace` but P2 logging (when it happens) uses orchestrator-side correlation IDs inconsistently.

## Summary Statement

**P2 produces zero trace evidence in execution logs because: (a) `P2ValidationEngine` has no logging calls internally, (b) all 4 orchestrators silently drop the `Accept` path (the common case), and (c) no code calls `PipelineTrace.dataQuality()` — the channel designed for this purpose. P2 traces exist only as ephemeral in-memory objects that are garbage-collected after the validation returns.**
