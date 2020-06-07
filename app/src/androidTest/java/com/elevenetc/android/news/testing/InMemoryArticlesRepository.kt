package com.elevenetc.android.news.testing

import com.elevenetc.android.news.core.models.Article
import com.elevenetc.android.news.core.repositories.ArticlesRepository
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result.CachedArticle
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result.NoCachedArticle
import io.reactivex.Observable
import io.reactivex.Single

class InMemoryArticlesRepository : ArticlesRepository {

    private val map = mutableMapOf<String, Article>()
    private val pages = mutableMapOf<Int, List<Article>>()

    fun add(page: Int, articles: List<Article>) {
        articles.forEach {
            map[it.id] = it
        }
        pages[page] = articles
    }

    override fun get(id: String): Single<out ArticlesRepository.Result> {
        return if (map.containsKey(id)) {
            val article = map[id]!!
            Single.just(CachedArticle(article))
        } else {
            Single.just((NoCachedArticle))
        }
    }

    override fun get(page: Int, pageSize: Int): Observable<out ArticlesRepository.Result> {

        return if (pages.containsKey(page)) {
            val list = pages[page]!!
            Observable.concat(
                Observable.just(ArticlesRepository.Result.CachedList(list)),
                Observable.just(ArticlesRepository.Result.NetworkList(list))
            )
        } else {
            Observable.error(RuntimeException("no page: $page"))
        }
    }

    fun clear() {
        map.clear()
        pages.clear()
    }
}