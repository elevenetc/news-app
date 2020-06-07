package com.elevenetc.android.news

import com.elevenetc.android.news.core.cache.ArticlesCacheImpl
import com.elevenetc.android.news.core.models.Article
import com.elevenetc.android.news.core.repositories.ArticlesRepository
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result.CachedList
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result.NetworkList
import com.elevenetc.android.news.core.repositories.ArticlesRepositoryImpl
import com.elevenetc.android.news.testing.FakeApi
import org.junit.Test

class TestArticlesRepository {

    val pageSize = 3
    val page0 = 0 to listOf(Article("0-a"), Article("0-b"), Article("0-c"))
    val page1 = 1 to listOf(Article("1-a"), Article("1-b"), Article("1-c"))
    val pages = mapOf(page0, page1)

    val repository = ArticlesRepositoryImpl(FakeApi(pages), ArticlesCacheImpl())

    @Test
    fun loadingAndFillingCached() {

        //page 0: cache is empty, network returned
        repository.get(0, pageSize)
            .cast(ArticlesRepository.Result::class.java)
            .test()
            .assertResult(CachedList(emptyList()), NetworkList(page0.second))

        //page 0: cache is filled, network returned
        repository.get(0, pageSize)
            .cast(ArticlesRepository.Result::class.java)
            .test()
            .assertResult(CachedList(page0.second), NetworkList(page0.second))

        //page 1: cache is empty, network returned
        repository.get(1, pageSize)
            .cast(ArticlesRepository.Result::class.java)
            .test()
            .assertResult(CachedList(emptyList()), NetworkList(page1.second))

        //page 1: cache is filled, network returned
        repository.get(1, pageSize)
            .cast(ArticlesRepository.Result::class.java)
            .test()
            .assertResult(CachedList(page1.second), NetworkList(page1.second))
    }


}