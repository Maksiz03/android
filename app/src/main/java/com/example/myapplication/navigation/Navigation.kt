package com.example.myapplication.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.MatchDetailScreen
import com.example.myapplication.ui.screens.MatchListScreen
import com.example.myapplication.viewmodel.MatchViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

@Composable
fun Navigation(navController: NavHostController, viewModel: MatchViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Adding NavHost for navigation between screens
        NavHost(navController = navController, startDestination = "matchList") {
            composable("matchList") {
                MatchListScreen(navController = navController, viewModel = viewModel, playerId = 301109979)
            }
            composable("matchDetail/{matchId}/{playerSlot}/{kills}/{deaths}/{assists}") { backStackEntry ->
                val matchId = backStackEntry.arguments?.getString("matchId")?.toLong() ?: 0
                val playerSlot = backStackEntry.arguments?.getString("playerSlot")?.toInt() ?: 0
                val kills = backStackEntry.arguments?.getString("kills")?.toInt() ?: 0
                val deaths = backStackEntry.arguments?.getString("deaths")?.toInt() ?: 0
                val assists = backStackEntry.arguments?.getString("assists")?.toInt() ?: 0

                MatchDetailScreen(matchId = matchId, playerSlot = playerSlot, kills = kills, deaths = deaths, assists = assists)
            }
        }
        // Placing BottomNavigationBar at the bottom of the screen
        BottomNavigationBar(navController = navController)
    }
}
