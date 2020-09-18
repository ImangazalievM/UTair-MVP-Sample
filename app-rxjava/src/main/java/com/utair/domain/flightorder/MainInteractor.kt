package com.utair.domain.flightorder

import com.utair.data.cities.CityRepository
import com.utair.data.global.UTairPreferences
import com.utair.domain.global.models.FlightOrderData
import com.utair.presentation.mvp.flightorder.PassengersData
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(
        private val prefs: UTairPreferences,
        private val passengersDataValidator: PassengersDataValidator,
        private val flightOrderDataValidator: FlightOrderDataValidator,
        private val cityRepository: CityRepository
) {

    fun getFlightOrderData(): FlightOrderData {
        return prefs.getFlightOrderData()
    }

    fun saveFlightOrderData(flightOrderData: FlightOrderData) {
        prefs.saveOrderData(flightOrderData)
    }

    fun getCities(): Single<List<String>> {
        return cityRepository.getCitiesList()
    }

    fun validatePassengersData(data: PassengersData) {
        return passengersDataValidator.validate(data)
    }

    fun validateData(data: FlightOrderData) {
        return flightOrderDataValidator.validate(data)
    }

}