package com.example.myapplication.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FavoritesDataStore {

    // A simple in-memory data store for demonstration purposes
    private val _favoritesFlow = MutableStateFlow<Set<String>>(emptySet())
    val favoritesFlow: Flow<Set<String>> get() = _favoritesFlow

    fun addFavorite(matchId: String) {
        val currentFavorites = _favoritesFlow.value.toMutableSet()
        currentFavorites.add(matchId)
        _favoritesFlow.value = currentFavorites
    }

    fun removeFavorite(matchId: String) {
        val currentFavorites = _favoritesFlow.value.toMutableSet()
        currentFavorites.remove(matchId)
        _favoritesFlow.value = currentFavorites
    }
}
