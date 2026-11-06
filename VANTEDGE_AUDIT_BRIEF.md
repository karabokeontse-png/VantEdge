# VantEdge Audit — Briefing (read this first)

> **Full audit:** `VANTEDGE_TECHNICAL_AUDIT.md` (2,980 lines, ~140 KB, ~35k tokens).
> **This brief:** the same 46 P0/P1 findings as one line each (~3k tokens, ~90% cheaper).
> **Read this first. Only open the full doc for the findings you actually work on.**

| | |
|---|---|
| **Repo** | karabokeontse-png/VantEdge |
| **Branch / HEAD** | `verification/ced-p0-authority` / `5609322` |
| **Scope** | 8 subsystems, 234 Kotlin files, 23,624 LOC |
| **Totals** | **145 findings — 15 P0, 53 P1, 55 P2, 22 P3** |
| **Audit date** | 2026-10-15 |

## Verdict (one paragraph)

The deterministic-authority pivot (W5 replacing the LLM as the scorer) is **structurally sound and worth keeping** — but the three commits that were supposed to make it *work* are, in production, **inert**. `JobExtractionOrchestrator` extracts structured requirements, validates them, then drops them at a function boundary (`A3`), so the adapter always takes the fallback path. The job title is hardcoded to `""` (`A1`), so the `RoleRelevance` axis is identically `0.0` and **the maximum achievable score is 83 while the UI advertises "Strong match" at 80** — the product shows users a number it cannot produce. Two live PII exposures (`A8`, `A9`) and a plaintext API key (`A13`) must ship-block. **No CI workflow runs on this branch (`A44`), so nothing here is currently checked.**

## P0 — merge-blocking (13)

