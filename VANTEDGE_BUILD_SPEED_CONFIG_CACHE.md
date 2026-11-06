# VANTEDGE — BUILD SPEED: GRADLE CONFIGURATION CACHE + BUILD CACHE

Baseline HEAD: 56093227307f5b4ab7d7511e14541fa3d496fc78

## Change (project gradle.properties)
```diff
diff --git a/gradle.properties b/gradle.properties
index 8ffd42a..b115a74 100644
--- a/gradle.properties
+++ b/gradle.properties
@@ -1,8 +1,12 @@
 org.gradle.daemon=false
 org.gradle.parallel=false
-org.gradle.caching=false
+org.gradle.caching=true
 org.gradle.configureondemand=false
 
+# Speed: reuse task graph across invocations (safe rollout: problems=warn)
+org.gradle.configuration-cache=true
+org.gradle.configuration-cache.problems=warn
+
 org.gradle.jvmargs=-Xmx1536m -XX:MaxMetaspaceSize=384m -Dfile.encoding=UTF-8
 
 kotlin.incremental=false
```

## Resulting settings (gradle.properties)
```
1:org.gradle.daemon=false
2:org.gradle.parallel=false
3:org.gradle.caching=true
4:org.gradle.configureondemand=false
7:org.gradle.configuration-cache=true
8:org.gradle.configuration-cache.problems=warn
12:kotlin.incremental=false
16:kotlin.incremental=false
```

## Verification (raw Gradle output)
```
# distinct task graph -> stored
$ gradle :app:compileDebugKotlin --offline -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2
Calculating task graph as no cached configuration is available for tasks: :app:compileDebugKotlin
> Task :app:compileDebugKotlin UP-TO-DATE
BUILD SUCCESSFUL in 3m 5s
Configuration cache entry stored.

# identical invocation -> reused
$ gradle :app:compileDebugKotlin --offline -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2
Reusing configuration cache.
BUILD SUCCESSFUL in 1m 59s
Configuration cache entry reused.

# help task also reused
$ gradle help --offline ...
Reusing configuration cache.
BUILD SUCCESSFUL in 1m 32s
Configuration cache entry reused.
```

## What was enabled
- `org.gradle.configuration-cache=true` — reuses the configured task graph across
  invocations instead of re-running the (slow) configuration phase each time.
- `org.gradle.configuration-cache.problems=warn` — safe rollout: if any task is not
  configuration-cache compatible it warns instead of hard-failing.
- `org.gradle.caching=true` — enables the Gradle build cache (task outputs reused
  across builds/clean builds).

## Notes / remaining levers
- The dominant cost remains Kotlin compilation. `kotlin.incremental=false` (set in
  gradle.properties) forces a FULL recompile of all Kotlin sources whenever any main
  source changes (~8-9 min). Setting `kotlin.incremental=true` is the single biggest
  speed lever for edit→build cycles, but it was deliberately disabled in this project,
  so it is NOT changed here without separate authorization.
- `org.gradle.daemon=false`: leaving as-is. In this sandbox each Gradle client runs
  under a proot that kills its process tree on exit, so a resident daemon would not
  survive between commands anyway.
- Configuration cache is stored under the project `.gradle/configuration-cache`.
  It is invalidated automatically when build scripts, properties, or the command line
  change (e.g., a different `-P...` set), then re-stored on the next run.

## UNVERIFIED
- Full `assembleDebug`/test cycles under configuration cache were not re-run end-to-end
  in this session after enabling it (the previously built APK predates this change).
  Configuration phase reuse itself is verified above. `problems=warn` prevents hard
  failures if any task is config-cache incompatible.
