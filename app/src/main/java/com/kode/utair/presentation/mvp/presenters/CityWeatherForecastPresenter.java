package com.kode.utair.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kode.utair.di.ApplicationComponent;
import com.kode.utair.domain.exceptions.NoNetworkException;
import com.kode.utair.domain.interactors.WeatherForecastInteractor;
import com.kode.utair.presentation.mvp.views.CityWeatherForecastView;
import com.kode.utair.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class CityWeatherForecastPresenter extends MvpPresenter<CityWeatherForecastView> {

    @Inject
    WeatherForecastInteractor weatherForecastInteractor;

    private String cityName;

    public CityWeatherForecastPresenter(ApplicationComponent applicationComponent, String cityName) {
        this.cityName = cityName;

        applicationComponent.inject(this);
        initUi();
    }

    private void initUi() {
        showForecast();
    }

    private void showForecast() {
        weatherForecastInteractor.getWeatherForecastForCity(cityName)
                .subscribe(weatherForecastEntity -> getViewState().showForecast(weatherForecastEntity.getDailyForecasts()),
                        this::handleError);
    }

    private void handleError(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            getViewState().showNoNetworkMessage();
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

}
