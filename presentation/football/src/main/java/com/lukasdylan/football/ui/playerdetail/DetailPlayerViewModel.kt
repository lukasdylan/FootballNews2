package com.lukasdylan.football.ui.playerdetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lukasdylan.footballservice.data.response.Player

class DetailPlayerViewModel : ViewModel() {

    private val _playerData = MutableLiveData<Player>().apply { value = null }
    val playerPhotoUrl: LiveData<String> = Transformations.map(_playerData) {
        it?.playerIconUrl.orEmpty()
    }
    val toolbarTitle: LiveData<String> = Transformations.map(_playerData) {
        it?.playerName.orEmpty()
    }
    val playerName: LiveData<String> = Transformations.map(_playerData) {
        it?.playerName.orEmpty()
    }
    val playerPosition: LiveData<String> = Transformations.map(_playerData) {
        it?.playerPosition.orEmpty()
    }
    val playerPhotoBackground: LiveData<String> = Transformations.map(_playerData) {
        it?.playerFanArtUrl.orEmpty()
    }

    fun loadData(bundle: Bundle) {
        _playerData.value = bundle.getParcelable("player_data")
    }
}