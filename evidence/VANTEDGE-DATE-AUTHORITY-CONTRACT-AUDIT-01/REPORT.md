# VANTEDGE-DATE-AUTHORITY-CONTRACT-AUDIT-01

Status: **READ-ONLY AUDIT COMPLETE — CONTRACT PARTIALLY ESTABLISHED — BOUNDED CORRECTIVE CED CAN BE ISSUED**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Mode: **READ-ONLY**
- Audit timestamp: `2026-11-05T11:29:48Z`
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01`
- Reference HEAD: `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`
- Reference tree: `86468790a47381865a976a6788d32097c70821e7`
- R2 status: **OPEN**
- Merge authorization: **NOT GRANTED**

---

## 0. Disposition

The committed extraction contract establishes `startDate` and `endDate` as the canonical employment-date fields. The committed AI prompt/schema, `ExtractedExperience`, `WorkExperience`, persistence, UI, and W5 consumers all use only those two fields.

`dates` and `period` are **undocumented compatibility-only fields**. They do not appear in the AI prompt/schema, the extracted model, the domain model, persistence, display, or W5. In the committed reference they appear in exactly one place: `ProfileExtractionEngine.normalizeRootKeys()` at line 624.

That one compatibility fallback contradicts the governing integrity rule:

- It copies the entire combined `dates`/`period` string into `startDate`.
- It clears `endDate` whenever it fires, even when an explicit `endDate` exists.
- It can turn a completed range such as `2020-2024` into an open-ended period because W5 treats a blank `endDate` as the current year.
- It does not parse, split, validate, or mark the result unassessed.

The committed test contract is not a reliable proof of production behavior. `DateRecoveryPostProcessingTest` simulates a secondary year-recovery fallback that does not exist in production. Its two failing assertions are deterministic primary-fallback mismatches, and its passing “unparseable startDate” tests pass only against the simulation.

The current dirty worktree contains a coherent alternative direction (remove the compatibility fallback; add direct authority tests; add a deterministic combined-range parser), but it is uncommitted, not in CI, and not approved merely because it exists. The dirty `DateRecoveryPostProcessingTest` still simulates the old fallback and therefore conflicts with the dirty production change that removes it.

**Final classification: CONTRACT PARTIALLY ESTABLISHED.** A bounded corrective CED can be issued for canonical-field preservation and compatibility-field authority. A broader date-normalization/parser CED is not established by the committed contract.

No implementation changes were made.

---

## 1. Repository and Git contract snapshot

| Item | Observed |
|---|---|
| Repository | `/root/vantedge-canonical` |
| Branch | `r2/ci-draft-01` |
| HEAD | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` |
| HEAD tree | `86468790a47381865a976a6788d32097c70821e7` |
| Staged paths | `0` |
| Worktree counts | `33 modified, 1 deleted, 34 untracked, 0 staged` |
| Tracked diff SHA-256 | `03b563e46fdd67ae3d57284e7130d1820d389de0bbc4a87d068694b0cdeb327b` |
| Index diff SHA-256 | `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` |
| Porcelain status SHA-256 | `d68fd78c035f7619103a1470d56241c1a60643e912d7e4b60257e56c1ca64237` |
| Stash entries | none |
| R2 | **OPEN** |
| Merge | **NOT AUTHORIZED** |

The pre-existing dirty worktree was preserved. This audit added only the report directory and its checksum sidecar.

---

## 2. Evidence separation

### Committed at `d03d38b`

- AI extraction prompt/schema.
- `ExtractedExperience`, `WorkExperience`, `WorkEntry`.
- `ProfileExtractionEngine.normalizeRootKeys()` and `parse()`.
- `ExtractionValidator`.
- `OnboardingCommitService`, `UserPreferences`.
- `ReviewExtractionScreen`, `EditProfileScreen`, `FinalReviewScreen`.
- `CareerEngine`, `GeneratorEngine`.
- `W5Utils`, `W5Adapter`, `DirectW5InputAdapter`, `CompatibilityRecordMapper`, `EvidenceRegistry`.
- `DateRecoveryPostProcessingTest` and the other committed tests cited below.

### Dirty only (not committed, not in reference CI)

