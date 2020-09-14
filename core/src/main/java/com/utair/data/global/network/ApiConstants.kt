package com.utair.data.global.network

import com.utair.core.BuildConfig

object ApiConstants {
        const val CITIES_BASE_URL = "http://api.meetup.com/2/"
        const val RUSSIA_ISO_CODE = "ru"

        const val OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY
        const val CELSIUS_METRICS_KEY = "metric"
}