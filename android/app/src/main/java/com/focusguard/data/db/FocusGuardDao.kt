package com.focusguard.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.focusguard.data.entity.BoundaryRuleEntity
import com.focusguard.data.entity.ChosenWindowEntity
import com.focusguard.data.entity.InstalledRulePackEntity
import com.focusguard.data.entity.InterventionEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusGuardDao {
    @Query("SELECT * FROM boundary_rules ORDER BY appName, surfaceName")
    fun observeBoundaryRules(): Flow<List<BoundaryRuleEntity>>

    @Query("SELECT * FROM chosen_windows")
    fun observeChosenWindows(): Flow<List<ChosenWindowEntity>>

    @Query("SELECT * FROM intervention_events ORDER BY createdAtMillis DESC LIMIT :limit")
    fun observeRecentEvents(limit: Int = 100): Flow<List<InterventionEventEntity>>

    @Query("SELECT * FROM installed_rule_packs ORDER BY installedAtMillis DESC")
    fun observeInstalledRulePacks(): Flow<List<InstalledRulePackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBoundaryRules(rules: List<BoundaryRuleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertChosenWindows(windows: List<ChosenWindowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: InterventionEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRulePack(pack: InstalledRulePackEntity)

    @Query("UPDATE boundary_rules SET enabled = :enabled WHERE id = :id")
    suspend fun setBoundaryEnabled(id: String, enabled: Boolean)
}
