package com.elevenetc.android.news.core.repositories

import com.elevenetc.android.news.core.models.Article
import io.reactivex.Observable

interface ArticlesRepository {

    fun get(page: Int, pageSize: Int): Observable<out Result>

    sealed class Result {
        data class Cached(val data: List<Article>) : Result()
        data class Network(val data: List<Article>) : Result()
        data class NetworkError(val error: Throwable) : Result()
    }
}