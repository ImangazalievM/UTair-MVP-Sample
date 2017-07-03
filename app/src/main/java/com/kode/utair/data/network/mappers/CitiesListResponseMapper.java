package com.kode.utair.data.network.mappers;

import com.kode.utair.data.network.responses.CitiesListResponse;
import com.kode.utair.domain.commons.Mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CitiesListResponseMapper extends Mapper<CitiesListResponse, List<String>> {

    @Inject
    public CitiesListResponseMapper() {
    }

    /**
     * По-хорошему, нужно было создать класс CityEntity, но в данном приложении это было лишним
     */
    public List<String> map(CitiesListResponse response) {
        List<String> cities = new ArrayList<>();
        for (CitiesListResponse.City city : response.getCities()) {
            cities.add(city.getName());
        }
        return cities;
    }

}
