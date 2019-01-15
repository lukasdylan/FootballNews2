package com.lukasdylan.home.ui.leaguelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.loadImage
import com.lukasdylan.footballservice.data.model.LA_LIGA_ID
import com.lukasdylan.footballservice.data.model.League
import com.lukasdylan.footballservice.data.model.PREMIER_LEAGUE_ID
import com.lukasdylan.footballservice.data.model.SERIE_A_ID
import com.lukasdylan.home.databinding.ItemLeagueBinding
import com.lukasdylan.home.R
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by Lukas Dylan Adisurya <lukas.adisurya@ovo.id>
 * on 17/09/18
 */
class LeagueListAdapter(
    private val leagueList: List<League>,
    private val listener: (League) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLeagueBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_league,
            viewGroup,
            false
        )
        return ItemViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = leagueList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(leagueList[position])
        }
    }

    class ItemViewHolder(
        private val binding: ItemLeagueBinding,
        private val listener: (League) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(league: League) {
            with(binding) {
                this.league = league
                ivLeagueIcon.loadImage(
                    when (league.leagueId) {
                        PREMIER_LEAGUE_ID.toString() -> R.drawable.icon_premier_league
                        LA_LIGA_ID.toString() -> R.drawable.icon_la_liga
                        SERIE_A_ID.toString() -> R.drawable.icon_serie_a
                        else -> R.drawable.no_image_icon
                    }
                )
                rootLayout.onClick {
                    delay(300)
                    listener(league)
                }
                executePendingBindings()
            }
        }
    }
}