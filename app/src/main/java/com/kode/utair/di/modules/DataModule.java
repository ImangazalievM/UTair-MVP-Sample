package com.kode.utair.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kode.utair.data.network.CityApiService;
import com.kode.utair.data.network.NetworkCheckInterceptor;
import com.kode.utair.data.network.NetworkChecker;
import com.kode.utair.data.network.WeatherApiInterceptor;
import com.kode.utair.data.network.WeatherApiService;
import com.kode.utair.data.network.WeatherForecastDeserializer;
import com.kode.utair.data.network.responses.WeatherForecastResponse;
import com.kode.utair.data.repository.CityRepository;
import com.kode.utair.data.repository.WeatherRepository;
import com.kode.utair.data.repository.WeatherSettingsRepository;
import com.kode.utair.domain.repository.ICityRepository;
import com.kode.utair.domain.repository.IWeatherRepository;
import com.kode.utair.domain.repository.IWeatherSettingsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private final String cityApiBaseUrl;
    private final String weatherApiBaseUrl;

    public DataModule(String cityApiBaseUrl, String weatherApiBaseUrl) {
        this.cityApiBaseUrl = cityApiBaseUrl;
        this.weatherApiBaseUrl = weatherApiBaseUrl;
    }

    @Provides
    @Singleton
    ICityRepository provideCityRepository(CityRepository cityRepository) {
        return cityRepository;
    }

    @Provides
    @Singleton
    IWeatherRepository provideWeatherRepository(WeatherRepository weatherRepository) {
        return weatherRepository;
    }

    @Provides
    @Singleton
    IWeatherSettingsRepository provideWeatherSettingsRepository(WeatherSettingsRepository weatherForecastSettingsRepository) {
        return weatherForecastSettingsRepository;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(NetworkChecker networkChecker) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkCheckInterceptor(networkChecker))
                .addInterceptor(new WeatherApiInterceptor())
                .build();

        return client;
    }

    @Provides
    @Singleton
    CityApiService provideCityApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(cityApiBaseUrl)
                .build();

        return retrofit.create(CityApiService.class);
    }

    @Provides
    @Singleton
    WeatherApiService provideWeatherApi(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherForecastResponse.class, new WeatherForecastDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(weatherApiBaseUrl)
                .build();

        return retrofit.create(WeatherApiService.class);
    }

}
