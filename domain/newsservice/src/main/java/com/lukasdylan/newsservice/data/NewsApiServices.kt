package com.lukasdylan.newsservice.data

import com.lukasdylan.newsservice.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_PAGE_SIZE = 10
private const val API_NEWS_TOP_HEADLINES_ENDPOINT = "top-headlines"
private const val API_NEWS_EVERYTHING_ENDPOINT = "everything"

interface NewsApiServices {
    @GET(API_NEWS_TOP_HEADLINES_ENDPOINT)
    fun fetchTopHeadLineNews(
        @Query("sources") sourceName: String = NewsSources.defaultSources,
        @Query("q") leagueName: String = "",
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Call<NewsResponse>

    @GET(API_NEWS_EVERYTHING_ENDPOINT)
    fun fetchNewsByLeague(
        @Query("sources") sourceName: String = NewsSources.defaultSources,
        @Query("q") leagueName: String = "",
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Call<NewsResponse>
}