package com.utair.domain.global.repositories

import com.utair.domain.global.models.WeatherSettings
import io.reactivex.Completable
import io.reactivex.Single

interface IWeatherSettingsRepository {

    fun getWeatherSettings(): Single<WeatherSettings>

    fun saveWeatherSettings(weatherSettings: WeatherSettings): Completable

}