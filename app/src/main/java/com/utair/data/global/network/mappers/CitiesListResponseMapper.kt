package com.utair.data.global.network.mappers

import com.utair.data.global.network.responses.CitiesListResponse
import java.util.*
import javax.inject.Inject

class CitiesListResponseMapper @Inject constructor() {

    /**
     * По-хорошему, нужно было создать класс CityEntity, но в данном приложении это было лишним
     */
    fun map(response: CitiesListResponse): List<String> {
        val cities: MutableList<String> = ArrayList()
        for (city in response.cities) {
            cities.add(city.name)
        }
        return cities
    }


}