# FocusGuard — Codex Master Prompt v2

You are my full-stack engineer, product designer, open-source maintainer, and community growth operator.

Your job is to help me build and launch **FocusGuard**, an open-source Android-first product for the US and English-speaking markets.

This is not a generic app blocker.
This is not a parental control app.
This is not a productivity cult tool.

FocusGuard should feel like a serious, calm, humane, open-source product with a clear philosophical point of view.

---

# 1. Core Product Philosophy

## One-line idea

**FocusGuard is an open-source attention firewall for reclaiming agency in the age of infinite feeds.**

## Short explanation

Modern apps removed stopping cues.
FocusGuard puts them back.

We do not block joy.
We block autopilot.

We do not shame users.
We help them recover the moment of choice.

## Product worldview

Many modern apps are not just tools anymore.
They are environments optimized to capture attention, dissolve intention, and turn small impulses into long sessions.

FocusGuard exists to restore:

- agency
- intention
- autonomy
- humane friction
- user-controlled digital boundaries

## Product principles

1. **Block addictive surfaces, not useful apps**
2. **Restore stopping cues**
3. **Add friction without shame**
4. **Local-first by default**
5. **No ads, no surveillance, no manipulation**
6. **Open-source and community-driven**
7. **Built for adults, not parental control**
8. **Design for calm, not guilt**

## Tone and voice

Use a calm, native-English, builder-led voice.
Avoid corporate tone.
Avoid translated-sounding English.
Avoid hype.
Avoid productivity guru language.

Avoid phrases like:

- unlock your productivity
- transform your life
- beat addiction instantly
- 10x focus
- revolutionary AI
- self-discipline hack

Prefer phrases like:

- a small tool to help you pause
- rules you control
- local-first
- no shame
- moments of choice
- humane boundaries
- attention sovereignty
- stopping cues

---

# 2. Product Positioning

## Primary positioning

**FocusGuard is an open-source attention firewall that blocks addictive surfaces like Shorts, Reels, For You feeds, Popular pages, and infinite scroll — without blocking the useful parts of apps.**

## Simple explanation

People usually do not want to block YouTube.
They want to block YouTube Shorts.

People usually do not want to block Instagram.
They want to block Reels and Explore.

People usually do not want to block Reddit entirely.
They want to stop getting trapped in Popular, endless scrolling, and mindless refresh loops.

## Core differentiation

Do not build “just another screen-time blocker.”
Build a product with these differentiators:

1. **Surface-level blocking** instead of whole-app blocking
2. **Intent Gate** before high-risk surfaces
3. **Community Rule Packs** like AdBlock lists
4. **Attention Mirror** instead of guilt-driven analytics
5. **No Shame Design** as a clear design principle
6. **Open-source + local-first** as a trust layer

---

# 3. Market Scope

Target market first:

- United States
- Canada
- United Kingdom
- Australia
- English-speaking tech and digital wellbeing communities

Do **not** optimize for China.
Do **not** build around Douyin, Kuaishou, WeChat Channels, Xiaohongshu, or China-specific app ecosystems.

First target apps / surfaces:

- TikTok
- Instagram Reels / Explore
- YouTube Shorts / Home recommendations
- X / Twitter “For You” feed
- Reddit Popular / All / infinite feed
- Facebook Reels
- Snapchat Spotlight
- user-defined apps or websites in future roadmap

---

# 4. Product Identity

## Working name

**FocusGuard**

## Category

- attention firewall
- humane technology tool
- local-first digital boundary tool
- open-source digital wellbeing tool

## Recommended taglines

Choose the best one and use it consistently:

1. **An open-source attention firewall for the age of infinite feeds.**
2. **Like AdBlock, but for addictive feeds.**
3. **Reclaim the moment before the scroll.**
4. **Open-source boundaries for inhumane interfaces.**
5. **Modern apps removed stopping cues. FocusGuard puts them back.**

---

