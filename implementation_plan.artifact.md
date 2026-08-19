# Fix: Unable to instantiate application NewFlixApp

The application fails to start with a `ClassNotFoundException` for `com.movie.newflix.NewFlixApp`. This is likely due to a namespace conflict between the `:app` and `:shared` modules, which both use the same namespace `com.movie.newflix`. This causes issues during the build and manifest merging process, leading to the application class not being correctly identified or included in the final DEX.

## Proposed Changes

### 1. Update `:shared` module namespace
Change the namespace of the `:shared` module to `com.movie.newflix.shared` to avoid conflicts with the `:app` module.

#### [MODIFY] [shared/build.gradle.kts](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/shared/build.gradle.kts)
- Change `android.namespace` from `com.movie.newflix` to `com.movie.newflix.shared`.

### 2. Organize source code (Optional but Recommended)
Move Kotlin files from `src/main/java` to `src/main/kotlin` in the `:app` module to follow standard Kotlin project structure.

#### [NEW] `app/src/main/kotlin/com/movie/newflix/`
#### [MOVE] [NewFlixApp.kt](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/java/com/movie/newflix/NewFlixApp.kt) to `app/src/main/kotlin/com/movie/newflix/NewFlixApp.kt`
#### [MOVE] [MainActivity.kt](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/java/com/movie/newflix/MainActivity.kt) to `app/src/main/kotlin/com/movie/newflix/MainActivity.kt`

## Verification Plan

### Automated Tests
- Run `:app:assembleDebug` to ensure the project builds correctly with the new namespace.

### Manual Verification
- Deploy the app to a device/emulator and verify that it starts without the `Unable to instantiate application` error.
