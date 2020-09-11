package com.utair.domain.interactors

import com.utair.domain.models.WeatherForecastEntity
import com.utair.domain.models.WeatherSettings
import com.utair.domain.repository.IWeatherRepository
import com.utair.domain.repository.IWeatherSettingsRepository
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