# VANTEDGE — DEBUG SIGNING CONTINUITY RESTORATION — EVIDENCE REPORT

**Directive:** RESTORE VANTEDGE ANDROID DEBUG SIGNING CONTINUITY
**Package:** com.vantedge.app
**Baseline HEAD:** 56093227307f5b4ab7d7511e14541fa3d496fc78
**Date:** 2026-10-16

## OUTCOME (summary)
- The known original debug keystore WAS located — inside the archive
  `/storage/emulated/0/Download/7th export/VantEdge3.zip`, member
  `VantEdge3.0/VantEdge-original-debug.keystore`.
- Its certificate SHA-256 matches the known original exactly.
- It was installed as the IDE's effective debug keystore (existing debug
  keystore preserved first).
- The debug APK was rebuilt and re-signed.
- The rebuilt APK's signing certificate MATCHES the known original.
- Package ID remains `com.vantedge.app`.
- **Installation was NOT tested** (no device/ADB interaction available), so the
  install-time conflict resolution is NOT independently confirmed here.

## 1. ENVIRONMENT IDENTIFICATION
- Build shell HOME (guest proot): `HOME=/root`, uid=0(root).
- Effective debug keystore path (authoritative, from `gradle :app:signingReport`):
  `/data/user/0/ai.opencode.app/files/home/.config/.android/debug.keystore`
- Debug key alias: `AndroidDebugKey` (case-insensitive match to `androiddebugkey`)
- Signing config: none declared in `app/build.gradle`; AGP default debug signing is used.
- SDK dir: `/root/android-sdk` (local.properties sdk.dir=/root/android-sdk)
- aapt2 override: `/opt/box64/wrap/aapt2` (box64 wrapper)

## 2. KEYSTORE CANDIDATES AND FINGERPRINTS
Known original target SHA-256:
`3F:E5:79:67:C1:5F:81:EE:39:EA:2B:9B:88:DD:96:A7:50:6C:1B:5B:90:32:60:6E:CB:EA:04:BB:DB:15:EE:3C`

| Path | SHA-256 | Matches original? |
|---|---|---|
| /data/user/0/ai.opencode.app/files/home/.config/.android/debug.keystore (BEFORE restore) | 64:0C:B5:D3:47:A2:89:0B:14:61:49:33:0E:F5:02:EB:3D:78:66:E2:6C:E0:E0:56:33:D8:31:68:29:53:DC:56 | NO |
| /root/opencode-android/keystore/debug.keystore | A8:95:AC:0F:D6:61:9A:7D:DF:E3:BA:CD:0C:5A:31:D4:41:93:51:35:07:49:97:98:B2:48:AE:9C:D2:6D:1B:34 | NO |
| /root/keystores/VantEdge-original-debug.keystore (extracted from archive) | 3F:E5:79:67:C1:5F:81:EE:39:EA:2B:9B:88:DD:96:A7:50:6C:1B:5B:90:32:60:6E:CB:EA:04:BB:DB:15:EE:3C | YES |
| /root/.android/debug.keystore (installed copy) | 3F:E5:79:67:C1:5F:81:EE:39:EA:2B:9B:88:DD:96:A7:50:6C:1B:5B:90:32:60:6E:CB:EA:04:BB:DB:15:EE:3C | YES |
| /data/user/0/ai.opencode.app/files/home/.config/.android/debug.keystore (AFTER restore) | 3F:E5:79:67:C1:5F:81:EE:39:EA:2B:9B:88:DD:96:A7:50:6C:1B:5B:90:32:60:6E:CB:EA:04:BB:DB:15:EE:3C | YES |

Archive member found: `VantEdge3.0/VantEdge-original-debug.keystore` (2106 bytes, 2026-09-24)
inside `/storage/emulated/0/Download/7th export/VantEdge3.zip`.
Durable copy made at `/root/keystores/VantEdge-original-debug.keystore`
(sha256 56231e38bea93848b72222d5662345a7c04fc5844f7a76a4b8ff19637bec4adc).

## 3. SEARCH LOCATIONS CHECKED
- /root/.android/ (no debug.keystore present)
- /data/user/0/ai.opencode.app/files/home/.android/ (does not exist)
- /root, /tmp, /data/user/0/ai.opencode.app/files/home, /data/user/0/ai.opencode.app/files/projects (maxdepth 6, *.keystore/*.jks/*cogo*/*original*)
- /data/user/0/ai.opencode.app (maxdepth 6)
- /root/android-sdk, /opt, /data/local/tmp, /sdcard, /storage/emulated/0 (maxdepth 4)
- Project roots (/root/vantedge-canonical, /data/.../projects/Vantedge): no keystore
- Git history: no keystore committed
- Archives: found the original inside /storage/emulated/0/Download/7th export/VantEdge3.zip
- grep for the fingerprint string across /root and /data/.../files: not present in any readable text file

