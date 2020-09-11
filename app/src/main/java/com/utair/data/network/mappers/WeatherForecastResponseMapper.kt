package com.utair.data.network.mappers

import com.utair.data.network.responses.WeatherForecastResponse
import com.utair.domain.models.WeatherForecastEntity
import com.utair.domain.models.WeatherForecastEntity.DailyForecast
import com.utair.domain.models.WeatherForecastEntity.WeatherCondition
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject

class WeatherForecastResponseMapper @Inject constructor() {

     fun map(response: WeatherForecastResponse): WeatherForecastEntity {
        val hourlyForecastEntities: MutableList<WeatherForecastEntity.HourlyForecast> = ArrayList()
        val hourlyForecastResponses = response.hourlyForecasts
        for (hourlyForecastResponse in hourlyForecastResponses) {
            //переводим из unix timestamp в дату, умножаем на тысячу для перевода секунд в миллисекунды
            val dateTime = DateTime(hourlyForecastResponse.timestamp * 1000)
            val condition = getConditionById(hourlyForecastResponse.weatherId)
            val temperature = hourlyForecastResponse.temperature
            val speed = hourlyForecastResponse.speed
            hourlyForecastEntities.add(WeatherForecastEntity.HourlyForecast(dateTime, condition, temperature, speed))
        }
        val dailyForecastEntities = splitForecastsByDays(hourlyForecastEntities)
        return WeatherForecastEntity(response.cityName, dailyForecastEntities)
    }

    /**
     * Возвращает погодные условия по идентификатору
     * Подробнее об иденетификаторах погодных условий можно узнать здесь https://openweathermap.org/weather-conditions
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
     * Разбиват почасовой прогноз по дням
     */
    private fun splitForecastsByDays(hourlyForecastEntities: List<WeatherForecastEntity.HourlyForecast>): List<DailyForecast> {
        //сортируем почасовой прогноз по возрастанию времени
        Collections.sort(hourlyForecastEntities) { forecast1: WeatherForecastEntity.HourlyForecast, forecast2: WeatherForecastEntity.HourlyForecast -> forecast1.dateTime.compareTo(forecast2.dateTime) }
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

        //сортируем прогноз по дате
        Collections.sort(dailyForecastEntities) { forecast1: DailyForecast, forecast2: DailyForecast -> forecast1.date.compareTo(forecast2.date) }
        return dailyForecastEntities
    }
}