package com.elevenetc.android.news.core.cache

import com.elevenetc.android.news.core.models.Article
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class ArticlesCacheImpl @Inject constructor() : ArticlesCache {

    //TODO: add tests

    private val pages = mutableMapOf<Int, List<Article>>()
    private val articles = mutableMapOf<String, Article>()

    override fun store(
        articles: List<Article>,
        page: Int
    ): Single<List<Article>> {
        articles.forEach { this.articles[it.id] = it }
        pages[page] = articles
        return Single.just(articles)
    }

    override fun get(page: Int, pageSize: Int): Single<List<Article>> {
        val result = pages.getOrDefault(page, emptyList())
        return Single.just(result)
    }

    override fun get(id: String): Maybe<Article> {
        return if (articles.containsKey(id)) {
            Maybe.just(articles[id])
        } else {
            Maybe.empty()
        }
    }

}