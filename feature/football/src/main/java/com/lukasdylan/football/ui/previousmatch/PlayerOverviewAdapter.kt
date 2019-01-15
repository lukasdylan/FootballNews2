package com.lukasdylan.football.ui.previousmatch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemPlayerOverviewBinding

class PlayerOverviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var squadInformation = listOf<String>()

    fun addData(data: List<String>) {
        squadInformation = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPlayerOverviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_player_overview,
            parent,
            false
        )
        return PlayerOverviewViewHolder(binding)
    }

    override fun getItemCount(): Int = squadInformation.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlayerOverviewViewHolder) {
            holder.bind(squadInformation[position])
        }
    }

    class PlayerOverviewViewHolder(private val binding: ItemPlayerOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(squad: String) {
            with(binding) {
                this.value = squad
                executePendingBindings()
            }
        }
    }

}