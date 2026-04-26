package com.focusguard

import android.app.Application
import androidx.room.Room
import com.focusguard.data.datastore.AppPreferences
import com.focusguard.data.db.FocusGuardDatabase
import com.focusguard.data.repository.AttentionMirrorRepository
import com.focusguard.data.repository.BoundaryRepository
import com.focusguard.domain.boundaries.BoundaryEngine
import com.focusguard.domain.surfaces.CompositeSurfaceMatcher
import com.focusguard.rules.LocalRulePackLoader

class FocusGuardApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            applicationContext,
            FocusGuardDatabase::class.java,
            "focusguard.db"
        ).build()

        container = AppContainer(
            database = database,
            boundaryRepository = BoundaryRepository(database.dao()),
            attentionMirrorRepository = AttentionMirrorRepository(database.dao()),
            preferences = AppPreferences(applicationContext),
            rulePackLoader = LocalRulePackLoader(applicationContext),
            boundaryEngine = BoundaryEngine(),
            surfaceMatcher = CompositeSurfaceMatcher()
        )
    }
}

data class AppContainer(
    val database: FocusGuardDatabase,
    val boundaryRepository: BoundaryRepository,
    val attentionMirrorRepository: AttentionMirrorRepository,
    val preferences: AppPreferences,
    val rulePackLoader: LocalRulePackLoader,
    val boundaryEngine: BoundaryEngine,
    val surfaceMatcher: CompositeSurfaceMatcher
)
