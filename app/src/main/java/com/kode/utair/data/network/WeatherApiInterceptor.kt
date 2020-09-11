package com.kode.utair.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class WeatherApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", WeatherApiService.API_KEY)
                .build()
        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}