package com.elevenetc.android.news.features.details

import com.elevenetc.android.news.core.utils.ViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    fun viewModelFactory(): ViewModelFactory
}