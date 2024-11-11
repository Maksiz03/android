package com.example.myapplication.repository

import com.example.myapplication.model.Match
import com.example.myapplication.network.DotaApi

class MatchRepositoryImpl(private val dotaApi: DotaApi) : MatchRepository {

    override suspend fun getRecentMatches(playerId: Long): List<Match> {
        return dotaApi.getRecentMatches(playerId)
    }

    override suspend fun getMatchesByIds(matchIds: Set<String>): List<Match> {
        return dotaApi.getMatchesByIds(matchIds)
    }
}
