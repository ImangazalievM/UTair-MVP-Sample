package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface WeatherForecastView : MvpView {

    fun showCitiesNames(departCityName: String, arriveCityName: String)

    fun setTabSelected(currentTabPosition: Int)

    fun openForecastPage(position: Int)

    fun showForecastForCities(departCity: String, arriveCity: String)

}