- `ProfileExtractionEngine.kt` modification removing the compatibility fallback.
- `DateRecoveryPostProcessingTest.kt` modification changing expectations to verbatim copy.
- `NormalizeRootKeysDateAuthorityTest.kt` untracked direct authority test.
- `DeterministicProfileExtractor.kt` untracked deterministic parser.
- `DeterministicProfileExtractorTest.kt` untracked test with no date assertions.
- `GeneratorEngine.kt` modification adding canonical dates to a DOCX prompt.
- `ReviewedJobRequirementsPromptTest.kt` and `GeneratorViewModelReviewedRequirementsChainTest.kt` untracked fixture use only.

No Git provenance can be established for the dirty changes. They are uncommitted; there are no stash entries; they are not present in any reachable commit. Their existence is evidence of work in progress, not approval.

---

## 3. Field authority matrix

| Field | AI schema | Extracted model | Domain/persistence | Normalization | Validator | Display/W5 | Authority |
|---|---|---|---|---|---|---|---|
| `startDate` | Yes, line 528 | `ExtractedExperience.startDate: ExtractedField?` | `WorkExperience.startDate`, `UserPreferences` | Canonical copy; compatibility fallback can populate only when blank | Hallucination marker only | Displayed; first-year extraction for W5 | **Canonical** |
| `endDate` | Yes, line 529 | `ExtractedExperience.endDate: ExtractedField?` | `WorkExperience.endDate`, `UserPreferences` | Canonical copy; compatibility fallback clears it when it fires | Hallucination marker only | Displayed; blank treated as current by W5 | **Canonical** |
| `dates` | No | No | No | Compatibility fallback only | No | No | **Undocumented compatibility-only** |
| `period` | No | No | No | Compatibility fallback only; lower precedence than `dates` | No | No | **Undocumented compatibility-only** |
| `description` | Yes | `ExtractedExperience.description?` | `WorkExperience.description` | Copied; no date mining | Length/hallucination only | Used in prompts/display | Not a date source |
| `graduationYear` | Education schema | `ExtractedEducation.graduationYear?` | Education string | Education only | Strict 4-digit regex | Education display | Not an employment date |

The committed main-source search for `"dates"`/`"period"` returns exactly one production occurrence:

```text
HEAD:app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt:624
```

No model, persistence, UI, or W5 file contains either field.

---

## 4. Extraction contract (A)

### 4.1 AI extraction prompt and response schema

`app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt:514-557`:

```kotlin
val systemPrompt = """
You are a precise CV extraction engine. Return ONLY valid JSON — no markdown, no explanations, no code fences.

Schema (extract ONLY verbatim — never invent, infer, or expand):
{
  ...
  "workHistory": [
    {
      "jobTitle": "Title exactly as written",
      "company": "Company exactly as written",
      "startDate": "Start exactly as written (month/year)",
      "endDate": "End exactly as written, or empty if current",
      "description": "Description exactly as written"
    }
  ],
  ...
}

STRICT RULES:
- Extract ONLY values explicitly present in the input text
- NEVER invent, infer, or expand abbreviations or generic terms
...
- Use "" for missing strings, [] for missing arrays
- Never omit any key
""".trimIndent()
```

Verified facts:

- The AI contract requests **separate** `startDate` and `endDate` fields.
- It does **not** request `dates` or `period`.
- It explicitly defines empty `endDate` as the representation of “current.”
- It says “month/year” for `startDate`, not a combined range.
- It says “extract ONLY verbatim — never invent, infer, or expand.”

### 4.2 Structured and domain models

`app/src/main/java/com/vantedge/app/data/model/ExtractedExperience.kt:3-9`:

```kotlin
data class ExtractedExperience(
    val jobTitle: ExtractedField,
    val company: ExtractedField,
    val startDate: ExtractedField?,
    val endDate: ExtractedField?,
    val description: ExtractedField?,
    val confidence: Float
)
```

`app/src/main/java/com/vantedge/app/data/model/WorkExperience.kt:3-9`:

```kotlin
data class WorkExperience(
    val role: String,
    val company: String,
    val startDate: String,
    val endDate: String,
    val description: String
)
```

`app/src/main/java/com/vantedge/app/data/model/WorkEntry.kt:3-9` also defines only `startDate` and `endDate`. It is not referenced elsewhere in committed main source.

Verified facts:

