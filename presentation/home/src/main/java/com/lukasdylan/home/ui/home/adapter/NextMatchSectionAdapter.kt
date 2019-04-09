package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.core.utility.StringUtils
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.ItemHomeNextMatchBinding
import org.jetbrains.anko.sdk27.coroutines.onClick

class NextMatchSectionAdapter(
    private val layoutMaxWidth: Int,
    private val listener: (Array<Pair<String, Any?>>) -> Unit
) : BaseAdapter<DetailMatch, NextMatchSectionAdapter.NextMatchViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemHomeNextMatchBinding>(
            inflater,
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

        override fun bindWithImageMap(item: DetailMatch, imageMap: Map<String, String>) {
            super.bindWithImageMap(item, imageMap)
            with(binding) {
                this.placeholder = R.drawable.placeholder_circle_background
                this.mode = GlideTransformationMode.FULL_IMAGE
                val homeImageUrl = imageMap[item.homeTeamId].orEmpty()
                val awayImageUrl = imageMap[item.awayTeamId].orEmpty()
                this.homeImageUrl = homeImageUrl
                this.awayImageUrl = awayImageUrl
                val calendar = StringUtils.calendarFromString(item.date.orEmpty(), item.time.orEmpty())
                this.match = item
                this.matchDate = StringUtils.formatAsDate(calendar.time)
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