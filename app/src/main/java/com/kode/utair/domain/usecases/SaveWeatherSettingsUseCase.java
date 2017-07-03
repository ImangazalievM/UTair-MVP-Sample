package com.kode.utair.domain.usecases;

import com.kode.utair.di.qualifiers.JobScheduler;
import com.kode.utair.di.qualifiers.UiScheduler;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.domain.repository.IWeatherSettingsRepository;
import com.kode.utair.domain.usecases.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SaveWeatherSettingsUseCase extends CompletableUseCase<WeatherSettings> {

    private IWeatherSettingsRepository weatherForecastSettingsRepository;

    @Inject
    public SaveWeatherSettingsUseCase(@JobScheduler Scheduler jobScheduler,
                                      @UiScheduler Scheduler uiScheduler,
                                      IWeatherSettingsRepository weatherForecastSettingsRepository) {
        super(jobScheduler, uiScheduler);

        this.weatherForecastSettingsRepository = weatherForecastSettingsRepository;
    }

    @Override
    protected Completable buildCompletable(WeatherSettings weatherSettings) {
        return weatherForecastSettingsRepository.saveWeatherSettings(weatherSettings);
    }

}