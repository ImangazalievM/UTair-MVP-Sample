package com.kode.utair.data.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import com.kode.utair.domain.models.WeatherSettings
import javax.inject.Inject

class WeatherPreferences @Inject constructor(context: Context) {

    private val appPreferences: SharedPreferences

    fun getWeatherSettings(): WeatherSettings {
        val departCity = appPreferences.getString(DEPART_CITY_KEY, "")!!
        val arriveCity = appPreferences.getString(ARRIVE_CITY_KEY, "")!!
        return WeatherSettings(departCity, arriveCity)
    }

    fun saveWeatherSettings(weatherSettings: WeatherSettings) {
        if (weatherSettings.departCity != null) {
            appPreferences.edit().putString(DEPART_CITY_KEY, weatherSettings.departCity).apply()
        }
        if (weatherSettings.arriveCity != null) {
            appPreferences.edit().putString(ARRIVE_CITY_KEY, weatherSettings.arriveCity).apply()
        }
    }

    companion object {
        private const val APP_PREFS_FILE_NAME = "app_preferences"
        private const val DEPART_CITY_KEY = "depart_city_key"
        private const val ARRIVE_CITY_KEY = "arrive_city_key"
    }

    init {
        appPreferences = context.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}