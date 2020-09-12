package com.utair.domain.flightorder

import com.utair.R
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.exceptions.FlightOrderDataValidationError
import com.utair.domain.global.models.FlightOrderData
import java.util.*
import javax.inject.Inject

class FlightOrderDataValidator @Inject constructor(
        private val resourceManager: ResourceManager
) {

    fun validate(flightOrderData: FlightOrderData) {
        val validateErrors = validateFlightOrderData(flightOrderData)
        // send one error for processing
        validateErrors.firstOrNull()?.let {
            throw it
        }
    }

    private fun validateFlightOrderData(flightOrderData: FlightOrderData): List<FlightOrderDataValidationError> {
        val validateErrors: MutableList<FlightOrderDataValidationError> = ArrayList()
        val departCityValidateError = validateDepartCity(flightOrderData.departCity)
        if (departCityValidateError != null) validateErrors.add(departCityValidateError)
        val arriveCityValidateError = validateArriveCity(flightOrderData.arriveCity)
        if (arriveCityValidateError != null) validateErrors.add(arriveCityValidateError)
        return validateErrors
    }

    private fun validateDepartCity(departCity: String?): FlightOrderDataValidationError? {
        return if (departCity == null) {
            FlightOrderDataValidationError(resourceManager.getString(R.string.depart_city_is_empty_message))
        } else null
    }

    private fun validateArriveCity(arriveCity: String?): FlightOrderDataValidationError? {
        return if (arriveCity == null) {
            FlightOrderDataValidationError(resourceManager.getString(R.string.arrive_city_is_empty_message))
        } else null
    }

}