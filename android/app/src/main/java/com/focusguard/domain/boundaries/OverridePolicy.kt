package com.focusguard.domain.boundaries

class OverridePolicy {
    fun isEligible(rule: BoundaryRule, state: OverrideState): Boolean {
        return when (rule.overrideMode) {
            OverrideMode.NONE -> true
            OverrideMode.WAIT_10_SECONDS -> state.waitCompleted
            OverrideMode.TYPE_PHRASE -> state.typedPhrase == "I choose to continue"
            OverrideMode.ASK_INTENT -> state.intentAnswered
        }
    }
}
