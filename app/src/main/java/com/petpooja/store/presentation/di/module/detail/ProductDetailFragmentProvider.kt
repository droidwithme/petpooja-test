package com.petpooja.store.presentation.di.module.detail

import com.petpooja.store.presentation.ui.detail.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ProductDetailFragmentProvider {

    @ContributesAndroidInjector(modules = [(ProductDetailFragmentModule::class)])
    internal abstract fun provideDetailFragmentFactory(): DetailFragment

}