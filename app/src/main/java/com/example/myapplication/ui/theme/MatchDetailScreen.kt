package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MatchDetailScreen(matchId: Long, playerSlot: Int, kills: Int, deaths: Int, assists: Int) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Match ID: $matchId")
        Text(text = "Player Slot: $playerSlot")
        Text(text = "Kills: $kills")
        Text(text = "Deaths: $deaths")
        Text(text = "Assists: $assists")
        // Add more match details as needed
    }
}
