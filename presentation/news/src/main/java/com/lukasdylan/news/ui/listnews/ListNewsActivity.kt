package com.lukasdylan.news.ui.listnews

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.extension.showBottomSheetFragment
import com.lukasdylan.core.widget.EndlessRecyclerViewScrollListener
import com.lukasdylan.news.R
import com.lukasdylan.news.databinding.ActivityListNewsBinding
import com.lukasdylan.news.ui.detailnews.DetailNewsActivity
import com.lukasdylan.news.ui.sort.NewsSortFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListNewsActivity : AppCompatActivity() {

    private val listNewsViewModel by viewModel<ListNewsViewModel>()

    private val newsAdapter by lazy {
        NewsAdapter {
            val params = arrayOf<Pair<String, Any?>>("news_url" to it.url, "news_title" to it.title)
            startActivity<DetailNewsActivity>(*params)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListNewsBinding>(this, R.layout.activity_list_news)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            rvNews.apply {
                setHasFixedSize(true)
                val linearLayoutManager = LinearLayoutManager(context)
                layoutManager = linearLayoutManager
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                        listNewsViewModel.onLoadMore()
                    }
                })
                adapter = newsAdapter
            }
            sortLayout.onClick {
                listNewsViewModel.openSortDialog()
            }
            filterLayout.onClick {
                listNewsViewModel.openFilterDialog()
            }
        }

        with(listNewsViewModel) {
            observeValue(newsList) { newsAdapter.update(it) }
            observeValue(toolbarTitle) { supportActionBar?.title = "News on $it" }
            observeValue(isFiltered) {
                TransitionManager.beginDelayedTransition(binding.filterLayout)
                if (it) {
                    binding.tvFilter.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_filter_active,0,0,0)
                    binding.tvFilter.setTextColor(ContextCompat.getColor(this@ListNewsActivity, android.R.color.holo_orange_light))
                } else {
                    binding.tvFilter.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_filter,0,0,0)
                    binding.tvFilter.setTextColor(ContextCompat.getColor(this@ListNewsActivity, android.R.color.black))
                }
            }
            observeValue(isSorted) {
                TransitionManager.beginDelayedTransition(binding.sortLayout)
                if (it) {
                    binding.tvSort.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_sort_active,0,0,0)
                    binding.tvSort.setTextColor(ContextCompat.getColor(this@ListNewsActivity, android.R.color.holo_orange_light))
                } else {
                    binding.tvSort.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_sort,0,0,0)
                    binding.tvSort.setTextColor(ContextCompat.getColor(this@ListNewsActivity, android.R.color.black))
                }
            }
            observeValue(navigationScreenEvent) {
                when (it.navigationId) {
                    NAVIGATE_SORT_DIALOG -> {
                        showBottomSheetFragment(NewsSortFragment(), it.params)
                    }
                    NAVIGATE_FILTER_DIALOG -> {

                    }
                }
            }
            intent?.extras?.let { return@with loadData(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
