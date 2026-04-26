package com.focusguard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boundary_rules")
data class BoundaryRuleEntity(
    @PrimaryKey val id: String,
    val appName: String,
    val packageName: String,
    val surfaceId: String,
    val surfaceName: String,
    val action: String,
    val enabled: Boolean,
    val dailyLimitMinutes: Int?,
    val sessionLimitMinutes: Int?,
    val cooldownMinutes: Int,
    val overrideMode: String,
    val sourcePackId: String?
)
