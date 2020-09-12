package com.utair.domain.weatherforecast

import com.utair.data.global.UTairPreferences
import com.utair.domain.global.models.WeatherForecastEntity
import com.utair.domain.global.models.WeatherSettings
import com.utair.domain.global.repositories.IWeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherForecastInteractor @Inject constructor(
        private val utairPreferences: UTairPreferences,
        private val weatherRepository: IWeatherRepository
) {

    fun getWeatherSettings(): WeatherSettings {
        return utairPreferences.getWeatherSettings()
    }

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastEntity> {
        return weatherRepository.getWeatherForecastForCity(cityName)
    }

}