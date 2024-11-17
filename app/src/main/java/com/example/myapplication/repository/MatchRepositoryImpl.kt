package com.example.myapplication.repository

import com.example.myapplication.dao.FavoriteDao
import com.example.myapplication.entities.FavoriteItem
import com.example.myapplication.model.Match
import com.example.myapplication.network.DotaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatchRepositoryImpl(
    private val favoriteDao: FavoriteDao,
    private val dotaApi: DotaApi
) : MatchRepository {

    override suspend fun getRecentMatches(playerId: Long): List<Match> {
        return withContext(Dispatchers.IO) {
            try {
                // Fetch recent matches from the network
                val recentMatches = dotaApi.getRecentMatches(playerId)

                // Convert network matches to FavoriteItems and store them locally
                val favoriteItems = recentMatches.map { match ->
                    FavoriteItem(
                        matchId = match.match_id,
                        matchDetails = "Kills: ${match.kills}, Deaths: ${match.deaths}, Assists: ${match.assists}"
                    )
                }
                favoriteDao.insertFavorites(favoriteItems)

                // Return the matches
                recentMatches
            } catch (e: Exception) {
                // Handle exception (e.g., network error), return cached data
                favoriteDao.getAllFavorites().map { favoriteItem ->
                    // Convert FavoriteItem back to Match model
                    Match(
                        match_id = favoriteItem.matchId,
                        kills = 0, // Replace with actual data if available
                        deaths = 0,
                        assists = 0,
                        player_slot = 0 // Replace with actual data if available
                    )
                }
            }
        }
    }

    override suspend fun getMatchesByIds(matchIds: Set<Long>): List<Match> {
        return withContext(Dispatchers.IO) {
            // Fetch matches by IDs from the local database
            val favoriteItems = favoriteDao.getMatchesByIds(matchIds.toList())

            // Convert FavoriteItem back to Match model
            favoriteItems.map { favoriteItem ->
                Match(
                    match_id = favoriteItem.matchId,
                    kills = 0, // Replace with actual data if available
                    deaths = 0,
                    assists = 0,
                    player_slot = 0 // Replace with actual data if available
                )
            }
        }
    }
}
