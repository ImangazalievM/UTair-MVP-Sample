package com.utair.data.global

import android.content.Context
import android.content.SharedPreferences
import com.utair.domain.global.models.FlightOrderData
import javax.inject.Inject

class UTairPreferences @Inject constructor(context: Context) {

    private val appPreferences: SharedPreferences

    fun getFlightOrderData(): FlightOrderData {
        val departCity = appPreferences.getString(DEPART_CITY_KEY, "")!!
        val arriveCity = appPreferences.getString(ARRIVE_CITY_KEY, "")!!
        return FlightOrderData(departCity, arriveCity)
    }

    fun saveOrderData(flightOrderData: FlightOrderData) {
        if (flightOrderData.departCity != null) {
            appPreferences.edit().putString(DEPART_CITY_KEY, flightOrderData.departCity).apply()
        }
        if (flightOrderData.arriveCity != null) {
            appPreferences.edit().putString(ARRIVE_CITY_KEY, flightOrderData.arriveCity).apply()
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