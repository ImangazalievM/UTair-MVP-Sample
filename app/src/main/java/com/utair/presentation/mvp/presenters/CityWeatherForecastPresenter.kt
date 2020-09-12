package com.utair.presentation.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.presentation.mvp.views.CityWeatherForecastView
import com.utair.presentation.ui.global.base.mvp.BasePresenter
import com.utair.presentation.utils.DebugUtils
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class CityWeatherForecastPresenter(
        private val weatherForecastInteractor: WeatherForecastInteractor,
        private val cityName: String
) : BasePresenter<CityWeatherForecastView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        showForecast()
    }

    private fun showForecast() {
        weatherForecastInteractor.getWeatherForecastForCity(cityName)
                .subscribeBy(
                        onSuccess = {
                            viewState.showForecast(it.dailyForecasts)
                        },
                        onError = { handleError(it) }
                )
                .connect()
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is NoNetworkException) {
            viewState.showNoNetworkMessage()
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
        }
    }

}