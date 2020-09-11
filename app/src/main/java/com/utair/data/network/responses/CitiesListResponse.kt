package com.utair.data.network.responses

import com.google.gson.annotations.SerializedName

class CitiesListResponse {

    @SerializedName("results")
    lateinit var cities: List<City>
        private set

    inner class City {
        @SerializedName("city")
        lateinit var name: String
            private set
    }

}