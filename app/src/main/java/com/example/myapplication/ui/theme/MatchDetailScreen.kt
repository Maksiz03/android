package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.datastore.FavoritesDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(
    matchId: Long,
    playerSlot: Int,
    kills: Int,
    deaths: Int,
    assists: Int,
    navController: NavController,
    favoritesDataStore: FavoritesDataStore
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Match Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            try {
                                if (!favoritesDataStore.isFavorite(matchId)) {
                                    favoritesDataStore.addFavorite(matchId)
                                    snackbarHostState.showSnackbar("Added to favorites")
                                } else {
                                    snackbarHostState.showSnackbar("Already in favorites")
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Failed to add to favorites")
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Add to Favorites")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = "Match ID: $matchId")
            Text(text = "Player Slot: $playerSlot")
            Text(text = "Kills: $kills")
            Text(text = "Deaths: $deaths")
            Text(text = "Assists: $assists")
            // Добавьте больше деталей матча, если необходимо
        }
    }
}