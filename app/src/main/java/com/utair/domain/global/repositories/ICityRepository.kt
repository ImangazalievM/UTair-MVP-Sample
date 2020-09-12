package com.utair.domain.global.repositories

import io.reactivex.Single

interface ICityRepository {

    fun getCitiesList(): Single<List<String>>

}