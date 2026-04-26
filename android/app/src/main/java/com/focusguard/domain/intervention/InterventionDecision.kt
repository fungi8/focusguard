package com.focusguard.domain.intervention

sealed interface InterventionDecision {
    data object Allow : InterventionDecision
    data class Warn(val message: String, val boundaryId: String) : InterventionDecision
    data class ShowIntentGate(val surfaceId: String, val boundaryId: String) : InterventionDecision
    data class Block(val reason: String, val boundaryId: String) : InterventionDecision
}
