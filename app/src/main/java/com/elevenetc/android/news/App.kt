package com.elevenetc.android.news

import android.app.Application
import com.elevenetc.android.news.core.di.AppModule
import com.elevenetc.android.news.core.di.DaggerAppComponent

class App : Application() {
    val appComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
}