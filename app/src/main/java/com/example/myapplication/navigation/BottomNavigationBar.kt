package com.example.myapplication.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    BottomNavigation {
        val items = listOf(
            NavigationItem.PlayerList,
            NavigationItem.PlayerDetail
        )

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    val route = if (item is NavigationItem.PlayerDetail) {
                        "playerDetail/1" // Здесь должен быть ID игрока
                    } else {
                        item.route
                    }
                    navController.navigate(route) {
                        popUpTo(NavigationItem.PlayerList.route) { inclusive = false }
                    }
                }
            )
        }
    }
}

sealed class NavigationItem(val title: String, val icon: ImageVector, val route: String) {
    object PlayerList : NavigationItem("Players", Icons.Filled.List, "playerList")
    object PlayerDetail : NavigationItem("Details", Icons.Filled.Info, "playerDetail/{playerId}")
}
