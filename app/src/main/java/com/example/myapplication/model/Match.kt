package com.example.myapplication.model

data class Match(
    val match_id: Long,
    val player_slot: Int,
    val kills: Int,
    val deaths: Int,
    val assists: Int
)
