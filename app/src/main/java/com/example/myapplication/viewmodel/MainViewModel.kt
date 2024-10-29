package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Match
import com.example.myapplication.model.Player
import com.example.myapplication.usecase.GetPlayersUseCase
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase
) : ViewModel() {

    private val _recentMatchesState = MutableStateFlow<UiState<List<Match>>>(UiState.Loading)
    val recentMatchesState: StateFlow<UiState<List<Match>>> = _recentMatchesState

    fun loadRecentMatches(playerId: Long) {
        viewModelScope.launch {
            _recentMatchesState.value = UiState.Loading
            try {
                val matches = getRecentMatchesUseCase(playerId)
                _recentMatchesState.value = UiState.Success(matches)
            } catch (e: Exception) {
                _recentMatchesState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
