package com.lukasdylan.football.ui.matchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImagesFromUrl
import com.lukasdylan.core.utility.asStringDate
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemMatchListBinding
import com.lukasdylan.football.utility.asyncText
import com.lukasdylan.footballservice.data.entity.DetailMatch
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class MatchListAdapter(private val listener: (Array<Pair<String, Any?>>) -> Unit) :
    BaseAdapter<DetailMatch, MatchListAdapter.MatchListViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemMatchListBinding>(
            inflater,
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

        private val calendar = Calendar.getInstance()

        override fun bindWithImageMap(item: DetailMatch, imageMap: Map<String, String>) {
            super.bindWithImageMap(item, imageMap)
            with(binding) {
                val homeImageUrl = imageMap[item.homeTeamId].orEmpty()
                val awayImageUrl = imageMap[item.awayTeamId].orEmpty()
                ivHomeTeamIcon.loadImagesFromUrl(homeImageUrl, R.drawable.placeholder_circle_background)
                ivAwayTeamIcon.loadImagesFromUrl(awayImageUrl, R.drawable.placeholder_circle_background)
                rootLayout.onClick {
                    val params = arrayOf<Pair<String, Any?>>(
                        "detail_match" to item,
                        "home_image_url" to homeImageUrl,
                        "away_image_url" to awayImageUrl
                    )
                    listener(params)
                }
                this.match = item
                tvDateTimeMatch.asyncText(calendar.asStringDate(item.date.orEmpty(), item.time.orEmpty()))
                executePendingBindings()
            }
        }
    }
}