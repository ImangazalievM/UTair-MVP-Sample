package com.kode.utair.data.repository;

import com.kode.utair.data.repository.datasource.CloudCityDataSource;
import com.kode.utair.domain.repository.ICityRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CityRepository implements ICityRepository {

    private CloudCityDataSource cloudCityDataSource;

    @Inject
    public CityRepository(CloudCityDataSource cloudCityDataSource) {
        this.cloudCityDataSource = cloudCityDataSource;
    }

    @Override
    public Single<List<String>> getCitiesList() {
        return cloudCityDataSource.getCitiesList();
    }

}
