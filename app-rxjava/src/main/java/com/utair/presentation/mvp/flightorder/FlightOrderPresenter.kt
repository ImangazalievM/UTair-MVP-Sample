package com.utair.presentation.mvp.flightorder

import com.arellomobile.mvp.InjectViewState
import com.utair.domain.global.models.FlightOrderData
import com.utair.domain.flightorder.MainInteractor
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.global.exceptions.FlightOrderDataValidationError
import com.utair.presentation.ui.global.base.mvp.BasePresenter
import com.utair.presentation.ui.global.navigation.WeatherForecastScreen
import com.utair.presentation.mvp.global.ErrorHandler
import io.reactivex.rxkotlin.subscribeBy
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
        this.departCity = departCity
        viewState.showDepartCity(departCity)
        viewState.enableSwapCitiesButton()
    }

    fun onArriveCityClicked() {
        if (::cities.isInitialized) {
            viewState.showArriveCitySelector(cities)
        }
    }

    fun onArriveCitySelected(arriveCity: String) {
        this.arriveCity = arriveCity
        viewState.showArriveCity(arriveCity)
        viewState.enableSwapCitiesButton()
    }

    fun onSwapCitiesButtonClicked() {
        val oldDepartCity = this.departCity
        departCity = arriveCity
        arriveCity = oldDepartCity
        updateCitiesView()
    }

    fun onSetDepartDateClicked() {
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
        viewState.showReturnDate(returnDate)
    }

    fun onSetReturnDateButtonClicked() {
        viewState.showReturnDatePicker(arriveDate ?: departureDate)
    }

    fun onClearReturnDateClicked() {
        arriveDate = null
        viewState.hideReturnDate()
        viewState.showReturnDateButton()
    }

    fun onFindFlightsButtonClicked() {
        val data = FlightOrderData(arriveCity, departCity)
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
            errorHandler.handle(throwable)
        }
    }

}