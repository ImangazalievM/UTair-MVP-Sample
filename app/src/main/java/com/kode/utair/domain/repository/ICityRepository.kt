package com.kode.utair.domain.repository

import io.reactivex.Single

interface ICityRepository {

    fun getCitiesList(): Single<List<String>>

}