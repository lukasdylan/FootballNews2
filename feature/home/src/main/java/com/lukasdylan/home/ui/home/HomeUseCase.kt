package com.lukasdylan.home.ui.home

import android.content.SharedPreferences
import com.lukasdylan.core.extension.getObject
import com.lukasdylan.core.extension.saveObject
import com.lukasdylan.footballservice.data.model.League
import com.lukasdylan.footballservice.data.response.DetailMatchResponse
import com.lukasdylan.footballservice.data.response.DetailTeamResponse
import com.lukasdylan.footballservice.data.response.StandingResponse
import com.lukasdylan.footballservice.data.service.FootballApiServices
import com.lukasdylan.newsservice.data.NewsApiServices
import com.lukasdylan.newsservice.data.NewsResponse
import retrofit2.Call
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

interface HomeUseCase {
    suspend fun getDefaultLeague(): League
    fun setDefaultLeague(league: League)
    suspend fun getLeagueTeamsData(leagueId: String): Result<DetailTeamResponse>
    suspend fun getStandingsData(leagueId: String): Result<StandingResponse>
    suspend fun getMatchesDataByType(matchType: Int, leagueId: String): Result<DetailMatchResponse>
    suspend fun getTrendingNewsByLeagueName(leagueName: String): Result<NewsResponse>
}

class HomeUseCaseImpl(
    private val service: FootballApiServices,
    private val newsService: NewsApiServices,
    private val preferences: SharedPreferences
) : HomeUseCase {

    override suspend fun getTrendingNewsByLeagueName(leagueName: String): Result<NewsResponse> {
        return newsService.fetchNewsByLeague(leagueName = leagueName).awaitResult()
    }

    override fun setDefaultLeague(league: League) {
        return preferences.saveObject("League", league)
    }

    override suspend fun getDefaultLeague(): League {
        return preferences.getObject("League") ?: League.defaultLeague
    }

    override suspend fun getLeagueTeamsData(leagueId: String): Result<DetailTeamResponse> {
        return service.fetchLeagueTeams(leagueId).awaitResult()
    }

    override suspend fun getMatchesDataByType(matchType: Int, leagueId: String): Result<DetailMatchResponse> {
        return getServiceByType(matchType, leagueId).awaitResult()
    }

    override suspend fun getStandingsData(leagueId: String): Result<StandingResponse> {
        val header = HashMap<String, String>()
        header["s"] = "1819"
        header["l"] = leagueId
        return service.fetchLeagueStandings(header).awaitResult()
    }

    private fun getServiceByType(matchType: Int, leagueId: String): Call<DetailMatchResponse> {
        return if (matchType == PREVIOUS_MATCH_TYPE) {
            service.fetchPreviousLeagueMatch(leagueId)
        } else {
            service.fetchNextLeagueMatch(leagueId)
        }
    }

}