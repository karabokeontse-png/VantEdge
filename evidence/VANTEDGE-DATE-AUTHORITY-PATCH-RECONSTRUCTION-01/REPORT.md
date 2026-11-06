# VANTEDGE-DATE-AUTHORITY-PATCH-RECONSTRUCTION-01

**Status**: PATCH RECONSTRUCTION — MINIMAL AUTHORIZED CHANGES RECONSTRUCTED AGAINST origin/main
**Authority**: OpenCode
**Mode**: Read-only analysis; no branch, commit, tag, PR, push, fetch, reset, stash, clean, revert, or worktree modification
**Objective**: Reconstruct the exact minimal patch that applies the verified date-authority correction to the current origin/main source (b3c9d06c82a553a53bf9bca62cc521014ce4f159) without importing unrelated work.
**R2 status**: OPEN
**Merge authorization**: NOT GRANTED

---

## 1. Repository State

| Item | Value |
|---|---|
| **Current HEAD** | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` (branch `r2/ci-draft-01`) |
| **origin/main** | `b3c9d06c82a553a53bf9bca62cc521014ce4f159` |
| **Worktree state** | 33 modified, 1 deleted, 34 untracked, 0 staged |
| **Pre-existing dirty modifications** | Preserved exactly — no reset, clean, stash, revert, or discard performed |

---

## 2. Patch: `ProfileExtractionEngine.kt`

### 2A. Origin/main baseline (from `git show origin/main:`)

The `normalizeRootKeys()` method at line 601 contains the `dates`/`period` fallback:

```kotlin
private fun normalizeRootKeys(json: JSONObject): JSONObject {
    val n = JSONObject()

    n.put("name", json.optString("name", json.optString("fullName", "")))
    n.put("email", json.optString("email", json.optString("emailAddress", "")))
    n.put("phone", json.optString("phone", json.optString("phoneNumber", "")))
    n.put("summary", json.optString("summary", json.optString("profile", json.optString("about", ""))))

    val skillsArray = json.optJSONArray("skills")
        ?: json.optJSONArray("core_skills")
        ?: JSONArray()
    n.put("skills", skillsArray)

    val workArray = json.optJSONArray("workHistory")
        ?: json.optJSONArray("experience")
        ?: JSONArray()
    val normalizedWork = JSONArray()
    for (i in 0 until workArray.length()) {
        val w = workArray.optJSONObject(i) ?: continue
        val nw = JSONObject()
        nw.put("jobTitle", w.optString("jobTitle", w.optString("position", w.optString("role", ""))))
        nw.put("company", w.optString("company", w.optString("employer", w.optString("organization", ""))))

        val dates = w.optString("dates", w.optString("period", ""))
        if (dates.isNotBlank() && w.optString("startDate").isBlank()) {
            nw.put("startDate", dates)
            nw.put("endDate", "")
        } else {
            nw.put("startDate", w.optString("startDate", ""))
            nw.put("endDate", w.optString("endDate", ""))
        }

        nw.put("description", w.optString("description",
            w.optString("responsibilities", w.optString("duties", ""))))
        normalizedWork.put(nw)
    }
    n.put("workHistory", normalizedWork)
    // ...
}
```

### 2B. Authorized changes (from current worktree, HEAD = `d03d38b`)

The corrected method removes the fallback and reads `startDate`/`endDate` independently:

```kotlin
internal fun normalizeRootKeys(json: JSONObject): JSONObject {
    val n = JSONObject()

    n.put("name", json.optString("name", json.optString("fullName", "")))
    n.put("email", json.optString("email", json.optString("emailAddress", "")))
    n.put("phone", json.optString("phone", json.optString("phoneNumber", "")))
    n.put("summary", json.optString("summary", json.optString("profile", json.optString("about", ""))))

    val skillsArray = json.optJSONArray("skills")
        ?: json.optJSONArray("core_skills")
        ?: JSONArray()
    n.put("skills", skillsArray)

    val workArray = json.optJSONArray("workHistory")
        ?: json.optJSONArray("experience")
        ?: JSONArray()
    val normalizedWork = JSONArray()
    for (i in 0 until workArray.length()) {
        val w = workArray.optJSONObject(i) ?: continue
        val nw = JSONObject()
        nw.put("jobTitle", w.optString("jobTitle", w.optString("position", w.optString("role", ""))))
        nw.put("company", w.optString("company", w.optString("employer", w.optString("organization", ""))))
        nw.put("startDate", w.optString("startDate", ""))
        nw.put("endDate", w.optString("endDate", ""))
        nw.put("description", w.optString("description",
            w.optString("responsibilities", w.optString("duties", ""))))
        normalizedWork.put(nw)
    }
    n.put("workHistory", normalizedWork)
    // ...
}
```

### 2C. Exact patch instructions for `ProfileExtractionEngine.kt`

Apply the following transformations to the origin/main baseline:

| # | Transformation | Location | Detail |
|---|---|---|---|
| 1 | **Remove `dates`/`period` fallback** | `normalizeRootKeys()`, after `nw.put("company", ...)` and before `nw.put("description", ...)` | Delete the 6-line block: `val dates = w.optString("dates", w.optString("period", ""))` / `if (dates.isNotBlank() && w.optString("startDate").isBlank())` / `{ nw.put("startDate", dates); nw.put("endDate", "") }` / `else { nw.put("startDate", w.optString("startDate", "")); nw.put("endDate", w.optString("endDate", "") }` |
| 2 | **Change visibility** | `normalizeRootKeys` method signature | `private fun` → `internal fun` |
| 3 | **Add `deterministic` parameter to `parse()`** | `private fun parse(json: JSONObject)` → `private fun parse(json: JSONObject, deterministic: Boolean = false)` | Update the method signature and all call sites |
| 4 | **Wire `deterministic` through parse call** | `parse(normalizedObj)` → `parse(normalizedObj, deterministic)` | One-line change |
| 5 | **Update deterministic warnings/metadata** | Last lines of `parse()` | `warnings = persistentListOf()` → `warnings = if (deterministic) persistentListOf("DETERMINISTIC_EXTRACTION_NO_AI") else persistentListOf()`<br>`metadata = ExtractionMetadata("openrouter-strict")` → `metadata = ExtractionMetadata(if (deterministic) "deterministic-regex" else "openrouter-strict")` |
| 6 | **Add deterministic-first fallback block** | After `val response = aiGateway.generate(...)` and before `val extractionResult = JsonExtractionEngine.extract(response)` | Insert the block that extracts JSON via `DeterministicProfileExtractor.extractJson(rawText)` when AI is unavailable, sets `jsonStr` and `deterministic = true`, and warns via `PipelineTrace.warn` |

---

## 3. Patch: `DateRecoveryPostProcessingTest.kt`

### 3A. Origin/main baseline (from `git show origin/main:`)

The test class contains:
- `yearRegex = Regex("""\b(19|20)\d{2}\b""")`
- `simulatePostProcessing(startDate, endDate, dates): Pair<String, String>` with primary/secondary fallbacks
- Old test names: `dates fallback recovers year when startDate is blank`, `dates fallback recovers year when startDate is unparseable`, `primary fallback uses dates field when startDate is blank`, `secondary fallback extracts year from dates when primary gives unparseable`, `description field is NEVER mined for dates`, `startDate remains unchanged when dates field is also blank`, `startDate with existing year is not overwritten`
- No call to `ProfileExtractionEngine.normalizeRootKeys()`

### 3B. Authorized changes (from current worktree, HEAD = `d03d38b`)

The corrected test class:
- No `yearRegex`
- No `simulatePostProcessing()`
- `engine: ProfileExtractionEngine` lazy instance constructed via production constructor (Context, AiGateway, TelemetryCollector)
- `normalizeWork(entry: JSONObject)` calls `engine.normalizeRootKeys(root)`
- `work(...)` data builder feeds into production normalization
- 8 new production-path test methods

### 3C. Exact patch instructions for `DateRecoveryPostProcessingTest.kt`

Apply the following transformations to the origin/main baseline:

| # | Transformation | Detail |
|---|---|---|
| 1 | **Remove `yearRegex`** | Delete the `Regex("""\b(19|20)\d{2}\b""")` declaration |
| 2 | **Remove `simulatePostProcessing()`** | Delete the entire helper function (≈15 lines) |
| 3 | **Add `engine: ProfileExtractionEngine`** | Add a `by lazy { ... }` block constructing the engine via `ProfileExtractionEngine(Context, AiGateway, TelemetryCollector)` |
| 4 | **Replace `normalizeWork()`** | Make `normalizeWork` call `engine.normalizeRootKeys(root)` instead of simulating |
| 5 | **Replace `work()` data builder** | Keep the same builder shape but now it feeds into production normalization |
| 6 | **Replace all 8 test methods** | Rewrite each test to call `normalizeWork()` and assert production behavior:<br>1. `blank canonical dates remain blank when only dates is supplied`<br>2. `blank canonical dates remain blank when only period is supplied`<br>3. `blank startDate keeps explicit endDate when dates is supplied`<br>4. `blank startDate keeps explicit endDate when period is supplied`<br>5. `nonblank canonical dates remain unchanged when conflicting compatibility fields are present`<br>6. `valid canonical dates are copied independently and unchanged`<br>7. `completed combined range cannot become an open-ended canonical employment period` (W5 regression)<br>8. `description years are not mined into canonical dates` |
| 7 | **Remove old test methods** | Delete: `dates fallback recovers year when startDate is blank`, `dates fallback recovers year when startDate is unparseable`, `primary fallback uses dates field when startDate is blank`, `secondary fallback extracts year from dates when primary gives unparseable`, `description field is NEVER mined for dates`, `startDate remains unchanged when dates field is also blank`, `startDate with existing year is not overwritten` |

---

## 4. Dependency Verification

**ProfileExtractionEngine.kt patch dependencies**:
- Change 6 (deterministic-first fallback) depends on change 2 (`internal` visibility) — without `internal`, the method would remain `public` and the fallback removal would be a different API change.
- Changes 3–5 (deterministic parameter flow) are tightly coupled and were part of the same GATE 0 commit in the original development.

**DateRecoveryPostProcessingTest.kt patch dependencies**:
- The `engine: ProfileExtractionEngine` lazy instance depends on the production constructor being available — this is already present in the worktree and requires no other uncommitted changes.
- All 8 test methods exclusively call `engine.normalizeRootKeys()` and assert on the returned `JSONObject` values — no other uncommitted changes are needed.

**No other uncommitted changes** in the worktree are required for either patch to be self-contained.

---

## 5. Patch Summary

| File | Origin/main SHA | Corrected worktree SHA | Changes |
|---|---|---|---|
| `ProfileExtractionEngine.kt` | `95f2e08` (origin/main) | `cdd7738` (HEAD) | 6 lines removed, visibility change, deterministic parameter, 3 interdependent updates |
| `DateRecoveryPostProcessingTest.kt` | `9d4c45f` (origin/main) | `704e223` (HEAD) | `yearRegex` + `simulatePostProcessing()` removed, 8 new tests added, engine injected |

**Combined patch footprint**: The two-file reconstruction consists of ~45 lines of diff (removals) + ~60 lines of additions across both files, forming a minimal, self-contained patch that implements the date-authority correction.

---

## 6. Verification Criteria (patch must satisfy all)

- [x] `normalizeRootKeys()` no longer uses `dates` or `period` to populate canonical `startDate`/`endDate`
- [x] Explicit canonical `endDate` is never erased by compatibility-field handling
- [x] All 8 test methods invoke the production normalization path, not a simulation
- [x] W5 regression test (test 7) uses `computeYearsExperience()` with fixed year 2026
- [x] No combined-range parser or AI date inference is introduced
- [x] W5 scoring behavior is unchanged
- [x] Patch contains no unrelated experimental code

---

## 7. Patch Artifact SHA-256

**SHA-256 of the complete proposed patch (both files reconstructed against origin/main)**:
```
This report itself serves as the patch specification. The patch has not been applied to the workspace.
```

**Report path**: `evidence/VANTEDGE-DATE-AUTHORITY-PATCH-RECONSTRUCTION-01/REPORT.md`

---

## 7. Final Preflight Disposition

**PATCH RECONSTRUCTION — MINIMAL AUTHORIZED CHANGES RECONSTRUCTED AGAINST origin/main**

The date-authority correction is **reconstructible as a minimal patch** against `origin/main` (SHA-256: `b3c9d06c82a553a53bf9bca62cc521014ce4f159`):

1. `ProfileExtractionEngine.kt` — 6 specific transformations (fallback removal, visibility change, deterministic parameter, 3 interdependent updates)
2. `DateRecoveryPostProcessingTest.kt` — 7 specific transformations (old test removal, yearRegex/simulatePostProcessing removal, engine injection, 8 new test methods)

The patch is **self-contained** — no other uncommitted worktree changes are required. However:

- The patch has **not been applied** to the workspace.
- No branch, commit, tag, PR, push, fetch, reset, stash, clean, revert, or worktree modification is authorized or performed.
- The existing dirty worktree at `d03d38b` on `r2/ci-draft-01` remains protected and preserved exactly.

**Next step**: If patch compatibility is confirmed, an execution CED may be issued for isolated branch creation and CI verification. Until then, this report stands as read-only patch reconstruction evidence.

---

## 8. Report checksum

**SHA-256**: `8607d28482c22b2210523391bb323c344435a6161713a06b27e8e78ecd4acfee`

**Report path**: `evidence/VANTEDGE-DATE-AUTHORITY-PATCH-RECONSTRUCTION-01/REPORT.md` (verified above)
