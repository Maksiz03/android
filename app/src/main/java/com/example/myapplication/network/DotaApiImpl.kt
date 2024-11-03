package com.example.myapplication.network

import com.example.myapplication.model.Match
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DotaApiImpl : DotaApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.opendota.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Например, добавьте метод для получения матчей
    override suspend fun getRecentMatches(playerId: Long): List<Match> {
        return retrofit.create(DotaApi::class.java).getRecentMatches(playerId)
    }
}
