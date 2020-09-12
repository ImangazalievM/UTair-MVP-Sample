package com.utair.domain.weatherforecast

import com.utair.domain.global.models.WeatherForecastEntity
import com.utair.domain.global.repositories.IWeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastInteractor @Inject constructor(
        private val weatherRepository: IWeatherRepository
) {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastEntity> {
        return weatherRepository.getWeatherForecastForCity(cityName)
    }

}