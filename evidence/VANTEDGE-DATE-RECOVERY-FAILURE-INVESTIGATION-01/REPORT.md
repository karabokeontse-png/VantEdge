# VANTEDGE-DATE-RECOVERY-FAILURE-INVESTIGATION-01

Status: **READ-ONLY INVESTIGATION COMPLETE — ROOT CAUSE ESTABLISHED FOR BOTH FAILURES — CONTRACT AUTHORITY AMBIGUOUS**

- Authority: ChatGPT — Lead Architect
- Executor: OpenCode
- Mode: **READ-ONLY**
- Investigation timestamp: `2026-11-05T10:57:19Z`
- Repository: `/root/vantedge-canonical`
- Branch: `r2/ci-draft-01`
- Reference commit: `d03d38b3f44c3ba9a104241e0a93cee07fc285ee`
- CI run: https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081565
- R2 status: **OPEN**
- Merge authorization: **NOT GRANTED**

---

## 0. Executive summary

The two failing `DateRecoveryPostProcessingTest` assertions are caused by the **blank-`startDate` primary fallback copying the entire combined `dates` string into `startDate`** and by the test's **secondary year-recovery step not firing** because the copied string already contains a four-digit year.

The failures are not caused by AAPT2, CI configuration, or flaky test infrastructure. They are deterministic test-assertion failures in the committed test source at `d03d38b`.

### Definitive finding — Failure 1

Test: `dates fallback recovers year when startDate is blank` (`DateRecoveryPostProcessingTest.kt:45`).

- Input: `startDate=""`, `endDate=""`, `dates="2020-2024"`.
- Expected: `start="2020"`, `end=""`.
- Actual: `start="2020-2024"`, `end=""`.
- Cause: `simulatePostProcessing()` line 29 assigns the whole `dates` value to `resultStart`. The secondary fallback at lines 33–37 is skipped because `yearRegex.find("2020-2024")` succeeds. No range-splitting or first-year normalization exists.

### Definitive finding — Failure 2

Test: `primary fallback uses dates field when startDate is blank` (`DateRecoveryPostProcessingTest.kt:67`).

- Input: `startDate=""`, `endDate=""`, `dates="Jan 2020 - Present"`.
- Expected: `start="Jan 2020"`, `end=""`.
- Actual: `start="Jan 2020 - Present"`, `end=""`.
- Cause: the same line 29 copies the whole combined string. The secondary fallback is skipped because the string contains `2020`. No delimiter-based left-side extraction or `"Present"` stripping exists.
- Independent additional finding: even if the secondary fallback did fire, lines 34–35 would set `resultStart` to `"2020"`, not `"Jan 2020"`. The expected value is therefore unreachable in the current `simulatePostProcessing()` logic for this input.

### Shared trigger, distinct missing transformations

Both failures enter the same primary-fallback branch. They are not fully explained by a single missing year-extraction step:

- Failure 1 needs a range-start/first-year transformation.
- Failure 2 needs a combined-range split that preserves the month-year left side.

The committed test is a **pure-function simulation**, not a call into `ProfileExtractionEngine`. Production has the same primary fallback but no secondary fallback at all. The test's expectations are inconsistent with its own simulation and with production. Whether the test or production is authoritative is contractually ambiguous; the mechanical cause of both CI failures is established.

No source, test, workflow, or Gradle file was modified. No implementation change is proposed.

---

## 1. Contract snapshot and preservation

| Item | Observed |
|---|---|
| Branch | `r2/ci-draft-01` |
| HEAD | `d03d38b3f44c3ba9a104241e0a93cee07fc285ee` |
| Reference commit | same |
| Test blob | `9d4c45f6f37ad4d02086daa80f271e6716215c7f` |
| Production blob | `95f2e088bbe8b7456744a18fa2a772e9f8237cdd` |
| Staged paths | `0` |
| Worktree counts | `33 M, 1 D, 34 ??, 0 staged` |
| Tracked diff SHA-256 | `03b563e46fdd67ae3d57284e7130d1820d389de0bbc4a87d068694b0cdeb327b` |
| Index diff SHA-256 | `e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855` |
| Tracked-status SHA-256 | `f42d00b279969ddf211f5ef69546af88d50b75adfde641c636794f429272eda8` |

