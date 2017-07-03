package com.kode.utair.domain.usecases;

import com.kode.utair.di.qualifiers.JobScheduler;
import com.kode.utair.di.qualifiers.UiScheduler;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.domain.repository.IWeatherSettingsRepository;
import com.kode.utair.domain.usecases.base.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetWeatherSettingsUseCase extends SingleUseCase<Void, WeatherSettings> {

    private IWeatherSettingsRepository weatherForecastSettingsRepository;

    @Inject
    public GetWeatherSettingsUseCase(@JobScheduler Scheduler jobScheduler,
                                     @UiScheduler Scheduler uiScheduler,
                                     IWeatherSettingsRepository weatherForecastSettingsRepository) {
        super(jobScheduler, uiScheduler);

        this.weatherForecastSettingsRepository = weatherForecastSettingsRepository;
    }

    @Override
    protected Single<WeatherSettings> buildSingle(Void arg) {
        return weatherForecastSettingsRepository.getWeatherSettings();
    }

}