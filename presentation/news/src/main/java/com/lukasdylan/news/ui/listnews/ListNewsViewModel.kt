package com.lukasdylan.news.ui.listnews

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lukasdylan.core.base.BaseViewModel
import com.lukasdylan.core.extension.onFailed
import com.lukasdylan.core.extension.onSuccess
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.newsservice.data.Article
import com.lukasdylan.newsservice.data.NewsSources
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal const val NAVIGATE_SORT_DIALOG = 100
internal const val NAVIGATE_FILTER_DIALOG = 101
class ListNewsViewModel(private val useCase: ListNewsUseCase, dispatcherProviders: DispatcherProviders) :
    BaseViewModel(dispatcherProviders) {

    private val _filteredSourceNews = MutableLiveData<List<String>>().apply { value = emptyList() }
    val isFiltered: LiveData<Boolean> = Transformations.map(_filteredSourceNews) {
        !it.isNullOrEmpty()
    }

    private val _newsList = MutableLiveData<MutableList<Article>>()
    val newsList: LiveData<List<Article>> = Transformations.map(_newsList) {
        return@map it.toList()
    }

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle

    private val _selectedSort = MutableLiveData<String>().apply { value = NewsSources.SORT_BY_PUBLISH_TIME }
    val isSorted: LiveData<Boolean> = Transformations.map(_selectedSort) {
        return@map !it.equals(NewsSources.SORT_BY_PUBLISH_TIME, true)
    }

    private var currentPage = 1
    private var currentNewsCount = 0
    private var maxNewsCount = 1
    private var query = ""

    fun loadData(bundle: Bundle) {
        with(bundle) {
            _toolbarTitle.value = getString("team_name").orEmpty()
            query = getString("query").orEmpty()
            loadNewsList()
        }
    }

    fun onSelectedFilter(sources: List<String>) {
        currentPage = 1
        currentNewsCount = 0
        _filteredSourceNews.value = sources
        _newsList.value = null
        if (sources.isNullOrEmpty()) {
            loadNewsList()
        } else {
            loadNewsList(sources.joinToString(","))
        }
    }

    fun onSelectedSort(index: Int) {
        currentPage = 1
        currentNewsCount = 0
        _newsList.value = null
        _selectedSort.value = when (index) {
            0 -> NewsSources.SORT_BY_PUBLISH_TIME
            1 -> NewsSources.SORT_BY_POPULARITY
            else -> NewsSources.SORT_BY_RELEVANCY
        }
        val filteredSources = _filteredSourceNews.value.orEmpty()
        if (filteredSources.isNullOrEmpty()) {
            loadNewsList()
        } else {
            loadNewsList(filteredSources.joinToString(","))
        }
    }

    fun onLoadMore() {
        val filteredSources = _filteredSourceNews.value.orEmpty()
        if (filteredSources.isNullOrEmpty()) {
            loadNewsList()
        } else {
            loadNewsList(filteredSources.joinToString(","))
        }
    }

    fun openSortDialog() {
        val params = arrayOf<Pair<String, Any?>>("sort_name" to _selectedSort.value)
        setNavigationScreen(NavigationScreen(NAVIGATE_SORT_DIALOG, params))
    }

    fun openFilterDialog() {
        val params = arrayOf<Pair<String, Any?>>("filter_source" to _filteredSourceNews.value)
        setNavigationScreen(NavigationScreen(NAVIGATE_FILTER_DIALOG, params))
    }

    private fun loadNewsList(sources: String = NewsSources.defaultSources) {
        if (currentNewsCount < maxNewsCount) {
            launch {
                val result = withContext(dispatcherProviders.IO) {
                    useCase.getNewsList(query = query, sources = sources, page = currentPage, sortBy = _selectedSort.value.orEmpty())
                }
                result
                    .onSuccess {
                        maxNewsCount = it.totalResult ?: 0
                        val currentList = _newsList.value.orEmpty() + it.articles
                        _newsList.value = currentList.toMutableList()
                        currentPage = currentPage.inc()
                        currentNewsCount = _newsList.value.orEmpty().size
                    }
                    .onFailed {
                        setErrorSnackBar(it)
                    }
            }
        }
    }
}