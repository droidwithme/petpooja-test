

package com.petpooja.store.presentation.di.module.cart

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.petpooja.store.domain.usecase.ProductUseCase
import com.petpooja.store.presentation.commons.GridSpacingItemDecoration
import com.petpooja.store.presentation.commons.ViewModelProviderFactory
import com.petpooja.store.presentation.main.MainActivity
import com.petpooja.store.presentation.ui.cart.CartAdapter
import com.petpooja.store.presentation.ui.cart.CartFragment
import com.petpooja.store.presentation.ui.cart.CartFragmentViewModel
import dagger.Module
import dagger.Provides


@Module
class CartFragmentModule {

    @Provides
    internal fun provideMainFragmentViewModel(productUseCase: ProductUseCase): CartFragmentViewModel {
        return CartFragmentViewModel(productUseCase)
    }

    @Provides
    internal fun mainFragmentViewModelProvider(cartFragmentViewModel: CartFragmentViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(cartFragmentViewModel)
   }

    @Provides
    internal fun provideLinearLayoutManager(fragment: CartFragment): LinearLayoutManager {
        return LinearLayoutManager((fragment.activity as Context?)!!)
    }

    @Provides
    internal fun provideGridSpacingItemDecoration(): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(2, 5, true)
    }

    @Provides
    internal fun provideMovieAdapter(context: MainActivity): CartAdapter {
        return CartAdapter(ArrayList(), context)
    }

    @Provides
    internal fun getGson(): Gson {
        return Gson()
    }

}