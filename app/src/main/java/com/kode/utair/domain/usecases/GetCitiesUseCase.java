package com.kode.utair.domain.usecases;

import com.kode.utair.di.qualifiers.JobScheduler;
import com.kode.utair.di.qualifiers.UiScheduler;
import com.kode.utair.domain.repository.ICityRepository;
import com.kode.utair.domain.usecases.base.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetCitiesUseCase extends SingleUseCase<Void, List<String>> {

    private ICityRepository cityRepository;

    @Inject
    public GetCitiesUseCase(@JobScheduler Scheduler jobScheduler,
                            @UiScheduler Scheduler uiScheduler,
                            ICityRepository cityRepository) {
        super(jobScheduler, uiScheduler);

        this.cityRepository = cityRepository;
    }

    @Override
    protected Single<List<String>> buildSingle(Void arg) {
        return cityRepository.getCitiesList();
    }

}