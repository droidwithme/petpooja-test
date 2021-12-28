

package com.petpooja.store.presentation.commons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/*
* Factory pattern to provide ViewModel
* */
class ViewModelProviderFactory<V : Any>(private var viewModel: V) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel.javaClass)) {
            return  viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}