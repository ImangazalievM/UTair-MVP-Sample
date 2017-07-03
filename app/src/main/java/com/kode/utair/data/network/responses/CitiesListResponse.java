package com.kode.utair.data.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesListResponse {

    @SerializedName("results")
    private List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public class City {

        @SerializedName("city")
        private String name;

        public String getName() {
            return name;
        }
    }

}