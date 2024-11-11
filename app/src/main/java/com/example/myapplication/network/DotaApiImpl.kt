package com.example.myapplication.network

import com.example.myapplication.model.Match
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DotaApiImpl : DotaApi {

    private val api: DotaApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.opendota.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(DotaApi::class.java)
    }

    override suspend fun getRecentMatches(playerId: Long): List<Match> {
        return api.getRecentMatches(playerId)
    }

    override suspend fun getMatchesByIds(matchIds: Set<String>): List<Match> {
        // Assuming the API supports querying multiple match IDs
        return api.getMatchesByIds(matchIds)
    }
}
