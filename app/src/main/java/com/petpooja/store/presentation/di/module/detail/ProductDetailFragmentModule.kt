

package com.petpooja.store.presentation.di.module.detail

import androidx.lifecycle.ViewModelProvider
import com.petpooja.store.presentation.commons.ViewModelProviderFactory
import com.petpooja.store.presentation.ui.detail.DetailFragmentViewModel
import dagger.Module
import dagger.Provides


@Module
class ProductDetailFragmentModule {

    @Provides
    internal fun provideMainFragmentViewModel(): DetailFragmentViewModel {
        return DetailFragmentViewModel()
    }

    @Provides
    internal fun mainFragmentViewModelProvider(detailFragmentViewModel: DetailFragmentViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(detailFragmentViewModel)
   }
}