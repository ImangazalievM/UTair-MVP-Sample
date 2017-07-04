package com.kode.utair.presentation.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kode.utair.di.ApplicationComponent;
import com.kode.utair.domain.exceptions.NoNetworkException;
import com.kode.utair.domain.exceptions.WeatherSettingsValidationError;
import com.kode.utair.domain.interactors.MainInteractor;
import com.kode.utair.domain.models.WeatherSettings;
import com.kode.utair.presentation.mvp.views.MainView;
import com.kode.utair.presentation.utils.DebugUtils;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    MainInteractor mainInteractor;

    private WeatherSettings weatherSettings;
    private DateTime departureDate;
    private DateTime returnDate;
    private List<String> cities;

    public MainPresenter(ApplicationComponent applicationComponent) {

        applicationComponent.inject(this);

        initUi();
        loadWeatherSettings();
    }

    private void initUi() {
        departureDate = new DateTime();
        returnDate = new DateTime().plusWeeks(1);

        getViewState().showDepartDate(departureDate);
        getViewState().showReturnDate(returnDate);
    }

    private void loadWeatherSettings() {
        mainInteractor.getWeatherSettings()
                .subscribe(this::onWeatherSettingsLoaded);
    }

    private void onWeatherSettingsLoaded(WeatherSettings weatherSettings) {
        this.weatherSettings = weatherSettings;
        if (weatherSettings.getDepartCity() == null || weatherSettings.getArriveCity() == null) {
            getViewState().disableSwapCitiesButton();
        }
        updateCitiesView();
    }

    public void onDepartCityClicked() {
        if (cities == null) {
            fetchCities(cities -> getViewState().showDepartCitySelector(cities));
        } else {
            getViewState().showDepartCitySelector(cities);
        }
    }

    public void onDepartCitySelected(String departCity) {
        weatherSettings.setDepartCity(departCity);
        getViewState().showDepartCity(departCity);
        getViewState().enableSwapCitiesButton();
    }

    public void onArriveCityClicked() {
        if (cities == null) {
            fetchCities(cities -> getViewState().showArriveCitySelector(cities));
        } else {
            getViewState().showArriveCitySelector(cities);
        }
    }

    public void onArriveCitySelected(String arriveCity) {
        weatherSettings.setArriveCity(arriveCity);
        getViewState().showArriveCity(arriveCity);
        getViewState().enableSwapCitiesButton();
    }

    public void onSwapCitiesButtonClicked() {
        String departCity = weatherSettings.getDepartCity();
        String arriveCity = weatherSettings.getArriveCity();
        weatherSettings.setDepartCity(arriveCity);
        weatherSettings.setArriveCity(departCity);
        updateCitiesView();
    }

    public void onDepartDateClicked() {
        getViewState().showDepartDatePicker(departureDate);
    }

    public void onDepartDateSelected(DateTime departureDate) {
        this.departureDate = departureDate;
        getViewState().showDepartDate(returnDate);
    }

    public void onReturnDateClicked() {
        getViewState().showReturnDatePicker(departureDate);
    }

    public void onReturnDateSelected(DateTime returnDate) {
        this.returnDate = returnDate;
        getViewState().showReturnDate(returnDate);
    }

    public void onSetReturnDateButtonClicked() {
        getViewState().showReturnDatePicker(departureDate);
    }

    public void onClearReturnDateClicked() {
        this.returnDate = null;
        getViewState().hideReturnDate();
        getViewState().showReturnDateButton();
    }

    public void onFindFlightsButtonClicked() {
        mainInteractor.validateData(weatherSettings)
                .subscribe(this::onValidationPassed, this::handleValidationError);

    }

    private void onValidationPassed() {
        mainInteractor.saveWeatherSettings(weatherSettings)
                .subscribe(() -> getViewState().openWeatherScreen());
    }

    private void handleValidationError(Throwable throwable) {
        if (throwable instanceof WeatherSettingsValidationError) {
            getViewState().showValidationErrorMessage(((WeatherSettingsValidationError) throwable).getErrorMessage());
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

    private void updateCitiesView() {
        if (weatherSettings.getDepartCity() != null) {
            getViewState().showDepartCity(weatherSettings.getDepartCity());
        } else {
            getViewState().showEmptyDepartCity();
        }

        if (weatherSettings.getArriveCity() != null) {
            getViewState().showArriveCity(weatherSettings.getArriveCity());
        } else {
            getViewState().showEmptyArriveCity();

        }
    }

    private void fetchCities(Consumer<List<String>> successCallback) {
        mainInteractor.getCities()
                .subscribe(cities -> {
                    Collections.sort(cities, String::compareToIgnoreCase);
                    this.cities = cities;
                    successCallback.accept(cities);
                }, this::handleFetchCitiesError);
    }

    private void handleFetchCitiesError(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            getViewState().showNoNetworkMessage();
        } else {
            DebugUtils.showDebugErrorMessage(throwable);
        }
    }

}
