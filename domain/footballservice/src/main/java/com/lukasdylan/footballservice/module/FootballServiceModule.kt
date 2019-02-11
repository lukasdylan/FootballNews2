package com.lukasdylan.footballservice.module

import com.lukasdylan.core.extension.initRetrofit
import com.lukasdylan.footballservice.BuildConfig
import com.lukasdylan.footballservice.data.database.createDetailMatchDao
import com.lukasdylan.footballservice.data.database.createDetailTeamDao
import com.lukasdylan.footballservice.data.database.initDatabase
import com.lukasdylan.footballservice.data.service.FootballApiServices
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

private val networkModule = module {
    single { initRetrofit<FootballApiServices>(BuildConfig.BASE_URL, get(), get()) }
}

private val databaseModule = module {
    single { initDatabase(androidApplication()) }
    single { createDetailMatchDao(get()) }
    single { createDetailTeamDao(get()) }
}

val footballServiceModule = listOf(networkModule, databaseModule)