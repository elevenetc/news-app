package com.elevenetc.android.news.features.list

import androidx.lifecycle.LiveData
import com.elevenetc.android.news.core.models.Article

interface ListViewModel {

    val state: LiveData<State>
    fun onAction(action: Action)

    sealed class Action {
        data class GetArticles(val page: Int, val pageSize: Int) : Action()
    }

    sealed class State {
        object Idle : State()
        object Loading : State()
        data class CachedResult(val data: List<Article>, val page: Int, val pageSize: Int) : State()
        data class NetworkResult(val data: List<Article>, val page: Int, val pageSize: Int) : State()
        data class ErrorResult(val error: Throwable) : State()
    }
}