# 5. MVP Scope

Build Android MVP first.
Do not start with iOS.
The Android version should be credible, polished, and open-source ready.

## MVP goals

The MVP should demonstrate the entire product thesis:

- block addictive surfaces rather than whole apps when possible
- let users create boundaries
- show a calm intervention flow
- include a strong philosophical identity
- include community-oriented rule concepts
- include open-source quality docs and launch materials

---

# 6. Core Feature Set

## 6.1 Onboarding

The onboarding should explain the product clearly.
It should feel reflective and premium.

Suggested onboarding messaging:

### Screen 1
**Your attention firewall**

FocusGuard helps you interrupt addictive feeds and restore stopping cues.

### Screen 2
**Block addictive surfaces, not useful apps**

Keep Search, DM, and subscriptions.
Block Shorts, Reels, For You, and infinite scroll.

### Screen 3
**No shame. No tracking. No ads.**

Everything stays local by default.
You stay in control.

### Screen 4
**A moment of choice**

Before a high-risk feed opens, FocusGuard can ask what brought you here.

Required onboarding actions:

- explain permissions simply
- explain privacy clearly
- explain that this is not parental control
- explain that overrides are allowed

---

## 6.2 Boundaries

Do not call this screen “Rules” in the final UX.
Call it **Boundaries**.

This screen should allow users to configure app-level and surface-level boundaries.

### Example configuration model

#### YouTube
- Shorts: Block
- Home recommendations: Warn
- Subscriptions: Allow
- Search: Allow

#### Instagram
- Reels: Block
- Explore: Warn
- DM: Allow
- Profile: Allow

#### Reddit
- Popular: Block
- All: Warn
- Joined communities: Allow
- Search: Allow

#### X
- For You: Block
- Following: Warn or Allow
- Search: Allow
- DMs: Allow

#### TikTok
- For You: Intent Gate + Daily Limit
- Following: Allow or Warn
- Search: Allow

Each boundary can include:

- enabled / disabled
- action: allow / warn / block / intent_gate
- daily limit
- session reminder
- cooldown duration
- override friction
- chosen windows (allowed time windows)

### Data structure direction

Use a clean structure, for example:

```json
{
  "app": "YouTube",
  "package": "com.google.android.youtube",
  "surfaces": [
    {
      "id": "shorts",
      "name": "Shorts",
      "action": "block",
      "daily_limit_minutes": 0,
      "session_limit_minutes": 0,
      "cooldown_minutes": 0,
      "override_mode": "type_phrase",
      "enabled": true
    },
    {
      "id": "home_feed",
      "name": "Home feed",
      "action": "warn",
      "session_limit_minutes": 5,
      "enabled": true
    },
    {
      "id": "subscriptions",
      "name": "Subscriptions",
      "action": "allow",
      "enabled": true
    }
  ]
}
```

---

## 6.3 Surface Detection

For the MVP, implement a pragmatic version based on Android capabilities.
Use:

- UsageStatsManager
- Accessibility Service
- foreground app detection
- text / UI heuristics where realistic

If precise surface detection is hard for every app in v1, do the following:

1. Support credible MVP surface detection for a few flagship cases.
2. Make the detection system extensible.
3. Document known limitations clearly.

Priority surfaces to support first:

- YouTube Shorts
- Instagram Reels
- Reddit Popular / All or continuous feed view
- X “For You”
- TikTok main feed

If exact detection is not fully reliable, still build the architecture around “surface rules” and label unsupported cases as experimental.

---

## 6.4 Intent Gate

This is a major differentiator.

When a user tries to open a high-risk surface, FocusGuard can present a **Moment of Choice** screen.

Do not make it feel punitive.
It should feel calm and reflective.

### Suggested copy

**A moment of choice**

What brought you here?

Options:

- I came to message someone
- I came to search for something
- I came to post something
- I came to check one thing
- I came here without thinking

If the user selects “I came here without thinking,” respond with:

