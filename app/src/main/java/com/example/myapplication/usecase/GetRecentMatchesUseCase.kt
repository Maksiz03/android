package com.example.myapplication.usecase

import com.example.myapplication.model.Match
import com.example.myapplication.repository.MatchRepository

class GetRecentMatchesUseCase(private val matchRepository: MatchRepository) {
    suspend fun execute(playerId: Long): List<Match> {
        return matchRepository.getRecentMatches(playerId)
    }
}
