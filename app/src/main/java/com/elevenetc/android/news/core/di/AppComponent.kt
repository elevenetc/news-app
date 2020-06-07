package com.elevenetc.android.news.core.di

import com.elevenetc.android.news.core.api.newsapi.NewsApiOrgModule
import com.elevenetc.android.news.core.images.ImagesLoader
import com.elevenetc.android.news.core.logging.Logger
import com.elevenetc.android.news.core.scheduling.Schedulers
import com.elevenetc.android.news.features.details.DetailsComponent
import com.elevenetc.android.news.features.list.ListComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NewsApiOrgModule::class])
interface AppComponent {

    fun images(): ImagesLoader
    fun schedulers(): Schedulers
    fun logger(): Logger

    fun list(): ListComponent
    fun details(): DetailsComponent
}