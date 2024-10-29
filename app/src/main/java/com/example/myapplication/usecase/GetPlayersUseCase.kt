package com.example.myapplication.usecase

import com.example.myapplication.model.Player
import com.example.myapplication.repository.PlayerRepository
import com.example.myapplication.repository.Result

class GetPlayersUseCase(private val playerRepository: PlayerRepository) {
    suspend operator fun invoke(filter: String): List<Player> {
        return when (val result = playerRepository.getPlayers(filter)) {
            is Result.Success -> result.value
            is Result.Error -> throw Exception(result.exception)
        }
    }
}
