package com.focusguard.data.repository

import com.focusguard.data.db.FocusGuardDao
import com.focusguard.data.model.toDomain
import com.focusguard.data.model.toEntity
import com.focusguard.domain.boundaries.BoundaryRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class BoundaryRepository(
    private val dao: FocusGuardDao
) {
    val rules: Flow<List<BoundaryRule>> = combine(
        dao.observeBoundaryRules(),
        dao.observeChosenWindows()
    ) { rules, windows ->
        rules.map { it.toDomain(windows) }
    }

    suspend fun installRules(packId: String, rules: List<BoundaryRule>) {
        dao.upsertBoundaryRules(rules.map { it.toEntity(packId) })
        dao.upsertChosenWindows(
            rules.flatMap { rule ->
                rule.chosenWindows.map { it.toEntity(rule.id) }
            }
        )
    }

    suspend fun setEnabled(ruleId: String, enabled: Boolean) {
        dao.setBoundaryEnabled(ruleId, enabled)
    }
}
