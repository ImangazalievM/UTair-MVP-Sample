package com.kode.utair.data.repository

import com.kode.utair.data.network.UTairApiService
import com.kode.utair.data.network.mappers.CitiesListResponseMapper
import com.kode.utair.domain.repository.ICityRepository
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