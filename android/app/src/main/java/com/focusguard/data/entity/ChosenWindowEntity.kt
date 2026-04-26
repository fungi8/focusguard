package com.focusguard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chosen_windows")
data class ChosenWindowEntity(
    @PrimaryKey val id: String,
    val boundaryRuleId: String,
    val label: String,
    val startMinuteOfDay: Int,
    val endMinuteOfDay: Int,
    val daysOfWeekCsv: String,
    val enabled: Boolean
)
