package com.utair.domain.global

import com.utair.domain.global.models.WeatherForecastEntity
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastEntity>

}