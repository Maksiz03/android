package com.example.myapplication.network

import com.example.myapplication.model.Match
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DotaApi {

    @GET("players/{playerId}/recentMatches")
    suspend fun getRecentMatches(@Path("playerId") playerId: Long): List<Match>

    @GET("matches")
    suspend fun getMatchesByIds(@Query("match_id") matchIds: Set<String>): List<Match> // Example query
}
