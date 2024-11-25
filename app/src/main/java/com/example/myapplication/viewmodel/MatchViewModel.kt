package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Match
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import com.example.myapplication.usecase.GetMatchesByIdsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MatchViewModel @Inject constructor(
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase,
    private val getMatchesByIdsUseCase: GetMatchesByIdsUseCase
) : ViewModel() {

    // MutableStateFlow to hold the UI state and expose it as an immutable StateFlow
    private val _uiState = MutableStateFlow<UiState<List<Match>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Match>>> = _uiState

    // Function to fetch recent matches for a given player ID
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

    // Function to fetch matches by a set of favorite IDs
    fun getFavoriteMatches(favoriteIds: Set<Long>) {
        viewModelScope.launch {
            _uiState.value = try {
                val matches = getMatchesByIdsUseCase.execute(favoriteIds)
                if (matches.isEmpty()) {
                    UiState.Error("No matches found for the provided IDs.")
                } else {
                    UiState.Success(matches)
                }
            } catch (e: Exception) {
                UiState.Error("Failed to load favorite matches: ${e.message}")
            }
        }
    }
}