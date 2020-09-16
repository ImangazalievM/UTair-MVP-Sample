package com.utair.presentation.mvp

import com.utair.di.PrimitiveWrapper
import com.utair.domain.flightorder.MainInteractor
import com.utair.domain.global.models.FlightOrderData
import com.utair.presentation.mvp.global.ErrorHandler
import com.utair.presentation.mvp.weatherforecast.WeatherForecastPresenter
import com.utair.presentation.mvp.weatherforecast.WeatherForecastView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import me.aartikov.alligator.Navigator
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe

class WeatherForecastPresenterTest : Spek({

    val departCity = "Moscow"
    val departCityWrapper by memoized { mockk<PrimitiveWrapper<String>>() }
    val arriveCity = "St. Petersburg"
    val arriveCityWrapper by memoized { mockk<PrimitiveWrapper<String>>() }

    val view by memoized { mockk<WeatherForecastView>(relaxed = true) }

    val presenter by memoized(CachingMode.TEST) {
        WeatherForecastPresenter(
                departCity = departCityWrapper,
                arriveCity = arriveCityWrapper
        )
    }

    beforeEachTest {
        Dispatchers.setMain(Dispatchers.Unconfined)

        every { departCityWrapper.value } returns departCity
        every { arriveCityWrapper.value } returns arriveCity
    }

    afterEachTest {
        Dispatchers.resetMain()
    }

    describe("start actions") {
        beforeEachTest {
            presenter.attachView(view)
        }

        it("should open tab depart tab") {
            verifyOrder {
                view.showForecastForCities(departCity, arriveCity)
                view.openForecastPage(WeatherForecastPresenter.DEPART_CITY_TAB_POSITION)
            }
        }

    }

    describe("user interactions") {

        beforeEachTest {
            presenter.attachView(view)
        }

        describe("on tab selected") {
            beforeEachTest {
                presenter.onTabSelected(WeatherForecastPresenter.ARRIVE_CITY_TAB_POSITION)
                presenter.onTabSelected(WeatherForecastPresenter.DEPART_CITY_TAB_POSITION)
            }

            it("should open tab depart tab") {
                verifyOrder {
                    view.showForecastForCities(departCity, arriveCity)
                    view.openForecastPage(WeatherForecastPresenter.DEPART_CITY_TAB_POSITION)
                    view.openForecastPage(WeatherForecastPresenter.ARRIVE_CITY_TAB_POSITION)
                    view.openForecastPage(WeatherForecastPresenter.DEPART_CITY_TAB_POSITION)
                }
            }

        }

    }

})
