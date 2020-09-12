package com.utair.presentation.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.domain.global.models.WeatherSettings
import com.utair.presentation.mvp.views.WeatherForecastView
import com.utair.presentation.utils.DebugUtils
import javax.inject.Inject

@InjectViewState
class WeatherForecastPresenter @Inject constructor(
        private val weatherForecastInteractor: WeatherForecastInteractor
) : MvpPresenter<WeatherForecastView>() {

    private val currentTabPosition = 0

    init {
        onTabSelected(0)
        showCitiesForecasts()
    }

    private fun showCitiesForecasts() {
        weatherForecastInteractor.getWeatherSettings()
                .subscribe(
                        { onWeatherSettingsLoaded(it) },
                        { handleError(it) }
                )
    }

    private fun onWeatherSettingsLoaded(weatherSettings: WeatherSettings) {
        val departCity = weatherSettings.departCity
        val arriveCity = weatherSettings.arriveCity
        viewState.showCitiesNames(departCity, arriveCity)
        viewState.showForecastForCities(departCity, arriveCity)
    }

    private fun handleError(throwable: Throwable) {
        DebugUtils.showDebugErrorMessage(throwable)
    }

    fun onTabSelected(position: Int) {
        viewState.setTabSelected(position)
        viewState.openForecastPage(position)
    }

}