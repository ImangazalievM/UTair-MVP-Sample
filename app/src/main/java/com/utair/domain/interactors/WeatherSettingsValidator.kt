package com.utair.domain.interactors

import com.utair.R
import com.utair.di.qualifiers.JobScheduler
import com.utair.di.qualifiers.UiScheduler
import com.utair.domain.commons.ResourceManager
import com.utair.domain.exceptions.WeatherSettingsValidationError
import com.utair.domain.models.WeatherSettings
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Scheduler
import java.util.*
import javax.inject.Inject

class WeatherSettingsValidator @Inject constructor(
        @JobScheduler jobScheduler: Scheduler?,
        @UiScheduler uiScheduler: Scheduler?,
        private val resourceManager: ResourceManager
) {

    fun validate(weatherSettings: WeatherSettings): Completable {
        return Completable.create { e: CompletableEmitter ->
            val validateErrors = validateWeatherSettings(weatherSettings)
            if (validateErrors.isNotEmpty()) {
                //отправляем на обработку одну ошибку
                e.onError(validateErrors[0])
            } else {
                e.onComplete()
            }
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