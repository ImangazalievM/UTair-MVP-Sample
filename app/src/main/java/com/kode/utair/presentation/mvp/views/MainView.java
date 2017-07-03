package com.kode.utair.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import org.joda.time.DateTime;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void showEmptyDepartCity();
    void showDepartCity(String arriveCity);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDepartCitySelector(List<String> cities);

    void showEmptyArriveCity();
    void showArriveCity(String arriveCity);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showArriveCitySelector(List<String> cities);

    void disableSwapCitiesButton();
    void enableSwapCitiesButton();

    void showDepartDate(DateTime departDate);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDepartDatePicker(DateTime departDate);
    void showReturnDate(DateTime returnDate);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showReturnDatePicker(DateTime returnDate);

    void hideReturnDate();
    void showReturnDateButton();
    void hideReturnDateButton();

    void showValidationErrorMessage(String errorMessage);

    void openWeatherScreen();

}
