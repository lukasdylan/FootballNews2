package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.core.utility.StringUtils
import com.lukasdylan.home.R
import com.lukasdylan.home.asyncText
import com.lukasdylan.home.databinding.ItemHomePreviousMatchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class PreviousMatchSectionAdapter(
    private val layoutMaxWidth: Int,
    private val listener: (Array<Pair<String, Any?>>) -> Unit?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var prevMatchList = listOf<com.lukasdylan.footballservice.data.entity.DetailMatch>()
    private var imageTeamData: Map<String, String> = HashMap()

    fun addData(data: List<com.lukasdylan.footballservice.data.entity.DetailMatch>) {
        prevMatchList = data
    }

    fun addImageData(data: Map<String, String>) {
        imageTeamData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemHomePreviousMatchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home_previous_match,
            parent,
            false
        )
        binding.cvPreviousMatch.layoutParams.width = layoutMaxWidth
        return PreviousMatchViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = prevMatchList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PreviousMatchViewHolder) {
            val match = prevMatchList[position]
            val homeImageUrl = imageTeamData[match.homeTeamId]
            val awayImageUrl = imageTeamData[match.awayTeamId]
            holder.bind(match, homeImageUrl, awayImageUrl)
        }
    }

    class PreviousMatchViewHolder(
        private val binding: ItemHomePreviousMatchBinding,
        private val listener: (Array<Pair<String, Any?>>) -> Unit?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(detailMatch: com.lukasdylan.footballservice.data.entity.DetailMatch, homeImageUrl: String?, awayImageUrl: String?) {
            with(binding) {
                this.match = detailMatch
                ivHomeTeamIcon.loadImageByUrl(homeImageUrl)
                ivAwayTeamIcon.loadImageByUrl(awayImageUrl)
                val calendar = StringUtils.calendarFromString(detailMatch.date.orEmpty(), detailMatch.time.orEmpty())
                tvDateTimeMatch.asyncText(StringUtils.formatAsDate(calendar.time))
                rootLayout.onClick {
                    val params = arrayOf(
                        "detail_match" to detailMatch,
                        "home_image_url" to homeImageUrl,
                        "away_image_url" to awayImageUrl
                    )
                    listener(params)
                }
                executePendingBindings()
            }
        }
    }

}