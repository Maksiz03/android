package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.UiState
import androidx.compose.foundation.layout.PaddingValues
import com.example.myapplication.model.Player

@Composable
fun PlayerListScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    uiState: UiState<List<Player>>, // Specify the type argument as List<Player>
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        when (uiState) {
            is UiState.Loading -> {
                Text(text = "Loading...")
            }
            is UiState.Success -> {
                val players = uiState.data // Use 'data' instead of 'players'
                players.forEach { player ->
                    Text(
                        text = player.name,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            is UiState.Error -> {
                Text(text = "Error: ${uiState.message}")
            }
        }
    }
}