Pre-existing dirty paths relevant to this investigation:

```text
 M app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt
 M app/src/test/java/com/vantedge/app/data/engine/DateRecoveryPostProcessingTest.kt
?? app/src/test/java/com/vantedge/app/data/engine/NormalizeRootKeysDateAuthorityTest.kt
```

These paths were already dirty before this investigation and were not modified. The report directory is the only new path added by this investigation.

---

## 2. Complete source of `DateRecoveryPostProcessingTest`

Committed source at `d03d38b` (`app/src/test/java/com/vantedge/app/data/engine/DateRecoveryPostProcessingTest.kt`, 132 lines):

```kotlin
package com.vantedge.app.data.engine

import org.junit.Assert.*
import org.junit.Test

/**
 * TD-PROFILE-002a: Deterministic date recovery test.
 * Verifies the post-processing fallback logic for startDate recovery
 * from structured dates/period fields, and confirms description mining is NOT performed.
 */
class DateRecoveryPostProcessingTest {

    private val yearRegex = Regex("""\b(19|20)\d{2}\b""")

    /**
     * Simulates the enhanced post-processing logic from ProfileExtractionEngine.kt:
     * 1. Primary fallback: use combined dates field if startDate is blank
     * 2. Secondary fallback: if startDate still lacks a 4-digit year, recover from dates field
     */
    private fun simulatePostProcessing(
        startDate: String,
        endDate: String,
        dates: String
    ): Pair<String, String> {
        var resultStart = startDate
        var resultEnd = endDate

        if (dates.isNotBlank() && resultStart.isBlank()) {
            resultStart = dates
            resultEnd = ""
        }

        if (yearRegex.find(resultStart) == null && dates.isNotBlank()) {
            yearRegex.find(dates)?.value?.let { year ->
                resultStart = year
            }
        }

        return resultStart to resultEnd
    }

    // --- Positive Tests: dates fallback ---

    @Test
    fun `dates fallback recovers year when startDate is blank`() {
        val (start, end) = simulatePostProcessing(
            startDate = "",
            endDate = "",
            dates = "2020-2024"
        )
        assertEquals("2020", start)
        assertEquals("", end)
    }

    @Test
    fun `dates fallback recovers year when startDate is unparseable`() {
        val (start, end) = simulatePostProcessing(
            startDate = "Recent",
            endDate = "Current",
            dates = "2018-2022"
        )
        assertEquals("2018", start)
        assertEquals("Current", end)
    }

    @Test
    fun `primary fallback uses dates field when startDate is blank`() {
        val (start, end) = simulatePostProcessing(
            startDate = "",
            endDate = "",
            dates = "Jan 2020 - Present"
        )
        assertEquals("Jan 2020", start)
        assertEquals("", end)
    }

    @Test
    fun `secondary fallback extracts year from dates when primary gives unparseable`() {
        val (start, end) = simulatePostProcessing(
            startDate = "N/A",
            endDate = "",
            dates = "2015"
        )
        assertEquals("2015", start)
        assertEquals("", end)
    }

    // --- Negative Tests: description guard ---

    @Test
    fun `description field is NEVER mined for dates`() {
        // Verify simulatePostProcessing does NOT accept description parameter.
        // startDate is blank, dates is blank — only description has years.
        // If the function signature were expanded to include description,
        // this would be a compilation error (tests would not compile).
        val (start, _) = simulatePostProcessing(
            startDate = "",
            endDate = "",
            dates = ""
        )
        // startDate must remain blank — description years are NOT used
        assertEquals("", start)
        // Confirm description contains parseable years (proving the risk exists)
        val description = "Company founded in 1998, project completed 2015"
        val yearsInDescription = yearRegex.findAll(description).map { it.value }.toList()
        assertEquals(2, yearsInDescription.size)
        assertEquals("1998", yearsInDescription[0])
        assertEquals("2015", yearsInDescription[1])
    }

    @Test
    fun `startDate remains unchanged when dates field is also blank`() {
        val (start, end) = simulatePostProcessing(
            startDate = "Recent",
            endDate = "",
            dates = ""
        )
        assertEquals("Recent", start)
        assertEquals("", end)
    }

    @Test
    fun `startDate with existing year is not overwritten`() {
        val (start, end) = simulatePostProcessing(
            startDate = "Jan 2020",
            endDate = "Present",
            dates = "2015-2018"
        )
        assertEquals("Jan 2020", start)
        assertEquals("Present", end)
    }
}
```

