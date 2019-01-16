package com.lukasdylan.football.ui.previousmatch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemMatchOverviewBinding

class MatchOverviewAdapter : BaseAdapter<Pair<String, String>, MatchOverviewAdapter.MatchOverviewViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemMatchOverviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_match_overview,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): MatchOverviewViewHolder {
        return MatchOverviewViewHolder(binding as ItemMatchOverviewBinding)
    }

    class MatchOverviewViewHolder(private val binding: ItemMatchOverviewBinding) :
        BaseViewHolder<Pair<String, String>>(binding) {

        override fun bind(item: Pair<String, String>, imageMap: Map<String, String>?) {
            with(binding) {
                this.label = item.first
                this.value = item.second
                executePendingBindings()
            }
        }
    }
}