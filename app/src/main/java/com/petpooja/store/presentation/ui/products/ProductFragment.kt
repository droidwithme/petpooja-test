package com.petpooja.store.presentation.ui.products

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_product.*
import javax.inject.Inject
import android.view.*
import androidx.core.content.ContextCompat

import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher

import android.view.Gravity
import com.petpooja.store.domain.entities.Categories

import com.skydoves.powermenu.MenuAnimation

import com.skydoves.powermenu.PowerMenuItem

import com.skydoves.powermenu.PowerMenu
import android.widget.Toast
import com.skydoves.powermenu.OnMenuItemClickListener
import android.view.inputmethod.EditorInfo
import android.widget.EditText

import com.petpooja.store.presentation.ui.cart.CartFragment


@Suppress("UNCHECKED_CAST")
class ProductFragment : BaseFragment<ProductFragmentViewModel>(),
        ProductAdapter.ProductCallbacks {

    companion object {
        val TAG = ProductFragment::class.java.simpleName
        private const val MY_DATA = "data"
        fun newInstance(strData: String?) = ProductFragment().apply {
            arguments = bundleOf(
                    MY_DATA to strData
            )
        }
    }

    lateinit var powerMenu: PowerMenu

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory


    @Inject
    lateinit var mGridSpacingItemDecoration: GridSpacingItemDecoration

    @Inject
    lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var gson: Gson


    override fun getLayoutId(): Int = R.layout.fragment_product
    override fun getViewModel(): ProductFragmentViewModel =
            ViewModelProviders.of(this, mViewModelFactory)
                    .get(ProductFragmentViewModel::class.java)

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated()")
        setUp()
        setUpUserRecyclerView()
        observeViewState()
        getViewModel().getCategory()
        getViewModel().getProducts(null)
        getViewModel().getFromDatabase()

        imageViewCategory.setOnClickListener {
            powerMenu.showAsDropDown(it);
        }

        editTextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = editTextSearch.text.toString().trim()
                getViewModel().getProducts(searchTerm)
            }
            false
        }

        editTextSearch.afterTextChanged {
            if (it == "") {
                getViewModel().getProducts(null)
            }
        }

        fab.setOnClickListener {
            proceedToCart()
        }


    }

    private fun proceedToCart() {
        logger.printLog(TAG, "proceedToCart")
        (activity as MainActivity).replaceFragment(CartFragment.newInstance(null))
    }

    private fun setUp() {
        Log.e(TAG, "setUp()")
        (activity as MainActivity).showToolBar("Products")
        (activity as MainActivity).hideBackButton()
    }

    private fun setUpUserRecyclerView() {

        productRecyclerView.layoutManager = LinearLayoutManager(context)
        productRecyclerView.addItemDecoration(mGridSpacingItemDecoration)
        productRecyclerView.itemAnimator = DefaultItemAnimator()
        productRecyclerView.adapter = productAdapter
        productAdapter.setListener(this)
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
                    if (it.data is ArrayList<*>) {
                        if (it.data.size > 0) {
                            productAdapter.addItems(it.data as ArrayList<Product>)
                        } else {
                            onError("Product not found")
                        }
                    } else if (it.data is String) {
                        onSuccess(it.data)
                    } else if (it.data is CartProducts) {
                        logger.printLog(TAG, "data from database $${it.data}")
                        if (!it.data.list.isNullOrEmpty()) {
                            setAlphaAnimation(fab)
                            count.text = "${it.data.list.size}"
                        }
                    } else if (it.data is Categories) {
                        configCategory(it.data.categoryList.map {
                            PowerMenuItem(it)
                        } as ArrayList<PowerMenuItem>)
                    }
                }
            }
        })
    }

    private fun configCategory(list: ArrayList<PowerMenuItem>) {
        powerMenu = PowerMenu.Builder(context!!)
                .addItemList(list)
                .addItem(PowerMenuItem("Clear selection"))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setTextColor(ContextCompat.getColor(context!!, R.color.light_gray))
                .setTextGravity(Gravity.CENTER)
                .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build()
    }

    private val onMenuItemClickListener: OnMenuItemClickListener<PowerMenuItem> = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        powerMenu.selectedPosition = position // change selected item
        if (item.title == "Clear selection") {
            getViewModel().getProducts(null)
        } else {
            getViewModel().getProducts(item.title.toString())
        }
        powerMenu.dismiss()
    }

    override fun onClickItem(product: Product) {
        logger.printLog(TAG, "onClickItem($product)")
        val strData = gson.toJson(product)
        proceedToProductDetails(strData)
    }

    override fun onItemAction(totalCount: Int) {
        count.text = "$totalCount"
    }

    override fun addItem(product: Product) {
        getViewModel().addItem(product)
    }

    override fun removeItem(product: Product) {
        getViewModel().removeItem(product)
    }

    private fun proceedToProductDetails(strProduct: String) {
        logger.printLog(TAG, "proceedToProductDetails")
        (activity as MainActivity).replaceFragment(DetailFragment.newInstance(strProduct))
    }

    fun setAlphaAnimation(v: View?) {
        val fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, .3f)
        fadeOut.duration = 1000
        val fadeIn = ObjectAnimator.ofFloat(v, "alpha", .3f, 1f)
        fadeIn.duration = 1000
        val mAnimationSet = AnimatorSet()
        mAnimationSet.play(fadeIn).after(fadeOut)
        mAnimationSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mAnimationSet.start()
            }
        })
        mAnimationSet.start()
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}