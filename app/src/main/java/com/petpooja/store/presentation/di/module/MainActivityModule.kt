package com.petpooja.store.presentation.di.module

import com.petpooja.store.data.repository.DomainRepoImp
import com.petpooja.store.presentation.main.MainViewModel
import dagger.Module
import dagger.Provides



@Module
class MainActivityModule {

    @Provides
    internal fun provideMainViewModel(lmdRepo: DomainRepoImp): MainViewModel {
        return MainViewModel(lmdRepo)
    }

}