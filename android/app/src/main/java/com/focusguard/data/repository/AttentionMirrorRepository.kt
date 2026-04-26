package com.focusguard.data.repository

import com.focusguard.data.db.FocusGuardDao
import com.focusguard.data.entity.InterventionEventEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class AttentionMirrorRepository(
    private val dao: FocusGuardDao
) {
    fun observeRecentEvents(): Flow<List<InterventionEventEntity>> = dao.observeRecentEvents()

    suspend fun logEvent(
        boundaryRuleId: String?,
        packageName: String,
        appName: String?,
        surfaceId: String?,
        surfaceName: String?,
        decision: String,
        reason: String?,
        reflectionReason: String? = null,
        userAction: String? = null
    ) {
        dao.insertEvent(
            InterventionEventEntity(
                id = UUID.randomUUID().toString(),
                boundaryRuleId = boundaryRuleId,
                packageName = packageName,
                appName = appName,
                surfaceId = surfaceId,
                surfaceName = surfaceName,
                decision = decision,
                reason = reason,
                reflectionReason = reflectionReason,
                userAction = userAction,
                createdAtMillis = System.currentTimeMillis()
            )
        )
    }
}
