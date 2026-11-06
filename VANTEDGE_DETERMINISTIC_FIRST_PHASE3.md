# VANTEDGE — DEVELOPMENT CONTINUITY & DETERMINISTIC-FIRST — PHASE 3 (IMPLEMENTATION + VERIFICATION)

Baseline HEAD: 56093227307f5b4ab7d7511e14541fa3d496fc78 (branch verification/ced-p0-authority)

## 1. Exact cause established
Onboarding blocked because `ProfileExtractionEngine.callAi()` threw `EMPTY_AI_RESPONSE`
when `AiGateway.generate()` returned null — which happens in ~1 ms because the A13 blank-key
guard in `GeminiService.generate()` returns null when `BuildConfig.OPENROUTER_API_KEY` is empty.
The pipeline argument IS forwarded correctly; there was no routing defect.

## 2. Files changed (this directive)
NEW:
- `app/src/main/java/com/vantedge/app/data/engine/DeterministicProfileExtractor.kt`
- `app/src/test/java/com/vantedge/app/data/engine/DeterministicProfileExtractorTest.kt`

MODIFIED:
- `app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt`

(all other modifications in the working tree are from prior directives: Rev E privacy,
signing restore notes, etc.)

### Diff — ProfileExtractionEngine.kt
(see section 2b below)

### New — DeterministicProfileExtractor.kt (behaviour)
Object with `extractJson(rawText): String?`. Builds the SAME JSON schema the AI prompt
requests, using ONLY evidence present in the text:
- email / phone / LinkedIn — regex (DIRECT evidence)
- name — first plausible name line heuristic (INFERRED, low confidence)
- summary — "Summary/Profile/About" section text (verbatim)
- skills — SkillTaxonomyProvider matches + verbatim "Skills" section tokens
- workHistory — "Experience" section lines carrying an explicit date range
- education — "Education" section lines carrying a degree keyword
- certifications — "Certifications" section lines
Returns `null` when name AND email are not both present (output contract: they are
BLOCKER-required by ExtractionValidator). No fabrication.

## 3. Deterministic-first control flow (after change)
```
structureProfile()
  Gate0 (deterministic) -> Gate1 (deterministic)
  callAi():
     response = AiGateway.generate("profile_extraction", ...)   // A13 guard unchanged
     if response.isNullOrBlank():
         det = DeterministicProfileExtractor.extractJson(rawText)
         if det == null: throw "AI_UNAVAILABLE"        // truthful failure, no fabrication
         jsonStr = det; deterministic = true
     else:
         jsonStr = JsonExtractionEngine.extract(response).content; deterministic = false
     JsonRepairUtil.repair -> normalizeRootKeys -> parse(jsonStr, deterministic)
         (metadata.modelUsed = "deterministic-regex"; warning DETERMINISTIC_EXTRACTION_NO_AI)
  Gate3: ExtractionValidator.validateProfile()   // name+email BLOCKER, unchanged
  P2ValidationEngine.validateProfileExtraction() // unchanged
  -> Result.success (onboarding continues to ReviewingExtraction)
```

## 4. Explicit outcomes
| Condition | Outcome |
|---|---|
| Deterministic extraction succeeds (name+email+evidence) | `Result.success`; warning `DETERMINISTIC_EXTRACTION_NO_AI`; metadata `deterministic-regex`; user reviews on ReviewingExtraction |
| Deterministic succeeds but incomplete/uncertain fields | Same success; validator emits WARNINGs (skills/workHistory may be sparse); fields marked INFERRED with confidence; user edits |
| OCR/document extraction insufficient | Fails earlier: `EMPTY_TEXT` or `NOT_A_CV` (unchanged) |
| AI configured and succeeds | Unchanged AI path; metadata `openrouter-strict` |
| AI unconfigured or fails | Deterministic fallback; if insufficient -> `AI_UNAVAILABLE` (truthful failure surfaced by ExtractingScreen "Try again") |

## 5. Guardrails preserved
- A13 API-key guard UNCHANGED (GeminiService.generate() still returns null on blank key).
- Gate0 / Gate1 / Gate3 (ExtractionValidator) / P2ValidationEngine unchanged.
- W5 scoring authority and the Profile → Job → Generate → Score → Improve → Version → Store
  → Repeat workflow untouched.
- No API key hardcoded; no placeholder; no fabrication (missing → truthful failure).

