package com.elevenetc.android.news.core.cache

import com.elevenetc.android.news.core.models.Article
import io.reactivex.Maybe
import io.reactivex.Single

interface ArticlesCache {
    fun store(articles: List<Article>, page: Int): Single<List<Article>>
    fun get(page: Int, pageSize: Int): Single<List<Article>>
    fun get(id: String): Maybe<Article>
}