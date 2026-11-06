# VANTEDGE — P0 PROFILE EXTRACTION FAILURE INVESTIGATION — EVIDENCE REPORT

**Directive:** VANTEDGE — P0 PROFILE EXTRACTION FAILURE INVESTIGATION
**Symptom (device):** Onboarding CV upload → "AI timed out"; log shows `EMPTY_AI_RESPONSE` at `ProfileExtractionEngine.kt:568`
**Baseline HEAD:** 56093227307f5b4ab7d7511e14541fa3d496fc78 (branch `verification/ced-p0-authority`)
**Date:** 2026-10-16

---

## VERDICT (one line)
The failure is **NOT** a pipeline-routing defect. The semantic `pipeline` argument IS
forwarded correctly end-to-end. The immediate 1 ms failure is caused by an **empty
`OPENROUTER_API_KEY`** hitting the A13 fail-closed guard in `GeminiService.generate()`,
which returns `null` before any network request → `AiGateway` reports `success=false` →
`ProfileExtractionEngine.callAi()` throws `EMPTY_AI_RESPONSE`.

Per Phase 2 ("If the routing defect is absent, do not apply an unrelated patch"), **no
source patch is applied**. The corrective action is a build-configuration change (supply
a valid API key), not a source change.

---

## PHASE 1 — INSPECT (no modifications made)

### 1.1 Repository state
```
HEAD: 56093227307f5b4ab7d7511e14541fa3d496fc78
branch: verification/ced-p0-authority
working tree: 9 modified, 1 deleted (Rev E privacy changes), plus untracked report/artifact files
(with the Revision E privacy remediation + signing-restore in place; no new edits here)
```

### 1.2 Code that produces EMPTY_AI_RESPONSE
`ProfileExtractionEngine.kt:565-569`:
```kotlin
val response = aiGateway.generate("profile_extraction", request, 120_000L)

if (response.isNullOrBlank()) {
    throw Exception("EMPTY_AI_RESPONSE")   // line 568
}
```

### 1.3 End-to-end trace (from source + device log)
1. `ProfileExtractionEngine.callAi()` → `aiGateway.generate("profile_extraction", request, 120_000L)` (line 565)
2. `AiGateway.generate(...)` logs `ENTRY promptLength=2579` (device log) then calls:
   `geminiService.generate(requestId=..., pipeline=pipeline, request=..., onModelResult=..., budgetDeadlineMs=...)` (AiGateway.kt:71-77)
3. `GeminiService.generate(...)` (GeminiService.kt:153-180):
   **line 161** `if (apiKey.isBlank()) { onModelResult(CONFIG_ERROR ...); return null }`
4. `result == null` back in `AiGateway` → logs `EXIT duration=1ms success=false` (device log) and returns `null`
5. `ProfileExtractionEngine.callAi` → `response.isNullOrBlank()` true → `throw Exception("EMPTY_AI_RESPONSE")` (line 568)

Device log corroborates the ~1 ms no-network path:
```
I AiGateway: [profile_extraction] [REQ-000002] ENTRY promptLength=2579
I AiGateway: [profile_extraction] [REQ-000002] EXIT duration=1ms success=false
E ProfileExtractionEngine: structureProfile failed: EMPTY_AI_RESPONSE
E ProfileExtractionEngine: java.lang.Exception: EMPTY_AI_RESPONSE
    at com.vantedge.app.data.engine.ProfileExtractionEngine.callAi(ProfileExtractionEngine.kt:568)
    at com.vantedge.app.data.engine.ProfileExtractionEngine.structureProfile-yxL6bBk(ProfileExtractionEngine.kt:280)
```

### 1.4 Does AiGateway forward the semantic `pipeline`?  YES
- `AiGateway.kt:73` → `pipeline = pipeline` into `geminiService.generate(...)`
- `GeminiService.kt:179` → `tryChain(requestId, pipeline, request, ...)`
- `GeminiService.kt:272` → `tryModel(requestId, pipeline, model, ...)`
- `GeminiService.kt:485` accepts `pipeline: String` and **uses it for routing** at line 936:
```kotlin
} else when (pipeline) {
    "cover_letter" -> { ... raw passthrough ... }
    "job_extraction", "compatibility", "profile_extraction", "cv", "cv_docx" -> { extractContent(raw) }
    else -> { ... default JSON ... }
}
```
So `profile_extraction` is explicitly handled. **No routing defect present.**

