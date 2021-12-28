package com.petpooja.store.presentation.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.petpooja.store.R
import com.petpooja.store.domain.entities.CartProducts
import com.petpooja.store.domain.entities.Product
import com.petpooja.store.presentation.base.BaseFragment
import com.petpooja.store.presentation.base.BaseViewState
import com.petpooja.store.presentation.commons.GridSpacingItemDecoration
import com.petpooja.store.presentation.main.MainActivity
import com.petpooja.store.presentation.ui.detail.DetailFragment
import com.petpooja.store.presentation.ui.products.ProductFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class CartFragment : BaseFragment<CartFragmentViewModel>(),
        CartAdapter.ProductCartCallbacks {

    companion object {
        val TAG = CartFragment::class.java.simpleName
        private const val MY_DATA = "data"
        fun newInstance(strData: String?) = CartFragment().apply {
            arguments = bundleOf(
                    MY_DATA to strData
            )
        }
    }


    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory


    @Inject
    lateinit var mGridSpacingItemDecoration: GridSpacingItemDecoration

    @Inject
    lateinit var cartAdapter: CartAdapter

    @Inject
    lateinit var gson: Gson


    override fun getLayoutId(): Int = R.layout.fragment_cart
    override fun getViewModel(): CartFragmentViewModel =
            ViewModelProviders.of(this, mViewModelFactory)
                    .get(CartFragmentViewModel::class.java)

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated()")
        setUp()
        setUpUserRecyclerView()
        observeViewState()
        getViewModel().getFromDatabase()

        buttonCheckOut.setOnClickListener {
            getViewModel().removeAll()
        }

    }

    private fun setUp() {
        Log.e(TAG, "setUp()")
        (activity as MainActivity).showToolBar("Cart")
    }

    private fun setUpUserRecyclerView() {

        cartRecyclerView.layoutManager = LinearLayoutManager(context)
        cartRecyclerView.addItemDecoration(mGridSpacingItemDecoration)
        cartRecyclerView.itemAnimator = null
        cartRecyclerView.adapter = cartAdapter
        cartAdapter.setListener(this)
    }


    private fun observeViewState() {
        Log.e(TAG, "observeViewState()")
        getViewModel().uiState.observe(this, Observer {
            hideLoading()
            when (it) {
                is BaseViewState.messageText -> {
                    showMessage(it.text)
                }

                is BaseViewState.loading -> {
                    showLoading()
                }

                is BaseViewState.errorText -> {
                    onError(it.text)
                }

                is BaseViewState.hasData<*> -> {
                    if (it.data is CartProducts) {
                        logger.printLog(ProductFragment.TAG, "data from database $${it.data}")
                        if (it.data.list.size > 0) {
                            cartAdapter.addItems(it.data.list)
                            var totalItem = 0
                            var totalPrice = 0.0
                            it.data.list.forEach { it ->
                                totalItem += it.count
                                totalPrice += it.count * it.price
                            }
                            textViewTotalPrice.text =  "${Math.round(totalPrice * 100.0) / 100.0}"
                            textViewItem.text = "${totalItem}"
                        } else {
                            cartAdapter.addItems(ArrayList<Product>())
                            cartContainer.visibility = View.GONE
                            onError("Empty cart")
                        }
                    }
                }
            }
        })
    }


    override fun onClickItem(product: Product) {
        logger.printLog(TAG, "onClickItem($product)")
        val strData = gson.toJson(product)
        proceedToProductDetails(strData)
    }

    override fun onItemAction(totalCount: Int) {

    }

    override fun addItem(product: Product) {
        getViewModel().addItem(product)
        getViewModel().getFromDatabase()
    }

    override fun removeItem(product: Product) {
        getViewModel().removeItem(product)
        getViewModel().getFromDatabase()
    }

    override fun onItemDelete(product: Product) {
        getViewModel().deleteItem(product)
        getViewModel().getFromDatabase()
    }

    private fun proceedToProductDetails(strProduct: String) {
        logger.printLog(TAG, "proceedToProductDetails")
        (activity as MainActivity).replaceFragment(DetailFragment.newInstance(strProduct))
    }
}