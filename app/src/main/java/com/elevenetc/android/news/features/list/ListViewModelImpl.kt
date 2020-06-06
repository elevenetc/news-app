package com.elevenetc.android.news.features.list

import androidx.lifecycle.MutableLiveData
import com.elevenetc.android.news.core.logging.Logger
import com.elevenetc.android.news.core.repositories.ArticlesRepository.Result.*
import com.elevenetc.android.news.core.scheduling.Schedulers
import com.elevenetc.android.news.core.usecases.GetArticles
import com.elevenetc.android.news.features.list.ListViewModel.Action
import com.elevenetc.android.news.features.list.ListViewModel.State
import com.elevenetc.android.news.features.list.ListViewModel.State.Idle
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ListViewModelImpl @Inject constructor(
    private val schedulers: Schedulers,
    private val getArticles: GetArticles,
    private val logger: Logger
) : ListViewModel, androidx.lifecycle.ViewModel() {

    override val state = MutableLiveData<State>(Idle)
    private val subs = CompositeDisposable()

    override fun onCleared() {
        subs.dispose()
    }

    override fun onAction(action: Action) {
        reduce(action, state.value!!)
    }

    private fun reduce(action: Action, currentState: State) {

        if (currentState is Idle && action is Action.GetArticles) {
            getArticles(action.page, action.pageSize)
        } else if (currentState is State.CachedResult && action is Action.GetArticles) {
            getArticles(action.page, action.pageSize)
        } else if (currentState is State.NetworkResult && action is Action.GetArticles) {
            getArticles(action.page, action.pageSize)
        } else if (currentState is State.ErrorResult && action is Action.GetArticles) {
            getArticles(action.page, action.pageSize)
        } else if (currentState is State.Loading) {
            //ignore, next state is either loaded or error
        } else {
            logger.logD("Unimplemented state. Current state: $currentState. Action: $action")
        }
    }

    private fun updateCurrentState(newState: State) {
        state.postValue(newState)
    }

    private fun getArticles(page: Int, pageSize: Int) {

        updateCurrentState(State.Loading)

        subs.add(

            getArticles.get(page, pageSize)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui())
                .subscribe({ result ->

                    when (result) {
                        is Cached -> {
                            updateCurrentState(State.CachedResult(result.data, page, pageSize))
                        }
                        is Network -> {
                            updateCurrentState(State.NetworkResult(result.data, page, pageSize))
                        }
                        is NetworkError -> {
                            updateCurrentState(State.ErrorResult(result.error))
                        }
                    }

                }, {
                    updateCurrentState(State.ErrorResult(it))
                })

        )
    }
}