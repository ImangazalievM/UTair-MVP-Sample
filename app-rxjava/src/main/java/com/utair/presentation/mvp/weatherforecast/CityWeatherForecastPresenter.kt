package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.InjectViewState
import com.utair.di.presenters.weatherforecast.cityforecast.CityName
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.presentation.ui.global.base.mvp.BasePresenter
import com.utair.presentation.mvp.global.ErrorHandler
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@InjectViewState
class CityWeatherForecastPresenter @Inject constructor(
        @CityName private val cityName: String,
        private val weatherForecastInteractor: WeatherForecastInteractor,
        private val errorHandler: ErrorHandler
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
            errorHandler.handle(throwable)
        }
    }

}