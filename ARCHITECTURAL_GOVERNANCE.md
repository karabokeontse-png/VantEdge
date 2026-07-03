# Architectural Governance

## Compatibility Pipeline Invariant

`OptimizationOrchestrator.runAnalysisFresh()` is the **exclusive entry point** for compatibility analysis.

Any new caller to `CompatibilityEngine`, `ContractValidator`, or compatibility-specific extraction logic constitutes an **architectural regression** and must fail architecture review.

### Pipeline ownership map

```
CompatibilityViewModel
        ↓
OptimizationOrchestrator.runAnalysisFresh()
        ↓
AiGateway.generate()
        ↓
JsonExtractionEngine.extract()
        ↓
ContractValidator.validate()          ← exactly one caller
        ↓
CompatibilityEngine.analyze()         ← exactly one caller
        ↓
CompatibilityResult
```

### Enforced via

- **Type-system boundary:** `CompatibilityViewModel` receives `CompatibilityOrchestrator` interface — cannot call `runAnalysisOnly`, `runFullPipeline`, `applyDesign`, or any other `OptimizationOrchestrator` method.
- **Interface contract:** `CompatibilityOrchestrator` exposes only `runAnalysisFresh()`.
- **Audit requirement:** Repository-wide audits of `AiGateway.generate`, `JsonExtractionEngine.extract`, `ContractValidator.validate`, `CompatibilityEngine.analyze` must show zero FORBIDDEN call sites.

## Single Entry Rule

The compatibility pipeline has **exactly one execution path**. Other AI pipelines (`ProfileExtractionEngine`, `GeneratorEngine`, `LlmJobExtractor`) are independent bounded contexts — they own their own AI requests, extraction, and processing. They are not alternate entry points into the compatibility pipeline.

## Three-tier validation model

| Tier | Name | Owner | Examples |
|------|------|-------|---------|
| Tier 1 | Structural | `ContractValidator` | type correctness, field presence, nullability, truncation, JSON integrity |
| Tier 2 | Schema-boundary | `ContractValidator` | value ranges, format constraints, enum membership, array bounds |
| Tier 3 | Domain | Downstream scoring | semantic correctness, business logic, scoring algorithm validity |

`ContractValidator` owns Tier 1 and Tier 2 only. Tier 3 is owned by downstream scoring components.

## Contract Enforcement Layer rules

- Deterministic — no AI-based judgment
- No exceptions for control flow
- Binary confidence: 1.0 = success, 0.0 = failure
- Job-type schemas are hardcoded for P1 stability
- No coupling to scoring, UI, retry, or model selection logic
- Jackson `JsonNode` is the single parsed tree type — no `org.json.JSONObject` in the compatibility pipeline
- `ValidatedAiPayload.node` is immutable — `deepCopy()` at construction
