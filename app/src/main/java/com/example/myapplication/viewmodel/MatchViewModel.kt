package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Match
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import com.example.myapplication.usecase.GetMatchesByIdsUseCase // New use case for fetching by IDs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel(
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase,
    private val getMatchesByIdsUseCase: GetMatchesByIdsUseCase // Injected new use case
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Match>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Match>>> = _uiState

    fun getRecentMatches(playerId: Long) {
        viewModelScope.launch {
            _uiState.value = try {
                val matches = getRecentMatchesUseCase.execute(playerId)
                UiState.Success(matches)
            } catch (e: Exception) {
                UiState.Error("Failed to load matches: ${e.message}")
            }
        }
    }

    fun getFavoriteMatches(favoriteIds: Set<String>) {
        viewModelScope.launch {
            _uiState.value = try {
                val matches = getMatchesByIdsUseCase.execute(favoriteIds)
                UiState.Success(matches)
            } catch (e: Exception) {
                UiState.Error("Failed to load favorite matches: ${e.message}")
            }
        }
    }
}
