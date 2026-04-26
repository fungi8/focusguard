# Architecture

FocusGuard v0.1 is Android-first, local-first, and backend-free.

The policy flow is:

```text
SurfaceContext -> SurfaceMatcher -> BoundaryEngine -> InterventionDecision -> UI / Service action
```

UI renders state and user choices. Services observe Android signals. Product policy belongs in `BoundaryEngine`, which is pure Kotlin and unit-tested.

Core modules:

- `ui/`: Compose screens only
- `domain/`: BoundaryEngine, SurfaceMatcher, InterventionDecision, override and chosen-window logic
- `data/`: Room entities, DAO, repositories, DataStore preferences
- `rules/`: bundled JSON rule pack loading and validation
- `service/`: UsageStatsManager and AccessibilityService integration