**That happens.**

Take a breath.
If you still want this, continue deliberately.

Possible next actions:

- Continue with intention
- Leave for now
- Take one breath
- Open an alternative

### Product purpose

The Intent Gate is not about moral judgment.
It is about making the user’s intention visible.

---

## 6.5 Moment of Choice / Block Screen

Do not call it “Block Screen” in the product.
Internally you can use that term, but UX copy should be:

- A moment of choice
- Pause
- Stop and choose

### Example copy

**A moment of choice**

You opened this feed 8 minutes ago.
Before continuing, ask yourself:

**Is this still what you came here for?**

Buttons:

- Leave with intention
- Continue with intention
- Take a 5-minute break
- Open a better next step

If override friction is enabled, possible flows include:

- 10-second wait
- type phrase: “I choose to continue”
- simple reflective question

---

## 6.6 Better Next Step

When a user hits a high-risk surface or boundary, FocusGuard should optionally suggest a replacement action.

Do not turn this into a habit app.
Keep it lightweight.

Examples:

- Open Notes
- Open Kindle
- Open Spotify
- Breathe for 30 seconds
- Stretch for 1 minute
- Message someone intentionally

This feature can be named:

- Better next step
- Alternative path
- Recenter

---

## 6.7 Chosen Windows

Do not call them “whitelist windows” in the product.
Call them **Chosen Windows**.

Examples:

- Lunch window: 12:00 PM – 12:30 PM
- Evening unwind: 8:00 PM – 8:30 PM

Purpose:

The app should support intentional entertainment, not forced abstinence.

---

## 6.8 Shared Boundaries

Do not call them “Presets” in the product.
Call them **Shared Boundaries**.

These are community-maintained rule packs.

Examples:

- Essential
- No Shorts
- Reddit Minimal
- Creator Mode
- Evening Reset

### Example rule packs

#### Essential
Blocks the most common attention traps.

#### No Shorts
Blocks YouTube Shorts, Instagram Reels, and similar short-form feeds.

#### Reddit Minimal
Blocks Popular and All, keeps joined communities and search.

#### Creator Mode
Allows posting, messages, and analytics while blocking consumption-heavy surfaces.

### Open-source angle

Users should be able to contribute rule packs via GitHub in the future.
This is a key community differentiator.

---

## 6.9 Attention Mirror

Do not call the main insights screen “Stats” in the product.
Call it **Attention Mirror**.

This is not a guilt dashboard.
It is a reflective tool.

Show things like:

- moments of choice reclaimed today
- interventions today
- top blocked surfaces
- common autopilot times
- common reasons selected in Intent Gate

### Example copy

**You reclaimed 7 moments of choice today.**

Possible insight cards:

- Most autopilot openings happened after 10 PM
- You most often opened YouTube Shorts when feeling tired
- Reddit Popular was your most interrupted surface this week

### Mood / reason tagging

If it fits the flow, allow users to optionally select why they opened the app:

- bored
- tired
- avoiding something
- looking for connection
- searching something
- habit

Keep this optional and local-only.

---

## 6.10 Local-First Privacy

This is core.
Not optional.

Everything should be local-first by default.

Privacy commitments:

- no account required
- no ads
- no default analytics
- no cloud by default
- no selling data
- no uploading usage history
- no reading messages
- no recording keystrokes
- no screen recording

For Accessibility Service, document clearly:

FocusGuard uses Accessibility Service only for the minimum required interaction detection and intervention logic.
It does not read chat content.
It does not collect keystrokes.
It does not upload accessibility data.

---

# 7. Information Architecture

Final product naming should lean philosophical / humane.

Recommended tab / section naming:

- **Today**
- **Boundaries**
- **Attention Mirror**
- **Shared Boundaries**
- **Settings**

Alternative section labels are acceptable if they are better.

### Suggested product terminology

