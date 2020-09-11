package com.utair.domain.exceptions

class WeatherSettingsValidationError(
        val errorMessage: String
) : Exception()