

package com.petpooja.store.presentation.di.module.products

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.petpooja.store.domain.usecase.ProductUseCase
import com.petpooja.store.presentation.commons.GridSpacingItemDecoration
import com.petpooja.store.presentation.commons.ViewModelProviderFactory
import com.petpooja.store.presentation.main.MainActivity
import com.petpooja.store.presentation.ui.products.ProductAdapter
import com.petpooja.store.presentation.ui.products.ProductFragment
import com.petpooja.store.presentation.ui.products.ProductFragmentViewModel
import dagger.Module
import dagger.Provides


@Module
class ProductFragmentModule {

    @Provides
    internal fun provideMainFragmentViewModel(productUseCase: ProductUseCase): ProductFragmentViewModel {
        return ProductFragmentViewModel(productUseCase)
    }

    @Provides
    internal fun mainFragmentViewModelProvider(productFragmentViewModel: ProductFragmentViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(productFragmentViewModel)
   }

    @Provides
    internal fun provideLinearLayoutManager(fragment: ProductFragment): LinearLayoutManager {
        return LinearLayoutManager((fragment.activity as Context?)!!)
    }

    @Provides
    internal fun provideGridSpacingItemDecoration(): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(2, 5, true)
    }

    @Provides
    internal fun provideMovieAdapter(context: MainActivity): ProductAdapter {
        return ProductAdapter(ArrayList(), context)
    }

    @Provides
    internal fun getGson(): Gson {
        return Gson()
    }

}