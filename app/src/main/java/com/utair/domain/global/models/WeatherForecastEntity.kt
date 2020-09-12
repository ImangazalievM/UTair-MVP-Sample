package com.utair.domain.global.models

import org.joda.time.DateTime
import java.util.*

class WeatherForecastEntity(val cityName: String, val dailyForecasts: List<DailyForecast>) {

    class DailyForecast(val date: DateTime) {

        private val hourlyForecasts: MutableList<HourlyForecast>

        init {
            hourlyForecasts = ArrayList()
        }

        fun addHourForecast(hourlyForecast: HourlyForecast) {
            hourlyForecasts.add(hourlyForecast)
        }

        fun getHourlyForecasts(): List<HourlyForecast> {
            return hourlyForecasts
        }

    }

    class HourlyForecast(
            val dateTime: DateTime,
            val condition: WeatherCondition,
            val temperature: Float,
            val speed: Float
    )

    enum class WeatherCondition {
        SUNNY, FOG, LIGHT_CLOUDS, CLOUDS, LIGHT_RAIN, RAIN, SNOW, STORM, UNKNOWN
    }

}