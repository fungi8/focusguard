package com.focusguard.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.focusGuardDataStore by preferencesDataStore("focusguard_preferences")

class AppPreferences(
    private val context: Context
) {
    val onboardingCompleted: Flow<Boolean> = context.focusGuardDataStore.data
        .map { it[ONBOARDING_COMPLETED] ?: false }

    suspend fun setOnboardingCompleted(value: Boolean) {
        context.focusGuardDataStore.edit { it[ONBOARDING_COMPLETED] = value }
    }

    private companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }
}
