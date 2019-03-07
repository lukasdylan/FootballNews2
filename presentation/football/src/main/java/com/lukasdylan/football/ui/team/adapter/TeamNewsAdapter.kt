package com.lukasdylan.football.ui.team.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemTeamNewsBinding
import com.lukasdylan.newsservice.data.Article
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamNewsAdapter(private val listener: (Article) -> Unit) :
    BaseAdapter<Article, TeamNewsAdapter.NewsViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemTeamNewsBinding>(inflater, R.layout.item_team_news, parent, false)
    }

    override fun setViewHolder(binding: ViewDataBinding): NewsViewHolder {
        return NewsViewHolder(binding as ItemTeamNewsBinding, listener)
    }

    class NewsViewHolder(
        private val binding: ItemTeamNewsBinding,
        private val listener: (Article) -> Unit
    ) : BaseViewHolder<Article>(binding) {

        override fun bind(item: Article, imageMap: Map<String, String>?) {
            with(binding) {
                this.placeholder = R.color.lighter_gray
                this.mode = GlideTransformationMode.ROUNDED_CENTER_CROP_IMAGE
                this.article = item
                this.position = adapterPosition + 1
                rootLayout.onClick {
                    listener(item)
                }
                executePendingBindings()
            }
        }
    }
}