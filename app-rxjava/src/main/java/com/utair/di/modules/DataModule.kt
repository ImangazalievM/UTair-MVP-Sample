package com.utair.di.modules

import com.google.gson.GsonBuilder
import com.utair.data.global.network.*
import com.utair.data.global.network.responses.WeatherForecastResponse
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
class DataModule(
        private val utairApiBaseUrl: String,
        private val weatherApiBaseUrl: String
) {

    @Provides
    @Singleton
    fun provideUTairApi(networkCheckInterceptor: NetworkCheckInterceptor): UTairApiService {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkCheckInterceptor)
                .addInterceptor(WeatherApiInterceptor())
                .build()

        return newApiBuilder()
                .okHttpClient(okHttpClient)
                .createApi(
                        apiClass = UTairApiService::class,
                        baseUrl = utairApiBaseUrl
                )
    }

    @Provides
    @Singleton
    fun provideWeatherApi(networkCheckInterceptor: NetworkCheckInterceptor): WeatherApiService {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkCheckInterceptor)
                .addInterceptor(WeatherApiInterceptor())
                .build()

        val gson = GsonBuilder()
                .registerTypeAdapter(WeatherForecastResponse::class.java, WeatherForecastDeserializer())
                .create()
        return newApiBuilder()
                .okHttpClient(okHttpClient)
                .gson(gson)
                .createApi(
                        apiClass = WeatherApiService::class,
                        baseUrl = weatherApiBaseUrl
                )
    }

    private fun newApiBuilder() = ApiBuilder()
            .callAdapter(RxJava2CallAdapterFactory.create())

}