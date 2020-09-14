package com.utair.di.presenters.weatherforecast.cityforecast

import com.utair.di.ApplicationComponent
import com.utair.di.scopes.Presenter
import com.utair.presentation.mvp.weatherforecast.CityWeatherForecastPresenter
import dagger.Component

@Presenter
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [CityForecastPresenterModule::class]
)
interface CityForecastComponent {

    fun getCityWeatherForecastPresenter(): CityWeatherForecastPresenter

}