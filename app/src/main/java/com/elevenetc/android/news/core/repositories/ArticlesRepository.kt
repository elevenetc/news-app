package com.elevenetc.android.news.core.repositories

import com.elevenetc.android.news.core.models.Article
import io.reactivex.Observable
import io.reactivex.Single

interface ArticlesRepository {

    fun get(id: String): Single<out Result>
    fun get(page: Int, pageSize: Int): Observable<out Result>

    sealed class Result {

        data class CachedArticle(val data: Article) : Result()
        data class NetworkArticle(val data: Article) : Result()
        data class ErrorGettingArticle(val error: Throwable) : Result()
        object NoCachedArticle : Result()

        data class CachedList(val data: List<Article>) : Result()
        data class NetworkList(val data: List<Article>) : Result()
        data class NetworkError(val error: Throwable) : Result()
    }
}