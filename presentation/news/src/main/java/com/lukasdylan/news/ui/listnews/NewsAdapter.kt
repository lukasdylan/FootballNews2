package com.lukasdylan.news.ui.listnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.news.R
import com.lukasdylan.news.databinding.ItemNewsBinding
import com.lukasdylan.newsservice.data.Article
import org.jetbrains.anko.sdk27.coroutines.onClick

class NewsAdapter(private val listener: (Article) -> Unit) : BaseAdapter<Article, NewsAdapter.NewsViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, parent, false)
    }

    override fun setViewHolder(binding: ViewDataBinding): NewsViewHolder {
        return NewsViewHolder(binding as ItemNewsBinding, listener)
    }

    fun update(data: List<Article>) {
        addData(data)
        notifyDataSetChanged()
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val listener: (Article) -> Unit
    ) : BaseViewHolder<Article>(binding) {

        override fun bind(item: Article) {
            with(binding) {
                this.article = item
                this.position = adapterPosition + 1
                this.mode = GlideTransformationMode.ROUNDED_CENTER_CROP_IMAGE
                rootLayout.onClick {
                    listener(item)
                }
                executePendingBindings()
            }
        }
    }
}