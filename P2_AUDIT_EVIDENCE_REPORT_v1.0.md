# P2 Audit Evidence Report v1.0

**Date:** 2026-07-01
**Constraint:** Observation only. Zero inference. Zero recommendations. Zero modifications.
**Coverage:** All scoped files inspected. Every conclusion supported by direct evidence.

---

## 1. File Inventory

### Core Validation — Pipeline Contract Layer

| File | Location | Purpose | Current Responsibility |
|---|---|---|---|
| ContractValidator.kt | `com.vantedge.pipeline.contract` | Three-tier validation (structural/schema-boundary/domain) | Owns Tier 1 + Tier 2 validation. Registers job-type validators for VACANCY_SCORING, PROFILE_ANALYSIS, COVER_LETTER_GENERATION. Hardcodes schemas. Binary confidence (1.0/0.0). |
| ContractValidationResult.kt | `com.vantedge.pipeline.contract` | Sealed result: Success/Failure | Carries ValidatedAiPayload on success, reason+details+correlationId+rawSnapshot on failure. Confidence=1.0 success, 0.0 failure. |
| ContractFailureReason.kt | `com.vantedge.pipeline.contract` | Deterministic failure taxonomy | Sealed class with code+category. 8 concrete reasons across 3 categories: SCHEMA (SchemaMismatch, MissingRequiredFields, TypeMismatch), CONTENT (EmptyContent, TruncatedJson, InvalidJsonStructure), SYSTEM (UnknownModelOutputFormat, UnsupportedSchemaVersion). |
| ValidatedAiPayload.kt | `com.vantedge.pipeline.contract` | Immutable validated JSON projection | Private constructor with deepCopy guarantee. node + jobType fields. Factory method `create()`. |
| ExtractedAiPayload.kt | `com.vantedge.pipeline.contract` | Input to contract enforcement | rawJson + parsedObject (JsonNode) + metadata (ExtractionMetadata). |
| ExtractionMetadata.kt | `com.vantedge.pipeline.contract` | Extraction context metadata | requestId, correlationId, modelName, extractionStrategy, rawLength, normalizedLength. |
| JobType.kt | `com.vantedge.pipeline.contract` | Supported job types | Enum: VACANCY_SCORING, PROFILE_ANALYSIS, COVER_LETTER_GENERATION. |

### Input Contracts

| File | Location | Purpose | Current Responsibility |
|---|---|---|---|
| JobExtractionResult.kt | `com.vantedge.app.data.model` | Job extraction output | extractionId, extractedAt, jobTitle?, company?, description, confidenceScore, confidenceBreakdown, gate0Result, metrics. |
| CompatibilityRecord.kt | `com.vantedge.app.data.model` | Compatibility analysis output | id, jobTitle, company, jobDescription, score, vacancyScore, roleSummary, eligibilitySummary, dataIntegrityNote, profileStats, qualificationRatio, relevancyItems, gaps, criticalGapCount, createdAt. |
| CompatibilityResult.kt (model) | `com.vantedge.app.data.model` | Legacy compatibility result | score, matchedSkills, missingSkills, weakSections, suggestions, courses. **NOT used by CompatibilityEngine** — appears to be orphaned. |
| CompatibilityResult.kt (engine) | `com.vantedge.app.data.engine` | Sealed result for pipeline | Success(data: CompatibilityRecord) / Failure(type, message, throwable, rawResponse). Used by OptimizationOrchestrator, CompatibilityOrchestrator, CompatibilityViewModel. |
| GapAnalysisResult.kt | `com.vantedge.app.data.model` | Skill gap analysis output | matchedSkills, missingSkills, skillGaps, transferableSkills, learningRecommendations, gapScore. |
| GapItem.kt | `com.vantedge.app.data.model` | Individual gap entry | skill, importance, description, experienceGap, platformGap, courses. |
| ProfileStats.kt | `com.vantedge.app.data.model` | Profile statistics substructure | yearsExperience, certificationCount, skillCount, matchedCount, gapCount, dataIntegrityNote. |
| QualificationRatio.kt | `com.vantedge.app.data.model` | Qualification ratio substructure | matched, total, gaps. |
| RelevancyItem.kt | `com.vantedge.app.data.model` | Relevancy item substructure | name, type, matchPercent, aiDescription, relevancyGroup. |
| CourseRecommendation.kt | `com.vantedge.app.data.model` | Course recommendation + VerificationStatus | title, provider, url, category, hasCertificate, estimatedDuration, relevancyPercent, priority, verificationStatus. |
| VantageScoreResults.kt | `com.vantedge.app.data.model` | Overall score results | overallScore, atsScore, clarityScore, keywordScore, interpretation. |
| AtsResult.kt (model) | `com.vantedge.app.data.model` | ATS result model | score, missingKeywords, weakSections. |
| SkillGap.kt | `com.vantedge.app.data.model` | Simple skill gap | skill, severity (GapSeverity), reason. |
| GenerationCycle.kt | `com.vantedge.app.data.model` | Full generation cycle | id, jobTitle, company, jobDescription, profileSnapshot, compatibility, matchedKeywords, design, cvContent, coverLetterContent, cvErrorMessage, coverLetterErrorMessage. |

