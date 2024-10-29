package com.example.myapplication.network

import com.example.myapplication.model.Player
import com.example.myapplication.model.Match
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DotaApi {
    @GET("players")
    suspend fun getPlayers(
        @Query("filter") filter: String
    ): List<Player>

    @GET("players/{id}/recentMatches")
    suspend fun getRecentMatches(
        @Path("id") id: Long
    ): List<Match>
}
