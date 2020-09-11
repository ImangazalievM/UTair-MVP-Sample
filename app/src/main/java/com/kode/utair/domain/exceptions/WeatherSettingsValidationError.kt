package com.kode.utair.domain.exceptions

class WeatherSettingsValidationError(
        val errorMessage: String
) : Exception()