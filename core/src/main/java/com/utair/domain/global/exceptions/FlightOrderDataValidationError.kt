package com.utair.domain.global.exceptions

class FlightOrderDataValidationError(
        val errorMessage: String
) : Exception(errorMessage)