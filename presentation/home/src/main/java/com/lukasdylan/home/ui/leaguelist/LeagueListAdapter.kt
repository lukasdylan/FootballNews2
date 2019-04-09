package com.lukasdylan.home.ui.leaguelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImagesFromResources
import com.lukasdylan.footballservice.data.model.LA_LIGA_ID
import com.lukasdylan.footballservice.data.model.League
import com.lukasdylan.footballservice.data.model.PREMIER_LEAGUE_ID
import com.lukasdylan.footballservice.data.model.SERIE_A_ID
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.ItemLeagueBinding
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id>
 * on 17/09/18
 */
class LeagueListAdapter(private val listener: (League) -> Unit) :
    BaseAdapter<League, LeagueListAdapter.ItemViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemLeagueBinding>(
            inflater,
            R.layout.item_league,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): ItemViewHolder {
        return ItemViewHolder(binding as ItemLeagueBinding, listener)
    }

    class ItemViewHolder(
        private val binding: ItemLeagueBinding,
        private val listener: (League) -> Unit
    ) : BaseViewHolder<League>(binding) {

        override fun bind(item: League) {
            with(binding) {
                this.league = item
                ivLeagueIcon.loadImagesFromResources(
                    when (item.leagueId) {
                        PREMIER_LEAGUE_ID.toString() -> R.drawable.icon_premier_league
                        LA_LIGA_ID.toString() -> R.drawable.icon_la_liga
                        SERIE_A_ID.toString() -> R.drawable.icon_serie_a
                        else -> R.drawable.no_image_icon
                    }
                )
                rootLayout.onClick {
                    delay(300)
                    listener(item)
                }
                executePendingBindings()
            }
        }
    }
}