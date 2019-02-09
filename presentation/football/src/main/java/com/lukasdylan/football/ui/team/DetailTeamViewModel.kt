package com.lukasdylan.football.ui.team

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.extension.onFailed
import com.lukasdylan.core.extension.onSuccess
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.ErrorWrapper
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.core.utility.SingleLiveEvent
import com.lukasdylan.football.ui.team.adapter.NAVIGATE_ALL_NEWS_SCREEN
import com.lukasdylan.football.ui.team.adapter.NAVIGATE_ALL_PLAYER_SCREEN
import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.lukasdylan.footballservice.data.response.Player
import com.lukasdylan.newsservice.data.NewsResponse
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

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse> = _newsResponse

    private val _isFavoriteMatch = MutableLiveData<Boolean>()
    val isFavoriteMatch: LiveData<Boolean> = _isFavoriteMatch

    private val _successSnackBarEvent = SingleLiveEvent<String>()
    val successSnackBarEvent: LiveData<String> = _successSnackBarEvent

    private val _normalSnackBarEvent = SingleLiveEvent<String>()
    val normalSnackBarEvent: LiveData<String> = _normalSnackBarEvent

    private val onFailedListener = { errorWrapper: ErrorWrapper -> setErrorSnackBar(errorWrapper) }

    fun loadData(bundle: Bundle) {
        _detailTeam.value = bundle.getParcelable("detail_team")
        loadPlayerList()
        loadNewsList()
    }

    fun checkFavoriteTeam() {
        launch {
            val result = withContext(dispatcherProviders.IO) {
                useCase.checkIsFavoriteTeam(_detailTeam.value?.idTeam.orEmpty())
            }
            _isFavoriteMatch.value = result != null
        }
    }

    fun onFavoriteIconClick() {
        launch(dispatcherProviders.IO) {
            val isFavorite = _isFavoriteMatch.value ?: false
            val detailTeam = _detailTeam.value ?: return@launch
            if (!isFavorite) {
                useCase.saveAsFavoriteTeam(detailTeam, onSuccess = {
                    _successSnackBarEvent.postValue("Added as Favorite Team")
                    _isFavoriteMatch.postValue(true)
                }, onFailed = onFailedListener)
            } else {
                useCase.deleteFavoriteTeam(detailTeam, onSuccess = {
                    _normalSnackBarEvent.postValue("Removed from Favorite Team")
                    _isFavoriteMatch.postValue(false)
                }, onFailed = onFailedListener)
            }
        }
    }

    fun openListNewsScreen() {
        val detailTeam = _detailTeam.value ?: return
        val params = arrayOf<Pair<String, Any?>>(
            "team_name" to detailTeam.teamName.orEmpty(),
            "query" to "${detailTeam.teamName.orEmpty()} ${detailTeam.leagueName.orEmpty()}"
        )
        setNavigationScreen(NavigationScreen(NAVIGATE_ALL_NEWS_SCREEN, params))
    }

    fun openListPlayerScreen() {
        val detailTeam = _detailTeam.value ?: return
        val params = arrayOf(
            "team_name" to detailTeam.teamName.orEmpty(),
            "players" to _playerList.value
        )
        setNavigationScreen(NavigationScreen(NAVIGATE_ALL_PLAYER_SCREEN, params))
    }

    private fun loadPlayerList() {
        launch {
            val detailTeam = _detailTeam.value ?: return@launch
            val playersResult = withContext(dispatcherProviders.IO) {
                useCase.getPlayerList(detailTeam.idTeam)
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

    private fun loadNewsList() {
        launch {
            val detailTeam = _detailTeam.value ?: return@launch
            val newsResult = withContext(dispatcherProviders.IO) {
                useCase.getNewsByQuery("${detailTeam.teamName.orEmpty()} ${detailTeam.leagueName.orEmpty()}")
            }
            newsResult
                .onSuccess {
                    _newsResponse.value = it
                }
                .onFailed {
                    setErrorSnackBar(it)
                }
        }
    }
}