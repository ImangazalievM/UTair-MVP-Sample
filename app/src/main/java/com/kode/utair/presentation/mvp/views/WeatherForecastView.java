package com.kode.utair.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherForecastView extends MvpView {

    void showCitiesNames(String departCityName, String arriveCityName);
    void setTabSelected(int currentTabPosition);
    void openForecastPage(int position);
    void showForecastForCities(String departCity, String arriveCity);

}
