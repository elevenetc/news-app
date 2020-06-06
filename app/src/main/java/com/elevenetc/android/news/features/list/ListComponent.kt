package com.elevenetc.android.news.features.list

import dagger.Subcomponent

@Subcomponent(modules = [ListModule::class])
interface ListComponent {
    fun viewModelFactory(): ViewModelFactory
}