### 2.1 Test result matrix from CI artifact

Source: `test-results/testDebugUnitTest/TEST-com.vantedge.app.data.engine.DateRecoveryPostProcessingTest.xml`.

| Test | Result |
|---|---|
| `dates fallback recovers year when startDate is unparseable` | PASS |
| `secondary fallback extracts year from dates when primary gives unparseable` | PASS |
| `startDate remains unchanged when dates field is also blank` | PASS |
| `dates fallback recovers year when startDate is blank` | **FAIL** |
| `description field is NEVER mined for dates` | PASS |
| `startDate with existing year is not overwritten` | PASS |
| `primary fallback uses dates field when startDate is blank` | **FAIL** |

Suite totals: `tests="7" skipped="0" failures="2" errors="0"`.

---

## 3. Production implementation and call path

### 3.1 Entry point and caller

`ProfileExtractionEngine.kt` at `d03d38b`:

- `callAi()` parses the AI JSON, calls `normalizeRootKeys(rawObj)` at line 589, then calls `parse(normalizedObj)` at line 590.
- `normalizeRootKeys()` is a private function at line 601. At the reference commit it is not directly callable from the test.
- `parse()` reads `startDate` and `endDate` at lines 699–700 and wraps them in `ExtractedField` at lines 710–713 without changing their string values.

### 3.2 The responsible production code

`app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt:624-631`:

```kotlin
val dates = w.optString("dates", w.optString("period", ""))
if (dates.isNotBlank() && w.optString("startDate").isBlank()) {
    nw.put("startDate", dates)
    nw.put("endDate", "")
} else {
    nw.put("startDate", w.optString("startDate", ""))
    nw.put("endDate", w.optString("endDate", ""))
}
```

Verified facts:

- If `dates` is nonblank and `startDate` is blank, the entire `dates` string is written to `startDate`.
- `endDate` is forcibly set to `""` in that branch, even if an explicit `endDate` was present.
- There is no split on `-`, no extraction of the left side, no `"Present"` stripping, and no year-only normalization.
- The fallback reads `period` only when `dates` is absent or blank.

### 3.3 The test is not an integration test

The test does not construct `ProfileExtractionEngine` and does not call `normalizeRootKeys()`. It reimplements a simplified version in `simulatePostProcessing()`.

The test's comment claims a "secondary fallback: if startDate still lacks a 4-digit year, recover from dates field." Production has no such secondary fallback. A repository-wide search for a production implementation of `yearRegex.find` returned no matches in `app/src/main` across reachable history. The secondary fallback exists only inside the test simulation.

### 3.4 Downstream data transformations

If the production fallback produces a combined range:

- `W5Utils.extractYear()` (`W5Utils.kt:69-73`) returns the first four-digit year in the string.
- `W5Utils.computeYearsExperience()` (`W5Utils.kt:75-96`) uses `extractYear(endDate) ?: asOfYear`. A blank `endDate` therefore becomes the current year.
- `W5Adapter.deriveExperienceYears()` (`W5Adapter.kt:87-125`) applies the same first-year/open-ended logic to `ExtractedExperience`.
- `CareerEngine` (`CareerEngine.kt:27`) renders `"${work.startDate} - ${work.endDate}"`, so a combined range can be displayed with an extra separator.

These are verified code-path consequences. They are not part of the two CI assertion failures, which occur entirely inside the test simulation.

---

## 4. Failure 1 — `dates fallback recovers year when startDate is blank`

### 4.1 Exact input and expected output

`DateRecoveryPostProcessingTest.kt:45-53`:

```kotlin
fun `dates fallback recovers year when startDate is blank`() {
    val (start, end) = simulatePostProcessing(
        startDate = "",
        endDate = "",
        dates = "2020-2024"
    )
    assertEquals("2020", start)
    assertEquals("", end)
}
```

