package com.kode.utair.data.network;

import com.kode.utair.data.network.responses.CitiesListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CityApiService {

    String BASE_URL = "http://api.meetup.com/2/";

    String RUSSIA_ISO_CODE = "ru";

    @GET("cities?country=" + RUSSIA_ISO_CODE)
    Single<CitiesListResponse> getCities();


}