### W5 Consumers

| File | Location | Purpose | How It Consumes W4 Outputs |
|---|---|---|---|
| AtsEngine.kt | `com.vantedge.app.data.engine` | ATS keyword analysis | **Does NOT consume any W4 contract output.** Takes `UserProfile + jobDescription: String`, splits by spaces, takes first 10 tokens as keywords. Scalar score. No contract validation used. |
| ScoreEngine.kt | `com.vantedge.app.data.engine` | Profile scoring | **Does NOT consume any W4 contract output.** Takes `UserProfile` only. Simple weighted sum of profile field presence. Returns VantEdgeScoreResult(score, interpretation). |
| GapAnalysisEngine.kt | `com.vantedge.app.data.engine` | Skill gap analysis | **Does NOT consume any W4 contract output.** Takes `UserProfile + jobSkills: List<String>`. Simple set difference. Returns `List<SkillGap>`. |
| CareerEngine.kt | `com.vantedge.app.data.engine` | CV/cover letter generation | **Does NOT consume any W4 contract output.** Takes `UserProfile` (and optional jobDescription). Plain text string concatenation. |
| CompatibilityEngine.kt | `com.vantedge.app.data.engine` | Compatibility analysis | **Only W5 consumer that consumes validated contract output.** Receives `ValidatedAiPayload.node` (JsonNode) from ContractValidator success path. Parses into CompatibilityRecord. Has its own `validateCompatibilitySchema()`. Uses `computeUrlVerification()` with 3 states (VERIFIED/UNVERIFIED/HALLUCINATED) but only assigns UNVERIFIED or HALLUCINATED (never VERIFIED). |
| GeneratorEngine.kt | `com.vantedge.app.data.engine` | CV/cover letter generation with AI | Consumes AI response JSON. Uses JsonExtractionEngine + JsonRepairUtil internally. No W4 contract output consumed. |
| OptimizationOrchestrator.kt | `com.vantedge.app.data.domain` | Pipeline orchestrator | **Only consumer of ContractValidator.** Creates ExtractedAiPayload → calls contractValidator.validate(JobType.VACANCY_SCORING, payload) → on Success passes `validation.validatedObject.node` to CompatibilityEngine.analyze(). On Failure wraps into CompatibilityResult.Failure. |

### Downstream Consumer Mapping — W4 Output Flow

```
AiGateway.generate() → raw String
  → JsonExtractionEngine.extract() → ExtractionResult
    → JsonMapper().readTree() → JsonNode
      → ExtractedAiPayload created
        → ContractValidator.validate(JobType.VACANCY_SCORING, payload)
          → ContractValidationResult.Success(validatedObject)
            → CompatibilityEngine.analyze(validatedObject.node, ...) → CompatibilityRecord
              → CompatibilityResult.Success(record)
                → OptimizationOrchestrator / CompatibilityViewModel
          → ContractValidationResult.Failure
            → CompatibilityResult.Failure
```

### Extraction Subsystem

