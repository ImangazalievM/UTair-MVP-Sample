package com.kode.utair.domain.interactors;

import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.domain.usecases.GetCitiesUseCase;
import com.kode.utair.domain.usecases.GetWeatherSettingsUseCase;
import com.kode.utair.domain.usecases.SaveWeatherSettingsUseCase;
import com.kode.utair.domain.usecases.ValidateWeatherSettingsUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MainInteractor {

    private GetWeatherSettingsUseCase getWeatherForecastSettings;
    private SaveWeatherSettingsUseCase saveWeatherSettingsUseCase;
    private GetCitiesUseCase getCitiesUseCase;
    private ValidateWeatherSettingsUseCase validateWeatherSettingsUseCase;

    @Inject
    public MainInteractor(GetWeatherSettingsUseCase getWeatherForecastSettings,
                          SaveWeatherSettingsUseCase saveWeatherSettingsUseCase,
                          GetCitiesUseCase getCitiesUseCase,
                          ValidateWeatherSettingsUseCase validateWeatherSettingsUseCase) {
        this.getWeatherForecastSettings = getWeatherForecastSettings;
        this.saveWeatherSettingsUseCase = saveWeatherSettingsUseCase;
        this.getCitiesUseCase = getCitiesUseCase;
        this.validateWeatherSettingsUseCase = validateWeatherSettingsUseCase;
    }

    public Single<WeatherSettings> getWeatherSettings() {
        return getWeatherForecastSettings.getSingle();
    }

    public Completable saveWeatherSettings(WeatherSettings weatherSettings) {
        return saveWeatherSettingsUseCase.getCompletable(weatherSettings);
    }

    public Single<List<String>> getCities() {
        return getCitiesUseCase.getSingle();
    }

    public Completable validateData(WeatherSettings weatherSettings) {
        return validateWeatherSettingsUseCase.getCompletable(weatherSettings);
    }
}