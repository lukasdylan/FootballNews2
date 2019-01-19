package com.lukasdylan.football.ui.team

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.extension.onFailed
import com.lukasdylan.core.extension.onSuccess
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.lukasdylan.footballservice.data.response.Player
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTeamViewModel(
    private val useCase: DetailTeamUseCase,
    dispatcherProviders: DispatcherProviders
) : BaseViewModel(dispatcherProviders) {

    private val _detailTeam = MutableLiveData<DetailTeam>()
    val toolbarTitle: LiveData<String> = Transformations.map(_detailTeam) {
        it?.teamName.orEmpty()
    }
    val bannerList: LiveData<List<String>> = Transformations.map(_detailTeam) {
        val imageList = mutableListOf<String>()
        imageList.add(it?.teamFanArt1.orEmpty())
        imageList.add(it?.teamFanArt2.orEmpty())
        imageList.add(it?.teamFanArt3.orEmpty())
        imageList.add(it?.teamFanArt4.orEmpty())
        return@map imageList.filter { url -> url.isNotBlank() }
    }
    val detailTeam: LiveData<DetailTeam> = _detailTeam

    private val _playerList = MutableLiveData<List<Player>>()
    val playerList: LiveData<List<Player>> = Transformations.map(_playerList) {
        it?.shuffled()?.take(10)
    }

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _detailTeam.value = getParcelable("detail_team")
            loadPlayerList()
        }
    }

    private fun loadPlayerList() {
        launch {
            val playersResult = withContext(dispatcherProviders.IO) {
                useCase.getPlayerList(_detailTeam.value?.idTeam.orEmpty())
            }
            playersResult
                .onSuccess {
                    _playerList.value = it.playerList
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }
}