| File | Location | Purpose | Current Responsibility |
|---|---|---|---|
| JsonExtractionEngine.kt | `com.vantedge.app.data.engine.extraction` | JSON extraction from AI response | Strips think/reasoning tags, tries direct_parse/markdown_strip/nesting_aware_root/substring_scan. Returns ExtractionResult. |
| SchemaValidator.kt | `com.vantedge.app.data.engine.extraction` | Schema validation of job extraction | Validates LLM-extracted JSON against required string fields (jobTitle, company, location, etc.) and array fields (responsibilities, requiredSkills, preferredSkills). Returns SchemaValidationResult. |
| SemanticValidator.kt | `com.vantedge.app.data.engine.extraction` | Semantic validation | Checks jobTitle/company against header blacklists, detects hallucination markers, checks description length. Returns SemanticValidationResult with fieldConfidenceAdjustments. |
| ConfidenceEngine.kt | `com.vantedge.app.data.engine.extraction` | Field-level confidence computation | Weighted combination (0.5/0.3/0.2) of LLM confidence, validation confidence, consistency confidence. Returns ConfidenceResult. |
| LlmJobExtractor.kt | `com.vantedge.app.data.engine.extraction` | LLM-based job extraction | Calls AiGateway, extracts JSON, validates schema, parses into LlmFieldResult. |
| DocumentPreprocessor.kt | `com.vantedge.app.data.engine.extraction` | Document text preprocessing | Unicode normalization, hyphen repair, OCR confusion pair repair, quality estimation. |
| DocumentStructureAnalyzer.kt | `com.vantedge.app.data.engine.extraction` | Document structure analysis | Classifies blocks (HEADING, PARAGRAPH, LIST, TABLE, etc.), extracts metadata (emails, phones, dates, currencies). |
| RuleBasedEmergencyExtractor.kt | `com.vantedge.app.data.engine.extraction` | Emergency fallback extraction | Rule-based title/company detection when LLM unavailable. Uses ExtractionValidator for post-validation. |
| ExtractionValidator.kt | `com.vantedge.app.data.engine` | Profile + job extraction validation | Validates StructuredProfileExtraction and JobExtractionResult. Max length checks, regex (email, phone, year), hallucination marker detection. BLOCKER/WARNING severity. |
| JsonRepairUtil.kt | `com.vantedge.app.data.engine` | JSON structural repair | Brace/bracket balancing, trailing comma removal. Returns RepairResult(json, isSafe). |
| ProfileExtractionEngine.kt | `com.vantedge.app.data.engine` | Full profile extraction pipeline | PDF/OCR/DOCX ingestion, Gate 0 scoring, AI extraction, telemetry recording. 916 lines. |

---

## 2. Contract Inventory

### Existing Data Contracts

| Contract | File | Status |
|---|---|---|
| ValidatedAiPayload | pipeline/contract/ValidatedAiPayload.kt | Present |
| ExtractedAiPayload | pipeline/contract/ExtractedAiPayload.kt | Present |
| ExtractionMetadata | pipeline/contract/ExtractionMetadata.kt | Present |
| ContractValidationResult | pipeline/contract/ContractValidationResult.kt | Present |
| ContractFailureReason | pipeline/contract/ContractFailureReason.kt | Present |
| JobType | pipeline/contract/JobType.kt | Present |
| JobExtractionResult | data/model/JobExtractionResult.kt | Present |
| CompatibilityRecord | data/model/CompatibilityRecord.kt | Present |
| CompatibilityResult | data/engine/CompatibilityEngine.kt:16 | Present |
| CompatibilityResult | data/model/CompatibilityResult.kt | Present (orphaned duplicate) |
| ExtractionResult | data/engine/extraction/JsonExtractionEngine.kt:8 | Present |
| SchemaValidationResult | data/engine/extraction/SchemaValidator.kt:9 | Present |
| SemanticValidationResult | data/engine/extraction/SemanticValidator.kt:7 | Present |
| ConfidenceResult | data/engine/extraction/ConfidenceEngine.kt:7 | Present |
| ValidationResult | data/engine/ExtractionValidator.kt:17 | Present |
| ExtractionDecision | domain/extraction/ExtractionDecision.kt:5 | Present |
| LlmFieldResult | data/engine/extraction/LlmJobExtractor.kt:53 | Present |

