package com.utair.data.global.network

import com.utair.BuildConfig
import com.utair.data.global.network.responses.WeatherForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val API_KEY = BuildConfig.OPENWEATHER_API_KEY
        const val CELSIUS_METRICS_KEY = "metric"
    }

    @GET("forecast?units=$CELSIUS_METRICS_KEY")
    fun getWeather(@Query("q") cityName: String?): Single<WeatherForecastResponse>

}