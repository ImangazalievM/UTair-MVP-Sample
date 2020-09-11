package com.utair.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.utair.presentation.ui.fragments.CityWeatherForecastFragment

class CitiesForecastPagerAdapter(
        fragmentManager: FragmentManager,
        private val departCityName: String,
        private val arriveCityName: String
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            DEPART_CITY_POSITION -> CityWeatherForecastFragment.newInstance(departCityName)
            ARRIVE_CITY_POSITION -> CityWeatherForecastFragment.newInstance(arriveCityName)
            else -> throw IllegalStateException()
        }
    }

    override fun getCount(): Int {
        return TABS_COUNT
    }

    companion object {
        const val TABS_COUNT = 2
        const val DEPART_CITY_POSITION = 0
        const val ARRIVE_CITY_POSITION = 1
    }

}