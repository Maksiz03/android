package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.datastore.PreferencesDataStore
import com.example.myapplication.model.Match
import com.example.myapplication.viewmodel.UiState
import kotlinx.coroutines.launch

@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchViewModel,
    preferencesDataStore: PreferencesDataStore,
    playerId: Long,
) {
    val uiState by viewModel.uiState.collectAsState()

    var minKills by remember { mutableStateOf(0) }
    var maxDeaths by remember { mutableStateOf(Int.MAX_VALUE) }
    var minAssists by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()

    // Load filter preferences from DataStore
    LaunchedEffect(preferencesDataStore) {
        launch {
            preferencesDataStore.minKillsFlow.collect { minKills = it }
        }
        launch {
            preferencesDataStore.maxDeathsFlow.collect { maxDeaths = it }
        }
        launch {
            preferencesDataStore.minAssistsFlow.collect { minAssists = it }
        }
    }

    // Fetch matches when the screen loads
    LaunchedEffect(playerId) {
        viewModel.getRecentMatches(playerId)
    }

    // Automatically save filter settings when they change
    DisposableEffect(minKills, maxDeaths, minAssists) {
        scope.launch {
            preferencesDataStore.saveMinKills(minKills)
            preferencesDataStore.saveMaxDeaths(maxDeaths)
            preferencesDataStore.saveMinAssists(minAssists)
        }
        onDispose { }
    }

    Column {
        // Add FilterControls to adjust filter values dynamically
        FilterControls(
            minKills = minKills,
            onMinKillsChange = { minKills = it },
            maxDeaths = maxDeaths,
            onMaxDeathsChange = { maxDeaths = it },
            minAssists = minAssists,
            onMinAssistsChange = { minAssists = it }
        )

        when (uiState) {
            is UiState.Loading -> {
                Text(text = "Loading...")
            }
            is UiState.Success -> {
                val matches = (uiState as UiState.Success<List<Match>>).data
                Log.d("MatchListScreen", "Filters - Min Kills: $minKills, Max Deaths: $maxDeaths, Min Assists: $minAssists")

                val filteredMatches = matches.filter { match ->
                    Log.d("MatchListScreen", "Match ID: ${match.match_id} | K: ${match.kills}, D: ${match.deaths}, A: ${match.assists}")
                    match.kills >= minKills && match.deaths <= maxDeaths && match.assists >= minAssists
                }

                Log.d("MatchListScreen", "Filtered Matches Count: ${filteredMatches.size}")

                if (filteredMatches.isEmpty()) {
                    Text(text = "No matches found with the current filter settings.")
                } else {
                    filteredMatches.forEach { match ->
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
        }
    }
}

@Composable
fun FilterControls(
    minKills: Int,
    onMinKillsChange: (Int) -> Unit,
    maxDeaths: Int,
    onMaxDeathsChange: (Int) -> Unit,
    minAssists: Int,
    onMinAssistsChange: (Int) -> Unit
) {
    Column {
        Text(text = "Min Kills: $minKills")
        Slider(
            value = minKills.toFloat(),
            onValueChange = { onMinKillsChange(it.toInt()) },
            valueRange = 0f..100f
        )
        Text(text = "Max Deaths: $maxDeaths")
        Slider(
            value = maxDeaths.toFloat(),
            onValueChange = { onMaxDeathsChange(it.toInt()) },
            valueRange = 0f..100f
        )
        Text(text = "Min Assists: $minAssists")
        Slider(
            value = minAssists.toFloat(),
            onValueChange = { onMinAssistsChange(it.toInt()) },
            valueRange = 0f..100f
        )
    }
}