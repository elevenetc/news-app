package com.elevenetc.android.news.core.api.newsapi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NewsApiOrg {

    @GET("v2/top-headlines")
    fun headlines(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): Single<Headlines>

    data class Headlines(val articles: List<Article>)

    data class Article(
        val title: String,
        val author: String,
        val source: Source,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: Date,
        val content: String
    )

    data class Source(
        val id: String?,
        val name: String
    )
}