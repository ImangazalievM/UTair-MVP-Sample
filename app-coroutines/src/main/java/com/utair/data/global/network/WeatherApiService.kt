package com.utair.data.global.network

import com.utair.data.global.network.responses.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast?units=${ApiConstants.CELSIUS_METRICS_KEY}")
    suspend fun getWeather(@Query("q") cityName: String?): WeatherForecastResponse

}