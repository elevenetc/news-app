package com.elevenetc.android.news.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.elevenetc.android.news.App
import com.elevenetc.android.news.core.di.AppComponent
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId) {
    /**
     * Available after [Fragment.onAttach] is called
     */
    val appComponent: AppComponent by lazy { (context!!.applicationContext as App).appComponent }

    protected var subs = CompositeDisposable()

    override fun onDestroyView() {
        subs.dispose()
        super.onDestroyView()
    }

    fun showRetry() {

    }
}