Result of search: exactly TWO standalone debug keystores existed (both NON-matching);
the matching original existed only INSIDE the archive named above.

## 4. ACTIONS TAKEN (with preservation)
1. Backed up the previous (non-matching) effective debug keystore:
   `/data/user/0/ai.opencode.app/files/home/.config/.android/debug.keystore.pre-vantedge-restore`
2. Installed the matching original as the effective debug keystore:
   `/data/user/0/ai.opencode.app/files/home/.config/.android/debug.keystore`
3. Also installed as canonical `/root/.android/debug.keystore`.
4. Kept durable non-repo copy `/root/keystores/VantEdge-original-debug.keystore`.
No repository/Git changes; keystore not committed; no hard-coded path added to shared project config.

## 5. BUILD EVIDENCE
Command (IDE's Gradle workflow in this environment):
```
/opt/gradle-9.6.1/bin/gradle :app:assembleDebug --offline -Pandroid.aapt2FromMavenOverride=/opt/box64/wrap/aapt2
```
Result:
```
BUILD SUCCESSFUL in 2m 53s   (40 actionable tasks: 1 executed, 39 up-to-date)
> Task :app:packageDebug   (executed — APK re-signed)
```
APK:
```
path   : app/build/outputs/apk/debug/app-debug.apk
size   : 70094299 bytes
sha256 : af6d757af21f94a0aeaebbd7d6a673efd27c96c2b9cbccd9fb2fae42104ef4ef
```

## 6. VERIFICATION
APK signing certificate (apksigner verify --print-certs):
```
Signer #1 certificate DN: C=US, O=Android, CN=Android Debug
Signer #1 certificate SHA-256 digest: 3fe57967c15f81ee39ea2b9b88dd96a7506c1b5b9032606ecbea04bbdb15ee3c
```
Known original: `3F:E5:79:67:C1:5F:81:EE:39:EA:2B:9B:88:DD:96:A7:50:6C:1B:5B:90:32:60:6E:CB:EA:04:BB:DB:15:EE:3C`
=> **MATCH** (colons removed for apksigner output format).

Package ID (aapt2 dump badging):
```
package: name='com.vantedge.app' versionCode='1' versionName='1.0' ... compileSdkVersion='34'
```
=> **com.vantedge.app retained**

Effective debug keystore (keytool -list -v):
```
Alias name: androiddebugkey
SHA256: 3F:E5:79:67:C1:5F:81:EE:39:EA:2B:9B:88:DD:96:A7:50:6C:1B:5B:90:32:60:6E:CB:EA:04:BB:DB:15:EE:3C
```
=> **MATCH**

APK copies written to Downloads:
- /storage/emulated/0/Download/app-debug.apk
- /storage/emulated/0/Download/vantedge/app-debug.apk
(identical sha256 to the build output)

## 7. PROHIBITED-ACTION COMPLIANCE
- Existing VantEdge app NOT uninstalled.
- Package ID NOT changed.
- No NEW debug keystore generated (the ORIGINAL was restored).
- No keystore / key / password / secret committed to Git.
- No unrelated source, dependency, app logic, or release-signing config changed.

## 8. REMAINING / CAVEATS
- **Installation NOT tested** — no ADB/device interaction was performed, so the
  actual `adb install -r` update over the existing install is NOT confirmed.
- Replacing the IDE-global debug keystore changes the debug signer for ALL debug
  builds in this environment; the previous keystore is preserved at
  `.pre-vantedge-restore` for reverting.
- The original certificate uses SHA1withRSA (weak); this is inherent to the
  original and required for signing continuity.
- Keystore password is the standard Android debug default and is not disclosed here.

## MANDATORY CLOSING STATEMENT
No unauthorized source modifications, test-suite modifications, subsystem deletions,
or repository pushes were made. The existing application was not uninstalled, the
package ID was not changed, and no replacement key was generated; the original debug
signing certificate was restored from the recovered keystore. The keystore was not
committed to Git. Installation success over the existing app is marked UNVERIFIED
because no device installation was performed.
