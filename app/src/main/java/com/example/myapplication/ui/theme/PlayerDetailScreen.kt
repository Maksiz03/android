package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.model.Player

@Composable
fun PlayerDetailScreen(accountId: String) {
    // Здесь вы можете получить данные игрока по accountId
    // Пример данных (замените на реальный вызов API)
    val player = Player(
        accountId.toLong(),
        "Player Name",
        "https://avatar.url/$accountId",
        "2023-10-21",
        1
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = player.personaname, style = MaterialTheme.typography.titleLarge)
        Image(
            painter = rememberImagePainter(player.avatar),
            contentDescription = "Avatar",
            modifier = Modifier.size(100.dp).padding(16.dp)
        )
        player.rank_tier?.let {
            Text(text = "Rank Tier: $it", style = MaterialTheme.typography.bodyMedium)
        }
        Text(text = "Last Login: ${player.lastLogin}", style = MaterialTheme.typography.bodyMedium)
    }
}
