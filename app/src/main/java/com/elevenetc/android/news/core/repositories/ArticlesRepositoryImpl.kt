package com.elevenetc.android.news.core.repositories

import com.elevenetc.android.news.core.api.NewsApi
import com.elevenetc.android.news.core.cache.ArticlesCache
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result
import io.reactivex.Observable
import io.reactivex.Observable.concat
import io.reactivex.Single
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val cache: ArticlesCache
) : ArticlesRepository {

    override fun get(page: Int, pageSize: Int): Observable<out Result> {
        return concat(getCachedPage(page, pageSize), loadAndCachePage(page, pageSize))
    }

    override fun get(id: String): Single<out Result> =
        cache.get(id)
            .map { Result.CachedArticle(it) }
            .cast(Result::class.java)
            .switchIfEmpty(Single.just(Result.NoCachedArticle))


    private fun getCachedPage(page: Int, pageSize: Int): Observable<out Result> =
        cache.get(page, pageSize)
            .map { Result.CachedList(it) }
            .toObservable()

    private fun loadAndCachePage(page: Int, pageSize: Int): Observable<out Result> =
        api.get(page, pageSize)
            .flatMap { cache.store(it, page) }
            .map { Result.NetworkList(it) }
            .cast(Result::class.java)
            .onErrorReturn { Result.NetworkError(it) }
            .toObservable()
}