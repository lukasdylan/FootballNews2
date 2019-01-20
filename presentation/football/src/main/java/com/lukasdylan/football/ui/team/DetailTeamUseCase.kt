package com.lukasdylan.football.ui.team

import com.lukasdylan.core.utility.ErrorWrapper
import com.lukasdylan.footballservice.data.database.DetailTeamDao
import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.lukasdylan.footballservice.data.response.PlayerResponse
import com.lukasdylan.footballservice.data.service.FootballApiServices
import com.lukasdylan.newsservice.data.NewsApiServices
import com.lukasdylan.newsservice.data.NewsResponse
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

interface DetailTeamUseCase {
    suspend fun getPlayerList(teamId: String): Result<PlayerResponse>
    suspend fun checkIsFavoriteTeam(teamId: String): DetailTeam?
    suspend fun saveAsFavoriteTeam(detailTeam: DetailTeam, onSuccess: () -> Unit, onFailed: (ErrorWrapper) -> Unit)
    suspend fun deleteFavoriteTeam(detailTeam: DetailTeam, onSuccess: () -> Unit, onFailed: (ErrorWrapper) -> Unit)
    suspend fun getNewsByTeamName(teamName: String): Result<NewsResponse>
}

class DetailTeamUseCaseImpl(
    private val apiServices: FootballApiServices,
    private val newsApiServices: NewsApiServices,
    private val detailTeamDao: DetailTeamDao
) : DetailTeamUseCase {

    override suspend fun getNewsByTeamName(teamName: String): Result<NewsResponse> {
        return newsApiServices.fetchNewsByLeague(leagueName = teamName, pageSize = 5).awaitResult()
    }

    override suspend fun checkIsFavoriteTeam(teamId: String): DetailTeam? {
        return detailTeamDao.checkIsFavoriteTeam(teamId)
    }

    override suspend fun saveAsFavoriteTeam(
        detailTeam: DetailTeam,
        onSuccess: () -> Unit,
        onFailed: (ErrorWrapper) -> Unit
    ) {
        try {
            detailTeamDao.saveFavoriteTeam(detailTeam)
            onSuccess()
        } catch (ex: Exception) {
            AnkoLogger(this::class.java).error { ex.localizedMessage }
            onFailed(ErrorWrapper.invoke(ex))
        }
    }

    override suspend fun deleteFavoriteTeam(
        detailTeam: DetailTeam,
        onSuccess: () -> Unit,
        onFailed: (ErrorWrapper) -> Unit
    ) {
        try {
            detailTeamDao.deleteFavoriteTeam(detailTeam)
            onSuccess()
        } catch (ex: Exception) {
            AnkoLogger(this::class.java).error { ex.localizedMessage }
            onFailed(ErrorWrapper.invoke(ex))
        }
    }

    override suspend fun getPlayerList(teamId: String): Result<PlayerResponse> {
        return apiServices.fetchPlayersTeam(teamId).awaitResult()
    }

}