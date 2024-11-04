package com.example.myapplication.network

import com.example.myapplication.model.Match
import retrofit2.http.GET
import retrofit2.http.Path

interface DotaApi {
    @GET("players/{playerId}/recentMatches")
    suspend fun getRecentMatches(@Path("playerId") playerId: Long): List<Match>
}
