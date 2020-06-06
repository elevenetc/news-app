package com.elevenetc.android.news.core.repositories

import android.util.Log
import com.elevenetc.android.news.core.api.NewsApi
import com.elevenetc.android.news.core.cache.ArticlesCache
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result
import io.reactivex.Observable
import io.reactivex.Observable.concat
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val cache: ArticlesCache
) : ArticlesRepository {

    override fun get(page: Int, pageSize: Int): Observable<out Result> {
        return concat(getCached(page, pageSize), loadAndCache(page, pageSize))
    }

    private fun getCached(page: Int, pageSize: Int): Observable<out Result> =
        cache.get(page, pageSize)
            .map { Result.Cached(it) }
            .toObservable()

    private fun loadAndCache(page: Int, pageSize: Int): Observable<out Result> =
        api.get(page, pageSize)
            .flatMap { cache.store(it, page) }
            .map { Result.Network(it) }
            .cast(Result::class.java)
            .onErrorReturn { Result.NetworkError(it) }
            .doOnEvent { result, error ->
                val network = result as Result.Network
                val article = network.data[0]
                Log.d("tagz", "first art on page$page is " + article.title)
            }
            .toObservable()
}