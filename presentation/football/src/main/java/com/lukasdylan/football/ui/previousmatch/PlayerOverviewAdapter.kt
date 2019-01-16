package com.lukasdylan.football.ui.previousmatch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemPlayerOverviewBinding

class PlayerOverviewAdapter : BaseAdapter<String, PlayerOverviewAdapter.PlayerOverviewViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemPlayerOverviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_player_overview,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): PlayerOverviewViewHolder {
        return PlayerOverviewViewHolder(binding as ItemPlayerOverviewBinding)
    }

    class PlayerOverviewViewHolder(private val binding: ItemPlayerOverviewBinding) :
        BaseViewHolder<String>(binding) {

        override fun bind(item: String, imageMap: Map<String, String>?) {
            with(binding) {
                this.value = item
                executePendingBindings()
            }
        }
    }
}