package com.example.myapplication.network

import com.example.myapplication.model.Player
import com.example.myapplication.model.Match
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DotaApiImpl : DotaApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.opendota.com/api/") // Убедитесь, что это правильный базовый URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: DotaApi = retrofit.create(DotaApi::class.java)

    override suspend fun getPlayers(filter: String): List<Player> {
        return api.getPlayers(filter)
    }

    override suspend fun getRecentMatches(playerId: Long): List<Match> {
        return api.getRecentMatches(playerId)
    }
}
