package com.petpooja.store.data.networkconfig

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkConnectionInterceptor @Inject constructor(private val mContext: Context) : Interceptor {

    companion object{
        private val TAG  = NetworkConnectionInterceptor::class.java.simpleName
    }

    private val isConnected: Boolean
        get() {
            Log.e(TAG, "get()")
            val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(TAG, "intercept()")
        if (!isConnected) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val response = chain.proceed(chain.request())
        val maxAge = 3000 // read from cache for 60 seconds even if there is internet connection

        return response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
    }

}