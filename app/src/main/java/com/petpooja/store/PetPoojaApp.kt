package com.petpooja.store

import android.app.Activity
import android.app.Application
import android.util.Log
import com.petpooja.store.presentation.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/*
* Main Application instance
* */
class PetPoojaApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    /*
    * Setting up the Dagger
    * */
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("DroidApp", "onCreate()")
        DaggerAppComponent.builder().application(this).build().inject(this)
    }


}