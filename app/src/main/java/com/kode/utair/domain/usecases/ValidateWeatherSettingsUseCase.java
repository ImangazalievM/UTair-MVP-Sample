package com.kode.utair.domain.usecases;

import com.kode.utair.di.qualifiers.JobScheduler;
import com.kode.utair.di.qualifiers.UiScheduler;
import com.kode.utair.domain.commons.ResourceManager;
import com.kode.utair.domain.exceptions.WeatherSettingsValidationError;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.domain.usecases.base.CompletableUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class ValidateWeatherSettingsUseCase extends CompletableUseCase<WeatherSettings> {

    private ResourceManager resourceManager;

    @Inject
    public ValidateWeatherSettingsUseCase(@JobScheduler Scheduler jobScheduler,
                                          @UiScheduler Scheduler uiScheduler,
                                          ResourceManager resourceManager) {
        super(jobScheduler, uiScheduler);

        this.resourceManager = resourceManager;
    }

    @Override
    protected Completable buildCompletable(WeatherSettings weatherSettings) {
        return  Completable.create(e -> {
            List<WeatherSettingsValidationError> validateErrors = validateWeatherSettings(weatherSettings);
            if (!validateErrors.isEmpty()) {
                //отправляем на обработку одну ошибку
                e.onError(validateErrors.get(0));
            } else {
                e.onComplete();
            }
        });
    }

    private List<WeatherSettingsValidationError> validateWeatherSettings(WeatherSettings weatherSettings) {
        List<WeatherSettingsValidationError> validateErrors = new ArrayList<>();

        WeatherSettingsValidationError departCityValidateError = validateDepartCity(weatherSettings.getDepartCity());
        if (departCityValidateError != null) validateErrors.add(departCityValidateError);

        WeatherSettingsValidationError arriveCityValidateError = validateArriveCity(weatherSettings.getArriveCity());
        if (arriveCityValidateError != null) validateErrors.add(arriveCityValidateError);

        return validateErrors;
    }

    private WeatherSettingsValidationError validateDepartCity(String departCity) {
        if (departCity == null) {
            return new WeatherSettingsValidationError(resourceManager.getString("depart_city_is_empty_message"));
        }
        return null;
    }

    private WeatherSettingsValidationError validateArriveCity(String arriveCity) {
        if (arriveCity == null) {
            return new WeatherSettingsValidationError(resourceManager.getString("arrive_city_is_empty_message"));
        }
        return null;
    }

}