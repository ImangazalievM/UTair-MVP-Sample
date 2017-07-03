package com.kode.utair.domain.models;

import android.support.annotation.Nullable;

public class WeatherSettings {

    private String departCity;
    private String arriveCity;

    public WeatherSettings(@Nullable String departCity, @Nullable String arriveCity) {
        this.departCity = departCity;
        this.arriveCity = arriveCity;
    }

    @Nullable
    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    @Nullable
    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

}
