package com.lukasdylan.news.ui.listnews

import com.lukasdylan.newsservice.data.NewsApiServices
import com.lukasdylan.newsservice.data.NewsResponse
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

interface ListNewsUseCase {
    suspend fun getNewsList(query: String, sources: String, page: Int, sortBy: String): Result<NewsResponse>
}

class ListNewsUseCaseImpl(private val newsApiServices: NewsApiServices) : ListNewsUseCase {

    override suspend fun getNewsList(query: String, sources: String, page: Int, sortBy: String): Result<NewsResponse> {
        return newsApiServices.fetchNewsByQuery(query = query, sourceName = sources, page = page, sortBy = sortBy).awaitResult()
    }

}