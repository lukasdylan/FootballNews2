package com.lukasdylan.football.ui.previousmatch

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.ErrorWrapper
import com.lukasdylan.core.utility.SingleLiveEvent
import com.lukasdylan.footballservice.data.entity.DetailMatch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreviousMatchViewModel(
    private val useCase: PreviousMatchUseCase,
    dispatcherProviders: DispatcherProviders
) : BaseViewModel(dispatcherProviders) {

    private val _selectedMatch = MutableLiveData<DetailMatch>()
    val matchScore: LiveData<String> = Transformations.map(_selectedMatch) {
        return@map "${it.homeTeamScore ?: "0"} - ${it.awayTeamScore ?: "0"}"
    }
    val matchName: LiveData<String> = Transformations.map(_selectedMatch) {
        it?.matchName.orEmpty()
    }
    val matchInformationPage: LiveData<DetailMatch> = _selectedMatch

    private val _homeTeamImageUrl = MutableLiveData<String>()
    val homeTeamImageUrl: LiveData<String> = Transformations.map(_homeTeamImageUrl) {
        it.orEmpty()
    }

    private val _awayTeamImageUrl = MutableLiveData<String>()
    val awayTeamImageUrl: LiveData<String> = Transformations.map(_awayTeamImageUrl) {
        it.orEmpty()
    }

    private val _isFavoriteMatch = MutableLiveData<Boolean>()
    val isFavoriteMatch: LiveData<Boolean> = _isFavoriteMatch

    private val _successSnackBarEvent = SingleLiveEvent<String>()
    val successSnackBarEvent: LiveData<String> = _successSnackBarEvent

    private val _normalSnackBarEvent = SingleLiveEvent<String>()
    val normalSnackBarEvent: LiveData<String> = _normalSnackBarEvent

    private val onFailedListener = { errorWrapper: ErrorWrapper -> _errorSnackBarEvent.postValue(errorWrapper) }

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _homeTeamImageUrl.value = getString("home_image_url")
            _awayTeamImageUrl.value = getString("away_image_url")
            _selectedMatch.value = getParcelable("detail_match")
        }
    }

    fun checkFavoriteMatch() {
        launch {
            _selectedMatch.value?.idEvent?.let {
                val favoriteMatch = withContext(dispatcherProviders.IO) {
                    useCase.checkIsFavoriteMatch(it)
                }
                _isFavoriteMatch.value = favoriteMatch != null
            }
        }
    }

    fun onFavoriteIconClick() {
        launch(dispatcherProviders.IO) {
            val isFavorite = _isFavoriteMatch.value ?: false
            val detailMatch = _selectedMatch.value ?: return@launch
            if (!isFavorite) {
                useCase.saveAsFavoriteMatch(detailMatch, onSuccess = {
                    _successSnackBarEvent.postValue("Added as Favorite Match")
                    _isFavoriteMatch.postValue(true)
                }, onFailed = onFailedListener)
            } else {
                useCase.deleteFavoriteMatch(detailMatch, onSuccess = {
                    _normalSnackBarEvent.postValue("Removed from Favorite Match")
                    _isFavoriteMatch.postValue(false)
                }, onFailed = onFailedListener)
            }
        }
    }

}