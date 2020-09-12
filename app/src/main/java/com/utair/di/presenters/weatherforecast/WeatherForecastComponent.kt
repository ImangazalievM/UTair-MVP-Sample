package com.utair.di.presenters.weatherforecast

import com.utair.di.ApplicationComponent
import com.utair.di.scopes.Presenter
import com.utair.presentation.mvp.weatherforecast.WeatherForecastPresenter
import dagger.Component

@Presenter
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [WeatherForecastPresenterModule::class]
)
interface WeatherForecastComponent {

    fun getWeatherForecastPresenter(): WeatherForecastPresenter

}