package com.utair.presentation.mvp.weatherforecast

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.utair.di.PrimitiveWrapper
import com.utair.di.qualifiers.ArriveCity
import com.utair.di.qualifiers.DepartCity
import javax.inject.Inject

@InjectViewState
class WeatherForecastPresenter @Inject constructor(
        @DepartCity private val departCity: PrimitiveWrapper<String>,
        @ArriveCity private val arriveCity: PrimitiveWrapper<String>
) : MvpPresenter<WeatherForecastView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showForecastForCities(departCity.value, arriveCity.value)
        onTabSelected(DEPART_CITY_TAB_POSITION)
    }

    fun onTabSelected(position: Int) {
        viewState.openForecastPage(position)
    }

    companion object {
        const val DEPART_CITY_TAB_POSITION = 0
        const val ARRIVE_CITY_TAB_POSITION = 1
    }

}