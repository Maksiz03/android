package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.myapplication.viewmodel.MainViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerDetailScreen(viewModel: MainViewModel, playerId: Long) {
    // Здесь вы можете добавить дополнительную логику для отображения деталей игрока
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Player ID: $playerId")
        // Здесь будет ваша логика для отображения информации о игроке
    }
}
