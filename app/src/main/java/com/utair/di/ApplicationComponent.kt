package com.utair.di

import com.utair.di.modules.AndroidModule
import com.utair.di.modules.ApplicationModule
import com.utair.di.modules.DataModule
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.presentation.mvp.presenters.MainPresenter
import com.utair.presentation.mvp.presenters.WeatherForecastPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class, ApplicationModule::class, DataModule::class])
interface ApplicationComponent {

    fun getMainPresenter(): MainPresenter

    fun getWeatherForecastPresenter(): WeatherForecastPresenter

    fun getWeatherForecastInteractor(): WeatherForecastInteractor

}