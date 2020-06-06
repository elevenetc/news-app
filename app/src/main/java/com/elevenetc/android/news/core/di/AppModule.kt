package com.elevenetc.android.news.core.di

import android.content.Context
import com.elevenetc.android.news.core.cache.ArticlesCache
import com.elevenetc.android.news.core.cache.ArticlesCacheImpl
import com.elevenetc.android.news.core.logging.Logger
import com.elevenetc.android.news.core.logging.LoggerImpl
import com.elevenetc.android.news.core.repositories.ArticlesRepository
import com.elevenetc.android.news.core.repositories.ArticlesRepositoryImpl
import com.elevenetc.android.news.core.scheduling.Schedulers
import com.elevenetc.android.news.core.scheduling.SchedulersImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Provides
    fun schedulers(inst: SchedulersImpl): Schedulers = inst

    @Provides
    fun logger(inst: LoggerImpl): Logger = inst

    @Provides
    @Singleton
    fun articlesCache(inst: ArticlesCacheImpl): ArticlesCache = inst

    @Provides
    @Singleton
    fun articlesRepository(inst: ArticlesRepositoryImpl): ArticlesRepository = inst

    @Provides
    fun appContext(): Context {
        return appContext
    }
}