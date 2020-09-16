package com.utair.presentation.mvp

import com.utair.di.PrimitiveWrapper
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.global.models.WeatherForecast
import com.utair.domain.weatherforecast.WeatherForecastInteractor
import com.utair.presentation.mvp.global.ErrorHandler
import com.utair.presentation.mvp.weatherforecast.CityWeatherForecastPresenter
import com.utair.presentation.mvp.weatherforecast.CityWeatherForecastView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe

class CityWeatherForecastPresenterTest : Spek({

    val cityName = "Moscow"
    val cityNameWrapper by memoized { mockk<PrimitiveWrapper<String>>() }
    val interactor by memoized { mockk<WeatherForecastInteractor>() }
    val errorHandler by memoized { mockk<ErrorHandler>() }
    val view by memoized { mockk<CityWeatherForecastView>(relaxed = true) }

    val presenter by memoized(CachingMode.TEST) {
        CityWeatherForecastPresenter(
                cityName = cityNameWrapper,
                weatherForecastInteractor = interactor,
                errorHandler = errorHandler
        )
    }

    val weatherForecast by memoized(CachingMode.TEST) {
        mockk<WeatherForecast>()
    }

    beforeEachTest {
        every { cityNameWrapper.value } returns cityName

        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { interactor.getWeatherForecastForCity(cityName) } returns weatherForecast
    }

    afterEachTest {
        Dispatchers.resetMain()
    }

    describe("start actions") {
        describe("forecasts loading") {

            describe("on network is available") {
                val dailyForecasts by memoized { mockk<List<WeatherForecast.DailyForecast>>() }
                beforeEachTest {
                    every { weatherForecast.dailyForecasts } returns dailyForecasts
                    presenter.attachView(view)
                }

                it("should show forecast") {
                    coVerify {
                        interactor.getWeatherForecastForCity(cityName)
                        view.showForecast(dailyForecasts)
                    }
                }
            }

            describe("on no network error") {
                beforeEachTest {
                    coEvery { interactor.getWeatherForecastForCity(cityName) } throws NoNetworkException("Error message")
                    presenter.attachView(view)
                }

                it("should show network error") {
                    coVerify {
                        interactor.getWeatherForecastForCity(cityName)
                        view.showNoNetworkMessage()
                    }
                }
            }
        }

    }

})
