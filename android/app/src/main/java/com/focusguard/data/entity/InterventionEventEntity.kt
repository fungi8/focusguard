package com.focusguard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "intervention_events")
data class InterventionEventEntity(
    @PrimaryKey val id: String,
    val boundaryRuleId: String?,
    val packageName: String,
    val appName: String?,
    val surfaceId: String?,
    val surfaceName: String?,
    val decision: String,
    val reason: String?,
    val reflectionReason: String?,
    val userAction: String?,
    val createdAtMillis: Long
)