- `startDate` and `endDate` are optional in the extraction model (`ExtractedField?`) and non-null strings in the domain model, defaulting to `""` at the mapping boundaries.
- There is no `dates` or `period` property anywhere in these models.
- There is no combined-range type, parser, or serializer.

### 4.3 Persistence contract

`app/src/main/java/com/vantedge/app/data/storage/UserPreferences.kt:122-152`:

```kotlin
private fun serializeWorkHistory(list: List<WorkExperience>): String {
    val array = JSONArray()
    list.forEach { w ->
        array.put(JSONObject().apply {
            put("role", w.role)
            put("company", w.company)
            put("startDate", w.startDate)
            put("endDate", w.endDate)
            put("description", w.description)
        })
    }
    return array.toString()
}

private fun parseWorkHistory(json: String): List<WorkExperience> {
    return try {
        val array = JSONArray(json)
        (0 until array.length()).map { i ->
            val obj = array.getJSONObject(i)
            WorkExperience(
                role = obj.optString("role"),
                company = obj.optString("company"),
                startDate = obj.optString("startDate"),
                endDate = obj.optString("endDate"),
                description = obj.optString("description")
            )
        }
    } catch (e: Exception) {
        emptyList()
    }
}
```

`app/src/main/java/com/vantedge/app/data/domain/OnboardingCommitService.kt:40-47`:

```kotlin
workHistory = extraction.workHistory.map { exp ->
    WorkExperience(
        role = exp.jobTitle.value,
        company = exp.company.value,
        startDate = exp.startDate?.value ?: "",
        endDate = exp.endDate?.value ?: "",
        description = exp.description?.value ?: ""
    )
}
```

Verified facts:

- Persistence stores only `startDate` and `endDate`; `dates`/`period` are never persisted.
- Missing extracted dates become `""`; the model does not invent a value.
- Reload preserves whatever string was stored; there is no date normalization on load.
- A combined range copied into `startDate` will persist and reload unchanged.

---

## 5. Normalization and validation (B)

### 5.1 Committed `normalizeRootKeys()`

`app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt:601-637`:

```kotlin
private fun normalizeRootKeys(json: JSONObject): JSONObject {
    ...
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
    ...
}
```

Caller: `callAi()` at lines 587-590:

```kotlin
val rawObj = JSONObject(repairResult.json)
val normalizedObj = normalizeRootKeys(rawObj)
parse(normalizedObj)
```

Verified facts:

- `dates` takes precedence over `period`; `period` is read only when `dates` is blank.
- The fallback fires only when `startDate` is blank.
- It copies the entire combined string verbatim into `startDate`.
- It clears `endDate` in that branch, even if the input had a nonblank explicit `endDate`.
- It does not parse a range, split on delimiters, strip “Present,” or validate a year.
- It does not mark the result missing/unassessed.
- It cannot overwrite a nonblank `startDate`; the `else` branch preserves both canonical values.

### 5.2 `parse()` date handling

`app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt:699-713`:

```kotlin
val startDateStr = o.optString("startDate")
val endDateStr = o.optString("endDate")
val descStr = o.optString("description")
...
ExtractedExperience(
    ...
    startDate = ExtractedField(startDateStr, computeFieldConfidence(startDateStr, "startDate"), ExtractionSourceType.INFERRED)
        .takeIf { it.value.isNotBlank() },
    endDate = ExtractedField(endDateStr, computeFieldConfidence(endDateStr, "endDate"), ExtractionSourceType.INFERRED)
        .takeIf { it.value.isNotBlank() },
    ...
)
```

Verified facts:

- `parse()` copies the strings into optional `ExtractedField` values.
- Blank dates become `null`; nonblank combined ranges are accepted unchanged.
- No format validation or normalization occurs here.

### 5.3 Extraction validator

`app/src/main/java/com/vantedge/app/data/engine/ExtractionValidator.kt:256-266`:

```kotlin
exp.startDate?.let { sd ->
    checkField("workHistory[$idx].startDate", sd.value, warnings) { value ->
        if (containsHallucinationMarker(value)) "Start date contains hallucination marker" else null
    }
}

exp.endDate?.let { ed ->
    checkField("workHistory[$idx].endDate", ed.value, warnings) { value ->
        if (containsHallucinationMarker(value)) "End date contains hallucination marker" else null
    }
}
```

`ExtractionValidator.kt:293-299` validates education only:

