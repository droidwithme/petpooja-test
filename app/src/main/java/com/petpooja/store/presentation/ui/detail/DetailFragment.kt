package com.petpooja.store.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.petpooja.store.R
import com.petpooja.store.domain.entities.Product
import com.petpooja.store.presentation.base.BaseFragment
import com.petpooja.store.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class DetailFragment : BaseFragment<DetailFragmentViewModel>() {

    companion object {
        val TAG = DetailFragment::class.java.simpleName
        private const val MY_DATA = "data"
        fun newInstance(strData: String?) = DetailFragment().apply {
            arguments = bundleOf(
                    MY_DATA to strData
            )
        }
    }

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun getViewModel(): DetailFragmentViewModel =
            ViewModelProviders.of(this, mViewModelFactory)
                    .get(DetailFragmentViewModel::class.java)

    override fun getLifeCycleOwner(): LifecycleOwner = this


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated()")
        val data = arguments!!["data"].toString()

        setUp()
        createDetailView(data)
    }

    private fun setUp() {
        Log.e(TAG, "setUp()")
        (activity as MainActivity).showToolBar("Product Details")
    }

    private fun createDetailView(data: String) {
        Log.e(TAG, "createDetailView($data)")
        val product = Gson().fromJson<Product>(data, Product::class.java)
        textViewProductDetailsPrice.text = resources.getString(R.string.postfix_price, "${product.price}")
        textViewProductDetailDescription.text = product.description
        textViewProductDetailsCategory.text = product.category
        textViewProductDetailTitle.text = product.title
        Glide.with(requireContext())
                .load(product.image)
                .into(productImage)
    }
}