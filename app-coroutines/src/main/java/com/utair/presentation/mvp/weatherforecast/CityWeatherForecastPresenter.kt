package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.InjectViewState
import com.utair.di.PrimitiveWrapper
import com.utair.di.qualifiers.CityName
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.presentation.mvp.global.ErrorHandler
import com.utair.presentation.ui.global.base.mvp.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@InjectViewState
class CityWeatherForecastPresenter @Inject constructor(
        @CityName private val cityName: PrimitiveWrapper<String>,
        private val weatherForecastInteractor: WeatherForecastInteractor,
        private val errorHandler: ErrorHandler
) : BasePresenter<CityWeatherForecastView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        showForecast()
    }

    private fun showForecast() = launch {
        try {
            val forecast = weatherForecastInteractor.getWeatherForecastForCity(cityName.value)
            viewState.showForecast(forecast.dailyForecasts)
        } catch (error: Exception) {
            if (error is NoNetworkException) {
                viewState.showNoNetworkMessage()
            } else {
                errorHandler.handle(error)
            }
        }
    }

}