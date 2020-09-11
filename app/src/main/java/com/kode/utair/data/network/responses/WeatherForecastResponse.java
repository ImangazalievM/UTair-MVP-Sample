package com.kode.utair.data.network.responses;

import androidx.annotation.NonNull;

import java.util.List;

public class WeatherForecastResponse {

    private String cityName;
    private List<HourlyForecast> hourlyForecasts;

    public WeatherForecastResponse(@NonNull String cityName, @NonNull List<HourlyForecast> hourlyForecasts) {
        this.cityName = cityName;
        this.hourlyForecasts = hourlyForecasts;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    @NonNull
    public List<HourlyForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public static class HourlyForecast {

        private long timestamp;
        private int weatherId;
        private float temperature;
        private float speed;

        public HourlyForecast(@NonNull long timestamp, @NonNull int weatherId, float temperature, float speed) {
            this.timestamp = timestamp;
            this.weatherId = weatherId;
            this.temperature = temperature;
            this.speed = speed;
        }

        @NonNull
        public long getTimestamp() {
            return timestamp;
        }

        @NonNull
        public int getWeatherId() {
            return weatherId;
        }

        public float getTemperature() {
            return temperature;
        }

        public float getSpeed() {
            return speed;
        }

    }

}