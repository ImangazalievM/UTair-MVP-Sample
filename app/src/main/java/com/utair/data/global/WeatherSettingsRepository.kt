package com.utair.data.global

import com.utair.domain.global.models.WeatherSettings
import com.utair.domain.global.repositories.IWeatherSettingsRepository
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