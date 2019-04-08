package com.lukasdylan.newsservice.module

import com.lukasdylan.core.extension.initRetrofit
import com.lukasdylan.newsservice.BuildConfig
import com.lukasdylan.newsservice.data.NewsApiServices
import org.koin.dsl.module

private val networkModule = module {
    single { initRetrofit<NewsApiServices>(BuildConfig.NEWS_BASE_URL, get(), get()) }
}

val newsServiceModules = listOf(networkModule)