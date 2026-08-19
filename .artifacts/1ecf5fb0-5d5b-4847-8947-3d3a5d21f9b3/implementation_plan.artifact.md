# Implementation Plan - Modern Splash Screen

This plan updates the app to use the modern Android SplashScreen API and adds a new custom splash icon.

## Proposed Changes

### 1. Build Configuration
#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/build.gradle.kts)
- Add `androidx.core:core-splashscreen` dependency.

### 2. Assets
#### [NEW] [ic_splash.xml](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/res/drawable/ic_splash.xml)
- Create a new vector icon for the splash screen (a stylized "Cine Sphere" logo).

### 3. Themes
#### [MODIFY] [themes.xml](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/res/values/themes.xml)
- Define `Theme.App.Starting` inheriting from `Theme.SplashScreen`.
- Set `windowSplashScreenAnimatedIcon` to `@drawable/ic_splash`.
- Set `postSplashScreenTheme` to `@style/Theme.NewFlix`.

### 4. Manifest
#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/AndroidManifest.xml)
- Update `MainActivity` to use `android:theme="@style/Theme.App.Starting"`.

### 5. Activity
#### [MODIFY] [MainActivity.kt](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/kotlin/com/movie/newflix/MainActivity.kt)
- Call `installSplashScreen()` before `super.onCreate()`.

## Verification Plan
### Automated Tests
- Build the app: `./gradlew :app:assembleDebug`

### Manual Verification
- Launch the app and verify the new splash icon appears.
- Ensure the app transitions smoothly to the Home screen after the splash.
