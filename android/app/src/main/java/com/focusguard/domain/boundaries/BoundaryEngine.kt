package com.focusguard.domain.boundaries

import com.focusguard.domain.intervention.InterventionDecision

class BoundaryEngine(
    private val chosenWindowChecker: ChosenWindowChecker = ChosenWindowChecker(),
    private val overridePolicy: OverridePolicy = OverridePolicy()
) {
    fun evaluate(context: BoundaryEvaluationContext): InterventionDecision {
        val boundary = findMatchingBoundary(context.surfaceContext, context.rules)
            ?: return InterventionDecision.Allow

        if (!boundary.enabled) return InterventionDecision.Allow

        if (chosenWindowChecker.isInChosenWindow(boundary, context.surfaceContext.timestampMillis)) {
            return InterventionDecision.Allow
        }

        if (boundary.id in context.activeResetBoundaryIds) {
            return InterventionDecision.Block("reset_window_active", boundary.id)
        }

        if (boundary.dailyLimitMinutes != null && context.usage.todayMinutes >= boundary.dailyLimitMinutes) {
            return InterventionDecision.Block("daily_limit_reached", boundary.id)
        }

        if (boundary.sessionLimitMinutes != null && context.usage.sessionMinutes >= boundary.sessionLimitMinutes) {
            return actionDecision(boundary, "session_limit_reached")
        }

        if (
            boundary.action == SurfaceAction.BLOCK &&
            boundary.overrideMode != OverrideMode.NONE &&
            overridePolicy.isEligible(boundary, context.overrideState)
        ) {
            return InterventionDecision.Allow
        }

        return actionDecision(boundary, "surface_boundary_triggered")
    }

    private fun findMatchingBoundary(
        surfaceContext: SurfaceContext,
        rules: List<BoundaryRule>
    ): BoundaryRule? {
        return rules.firstOrNull { rule ->
            rule.packageName == surfaceContext.packageName &&
                (surfaceContext.surfaceId == null || rule.surfaceId == surfaceContext.surfaceId)
        } ?: rules.firstOrNull { rule ->
            rule.packageName == surfaceContext.packageName && rule.surfaceId == "app"
        }
    }

    private fun actionDecision(rule: BoundaryRule, reason: String): InterventionDecision {
        return when (rule.action) {
            SurfaceAction.ALLOW -> InterventionDecision.Allow
            SurfaceAction.WARN -> InterventionDecision.Warn(reason, rule.id)
            SurfaceAction.INTENT_GATE -> InterventionDecision.ShowIntentGate(rule.surfaceId, rule.id)
            SurfaceAction.BLOCK -> InterventionDecision.Block(reason, rule.id)
        }
    }
}
