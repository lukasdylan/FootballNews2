package com.lukasdylan.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukasdylan.core.extension.loadImageByUrl
import com.lukasdylan.home.R
import com.lukasdylan.home.asyncText
import com.lukasdylan.home.databinding.ItemTrendingNewsBinding
import com.lukasdylan.newsservice.data.Article
import org.jetbrains.anko.sdk27.coroutines.onClick

class TrendingNewsSectionAdapter(private val articleListener: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var trendingNewsList = listOf<Article>()

    fun addData(data: List<Article>) {
        trendingNewsList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemTrendingNewsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_news,
            parent,
            false
        )
        return TrendingNewsViewHolder(binding, articleListener)
    }

    override fun getItemCount(): Int = trendingNewsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TrendingNewsViewHolder && position <= (trendingNewsList.size - 1)) {
            holder.bind(trendingNewsList[position])
        }
    }

    class TrendingNewsViewHolder(
        private val binding: ItemTrendingNewsBinding,
        private val listener: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            with(binding) {
                tvNewsTitle.asyncText(article.title)
                ivNewsImage.loadImageByUrl(article.urlToImage)
                cvTrendingNews.onClick {
                    listener(article.url.orEmpty())
                }
                executePendingBindings()
            }
        }
    }

}