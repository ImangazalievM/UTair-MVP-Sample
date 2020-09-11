package com.kode.utair.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kode.utair.R;
import com.kode.utair.UTairApplication;
import com.kode.utair.presentation.mvp.presenters.WeatherForecastPresenter;
import com.kode.utair.presentation.mvp.views.WeatherForecastView;
import com.kode.utair.presentation.ui.adapters.CitiesForecastPagerAdapter;

public class WeatherForecastActivity extends BaseMvpActivity implements WeatherForecastView {

    public static final int DEPART_CITY_TAB_POSITION = 0;
    public static final int ARRIVE_CITY_TAB_POSITION = 1;

    public static Intent buildIntent(Activity activity) {
        return new Intent(activity, WeatherForecastActivity.class);
    }

    @InjectPresenter
    WeatherForecastPresenter weatherPresenter;

    @ProvidePresenter
    WeatherForecastPresenter providePresenter() {
        return new WeatherForecastPresenter(UTairApplication.component());
    }

    private TextView departCity;
    private TextView arriveCity;
    private View forwardTab;
    private View returnTab;
    private ViewPager citiesForecastPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        departCity = (TextView) findViewById(R.id.depart_city);
        arriveCity = (TextView) findViewById(R.id.arrive_city);
        forwardTab = findViewById(R.id.forward_tab);
        returnTab = findViewById(R.id.return_tab);
        forwardTab.setOnClickListener(v -> weatherPresenter.onTabSelected(DEPART_CITY_TAB_POSITION));
        returnTab.setOnClickListener(v -> weatherPresenter.onTabSelected(ARRIVE_CITY_TAB_POSITION));

        citiesForecastPager = (ViewPager) findViewById(R.id.cities_forecasts_pager);
        citiesForecastPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                weatherPresenter.onTabSelected(position);
            }
        });
    }

    @Override
    public void showCitiesNames(String departCityName, String arriveCityName) {
        departCity.setText(departCityName);
        arriveCity.setText(arriveCityName);
    }

    @Override
    public void setTabSelected(int currentTabPosition) {
        forwardTab.setSelected(currentTabPosition == DEPART_CITY_TAB_POSITION);
        returnTab.setSelected(currentTabPosition == ARRIVE_CITY_TAB_POSITION);
    }

    @Override
    public void openForecastPage(int position) {
        citiesForecastPager.setCurrentItem(position);
    }

    @Override
    public void showForecastForCities(String departCity, String arriveCity) {
        citiesForecastPager.setAdapter(new CitiesForecastPagerAdapter(getSupportFragmentManager(), departCity, arriveCity));
    }


}
