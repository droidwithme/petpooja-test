package com.petpooja.store.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.petpooja.store.domain.entities.CartProducts
import com.petpooja.store.domain.entities.Product
import com.petpooja.store.domain.usecase.ProductUseCase
import com.petpooja.store.presentation.base.BaseViewModel
import com.petpooja.store.presentation.base.BaseViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CartFragmentViewModel @Inject constructor(private val productUseCase: ProductUseCase) :
        BaseViewModel<BaseViewState>() {

    private var TAG = CartFragmentViewModel::class.java.simpleName



    fun getFromDatabase() {
        Log.e(TAG, "getFromDatabase()")
        mUiState.value = BaseViewState.loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.getFromCart().collect {
                withContext(Dispatchers.Main) { updateUI(it) }
            }
        }
    }


    fun addItem(product: Product) {
        Log.e(TAG, "addItem()")
        mUiState.value = BaseViewState.loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.addProduct(product).collect {
                withContext(Dispatchers.Main) { updateUI(it) }
            }
        }
    }

    fun removeItem(product: Product) {
        Log.e(TAG, "removeItem()")
        mUiState.value = BaseViewState.loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.removeProduct(product).collect {
                withContext(Dispatchers.Main) { updateUI(it) }
            }
        }
    }

    fun deleteItem(product: Product) {
        Log.e(TAG, "deleteItem()")
        mUiState.value = BaseViewState.loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.deleteProduct(product).collect {
                withContext(Dispatchers.Main) { updateUI(it) }
            }
        }
    }


    fun removeAll() {
        mUiState.value = BaseViewState.loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.deleteAll().collect {
                withContext(Dispatchers.Main) { updateUI(it) }
            }
        }
    }

    private fun updateUI(it: String?) {
        Log.e(TAG, "updateUI($it)")
        if (it != null)
            mUiState.postValue(BaseViewState.hasData(it))
        else
            mUiState.postValue(BaseViewState.errorText("not found"))
    }

    private fun updateUI(it: CartProducts?) {
        Log.e(TAG, "updateUI($it)")
        if (it != null)
            mUiState.postValue(BaseViewState.hasData(it))
        else
            mUiState.postValue(BaseViewState.errorText("No data in database"))
    }
}