package com.utair.domain.flightorder

import com.utair.data.global.UTairPreferences
import com.utair.domain.global.models.WeatherSettings
import com.utair.domain.global.repositories.ICityRepository
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(
        private val prefs: UTairPreferences,
        private val weatherSettingsValidator: WeatherSettingsValidator,
        private val cityRepository: ICityRepository
) {

    fun getWeatherSettings(): WeatherSettings {
        return prefs.getWeatherSettings()
    }

    fun saveWeatherSettings(weatherSettings: WeatherSettings) {
        prefs.saveWeatherSettings(weatherSettings)
    }

    fun getCities(): Single<List<String>> {
        return cityRepository.getCitiesList()
    }

    fun validateData(weatherSettings: WeatherSettings) {
        return weatherSettingsValidator.validate(weatherSettings)
    }

}