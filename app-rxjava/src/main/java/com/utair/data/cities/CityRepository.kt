package com.utair.data.cities

import com.utair.data.global.network.UTairApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityRepository @Inject constructor(
        private val utairApiService: UTairApiService
) {

    fun getCitiesList(): Single<List<String>> {
        return utairApiService.getCities()
                .map { it.cities.map { city -> city.name } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}