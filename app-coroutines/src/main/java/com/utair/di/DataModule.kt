package com.utair.di

import com.google.gson.GsonBuilder
import com.utair.data.global.UTairPreferences
import com.utair.data.global.network.*
import com.utair.data.global.network.mappers.WeatherForecastResponseMapper
import com.utair.data.global.network.responses.WeatherForecastResponse
import okhttp3.OkHttpClient
import toothpick.config.Module

class DataModule(
        private val utairApiBaseUrl: String,
        private val weatherApiBaseUrl: String
) : Module() {

    init {
        bind(UTairPreferences::class)
                .toProviderInstance { UTairPreferences(getGlobal()) }

        bind(NetworkCheckInterceptor::class)
                .toProviderInstance { NetworkCheckInterceptor(getGlobal()) }

        bind(NetworkChecker::class)
                .toProviderInstance { NetworkChecker(getGlobal()) }

        bind(WeatherForecastResponseMapper::class)
                .toProviderInstance { WeatherForecastResponseMapper() }

        bind(UTairApiService::class)
                .toProviderInstance { createUTairApi(getGlobal()) }

        bind(WeatherApiService::class)
                .toProviderInstance { createWeatherApi(getGlobal()) }
    }

    private fun createUTairApi(
            networkCheckInterceptor: NetworkCheckInterceptor
    ): UTairApiService {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkCheckInterceptor)
                .addInterceptor(WeatherApiInterceptor())
                .build()

        return ApiBuilder()
                .okHttpClient(okHttpClient)
                .createApi(
                        apiClass = UTairApiService::class,
                        baseUrl = utairApiBaseUrl
                )
    }

    private fun createWeatherApi(
            networkCheckInterceptor: NetworkCheckInterceptor
    ): WeatherApiService {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkCheckInterceptor)
                .addInterceptor(WeatherApiInterceptor())
                .build()

        val gson = GsonBuilder()
                .registerTypeAdapter(WeatherForecastResponse::class.java, WeatherForecastDeserializer())
                .create()
        return ApiBuilder()
                .okHttpClient(okHttpClient)
                .gson(gson)
                .createApi(
                        apiClass = WeatherApiService::class,
                        baseUrl = weatherApiBaseUrl
                )
    }

}