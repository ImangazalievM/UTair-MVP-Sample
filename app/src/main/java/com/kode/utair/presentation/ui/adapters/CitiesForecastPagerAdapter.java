package com.kode.utair.presentation.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kode.utair.presentation.ui.fragments.CityWeatherForecastFragment;

public class CitiesForecastPagerAdapter extends FragmentPagerAdapter {

    public static final int TABS_COUNT = 2;
    public static final int DEPART_CITY_POSITION = 0;
    public static final int ARRIVE_CITY_POSITION = 1;

    private String departCityName;
    private String arriveCityName;

    public CitiesForecastPagerAdapter(FragmentManager fm, String departCityName, String arriveCityName) {
        super(fm);
        this.departCityName = departCityName;
        this.arriveCityName = arriveCityName;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DEPART_CITY_POSITION:
                return CityWeatherForecastFragment.newInstance(departCityName);
            case ARRIVE_CITY_POSITION:
                return CityWeatherForecastFragment.newInstance(arriveCityName);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }


}