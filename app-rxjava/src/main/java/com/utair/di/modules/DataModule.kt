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
    fun provideOkHttpClient(networkChecker: NetworkChecker): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(NetworkCheckInterceptor(networkChecker))
                .addInterceptor(WeatherApiInterceptor())
                .build()
    }

    @Provides
    @Singleton
    fun provideCityApi(): UTairApiService {
        return newApiBuilder().createApi(
                apiClass = UTairApiService::class,
                baseUrl = utairApiBaseUrl
        )
    }

    @Provides
    @Singleton
    fun provideWeatherApi(okHttpClient: OkHttpClient): WeatherApiService {
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