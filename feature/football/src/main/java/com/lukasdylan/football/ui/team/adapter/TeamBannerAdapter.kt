package com.lukasdylan.football.ui.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemTeamBannerBinding

class TeamBannerAdapter : RecyclerView.Adapter<TeamBannerAdapter.BannerViewHolder>() {

    private var bannerList = listOf<String>()

    fun addData(data: List<String>) {
        bannerList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = DataBindingUtil.inflate<ItemTeamBannerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_team_banner,
            parent,
            false
        )
        return BannerViewHolder(binding)
    }

    override fun getItemCount(): Int = bannerList.size

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    class BannerViewHolder(private val binding: ItemTeamBannerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            with(binding) {
                ivBanner.loadImageByUrl(imageUrl)
                executePendingBindings()
            }
        }
    }

}