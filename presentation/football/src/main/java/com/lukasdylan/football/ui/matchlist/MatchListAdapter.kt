package com.lukasdylan.football.ui.matchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.core.utility.StringUtils
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemMatchListBinding
import com.lukasdylan.football.utility.asyncText
import com.lukasdylan.footballservice.data.entity.DetailMatch
import org.jetbrains.anko.sdk27.coroutines.onClick

class MatchListAdapter(private val listener: (Array<Pair<String, Any?>>) -> Unit) :
    BaseAdapter<DetailMatch, MatchListAdapter.MatchListViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemMatchListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_match_list,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): MatchListViewHolder {
        return MatchListViewHolder(binding as ItemMatchListBinding, listener)
    }

    class MatchListViewHolder(
        private val binding: ItemMatchListBinding,
        private val listener: (Array<Pair<String, Any?>>) -> Unit
    ) : BaseViewHolder<DetailMatch>(binding) {

        override fun bind(item: DetailMatch, imageMap: Map<String, String>?) {
            with(binding) {
                this.match = item
                val homeImageUrl = imageMap?.get(item.homeTeamId).orEmpty()
                val awayImageUrl = imageMap?.get(item.awayTeamId).orEmpty()
                ivHomeTeamIcon.loadImageByUrl(homeImageUrl)
                ivAwayTeamIcon.loadImageByUrl(awayImageUrl)
                val calendar = StringUtils.calendarFromString(item.date.orEmpty(), item.time.orEmpty())
                tvDateTimeMatch.asyncText(StringUtils.formatAsDate(calendar.time))
                rootLayout.onClick {
                    val params = arrayOf<Pair<String, Any?>>(
                        "detail_match" to item,
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