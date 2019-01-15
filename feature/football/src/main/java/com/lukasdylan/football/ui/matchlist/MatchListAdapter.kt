package com.lukasdylan.football.ui.matchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.core.utility.StringUtils
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemMatchListBinding
import com.lukasdylan.football.utility.asyncText
import com.lukasdylan.footballservice.data.entity.DetailMatch
import org.jetbrains.anko.sdk27.coroutines.onClick

class MatchListAdapter(private val listener: (Array<Pair<String, Any?>>) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var matchList = listOf<DetailMatch>()
    private var imageTeamData: Map<String, String> = HashMap()

    fun addData(data: List<DetailMatch>) {
        matchList = data
    }

    fun addImageData(data: Map<String, String>) {
        imageTeamData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemMatchListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_match_list,
            parent,
            false
        )
        return MatchListViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = matchList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MatchListViewHolder) {
            val match = matchList[position]
            val homeImageUrl = imageTeamData[match.homeTeamId]
            val awayImageUrl = imageTeamData[match.awayTeamId]
            holder.bind(match, homeImageUrl, awayImageUrl)
        }
    }

    class MatchListViewHolder(
        private val binding: ItemMatchListBinding,
        private val listener: (Array<Pair<String, Any?>>) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(detailMatch: DetailMatch, homeImageUrl: String?, awayImageUrl: String?) {
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