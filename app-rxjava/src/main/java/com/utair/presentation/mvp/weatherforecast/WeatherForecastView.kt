package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface WeatherForecastView : MvpView {

    fun showForecastForCities(departCity: String, arriveCity: String)

    fun openForecastPage(position: Int)

}