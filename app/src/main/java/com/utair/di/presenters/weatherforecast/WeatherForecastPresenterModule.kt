package com.utair.di.presenters.weatherforecast

import dagger.Module
import dagger.Provides

@Module
class WeatherForecastPresenterModule(
        private val departCity: String,
        private val arriveCity: String
) {

    @Provides
    @DepartCity
    fun provideDepartCity(): String = departCity

    @Provides
    @ArriveCity
    fun provideArriveCity(): String = arriveCity

}