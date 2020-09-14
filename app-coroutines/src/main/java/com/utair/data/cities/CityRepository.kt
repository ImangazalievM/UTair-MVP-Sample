package com.utair.data.cities

import com.utair.data.global.network.UTairApiService
import com.utair.data.global.network.mappers.CitiesListResponseMapper
import javax.inject.Inject

class CityRepository @Inject constructor(
        private val utairApiService: UTairApiService,
        private val citiesListResponseMapper: CitiesListResponseMapper
) {

    suspend fun getCitiesList(): List<String> {
        val citiesResponse = utairApiService.getCities()
        return citiesListResponseMapper.map(citiesResponse)
    }

}