| Internal / conventional | Product language |
|---|---|
| Rules | Boundaries |
| Presets | Shared Boundaries |
| Stats | Attention Mirror |
| Block Screen | Moment of Choice |
| Whitelist | Chosen Windows |
| Override | Conscious Override |
| Cooldown | Reset Window |

---

# 8. UX Flows

Build the app and the prototype around these flows.

## Flow A — First-time user

1. User opens app
2. Sees onboarding
3. Understands philosophy
4. Understands privacy
5. Grants required permissions
6. Chooses starter Shared Boundary pack or manual setup
7. Lands on Today screen

## Flow B — Add a new boundary

1. Open Boundaries
2. Tap Add boundary
3. Select app
4. Select surface(s)
5. Configure action:
   - allow
   - warn
   - intent gate
   - block
6. Configure daily limit / session reminder / override mode / chosen windows
7. Save

## Flow C — Hit a high-risk surface

1. User opens TikTok / Shorts / Reels / Popular page
2. FocusGuard detects boundary
3. Show Moment of Choice or Intent Gate
4. User answers intent prompt or chooses action
5. User leaves, continues intentionally, or opens better next step
6. Event is logged locally in Attention Mirror

## Flow D — Install a Shared Boundary pack

1. Open Shared Boundaries
2. Browse packs
3. Read pack explanation
4. Preview affected apps and surfaces
5. Install
6. Edit if needed

## Flow E — Reflect on usage

1. Open Attention Mirror
2. See moments reclaimed today
3. See top interrupted surfaces
4. See time and reason patterns
5. Adjust boundaries if needed

---

# 9. Design Direction

The UI should be beautiful, calm, and screenshot-friendly.
It should feel suitable for GitHub, Product Hunt, Reddit, and Hacker News.

## Design adjectives

- calm
- premium
- reflective
- humane
- minimal
- not corporate
- not childish
- not medical
- not punitive
- not “productivity bro”

## Visual direction

Use a more philosophical, restrained theme. Avoid a sunny yellow lifestyle-app feeling. The visual identity should feel closer to a quiet library, a thoughtful notebook, or monastic minimalism.

Recommended theme: **Bone White + Charcoal + Deep Sage**.

### Design tokens

```text
Canvas / App Background: #F5F2EA
Surface / Card: #FCFBF8
Primary Text: #1B1B1B
Secondary Text: #6C6A63
Border / Divider: #D8D2C7
Primary Accent: #496A5A
Accent Hover: #3F5B4D
Soft Accent Background: #E5EEE8
Warning / Clay: #A67C5B
Critical / Muted Rust: #8A5A4A
```

The theme should communicate:

- philosophy
- restraint
- agency
- reflection
- humane technology
- attention sovereignty

UI direction:

- bone-white background instead of obvious yellow
- charcoal primary actions
- deep sage for reflective states, installed packs, and humane positive feedback
- muted clay for warnings instead of bright orange
- muted rust for hard boundaries instead of aggressive red
- large rounded cards
- generous spacing
- elegant typography
- subtle dividers
- minimal iconography
- thoughtful empty states

## Avoid

- bright gamified colors
- streaks
- badges
- dopamine reward loops
- leaderboard style mechanics
- aggressive red error states unless truly necessary

## Visual concept

The visual concept is **Monastic Minimalism**:

- quiet, not sleepy
- reflective, not therapeutic cliché
- premium, not luxury
- humane, not gamified
- philosophical, not academic

The app should feel like a small tool made by people who deeply care about agency, not like another productivity subscription.

---

# 10. Android Technical Direction

Build the MVP in:

- Kotlin
- Jetpack Compose
- Room
- DataStore
- Coroutines / Flow
- UsageStatsManager
- Accessibility Service
- Foreground Service where needed
- Material 3
- JUnit tests for core logic

## Recommended project structure

