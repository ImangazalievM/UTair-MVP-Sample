package com.kode.utair.domain.exceptions;

public class WeatherSettingsValidationError extends Exception {

    private String errorMessage;

    public WeatherSettingsValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
