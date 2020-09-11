package com.utair.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.utair.R
import com.utair.UTairApplication.Companion.component
import com.utair.domain.models.WeatherForecastEntity.DailyForecast
import com.utair.presentation.mvp.presenters.CityWeatherForecastPresenter
import com.utair.presentation.mvp.views.CityWeatherForecastView
import com.utair.presentation.ui.adapters.DailyForecastAdapter
import com.utair.presentation.ui.base.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_city_weather_forecast.*

class CityWeatherForecastFragment : MvpAppCompatFragment(), CityWeatherForecastView {

    @InjectPresenter
    lateinit var weatherPresenter: CityWeatherForecastPresenter

    @ProvidePresenter
    fun providePresenter(): CityWeatherForecastPresenter {
        val cityName = arguments!!.getString(CITY_NAME_ARG)!!
        val interactor = component().getWeatherForecastInteractor()
        return CityWeatherForecastPresenter(interactor, cityName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_weather_forecast, container, false)
    }

    override fun showForecast(dailyForecasts: List<DailyForecast>) {
        forecastList.adapter = DailyForecastAdapter(context, dailyForecasts)
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