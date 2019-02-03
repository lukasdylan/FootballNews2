package com.lukasdylan.footballnews2

import android.app.Application
import com.lukasdylan.core.module.coreModules
import com.lukasdylan.football.module.footballModules
import com.lukasdylan.home.module.homeModules
import com.lukasdylan.footballservice.module.footballServiceModule
import com.lukasdylan.news.module.newsModules
import com.lukasdylan.newsservice.module.newsServiceModules
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.koin.android.ext.android.startKoin
import org.koin.log.EmptyLogger
import org.koin.log.Logger
import com.lukasdylan.footballnews2.R as Resources

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val logger = if (BuildConfig.DEBUG) {
            object : Logger {

                override fun debug(msg: String) {
                    AnkoLogger(getString(Resources.string.app_name)).debug { msg }
                }

                override fun err(msg: String) {
                    AnkoLogger(getString(Resources.string.app_name)).error { msg }
                }

                override fun info(msg: String) {
                    AnkoLogger(getString(Resources.string.app_name)).info { msg }
                }
            }
        } else {
            EmptyLogger()
        }
        startKoin(
            this,
            coreModules + footballServiceModule + newsServiceModules + homeModules + footballModules + newsModules,
            logger = logger
        )
    }
}