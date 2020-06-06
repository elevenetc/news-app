package com.elevenetc.android.news.core.api.newsapi

import com.elevenetc.android.news.core.models.Article
import io.reactivex.Single

class ModelsMapper {
    fun map(headlines: Single<NewsApiOrg.Headlines>): Single<List<Article>> {
        return headlines.map {
            map(it.articles)
        }
    }

    fun map(articles: List<NewsApiOrg.Article>): List<Article> {
        return articles.map { map(it) }
    }

    fun map(article: NewsApiOrg.Article): Article {
        return Article(
            genId(article),
            article.title,
            article.description,
            article.urlToImage
        )
    }

    /**
     * Simplified way of generating unique id.
     */
    private fun genId(article: NewsApiOrg.Article): String {
        return article.url + article.title + article.urlToImage
    }
}