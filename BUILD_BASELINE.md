# VantEdge Build Baseline

## Toolchain Versions

| Component | Version |
|-----------|---------|
| Gradle | 8.4 |
| Android Gradle Plugin (AGP) | 8.2.2 |
| Kotlin | 1.9.22 |
| Kotlin Compiler Extension | 1.5.8 |
| JDK | 17.0.19 |
| compileSdk | 34 |
| targetSdk | 34 |
| minSdk | 24 |
| Java source/target | 17 |
| JVM target | 17 |
| Compose BOM | 2024.02.00 |

## Key Dependencies

| Artifact | Version |
|----------|---------|
| androidx.core:core-ktx | 1.12.0 |
| androidx.lifecycle:lifecycle-runtime-ktx | 2.7.0 |
| androidx.activity:activity-compose | 1.8.2 |
| androidx.lifecycle:lifecycle-viewmodel-compose | 2.7.0 |
| androidx.navigation:navigation-compose | 2.7.7 |
| androidx.room:room-runtime | 2.6.1 |
| androidx.room:room-compiler | 2.6.1 (kapt) |
| androidx.work:work-runtime-ktx | 2.9.0 |
| androidx.datastore:datastore-preferences | 1.0.0 |
| androidx.core:core-splashscreen | 1.0.1 |
| com.squareup.okhttp3:okhttp | 4.12.0 |
| org.jetbrains.kotlinx:kotlinx-serialization-json | 1.6.3 |
| org.jetbrains.kotlinx:kotlinx-collections-immutable | 0.3.7 |
| org.jsoup:jsoup | 1.17.2 |
| com.tom-roush:pdfbox-android | 2.0.27.0 |
| com.google.mlkit:text-recognition | 16.0.0 |
| com.google.code.gson:gson | 2.10.1 |

## gradle.properties

```properties
org.gradle.java.home=/opt/java/jdk-17.0.19+10
org.gradle.daemon=false
org.gradle.parallel=false
org.gradle.caching=false
org.gradle.configureondemand=false
org.gradle.jvmargs=-Xmx1536m -XX:MaxMetaspaceSize=384m -Dfile.encoding=UTF-8
kotlin.incremental=false
kotlin.compiler.execution.strategy=in-process
kotlin.daemon.enabled=false
android.useAndroidX=true
android.nonTransitiveRClass=true
android.nonFinalResIds=true
kapt.incremental.apt=false
kapt.use.worker.api=false
```

## Rules

1. Never change more than one build component at a time.
2. Commit every working build before experimenting.
3. Keep build configuration under version control.
4. Avoid manual Gradle edits unless solving a verified problem.
5. When a build breaks, isolate the layer: configuration → dependency resolution → compilation → packaging → runtime.