| # | Finding | Location |
|---|---|---|
| **A1** | `RoleRelevance` axis is identically `0.0` — 15 % of the score is dead and **max achievable score is 83** | `JobRequirementAdapter.kt:120` |
| **A2** | `ConstraintCompliance` is a tautology (`isAccepted && isAccepted`, both hardcoded `true`) and is double-counted; `vacancyScore` gets a free constant +20 | `AxisEvaluators.kt:62-64` |
| **A3** | P0-A is **inert**: structured requirements are extracted, validated, then dropped at the orchestrator boundary. `DESCRIPTION_FALLBACK` is 100 % of production traffic | `JobExtractionOrchestrator.kt:120-128, 220-251` |
| **A4** | `ExtractionDecision` is computed, exhaustively matched, and then **ignored** — `Failure` still yields `Accepted` | `JobExtractionOrchestrator.kt:91-128` |
| **A5** | `ProfileSanitizer` silently rewrites valid skills: `REST`→`rust`, `CI`/`CD`→`c#`, `Redux`→`redis` | `ProfileSanitizer.kt:96-115` |
| **A6** | `DocumentPreprocessor` destroys legitimate words: `Client`→`Dient`, `Ireland`→`lreland`, `learn`→`leam` | `DocumentPreprocessor.kt:69-78` |
| **A7** | Deterministic W5 is **sequentially gated behind a 120 s AI call**; an AI transport exception is still fatal | `OptimizationOrchestrator.kt:222-298` |
| **A8** | Full PII (model output = the candidate's CV) written to logcat; also written and then **discarded** | `AiGateway.kt:144, GeneratorEngine.kt:172` |
| **A9** | `allowBackup="true"` with no backup rules exfiltrates the entire CV database + profile to Google Drive | `AndroidManifest.xml:11` |
| **A10** | `inputSnapshotHash` is hardcoded `""` — the observability subsystem's core reproducibility claim is not implemented | `ObservabilityTraceModel.kt` |
| **A11** | `P2VerificationHarnessTest` fabricates the rule set by reflection and mislabels it as `validation_rules.json`; T02/T03 assert behaviour the product does not have | `P2VerificationHarnessTest.kt:91-111` |
| **A12** | `cv_design` stub discards the selected design — `applyDesign` is unreachable from the UI | `Navigation.kt / CVDesignScreen.kt` |
| **A13** | OpenRouter key is a plaintext constant compiled into the APK; `buildConfigField` is fed an unquoted value so the key path has never actually built | `build.gradle:29-31` |

## P1 — fix this cycle (33)

| # | Finding | Location |
|---|---|---|
| **A14** | Unknown seniority string ⇒ `seniorityFit = 0.0` — "Staff Engineer" scores zero on seniority fit | `AxisEvaluators.kt:51, W5Utils.kt:134-136` |
| **A15** | Unknown years requirement ⇒ `experienceAlignment = 0.0` (20 % of score) rather than neutral | `AxisEvaluators.kt:17` |
| **A16** | Wall-clock year is a scoring input — the same CV scores differently on 1 Jan vs 31 Dec | `DeterministicCompatibilityEngine.kt:36` |
| **A17** | 12 sites use locale-default `lowercase()`; on a Turkish-locale device Gate 0 rejects valid CVs and skills drop out of the taxonomy | `12 sites, §4.5` |
| **A18** | `W5Utils.tokenize` strips all non-ASCII — a non-Latin CV scores 0 on `skillMatch` *and* `roleRelevance` (40 %) | `W5Utils.kt:9` |
| **A19** | Every W5 test runs against **fabricated assets**; the test `keyword_dictionary` is structurally different from production | `W5ScoreEngineTest.kt:16-29` |
| **A20** | 4 tests are literally `assertTrue("...", true)`; 2 of them are not disclosed in the class KDoc | `P2VerificationHarnessTest.kt:453,673,689,737` |
| **A21** | `WorkManagerInitializer` removed from the manifest with no `Configuration.Provider` — `WorkManager.getInstance()` **always throws**; deadline reminders can never fire | `AndroidManifest.xml:38-42` |
| **A22** | `TelemetryCollector` writes `cacheDir/telemetry.log`; `TelemetryStorage` reads `filesDir/telemetry.log` — telemetry is **100 % non-functional** | `TelemetryCollector.kt:26 vs TelemetryStorage.kt:19` |
| **A23** | Telemetry export is broken by a **FileProvider authority mismatch** (`.fileprovider` vs `.provider`) and fails silently | `TelemetryExportService.kt:23` |
| **A24** | One-shot navigation driven by `LaunchedEffect(uiState)` with no reset ⇒ **user is trapped in a navigation loop** | `CompatibilityInputScreen.kt:97-101` |
| **A25** | `currentCycle` is a plain `get()` read inside a `@Composable` — no snapshot subscription ⇒ permanently blank screen | `Navigation.kt:389-398` |
| **A26** | `cv_docx` routes into the JSON extractor while its prompt demands plain prose ⇒ **DOCX generation fails 100 % of the time** | `GeminiService.kt:944` |
| **A27** | Budget timeout cannot interrupt an in-flight attempt; `AiBudgetPolicy` is **dead code** — all 6 call sites hardcode `120_000L` | `GeminiService.kt:199-221` |
| **A28** | 429 ignores `Retry-After`; backoff is a flat 1 s with no jitter (`index` can never exceed 1) | `GeminiService.kt:117-129` |
| **A29** | `substring_scan` returns the **largest** parsable JSON block as success — an injection-controllable "fail to success" path | `JsonExtractionEngine.kt:117-127` |
| **A30** | `**bold**`/`*italic*` regex corrupts model output inside JSON string values — and the `cv_docx` prompt *asks for* `**bold**` | `JsonExtractionEngine.kt:74-75` |
| **A31** | Cover letter bypasses the entire extraction/validation pipeline — raw unvalidated model text is persisted | `GeneratorEngine.kt:163-174` |
| **A32** | `GeneratorEngine` swallows a P2 `Reject` and returns `Success` | `GeneratorEngine.kt:85-98` |
| **A33** | `CORRECT` enforcement action corrects nothing (identical branch to `ANNOTATE`); `REGENERATE` is unreachable dead code | `OptimizationOrchestrator.kt:330-372` |
| **A34** | Fatal re-throw on `matchedKeywords` parse aborts the whole pipeline and discards a successful analysis | `OptimizationOrchestrator.kt:149` |
| **A35** | `ExportSchema = false`, no schema JSONs, no `1→2` migration and no destructive fallback ⇒ any future forgotten migration **bricks the app** | `VantEdgeDatabase.kt:11-15` |
| **A36** | `OnboardingDraftStore.saveDraft` is documented "ATOMIC WRITE" and performs two separate transactions | `OnboardingDraftStore.kt:16-35` |
| **A37** | `CycleViewModel.init` reads a `StateFlow` that has not emitted ⇒ **cycles are never restored on cold start**; also corrupts the version counter | `CycleViewModel.kt:40-44` |
| **A38** | Export fails silently on API 24–28 (no `WRITE_EXTERNAL_STORAGE`, no runtime request); defect duplicated into 3 live paths | `MediaStoreExporter.kt:26-31 + 2 screens` |
| **A39** | Entire object graph (14 services incl. `GeminiService` and a never-cancelled `CoroutineScope`) is constructed with `remember {}` inside a `@Composable` | `Navigation.kt:99-142` |
| **A40** | Form input in `remember` not `rememberSaveable` — rotation destroys the user's work on 5 screens | `EditProfileScreen.kt:45-66 + 4` |
| **A41** | WebView reloaded on **every** recomposition via `AndroidView`'s `update` lambda — a reload loop that destroys scroll/zoom | `ResultScreen.kt:258-281` |
| **A42** | `GeneratorViewModel.generate` uses a blocking `CountDownLatch` on a coroutine dispatcher + unsynchronised captured `var`s + no re-entrancy guard | `GeneratorViewModel.kt:49-92` |
| **A43** | `Room` schema advanced 2→3 **without a `versionCode` bump** (`versionCode` is still 1) | `build.gradle:26-27` |
| **A44** | No CI workflow triggers on `verification/**` — **the three P0 commits have run no workflow at all** | `.github/workflows/.yml` |
| **A45** | `ci.yml` is the only workflow that runs pipeline tests and it fails on a clean checkout (committed absolute `aapt2` path, no `sed` repair) | `gradle.properties:22` |
| **A46** | `h3-corrupted-ocr-test.yml` targets a class that exists only as a `.kt.bak` ⇒ permanently broken | `h3-corrupted-ocr-test.yml:29` |

## Open decisions — need a human call before the fix (7)

These are **not** defects anyone can fix unilaterally; each one currently has code answering a question nobody asked on purpose.

1. **Is the compatibility score comparable across candidates, or a per-user coaching signal?** Gates the `A1`/`A2`/threshold fixes together.
2. **Missing `requiredYears`: neutral or penalising?** Currently penalising at 20% of the score — a fairness call.
3. **Is the AI narrative advisory or load-bearing?** Determines whether `A7` is "run concurrently" or "delete the narrative".
4. **`ContractValidator`: hard gate or advisory?** If gate, five bypass paths are P0; if advisory, delete it.
5. **Cloud-backup CV data: yes or no?** Safe default is no.
6. **W6 engine: wire it or delete it?** 420 lines of tests, zero callers.
7. **Model tier:** both configured models are `:free`; commits `70ef4ee`/`29bd88a`/`77be059` are three workarounds for free-tier token exhaustion. Paid models make several P1s disappear.

## Remediation tracks (4, ordered) — full detail in `AUDIT_2026-10-15.md` §13

| Track | Focus | Items |
|---|---|---|
| **1. Product is lying to users** | `A1` `A3` `A4` `A5` `A6` — score authority + P0-A completion | 8 |
| **2. Security & privacy** | `A8` `A9` `A13` — PII, backup, key triage | 8 |
| **3. Build, CI, tests** | `A19` `A20` `A44` `A45` `A46` — make anything verifiable | 12 |
| **4. Structural cleanup** | `A39` `A12` `A24`–`A42` — DI, i18n, dead code | 40 |

**Order matters:** Track 3 first (half a day — it is the only thing that makes fixes verifiable), then 1+2, then 4. Two traps: the `A1` fix changes the score's numeric range (existing `CompatibilityRecord`s become non-comparable → a migration decision), and `A3` has already shipped broken once (needs a `STRUCTURED` vs `DESCRIPTION_FALLBACK` regression test).

## The one caveat you must not skip

**No test was executed during this audit.** The sandbox is `aarch64`; the Android SDK's `aapt2` is x86-64, so `testDebugUnitTest` cannot run. Main-source compilation and Room codegen *are* verified; **every test finding is static analysis.** Running the suite is Track 3, item 1.

## How to use this cheaply

- **Just the list?** Load `VANTEDGE_AUDIT_INDEX.json` — one object per finding (`id`, `severity`, `finding`, `location`).
- **Deep-dive a finding?** `grep -n 'A3' VANTEDGE_TECHNICAL_AUDIT.md` — every finding has a verbatim code quote and a fix.
- **Full context for one area?** Read only that section: §4 scoring, §5 orchestration, §6 domain/extraction, §7 contracts, §8 network/secrets, §9 storage/PII, §10 UI, §11 tests/CI, §12 docs, §13 tracks, §14 decisions, §15 self-assessment.
- **4 collaborators?** Split by track. Each reads this brief + their track's rows + greps the full doc for their IDs.

*Generated from `VANTEDGE_TECHNICAL_AUDIT.md` — never edit by hand; regenerate.*
