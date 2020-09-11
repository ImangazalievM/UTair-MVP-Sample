package com.kode.utair.domain.interactors

import com.kode.utair.domain.models.WeatherSettings
import com.kode.utair.domain.repository.ICityRepository
import com.kode.utair.domain.repository.IWeatherSettingsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(
        private val weatherSettingsRepository: IWeatherSettingsRepository,
        private val weatherSettingsValidator: WeatherSettingsValidator,
        private val cityRepository: ICityRepository
) {

    fun getWeatherSettings(): Single<WeatherSettings> {
        return weatherSettingsRepository.getWeatherSettings()
    }

    fun saveWeatherSettings(weatherSettings: WeatherSettings): Completable {
        return weatherSettingsRepository.saveWeatherSettings(weatherSettings)
    }

    fun getCities(): Single<List<String>> {
        return cityRepository.getCitiesList()
    }

    fun validateData(weatherSettings: WeatherSettings): Completable {
        return weatherSettingsValidator.validate(weatherSettings)
    }

}