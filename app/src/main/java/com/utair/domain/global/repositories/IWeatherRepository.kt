package com.utair.domain.global.repositories

import com.utair.domain.global.models.WeatherForecast
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecast>

}