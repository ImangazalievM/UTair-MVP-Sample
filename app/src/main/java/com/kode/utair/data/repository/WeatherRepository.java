package com.kode.utair.data.repository;

import com.kode.utair.data.repository.datasource.CloudWeatherDataSource;
import com.kode.utair.domain.models.WeatherForecastEntity;
import com.kode.utair.domain.repository.IWeatherRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepository implements IWeatherRepository {

    private CloudWeatherDataSource cloudWeatherDataSource;

    @Inject
    public WeatherRepository(CloudWeatherDataSource cloudWeatherDataSource) {
        this.cloudWeatherDataSource = cloudWeatherDataSource;
    }

    @Override
    public Single<WeatherForecastEntity> getWeatherForecastForCity(String cityName) {
        return cloudWeatherDataSource.getWeather(cityName);
    }



}
