package com.example.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.PlayerDetailScreen
import com.example.myapplication.ui.screens.RecentMatchesScreen
import com.example.myapplication.viewmodel.MainViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = "playerList") {
        composable("playerList") {
            // Здесь можно добавить экран со списком игроков
            // PlayerListScreen(navController = navController, viewModel = viewModel)
        }
        composable("playerDetail/{playerId}") { backStackEntry ->
            val playerId = backStackEntry.arguments?.getString("playerId")?.toLongOrNull()
            if (playerId != null) {
                RecentMatchesScreen(viewModel = viewModel, playerId = playerId) // Передаём playerId в экран
            }
        }
    }
}
