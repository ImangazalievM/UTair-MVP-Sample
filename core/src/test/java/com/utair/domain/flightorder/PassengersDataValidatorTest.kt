package com.utair.domain.flightorder

import com.utair.core.R
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.exceptions.PassengersDataValidationError
import com.utair.presentation.mvp.flightorder.PassengersData
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message

class PassengersDataValidatorTest : Spek({

    val resourceManager by memoized { mockk<ResourceManager>() }
    val validator by memoized(CachingMode.TEST) {
        PassengersDataValidator(resourceManager)
    }

    describe("on passengers count is correct") {
        val data = listOf(
                PassengersData(1, 0, 0),
                PassengersData(9, 0, 0),
                PassengersData(3, 3, 3)
        )
        val maxPassengerCountMessage = "Passengers count is too much"
        beforeEachTest {
            every {
                resourceManager.getString(R.string.max_passenger_count_message)
            } returns maxPassengerCountMessage
        }
        data.forEach {
            describe("a=${it.adultCount},k=${it.kidCount},b=${it.babyCount}") {
                it("should not throw any error") {
                    validator.validate(it)
                }
            }
        }
    }

    describe("passengers number exceeds the maximum allowed") {
        val data = listOf(
                PassengersData(10, 0, 0),
                PassengersData(4, 3, 3),
                PassengersData(2, 3, 5)
        )
        val maxPassengerCountMessage = "Passengers count is too much"
        beforeEachTest {
            every {
                resourceManager.getString(R.string.max_passenger_count_message, any())
            } returns maxPassengerCountMessage
        }
        data.forEach {

        }
    }

    describe("babies count more than adults") {
        val data = listOf(
                PassengersData(1, 0, 2),
                PassengersData(2, 3, 4)
        )
        val babiesLessThanAdultsMessage = "Babies less than adults"
        beforeEachTest {
            every {
                resourceManager.getString(R.string.babies_less_adults_message)
            } returns babiesLessThanAdultsMessage
        }
        data.forEach {
            describe("a=${it.adultCount},k=${it.kidCount},b=${it.babyCount}") {
                it("should throw error") {
                    expectThrows<PassengersDataValidationError> {
                        validator.validate(it)
                    }.message.isEqualTo(babiesLessThanAdultsMessage)
                }
            }
        }
    }

})