### Missing Contracts

| Contract | Evidence |
|---|---|
| **No unified W4 output model** | W4 outputs are scattered across individual model classes (CompatibilityRecord, GapAnalysisResult, AtsResult, VantageScoreResults, JobExtractionResult). No single sealed hierarchy or shared contract. |
| **No JobExtractionResult <-> ValidatedAiPayload bridge** | JobExtractionResult (from job extraction pipeline) and ValidatedAiPayload (from contract layer) are completely separate types with no conversion/adaptation path. |
| **No consumer-facing result envelope** | Each W5 consumer (AtsEngine, ScoreEngine, GapAnalysisEngine) returns its own bespoke type. No shared success/failure envelope. |
| **No validation trace contract** | No ValidationTrace or equivalent data class exists. PipelineTrace is a logging utility, not a structured trace output. |

---

## 3. Dependency Inventory

### Internal Dependencies

| Dependency | Used By |
|---|---|
| `com.vantedge.pipeline.contract.*` | OptimizationOrchestrator, Navigation |
| `com.vantedge.app.data.engine.CompatibilityResult` | OptimizationOrchestrator, CompatibilityOrchestrator, CompatibilityViewModel |
| `com.vantedge.app.data.engine.extraction.*` | JobExtractionOrchestrator, LlmJobExtractor, GeneratorEngine |
| `com.vantedge.app.domain.PipelineTrace` | All engines, orchestrators, validators |
| `com.vantedge.app.util.HashUtils` | ProfileExtractionEngine, OnboardingViewModel |
| `com.vantedge.app.util.TelemetryCollector` | ProfileExtractionEngine, OnboardingViewModel |
| `com.vantedge.app.util.LogSink` | PipelineTrace |

### External Libraries

| Library | Evidence |
|---|---|
| com.fasterxml.jackson (jackson-databind) | Used in ContractValidator (JsonNode), CompatibilityEngine, OptimizationOrchestrator |
| org.json (JSONObject, JSONArray) | Used in SchemaValidator, GeneratorEngine, LlmJobExtractor, JsonRepairUtil, CVTemplate |
| Google MLKit (TextRecognition) | Used in ProfileExtractionEngine (OCR) |
| AndroidX Room | AiRawResponseArtifact (Entity), CycleEntity, OnboardingDraftEntity |
| OkHttp | GeminiService |
| Kotlinx Coroutines | Throughout |
| Kotlinx Immutable Collections | StructuredProfileExtraction |

### Offline Assets

| Asset | Location | Status |
|---|---|---|
| None found | — | No taxonomy files, dictionary files, or offline knowledge bases detected in the source tree. |

### Validation Resources

| Resource | Status | Location/Evidence |
|---|---|---|
| Skill taxonomies | **Absent** | No taxonomy files found anywhere in source tree |
| Dictionaries | **Absent** | No dictionary files found |
| Whitelist resources | **Absent** | No whitelist files found |
| OCR utilities | **Present** | DocumentPreprocessor.kt (OCR confusion repair, quality estimation), ProfileExtractionEngine (MLKit, PDF parsing) |
| URL validation | **Minimal** | CompatibilityEngine.computeUrlVerification() — only checks blank/prefix http/https. Never attempts resolution. No regex-based domain validation. |
| Field validators | **Present** | ExtractionValidator.kt (length, format, hallucination markers for profile + job fields) |
| Parsing utilities | **Present** | JsonExtractionEngine.kt, JsonRepairUtil.kt |
| Normalization helpers | **Present** | DocumentPreprocessor.kt (unicode, hyphen, OCR repair, whitespace normalization) |
| Date parsing utilities | **Present** | DocumentStructureAnalyzer.kt — DATE_REGEX for detection only. No date validation or normalization beyond regex matching. |
| Hallucination marker lists | **Present** | ExtractionValidator.kt (10 markers), SemanticValidator.kt (9 markers — separate list) |

---

## 4. Validation Inventory

### Existing Validation

