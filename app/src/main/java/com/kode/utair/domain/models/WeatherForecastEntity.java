package com.kode.utair.domain.models;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecastEntity {

    private String cityName;
    private List<DailyForecast> dailyForecasts;

    public WeatherForecastEntity(@NonNull String cityName, @NonNull List<DailyForecast> dailyForecasts) {
        this.cityName = cityName;
        this.dailyForecasts = dailyForecasts;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    @NonNull
    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public static class DailyForecast {

        private DateTime date;
        private List<HourlyForecast> hourlyForecasts;

        public DailyForecast(DateTime date) {
            this.date = date;
            this.hourlyForecasts = new ArrayList<>();
        }

        public DateTime getDate() {
            return date;
        }

        public void addHourForecast(HourlyForecast hourlyForecast) {
            hourlyForecasts.add(hourlyForecast);
        }

        @NonNull
        public List<HourlyForecast> getHourlyForecasts() {
            return hourlyForecasts;
        }

    }

    public static class HourlyForecast {

        private DateTime dateTime;
        private WeatherCondition condition;
        private float temperature;
        private float speed;

        public HourlyForecast(@NonNull DateTime dateTime, @NonNull WeatherCondition condition, float temperature, float speed) {
            this.dateTime = dateTime;
            this.condition = condition;
            this.temperature = temperature;
            this.speed = speed;
        }

        @NonNull
        public DateTime getDateTime() {
            return dateTime;
        }

        @NonNull
        public WeatherCondition getCondition() {
            return condition;
        }

        public float getTemperature() {
            return temperature;
        }

        public float getSpeed() {
            return speed;
        }

    }

    public enum WeatherCondition {
        SUNNY, FOG, LIGHT_CLOUDS, CLOUDS, LIGHT_RAIN, RAIN, SNOW, STORM, UNKNOWN
    }

}
