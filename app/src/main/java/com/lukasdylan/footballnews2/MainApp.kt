package com.lukasdylan.footballnews2

import android.app.Application
import com.lukasdylan.core.module.coreModules
import com.lukasdylan.football.module.footballModules
import com.lukasdylan.home.module.homeModules
import com.lukasdylan.footballservice.module.footballServiceModule
import com.lukasdylan.newsservice.module.newsServiceModules
import org.koin.android.ext.android.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, coreModules + footballServiceModule + newsServiceModules + homeModules + footballModules)
    }

}