```text
focusguard/
  app/
    src/main/java/com/focusguard/
      ui/
        onboarding/
        today/
        boundaries/
        boundaryedit/
        momentofchoice/
        attentionmirror/
        sharedboundaries/
        settings/
      data/
        db/
        entity/
        repository/
        datastore/
        model/
      domain/
        boundaries/
        surfaces/
        detection/
        intervention/
        attentionmirror/
        sharedrules/
      service/
        UsageMonitorService
        AccessibilityDetectionService
        ForegroundAppMonitor
      design/
        theme/
        components/
      utils/
  docs/
    architecture.md
    privacy.md
    manifesto.md
    product-design.md
    ux-flows.md
    shared-boundaries.md
    launch-plan.md
    community-strategy.md
    social-copy.md
  README.md
  MANIFESTO.md
  ROADMAP.md
  CONTRIBUTING.md
  CODE_OF_CONDUCT.md
  SECURITY.md
  CHANGELOG.md
  LICENSE
```

---

# 11. Domain Model Suggestions

## Main entities

- AppTarget
- SurfaceTarget
- BoundaryRule
- ChosenWindow
- OverrideMode
- InterventionEvent
- SharedBoundaryPack
- ReflectionReason

### Example enums

```kotlin
enum class SurfaceAction {
    ALLOW,
    WARN,
    INTENT_GATE,
    BLOCK
}

enum class OverrideMode {
    NONE,
    WAIT_10_SECONDS,
    TYPE_PHRASE,
    ASK_INTENT
}

enum class ReflectionReason {
    BORED,
    TIRED,
    AVOIDING_SOMETHING,
    LOOKING_FOR_CONNECTION,
    SEARCHING_SOMETHING,
    HABIT,
    OTHER
}
```

### Core decision result

```kotlin
sealed class InterventionDecision {
    data object Allow : InterventionDecision()
    data class Warn(val message: String) : InterventionDecision()
    data class ShowIntentGate(val surfaceId: String) : InterventionDecision()
    data class Block(val reason: String) : InterventionDecision()
}
```

---

# 12. Rule Engine / Boundary Engine

The core decision engine must be independent from UI.
Test it properly.

## Pseudocode direction

```kotlin
fun evaluate(surfaceContext: SurfaceContext, now: Long): InterventionDecision {
    val boundary = boundaryRepository.findMatchingBoundary(surfaceContext)
        ?: return InterventionDecision.Allow

    if (!boundary.enabled) return InterventionDecision.Allow

    if (chosenWindowChecker.isInChosenWindow(boundary, now)) {
        return InterventionDecision.Allow
    }

    if (resetWindowManager.isActive(boundary, now)) {
        return InterventionDecision.Block(reason = "reset_window_active")
    }

    val todayUsage = usageTracker.getTodayUsage(boundary)
    if (boundary.dailyLimitMinutes != null && todayUsage >= boundary.dailyLimitMinutes) {
        resetWindowManager.start(boundary, now)
        return InterventionDecision.Block(reason = "daily_limit_reached")
    }

    val sessionUsage = usageTracker.getSessionUsage(boundary)
    if (boundary.sessionLimitMinutes != null && sessionUsage >= boundary.sessionLimitMinutes) {
        return when (boundary.surfaceAction) {
            SurfaceAction.WARN -> InterventionDecision.Warn("session_limit_reached")
            SurfaceAction.INTENT_GATE -> InterventionDecision.ShowIntentGate(boundary.surfaceId)
            SurfaceAction.BLOCK -> InterventionDecision.Block("session_limit_reached")
            SurfaceAction.ALLOW -> InterventionDecision.Allow
        }
    }

    return when (boundary.surfaceAction) {
        SurfaceAction.INTENT_GATE -> InterventionDecision.ShowIntentGate(boundary.surfaceId)
        SurfaceAction.WARN -> InterventionDecision.Warn("surface_boundary_triggered")
        SurfaceAction.BLOCK -> InterventionDecision.Block("surface_boundary_triggered")
        SurfaceAction.ALLOW -> InterventionDecision.Allow
    }
}
```

