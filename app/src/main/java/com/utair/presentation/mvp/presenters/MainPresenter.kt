package com.utair.presentation.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.utair.domain.flightorder.MainInteractor
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.global.exceptions.WeatherSettingsValidationError
import com.utair.domain.global.models.WeatherSettings
import com.utair.presentation.mvp.views.MainView
import com.utair.presentation.ui.global.base.mvp.BasePresenter
import com.utair.presentation.utils.DebugUtils
import io.reactivex.rxkotlin.subscribeBy
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val interactor: MainInteractor
) : BasePresenter<MainView>() {

    private lateinit var weatherSettings: WeatherSettings
    private var departureDate: DateTime = DateTime()
    private var returnDate: DateTime? = DateTime().plusWeeks(1)
    private lateinit var cities: List<String>
    
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showDepartDate(departureDate)
        viewState.showReturnDate(returnDate)
        this.weatherSettings = interactor.getWeatherSettings()
        if (weatherSettings.departCity == null || weatherSettings.arriveCity == null) {
            viewState.disableSwapCitiesButton()
        }

        updateCitiesView()
        fetchCities()
    }

    fun onDepartCityClicked() {
        if (::cities.isInitialized) {
            viewState.showDepartCitySelector(cities)
        }
    }

    fun onDepartCitySelected(departCity: String) {
        weatherSettings.departCity = departCity
        viewState.showDepartCity(departCity)
        viewState.enableSwapCitiesButton()
    }

    fun onArriveCityClicked() {
        if (::cities.isInitialized) {
            viewState.showArriveCitySelector(cities)
        }
    }

    fun onArriveCitySelected(arriveCity: String) {
        weatherSettings.arriveCity = arriveCity
        viewState.showArriveCity(arriveCity)
        viewState.enableSwapCitiesButton()
    }

    fun onSwapCitiesButtonClicked() {
        val departCity = weatherSettings.departCity
        val arriveCity = weatherSettings.arriveCity
        weatherSettings.departCity = arriveCity
        weatherSettings.arriveCity = departCity
        updateCitiesView()
    }

    fun onDepartDateClicked() {
        viewState.showDepartDatePicker(departureDate)
    }

    fun onDepartDateSelected(departureDate: DateTime) {
        this.departureDate = departureDate
        viewState.showDepartDate(returnDate)
    }

    fun onReturnDateClicked() {
        viewState.showReturnDatePicker(departureDate)
    }

    fun onReturnDateSelected(returnDate: DateTime) {
        this.returnDate = returnDate
        viewState.showReturnDate(returnDate)
    }

    fun onSetReturnDateButtonClicked() {
        viewState.showReturnDatePicker(departureDate)
    }

    fun onClearReturnDateClicked() {
        returnDate = null
        viewState.hideReturnDate()
        viewState.showReturnDateButton()
    }

    fun onFindFlightsButtonClicked() {
        interactor.saveWeatherSettings(weatherSettings)
        try {
            interactor.validateData(weatherSettings)
            viewState.openWeatherScreen()
        } catch (error: Exception) {
            handleValidationError(error)
        }
    }

    private fun handleValidationError(throwable: Throwable) {
        if (throwable is WeatherSettingsValidationError) {
            viewState.showValidationErrorMessage(throwable.errorMessage)
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
        }
    }

    private fun updateCitiesView() {
        if (weatherSettings.departCity != null) {
            viewState.showDepartCity(weatherSettings.departCity)
        } else {
            viewState.showEmptyDepartCity()
        }
        if (weatherSettings.arriveCity != null) {
            viewState.showArriveCity(weatherSettings.arriveCity)
        } else {
            viewState.showEmptyArriveCity()
        }
    }

    private fun fetchCities() {
        interactor.getCities()
                .subscribeBy(
                        onSuccess = { cities ->
                            this.cities = cities.sortedBy { it.toLowerCase() }
                        },
                        onError = { handleFetchCitiesError(it) }
                )
                .connect()
    }

    private fun handleFetchCitiesError(throwable: Throwable) {
        if (throwable is NoNetworkException) {
            viewState.showNoNetworkMessage()
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
        }
    }

}