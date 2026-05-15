# Agent Skills & Directives Improvement Spec

## Current State Analysis

### What's Good
*   **Clear Tech Stack Identification:** The current memory successfully identifies the core technologies: Kotlin, Jetpack Compose, Material 3, Room, Hilt, Navigation Compose, and KSP.
*   **Crucial Environment Constraints Known:** The memory explicitly states the network timeout issue with downloading Gradle distributions and dependencies, and provides a working workaround (using system `gradle` over `./gradlew`).
*   **Specific UI Framework Guidelines:** Includes detailed Jetpack Compose Material 3 standards (e.g., `Icons.AutoMirrored`, `OutlinedTextFieldDefaults.colors`, lambda-based `LinearProgressIndicator`).
*   **Performance Awareness:** Contains specific instructions regarding Compose performance (avoiding static object/collection instantiation inside Composables).
*   **Coding Conventions Mentioned:** Specifies naming conventions (`SCREAMING_SNAKE_CASE` for constants).

### What's Missing
*   **Directory Structure Documentation:** Lack of explicit documentation on where specific components (ViewModels, DAOs, Screens) should be located within the `app/` module.
*   **State Management Patterns:** While MVI StateFlow-based ViewModels are mentioned in `tasks.md`, there's no explicit guideline in memory or standard agent files on *how* to implement this pattern within this project (e.g., naming of Intents/Events, State classes).
*   **Dependency Injection Rules:** No specific guidelines on how to structure Hilt modules or where to place them.
*   **Centralized Agent Directives:** Prior to this session, there was no `AGENTS.md` or `.cursor/rules/` directory to persistently store these constraints for future AI sessions, relying entirely on ephemeral system memory.

### What's Wrong
*   **Reliance on System Memory:** Relying on system memory for critical build constraints (like not using `./gradlew`) is error-prone. This needs to be formalized in `AGENTS.md`.
*   **Lack of Tool-Specific Directives:** No rules for IDE specific agents (Cursor, Ona) were configured.

## Concrete Improvement Specification

1.  **Consolidate and Persist Directives (Implemented):**
    *   Create `AGENTS.md` at the project root. (Done)
    *   Migrate all memory constraints regarding `gradle` usage, Jetpack Compose performance, and naming conventions into `AGENTS.md`. (Done)

2.  **Define Architectural Guidelines (To Be Added to AGENTS.md in future iterations):**
    *   *Action:* Add a section in `AGENTS.md` detailing the MVI pattern implementation.
    *   *Detail:* Specify that ViewModels should expose a single `StateFlow` for UI state and accept a flow/channel of UI Events (Intents).

3.  **Define Project Structure Guidelines (To Be Added to AGENTS.md):**
    *   *Action:* Outline the expected package structure.
    *   *Detail:* E.g., `com.example.iqra.ui`, `com.example.iqra.data.local`, `com.example.iqra.di`.

4.  **Create IDE-Specific Rule Files (Future Recommendation):**
    *   *Action:* Create `.cursor/rules/android-compose.mdc` and `.cursor/rules/build-constraints.mdc`.
    *   *Detail:* Map the rules from `AGENTS.md` into format-specific files for tooling that supports them.

5.  **Expand Testing Guidelines (To Be Added to AGENTS.md):**
    *   *Action:* Add specific instructions on testing Room DAOs (using in-memory DBs) and ViewModels (using Turbine or standard coroutine test dispatchers).
