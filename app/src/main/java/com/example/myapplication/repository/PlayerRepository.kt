package com.example.myapplication.repository

import com.example.myapplication.model.Player
import com.example.myapplication.model.Match

interface PlayerRepository {
    suspend fun getPlayers(filter: String): Result<List<Player>>
    suspend fun getRecentMatches(playerId: Long): Result<List<Match>> // Метод для получения недавних матчей
}
