package com.kode.utair.presentation.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kode.utair.R
import com.kode.utair.UTairApplication.Companion.component
import com.kode.utair.presentation.mvp.presenters.WeatherForecastPresenter
import com.kode.utair.presentation.mvp.views.WeatherForecastView
import com.kode.utair.presentation.ui.adapters.CitiesForecastPagerAdapter
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import kotlinx.android.synthetic.main.cities_forecasts_tabs.*
import kotlinx.android.synthetic.main.toolbar_white.*

class WeatherForecastActivity : BaseMvpActivity(), WeatherForecastView {

    @InjectPresenter
    lateinit var weatherPresenter: WeatherForecastPresenter

    @ProvidePresenter
    fun providePresenter(): WeatherForecastPresenter {
        return component().getWeatherForecastPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        toolbar.setNavigationOnClickListener { onBackPressed() }
        forwardTab.setOnClickListener {
            weatherPresenter.onTabSelected(DEPART_CITY_TAB_POSITION)
        }
        returnTab.setOnClickListener {
            weatherPresenter.onTabSelected(ARRIVE_CITY_TAB_POSITION)
        }
        citiesForecastPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                weatherPresenter.onTabSelected(position)
            }
        })
    }

    override fun showCitiesNames(departCityName: String, arriveCityName: String) {
        this@WeatherForecastActivity.departCityLabel.text = departCityName
        this@WeatherForecastActivity.arriveCityLabel.text = arriveCityName
    }

    override fun setTabSelected(currentTabPosition: Int) {
        forwardTab.isSelected = currentTabPosition == DEPART_CITY_TAB_POSITION
        returnTab.isSelected = currentTabPosition == ARRIVE_CITY_TAB_POSITION
    }

    override fun openForecastPage(position: Int) {
        citiesForecastPager.currentItem = position
    }

    override fun showForecastForCities(departCity: String, arriveCity: String) {
        citiesForecastPager.adapter = CitiesForecastPagerAdapter(
                fragmentManager = supportFragmentManager,
                departCityName = departCity,
                arriveCityName = arriveCity
        )
    }

    companion object {
        const val DEPART_CITY_TAB_POSITION = 0
        const val ARRIVE_CITY_TAB_POSITION = 1
        fun buildIntent(activity: Activity?): Intent {
            return Intent(activity, WeatherForecastActivity::class.java)
        }
    }
}