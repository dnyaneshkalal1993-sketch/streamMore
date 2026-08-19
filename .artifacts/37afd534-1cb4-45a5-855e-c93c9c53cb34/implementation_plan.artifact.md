# Implementation Plan - Splash Screen Visual Immediacy Fix

This plan addresses the perceived "black screen" delay by ensuring visuals are rendered and visible from the very first frame of the custom splash screen.

## User Review Required

> [!IMPORTANT]
> - I will add a baseline visibility to the central red glow and floating particles so the screen is never purely black once the system splash hands off.
> - I will start the logo reveal sequence earlier in the animation timeline.
> - I will use a dark gray background (`#050505`) instead of absolute black for the first few frames to ensure the screen appears "active."

## Proposed Changes

### [Component: UI - Premium Splash]

#### [MODIFY] [PremiumSplashScreen.kt](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/kotlin/com/movie/newflix/ui/splash/PremiumSplashScreen.kt)
- **Visual Baseline**:
    - Central glow will start at `0.15` alpha instead of `0`.
    - Particles will have a minimum `0.05` alpha baseline.
- **Timeline Adjustment**:
    - Logo fade-in will start at `p > 0.05f` (~150ms) instead of `p > 0.15f` (~450ms).
    - Light sweep will trigger earlier.
- **Handoff Reliability**:
    - Ensure `onComposed` is called only when we are ready to draw the baseline visuals.

### [Component: Activity]

#### [MODIFY] [MainActivity.kt](file:///C:/Users/KP/AndroidStudioProjects/NewFlix/app/src/main/kotlin/com/movie/newflix/MainActivity.kt)
- Add a slight delay to `setKeepOnScreenCondition` to ensure the Compose engine has fully "warmed up" before removing the OS splash.

## Verification Plan

### Manual Verification
- Deploy to device.
- Verify that as soon as the system splash disappears, the red glow and particles are already visible.
- Confirm the logo appears significantly faster than before.
