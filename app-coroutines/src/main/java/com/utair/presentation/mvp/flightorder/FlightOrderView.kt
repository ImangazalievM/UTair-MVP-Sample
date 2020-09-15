package com.utair.presentation.mvp.flightorder

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import org.joda.time.DateTime

@StateStrategyType(AddToEndSingleStrategy::class)
interface FlightOrderView : MvpView {

    fun showEmptyDepartCity()

    fun showDepartCity(departCity: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDepartCitySelector(cities: List<String>)

    fun showEmptyArriveCity()

    fun showArriveCity(arriveCity: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showArriveCitySelector(cities: List<String>)

    fun enableSwapCitiesButton(isEnabled: Boolean)

    fun showDepartDate(departDate: DateTime)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDepartDatePicker(departDate: DateTime)

    fun showReturnDate(returnDate: DateTime)

    fun updateReturnDateVisiblity(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showReturnDatePicker(returnDate: DateTime)

    fun showReturnDateButton(isVisible: Boolean)

    fun showNoNetworkMessage()

    fun showValidationErrorMessage(errorMessage: String)

}