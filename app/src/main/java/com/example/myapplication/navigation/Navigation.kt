package com.example.myapplication.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.MatchDetailScreen
import com.example.myapplication.ui.screens.MatchListScreen
import com.example.myapplication.ui.screens.FavoritesScreen
import com.example.myapplication.ui.SettingsScreen
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.datastore.FavoritesDataStore
import com.example.myapplication.datastore.PreferencesDataStore

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MatchViewModel,
    favoritesDataStore: FavoritesDataStore,
    preferencesDataStore: PreferencesDataStore,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        NavHost(navController = navController, startDestination = "matchList") {
            composable("matchList") {
                MatchListScreen(
                    navController = navController,
                    viewModel = viewModel,
                    preferencesDataStore = preferencesDataStore,
                    playerId = 301109979 // Example player ID
                )
            }
            composable("matchDetail/{matchId}/{playerSlot}/{kills}/{deaths}/{assists}") { backStackEntry ->
                val matchId = backStackEntry.arguments?.getString("matchId")?.toLong() ?: 0
                val playerSlot = backStackEntry.arguments?.getString("playerSlot")?.toInt() ?: 0
                val kills = backStackEntry.arguments?.getString("kills")?.toInt() ?: 0
                val deaths = backStackEntry.arguments?.getString("deaths")?.toInt() ?: 0
                val assists = backStackEntry.arguments?.getString("assists")?.toInt() ?: 0

                MatchDetailScreen(
                    matchId = matchId,
                    playerSlot = playerSlot,
                    kills = kills,
                    deaths = deaths,
                    assists = assists,
                    navController = navController,
                    favoritesDataStore = favoritesDataStore // Pass favoritesDataStore if needed
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    navController = navController,
                    viewModel = viewModel,
                    favoritesDataStore = favoritesDataStore // Ensure this is accepted in FavoritesScreen
                )
            }
            composable("settings") {
                SettingsScreen(
                    navController = navController,
                    preferencesDataStore = preferencesDataStore
                )
            }
        }
    }
}
