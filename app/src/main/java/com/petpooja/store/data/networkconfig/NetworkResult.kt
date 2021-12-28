package com.petpooja.store.data.networkconfig

sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val error: Exception) : NetworkResult<Nothing>()
    data class NoConnection(val exception: NoConnectivityException) : NetworkResult<Nothing>()
    data class NotFoundException(val exception: String) : NetworkResult<Nothing>()
    data class ServiceNotAvailException(val exception: String) : NetworkResult<Nothing>()
    data class ServerBroken(val exception: String) : NetworkResult<Nothing>()
}