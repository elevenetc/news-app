package com.elevenetc.android.news.core.usecases

import com.elevenetc.android.news.core.repositories.ArticlesRepository
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result
import io.reactivex.Single
import javax.inject.Inject

class GetArticle @Inject constructor(
    private val repository: ArticlesRepository
) : UseCase {
    fun get(id: String): Single<out Result> {
        return repository.get(id)
    }
}