- `startDate = ""`
- `endDate = ""`
- `dates = "2020-2024"`
- Expected `start = "2020"`, `end = ""`
- Assertion line: `DateRecoveryPostProcessingTest.kt:51`

### 4.2 Actual output

CI XML:

```text
expected:<2020[]> but was:<2020[-2024]>
at com.vantedge.app.data.engine.DateRecoveryPostProcessingTest
  .dates fallback recovers year when startDate is blank(DateRecoveryPostProcessingTest.kt:51)
```

CI log:

```text
DateRecoveryPostProcessingTest > dates fallback recovers year when startDate is blank FAILED
    org.junit.ComparisonFailure at DateRecoveryPostProcessingTest.kt:51
```

Actual `start = "2020-2024"`, `end = ""`.

### 4.3 Complete data path

1. `simulatePostProcessing()` receives `resultStart=""`, `resultEnd=""`, `dates="2020-2024"`.
2. Line 28: `dates.isNotBlank()` is true; `resultStart.isBlank()` is true.
3. Line 29: `resultStart = dates` → `"2020-2024"`.
4. Line 30: `resultEnd = ""`.
5. Line 33: `yearRegex.find("2020-2024")` returns `"2020"`, so `== null` is false. The secondary block is skipped.
6. Line 39 returns `"2020-2024" to ""`.
7. Line 51 compares `"2020-2024"` with `"2020"` and fails.

### 4.4 Definitive finding

**The primary fallback copies the whole range.** The unexpected suffix `-2024` is the literal tail of the input `dates` string. No transformation removes it. The secondary year-recovery step cannot remove it because the copied value already contains a year, so the guard at line 33 is false.

This is a verified deterministic cause.

---

## 5. Failure 2 — `primary fallback uses dates field when startDate is blank`

### 5.1 Exact input and expected output

`DateRecoveryPostProcessingTest.kt:67-75`:

```kotlin
fun `primary fallback uses dates field when startDate is blank`() {
    val (start, end) = simulatePostProcessing(
        startDate = "",
        endDate = "",
        dates = "Jan 2020 - Present"
    )
    assertEquals("Jan 2020", start)
    assertEquals("", end)
}
```

- `startDate = ""`
- `endDate = ""`
- `dates = "Jan 2020 - Present"`
- Expected `start = "Jan 2020"`, `end = ""`
- Assertion line: `DateRecoveryPostProcessingTest.kt:73`

### 5.2 Actual output

CI XML:

```text
expected:<Jan 2020[]> but was:<Jan 2020[ - Present]>
at com.vantedge.app.data.engine.DateRecoveryPostProcessingTest
  .primary fallback uses dates field when startDate is blank(DateRecoveryPostProcessingTest.kt:73)
```

CI log:

```text
DateRecoveryPostProcessingTest > primary fallback uses dates field when startDate is blank FAILED
    org.junit.ComparisonFailure at DateRecoveryPostProcessingTest.kt:73
```

Actual `start = "Jan 2020 - Present"`, `end = ""`.

### 5.3 Complete data path

1. `simulatePostProcessing()` receives `resultStart=""`, `resultEnd=""`, `dates="Jan 2020 - Present"`.
2. Line 28: `dates.isNotBlank()` is true; `resultStart.isBlank()` is true.
3. Line 29: `resultStart = dates` → `"Jan 2020 - Present"`.
4. Line 30: `resultEnd = ""`.
5. Line 33: `yearRegex.find("Jan 2020 - Present")` returns `"2020"`, so `== null` is false. The secondary block is skipped.
6. Line 39 returns `"Jan 2020 - Present" to ""`.
7. Line 73 compares `"Jan 2020 - Present"` with `"Jan 2020"` and fails.

### 5.4 Independent finding — the expected value is unreachable even if the secondary fallback ran

If line 33's guard were removed or changed so the secondary block executed, lines 34–35 would do:

```kotlin
yearRegex.find(dates)?.value?.let { year ->
    resultStart = year
}
```

For `dates = "Jan 2020 - Present"`, `yearRegex.find(dates)?.value` is `"2020"`. The result would be `start = "2020"`, not `"Jan 2020"`.

Therefore:

- The primary fallback produces `"Jan 2020 - Present"`.
- The secondary fallback, if it fired, would produce `"2020"`.
- The test expects `"Jan 2020"`.

