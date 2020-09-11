package com.kode.utair.domain.repository

import com.kode.utair.domain.models.WeatherForecastEntity
import io.reactivex.Single

interface IWeatherRepository {

    fun getWeatherForecastForCity(cityName: String?): Single<WeatherForecastEntity?>?

}