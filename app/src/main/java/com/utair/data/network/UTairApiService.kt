package com.utair.data.network

import com.utair.data.network.responses.CitiesListResponse
import io.reactivex.Single
import retrofit2.http.GET

interface UTairApiService {

    companion object {
        const val BASE_URL = "http://api.meetup.com/2/"
        const val RUSSIA_ISO_CODE = "ru"
    }

    @GET("cities?country=$RUSSIA_ISO_CODE")
    fun getCities(): Single<CitiesListResponse>

}