| What | Where | Details |
|---|---|---|
| Tier 1 Structural | ContractValidator.kt | Field presence, nullability, type correctness for VACANCY_SCORING schema |
| Tier 2 Schema-boundary | ContractValidator.kt | IntRange, SubFieldNonNegative for VACANCY_SCORING |
| Truncation detection | ContractValidator.kt | Brace/bracket depth analysis of raw JSON |
| Schema validation (job extraction) | SchemaValidator.kt | Required string fields + array fields for job postings |
| Semantic validation | SemanticValidator.kt | Header blacklist, hallucination markers, description length |
| Confidence computation | ConfidenceEngine.kt | Field-level weighted average (0.5/0.3/0.2) |
| Profile validation | ExtractionValidator.kt | Length limits, email/phone/year regex, hallucination markers |
| Job extraction validation | ExtractionValidator.kt | Title/company/description length, confidence range, hallucination markers |
| URL verification | CompatibilityEngine.kt | Blank → HALLUCINATED, http/https prefix → UNVERIFIED, else → HALLUCINATED. No VERIFIED state ever assigned. |
| Document quality estimation | DocumentPreprocessor.kt | Alpha ratio, digit ratio, word length, gibberish patterns |
| OCR confusion repair | DocumentPreprocessor.kt | Unicode normalization, hyphen repair, character confusion pairs ("rn"→"m", "cl"→"d", etc.) |
| Extraction decision routing | ExtractionDecisionCoordinator.kt | Threshold-based: Accept (>=0.90), AcceptWithWarning (>=0.70), UserReview (>=0.50), Failure |

### Missing Validation

| What | Evidence |
|---|---|
| No date validation/normalization | DATE_REGEX in DocumentStructureAnalyzer detects dates but no parsing, validation, or normalization exists. Dates are stored as raw strings. |
| No URL resolution/verification | `computeUrlVerification()` only checks string prefix. Never performs HTTP resolution. No domain validation regex. VerificationStatus.VERIFIED is defined but never assigned anywhere in codebase. |
| No skill taxonomy validation | No skill taxonomy exists. Skills are stored/compared as raw strings. No canonical skill names, no synonym mapping. |
| No certification taxonomy | No certification taxonomy. Certification names are free-text. |
| No coherence/cross-field validation | No validation that work history dates are chronologically consistent. No validation that skill names match any reference. |
| No confidence override mechanism | ContractFailureReason has no override mechanism. ConfidenceEngine uses hardcoded weights with no override injection point. |
| No output schema validation for AtsEngine/ScoreEngine/GapAnalysisEngine outputs | These engines return bespoke types with no validation at output boundary. |

---

## 5. Conflict Inventory

### Duplicate Validators

| Conflict | Evidence |
|---|---|
| Two separate hallucination marker lists | `ExtractionValidator.HALLUCINATION_MARKERS` (10 items, lines 49-53) and `SemanticValidator.KNOWN_INVALID_TOKENS` (9 items, lines 26-30) contain overlapping concepts but different wordings. Neither references the other. |
| Two SchemaValidators | `SchemaValidator` in `data/engine/extraction/` validates job extraction schemas (string fields, array fields). `ContractValidator` in `pipeline/contract/` performs Tier 1+2 validation on compatibility JSON. Different scope but both called "schema validation". |

### Overlapping Responsibility

| Conflict | Evidence |
|---|---|
| Two CompatibilityResult types | `data/model/CompatibilityResult.kt` defines `data class CompatibilityResult(score, matchedSkills, missingSkills, weakSections, suggestions, courses)` — **never imported by any engine or consumer**. `data/engine/CompatibilityEngine.kt` defines `sealed class CompatibilityResult` with `Success(CompatibilityRecord)` / `Failure` — used by all 3 consumers (OptimizationOrchestrator, CompatibilityOrchestrator, CompatibilityViewModel). The model version appears orphaned. |
| Two AtsResult types | `data/model/AtsResult.kt` (score, missingKeywords, weakSections) and `data/engine/AtsEngine.kt` defines `data class AtsResult(score, keywords, missingKeywords)` inline. The model version has "weakSections" but is never imported; the engine version has "keywords" instead. |
| Validation spread across layers | Field validation exists in ExtractionValidator (profile/job), ContractValidator (compatibility schema), SchemaValidator (job extraction schema), SemanticValidator (content quality), and CompatibilityEngine (runtime schema check). No unified validation orchestration. |

