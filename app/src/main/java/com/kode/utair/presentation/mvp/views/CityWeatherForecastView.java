package com.kode.utair.presentation.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kode.utair.domain.models.WeatherForecastEntity;

import java.util.List;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface CityWeatherForecastView extends MvpView {

    void showForecast(List<WeatherForecastEntity.DailyForecast> dailyForecasts);
    void showNoNetworkMessage();

}

