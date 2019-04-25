package com.lukasdylan.football.ui.standings

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.lukasdylan.footballservice.data.response.Standings

class StandingsViewModel(dispatcherProviders: DispatcherProviders) : BaseViewModel(dispatcherProviders) {

    private val _standingsList = MutableLiveData<List<Standings>>()
    val standingsList: LiveData<List<Standings>> = _standingsList

    private val _leagueTeamDetailList = MutableLiveData<List<DetailTeam>>()
    val imageTeamMap: LiveData<Map<String, String>> = Transformations.map(_leagueTeamDetailList) { list ->
        list?.map { it.idTeam to it.teamBadge.orEmpty() }?.toMap()
    }

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    fun loadData(bundle: Bundle) = with(bundle) {
        _toolbarTitle.value = getString("league_name", "")
        _leagueTeamDetailList.value = getParcelableArrayList("league_teams")
        _standingsList.value = getParcelableArrayList("standings")
    }

    fun onSelectedTeam(teamId: String) {
        val detailTeam = _leagueTeamDetailList.value?.find { it.idTeam == teamId }
        val params = arrayOf<Pair<String, Any?>>("detail_team" to detailTeam)
        setNavigationScreen(NavigationScreen(0, params))
    }

}