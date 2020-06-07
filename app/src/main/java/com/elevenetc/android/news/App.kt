package com.elevenetc.android.news

import android.app.Application
import com.elevenetc.android.news.core.di.AppComponent
import com.elevenetc.android.news.core.di.AppModule
import com.elevenetc.android.news.core.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}