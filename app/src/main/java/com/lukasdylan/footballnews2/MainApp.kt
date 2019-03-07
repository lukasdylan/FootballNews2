package com.lukasdylan.footballnews2

import android.app.Application
import com.lukasdylan.core.module.coreModules
import com.lukasdylan.football.module.footballModules
import com.lukasdylan.footballservice.module.footballServiceModule
import com.lukasdylan.home.module.homeModules
import com.lukasdylan.news.module.newsModules
import com.lukasdylan.newsservice.module.newsServiceModules
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import com.lukasdylan.footballnews2.R as Resources

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            androidContext = this,
            modules = coreModules + footballServiceModule + newsServiceModules + homeModules + footballModules + newsModules,
            logger = AndroidLogger(BuildConfig.DEBUG)
        )
    }
}