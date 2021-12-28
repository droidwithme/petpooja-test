package com.petpooja.store.presentation.di.builder

import com.petpooja.store.presentation.di.module.MainActivityModule
import com.petpooja.store.presentation.di.module.cart.CartFragmentProvider
import com.petpooja.store.presentation.di.module.detail.ProductDetailFragmentProvider
import com.petpooja.store.presentation.di.module.products.ProductFragmentProvider
import com.petpooja.store.presentation.di.module.splash.SplashFragmentProvider
import com.petpooja.store.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
            modules = [(MainActivityModule::class), (SplashFragmentProvider::class),
                (ProductFragmentProvider::class), (ProductDetailFragmentProvider::class), (CartFragmentProvider::class)]
    )
    internal abstract fun bindMainActivity(): MainActivity

}