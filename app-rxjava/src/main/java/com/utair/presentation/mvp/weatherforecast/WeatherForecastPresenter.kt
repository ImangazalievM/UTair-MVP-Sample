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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        onTabSelected(DEPART_CITY_TAB_POSITION)
        showCitiesForecasts()
    }

    fun onTabSelected(position: Int) {
        viewState.setTabSelected(position)
        viewState.openForecastPage(position)
    }

    private fun showCitiesForecasts() {
        viewState.showCitiesNames(departCity, arriveCity)
        viewState.showForecastForCities(departCity, arriveCity)
    }

    companion object {
        const val DEPART_CITY_TAB_POSITION = 0
        const val ARRIVE_CITY_TAB_POSITION = 1
    }

}