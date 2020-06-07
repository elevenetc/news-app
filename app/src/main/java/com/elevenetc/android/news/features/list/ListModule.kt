package com.elevenetc.android.news.features.list

import com.elevenetc.android.news.core.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ListModule {
    @Provides
    fun viewModel(inst: ListViewModelImpl): ListViewModel = inst

    @Provides
    fun viewModelFactory(vm: ListViewModel): ViewModelFactory {
        return ViewModelFactory(vm)
    }
}