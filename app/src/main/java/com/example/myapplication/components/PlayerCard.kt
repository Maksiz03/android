package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.model.Player
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme

@Composable
fun PlayerCard(player: Player, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(player.avatar),
                contentDescription = "Player Avatar",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = player.personaname, style = MaterialTheme.typography.titleMedium)
                player.rank_tier?.let {
                    Text(text = "Rank Tier: $it", style = MaterialTheme.typography.bodySmall)
                }
                Text(text = "Last Login: ${player.lastLogin}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
