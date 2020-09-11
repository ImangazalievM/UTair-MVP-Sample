package com.utair.domain.repository

import com.utair.domain.models.WeatherForecastEntity
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeatherForecastForCity(cityName: String?): Single<WeatherForecastEntity?>?

}