package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.utair.domain.global.models.WeatherForecast.DailyForecast

@StateStrategyType(AddToEndSingleStrategy::class)
interface CityWeatherForecastView : MvpView {

    fun showForecast(dailyForecasts: List<DailyForecast>)

    fun showNoNetworkMessage()

}