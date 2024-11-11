package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.datastore.FavoritesDataStore
import com.example.myapplication.model.Match
import com.example.myapplication.viewmodel.UiState
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MatchViewModel,
    favoritesDataStore: FavoritesDataStore
) {
    // Collect the set of favorite match IDs from the data store
    val favorites by favoritesDataStore.favoritesFlow.collectAsState(initial = emptySet())
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Coroutine scope for launching asynchronous operations

    // Fetch favorite matches when the list of favorites changes
    LaunchedEffect(favorites) {
        if (favorites.isNotEmpty()) {
            viewModel.getFavoriteMatches(favorites) // Ensure this method is implemented in your ViewModel
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
                    matches.forEach { match ->
                        Text(
                            text = "Match ID: ${match.match_id} | K: ${match.kills} D: ${match.deaths} A: ${match.assists}",
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate("matchDetail/${match.match_id}/${match.player_slot}/${match.kills}/${match.deaths}/${match.assists}")
                                }
                        )
                    }
                }
            }
            is UiState.Error -> {
                Text(text = "Error: ${(uiState as UiState.Error).message}")
            }
            else -> {
                // Handle any other states if necessary
            }
        }
    }
}
