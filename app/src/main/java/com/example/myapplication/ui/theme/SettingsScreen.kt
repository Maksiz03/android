package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.datastore.PreferencesDataStore
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavHostController,  // Add navController as a parameter
    preferencesDataStore: PreferencesDataStore
) {
    var minKills by remember { mutableStateOf(0) }
    var maxDeaths by remember { mutableStateOf(0) }
    var minAssists by remember { mutableStateOf(0) }

    val coroutineScope = rememberCoroutineScope()

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

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Filter Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Kills filter
        Text(text = "Minimum Kills")
        Slider(
            value = minKills.toFloat(),
            onValueChange = { minKills = it.toInt() },
            valueRange = 0f..20f,
            steps = 19
        )

        // Deaths filter
        Text(text = "Maximum Deaths")
        Slider(
            value = maxDeaths.toFloat(),
            onValueChange = { maxDeaths = it.toInt() },
            valueRange = 0f..20f,
            steps = 19
        )

        // Assists filter
        Text(text = "Minimum Assists")
        Slider(
            value = minAssists.toFloat(),
            onValueChange = { minAssists = it.toInt() },
            valueRange = 0f..20f,
            steps = 19
        )

        // Apply button to save the settings
        Button(
            onClick = {
                coroutineScope.launch {
                    preferencesDataStore.saveMinKills(minKills)
                    preferencesDataStore.saveMaxDeaths(maxDeaths)
                    preferencesDataStore.saveMinAssists(minAssists)
                }
                // Navigate to MatchListScreen after saving
                navController.navigate("matchList")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Apply")
        }
    }
}

