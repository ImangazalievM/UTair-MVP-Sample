package com.kode.utair.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kode.utair.di.ApplicationComponent;
import com.kode.utair.domain.interactors.WeatherForecastInteractor;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.presentation.mvp.views.WeatherForecastView;
import com.kode.utair.presentation.utils.DebugUtils;

import javax.inject.Inject;

@InjectViewState
public class WeatherForecastPresenter extends MvpPresenter<WeatherForecastView> {

    @Inject
    WeatherForecastInteractor weatherForecastInteractor;

    private int currentTabPosition;

    public WeatherForecastPresenter(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
        initUi();
    }

    private void initUi() {
        onTabSelected(0);
        showCitiesForecasts();
    }

    private void showCitiesForecasts() {
        weatherForecastInteractor.getWeatherSettings()
                .subscribe(this::onWeatherSettingsLoaded, this::handleError);
    }

    private void onWeatherSettingsLoaded(WeatherSettings weatherSettings) {
        String departCity = weatherSettings.getDepartCity();
        String arriveCity =  weatherSettings.getArriveCity();
        getViewState().showCitiesNames(departCity, arriveCity);
        getViewState().showForecastForCities(departCity,arriveCity);
    }

    private void handleError(Throwable throwable) {
        DebugUtils.showDebugErrorMessage(throwable);
    }

    public void onTabSelected(int position) {
        getViewState().setTabSelected(position);
        getViewState().openForecastPage(position);
    }

}
