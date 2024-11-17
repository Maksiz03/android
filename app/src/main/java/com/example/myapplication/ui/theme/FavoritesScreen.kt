package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.datastore.FavoritesDataStore
import com.example.myapplication.model.Match
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.viewmodel.UiState

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MatchViewModel = hiltViewModel(),
    favoritesDataStore: FavoritesDataStore
) {
    // Observe the flow of favorite IDs
    val favoriteIds by favoritesDataStore.favoritesFlow.collectAsState(initial = emptySet())

    // Fetch favorite matches based on the observed IDs
    LaunchedEffect(favoriteIds) {
        viewModel.getFavoriteMatches(favoriteIds)
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Loading your favorite matches...")
            }
            is UiState.Success -> {
                val matches = (uiState as UiState.Success<List<Match>>).data
                if (matches.isEmpty()) {
                    Text(text = "No favorite matches found.")
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(matches) { match ->
                            MatchListItem(match, navController)
                        }
                    }
                }
            }
            is UiState.Error -> {
                Text(text = "Error: ${(uiState as UiState.Error).message}")
            }
            else -> {
                // Handle other possible states if needed
            }
        }
    }
}


@Composable
fun MatchListItem(match: Match, navController: NavController) {
    Text(
        text = "Match ID: ${match.match_id} | K: ${match.kills} D: ${match.deaths} A: ${match.assists}",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("matchDetail/${match.match_id}/${match.player_slot}/${match.kills}/${match.deaths}/${match.assists}")
            }
    )
}
