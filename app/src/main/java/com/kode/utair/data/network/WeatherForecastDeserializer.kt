package com.kode.utair.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.kode.utair.data.network.responses.WeatherForecastResponse
import java.lang.reflect.Type
import java.util.*

class WeatherForecastDeserializer : JsonDeserializer<WeatherForecastResponse> {

    @Throws(JsonParseException::class)
    override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
    ): WeatherForecastResponse {
        val jsonObject = json.asJsonObject
        val cityName = jsonObject.getAsJsonObject("city")["name"].asString
        val hourlyForecasts: MutableList<WeatherForecastResponse.HourlyForecast> = ArrayList()
        val hourlyForecastsJson = jsonObject.getAsJsonArray("list")
        for (hourlyForecastJsonElement in hourlyForecastsJson) {
            val hourlyForecastJson = hourlyForecastJsonElement.asJsonObject
            val timestamp = hourlyForecastJson["dt"].asLong //unix timestamp
            val weatherId = hourlyForecastJson.getAsJsonArray("weather")[0].asJsonObject["id"].asInt
            val temperature = hourlyForecastJson.getAsJsonObject("main")["temp"].asFloat
            val speed = hourlyForecastJson.getAsJsonObject("wind")["speed"].asFloat
            val hourlyForecast = WeatherForecastResponse.HourlyForecast(timestamp, weatherId, temperature, speed)
            hourlyForecasts.add(hourlyForecast)
        }
        return WeatherForecastResponse(cityName, hourlyForecasts)
    }

}