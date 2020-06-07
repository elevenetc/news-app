package com.elevenetc.android.news.features.details

import com.elevenetc.android.news.core.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {
    
    @Provides
    fun viewModel(inst: DetailsViewModelImpl): DetailsViewModel = inst

    @Provides
    fun viewModelFactory(vm: DetailsViewModel): ViewModelFactory {
        return ViewModelFactory(vm)
    }
}