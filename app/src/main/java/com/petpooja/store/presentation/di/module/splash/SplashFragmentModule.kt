package com.petpooja.store.presentation.di.module.splash

import androidx.lifecycle.ViewModelProvider
import com.petpooja.store.presentation.commons.ViewModelProviderFactory
import com.petpooja.store.presentation.ui.splash.SplashFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class SplashFragmentModule {
    @Provides
    internal fun provideMainFragmentViewModel(): SplashFragmentViewModel {
        return SplashFragmentViewModel()
    }

    @Provides
    internal fun mainFragmentViewModelProvider(splashFragmentViewModel: SplashFragmentViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(splashFragmentViewModel)
    }

}