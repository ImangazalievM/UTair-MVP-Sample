package com.kode.utair.data.repository

import com.kode.utair.data.repository.datasource.WeatherPreferences
import com.kode.utair.domain.models.WeatherSettings
import com.kode.utair.domain.repository.IWeatherSettingsRepository
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class WeatherSettingsRepository @Inject constructor(
        private val weatherPreferences: WeatherPreferences
) : IWeatherSettingsRepository {

    override fun getWeatherSettings(): Single<WeatherSettings> {
        return Single.create { e: SingleEmitter<WeatherSettings> ->
            e.onSuccess(weatherPreferences.getWeatherSettings())
        }
    }

    override fun saveWeatherSettings(weatherSettings: WeatherSettings): Completable {
        return Completable.create { e: CompletableEmitter ->
            weatherPreferences.saveWeatherSettings(weatherSettings)
            e.onComplete()
        }
    }

}