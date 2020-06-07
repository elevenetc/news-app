package com.elevenetc.android.news.features.details

import androidx.lifecycle.MutableLiveData
import com.elevenetc.android.news.core.logging.Logger
import com.elevenetc.android.news.core.repositories.ArticlesRepository
import com.elevenetc.android.news.core.scheduling.Schedulers
import com.elevenetc.android.news.core.usecases.GetArticle
import com.elevenetc.android.news.features.details.DetailsViewModel.Action
import com.elevenetc.android.news.features.details.DetailsViewModel.State
import io.reactivex.disposables.CompositeDisposable
import java.lang.RuntimeException
import javax.inject.Inject

class DetailsViewModelImpl @Inject constructor(
    private val schedulers: Schedulers,
    private val getArticle: GetArticle,
    private val logger: Logger
) : DetailsViewModel, androidx.lifecycle.ViewModel() {

    private val subs = CompositeDisposable()
    override val state = MutableLiveData<State>(State.Init)

    override fun onCleared() {
        subs.dispose()
    }

    override fun onAction(action: Action) {
        reduce(action, state.value!!)
    }

    private fun updateCurrentState(newState: State) {
        state.value = newState
    }

    private fun reduce(action: Action, currentState: State) {
        if (currentState == State.Init && action is Action.GetArticle) {
            updateCurrentState(State.Loading)
            getArticle(action)
        }
    }

    private fun getArticle(action: Action.GetArticle) {
        subs.add(
            getArticle.get(action.id)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui())
                .subscribe({
                    when (it) {
                        is ArticlesRepository.Result.NoCachedArticle -> {
                            updateCurrentState(State.NoResult)
                        }
                        is ArticlesRepository.Result.CachedArticle -> {
                            updateCurrentState(State.CachedResult(it.data))
                        }
                        is ArticlesRepository.Result.NetworkArticle -> {
                            updateCurrentState(State.NetworkResult(it.data))
                        }
                        is ArticlesRepository.Result.ErrorGettingArticle -> {
                            updateCurrentState(State.ErrorResult(it.error))
                        }
                    }
                }, {
                    updateCurrentState(State.ErrorResult(it))
                })
        )
    }

}