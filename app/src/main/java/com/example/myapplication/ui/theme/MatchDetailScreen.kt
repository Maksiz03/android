package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(
    matchId: Long,
    playerSlot: Int,
    kills: Int,
    deaths: Int,
    assists: Int,
    navController: NavController // Add NavController parameter
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("Match Details") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) { // Use navigateUp for back navigation
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Text(text = "Match ID: $matchId")
        Text(text = "Player Slot: $playerSlot")
        Text(text = "Kills: $kills")
        Text(text = "Deaths: $deaths")
        Text(text = "Assists: $assists")
        // Add more match details as needed
    }
}