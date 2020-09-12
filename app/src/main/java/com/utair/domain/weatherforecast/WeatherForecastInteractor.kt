package com.utair.domain.weatherforecast

import com.utair.domain.global.models.WeatherForecastEntity
import com.utair.domain.global.models.WeatherSettings
import com.utair.domain.global.IWeatherRepository
import com.utair.domain.global.repositories.IWeatherSettingsRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastInteractor @Inject constructor(
        private val weatherSettingsRepository: IWeatherSettingsRepository,
        private val weatherRepository: IWeatherRepository
) {

    fun getWeatherSettings(): Single<WeatherSettings> {
        return weatherSettingsRepository.getWeatherSettings()
    }

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastEntity> {
        return weatherRepository.getWeatherForecastForCity(cityName)
    }

}