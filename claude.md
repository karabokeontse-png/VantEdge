# VantEdge Project Guide

## Tech Stack
- **IDE:** AndroidIDE (Mobile)
- **Language:** Kotlin / Jetpack Compose
- **UI:** Material 3
- **JDK:** 21 (Forced via settings)
- **Build System:** Gradle 8.2.2

## Project Structure
- `app/src/main/java/com/vantedge/app/data/`: All logic engines (ScoreEngine, AtsEngine).
- `app/src/main/java/com/vantedge/app/ui/screens/`: All UI screen files.
- `app/src/main/res/`: Assets and Themes.

## Coding Rules
- **Full Files Only:** Always provide the complete code for a file when requested.
- **Specific Imports:** Use explicit imports (e.g., `import androidx.compose.runtime.Composable`)—no wildcards (`.*`).
- **Domain Focus:** This is an IT-career focused ATS scanner and optimizer.
- **Packages:** All new data/logic files must use `package com.vantedge.app.data`.

## Build Commands
- Built via the AndroidIDE "Play" button.
- If "Java 8" errors appear, the user will handle the JDK config menu.
