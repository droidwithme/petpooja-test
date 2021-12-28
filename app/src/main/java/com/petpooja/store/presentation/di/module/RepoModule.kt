package com.petpooja.store.presentation.di.module

import android.content.Context
import com.petpooja.store.data.api.live.APIServices
import com.petpooja.store.data.repository.DomainRepoImp
import com.petpooja.store.domain.repository.DomainRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepoModule {
    @Provides
    @Singleton
    internal fun provideLmdRepository(APIServices: APIServices, context: Context): DomainRepo {
        return DomainRepoImp(APIServices, context)
    }

}