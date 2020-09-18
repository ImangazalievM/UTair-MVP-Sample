package com.utair.domain.flightorder

import com.utair.Constants
import com.utair.core.R
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.exceptions.PassengersDataValidationError
import com.utair.presentation.mvp.flightorder.PassengersData
import javax.inject.Inject

class PassengersDataValidator @Inject constructor(
        private val resourceManager: ResourceManager
) {

    fun validate(passengersData: PassengersData) {
        val validateErrors = validatePassengersData(passengersData)
        // send one error for processing
        validateErrors.firstOrNull()?.let {
            throw it
        }
    }

    private fun validatePassengersData(
            passengersData: PassengersData
    ): List<PassengersDataValidationError> {
        val validateErrors = mutableListOf<PassengersDataValidationError>()

        val summaryCount = passengersData.run {
            adultCount + kidCount + babyCount
        }
        val isMaxValueReached = summaryCount > Constants.SUMMARY_MAX_PASSENGERS_COUNT
        if (isMaxValueReached) {
            validateErrors.add(validationError(
                    R.string.max_passenger_count_message,
                    Constants.SUMMARY_MAX_PASSENGERS_COUNT
            ))
        }

        if (passengersData.babyCount > passengersData.adultCount) {
            validateErrors.add(validationError(R.string.babies_less_adults_message))
        }

        return validateErrors
    }

    private fun validationError(
            stringResId: Int,
            vararg args: Any
    ): PassengersDataValidationError {
        val message = resourceManager.getString(stringResId, *args)
        return PassengersDataValidationError(message)
    }

}