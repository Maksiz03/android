package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.UiState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecentMatchesScreen(
    viewModel: MainViewModel,
    playerId: Long // Передаем playerId как параметр
) {
    // Загрузка недавних матчей при изменении playerId
    LaunchedEffect(playerId) {
        viewModel.loadRecentMatches(playerId)
    }

    // Сбор состояния для недавних матчей
    val recentMatchesState = viewModel.recentMatchesState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        when (val state = recentMatchesState.value) {
            is UiState.Loading -> {
                Text("Loading...")
            }
            is UiState.Success -> {
                val matches = state.data // Получаем список матчей
                matches.forEach { match ->
                    // Отображаем детали матчей
                    Text("Match ID: ${match.match_id}, Kills: ${match.kills}, Deaths: ${match.deaths}, Assists: ${match.assists}")
                }
            }
            is UiState.Error -> {
                Text("Error: ${state.message}")
            }
        }
    }
}
