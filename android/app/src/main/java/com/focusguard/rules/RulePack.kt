package com.focusguard.rules

import com.focusguard.domain.boundaries.BoundaryRule

data class RulePack(
    val id: String,
    val name: String,
    val description: String,
    val rules: List<BoundaryRule>
)

data class RulePackValidationResult(
    val valid: Boolean,
    val errors: List<String> = emptyList()
)
