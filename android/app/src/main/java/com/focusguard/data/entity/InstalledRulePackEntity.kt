package com.focusguard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "installed_rule_packs")
data class InstalledRulePackEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val installedAtMillis: Long
)
