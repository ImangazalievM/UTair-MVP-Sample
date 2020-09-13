package com.utair.domain.weatherforecast

import com.utair.data.weather.WeatherRepository
import com.utair.domain.global.models.WeatherForecast
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastInteractor @Inject constructor(
        private val weatherRepository: WeatherRepository
) {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecast> {
        return weatherRepository.getWeatherForecastForCity(cityName)
    }

}