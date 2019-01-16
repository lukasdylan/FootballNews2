package com.lukasdylan.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.extension.onFailed
import com.lukasdylan.core.extension.onSuccess
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.core.utility.SingleLiveEvent
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.footballservice.data.model.League
import com.lukasdylan.footballservice.data.response.DetailTeam
import com.lukasdylan.footballservice.data.response.Standings
import com.lukasdylan.home.ui.home.adapter.NAVIGATE_ALL_PREVIOUS_MATCH_SCREEN
import com.lukasdylan.home.ui.home.adapter.NAVIGATE_DETAIL_TEAM_SCREEN
import com.lukasdylan.home.ui.home.adapter.NAVIGATE_STANDINGS_SCREEN
import com.lukasdylan.newsservice.data.Article
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal const val PREVIOUS_MATCH_TYPE = 100
internal const val NEXT_MATCH_TYPE = 101

class HomeViewModel(private val useCase: HomeUseCase, dispatcherProviders: DispatcherProviders) :
    BaseViewModel(dispatcherProviders) {

    private val _standingsList = MutableLiveData<List<Standings>>()
    val standingsList: LiveData<List<Standings>> = Transformations.map(_standingsList) {
        it?.take(6)
    }

    private val _previousMatchList = MutableLiveData<List<DetailMatch>>()
    val previousMatchList: LiveData<List<DetailMatch>> = Transformations.map(_previousMatchList) {
        it?.take(6)
    }

    private val _nextMatchList = MutableLiveData<List<DetailMatch>>()
    val nextMatchList: LiveData<List<DetailMatch>> = Transformations.map(_nextMatchList) {
        it?.take(6)
    }

    private val _leagueTeamDetailList = MutableLiveData<List<DetailTeam>>()
    val imageTeamMap: LiveData<Map<String, String>> = Transformations.map(_leagueTeamDetailList) { list ->
        list?.map { it.idTeam.orEmpty() to it.teamBadge.orEmpty() }?.toMap()
    }

    private val _leagueTrendingNewsList = MutableLiveData<List<Article>>()
    val leagueTrendingNewsList: LiveData<List<Article>> = _leagueTrendingNewsList

    private val _selectedLeague = MutableLiveData<League>()
    val selectedLeague: LiveData<League> = _selectedLeague

    private val _reloadHomeAdapterEvent = SingleLiveEvent<Unit>()
    val reloadHomeAdapterEvent: LiveData<Unit> = _reloadHomeAdapterEvent

    fun loadHomeView() {
        launch {
            val league = withContext(dispatcherProviders.IO) {
                useCase.getDefaultLeague()
            }
            _selectedLeague.value = league
            loadLeagueTeamsData()
            loadLeagueTrendingNews(leagueName = league.leagueName)
        }
    }

    fun onSelectedLeague(league: League) {
        if (_selectedLeague.value != league) {
            coroutineContext.cancelChildren()
            useCase.setDefaultLeague(league)
            _leagueTrendingNewsList.value = null
            _standingsList.value = null
            _previousMatchList.value = null
            _nextMatchList.value = null
            _leagueTeamDetailList.value = null
            _reloadHomeAdapterEvent.call()
        }
    }

    fun openStandingsScreen() {
        val params = arrayOf(
            "league_name" to _selectedLeague.value?.leagueName,
            "league_teams" to _leagueTeamDetailList.value,
            "standings" to _standingsList.value
        )
        setNavigationScreen(NavigationScreen(NAVIGATE_STANDINGS_SCREEN, params))
    }

    fun openAllMatchListScreen(type: Int = 0) {
        val params = arrayOf(
            "league_name" to _selectedLeague.value?.leagueName,
            "league_teams" to _leagueTeamDetailList.value,
            "match_list" to _previousMatchList.value,
            "match_type" to type
        )
        setNavigationScreen(NavigationScreen(NAVIGATE_ALL_PREVIOUS_MATCH_SCREEN, params))
    }

    fun openDetailTeamScreen(teamId: String) {
        val team = _leagueTeamDetailList.value?.firstOrNull { it.idTeam == teamId }
        val params = arrayOf<Pair<String, Any?>>("detail_team" to team)
        setNavigationScreen(NavigationScreen(NAVIGATE_DETAIL_TEAM_SCREEN, params))
    }

    private fun loadLeagueTeamsData() {
        launch {
            val leagueId = _selectedLeague.value?.leagueId.orEmpty()
            val teamsResult = withContext(dispatcherProviders.IO) {
                useCase.getLeagueTeamsData(leagueId)
            }
            teamsResult
                .onSuccess {
                    _leagueTeamDetailList.value = it.listTeam
                    loadNextMatch(leagueId)
                    loadStandings(leagueId)
                    loadPreviousMatch(leagueId)
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }

    private fun loadStandings(leagueId: String) {
        launch {
            val standingsResult = withContext(dispatcherProviders.IO) {
                useCase.getStandingsData(leagueId)
            }
            standingsResult
                .onSuccess {
                    _standingsList.value = it.standingList
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }

    private fun loadPreviousMatch(leagueId: String) {
        launch {
            val previousMatchResult = withContext(dispatcherProviders.IO) {
                useCase.getMatchesDataByType(PREVIOUS_MATCH_TYPE, leagueId)
            }
            previousMatchResult
                .onSuccess {
                    _previousMatchList.value = it.listEvent
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }

    private fun loadNextMatch(leagueId: String) {
        launch {
            val nextMatchResult = withContext(dispatcherProviders.IO) {
                useCase.getMatchesDataByType(NEXT_MATCH_TYPE, leagueId)
            }
            nextMatchResult
                .onSuccess {
                    _nextMatchList.value = it.listEvent
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }

    private fun loadLeagueTrendingNews(leagueName: String) {
        launch {
            val trendingNewsResult = withContext(dispatcherProviders.IO) {
                useCase.getTrendingNewsByLeagueName(leagueName)
            }
            trendingNewsResult
                .onSuccess {
                    _leagueTrendingNewsList.value = it.articles
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }
}