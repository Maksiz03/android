package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.Match
import com.example.myapplication.viewmodel.MainViewModel


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
    val recentMatches = viewModel.recentMatches.collectAsState(initial = null)

    Column(modifier = Modifier.padding(16.dp)) {
        when (val matches = recentMatches.value) {
            null -> {
                Text("Loading or Error loading recent matches.")
            }
            else -> {
                matches.forEach { match ->
                    // Отображаем детали матчей
                    Text("Match ID: ${match.match_id}, Kills: ${match.kills}, Deaths: ${match.deaths}, Assists: ${match.assists}")
                }
            }
        }
    }
}
