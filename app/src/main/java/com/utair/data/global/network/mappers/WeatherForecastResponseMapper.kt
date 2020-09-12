package com.utair.data.global.network.mappers

import com.utair.data.global.network.responses.WeatherForecastResponse
import com.utair.domain.global.models.WeatherForecast
import com.utair.domain.global.models.WeatherForecast.DailyForecast
import com.utair.domain.global.models.WeatherForecast.WeatherCondition
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class WeatherForecastResponseMapper @Inject constructor() {

     fun map(response: WeatherForecastResponse): WeatherForecast {
        val hourlyForecastEntities: MutableList<WeatherForecast.HourlyForecast> = ArrayList()
        val hourlyForecastResponses = response.hourlyForecasts
        for (hourlyForecastResponse in hourlyForecastResponses) {
            // convert from unix timestamp to date, multiply
            // by thousand to convert seconds to milliseconds
            val dateTime = DateTime(hourlyForecastResponse.timestamp * 1000)
            val condition = getConditionById(hourlyForecastResponse.weatherId)
            val temperature = hourlyForecastResponse.temperature
            val speed = hourlyForecastResponse.speed
            hourlyForecastEntities.add(WeatherForecast.HourlyForecast(dateTime, condition, temperature, speed))
        }
        val dailyForecastEntities = splitForecastsByDays(hourlyForecastEntities)
        return WeatherForecast(response.cityName, dailyForecastEntities)
    }

    /**
     * Returns weather conditions by identifier
     * More information about weather IDs can be
     * found here https://openweathermap.org/weather-conditions
     */
    private fun getConditionById(weatherId: Int): WeatherCondition {
        if (weatherId >= 200 && weatherId <= 232) {
            return WeatherCondition.STORM
        } else if (weatherId >= 300 && weatherId <= 321) {
            return WeatherCondition.LIGHT_RAIN
        } else if (weatherId >= 500 && weatherId <= 504) {
            return WeatherCondition.RAIN
        } else if (weatherId == 511) {
            return WeatherCondition.SNOW
        } else if (weatherId >= 520 && weatherId <= 531) {
            return WeatherCondition.RAIN
        } else if (weatherId >= 600 && weatherId <= 622) {
            return WeatherCondition.SNOW
        } else if (weatherId >= 701 && weatherId <= 761) {
            return WeatherCondition.FOG
        } else if (weatherId == 761 || weatherId == 781) {
            return WeatherCondition.STORM
        } else if (weatherId == 800) {
            return WeatherCondition.SUNNY
        } else if (weatherId == 801) {
            return WeatherCondition.LIGHT_CLOUDS
        } else if (weatherId >= 802 && weatherId <= 804) {
            return WeatherCondition.CLOUDS
        }
        return WeatherCondition.UNKNOWN
    }

    /**
     * Split hourly forecast by day
     */
    private fun splitForecastsByDays(hourlyForecastEntities: List<WeatherForecast.HourlyForecast>): List<DailyForecast> {
        //sort the hourly forecast by ascending time
        Collections.sort(hourlyForecastEntities) { forecast1: WeatherForecast.HourlyForecast, forecast2: WeatherForecast.HourlyForecast -> forecast1.dateTime.compareTo(forecast2.dateTime) }
        val forecastsByDay: MutableMap<String, DailyForecast> = HashMap()
        for (hourlyForecast in hourlyForecastEntities) {
            val forecastDate = hourlyForecast.dateTime
            val dateStringKey = forecastDate.dayOfMonth.toString() + "." + forecastDate.monthOfYear + "." + forecastDate.year
            var dailyForecast = forecastsByDay[dateStringKey]
            if (dailyForecast == null) {
                dailyForecast = DailyForecast(DateTime(forecastDate))
                forecastsByDay[dateStringKey] = dailyForecast
            }
            dailyForecast.addHourForecast(hourlyForecast)
        }
        val dailyForecastEntities: List<DailyForecast> = ArrayList(forecastsByDay.values)

        //sort the forecast by date
        Collections.sort(dailyForecastEntities) { forecast1: DailyForecast, forecast2: DailyForecast -> forecast1.date.compareTo(forecast2.date) }
        return dailyForecastEntities
    }
}