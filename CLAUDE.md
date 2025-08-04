# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Invoice4Me is an Android application written in Kotlin using Jetpack Compose.
The project follows modern Android development practices with a clean architecture approach.
The app is designed to manage invoices.
The app ui is minimalistic and intuitive, focusing on ease of use for managing invoices.
It includes features such as invoice creation, editing, and viewing.
The application ui is build with Glassmorphism and Material3 design principles.
It supports both light and dark themes, dynamic color, and edge-to-edge display.
The application is built with a single Activity architecture, leveraging Jetpack Compose for UI development.
It includes unit tests and instrumented tests to ensure code quality and functionality.


## Key Technologies & Architecture

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: 34 (Android 14)
- **Target SDK**: 36
- **Package**: `com.gigapingu.invoice4me`

## Build Commands

### Development
```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install debug build on connected device
./gradlew installDebug
```

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run specific test class
./gradlew test --tests "com.gigapingu.invoice4me.ExampleUnitTest"

# Run instrumented test class
./gradlew connectedAndroidTest --tests "com.gigapingu.invoice4me.ExampleInstrumentedTest"
```

### Code Quality
```bash
# Run lint checks
./gradlew lint

# Generate lint report
./gradlew lintDebug
```

## Project Structure

- **app/src/main/java/com/gigapingu/invoice4me/** - Main application code
  - `MainActivity.kt` - Entry point activity using Compose
  - `ui/theme/` - Theme definitions (Theme.kt, Color.kt, Type.kt)
- **app/src/test/** - Unit tests
- **app/src/androidTest/** - Instrumented tests
- **gradle/libs.versions.toml** - Version catalog for dependencies

## Key Architecture Notes

- Uses single Activity architecture with Jetpack Compose
- Follows Material3 design system with dynamic color support (Android 12+)
- Theme supports both light and dark modes with system detection
- Uses Glassmorphism design principles for UI elements
- Implements MVVM pattern for separation of concerns

## Development Environment

- **Java Version**: 11 (both source and target compatibility)
- **Kotlin Version**: 2.0.21
- **Android Gradle Plugin**: 8.13.0-alpha03
- **Compose BOM**: 2024.09.00

## Testing Framework

- **Unit Tests**: JUnit 4.13.2
- **Android Tests**: AndroidX Test with Espresso
- **Compose Tests**: UI testing with Compose test framework