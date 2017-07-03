package com.kode.utair.domain.repository;

import com.kode.utair.domain.models.WeatherSettings;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IWeatherSettingsRepository {

    Single<WeatherSettings> getWeatherSettings();

    Completable saveWeatherSettings(WeatherSettings weatherSettings);

}
