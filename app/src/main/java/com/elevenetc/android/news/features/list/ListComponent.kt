package com.elevenetc.android.news.features.list

import com.elevenetc.android.news.core.utils.ViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [ListModule::class])
interface ListComponent {
    fun viewModelFactory(): ViewModelFactory
}