# P2 Pipeline Architecture Diagram v1.0

**Date**: 2026-07-03

---

## Canonical Pipeline

```
  User
    |
    v
+------------------+     +------------------------+     +-----------------+
|    LLM Layer     |     |  W4 Structural         |     |  P2 Validation  |
|  (GeminiService  | --> |  Normalization          | --> |  Layer          |
|   AiGateway)     |     |  (ExtractionValidator,  |     |  (GOVERNED)     |
|                  |     |   JobExtractionEngine,  |     |                 |
| Raw AI output    |     |   ProfileExtractionEng) |     |  Validates:     |
|                  |     |                         |     |  - ContractRes  |
|                  |     |  Produces structured    |     |  - JobExtract   |
|                  |     |  domain objects         |     |  - ProfileExtr  |
|                  |     |                         |     |  - GeneratorOut |
+------------------+     +------------------------+     +--------+--------+
                                                                    |
                                          +-------------------------+
                                          |  P2 Events via
                                          |  PipelineTrace.dataQuality():
                                          |  - P2_VALIDATION_START
                                          |  - P2_VALIDATION_END
                                          |  - P2_DECISION
                                          v
                                 +------------------+
                                 |  LogSink /       |
                                 |  Logcat          |
                                 |  (Observability) |
                                 +------------------+
                                          |
                                          v
+------------------+     +------------------------+     +-----------------+
|  W5 Deterministic|     |  Version Store         |     |  User           |
|  Scoring Layer   | <-- |  (Persistence)         | <-- |  (Result View)  |
|  (PLANNING ONLY) |     |                        |     |                 |
|                  |     |  Stores validated +    |     |  Displays       |
|  Consumes        |     |  scored pipeline       |     |  compatibility, |
|  validated data  |     |  artifacts             |     |  job match,     |
|  from P2         |     |                        |     |  profile, gen   |
+------------------+     +------------------------+     +-----------------+
```

---

## Ownership Boundaries

```
  [LLM Layer]          -- AI Services Team
  [W4 Normalization]   -- Extraction Team
  [P2 Validation]      -- P2 Governance (GOVERNED)
  [W5 Scoring]         -- Scoring Team (future)
  [Version Store]      -- Data Team
  [UI]                 -- Frontend Team
```

---

## Governance Boundaries

```
  +------------------------------------------------------+
  |              P2 GOVERNANCE BOUNDARY                   |
  |                                                       |
  |  Protected Components:                                |
  |    P2ValidationEngine          (validation logic)     |
  |    PipelineTrace               (event emission)       |
  |    OptimizationOrchestrator    (P2 call site)         |
  |    JobExtractionOrchestrator   (P2 call site)         |
  |    ProfileExtractionEngine     (P2 call site)         |
  |    GeneratorEngine              (P2 call site)         |
  |    P2VerificationHarnessTest    (acceptance tests)    |
  |                                                       |
  |  Changes to any protected component require           |
  |  full harness re-execution before stabilization.      |
  +------------------------------------------------------+
```

---

## Validation Boundaries

```
  P2ValidationEngine.validateContractResult()
    Input: JsonNode (contract result compatibility data)
    Output: ValidatedDomainObject<JsonNode>
    Used by: OptimizationOrchestrator

  P2ValidationEngine.validateJobExtractionResult()
    Input: JobExtractionResult
    Output: ValidatedDomainObject<JobExtractionResult>
    Used by: JobExtractionOrchestrator

  P2ValidationEngine.validateProfileExtraction()
    Input: StructuredProfileExtraction
    Output: ValidatedDomainObject<StructuredProfileExtraction>
    Used by: ProfileExtractionEngine

  P2ValidationEngine.validateGeneratorOutput()
    Input: String (JSON)
    Output: ValidatedDomainObject<String>
    Used by: GeneratorEngine
```

---

## Data Flow (read-only)

```
LLM
 |  Raw text/JSON
 v
W4 Normalization
 |  Structured domain objects (JobExtractionResult,
 |  StructuredProfileExtraction, ContractResult JsonNode)
 v
P2 Validation                  ---> PipelineTrace (log events)
 |  ValidatedDomainObject<T>
 |  (Accept | Degraded | Reject) with failureCodes + trace
 v
W5 Scoring (planned)
 |  Scored + validated artifacts
 v
Version Store
 |  Persisted results
 v
User (via UI)
```