### Incompatible Contracts

| Conflict | Evidence |
|---|---|
| ValidatedAiPayload not consumed by most W5 engines | AtsEngine, ScoreEngine, GapAnalysisEngine, CareerEngine all take raw UserProfile + strings. Only CompatibilityEngine receives ValidatedAiPayload.node. The contract validation layer is bypassed for 4/5 W5 consumers. |
| JobExtractionResult confidence is Float 0-1; ContractValidationResult confidence is binary (1.0/0.0) | Different confidence semantics between extraction pipeline and contract layer. No mapping/comparison path. |

### Missing Models

| Item | Evidence |
|---|---|
| No W4 output sealed hierarchy | No sealed class or interface groups the W4 output types. GapAnalysisResult, AtsResult, VantageScoreResults, CompatibilityRecord are independent data classes. |
| No ValidationTrace model | `PipelineTrace` is a logging singleton with no structured output model that could be consumed programmatically. |

### Orphaned Consumers

| Item | Evidence |
|---|---|
| `data/model/CompatibilityResult.kt` | Defined but never imported or referenced by any other file in the codebase. |
| `data/model/AtsResult.kt` | Imported by 0 files. The `data/engine/AtsEngine.kt` defines its own local `AtsResult` data class. |
| `data/engine/ATSResults.kt` (empty file) | Referenced in CURRENT_STATE.md as existing; glob finds no results (may have been empty or deleted). |

---

## 6. Resource Inventory

### Dictionary Resources

| Resource | Status |
|---|---|
| Skill taxonomy | **Absent** |
| Certification taxonomy | **Absent** |
| Job title taxonomy | **Absent** |
| Company name dictionary | **Absent** |
| Industry/sector taxonomy | **Absent** |
| Location/geography dictionary | **Absent** |
| Education institution dictionary | **Absent** |

### Whitelist Resources

| Resource | Status |
|---|---|
| URL domain whitelist | **Absent** |
| Course provider whitelist | **Absent** (only referenced in prompt strings, not in validation code) |
| Skill name whitelist | **Absent** |
| Certification name whitelist | **Absent** |

### Regex Utilities

| Resource | Status | Location |
|---|---|---|
| Email regex | Present | ExtractionValidator.kt:46, DocumentStructureAnalyzer.kt:36 |
| Phone regex | Present | ExtractionValidator.kt:47, DocumentStructureAnalyzer.kt:37 |
| Date regex | Present | DocumentStructureAnalyzer.kt:38-41 |
| Year regex | Present | ExtractionValidator.kt:48 |
| Currency regex | Present | DocumentStructureAnalyzer.kt:42 |
| URL validation regex | **Absent** | Only prefix check exists |

### Date Parsing Utilities

| Resource | Status | Details |
|---|---|---|
| Date detection regex | Present | DocumentStructureAnalyzer.kt:38-41 — matches 3 date formats |
| Date parsing/validation | **Absent** | No actual date parsing, validation, or normalization |
| Date formatting | **Absent** | No standardized date output format |

### URL Validation Utilities

| Resource | Status | Details |
|---|---|---|
| computeUrlVerification() | Present | CompatibilityEngine.kt:47-53 — only checks blank / http/https prefix |
| HTTP resolution | **Absent** | Never attempts to validate URL by fetching |
| Domain regex validation | **Absent** | No domain format/pattern validation |
| Provider whitelisting | **Absent** | The allowed providers (Coursera, Udemy, etc.) exist only in system prompt strings, not in code |

---

## 7. Infrastructure Inventory

### ValidationTrace Equivalents

