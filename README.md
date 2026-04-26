# FocusGuard

An open-source attention firewall for reclaiming agency in the age of infinite feeds.

FocusGuard helps block addictive surfaces like YouTube Shorts and Reddit Popular without blocking the useful parts of apps. It is local-first, Android-first, and built without ads, accounts, cloud sync, or default analytics.

> Modern apps removed stopping cues. FocusGuard puts them back.

## Current Status

This repository contains a production-oriented Android v0.1 implementation:

- Kotlin + Jetpack Compose Android app in `android/`
- Room local database and DataStore preferences
- Bundled Shared Boundary JSON rule packs
- Pure, tested `BoundaryEngine`
- Experimental YouTube Shorts and Reddit Popular/All surface matchers
- AccessibilityService integration that can trigger a Moment of Choice
- Local Attention Mirror event log

Surface detection is experimental because third-party app UIs change often. The architecture is designed so matchers and rule packs can be improved by the community.

## Try It On A Phone

The easiest realistic test is a physical Android phone.

1. Build the debug APK:

   ```bash
   cd android
   ./gradlew assembleDebug
   ```

2. Install it with ADB:

   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

3. Open FocusGuard and finish onboarding.
4. Go to Settings & privacy.
5. Tap Open Accessibility settings and enable FocusGuard Surface Detection.
6. Tap Open Usage Access settings and allow FocusGuard.
7. Open YouTube Shorts or Reddit Popular/All. FocusGuard should show a Moment of Choice when the bundled Essential boundary matches.

## Configure Boundaries

Open `Boundaries`, choose an app, then configure each surface independently:

- `ALLOW`: no intervention
- `WARN`: show a gentle Moment of Choice
- `INTENT GATE`: ask why you came here
- `BLOCK`: perform a back action and show a Moment of Choice

The configuration is stored in the local Room database and takes effect for Accessibility detections.

If YouTube Shorts does not trigger, open `Settings & privacy`, tap `Refresh permission and detection status`, and check:

- Accessibility is enabled
- Usage Access is enabled
- Last package shows `com.google.android.youtube`
- Last match shows `YouTube Shorts`

If Last package updates but Last match is empty, the YouTube UI on that device is exposing different Accessibility text or view IDs and the matcher needs another heuristic.

## Emulator Or Phone?

Use a physical phone for meaningful testing. Accessibility node trees and third-party app behavior are much closer to reality on a real device.

An emulator is useful for app UI development, but it is weaker for this product because YouTube, Reddit, Instagram, TikTok, and X may not behave normally without Play Services, login state, or real app installs.

## Build Checks

```bash
cd android
./gradlew testDebugUnitTest assembleDebug
```

## Privacy

FocusGuard stores rules and intervention events on-device. The AccessibilityService is used only to detect user-defined high-risk surfaces and show interventions. It does not read messages, collect passwords, record the screen, collect keystrokes, or upload Accessibility data.

## Product Direction

FocusGuard is not a generic blocker and not parental control. It is a small local-first tool that restores the pause between impulse and action.
