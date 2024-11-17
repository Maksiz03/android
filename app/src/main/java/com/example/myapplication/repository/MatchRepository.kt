package com.example.myapplication.repository

import com.example.myapplication.model.Match

interface MatchRepository {
    suspend fun getRecentMatches(playerId: Long): List<Match>
    suspend fun getMatchesByIds(ids: Set<Long>): List<Match>
}