package com.example.myapplication.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.datastore.BadgeCache
import com.example.myapplication.ui.screens.MatchDetailScreen
import com.example.myapplication.ui.screens.MatchListScreen
import com.example.myapplication.ui.screens.FavoritesScreen
import com.example.myapplication.ui.SettingsScreen
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.datastore.FavoritesDataStore
import com.example.myapplication.datastore.PreferencesDataStore
import com.example.myapplication.ui.SettingsScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.viewmodel.ProfileViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MatchViewModel,
    profileViewModel: ProfileViewModel,
    favoritesDataStore: FavoritesDataStore,
    preferencesDataStore: PreferencesDataStore,
    paddingValues: PaddingValues,
    badgeCache: BadgeCache
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
                    badgeCache = badgeCache, // Use existing badgeCache
                    playerId = 301109979 // Example player ID, consider making this dynamic
                )
            }
            composable(
                route = "matchDetail/{matchId}/{playerSlot}/{kills}/{deaths}/{assists}",
                arguments = listOf(
                    navArgument("matchId") { type = NavType.LongType },
                    navArgument("playerSlot") { type = NavType.IntType },
                    navArgument("kills") { type = NavType.IntType },
                    navArgument("deaths") { type = NavType.IntType },
                    navArgument("assists") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val matchId = backStackEntry.arguments?.getLong("matchId") ?: 0L
                val playerSlot = backStackEntry.arguments?.getInt("playerSlot") ?: 0
                val kills = backStackEntry.arguments?.getInt("kills") ?: 0
                val deaths = backStackEntry.arguments?.getInt("deaths") ?: 0
                val assists = backStackEntry.arguments?.getInt("assists") ?: 0

                MatchDetailScreen(
                    matchId = matchId,
                    playerSlot = playerSlot,
                    kills = kills,
                    deaths = deaths,
                    assists = assists,
                    navController = navController,
                    favoritesDataStore = favoritesDataStore
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    navController = navController,
                    viewModel = viewModel,
                    favoritesDataStore = favoritesDataStore
                )
            }
            composable("settings") {
                SettingsScreen(
                    navController = navController,
                    preferencesDataStore = preferencesDataStore
                )
            }
            composable("profile") {  // Add the ProfileScreen route
                ProfileScreen(
                    viewModel = profileViewModel,
                )
            }
        }
    }
}