```kotlin
edu.graduationYear?.let { gy ->
    checkField("education[$idx].graduationYear", gy.value, warnings) { value ->
        when {
            !YEAR_REGEX.matches(value.trim()) -> "Graduation year is not a valid year (expected 4 digits)"
            containsHallucinationMarker(value) -> "Graduation year contains hallucination marker"
            else -> null
        }
    }
}
```

Verified facts:

- Employment dates are checked only for hallucination markers.
- There is no format check, range check, delimiter check, year check, or open-ended check for `startDate`/`endDate`.
- A combined range in `startDate` is silently accepted.
- `graduationYear` has a strict 4-digit check, but that is education, not employment.

### 5.4 Sanitizer

Committed `ProfileSanitizer.kt` contains no `workHistory`, `startDate`, `endDate`, `dates`, or `period` handling. There is no sanitizer-level date repair.

---

## 6. Downstream consumers (C)

### 6.1 Caller-to-consumer path

```text
AI JSON (startDate/endDate only per prompt)
  -> JsonRepairUtil.repair
  -> ProfileExtractionEngine.normalizeRootKeys   [compatibility fallback]
  -> ProfileExtractionEngine.parse
  -> StructuredProfileExtraction.workHistory: ExtractedExperience
  -> OnboardingCommitService.extractionToProfile / ReviewExtractionScreen
  -> UserProfile.workHistory: WorkExperience
  -> UserPreferences.serializeWorkHistory / parseWorkHistory
  -> UI display/edit
  -> W5 experience calculation
```

### 6.2 Persistence and reload

- `UserPreferences` serializes and parses only `startDate`/`endDate` (lines 122-152).
- `OnboardingCommitService` maps optional extracted values to `""` (lines 40-47).
- No reload-time normalization exists.
- Consequence: a combined range copied into `startDate` is durable and reappears unchanged after reload.

### 6.3 Display and editing

`app/src/main/java/com/vantedge/app/ui/screens/FinalReviewScreen.kt:252-255`:

```kotlin
if (exp.startDate.isNotBlank()) {
    append("  ·  ${exp.startDate}")
    if (exp.endDate.isNotBlank()) append(" – ${exp.endDate}")
}
```

`app/src/main/java/com/vantedge/app/data/engine/CareerEngine.kt:27`:

```kotlin
appendLine("${work.startDate} - ${work.endDate}")
```

`app/src/main/java/com/vantedge/app/data/engine/GeneratorEngine.kt:241`:

```kotlin
"${it.role} at ${it.company} (${it.startDate} - ${it.endDate}): ${it.description}"
```

`EditProfileScreen.kt:489-498` exposes `startDate` and `endDate` as free-text edit fields.

Verified facts:

- Display code renders the raw strings.
- A combined range in `startDate` is displayed as a combined range and can produce a doubled separator.
- The UI warning at `EditProfileScreen.kt:205` and `FinalReviewScreen.kt:220` is:

```kotlin
val hasUnparseableStartDate = workHistory.any { extractYear(it.startDate) == null }
```

Because `extractYear("2020-2024")` returns `2020`, a combined range is **not** warned about. The warning only fires when no 4-digit year exists.

### 6.4 W5 experience calculation

`app/src/main/java/com/vantedge/app/w5/scoring/W5Utils.kt:69-96`:

```kotlin
fun extractYear(dateValue: String?): Int? {
    if (dateValue == null) return null
    val match = Regex("""\b(19|20)\d{2}\b""").find(dateValue)
    return match?.value?.toInt()
}

fun computeYearsExperience(workHistory: List<WorkExperience>, asOfYear: Int): Int {
    val intervals = workHistory.mapNotNull { exp ->
        val start = extractYear(exp.startDate)
        val end = extractYear(exp.endDate) ?: asOfYear
        if (start != null && start <= end) start to end else null
    }.sortedBy { it.first }
    ...
    return merged.sumOf { (start, end) -> end - start }
}
```

`app/src/main/java/com/vantedge/app/w5/scoring/W5Adapter.kt:87-125` applies the same logic to `StructuredProfileExtraction`:

```kotlin
val start = extractYear(experience.startDate?.value)
val end = extractYear(experience.endDate?.value) ?: asOfYear
```

Other consumers:

