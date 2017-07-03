package com.kode.utair.domain.repository;

import java.util.List;

import io.reactivex.Single;

public interface ICityRepository {

    Single<List<String>> getCitiesList();

}