## 6. Test + build evidence
Focused test `DeterministicProfileExtractorTest`:
```
tests="3" skipped="0" failures="0" errors="0"
  - extractsRequiredFieldsFromSampleCv
  - returnsNullWhenEmailMissing
  - returnsNullWhenNameMissing
```
Build:
```
gradle :app:testDebugUnitTest --tests "com.vantedge.app.data.engine.DeterministicProfileExtractorTest"
  -> BUILD SUCCESSFUL in 11m 59s
gradle :app:assembleDebug
  -> BUILD SUCCESSFUL in 4m
```
APK: /storage/emulated/0/Download/app-debug.apk  (70,825,245 bytes)
  cert SHA-256 = 3fe57967c15f81ee39ea2b9b88dd96a7506c1b5b9032606ecbea04bbdb15ee3c (original)
  package = com.vantedge.app

## 7. Limitations / UNVERIFIED
- Device-level onboarding success NOT tested here (no device). Final confirmation requires
  a device run: reinstall the new APK, upload a CV, confirm it reaches the review screen
  without a configured API key, and that fields carry INFERRED provenance + the
  DETERMINISTIC_EXTRACTION_NO_AI warning.
- Deterministic name/work/education heuristics are conservative and may return fewer fields
  than the AI on unusual layouts; such fields are simply omitted (never invented).
- If a CV lacks an email, deterministic extraction returns null and onboarding reports
  AI_UNAVAILABLE (truthful) — the user can use manual entry or configure a key.

## 2b. Verbatim diff — ProfileExtractionEngine.kt
```diff
diff --git a/app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt b/app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt
index 95f2e08..30af937 100644
--- a/app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt
+++ b/app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt
@@ -564,17 +564,31 @@ STRICT RULES:
         )
         val response = aiGateway.generate("profile_extraction", request, 120_000L)
 
+        // ==========================================
+        // DETERMINISTIC-FIRST FALLBACK (no fabrication)
+        // The A13 API-key guard and the provider path are unchanged. When the AI is
+        // unavailable/empty (e.g. no API key configured -> guard returns null), assemble the
+        // profile deterministically from the already-extracted document text. If the
+        // deterministic evidence cannot satisfy the output contract (name AND email), fail
+        // truthfully ("AI_UNAVAILABLE") rather than fabricating a profile.
+        // ==========================================
+        val jsonStr: String
+        val deterministic: Boolean
         if (response.isNullOrBlank()) {
-            throw Exception("EMPTY_AI_RESPONSE")
-        }
-
-        val extractionResult = JsonExtractionEngine.extract(response)
-        if (!extractionResult.success) {
-            throw Exception(extractionResult.failureReason ?: "NO_JSON_FOUND")
+            val det = DeterministicProfileExtractor.extractJson(rawText)
+                ?: throw Exception("AI_UNAVAILABLE")
+            jsonStr = det
+            deterministic = true
+            PipelineTrace.warn(TAG, "AI unavailable — deterministic profile extraction engaged", null)
+        } else {
+            val extractionResult = JsonExtractionEngine.extract(response)
+            if (!extractionResult.success) {
+                throw Exception(extractionResult.failureReason ?: "NO_JSON_FOUND")
+            }
+            jsonStr = extractionResult.content
+            deterministic = false
         }
 
-        val jsonStr = extractionResult.content
-
         // ==========================================
         // GATE 2: STRUCTURAL INTEGRITY & SAFE REPAIR
         // ==========================================
@@ -587,7 +601,7 @@ STRICT RULES:
         return try {
             val rawObj = JSONObject(repairResult.json)
             val normalizedObj = normalizeRootKeys(rawObj)
-            parse(normalizedObj)
+            parse(normalizedObj, deterministic)
         } catch (e: Exception) {
             Log.e(TAG, "JSON parse/mapping failed", e)
             throw Exception("PARSE_ERROR")
@@ -674,7 +688,7 @@ STRICT RULES:
         return conf.coerceIn(0.0f, 1.0f)
     }
 
-    private fun parse(json: JSONObject): StructuredProfileExtraction {
+    private fun parse(json: JSONObject, deterministic: Boolean = false): StructuredProfileExtraction {
         val skillsArray = json.optJSONArray("skills")
         val workArray = json.optJSONArray("workHistory")
         val eduArray = json.optJSONArray("education")
@@ -798,8 +812,8 @@ STRICT RULES:
             education = edu.toImmutableList(),
             certifications = certs.toImmutableList(),
             overallConfidence = confidence,
-            warnings = persistentListOf(),
-            metadata = ExtractionMetadata("openrouter-strict")
+            warnings = if (deterministic) persistentListOf("DETERMINISTIC_EXTRACTION_NO_AI") else persistentListOf(),
+            metadata = ExtractionMetadata(if (deterministic) "deterministic-regex" else "openrouter-strict")
         )
     }
 
```
