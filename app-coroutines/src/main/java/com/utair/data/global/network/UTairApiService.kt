package com.utair.data.global.network

import com.utair.data.global.network.responses.CitiesListResponse
import retrofit2.http.GET

interface UTairApiService {

    @GET("cities?country=${ApiConstants.RUSSIA_ISO_CODE}")
    suspend fun getCities(): CitiesListResponse

}