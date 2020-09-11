package com.utair.data.repository

import com.utair.data.network.WeatherApiService
import com.utair.data.network.mappers.WeatherForecastResponseMapper
import com.utair.domain.models.WeatherForecastEntity
import com.utair.domain.repository.IWeatherRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        private val weatherApiService: WeatherApiService,
        private val weatherForecastResponseMapper: WeatherForecastResponseMapper
) : IWeatherRepository {

    override fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastEntity> {
        return weatherApiService.getWeather(cityName)
                .map { weatherForecastResponseMapper.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

}