### 1.5 Is routing based on REQ correlation IDs?  NO
The `REQ-XXXXXX` value is used only for correlation/telemetry (requestId in logs and PipelineTrace).
Routing is by the semantic `pipeline` string (line 936). **The CED-P0-PIPELINE-IDENTITY-001 defect is absent.**

### 1.6 Why the gateway reports failure in 1 ms — EXACT cause
`BuildConfig.OPENROUTER_API_KEY` resolves to the empty string:
- `app/build.gradle:29-31`: `def apiKey = properties.getProperty('OPENROUTER_API_KEY', '""')` then `buildConfigField "String","OPENROUTER_API_KEY", apiKey`
- `local.properties` contains **no** `OPENROUTER_API_KEY` (0 occurrences)
⇒ `apiKey = ""` ⇒ `GeminiService.generate()` line 161 `apiKey.isBlank()` is TRUE
⇒ emits a controlled `CONFIG_ERROR` result and `return null` **without any network request** (~1 ms).
This is exactly the intentional fail-closed behavior introduced by CED-PRIV-01 Revision E (A13).

---

## PHASE 2 — CORRECTION DECISION
**No source patch applied.** The authorized pipeline-identity correction
(CED-P0-PIPELINE-IDENTITY-001) is **not applicable**: the pipeline is already forwarded
and used for routing. Applying it would be an unrelated change. Per the directive,
the actual cause is reported instead.

**Required corrective action (configuration, not source):** provide a valid OpenRouter API
key so `BuildConfig.OPENROUTER_API_KEY` is non-blank:
1. Add to `local.properties` (gitignored): `OPENROUTER_API_KEY=sk-or-...` (the operator's own key)
2. Rebuild: `gradle :app:assembleDebug ...`
3. Reinstall the rebuilt APK.

This does not authorize committing or embedding a shared production secret.

---

## PHASE 3 — VERIFICATION / EVIDENCE
1. **Cause established:** blank `BuildConfig.OPENROUTER_API_KEY` → A13 fail-closed guard
   (`GeminiService.kt:161-176`) → `null` in ~1 ms → `EMPTY_AI_RESPONSE`
   (`ProfileExtractionEngine.kt:568`). Matches the device log exactly.
2. **Files changed in this investigation:** NONE (Phase 1 was read-only; Phase 2 no patch).
3. **Semantic pipeline reaching routing:** verified by source trace (AiGateway.kt:73 →
   GeminiService.kt:179 → :272 → :485 → :936 `"profile_extraction"` case).
4. **Original error path:** it still returns empty **for this reason** because no API key is
   configured; with a valid key, `generate()` proceeds to `tryChain()`/`tryModel()` and the
   `EMPTY_AI_RESPONSE` short-circuit is not reached.
5. **Test/build evidence:**
   - `A13BlankApiKeyTest` (existing): PASS — asserts blank key returns
     `failureType="CONFIG_ERROR"` and `generate()==null` (tests="1" failures="0" errors="0").
   - `:app:assembleDebug`: BUILD SUCCESSFUL (debug APK built earlier this session).
   - **Real profile-extraction confirmation is NOT available** in this sandbox (no device/network);
     final confirmation requires a device log after configuring the key. Marked UNVERIFIED.

---

## PROHIBITED-ACTION COMPLIANCE
- No OCR, structural-gate, or scoring changes.
- No broad refactoring; no unrelated fixes.
- No commits or pushes.
- Phase 1 read-only; Phase 2 intentionally applied no patch (routing defect absent).

## REMAINING / UNVERIFIED
- Whether onboarding succeeds end-to-end after a valid key is configured — UNVERIFIED
  (requires a device run with network).
- The UI text "AI timed out" is generic; the log shows an instantaneous (1 ms)
  configuration failure, not a network timeout.
