package com.kode.utair.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kode.utair.R;
import com.kode.utair.UTairApplication;
import com.kode.utair.domain.models.WeatherForecastEntity;
import com.kode.utair.presentation.mvp.presenters.CityWeatherForecastPresenter;
import com.kode.utair.presentation.mvp.views.CityWeatherForecastView;
import com.kode.utair.presentation.ui.adapters.DailyForecastAdapter;

import java.util.List;

public class CityWeatherForecastFragment extends MvpAppCompatFragment implements CityWeatherForecastView {

    public static final String CITY_NAME_ARG = "city_name";

    public static CityWeatherForecastFragment newInstance(String cityName) {
        CityWeatherForecastFragment fragment = new CityWeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString(CITY_NAME_ARG, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    CityWeatherForecastPresenter weatherPresenter;

    @ProvidePresenter
    CityWeatherForecastPresenter providePresenter() {
        String cityName = getArguments().getString(CITY_NAME_ARG);
        return new CityWeatherForecastPresenter(UTairApplication.component(), cityName);
    }

    private RecyclerView forecastList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_weather_forecast, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forecastList = (RecyclerView) view.findViewById(R.id.weather_forecast_list);
    }

    @Override
    public void showForecast(List<WeatherForecastEntity.DailyForecast> dailyForecasts) {
        forecastList.setAdapter(new DailyForecastAdapter(getContext(), dailyForecasts));
    }

    @Override
    public void showNoNetworkMessage() {
        Toast.makeText(getContext(), getString(R.string.no_network_message), Toast.LENGTH_SHORT).show();
    }

}
