package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.utair.di.presenters.weatherforecast.ArriveCity
import com.utair.di.presenters.weatherforecast.DepartCity
import javax.inject.Inject

@InjectViewState
class WeatherForecastPresenter @Inject constructor(
        @DepartCity private val departCity: String,
        @ArriveCity private val arriveCity: String
) : MvpPresenter<WeatherForecastView>() {

    init {
        onTabSelected(0)
        showCitiesForecasts()
    }

    private fun showCitiesForecasts() {
        viewState.showCitiesNames(departCity, arriveCity)
        viewState.showForecastForCities(departCity, arriveCity)
    }

    fun onTabSelected(position: Int) {
        viewState.setTabSelected(position)
        viewState.openForecastPage(position)
    }

}