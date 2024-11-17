package com.example.myapplication.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "favorites")

class FavoritesDataStore(private val context: Context) {

    private val FAVORITES_KEY = longPreferencesKey("favorites")

    val favoritesFlow: Flow<Set<Long>> = context.dataStore.data
        .map { preferences ->
            preferences.asMap().keys.mapNotNull { it.name.toLongOrNull() }.toSet()
        }

    suspend fun addFavorite(matchId: Long) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences.asMap().keys.mapNotNull { it.name.toLongOrNull() }.toMutableSet()
            currentFavorites.add(matchId)
            for (id in currentFavorites) {
                preferences[longPreferencesKey(id.toString())] = id
            }
        }
    }

    suspend fun removeFavorite(matchId: Long) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences.asMap().keys.mapNotNull { it.name.toLongOrNull() }.toMutableSet()
            currentFavorites.remove(matchId)
            for (id in currentFavorites) {
                preferences[longPreferencesKey(id.toString())] = id
            }
            preferences.remove(longPreferencesKey(matchId.toString()))
        }
    }

    suspend fun isFavorite(matchId: Long): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences.contains(longPreferencesKey(matchId.toString()))
        }.first()
    }
}
   