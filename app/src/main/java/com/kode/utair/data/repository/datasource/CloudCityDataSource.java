package com.kode.utair.data.repository.datasource;

import com.kode.utair.data.network.CityApiService;
import com.kode.utair.data.network.mappers.CitiesListResponseMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


public class CloudCityDataSource {

    private CityApiService cityApiService;
    private CitiesListResponseMapper citiesListResponseMapper;

    @Inject
    public CloudCityDataSource(CityApiService cityApiService,
                               CitiesListResponseMapper citiesListResponseMapper) {

        this.cityApiService = cityApiService;
        this.citiesListResponseMapper = citiesListResponseMapper;
    }

    public Single<List<String>> getCitiesList() {
        return cityApiService.getCities()
                .map(cityResponse -> citiesListResponseMapper.map(cityResponse));

    }

}
