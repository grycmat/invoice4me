# Repository Guidelines

## Project Structure & Module Organization
Invoice4Me is a single-module Android app under pp/. Core Kotlin sources live in pp/src/main/java/com/gigapingu/invoice4me, organized by layer: data/ for Room setup and repositories, model/ for entities, ui/ for Compose screens/components/navigation, and utils/ for helpers. Resources stay in pp/src/main/res with layouts, drawables, and theming tokens. Unit tests belong in pp/src/test/java, while instrumentation tests use pp/src/androidTest/java.

## Build, Test, and Development Commands
Run ./gradlew assembleDebug to compile a debuggable APK for local installs. Execute ./gradlew test for JVM unit tests and ./gradlew connectedAndroidTest on a booted emulator for Espresso/Compose UI coverage. Use ./gradlew lint before reviews to surface Android lint warnings, and ./gradlew clean if Gradle caching causes unexpected build artefacts. Android Studio users should sync Gradle after dependency edits.

## Coding Style & Naming Conventions
Follow the Kotlin coding conventions: four-space indentation, trailing commas where Kotlin allows, and expressive camelCase for variables and functions. Compose UI functions stay PascalCase (DashboardScreen), while preview composables append Preview. ViewModels end with ViewModel, Room DAO interfaces end with Dao, and modifiers live in modifier/ extensions. Prefer immutable al and StateFlow-based state holders. Reformat files with Android Studio’s Code > Reformat Code to keep imports and spacing consistent.

## Testing Guidelines
Unit tests use JUnit4 and Mockito-style assertions in pp/src/test. Name test classes after the subject (InvoiceRepositoryTest) and methods with should... phrasing for readability. Instrumented tests rely on Espresso and Compose test APIs; ensure a device running API 26+ is connected before invoking connectedAndroidTest. Aim to cover repository logic and primary UI flows for any new feature, and update sample data when seed content changes to keep assertions stable.

## Commit & Pull Request Guidelines
Commits should be concise, present-tense commands (Fix gradient background theme switching). Group related changes and avoid mixing refactors with behavioural tweaks. Pull requests need a clear summary, validation notes (build/tests), and screenshots or screen recordings when UI changes are visible. Reference Jira or GitHub issues with Fixes #123 where applicable, and wait for at least one review before merging.
