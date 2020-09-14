package com.utair.data.weather

import com.utair.data.global.network.WeatherApiService
import com.utair.data.global.network.mappers.WeatherForecastResponseMapper
import com.utair.domain.global.models.WeatherForecast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        private val weatherApiService: WeatherApiService,
        private val weatherForecastResponseMapper: WeatherForecastResponseMapper
) {

    fun getWeatherForecastForCity(
            cityName: String
    ): Single<WeatherForecast> {
        return weatherApiService.getWeather(cityName)
                .map { weatherForecastResponseMapper.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

}