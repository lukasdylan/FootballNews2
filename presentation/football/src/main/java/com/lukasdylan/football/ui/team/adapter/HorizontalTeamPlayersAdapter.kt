package com.lukasdylan.football.ui.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemPlayerHorizontalListBinding
import com.lukasdylan.football.utility.asyncText
import com.lukasdylan.footballservice.data.response.Player
import org.jetbrains.anko.sdk27.coroutines.onClick

class HorizontalTeamPlayersAdapter(private val listener: (Player) -> Unit) :
    BaseAdapter<Player, HorizontalTeamPlayersAdapter.PlayerViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemPlayerHorizontalListBinding>(
            inflater,
            R.layout.item_player_horizontal_list,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): PlayerViewHolder {
        return PlayerViewHolder(binding as ItemPlayerHorizontalListBinding, listener)
    }

    class PlayerViewHolder(
        private val binding: ItemPlayerHorizontalListBinding,
        private val listener: (Player) -> Unit
    ) : BaseViewHolder<Player>(binding) {

        override fun bind(item: Player, imageMap: Map<String, String>?) {
            with(binding) {
                ivPlayerPhoto.loadImageByUrl(item.playerIconUrl)
                tvPlayerName.asyncText(item.playerName)
                rootLayout.onClick {
                    listener(item)
                }
                executePendingBindings()
            }
        }
    }
}