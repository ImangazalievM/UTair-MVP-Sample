package com.utair.domain.flightorder

import com.utair.core.R
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.exceptions.FlightOrderDataValidationError
import com.utair.domain.global.models.FlightOrderData
import javax.inject.Inject

class FlightOrderDataValidator @Inject constructor(
        private val resourceManager: ResourceManager
) {

    fun validate(flightOrderData: FlightOrderData) {
        if (flightOrderData.departCity == null) {
            throw validationError(R.string.depart_city_is_empty_message)
        }

        if (flightOrderData.arriveCity == null) {
            throw validationError(R.string.arrive_city_is_empty_message)
        }
    }

    private fun validationError(stringResId: Int): FlightOrderDataValidationError {
        return FlightOrderDataValidationError(resourceManager.getString(stringResId))
    }

}