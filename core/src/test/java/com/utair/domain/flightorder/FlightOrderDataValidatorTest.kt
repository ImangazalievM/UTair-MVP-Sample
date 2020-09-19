package com.utair.domain.flightorder

import com.utair.core.R
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.exceptions.FlightOrderDataValidationError
import com.utair.domain.global.models.FlightOrderData
import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.message

class FlightOrderDataValidatorTest : Spek({

    val resourceManager by memoized { mockk<ResourceManager>() }
    val validator by memoized(CachingMode.TEST) {
        FlightOrderDataValidator(resourceManager)
    }

    describe("on depart city is not selected") {
        val errorMessage = "Depart city not selected"
        val data = FlightOrderData(null, "London")
        beforeEachTest {
            every {
                resourceManager.getString(R.string.depart_city_is_empty_message)
            } returns errorMessage
        }
        it("should throw 'arrive city not selected' error") {
            expectThrows<FlightOrderDataValidationError> {
                validator.validate(data)
            }.message.isEqualTo(errorMessage)
        }
    }

    describe("on arrive city is not selected") {
        val errorMessage = "Arrive city not selected"
        val data = FlightOrderData("London", null)
        beforeEachTest {
            every {
                resourceManager.getString(R.string.arrive_city_is_empty_message)
            } returns errorMessage
        }
        it("should throw 'arrive city not selected' error") {
            expectThrows<FlightOrderDataValidationError> {
                validator.validate(data)
            }.message.isEqualTo(errorMessage)
        }
    }

})
