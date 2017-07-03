package com.kode.utair.domain.usecases;

import com.kode.utair.di.qualifiers.JobScheduler;
import com.kode.utair.di.qualifiers.UiScheduler;
import com.kode.utair.domain.models.WeatherForecastEntity;
import com.kode.utair.domain.repository.IWeatherRepository;
import com.kode.utair.domain.usecases.base.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetWeatherForecastUseCase extends SingleUseCase<String, WeatherForecastEntity> {

    private IWeatherRepository weatherRepository;

    @Inject
    public GetWeatherForecastUseCase(@JobScheduler Scheduler jobScheduler,
                                     @UiScheduler Scheduler uiScheduler,
                                     IWeatherRepository weatherRepository) {
        super(jobScheduler, uiScheduler);

        this.weatherRepository = weatherRepository;
    }

    @Override
    protected Single<WeatherForecastEntity> buildSingle(String cityName) {
        return weatherRepository.getWeatherForecastForCity(cityName);
    }

}