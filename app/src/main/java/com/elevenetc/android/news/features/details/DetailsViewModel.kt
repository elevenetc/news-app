package com.elevenetc.android.news.features.details

import androidx.lifecycle.LiveData
import com.elevenetc.android.news.core.models.Article

interface DetailsViewModel {

    val state: LiveData<State>
    fun onAction(action: Action)

    sealed class Action {
        data class GetArticle(val id: String) : Action()
    }

    sealed class State {
        object Init : State()
        object Loading : State()
        data class CachedResult(val article: Article) : State()
        object NoResult : State()
        data class NetworkResult(val article: Article) : State()
        data class ErrorResult(val error: Throwable) : State()
    }
}