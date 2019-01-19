package com.lukasdylan.football.ui.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemTeamBannerBinding

class TeamBannerAdapter : BaseAdapter<String, TeamBannerAdapter.BannerViewHolder>() {
    
    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemTeamBannerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_team_banner,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): BannerViewHolder {
        return BannerViewHolder(binding as ItemTeamBannerBinding)
    }

    class BannerViewHolder(private val binding: ItemTeamBannerBinding) : BaseViewHolder<String>(binding) {
        override fun bind(item: String, imageMap: Map<String, String>?) {
            with(binding) {
                ivBanner.loadImageByUrl(item)
                executePendingBindings()
            }
        }
    }
}