package com.elevenetc.android.news.core.api.newsapi

import com.elevenetc.android.news.core.api.NewsApi
import com.elevenetc.android.news.core.models.Article
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class NewsApiImpl @Inject constructor(
    val mapper: ModelsMapper,
    val api: NewsApiOrg,
    @Named(NewsApiOrgModule.Names.API_KEY) val apiKey: String
) : NewsApi {

    /**
     * NewsApiOrg unconventionally starts pages list/array from 1 index
     */
    private val defaultPageShift = 1
    private val country = "nl"

    override fun get(page: Int, pageSize: Int): Single<List<Article>> {
        return mapper.map(api.headlines(country, page + defaultPageShift, pageSize, apiKey))
    }
}