- `DirectW5InputAdapter.kt:18` calls `computeYearsExperience(profile.workHistory, currentYear)`.
- `CompatibilityRecordMapper.kt:186` sets `ProfileStats.yearsExperience = computeYearsExperience(...)`.
- `EvidenceRegistry.kt:131-145` computes years from `userProfile.workHistory` with the same blank-endDate-as-current logic.

Verified facts:

- `extractYear` returns the first four-digit year in any string.
- A blank `endDate` becomes `asOfYear`.
- The prompt explicitly defines blank `endDate` as “current.”
- Therefore a completed range copied into `startDate` with `endDate` cleared becomes an open-ended interval.

### 6.5 Inflation example

For a completed role with `startDate="2020-2024"` and `endDate=""`, as of 2026:

```text
extractYear("2020-2024") = 2020
extractYear("")          = null -> asOfYear = 2026
computed interval        = 2020..2026
computed years           = 6
```

The true completed range `2020-2024` is 4 years. The fallback therefore adds **2 invented years** in this example. A range such as `2018-2020` would become 8 years instead of 2.

This is a verified code-path consequence. It is not directly observed in CI because the committed tests do not assert exact experience values for combined ranges.

---

## 7. Dirty worktree changes (D)

### 7.1 `ProfileExtractionEngine.kt` (modified)

Date-relevant diff:

```diff
-            val dates = w.optString("dates", w.optString("period", ""))
-            if (dates.isNotBlank() && w.optString("startDate").isBlank()) {
-                nw.put("startDate", dates)
-                nw.put("endDate", "")
-            } else {
-                nw.put("startDate", w.optString("startDate", ""))
-                nw.put("endDate", w.optString("endDate", ""))
-            }
+            nw.put("startDate", w.optString("startDate", ""))
+            nw.put("endDate", w.optString("endDate", ""))
```

Worktree lines 638-639 now preserve canonical fields only. The same diff makes `normalizeRootKeys()` `internal` and adds a deterministic-first fallback path that references the untracked `DeterministicProfileExtractor`.

Effect: removes the committed compatibility fallback and the endDate erasure. This supports canonical authority. It is uncommitted and not part of the reference contract.

### 7.2 `DateRecoveryPostProcessingTest.kt` (modified)

Date-relevant diff:

```diff
-    fun `dates fallback recovers year when startDate is blank`() {
+    fun `primary fallback copies dates verbatim when startDate is blank`() {
         ...
-        assertEquals("2020", start)
+        // Verbatim copy contains a year, so the secondary year-recovery step does not fire.
+        assertEquals("2020-2024", start)
...
-        assertEquals("Jan 2020", start)
+        // Verbatim copy of the combined dates field; no "- Present" stripping occurs.
+        assertEquals("Jan 2020 - Present", start)
```

Effect: changes expectations to the committed simulation's actual output. This documents the current fallback behavior; it does not establish canonical authority. It still simulates a fallback that the dirty `ProfileExtractionEngine` has removed, so it is stale relative to the dirty production change.

### 7.3 `NormalizeRootKeysDateAuthorityTest.kt` (untracked)

This test directly calls `normalizeRootKeys()` (possible only because the dirty engine makes it `internal`). It contains six cases:

- Case 1: blank `startDate` keeps explicit `endDate="2022"` and ignores `dates="2020-2024"`.
- Case 2: blank `startDate`/`endDate` stay blank when only `dates` is present.
- Case 3: blank `startDate`/`endDate` stay blank when only `period` is present.
- Case 4: nonblank `startDate="Jan 2020"`, `endDate="Present"` are not overwritten by conflicting `dates`/`period`.
- Case 5: valid explicit `2018`/`2021` are preserved.
- Case 6: combined compatibility range cannot become an open-ended `startDate`; `computeYearsExperience` returns 0.

Effect: encodes the strict authority contract. It is not committed, not in CI, and would not compile against HEAD because `normalizeRootKeys()` is private at the reference commit. It is candidate evidence, not approved contract.

### 7.4 `DeterministicProfileExtractor.kt` (untracked)

Date-relevant source:

