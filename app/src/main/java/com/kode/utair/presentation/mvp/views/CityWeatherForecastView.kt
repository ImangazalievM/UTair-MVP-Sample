package com.kode.utair.presentation.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kode.utair.domain.models.WeatherForecastEntity.DailyForecast

@StateStrategyType(AddToEndSingleStrategy::class)
interface CityWeatherForecastView : MvpView {

    fun showForecast(dailyForecasts: List<DailyForecast?>?)

    fun showNoNetworkMessage()

}