No path in the committed `simulatePostProcessing()` produces the expected value for this input. This is a separate defect in the test/specification, not merely a skipped branch.

### 5.5 Definitive finding

**The primary fallback copies the whole combined string, and the test's own secondary fallback cannot preserve the month.** The unexpected suffix `" - Present"` is the literal tail of the input. There is no delimiter split and no left-side selection. The expected `"Jan 2020"` requires a transformation that does not exist in either the test simulation or production.

This is a verified deterministic cause.

---

## 6. Shared trigger versus independent causes

| Aspect | Failure 1 | Failure 2 |
|---|---|---|
| Trigger branch | blank `startDate` + nonblank `dates` | blank `startDate` + nonblank `dates` |
| Primary copy at line 29 | `"2020-2024"` | `"Jan 2020 - Present"` |
| Secondary guard at line 33 | skipped — year present | skipped — year present |
| Missing transformation | split range / take first year | split range / take left month-year |
| Would secondary fallback satisfy expected? | Yes, it would return `"2020"` | **No — it would return `"2020"`, not `"Jan 2020"`** |
| Production equivalent | copies whole `dates` | copies whole `dates` |
| Test expectation reachable? | Only with a new normalization step | **Not reachable in current simulation** |

Conclusion: both failures share the primary-fallback trigger, but they do not share a single complete root cause. Failure 1 is a missing range-start/year normalization. Failure 2 is a missing combined-range split plus an internally inconsistent test expectation.

---

## 7. Related date parsing, fallback, normalization, and formatting tests

### 7.1 Committed tests

- `DateRecoveryPostProcessingTest` — the failing class. 7 tests, 2 failures. It simulates fallback logic; it does not call production.
- `ExtractionValidatorTest` — uses `startDate = "Jan 2020"` as a valid fixture and validates `graduationYear` as four digits. It does not validate `startDate`/`endDate` format and would not reject a combined range.
- `P2VerificationHarnessTest` — uses `startDate = "Jan 2020"` only as a profile fixture; no date-normalization assertion.
- `PhaseDAuthorityTest` — no direct date-recovery assertion.
- `W5ScoreEngineTest` / `JobTitleRoleRelevanceProductionTest` — consume dates indirectly through W5 scoring; no assertion on combined-range normalization.

### 7.2 Uncommitted worktree tests and drift (non-authoritative)

The pre-existing dirty worktree contains changes that are not in `d03d38b` and were not executed by CI:

1. `DateRecoveryPostProcessingTest.kt` (modified):
   - Renames `dates fallback recovers year when startDate is blank` to `primary fallback copies dates verbatim when startDate is blank`.
   - Changes the first expectation from `"2020"` to `"2020-2024"`.
   - Changes the second expectation from `"Jan 2020"` to `"Jan 2020 - Present"`.
   - Adds comments stating that verbatim copy occurs and no `"- Present"` stripping occurs.

2. `ProfileExtractionEngine.kt` (modified):
   - Removes the `dates`/`period` fallback from `normalizeRootKeys()`.
   - Adds unrelated deterministic-extraction changes.
   - Makes `normalizeRootKeys()` `internal` for testing.

3. `NormalizeRootKeysDateAuthorityTest.kt` (untracked, not in HEAD):
   - Directly calls `normalizeRootKeys()`.
   - Asserts that blank `startDate` stays blank when only `dates` or `period` is present.
   - Asserts that compatibility fields cannot populate or overwrite authoritative date fields.
   - Asserts that a combined range cannot become an open-ended `startDate` inflating experience.

These uncommitted files show that a different contract direction already exists in the worktree, but they are not part of the reference commit and cannot explain the CI result. They are recorded here only as evidence of contract ambiguity and drift.

### 7.3 History

- The production fallback lines are attributed to `55698de` (2026-06-15).
- The failing test was introduced in `c84e6a7` (2026-10-05) and has not been modified in reachable history.
- The test's claimed secondary production fallback has no production implementation in reachable history.
- The failures were latent while the general unfiltered CI could not reach `testDebugUnitTest`; they became visible after the CI workflow repair.

---

## 8. Intentional, accidental, or contractually ambiguous?

### Verified facts

