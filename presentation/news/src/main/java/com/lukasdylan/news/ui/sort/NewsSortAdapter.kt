package com.lukasdylan.news.ui.sort

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.news.R
import com.lukasdylan.news.databinding.ItemNewsSortBinding
import com.lukasdylan.newsservice.data.NewsSources
import org.jetbrains.anko.sdk27.coroutines.onClick

class NewsSortAdapter(
    private val selectedSort: String,
    private val listener: (Int) -> Unit
) : BaseAdapter<String, NewsSortAdapter.NewsSortViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemNewsSortBinding>(inflater, R.layout.item_news_sort, parent, false)
    }

    override fun setViewHolder(binding: ViewDataBinding): NewsSortViewHolder {
        return NewsSortViewHolder(binding as ItemNewsSortBinding, selectedSort, listener)
    }

    class NewsSortViewHolder(
        private val binding: ItemNewsSortBinding,
        private val selectedSort: String,
        private val listener: (Int) -> Unit
    ) : BaseViewHolder<String>(binding) {

        override fun bind(item: String, imageMap: Map<String, String>?) {
            with(binding) {
                tvSortName.text = item
                ivCheck.visibility =
                    if (adapterPosition == 0 && selectedSort.equals(NewsSources.SORT_BY_PUBLISH_TIME, true)) {
                        View.VISIBLE
                    } else if (adapterPosition == 1 && selectedSort.equals(NewsSources.SORT_BY_POPULARITY, true)) {
                        View.VISIBLE
                    } else if (adapterPosition == 2 && selectedSort.equals(NewsSources.SORT_BY_RELEVANCY, true)) {
                        View.VISIBLE
                    } else {
                        View.INVISIBLE
                    }
                rootLayout.onClick {
                    listener(adapterPosition)
                }
                executePendingBindings()
            }
        }
    }
}