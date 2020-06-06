package com.elevenetc.android.news.core.api.newsapi

import com.elevenetc.android.news.core.api.NewsApi
import com.elevenetc.android.news.core.models.Article
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class NewsApiOrgProvider @Inject constructor(
    val mapper: ModelsMapper,
    val api: NewsApiOrg,
    @Named(NewsApiOrgModule.Names.API_KEY) val apiKey: String
) : NewsApi {

    /**
     * NewsApiOrg unconventionally starts pages counting from 1
     */
    private val defaultPageShift = 1

    override fun get(page: Int, pageSize: Int): Single<List<Article>> {
        var time = 0L
        if(page > 0){
            time = 1L
        }
        return mapper
            .map(api.headlines("nl", page + defaultPageShift, pageSize, apiKey)
            .delay(time, TimeUnit.SECONDS))
    }
}