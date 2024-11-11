package com.example.myapplication.datastore

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesDataStore(private val context: Context) {

    private val MIN_KILLS_KEY = intPreferencesKey("min_kills")
    private val MAX_DEATHS_KEY = intPreferencesKey("max_deaths")
    private val MIN_ASSISTS_KEY = intPreferencesKey("min_assists")

    val minKillsFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[MIN_KILLS_KEY] ?: 0 }

    val maxDeathsFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[MAX_DEATHS_KEY] ?: Int.MAX_VALUE }

    val minAssistsFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[MIN_ASSISTS_KEY] ?: 0 }

    suspend fun saveMinKills(kills: Int) {
        context.dataStore.edit { preferences ->
            preferences[MIN_KILLS_KEY] = kills
        }
    }

    suspend fun saveMaxDeaths(deaths: Int) {
        context.dataStore.edit { preferences ->
            preferences[MAX_DEATHS_KEY] = deaths
        }
    }

    suspend fun saveMinAssists(assists: Int) {
        context.dataStore.edit { preferences ->
            preferences[MIN_ASSISTS_KEY] = assists
        }
    }
}