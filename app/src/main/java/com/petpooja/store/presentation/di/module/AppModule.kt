

package com.petpooja.store.presentation.di.module

import android.app.Application
import android.content.Context
import com.petpooja.store.presentation.commons.AppUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class AppModule {


    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideAppUtils(): AppUtils {
        return AppUtils()
    }
}