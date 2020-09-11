package com.kode.utair.di

import com.kode.utair.di.modules.AndroidModule
import com.kode.utair.di.modules.ApplicationModule
import com.kode.utair.di.modules.DataModule
import com.kode.utair.domain.interactors.WeatherForecastInteractor
import com.kode.utair.presentation.mvp.presenters.MainPresenter
import com.kode.utair.presentation.mvp.presenters.WeatherForecastPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class, ApplicationModule::class, DataModule::class])
interface ApplicationComponent {

    fun getMainPresenter(): MainPresenter

    fun getWeatherForecastPresenter(): WeatherForecastPresenter

    fun getWeatherForecastInteractor(): WeatherForecastInteractor

}