package com.kode.utair.data.network;

import com.kode.utair.data.network.responses.WeatherForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String API_KEY = "eb83085d6324416a2af66f8aa214eff8";
    String CELSIUS_METRICS_KEY = "metric";

    @GET("forecast?units=" + CELSIUS_METRICS_KEY)
    Single<WeatherForecastResponse> getWeather(@Query("q") String cityName);


}
