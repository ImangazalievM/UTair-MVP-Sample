package com.kode.utair.domain.interactors

import android.util.Log
import com.kode.utair.domain.models.WeatherForecastEntity
import com.kode.utair.domain.models.WeatherSettings
import com.kode.utair.domain.repository.IWeatherRepository
import com.kode.utair.domain.repository.IWeatherSettingsRepository
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