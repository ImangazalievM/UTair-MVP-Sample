package com.kode.utair.domain.repository;

import com.kode.utair.domain.models.WeatherForecastEntity;

import java.util.List;

import io.reactivex.Single;

public interface IWeatherRepository {

    Single<WeatherForecastEntity> getWeatherForecastForCity(String cityName);

}
