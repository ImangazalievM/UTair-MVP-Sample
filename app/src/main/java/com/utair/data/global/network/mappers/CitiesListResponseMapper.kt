package com.utair.data.global.network.mappers

import com.utair.data.global.network.responses.CitiesListResponse
import java.util.*
import javax.inject.Inject

class CitiesListResponseMapper @Inject constructor() {

    fun map(response: CitiesListResponse): List<String> {
        val cities: MutableList<String> = ArrayList()
        for (city in response.cities) {
            cities.add(city.name)
        }
        return cities
    }


}