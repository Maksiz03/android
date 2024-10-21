package com.example.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.PlayerListScreen
import com.example.myapplication.ui.screens.PlayerDetailScreen

@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = "playerList") {
        composable("playerList") { PlayerListScreen(navController, paddingValues) }
        composable("playerDetail/{accountId}") { backStackEntry ->
            val accountId = backStackEntry.arguments?.getString("accountId") ?: ""
            PlayerDetailScreen(accountId = accountId)
        }
    }
}
