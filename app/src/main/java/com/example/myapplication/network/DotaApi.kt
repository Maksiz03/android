package com.example.myapplication.network

import com.example.myapplication.model.Player
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Определите интерфейс API
interface DotaApi {
    @GET("players") // Замените на реальный путь к вашему API
    suspend fun getPlayers(): List<Player>

    @GET("players/{accountId}") // Замените на реальный путь к вашему API
    suspend fun getPlayerById(@Path("accountId") accountId: Long): Player

    companion object {
        private const val BASE_URL = "https://api.example.com/" // Замените на ваш базовый URL

        fun create(): DotaApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(DotaApi::class.java)
        }
    }
}