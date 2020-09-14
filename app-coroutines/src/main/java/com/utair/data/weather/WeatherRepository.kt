package com.utair.data.weather

import com.utair.data.global.network.WeatherApiService
import com.utair.data.global.network.mappers.WeatherForecastResponseMapper
import com.utair.domain.global.models.WeatherForecast
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        private val weatherApiService: WeatherApiService,
        private val weatherForecastResponseMapper: WeatherForecastResponseMapper
) {

    suspend fun getWeatherForecastForCity(
            cityName: String
    ): WeatherForecast {
        val forecastResponse = weatherApiService.getWeather(cityName)
        return weatherForecastResponseMapper.map(forecastResponse)
    }

}