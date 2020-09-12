package com.utair.domain.weatherforecast

import com.utair.domain.global.models.WeatherForecast
import com.utair.domain.global.repositories.IWeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastInteractor @Inject constructor(
        private val weatherRepository: IWeatherRepository
) {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecast> {
        return weatherRepository.getWeatherForecastForCity(cityName)
    }

}