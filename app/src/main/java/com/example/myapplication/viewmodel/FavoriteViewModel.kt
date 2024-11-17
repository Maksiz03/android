package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.FavoriteDao
import com.example.myapplication.entities.FavoriteItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val dao: FavoriteDao) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteItem>>(emptyList())
    val favorites: StateFlow<List<FavoriteItem>> = _favorites

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = dao.getAllFavorites()
        }
    }

    fun addFavorite(item: FavoriteItem) {
        viewModelScope.launch {
            dao.insertFavorite(item)
            loadFavorites()
        }
    }

    fun removeFavorite(matchId: Long) {
        viewModelScope.launch {
            dao.deleteFavorite(matchId)
            loadFavorites()
        }
    }
}
