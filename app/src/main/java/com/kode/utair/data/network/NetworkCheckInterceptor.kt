package com.kode.utair.data.network

import com.kode.utair.domain.exceptions.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkCheckInterceptor(private val networkChecker: NetworkChecker) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (!networkChecker.isConnected) {
            throw NoNetworkException("No network connection")
        }
        return chain.proceed(requestBuilder.build())
    }

}