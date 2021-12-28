

package com.petpooja.store.presentation.di.module.cart

import com.petpooja.store.presentation.ui.cart.CartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class CartFragmentProvider {

    @ContributesAndroidInjector(modules =[(CartFragmentModule::class)])
    internal abstract fun provideCartFragmentFactory(): CartFragment

}