- Production explicitly copies `dates`/`period` into `startDate` when `startDate` is blank. This is intentional code, not an accidental concatenation.
- Production does not implement the test's secondary fallback.
- The committed test is a simulation, not a production call.
- Both failing test expectations conflict with the simulation's actual output.
- For Failure 2, the expected `"Jan 2020"` is unreachable even if the secondary fallback executes.
- The worktree contains uncommitted, mutually inconsistent contract changes: one set changes the test to expect verbatim copy; another adds a test asserting compatibility fields must be ignored.

### Assessment

The **mechanical behavior is intentional**: production copies the combined string by design, and the test simulation reproduces that copy. The **test expectations are not consistent** with that behavior, and the test's description of a secondary production fallback is not supported by production source. Whether the intended contract is:

- normalize the combined range into a start value,
- copy the combined range verbatim, or
- ignore `dates`/`period` entirely,

is **contractually ambiguous** at the reference commit. The CI failure itself is not ambiguous: it is a deterministic mismatch between the committed test expectations and the committed simulation/production behavior.

---

## 9. Scope and downstream impact

- The two assertion failures are isolated to the **blank-`startDate` + nonblank-`dates`** path.
- The unparseable-`startDate` path is not failing: with `startDate="Recent"`, `dates="2018-2022"`, the primary fallback does not run, the secondary fallback extracts `"2018"`, and `endDate` remains `"Current"`.
- The existing-year path is not failing: `startDate="Jan 2020"` is preserved.
- The description-guard test passes; description mining is not involved.
- The production fallback is not limited to the two test inputs. It applies to every work entry with blank `startDate` and nonblank `dates`/`period`, and it clears `endDate` in that branch.
- Downstream W5 code can interpret the first year of a copied range as the start year and a blank end date as the current year. This can inflate experience years. This is a verified code-path consequence, not an observed CI failure in this run.
- No other CI test failure in run `42005081565` is date-related. The other 239 tests passed.

---

## 10. Evidence index

- Test source: `app/src/test/java/com/vantedge/app/data/engine/DateRecoveryPostProcessingTest.kt` at `d03d38b` (blob `9d4c45f6f37ad4d02086daa80f271e6716215c7f`).
- Production source: `app/src/main/java/com/vantedge/app/data/engine/ProfileExtractionEngine.kt` at `d03d38b` (blob `95f2e088bbe8b7456744a18fa2a772e9f8237cdd`), lines 589–590, 601, 624–631, 699–713.
- Downstream: `app/src/main/java/com/vantedge/app/w5/scoring/W5Utils.kt:69-96`; `app/src/main/java/com/vantedge/app/w5/scoring/W5Adapter.kt:87-125`; `app/src/main/java/com/vantedge/app/data/engine/CareerEngine.kt:27`.
- CI run: https://github.com/karabokeontse-png/VantEdge/actions/runs/42005081565.
- JUnit XML: `test-results/testDebugUnitTest/TEST-com.vantedge.app.data.engine.DateRecoveryPostProcessingTest.xml`.
- CI log: `/tmp/vantedge-ced01/logs/test/6_Run unit tests.txt`.
- Artifact: `test-results`, ID `13403723118`, SHA-256 `fb3d17a77c9ab18137362a0b9f95504bd5ff7a4d76b1b1d2e1b0ec3d31eee3d3`.

---

## 11. Final conclusion

**ROOT CAUSE ESTABLISHED**

Both failures are deterministically explained:

1. `dates fallback recovers year when startDate is blank` fails because the blank-`startDate` primary fallback copies `"2020-2024"` verbatim, and the secondary year-recovery guard is skipped because the copied string already contains `2020`.
2. `primary fallback uses dates field when startDate is blank` fails because the same fallback copies `"Jan 2020 - Present"` verbatim, and the test's year-only secondary fallback cannot produce the expected `"Jan 2020"` even if it ran.

The failures are not AAPT2-related and are not caused by CI infrastructure. They are deterministic test/specification mismatches. The authoritative contract for `dates`/`period` fallback is ambiguous at the reference commit; the committed test, the committed production behavior, and the uncommitted worktree changes describe three different contracts. No implementation change is proposed or performed in this read-only investigation.
