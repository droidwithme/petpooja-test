package com.petpooja.store.data.repository

import com.petpooja.store.data.networkconfig.NetworkResult
import com.petpooja.store.data.networkconfig.NoConnectivityException
import android.util.Log
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException



open class BaseRepoImp {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorContext: String): Any? {

        val result: NetworkResult<T> = safeApiResult(call)
        var data: Any? = null

        data = when (result) {
            is NetworkResult.Success ->
                result.data
            is NetworkResult.Error ->
                "Ops! Its the new error."
            is NetworkResult.NoConnection ->
                "No network connection!"
            is NetworkResult.NotFoundException ->
                "The accessed url is not available anymore."
            is NetworkResult.ServerBroken ->
                "Server is broken just before you hit! We are fixing it."
            is NetworkResult.ServiceNotAvailException ->
                "Server is busy to serve you batter, Please try again."
        }
        return data
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): NetworkResult<T> {
        val result: NetworkResult<T>
        try {
            Log.e("BaseRepoImp", "safeApiResult()")

            val response = call.invoke()
            Log.e("response", "safeApiResult($response)")
            if (response.isSuccessful) {
                return NetworkResult.Success(response.body()!!)
            } else {
                when {
                    response.code() == 404 -> {
                        return NetworkResult.NotFoundException("The accessed url is not available anymore")
                    }
                    response.code() == 500 -> {
                        return NetworkResult.ServerBroken("Server is broken just before you hit! We are on fixing it")
                    }
                    response.code() == 503 -> {
                        return NetworkResult.ServiceNotAvailException("Server is busy to serve you batter, Please try again")
                    }
                }
                return NetworkResult.Error(java.lang.Exception("Oppps... I was not expecting this from server"))
            }

        } catch (exception: IOException) {
            Log.e("exception", "safeApiResult($exception)")
            if (exception is NoConnectivityException) {
                return NetworkResult.NoConnection(exception)
            }  else {

                return NetworkResult.Error(exception)
                /*if (exception.toString().startsWith("java.io.EOFException:")) {
                    Log.e("EOFException", "safeApiResult($exception)")
                    val response = call.invoke()
                    Log.e("calling back to api", "${call.toString()}")
                    return NetworkResult.Success(response.body()!!)
                } else {
                    return NetworkResult.Error(exception)
                }*/
            }
        }

    }

    private fun checkException(e: IOException) {

        Log.e("BaseRepoImpl", "checkException($e)")
    }

    private fun <T : Any> setErrorMessage(response: Response<T>): String {
        Log.e("BaseRepoImp", "setErrorMessage($response)")
        val code = response.code().toString()
        val message = try {
            val jObjError = JSONObject(response.errorBody()?.string())
            jObjError.getJSONObject("error").getString("message")

        } catch (e: Exception) {
            e.message
        }
        return if (message.isNullOrEmpty()) " error code = $code " else " error code = $code  & error message = $message "
    }

}