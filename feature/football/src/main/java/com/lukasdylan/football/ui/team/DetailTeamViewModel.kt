package com.lukasdylan.football.ui.team

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.footballservice.data.response.DetailTeam

class DetailTeamViewModel(dispatcherProviders: DispatcherProviders) : BaseViewModel(dispatcherProviders) {

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

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _detailTeam.value = getParcelable("detail_team")
        }
    }
}