package com.lukasdylan.football.ui.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemPlayerHorizontalListBinding
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

        override fun bind(item: Player) {
            with(binding) {
                this.placeholder = R.drawable.placeholder_circle_background
                this.mode = GlideTransformationMode.FULL_IMAGE
                this.name = item.playerName.orEmpty()
                this.imageUrl = item.playerIconUrl.orEmpty()
                rootLayout.onClick {
                    listener(item)
                }
                executePendingBindings()
            }
        }
    }
}