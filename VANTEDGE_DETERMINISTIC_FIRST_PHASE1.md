# VANTEDGE — DEVELOPMENT CONTINUITY & DETERMINISTIC-FIRST — PHASE 1 (INSPECT)

## 1. Onboarding control flow (document selection → persistence)
```
JobInput/Onboarding UI → OnboardingViewModel.startExtraction(uri)
  → ProfileExtractionEngine.extractRawText(uri)            [PDF text / OCR / DOCX / image]
  → OnboardingViewModel.runStructuredExtraction(...)
      → ProfileExtractionEngine.structureProfile(rawText, mode, sessionId, onProgress)
          → runGate0()            (deterministic structural gate, threshold 3)
          → Gate1 keyword check    (deterministic)
          → callAi(inputText)      <-- AI REQUIRED HERE
              → AiGateway.generate("profile_extraction", request, 120_000)
                  → GeminiService.generate(...)  [A13 blank-key guard → return null]
              → if response.isNullOrBlank() throw EMPTY_AI_RESPONSE   (line 568)
              → JsonExtractionEngine.extract → JsonRepairUtil.repair
              → normalizeRootKeys() → parse() → StructuredProfileExtraction
          → ExtractionValidator.validateProfile(result)  (name/email = BLOCKER)
          → P2ValidationEngine.validateProfileExtraction(result)
      → on failure: OnboardingViewModel sets ExtractionState.Failure → ExtractingScreen "Try again"
  → handleSuccess → commitService.extractionToProfile() → stage ReviewingExtraction
  → commitProfile() → OnboardingCommitService.commit() → UserPreferences.saveProfile()
```

## 2. Where EMPTY_AI_RESPONSE blocks onboarding (exact)
- `ProfileExtractionEngine.callAi()` line 567-569: `if (response.isNullOrBlank()) throw Exception("EMPTY_AI_RESPONSE")`.
- Propagates: `structureProfile` `catch` (line 322-327) → `Result.failure("EMPTY_AI_RESPONSE")`
  → `OnboardingViewModel.runStructuredExtraction` line 208-214 → `ExtractionState.Failure` → dead-end screen.
- Root trigger (prior investigation): `BuildConfig.OPENROUTER_API_KEY` is empty → `GeminiService.generate()` A13 guard returns `null` in ~1 ms (no network).

## 3. Existing deterministic components (reusable)
- `ProfileExtractionEngine.runGate0()` — structural CV classification (deterministic).
- Gate1 CV-keyword check (deterministic).
- `ExtractionValidator.validateProfile()` — validates a `StructuredProfileExtraction`.
- `P2ValidationEngine.validateProfileExtraction()`.
- `JsonExtractionEngine`, `JsonRepairUtil`, `normalizeRootKeys()`, `parse()` — JSON → model mapping.
- `SkillTaxonomyProvider.getTaxonomy()` — canonical skill vocabulary (assets/validation/skill_taxonomy.json).
- EMAIL/PHONE/URL regexes already present in `ProfileExtractionEngine` companion.
- `RuleBasedEmergencyExtractor` — the established "deterministic fallback, no fabrication, provenance" pattern for JOB extraction.

## 4. Profile fields deterministically derivable from extracted text
| Field | Method | Provenance |
|---|---|---|
| email | regex | DIRECT |
| phone | regex | DIRECT |
| linkedIn | URL regex | DIRECT |
| name | first plausible name line (heuristic) | INFERRED |
| summary | "Summary/Profile" section text | DIRECT (verbatim) |
| skills | taxonomy word matches + "Skills" section tokens | DIRECT |
| workHistory | "Experience" section lines with explicit date ranges | DIRECT/INFERRED |
| education | "Education" section lines with degree keywords | DIRECT/INFERRED |
| certifications | "Certifications" section lines | DIRECT |

BLOCKER requirement (validator): `name` + `email`. If either cannot be found, deterministic extraction MUST fail truthfully (no fabrication).

## 5. Smallest scoped change
1. New `DeterministicProfileExtractor` (data/engine): builds schema JSON from `rawText` (regex + taxonomy); returns `null` if name or email absent.
2. `ProfileExtractionEngine.callAi`: when AI response is null/blank → use the deterministic JSON through the SAME existing pipeline (`JsonRepairUtil` → `normalizeRootKeys` → `parse`); if deterministic is insufficient → throw `AI_UNAVAILABLE` (truthful).
3. `parse()` gains a `deterministic: Boolean` → `metadata.modelUsed="deterministic-regex"` + warning `DETERMINISTIC_EXTRACTION_NO_AI`.
4. A13 guard, Gate0/Gate1/Gate3, P2, W5 scoring, commit flow, and the workflow are untouched.

Outcomes:
- deterministic success → `Result.success` with warning; onboarding continues (ReviewingExtraction).
- deterministic incomplete (missing name/email) → `Result.failure("AI_UNAVAILABLE")` (truthful; user can retry/manual).
- OCR text insufficient → already `EMPTY_TEXT`/`NOT_A_CV` before AI.
- AI configured + success → unchanged AI path.
- AI unconfigured/fails → deterministic fallback above.
