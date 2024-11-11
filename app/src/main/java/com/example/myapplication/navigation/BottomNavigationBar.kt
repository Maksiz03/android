package com.example.myapplication.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

// Define the navigation items including the new Settings item
sealed class NavigationItem(val title: String, val icon: ImageVector, val route: String) {
    object MatchList : NavigationItem("Matches", Icons.Filled.List, "matchList")
    object MatchDetail : NavigationItem("Match Details", Icons.Filled.Info, "matchDetail/{matchId}/{playerSlot}/{kills}/{deaths}/{assists}")
    object Settings : NavigationItem("Settings", Icons.Filled.Settings, "settings")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    BottomNavigation {
        val items = listOf(
            NavigationItem.MatchList,
            NavigationItem.MatchDetail,
            NavigationItem.Settings  // Add Settings to the navigation items
        )

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute?.startsWith(item.route.substringBefore("/{")) == true,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}