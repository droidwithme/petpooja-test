

package com.petpooja.store.presentation.di.module.products

import com.petpooja.store.presentation.ui.products.ProductFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ProductFragmentProvider {

    @ContributesAndroidInjector(modules =[(ProductFragmentModule::class)])
    internal abstract fun provideMainFragmentFactory(): ProductFragment

}