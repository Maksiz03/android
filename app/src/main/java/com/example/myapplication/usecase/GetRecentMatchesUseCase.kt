package com.example.myapplication.usecase

import com.example.myapplication.model.Match
import com.example.myapplication.repository.PlayerRepository
import com.example.myapplication.repository.Result

class GetRecentMatchesUseCase(private val playerRepository: PlayerRepository) {

    suspend operator fun invoke(playerId: Long): List<Match> {
        return when (val result = playerRepository.getRecentMatches(playerId)) {
            is Result.Success -> result.value
            is Result.Error -> throw Exception(result.exception)
        }
    }
}