```kotlin
private val DATE_RANGE = Regex(
    """(?i)((?:jan|feb|...)[a-z]*\.?\s*\d{4}|(?:19|20)\d{2})\s*(?:-|–|—|to|until|till)\s*(present|current|now|...|(?:19|20)\d{2})"""
)
...
o.put("startDate", w.third)
o.put("endDate", w.fourth)
...
val dates = rangeText.split(Regex("""(?i)\s*(?:-|–|—|to|until|till)\s*"""))
val start = dates.getOrNull(0)?.trim().orEmpty()
val end = dates.getOrNull(1)?.trim().orEmpty()
```

Effect: this is a deterministic combined-range parser, but it operates on document experience lines, not on the `dates`/`period` compatibility fields. It is referenced only by the dirty `ProfileExtractionEngine` and its untracked test. It is not committed and not part of the reference extraction contract.

### 7.5 `DeterministicProfileExtractorTest.kt` (untracked)

The sample CV contains `Jan 2020 - Present`, but the three tests assert only name/email/skills/null cases. There is no assertion on parsed `startDate`/`endDate`. The deterministic parser's date behavior is therefore untested even in the dirty worktree.

### 7.6 Other dirty files touching dates

- `GeneratorEngine.kt` (modified) adds `startDate`/`endDate` to a new DOCX prompt builder at worktree line 95. It consumes canonical fields; it does not create or overwrite them.
- `ReviewedJobRequirementsPromptTest.kt` and `GeneratorViewModelReviewedRequirementsChainTest.kt` (untracked) use `startDate="2020"`/`endDate="2022"` as fixture data and assert prompt separation. They do not change date authority.
- `git diff -G 'startDate|endDate|"dates"|"period"' HEAD` identifies only `GeneratorEngine.kt`, `ProfileExtractionEngine.kt`, and `DateRecoveryPostProcessingTest.kt` among tracked diffs. The untracked date-relevant source/test files are `DeterministicProfileExtractor.kt`, `DeterministicProfileExtractorTest.kt`, and `NormalizeRootKeysDateAuthorityTest.kt`.

### 7.7 Origin assessment

Git evidence does not permit origin attribution. The changes are uncommitted, there are no stashes, and the untracked files have no commit history. The content pattern is consistent with a prior corrective attempt, but that is inference, not verified provenance. The worktree is not approved merely because it exists.

---

## 8. Test contract (E)

### 8.1 Committed test coverage

| Test / path | What it proves | Gap |
|---|---|---|
| `DateRecoveryPostProcessingTest` (`app/src/test/.../DateRecoveryPostProcessingTest.kt`) | Simulates primary and secondary fallback logic; 7 tests, 2 failures in CI run `42005081565` | Does not call production; secondary fallback does not exist in production; expectations conflict with simulation for 2 cases; no explicit endDate preservation; no combined range in canonical `startDate` |
| `ExtractionValidatorTest` (`.../ExtractionValidatorTest.kt:32-33`) | Accepts `startDate="Jan 2020"` and null `endDate` as valid | Does not validate employment-date format; does not test malformed/combined ranges |
| `W5ScoreEngineTest` (`.../W5ScoreEngineTest.kt:596-618`) | `W5Adapter` produces `experienceYears > 0` from dates `2020-01`/`2024-12` and `2016-06`/`2020-01` | No exact year assertion; no combined-range or blank-endDate inflation case |
| `JobTitleRoleRelevanceProductionTest` (`.../JobTitleRoleRelevanceProductionTest.kt:39-46`) | Uses `WorkExperience` dates `2018`/`2021` as fixture | No assertion on computed experience or date normalization |
| `CompatibilityRecordLegacyJsonTest` (`.../CompatibilityRecordLegacyJsonTest.kt:31,75`) | Persisted `yearsExperience=5` is read from legacy JSON | Tests persistence, not date-derived computation |
| `PhaseDAuthorityTest` (`.../PhaseDAuthorityTest.kt:52`) | Injects `ProfileStats(8,4,7,3,5,"")` | Bypasses date computation entirely |
| `W5ScoreBaselineTest` (`.../W5ScoreBaselineTest.kt:73`) | Injects `experienceYears = i % 15 + 1` | Bypasses date computation |
| `P2VerificationHarnessTest` (`.../P2VerificationHarnessTest.kt:163`) | Injects `"yearsExperience" to 6` | Bypasses date computation |
| `P2VerificationHarnessTest` date fixture (`:236-237`) | Uses `startDate="Jan 2020"`, `endDate=null` | No date assertion |

### 8.2 Committed `DateRecoveryPostProcessingTest` is not a production contract

