package com.utair

import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.utair.presentation.ui.flightorder.MainActivity
import com.utair.screens.FlightOrderScreen
import org.junit.Rule
import org.junit.Test

class FlightOrderScreenTest : TestCase() {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun test() = run {
        step("Open Flight Order Screen") {
            activityTestRule.launchActivity(null)
            FlightOrderScreen {
                departCityButton {
                    click()
                }
            }
        }

    }
}