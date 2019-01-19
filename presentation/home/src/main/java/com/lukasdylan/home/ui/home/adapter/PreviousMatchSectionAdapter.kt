package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.core.utility.StringUtils
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.home.R
import com.lukasdylan.home.asyncText
import com.lukasdylan.home.databinding.ItemHomePreviousMatchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class PreviousMatchSectionAdapter(
    private val layoutMaxWidth: Int,
    private val listener: (Array<Pair<String, Any?>>) -> Unit
) : BaseAdapter<DetailMatch, PreviousMatchSectionAdapter.PreviousMatchViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemHomePreviousMatchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home_previous_match,
            parent,
            false
        ).also {
            it?.cvPreviousMatch?.layoutParams?.width = layoutMaxWidth
        }
    }

    override fun setViewHolder(binding: ViewDataBinding): PreviousMatchViewHolder {
        return PreviousMatchViewHolder(binding as ItemHomePreviousMatchBinding, listener)
    }

    class PreviousMatchViewHolder(
        private val binding: ItemHomePreviousMatchBinding,
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