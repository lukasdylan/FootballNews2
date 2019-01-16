package com.lukasdylan.football.ui.matchlist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.footballservice.data.response.DetailTeam

class MatchListViewModel(dispatcherProviders: DispatcherProviders) : BaseViewModel(dispatcherProviders) {

    private val _matchList = MutableLiveData<List<DetailMatch>>()
    val matchList: LiveData<List<DetailMatch>> = _matchList

    private val _leagueTeamDetailList = MutableLiveData<List<DetailTeam>>()
    val imageTeamMap: LiveData<Map<String, String>> = Transformations.map(_leagueTeamDetailList) { list ->
        list?.map { it.idTeam.orEmpty() to it.teamBadge.orEmpty() }?.toMap()
    }

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    private var matchType = 0

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _toolbarTitle.value = getString("league_name", "")
            _leagueTeamDetailList.value = getParcelableArrayList("league_teams")
            _matchList.value = getParcelableArrayList("match_list")
            matchType = getInt("match_type", 0)
        }
    }

    fun onSelectedMatch(params: Array<Pair<String, Any?>>) {
        setNavigationScreen(NavigationScreen(matchType, params))
    }
}