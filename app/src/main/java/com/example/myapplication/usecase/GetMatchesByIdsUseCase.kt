package com.example.myapplication.usecase

import com.example.myapplication.model.Match
import com.example.myapplication.repository.MatchRepository

class GetMatchesByIdsUseCase(private val matchRepository: MatchRepository) {
    suspend fun execute(ids: Set<Long>): List<Match> {
        return matchRepository.getMatchesByIds(ids)
    }
}
