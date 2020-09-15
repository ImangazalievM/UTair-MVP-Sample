package com.utair.presentation.mvp.flightorder

import com.arellomobile.mvp.InjectViewState
import com.utair.domain.flightorder.MainInteractor
import com.utair.domain.global.exceptions.FlightOrderDataValidationError
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.global.models.FlightOrderData
import com.utair.presentation.mvp.global.ErrorHandler
import com.utair.presentation.ui.global.base.mvp.BasePresenter
import com.utair.presentation.ui.global.navigation.WeatherForecastScreen
import kotlinx.coroutines.launch
import me.aartikov.alligator.Navigator
import org.joda.time.DateTime
import javax.inject.Inject

@InjectViewState
class FlightOrderPresenter @Inject constructor(
        private val interactor: MainInteractor,
        private val navigator: Navigator,
        private val errorHandler: ErrorHandler
) : BasePresenter<FlightOrderView>() {

    private var departCity: String? = null
    private var arriveCity: String? = null
    private var departureDate: DateTime = DateTime()
    private var arriveDate: DateTime? = DateTime().plusWeeks(1)
    private lateinit var cities: List<String>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showDepartDate(departureDate)
        viewState.showReturnDate(arriveDate!!)

        val flightOrderData = interactor.getFlightOrderData()
        if (flightOrderData.departCity == null || flightOrderData.arriveCity == null) {
            departCity = flightOrderData.departCity
            arriveCity = flightOrderData.arriveCity
            viewState.enableSwapCitiesButton(false)
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
        this.departCity = departCity
        viewState.showDepartCity(departCity)
        viewState.enableSwapCitiesButton(true)
    }

    fun onArriveCityClicked() {
        if (::cities.isInitialized) {
            viewState.showArriveCitySelector(cities)
        }
    }

    fun onArriveCitySelected(arriveCity: String) {
        this.arriveCity = arriveCity
        viewState.showArriveCity(arriveCity)
        viewState.enableSwapCitiesButton(true)
    }

    fun onSwapCitiesButtonClicked() {
        val oldDepartCity = this.departCity
        departCity = arriveCity
        arriveCity = oldDepartCity
        updateCitiesView()
    }

    fun onDepartDateClicked() {
        viewState.showDepartDatePicker(departureDate)
    }

    fun onDepartDateSelected(departureDate: DateTime) {
        this.departureDate = departureDate
        viewState.showDepartDate(departureDate)
    }

    fun onReturnDateClicked() {
        viewState.showReturnDatePicker(arriveDate!!)
    }

    fun onReturnDateSelected(returnDate: DateTime) {
        this.arriveDate = returnDate
        viewState.updateReturnDateVisiblity(true)
        viewState.showReturnDate(returnDate)
    }

    fun onSetReturnDateButtonClicked() {
        viewState.showReturnDatePicker(arriveDate ?: departureDate)
    }

    fun onClearReturnDateClicked() {
        arriveDate = null
        viewState.updateReturnDateVisiblity(false)
        viewState.showReturnDateButton(true)
    }

    fun onFindFlightsButtonClicked() {
        val data = FlightOrderData(departCity, arriveCity)
        try {
            interactor.validateData(data)
            interactor.saveFlightOrderData(data)
            val screen = WeatherForecastScreen(
                    arriveCity = arriveCity!!,
                    departCity = departCity!!
            )
            navigator.goForward(screen)
        } catch (error: Exception) {
            handleValidationError(error)
        }
    }

    private fun handleValidationError(throwable: Throwable) {
        if (throwable is FlightOrderDataValidationError) {
            viewState.showValidationErrorMessage(throwable.errorMessage)
        } else {
            errorHandler.handle(throwable)
        }
    }

    private fun updateCitiesView() {
        if (departCity != null) {
            viewState.showDepartCity(departCity!!)
        } else {
            viewState.showEmptyDepartCity()
        }
        if (arriveCity != null) {
            viewState.showArriveCity(arriveCity!!)
        } else {
            viewState.showEmptyArriveCity()
        }
    }

    private fun fetchCities() = launch {
        try {
            cities = interactor.getCities()
            cities = cities.sortedBy { it.toLowerCase() }
        } catch (error: Exception) {
            if (error is NoNetworkException) {
                viewState.showNoNetworkMessage()
            } else {
                errorHandler.handle(error)
            }
        }
    }

}