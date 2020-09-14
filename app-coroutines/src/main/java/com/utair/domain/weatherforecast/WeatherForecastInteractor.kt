package com.utair.domain.weatherforecast

import com.utair.data.weather.WeatherRepository
import com.utair.domain.global.models.WeatherForecast
import javax.inject.Inject

class WeatherForecastInteractor @Inject constructor(
        private val weatherRepository: WeatherRepository
) {

    suspend fun getWeatherForecastForCity(cityName: String): WeatherForecast {
        return weatherRepository.getWeatherForecastForCity(cityName)
    }

}