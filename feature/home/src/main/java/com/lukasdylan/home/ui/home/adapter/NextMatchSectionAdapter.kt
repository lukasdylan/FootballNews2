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
import com.lukasdylan.home.databinding.ItemHomeNextMatchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class NextMatchSectionAdapter(
    private val layoutMaxWidth: Int,
    private val listener: (Array<Pair<String, Any?>>) -> Unit
) : BaseAdapter<DetailMatch, NextMatchSectionAdapter.NextMatchViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemHomeNextMatchBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home_next_match,
            parent,
            false
        ).also {
            it?.cvNextMatch?.layoutParams?.width = layoutMaxWidth
        }
    }

    override fun setViewHolder(binding: ViewDataBinding): NextMatchViewHolder {
        return NextMatchViewHolder(binding as ItemHomeNextMatchBinding, listener)
    }

    class NextMatchViewHolder(
        private val binding: ItemHomeNextMatchBinding,
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