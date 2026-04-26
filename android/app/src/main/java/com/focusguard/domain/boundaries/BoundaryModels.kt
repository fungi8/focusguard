package com.focusguard.domain.boundaries

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

data class ChosenWindow(
    val id: String,
    val label: String,
    val startMinuteOfDay: Int,
    val endMinuteOfDay: Int,
    val daysOfWeek: Set<Int> = setOf(1, 2, 3, 4, 5, 6, 7),
    val enabled: Boolean = true
)

data class BoundaryRule(
    val id: String,
    val appName: String,
    val packageName: String,
    val surfaceId: String,
    val surfaceName: String,
    val action: SurfaceAction,
    val enabled: Boolean = true,
    val dailyLimitMinutes: Int? = null,
    val sessionLimitMinutes: Int? = null,
    val cooldownMinutes: Int = 0,
    val overrideMode: OverrideMode = OverrideMode.NONE,
    val chosenWindows: List<ChosenWindow> = emptyList()
)

data class SurfaceContext(
    val packageName: String,
    val appName: String? = null,
    val surfaceId: String? = null,
    val surfaceName: String? = null,
    val visibleText: List<String> = emptyList(),
    val timestampMillis: Long
)

data class UsageSnapshot(
    val todayMinutes: Int = 0,
    val sessionMinutes: Int = 0
)

data class OverrideState(
    val waitCompleted: Boolean = false,
    val typedPhrase: String? = null,
    val intentAnswered: Boolean = false
)

data class BoundaryEvaluationContext(
    val surfaceContext: SurfaceContext,
    val rules: List<BoundaryRule>,
    val usage: UsageSnapshot = UsageSnapshot(),
    val activeResetBoundaryIds: Set<String> = emptySet(),
    val overrideState: OverrideState = OverrideState()
)
