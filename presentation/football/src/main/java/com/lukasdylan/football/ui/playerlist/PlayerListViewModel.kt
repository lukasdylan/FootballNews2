package com.lukasdylan.football.ui.playerlist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.footballservice.data.response.Player

internal const val NAVIGATE_FILTER_SCREEN = 100

class PlayerListViewModel(dispatcherProviders: DispatcherProviders) : BaseViewModel(dispatcherProviders) {

    private val _filteredPosition = MutableLiveData<List<String>>()
    private val _playerList = MutableLiveData<List<Player>>()
    val playerList: LiveData<List<Player>> = FilteredPlayersLiveData().apply {
        value = emptyList()

        addSource(_playerList) { setPlayerList(it.orEmpty()) }

        addSource(_filteredPosition) { setFilteredPosition(it.orEmpty()) }
    }

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    private var playerPositionMap = listOf<Pair<String, Int>>()

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _toolbarTitle.value = getString("team_name").orEmpty()
            val playerList = getParcelableArrayList("players") ?: emptyList<Player>()
            _playerList.value = playerList
            playerPositionMap = playerList.distinctBy { it.playerPosition }.map {
                val currentPosition = it.playerPosition.orEmpty()
                currentPosition to playerList.count { it.playerPosition == currentPosition }
            }.toList()
        }
    }

    fun onSelectedFilters(filters: List<String>) {
        _filteredPosition.value = filters
    }

    fun openFilterScreen() {
        val params = arrayOf<Pair<String, Any?>>(
            "filter_map" to playerPositionMap,
            "selected_filter" to _filteredPosition.value)
        setNavigationScreen(NavigationScreen(NAVIGATE_FILTER_SCREEN, params))
    }
}

class FilteredPlayersLiveData : MediatorLiveData<List<Player>>() {
    private var filteredPosition = emptyList<String>()
    private var playerList = emptyList<Player>()
    private var defaultFilterList = emptyList<String>()

    private fun updateValue() {
        value = playerList.asSequence()
            .filter { it.playerPosition in filteredPosition }
            .sortedBy { it.playerPosition }
            .toList()
    }

    fun setPlayerList(data: List<Player>) {
        playerList = data
        if (defaultFilterList.isEmpty()) {
            defaultFilterList = playerList.asSequence()
                .map { it.playerPosition.orEmpty() }
                .distinct()
                .toList()
            filteredPosition = defaultFilterList
        }
        updateValue()
    }

    fun setFilteredPosition(data: List<String>) {
        filteredPosition = if (data.isEmpty()) {
            defaultFilterList
        } else {
            data
        }
        updateValue()
    }
}