package com.kode.utair.data.repository.datasource;

import android.content.Context;
import android.content.SharedPreferences;

import com.kode.utair.domain.models.WeatherSettings;

import javax.inject.Inject;

public class WeatherPreferences {

    private static final String APP_PREFS_FILE_NAME = "app_preferences";

    private static final String DEPART_CITY_KEY = "depart_city_key";
    private static final String ARRIVE_CITY_KEY = "arrive_city_key";


    private SharedPreferences appPreferences;

    @Inject
    public WeatherPreferences(Context context) {
        this.appPreferences = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public WeatherSettings getWeatherSettings() {
        String departCity = appPreferences.getString(DEPART_CITY_KEY, null);
        String arriveCity = appPreferences.getString(ARRIVE_CITY_KEY, null);
        return new WeatherSettings(departCity, arriveCity);
    }


    public void saveWeatherSettings(WeatherSettings weatherSettings) {
        if (weatherSettings.getDepartCity() != null) {
            appPreferences.edit().putString(DEPART_CITY_KEY, weatherSettings.getDepartCity()).apply();
        }

        if (weatherSettings.getArriveCity() != null) {
            appPreferences.edit().putString(ARRIVE_CITY_KEY, weatherSettings.getArriveCity()).apply();
        }
    }


}
