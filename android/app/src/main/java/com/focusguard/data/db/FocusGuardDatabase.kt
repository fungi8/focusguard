package com.focusguard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.focusguard.data.entity.BoundaryRuleEntity
import com.focusguard.data.entity.ChosenWindowEntity
import com.focusguard.data.entity.InstalledRulePackEntity
import com.focusguard.data.entity.InterventionEventEntity

@Database(
    entities = [
        BoundaryRuleEntity::class,
        ChosenWindowEntity::class,
        InterventionEventEntity::class,
        InstalledRulePackEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FocusGuardDatabase : RoomDatabase() {
    abstract fun dao(): FocusGuardDao
}
