package com.example.myapplication.repository

import com.example.myapplication.model.Player
import com.example.myapplication.model.Match
import com.example.myapplication.network.DotaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepositoryImpl(private val api: DotaApi) : PlayerRepository {
    override suspend fun getPlayers(filter: String): Result<List<Player>> {
        return withContext(Dispatchers.IO) {
            try {
                val players = api.getPlayers(filter)
                Result.success(players)
            } catch (e: Exception) {
                Result.error(e)
            }
        }
    }

    override suspend fun getRecentMatches(playerId: Long): Result<List<Match>> {
        return withContext(Dispatchers.IO) {
            try {
                val matches = api.getRecentMatches(playerId)
                Result.success(matches)
            } catch (e: Exception) {
                Result.error(e)
            }
        }
    }
}
