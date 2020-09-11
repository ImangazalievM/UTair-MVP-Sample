package com.utair.data.repository

import com.utair.data.network.UTairApiService
import com.utair.data.network.mappers.CitiesListResponseMapper
import com.utair.domain.repository.ICityRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(
        private val utairApiService: UTairApiService,
        private val citiesListResponseMapper: CitiesListResponseMapper
) : ICityRepository {

    override fun getCitiesList(): Single<List<String>> {
        return utairApiService.getCities()
                .map { citiesListResponseMapper.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}