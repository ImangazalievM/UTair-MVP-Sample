package com.utair.presentation.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.domain.global.models.WeatherForecastEntity
import com.utair.presentation.mvp.views.CityWeatherForecastView
import com.utair.presentation.utils.DebugUtils

@InjectViewState
class CityWeatherForecastPresenter(
        private val weatherForecastInteractor: WeatherForecastInteractor,
        private val cityName: String
) : MvpPresenter<CityWeatherForecastView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        showForecast()
    }

    private fun showForecast() {
        weatherForecastInteractor.getWeatherForecastForCity(cityName)
                .subscribe(
                        { weatherForecastEntity: WeatherForecastEntity ->
                            viewState.showForecast(weatherForecastEntity.dailyForecasts)
                        })
                { throwable: Throwable -> handleError(throwable) }
    }

    private fun handleError(throwable: Throwable) {
        Log.e("UTair", "showForecast", throwable)
        if (throwable is NoNetworkException) {
            viewState.showNoNetworkMessage()
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
        }
    }

}