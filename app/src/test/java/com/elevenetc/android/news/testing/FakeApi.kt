package com.elevenetc.android.news.testing

import com.elevenetc.android.news.core.api.NewsApi
import com.elevenetc.android.news.core.models.Article
import io.reactivex.Single

class FakeApi(val pages: Map<Int, List<Article>>) : NewsApi {
    override fun get(page: Int, pageSize: Int): Single<List<Article>> {
        return if (pages.containsKey(page)) {
            Single.just(pages[page])
        } else {
            Single.just(emptyList())
        }
    }
}