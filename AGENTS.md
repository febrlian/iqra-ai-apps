# Agent Instructions for iqra-ai-apps

## Tech Stack & Architecture
*   **Android App**: Developed with Kotlin and Jetpack Compose.
*   **UI Framework**: Modern Jetpack Compose Material 3 standards.
    *   Utilize `Icons.AutoMirrored` for directional icons.
    *   Use `OutlinedTextFieldDefaults.colors` for text fields.
    *   Use lambda-based `LinearProgressIndicator`.
*   **Routing**: Jetpack Navigation Compose.
*   **Local Storage**: Room (via DAOs and Entities).
*   **Dependency Injection**: Dagger Hilt.
*   **Build System**: Gradle 8.8 with KSP (Kotlin Symbol Processing).

## Build and Environment Constraints
*   **IMPORTANT:** The development environment experiences network timeouts when attempting to download Gradle distributions or resolve dependencies via Maven/Google repositories.
*   **Gradle Command:** ALWAYS use the system-installed `gradle` command instead of `./gradlew` to avoid failed attempts to download the Gradle distribution.
    *   Build debug: `gradle assembleDebug`
    *   Build release: `gradle assembleRelease`
    *   Run tests: `gradle test`

## Coding Conventions
*   **Constants**: The preferred naming convention for top-level private immutable constants in this repository is `SCREAMING_SNAKE_CASE` (e.g., `ONBOARDING_STEPS`).
*   **Performance (Compose)**: Avoid instantiating static objects or collections (like lists) within Jetpack Composables. Instead, declare them as top-level constants outside the Composable function to prevent unnecessary memory allocations during recomposition.

## Testing
*   The project contains standard Android test directories (`app/src/test` and `app/src/androidTest`) configured for unit and instrumentation testing.
*   Run local unit tests using `gradle test`. Note that instrumentation tests require an emulator or a connected device.

## Version Control
*   The `.gradle/` directory, its auto-generated cache/build state files, and patch artifacts (e.g., `.orig`, `.rej`, `patch.diff`) should NOT be tracked in version control.
