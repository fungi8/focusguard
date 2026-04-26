# FocusGuard

An open-source attention firewall for reclaiming agency in the age of infinite feeds.

FocusGuard helps people block addictive surfaces like Shorts, Reels, For You feeds, Popular pages, and infinite scroll without blocking the useful parts of apps.

No ads. No tracking. No shame. Local-first by default.

> Modern apps removed stopping cues. FocusGuard puts them back.

## v0.1 Scope

- Android-first native MVP
- Kotlin, Jetpack Compose, Room, DataStore, Coroutines, UsageStatsManager, AccessibilityService
- Local bundled Shared Boundary packs
- Pure BoundaryEngine with unit tests
- Experimental YouTube Shorts and Reddit Popular/All surface matchers
- No backend, account, analytics SDK, ads, Firebase, Supabase, or cloud sync

## Build

Install JDK 17 and Android SDK, then run:

```bash
cd android
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

## Privacy

FocusGuard stores rules and intervention events on-device. AccessibilityService is used only to detect user-defined high-risk surfaces and show interventions when a matching boundary is triggered. FocusGuard does not read messages, collect passwords, record the screen, collect keystrokes, or upload Accessibility data.

## Known Limits

Surface detection is experimental and community-maintained. Third-party apps change their UI often, so matchers may require updates over time.