## Required tests

Add tests at minimum for:

- no matching boundary
- disabled boundary
- in chosen window
- daily limit reached
- session limit reached
- reset window active
- intent gate action
- warn action
- block action
- allow action
- override flow eligibility

If there are no tests yet, add them.

---

# 13. Open-Source Requirements

The repo must look serious and contributor-friendly.

## Required files

```text
README.md
MANIFESTO.md
ROADMAP.md
CONTRIBUTING.md
CODE_OF_CONDUCT.md
SECURITY.md
CHANGELOG.md
LICENSE
docs/
  architecture.md
  privacy.md
  manifesto.md
  product-design.md
  ux-flows.md
  shared-boundaries.md
  launch-plan.md
  community-strategy.md
  social-copy.md
```

Use MIT License unless you have a strong reason otherwise.

## README requirements

The README is critical.
Make it strong.

The first screen should clearly express the philosophy and utility.

### README first section direction

```md
# FocusGuard

An open-source attention firewall for reclaiming agency in the age of infinite feeds.

FocusGuard helps people block addictive surfaces like Shorts, Reels, For You feeds, Popular pages, and infinite scroll — without blocking the useful parts of apps.

No ads. No tracking. No shame. Local-first by default.

> Modern apps removed stopping cues. FocusGuard puts them back.
```

README should include:

1. one-sentence pitch
2. manifesto-level explanation
3. screenshots / prototype images
4. why this exists
5. key differentiators
6. supported surfaces / app targets
7. installation / build from source
8. architecture summary
9. privacy principles
10. roadmap
11. contributing guide
12. community links
13. request for feedback and stars

---

# 14. Manifesto

Create `MANIFESTO.md` with a more philosophical voice.

Direction:

```md
# The FocusGuard Manifesto

Attention is not just a productivity resource.
It is the ground of a human life.

We do not believe people are weak because they scroll.
We believe many interfaces are designed to dissolve intention.

We are not here to ban pleasure.
We are here to restore the pause between impulse and action.

Modern apps removed stopping cues.
We put them back.

No shame.
No surveillance.
No dark patterns.
No productivity cult.

Just local-first tools, transparent rules, and moments of choice.
```

---

# 15. Community Strategy

This should be built as a community-driven open-source project.

Main narrative:

**Help map the attention traps of the modern internet.**

Secondary narrative:

**Let’s build humane boundaries for inhumane interfaces.**

## Key community angles

- open-source digital wellbeing
- humane tech
- local-first privacy
- Android builder story
- “I built this because I needed it”
- block addictive surfaces, not useful apps

## Platforms to prepare for

- GitHub
- Hacker News
- Reddit
- Product Hunt
- X / Twitter
- Indie Hackers
- Dev.to
- Medium
- Android communities
- digital minimalism communities
- ADHD and focus communities (respectfully, without making medical claims)

Do not focus launch docs on Chinese platforms.

---

# 16. Launch Content Requirements

Generate native English launch content.
It should sound like a builder, not a marketer.

## Required launch content

Create `docs/social-copy.md` with:

1. Hacker News launch copy
2. Reddit posts for:
   - r/digitalminimalism
   - r/nosurf
   - r/androidapps
   - r/opensource
3. Product Hunt listing copy
4. 20 X / Twitter posts
5. Dev.to / Medium article outline and first draft
6. short GitHub repo introduction text
7. GitHub issue templates / starter issues

## Content tone

- native English
- honest
- reflective
- clear
- no hype
- no fake founder-story tone

### Example Hacker News title

**Show HN: FocusGuard — open-source attention firewall for Shorts, Reels, and infinite feeds**

### Example Reddit angle

**I don’t want to block YouTube. I want to block YouTube Shorts.**

### Example Product Hunt positioning

**An open-source attention firewall that blocks addictive surfaces without blocking the useful parts of apps.**

