package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.ItemTrendingNewsBinding
import com.lukasdylan.newsservice.data.Article
import org.jetbrains.anko.sdk27.coroutines.onClick

class TrendingNewsSectionAdapter(private val articleListener: (Array<Pair<String, Any?>>) -> Unit) :
    BaseAdapter<Article, TrendingNewsSectionAdapter.TrendingNewsViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemTrendingNewsBinding>(
            inflater,
            R.layout.item_trending_news,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): TrendingNewsViewHolder {
        return TrendingNewsViewHolder(binding as ItemTrendingNewsBinding, articleListener)
    }

    class TrendingNewsViewHolder(
        private val binding: ItemTrendingNewsBinding,
        private val listener: (Array<Pair<String, Any?>>) -> Unit
    ) : BaseViewHolder<Article>(binding) {

        override fun bind(item: Article, imageMap: Map<String, String>?) {
            with(binding) {
                this.placeholder = R.color.lighter_gray
                this.mode = GlideTransformationMode.ROUNDED_CENTER_CROP_IMAGE
                this.article = item
                rootLayout.onClick {
                    val params = arrayOf<Pair<String, Any?>>("news_url" to item.url, "news_title" to item.title)
                    listener(params)
                }
                executePendingBindings()
            }
        }
    }
}