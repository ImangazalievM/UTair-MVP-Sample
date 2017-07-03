package com.kode.utair.data.repository;

import com.kode.utair.data.repository.datasource.WeatherPreferences;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.domain.repository.IWeatherSettingsRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class WeatherSettingsRepository implements IWeatherSettingsRepository {

    private WeatherPreferences weatherPreferences;

    @Inject
    public WeatherSettingsRepository(WeatherPreferences weatherPreferences) {

        this.weatherPreferences = weatherPreferences;
    }

    @Override
    public Single<WeatherSettings> getWeatherSettings() {
        return Single.create(e -> {
            e.onSuccess(weatherPreferences.getWeatherSettings());
        });
    }

    @Override
    public Completable saveWeatherSettings(WeatherSettings weatherSettings) {
        return Completable.create(e -> {
            weatherPreferences.saveWeatherSettings(weatherSettings);
            e.onComplete();
        });
    }

}
