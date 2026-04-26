# Contributing

FocusGuard needs careful Android engineering, clear privacy language, and community-maintained surface rules.

Useful contribution areas:

- Improve Accessibility surface matchers
- Add or refine Shared Boundary rule packs
- Test third-party app UI changes
- Improve privacy and Android permission documentation
- Polish Compose screens while keeping the product calm and non-punitive

Run checks before opening changes:

```bash
cd android
./gradlew testDebugUnitTest assembleDebug
```