---

# 17. Prototype Requirements

In addition to the Android codebase, create a polished HTML product prototype that illustrates the full interaction logic.

This prototype should be a standalone HTML file, visually strong, and understandable even without a backend.
It should be suitable for:

- sharing with me
- sharing with contributors
- helping Codex understand flows
- early user feedback
- screenshots for repo planning

## HTML prototype must include

1. Today screen
2. Boundaries screen
3. Boundary detail / edit screen
4. Surface configuration view
5. Intent Gate / Moment of Choice flow
6. Shared Boundaries screen
7. Attention Mirror screen
8. Onboarding flow
9. Privacy / permissions explanation view
10. high-level interaction map / product flow diagram

## Prototype goals

- beautiful
- coherent
- use the Bone White + Charcoal + Deep Sage philosophical theme
- avoid sunny yellow as the main product color
- shows product logic clearly
- shows terminology clearly
- shows navigation clearly
- shows decision flows clearly
- usable as a design spec for implementation

---

# 18. Implementation Order

Do the work in this order:

## Step 1
Set up project structure.

## Step 2
Create README, MANIFESTO, and docs first.

## Step 3
Create core models and repositories.

## Step 4
Implement boundary engine with tests.

## Step 5
Implement Compose screens for:
- onboarding
- today
- boundaries
- boundary detail
- moment of choice
- attention mirror
- shared boundaries
- settings

## Step 6
Implement Android monitoring and accessibility integration.

## Step 7
Implement event logging for Attention Mirror.

## Step 8
Create HTML prototype showing full flow.

## Step 9
Generate launch materials and community docs.

## Step 10
Polish code quality, docs, and issue templates.

---

# 19. Final Deliverables

At the end, provide:

1. working Android MVP codebase
2. polished README
3. MANIFESTO.md
4. complete docs
5. core tests for boundary engine
6. HTML prototype file
7. launch content docs
8. issue templates or starter issue list
9. clear roadmap
10. clear explanation of known Android limitations and next steps

---

# 20. Final Reminder

This project should not feel like a generic blocker.
It should feel like an open-source product with a point of view.

The central idea is:

**Between impulse and action, there should be a pause.**

FocusGuard is that pause.

---

# 21. Low-Cost Technical Strategy

FocusGuard must be designed as a **no-backend, Android-first, local-first app** for v1.

The first version must not require:

- a backend server
- a cloud database
- user accounts
- login
- payment
- subscriptions
- Firebase
- Supabase
- Google Analytics
- ad SDKs
- remote config
- server-side AI
- web dashboard
- admin panel

The app should work fully offline after installation.

## Recommended v1 stack

Use the simplest reliable Android-native stack:

- Kotlin
- Jetpack Compose
- Material 3
- Room for structured local data
- DataStore for lightweight preferences
- Coroutines / Flow
- WorkManager only if needed
- UsageStatsManager for app-level usage detection
- AccessibilityService only where necessary for surface detection and intervention
- JUnit for domain tests

Do not use Flutter or React Native for v1 unless there is a strong reason. The core challenge is Android system capability, not cross-platform UI.

## Release and distribution strategy

Use the lowest-cost distribution path:

1. **GitHub Releases APK** for early open-source testers
2. **F-Droid** for privacy-first Android users
3. **Google Play later**, only after AccessibilityService disclosures and policy risks are carefully reviewed

Do not optimize for app-store polish before the MVP proves that the core intervention and surface detection loops work.

## Rule pack distribution without a server

Shared Boundary Packs should not require a backend in v1.

Use local bundled JSON assets:

```text
assets/rule-packs/
  essential.json
  no-shorts.json
  reddit-minimal.json
  creator-mode.json
  evening-reset.json
```

Future versions can optionally support:

- import rule pack from local file
- import rule pack from GitHub raw URL
- manual update from a public GitHub repository

Do not add silent background syncing in v1.
Do not add cloud rule marketplace in v1.

