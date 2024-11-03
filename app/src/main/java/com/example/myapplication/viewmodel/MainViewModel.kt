package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Match
import com.example.myapplication.usecase.GetPlayersUseCase
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase
) : ViewModel() {

    // StateFlow для хранения недавних матчей
    private val _recentMatches = MutableStateFlow<List<Match>>(emptyList())
    val recentMatches: StateFlow<List<Match>> get() = _recentMatches

    fun loadRecentMatches(playerId: Long) {
        viewModelScope.launch {
            try {
                val matches = getRecentMatchesUseCase(playerId)
                _recentMatches.value = matches
            } catch (e: Exception) {
                _recentMatches.value = emptyList() // Устанавливаем пустой список в случае ошибки
                println("Error loading recent matches: ${e.message}")
            }
        }
    }
}
