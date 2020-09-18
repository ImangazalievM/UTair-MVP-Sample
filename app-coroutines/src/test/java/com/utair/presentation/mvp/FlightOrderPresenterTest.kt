package com.utair.presentation.mvp

import com.utair.domain.flightorder.MainInteractor
import com.utair.domain.global.exceptions.FlightOrderDataValidationError
import com.utair.domain.global.exceptions.NoNetworkException
import com.utair.domain.global.models.FlightOrderData
import com.utair.presentation.mvp.flightorder.FlightOrderPresenter
import com.utair.presentation.mvp.flightorder.FlightOrderView
import com.utair.presentation.mvp.global.ErrorHandler
import com.utair.presentation.ui.global.navigation.WeatherForecastScreen
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import me.aartikov.alligator.Navigator
import org.joda.time.DateTime
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FlightOrderPresenterTest : Spek({

    val interactor by memoized(CachingMode.TEST) { mockk<MainInteractor>() }
    val navigator by memoized { mockk<Navigator>() }
    val errorHandler by memoized { mockk<ErrorHandler>() }
    val view by memoized { mockk<FlightOrderView>(relaxed = true) }

    val presenter by memoized(CachingMode.TEST) {
        FlightOrderPresenter(
                interactor = interactor,
                navigator = navigator,
                errorHandler = errorHandler
        )
    }

    val flightOrderData by memoized {
        FlightOrderData("City", "City")
    }
    val cities by memoized {
        listOf("Anapa", "Moscow", "St. Petersburg")
    }

    beforeEachTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        every { interactor.getFlightOrderData() } returns flightOrderData
        coEvery { interactor.getCities() } returns cities
    }

    afterEachTest {
        Dispatchers.resetMain()
    }

    describe("start actions") {

        describe("cities list loading") {
            beforeEachTest {
                presenter.attachView(view)
            }

            it("should load cities list") {
                coVerify {
                    view.showEmptyArriveCity()
                    interactor.getCities()
                }
            }
        }

        describe("no network error when loading cities list") {
            beforeEachTest {
                coEvery { interactor.getCities() } throws NoNetworkException("Error message")
                presenter.attachView(view)
            }

            it("should show network error") {
                coVerify {
                    interactor.getCities()
                    view.showNoNetworkMessage()
                }
            }
        }
    }

    describe("user interactions") {
        beforeEachTest {
            presenter.attachView(view)
        }

        describe("city selection") {
            describe("on depart city clicked") {
                beforeEachTest { presenter.onDepartCityClicked() }
                it("should show depart city selector") {
                    verify { view.showDepartCitySelector(cities) }
                }
            }

            describe("on depart city selected") {
                val selectedCity = "Moscow"
                beforeEachTest { presenter.onDepartCitySelected(selectedCity) }
                it("should show selected city") {
                    verify {
                        view.showDepartCity(selectedCity)
                        view.enableSwapCitiesButton(true)
                    }
                }
            }

            describe("on arrive city clicked") {
                beforeEachTest { presenter.onDepartCityClicked() }
                it("should show depart city selector") {
                    verify { view.showDepartCitySelector(cities) }
                }
            }

            describe("on arrive city selected") {
                val selectedCity = "St. Petersburg"
                beforeEachTest { presenter.onArriveCitySelected(selectedCity) }
                it("should show selected city") {
                    verify {
                        view.showArriveCity(selectedCity)
                        view.enableSwapCitiesButton(true)
                    }
                }
            }

            describe("on swap cities button clicked") {
                val departCity = "Moscow"
                val arriveCity = "St. Petersburg"
                beforeEachTest {
                    presenter.onDepartCitySelected(departCity)
                    presenter.onArriveCitySelected(arriveCity)
                    presenter.onSwapCitiesButtonClicked()
                }
                it("should swap cities") {
                    verifyOrder {
                        view.showDepartCity(departCity)
                        view.showArriveCity(arriveCity)
                        //after swapping
                        view.showDepartCity(arriveCity)
                        view.showArriveCity(departCity)
                    }
                }
            }
        }

        describe("date selection") {

            describe("depart date selection") {
                describe("on date not selected before") {
                    beforeEachTest { presenter.onDepartDateClicked() }
                    it("should show selected city") {
                        verify {
                            view.showDepartDatePicker(any())
                        }
                    }
                }

                describe("on depart date selected") {
                    val departDate by memoized { DateTime() }
                    beforeEachTest {
                        presenter.onDepartDateClicked()
                        presenter.onDepartDateSelected(departDate)
                    }
                    it("should show depart date picker") {
                        verifyOrder {
                            view.showDepartDatePicker(any())
                            view.showDepartDate(departDate)
                        }
                    }
                }

                describe("on date selected before") {
                    val departDate by memoized { DateTime() }
                    beforeEachTest {
                        presenter.onDepartDateClicked()
                        presenter.onDepartDateSelected(departDate)
                        presenter.onDepartDateClicked()
                    }
                    it("should show depart date picker") {
                        verifyOrder {
                            view.showDepartDatePicker(any())
                            view.showDepartDate(departDate)
                            view.showDepartDatePicker(departDate)
                        }
                    }
                }
            }

            describe("return date selection") {
                describe("on date not selected before") {
                    beforeEachTest { presenter.onReturnDateClicked() }
                    it("should show selected city") {
                        verify {
                            view.showReturnDatePicker(any())
                        }
                    }
                }

                describe("on return date selected") {
                    val returnDate by memoized { DateTime() }
                    beforeEachTest {
                        presenter.onReturnDateClicked()
                        presenter.onReturnDateSelected(returnDate)
                    }
                    it("should show return date picker") {
                        verifyOrder {
                            view.showReturnDatePicker(any())
                            view.showReturnDate(returnDate)
                        }
                    }
                }

                describe("on return date selected before") {
                    val returnDate by memoized { DateTime() }
                    beforeEachTest {
                        presenter.onReturnDateClicked()
                        presenter.onReturnDateSelected(returnDate)
                        presenter.onReturnDateClicked()
                    }
                    it("should show return date picker") {
                        verifyOrder {
                            view.showReturnDatePicker(any())
                            view.showReturnDate(returnDate)
                            view.showReturnDatePicker(returnDate)
                        }
                    }
                }
            }

            describe("on set return date button clicked") {

            }

            describe("on clear return date clicked") {
                beforeEachTest {
                    presenter.onReturnDateClicked()
                    presenter.onReturnDateSelected(mockk())
                    presenter.onClearReturnDateClicked()
                }
                it("should hide return date") {
                    verify {
                        view.updateReturnDateVisibility(false)
                        view.showReturnDateButton(true)
                    }
                }
            }
        }


        describe("on find flights button clicked") {
            val flightData = slot<FlightOrderData>()

            describe("on invalid flight data") {
                beforeEachTest {
                    every {
                        interactor.validateFlightOrderData(capture(flightData))
                    } throws FlightOrderDataValidationError("My error message")
                    presenter.onFindFlightsButtonClicked()
                }

                it("should show error") {
                    verify {
                        interactor.validateFlightOrderData(any())
                    }

                    verify(inverse = true) {
                        interactor.saveFlightOrderData(any())
                        navigator.goForward(any())
                    }
                }
            }

            describe("on correct flight data") {
                val departCity = "Moscow"
                val arriveCity = "St. Petersburg"

                beforeEachTest {
                    every { interactor.validateFlightOrderData(capture(flightData)) } just Runs
                    every { interactor.saveFlightOrderData(capture(flightData)) } just Runs
                    every { navigator.goForward(any<WeatherForecastScreen>()) } just Runs
                    presenter.onDepartCitySelected(departCity)
                    presenter.onArriveCitySelected(arriveCity)
                    presenter.onFindFlightsButtonClicked()
                }
                it("should validate and save data") {
                    verifyOrder {
                        interactor.validateFlightOrderData(flightData.captured)
                        interactor.saveFlightOrderData(flightData.captured)
                        navigator.goForward(any<WeatherForecastScreen>())
                    }

                    expectThat(flightData.captured.arriveCity)
                            .isEqualTo(arriveCity)
                    expectThat(flightData.captured.departCity)
                            .isEqualTo(departCity)
                }
            }
        }

    }

})
