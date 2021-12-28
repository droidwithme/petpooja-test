package com.petpooja.store.presentation.di.component

import com.petpooja.store.PetPoojaApp
import com.petpooja.store.presentation.di.builder.ActivityBuilder
import com.petpooja.store.presentation.di.module.*
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton



@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class),
    (NetworkModule::class), (RepoModule::class), (ActivityBuilder::class)])

interface AppComponent {

    fun inject(app: PetPoojaApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}