package com.elevenetc.android.news.core.api

import com.elevenetc.android.news.core.models.Article
import io.reactivex.Single

interface NewsApi {
    fun get(page: Int, pageSize: Int): Single<List<Article>>
}