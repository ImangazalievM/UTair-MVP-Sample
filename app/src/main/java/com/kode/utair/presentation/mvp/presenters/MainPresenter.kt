package com.kode.utair.presentation.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kode.utair.domain.exceptions.NoNetworkException
import com.kode.utair.domain.exceptions.WeatherSettingsValidationError
import com.kode.utair.domain.interactors.MainInteractor
import com.kode.utair.domain.models.WeatherSettings
import com.kode.utair.presentation.mvp.views.MainView
import com.kode.utair.presentation.utils.DebugUtils
import io.reactivex.functions.Consumer
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
        private val interactor: MainInteractor
) : MvpPresenter<MainView>() {

    private lateinit var weatherSettings: WeatherSettings
    private var departureDate: DateTime = DateTime()
    private var returnDate: DateTime? = DateTime().plusWeeks(1)
    private lateinit var cities: List<String>
    
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        initUi()
        loadWeatherSettings()

        fetchCities()
    }

    private fun initUi() {
        viewState.showDepartDate(departureDate)
        viewState.showReturnDate(returnDate)
    }

    private fun loadWeatherSettings() {
        interactor.getWeatherSettings()
                .subscribe { weatherSettings: WeatherSettings -> onWeatherSettingsLoaded(weatherSettings) }
    }

    private fun onWeatherSettingsLoaded(weatherSettings: WeatherSettings) {
        this.weatherSettings = weatherSettings
        if (weatherSettings.departCity == null || weatherSettings.arriveCity == null) {
            viewState.disableSwapCitiesButton()
        }
        updateCitiesView()
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
        interactor.saveWeatherSettings(weatherSettings).blockingAwait()
        interactor.validateData(weatherSettings!!)
                .subscribe(
                        {
                            viewState.openWeatherScreen()
                        },
                        { throwable: Throwable -> handleValidationError(throwable) }
                )
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
                .subscribe({ cities: List<String> ->
                    Collections.sort(cities) { obj: String, str: String? ->
                        obj.compareTo(str!!, ignoreCase = true)
                    }
                    this.cities = cities
                }) { throwable: Throwable -> handleFetchCitiesError(throwable) }
    }

    private fun handleFetchCitiesError(throwable: Throwable) {
        Log.e("UTair", "FetchCitiesError", throwable)
        if (throwable is NoNetworkException) {
            viewState.showNoNetworkMessage()
        } else {
            DebugUtils.showDebugErrorMessage(throwable)
        }
    }

}