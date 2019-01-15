package com.lukasdylan.football.ui.previousmatch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemMatchOverviewBinding

class MatchOverviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var matchInformation = listOf<Pair<String, String>>()

    fun addData(data: List<Pair<String, String>>) {
        matchInformation = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemMatchOverviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_match_overview,
            parent,
            false
        )
        return MatchOverviewViewHolder(binding)
    }

    override fun getItemCount(): Int = matchInformation.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MatchOverviewViewHolder) {
            holder.bind(matchInformation[position])
        }
    }

    class MatchOverviewViewHolder(private val binding: ItemMatchOverviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Pair<String, String>) {
            with(binding) {
                this.label = data.first
                this.value = data.second
                executePendingBindings()
            }
        }
    }

}