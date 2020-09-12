package com.utair.presentation.ui.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.utair.R
import com.utair.di.presenters.weatherforecast.cityforecast.CityForecastPresenterModule
import com.utair.di.presenters.weatherforecast.cityforecast.DaggerCityForecastComponent
import com.utair.domain.global.models.WeatherForecast.DailyForecast
import com.utair.presentation.mvp.weatherforecast.CityWeatherForecastPresenter
import com.utair.presentation.mvp.weatherforecast.CityWeatherForecastView
import com.utair.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_city_weather_forecast.*

class CityWeatherForecastFragment : BaseFragment(), CityWeatherForecastView {

    @InjectPresenter
    lateinit var weatherPresenter: CityWeatherForecastPresenter

    @ProvidePresenter
    fun providePresenter(): CityWeatherForecastPresenter {
        val cityName = arguments!!.getString(CITY_NAME_ARG)!!
        return DaggerCityForecastComponent.builder()
                .applicationComponent(appComponent)
                .cityForecastPresenterModule(CityForecastPresenterModule(cityName))
                .build()
                .getCityWeatherForecastPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_weather_forecast, container, false)
    }

    override fun showForecast(dailyForecasts: List<DailyForecast>) {
        forecastList.adapter = DailyForecastAdapter(context!!, dailyForecasts)
    }

    override fun showNoNetworkMessage() {
        Toast.makeText(context, getString(R.string.no_network_message), Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val CITY_NAME_ARG = "city_name"

        fun newInstance(cityName: String): CityWeatherForecastFragment {
            val fragment = CityWeatherForecastFragment()
            fragment.arguments = Bundle().apply {
                putString(CITY_NAME_ARG, cityName)
            }
            return fragment
        }

    }
}