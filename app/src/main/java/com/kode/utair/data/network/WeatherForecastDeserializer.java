package com.kode.utair.data.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.kode.utair.data.network.responses.WeatherForecastResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecastDeserializer implements JsonDeserializer<WeatherForecastResponse> {

    @Override
    public WeatherForecastResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String cityName = jsonObject.getAsJsonObject("city").get("name").getAsString();

        List<WeatherForecastResponse.HourlyForecast> hourlyForecasts = new ArrayList<>();
        JsonArray hourlyForecastsJson = jsonObject.getAsJsonArray("list");
        for (JsonElement hourlyForecastJsonElement : hourlyForecastsJson) {
            JsonObject hourlyForecastJson = hourlyForecastJsonElement.getAsJsonObject();
            long timestamp = hourlyForecastJson.get("dt").getAsLong(); //unix timestamp
            int weatherId = hourlyForecastJson.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsInt();
            float temperature = hourlyForecastJson.getAsJsonObject("main").get("temp").getAsFloat();
            float speed = hourlyForecastJson.getAsJsonObject("wind").get("speed").getAsFloat();
            WeatherForecastResponse.HourlyForecast hourlyForecast = new WeatherForecastResponse.HourlyForecast(timestamp, weatherId, temperature, speed);
            hourlyForecasts.add(hourlyForecast);
        }

        return new WeatherForecastResponse(cityName, hourlyForecasts);
    }

}