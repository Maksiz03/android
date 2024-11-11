package com.example.myapplication.repository

import com.example.myapplication.model.Match

interface MatchRepository {
    suspend fun getRecentMatches(playerId: Long): List<Match>
    suspend fun getMatchesByIds(matchIds: Set<String>): List<Match> // New method
}
