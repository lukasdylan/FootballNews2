package com.lukasdylan.football.ui.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImagesFromUrl
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemTeamStandingsBinding
import com.lukasdylan.footballservice.data.response.Standings
import org.jetbrains.anko.sdk27.coroutines.onClick

class StandingsAdapter(private val listener: (String) -> Unit) : BaseAdapter<Standings, StandingsAdapter.StandingViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemTeamStandingsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_team_standings,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): StandingViewHolder {
        return StandingViewHolder(binding as ItemTeamStandingsBinding, listener)
    }

    class StandingViewHolder(
        private val binding: ItemTeamStandingsBinding,
        private val listener: (String) -> Unit
    ) : BaseViewHolder<Standings>(binding) {

        override fun bind(item: Standings, imageMap: Map<String, String>?) {
            with(binding) {
                standings = item
                val imageUrl = imageMap?.get(item.teamId)
                ivClubIcon.loadImagesFromUrl(imageUrl, R.drawable.placeholder_circle_background)
                position = adapterPosition + 1
                rootLayout.onClick {
                    listener(item.teamId.orEmpty())
                }
                executePendingBindings()
            }
        }
    }
}