| Component | Status | Details |
|---|---|---|
| PipelineTrace | Present | Logging singleton in `domain/PipelineTrace.kt`. Methods: entry, exit, error, warn, dataQuality, validateScore, validateNonBlank, validateEnum. Writes to logcat + LogSink file. |
| LogSink | Present | File-based rotation logging in `util/LogSink.kt`. 2MB cap, 5 archives. |
| TelemetryCollector | Present | Gate 0 telemetry in `util/TelemetryCollector.kt`. Queue-based, idempotent dedup by documentHash, file append. |
| TelemetryStorage | Present | Reads telemetry.log in `data/storage/TelemetryStorage.kt`. Parses GATE0-prefixed lines. |
| FailureLogBundle | Present | CV failure bundle in `util/FailureLogBundle.kt`. Captures logcat + error → shares via intent. |
| AppLogger | Present | In-memory ring buffer logger in `util/AppLogger.kt`. 500 entries. |
| LogDumper | Present | logcat capture + file share in `util/LogDumper.kt`. |

### Failure Enums

| Enum | Location | Values |
|---|---|---|
| ContractFailureReason | pipeline/contract/ContractFailureReason.kt | SchemaMismatch, MissingRequiredFields, TypeMismatch, EmptyContent, TruncatedJson, InvalidJsonStructure, UnknownModelOutputFormat, UnsupportedSchemaVersion |
| FailureReason | domain/extraction/ExtractionDecision.kt | ALL_MODELS_EXHAUSTED, VALIDATION_BLOCKER, UNRECOVERABLE_PARSE_ERROR |
| RetryReason | domain/extraction/ExtractionDecision.kt | TRUNCATED_RESPONSE, INVALID_JSON, TIMEOUT, TRANSPORT_INTERRUPTION |
| AiFailureCategory | data/network/AiResult.kt | TIMEOUT, RATE_LIMIT, CONFIG_ERROR, TRANSPORT, EMPTY_RESPONSE, PARSE_ERROR, PROVIDER_ERROR, OTHER |
| ValidationSeverity | data/engine/ExtractionValidator.kt | BLOCKER, WARNING |
| Gate0Reason | data/engine/Gate0Reason.kt | ACCEPTED, LOW_STRUCTURAL_EVIDENCE, HIGH_NARRATIVE_DENSITY, NO_CHRONOLOGY, NO_SECTIONAL_STRUCTURE, OCR_TOO_FRAGMENTED |
| Gate0JobReason | data/model/Gate0JobReason.kt | ACCEPTED, LOW_STRUCTURAL_EVIDENCE, HIGH_NARRATIVE_DENSITY, NO_VACANCY_SIGNALS |
| GapSeverity | data/model/GapSeverity.kt | LOW, MEDIUM, HIGH |

### Confidence Overrides

| Mechanism | Status | Details |
|---|---|---|
| Binary confidence on ContractValidationResult | Present | 1.0 success / 0.0 failure. No override path. |
| Field-level confidence adjustments | Present | ConfidenceEngine has hardcoded weights (0.5/0.3/0.2). SemanticValidator applies adjustments (-0.4, -0.5, +0.1, -0.3). |
| Confidence override mechanism | **Absent** | No mechanism to inject override confidence values into any pipeline stage. |

### Deterministic Hashing

| Mechanism | Status | Details |
|---|---|---|
| HashUtils.sha256 | Present | `util/HashUtils.kt` — SHA-256 hex digest. Used by ProfileExtractionEngine (document hash) and OnboardingViewModel (raw text hash). |
| Prompt hashing in GeminiService | Present | `MessageDigest.getInstance("SHA-256")` for prompt deduplication in GeminiService. |

### Immutable Validation Models

| Model | Status | Evidence |
|---|---|---|
| ValidatedAiPayload | Present | Private constructor, deepCopy guarantee |
| ContractValidationResult | Present | Sealed class, no setters |
| ContractFailureReason | Present | Sealed class with code+category |
| ExtractionMetadata | Present | Regular data class (immutable by convention) |
| PipelineTrace | Present | Object (no mutable state beyond LogSink file rotation) |

---

## 8. Evidence Appendix

### Production Evidence Files

| File | Status | Notes |
|---|---|---|
| test_cv.txt | Present | 21-line test CV (John Doe, software engineer). Simple structure. |
| real_cv_test.log | Present | Empty file (0 bytes). No real CV test data recorded. |
| build_log.txt | Present | Empty file. |
| build.log | Present | Build log file. |

### Audit Trail of W4 Output Flow

