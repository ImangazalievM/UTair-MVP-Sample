package com.utair.data.cities

import com.utair.data.global.network.UTairApiService
import javax.inject.Inject

class CityRepository @Inject constructor(
        private val utairApiService: UTairApiService
) {

    suspend fun getCitiesList(): List<String> {
        val citiesRepose = utairApiService.getCities()
        return citiesRepose.cities.map { city -> city.name }
    }

}