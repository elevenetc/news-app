package com.elevenetc.android.news.core.usecases

import com.elevenetc.android.news.core.repositories.ArticlesRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetArticles @Inject constructor(
    private val repository: ArticlesRepository
) : UseCase {
    fun get(page: Int, pageSize: Int): Observable<out ArticlesRepository.Result> {
        return repository.get(page, pageSize)
    }
}