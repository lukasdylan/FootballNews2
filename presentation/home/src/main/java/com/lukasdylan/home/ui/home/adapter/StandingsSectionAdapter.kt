package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.footballservice.data.response.Standings
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.ItemTeamStandingBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class StandingsSectionAdapter(private val listener: (String) -> Unit) :
    BaseAdapter<Standings, StandingsSectionAdapter.StandingViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemTeamStandingBinding>(
            inflater,
            R.layout.item_team_standing,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): StandingViewHolder {
        return StandingViewHolder(binding as ItemTeamStandingBinding, listener)
    }

    class StandingViewHolder(
        private val binding: ItemTeamStandingBinding,
        private val listener: (String) -> Unit
    ) : BaseViewHolder<Standings>(binding) {

        override fun bindWithImageMap(item: Standings, imageMap: Map<String, String>) {
            super.bindWithImageMap(item, imageMap)
            with(binding) {
                this.placeholder = R.drawable.placeholder_circle_background
                this.mode = GlideTransformationMode.FULL_IMAGE
                this.imageUrl = imageMap[item.teamId].orEmpty()
                this.standings = item
                position = adapterPosition + 1
                rootLayout.onClick {
                    listener(item.teamId.orEmpty())
                }
                executePendingBindings()
            }
        }
    }
}