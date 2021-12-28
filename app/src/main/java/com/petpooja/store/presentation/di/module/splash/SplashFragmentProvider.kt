package com.petpooja.store.presentation.di.module.splash

import com.petpooja.store.presentation.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashFragmentProvider {

    @ContributesAndroidInjector(modules = [(SplashFragmentModule::class)])
    internal abstract fun provideMainFragmentFactory(): SplashFragment


}