The committed test's `simulatePostProcessing()` includes a secondary fallback:

```kotlin
if (yearRegex.find(resultStart) == null && dates.isNotBlank()) {
    yearRegex.find(dates)?.value?.let { year ->
        resultStart = year
    }
}
```

Production has no such branch. A repository-wide search for a production `yearRegex.find` returns no matches in `app/src/main` across reachable history. Therefore:

- The passing tests `dates fallback recovers year when startDate is unparseable` and `secondary fallback extracts year from dates when primary gives unparseable` pass only against the simulation.
- Production would preserve `"Recent"`/`"N/A"` rather than recover `"2018"`/`"2015"`.
- The committed test cannot be cited as proof of production fallback behavior.

### 8.3 Missing committed tests

- Direct test of `normalizeRootKeys()` canonical precedence.
- Blank `startDate` + explicit `endDate` + `dates`/`period` → explicit `endDate` must survive.
- `dates`/`period` cannot populate a blank `startDate`.
- `dates`/`period` cannot overwrite nonblank canonical dates.
- Combined range in canonical `startDate` (AI path) is rejected or deterministically parsed.
- `computeYearsExperience` exact result for a completed combined range and blank `endDate` (inflation).
- `extractYear` / `W5Adapter` exact experience for combined ranges.
- Persistence/reload of a combined range.
- Deterministic parser date assertions (if a parser is introduced).

The untracked `NormalizeRootKeysDateAuthorityTest` fills several of these gaps but is not committed and not executed by CI.

---

## 9. Answers to the ten required decisions

### 1. Are `startDate` and `endDate` the canonical employment-date fields?

**Yes.** The AI prompt/schema requests only `startDate` and `endDate` (`ProfileExtractionEngine.kt:528-529`). `ExtractedExperience`, `WorkExperience`, and `WorkEntry` define only those two date fields. Persistence, UI, and W5 consume only those two. This is the strongest established part of the contract.

### 2. Are `dates` and `period` authoritative, compatibility-only, or undocumented?

**Undocumented compatibility-only fields.** They do not appear in the AI schema, extracted model, domain model, persistence, display, or W5. Their only committed production occurrence is the `normalizeRootKeys()` fallback at `ProfileExtractionEngine.kt:624`. They are not authoritative.

### 3. Can either compatibility field populate a blank `startDate`?

**Yes, in the committed reference.** `ProfileExtractionEngine.kt:625-627` copies the entire `dates`/`period` string into `startDate` when `startDate` is blank. This contradicts the governing rule that a combined string must not be copied wholesale.

### 4. Can either compatibility field overwrite a nonblank canonical date?

**No for `startDate`.** The fallback requires `startDate` to be blank. A nonblank `startDate` is preserved by the `else` branch. However, see decision 5: a nonblank `endDate` can be erased when `startDate` is blank.

### 5. Can the current fallback erase an explicit `endDate`?

**Yes.** `ProfileExtractionEngine.kt:627` sets `endDate=""` whenever the fallback fires. If the input had `startDate=""`, `endDate="2022"`, and `dates="2020-2024"`, the output is `startDate="2020-2024"`, `endDate=""`. The explicit end date is lost.

### 6. Can the current behavior inflate W5 experience?

**Yes.** `W5Utils.extractYear()` takes the first year from `startDate`; `computeYearsExperience()` uses `extractYear(endDate) ?: asOfYear`. With `startDate="2020-2024"` and `endDate=""`, W5 computes 2020..2026 = 6 years instead of 4. The same logic exists in `W5Adapter.deriveExperienceYears()`, `DirectW5InputAdapter`, `CompatibilityRecordMapper`, and `EvidenceRegistry`.

### 7. Does the authoritative extraction contract define a deterministic combined-range parser?

**No, not in the committed reference.** The committed prompt requests separate `startDate`/`endDate`; `normalizeRootKeys()` performs no parsing; `ExtractionValidator` performs no format validation. A deterministic parser exists only in the dirty untracked `DeterministicProfileExtractor`, which operates on document lines and is not part of the reference contract.

### 8. Which existing tests prove the intended behavior?