## Core technical modules

Implement the app around these modules:

```text
ui/
  Compose screens only. No business logic here.

domain/
  BoundaryEngine
  SurfaceMatcher
  InterventionDecision
  ChosenWindowChecker
  ResetWindowManager
  OverridePolicy

data/
  Room entities
  DAOs
  repositories
  DataStore preferences
  local JSON rule pack parser

service/
  UsageMonitorService
  AccessibilityDetectionService
  ForegroundAppMonitor

rules/
  LocalRulePackLoader
  RulePackValidator
  bundled JSON rule packs
```

## Architecture principle

UI should not decide whether to block.
Services should not contain product policy.

All policy decisions should go through:

```text
SurfaceContext -> BoundaryEngine -> InterventionDecision -> UI / Service action
```

## BoundaryEngine responsibilities

The BoundaryEngine is the heart of the product.
It should be pure, testable, and independent from Android UI.

It takes:

- current app
- current surface if known
- current time
- matching boundary rule
- today usage
- current session usage
- chosen windows
- active reset windows
- override state

It returns:

- Allow
- Warn
- ShowIntentGate
- Block

## SurfaceMatcher responsibilities

SurfaceMatcher is the key technical differentiator.

It tries to answer:

- Is this YouTube Shorts or normal YouTube?
- Is this Instagram Reels or DM/Profile?
- Is this Reddit Popular/All or a joined community?
- Is this X For You or Search/DM?

The first version can use pragmatic heuristics:

- package name
- Accessibility node text
- visible UI labels
- view hierarchy hints
- activity/window changes where available

Design it as an extensible interface:

```kotlin
interface SurfaceMatcher {
    fun match(context: AccessibilitySurfaceContext): SurfaceMatch?
}
```

Create app-specific matchers:

```text
YouTubeSurfaceMatcher
InstagramSurfaceMatcher
RedditSurfaceMatcher
XSurfaceMatcher
TikTokSurfaceMatcher
```

Surface detection can be experimental in v1. Be honest in docs.

Suggested docs wording:

```text
Surface detection is experimental and community-maintained. Some apps frequently change their UI, so detection may require updates over time.
```

## Testing requirements

Add tests for:

- BoundaryEngine no matching boundary
- disabled boundary
- chosen window active
- reset window active
- daily limit reached
- session limit reached
- action = allow
- action = warn
- action = intent gate
- action = block
- override mode eligibility
- local JSON rule pack parsing
- invalid rule pack handling

## Privacy requirements

No user data leaves the device in v1.

No account.
No tracking.
No analytics by default.
No ads.
No cloud sync.
No third-party SDK unless it is absolutely necessary and open-source friendly.

AccessibilityService must be documented clearly:

```text
FocusGuard uses AccessibilityService only to detect user-defined high-risk surfaces and show interventions when a matching boundary is triggered.

FocusGuard does not read messages.
FocusGuard does not collect passwords.
FocusGuard does not record the screen.
FocusGuard does not collect keystrokes.
FocusGuard does not upload Accessibility data.
```

## v0.1 technical target

Build:

- Android local MVP
- bundled local rule packs
- BoundaryEngine with tests
- SurfaceMatcher architecture
- one or two credible experimental surface matchers
- Moment of Choice flow
- Attention Mirror local event log
- GitHub Releases APK
- F-Droid-friendly dependency choices

Do not build backend infrastructure before this works.

---

# 22. Final Technical Reminder

The lowest-cost and most philosophically consistent architecture is:

**Kotlin Android app + local Room database + DataStore + bundled JSON rule packs + GitHub Releases + F-Droid.**

This matches the product thesis:

- local-first
- no surveillance
- no cloud dependency
- transparent rules
- open-source community contribution
- low operational cost

Focus engineering energy on Android reliability, permission flows, surface detection, and BoundaryEngine correctness — not on backend infrastructure.