Extracted from OptimizationOrchestrator.kt:333-435:

1. `runAnalysisFresh()` builds system prompt with JSON Schema (lines 339-365)
2. Calls `aiGateway.generate("compatibility", request, 120_000L)` (line 382)
3. Calls `JsonExtractionEngine.extract(aiResponse)` (line 388)
4. Calls `JsonMapper().readTree(extractionResult.content)` to get JsonNode (line 398)
5. Creates `ExtractedAiPayload` with metadata (lines 403-416)
6. Calls `contractValidator.validate(JobType.VACANCY_SCORING, payload)` (line 417)
7. On success: calls `compatibilityEngine.analyze(validation.validatedObject.node, ...)` (lines 419-424)
8. On failure: wraps into `CompatibilityResult.Failure` (lines 428-433)

### W5 Consumer Bypass Evidence

AtsEngine.kt:13 — `fun analyze(profile: UserProfile, jobDescription: String): AtsResult`
ScoreEngine.kt:12 — `fun calculate(profile: UserProfile): VantEdgeScoreResult`
GapAnalysisEngine.kt:7 — `fun analyze(profile: UserProfile, jobSkills: List<String>): List<SkillGap>`
CareerEngine.kt:7 — `fun generateCV(profile: UserProfile): String`

None of these receive any validated contract output. They operate on raw UserProfile data and string parameters.

### URL Verification Gap Evidence

CompatibilityEngine.kt:47-53:
```kotlin
private fun computeUrlVerification(url: String): VerificationStatus {
    if (url.isBlank()) return VerificationStatus.HALLUCINATED
    return if (url.startsWith("http://") || url.startsWith("https://")) {
        VerificationStatus.UNVERIFIED
    } else {
        VerificationStatus.HALLUCINATED
    }
}
```

`VerificationStatus.VERIFIED` exists in enum (CourseRecommendation.kt:3-7) but is **never produced** by this function or anywhere else in the codebase.

### Two CompatibilityResults Conflict

- `data/model/CompatibilityResult.kt:3` — `data class CompatibilityResult(score, matchedSkills, missingSkills, weakSections, suggestions, courses)` — **ZERO** references across codebase
- `data/engine/CompatibilityEngine.kt:16` — `sealed class CompatibilityResult { Success(data: CompatibilityRecord), Failure(...) }` — **3 import references**: OptimizationOrchestrator, CompatibilityOrchestrator, CompatibilityViewModel

### Duplicate AtsResult Types

- `data/model/AtsResult.kt:3` — `data class AtsResult(score, missingKeywords, weakSections)` — **ZERO** references
- `data/engine/AtsEngine.kt:5` — `data class AtsResult(score, keywords, missingKeywords)` — local definition used by ATSEngine.analyze()

---

## 9. Summary of Key Findings

1. **Contract layer is only used by CompatibilityEngine path.** AtsEngine, ScoreEngine, GapAnalysisEngine, and CareerEngine bypass the entire `pipeline.contract` validation.

2. **ValidatedAiPayload is consumed by exactly one caller** — `OptimizationOrchestrator.runAnalysisFresh()` passes it to `CompatibilityEngine.analyze()`. No other consumer.

3. **Two CompatibilityResult types exist** — one in `data/model/` (orphaned) and one in `data/engine/` (active). This is a naming collision.

4. **Two AtsResult types exist** — one in `data/model/` (orphaned) and one in-line in `data/engine/AtsEngine.kt` (active).

5. **URL verification is a stub** — `VerificationStatus.VERIFIED` is defined in enum but never assigned. No URL resolution, domain validation, or provider whitelist exists in code (providers only in prompt strings).

6. **No skill/certification/job/company taxonomies exist** — all matching is raw string comparison.

7. **Date handling is detection-only** — DocumentStructureAnalyzer detects dates via regex but never parses, validates, or normalizes them.

8. **No ValidationTrace model exists** — PipelineTrace is a logging singleton with no structured output consumers.

9. **Hallucination marker detection is duplicated** — Two separate lists in ExtractionValidator and SemanticValidator with different items.

10. **No confidence override mechanism** exists in any validation stage.
