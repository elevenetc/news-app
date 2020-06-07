package com.elevenetc.android.news.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.elevenetc.android.news.App

open class BaseFragment(@LayoutRes val contentLayoutId: Int) : Fragment(contentLayoutId) {
    val appComponent = App.appComponent
}