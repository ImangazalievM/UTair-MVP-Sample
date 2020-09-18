package com.utair.domain.global.exceptions

class PassengersDataValidationError(
        val errorMessage: String
) : Exception(errorMessage)