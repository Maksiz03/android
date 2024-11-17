package com.example.myapplication.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings

// Define the navigation items including the new Favorites item
sealed class NavigationItem(val title: String, val icon: ImageVector, val route: String) {
    object MatchList : NavigationItem("Matches", Icons.Filled.List, "matchList")
    object MatchDetail : NavigationItem("Match Details", Icons.Filled.Info, "matchDetail/{matchId}/{playerSlot}/{kills}/{deaths}/{assists}")
    object Settings : NavigationItem("Settings", Icons.Filled.Settings, "settings")
    object Favorites : NavigationItem("Favorites", Icons.Filled.Favorite, "favorites") // Новый элемент
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    BottomNavigation {
        val items = listOf(
            NavigationItem.MatchList,
            NavigationItem.Favorites, // Добавьте "Избранное" в список навигации
            NavigationItem.Settings
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
