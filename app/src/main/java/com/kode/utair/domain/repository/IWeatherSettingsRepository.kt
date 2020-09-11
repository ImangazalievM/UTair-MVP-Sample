package com.kode.utair.domain.repository

import com.kode.utair.domain.models.WeatherSettings
import io.reactivex.Completable
import io.reactivex.Single

interface IWeatherSettingsRepository {

    fun getWeatherSettings(): Single<WeatherSettings>

    fun saveWeatherSettings(weatherSettings: WeatherSettings): Completable

}