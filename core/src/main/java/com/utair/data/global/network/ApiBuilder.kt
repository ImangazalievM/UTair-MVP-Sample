package com.utair.data.global.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class ApiBuilder {

    private var okHttpClient: OkHttpClient? = null
    private var gson: Gson = GsonBuilder().create()
    private lateinit var callAdapter: CallAdapter.Factory

    fun okHttpClient(okHttpClient: OkHttpClient): ApiBuilder{
        this.okHttpClient = okHttpClient
        return this
    }

    fun gson(gson: Gson): ApiBuilder{
        this.gson = gson
        return this
    }

    fun callAdapter(callAdapter: CallAdapter.Factory): ApiBuilder{
        this.callAdapter = callAdapter
        return this
    }

    fun <T : Any> createApi(
            apiClass: KClass<T>,
            baseUrl: String
    ): T {
        val builder = Retrofit.Builder()
                .addCallAdapterFactory(callAdapter)
                .baseUrl(baseUrl)
        okHttpClient?.let {
            builder.client(it)
        }
        gson.let {
            builder.addConverterFactory(GsonConverterFactory.create(gson))
        }

        return builder.build()
                .create(apiClass.java)
    }

}