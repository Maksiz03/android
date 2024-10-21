package com.example.myapplication.model

data class Player(
    val accountId: Long,
    val personaname: String,
    val avatar: String,
    val lastLogin: String,
    val rank_tier: Int?
)
