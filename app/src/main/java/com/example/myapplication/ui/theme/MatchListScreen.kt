package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.model.Match
import com.example.myapplication.viewmodel.UiState

@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchViewModel,
    playerId: Long,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRecentMatches(playerId)
    }

    Column {
        when (uiState) {
            is UiState.Loading -> {
                Text(text = "Loading...")
            }
            is UiState.Success -> {
                val matches = (uiState as UiState.Success<List<Match>>).data
                matches.forEach { match ->
                    Text(
                        text = "Match ID: ${match.match_id}",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("matchDetail/${match.match_id}/${match.player_slot}/${match.kills}/${match.deaths}/${match.assists}")
                            }
                    )
                }
            }
            is UiState.Error -> {
                Text(text = "Error: ${(uiState as UiState.Error).message}")
            }
        }
    }
}
