package com.utair.di.presenters.weatherforecast.cityforecast

import dagger.Module
import dagger.Provides

@Module
class CityForecastPresenterModule(
        private val cityName: String
) {

    @Provides
    @CityName
    fun provideCityName(): String = cityName

}