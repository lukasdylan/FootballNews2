package com.lukasdylan.football.ui.team

import com.lukasdylan.footballservice.data.response.PlayerResponse
import com.lukasdylan.footballservice.data.service.FootballApiServices
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

interface DetailTeamUseCase {
    suspend fun getPlayerList(teamId: String): Result<PlayerResponse>
}

class DetailTeamUseCaseImpl(private val apiServices: FootballApiServices) : DetailTeamUseCase {

    override suspend fun getPlayerList(teamId: String): Result<PlayerResponse> {
        return apiServices.fetchPlayersTeam(teamId).awaitResult()
    }

}