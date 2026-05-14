# iqra-ai-apps

## Getting Started

This is an Android application developed with Kotlin and Jetpack Compose.

### Prerequisites

- Android SDK (API 34)
- Gradle 8.8 (use the system-installed `gradle` command instead of `./gradlew` to avoid downloading issues in restricted environments).

### Building

To build the application and generate a debug APK:

```bash
gradle assembleDebug
```

To build a release APK:

```bash
gradle assembleRelease
```

### Testing

The project is configured to run tests using Gradle. Note that tests might require an emulator or a connected device if they are instrumentation tests.

To run local unit tests:

```bash
gradle test
```

### Deployment

To deploy the app to a connected emulator or physical device via ADB:

1. Ensure your device is connected and recognized by ADB:
   ```bash
   adb devices
   ```
2. Install the generated APK (assuming you built the debug version):
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
