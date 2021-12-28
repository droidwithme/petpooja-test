package com.petpooja.store.presentation.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.petpooja.store.R
import com.petpooja.store.presentation.base.BaseActivity
import com.petpooja.store.presentation.ui.cart.CartFragment
import com.petpooja.store.presentation.ui.detail.DetailFragment
import com.petpooja.store.presentation.ui.splash.SplashFragment
import com.petpooja.store.presentation.ui.products.ProductFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mMainViewModel: MainViewModel

    private lateinit var mContext: Context
    private var doubleBackToExitPressedOnce = false
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): MainViewModel = mMainViewModel


    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setUp()
        Log.e("MainActivity", "onCreate()")
    }

    private fun setUp() {
        setSupportActionBar(toolbar)
        Log.e("MainActivity", "setUp()")
        replaceFragment(SplashFragment.newInstance(null))
    }

    fun showToolBar(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.visibility = View.VISIBLE
        toolbar_title.text = title
    }

    fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun hideBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    fun hideToolBar() {
        toolbar.visibility = View.GONE
    }


    fun replaceFragment(fragment: Fragment) {
        Log.e("MainActivity", "replaceFragment()")
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        fragmentTransaction.replace(R.id.container, fragment, fragment.tag)
                .addToBackStack(null).commit()

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)

        when {
            doubleBackToExitPressedOnce -> {
                this.finish()
            }

            fragment is CartFragment || fragment is DetailFragment -> {
                replaceFragment(ProductFragment.newInstance(null))
            }

            else -> {
                this.doubleBackToExitPressedOnce = true
                onError("Please click Back again to exit")
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        }

    }


}

