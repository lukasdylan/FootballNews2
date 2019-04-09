package com.lukasdylan.football.ui.playerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemPlayer2Binding
import com.lukasdylan.footballservice.data.response.Player
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayerListAdapter(private val listener: (Player) -> Unit) :
    BaseAdapter<Player, PlayerListAdapter.PlayerViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemPlayer2Binding>(inflater, R.layout.item_player_2, parent, false)
    }

    override fun setViewHolder(binding: ViewDataBinding): PlayerViewHolder {
        return PlayerViewHolder(binding as ItemPlayer2Binding, listener)
    }

    class PlayerViewHolder(
        private val binding: ItemPlayer2Binding,
        private val listener: (Player) -> Unit
    ) : BaseViewHolder<Player>(binding) {

        override fun bind(item: Player) {
            with(binding) {
                this.mode = GlideTransformationMode.FULL_IMAGE
                this.placeholder = R.drawable.placeholder_circle_background
                this.imageUrl = item.playerIconUrl.orEmpty()
                this.name = item.playerName.orEmpty()
                this.position = item.playerPosition.orEmpty()
                rootLayout.onClick {
                    listener(item)
                }
                executePendingBindings()
            }
        }
    }
}