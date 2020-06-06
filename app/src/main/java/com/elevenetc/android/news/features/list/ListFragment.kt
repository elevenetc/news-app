package com.elevenetc.android.news.features.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevenetc.android.news.R
import com.elevenetc.android.news.core.BaseFragment
import com.elevenetc.android.news.features.list.ListViewModel.State.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment(R.layout.fragment_list) {

    private lateinit var vm: ListViewModel
    private var page = 0
    private val pageSize = 10
    private var end = false
    private val adapter = Adapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vmFactory = appComponent.list().viewModelFactory()
        vm = ViewModelProvider(this, vmFactory).get(ListViewModelImpl::class.java)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = adapter

        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    if (!end && vm.state.value !is Loading) {
                        page++
                        vm.onAction(ListViewModel.Action.GetArticles(page, pageSize))
                    }
                }
            }
        })

        vm.state.observe(viewLifecycleOwner, Observer { state -> handleState(state) })
        vm.onAction(ListViewModel.Action.GetArticles(0, pageSize))
    }

    private fun handleState(state: ListViewModel.State) {
        if (state is Idle) {
            progressCache.show()
            progressNetworkRefresh.hide()
            recycleView.visibility = View.INVISIBLE
        } else if (state is Loading) {
            progressCache.show()
            //adapter.setLoading(true)
        } else if (state is CachedResult) {
            progressCache.hide()
            progressNetworkRefresh.show()

            adapter.append(state.data)
        } else if (state is ErrorResult) {

        } else if (state is NetworkResult) {

            if (state.data.size < pageSize) {
                end = true
            }

            progressCache.hide()
            progressNetworkRefresh.hide()
            recycleView.visibility = View.VISIBLE

            adapter.appendOrRefresh(state.data, state.page, state.pageSize)
        }
    }
}