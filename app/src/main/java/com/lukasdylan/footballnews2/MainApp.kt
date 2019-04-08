package com.lukasdylan.footballnews2

import android.app.Application
import com.lukasdylan.core.module.coreModules
import com.lukasdylan.football.module.footballModules
import com.lukasdylan.footballservice.module.footballServiceModule
import com.lukasdylan.home.module.homeModules
import com.lukasdylan.news.module.newsModules
import com.lukasdylan.newsservice.module.newsServiceModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            androidLogger()
            modules(coreModules + footballServiceModule + newsServiceModules + homeModules + footballModules + newsModules)
        }
    }
}