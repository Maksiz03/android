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
import androidx.compose.material.icons.filled.Person

// Define the navigation items
sealed class NavigationItem(val title: String, val icon: ImageVector, val route: String) {
    object MatchList : NavigationItem("Matches", Icons.Filled.List, "matchList")
    object Favorites : NavigationItem("Favorites", Icons.Filled.Favorite, "favorites")
    object Settings : NavigationItem("Settings", Icons.Filled.Settings, "settings")
    object Profile : NavigationItem("Profile", Icons.Filled.Person, "profile")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    BottomNavigation {
        val items = listOf(
            NavigationItem.MatchList,
            NavigationItem.Favorites,
            NavigationItem.Settings,
            NavigationItem.Profile
        )

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Navigate to the destination, considering the navigation state
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
