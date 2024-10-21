package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Player
import com.example.myapplication.network.DotaApi
import kotlinx.coroutines.launch

class MainViewModel(private val api: DotaApi) : ViewModel() {
    private val _players = mutableListOf<Player>()

    fun fetchPlayers() {
        viewModelScope.launch {
            try {
                val players = api.getPlayers()
                _players.addAll(players)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}