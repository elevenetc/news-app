package com.elevenetc.android.news

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elevenetc.android.news.core.models.Article
import com.elevenetc.android.news.core.usecases.GetArticles
import com.elevenetc.android.news.features.list.ListViewModel.Action
import com.elevenetc.android.news.features.list.ListViewModel.State.*
import com.elevenetc.android.news.features.list.ListViewModelImpl
import com.elevenetc.android.news.testing.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListViewModelTests : BaseInstrumentationTest() {

    val schedulers = CurrentThreadSchedulers()
    val logger = FakeLogger()
    val repository = InMemoryArticlesRepository()
    val getArticles = GetArticles(repository)

    @Test
    fun twoPagesLoading() {

        runOnUi {

            val page0 = listOf(Article("a"), Article("b"), Article("c"))
            val page1 = listOf(Article("d"), Article("e"), Article("f"))
            val pageSize = 3

            repository.add(0, page0)
            repository.add(1, page1)

            val vm = ListViewModelImpl(schedulers, getArticles, logger)

            val stateCollector =
                StateCollector(vm.state)

            vm.onAction(Action.GetArticles(0, pageSize))
            vm.onAction(Action.GetArticles(1, pageSize))

            stateCollector.assert(
                listOf(
                    Init,
                    Loading,
                    CachedResult(page0, 0, 3),
                    NetworkResult(page0, 0, 3),
                    Loading,
                    CachedResult(page1, 1, 3),
                    NetworkResult(page1, 1, 3)
                )
            )
        }
    }
}
