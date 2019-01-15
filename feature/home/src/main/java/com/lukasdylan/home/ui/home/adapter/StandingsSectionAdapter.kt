package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.footballservice.data.response.Standings
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.ItemTeamStandingBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class StandingsSectionAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var standingList = listOf<Standings>()
    private var imageTeamData: Map<String, String> = HashMap()

    fun addData(data: List<Standings>) {
        standingList = data
    }

    fun addImageData(data: Map<String, String>) {
        imageTeamData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemTeamStandingBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_team_standing,
            parent,
            false
        )
        return StandingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = standingList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StandingViewHolder) {
            val standings = standingList[position]
            val imageUrl = imageTeamData[standings.teamId]
            holder.bind(standings, imageUrl)
        }
    }

    class StandingViewHolder(
        private val binding: ItemTeamStandingBinding,
        private val listener: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(standings: Standings, imageUrl: String?) {
            with(binding) {
                this.standings = standings
                ivClubIcon.loadImageByUrl(imageUrl)
                position = adapterPosition + 1
                rootLayout.onClick {
                    listener(standings.teamId.orEmpty())
                }
                executePendingBindings()
            }
        }
    }
}