package com.petpooja.store.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.petpooja.store.R
import com.petpooja.store.presentation.base.BaseFragment
import com.petpooja.store.presentation.main.MainActivity
import com.petpooja.store.presentation.ui.products.ProductFragment
import javax.inject.Inject



@Suppress("UNCHECKED_CAST")
private val TAG = SplashFragment::class.java.simpleName

class SplashFragment : BaseFragment<SplashFragmentViewModel>() {

    private val SPLASHDISPLAYLENGTH: Long = 3000


    companion object {
        private const val MY_DATA = "data"
        fun newInstance(strData: String?) = SplashFragment().apply {
            arguments = bundleOf(
                MY_DATA to strData
            )
        }
    }


    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory //root factory that provides viewmodel for a fragment

    override fun getLayoutId(): Int = R.layout.fragment_splash          //assigning splash layout

    override fun getViewModel(): SplashFragmentViewModel =
        ViewModelProviders.of(this, mViewModelFactory).get(SplashFragmentViewModel::class.java)

    override fun getLifeCycleOwner(): LifecycleOwner = this


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logger.printLog(TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()

        hideKeyboard()
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().onBackPressed()
                }
            })
        Handler().postDelayed({
            proceedToProduct(ProductFragment.newInstance(null))
        }, SPLASHDISPLAYLENGTH)
    }

    /*
    * setting up the toolbar/titlebar on top of the screen.
    * */
    private fun setUpToolbar() {
        logger.printLog(TAG, "setUpToolbar()")
        (activity as MainActivity).hideToolBar()
        (activity as MainActivity).hideBackButton()
    }

    private fun proceedToProduct(fragment: Fragment) {
        logger.printLog(TAG, "proceedToProduct")
        (activity as MainActivity).replaceFragment(fragment)
    }


}