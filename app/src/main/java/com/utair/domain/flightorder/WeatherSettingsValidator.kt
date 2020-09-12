package com.utair.domain.flightorder

import com.utair.R
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.exceptions.WeatherSettingsValidationError
import com.utair.domain.global.models.WeatherSettings
import java.util.*
import javax.inject.Inject

class WeatherSettingsValidator @Inject constructor(
        private val resourceManager: ResourceManager
) {

    fun validate(weatherSettings: WeatherSettings) {
        val validateErrors = validateWeatherSettings(weatherSettings)
        //отправляем на обработку одну ошибку
        validateErrors.firstOrNull()?.let {
            throw it
        }
    }

    private fun validateWeatherSettings(weatherSettings: WeatherSettings): List<WeatherSettingsValidationError> {
        val validateErrors: MutableList<WeatherSettingsValidationError> = ArrayList()
        val departCityValidateError = validateDepartCity(weatherSettings.departCity)
        if (departCityValidateError != null) validateErrors.add(departCityValidateError)
        val arriveCityValidateError = validateArriveCity(weatherSettings.arriveCity)
        if (arriveCityValidateError != null) validateErrors.add(arriveCityValidateError)
        return validateErrors
    }

    private fun validateDepartCity(departCity: String?): WeatherSettingsValidationError? {
        return if (departCity == null) {
            WeatherSettingsValidationError(resourceManager.getString(R.string.depart_city_is_empty_message))
        } else null
    }

    private fun validateArriveCity(arriveCity: String?): WeatherSettingsValidationError? {
        return if (arriveCity == null) {
            WeatherSettingsValidationError(resourceManager.getString(R.string.arrive_city_is_empty_message))
        } else null
    }

}