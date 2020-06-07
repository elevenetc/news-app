package com.elevenetc.android.news.features.list

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevenetc.android.news.R
import com.elevenetc.android.news.core.BaseFragment
import com.elevenetc.android.news.core.models.Article
import com.elevenetc.android.news.features.details.DetailsFragment
import com.elevenetc.android.news.features.list.ListViewModel.Action.GetArticles
import com.elevenetc.android.news.features.list.ListViewModel.State.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlin.math.floor


class ListFragment : BaseFragment(R.layout.fragment_list) {

    private lateinit var vm: ListViewModel
    private var page = 0
    private var pageSize = 20
    private var reachedEnd = false
    private val adapter = Adapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        configAdapter()
        configViews()
        configPageSize()

        val vmFactory = appComponent.list().viewModelFactory()
        vm = ViewModelProvider(this, vmFactory).get(ListViewModelImpl::class.java)
        vm.state.observe(viewLifecycleOwner, Observer { state -> handleState(state) })
        vm.onAction(GetArticles(0, pageSize))
    }

    private fun handleState(state: ListViewModel.State) {
        if (state is Loading) {

            textNoArticles.visibility = View.INVISIBLE

            if (adapter.isEmpty()) {
                retryProgressView.showProgress()
            } else {
                adapter.setErrorState(false)
            }
        } else if (state is CachedResult) {
            if (state.data.isNotEmpty()) retryProgressView.hide()
            adapter.append(state.data)
        } else if (state is ErrorResult) {
            if (adapter.isEmpty()) {
                retryProgressView.showError()
            } else {
                adapter.setErrorState(true)
            }
        } else if (state is NetworkResult) {

            if (state.data.size < pageSize) reachedEnd = true

            retryProgressView.hide()
            recycleView.visibility = View.VISIBLE

            adapter.appendOrRefresh(state.data, state.page, state.pageSize)

            if (adapter.isEmpty()) {
                textNoArticles.visibility = View.VISIBLE
            }
        }
    }

    private fun configAdapter() {
        adapter.retryHandler = { vm.onAction(GetArticles(page, pageSize)) }
        adapter.itemClickHandler = { openArticleDetails(it) }
    }

    private fun configViews() {
        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = adapter

        retryProgressView.retryHandler = {
            vm.onAction(GetArticles(page, pageSize))
        }

        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    if (!reachedEnd && vm.state.value !is Loading) {
                        page++
                        vm.onAction(GetArticles(page, pageSize))
                    }
                }
            }
        })
    }

    private fun configPageSize() {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val dimension = resources.getDimension(R.dimen.list_item_height)

        pageSize = (floor(height / dimension)).toInt() * 2
    }

    private fun openArticleDetails(article: Article) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .add(R.id.root, DetailsFragment.create(article.id))
            .addToBackStack(null)
            .commit()
    }
}