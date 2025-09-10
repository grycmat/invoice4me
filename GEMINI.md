# Project Overview

This is a native Android application for managing invoices. It is built with Kotlin and Jetpack Compose, following the MVVM architecture pattern. The app uses Room for local data persistence and features a modern UI with Material3 design principles and a glassmorphic theme.

**Key Technologies:**

*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose with Material3
*   **Architecture:** MVVM with Repository pattern
*   **Database:** Room
*   **Navigation:** Jetpack Navigation Compose
*   **State Management:** ViewModel with StateFlow
*   **Build System:** Gradle with Kotlin DSL

# Building and Running

The project can be built and run using Android Studio or the Gradle wrapper.

**Build the project:**

```bash
./gradlew build
```

**Run the application on a connected device or emulator:**

```bash
./gradlew installDebug
./gradlew run
```

**Run tests:**

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

# Development Conventions

*   **Coding Style:** The codebase follows the standard Kotlin coding conventions.
*   **Testing:** The project includes both unit tests and instrumented tests. New features should be accompanied by corresponding tests.
*   **UI:** The UI is built with Jetpack Compose and Material3. Reusable components are located in the `app/src/main/java/com/gigapingu/invoice4me/ui/components` directory.
*   **State Management:** UI state is managed in ViewModels using `StateFlow`.
*   **Database:** The database schema is defined in the `app/src/main/java/com/gigapingu/invoice4me/model` directory. Database migrations are handled by Room.