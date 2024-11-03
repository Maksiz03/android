package com.example.myapplication.repository

import com.example.myapplication.model.Match

interface MatchRepository {
    suspend fun getRecentMatches(playerId: Long): List<Match>
}
