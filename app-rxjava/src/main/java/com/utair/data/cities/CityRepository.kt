package com.utair.data.cities

import com.utair.data.global.network.UTairApiService
import com.utair.data.global.network.mappers.CitiesListResponseMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(
        private val utairApiService: UTairApiService,
        private val citiesListResponseMapper: CitiesListResponseMapper
) {

    fun getCitiesList(): Single<List<String>> {
        return utairApiService.getCities()
                .map { citiesListResponseMapper.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}