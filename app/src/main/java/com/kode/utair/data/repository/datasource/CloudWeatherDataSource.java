package com.kode.utair.data.repository.datasource;

import com.kode.utair.data.network.WeatherApiService;
import com.kode.utair.data.network.mappers.WeatherForecastResponseToEntityMapper;
import com.kode.utair.domain.models.WeatherForecastEntity;

import javax.inject.Inject;

import io.reactivex.Single;


public class CloudWeatherDataSource {

    private WeatherApiService weatherApiService;
    private WeatherForecastResponseToEntityMapper weatherForecastResponseToEntityMapper;

    @Inject
    public CloudWeatherDataSource(WeatherApiService weatherApiService,
                                  WeatherForecastResponseToEntityMapper weatherForecastResponseToEntityMapper) {

        this.weatherApiService = weatherApiService;
        this.weatherForecastResponseToEntityMapper = weatherForecastResponseToEntityMapper;
    }

    public Single<WeatherForecastEntity> getWeather(String cityName) {
        return weatherApiService.getWeather(cityName)
                .map(weatherForecastResponse -> weatherForecastResponseToEntityMapper.map(weatherForecastResponse));

    }

}