**No committed test fully proves a coherent date-authority contract.** `DateRecoveryPostProcessingTest` simulates a secondary fallback that production does not implement and has two failing assertions. `ExtractionValidatorTest` only proves that `"Jan 2020"` is accepted. `W5ScoreEngineTest` only asserts `experienceYears > 0`. Most W5 tests inject `experienceYears` directly and bypass date computation. The only direct authority test is the untracked `NormalizeRootKeysDateAuthorityTest`, which is not committed or executed.

### 9. Which dirty worktree changes conflict with or support the contract?

**Support canonical authority:**

- `ProfileExtractionEngine.kt` removes the compatibility fallback and preserves canonical fields only.
- `NormalizeRootKeysDateAuthorityTest.kt` asserts strict authority, explicit endDate preservation, and no inflation.
- `DeterministicProfileExtractor.kt` adds a deterministic combined-range parser for document text.

**Conflict or remain unresolved:**

- `DateRecoveryPostProcessingTest.kt` changes expectations to verbatim compatibility copy, which documents the old fallback rather than enforcing canonical authority. It also still simulates a fallback that the dirty production change removes.
- The dirty parser is not committed, not in CI, and its own test has no date assertions.
- The dirty changes have no Git provenance and are not approved.

### 10. Is there sufficient evidence to issue a bounded corrective CED?

**Yes — for a bounded corrective CED on canonical-field authority.** The evidence establishes that the committed compatibility fallback copies a combined string wholesale, erases explicit end dates, and can inflate W5 experience. A bounded CED can require canonical-field preservation and removal/neutralization of the compatibility fallback, with committed tests for the missing cases.

A broader CED that introduces a deterministic combined-range parser is **not** established by the committed contract. That parser exists only in the dirty worktree and would require an explicit contract decision. The dirty worktree must not be treated as approval.

---

## 10. Contradictions against the governing integrity rule

| Governing rule | Committed evidence | Status |
|---|---|---|
| `startDate`/`endDate` are canonical | AI schema, models, persistence, UI, W5 use only these | **Supported** |
| Combined `dates`/`period` must not be copied wholesale into `startDate` | `ProfileExtractionEngine.kt:626` copies the whole string | **Contradicted** |
| Compatibility field must not silently erase/replace an explicit canonical field | `ProfileExtractionEngine.kt:627` clears explicit `endDate` | **Contradicted** |
| Ambiguous combined range must not be interpreted as open-ended | Blank `endDate` is defined as current; W5 treats blank as `asOfYear` | **Contradicted** |
| Missing/ambiguous date evidence must remain missing/unassessed | Fallback converts missing `startDate` into a combined string and clears `endDate` | **Contradicted** |
| No description mining or AI inference may establish canonical dates | No description mining found; AI prompt says verbatim only; but AI-returned combined `startDate` is accepted without validation | **Partially supported** |

---

## 11. Final classification

**CONTRACT PARTIALLY ESTABLISHED**

Established:

- `startDate` and `endDate` are the canonical employment-date fields.
- `dates` and `period` are undocumented compatibility-only fields, not authoritative.
- A nonblank `startDate` is not overwritten by the committed fallback.
- Missing extracted dates remain blank at the model/persistence boundary when no fallback is applied.

Not established or contradicted:

- Whether compatibility fields may populate a blank `startDate`.
- Whether an explicit `endDate` must survive when `startDate` is blank and compatibility data exists.
- Whether a combined range must be rejected, ignored, or deterministically parsed.
- Whether AI-returned combined `startDate` values are valid.
- Exact W5 experience semantics for combined ranges and blank end dates.
- A coherent committed test contract for date authority.

---

## 12. Corrective CED statement

**A bounded corrective CED can be issued.**

The bounded scope supported by this audit is:

- Preserve `startDate` and `endDate` as canonical in `normalizeRootKeys()`.
- Stop wholesale copying of `dates`/`period` into `startDate`.
- Do not clear an explicit `endDate` because a compatibility field is present.
- Do not let missing/ambiguous compatibility data become an open-ended employment period.
- Add committed tests for canonical preservation, explicit endDate preservation, compatibility-field non-authority, combined canonical `startDate`, and W5 inflation.

The audit does **not** establish a deterministic combined-range parser in the committed contract. The dirty `DeterministicProfileExtractor` is a candidate only. A CED that adds or adopts a parser must separately establish that contract and must not rely on uncommitted worktree state as approval.

No R2 acceptance is claimed. No PR merge is authorized.
