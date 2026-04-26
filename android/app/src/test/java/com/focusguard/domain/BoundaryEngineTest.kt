package com.focusguard.domain

import com.focusguard.domain.boundaries.BoundaryEvaluationContext
import com.focusguard.domain.boundaries.BoundaryEngine
import com.focusguard.domain.boundaries.BoundaryRule
import com.focusguard.domain.boundaries.ChosenWindow
import com.focusguard.domain.boundaries.OverrideMode
import com.focusguard.domain.boundaries.OverridePolicy
import com.focusguard.domain.boundaries.OverrideState
import com.focusguard.domain.boundaries.SurfaceAction
import com.focusguard.domain.boundaries.SurfaceContext
import com.focusguard.domain.boundaries.UsageSnapshot
import com.focusguard.domain.intervention.InterventionDecision
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class BoundaryEngineTest {
    private val engine = BoundaryEngine()
    private val now = LocalDateTime.of(2026, 4, 26, 12, 15)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    @Test
    fun noMatchingBoundaryAllows() {
        val decision = engine.evaluate(eval(emptyList(), "shorts"))
        assertEquals(InterventionDecision.Allow, decision)
    }

    @Test
    fun disabledBoundaryAllows() {
        val decision = engine.evaluate(eval(listOf(rule(enabled = false)), "shorts"))
        assertEquals(InterventionDecision.Allow, decision)
    }

    @Test
    fun chosenWindowAllows() {
        val rule = rule(
            action = SurfaceAction.BLOCK,
            chosenWindows = listOf(
                ChosenWindow("lunch", "Lunch", 12 * 60, 12 * 60 + 30)
            )
        )
        val decision = engine.evaluate(eval(listOf(rule), "shorts"))
        assertEquals(InterventionDecision.Allow, decision)
    }

    @Test
    fun resetWindowBlocks() {
        val decision = engine.evaluate(
            eval(listOf(rule()), "shorts", activeResetBoundaryIds = setOf("youtube-shorts"))
        )
        assertTrue(decision is InterventionDecision.Block)
        assertEquals("reset_window_active", (decision as InterventionDecision.Block).reason)
    }

    @Test
    fun dailyLimitBlocks() {
        val decision = engine.evaluate(
            eval(listOf(rule(dailyLimitMinutes = 0)), "shorts", usage = UsageSnapshot(todayMinutes = 0))
        )
        assertTrue(decision is InterventionDecision.Block)
        assertEquals("daily_limit_reached", (decision as InterventionDecision.Block).reason)
    }

    @Test
    fun sessionLimitWarns() {
        val decision = engine.evaluate(
            eval(
                listOf(rule(action = SurfaceAction.WARN, sessionLimitMinutes = 5)),
                "shorts",
                usage = UsageSnapshot(sessionMinutes = 5)
            )
        )
        assertTrue(decision is InterventionDecision.Warn)
    }

    @Test
    fun intentGateActionShowsIntentGate() {
        val decision = engine.evaluate(eval(listOf(rule(action = SurfaceAction.INTENT_GATE)), "shorts"))
        assertTrue(decision is InterventionDecision.ShowIntentGate)
    }

    @Test
    fun warnActionWarns() {
        val decision = engine.evaluate(eval(listOf(rule(action = SurfaceAction.WARN)), "shorts"))
        assertTrue(decision is InterventionDecision.Warn)
    }

    @Test
    fun blockActionBlocks() {
        val decision = engine.evaluate(eval(listOf(rule(action = SurfaceAction.BLOCK)), "shorts"))
        assertTrue(decision is InterventionDecision.Block)
    }

    @Test
    fun allowActionAllows() {
        val decision = engine.evaluate(eval(listOf(rule(action = SurfaceAction.ALLOW)), "shorts"))
        assertEquals(InterventionDecision.Allow, decision)
    }

    @Test
    fun overrideEligibilityRequiresConfiguredFriction() {
        val policy = OverridePolicy()
        val phraseRule = rule(overrideMode = OverrideMode.TYPE_PHRASE)
        assertTrue(!policy.isEligible(phraseRule, OverrideState(typedPhrase = "continue")))
        assertTrue(policy.isEligible(phraseRule, OverrideState(typedPhrase = "I choose to continue")))
    }

    private fun eval(
        rules: List<BoundaryRule>,
        surfaceId: String,
        usage: UsageSnapshot = UsageSnapshot(),
        activeResetBoundaryIds: Set<String> = emptySet()
    ): BoundaryEvaluationContext {
        return BoundaryEvaluationContext(
            surfaceContext = SurfaceContext(
                packageName = "com.google.android.youtube",
                appName = "YouTube",
                surfaceId = surfaceId,
                timestampMillis = now
            ),
            rules = rules,
            usage = usage,
            activeResetBoundaryIds = activeResetBoundaryIds
        )
    }

    private fun rule(
        action: SurfaceAction = SurfaceAction.BLOCK,
        enabled: Boolean = true,
        dailyLimitMinutes: Int? = null,
        sessionLimitMinutes: Int? = null,
        overrideMode: OverrideMode = OverrideMode.NONE,
        chosenWindows: List<ChosenWindow> = emptyList()
    ): BoundaryRule {
        return BoundaryRule(
            id = "youtube-shorts",
            appName = "YouTube",
            packageName = "com.google.android.youtube",
            surfaceId = "shorts",
            surfaceName = "Shorts",
            action = action,
            enabled = enabled,
            dailyLimitMinutes = dailyLimitMinutes,
            sessionLimitMinutes = sessionLimitMinutes,
            overrideMode = overrideMode,
            chosenWindows = chosenWindows
        )
    }
}
