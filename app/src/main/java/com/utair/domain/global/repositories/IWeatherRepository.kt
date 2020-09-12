package com.utair.domain.global.repositories

import com.utair.domain.global.models.WeatherForecastEntity
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeatherForecastForCity(cityName: String): Single<WeatherForecastEntity>

}