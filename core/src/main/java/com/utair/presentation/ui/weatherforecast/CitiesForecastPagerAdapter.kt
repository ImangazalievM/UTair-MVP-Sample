package com.utair.presentation.ui.weatherforecast

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CitiesForecastPagerAdapter(
        fragmentManager: FragmentManager,
        private val departCityName: String,
        private val arriveCityName: String,
        private val fragmentConstructor: (name: String) -> Fragment
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            DEPART_CITY_POSITION -> fragmentConstructor(departCityName)
            ARRIVE_CITY_POSITION -> fragmentConstructor(arriveCityName)
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