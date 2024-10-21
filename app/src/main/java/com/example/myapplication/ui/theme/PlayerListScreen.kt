package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.components.PlayerCard
import com.example.myapplication.model.Player

@Composable
fun PlayerListScreen(navController: NavHostController, paddingValues: PaddingValues) {
    // Example data; replace with actual API data
    val players = listOf(
        Player(1, "Player One", "https://avatar.url/1", "2023-10-20", 1),
        Player(2, "Player Two", "https://avatar.url/2", "2023-10-21", 2)
    )

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(players) { player ->
            PlayerCard(player = player) {
                navController.navigate("playerDetail/${player.accountId}")
            }
        }
    }
}
