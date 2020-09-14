package com.utair.data.global.network

import com.utair.data.global.network.responses.WeatherForecastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast?units=${ApiConstants.CELSIUS_METRICS_KEY}")
    fun getWeather(@Query("q") cityName: String?): Single<WeatherForecastResponse>

}