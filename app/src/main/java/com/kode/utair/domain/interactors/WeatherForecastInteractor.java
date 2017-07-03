package com.kode.utair.domain.interactors;

import com.kode.utair.domain.models.WeatherForecastEntity;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.domain.usecases.GetWeatherForecastUseCase;
import com.kode.utair.domain.usecases.GetWeatherSettingsUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherForecastInteractor {

    private GetWeatherSettingsUseCase getWeatherForecastSettings;
    private GetWeatherForecastUseCase getWeatherForecastUseCase;

    @Inject
    public WeatherForecastInteractor(GetWeatherSettingsUseCase getWeatherForecastSettings, GetWeatherForecastUseCase getWeatherForecastUseCase) {
        this.getWeatherForecastSettings = getWeatherForecastSettings;
        this.getWeatherForecastUseCase = getWeatherForecastUseCase;
    }

    public Single<WeatherSettings> getWeatherSettings() {
        return getWeatherForecastSettings.getSingle();
    }

    public Single<WeatherForecastEntity> getWeatherForecastForCity(String cityName) {
        return getWeatherForecastUseCase.getSingle(cityName);
    }
}