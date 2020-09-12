package com.utair.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.utair.data.cities.CityRepository
import com.utair.data.global.network.*
import com.utair.data.global.network.responses.WeatherForecastResponse
import com.utair.data.weather.WeatherRepository
import com.utair.domain.global.repositories.IWeatherRepository
import com.utair.domain.global.repositories.ICityRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class DataModule(
        private val utairApiBaseUrl: String,
        private val weatherApiBaseUrl: String
) {
    @Provides
    @Singleton
    fun provideCityRepository(repository: CityRepository): ICityRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(repository: WeatherRepository): IWeatherRepository {
        return repository
    }

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
        return createApi(
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
        return createApi(
                apiClass = WeatherApiService::class,
                baseUrl = weatherApiBaseUrl,
                okHttpClient = okHttpClient,
                gson = gson
        )
    }

    private fun <T : Any> createApi(
            apiClass: KClass<T>,
            baseUrl: String,
            okHttpClient: OkHttpClient? = null,
            gson: Gson = GsonBuilder().create()
    ): T {
        val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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