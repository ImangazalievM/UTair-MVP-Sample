package com.utair.presentation.ui.weatherforecast

import android.os.Bundle
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.utair.core.databinding.ActivityWeatherForecastBinding
import com.utair.di.bindPrimitive
import com.utair.di.get
import com.utair.di.installModule
import com.utair.di.qualifiers.ArriveCity
import com.utair.di.qualifiers.DepartCity
import com.utair.presentation.mvp.weatherforecast.WeatherForecastPresenter
import com.utair.presentation.mvp.weatherforecast.WeatherForecastPresenter.Companion.ARRIVE_CITY_TAB_POSITION
import com.utair.presentation.mvp.weatherforecast.WeatherForecastPresenter.Companion.DEPART_CITY_TAB_POSITION
import com.utair.presentation.mvp.weatherforecast.WeatherForecastView
import com.utair.presentation.ui.global.base.BaseActivity
import com.utair.presentation.ui.global.navigation.WeatherForecastScreen
import toothpick.Scope

class WeatherForecastActivity : BaseActivity(), WeatherForecastView {

    override val scopeModuleInstaller: (Scope) -> Unit = {
        val screen = screenResolver.getScreen<WeatherForecastScreen>(this)
        it.installModule {
            bindPrimitive(DepartCity::class, screen.departCity)
            bindPrimitive(ArriveCity::class, screen.arriveCity)
        }
    }

    private val binding by lazy {
        ActivityWeatherForecastBinding.inflate(layoutInflater)
    }

    @InjectPresenter
    lateinit var presenter: WeatherForecastPresenter

    @ProvidePresenter
    fun providePresenter() = scope.get<WeatherForecastPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.tabs.forwardTab.setOnClickListener {
            presenter.onTabSelected(DEPART_CITY_TAB_POSITION)
        }
        binding.tabs.returnTab.setOnClickListener {
            presenter.onTabSelected(ARRIVE_CITY_TAB_POSITION)
        }
        binding.citiesForecastPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                presenter.onTabSelected(position)
            }
        })
    }

    override fun showForecastForCities(departCity: String, arriveCity: String) {
        binding.toolbar.departCityLabel.text = departCity
        binding.toolbar.arriveCityLabel.text = arriveCity

        binding.citiesForecastPager.adapter = CitiesForecastPagerAdapter(
                fragmentManager = supportFragmentManager,
                departCityName = departCity,
                arriveCityName = arriveCity,
                fragmentConstructor = {
                    CityWeatherForecastFragment.newInstance(it)
                }
        )
    }

    override fun openForecastPage(position: Int) {
        binding.tabs.forwardTab.isSelected = position == DEPART_CITY_TAB_POSITION
        binding.tabs.returnTab.isSelected = position == ARRIVE_CITY_TAB_POSITION
        binding.citiesForecastPager.currentItem = position
    }

}