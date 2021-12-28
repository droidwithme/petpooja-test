package com.petpooja.store.presentation.main

import com.petpooja.store.data.repository.DomainRepoImp
import com.petpooja.store.presentation.base.BaseViewModel
import com.petpooja.store.presentation.base.BaseViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(movieRepository: DomainRepoImp) : BaseViewModel<BaseViewState>() {


}